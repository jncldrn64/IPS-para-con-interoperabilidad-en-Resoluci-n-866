package com.example.demo.servicio;

import com.example.demo.modelo.Catalogo;
import com.example.demo.modelo.CatalogoCambio;
import com.example.demo.modelo.CatalogoVersion;
import com.example.demo.modelo.Motivo;
import com.example.demo.repositorio.CatalogoRepositorio;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;

/** La base común de los catálogos: la lógica que comparten los 8 (docs/DESIGN.md, sección 7). */
public abstract class ServicioCatalogo<T extends Catalogo> implements CrudCatalogo<T> {

    private final Class<T> tipo;
    private final CatalogoRepositorio<T> datos;
    private final Validator validador;
    private final RegistroCatalogos registro;

    protected ServicioCatalogo(Class<T> tipo, CatalogoRepositorio<T> datos, Validator validador,
            RegistroCatalogos registro) {
        this.tipo = tipo;
        this.datos = datos;
        this.validador = validador;
        this.registro = registro;
    }

    /** El nombre con que el catálogo aparece en las versiones y en la tabla de cambios. */
    protected String catalogo() {
        return tipo.getSimpleName();
    }

    @Override
    public List<T> listar() {
        return datos.findAllByOrderByCodigoAsc();
    }

    @Override
    public List<T> listarActivos() {
        return datos.findByEstadoOrderByCodigoAsc(Catalogo.Estado.ACTIVO);
    }

    @Override
    public Optional<T> buscarPorCodigo(String codigo) {
        return datos.findById(codigo);
    }

    @Override
    @Transactional
    public T crear(T valor, String referencia) {
        validar(validador.validate(valor));
        exigir(referencia, "falta la referencia oficial");
        if (datos.findById(valor.getCodigo()).isPresent()) {
            throw new IllegalStateException("el código " + valor.getCodigo() + " ya existe");
        }
        valor.setEstado(Catalogo.Estado.ACTIVO);
        valor.setVersionId(null);
        T guardado = datos.save(valor);
        CatalogoCambio cambio = cambio(valor.getCodigo(), CatalogoCambio.Tipo.CREADO);
        cambio.setValorNuevo(valor.getNombre());
        cambio.setReferenciaOficial(referencia);
        registro.anotar(cambio);
        return guardado;
    }

    @Override
    @Transactional
    public T cambiarNombre(String codigo, String nombre, String referencia) {
        T valor = existente(codigo);
        // Se valida el nombre antes de tocar el valor: el valor ya está en la sesión de JPA, y un
        // nombre inválido puesto en él se guardaría al cerrar la transacción.
        validar(validador.validateValue(tipo, "nombre", nombre));
        exigir(referencia, "falta la referencia oficial");
        if (Objects.equals(valor.getNombre(), nombre)) {
            throw new IllegalArgumentException("el nombre nuevo es igual al actual");
        }
        CatalogoCambio cambio = cambio(codigo, CatalogoCambio.Tipo.NOMBRE);
        cambio.setValorAnterior(valor.getNombre());
        cambio.setValorNuevo(nombre);
        cambio.setReferenciaOficial(referencia);
        valor.setNombre(nombre);
        T guardado = datos.save(valor);
        registro.anotar(cambio);
        return guardado;
    }

    @Override
    @Transactional
    public void cambiarCodigo(String viejo, T nuevo, String referencia) {
        T anterior = existente(viejo);
        exigir(referencia, "falta la referencia oficial");
        if (anterior.getEstado() == Catalogo.Estado.INACTIVO) {
            throw new IllegalStateException("el código " + viejo + " ya está inactivo");
        }
        if (viejo.equals(nuevo.getCodigo())) {
            throw new IllegalArgumentException("el código nuevo es igual al viejo");
        }
        Optional<T> existente = datos.findById(nuevo.getCodigo());
        if (existente.isEmpty()) {
            crear(nuevo, referencia);
        } else if (existente.get().getEstado() == Catalogo.Estado.INACTIVO) {
            throw new IllegalStateException(
                    "el código nuevo " + nuevo.getCodigo() + " está inactivo; reactívelo primero");
        }
        desactivar(anterior, Motivo.REEMPLAZADO_POR, referencia, nuevo.getCodigo());
    }

    // Hasta que exista el registro del paciente (HU-004) ningún registro usa un valor de catálogo.
    @Override
    public long contarUsos(String codigo) {
        return 0;
    }

    @Override
    @Transactional
    public void inactivar(String codigo, Motivo motivo, String referencia) {
        T valor = existente(codigo);
        if (valor.getEstado() == Catalogo.Estado.INACTIVO) {
            throw new IllegalStateException("el código " + codigo + " ya está inactivo");
        }
        if (motivo == null) {
            throw new IllegalArgumentException("elija un motivo");
        }
        if (motivo.loPoneElSistema()) {
            throw new IllegalArgumentException(
                    "\"" + motivo.texto() + "\" lo pone el sistema al cargar una versión");
        }
        if (motivo == Motivo.REEMPLAZADO_POR) {
            throw new IllegalArgumentException(
                    "\"Reemplazado por\" se registra con el cambio de código");
        }
        if (motivo == Motivo.RETIRADO_POR_LA_FUENTE) {
            exigir(referencia, "\"Retirado por la fuente oficial\" pide la referencia oficial");
        }
        if (motivo == Motivo.CREADO_POR_ERROR && enVersionVigente(valor)) {
            throw new IllegalArgumentException("\"Creado por error\" solo vale para un código"
                    + " que no está en la versión vigente; para este, escriba la referencia"
                    + " oficial");
        }
        desactivar(valor, motivo, referencia, null);
    }

    @Override
    @Transactional
    public void reactivar(String codigo, String referencia, String justificacion) {
        T valor = existente(codigo);
        if (valor.getEstado() == Catalogo.Estado.ACTIVO) {
            throw new IllegalStateException("el código " + codigo + " ya está activo");
        }
        boolean conReferencia = !vacio(referencia);
        if (!conReferencia && vacio(justificacion)) {
            boolean seInactivoConReferencia = registro
                    .ultimo(catalogo(), codigo, CatalogoCambio.Tipo.INACTIVADO)
                    .map(c -> !vacio(c.getReferenciaOficial())).orElse(false);
            throw new IllegalArgumentException(seInactivoConReferencia
                    ? "se inactivó con una referencia oficial; para reactivarlo, escriba otra"
                            + " referencia o por qué esa no lo retiraba"
                    : "reactivar pide la referencia oficial o por qué se reactiva");
        }
        if (!conReferencia && !enVersionVigente(valor)
                && !registro.creadoPorCambioPosterior(catalogo(), codigo)) {
            throw new IllegalArgumentException("no se puede reactivar, porque no está en la"
                    + " versión vigente ni lo creó un cambio oficial posterior a ella; escriba la"
                    + " referencia oficial que lo trae de vuelta");
        }
        valor.setEstado(Catalogo.Estado.ACTIVO);
        datos.save(valor);
        CatalogoCambio cambio = cambio(codigo, CatalogoCambio.Tipo.REACTIVADO);
        cambio.setReferenciaOficial(conReferencia ? referencia : null);
        cambio.setJustificacion(vacio(justificacion) ? null : justificacion);
        registro.anotar(cambio);
    }

    @Override
    public List<CatalogoCambio> historial(String codigo) {
        return registro.historial(catalogo(), codigo);
    }

    /** Un código está en la versión vigente si vino en su archivo (RC-10). */
    protected boolean enVersionVigente(T valor) {
        Optional<CatalogoVersion> vigente = registro.versionVigente(catalogo());
        return vigente.isPresent() && vigente.get().getId().equals(valor.getVersionId());
    }

    private void desactivar(T valor, Motivo motivo, String referencia, String codigoReemplazo) {
        valor.setEstado(Catalogo.Estado.INACTIVO);
        datos.save(valor);
        CatalogoCambio cambio = cambio(valor.getCodigo(), CatalogoCambio.Tipo.INACTIVADO);
        cambio.setMotivo(motivo);
        cambio.setReferenciaOficial(vacio(referencia) ? null : referencia);
        cambio.setCodigoReemplazo(codigoReemplazo);
        registro.anotar(cambio);
    }

    private CatalogoCambio cambio(String codigo, CatalogoCambio.Tipo tipoDeCambio) {
        CatalogoCambio cambio = new CatalogoCambio();
        cambio.setCatalogo(catalogo());
        cambio.setCodigo(codigo);
        cambio.setTipo(tipoDeCambio);
        return cambio;
    }

    private T existente(String codigo) {
        return datos.findById(codigo)
                .orElseThrow(() -> new IllegalArgumentException("no existe el código " + codigo));
    }

    // Valida aquí y no solo al persistir: al persistir, el error llega envuelto en una excepción de
    // la transacción y el menú no puede mostrar el motivo.
    private static <V> void validar(Set<ConstraintViolation<V>> errores) {
        if (!errores.isEmpty()) {
            throw new ConstraintViolationException(errores);
        }
    }

    private static void exigir(String dato, String mensaje) {
        if (vacio(dato)) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private static boolean vacio(String dato) {
        return dato == null || dato.isBlank();
    }
}
