package com.ipssaludyvida.comun.servicios;

import com.ipssaludyvida.comun.modelos.CatalogoVersion;
import java.util.List;
import java.util.Optional;

/**
 * Lo que muestra la consulta de un catálogo: los valores del filtro, cuántos hay activos, la
 * versión vigente y, si el catálogo está vacío, el aviso (escenarios 13 y 17 de HU-036).
 */
public record Consulta<T>(List<T> valores, long activos, Optional<CatalogoVersion> vigente,
        String aviso) {
}
