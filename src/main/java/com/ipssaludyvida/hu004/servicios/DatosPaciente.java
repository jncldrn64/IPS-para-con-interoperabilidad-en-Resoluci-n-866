package com.ipssaludyvida.hu004.servicios;

import com.ipssaludyvida.hu004.modelos.Paciente;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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

    /** Los datos que tiene hoy un paciente, para cambiarlos (HU-006). */
    public static DatosPaciente de(Paciente p) {
        DatosPaciente d = new DatosPaciente();
        d.setNacionalidades(new ArrayList<>(p.getNacionalidades()));
        d.setTipoDocumento(p.getTipoDocumento());
        d.setNumeroDocumento(p.getNumeroDocumento());
        d.setPrimerApellido(p.getPrimerApellido());
        d.setSegundoApellido(texto(p.getSegundoApellido()));
        d.setPrimerNombre(p.getPrimerNombre());
        d.setSegundoNombre(texto(p.getSegundoNombre()));
        d.setFechaNacimiento(p.getFechaNacimiento().toString());
        d.setHoraNacimiento(p.getHoraNacimiento() == null ? ""
                : p.getHoraNacimiento().format(DateTimeFormatter.ofPattern("HH:mm")));
        d.setSexoBiologico(p.getSexoBiologico());
        d.setIdentidadGenero(p.getIdentidadGenero());
        d.setOcupacion(texto(p.getOcupacion()));
        d.setDiscapacidades(new ArrayList<>(p.getDiscapacidades()));
        d.setPaisResidencia(p.getPaisResidencia());
        d.setMunicipioResidencia(texto(p.getMunicipioResidencia()));
        d.setEtnia(p.getEtnia());
        d.setComunidadEtnica(texto(p.getComunidadEtnica()));
        d.setZonaTerritorial(p.getZonaTerritorial());
        d.setEntidadPlanBeneficios(p.getEntidadPlanBeneficios());
        return d;
    }

    /**
     * Cada dato como texto, para compararlo y guardarlo en el historial: sin espacios a los lados,
     * los códigos en mayúsculas y los datos con varios valores ordenados y separados por coma.
     */
    public Map<Campo, String> valores() {
        Map<Campo, String> v = new EnumMap<>(Campo.class);
        v.put(Campo.NACIONALIDAD, lista(nacionalidades));
        v.put(Campo.TIPO_DOCUMENTO, codigo(tipoDocumento));
        v.put(Campo.NUMERO_DOCUMENTO, texto(numeroDocumento).trim());
        v.put(Campo.PRIMER_APELLIDO, texto(primerApellido).trim());
        v.put(Campo.SEGUNDO_APELLIDO, texto(segundoApellido).trim());
        v.put(Campo.PRIMER_NOMBRE, texto(primerNombre).trim());
        v.put(Campo.SEGUNDO_NOMBRE, texto(segundoNombre).trim());
        v.put(Campo.FECHA_NACIMIENTO, texto(fechaNacimiento).trim());
        v.put(Campo.HORA_NACIMIENTO, texto(horaNacimiento).trim());
        v.put(Campo.SEXO_BIOLOGICO, codigo(sexoBiologico));
        v.put(Campo.IDENTIDAD_GENERO, codigo(identidadGenero));
        v.put(Campo.OCUPACION, codigo(ocupacion));
        v.put(Campo.DISCAPACIDAD, lista(discapacidades));
        v.put(Campo.PAIS_RESIDENCIA, codigo(paisResidencia));
        v.put(Campo.MUNICIPIO_RESIDENCIA, codigo(municipioResidencia));
        v.put(Campo.ETNIA, codigo(etnia));
        v.put(Campo.COMUNIDAD_ETNICA, codigo(comunidadEtnica));
        v.put(Campo.ZONA_TERRITORIAL, codigo(zonaTerritorial));
        v.put(Campo.ENTIDAD_PLAN_BENEFICIOS, codigo(entidadPlanBeneficios));
        return v;
    }

    private static String texto(String valor) {
        return valor == null ? "" : valor;
    }

    private static String codigo(String valor) {
        return texto(valor).trim().toUpperCase(java.util.Locale.ROOT);
    }

    private static String lista(List<String> valores) {
        return valores.stream().map(DatosPaciente::codigo).filter(c -> !c.isEmpty()).distinct()
                .sorted().collect(java.util.stream.Collectors.joining(", "));
    }
}
