package com.example.demo.servicio;

import com.example.demo.modelo.Etnia;
import com.example.demo.repositorio.EtniaRepositorio;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class EtniaServicio extends ServicioCatalogo<Etnia> {

    public EtniaServicio(EtniaRepositorio datos, Validator validador) {
        super(datos, validador);
    }
}
