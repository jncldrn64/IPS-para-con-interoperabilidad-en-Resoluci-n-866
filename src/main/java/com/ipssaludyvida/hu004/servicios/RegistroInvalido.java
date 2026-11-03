package com.ipssaludyvida.hu004.servicios;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Lo que impide guardar un paciente: cada dato que falta o que está mal, con su motivo, en el orden
 * de la tarjeta. No se guarda nada (escenarios 8 a 10).
 */
public class RegistroInvalido extends IllegalArgumentException {

    private final transient Map<Campo, String> errores;

    public RegistroInvalido(Map<Campo, String> errores) {
        super("no se guardó el paciente: " + errores.size() + " datos por corregir");
        this.errores = new LinkedHashMap<>(errores);
    }

    /** Cada dato con su motivo, en el orden de la tarjeta. */
    public Map<Campo, String> getErrores() {
        return errores;
    }
}
