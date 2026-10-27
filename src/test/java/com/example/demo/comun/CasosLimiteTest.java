package com.example.demo.comun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.comun.interfaces.CrudCatalogo;
import com.example.demo.comun.modelos.Catalogo;
import com.example.demo.comun.modelos.CatalogoCambio;
import com.example.demo.comun.servicios.DatosVersion;
import com.example.demo.comun.servicios.ResultadoCarga;
import com.example.demo.hu036.interfaces.ContratoEtnias;
import com.example.demo.hu036.modelos.Etnia;
import com.example.demo.hu037.modelos.ComunidadEtnica;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    static final Path HOJA_15 = Path.of("src/test/resources/cargas/etnias-hoja-15.csv");
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
}
