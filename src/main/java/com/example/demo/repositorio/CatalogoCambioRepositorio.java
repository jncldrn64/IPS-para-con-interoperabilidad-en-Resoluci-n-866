package com.example.demo.repositorio;

import com.example.demo.modelo.CatalogoCambio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

/** La tabla de cambios de los catálogos. Solo agrega y busca: un cambio no se borra (RC-4). */
public interface CatalogoCambioRepositorio extends Repository<CatalogoCambio, Long> {

    List<CatalogoCambio> findByCatalogoAndCodigoOrderByIdAsc(String catalogo, String codigo);

    Optional<CatalogoCambio> findFirstByCatalogoAndCodigoAndTipoOrderByIdDesc(
            String catalogo, String codigo, CatalogoCambio.Tipo tipo);

    CatalogoCambio save(CatalogoCambio cambio);
}
