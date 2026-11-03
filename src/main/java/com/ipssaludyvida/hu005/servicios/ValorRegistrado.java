package com.ipssaludyvida.hu005.servicios;

/**
 * Un valor de catálogo tal como lo muestra la consulta: su código, el nombre que tenía el día en
 * que se registró el paciente, el nombre vigente si cambió después, o null si no (RC-11, escenario
 * 13), y si hoy está inactivo en su catálogo (RC-4, escenario 9).
 */
public record ValorRegistrado(String codigo, String nombre, String nombreVigente,
        boolean inactivo) {
}
