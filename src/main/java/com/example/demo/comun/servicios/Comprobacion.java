package com.example.demo.comun.servicios;

import java.util.List;

/**
 * El catálogo comparado con su lista oficial, sin cambiar nada (escenario 18 de HU-036, 17 de
 * HU-037): los códigos oficiales que faltan, los oficiales que están inactivos, los activos que la
 * lista no tiene y los oficiales con otro nombre.
 */
public record Comprobacion(List<String> faltan, List<String> inactivos, List<String> sobran,
        List<String> nombresDistintos) {

    public boolean coincide() {
        return faltan.isEmpty() && inactivos.isEmpty() && sobran.isEmpty()
                && nombresDistintos.isEmpty();
    }
}
