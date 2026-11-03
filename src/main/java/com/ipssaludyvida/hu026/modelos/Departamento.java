package com.ipssaludyvida.hu026.modelos;

import com.ipssaludyvida.comun.modelos.CatalogoVersion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
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

    // La llave foránea de versionId hacia la versión de la DIVIPOLA; solo lectura.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "versionId", insertable = false, updatable = false)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private CatalogoVersion version;
}
