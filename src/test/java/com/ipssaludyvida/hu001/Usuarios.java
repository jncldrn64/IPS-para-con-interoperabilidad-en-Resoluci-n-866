package com.ipssaludyvida.hu001;

import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Cargo;
import com.ipssaludyvida.hu001.modelos.Usuario;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Los usuarios de los tests. La base en memoria arranca sin usuarios, y el test que necesita entrar
 * crea el suyo. La clave se cifra una vez, porque cada cifrado tarda.
 */
public final class Usuarios {

    public static final String CLAVE = "Clave123";
    private static final String HASH = new BCryptPasswordEncoder().encode(CLAVE);

    private Usuarios() {
    }

    /** Crea el usuario de ese cargo, con el nombre de usuario igual al nombre del cargo. */
    public static Usuario crear(UsuarioRepositorio usuarios, Cargo cargo) {
        String nombre = cargo.name().toLowerCase();
        return usuarios.findByUsuario(nombre).orElseGet(() -> usuarios.save(new Usuario(
                "id-" + nombre, cargo.texto() + " de prueba", nombre, HASH, cargo)));
    }

    /** Crea el usuario de ese cargo y abre su sesión. */
    public static Usuario entrar(UsuarioRepositorio usuarios, ContratoSesion sesion, Cargo cargo) {
        Usuario u = crear(usuarios, cargo);
        sesion.iniciar(u.getUsuario(), CLAVE);
        return u;
    }
}
