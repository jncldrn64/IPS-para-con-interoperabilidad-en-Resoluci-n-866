package com.example.demo.comun.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
}
