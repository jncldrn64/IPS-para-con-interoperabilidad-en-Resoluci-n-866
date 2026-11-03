package com.ipssaludyvida.hu004.modelos;

/**
 * Identidad de género, numeral 6: Alfanumérico, formato 99, longitud 2. Resolución 866 de 2021,
 * anexo técnico, hoja 14; se copia citando la norma (CLAUDE.md, sección 10). Los valores se
 * comprobaron el 2026-11-02 contra la hoja 14 del PDF de la resolución.
 */
public enum IdentidadGenero implements ListaFija {

    MASCULINO("01", "Masculino"),
    FEMENINO("02", "Femenino"),
    TRANSGENERO("03", "Transgénero"),
    NEUTRO("04", "Neutro"),
    NO_LO_DECLARA("05", "No lo declara");

    private final String codigo;
    private final String texto;

    IdentidadGenero(String codigo, String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    @Override
    public String codigo() {
        return codigo;
    }

    @Override
    public String texto() {
        return texto;
    }
}
