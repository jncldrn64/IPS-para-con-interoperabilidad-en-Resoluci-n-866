package com.ipssaludyvida.hu001.servicios;

import com.ipssaludyvida.comun.servicios.RegistroCatalogos;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Permiso;
import com.ipssaludyvida.hu001.modelos.Usuario;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * La sesión de quien usa la terminal. El programa corre en una sola terminal, así que hay una sola
 * sesión a la vez. No lee del teclado ni imprime (docs/DESIGN.md, sección 6).
 */
@Service
public class SesionServicio implements ContratoSesion {

    /** D-06, valor propuesto. */
    public static final Duration INACTIVIDAD = Duration.ofMinutes(15);

    private final UsuarioRepositorio usuarios;
    private final Clock reloj;
    private final PasswordEncoder claves = new BCryptPasswordEncoder();
    private Usuario actual;
    private Instant ultimaActividad;

    @Autowired
    public SesionServicio(UsuarioRepositorio usuarios) {
        this(usuarios, Clock.system(RegistroCatalogos.ZONA));
    }

    /** Un test pasa un reloj que avanza cuando él quiere, para el escenario 12. */
    public SesionServicio(UsuarioRepositorio usuarios, Clock reloj) {
        this.usuarios = usuarios;
        this.reloj = reloj;
    }

    /** La clave cifrada que se guarda. La usa el equipo técnico al crear un usuario. */
    public String cifrar(String clave) {
        return claves.encode(clave);
    }

    @Override
    public Usuario iniciar(String usuario, String clave) {
        boolean sinUsuario = usuario == null || usuario.isBlank();
        boolean sinClave = clave == null || clave.isEmpty();
        if (sinUsuario && sinClave) {
            throw new IllegalArgumentException("faltan el usuario y la contraseña");
        }
        if (sinUsuario) {
            throw new IllegalArgumentException("falta el usuario");
        }
        if (sinClave) {
            throw new IllegalArgumentException("falta la contraseña");
        }
        // Un usuario que no existe, uno inactivo y una clave equivocada dan el mismo mensaje: si
        // fueran distintos, quien prueba sabría qué usuarios existen (escenario 2).
        Usuario encontrado = usuarios.findByUsuario(usuario.trim())
                .filter(Usuario::isActivo)
                .filter(u -> claves.matches(clave, u.getClaveHash()))
                .orElseThrow(() -> new IllegalArgumentException(
                        "el usuario o la contraseña no son válidos"));
        actual = encontrado;
        ultimaActividad = reloj.instant();
        return encontrado;
    }

    @Override
    public void cerrar() {
        actual = null;
        ultimaActividad = null;
    }

    @Override
    public boolean abierta() {
        return actual != null;
    }

    @Override
    public Optional<Usuario> actual() {
        return Optional.ofNullable(actual);
    }

    @Override
    public boolean seguir() {
        if (actual == null) {
            return false;
        }
        Instant ahora = reloj.instant();
        if (Duration.between(ultimaActividad, ahora).compareTo(INACTIVIDAD) >= 0) {
            cerrar();
            return false;
        }
        ultimaActividad = ahora;
        return true;
    }

    @Override
    public boolean puede(Permiso permiso) {
        return actual != null && actual.getCargo().tiene(permiso);
    }

    @Override
    public void exigir(Permiso permiso) {
        if (actual == null) {
            throw new IllegalStateException(SIN_SESION);
        }
        if (!puede(permiso)) {
            throw new IllegalStateException(SIN_PERMISO);
        }
    }
}
