package com.ipssaludyvida.hu004.servicios;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Lo que la pantalla de HU-004 lleva escrito, todavía sin validar. Las fechas y las horas van como
 * texto para que el servicio diga cuál está mal (escenario 9), y lo que está bien se conserva
 * mientras se corrige lo demás (escenario 11). Un dato vacío es un dato que falta.
 */
@Getter
@Setter
public class DatosPaciente {

    private List<String> nacionalidades = new ArrayList<>();
    private String tipoDocumento = "";
    private String numeroDocumento = "";
    private String primerApellido = "";
    private String segundoApellido = "";
    private String primerNombre = "";
    private String segundoNombre = "";
    /** AAAA-MM-DD. */
    private String fechaNacimiento = "";
    /** HH:MM, o vacía si no se sabe (escenario 4). */
    private String horaNacimiento = "";
    private String sexoBiologico = "";
    private String identidadGenero = "";
    private String ocupacion = "";
    private List<String> discapacidades = new ArrayList<>();
    private String paisResidencia = "";
    private String municipioResidencia = "";
    private String etnia = "";
    private String comunidadEtnica = "";
    private String zonaTerritorial = "";
    private String entidadPlanBeneficios = "";
}
