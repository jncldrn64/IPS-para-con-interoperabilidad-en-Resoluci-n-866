package com.example.demo.modelo;

/**
 * Los motivos de RC-10, más "Vuelve en la versión" de RC-9. Son una lista fija de las reglas
 * comunes y no llegan de un archivo oficial, así que van como enum (docs/DESIGN.md, sección 7).
 */
public enum Motivo {
    RETIRADO_POR_LA_FUENTE("Retirado por la fuente oficial"),
    REEMPLAZADO_POR("Reemplazado por"),
    NO_ESTA_EN_LA_VERSION("No está en la versión"),
    CREADO_POR_ERROR("Creado por error"),
    VUELVE_EN_LA_VERSION("Vuelve en la versión");

    private final String texto;

    Motivo(String texto) {
        this.texto = texto;
    }

    public String texto() {
        return texto;
    }

    /** Los que pone el sistema al cargar una versión (RC-9); una persona no los elige. */
    public boolean loPoneElSistema() {
        return this == NO_ESTA_EN_LA_VERSION || this == VUELVE_EN_LA_VERSION;
    }
}
