package com.ipssaludyvida.hu026.servicios;

import com.ipssaludyvida.comun.modelos.CatalogoVersion;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.comun.servicios.RegistroCatalogos;
import com.ipssaludyvida.comun.servicios.ResultadoCarga;
import com.ipssaludyvida.comun.servicios.ServicioCatalogo;
import com.ipssaludyvida.hu026.interfaces.ContratoMunicipios;
import com.ipssaludyvida.hu026.modelos.Departamento;
import com.ipssaludyvida.hu026.modelos.Municipio;
import com.ipssaludyvida.hu026.repositorios.DepartamentoRepositorio;
import com.ipssaludyvida.hu026.repositorios.MunicipioRepositorio;
import jakarta.validation.Validator;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * HU-026 sobre la base común. Lo suyo es el departamento: llega en dos columnas del archivo, tiene
 * que corresponder al código del municipio y tener un solo nombre, y se guarda en su tabla al
 * confirmar la carga.
 */
@Service
public class MunicipioServicio extends ServicioCatalogo<Municipio> implements ContratoMunicipios {

    private final MunicipioRepositorio datos;
    private final DepartamentoRepositorio departamentos;

    public MunicipioServicio(MunicipioRepositorio datos, DepartamentoRepositorio departamentos,
            Validator validador, RegistroCatalogos registro) {
        super(Municipio.class, datos, validador, registro);
        this.datos = datos;
        this.departamentos = departamentos;
    }

    @Override
    protected List<String> columnasPropias() {
        return List.of(CODIGO_DEPARTAMENTO, NOMBRE_DEPARTAMENTO);
    }

    @Override
    @Transactional
    public Municipio crear(Municipio valor, String referencia) {
        String codigo = valor.getCodigo();
        // RC-8 a mano: el sistema no completa ceros, y dice por qué rechaza el código.
        if (codigo != null && codigo.matches("\\d{1,4}")) {
            throw new IllegalArgumentException("el código de un municipio tiene 5 dígitos (numeral"
                    + " 12); revise que no haya perdido los ceros iniciales");
        }
        if (codigo != null && codigo.matches("\\d{5}")
                && departamentos.findById(Municipio.departamentoDe(codigo)).isEmpty()) {
            throw new IllegalStateException("los dos primeros dígitos, "
                    + Municipio.departamentoDe(codigo)
                    + ", no son de un departamento del catálogo");
        }
        return super.crear(valor, referencia);
    }

    @Override
    public Optional<Departamento> departamento(String codigoMunicipio) {
        String codigo = Municipio.departamentoDe(codigoMunicipio);
        return codigo == null ? Optional.empty() : departamentos.findById(codigo);
    }

    /**
     * Rechaza la fila cuyo código no empieza por su departamento, y la que da a un departamento un
     * nombre distinto del que tiene en las demás (escenario 6). El nombre que vale es el que más
     * filas le dan, y con empate el primero del archivo.
     */
    @Override
    protected void revisarFilas(ResultadoCarga carga) {
        for (ResultadoCarga.Fila fila : List.copyOf(carga.getAceptadas())) {
            String codigo = fila.propia(CODIGO_DEPARTAMENTO);
            if (codigo.isEmpty() || !fila.codigo().startsWith(codigo)) {
                rechazar(carga, fila, "el código no corresponde al departamento");
            } else if (fila.propia(NOMBRE_DEPARTAMENTO).isEmpty()) {
                rechazar(carga, fila, "falta el nombre del departamento");
            }
        }
        Map<String, String> nombres = nombresQueValen(carga.getAceptadas());
        for (ResultadoCarga.Fila fila : List.copyOf(carga.getAceptadas())) {
            if (!fila.propia(NOMBRE_DEPARTAMENTO)
                    .equals(nombres.get(fila.propia(CODIGO_DEPARTAMENTO)))) {
                rechazar(carga, fila, "el departamento tiene dos nombres");
            }
        }
        carga.getRechazadas().sort(Comparator.comparingInt(ResultadoCarga.Rechazo::numero));
    }

    /** Avisa del departamento que la carga trae con otro nombre (escenario 7). */
    @Override
    protected void revisarComparacion(ResultadoCarga carga) {
        nombresQueValen(carga.getAceptadas()).forEach((codigo, nombre) ->
                departamentos.findById(codigo).filter(d -> !d.getNombre().equals(nombre))
                        .ifPresent(d -> carga.getAvisos().add("el departamento " + codigo
                                + (carga.getTipo() == ResultadoCarga.Tipo.RECARGA
                                        ? " trae otro nombre, \"" + nombre
                                                + "\"; la recarga no cambia nombres"
                                        : " cambia de nombre, de \"" + d.getNombre() + "\" a \""
                                                + nombre + "\""))));
    }

    /**
     * Guarda los departamentos de las filas aceptadas con la versión. Una versión nueva cambia el
     * nombre del que ya existe; una recarga no.
     */
    @Override
    protected void alConfirmar(ResultadoCarga carga, CatalogoVersion version) {
        nombresQueValen(carga.getAceptadas()).forEach((codigo, nombre) -> {
            Departamento d = departamentos.findById(codigo)
                    .orElseGet(() -> new Departamento(codigo, nombre));
            if (carga.getTipo() != ResultadoCarga.Tipo.RECARGA) {
                d.setNombre(nombre);
            }
            d.setVersionId(version.getId());
            departamentos.save(d);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Comprobacion comprobarContraElArchivo(Path archivo) {
        List<Map<String, String>> filas = leerTabla(archivo);
        Map<String, String> oficial = new LinkedHashMap<>();
        filas.forEach(f -> oficial.put(f.get("codigo"), f.get("nombre")));
        Comprobacion comprobacion = comprobarContra(oficial);
        Map<String, Departamento> guardados = departamentos.findAllByOrderByCodigoAsc().stream()
                .collect(Collectors.toMap(Departamento::getCodigo, Function.identity()));
        TreeSet<String> distintos = new TreeSet<>(comprobacion.nombresDistintos());
        for (Map<String, String> f : filas) {
            Departamento d = guardados.get(Municipio.departamentoDe(f.get("codigo")));
            if (d != null && datos.findById(f.get("codigo")).isPresent()
                    && !d.getNombre().equals(f.get(NOMBRE_DEPARTAMENTO))) {
                distintos.add(f.get("codigo"));
            }
        }
        return new Comprobacion(comprobacion.faltan(), comprobacion.inactivos(),
                comprobacion.sobran(), new ArrayList<>(distintos));
    }

    // El nombre de cada departamento según las filas: el que más filas le dan, y con empate el que
    // aparece primero.
    private static Map<String, String> nombresQueValen(List<ResultadoCarga.Fila> filas) {
        Map<String, Map<String, Integer>> cuentas = new LinkedHashMap<>();
        for (ResultadoCarga.Fila f : filas) {
            cuentas.computeIfAbsent(f.propia(CODIGO_DEPARTAMENTO), k -> new LinkedHashMap<>())
                    .merge(f.propia(NOMBRE_DEPARTAMENTO), 1, Integer::sum);
        }
        Map<String, String> nombres = new LinkedHashMap<>();
        cuentas.forEach((codigo, porNombre) -> {
            String elegido = null;
            int mayor = 0;
            for (Map.Entry<String, Integer> e : porNombre.entrySet()) {
                if (e.getValue() > mayor) {
                    elegido = e.getKey();
                    mayor = e.getValue();
                }
            }
            nombres.put(codigo, elegido);
        });
        return nombres;
    }

    private static void rechazar(ResultadoCarga carga, ResultadoCarga.Fila fila, String motivo) {
        carga.getAceptadas().remove(fila);
        carga.getRechazadas().add(new ResultadoCarga.Rechazo(fila.numero(), fila.codigo(), motivo));
    }
}
