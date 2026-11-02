package com.ipssaludyvida.hu035.modelos;

import com.ipssaludyvida.comun.modelos.Catalogo;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;

/** Numeral 10, categoría de discapacidad: Alfanumérico, formato 99, 2 y 2 (hojas 14 y 15). */
@Entity
@NoArgsConstructor
@AttributeOverride(name = "codigo", column = @Column(length = 2))
public class CategoriaDiscapacidad extends Catalogo {

    public CategoriaDiscapacidad(String codigo, String nombre) {
        super(codigo, nombre);
    }

    // La regla va en el getter para que cada catálogo ponga su largo; sin ella entra un código de
    // cualquier largo, porque Catalogo no fija ninguno.
    @Override
    @NotBlank(message = "el código no puede quedar vacío")
    @Pattern(regexp = "\\d{2}",
            message = "el código de una categoría de discapacidad tiene 2 dígitos (numeral 10)")
    public String getCodigo() {
        return super.getCodigo();
    }
}
