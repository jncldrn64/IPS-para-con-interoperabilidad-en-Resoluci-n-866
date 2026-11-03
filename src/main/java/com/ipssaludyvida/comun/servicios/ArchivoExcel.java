package com.ipssaludyvida.comun.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Lee la primera hoja de un Excel (.xlsx) tal como lo exporta la fuente, como el de una tabla de
 * referencia de SISPRO, y la devuelve como filas de texto, la primera con los nombres de columna.
 * Desde ahí la carga sigue igual que con un CSV de RC-8 (docs/DECISIONS.md, 2026-11-02 "La carga
 * lee también el Excel que exporta la fuente").
 *
 * <p>Un .xlsx es un zip con XML adentro. Este lector usa solo Java: el libro dice cuál es la
 * primera hoja, los textos repetidos van en la tabla de textos compartidos, y cada celda dice su
 * columna, porque las celdas vacías no se escriben.
 */
final class ArchivoExcel {

    private static final String LIBRO = "xl/workbook.xml";
    private static final String RELACIONES = "xl/_rels/workbook.xml.rels";
    private static final String TEXTOS = "xl/sharedStrings.xml";

    private ArchivoExcel() {
    }

    static boolean esExcel(Path ruta) {
        return ruta.getFileName() != null
                && ruta.getFileName().toString().toLowerCase().endsWith(".xlsx");
    }

    static List<List<String>> filas(Path ruta) {
        try (ZipFile zip = new ZipFile(ruta.toFile())) {
            List<String> textos = zip.getEntry(TEXTOS) == null ? List.of() : textos(zip);
            return hoja(zip, primeraHoja(zip), textos);
        } catch (NoSuchFileException e) {
            throw new IllegalArgumentException("no existe el archivo " + ruta);
        } catch (ZipException e) {
            throw new IllegalArgumentException("no se pudo leer " + ruta
                    + " como Excel (.xlsx): no es un archivo de Excel 2007 o posterior");
        } catch (IOException | XMLStreamException e) {
            // Un archivo que no existe llega aquí como IOException en algunos sistemas.
            if (!ruta.toFile().exists()) {
                throw new IllegalArgumentException("no existe el archivo " + ruta);
            }
            throw new IllegalArgumentException("no se pudo leer el Excel " + ruta + ": "
                    + e.getMessage());
        }
    }

    // La primera hoja del libro, por su relación: su archivo no siempre se llama sheet1.xml.
    private static String primeraHoja(ZipFile zip) throws IOException, XMLStreamException {
        String id = null;
        XMLStreamReader libro = abrir(zip, LIBRO);
        while (libro.hasNext() && id == null) {
            if (libro.next() == XMLStreamConstants.START_ELEMENT
                    && libro.getLocalName().equals("sheet")) {
                id = atributo(libro, "id");
            }
        }
        if (id == null) {
            throw new IllegalArgumentException("el Excel no tiene hojas");
        }
        XMLStreamReader relaciones = abrir(zip, RELACIONES);
        while (relaciones.hasNext()) {
            if (relaciones.next() == XMLStreamConstants.START_ELEMENT
                    && relaciones.getLocalName().equals("Relationship")
                    && id.equals(relaciones.getAttributeValue(null, "Id"))) {
                String destino = relaciones.getAttributeValue(null, "Target");
                return destino.startsWith("/") ? destino.substring(1) : "xl/" + destino;
            }
        }
        throw new IllegalArgumentException("el Excel no dice dónde está su primera hoja");
    }

    private static List<String> textos(ZipFile zip) throws IOException, XMLStreamException {
        List<String> textos = new ArrayList<>();
        XMLStreamReader xml = abrir(zip, TEXTOS);
        StringBuilder texto = null;
        boolean fonetica = false;
        while (xml.hasNext()) {
            int evento = xml.next();
            String nombre = evento == XMLStreamConstants.START_ELEMENT
                    || evento == XMLStreamConstants.END_ELEMENT ? xml.getLocalName() : "";
            if (evento == XMLStreamConstants.START_ELEMENT && nombre.equals("si")) {
                texto = new StringBuilder();
            } else if (evento == XMLStreamConstants.START_ELEMENT && nombre.equals("rPh")) {
                // La guía de lectura fonética trae su propio <t>, que no es parte del valor.
                fonetica = true;
            } else if (evento == XMLStreamConstants.END_ELEMENT && nombre.equals("rPh")) {
                fonetica = false;
            } else if (evento == XMLStreamConstants.START_ELEMENT && texto != null && !fonetica
                    && nombre.equals("t")) {
                // Un texto con formato viene partido en varios <t>; se juntan en uno.
                texto.append(xml.getElementText());
            } else if (evento == XMLStreamConstants.END_ELEMENT && nombre.equals("si")) {
                textos.add(texto.toString());
                texto = null;
            }
        }
        return textos;
    }

    private static List<List<String>> hoja(ZipFile zip, String nombre, List<String> textos)
            throws IOException, XMLStreamException {
        List<List<String>> filas = new ArrayList<>();
        XMLStreamReader xml = abrir(zip, nombre);
        Map<Integer, String> fila = null;
        String tipo = null;
        int columna = 0;
        String valor = null;
        while (xml.hasNext()) {
            int evento = xml.next();
            if (evento == XMLStreamConstants.START_ELEMENT) {
                switch (xml.getLocalName()) {
                    case "row" -> fila = new HashMap<>();
                    case "c" -> {
                        tipo = atributo(xml, "t");
                        columna = columna(atributo(xml, "r"), fila.size());
                        valor = null;
                    }
                    case "v" -> valor = xml.getElementText();
                    // Un texto escrito en la celda misma, sin pasar por la tabla de textos.
                    case "t" -> valor = valor == null ? xml.getElementText()
                            : valor + xml.getElementText();
                    default -> {
                    }
                }
            } else if (evento == XMLStreamConstants.END_ELEMENT) {
                if (xml.getLocalName().equals("c") && valor != null) {
                    fila.put(columna, "s".equals(tipo) ? textos.get(Integer.parseInt(valor))
                            : valor);
                } else if (xml.getLocalName().equals("row")) {
                    filas.add(enOrden(fila));
                }
            }
        }
        return filas;
    }

    private static List<String> enOrden(Map<Integer, String> fila) {
        int ancho = fila.keySet().stream().mapToInt(Integer::intValue).max().orElse(-1) + 1;
        List<String> campos = new ArrayList<>(ancho);
        for (int i = 0; i < ancho; i++) {
            campos.add(fila.getOrDefault(i, "").trim());
        }
        return campos;
    }

    // "C12" es la columna 2, contando desde 0. Sin referencia, la celda va después de la anterior.
    private static int columna(String referencia, int siguiente) {
        if (referencia == null) {
            return siguiente;
        }
        int columna = 0;
        for (char c : referencia.toCharArray()) {
            if (!Character.isLetter(c)) {
                break;
            }
            columna = columna * 26 + (Character.toUpperCase(c) - 'A' + 1);
        }
        return columna - 1;
    }

    private static String atributo(XMLStreamReader xml, String nombre) {
        for (int i = 0; i < xml.getAttributeCount(); i++) {
            if (xml.getAttributeLocalName(i).equals(nombre)) {
                return xml.getAttributeValue(i);
            }
        }
        return null;
    }

    private static XMLStreamReader abrir(ZipFile zip, String nombre)
            throws IOException, XMLStreamException {
        ZipEntry entrada = zip.getEntry(nombre);
        if (entrada == null) {
            throw new IllegalArgumentException("al Excel le falta " + nombre);
        }
        InputStream datos = zip.getInputStream(entrada);
        XMLInputFactory fabrica = XMLInputFactory.newFactory();
        // Sin esto, un archivo armado a propósito puede hacer que el lector abra otros archivos del
        // equipo o direcciones de red por medio de una entidad externa.
        fabrica.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        fabrica.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        return fabrica.createXMLStreamReader(datos, "UTF-8");
    }
}
