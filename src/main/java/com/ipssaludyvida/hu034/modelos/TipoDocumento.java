package com.ipssaludyvida.hu034.modelos;

import com.ipssaludyvida.comun.modelos.Catalogo;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;

/** Numeral 2, tipo de documento: Alfanumérico, formato 99, 2 y 2 (hoja 13), como CC o TI. */
@Entity
@NoArgsConstructor
@AttributeOverride(name = "codigo", column = @Column(length = 2))
public class TipoDocumento extends Catalogo {

    public TipoDocumento(String codigo, String nombre) {
        super(codigo, nombre);
    }

    // La regla va en el getter para que cada catálogo ponga su largo; sin ella entra un código de
    // cualquier largo, porque Catalogo no fija ninguno. Los 14 de la hoja 13 son dos mayúsculas.
    @Override
    @NotBlank(message = "el código no puede quedar vacío")
    @Pattern(regexp = "[A-Z]{2}",
            message = "el código de un tipo de documento tiene 2 letras mayúsculas (numeral 2)")
    public String getCodigo() {
        return super.getCodigo();
    }
}
