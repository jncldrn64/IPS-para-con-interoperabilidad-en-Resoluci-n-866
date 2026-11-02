package com.ipssaludyvida.hu001.modelos;

/**
 * Las capacidades de la matriz de permisos de HU-001, una por fila de la tabla de la tarjeta
 * (docs/context/trello/tarjetas/HU-001.md, "Reglas de negocio aplicables"). Qué cargo tiene cada
 * una lo dice Cargo.
 */
public enum Permiso {

    CAMBIAR_PACIENTE("Registrar, actualizar y corregir los datos del paciente"),
    CONSULTAR_PACIENTE("Consultar los datos del paciente"),
    CAMBIAR_OPOSICION_Y_VOLUNTAD(
            "Registrar, actualizar, corregir y anular la oposición y la voluntad anticipada"),
    CONSULTAR_OPOSICION_Y_VOLUNTAD("Consultar la oposición y la voluntad anticipada"),
    CAMBIAR_ATENCIONES("Registrar, corregir y anular atenciones"),
    CONSULTAR_ATENCIONES("Consultar atenciones"),
    MANTENER_CATALOGOS("Cargar y mantener catálogos (crear, actualizar, inactivar, reactivar)"),
    CONSULTAR_CATALOGOS("Consultar catálogos");

    private final String texto;

    Permiso(String texto) {
        this.texto = texto;
    }

    /** La fila de la matriz, como la escribe la tarjeta. */
    public String texto() {
        return texto;
    }
}
