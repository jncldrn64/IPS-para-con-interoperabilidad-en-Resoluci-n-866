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
 * Numeral 15, entidad que responde por el plan de beneficios: el código es Alfanumérico, de hasta 6,
 * y el nombre, de 3 a 200 caracteres (hojas 15 y 16). El catálogo es la tabla CodigoEAPByNit de
 * SISPRO (D-21).
 */
@Entity
@NoArgsConstructor
@AttributeOverride(name = "codigo", column = @Column(length = 6))
public class EntidadPlanBeneficios extends Catalogo {

    public EntidadPlanBeneficios(String codigo, String nombre) {
        super(codigo, nombre);
    }

    // Igual que en Etnia: sin esta regla entra un código de cualquier largo. La hoja pone formato
    // 999999, pero los códigos de CodigoEAPByNit van de 4 a 6: 05001 una dirección de salud, EPS037
    // una EPS y 13-5 una aseguradora (docs/DECISIONS.md, 2026-11-02 "La carga lee la columna
    // Habilitado").
    @Override
    @NotBlank(message = "el código no puede quedar vacío")
    @Pattern(regexp = "[A-Z0-9-]{4,6}", message = "el código de una entidad del plan de beneficios"
            + " tiene de 4 a 6 letras mayúsculas, dígitos o guiones (numeral 15)")
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
