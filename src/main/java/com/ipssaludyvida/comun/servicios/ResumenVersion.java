package com.ipssaludyvida.comun.servicios;

import com.ipssaludyvida.comun.modelos.CatalogoVersion;

/**
 * Una versión cargada y cuántos códigos creó, cambió e inactivó (escenario 13 de HU-036). Cambiar
 * cuenta los cambios de nombre y los códigos que la versión trajo de vuelta.
 */
public record ResumenVersion(CatalogoVersion version, long creados, long cambiados,
        long inactivados) {
}
