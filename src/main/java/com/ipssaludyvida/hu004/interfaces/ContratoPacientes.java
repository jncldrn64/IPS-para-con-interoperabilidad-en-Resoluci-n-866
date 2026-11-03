package com.ipssaludyvida.hu004.interfaces;

import com.ipssaludyvida.hu004.modelos.Paciente;
import com.ipssaludyvida.hu004.servicios.Campo;
import com.ipssaludyvida.hu004.servicios.DatosPaciente;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * El contrato de HU-004, registro de un paciente. Lo implementa PacienteServicio y lo usa
 * PacienteControlador; HU-005 y HU-006 buscarán al paciente con buscar.
 *
 * <p>Escenarios de docs/context/trello/tarjetas/HU-004.md y la operación que los cumple: 1 a 7,
 * registrar; 8 a 10, validar y registrar, que lanza RegistroInvalido con cada dato; 11, el
 * controlador, que vuelve a pedir solo los datos marcados; 12, registrar, que lanza
 * IllegalStateException; 13, no confirmar, en el controlador; 14, catalogosSinValores y validar;
 * 15, la búsqueda de cada catálogo, en el controlador.
 */
public interface ContratoPacientes {

    /** Colombia en la tabla Pais de SISPRO: con ella el municipio es obligatorio (numeral 12). */
    String COLOMBIA = "170";

    /** "Sin discapacidad" en las hojas 14 y 15: no va con otra categoría. */
    String SIN_DISCAPACIDAD = "08";

    /** Cada dato que falta o que está mal, con su motivo, sin guardar nada. Vacío si sirve. */
    Map<Campo, String> validar(DatosPaciente datos);

    /**
     * Guarda el paciente con quien tiene la sesión y la hora (RC-5). Lanza RegistroInvalido si un
     * dato falta o está mal, e IllegalStateException si ya hay un paciente con ese documento
     * (D-07).
     */
    Paciente registrar(DatosPaciente datos);

    Optional<Paciente> buscar(String tipoDocumento, String numeroDocumento);

    /**
     * Los catálogos del paciente sin valores activos, cada uno con cuándo es obligatorio. El
     * registro no se guarda mientras falte uno obligatorio para ese paciente (escenario 14).
     */
    List<String> catalogosSinValores();
}
