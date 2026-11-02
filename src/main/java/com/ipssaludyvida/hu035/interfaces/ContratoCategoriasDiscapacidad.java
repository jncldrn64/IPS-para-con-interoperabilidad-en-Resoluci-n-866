package com.ipssaludyvida.hu035.interfaces;

import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu035.modelos.CategoriaDiscapacidad;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * El contrato de HU-035, catálogo de categorías de discapacidad. Lo implementa
 * CategoriaDiscapacidadServicio y lo usa CategoriaDiscapacidadControlador. Las operaciones comunes
 * vienen de CrudCatalogo; aquí va lo propio de la historia.
 *
 * <p>Escenarios de docs/context/trello/tarjetas/HU-035.md y la operación que los cumple: 1 a 7,
 * leerCarga, confirmarCarga y escribirRechazadas; 8, cambiarCodigo; 9 y 10, crear y cambiarNombre;
 * 11, cambiarNombre e historial; 12, no confirmar, en el controlador; 13, consultar e
 * historialDeVersiones; 14 y 15, buscar; 16, buscar y sinCoincidencias; 17, consultar; 18,
 * comprobarContraLaHoja; 19 a 22, contarUsos, inactivar y reactivar; 23, historial.
 */
public interface ContratoCategoriasDiscapacidad extends CrudCatalogo<CategoriaDiscapacidad> {

    /**
     * Resolución 866 de 2021, anexo técnico, numeral 10, hojas 14 y 15: el 05 empieza en la hoja 14
     * y su "(mental)" sigue en la 15. Se copia citando la norma (CLAUDE.md, sección 10). Si no
     * coincide con la hoja del PDF, manda la hoja; el 05 dice "sicosocial", como la hoja.
     */
    Map<String, String> HOJAS_14_Y_15 = hojas14y15();

    /** Compara el catálogo con los 8 códigos y nombres de las hojas 14 y 15, sin cambiar nada. */
    Comprobacion comprobarContraLaHoja();

    @Override
    default String nombreEnPlural() {
        return "categorías de discapacidad";
    }

    private static Map<String, String> hojas14y15() {
        Map<String, String> hoja = new LinkedHashMap<>();
        hoja.put("01", "Discapacidad física");
        hoja.put("02", "Discapacidad visual");
        hoja.put("03", "Discapacidad auditiva");
        hoja.put("04", "Discapacidad intelectual");
        hoja.put("05", "Discapacidad sicosocial (mental)");
        hoja.put("06", "Sordoceguera");
        hoja.put("07", "Discapacidad múltiple");
        hoja.put("08", "Sin discapacidad");
        return Collections.unmodifiableMap(hoja);
    }
}
