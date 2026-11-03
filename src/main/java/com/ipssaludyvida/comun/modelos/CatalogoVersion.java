package com.ipssaludyvida.comun.modelos;

import com.ipssaludyvida.hu001.modelos.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Cada carga de un archivo oficial, con los datos que pide RC-8. La vigente de un catálogo es la de
 * fecha de publicación más reciente. Es la tabla catalogo_version del modelo E-R.
 */
@Entity
@Getter
@Setter
public class CatalogoVersion {

    @Id
    @GeneratedValue
    private Long id;

    /** El nombre de la entidad del catálogo, como "Etnia". */
    private String catalogo;
    private String version;
    private LocalDate fechaPublicacion;
    private String fuente;
    private String direccionDescarga;
    private LocalDate fechaDescarga;
    private int filasLeidas;
    private int filasAceptadas;
    private int filasRechazadas;
    private LocalDateTime cargadoEn;
    /** Quién la cargó. Lo llena el inicio de sesión (HU-001); antes queda vacío (RC-5). */
    private Long usuarioId;

    // La llave foránea de usuarioId hacia quien cargó el archivo; solo lectura.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", insertable = false, updatable = false)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Usuario usuario;
}
