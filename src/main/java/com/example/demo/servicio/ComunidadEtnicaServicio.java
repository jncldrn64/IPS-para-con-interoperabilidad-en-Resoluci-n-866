package com.example.demo.servicio;

import com.example.demo.modelo.ComunidadEtnica;
import com.example.demo.repositorio.ComunidadEtnicaRepositorio;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class ComunidadEtnicaServicio extends ServicioCatalogo<ComunidadEtnica> {

    public ComunidadEtnicaServicio(ComunidadEtnicaRepositorio datos, Validator validador) {
        super(datos, validador);
    }
}
