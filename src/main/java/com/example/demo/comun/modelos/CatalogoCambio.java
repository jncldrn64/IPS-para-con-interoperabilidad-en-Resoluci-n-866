package com.example.demo.comun.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
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
}
