package com.ipssaludyvida.hu026.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Un departamento de la DIVIPOLA, que llega en el mismo archivo que los municipios. Es la tabla
 * departamento del modelo E-R: código de 2 dígitos, nombre y versión, sin estado, porque no tiene
 * historia propia ni se inactiva. HU-026 lo guarda al cargar.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Departamento {

    // Texto, no número: como Integer, el 05 de Antioquia se guarda como 5 (D-18).
    @Id
    @Column(length = 2)
    private String codigo;

    @Column(length = 200, nullable = false)
    private String nombre;

    private Long versionId;

    public Departamento(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
}
