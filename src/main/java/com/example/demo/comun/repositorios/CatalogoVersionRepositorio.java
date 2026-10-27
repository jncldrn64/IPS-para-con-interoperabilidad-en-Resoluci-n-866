package com.example.demo.comun.repositorios;

import com.example.demo.comun.modelos.CatalogoVersion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

/** Las versiones cargadas de cada catálogo. Como los catálogos, no borra. */
public interface CatalogoVersionRepositorio extends Repository<CatalogoVersion, Long> {

    Optional<CatalogoVersion> findFirstByCatalogoOrderByFechaPublicacionDescIdDesc(String catalogo);

    List<CatalogoVersion> findByCatalogoOrderByFechaPublicacionAscIdAsc(String catalogo);

    CatalogoVersion save(CatalogoVersion version);
}
