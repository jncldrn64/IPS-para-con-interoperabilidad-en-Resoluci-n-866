package com.ipssaludyvida.hu018;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.modelos.Catalogo;
import com.ipssaludyvida.comun.modelos.CatalogoCambio;
import com.ipssaludyvida.comun.modelos.CatalogoVersion;
import com.ipssaludyvida.comun.modelos.Motivo;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.comun.servicios.Consulta;
import com.ipssaludyvida.comun.servicios.DatosVersion;
import com.ipssaludyvida.comun.servicios.RegistroCatalogos;
import com.ipssaludyvida.comun.servicios.ResultadoCarga;
import com.ipssaludyvida.hu001.Usuarios;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Cargo;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import com.ipssaludyvida.hu018.controladores.PaisControlador;
import com.ipssaludyvida.hu018.interfaces.ContratoPaises;
import com.ipssaludyvida.hu018.modelos.Pais;
import com.ipssaludyvida.hu018.repositorios.PaisRepositorio;
import jakarta.validation.ConstraintViolationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Los escenarios de HU-018, en docs/context/trello/tarjetas/HU-018.md. Cada método se llama
 * esc<N>_<alternativa>, por el número del escenario y el caso de su "Dado" que prueba. Los que
 * cargan el archivo oficial leen data/catalogos/pais-sispro.xlsx, el Excel de la tabla Pais de
 * SISPRO tal como se exportó; los demás escriben un CSV corto.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU018PaisesTest {

    static final String REFERENCIA = "Resolución de prueba";
    static final Path SISPRO = Path.of("data/catalogos/pais-sispro.xlsx");
    static final DatosVersion TABLA_PAIS = new DatosVersion(
            "Ministerio de Salud y Protección Social, SISPRO, tabla de referencia Pais",
            "https://web.sispro.gov.co/WebPublico/Consultas/ConsultarDetalleReferenciaBasica.aspx"
                    + "?Code=Pais", LocalDate.of(2026, 11, 2), "Tabla Pais de SISPRO",
            LocalDate.of(2024, 2, 26));
    static final String TRES = "código;nombre\n032;ARGENTINA\n076;BRASIL\n170;COLOMBIA\n";
    static final DatosVersion PRIMERA = new DatosVersion("Fuente de prueba", null,
            LocalDate.of(2026, 11, 2), "Versión de prueba 1", LocalDate.of(2025, 1, 1));
    static final DatosVersion SEGUNDA = new DatosVersion("Fuente de prueba", null,
            LocalDate.of(2026, 11, 2), "Versión de prueba 2", LocalDate.of(2026, 1, 1));

    @TempDir Path carpeta;

    @Autowired UsuarioRepositorio usuarios;
    @Autowired ContratoSesion sesion;
    @Autowired ContratoPaises paises;
    @Autowired PaisRepositorio repositorio;
    @Autowired RegistroCatalogos registro;

    @Test
    @DisplayName("HU-018 Esc. 1: la primera carga del Excel de SISPRO crea los 249 países activos")
    void esc1_primeraCargaDelExcelDeSispro() {
        ResultadoCarga carga = paises.leerCarga(SISPRO, TABLA_PAIS);
        assertEquals(ResultadoCarga.Tipo.PRIMERA, carga.getTipo());
        assertEquals(249, carga.getFilasLeidas());
        assertEquals(249, carga.getAceptadas().size());
        assertTrue(carga.getRechazadas().isEmpty());
        CatalogoVersion version = paises.confirmarCarga(carga, Map.of());
        assertEquals(249, activos().size());
        assertEquals("COLOMBIA", paises.buscarPorCodigo("170").orElseThrow().getNombre());
        // El Excel guarda el código como texto, así que conserva sus ceros (D-18).
        assertEquals("AFGANISTÁN", paises.buscarPorCodigo("004").orElseThrow().getNombre());
        assertEquals(version.getId(), paises.buscarPorCodigo("170").orElseThrow().getVersionId());
        assertEquals(LocalDate.of(2024, 2, 26), version.getFechaPublicacion());
    }

    @Test
    @DisplayName("HU-018 Esc. 1: la primera carga de un CSV crea un país activo por fila aceptada")
    void esc1_primeraCargaDeUnCsv() {
        CatalogoVersion version = paises.confirmarCarga(paises.leerCarga(csv(TRES), PRIMERA),
                Map.of());
        assertEquals(List.of("032", "076", "170"), activos());
        assertEquals(version.getId(), paises.buscarPorCodigo("170").orElseThrow().getVersionId());
    }

    @Test
    @DisplayName("HU-018 Esc. 2: las filas con errores se rechazan, y una recarga completa sin inactivar")
    void esc2_filasConErroresYRecarga() {
        ResultadoCarga carga = paises.leerCarga(
                csv("código;nombre\n032;ARGENTINA\n76;Dos caracteres\n170;\n"), PRIMERA);
        assertEquals(3, carga.getFilasLeidas());
        assertEquals(List.of(2, 3),
                carga.getRechazadas().stream().map(ResultadoCarga.Rechazo::numero).toList());
        paises.confirmarCarga(carga, Map.of());
        ResultadoCarga recarga = paises.leerCarga(csv(TRES), PRIMERA);
        assertEquals(ResultadoCarga.Tipo.RECARGA, recarga.getTipo());
        assertEquals(List.of("076", "170"), recarga.getNuevos());
        paises.confirmarCarga(recarga, Map.of());
        assertEquals(List.of("032", "076", "170"), activos());
    }

    @Test
    @DisplayName("HU-018 Esc. 2: una fila con letras en el código, como COL, se rechaza")
    void esc2_codigoConLetras() {
        ResultadoCarga carga = paises.leerCarga(csv(TRES + "COL;COLOMBIA\n"), PRIMERA);
        ResultadoCarga.Rechazo rechazo = carga.getRechazadas().get(0);
        assertEquals("COL", rechazo.codigo());
        assertTrue(rechazo.motivo().contains("3 dígitos"), rechazo.motivo());
    }

    @Test
    @DisplayName("HU-018 Esc. 3: un archivo sin la columna del nombre se rechaza completo")
    void esc3_faltaLaColumnaDelNombre() {
        assertThrows(IllegalArgumentException.class,
                () -> paises.leerCarga(csv("código\n170\n"), PRIMERA));
    }

    @Test
    @DisplayName("HU-018 Esc. 3: una fecha anterior a la de la versión vigente rechaza el archivo")
    void esc3_fechaAnteriorALaVigente() {
        paises.confirmarCarga(paises.leerCarga(csv(TRES), SEGUNDA), Map.of());
        assertThrows(IllegalArgumentException.class, () -> paises.leerCarga(csv(TRES), PRIMERA));
    }

    @Test
    @DisplayName("HU-018 Esc. 3: una fecha de publicación futura rechaza el archivo completo")
    void esc3_fechaFutura() {
        DatosVersion futura = new DatosVersion("Fuente de prueba", null, LocalDate.of(2026, 11, 2),
                "Versión futura", registro.hoy().plusDays(1));
        assertThrows(IllegalArgumentException.class, () -> paises.leerCarga(csv(TRES), futura));
    }

    @Test
    @DisplayName("HU-018 Esc. 4: dos filas con el mismo código se rechazan las dos")
    void esc4_codigoRepetido() {
        ResultadoCarga carga = paises.leerCarga(csv(TRES + "032;ARGENTINA OTRA VEZ\n"), PRIMERA);
        assertEquals(2, carga.getRechazadas().size());
        assertEquals(List.of("076", "170"),
                carga.getAceptadas().stream().map(ResultadoCarga.Fila::codigo).toList());
    }

    @Test
    @DisplayName("HU-018 Esc. 5: una versión nueva crea, cambia nombres, reactiva e inactiva")
    void esc5_versionNueva() {
        paises.confirmarCarga(paises.leerCarga(csv(TRES), PRIMERA), Map.of());
        paises.inactivar("170", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        ResultadoCarga carga = paises.leerCarga(
                csv("código;nombre\n076;BRASIL RENOMBRADO\n170;COLOMBIA\n218;ECUADOR\n"), SEGUNDA);
        assertEquals(List.of("218"), carga.getNuevos());
        assertEquals("BRASIL RENOMBRADO", carga.getCambianDeNombre().get(0).ahora());
        assertEquals(List.of("170"), carga.getVuelven());
        assertEquals(List.of("032"), carga.getYaNoEstan());
        paises.confirmarCarga(carga, Map.of());
        assertEquals(List.of("076", "170", "218"), activos());
        assertEquals(Motivo.NO_ESTA_EN_LA_VERSION, ultimoCambio("032").getMotivo());
        assertEquals("BRASIL", ultimoCambio("076").getValorAnterior());
    }

    @Test
    @DisplayName("HU-018 Esc. 6: una fila rechazada de un código existente lo deja como estaba")
    void esc6_versionNuevaConFilasRechazadas() {
        paises.confirmarCarga(paises.leerCarga(csv(TRES), PRIMERA), Map.of());
        ResultadoCarga carga = paises.leerCarga(csv(TRES.replace("032;ARGENTINA", "032;")),
                SEGUNDA);
        assertTrue(carga.getYaNoEstan().isEmpty());
        paises.confirmarCarga(carga, Map.of());
        assertEquals("ARGENTINA", paises.buscarPorCodigo("032").orElseThrow().getNombre());
        assertEquals(Catalogo.Estado.ACTIVO, estado("032"));
    }

    @Test
    @DisplayName("HU-018 Esc. 7: un cambio oficial de código inactiva el viejo y crea el nuevo")
    void esc7_cambioOficialDeCodigo() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        paises.cambiarCodigo("999", new Pais("998", "VALOR NUEVO"), REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("999"));
        assertEquals(Catalogo.Estado.ACTIVO, estado("998"));
        CatalogoCambio ultimo = ultimoCambio("999");
        assertEquals(Motivo.REEMPLAZADO_POR, ultimo.getMotivo());
        assertEquals("998", ultimo.getCodigoReemplazo());
        assertEquals(List.of("998"), activos());
    }

    @Test
    @DisplayName("HU-018 Esc. 8: un valor creado por cambio oficial queda activo con su referencia")
    void esc8_creacionPorCambioOficial() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        assertEquals(Catalogo.Estado.ACTIVO, estado("999"));
        CatalogoCambio creado = paises.historial("999").get(0);
        assertEquals(CatalogoCambio.Tipo.CREADO, creado.getTipo());
        assertEquals(REFERENCIA, creado.getReferenciaOficial());
    }

    @Test
    @DisplayName("HU-018 Esc. 9: sin la referencia oficial no se crea ni se cambia nada")
    void esc9_faltaLaReferenciaOficial() {
        assertThrows(IllegalArgumentException.class,
                () -> paises.crear(new Pais("999", "PAÍS DE PRUEBA"), " "));
        assertTrue(paises.buscarPorCodigo("999").isEmpty());
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> paises.cambiarNombre("999", "OTRO NOMBRE", null));
        assertEquals("PAÍS DE PRUEBA", paises.buscarPorCodigo("999").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-018 Esc. 9: un código que ya existe no se vuelve a crear")
    void esc9_elCodigoQueSeCreaYaExiste() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        assertThrows(IllegalStateException.class,
                () -> paises.crear(new Pais("999", "OTRO NOMBRE"), REFERENCIA));
        assertEquals("PAÍS DE PRUEBA", paises.buscarPorCodigo("999").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-018 Esc. 9: un código que no cumple los numerales 1 y 11 no se guarda")
    void esc9_codigoQueNoCumpleSuNumeral() {
        for (String codigo : new String[] {"17", "1700", "COL", "1A0", null}) {
            assertThrows(ConstraintViolationException.class,
                    () -> paises.crear(new Pais(codigo, "PAÍS DE PRUEBA"), REFERENCIA),
                    "aceptó el código " + codigo);
        }
        assertTrue(paises.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-018 Esc. 9: un nombre de menos de 3 caracteres no se guarda")
    void esc9_nombreQueNoCumpleSuNumeral() {
        for (String nombre : new String[] {" ", "CO"}) {
            assertThrows(ConstraintViolationException.class,
                    () -> paises.crear(new Pais("999", nombre), REFERENCIA),
                    "aceptó el nombre " + nombre);
        }
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        assertThrows(ConstraintViolationException.class,
                () -> paises.cambiarNombre("999", "CO", REFERENCIA));
        assertEquals("PAÍS DE PRUEBA", paises.buscarPorCodigo("999").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-018 Esc. 10: un cambio oficial de nombre deja el anterior en el historial")
    void esc10_cambioOficialDeNombre() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        paises.cambiarNombre("999", "NOMBRE NUEVO", REFERENCIA);
        assertEquals("NOMBRE NUEVO", paises.buscarPorCodigo("999").orElseThrow().getNombre());
        CatalogoCambio cambio = ultimoCambio("999");
        assertEquals(CatalogoCambio.Tipo.NOMBRE, cambio.getTipo());
        assertEquals("PAÍS DE PRUEBA", cambio.getValorAnterior());
        assertEquals("NOMBRE NUEVO", cambio.getValorNuevo());
    }

    @Test
    @DisplayName("HU-018 Esc. 11: leer una carga y no confirmarla deja el catálogo igual")
    void esc11_cancelarLaCarga() {
        paises.leerCarga(SISPRO, TABLA_PAIS);
        assertTrue(paises.listar().isEmpty());
        assertTrue(paises.versionVigente().isEmpty());
    }

    @Test
    @DisplayName("HU-018 Esc. 11: leer una versión nueva y no confirmarla deja el catálogo igual")
    void esc11_cancelarLaVersionNueva() {
        paises.confirmarCarga(paises.leerCarga(csv(TRES), PRIMERA), Map.of());
        paises.leerCarga(csv(TRES.replace("170;COLOMBIA\n", "")), SEGUNDA);
        assertEquals(Catalogo.Estado.ACTIVO, estado("170"));
        assertEquals("Versión de prueba 1", paises.versionVigente().orElseThrow().getVersion());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-018 Esc. 11: cancelar una creación en el menú deja el catálogo igual")
    void esc11_cancelarLaCreacion() {
        menu("2\n999\nPAÍS DE PRUEBA\n" + REFERENCIA + "\nn\n0\n");
        assertTrue(paises.listar().isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-018 Esc. 11: cancelar un cambio de nombre en el menú deja el catálogo igual")
    void esc11_cancelarElCambio() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        menu("3\n999\nOTRO NOMBRE\n" + REFERENCIA + "\nn\n0\n");
        assertEquals("PAÍS DE PRUEBA", paises.buscarPorCodigo("999").orElseThrow().getNombre());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-018 Esc. 11: cancelar una inactivación en el menú deja el catálogo igual")
    void esc11_cancelarLaInactivacion() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        menu("5\n999\n1\n" + REFERENCIA + "\nn\n0\n");
        assertEquals(Catalogo.Estado.ACTIVO, estado("999"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-018 Esc. 11: cancelar una reactivación en el menú deja el catálogo igual")
    void esc11_cancelarLaReactivacion() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        paises.inactivar("999", Motivo.CREADO_POR_ERROR, null);
        menu("6\n999\n\nLa referencia retiraba otro código\nn\n0\n");
        assertEquals(Catalogo.Estado.INACTIVO, estado("999"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-018 Esc. 11: cancelar la carga del Excel en el menú deja el catálogo igual")
    void esc11_cancelarLaCargaEnElMenu() {
        menu("8\n" + SISPRO + "\nFuente\n\n2026-11-02\nTabla Pais de SISPRO\n2024-02-26\nn\n0\n");
        assertTrue(paises.listar().isEmpty());
        assertTrue(paises.versionVigente().isEmpty());
    }

    @Test
    @DisplayName("HU-018 Esc. 12: la consulta trae el filtro, cuántos hay activos y la versión vigente")
    void esc12_consulta() {
        paises.confirmarCarga(paises.leerCarga(csv(TRES), PRIMERA), Map.of());
        paises.inactivar("170", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        Consulta<Pais> todos = paises.consultar(CrudCatalogo.Filtro.TODOS);
        assertEquals(3, todos.valores().size());
        assertEquals(2, todos.activos());
        assertEquals("Versión de prueba 1", todos.vigente().orElseThrow().getVersion());
        assertEquals(List.of("170"), codigos(paises.consultar(CrudCatalogo.Filtro.INACTIVOS)
                .valores()));
        assertEquals(List.of(3L, 0L, 0L), List.of(paises.historialDeVersiones().get(0).creados(),
                paises.historialDeVersiones().get(0).cambiados(),
                paises.historialDeVersiones().get(0).inactivados()));
    }

    @Test
    @DisplayName("HU-018 Esc. 13: la búsqueda por código trae solo el país con ese código")
    void esc13_busquedaPorCodigo() {
        paises.confirmarCarga(paises.leerCarga(SISPRO, TABLA_PAIS), Map.of());
        assertEquals(List.of("170"), codigos(paises.buscar("170")));
    }

    @Test
    @DisplayName("HU-018 Esc. 14: la búsqueda por nombre ignora mayúsculas y tildes y ordena por nombre")
    void esc14_busquedaPorNombre() {
        paises.confirmarCarga(paises.leerCarga(
                csv("código;nombre\n674;SAN MARINO\n662;SANTA LUCÍA\n170;COLOMBIA\n"), PRIMERA),
                Map.of());
        assertEquals(List.of("674", "662"), codigos(paises.buscar("san")));
        assertEquals(List.of("662"), codigos(paises.buscar("Lucia")));
    }

    @Test
    @DisplayName("HU-018 Esc. 15: una búsqueda sin resultados lo dice")
    void esc15_sinResultados() {
        paises.confirmarCarga(paises.leerCarga(csv(TRES), PRIMERA), Map.of());
        assertTrue(paises.buscar("xyz").isEmpty());
        assertEquals("No hay países que coincidan con la búsqueda", paises.sinCoincidencias());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-018 Esc. 15: el menú dice que la búsqueda no trajo resultados")
    void esc15_sinResultadosEnElMenu() {
        paises.confirmarCarga(paises.leerCarga(csv(TRES), PRIMERA), Map.of());
        assertTrue(menu("9\nxyz\n0\n").contains("No hay países que coincidan con la búsqueda"));
    }

    @Test
    @DisplayName("HU-018 Esc. 16: el catálogo vacío dice que falta cargar el archivo oficial")
    void esc16_catalogoVacio() {
        assertEquals("El catálogo de países está vacío: falta cargar el archivo oficial",
                paises.consultar(CrudCatalogo.Filtro.TODOS).aviso());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-018 Esc. 16: la consulta del menú dice que el catálogo está vacío")
    void esc16_catalogoVacioEnElMenu() {
        assertTrue(menu("1\n3\n0\n")
                .contains("El catálogo de países está vacío: falta cargar el archivo oficial"));
    }

    @Test
    @DisplayName("HU-018 Esc. 17: la comparación con el archivo oficial lista lo que falta, sobra o cambió")
    void esc17_comprobacionContraElArchivo() {
        paises.confirmarCarga(paises.leerCarga(csv(TRES), PRIMERA), Map.of());
        paises.inactivar("170", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        paises.crear(new Pais("999", "CREADO A MANO"), REFERENCIA);
        List<String> antes = foto();
        Comprobacion comprobacion = paises.comprobarContraElArchivo(csv("código;nombre\n"
                + "032;ARGENTINA RENOMBRADA\n076;BRASIL\n170;COLOMBIA\n218;ECUADOR\n"));
        assertEquals(List.of("218"), comprobacion.faltan());
        assertEquals(List.of("170"), comprobacion.inactivos());
        assertEquals(List.of("999"), comprobacion.sobran());
        assertEquals(List.of("032"), comprobacion.nombresDistintos());
        assertEquals(antes, foto(), "la comparación no cambia nada");
    }

    @Test
    @DisplayName("HU-018 Esc. 17: después de cargar el Excel de SISPRO, coincide con él")
    void esc17_coincideConElExcelDeSispro() {
        paises.confirmarCarga(paises.leerCarga(SISPRO, TABLA_PAIS), Map.of());
        assertTrue(paises.comprobarContraElArchivo(SISPRO).coincide());
        paises.inactivar("170", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertEquals(List.of("170"), paises.comprobarContraElArchivo(SISPRO).inactivos());
    }

    @Test
    @DisplayName("HU-018 Esc. 18: inactivar con motivo deja de ofrecer el valor y lo conserva")
    void esc18_inactivacionConMotivo() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        assertEquals(0, paises.contarUsos("999"));
        paises.inactivar("999", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("999"));
        assertTrue(activos().isEmpty());
        assertEquals(Motivo.RETIRADO_POR_LA_FUENTE, ultimoCambio("999").getMotivo());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-018 Esc. 18: el menú muestra cuántos usan el país antes de pedir confirmar")
    void esc18_elMenuMuestraLosUsos() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        String salida = menu("5\n999\n1\n" + REFERENCIA + "\ns\n0\n");
        int usos = salida.indexOf("La usan " + paises.contarUsos("999") + " pacientes");
        assertTrue(usos >= 0, salida);
        assertTrue(usos < salida.indexOf("¿Confirma?"), "los usos van antes de confirmar");
        assertEquals(Catalogo.Estado.INACTIVO, estado("999"));
    }

    @Test
    @DisplayName("HU-018 Esc. 19: sin motivo no se inactiva")
    void esc19_sinMotivo() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> paises.inactivar("999", null, REFERENCIA));
        assertEquals(Catalogo.Estado.ACTIVO, estado("999"));
    }

    @Test
    @DisplayName("HU-018 Esc. 19: \"Retirado por la fuente oficial\" sin referencia no inactiva")
    void esc19_retiradoSinReferencia() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> paises.inactivar("999", Motivo.RETIRADO_POR_LA_FUENTE, ""));
        assertEquals(Catalogo.Estado.ACTIVO, estado("999"));
    }

    @Test
    @DisplayName("HU-018 Esc. 19: \"Creado por error\" no vale para un código de la versión vigente")
    void esc19_creadoPorErrorEnLaVersionVigente() {
        enLaVersionVigente("999");
        assertThrows(IllegalArgumentException.class,
                () -> paises.inactivar("999", Motivo.CREADO_POR_ERROR, null));
        assertEquals(Catalogo.Estado.ACTIVO, estado("999"));
    }

    @Test
    @DisplayName("HU-018 Esc. 20: se reactiva diciendo por qué la referencia no lo retiraba")
    void esc20_reactivacion() {
        enLaVersionVigente("999");
        paises.inactivar("999", Motivo.RETIRADO_POR_LA_FUENTE, "Referencia que no lo retiraba");
        paises.reactivar("999", null, "La referencia retiraba otro código");
        assertEquals(Catalogo.Estado.ACTIVO, estado("999"));
        assertEquals(List.of("999"), activos());
        assertEquals("La referencia retiraba otro código", ultimoCambio("999").getJustificacion());
    }

    @Test
    @DisplayName("HU-018 Esc. 21: no se reactiva lo que no está en la versión vigente sin otra referencia")
    void esc21_fueraDeLaVersionVigente() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        // Una versión cargada después que ya no trae el código.
        version(LocalDateTime.now().plusMinutes(1));
        paises.inactivar("999", Motivo.CREADO_POR_ERROR, null);
        assertThrows(IllegalArgumentException.class,
                () -> paises.reactivar("999", null, "Se necesita"));
        assertEquals(Catalogo.Estado.INACTIVO, estado("999"));
    }

    @Test
    @DisplayName("HU-018 Esc. 21: inactivado con referencia, no se reactiva sin otra ni sin decir por qué")
    void esc21_sinOtraReferenciaNiPorQue() {
        enLaVersionVigente("999");
        paises.inactivar("999", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertThrows(IllegalArgumentException.class, () -> paises.reactivar("999", "", " "));
        assertEquals(Catalogo.Estado.INACTIVO, estado("999"));
    }

    @Test
    @DisplayName("HU-018 Esc. 22: el historial lista cada cambio con su motivo, su referencia y la hora")
    void esc22_historial() {
        paises.crear(new Pais("999", "PAÍS DE PRUEBA"), REFERENCIA);
        paises.cambiarNombre("999", "NOMBRE NUEVO", REFERENCIA);
        paises.inactivar("999", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        List<CatalogoCambio> historial = paises.historial("999");
        assertEquals(List.of(CatalogoCambio.Tipo.CREADO, CatalogoCambio.Tipo.NOMBRE,
                CatalogoCambio.Tipo.INACTIVADO),
                historial.stream().map(CatalogoCambio::getTipo).toList());
        assertFalse(historial.stream().anyMatch(c -> c.getFechaHora() == null));
        assertTrue(historial.stream().allMatch(c -> REFERENCIA.equals(c.getReferenciaOficial())));
    }

    // Lo que el menú imprimió con esas respuestas, con la sesión del profesional de estadística,
    // que tiene las 11 opciones. Un test del menú lleva @Timeout, porque un menú que no sale con la
    // entrada vacía se queda en un bucle (docs/DESIGN.md, sección 3).
    private String menu(String respuestas) {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Usuarios.entrar(usuarios, sesion, Cargo.PROFESIONAL_DE_ESTADISTICA);
        try {
            new PaisControlador(paises, new Terminal(
                    new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8)),
                    new PrintStream(salida, true, StandardCharsets.UTF_8)), sesion).abrir();
        } finally {
            sesion.cerrar();
        }
        return salida.toString(StandardCharsets.UTF_8);
    }

    private static List<String> codigos(List<Pais> valores) {
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

    private List<String> foto() {
        return paises.listar().stream()
                .map(p -> p.getCodigo() + p.getNombre() + p.getEstado() + p.getVersionId())
                .toList();
    }

    private Catalogo.Estado estado(String codigo) {
        return paises.buscarPorCodigo(codigo).orElseThrow().getEstado();
    }

    private List<String> activos() {
        return paises.listarActivos().stream().map(Catalogo::getCodigo).toList();
    }

    private CatalogoCambio ultimoCambio(String codigo) {
        List<CatalogoCambio> historial = paises.historial(codigo);
        return historial.get(historial.size() - 1);
    }

    private CatalogoVersion version(LocalDateTime cargadoEn) {
        CatalogoVersion version = new CatalogoVersion();
        version.setCatalogo("Pais");
        version.setVersion("Versión de prueba");
        version.setFechaPublicacion(LocalDate.of(2021, 6, 25));
        version.setCargadoEn(cargadoEn);
        return registro.guardar(version);
    }

    // Como si el código hubiera llegado en la carga de la versión vigente, sin cargar un archivo.
    private void enLaVersionVigente(String codigo) {
        Pais valor = new Pais(codigo, "PAÍS DE PRUEBA");
        valor.setVersionId(version(LocalDateTime.of(2026, 1, 1, 0, 0)).getId());
        repositorio.save(valor);
    }
}
