package com.example.demo.servicio;

import com.example.demo.modelo.Etnia;
import com.example.demo.repositorio.EtniaRepositorio;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class EtniaServicio extends ServicioCatalogo<Etnia> {

    /**
     * Resolución 866 de 2021, anexo técnico, numeral 13, componente etnia, hoja 15. Se copia
     * citando la norma (CLAUDE.md, sección 10). Si no coincide con la hoja del PDF, manda la hoja.
     */
    public static final Map<String, String> HOJA_15 = hoja15();

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

    private static Map<String, String> hoja15() {
        Map<String, String> hoja = new LinkedHashMap<>();
        hoja.put("01", "Indígena");
        hoja.put("02", "ROM (Gitanos)");
        hoja.put("03", "Raizal (San Andrés y Providencia)");
        hoja.put("04", "Palenquero de San Basilio de Palenque");
        hoja.put("05", "Negro(a)");
        hoja.put("06", "Afrocolombiano(a)");
        hoja.put("99", "Ninguna de las anteriores");
        return Collections.unmodifiableMap(hoja);
    }
}
