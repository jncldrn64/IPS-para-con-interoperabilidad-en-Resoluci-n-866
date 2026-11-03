package com.ipssaludyvida.comun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.modelos.Catalogo;
import com.ipssaludyvida.comun.modelos.CatalogoCambio;
import com.ipssaludyvida.comun.servicios.DatosVersion;
import com.ipssaludyvida.comun.servicios.ResultadoCarga;
import com.ipssaludyvida.hu036.interfaces.ContratoEtnias;
import com.ipssaludyvida.hu036.modelos.Etnia;
import com.ipssaludyvida.hu037.modelos.ComunidadEtnica;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Los casos límite que pidió el autor el 2026-10-26: los 7 valores de etnia en las dos entidades,
 * por el menú y por el CSV, con y sin valores previos, y los archivos que una hoja de cálculo
 * guarda distinto. No son el escenario de una historia (docs/DESIGN.md, sección 3).
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CasosLimiteTest {

    static final String REFERENCIA = "Resolución 866 de 2021, anexo técnico, hoja 15";
    static final Path HOJA_15 = Path.of("data/catalogos/etnias-hoja-15.csv");
    static final DatosVersion RESOLUCION_866 = new DatosVersion(REFERENCIA, null,
            LocalDate.of(2026, 10, 18), "Resolución 866 de 2021", LocalDate.of(2021, 6, 25));

    @Autowired CrudCatalogo<Etnia> etnias;
    @Autowired CrudCatalogo<ComunidadEtnica> comunidades;
    @TempDir Path carpeta;

    @Test
    void lasSieteEtniasPorElMenuEnEtnias() {
        ContratoEtnias.HOJA_15.forEach((codigo, nombre) ->
                etnias.crear(new Etnia(codigo, nombre), REFERENCIA));
        assertEquals(7, etnias.listarActivos().size());
        assertTrue(etnias.versionVigente().isEmpty());
    }

    @Test
    void lasSieteEtniasPorElMenuEnComunidades() {
        ContratoEtnias.HOJA_15.forEach((codigo, nombre) ->
                assertThrows(ConstraintViolationException.class,
                        () -> comunidades.crear(new ComunidadEtnica(codigo, nombre), REFERENCIA)));
        assertTrue(comunidades.listar().isEmpty());
    }

    @Test
    void lasSieteEtniasPorCsvEnComunidades() {
        ResultadoCarga carga = comunidades.leerCarga(HOJA_15, RESOLUCION_866);
        assertEquals(7, carga.getRechazadas().size());
        assertTrue(carga.getRechazadas().stream().allMatch(r -> r.motivo().contains("3 dígitos")
                && r.motivo().contains("ceros iniciales")));
        assertEquals(List.of("ninguna fila se aceptó: no hay nada que guardar"), carga.getBloqueos());
        assertThrows(IllegalStateException.class, () -> comunidades.confirmarCarga(carga, Map.of()));
    }

    @Test
    void elCsvDespuesDeCrearLasMismasPorElMenu() {
        ContratoEtnias.HOJA_15.forEach((codigo, nombre) ->
                etnias.crear(new Etnia(codigo, nombre), REFERENCIA));
        ResultadoCarga carga = etnias.leerCarga(HOJA_15, RESOLUCION_866);
        assertEquals(ResultadoCarga.Tipo.VERSION_NUEVA, carga.getTipo());
        assertTrue(carga.getNuevos().isEmpty() && carga.getCambianDeNombre().isEmpty()
                && carga.getVuelven().isEmpty() && carga.getYaNoEstan().isEmpty());
        etnias.confirmarCarga(carga, Map.of());
        Long vigente = etnias.versionVigente().orElseThrow().getId();
        assertTrue(etnias.listar().stream().allMatch(e -> vigente.equals(e.getVersionId())));
        assertEquals(List.of(CatalogoCambio.Tipo.CREADO),
                etnias.historial("01").stream().map(CatalogoCambio::getTipo).toList());
    }

    @Test
    void elMismoCsvDosVecesEsUnaRecargaQueNoCambiaNada() {
        etnias.confirmarCarga(etnias.leerCarga(HOJA_15, RESOLUCION_866), Map.of());
        ResultadoCarga otraVez = etnias.leerCarga(HOJA_15, RESOLUCION_866);
        assertEquals(ResultadoCarga.Tipo.RECARGA, otraVez.getTipo());
        assertTrue(otraVez.getNuevos().isEmpty() && otraVez.getAvisos().isEmpty());
    }

    @Test
    void elMismoCsvConUnaFechaPosteriorEsUnaVersionSinCambios() {
        etnias.confirmarCarga(etnias.leerCarga(HOJA_15, RESOLUCION_866), Map.of());
        DatosVersion posterior = new DatosVersion(REFERENCIA, null, LocalDate.of(2026, 10, 18),
                "Resolución 866 de 2021, otra vez", LocalDate.of(2022, 1, 1));
        ResultadoCarga carga = etnias.leerCarga(HOJA_15, posterior);
        assertEquals(ResultadoCarga.Tipo.VERSION_NUEVA, carga.getTipo());
        assertTrue(carga.getNuevos().isEmpty() && carga.getYaNoEstan().isEmpty());
    }

    @Test
    void crearPorElMenuUnCodigoQueYaTrajoElCsv() {
        etnias.confirmarCarga(etnias.leerCarga(HOJA_15, RESOLUCION_866), Map.of());
        assertThrows(IllegalStateException.class,
                () -> etnias.crear(new Etnia("01", "Indígena"), REFERENCIA));
    }

    @Test
    void unCsvConLaMarcaQuePoneLaHojaDeCalculo() throws IOException {
        Path archivo = carpeta.resolve("con-marca.csv");
        Files.writeString(archivo, "\uFEFF" + Files.readString(HOJA_15), StandardCharsets.UTF_8);
        assertEquals(7, etnias.leerCarga(archivo, RESOLUCION_866).getAceptadas().size());
    }

    @Test
    void unCsvSeparadoPorComas() throws IOException {
        Path archivo = carpeta.resolve("comas.csv");
        Files.writeString(archivo, Files.readString(HOJA_15).replace(';', ','));
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> etnias.leerCarga(archivo, RESOLUCION_866));
        assertTrue(error.getMessage().contains("comas"), error.getMessage());
    }

    @Test
    void unCsvQueNoEstaEnUtf8() throws IOException {
        Path archivo = carpeta.resolve("latin1.csv");
        Files.writeString(archivo, Files.readString(HOJA_15), Charset.forName("ISO-8859-1"));
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> etnias.leerCarga(archivo, RESOLUCION_866));
        assertTrue(error.getMessage().contains("UTF-8"), error.getMessage());
    }

    @Test
    void encabezadosConMayusculasSinTildesYUnaColumnaDeMas() throws IOException {
        Path archivo = carpeta.resolve("encabezados.csv");
        Files.writeString(archivo, Files.readString(HOJA_15)
                .replace("código;nombre", "CODIGO;Nombre;Observaciones"));
        assertEquals(7, etnias.leerCarga(archivo, RESOLUCION_866).getAceptadas().size());
    }

    @Test
    void unCodigoQuePerdioSuCeroInicial() throws IOException {
        Path archivo = carpeta.resolve("sin-cero.csv");
        Files.writeString(archivo, Files.readString(HOJA_15).replace("01;Indígena", "1;Indígena"));
        ResultadoCarga carga = etnias.leerCarga(archivo, RESOLUCION_866);
        assertTrue(carga.getRechazadas().get(0).motivo().contains("ceros iniciales"));
        assertEquals(List.of("falta el código 01 de la hoja 15"), carga.getBloqueos());
    }

    @Test
    void unNombreEntreComillasConPuntoYComa() throws IOException {
        Path archivo = carpeta.resolve("comillas.csv");
        Files.writeString(archivo, "código;nombre\n101;\"Comunidad; con punto y coma\"\n");
        DatosVersion datos = new DatosVersion("Fuente de prueba", null, LocalDate.of(2026, 10, 18),
                "Versión de prueba", LocalDate.of(2025, 1, 1));
        comunidades.confirmarCarga(comunidades.leerCarga(archivo, datos), Map.of());
        assertEquals("Comunidad; con punto y coma",
                comunidades.buscarPorCodigo("101").map(Catalogo::getNombre).orElseThrow());
    }

    // Los Excel de estos tests se arman a mano, sin el prefijo x: ni la forma del de SISPRO, para
    // probar que el lector no depende de cómo lo escribió cada programa. El de SISPRO lo carga
    // HU018PaisesTest (docs/DECISIONS.md, 2026-11-02 "La carga lee también el Excel que exporta la
    // fuente").

    @Test
    void unExcelConElTextoEnLaCeldaYUnaCeldaVaciaEnMedio() {
        // El encabezado tiene tres columnas y los datos dejan vacía la del medio, que el Excel no
        // escribe: sin leer la referencia de cada celda, el nombre caería en la columna B.
        StringBuilder filas = new StringBuilder(fila(1, celda("A1", "código"),
                celda("B1", "observaciones"), celda("C1", "nombre")));
        int numero = 2;
        for (Map.Entry<String, String> etnia : ContratoEtnias.HOJA_15.entrySet()) {
            filas.append(fila(numero, celda("A" + numero, etnia.getKey()),
                    celda("C" + numero, etnia.getValue())));
            numero++;
        }
        ResultadoCarga carga = etnias.leerCarga(excel(filas.toString(), null), RESOLUCION_866);
        assertEquals(7, carga.getAceptadas().size());
        assertEquals("Indígena", carga.getAceptadas().get(0).nombre());
    }

    @Test
    void unExcelConElCodigoGuardadoComoNumero() {
        String filas = fila(1, celda("A1", "código"), celda("B1", "nombre"))
                + fila(2, "<c r=\"A2\"><v>1</v></c>", celda("B2", "Indígena"));
        ResultadoCarga carga = etnias.leerCarga(excel(filas, null), RESOLUCION_866);
        assertTrue(carga.getRechazadas().get(0).motivo().contains("ceros iniciales"),
                carga.getRechazadas().get(0).motivo());
    }

    @Test
    void unExcelConTextoConFormatoYGuiaFonetica() {
        String textos = "<sst><si><t>código</t></si><si><t>nombre</t></si><si><t>101</t></si>"
                + "<si><r><t>Comunidad </t></r><r><t>con formato</t></r>"
                + "<rPh><t>LECTURA</t></rPh></si></sst>";
        String filas = "<row r=\"1\"><c r=\"A1\" t=\"s\"><v>0</v></c>"
                + "<c r=\"B1\" t=\"s\"><v>1</v></c></row>"
                + "<row r=\"2\"><c r=\"A2\" t=\"s\"><v>2</v></c>"
                + "<c r=\"B2\" t=\"s\"><v>3</v></c></row>";
        ResultadoCarga carga = comunidades.leerCarga(excel(filas, textos), new DatosVersion(
                "Fuente de prueba", null, LocalDate.of(2026, 11, 2), "Versión de prueba",
                LocalDate.of(2025, 1, 1)));
        assertEquals("Comunidad con formato", carga.getAceptadas().get(0).nombre());
    }

    @Test
    void unExcelSinLaColumnaDelNombre() {
        String filas = fila(1, celda("A1", "código")) + fila(2, celda("A2", "01"));
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> etnias.leerCarga(excel(filas, null), RESOLUCION_866));
        assertTrue(error.getMessage().contains("columna nombre"), error.getMessage());
    }

    @Test
    void unArchivoXlsxQueNoEsUnExcel() throws IOException {
        Path archivo = Files.writeString(carpeta.resolve("falso.xlsx"), "código;nombre\n01;X\n");
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> etnias.leerCarga(archivo, RESOLUCION_866));
        assertTrue(error.getMessage().contains("como Excel"), error.getMessage());
    }

    private static String fila(int numero, String... celdas) {
        return "<row r=\"" + numero + "\">" + String.join("", celdas) + "</row>";
    }

    // Una celda con el texto escrito en ella misma, sin pasar por la tabla de textos.
    private static String celda(String referencia, String texto) {
        return "<c r=\"" + referencia + "\" t=\"inlineStr\"><is><t>" + texto + "</t></is></c>";
    }

    // Un .xlsx mínimo: el libro, su relación con la hoja, la hoja y, si hay, los textos.
    private Path excel(String filas, String textos) {
        Path archivo = carpeta.resolve("prueba-" + System.nanoTime() + ".xlsx");
        try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(archivo))) {
            entrada(zip, "xl/workbook.xml", "<workbook xmlns:r=\"r\"><sheets>"
                    + "<sheet name=\"Hoja1\" sheetId=\"1\" r:id=\"rId1\"/></sheets></workbook>");
            entrada(zip, "xl/_rels/workbook.xml.rels", "<Relationships><Relationship Id=\"rId1\""
                    + " Target=\"worksheets/hoja.xml\"/></Relationships>");
            entrada(zip, "xl/worksheets/hoja.xml",
                    "<worksheet><sheetData>" + filas + "</sheetData></worksheet>");
            if (textos != null) {
                entrada(zip, "xl/sharedStrings.xml", textos);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return archivo;
    }

    private static void entrada(ZipOutputStream zip, String nombre, String xml) throws IOException {
        zip.putNextEntry(new ZipEntry(nombre));
        zip.write(xml.getBytes(StandardCharsets.UTF_8));
        zip.closeEntry();
    }
}
