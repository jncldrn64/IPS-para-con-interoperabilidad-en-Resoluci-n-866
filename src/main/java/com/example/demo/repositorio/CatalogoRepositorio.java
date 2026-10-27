package com.example.demo.repositorio;

import com.example.demo.modelo.Catalogo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * Lo que puede hacer el repositorio de un catálogo. Extiende Repository, que no trae ningún método,
 * en lugar de CrudRepository, que trae deleteById: un catálogo no borra (docs/DESIGN.md,
 * sección 6).
 */
@NoRepositoryBean
public interface CatalogoRepositorio<T extends Catalogo> extends Repository<T, String> {

    List<T> findAllByOrderByCodigoAsc();

    List<T> findByEstadoOrderByCodigoAsc(Catalogo.Estado estado);

    Optional<T> findById(String codigo);

    T save(T valor);
}
