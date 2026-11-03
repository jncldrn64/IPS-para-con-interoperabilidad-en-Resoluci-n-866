package com.ipssaludyvida.hu005.interfaces;

import com.ipssaludyvida.hu005.servicios.Ficha;
import java.util.Optional;

/**
 * El contrato de HU-005, consulta de un paciente. Lo implementa ConsultaPacienteServicio y lo usa
 * ConsultaPacienteControlador. Solo lee: no cambia el paciente ni los catálogos (escenario 12).
 *
 * <p>Escenarios de docs/context/trello/tarjetas/HU-005.md y la operación que los cumple: 1, 4 a 9 y
 * 13, consultar; 2, consultar vacío; 3, consultar, que lanza IllegalArgumentException; 10 y 11, el
 * permiso de consultar pacientes, en el controlador; 12, consultar, que no guarda nada.
 */
public interface ContratoConsultaPacientes {

    /**
     * La ficha del paciente con ese tipo y número de documento (D-07), o vacío si no hay. Lanza
     * IllegalArgumentException si falta el tipo o el número (escenario 3).
     */
    Optional<Ficha> consultar(String tipoDocumento, String numeroDocumento);
}
