package com.ipssaludyvida.hu004.servicios;

/**
 * Los datos que pide el registro del paciente, en el orden de la tarjeta de HU-004, con el nombre
 * con que la pantalla los pide y los marca cuando están mal (escenarios 8 a 11).
 */
public enum Campo {

    NACIONALIDAD("país de nacionalidad"),
    TIPO_DOCUMENTO("tipo de documento"),
    NUMERO_DOCUMENTO("número de documento"),
    PRIMER_APELLIDO("primer apellido"),
    SEGUNDO_APELLIDO("segundo apellido"),
    PRIMER_NOMBRE("primer nombre"),
    SEGUNDO_NOMBRE("segundo nombre"),
    FECHA_NACIMIENTO("fecha de nacimiento"),
    HORA_NACIMIENTO("hora de nacimiento"),
    SEXO_BIOLOGICO("sexo biológico"),
    IDENTIDAD_GENERO("identidad de género"),
    OCUPACION("ocupación"),
    DISCAPACIDAD("categoría de discapacidad"),
    PAIS_RESIDENCIA("país de residencia habitual"),
    MUNICIPIO_RESIDENCIA("ciudad (municipio) de residencia"),
    ETNIA("etnia"),
    COMUNIDAD_ETNICA("comunidad étnica"),
    ZONA_TERRITORIAL("zona territorial de residencia"),
    ENTIDAD_PLAN_BENEFICIOS("entidad que responde por el plan de beneficios");

    private final String texto;

    Campo(String texto) {
        this.texto = texto;
    }

    public String texto() {
        return texto;
    }
}
