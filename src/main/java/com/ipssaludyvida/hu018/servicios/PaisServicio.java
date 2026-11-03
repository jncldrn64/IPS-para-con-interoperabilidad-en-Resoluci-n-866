package com.ipssaludyvida.hu018.servicios;

import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.comun.servicios.RegistroCatalogos;
import com.ipssaludyvida.comun.servicios.ServicioCatalogo;
import com.ipssaludyvida.hu018.interfaces.ContratoPaises;
import com.ipssaludyvida.hu018.modelos.Pais;
import com.ipssaludyvida.hu018.repositorios.PaisRepositorio;
import jakarta.validation.Validator;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class PaisServicio extends ServicioCatalogo<Pais> implements ContratoPaises {

    public PaisServicio(PaisRepositorio datos, Validator validador, RegistroCatalogos registro) {
        super(Pais.class, datos, validador, registro);
    }

    @Override
    public Comprobacion comprobarContraElArchivo(Path archivo) {
        return comprobarContra(leerListaOficial(archivo));
    }
}
