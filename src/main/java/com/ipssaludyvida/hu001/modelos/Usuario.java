package com.ipssaludyvida.hu001.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Una persona que entra al sistema, con su cargo. Es la tabla usuario del modelo E-R. Los usuarios
 * los deja creados el equipo técnico al instalar: no hay registro de usuarios ni recuperación de
 * cuenta en esta fase [2.4]. La clave se guarda cifrada, nunca como se escribió.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String identificacion;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String claveHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    private boolean activo = true;

    public Usuario(String identificacion, String nombre, String usuario, String claveHash,
            Cargo cargo) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.usuario = usuario;
        this.claveHash = claveHash;
        this.cargo = cargo;
    }
}
