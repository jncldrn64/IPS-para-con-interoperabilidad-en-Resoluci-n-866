package com.ipssaludyvida.hu001.repositorios;

import com.ipssaludyvida.hu001.modelos.Usuario;
import java.util.Optional;
import org.springframework.data.repository.Repository;

/** Los usuarios. Busca y guarda, y no borra (docs/DESIGN.md, sección 6). */
public interface UsuarioRepositorio extends Repository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);

    long count();

    Usuario save(Usuario usuario);
}
