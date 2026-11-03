package com.ipssaludyvida.hu004.modelos;

import com.ipssaludyvida.comun.modelos.Catalogo;
import com.ipssaludyvida.hu001.modelos.Usuario;
import com.ipssaludyvida.hu018.modelos.Pais;
import com.ipssaludyvida.hu022.modelos.Ocupacion;
import com.ipssaludyvida.hu026.modelos.Municipio;
import com.ipssaludyvida.hu034.modelos.TipoDocumento;
import com.ipssaludyvida.hu035.modelos.CategoriaDiscapacidad;
import com.ipssaludyvida.hu036.modelos.Etnia;
import com.ipssaludyvida.hu037.modelos.ComunidadEtnica;
import com.ipssaludyvida.hu038.modelos.EntidadPlanBeneficios;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Un paciente con los datos que pide la Resolución 866, numerales 1 a 7 y 10 a 15. Es la tabla
 * paciente del modelo E-R. Cada dato de un catálogo se guarda con su código: el nombre que tenía el
 * valor el día del registro sale de la tabla de cambios del catálogo (RC-11, D-25). La oposición y
 * la voluntad anticipada son estructuras aparte, de HU-010 y HU-014.
 *
 * <p>Cada código es además una llave foránea hacia su catálogo, como dibuja el modelo E-R: un
 * {@code @ManyToOne} de solo lectura sobre la misma columna, y las dos tablas de intersección como
 * {@code @ManyToMany}. Van en un solo sentido, desde el paciente; los catálogos no saben de él.
 * Un catálogo no borra y un cambio de código crea otra fila, así que la llave no queda rota
 * (docs/DECISIONS.md, 2026-11-03 "El paciente apunta a sus catálogos con llaves foráneas").
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

    /** Numeral 1, uno o varios países, en la tabla de intersección paciente_nacionalidad. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "paciente_nacionalidad", joinColumns = @JoinColumn(name = "paciente_id"),
            inverseJoinColumns = @JoinColumn(name = "pais"))
    @OrderBy("codigo")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Set<Pais> nacionalidades = new LinkedHashSet<>();

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

    /** Numeral 10, una o varias categorías, en la tabla de intersección paciente_discapacidad. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "paciente_discapacidad", joinColumns = @JoinColumn(name = "paciente_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria"))
    @OrderBy("codigo")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Set<CategoriaDiscapacidad> discapacidades = new LinkedHashSet<>();

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

    // Las llaves foráneas de los datos de un solo valor. Cada una lee la columna del código de
    // arriba y no la escribe: lo que se guarda sigue siendo el código. El nombre de la columna es
    // el del campo, porque Hibernate no acepta dos nombres para una misma columna.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoDocumento", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private TipoDocumento valorTipoDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ocupacion", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Ocupacion valorOcupacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paisResidencia", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Pais valorPaisResidencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipioResidencia", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Municipio valorMunicipioResidencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etnia", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Etnia valorEtnia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comunidadEtnica", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private ComunidadEtnica valorComunidadEtnica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entidadPlanBeneficios", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private EntidadPlanBeneficios valorEntidadPlanBeneficios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registradoPor", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Usuario usuarioQueRegistro;

    /** Los códigos de los países de nacionalidad, en orden de código. */
    public Set<String> getNacionalidades() {
        return codigos(nacionalidades);
    }

    public void setNacionalidades(Collection<Pais> paises) {
        nacionalidades.clear();
        nacionalidades.addAll(paises);
    }

    /** Los códigos de las categorías de discapacidad, en orden de código. */
    public Set<String> getDiscapacidades() {
        return codigos(discapacidades);
    }

    public void setDiscapacidades(Collection<CategoriaDiscapacidad> categorias) {
        discapacidades.clear();
        discapacidades.addAll(categorias);
    }

    private static Set<String> codigos(Collection<? extends Catalogo> valores) {
        Set<String> codigos = new LinkedHashSet<>();
        valores.forEach(v -> codigos.add(v.getCodigo()));
        return Collections.unmodifiableSet(codigos);
    }
}
