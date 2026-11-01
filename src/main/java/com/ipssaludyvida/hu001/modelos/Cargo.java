package com.ipssaludyvida.hu001.modelos;

import static com.ipssaludyvida.hu001.modelos.Permiso.CAMBIAR_ATENCIONES;
import static com.ipssaludyvida.hu001.modelos.Permiso.CAMBIAR_OPOSICION_Y_VOLUNTAD;
import static com.ipssaludyvida.hu001.modelos.Permiso.CAMBIAR_PACIENTE;
import static com.ipssaludyvida.hu001.modelos.Permiso.CONSULTAR_ATENCIONES;
import static com.ipssaludyvida.hu001.modelos.Permiso.CONSULTAR_CATALOGOS;
import static com.ipssaludyvida.hu001.modelos.Permiso.CONSULTAR_OPOSICION_Y_VOLUNTAD;
import static com.ipssaludyvida.hu001.modelos.Permiso.CONSULTAR_PACIENTE;
import static com.ipssaludyvida.hu001.modelos.Permiso.MANTENER_CATALOGOS;

import java.util.EnumSet;
import java.util.Set;

/**
 * Los tres cargos de la IPS y las columnas de la matriz de permisos de HU-001. Es una lista del
 * sistema y no un catálogo: no llega de un archivo oficial (modelo E-R, usuario.cargo). No hay un
 * cargo de administrador (docs/REQUIREMENTS.md, sección 1).
 */
public enum Cargo {

    AUXILIAR_DE_ADMISION("Auxiliar de admisión", EnumSet.of(CAMBIAR_PACIENTE, CONSULTAR_PACIENTE,
            CAMBIAR_OPOSICION_Y_VOLUNTAD, CONSULTAR_OPOSICION_Y_VOLUNTAD, CONSULTAR_CATALOGOS)),
    MEDICO_TRATANTE("Médico tratante", EnumSet.of(CONSULTAR_PACIENTE,
            CONSULTAR_OPOSICION_Y_VOLUNTAD, CAMBIAR_ATENCIONES, CONSULTAR_ATENCIONES,
            CONSULTAR_CATALOGOS)),
    // D-02, propuesta del equipo: el cargo que mantiene los catálogos.
    PROFESIONAL_DE_ESTADISTICA("Profesional de estadística",
            EnumSet.of(MANTENER_CATALOGOS, CONSULTAR_CATALOGOS));

    private final String texto;
    private final Set<Permiso> permisos;

    Cargo(String texto, Set<Permiso> permisos) {
        this.texto = texto;
        this.permisos = permisos;
    }

    public String texto() {
        return texto;
    }

    public boolean tiene(Permiso permiso) {
        return permisos.contains(permiso);
    }
}
