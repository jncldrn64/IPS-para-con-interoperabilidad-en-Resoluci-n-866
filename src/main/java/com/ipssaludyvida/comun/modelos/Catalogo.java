package com.ipssaludyvida.comun.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lo que comparten los catálogos: código, nombre, estado y versión (docs/DESIGN.md, sección 7).
 * Cada catálogo es una entidad propia que hereda de esta clase y fija el largo de su código.
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Catalogo {

    public enum Estado { ACTIVO, INACTIVO }

    // Texto, no número: como Integer, el 01 del anexo se guarda como 1 (D-18).
    @Id
    private String codigo;

    @NotBlank(message = "el nombre no puede quedar vacío")
    @Column(length = 200)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private Estado estado = Estado.ACTIVO;

    // La versión del archivo oficial que trajo el código; la llena la carga desde el CSV (RC-8).
    private Long versionId;

    protected Catalogo(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
}
