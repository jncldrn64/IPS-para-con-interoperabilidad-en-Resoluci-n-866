package com.ipssaludyvida.hu034.servicios;

import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.comun.servicios.RegistroCatalogos;
import com.ipssaludyvida.comun.servicios.ServicioCatalogo;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu034.modelos.TipoDocumento;
import com.ipssaludyvida.hu034.repositorios.TipoDocumentoRepositorio;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class TipoDocumentoServicio extends ServicioCatalogo<TipoDocumento>
        implements ContratoTiposDocumento {

    public TipoDocumentoServicio(TipoDocumentoRepositorio datos,
            Validator validador, RegistroCatalogos registro) {
        super(TipoDocumento.class, datos, validador, registro);
    }

    // D-21: si el archivo no coincide con la hoja, se guarda el nombre de la hoja y se avisa
    // (escenario 1 de HU-034).
    @Override
    protected String nombreOficial(String codigo, String nombreDelArchivo, List<String> avisos) {
        String oficial = HOJA_13.get(codigo);
        if (oficial == null || oficial.equals(nombreDelArchivo)) {
            return nombreDelArchivo;
        }
        avisos.add("el código " + codigo + " trae \"" + nombreDelArchivo
                + "\"; se guarda el nombre de la hoja 13, \"" + oficial + "\"");
        return oficial;
    }

    @Override
    public Comprobacion comprobarContraLaHoja() {
        return comprobarContra(HOJA_13);
    }

    // La primera carga trae los 14 códigos de la hoja, ni uno más ni uno menos (escenario 2).
    @Override
    protected List<String> revisarPrimeraCarga(Set<String> codigosAceptados) {
        List<String> bloqueos = new ArrayList<>();
        HOJA_13.keySet().stream().filter(c -> !codigosAceptados.contains(c))
                .forEach(c -> bloqueos.add("falta el código " + c + " de la hoja 13"));
        codigosAceptados.stream().filter(c -> !HOJA_13.containsKey(c))
                .forEach(c -> bloqueos.add(
                        "sobra el código " + c + ", que la hoja 13 no tiene"));
        return bloqueos;
    }
}
