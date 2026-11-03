package com.ipssaludyvida.hu026.modelos;

import com.ipssaludyvida.comun.modelos.Catalogo;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Numeral 12, municipio de residencia: un código de 5 caracteres y un nombre de 3 a 200 (hoja 15).
 * HU-026. Los dos primeros caracteres del código son el departamento, que se guarda aparte para
 * mostrarlo junto al municipio.
 */
@Entity
@Getter
@NoArgsConstructor
@AttributeOverride(name = "codigo", column = @Column(length = 5))
public class Municipio extends Catalogo {

    /** El código del departamento: los dos primeros caracteres del código (numeral 12). */
    @Column(length = 2)
    private String departamento;

    public Municipio(String codigo, String nombre) {
        super(codigo, nombre);
        this.departamento = departamentoDe(codigo);
    }

    // Igual que en Etnia: sin esta regla entra un código de cualquier largo, y uno de 4 dígitos
    // perdería el cero inicial de Medellín, 05001 (D-18).
    @Override
    @NotBlank(message = "el código no puede quedar vacío")
    @Pattern(regexp = "\\d{5}", message = "el código de un municipio tiene 5 dígitos (numeral 12)")
    public String getCodigo() {
        return super.getCodigo();
    }

    // Sin esta regla, un nombre de 1 o 2 letras entra: Catalogo solo pide que no esté vacío.
    @Override
    @Size(min = 3, max = 200, message = "el nombre de un municipio tiene de 3 a 200 caracteres"
            + " (numeral 12)")
    public String getNombre() {
        return super.getNombre();
    }

    /** Los dos primeros caracteres del código, o null si tiene menos. */
    public static String departamentoDe(String codigo) {
        return codigo == null || codigo.length() < 2 ? null : codigo.substring(0, 2);
    }

    // La llave foránea del departamento hacia su tabla; solo lectura. Lo que se guarda sigue
    // siendo el código.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento", insertable = false, updatable = false)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Departamento valorDepartamento;
}
