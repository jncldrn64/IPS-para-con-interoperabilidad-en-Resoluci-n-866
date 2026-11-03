package com.ipssaludyvida.hu034.interfaces;

import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu034.modelos.TipoDocumento;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * El contrato de HU-034, catálogo de tipos de documento. Lo implementa TipoDocumentoServicio y lo
 * usa TipoDocumentoControlador. Las operaciones comunes vienen de CrudCatalogo; aquí va lo propio
 * de la historia.
 *
 * <p>Escenarios de docs/context/trello/tarjetas/HU-034.md y la operación que los cumple: 1 a 7,
 * leerCarga, confirmarCarga y escribirRechazadas; 8, cambiarCodigo; 9 y 10, crear y cambiarNombre;
 * 11, cambiarNombre e historial; 12, no confirmar, en el controlador; 13, consultar e
 * historialDeVersiones; 14 y 15, buscar; 16, buscar y sinCoincidencias; 17, consultar; 18,
 * comprobarContraLaHoja; 19 a 22, contarUsos, inactivar y reactivar; 23, historial.
 */
public interface ContratoTiposDocumento extends CrudCatalogo<TipoDocumento> {

    /**
     * Resolución 866 de 2021, anexo técnico, numeral 2, componente tipo de documento, hoja 13. Se
     * copia citando la norma (CLAUDE.md, sección 10). Si no coincide con la hoja del PDF, manda la
     * hoja.
     */
    Map<String, String> HOJA_13 = hoja13();

    /** Compara el catálogo con los 14 códigos y nombres de la hoja 13, sin cambiar nada. */
    Comprobacion comprobarContraLaHoja();

    @Override
    default String nombreEnPlural() {
        return "tipos de documento";
    }

    private static Map<String, String> hoja13() {
        Map<String, String> hoja = new LinkedHashMap<>();
        hoja.put("CC", "Cédula ciudadanía");
        hoja.put("CE", "Cédula de extranjería");
        hoja.put("CD", "Carné diplomático");
        hoja.put("PA", "Pasaporte");
        hoja.put("SC", "Salvoconducto de permanencia");
        hoja.put("PT", "Permiso Temporal de Permanencia");
        hoja.put("PE", "Permiso Especial de Permanencia");
        hoja.put("RC", "Registro civil");
        hoja.put("TI", "Tarjeta de identidad");
        hoja.put("CN", "Certificado de nacido vivo");
        hoja.put("AS", "Adulto sin identificar");
        hoja.put("MS", "Menor sin identificar");
        hoja.put("DE", "Documento extranjero");
        hoja.put("SI", "Sin identificación");
        return Collections.unmodifiableMap(hoja);
    }
}
