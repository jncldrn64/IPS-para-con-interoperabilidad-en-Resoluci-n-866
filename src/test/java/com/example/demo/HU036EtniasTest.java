package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.modelo.Catalogo;
import com.example.demo.modelo.CatalogoCambio;
import com.example.demo.modelo.CatalogoVersion;
import com.example.demo.modelo.Etnia;
import com.example.demo.modelo.Motivo;
import com.example.demo.repositorio.EtniaRepositorio;
import com.example.demo.servicio.CrudCatalogo;
import com.example.demo.servicio.DatosVersion;
import com.example.demo.servicio.RegistroCatalogos;
import com.example.demo.servicio.ResultadoCarga;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Los escenarios de HU-036, en docs/context/trello/tarjetas/HU-036.md. Cada método se llama
 * esc<N>_<alternativa>, por el número del escenario y el caso de su "Dado" que prueba.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU036EtniasTest {

    static final String REFERENCIA = "Resolución de prueba";
    static final Path HOJA_15 = Path.of("src/test/resources/cargas/etnias-hoja-15.csv");
    static final DatosVersion RESOLUCION_866 = new DatosVersion(
            "Resolución 866 de 2021, anexo técnico, numeral 13, hoja 15", "https://www.minsalud.gov.co",
            LocalDate.of(2026, 10, 18), "Resolución 866 de 2021", LocalDate.of(2021, 6, 25));
    static final DatosVersion VERSION_2022 = new DatosVersion("Resolución de prueba", null,
            LocalDate.of(2026, 10, 18), "Versión de prueba 2022", LocalDate.of(2022, 1, 1));

    @TempDir Path carpeta;

    @Autowired CrudCatalogo<Etnia> etnias;
    @Autowired EtniaRepositorio repositorio;
    @Autowired RegistroCatalogos registro;

    @Test
    @DisplayName("HU-036 Esc. 8: un cambio oficial de código inactiva el viejo y crea el nuevo")
    void esc8_cambioOficialDeCodigo() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        etnias.cambiarCodigo("07", new Etnia("08", "Valor nuevo"), REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("07"));
        assertEquals(Catalogo.Estado.ACTIVO, estado("08"));
        CatalogoCambio ultimo = ultimoCambio("07");
        assertEquals(Motivo.REEMPLAZADO_POR, ultimo.getMotivo());
        assertEquals("08", ultimo.getCodigoReemplazo());
        assertEquals(List.of("08"), activos());
    }

    @Test
    @DisplayName("HU-036 Esc. 9: un valor creado por cambio oficial queda activo con su referencia")
    void esc9_creacionPorCambioOficial() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        assertEquals(Catalogo.Estado.ACTIVO, estado("07"));
        CatalogoCambio creado = etnias.historial("07").get(0);
        assertEquals(CatalogoCambio.Tipo.CREADO, creado.getTipo());
        assertEquals(REFERENCIA, creado.getReferenciaOficial());
    }

    @Test
    @DisplayName("HU-036 Esc. 10: sin la referencia oficial no se crea ni se cambia nada")
    void esc10_faltaLaReferenciaOficial() {
        assertThrows(IllegalArgumentException.class,
                () -> etnias.crear(new Etnia("07", "Etnia de prueba"), " "));
        assertTrue(etnias.buscarPorCodigo("07").isEmpty());
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> etnias.cambiarNombre("07", "Otro nombre", null));
        assertEquals("Etnia de prueba", etnias.buscarPorCodigo("07").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-036 Esc. 10: un código que ya existe no se vuelve a crear")
    void esc10_elCodigoQueSeCreaYaExiste() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        assertThrows(IllegalStateException.class,
                () -> etnias.crear(new Etnia("07", "Otro nombre"), REFERENCIA));
        assertEquals("Etnia de prueba", etnias.buscarPorCodigo("07").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-036 Esc. 10: un código que no cumple el numeral 13 no se guarda")
    void esc10_datoQueNoCumpleSuNumeral() {
        for (String codigo : new String[] {"1", "123", "123456", "0A", null}) {
            assertThrows(ConstraintViolationException.class,
                    () -> etnias.crear(new Etnia(codigo, "X"), REFERENCIA),
                    "aceptó el código " + codigo);
        }
        assertTrue(etnias.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-036 Esc. 10: un nombre vacío no se guarda")
    void esc10_nombreVacio() {
        assertThrows(ConstraintViolationException.class,
                () -> etnias.crear(new Etnia("07", " "), REFERENCIA));
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        assertThrows(ConstraintViolationException.class,
                () -> etnias.cambiarNombre("07", " ", REFERENCIA));
        assertEquals("Etnia de prueba", etnias.buscarPorCodigo("07").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-036 Esc. 11: un cambio oficial de nombre deja el anterior en el historial")
    void esc11_cambioOficialDeNombre() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        etnias.cambiarNombre("07", "Nombre nuevo", REFERENCIA);
        assertEquals("Nombre nuevo", etnias.buscarPorCodigo("07").orElseThrow().getNombre());
        CatalogoCambio cambio = ultimoCambio("07");
        assertEquals(CatalogoCambio.Tipo.NOMBRE, cambio.getTipo());
        assertEquals("Etnia de prueba", cambio.getValorAnterior());
        assertEquals("Nombre nuevo", cambio.getValorNuevo());
    }

    @Test
    @DisplayName("HU-036 Esc. 19: inactivar con motivo deja de ofrecer el valor y lo conserva")
    void esc19_inactivacionConMotivo() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        assertEquals(0, etnias.contarUsos("07"));
        etnias.inactivar("07", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("07"));
        assertTrue(activos().isEmpty());
        assertEquals(Motivo.RETIRADO_POR_LA_FUENTE, ultimoCambio("07").getMotivo());
    }

    @Test
    @DisplayName("HU-036 Esc. 20: sin motivo no se inactiva")
    void esc20_sinMotivo() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> etnias.inactivar("07", null, REFERENCIA));
        assertEquals(Catalogo.Estado.ACTIVO, estado("07"));
    }

    @Test
    @DisplayName("HU-036 Esc. 20: \"Retirado por la fuente oficial\" sin referencia no inactiva")
    void esc20_retiradoSinReferencia() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> etnias.inactivar("07", Motivo.RETIRADO_POR_LA_FUENTE, ""));
        assertEquals(Catalogo.Estado.ACTIVO, estado("07"));
    }

    @Test
    @DisplayName("HU-036 Esc. 20: \"Creado por error\" no vale para un código de la versión vigente")
    void esc20_creadoPorErrorEnLaVersionVigente() {
        enLaVersionVigente("07");
        assertThrows(IllegalArgumentException.class,
                () -> etnias.inactivar("07", Motivo.CREADO_POR_ERROR, null));
        assertEquals(Catalogo.Estado.ACTIVO, estado("07"));
    }

    @Test
    @DisplayName("HU-036 Esc. 21: se reactiva diciendo por qué la referencia no lo retiraba")
    void esc21_reactivacion() {
        enLaVersionVigente("07");
        etnias.inactivar("07", Motivo.RETIRADO_POR_LA_FUENTE, "Referencia que no lo retiraba");
        etnias.reactivar("07", null, "La referencia retiraba otro código");
        assertEquals(Catalogo.Estado.ACTIVO, estado("07"));
        assertEquals(List.of("07"), activos());
        assertEquals("La referencia retiraba otro código", ultimoCambio("07").getJustificacion());
    }

    @Test
    @DisplayName("HU-036 Esc. 22: no se reactiva lo que no está en la versión vigente sin otra referencia")
    void esc22_fueraDeLaVersionVigente() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        // Una versión cargada después que ya no trae el código.
        version(LocalDateTime.now().plusMinutes(1));
        etnias.inactivar("07", Motivo.CREADO_POR_ERROR, null);
        assertThrows(IllegalArgumentException.class,
                () -> etnias.reactivar("07", null, "Se necesita"));
        assertEquals(Catalogo.Estado.INACTIVO, estado("07"));
    }

    @Test
    @DisplayName("HU-036 Esc. 22: inactivado con referencia, no se reactiva sin otra ni sin decir por qué")
    void esc22_sinOtraReferenciaNiPorQue() {
        enLaVersionVigente("07");
        etnias.inactivar("07", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertThrows(IllegalArgumentException.class, () -> etnias.reactivar("07", "", " "));
        assertEquals(Catalogo.Estado.INACTIVO, estado("07"));
    }

    @Test
    @DisplayName("HU-036 Esc. 23: el historial lista cada cambio con su motivo, su referencia y la hora")
    void esc23_historial() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        etnias.cambiarNombre("07", "Nombre nuevo", REFERENCIA);
        etnias.inactivar("07", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        List<CatalogoCambio> historial = etnias.historial("07");
        assertEquals(List.of(CatalogoCambio.Tipo.CREADO, CatalogoCambio.Tipo.NOMBRE,
                CatalogoCambio.Tipo.INACTIVADO), historial.stream().map(CatalogoCambio::getTipo).toList());
        assertFalse(historial.stream().anyMatch(c -> c.getFechaHora() == null));
        assertTrue(historial.stream().allMatch(c -> REFERENCIA.equals(c.getReferenciaOficial())));
    }

    @Test
    @DisplayName("HU-036 Esc. 1: la primera carga crea las 7 etnias activas, con su versión")
    void esc1_primeraCarga() {
        ResultadoCarga carga = etnias.leerCarga(HOJA_15, RESOLUCION_866);
        assertEquals(ResultadoCarga.Tipo.PRIMERA, carga.getTipo());
        assertTrue(carga.sePuedeGuardar());
        CatalogoVersion version = etnias.confirmarCarga(carga, Map.of());
        assertEquals(List.of("01", "02", "03", "04", "05", "06", "99"), activos());
        assertEquals(version.getId(), etnias.buscarPorCodigo("99").orElseThrow().getVersionId());
        assertEquals(version.getId(), etnias.versionVigente().orElseThrow().getId());
        assertEquals(LocalDate.of(2021, 6, 25), version.getFechaPublicacion());
    }

    @Test
    @DisplayName("HU-036 Esc. 1: un nombre distinto del de la hoja se guarda como en la hoja, con aviso")
    void esc1_nombreDistintoDelDeLaHoja() throws IOException {
        Path archivo = csv(Files.readString(HOJA_15).replace("05;Negro(a)", "05;Negro"));
        ResultadoCarga carga = etnias.leerCarga(archivo, RESOLUCION_866);
        assertEquals(1, carga.getAvisos().size());
        etnias.confirmarCarga(carga, Map.of());
        assertEquals("Negro(a)", etnias.buscarPorCodigo("05").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-036 Esc. 2: si falta un código de la hoja 15, la carga no se puede guardar")
    void esc2_faltaUnCodigoDeLaHoja() throws IOException {
        Path archivo = csv(Files.readString(HOJA_15).replace("03;Raizal (San Andrés y Providencia)\n", ""));
        ResultadoCarga carga = etnias.leerCarga(archivo, RESOLUCION_866);
        assertEquals(List.of("falta el código 03 de la hoja 15"), carga.getBloqueos());
        assertThrows(IllegalStateException.class, () -> etnias.confirmarCarga(carga, Map.of()));
        assertTrue(etnias.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-036 Esc. 2: si sobra un código que la hoja 15 no tiene, la carga no se puede guardar")
    void esc2_sobraUnCodigo() throws IOException {
        Path archivo = csv(Files.readString(HOJA_15) + "07;Etnia que la hoja no tiene\n");
        ResultadoCarga carga = etnias.leerCarga(archivo, RESOLUCION_866);
        assertEquals(List.of("sobra el código 07, que la hoja 15 no tiene"), carga.getBloqueos());
        assertTrue(etnias.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-036 Esc. 3: una fila con un código de 3 caracteres se rechaza y las demás se guardan")
    void esc3_filasConErrores() throws IOException {
        Path archivo = csv(Files.readString(HOJA_15) + "100;Valor de 3 caracteres\n");
        ResultadoCarga carga = etnias.leerCarga(archivo, RESOLUCION_866);
        assertEquals(8, carga.getFilasLeidas());
        assertEquals(7, carga.getAceptadas().size());
        ResultadoCarga.Rechazo rechazo = carga.getRechazadas().get(0);
        assertEquals(8, rechazo.numero());
        assertTrue(rechazo.motivo().contains("2 dígitos"), rechazo.motivo());
        Path lista = etnias.escribirRechazadas(carga, carpeta.resolve("rechazadas.csv"));
        assertTrue(Files.exists(lista));
        etnias.confirmarCarga(carga, Map.of());
        assertEquals(7, activos().size());
    }

    @Test
    @DisplayName("HU-036 Esc. 4: un archivo sin la columna del nombre se rechaza completo")
    void esc4_faltaLaColumnaDelNombre() {
        Path archivo = csv("código;descripción\n01;Indígena\n");
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> etnias.leerCarga(archivo, RESOLUCION_866));
        assertTrue(error.getMessage().contains("columna nombre"), error.getMessage());
    }

    @Test
    @DisplayName("HU-036 Esc. 4: una fecha de publicación futura rechaza el archivo completo")
    void esc4_fechaFutura() {
        DatosVersion futura = new DatosVersion("Fuente", null, LocalDate.of(2026, 10, 18),
                "Versión futura", LocalDate.now().plusDays(1));
        assertThrows(IllegalArgumentException.class, () -> etnias.leerCarga(HOJA_15, futura));
    }

    @Test
    @DisplayName("HU-036 Esc. 4: una fecha anterior a la vigente se rechaza y dice cuál es la vigente")
    void esc4_fechaAnteriorALaVigente() {
        etnias.confirmarCarga(etnias.leerCarga(HOJA_15, RESOLUCION_866), Map.of());
        DatosVersion anterior = new DatosVersion("Fuente", null, LocalDate.of(2026, 10, 18),
                "Versión vieja", LocalDate.of(2020, 1, 1));
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> etnias.leerCarga(HOJA_15, anterior));
        assertTrue(error.getMessage().contains("Resolución 866 de 2021"), error.getMessage());
    }

    @Test
    @DisplayName("HU-036 Esc. 5: dos filas con el mismo código se rechazan las dos")
    void esc5_codigoRepetido() throws IOException {
        Path archivo = csv(Files.readString(HOJA_15) + "01;Indígena otra vez\n");
        ResultadoCarga carga = etnias.leerCarga(archivo, RESOLUCION_866);
        assertEquals(2, carga.getRechazadas().size());
        assertTrue(carga.getRechazadas().stream().allMatch(r -> r.motivo().equals("código repetido")));
    }

    @Test
    @DisplayName("HU-036 Esc. 6: una versión nueva crea, reactiva e inactiva, y avisa lo que deshace")
    void esc6_versionNueva() throws IOException {
        etnias.confirmarCarga(etnias.leerCarga(HOJA_15, RESOLUCION_866), Map.of());
        etnias.inactivar("03", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        Path archivo = csv(Files.readString(HOJA_15).replace("02;ROM (Gitanos)\n", "")
                + "07;Etnia nueva\n");
        ResultadoCarga carga = etnias.leerCarga(archivo, VERSION_2022);
        assertEquals(ResultadoCarga.Tipo.VERSION_NUEVA, carga.getTipo());
        assertEquals(List.of("07"), carga.getNuevos());
        assertEquals(List.of("03"), carga.getVuelven());
        assertEquals(List.of("02"), carga.getYaNoEstan());
        assertEquals(1, carga.getAvisos().size(), "la versión deshace la inactivación de 03");
        CatalogoVersion version = etnias.confirmarCarga(carga, Map.of("02", "07"));
        assertEquals(version.getId(), etnias.versionVigente().orElseThrow().getId());
        assertEquals(Catalogo.Estado.ACTIVO, estado("07"));
        assertEquals(Motivo.VUELVE_EN_LA_VERSION, ultimoCambio("03").getMotivo());
        assertEquals(Motivo.REEMPLAZADO_POR, ultimoCambio("02").getMotivo());
        assertEquals("07", ultimoCambio("02").getCodigoReemplazo());
    }

    @Test
    @DisplayName("HU-036 Esc. 7: una fila rechazada de un código existente lo deja como estaba")
    void esc7_versionNuevaConFilasRechazadas() throws IOException {
        etnias.confirmarCarga(etnias.leerCarga(HOJA_15, RESOLUCION_866), Map.of());
        Path archivo = csv(Files.readString(HOJA_15).replace("02;ROM (Gitanos)", "02;"));
        ResultadoCarga carga = etnias.leerCarga(archivo, VERSION_2022);
        assertEquals("02", carga.getRechazadas().get(0).codigo());
        assertTrue(carga.getYaNoEstan().isEmpty());
        CatalogoVersion version = etnias.confirmarCarga(carga, Map.of());
        Etnia rom = etnias.buscarPorCodigo("02").orElseThrow();
        assertEquals("ROM (Gitanos)", rom.getNombre());
        assertEquals(Catalogo.Estado.ACTIVO, rom.getEstado());
        assertEquals(version.getId(), rom.getVersionId());
    }

    @Test
    @DisplayName("HU-036 Esc. 12: leer una carga y no confirmarla deja el catálogo igual")
    void esc12_cancelarLaCarga() {
        etnias.leerCarga(HOJA_15, RESOLUCION_866);
        assertTrue(etnias.listar().isEmpty());
        assertTrue(etnias.versionVigente().isEmpty());
    }

    private Path csv(String contenido) {
        try {
            return Files.writeString(Files.createTempFile(carpeta, "carga", ".csv"), contenido,
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Catalogo.Estado estado(String codigo) {
        return etnias.buscarPorCodigo(codigo).orElseThrow().getEstado();
    }

    private List<String> activos() {
        return etnias.listarActivos().stream().map(Catalogo::getCodigo).toList();
    }

    private CatalogoCambio ultimoCambio(String codigo) {
        List<CatalogoCambio> historial = etnias.historial(codigo);
        return historial.get(historial.size() - 1);
    }

    private CatalogoVersion version(LocalDateTime cargadoEn) {
        CatalogoVersion version = new CatalogoVersion();
        version.setCatalogo("Etnia");
        version.setVersion("Versión de prueba");
        version.setFechaPublicacion(LocalDate.of(2021, 6, 25));
        version.setCargadoEn(cargadoEn);
        return registro.guardar(version);
    }

    // Como si el código hubiera llegado en la carga de la versión vigente; la carga desde el CSV es
    // la pieza siguiente de la historia.
    private void enLaVersionVigente(String codigo) {
        Etnia valor = new Etnia(codigo, "Etnia de prueba");
        valor.setVersionId(version(LocalDateTime.of(2026, 1, 1, 0, 0)).getId());
        repositorio.save(valor);
    }
}
