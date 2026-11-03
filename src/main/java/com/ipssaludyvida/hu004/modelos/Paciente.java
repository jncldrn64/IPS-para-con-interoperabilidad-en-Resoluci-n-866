package com.ipssaludyvida.hu004.modelos;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * Un paciente con los datos que pide la Resolución 866, numerales 1 a 7 y 10 a 15. Es la tabla
 * paciente del modelo E-R. Cada dato de un catálogo se guarda con su código: el nombre que tenía el
 * valor el día del registro sale de la tabla de cambios del catálogo (RC-11, D-25). La oposición y
 * la voluntad anticipada son estructuras aparte, de HU-010 y HU-014.
 */
@Entity
@Getter
@Setter
// Sin esta regla dos registros con el mismo documento entran si se guardan a la vez (D-07).
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"tipoDocumento", "numeroDocumento"}))
public class Paciente {

    @Id
    @GeneratedValue
    private Long id;

    /** Numeral 1, uno o varios países, en la tabla paciente_nacionalidad. */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "paciente_nacionalidad",
            joinColumns = @JoinColumn(name = "paciente_id"))
    @Column(name = "pais", length = 3)
    private Set<String> nacionalidades = new LinkedHashSet<>();

    @Column(length = 2, nullable = false)
    private String tipoDocumento;

    @Column(length = 20, nullable = false)
    private String numeroDocumento;

    @Column(length = 60, nullable = false)
    private String primerApellido;

    @Column(length = 60)
    private String segundoApellido;

    @Column(length = 60, nullable = false)
    private String primerNombre;

    @Column(length = 60)
    private String segundoNombre;

    /** Numeral 4: la fecha y la hora van por separado, y la hora puede faltar [2.1]. */
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    private LocalTime horaNacimiento;

    @Column(length = 2, nullable = false)
    private String sexoBiologico;

    @Column(length = 2, nullable = false)
    private String identidadGenero;

    /** Numeral 7: solo una ocupación de 4 dígitos, o ninguna si la persona no tiene [2.1]. */
    @Column(length = 4)
    private String ocupacion;

    /** Numeral 10, una o varias categorías, en la tabla paciente_discapacidad. */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "paciente_discapacidad",
            joinColumns = @JoinColumn(name = "paciente_id"))
    @Column(name = "categoria", length = 2)
    private Set<String> discapacidades = new LinkedHashSet<>();

    @Column(length = 3, nullable = false)
    private String paisResidencia;

    /** Numeral 12, con su código DANE, solo si reside en Colombia. */
    @Column(length = 5)
    private String municipioResidencia;

    @Column(length = 2, nullable = false)
    private String etnia;

    @Column(length = 3)
    private String comunidadEtnica;

    @Column(length = 2, nullable = false)
    private String zonaTerritorial;

    @Column(length = 6, nullable = false)
    private String entidadPlanBeneficios;

    /** Quién lo registró y cuándo, en la hora de Colombia (RC-5). Sin sesión queda vacío. */
    private Long registradoPor;

    @Column(nullable = false)
    private LocalDateTime registradoEn;
}
