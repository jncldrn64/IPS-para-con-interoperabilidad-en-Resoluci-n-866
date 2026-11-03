package com.ipssaludyvida.hu038.modelos;

import com.ipssaludyvida.comun.modelos.Catalogo;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

/**
 * Numeral 15, entidad que responde por el plan de beneficios: el código es Alfanumérico, 6 y 6, y
 * el nombre, de 3 a 200 caracteres (hojas 15 y 16). El catálogo es del Ministerio de Salud y no se
 * ha cargado (D-21).
 */
@Entity
@NoArgsConstructor
@AttributeOverride(name = "codigo", column = @Column(length = 6))
public class EntidadPlanBeneficios extends Catalogo {

    public EntidadPlanBeneficios(String codigo, String nombre) {
        super(codigo, nombre);
    }

    // Igual que en Etnia: sin esta regla entra un código de cualquier largo. La hoja pone formato
    // 999999, pero el tipo es Alfanumérico, y los códigos de las EAPB llevan letras, como EPS037.
    @Override
    @NotBlank(message = "el código no puede quedar vacío")
    @Pattern(regexp = "[A-Z0-9]{6}", message = "el código de una entidad del plan de beneficios"
            + " tiene 6 letras mayúsculas o dígitos (numeral 15)")
    public String getCodigo() {
        return super.getCodigo();
    }

    // Sin esta regla, un nombre de 1 o 2 letras entra: Catalogo solo pide que no esté vacío.
    @Override
    @Size(min = 3, max = 200, message = "el nombre de una entidad del plan de beneficios tiene"
            + " de 3 a 200 caracteres (numeral 15)")
    public String getNombre() {
        return super.getNombre();
    }
}
