package com.example.demo.hu037;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.comun.interfaces.CrudCatalogo;
import com.example.demo.comun.menu.MenuPrincipal;
import com.example.demo.comun.modelos.Catalogo;
import com.example.demo.comun.modelos.CatalogoCambio;
import com.example.demo.comun.modelos.CatalogoVersion;
import com.example.demo.comun.modelos.Motivo;
import com.example.demo.comun.servicios.Comprobacion;
import com.example.demo.comun.servicios.Consulta;
import com.example.demo.comun.servicios.DatosVersion;
import com.example.demo.comun.servicios.RegistroCatalogos;
import com.example.demo.comun.servicios.ResultadoCarga;
import com.example.demo.hu036.interfaces.ContratoEtnias;
import com.example.demo.hu037.interfaces.ContratoComunidadesEtnicas;
import com.example.demo.hu037.modelos.ComunidadEtnica;
import com.example.demo.hu037.repositorios.ComunidadEtnicaRepositorio;
import jakarta.validation.ConstraintViolationException;
import java.io.ByteArrayInputStream;
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
 * Los escenarios de HU-037, en docs/context/trello/tarjetas/HU-037.md. Cada método se llama
 * esc<N>_<alternativa>, por el número del escenario y el caso de su "Dado" que prueba.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU037ComunidadesEtnicasTest {

    static final String REFERENCIA = "Resolución de prueba";
    // Valores inventados: el catálogo oficial de comunidades étnicas no se ha encontrado (D-19).
    static final String TRES = "código;nombre\n101;Comunidad uno\n102;Comunidad dos\n103;Comunidad tres\n";
    static final DatosVersion PRIMERA = new DatosVersion("Fuente de prueba", null,
            LocalDate.of(2026, 10, 18), "Versión de prueba 1", LocalDate.of(2025, 1, 1));
    static final DatosVersion SEGUNDA = new DatosVersion("Fuente de prueba", null,
            LocalDate.of(2026, 10, 18), "Versión de prueba 2", LocalDate.of(2026, 1, 1));

    @TempDir Path carpeta;

    @Autowired ContratoComunidadesEtnicas comunidades;
    @Autowired ContratoEtnias etnias;
    @Autowired ComunidadEtnicaRepositorio repositorio;
    @Autowired RegistroCatalogos registro;

    @Test
    @DisplayName("HU-037 Esc. 7: un cambio oficial de código inactiva el viejo y crea el nuevo")
    void esc7_cambioOficialDeCodigo() {
        comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), REFERENCIA);
        comunidades.cambiarCodigo("101", new ComunidadEtnica("102", "Valor nuevo"), REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("101"));
        assertEquals(Catalogo.Estado.ACTIVO, estado("102"));
        CatalogoCambio ultimo = ultimoCambio("101");
        assertEquals(Motivo.REEMPLAZADO_POR, ultimo.getMotivo());
        assertEquals("102", ultimo.getCodigoReemplazo());
        assertEquals(List.of("102"), activos());
    }

    @Test
    @DisplayName("HU-037 Esc. 8: un valor creado por cambio oficial queda activo con su referencia")
    void esc8_creacionPorCambioOficial() {
        comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), REFERENCIA);
        assertEquals(Catalogo.Estado.ACTIVO, estado("101"));
        CatalogoCambio creado = comunidades.historial("101").get(0);
        assertEquals(CatalogoCambio.Tipo.CREADO, creado.getTipo());
        assertEquals(REFERENCIA, creado.getReferenciaOficial());
    }

    @Test
    @DisplayName("HU-037 Esc. 9: sin la referencia oficial no se crea ni se cambia nada")
    void esc9_faltaLaReferenciaOficial() {
        assertThrows(IllegalArgumentException.class,
                () -> comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), " "));
        assertTrue(comunidades.buscarPorCodigo("101").isEmpty());
        comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> comunidades.cambiarNombre("101", "Otro nombre", null));
        assertEquals("Comunidad de prueba", comunidades.buscarPorCodigo("101").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-037 Esc. 9: un código que ya existe no se vuelve a crear")
    void esc9_elCodigoQueSeCreaYaExiste() {
        comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), REFERENCIA);
        assertThrows(IllegalStateException.class,
                () -> comunidades.crear(new ComunidadEtnica("101", "Otro nombre"), REFERENCIA));
        assertEquals("Comunidad de prueba", comunidades.buscarPorCodigo("101").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-037 Esc. 9: un código que no cumple el numeral 13 no se guarda")
    void esc9_datoQueNoCumpleSuNumeral() {
        for (String codigo : new String[] {"01", "1234", "0AB", null}) {
            assertThrows(ConstraintViolationException.class,
                    () -> comunidades.crear(new ComunidadEtnica(codigo, "X"), REFERENCIA),
                    "aceptó el código " + codigo);
        }
        assertTrue(comunidades.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-037 Esc. 9: un nombre vacío no se guarda")
    void esc9_nombreVacio() {
        assertThrows(ConstraintViolationException.class,
                () -> comunidades.crear(new ComunidadEtnica("101", " "), REFERENCIA));
        comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), REFERENCIA);
        assertThrows(ConstraintViolationException.class,
                () -> comunidades.cambiarNombre("101", " ", REFERENCIA));
        assertEquals("Comunidad de prueba", comunidades.buscarPorCodigo("101").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-037 Esc. 10: un cambio oficial de nombre deja el anterior en el historial")
    void esc10_cambioOficialDeNombre() {
        comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), REFERENCIA);
        comunidades.cambiarNombre("101", "Nombre nuevo", REFERENCIA);
        assertEquals("Nombre nuevo", comunidades.buscarPorCodigo("101").orElseThrow().getNombre());
        CatalogoCambio cambio = ultimoCambio("101");
        assertEquals(CatalogoCambio.Tipo.NOMBRE, cambio.getTipo());
        assertEquals("Comunidad de prueba", cambio.getValorAnterior());
        assertEquals("Nombre nuevo", cambio.getValorNuevo());
    }

    @Test
    @DisplayName("HU-037 Esc. 18: inactivar con motivo deja de ofrecer el valor y lo conserva")
    void esc18_inactivacionConMotivo() {
        comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), REFERENCIA);
        assertEquals(0, comunidades.contarUsos("101"));
        comunidades.inactivar("101", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("101"));
        assertTrue(activos().isEmpty());
        assertEquals(Motivo.RETIRADO_POR_LA_FUENTE, ultimoCambio("101").getMotivo());
    }

    @Test
    @DisplayName("HU-037 Esc. 19: sin motivo no se inactiva")
    void esc19_sinMotivo() {
        comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> comunidades.inactivar("101", null, REFERENCIA));
        assertEquals(Catalogo.Estado.ACTIVO, estado("101"));
    }

    @Test
    @DisplayName("HU-037 Esc. 19: \"Retirado por la fuente oficial\" sin referencia no inactiva")
    void esc19_retiradoSinReferencia() {
        comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> comunidades.inactivar("101", Motivo.RETIRADO_POR_LA_FUENTE, ""));
        assertEquals(Catalogo.Estado.ACTIVO, estado("101"));
    }

    @Test
    @DisplayName("HU-037 Esc. 19: \"Creado por error\" no vale para un código de la versión vigente")
    void esc19_creadoPorErrorEnLaVersionVigente() {
        enLaVersionVigente("101");
        assertThrows(IllegalArgumentException.class,
                () -> comunidades.inactivar("101", Motivo.CREADO_POR_ERROR, null));
        assertEquals(Catalogo.Estado.ACTIVO, estado("101"));
    }

    @Test
    @DisplayName("HU-037 Esc. 20: se reactiva diciendo por qué la referencia no lo retiraba")
    void esc20_reactivacion() {
        enLaVersionVigente("101");
        comunidades.inactivar("101", Motivo.RETIRADO_POR_LA_FUENTE, "Referencia que no lo retiraba");
        comunidades.reactivar("101", null, "La referencia retiraba otro código");
        assertEquals(Catalogo.Estado.ACTIVO, estado("101"));
        assertEquals(List.of("101"), activos());
        assertEquals("La referencia retiraba otro código", ultimoCambio("101").getJustificacion());
    }

    @Test
    @DisplayName("HU-037 Esc. 21: no se reactiva lo que no está en la versión vigente sin otra referencia")
    void esc21_fueraDeLaVersionVigente() {
        comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), REFERENCIA);
        // Una versión cargada después que ya no trae el código.
        version(LocalDateTime.now().plusMinutes(1));
        comunidades.inactivar("101", Motivo.CREADO_POR_ERROR, null);
        assertThrows(IllegalArgumentException.class,
                () -> comunidades.reactivar("101", null, "Se necesita"));
        assertEquals(Catalogo.Estado.INACTIVO, estado("101"));
    }

    @Test
    @DisplayName("HU-037 Esc. 21: inactivado con referencia, no se reactiva sin otra ni sin decir por qué")
    void esc21_sinOtraReferenciaNiPorQue() {
        enLaVersionVigente("101");
        comunidades.inactivar("101", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertThrows(IllegalArgumentException.class, () -> comunidades.reactivar("101", "", " "));
        assertEquals(Catalogo.Estado.INACTIVO, estado("101"));
    }

    @Test
    @DisplayName("HU-037 Esc. 22: el historial lista cada cambio con su motivo, su referencia y la hora")
    void esc22_historial() {
        comunidades.crear(new ComunidadEtnica("101", "Comunidad de prueba"), REFERENCIA);
        comunidades.cambiarNombre("101", "Nombre nuevo", REFERENCIA);
        comunidades.inactivar("101", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        List<CatalogoCambio> historial = comunidades.historial("101");
        assertEquals(List.of(CatalogoCambio.Tipo.CREADO, CatalogoCambio.Tipo.NOMBRE,
                CatalogoCambio.Tipo.INACTIVADO), historial.stream().map(CatalogoCambio::getTipo).toList());
        assertFalse(historial.stream().anyMatch(c -> c.getFechaHora() == null));
        assertTrue(historial.stream().allMatch(c -> REFERENCIA.equals(c.getReferenciaOficial())));
    }

    @Test
    @DisplayName("HU-037 Esc. 1: la primera carga crea una comunidad activa por fila aceptada")
    void esc1_primeraCarga() {
        CatalogoVersion version = comunidades.confirmarCarga(
                comunidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        assertEquals(List.of("101", "102", "103"), activos());
        assertEquals(version.getId(), comunidades.buscarPorCodigo("101").orElseThrow().getVersionId());
    }

    @Test
    @DisplayName("HU-037 Esc. 2: las filas con errores se rechazan, y una recarga completa sin inactivar")
    void esc2_filasConErroresYRecarga() {
        ResultadoCarga carga = comunidades.leerCarga(
                csv("código;nombre\n101;Comunidad uno\n12;Dos caracteres\n103;\n"), PRIMERA);
        assertEquals(3, carga.getFilasLeidas());
        assertEquals(List.of(2, 3), carga.getRechazadas().stream().map(ResultadoCarga.Rechazo::numero).toList());
        comunidades.confirmarCarga(carga, Map.of());
        ResultadoCarga recarga = comunidades.leerCarga(csv(TRES), PRIMERA);
        assertEquals(ResultadoCarga.Tipo.RECARGA, recarga.getTipo());
        assertEquals(List.of("102", "103"), recarga.getNuevos());
        comunidades.confirmarCarga(recarga, Map.of());
        assertEquals(List.of("101", "102", "103"), activos());
    }

    @Test
    @DisplayName("HU-037 Esc. 3: un archivo sin la columna del nombre se rechaza completo")
    void esc3_faltaLaColumnaDelNombre() {
        assertThrows(IllegalArgumentException.class,
                () -> comunidades.leerCarga(csv("código\n101\n"), PRIMERA));
    }

    @Test
    @DisplayName("HU-037 Esc. 3: una fecha anterior a la de la versión vigente rechaza el archivo")
    void esc3_fechaAnteriorALaVigente() {
        comunidades.confirmarCarga(comunidades.leerCarga(csv(TRES), SEGUNDA), Map.of());
        assertThrows(IllegalArgumentException.class, () -> comunidades.leerCarga(csv(TRES), PRIMERA));
    }

    @Test
    @DisplayName("HU-037 Esc. 4: dos filas con el mismo código se rechazan las dos")
    void esc4_codigoRepetido() {
        ResultadoCarga carga = comunidades.leerCarga(csv(TRES + "101;Otra vez\n"), PRIMERA);
        assertEquals(2, carga.getRechazadas().size());
        assertEquals(List.of("102", "103"),
                carga.getAceptadas().stream().map(ResultadoCarga.Fila::codigo).toList());
    }

    @Test
    @DisplayName("HU-037 Esc. 5: una versión nueva crea, cambia nombres, reactiva e inactiva")
    void esc5_versionNueva() {
        comunidades.confirmarCarga(comunidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        comunidades.inactivar("103", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        ResultadoCarga carga = comunidades.leerCarga(
                csv("código;nombre\n102;Comunidad dos renombrada\n103;Comunidad tres\n104;Nueva\n"),
                SEGUNDA);
        assertEquals(List.of("104"), carga.getNuevos());
        assertEquals("Comunidad dos renombrada", carga.getCambianDeNombre().get(0).ahora());
        assertEquals(List.of("103"), carga.getVuelven());
        assertEquals(List.of("101"), carga.getYaNoEstan());
        comunidades.confirmarCarga(carga, Map.of());
        assertEquals(List.of("102", "103", "104"), activos());
        assertEquals(Motivo.NO_ESTA_EN_LA_VERSION, ultimoCambio("101").getMotivo());
        assertEquals("Comunidad dos", ultimoCambio("102").getValorAnterior());
    }

    @Test
    @DisplayName("HU-037 Esc. 6: una fila rechazada de un código existente lo deja como estaba")
    void esc6_versionNuevaConFilasRechazadas() {
        comunidades.confirmarCarga(comunidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        ResultadoCarga carga = comunidades.leerCarga(csv(TRES.replace("101;Comunidad uno", "101;")),
                SEGUNDA);
        assertTrue(carga.getYaNoEstan().isEmpty());
        comunidades.confirmarCarga(carga, Map.of());
        assertEquals("Comunidad uno", comunidades.buscarPorCodigo("101").orElseThrow().getNombre());
        assertEquals(Catalogo.Estado.ACTIVO, estado("101"));
    }

    @Test
    @DisplayName("HU-037 Esc. 11: leer una carga y no confirmarla deja el catálogo igual")
    void esc11_cancelarLaCarga() {
        comunidades.leerCarga(csv(TRES), PRIMERA);
        assertTrue(comunidades.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-037 Esc. 11: cancelar una creación en el menú deja el catálogo igual")
    void esc11_cancelarLaCreacion() {
        menu("2\n2\n101\nComunidad uno\n" + REFERENCIA + "\nn\n0\n0\n");
        assertTrue(comunidades.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-037 Esc. 11: cancelar una inactivación en el menú deja el catálogo igual")
    void esc11_cancelarLaInactivacion() {
        comunidades.crear(new ComunidadEtnica("101", "Comunidad uno"), REFERENCIA);
        menu("2\n5\n101\n1\n" + REFERENCIA + "\nn\n0\n0\n");
        assertEquals(Catalogo.Estado.ACTIVO, estado("101"));
    }

    @Test
    @DisplayName("HU-037 Esc. 12: la consulta trae el filtro, cuántas hay activas y la versión vigente")
    void esc12_consulta() {
        comunidades.confirmarCarga(comunidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        comunidades.inactivar("103", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        Consulta<ComunidadEtnica> todas = comunidades.consultar(CrudCatalogo.Filtro.TODOS);
        assertEquals(3, todas.valores().size());
        assertEquals(2, todas.activos());
        assertEquals("Versión de prueba 1", todas.vigente().orElseThrow().getVersion());
        assertEquals(List.of("103"), comunidades.consultar(CrudCatalogo.Filtro.INACTIVOS).valores()
                .stream().map(Catalogo::getCodigo).toList());
        assertEquals(List.of(3L, 0L, 0L), List.of(comunidades.historialDeVersiones().get(0).creados(),
                comunidades.historialDeVersiones().get(0).cambiados(),
                comunidades.historialDeVersiones().get(0).inactivados()));
    }

    @Test
    @DisplayName("HU-037 Esc. 13: la búsqueda por código trae solo la comunidad con ese código")
    void esc13_busquedaPorCodigo() {
        comunidades.confirmarCarga(comunidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        assertEquals(List.of("102"), codigos(comunidades.buscar("102")));
    }

    @Test
    @DisplayName("HU-037 Esc. 14: la búsqueda por nombre ignora mayúsculas y tildes y ordena por nombre")
    void esc14_busquedaPorNombre() {
        comunidades.confirmarCarga(comunidades.leerCarga(
                csv("código;nombre\n101;Pueblo Wayúu\n102;Comunidad Embera\n103;Pueblo Nasa\n"),
                PRIMERA), Map.of());
        assertEquals(List.of("103", "101"), codigos(comunidades.buscar("PUEBLO")));
        assertEquals(List.of("101"), codigos(comunidades.buscar("wayuu")));
    }

    @Test
    @DisplayName("HU-037 Esc. 15: una búsqueda sin resultados lo dice")
    void esc15_sinResultados() {
        comunidades.confirmarCarga(comunidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        assertTrue(comunidades.buscar("xyz").isEmpty());
        assertEquals("No hay comunidades étnicas que coincidan con la búsqueda",
                comunidades.sinCoincidencias());
    }

    @Test
    @DisplayName("HU-037 Esc. 16: el catálogo vacío dice que falta cargar el archivo oficial")
    void esc16_catalogoVacio() {
        assertEquals("El catálogo de comunidades étnicas está vacío: falta cargar el archivo oficial",
                comunidades.consultar(CrudCatalogo.Filtro.TODOS).aviso());
    }

    @Test
    @DisplayName("HU-037 Esc. 17: la comparación con el archivo oficial lista lo que falta, sobra o cambió")
    void esc17_comprobacionContraElArchivo() {
        comunidades.confirmarCarga(comunidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        comunidades.inactivar("103", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        comunidades.crear(new ComunidadEtnica("105", "Creada a mano"), REFERENCIA);
        List<String> antes = comunidades.listar().stream()
                .map(c -> c.getCodigo() + c.getNombre() + c.getEstado()).toList();
        Comprobacion comprobacion = comunidades.comprobarContraElArchivo(csv(
                "código;nombre\n101;Comunidad uno renombrada\n102;Comunidad dos\n"
                        + "103;Comunidad tres\n104;Nueva\n"));
        assertEquals(List.of("104"), comprobacion.faltan());
        assertEquals(List.of("103"), comprobacion.inactivos());
        assertEquals(List.of("105"), comprobacion.sobran());
        assertEquals(List.of("101"), comprobacion.nombresDistintos());
        assertEquals(antes, comunidades.listar().stream()
                .map(c -> c.getCodigo() + c.getNombre() + c.getEstado()).toList());
    }

    private void menu(String respuestas) {
        new MenuPrincipal(etnias, comunidades,
                new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8))).run();
    }

    private static List<String> codigos(List<ComunidadEtnica> valores) {
        return valores.stream().map(Catalogo::getCodigo).toList();
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
        return comunidades.buscarPorCodigo(codigo).orElseThrow().getEstado();
    }

    private List<String> activos() {
        return comunidades.listarActivos().stream().map(Catalogo::getCodigo).toList();
    }

    private CatalogoCambio ultimoCambio(String codigo) {
        List<CatalogoCambio> historial = comunidades.historial(codigo);
        return historial.get(historial.size() - 1);
    }

    private CatalogoVersion version(LocalDateTime cargadoEn) {
        CatalogoVersion version = new CatalogoVersion();
        version.setCatalogo("ComunidadEtnica");
        version.setVersion("Versión de prueba");
        version.setFechaPublicacion(LocalDate.of(2021, 6, 25));
        version.setCargadoEn(cargadoEn);
        return registro.guardar(version);
    }

    // Como si el código hubiera llegado en la carga de la versión vigente; la carga desde el CSV es
    // la pieza siguiente de la historia.
    private void enLaVersionVigente(String codigo) {
        ComunidadEtnica valor = new ComunidadEtnica(codigo, "Comunidad de prueba");
        valor.setVersionId(version(LocalDateTime.of(2026, 1, 1, 0, 0)).getId());
        repositorio.save(valor);
    }
}
