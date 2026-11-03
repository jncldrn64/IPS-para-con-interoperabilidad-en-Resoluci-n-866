package com.ipssaludyvida.hu004.modelos;

/**
 * Sexo biológico, numeral 5: Alfanumérico, formato 99, longitud 2. Resolución 866 de 2021, anexo
 * técnico, hoja 13; se copia citando la norma (CLAUDE.md, sección 10). Los valores se comprobaron
 * el 2026-11-02 contra la hoja 13 del PDF de la resolución.
 */
public enum SexoBiologico implements ListaFija {

    HOMBRE("01", "Hombre"),
    MUJER("02", "Mujer"),
    INDETERMINADO_INTERSEXUAL("03", "Indeterminado/Intersexual");

    private final String codigo;
    private final String texto;

    SexoBiologico(String codigo, String texto) {
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
