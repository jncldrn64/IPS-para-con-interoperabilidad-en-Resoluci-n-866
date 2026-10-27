package com.example.demo.servicio;

import com.example.demo.modelo.CatalogoCambio;
import com.example.demo.modelo.CatalogoVersion;
import com.example.demo.repositorio.CatalogoCambioRepositorio;
import com.example.demo.repositorio.CatalogoVersionRepositorio;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** Las versiones y la tabla de cambios que comparten los catálogos (RC-8 a RC-11). */
@Service
public class RegistroCatalogos {

    private final CatalogoVersionRepositorio versiones;
    private final CatalogoCambioRepositorio cambios;

    public RegistroCatalogos(CatalogoVersionRepositorio versiones,
            CatalogoCambioRepositorio cambios) {
        this.versiones = versiones;
        this.cambios = cambios;
    }

    /** La versión de fecha de publicación más reciente, si el catálogo ya se cargó. */
    public Optional<CatalogoVersion> versionVigente(String catalogo) {
        return versiones.findFirstByCatalogoOrderByFechaPublicacionDescIdDesc(catalogo);
    }

    public List<CatalogoVersion> versiones(String catalogo) {
        return versiones.findByCatalogoOrderByFechaPublicacionAscIdAsc(catalogo);
    }

    public CatalogoVersion guardar(CatalogoVersion version) {
        return versiones.save(version);
    }

    public CatalogoCambio anotar(CatalogoCambio cambio) {
        cambio.setFechaHora(LocalDateTime.now());
        return cambios.save(cambio);
    }

    public List<CatalogoCambio> historial(String catalogo, String codigo) {
        return cambios.findByCatalogoAndCodigoOrderByIdAsc(catalogo, codigo);
    }

    public Optional<CatalogoCambio> ultimo(String catalogo, String codigo,
            CatalogoCambio.Tipo tipo) {
        return cambios.findFirstByCatalogoAndCodigoAndTipoOrderByIdDesc(catalogo, codigo, tipo);
    }

    /**
     * Si el código lo creó un cambio oficial anotado después de cargar la versión vigente. Un
     * código creado en una carga no tiene referencia oficial en su cambio, y no cuenta.
     */
    public boolean creadoPorCambioPosterior(String catalogo, String codigo) {
        Optional<CatalogoVersion> vigente = versionVigente(catalogo);
        return ultimo(catalogo, codigo, CatalogoCambio.Tipo.CREADO)
                .filter(c -> c.getReferenciaOficial() != null)
                .filter(c -> vigente.isEmpty()
                        || c.getFechaHora().isAfter(vigente.get().getCargadoEn()))
                .isPresent();
    }
}
