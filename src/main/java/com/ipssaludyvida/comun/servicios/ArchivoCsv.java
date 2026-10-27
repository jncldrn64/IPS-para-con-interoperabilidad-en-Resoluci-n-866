package com.ipssaludyvida.comun.servicios;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Lee el CSV de RC-8: UTF-8, separado por punto y coma, con los nombres de columna arriba. */
final class ArchivoCsv {

    private ArchivoCsv() {
    }

    static List<String> lineas(Path ruta) {
        try {
            List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
            // La hoja de cálculo pone una marca invisible al comienzo del "CSV UTF-8"; sin
            // quitarla, la primera columna no se llama "código" y el archivo se rechaza completo.
            if (!lineas.isEmpty() && lineas.get(0).startsWith("\uFEFF")) {
                lineas.set(0, lineas.get(0).substring(1));
            }
            return lineas;
        } catch (MalformedInputException e) {
            throw new IllegalArgumentException("el archivo no está en UTF-8: guárdelo desde la hoja"
                    + " de cálculo como \"CSV UTF-8\"");
        } catch (NoSuchFileException e) {
            throw new IllegalArgumentException("no existe el archivo " + ruta);
        } catch (IOException e) {
            throw new IllegalArgumentException("no se pudo leer " + ruta + ": " + e.getMessage());
        }
    }

    /** Parte una línea por punto y coma, respetando los campos entre comillas dobles. */
    static List<String> campos(String linea) {
        List<String> campos = new ArrayList<>();
        StringBuilder campo = new StringBuilder();
        boolean entreComillas = false;
        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);
            if (c == '"') {
                if (entreComillas && i + 1 < linea.length() && linea.charAt(i + 1) == '"') {
                    campo.append('"');
                    i++;
                } else {
                    entreComillas = !entreComillas;
                }
            } else if (c == ';' && !entreComillas) {
                campos.add(campo.toString().trim());
                campo.setLength(0);
            } else {
                campo.append(c);
            }
        }
        campos.add(campo.toString().trim());
        return campos;
    }

    /** El nombre de una columna sin mayúsculas ni tildes, para buscarla (RC-8). */
    static String normalizar(String texto) {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toLowerCase(Locale.ROOT);
    }
}
