package com.example.demo.hu036.modelos;

import com.example.demo.comun.modelos.Catalogo;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;

/** Numeral 13, componente etnia: Alfanumérico, 2 caracteres (hoja 15). HU-036. */
@Entity
@NoArgsConstructor
@AttributeOverride(name = "codigo", column = @Column(length = 2))
public class Etnia extends Catalogo {

    public Etnia(String codigo, String nombre) {
        super(codigo, nombre);
    }

    // La regla va en el getter para que cada catálogo ponga su largo; sin ella entra un código de
    // cualquier largo, porque Catalogo no fija ninguno.
    @Override
    @NotBlank(message = "el código no puede quedar vacío")
    @Pattern(regexp = "\\d{2}", message = "el código de una etnia tiene 2 dígitos (numeral 13)")
    public String getCodigo() {
        return super.getCodigo();
    }
}
