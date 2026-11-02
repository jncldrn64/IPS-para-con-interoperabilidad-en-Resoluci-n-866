package com.ipssaludyvida.hu022.modelos;

import com.ipssaludyvida.comun.modelos.Catalogo;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

/**
 * Numeral 7, ocupación: un código de 4 caracteres y un nombre de 3 a 200 (hoja 14). HU-022. El
 * catálogo trae también los grupos de la CIUO-88 A.C., con códigos de 1 a 3 dígitos, para la
 * jerarquía; el grupo de cada código es el código sin su último dígito (D-26). Solo las de 4
 * dígitos se registran al paciente [2.1].
 */
@Entity
@NoArgsConstructor
@AttributeOverride(name = "codigo", column = @Column(length = 4))
public class Ocupacion extends Catalogo {

    /** El largo del código de una ocupación hija, la que se registra al paciente. */
    public static final int LARGO_HIJA = 4;

    public Ocupacion(String codigo, String nombre) {
        super(codigo, nombre);
    }

    // Igual que en Etnia: sin esta regla entra un código de cualquier largo. Los grupos tienen de 1
    // a 3 dígitos y se validan con su grupo, no con el largo del numeral 7 (RC-8).
    @Override
    @NotBlank(message = "el código no puede quedar vacío")
    @Pattern(regexp = "\\d{1,4}",
            message = "el código de una ocupación tiene de 1 a 4 dígitos (numeral 7)")
    public String getCodigo() {
        return super.getCodigo();
    }

    // Sin esta regla, un nombre de 1 o 2 letras entra: Catalogo solo pide que no esté vacío.
    @Override
    @Size(min = 3, max = 200, message = "el nombre de una ocupación tiene de 3 a 200 caracteres"
            + " (numeral 7)")
    public String getNombre() {
        return super.getNombre();
    }

    /** El código del grupo, o null si es un gran grupo (D-26). */
    public static String grupoDe(String codigo) {
        return codigo == null || codigo.length() <= 1 ? null
                : codigo.substring(0, codigo.length() - 1);
    }
}
