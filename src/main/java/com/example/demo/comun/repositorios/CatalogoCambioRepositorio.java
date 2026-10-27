package com.example.demo.comun.repositorios;

import com.example.demo.comun.modelos.CatalogoCambio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

/** La tabla de cambios de los catálogos. Solo agrega y busca: un cambio no se borra (RC-4). */
public interface CatalogoCambioRepositorio extends Repository<CatalogoCambio, Long> {

    List<CatalogoCambio> findByCatalogoAndCodigoOrderByIdAsc(String catalogo, String codigo);

    Optional<CatalogoCambio> findFirstByCatalogoAndCodigoAndTipoOrderByIdDesc(
            String catalogo, String codigo, CatalogoCambio.Tipo tipo);

    long countByCatalogoAndVersionIdAndTipo(String catalogo, Long versionId,
            CatalogoCambio.Tipo tipo);

    CatalogoCambio save(CatalogoCambio cambio);
}
