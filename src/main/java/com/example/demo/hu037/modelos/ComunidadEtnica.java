package com.example.demo.hu037.modelos;

import com.example.demo.comun.modelos.Catalogo;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;

/**
 * Numeral 13, componente comunidad étnica: Alfanumérico, 3 caracteres (hoja 15). HU-037. El
 * catálogo oficial no se ha encontrado; mientras esté vacío, el paciente se registra sin este dato
 * (D-19).
 */
@Entity
@NoArgsConstructor
@AttributeOverride(name = "codigo", column = @Column(length = 3))
public class ComunidadEtnica extends Catalogo {

    public ComunidadEtnica(String codigo, String nombre) {
        super(codigo, nombre);
    }

    // Igual que en Etnia: sin esta regla entra un código de cualquier largo.
    @Override
    @NotBlank(message = "el código no puede quedar vacío")
    @Pattern(regexp = "\\d{3}", message = "el código de una comunidad tiene 3 dígitos (numeral 13)")
    public String getCodigo() {
        return super.getCodigo();
    }
}
