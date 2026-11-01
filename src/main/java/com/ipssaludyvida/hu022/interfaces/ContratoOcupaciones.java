package com.ipssaludyvida.hu022.interfaces;

import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu022.modelos.Ocupacion;
import com.ipssaludyvida.hu022.servicios.Nodo;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * El contrato de HU-022, catálogo de ocupaciones. Lo implementa OcupacionServicio y lo usa
 * OcupacionControlador. Las operaciones comunes vienen de CrudCatalogo; aquí va lo propio de la
 * historia.
 *
 * <p>Escenarios de docs/context/trello/tarjetas/HU-022.md y la operación que los cumple: 1 a 7,
 * leerCarga, confirmarCarga y escribirRechazadas, con el grupo de cada fila y la cuenta por
 * niveles; 8 y 9, leerCarga y confirmarCarga en una versión nueva; 10, cambiarCodigo; 11 y 12,
 * crear y cambiarNombre, con el grupo activo; 13, cambiarNombre e historial; 14, no confirmar, en
 * el controlador; 15, consultar e historialDeVersiones; 16, jerarquia; 17 y 18, buscar; 19, buscar
 * y sinCoincidencias; 20, consultar; 21, comprobarContraElArchivo; 22, contarUsos,
 * contarAtenciones e inactivar; 23, inactivar y leerCarga con un grupo que tiene ocupaciones
 * activas; 24, inactivar; 25 a 27, reactivar, con el grupo activo; 28, historial.
 */
public interface ContratoOcupaciones extends CrudCatalogo<Ocupacion> {

    /**
     * Cuántos códigos tiene cada nivel de la CIUO-88 A.C.: "integrada en 4 niveles de agregación,
     * identificados mediante códigos numéricos de 1, 2, 3 y 4 dígitos" (DANE, 2006). La primera
     * carga se compara con ellos (escenario 7).
     */
    Map<Integer, Integer> NIVELES = Map.of(1, 10, 2, 34, 3, 117, 4, 401);

    /** El nombre de cada nivel, para el mensaje del escenario 7. */
    Map<Integer, String> NOMBRES_DE_NIVEL = Map.of(1, "grandes grupos", 2,
            "subgrupos principales", 3, "subgrupos", 4, "grupos primarios");

    /** Cada valor con su nivel, en el orden de la jerarquía (escenario 16). */
    List<Nodo> jerarquia();

    /** Compara el catálogo con un archivo oficial descargado otra vez, sin cambiar nada (21). */
    Comprobacion comprobarContraElArchivo(Path archivo);

    /**
     * Cuántas atenciones guardan la ocupación (D-24). Devuelve 0 hasta que exista la atención
     * (HU-007).
     */
    default long contarAtenciones(String codigo) {
        return 0;
    }

    @Override
    default String nombreEnPlural() {
        return "ocupaciones";
    }
}
