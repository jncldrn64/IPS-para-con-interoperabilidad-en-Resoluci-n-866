package com.ipssaludyvida.comun.interfaces;

import com.ipssaludyvida.comun.modelos.Catalogo;

/**
 * Cuántos registros de una historia usan un valor de catálogo, como los pacientes de HU-004. La
 * base común no conoce esas historias: cada una que guarda códigos de catálogo implementa esta
 * interfaz, y el catálogo suma lo que todas le cuentan (RC-4, D-20).
 */
public interface UsosDeCatalogo {

    /** Los registros que usan ese código de ese catálogo; 0 si la historia no lo guarda. */
    long contar(Class<? extends Catalogo> catalogo, String codigo);
}
