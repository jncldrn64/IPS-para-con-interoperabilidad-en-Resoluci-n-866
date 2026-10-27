package com.example.demo.servicio;

import java.time.LocalDate;

/**
 * Los datos que el profesional de estadística escribe al cargar un archivo oficial (RC-8). Si la
 * fuente no trae fecha de publicación, vale la de descarga.
 */
public record DatosVersion(String fuente, String direccionDescarga, LocalDate fechaDescarga,
        String version, LocalDate fechaPublicacion) {

    public LocalDate publicacion() {
        return fechaPublicacion != null ? fechaPublicacion : fechaDescarga;
    }
}
