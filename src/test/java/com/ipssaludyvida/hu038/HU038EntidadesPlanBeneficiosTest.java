package com.ipssaludyvida.hu038;

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
import com.ipssaludyvida.hu038.controladores.EntidadPlanBeneficiosControlador;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import com.ipssaludyvida.hu038.modelos.EntidadPlanBeneficios;
import com.ipssaludyvida.hu038.repositorios.EntidadPlanBeneficiosRepositorio;
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
 * Los escenarios de HU-038, en docs/context/trello/tarjetas/HU-038.md. Cada método se llama
 * esc<N>_<alternativa>, por el número del escenario y el caso de su "Dado" que prueba.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU038EntidadesPlanBeneficiosTest {

    static final String REFERENCIA = "Resolución de prueba";
    // Valores inventados para los casos cortos; el de SISPRO es data/catalogos/eapb-sispro.xlsx.
    static final Path SISPRO = Path.of("data/catalogos/eapb-sispro.xlsx");
    static final DatosVersion TABLA_EAPB = new DatosVersion(
            "Ministerio de Salud y Protección Social, SISPRO, tabla de referencia CodigoEAPByNit",
            "https://web.sispro.gov.co/WebPublico/Consultas/ConsultarDetalleReferenciaBasica.aspx"
                    + "?Code=CodigoEAPByNit", LocalDate.of(2026, 11, 2),
            "Tabla CodigoEAPByNit de SISPRO", LocalDate.of(2026, 1, 30));
    static final String TRES = "código;nombre\nENT101;Entidad uno\nENT102;Entidad dos\nENT103;Entidad tres\n";
    static final DatosVersion PRIMERA = new DatosVersion("Fuente de prueba", null,
            LocalDate.of(2026, 10, 18), "Versión de prueba 1", LocalDate.of(2025, 1, 1));
    static final DatosVersion SEGUNDA = new DatosVersion("Fuente de prueba", null,
            LocalDate.of(2026, 10, 18), "Versión de prueba 2", LocalDate.of(2026, 1, 1));

    @TempDir Path carpeta;

    @Autowired UsuarioRepositorio usuarios;
    @Autowired ContratoSesion sesion;
    @Autowired ContratoEntidadesPlanBeneficios entidades;
    @Autowired EntidadPlanBeneficiosRepositorio repositorio;
    @Autowired RegistroCatalogos registro;

    @Test
    @DisplayName("HU-038 Esc. 7: un cambio oficial de código inactiva el viejo y crea el nuevo")
    void esc7_cambioOficialDeCodigo() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        entidades.cambiarCodigo("ENT101", new EntidadPlanBeneficios("ENT102", "Valor nuevo"), REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("ENT101"));
        assertEquals(Catalogo.Estado.ACTIVO, estado("ENT102"));
        CatalogoCambio ultimo = ultimoCambio("ENT101");
        assertEquals(Motivo.REEMPLAZADO_POR, ultimo.getMotivo());
        assertEquals("ENT102", ultimo.getCodigoReemplazo());
        assertEquals(List.of("ENT102"), activos());
    }

    @Test
    @DisplayName("HU-038 Esc. 8: un valor creado por cambio oficial queda activo con su referencia")
    void esc8_creacionPorCambioOficial() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        assertEquals(Catalogo.Estado.ACTIVO, estado("ENT101"));
        CatalogoCambio creado = entidades.historial("ENT101").get(0);
        assertEquals(CatalogoCambio.Tipo.CREADO, creado.getTipo());
        assertEquals(REFERENCIA, creado.getReferenciaOficial());
    }

    @Test
    @DisplayName("HU-038 Esc. 9: sin la referencia oficial no se crea ni se cambia nada")
    void esc9_faltaLaReferenciaOficial() {
        assertThrows(IllegalArgumentException.class,
                () -> entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), " "));
        assertTrue(entidades.buscarPorCodigo("ENT101").isEmpty());
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> entidades.cambiarNombre("ENT101", "Otro nombre", null));
        assertEquals("Entidad de prueba", entidades.buscarPorCodigo("ENT101").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-038 Esc. 9: un código que ya existe no se vuelve a crear")
    void esc9_elCodigoQueSeCreaYaExiste() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        assertThrows(IllegalStateException.class,
                () -> entidades.crear(new EntidadPlanBeneficios("ENT101", "Otro nombre"), REFERENCIA));
        assertEquals("Entidad de prueba", entidades.buscarPorCodigo("ENT101").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-038 Esc. 9: un código que no cumple el numeral 15 no se guarda")
    void esc9_datoQueNoCumpleSuNumeral() {
        for (String codigo : new String[] {"ENT", "ENT0001", "ent101", "UT001_", null}) {
            assertThrows(ConstraintViolationException.class,
                    () -> entidades.crear(new EntidadPlanBeneficios(codigo, "Entidad de prueba"),
                            REFERENCIA),
                    "aceptó el código " + codigo);
        }
        assertTrue(entidades.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-038 Esc. 9: un nombre vacío no se guarda")
    void esc9_nombreVacio() {
        assertThrows(ConstraintViolationException.class,
                () -> entidades.crear(new EntidadPlanBeneficios("ENT101", " "), REFERENCIA));
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        assertThrows(ConstraintViolationException.class,
                () -> entidades.cambiarNombre("ENT101", " ", REFERENCIA));
        assertEquals("Entidad de prueba", entidades.buscarPorCodigo("ENT101").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-038 Esc. 9: un nombre de menos de 3 caracteres no se guarda")
    void esc9_nombreQueNoCumpleSuNumeral() {
        assertThrows(ConstraintViolationException.class,
                () -> entidades.crear(new EntidadPlanBeneficios("ENT101", "AB"), REFERENCIA));
        assertTrue(entidades.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-038 Esc. 10: un cambio oficial de nombre deja el anterior en el historial")
    void esc10_cambioOficialDeNombre() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        entidades.cambiarNombre("ENT101", "Nombre nuevo", REFERENCIA);
        assertEquals("Nombre nuevo", entidades.buscarPorCodigo("ENT101").orElseThrow().getNombre());
        CatalogoCambio cambio = ultimoCambio("ENT101");
        assertEquals(CatalogoCambio.Tipo.NOMBRE, cambio.getTipo());
        assertEquals("Entidad de prueba", cambio.getValorAnterior());
        assertEquals("Nombre nuevo", cambio.getValorNuevo());
    }

    @Test
    @DisplayName("HU-038 Esc. 18: inactivar con motivo deja de ofrecer el valor y lo conserva")
    void esc18_inactivacionConMotivo() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        assertEquals(0, entidades.contarUsos("ENT101"));
        entidades.inactivar("ENT101", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("ENT101"));
        assertTrue(activos().isEmpty());
        assertEquals(Motivo.RETIRADO_POR_LA_FUENTE, ultimoCambio("ENT101").getMotivo());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-038 Esc. 18: el menú muestra cuántos usan la entidad antes de pedir confirmar")
    void esc18_elMenuMuestraLosUsos() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        String salida = menu("5\nENT101\n1\n" + REFERENCIA + "\ns\n0\n");
        int usos = salida.indexOf("La usan " + entidades.contarUsos("ENT101") + " pacientes");
        assertTrue(usos >= 0, salida);
        assertTrue(usos < salida.indexOf("¿Confirma?"), "los usos van antes de confirmar");
        assertEquals(Catalogo.Estado.INACTIVO, estado("ENT101"));
    }

    @Test
    @DisplayName("HU-038 Esc. 19: sin motivo no se inactiva")
    void esc19_sinMotivo() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> entidades.inactivar("ENT101", null, REFERENCIA));
        assertEquals(Catalogo.Estado.ACTIVO, estado("ENT101"));
    }

    @Test
    @DisplayName("HU-038 Esc. 19: \"Retirado por la fuente oficial\" sin referencia no inactiva")
    void esc19_retiradoSinReferencia() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> entidades.inactivar("ENT101", Motivo.RETIRADO_POR_LA_FUENTE, ""));
        assertEquals(Catalogo.Estado.ACTIVO, estado("ENT101"));
    }

    @Test
    @DisplayName("HU-038 Esc. 19: \"Creado por error\" no vale para un código de la versión vigente")
    void esc19_creadoPorErrorEnLaVersionVigente() {
        enLaVersionVigente("ENT101");
        assertThrows(IllegalArgumentException.class,
                () -> entidades.inactivar("ENT101", Motivo.CREADO_POR_ERROR, null));
        assertEquals(Catalogo.Estado.ACTIVO, estado("ENT101"));
    }

    @Test
    @DisplayName("HU-038 Esc. 20: se reactiva diciendo por qué la referencia no lo retiraba")
    void esc20_reactivacion() {
        enLaVersionVigente("ENT101");
        entidades.inactivar("ENT101", Motivo.RETIRADO_POR_LA_FUENTE, "Referencia que no lo retiraba");
        entidades.reactivar("ENT101", null, "La referencia retiraba otro código");
        assertEquals(Catalogo.Estado.ACTIVO, estado("ENT101"));
        assertEquals(List.of("ENT101"), activos());
        assertEquals("La referencia retiraba otro código", ultimoCambio("ENT101").getJustificacion());
    }

    @Test
    @DisplayName("HU-038 Esc. 21: no se reactiva lo que no está en la versión vigente sin otra referencia")
    void esc21_fueraDeLaVersionVigente() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        // Una versión cargada después que ya no trae el código.
        version(LocalDateTime.now().plusMinutes(1));
        entidades.inactivar("ENT101", Motivo.CREADO_POR_ERROR, null);
        assertThrows(IllegalArgumentException.class,
                () -> entidades.reactivar("ENT101", null, "Se necesita"));
        assertEquals(Catalogo.Estado.INACTIVO, estado("ENT101"));
    }

    @Test
    @DisplayName("HU-038 Esc. 21: inactivado con referencia, no se reactiva sin otra ni sin decir por qué")
    void esc21_sinOtraReferenciaNiPorQue() {
        enLaVersionVigente("ENT101");
        entidades.inactivar("ENT101", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertThrows(IllegalArgumentException.class, () -> entidades.reactivar("ENT101", "", " "));
        assertEquals(Catalogo.Estado.INACTIVO, estado("ENT101"));
    }

    @Test
    @DisplayName("HU-038 Esc. 22: el historial lista cada cambio con su motivo, su referencia y la hora")
    void esc22_historial() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad de prueba"), REFERENCIA);
        entidades.cambiarNombre("ENT101", "Nombre nuevo", REFERENCIA);
        entidades.inactivar("ENT101", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        List<CatalogoCambio> historial = entidades.historial("ENT101");
        assertEquals(List.of(CatalogoCambio.Tipo.CREADO, CatalogoCambio.Tipo.NOMBRE,
                CatalogoCambio.Tipo.INACTIVADO), historial.stream().map(CatalogoCambio::getTipo).toList());
        assertFalse(historial.stream().anyMatch(c -> c.getFechaHora() == null));
        assertTrue(historial.stream().allMatch(c -> REFERENCIA.equals(c.getReferenciaOficial())));
    }

    @Test
    @DisplayName("HU-038 Esc. 1: la primera carga crea una entidad activa por fila aceptada")
    void esc1_primeraCarga() {
        CatalogoVersion version = entidades.confirmarCarga(
                entidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        assertEquals(List.of("ENT101", "ENT102", "ENT103"), activos());
        assertEquals(version.getId(), entidades.buscarPorCodigo("ENT101").orElseThrow().getVersionId());
    }

    @Test
    @DisplayName("HU-038 Esc. 1: el Excel de SISPRO deja activas las 1401 habilitadas")
    void esc1_primeraCargaDelExcelDeSispro() {
        ResultadoCarga carga = entidades.leerCarga(SISPRO, TABLA_EAPB);
        assertEquals(1717, carga.getFilasLeidas());
        assertEquals(List.of("UT001_", "UT004_"),
                carga.getRechazadas().stream().map(ResultadoCarga.Rechazo::codigo).sorted().toList());
        assertEquals(314, carga.getDeshabilitados().size());
        entidades.confirmarCarga(carga, Map.of());
        assertEquals(1401, entidades.listarActivos().size());
        assertEquals(1715, entidades.listar().size());
        for (String codigo : List.of("05001", "EPS037", "13-5")) {
            assertEquals(Catalogo.Estado.ACTIVO, estado(codigo), codigo);
        }
        String inactivo = carga.getDeshabilitados().iterator().next();
        assertEquals(Catalogo.Estado.INACTIVO, estado(inactivo));
        assertEquals(Motivo.RETIRADO_POR_LA_FUENTE, ultimoCambio(inactivo).getMotivo());
    }

    @Test
    @DisplayName("HU-038 Esc. 2: las filas con errores se rechazan, y una recarga completa sin inactivar")
    void esc2_filasConErroresYRecarga() {
        ResultadoCarga carga = entidades.leerCarga(
                csv("código;nombre\nENT101;Entidad uno\nENT_12;Guion bajo\nENT103;\n"), PRIMERA);
        assertEquals(3, carga.getFilasLeidas());
        assertEquals(List.of(2, 3), carga.getRechazadas().stream().map(ResultadoCarga.Rechazo::numero).toList());
        entidades.confirmarCarga(carga, Map.of());
        ResultadoCarga recarga = entidades.leerCarga(csv(TRES), PRIMERA);
        assertEquals(ResultadoCarga.Tipo.RECARGA, recarga.getTipo());
        assertEquals(List.of("ENT102", "ENT103"), recarga.getNuevos());
        entidades.confirmarCarga(recarga, Map.of());
        assertEquals(List.of("ENT101", "ENT102", "ENT103"), activos());
    }

    @Test
    @DisplayName("HU-038 Esc. 3: un archivo sin la columna del nombre se rechaza completo")
    void esc3_faltaLaColumnaDelNombre() {
        assertThrows(IllegalArgumentException.class,
                () -> entidades.leerCarga(csv("código\nENT101\n"), PRIMERA));
    }

    @Test
    @DisplayName("HU-038 Esc. 3: una fecha anterior a la de la versión vigente rechaza el archivo")
    void esc3_fechaAnteriorALaVigente() {
        entidades.confirmarCarga(entidades.leerCarga(csv(TRES), SEGUNDA), Map.of());
        assertThrows(IllegalArgumentException.class, () -> entidades.leerCarga(csv(TRES), PRIMERA));
    }

    @Test
    @DisplayName("HU-038 Esc. 3: una fecha de publicación futura rechaza el archivo completo")
    void esc3_fechaFutura() {
        DatosVersion futura = new DatosVersion("Fuente de prueba", null, LocalDate.of(2026, 10, 18),
                "Versión futura", LocalDate.now().plusDays(1));
        assertThrows(IllegalArgumentException.class, () -> entidades.leerCarga(csv(TRES), futura));
    }

    @Test
    @DisplayName("HU-038 Esc. 4: dos filas con el mismo código se rechazan las dos")
    void esc4_codigoRepetido() {
        ResultadoCarga carga = entidades.leerCarga(csv(TRES + "ENT101;Otra vez\n"), PRIMERA);
        assertEquals(2, carga.getRechazadas().size());
        assertEquals(List.of("ENT102", "ENT103"),
                carga.getAceptadas().stream().map(ResultadoCarga.Fila::codigo).toList());
    }

    @Test
    @DisplayName("HU-038 Esc. 5: una versión nueva crea, cambia nombres, reactiva e inactiva")
    void esc5_versionNueva() {
        entidades.confirmarCarga(entidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        entidades.inactivar("ENT103", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        ResultadoCarga carga = entidades.leerCarga(
                csv("código;nombre\nENT102;Entidad dos renombrada\nENT103;Entidad tres\nENT104;Nueva\n"),
                SEGUNDA);
        assertEquals(List.of("ENT104"), carga.getNuevos());
        assertEquals("Entidad dos renombrada", carga.getCambianDeNombre().get(0).ahora());
        assertEquals(List.of("ENT103"), carga.getVuelven());
        assertEquals(List.of("ENT101"), carga.getYaNoEstan());
        entidades.confirmarCarga(carga, Map.of());
        assertEquals(List.of("ENT102", "ENT103", "ENT104"), activos());
        assertEquals(Motivo.NO_ESTA_EN_LA_VERSION, ultimoCambio("ENT101").getMotivo());
        assertEquals("Entidad dos", ultimoCambio("ENT102").getValorAnterior());
    }

    @Test
    @DisplayName("HU-038 Esc. 6: una fila rechazada de un código existente lo deja como estaba")
    void esc6_versionNuevaConFilasRechazadas() {
        entidades.confirmarCarga(entidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        ResultadoCarga carga = entidades.leerCarga(csv(TRES.replace("ENT101;Entidad uno", "ENT101;")),
                SEGUNDA);
        assertTrue(carga.getYaNoEstan().isEmpty());
        entidades.confirmarCarga(carga, Map.of());
        assertEquals("Entidad uno", entidades.buscarPorCodigo("ENT101").orElseThrow().getNombre());
        assertEquals(Catalogo.Estado.ACTIVO, estado("ENT101"));
    }

    @Test
    @DisplayName("HU-038 Esc. 11: leer una carga y no confirmarla deja el catálogo igual")
    void esc11_cancelarLaCarga() {
        entidades.leerCarga(csv(TRES), PRIMERA);
        assertTrue(entidades.listar().isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-038 Esc. 11: cancelar una creación en el menú deja el catálogo igual")
    void esc11_cancelarLaCreacion() {
        menu("2\nENT101\nEntidad uno\n" + REFERENCIA + "\nn\n0\n");
        assertTrue(entidades.listar().isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-038 Esc. 11: cancelar una inactivación en el menú deja el catálogo igual")
    void esc11_cancelarLaInactivacion() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad uno"), REFERENCIA);
        menu("5\nENT101\n1\n" + REFERENCIA + "\nn\n0\n");
        assertEquals(Catalogo.Estado.ACTIVO, estado("ENT101"));
    }

    @Test
    @DisplayName("HU-038 Esc. 11: leer una versión nueva y no confirmarla deja el catálogo igual")
    void esc11_cancelarLaVersionNueva() {
        entidades.confirmarCarga(entidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        entidades.leerCarga(csv(TRES.replace("ENT103;Entidad tres\n", "")), SEGUNDA);
        assertEquals(Catalogo.Estado.ACTIVO, estado("ENT103"));
        assertEquals("Versión de prueba 1", entidades.versionVigente().orElseThrow().getVersion());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-038 Esc. 11: cancelar un cambio de nombre en el menú deja el catálogo igual")
    void esc11_cancelarElCambio() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad uno"), REFERENCIA);
        menu("3\nENT101\nOtro nombre\n" + REFERENCIA + "\nn\n0\n");
        assertEquals("Entidad uno", entidades.buscarPorCodigo("ENT101").orElseThrow().getNombre());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-038 Esc. 11: cancelar una reactivación en el menú deja el catálogo igual")
    void esc11_cancelarLaReactivacion() {
        entidades.crear(new EntidadPlanBeneficios("ENT101", "Entidad uno"), REFERENCIA);
        entidades.inactivar("ENT101", Motivo.CREADO_POR_ERROR, null);
        menu("6\nENT101\n\nLa referencia retiraba otro código\nn\n0\n");
        assertEquals(Catalogo.Estado.INACTIVO, estado("ENT101"));
    }

    @Test
    @DisplayName("HU-038 Esc. 12: la consulta trae el filtro, cuántas hay activas y la versión vigente")
    void esc12_consulta() {
        entidades.confirmarCarga(entidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        entidades.inactivar("ENT103", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        Consulta<EntidadPlanBeneficios> todas = entidades.consultar(CrudCatalogo.Filtro.TODOS);
        assertEquals(3, todas.valores().size());
        assertEquals(2, todas.activos());
        assertEquals("Versión de prueba 1", todas.vigente().orElseThrow().getVersion());
        assertEquals(List.of("ENT103"), entidades.consultar(CrudCatalogo.Filtro.INACTIVOS).valores()
                .stream().map(Catalogo::getCodigo).toList());
        assertEquals(List.of(3L, 0L, 0L), List.of(entidades.historialDeVersiones().get(0).creados(),
                entidades.historialDeVersiones().get(0).cambiados(),
                entidades.historialDeVersiones().get(0).inactivados()));
    }

    @Test
    @DisplayName("HU-038 Esc. 13: la búsqueda por código trae solo la entidad con ese código")
    void esc13_busquedaPorCodigo() {
        entidades.confirmarCarga(entidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        assertEquals(List.of("ENT102"), codigos(entidades.buscar("ENT102")));
    }

    @Test
    @DisplayName("HU-038 Esc. 14: la búsqueda por nombre ignora mayúsculas y tildes y ordena por nombre")
    void esc14_busquedaPorNombre() {
        entidades.confirmarCarga(entidades.leerCarga(
                csv("código;nombre\nENT101;Salud Móvil\nENT102;Entidad Norte\nENT103;Salud Total\n"),
                PRIMERA), Map.of());
        assertEquals(List.of("ENT101", "ENT103"), codigos(entidades.buscar("SALUD")));
        assertEquals(List.of("ENT101"), codigos(entidades.buscar("movil")));
    }

    @Test
    @DisplayName("HU-038 Esc. 15: una búsqueda sin resultados lo dice")
    void esc15_sinResultados() {
        entidades.confirmarCarga(entidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        assertTrue(entidades.buscar("xyz").isEmpty());
        assertEquals("No hay entidades del plan de beneficios que coincidan con la búsqueda",
                entidades.sinCoincidencias());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-038 Esc. 15: el menú dice que la búsqueda no trajo resultados")
    void esc15_sinResultadosEnElMenu() {
        entidades.confirmarCarga(entidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        assertTrue(menu("9\nxyz\n0\n")
                .contains("No hay entidades del plan de beneficios que coincidan con la búsqueda"));
    }

    @Test
    @DisplayName("HU-038 Esc. 16: el catálogo vacío dice que falta cargar el archivo oficial")
    void esc16_catalogoVacio() {
        assertEquals("El catálogo de entidades del plan de beneficios está vacío: falta cargar el archivo"
                + " oficial",
                entidades.consultar(CrudCatalogo.Filtro.TODOS).aviso());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-038 Esc. 16: la consulta del menú dice que el catálogo está vacío")
    void esc16_catalogoVacioEnElMenu() {
        assertTrue(menu("1\n3\n0\n").contains(
                "El catálogo de entidades del plan de beneficios está vacío: falta cargar el archivo"
                + " oficial"));
    }

    @Test
    @DisplayName("HU-038 Esc. 17: la comparación con el archivo oficial lista lo que falta, sobra o cambió")
    void esc17_comprobacionContraElArchivo() {
        entidades.confirmarCarga(entidades.leerCarga(csv(TRES), PRIMERA), Map.of());
        entidades.inactivar("ENT103", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        entidades.crear(new EntidadPlanBeneficios("ENT105", "Creada a mano"), REFERENCIA);
        List<String> antes = entidades.listar().stream()
                .map(c -> c.getCodigo() + c.getNombre() + c.getEstado()).toList();
        Comprobacion comprobacion = entidades.comprobarContraElArchivo(csv(
                "código;nombre\nENT101;Entidad uno renombrada\nENT102;Entidad dos\n"
                        + "ENT103;Entidad tres\nENT104;Nueva\n"));
        assertEquals(List.of("ENT104"), comprobacion.faltan());
        assertEquals(List.of("ENT103"), comprobacion.inactivos());
        assertEquals(List.of("ENT105"), comprobacion.sobran());
        assertEquals(List.of("ENT101"), comprobacion.nombresDistintos());
        assertEquals(antes, entidades.listar().stream()
                .map(c -> c.getCodigo() + c.getNombre() + c.getEstado()).toList());
    }

    // Lo que el menú imprimió con esas respuestas, con la sesión del profesional de estadística,
    // que tiene las 11 opciones. Un test del menú lleva @Timeout, porque un menú que no sale con la
    // entrada vacía se queda en un bucle (docs/DESIGN.md, sección 3).
    private String menu(String respuestas) {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Usuarios.entrar(usuarios, sesion, Cargo.PROFESIONAL_DE_ESTADISTICA);
        try {
            new EntidadPlanBeneficiosControlador(entidades, new Terminal(
                    new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8)),
                    new PrintStream(salida, true, StandardCharsets.UTF_8)), sesion).abrir();
        } finally {
            sesion.cerrar();
        }
        return salida.toString(StandardCharsets.UTF_8);
    }

    private static List<String> codigos(List<EntidadPlanBeneficios> valores) {
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
        return entidades.buscarPorCodigo(codigo).orElseThrow().getEstado();
    }

    private List<String> activos() {
        return entidades.listarActivos().stream().map(Catalogo::getCodigo).toList();
    }

    private CatalogoCambio ultimoCambio(String codigo) {
        List<CatalogoCambio> historial = entidades.historial(codigo);
        return historial.get(historial.size() - 1);
    }

    private CatalogoVersion version(LocalDateTime cargadoEn) {
        CatalogoVersion version = new CatalogoVersion();
        version.setCatalogo("EntidadPlanBeneficios");
        version.setVersion("Versión de prueba");
        version.setFechaPublicacion(LocalDate.of(2021, 6, 25));
        version.setCargadoEn(cargadoEn);
        return registro.guardar(version);
    }

    // Como si el código hubiera llegado en la carga de la versión vigente; la carga desde el CSV es
    // la pieza siguiente de la historia.
    private void enLaVersionVigente(String codigo) {
        EntidadPlanBeneficios valor = new EntidadPlanBeneficios(codigo, "Entidad de prueba");
        valor.setVersionId(version(LocalDateTime.of(2026, 1, 1, 0, 0)).getId());
        repositorio.save(valor);
    }
}
