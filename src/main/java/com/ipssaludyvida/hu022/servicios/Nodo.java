package com.ipssaludyvida.hu022.servicios;

import com.ipssaludyvida.hu022.modelos.Ocupacion;

/**
 * Una línea de la jerarquía de HU-022 (escenario 16): el valor, su nivel, de 1 a 4, si es un grupo
 * sin ocupaciones hijas activas y si se registra al paciente.
 */
public record Nodo(Ocupacion valor, int nivel, boolean sinOcupaciones, boolean seRegistra) {
}
