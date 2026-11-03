package com.ipssaludyvida.hu006.interfaces;

import com.ipssaludyvida.comun.modelos.HistorialCambio;
import com.ipssaludyvida.hu004.servicios.DatosPaciente;
import java.util.List;

/**
 * El contrato de HU-006, actualización y corrección de los datos de un paciente. Lo implementa
 * CambiosPacienteServicio y lo usa CambiosPacienteControlador.
 *
 * <p>Escenarios de docs/context/trello/tarjetas/HU-006.md y la operación que los cumple: 1, 2, 4 y
 * 5, cambiar; 3, historial y autor; 6 a 8, cambiar, que lanza RegistroInvalido y no cambia nada; 9,
 * cambiar, que lanza IllegalStateException; 10, cambiar, que acepta un valor inactivo que no se
 * toca; 11, no confirmar, en el controlador; 12, cambiar e historial, que lanzan
 * IllegalArgumentException; 13, el permiso de cambiar pacientes, en el controlador.
 */
public interface ContratoCambiosPaciente {

    /** La tabla del paciente en el historial común. */
    String TABLA = "paciente";

    /**
     * Cambia los datos del paciente con ese documento por los nuevos, con las reglas de HU-004, y
     * deja en el historial una fila por dato que cambió, con el valor anterior, el nuevo, el tipo,
     * quién tiene la sesión y la hora (RC-5, RC-6). O cambia todo, o nada. Devuelve las filas.
     */
    List<HistorialCambio> cambiar(String tipoDocumento, String numeroDocumento,
            DatosPaciente nuevos, HistorialCambio.Tipo tipo);

    /** Los datos que tiene hoy el paciente, para empezar a cambiarlos. */
    DatosPaciente datos(String tipoDocumento, String numeroDocumento);

    /** Cada cambio del paciente, del primero al último (escenario 3). */
    List<HistorialCambio> historial(String tipoDocumento, String numeroDocumento);

    /** El nombre y la identificación de quien hizo el cambio, o "sin sesión" (RC-6). */
    String autor(HistorialCambio cambio);
}
