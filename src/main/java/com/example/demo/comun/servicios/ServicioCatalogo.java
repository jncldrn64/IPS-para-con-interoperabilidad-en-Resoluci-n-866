package com.example.demo.comun.servicios;

import com.example.demo.comun.interfaces.CrudCatalogo;
import com.example.demo.comun.modelos.Catalogo;
import com.example.demo.comun.modelos.CatalogoCambio;
import com.example.demo.comun.modelos.CatalogoVersion;
import com.example.demo.comun.modelos.Motivo;
import com.example.demo.comun.repositorios.CatalogoRepositorio;
import jakarta.persistence.AttributeOverride;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
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

    @Override
    public Optional<CatalogoVersion> versionVigente() {
        return registro.versionVigente(catalogo());
    }

    @Override
    @Transactional(readOnly = true)
    public ResultadoCarga leerCarga(Path archivo, DatosVersion datosVersion) {
        ResultadoCarga carga = new ResultadoCarga(tipoDeCarga(datosVersion), datosVersion);
        List<String> lineas = ArchivoCsv.lineas(archivo).stream()
                .filter(l -> !l.isBlank()).toList();
        if (lineas.isEmpty()) {
            throw new IllegalArgumentException("el archivo está vacío");
        }
        List<String> columnas = ArchivoCsv.campos(lineas.get(0)).stream()
                .map(ArchivoCsv::normalizar).toList();
        int codigo = columna(columnas, "codigo", lineas.get(0));
        int nombre = columna(columnas, "nombre", lineas.get(0));
        List<ResultadoCarga.Fila> filas = new ArrayList<>();
        for (String linea : lineas.subList(1, lineas.size())) {
            carga.contarFila();
            List<String> campos = ArchivoCsv.campos(linea);
            filas.add(new ResultadoCarga.Fila(carga.getFilasLeidas(), campo(campos, codigo),
                    campo(campos, nombre)));
        }
        Map<String, Long> repeticiones = filas.stream().filter(f -> !f.codigo().isEmpty())
                .collect(Collectors.groupingBy(ResultadoCarga.Fila::codigo, Collectors.counting()));
        for (ResultadoCarga.Fila fila : filas) {
            if (!fila.codigo().isEmpty()) {
                carga.getCodigosDelArchivo().add(fila.codigo());
            }
            String motivo = repeticiones.getOrDefault(fila.codigo(), 0L) > 1
                    ? "código repetido" : motivoDeRechazo(fila);
            if (motivo != null) {
                carga.getRechazadas().add(
                        new ResultadoCarga.Rechazo(fila.numero(), fila.codigo(), motivo));
            } else {
                carga.getAceptadas().add(new ResultadoCarga.Fila(fila.numero(), fila.codigo(),
                        nombreOficial(fila.codigo(), fila.nombre(), carga.getAvisos())));
            }
        }
        if (carga.getFilasLeidas() == 0) {
            carga.getBloqueos().add("el archivo no trae filas debajo de los nombres de columna");
        }
        compararConElCatalogo(carga);
        return carga;
    }

    @Override
    @Transactional
    public CatalogoVersion confirmarCarga(ResultadoCarga carga, Map<String, String> reemplazos) {
        if (!carga.sePuedeGuardar()) {
            throw new IllegalStateException(
                    "la carga no se puede guardar: " + String.join("; ", carga.getBloqueos()));
        }
        reemplazos.forEach((viejo, nuevo) -> {
            if (!carga.getYaNoEstan().contains(viejo) || !carga.getNuevos().contains(nuevo)) {
                throw new IllegalArgumentException("el reemplazo de " + viejo + " por " + nuevo
                        + " no sirve: el viejo tiene que ya no estar y el nuevo, ser nuevo");
            }
        });
        CatalogoVersion version = carga.getTipo() == ResultadoCarga.Tipo.RECARGA
                ? registro.versionVigente(catalogo()).orElseThrow()
                : registro.guardar(nuevaVersion(carga));
        Map<String, T> existentes = datos.findAllByOrderByCodigoAsc().stream()
                .collect(Collectors.toMap(Catalogo::getCodigo, Function.identity()));
        for (ResultadoCarga.Fila fila : carga.getAceptadas()) {
            T valor = existentes.get(fila.codigo());
            if (valor == null) {
                valor = instancia(fila.codigo(), fila.nombre());
                valor.setVersionId(version.getId());
                datos.save(valor);
                CatalogoCambio cambio = cambio(fila.codigo(), CatalogoCambio.Tipo.CREADO);
                cambio.setValorNuevo(fila.nombre());
                anotar(cambio, version);
                continue;
            }
            if (carga.getTipo() == ResultadoCarga.Tipo.VERSION_NUEVA) {
                if (!valor.getNombre().equals(fila.nombre())) {
                    CatalogoCambio cambio = cambio(fila.codigo(), CatalogoCambio.Tipo.NOMBRE);
                    cambio.setValorAnterior(valor.getNombre());
                    cambio.setValorNuevo(fila.nombre());
                    anotar(cambio, version);
                    valor.setNombre(fila.nombre());
                }
                if (valor.getEstado() == Catalogo.Estado.INACTIVO) {
                    valor.setEstado(Catalogo.Estado.ACTIVO);
                    CatalogoCambio cambio = cambio(fila.codigo(), CatalogoCambio.Tipo.REACTIVADO);
                    cambio.setMotivo(Motivo.VUELVE_EN_LA_VERSION);
                    anotar(cambio, version);
                }
            }
            valor.setVersionId(version.getId());
            datos.save(valor);
        }
        // Una fila rechazada deja su código como estaba, pero el código vino en el archivo y queda
        // en la versión (RC-9).
        for (ResultadoCarga.Rechazo rechazo : carga.getRechazadas()) {
            T valor = existentes.get(rechazo.codigo());
            if (valor != null) {
                valor.setVersionId(version.getId());
                datos.save(valor);
            }
        }
        for (String codigo : carga.getYaNoEstan()) {
            T valor = existentes.get(codigo);
            valor.setEstado(Catalogo.Estado.INACTIVO);
            datos.save(valor);
            CatalogoCambio cambio = cambio(codigo, CatalogoCambio.Tipo.INACTIVADO);
            String reemplazo = reemplazos.get(codigo);
            cambio.setMotivo(reemplazo != null
                    ? Motivo.REEMPLAZADO_POR : Motivo.NO_ESTA_EN_LA_VERSION);
            cambio.setCodigoReemplazo(reemplazo);
            anotar(cambio, version);
        }
        return version;
    }

    @Override
    public Path escribirRechazadas(ResultadoCarga carga, Path destino) {
        List<String> lineas = new ArrayList<>(List.of("fila;código;motivo"));
        carga.getRechazadas().forEach(r -> lineas.add(r.numero() + ";" + r.codigo() + ";"
                + r.motivo().replace(';', ',')));
        try {
            return Files.write(destino, lineas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "no se pudo escribir " + destino + ": " + e.getMessage());
        }
    }

    /** El nombre que se guarda de una fila aceptada. Una historia con una lista fija lo cambia. */
    protected String nombreOficial(String codigo, String nombreDelArchivo, List<String> avisos) {
        return nombreDelArchivo;
    }

    /** Lo que impide guardar la primera carga. Una historia con una lista fija lo agrega. */
    protected List<String> revisarPrimeraCarga(Set<String> codigosAceptados) {
        return List.of();
    }

    private ResultadoCarga.Tipo tipoDeCarga(DatosVersion d) {
        exigir(d.fuente(), "falta la fuente");
        exigir(d.version(), "falta la versión");
        if (d.fechaDescarga() == null) {
            throw new IllegalArgumentException("falta la fecha de descarga");
        }
        Optional<CatalogoVersion> vigente = registro.versionVigente(catalogo());
        String cualEsLaVigente = vigente.map(v -> "; la versión vigente es " + v.getVersion()
                + ", publicada el " + v.getFechaPublicacion()).orElse("");
        LocalDate hoy = LocalDate.now();
        if (d.fechaDescarga().isAfter(hoy)) {
            throw new IllegalArgumentException(
                    "la fecha de descarga " + d.fechaDescarga() + " es futura" + cualEsLaVigente);
        }
        if (d.publicacion().isAfter(hoy)) {
            throw new IllegalArgumentException(
                    "la fecha de publicación " + d.publicacion() + " es futura" + cualEsLaVigente);
        }
        if (datos.findAllByOrderByCodigoAsc().isEmpty()) {
            return ResultadoCarga.Tipo.PRIMERA;
        }
        if (vigente.isEmpty() || d.publicacion().isAfter(vigente.get().getFechaPublicacion())) {
            return ResultadoCarga.Tipo.VERSION_NUEVA;
        }
        if (d.publicacion().isEqual(vigente.get().getFechaPublicacion())) {
            return ResultadoCarga.Tipo.RECARGA;
        }
        throw new IllegalArgumentException("la fecha de publicación " + d.publicacion()
                + " es anterior a la de la versión vigente" + cualEsLaVigente);
    }

    private static int columna(List<String> columnas, String nombre, String encabezado) {
        int indice = columnas.indexOf(nombre);
        if (indice < 0) {
            boolean conComas = encabezado.contains(",") && !encabezado.contains(";");
            throw new IllegalArgumentException("al archivo le falta la columna " + nombre
                    + (conComas ? "; parece separado por comas, y la carga pide punto y coma"
                            : ""));
        }
        return indice;
    }

    private static String campo(List<String> campos, int indice) {
        return indice < campos.size() ? campos.get(indice) : "";
    }

    private String motivoDeRechazo(ResultadoCarga.Fila fila) {
        List<String> motivos = new ArrayList<>();
        validador.validateValue(tipo, "codigo", fila.codigo())
                .forEach(v -> motivos.add(v.getMessage()));
        validador.validateValue(tipo, "nombre", fila.nombre())
                .forEach(v -> motivos.add(v.getMessage()));
        if (motivos.isEmpty()) {
            return null;
        }
        if (fila.codigo().matches("\\d+") && fila.codigo().length() < largoDelCodigo()) {
            motivos.add("revise que el archivo no haya perdido los ceros iniciales");
        }
        return String.join("; ", motivos);
    }

    private void compararConElCatalogo(ResultadoCarga carga) {
        Set<String> aceptados = carga.getAceptadas().stream().map(ResultadoCarga.Fila::codigo)
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));
        if (carga.getTipo() == ResultadoCarga.Tipo.PRIMERA) {
            carga.getNuevos().addAll(aceptados);
            if (aceptados.isEmpty() && carga.getFilasLeidas() > 0) {
                carga.getBloqueos().add("ninguna fila se aceptó: no hay nada que guardar");
            }
            carga.getBloqueos().addAll(revisarPrimeraCarga(aceptados));
            return;
        }
        Map<String, T> existentes = new HashMap<>();
        datos.findAllByOrderByCodigoAsc().forEach(v -> existentes.put(v.getCodigo(), v));
        for (ResultadoCarga.Fila fila : carga.getAceptadas()) {
            T valor = existentes.get(fila.codigo());
            if (valor == null) {
                carga.getNuevos().add(fila.codigo());
            } else if (carga.getTipo() == ResultadoCarga.Tipo.RECARGA) {
                if (!valor.getNombre().equals(fila.nombre())) {
                    carga.getAvisos().add("el código " + fila.codigo() + " trae otro nombre, \""
                            + fila.nombre() + "\"; la recarga no cambia nombres");
                }
            } else {
                if (!valor.getNombre().equals(fila.nombre())) {
                    carga.getCambianDeNombre().add(new ResultadoCarga.CambioDeNombre(
                            fila.codigo(), valor.getNombre(), fila.nombre()));
                }
                if (valor.getEstado() == Catalogo.Estado.INACTIVO) {
                    carga.getVuelven().add(fila.codigo());
                }
            }
        }
        if (carga.getTipo() == ResultadoCarga.Tipo.RECARGA) {
            return;
        }
        existentes.values().stream()
                .filter(v -> v.getEstado() == Catalogo.Estado.ACTIVO)
                .filter(v -> !carga.getCodigosDelArchivo().contains(v.getCodigo()))
                .forEach(v -> carga.getYaNoEstan().add(v.getCodigo()));
        carga.getYaNoEstan().sort(null);
        avisarCambiosOficialesQueSeDeshacen(carga);
    }

    // RC-9: la versión avisa si deshace un cambio oficial registrado después de su publicación.
    private void avisarCambiosOficialesQueSeDeshacen(ResultadoCarga carga) {
        LocalDate publicacion = carga.getDatos().publicacion();
        List<String> afectados = new ArrayList<>(carga.getYaNoEstan());
        carga.getCambianDeNombre().forEach(c -> afectados.add(c.codigo()));
        afectados.addAll(carga.getVuelven());
        for (String codigo : afectados) {
            registro.historial(catalogo(), codigo).stream()
                    .filter(c -> c.getReferenciaOficial() != null)
                    .filter(c -> c.getFechaHora().toLocalDate().isAfter(publicacion))
                    .reduce((primero, segundo) -> segundo)
                    .ifPresent(c -> carga.getAvisos().add("la versión deshace el cambio oficial"
                            + " del " + c.getFechaHora().toLocalDate() + " sobre el código "
                            + codigo + " (" + c.getReferenciaOficial() + "), registrado después"
                            + " de su publicación"));
        }
    }

    private CatalogoVersion nuevaVersion(ResultadoCarga carga) {
        DatosVersion d = carga.getDatos();
        CatalogoVersion version = new CatalogoVersion();
        version.setCatalogo(catalogo());
        version.setVersion(d.version());
        version.setFuente(d.fuente());
        version.setDireccionDescarga(d.direccionDescarga());
        version.setFechaDescarga(d.fechaDescarga());
        version.setFechaPublicacion(d.publicacion());
        version.setFilasLeidas(carga.getFilasLeidas());
        version.setFilasAceptadas(carga.getAceptadas().size());
        version.setFilasRechazadas(carga.getRechazadas().size());
        version.setCargadoEn(LocalDateTime.now());
        return version;
    }

    private void anotar(CatalogoCambio cambio, CatalogoVersion version) {
        cambio.setVersionId(version.getId());
        registro.anotar(cambio);
    }

    private T instancia(String codigo, String nombre) {
        try {
            return tipo.getConstructor(String.class, String.class).newInstance(codigo, nombre);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(tipo.getSimpleName() + " necesita un constructor"
                    + " (código, nombre)", e);
        }
    }

    /** El largo del código, leído del @AttributeOverride de la entidad. */
    private int largoDelCodigo() {
        AttributeOverride largo = tipo.getAnnotation(AttributeOverride.class);
        return largo != null ? largo.column().length() : Integer.MAX_VALUE;
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
