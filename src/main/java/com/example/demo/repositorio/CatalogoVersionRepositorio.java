package com.example.demo.repositorio;

import com.example.demo.modelo.CatalogoVersion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

/** Las versiones cargadas de cada catálogo. Como los catálogos, no borra. */
public interface CatalogoVersionRepositorio extends Repository<CatalogoVersion, Long> {

    Optional<CatalogoVersion> findFirstByCatalogoOrderByFechaPublicacionDescIdDesc(String catalogo);

    List<CatalogoVersion> findByCatalogoOrderByFechaPublicacionAscIdAsc(String catalogo);

    CatalogoVersion save(CatalogoVersion version);
}
