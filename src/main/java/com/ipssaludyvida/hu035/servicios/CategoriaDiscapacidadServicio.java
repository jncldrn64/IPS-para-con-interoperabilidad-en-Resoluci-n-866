package com.ipssaludyvida.hu035.servicios;

import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.comun.servicios.RegistroCatalogos;
import com.ipssaludyvida.comun.servicios.ServicioCatalogo;
import com.ipssaludyvida.hu035.interfaces.ContratoCategoriasDiscapacidad;
import com.ipssaludyvida.hu035.modelos.CategoriaDiscapacidad;
import com.ipssaludyvida.hu035.repositorios.CategoriaDiscapacidadRepositorio;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CategoriaDiscapacidadServicio extends ServicioCatalogo<CategoriaDiscapacidad>
        implements ContratoCategoriasDiscapacidad {

    public CategoriaDiscapacidadServicio(CategoriaDiscapacidadRepositorio datos,
            Validator validador, RegistroCatalogos registro) {
        super(CategoriaDiscapacidad.class, datos, validador, registro);
    }

    // D-21: si el archivo no coincide con la hoja, se guarda el nombre de la hoja y se avisa
    // (escenario 1 de HU-035).
    @Override
    protected String nombreOficial(String codigo, String nombreDelArchivo, List<String> avisos) {
        String oficial = HOJAS_14_Y_15.get(codigo);
        if (oficial == null || oficial.equals(nombreDelArchivo)) {
            return nombreDelArchivo;
        }
        avisos.add("el código " + codigo + " trae \"" + nombreDelArchivo
                + "\"; se guarda el nombre de las hojas 14 y 15, \"" + oficial + "\"");
        return oficial;
    }

    @Override
    public Comprobacion comprobarContraLaHoja() {
        return comprobarContra(HOJAS_14_Y_15);
    }

    // La primera carga trae los 8 códigos de la hoja, ni uno más ni uno menos (escenario 2).
    @Override
    protected List<String> revisarPrimeraCarga(Set<String> codigosAceptados) {
        List<String> bloqueos = new ArrayList<>();
        HOJAS_14_Y_15.keySet().stream().filter(c -> !codigosAceptados.contains(c))
                .forEach(c -> bloqueos.add("falta el código " + c + " de las hojas 14 y 15"));
        codigosAceptados.stream().filter(c -> !HOJAS_14_Y_15.containsKey(c))
                .forEach(c -> bloqueos.add(
                        "sobra el código " + c + ", que las hojas 14 y 15 no tienen"));
        return bloqueos;
    }
}
