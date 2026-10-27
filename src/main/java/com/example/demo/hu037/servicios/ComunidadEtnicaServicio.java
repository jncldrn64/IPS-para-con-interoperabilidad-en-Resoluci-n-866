package com.example.demo.hu037.servicios;

import com.example.demo.comun.servicios.RegistroCatalogos;
import com.example.demo.comun.servicios.ServicioCatalogo;
import com.example.demo.hu037.interfaces.ContratoComunidadesEtnicas;
import com.example.demo.hu037.modelos.ComunidadEtnica;
import com.example.demo.hu037.repositorios.ComunidadEtnicaRepositorio;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class ComunidadEtnicaServicio extends ServicioCatalogo<ComunidadEtnica>
        implements ContratoComunidadesEtnicas {

    public ComunidadEtnicaServicio(ComunidadEtnicaRepositorio datos, Validator validador,
            RegistroCatalogos registro) {
        super(ComunidadEtnica.class, datos, validador, registro);
    }
}
