package com.ipssaludyvida.hu022.servicios;

import com.ipssaludyvida.comun.modelos.Catalogo;
import com.ipssaludyvida.comun.modelos.Motivo;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.comun.servicios.RegistroCatalogos;
import com.ipssaludyvida.comun.servicios.ResultadoCarga;
import com.ipssaludyvida.comun.servicios.ServicioCatalogo;
import com.ipssaludyvida.hu022.interfaces.ContratoOcupaciones;
import com.ipssaludyvida.hu022.modelos.Ocupacion;
import com.ipssaludyvida.hu022.repositorios.OcupacionRepositorio;
import jakarta.validation.Validator;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * HU-022 sobre la base común. Lo suyo es la jerarquía de la CIUO-88 A.C.: el grupo de cada código
 * se lee del código (D-26), una ocupación no entra ni se reactiva sin su grupo activo, y un grupo
 * con ocupaciones activas no se inactiva.
 */
@Service
public class OcupacionServicio extends ServicioCatalogo<Ocupacion> implements ContratoOcupaciones {

    private final OcupacionRepositorio datos;

    public OcupacionServicio(OcupacionRepositorio datos, Validator validador,
            RegistroCatalogos registro) {
        super(Ocupacion.class, datos, validador, registro);
        this.datos = datos;
    }

    @Override
    @Transactional
    public Ocupacion crear(Ocupacion valor, String referencia) {
        // Solo con un código que cumple el numeral: con uno que no, el mensaje es el del numeral.
        if (valor.getCodigo() != null && valor.getCodigo().matches("\\d{2,4}")) {
            exigirGrupoActivo(valor.getCodigo());
        }
        return super.crear(valor, referencia);
    }

    @Override
    @Transactional
    public void cambiarCodigo(String viejo, Ocupacion nuevo, String referencia) {
        // Sin esto, el grupo reemplazado queda inactivo con sus ocupaciones activas debajo.
        exigirSinOcupacionesActivas(viejo);
        super.cambiarCodigo(viejo, nuevo, referencia);
    }

    @Override
    @Transactional
    public void inactivar(String codigo, Motivo motivo, String referencia) {
        exigirSinOcupacionesActivas(codigo);
        super.inactivar(codigo, motivo, referencia);
    }

    @Override
    @Transactional
    public void reactivar(String codigo, String referencia, String justificacion) {
        String grupo = Ocupacion.grupoDe(codigo);
        if (grupo != null && datos.findById(codigo).isPresent() && !activo(grupo)) {
            throw new IllegalStateException("su grupo " + grupo
                    + " está inactivo: primero se reactiva el grupo");
        }
        super.reactivar(codigo, referencia, justificacion);
    }

    /**
     * Rechaza la fila cuyo grupo no está activo en el catálogo ni se acepta en el archivo, sin
     * importar el orden de las filas (escenarios 5 y 6). Rechazar un grupo puede dejar sin grupo a
     * otra fila, así que se repite hasta que nada cambia. En la primera carga, además, cuenta las
     * filas por nivel (escenario 7).
     */
    @Override
    protected void revisarFilas(ResultadoCarga carga) {
        boolean cambio = true;
        while (cambio) {
            cambio = false;
            Set<String> aceptados = carga.getAceptadas().stream()
                    .map(ResultadoCarga.Fila::codigo).collect(Collectors.toSet());
            for (ResultadoCarga.Fila fila : List.copyOf(carga.getAceptadas())) {
                String grupo = Ocupacion.grupoDe(fila.codigo());
                if (grupo != null && !aceptados.contains(grupo) && !activo(grupo)) {
                    carga.getAceptadas().remove(fila);
                    carga.getRechazadas().add(new ResultadoCarga.Rechazo(fila.numero(),
                            fila.codigo(), "su grupo " + grupo + " no está activo"));
                    cambio = true;
                }
            }
        }
        carga.getRechazadas().sort(Comparator.comparingInt(ResultadoCarga.Rechazo::numero));
        if (carga.getTipo() == ResultadoCarga.Tipo.PRIMERA) {
            carga.getBloqueos().addAll(contarPorNivel(carga));
        }
    }

    /**
     * Saca de los que ya no están al grupo que se queda con ocupaciones activas, de los más
     * profundos a los grandes grupos, porque un grupo que se queda deja activo al suyo (23).
     */
    @Override
    protected void revisarComparacion(ResultadoCarga carga) {
        if (carga.getYaNoEstan().isEmpty()) {
            return;
        }
        Set<String> quedan = new HashSet<>();
        datos.findByEstadoOrderByCodigoAsc(Catalogo.Estado.ACTIVO)
                .forEach(v -> quedan.add(v.getCodigo()));
        quedan.removeAll(carga.getYaNoEstan());
        carga.getAceptadas().forEach(f -> quedan.add(f.codigo()));
        List<String> grupos = carga.getYaNoEstan().stream()
                .filter(c -> c.length() < Ocupacion.LARGO_HIJA)
                .sorted(Comparator.comparingInt(String::length).reversed()).toList();
        for (String grupo : grupos) {
            long activas = quedan.stream().filter(c -> grupo.equals(Ocupacion.grupoDe(c))).count();
            if (activas > 0) {
                carga.getYaNoEstan().remove(grupo);
                quedan.add(grupo);
                carga.getAvisos().add("el grupo " + grupo + " tiene " + activas
                        + " ocupaciones activas: no se inactiva");
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nodo> jerarquia() {
        List<Ocupacion> todas = datos.findAllByOrderByCodigoAsc();
        Set<String> conHijaActiva = new HashSet<>();
        for (Ocupacion o : todas) {
            if (o.getCodigo().length() == Ocupacion.LARGO_HIJA
                    && o.getEstado() == Catalogo.Estado.ACTIVO) {
                for (String g = Ocupacion.grupoDe(o.getCodigo()); g != null;
                        g = Ocupacion.grupoDe(g)) {
                    conHijaActiva.add(g);
                }
            }
        }
        // El orden por código ya es el de la jerarquía: 2, 22, 222, 2221, 2222, 223.
        return todas.stream().map(o -> {
            boolean hija = o.getCodigo().length() == Ocupacion.LARGO_HIJA;
            return new Nodo(o, o.getCodigo().length(),
                    !hija && !conHijaActiva.contains(o.getCodigo()), hija);
        }).toList();
    }

    @Override
    public Comprobacion comprobarContraElArchivo(Path archivo) {
        return comprobarContra(leerListaOficial(archivo));
    }

    // Cada fila leída cuenta en el nivel que da el largo de su código, aunque se rechace, así que
    // un archivo que perdió los ceros del gran grupo 0 no cuadra (escenario 7). Una fila sin
    // código o de más de 4 dígitos no tiene nivel.
    private List<String> contarPorNivel(ResultadoCarga carga) {
        Map<Integer, Integer> leidas = new TreeMap<>();
        NIVELES.keySet().forEach(n -> leidas.put(n, 0));
        List<String> codigos = new ArrayList<>();
        carga.getAceptadas().forEach(f -> codigos.add(f.codigo()));
        carga.getRechazadas().forEach(r -> codigos.add(r.codigo()));
        codigos.stream().filter(c -> leidas.containsKey(c.length()))
                .forEach(c -> leidas.merge(c.length(), 1, Integer::sum));
        List<String> bloqueos = new ArrayList<>();
        leidas.forEach((nivel, cuantas) -> {
            if (!cuantas.equals(NIVELES.get(nivel))) {
                bloqueos.add("el nivel " + nivel + " no cuadra: el archivo trae " + cuantas
                        + " códigos de " + nivel + " dígitos, y la CIUO-88 A.C. tiene "
                        + NIVELES.get(nivel) + " " + NOMBRES_DE_NIVEL.get(nivel));
            }
        });
        return bloqueos;
    }

    private void exigirGrupoActivo(String codigo) {
        String grupo = Ocupacion.grupoDe(codigo);
        if (grupo != null && !activo(grupo)) {
            throw new IllegalStateException("su grupo " + grupo + " no está activo");
        }
    }

    private void exigirSinOcupacionesActivas(String codigo) {
        if (codigo == null || codigo.length() >= Ocupacion.LARGO_HIJA || !activo(codigo)) {
            return;
        }
        long activas = datos.findByEstadoOrderByCodigoAsc(Catalogo.Estado.ACTIVO).stream()
                .filter(v -> codigo.equals(Ocupacion.grupoDe(v.getCodigo()))).count();
        if (activas > 0) {
            throw new IllegalStateException("el grupo " + codigo + " tiene " + activas
                    + " ocupaciones activas: no se inactiva");
        }
    }

    private boolean activo(String codigo) {
        return datos.findById(codigo).map(v -> v.getEstado() == Catalogo.Estado.ACTIVO)
                .orElse(false);
    }
}
