package com.ipssaludyvida.hu018.modelos;

import com.ipssaludyvida.comun.modelos.Catalogo;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

/**
 * Numerales 1 y 11: el código del país es Alfanumérico, formato 999, 3 y 3, según la ISO 3166-1, y
 * su nombre, de 3 a 200 caracteres (hojas 13 y 15). Es el código numérico: Colombia es 170 (D-03).
 */
@Entity
@NoArgsConstructor
@AttributeOverride(name = "codigo", column = @Column(length = 3))
public class Pais extends Catalogo {

    public Pais(String codigo, String nombre) {
        super(codigo, nombre);
    }

    // Igual que en Etnia: sin esta regla entra un código de cualquier largo, y uno con letras, como
    // COL, que es el alfabético de la misma norma y no el que pide la hoja.
    @Override
    @NotBlank(message = "el código no puede quedar vacío")
    @Pattern(regexp = "\\d{3}", message = "el código de un país tiene 3 dígitos (numerales 1 y 11)")
    public String getCodigo() {
        return super.getCodigo();
    }

    // Sin esta regla, un nombre de 1 o 2 letras entra: Catalogo solo pide que no esté vacío.
    @Override
    @Size(min = 3, max = 200,
            message = "el nombre de un país tiene de 3 a 200 caracteres (numerales 1 y 11)")
    public String getNombre() {
        return super.getNombre();
    }
}
