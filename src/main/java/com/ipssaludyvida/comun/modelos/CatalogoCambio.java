package com.ipssaludyvida.comun.modelos;

import com.ipssaludyvida.hu001.modelos.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * La tabla de cambios común a los catálogos: cada creación, cambio de nombre, inactivación o
 * reactivación de un valor, con su motivo y su referencia (RC-4, RC-10, RC-11). Es la tabla
 * catalogo_cambio del modelo E-R. Nada se borra de aquí.
 */
@Entity
@Getter
@Setter
public class CatalogoCambio {

    public enum Tipo { CREADO, NOMBRE, INACTIVADO, REACTIVADO }

    @Id
    @GeneratedValue
    private Long id;

    /** El nombre de la entidad del catálogo, como "Etnia". */
    private String catalogo;
    private String codigo;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    private String valorAnterior;
    private String valorNuevo;

    @Enumerated(EnumType.STRING)
    private Motivo motivo;

    private String referenciaOficial;
    /** Por qué se reactiva, cuando no hay otra referencia oficial (RC-10). */
    private String justificacion;
    /** El código nuevo, cuando el motivo es "Reemplazado por". */
    private String codigoReemplazo;
    private Long versionId;
    /** Quién lo hizo. Lo llena el inicio de sesión (HU-001); antes queda vacío (RC-5). */
    private Long usuarioId;
    private LocalDateTime fechaHora;

    // Llaves foráneas de versionId y usuarioId; solo lectura.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "versionId", insertable = false, updatable = false)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private CatalogoVersion version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", insertable = false, updatable = false)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Usuario usuario;
}
