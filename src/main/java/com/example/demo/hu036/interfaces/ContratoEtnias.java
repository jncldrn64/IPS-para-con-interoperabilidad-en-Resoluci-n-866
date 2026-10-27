package com.example.demo.hu036.interfaces;

import com.example.demo.comun.interfaces.CrudCatalogo;
import com.example.demo.hu036.modelos.Etnia;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * El contrato de HU-036, catálogo de etnias. Lo implementa EtniaServicio y lo usa el menú. Las
 * operaciones comunes vienen de CrudCatalogo; aquí va lo propio de la historia.
 *
 * <p>Escenarios de docs/context/trello/tarjetas/HU-036.md y la operación que los cumple: 1 a 7,
 * leerCarga, confirmarCarga y escribirRechazadas; 8, cambiarCodigo; 9 y 10, crear y cambiarNombre;
 * 11, cambiarNombre e historial; 12, no confirmar; 13 a 16, listar, listarActivos y
 * buscarPorCodigo; 19 a 22, contarUsos, inactivar y reactivar; 23, historial. Los escenarios 13 a
 * 18 están incompletos (docs/ROADMAP.md, fase 0).
 */
public interface ContratoEtnias extends CrudCatalogo<Etnia> {

    /**
     * Resolución 866 de 2021, anexo técnico, numeral 13, componente etnia, hoja 15. Se copia
     * citando la norma (CLAUDE.md, sección 10). Si no coincide con la hoja del PDF, manda la hoja.
     */
    Map<String, String> HOJA_15 = hoja15();

    private static Map<String, String> hoja15() {
        Map<String, String> hoja = new LinkedHashMap<>();
        hoja.put("01", "Indígena");
        hoja.put("02", "ROM (Gitanos)");
        hoja.put("03", "Raizal (San Andrés y Providencia)");
        hoja.put("04", "Palenquero de San Basilio de Palenque");
        hoja.put("05", "Negro(a)");
        hoja.put("06", "Afrocolombiano(a)");
        hoja.put("99", "Ninguna de las anteriores");
        return Collections.unmodifiableMap(hoja);
    }
}
