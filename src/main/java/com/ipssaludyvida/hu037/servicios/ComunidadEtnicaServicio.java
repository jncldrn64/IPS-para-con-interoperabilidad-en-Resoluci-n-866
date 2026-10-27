package com.ipssaludyvida.hu037.servicios;

import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.comun.servicios.RegistroCatalogos;
import com.ipssaludyvida.comun.servicios.ServicioCatalogo;
import com.ipssaludyvida.hu037.interfaces.ContratoComunidadesEtnicas;
import com.ipssaludyvida.hu037.modelos.ComunidadEtnica;
import com.ipssaludyvida.hu037.repositorios.ComunidadEtnicaRepositorio;
import jakarta.validation.Validator;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class ComunidadEtnicaServicio extends ServicioCatalogo<ComunidadEtnica>
        implements ContratoComunidadesEtnicas {

    public ComunidadEtnicaServicio(ComunidadEtnicaRepositorio datos, Validator validador,
            RegistroCatalogos registro) {
        super(ComunidadEtnica.class, datos, validador, registro);
    }

    @Override
    public Comprobacion comprobarContraElArchivo(Path archivo) {
        return comprobarContra(leerListaOficial(archivo));
    }
}
