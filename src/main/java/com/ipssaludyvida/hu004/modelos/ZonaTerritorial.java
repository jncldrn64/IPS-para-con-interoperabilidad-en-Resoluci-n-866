package com.ipssaludyvida.hu004.modelos;

/**
 * Zona territorial de residencia, numeral 14: Alfanumérico, formato 99, longitud 2. Resolución 866
 * de 2021, anexo técnico; se copia citando la norma (CLAUDE.md, sección 10). Los valores se leyeron
 * el 2026-11-02 en el texto del anexo que publica Supersalud en su normograma.
 */
public enum ZonaTerritorial implements ListaFija {

    URBANA("01", "Urbana"),
    RURAL("02", "Rural");

    private final String codigo;
    private final String texto;

    ZonaTerritorial(String codigo, String texto) {
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
