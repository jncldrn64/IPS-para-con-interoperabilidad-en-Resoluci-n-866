package com.ipssaludyvida.hu035;

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
import com.ipssaludyvida.comun.servicios.ResumenVersion;
import com.ipssaludyvida.hu001.Usuarios;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Cargo;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import com.ipssaludyvida.hu035.controladores.CategoriaDiscapacidadControlador;
import com.ipssaludyvida.hu035.interfaces.ContratoCategoriasDiscapacidad;
import com.ipssaludyvida.hu035.modelos.CategoriaDiscapacidad;
import com.ipssaludyvida.hu035.repositorios.CategoriaDiscapacidadRepositorio;
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
 * Los escenarios de HU-035, en docs/context/trello/tarjetas/HU-035.md. Cada método se llama
 * esc<N>_<alternativa>, por el número del escenario y el caso de su "Dado" que prueba. El código
 * 09 no está en las hojas 14 y 15, así que sirve de valor de prueba.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU035CategoriasDiscapacidadTest {

    static final String REFERENCIA = "Resolución de prueba";
    static final String PRUEBA = "Categoría de prueba";
    static final Path HOJAS = Path.of("src/test/resources/cargas/discapacidad-hojas-14-15.csv");
    static final DatosVersion RESOLUCION_866 = new DatosVersion(
            "Resolución 866 de 2021, anexo técnico, numeral 10, hojas 14 y 15",
            "https://www.minsalud.gov.co", LocalDate.of(2026, 11, 2), "Resolución 866 de 2021",
            LocalDate.of(2021, 6, 25));
    static final DatosVersion VERSION_2022 = new DatosVersion("Resolución de prueba", null,
            LocalDate.of(2026, 11, 2), "Versión de prueba 2022", LocalDate.of(2022, 1, 1));

    @TempDir Path carpeta;

    @Autowired UsuarioRepositorio usuarios;
    @Autowired ContratoSesion sesion;
    @Autowired ContratoCategoriasDiscapacidad categorias;
    @Autowired CategoriaDiscapacidadRepositorio repositorio;
    @Autowired RegistroCatalogos registro;

    @Test
    @DisplayName("HU-035 Esc. 1: la primera carga crea las 8 categorías activas, con su versión")
    void esc1_primeraCarga() {
        ResultadoCarga carga = categorias.leerCarga(HOJAS, RESOLUCION_866);
        assertEquals(ResultadoCarga.Tipo.PRIMERA, carga.getTipo());
        assertTrue(carga.sePuedeGuardar());
        CatalogoVersion version = categorias.confirmarCarga(carga, Map.of());
        assertEquals(List.of("01", "02", "03", "04", "05", "06", "07", "08"), activos());
        assertEquals(version.getId(),
                categorias.buscarPorCodigo("08").orElseThrow().getVersionId());
        assertEquals(version.getId(), categorias.versionVigente().orElseThrow().getId());
        assertEquals(LocalDate.of(2021, 6, 25), version.getFechaPublicacion());
    }

    @Test
    @DisplayName("HU-035 Esc. 1: \"psicosocial\" se guarda como \"sicosocial\", como en la hoja, con aviso")
    void esc1_nombreDistintoDelDeLaHoja() throws IOException {
        Path archivo = csv(Files.readString(HOJAS).replace("sicosocial", "psicosocial"));
        ResultadoCarga carga = categorias.leerCarga(archivo, RESOLUCION_866);
        assertEquals(1, carga.getAvisos().size());
        categorias.confirmarCarga(carga, Map.of());
        assertEquals("Discapacidad sicosocial (mental)",
                categorias.buscarPorCodigo("05").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-035 Esc. 2: si falta un código de las hojas 14 y 15, la carga no se puede guardar")
    void esc2_faltaUnCodigoDeLaHoja() throws IOException {
        Path archivo = csv(Files.readString(HOJAS).replace("03;Discapacidad auditiva\n", ""));
        ResultadoCarga carga = categorias.leerCarga(archivo, RESOLUCION_866);
        assertEquals(List.of("falta el código 03 de las hojas 14 y 15"), carga.getBloqueos());
        assertThrows(IllegalStateException.class,
                () -> categorias.confirmarCarga(carga, Map.of()));
        assertTrue(categorias.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-035 Esc. 2: si sobra un código que las hojas no tienen, la carga no se puede guardar")
    void esc2_sobraUnCodigo() throws IOException {
        Path archivo = csv(Files.readString(HOJAS) + "09;Categoría que la hoja no tiene\n");
        ResultadoCarga carga = categorias.leerCarga(archivo, RESOLUCION_866);
        assertEquals(List.of("sobra el código 09, que las hojas 14 y 15 no tienen"),
                carga.getBloqueos());
        assertTrue(categorias.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-035 Esc. 3: una fila con un código de 3 caracteres se rechaza y las demás se guardan")
    void esc3_filasConErrores() throws IOException {
        Path archivo = csv(Files.readString(HOJAS) + "100;Valor de 3 caracteres\n");
        ResultadoCarga carga = categorias.leerCarga(archivo, RESOLUCION_866);
        assertEquals(9, carga.getFilasLeidas());
        assertEquals(8, carga.getAceptadas().size());
        ResultadoCarga.Rechazo rechazo = carga.getRechazadas().get(0);
        assertEquals(9, rechazo.numero());
        assertTrue(rechazo.motivo().contains("2 dígitos"), rechazo.motivo());
        Path lista = categorias.escribirRechazadas(carga, carpeta.resolve("rechazadas.csv"));
        assertTrue(Files.exists(lista));
        categorias.confirmarCarga(carga, Map.of());
        assertEquals(8, activos().size());
    }

    @Test
    @DisplayName("HU-035 Esc. 4: un archivo sin la columna del nombre se rechaza completo")
    void esc4_faltaLaColumnaDelNombre() {
        Path archivo = csv("código;descripción\n01;Discapacidad física\n");
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> categorias.leerCarga(archivo, RESOLUCION_866));
        assertTrue(error.getMessage().contains("columna nombre"), error.getMessage());
    }

    @Test
    @DisplayName("HU-035 Esc. 4: una fecha de publicación futura rechaza el archivo completo")
    void esc4_fechaFutura() {
        DatosVersion futura = new DatosVersion("Fuente", null, LocalDate.of(2026, 11, 2),
                "Versión futura", registro.hoy().plusDays(1));
        assertThrows(IllegalArgumentException.class, () -> categorias.leerCarga(HOJAS, futura));
    }

    @Test
    @DisplayName("HU-035 Esc. 4: una fecha anterior a la vigente se rechaza y dice cuál es la vigente")
    void esc4_fechaAnteriorALaVigente() {
        categorias.confirmarCarga(categorias.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        DatosVersion anterior = new DatosVersion("Fuente", null, LocalDate.of(2026, 11, 2),
                "Versión vieja", LocalDate.of(2020, 1, 1));
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> categorias.leerCarga(HOJAS, anterior));
        assertTrue(error.getMessage().contains("Resolución 866 de 2021"), error.getMessage());
    }

    @Test
    @DisplayName("HU-035 Esc. 5: dos filas con el mismo código se rechazan las dos")
    void esc5_codigoRepetido() throws IOException {
        Path archivo = csv(Files.readString(HOJAS) + "01;Discapacidad física otra vez\n");
        ResultadoCarga carga = categorias.leerCarga(archivo, RESOLUCION_866);
        assertEquals(2, carga.getRechazadas().size());
        assertTrue(carga.getRechazadas().stream()
                .allMatch(r -> r.motivo().equals("código repetido")));
    }

    @Test
    @DisplayName("HU-035 Esc. 6: una versión nueva crea, reactiva e inactiva, y avisa lo que deshace")
    void esc6_versionNueva() throws IOException {
        categorias.confirmarCarga(categorias.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        categorias.inactivar("03", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        Path archivo = csv(Files.readString(HOJAS).replace("02;Discapacidad visual\n", "")
                + "09;Categoría nueva\n");
        ResultadoCarga carga = categorias.leerCarga(archivo, VERSION_2022);
        assertEquals(ResultadoCarga.Tipo.VERSION_NUEVA, carga.getTipo());
        assertEquals(List.of("09"), carga.getNuevos());
        assertEquals(List.of("03"), carga.getVuelven());
        assertEquals(List.of("02"), carga.getYaNoEstan());
        assertEquals(1, carga.getAvisos().size(), "la versión deshace la inactivación de 03");
        CatalogoVersion version = categorias.confirmarCarga(carga, Map.of("02", "09"));
        assertEquals(version.getId(), categorias.versionVigente().orElseThrow().getId());
        assertEquals(Catalogo.Estado.ACTIVO, estado("09"));
        assertEquals(Motivo.VUELVE_EN_LA_VERSION, ultimoCambio("03").getMotivo());
        assertEquals(Motivo.REEMPLAZADO_POR, ultimoCambio("02").getMotivo());
        assertEquals("09", ultimoCambio("02").getCodigoReemplazo());
    }

    @Test
    @DisplayName("HU-035 Esc. 7: una fila rechazada de un código existente lo deja como estaba")
    void esc7_versionNuevaConFilasRechazadas() throws IOException {
        categorias.confirmarCarga(categorias.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        Path archivo = csv(Files.readString(HOJAS).replace("02;Discapacidad visual", "02;"));
        ResultadoCarga carga = categorias.leerCarga(archivo, VERSION_2022);
        assertEquals("02", carga.getRechazadas().get(0).codigo());
        assertTrue(carga.getYaNoEstan().isEmpty());
        CatalogoVersion version = categorias.confirmarCarga(carga, Map.of());
        CategoriaDiscapacidad visual = categorias.buscarPorCodigo("02").orElseThrow();
        assertEquals("Discapacidad visual", visual.getNombre());
        assertEquals(Catalogo.Estado.ACTIVO, visual.getEstado());
        assertEquals(version.getId(), visual.getVersionId());
    }

    @Test
    @DisplayName("HU-035 Esc. 8: un cambio oficial de código inactiva el viejo y crea el nuevo")
    void esc8_cambioOficialDeCodigo() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        categorias.cambiarCodigo("09", new CategoriaDiscapacidad("10", "Valor nuevo"), REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("09"));
        assertEquals(Catalogo.Estado.ACTIVO, estado("10"));
        CatalogoCambio ultimo = ultimoCambio("09");
        assertEquals(Motivo.REEMPLAZADO_POR, ultimo.getMotivo());
        assertEquals("10", ultimo.getCodigoReemplazo());
        assertEquals(List.of("10"), activos());
    }

    @Test
    @DisplayName("HU-035 Esc. 9: un valor creado por cambio oficial queda activo con su referencia")
    void esc9_creacionPorCambioOficial() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        assertEquals(Catalogo.Estado.ACTIVO, estado("09"));
        CatalogoCambio creado = categorias.historial("09").get(0);
        assertEquals(CatalogoCambio.Tipo.CREADO, creado.getTipo());
        assertEquals(REFERENCIA, creado.getReferenciaOficial());
    }

    @Test
    @DisplayName("HU-035 Esc. 10: sin la referencia oficial no se crea ni se cambia nada")
    void esc10_faltaLaReferenciaOficial() {
        assertThrows(IllegalArgumentException.class,
                () -> categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), " "));
        assertTrue(categorias.buscarPorCodigo("09").isEmpty());
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> categorias.cambiarNombre("09", "Otro nombre", null));
        assertEquals(PRUEBA, categorias.buscarPorCodigo("09").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-035 Esc. 10: un código que ya existe no se vuelve a crear")
    void esc10_elCodigoQueSeCreaYaExiste() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        assertThrows(IllegalStateException.class,
                () -> categorias.crear(new CategoriaDiscapacidad("09", "Otro nombre"), REFERENCIA));
        assertEquals(PRUEBA, categorias.buscarPorCodigo("09").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-035 Esc. 10: un código que no cumple el numeral 10 no se guarda")
    void esc10_datoQueNoCumpleSuNumeral() {
        for (String codigo : new String[] {"1", "123", "123456", "0A", null}) {
            assertThrows(ConstraintViolationException.class,
                    () -> categorias.crear(new CategoriaDiscapacidad(codigo, "X"), REFERENCIA),
                    "aceptó el código " + codigo);
        }
        assertTrue(categorias.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-035 Esc. 10: un nombre vacío no se guarda")
    void esc10_nombreVacio() {
        assertThrows(ConstraintViolationException.class,
                () -> categorias.crear(new CategoriaDiscapacidad("09", " "), REFERENCIA));
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        assertThrows(ConstraintViolationException.class,
                () -> categorias.cambiarNombre("09", " ", REFERENCIA));
        assertEquals(PRUEBA, categorias.buscarPorCodigo("09").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-035 Esc. 11: un cambio oficial de nombre deja el anterior en el historial")
    void esc11_cambioOficialDeNombre() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        categorias.cambiarNombre("09", "Nombre nuevo", REFERENCIA);
        assertEquals("Nombre nuevo", categorias.buscarPorCodigo("09").orElseThrow().getNombre());
        CatalogoCambio cambio = ultimoCambio("09");
        assertEquals(CatalogoCambio.Tipo.NOMBRE, cambio.getTipo());
        assertEquals(PRUEBA, cambio.getValorAnterior());
        assertEquals("Nombre nuevo", cambio.getValorNuevo());
    }

    @Test
    @DisplayName("HU-035 Esc. 12: leer una carga y no confirmarla deja el catálogo igual")
    void esc12_cancelarLaCarga() {
        categorias.leerCarga(HOJAS, RESOLUCION_866);
        assertTrue(categorias.listar().isEmpty());
        assertTrue(categorias.versionVigente().isEmpty());
    }

    @Test
    @DisplayName("HU-035 Esc. 12: leer una versión nueva y no confirmarla deja el catálogo igual")
    void esc12_cancelarLaVersionNueva() throws IOException {
        categorias.confirmarCarga(categorias.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        categorias.leerCarga(csv(Files.readString(HOJAS).replace("02;Discapacidad visual\n", "")),
                VERSION_2022);
        assertEquals(Catalogo.Estado.ACTIVO, estado("02"));
        assertEquals(LocalDate.of(2021, 6, 25),
                categorias.versionVigente().orElseThrow().getFechaPublicacion());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-035 Esc. 12: cancelar una creación en el menú deja el catálogo igual")
    void esc12_cancelarLaCreacion() {
        menu("2\n09\n" + PRUEBA + "\n" + REFERENCIA + "\nn\n0\n");
        assertTrue(categorias.listar().isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-035 Esc. 12: cancelar un cambio de nombre en el menú deja el catálogo igual")
    void esc12_cancelarElCambio() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        menu("3\n09\nOtro nombre\n" + REFERENCIA + "\nn\n0\n");
        assertEquals(PRUEBA, categorias.buscarPorCodigo("09").orElseThrow().getNombre());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-035 Esc. 12: cancelar una inactivación en el menú deja el catálogo igual")
    void esc12_cancelarLaInactivacion() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        menu("5\n09\n1\n" + REFERENCIA + "\nn\n0\n");
        assertEquals(Catalogo.Estado.ACTIVO, estado("09"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-035 Esc. 12: cancelar una reactivación en el menú deja el catálogo igual")
    void esc12_cancelarLaReactivacion() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        categorias.inactivar("09", Motivo.CREADO_POR_ERROR, null);
        menu("6\n09\n\nLa referencia retiraba otro código\nn\n0\n");
        assertEquals(Catalogo.Estado.INACTIVO, estado("09"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-035 Esc. 12: cancelar una carga en el menú deja el catálogo igual")
    void esc12_cancelarLaCargaEnElMenu() {
        menu("8\n" + HOJAS + "\nFuente\n\n2026-11-02\nResolución 866 de 2021\n2021-06-25\n"
                + "n\n0\n");
        assertTrue(categorias.listar().isEmpty());
        assertTrue(categorias.versionVigente().isEmpty());
    }

    @Test
    @DisplayName("HU-035 Esc. 13: la consulta trae el filtro, cuántas hay activas y la versión vigente")
    void esc13_consulta() {
        categorias.confirmarCarga(categorias.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        categorias.inactivar("03", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        int cambios = categorias.historial("03").size();
        Consulta<CategoriaDiscapacidad> todas = categorias.consultar(CrudCatalogo.Filtro.TODOS);
        assertEquals(8, todas.valores().size());
        assertEquals(7, todas.activos());
        assertEquals(LocalDate.of(2021, 6, 25),
                todas.vigente().orElseThrow().getFechaPublicacion());
        assertEquals(LocalDate.of(2026, 11, 2), todas.vigente().orElseThrow().getFechaDescarga());
        assertEquals(7, categorias.consultar(CrudCatalogo.Filtro.ACTIVOS).valores().size());
        assertEquals(List.of("03"), codigos(
                categorias.consultar(CrudCatalogo.Filtro.INACTIVOS).valores()));
        assertEquals(cambios, categorias.historial("03").size(), "la consulta no cambia nada");
    }

    @Test
    @DisplayName("HU-035 Esc. 13: el historial de versiones dice cuántos códigos creó, cambió e inactivó")
    void esc13_historialDeVersiones() throws IOException {
        categorias.confirmarCarga(categorias.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        categorias.inactivar("03", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        categorias.confirmarCarga(categorias.leerCarga(csv(Files.readString(HOJAS)
                .replace("02;Discapacidad visual\n", "") + "09;Categoría nueva\n"), VERSION_2022),
                Map.of());
        List<ResumenVersion> versiones = categorias.historialDeVersiones();
        assertEquals(2, versiones.size());
        assertEquals(List.of(8L, 0L, 0L), cuentas(versiones.get(0)));
        assertEquals(List.of(1L, 1L, 1L), cuentas(versiones.get(1)));
        assertEquals("Resolución de prueba", versiones.get(1).version().getFuente());
    }

    @Test
    @DisplayName("HU-035 Esc. 14: la búsqueda por código trae solo la categoría con ese código")
    void esc14_busquedaPorCodigo() {
        categorias.confirmarCarga(categorias.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        assertEquals(List.of("05"), codigos(categorias.buscar("05")));
    }

    @Test
    @DisplayName("HU-035 Esc. 15: la búsqueda por nombre ignora mayúsculas y tildes y ordena por nombre")
    void esc15_busquedaPorNombre() {
        categorias.confirmarCarga(categorias.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        // Siete de las ocho dicen "discapacidad"; solo Sordoceguera no.
        assertEquals(List.of("03", "01", "04", "07", "05", "02", "08"),
                codigos(categorias.buscar("discapacidad")));
        assertEquals(List.of("01"), codigos(categorias.buscar("FISICA")));
        assertEquals(List.of("07"), codigos(categorias.buscar("Multiple")));
    }

    @Test
    @DisplayName("HU-035 Esc. 16: una búsqueda sin resultados lo dice")
    void esc16_sinResultados() {
        categorias.confirmarCarga(categorias.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        assertTrue(categorias.buscar("xyz").isEmpty());
        assertEquals("No hay categorías de discapacidad que coincidan con la búsqueda",
                categorias.sinCoincidencias());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-035 Esc. 16: el menú dice que la búsqueda no trajo resultados")
    void esc16_sinResultadosEnElMenu() {
        categorias.confirmarCarga(categorias.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        assertTrue(menu("9\nxyz\n0\n")
                .contains("No hay categorías de discapacidad que coincidan con la búsqueda"));
    }

    @Test
    @DisplayName("HU-035 Esc. 17: el catálogo vacío dice que falta cargar el archivo oficial")
    void esc17_catalogoVacio() {
        Consulta<CategoriaDiscapacidad> consulta = categorias.consultar(CrudCatalogo.Filtro.TODOS);
        assertTrue(consulta.valores().isEmpty());
        assertEquals("El catálogo de categorías de discapacidad está vacío: falta cargar el archivo"
                + " oficial", consulta.aviso());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-035 Esc. 17: la consulta del menú dice que el catálogo está vacío")
    void esc17_catalogoVacioEnElMenu() {
        assertTrue(menu("1\n3\n0\n").contains(
                "El catálogo de categorías de discapacidad está vacío: falta cargar el archivo"
                        + " oficial"));
    }

    @Test
    @DisplayName("HU-035 Esc. 18: la comparación con las hojas 14 y 15 lista lo que falta, sobra o cambió")
    void esc18_comprobacionContraLaHoja() {
        ContratoCategoriasDiscapacidad.HOJAS_14_Y_15.forEach((codigo, nombre) -> {
            if (!codigo.equals("08")) {
                categorias.crear(new CategoriaDiscapacidad(codigo, nombre), REFERENCIA);
            }
        });
        categorias.inactivar("03", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        categorias.cambiarNombre("05", "Discapacidad psicosocial (mental)", REFERENCIA);
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        List<String> antes = foto();
        Comprobacion comprobacion = categorias.comprobarContraLaHoja();
        assertEquals(List.of("08"), comprobacion.faltan());
        assertEquals(List.of("03"), comprobacion.inactivos());
        assertEquals(List.of("09"), comprobacion.sobran());
        assertEquals(List.of("05"), comprobacion.nombresDistintos());
        assertEquals(antes, foto(), "la comparación no cambia nada");
    }

    @Test
    @DisplayName("HU-035 Esc. 18: después de la primera carga, el catálogo coincide con las hojas")
    void esc18_coincideDespuesDeLaCarga() {
        categorias.confirmarCarga(categorias.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        assertTrue(categorias.comprobarContraLaHoja().coincide());
    }

    @Test
    @DisplayName("HU-035 Esc. 19: inactivar con motivo deja de ofrecer el valor y lo conserva")
    void esc19_inactivacionConMotivo() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        assertEquals(0, categorias.contarUsos("09"));
        categorias.inactivar("09", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("09"));
        assertTrue(activos().isEmpty());
        assertEquals(Motivo.RETIRADO_POR_LA_FUENTE, ultimoCambio("09").getMotivo());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-035 Esc. 19: el menú muestra cuántos usan la categoría antes de pedir confirmar")
    void esc19_elMenuMuestraLosUsos() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        String salida = menu("5\n09\n1\n" + REFERENCIA + "\ns\n0\n");
        int usos = salida.indexOf("La usan " + categorias.contarUsos("09") + " pacientes");
        assertTrue(usos >= 0, salida);
        assertTrue(usos < salida.indexOf("¿Confirma?"), "los usos van antes de confirmar");
        assertEquals(Catalogo.Estado.INACTIVO, estado("09"));
    }

    @Test
    @DisplayName("HU-035 Esc. 20: sin motivo no se inactiva")
    void esc20_sinMotivo() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> categorias.inactivar("09", null, REFERENCIA));
        assertEquals(Catalogo.Estado.ACTIVO, estado("09"));
    }

    @Test
    @DisplayName("HU-035 Esc. 20: \"Retirado por la fuente oficial\" sin referencia no inactiva")
    void esc20_retiradoSinReferencia() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> categorias.inactivar("09", Motivo.RETIRADO_POR_LA_FUENTE, ""));
        assertEquals(Catalogo.Estado.ACTIVO, estado("09"));
    }

    @Test
    @DisplayName("HU-035 Esc. 20: \"Creado por error\" no vale para un código de la versión vigente")
    void esc20_creadoPorErrorEnLaVersionVigente() {
        enLaVersionVigente("09");
        assertThrows(IllegalArgumentException.class,
                () -> categorias.inactivar("09", Motivo.CREADO_POR_ERROR, null));
        assertEquals(Catalogo.Estado.ACTIVO, estado("09"));
    }

    @Test
    @DisplayName("HU-035 Esc. 21: se reactiva diciendo por qué la referencia no lo retiraba")
    void esc21_reactivacion() {
        enLaVersionVigente("09");
        categorias.inactivar("09", Motivo.RETIRADO_POR_LA_FUENTE, "Referencia que no lo retiraba");
        categorias.reactivar("09", null, "La referencia retiraba otro código");
        assertEquals(Catalogo.Estado.ACTIVO, estado("09"));
        assertEquals(List.of("09"), activos());
        assertEquals("La referencia retiraba otro código", ultimoCambio("09").getJustificacion());
    }

    @Test
    @DisplayName("HU-035 Esc. 22: no se reactiva lo que no está en la versión vigente sin otra referencia")
    void esc22_fueraDeLaVersionVigente() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        // Una versión cargada después que ya no trae el código.
        version(LocalDateTime.now().plusMinutes(1));
        categorias.inactivar("09", Motivo.CREADO_POR_ERROR, null);
        assertThrows(IllegalArgumentException.class,
                () -> categorias.reactivar("09", null, "Se necesita"));
        assertEquals(Catalogo.Estado.INACTIVO, estado("09"));
    }

    @Test
    @DisplayName("HU-035 Esc. 22: inactivado con referencia, no se reactiva sin otra ni sin decir por qué")
    void esc22_sinOtraReferenciaNiPorQue() {
        enLaVersionVigente("09");
        categorias.inactivar("09", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertThrows(IllegalArgumentException.class, () -> categorias.reactivar("09", "", " "));
        assertEquals(Catalogo.Estado.INACTIVO, estado("09"));
    }

    @Test
    @DisplayName("HU-035 Esc. 23: el historial lista cada cambio con su motivo, su referencia y la hora")
    void esc23_historial() {
        categorias.crear(new CategoriaDiscapacidad("09", PRUEBA), REFERENCIA);
        categorias.cambiarNombre("09", "Nombre nuevo", REFERENCIA);
        categorias.inactivar("09", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        List<CatalogoCambio> historial = categorias.historial("09");
        assertEquals(List.of(CatalogoCambio.Tipo.CREADO, CatalogoCambio.Tipo.NOMBRE,
                CatalogoCambio.Tipo.INACTIVADO),
                historial.stream().map(CatalogoCambio::getTipo).toList());
        assertFalse(historial.stream().anyMatch(c -> c.getFechaHora() == null));
        assertTrue(historial.stream().allMatch(c -> REFERENCIA.equals(c.getReferenciaOficial())));
    }

    private Path csv(String contenido) {
        try {
            return Files.writeString(Files.createTempFile(carpeta, "carga", ".csv"), contenido,
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    // Lo que el menú imprimió con esas respuestas, con la sesión del profesional de estadística,
    // que tiene las 11 opciones. Un test del menú lleva @Timeout, porque un menú que no sale con la
    // entrada vacía se queda en un bucle (docs/DESIGN.md, sección 3).
    private String menu(String respuestas) {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Usuarios.entrar(usuarios, sesion, Cargo.PROFESIONAL_DE_ESTADISTICA);
        try {
            new CategoriaDiscapacidadControlador(categorias, new Terminal(
                    new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8)),
                    new PrintStream(salida, true, StandardCharsets.UTF_8)), sesion).abrir();
        } finally {
            sesion.cerrar();
        }
        return salida.toString(StandardCharsets.UTF_8);
    }

    private static List<String> codigos(List<CategoriaDiscapacidad> valores) {
        return valores.stream().map(Catalogo::getCodigo).toList();
    }

    private static List<Long> cuentas(ResumenVersion resumen) {
        return List.of(resumen.creados(), resumen.cambiados(), resumen.inactivados());
    }

    private List<String> foto() {
        return categorias.listar().stream()
                .map(c -> c.getCodigo() + c.getNombre() + c.getEstado() + c.getVersionId())
                .toList();
    }

    private Catalogo.Estado estado(String codigo) {
        return categorias.buscarPorCodigo(codigo).orElseThrow().getEstado();
    }

    private List<String> activos() {
        return categorias.listarActivos().stream().map(Catalogo::getCodigo).toList();
    }

    private CatalogoCambio ultimoCambio(String codigo) {
        List<CatalogoCambio> historial = categorias.historial(codigo);
        return historial.get(historial.size() - 1);
    }

    private CatalogoVersion version(LocalDateTime cargadoEn) {
        CatalogoVersion version = new CatalogoVersion();
        version.setCatalogo("CategoriaDiscapacidad");
        version.setVersion("Versión de prueba");
        version.setFechaPublicacion(LocalDate.of(2021, 6, 25));
        version.setCargadoEn(cargadoEn);
        return registro.guardar(version);
    }

    // Como si el código hubiera llegado en la carga de la versión vigente, sin pasar por el CSV.
    private void enLaVersionVigente(String codigo) {
        CategoriaDiscapacidad valor = new CategoriaDiscapacidad(codigo, PRUEBA);
        valor.setVersionId(version(LocalDateTime.of(2026, 1, 1, 0, 0)).getId());
        repositorio.save(valor);
    }
}
