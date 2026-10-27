package com.example.demo.hu036.servicios;

import com.example.demo.comun.servicios.RegistroCatalogos;
import com.example.demo.comun.servicios.ServicioCatalogo;
import com.example.demo.hu036.interfaces.ContratoEtnias;
import com.example.demo.hu036.modelos.Etnia;
import com.example.demo.hu036.repositorios.EtniaRepositorio;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class EtniaServicio extends ServicioCatalogo<Etnia> implements ContratoEtnias {

    public EtniaServicio(EtniaRepositorio datos, Validator validador, RegistroCatalogos registro) {
        super(Etnia.class, datos, validador, registro);
    }

    // D-21: si el archivo no coincide con la hoja, se guarda el nombre de la hoja y se avisa
    // (escenario 1 de HU-036).
    @Override
    protected String nombreOficial(String codigo, String nombreDelArchivo, List<String> avisos) {
        String oficial = HOJA_15.get(codigo);
        if (oficial == null || oficial.equals(nombreDelArchivo)) {
            return nombreDelArchivo;
        }
        avisos.add("el código " + codigo + " trae \"" + nombreDelArchivo
                + "\"; se guarda el nombre de la hoja 15, \"" + oficial + "\"");
        return oficial;
    }

    // La primera carga trae los 7 códigos de la hoja, ni uno más ni uno menos (escenario 2).
    @Override
    protected List<String> revisarPrimeraCarga(Set<String> codigosAceptados) {
        List<String> bloqueos = new ArrayList<>();
        HOJA_15.keySet().stream().filter(c -> !codigosAceptados.contains(c))
                .forEach(c -> bloqueos.add("falta el código " + c + " de la hoja 15"));
        codigosAceptados.stream().filter(c -> !HOJA_15.containsKey(c))
                .forEach(c -> bloqueos.add("sobra el código " + c + ", que la hoja 15 no tiene"));
        return bloqueos;
    }

}
