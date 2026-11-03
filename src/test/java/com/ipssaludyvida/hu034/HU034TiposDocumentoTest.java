package com.ipssaludyvida.hu034;

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
import com.ipssaludyvida.hu034.controladores.TipoDocumentoControlador;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu034.modelos.TipoDocumento;
import com.ipssaludyvida.hu034.repositorios.TipoDocumentoRepositorio;
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
 * Los escenarios de HU-034, en docs/context/trello/tarjetas/HU-034.md. Cada método se llama
 * esc<N>_<alternativa>, por el número del escenario y el caso de su "Dado" que prueba. El código
 * ZZ no está en la hoja 13, así que sirve de valor de prueba.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU034TiposDocumentoTest {

    static final String REFERENCIA = "Resolución de prueba";
    static final String PRUEBA = "Tipo de prueba";
    static final Path HOJAS = Path.of("data/catalogos/tipos-documento-hoja-13.csv");
    static final DatosVersion RESOLUCION_866 = new DatosVersion(
            "Resolución 866 de 2021, anexo técnico, numeral 2, hoja 13",
            "https://www.minsalud.gov.co", LocalDate.of(2026, 11, 2), "Resolución 866 de 2021",
            LocalDate.of(2021, 6, 25));
    static final DatosVersion VERSION_2022 = new DatosVersion("Resolución de prueba", null,
            LocalDate.of(2026, 11, 2), "Versión de prueba 2022", LocalDate.of(2022, 1, 1));

    @TempDir Path carpeta;

    @Autowired UsuarioRepositorio usuarios;
    @Autowired ContratoSesion sesion;
    @Autowired ContratoTiposDocumento tipos;
    @Autowired TipoDocumentoRepositorio repositorio;
    @Autowired RegistroCatalogos registro;

    @Test
    @DisplayName("HU-034 Esc. 1: la primera carga crea los 14 tipos activos, con su versión")
    void esc1_primeraCarga() {
        ResultadoCarga carga = tipos.leerCarga(HOJAS, RESOLUCION_866);
        assertEquals(ResultadoCarga.Tipo.PRIMERA, carga.getTipo());
        assertTrue(carga.sePuedeGuardar());
        CatalogoVersion version = tipos.confirmarCarga(carga, Map.of());
        assertEquals(List.of("AS", "CC", "CD", "CE", "CN", "DE", "MS", "PA", "PE", "PT", "RC", "SC", "SI", "TI"), activos());
        assertEquals(version.getId(),
                tipos.buscarPorCodigo("TI").orElseThrow().getVersionId());
        assertEquals(version.getId(), tipos.versionVigente().orElseThrow().getId());
        assertEquals(LocalDate.of(2021, 6, 25), version.getFechaPublicacion());
    }

    @Test
    @DisplayName("HU-034 Esc. 1: un nombre distinto del de la hoja se guarda como en la hoja, con aviso")
    void esc1_nombreDistintoDelDeLaHoja() throws IOException {
        Path archivo = csv(Files.readString(HOJAS)
                .replace("CC;Cédula ciudadanía", "CC;Cedula de ciudadania"));
        ResultadoCarga carga = tipos.leerCarga(archivo, RESOLUCION_866);
        assertEquals(1, carga.getAvisos().size());
        tipos.confirmarCarga(carga, Map.of());
        assertEquals("Cédula ciudadanía",
                tipos.buscarPorCodigo("CC").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-034 Esc. 2: si falta un código de la hoja 13, la carga no se puede guardar")
    void esc2_faltaUnCodigoDeLaHoja() throws IOException {
        Path archivo = csv(Files.readString(HOJAS).replace("CD;Carné diplomático\n", ""));
        ResultadoCarga carga = tipos.leerCarga(archivo, RESOLUCION_866);
        assertEquals(List.of("falta el código CD de la hoja 13"), carga.getBloqueos());
        assertThrows(IllegalStateException.class,
                () -> tipos.confirmarCarga(carga, Map.of()));
        assertTrue(tipos.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-034 Esc. 2: si sobra un código que la hoja 13 no tiene, la carga no se puede guardar")
    void esc2_sobraUnCodigo() throws IOException {
        Path archivo = csv(Files.readString(HOJAS) + "ZZ;Tipo que la hoja no tiene\n");
        ResultadoCarga carga = tipos.leerCarga(archivo, RESOLUCION_866);
        assertEquals(List.of("sobra el código ZZ, que la hoja 13 no tiene"),
                carga.getBloqueos());
        assertTrue(tipos.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-034 Esc. 3: una fila con un código de 3 caracteres se rechaza y las demás se guardan")
    void esc3_filasConErrores() throws IOException {
        Path archivo = csv(Files.readString(HOJAS) + "ABC;Valor de 3 caracteres\n");
        ResultadoCarga carga = tipos.leerCarga(archivo, RESOLUCION_866);
        assertEquals(15, carga.getFilasLeidas());
        assertEquals(14, carga.getAceptadas().size());
        ResultadoCarga.Rechazo rechazo = carga.getRechazadas().get(0);
        assertEquals(15, rechazo.numero());
        assertTrue(rechazo.motivo().contains("2 letras"), rechazo.motivo());
        Path lista = tipos.escribirRechazadas(carga, carpeta.resolve("rechazadas.csv"));
        assertTrue(Files.exists(lista));
        tipos.confirmarCarga(carga, Map.of());
        assertEquals(14, activos().size());
    }

    @Test
    @DisplayName("HU-034 Esc. 4: un archivo sin la columna del nombre se rechaza completo")
    void esc4_faltaLaColumnaDelNombre() {
        Path archivo = csv("código;descripción\nCC;Cédula ciudadanía\n");
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> tipos.leerCarga(archivo, RESOLUCION_866));
        assertTrue(error.getMessage().contains("columna nombre"), error.getMessage());
    }

    @Test
    @DisplayName("HU-034 Esc. 4: una fecha de publicación futura rechaza el archivo completo")
    void esc4_fechaFutura() {
        DatosVersion futura = new DatosVersion("Fuente", null, LocalDate.of(2026, 11, 2),
                "Versión futura", registro.hoy().plusDays(1));
        assertThrows(IllegalArgumentException.class, () -> tipos.leerCarga(HOJAS, futura));
    }

    @Test
    @DisplayName("HU-034 Esc. 4: una fecha anterior a la vigente se rechaza y dice cuál es la vigente")
    void esc4_fechaAnteriorALaVigente() {
        tipos.confirmarCarga(tipos.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        DatosVersion anterior = new DatosVersion("Fuente", null, LocalDate.of(2026, 11, 2),
                "Versión vieja", LocalDate.of(2020, 1, 1));
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> tipos.leerCarga(HOJAS, anterior));
        assertTrue(error.getMessage().contains("Resolución 866 de 2021"), error.getMessage());
    }

    @Test
    @DisplayName("HU-034 Esc. 5: dos filas con el mismo código se rechazan las dos")
    void esc5_codigoRepetido() throws IOException {
        Path archivo = csv(Files.readString(HOJAS) + "CC;Cédula otra vez\n");
        ResultadoCarga carga = tipos.leerCarga(archivo, RESOLUCION_866);
        assertEquals(2, carga.getRechazadas().size());
        assertTrue(carga.getRechazadas().stream()
                .allMatch(r -> r.motivo().equals("código repetido")));
    }

    @Test
    @DisplayName("HU-034 Esc. 6: una versión nueva crea, reactiva e inactiva, y avisa lo que deshace")
    void esc6_versionNueva() throws IOException {
        tipos.confirmarCarga(tipos.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        tipos.inactivar("CD", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        Path archivo = csv(Files.readString(HOJAS).replace("CE;Cédula de extranjería\n", "")
                + "ZZ;Tipo nuevo\n");
        ResultadoCarga carga = tipos.leerCarga(archivo, VERSION_2022);
        assertEquals(ResultadoCarga.Tipo.VERSION_NUEVA, carga.getTipo());
        assertEquals(List.of("ZZ"), carga.getNuevos());
        assertEquals(List.of("CD"), carga.getVuelven());
        assertEquals(List.of("CE"), carga.getYaNoEstan());
        assertEquals(1, carga.getAvisos().size(), "la versión deshace la inactivación de 03");
        CatalogoVersion version = tipos.confirmarCarga(carga, Map.of("CE", "ZZ"));
        assertEquals(version.getId(), tipos.versionVigente().orElseThrow().getId());
        assertEquals(Catalogo.Estado.ACTIVO, estado("ZZ"));
        assertEquals(Motivo.VUELVE_EN_LA_VERSION, ultimoCambio("CD").getMotivo());
        assertEquals(Motivo.REEMPLAZADO_POR, ultimoCambio("CE").getMotivo());
        assertEquals("ZZ", ultimoCambio("CE").getCodigoReemplazo());
    }

    @Test
    @DisplayName("HU-034 Esc. 7: una fila rechazada de un código existente lo deja como estaba")
    void esc7_versionNuevaConFilasRechazadas() throws IOException {
        tipos.confirmarCarga(tipos.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        Path archivo = csv(Files.readString(HOJAS).replace("CE;Cédula de extranjería", "CE;"));
        ResultadoCarga carga = tipos.leerCarga(archivo, VERSION_2022);
        assertEquals("CE", carga.getRechazadas().get(0).codigo());
        assertTrue(carga.getYaNoEstan().isEmpty());
        CatalogoVersion version = tipos.confirmarCarga(carga, Map.of());
        TipoDocumento extranjeria = tipos.buscarPorCodigo("CE").orElseThrow();
        assertEquals("Cédula de extranjería", extranjeria.getNombre());
        assertEquals(Catalogo.Estado.ACTIVO, extranjeria.getEstado());
        assertEquals(version.getId(), extranjeria.getVersionId());
    }

    @Test
    @DisplayName("HU-034 Esc. 8: un cambio oficial de código inactiva el viejo y crea el nuevo")
    void esc8_cambioOficialDeCodigo() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        tipos.cambiarCodigo("ZZ", new TipoDocumento("ZY", "Valor nuevo"), REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("ZZ"));
        assertEquals(Catalogo.Estado.ACTIVO, estado("ZY"));
        CatalogoCambio ultimo = ultimoCambio("ZZ");
        assertEquals(Motivo.REEMPLAZADO_POR, ultimo.getMotivo());
        assertEquals("ZY", ultimo.getCodigoReemplazo());
        assertEquals(List.of("ZY"), activos());
    }

    @Test
    @DisplayName("HU-034 Esc. 9: un valor creado por cambio oficial queda activo con su referencia")
    void esc9_creacionPorCambioOficial() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        assertEquals(Catalogo.Estado.ACTIVO, estado("ZZ"));
        CatalogoCambio creado = tipos.historial("ZZ").get(0);
        assertEquals(CatalogoCambio.Tipo.CREADO, creado.getTipo());
        assertEquals(REFERENCIA, creado.getReferenciaOficial());
    }

    @Test
    @DisplayName("HU-034 Esc. 10: sin la referencia oficial no se crea ni se cambia nada")
    void esc10_faltaLaReferenciaOficial() {
        assertThrows(IllegalArgumentException.class,
                () -> tipos.crear(new TipoDocumento("ZZ", PRUEBA), " "));
        assertTrue(tipos.buscarPorCodigo("ZZ").isEmpty());
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> tipos.cambiarNombre("ZZ", "Otro nombre", null));
        assertEquals(PRUEBA, tipos.buscarPorCodigo("ZZ").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-034 Esc. 10: un código que ya existe no se vuelve a crear")
    void esc10_elCodigoQueSeCreaYaExiste() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        assertThrows(IllegalStateException.class,
                () -> tipos.crear(new TipoDocumento("ZZ", "Otro nombre"), REFERENCIA));
        assertEquals(PRUEBA, tipos.buscarPorCodigo("ZZ").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-034 Esc. 10: un código que no cumple el numeral 10 no se guarda")
    void esc10_datoQueNoCumpleSuNumeral() {
        for (String codigo : new String[] {"C", "CCC", "12", "cc", null}) {
            assertThrows(ConstraintViolationException.class,
                    () -> tipos.crear(new TipoDocumento(codigo, "X"), REFERENCIA),
                    "aceptó el código " + codigo);
        }
        assertTrue(tipos.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-034 Esc. 10: un nombre vacío no se guarda")
    void esc10_nombreVacio() {
        assertThrows(ConstraintViolationException.class,
                () -> tipos.crear(new TipoDocumento("ZZ", " "), REFERENCIA));
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        assertThrows(ConstraintViolationException.class,
                () -> tipos.cambiarNombre("ZZ", " ", REFERENCIA));
        assertEquals(PRUEBA, tipos.buscarPorCodigo("ZZ").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-034 Esc. 11: un cambio oficial de nombre deja el anterior en el historial")
    void esc11_cambioOficialDeNombre() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        tipos.cambiarNombre("ZZ", "Nombre nuevo", REFERENCIA);
        assertEquals("Nombre nuevo", tipos.buscarPorCodigo("ZZ").orElseThrow().getNombre());
        CatalogoCambio cambio = ultimoCambio("ZZ");
        assertEquals(CatalogoCambio.Tipo.NOMBRE, cambio.getTipo());
        assertEquals(PRUEBA, cambio.getValorAnterior());
        assertEquals("Nombre nuevo", cambio.getValorNuevo());
    }

    @Test
    @DisplayName("HU-034 Esc. 12: leer una carga y no confirmarla deja el catálogo igual")
    void esc12_cancelarLaCarga() {
        tipos.leerCarga(HOJAS, RESOLUCION_866);
        assertTrue(tipos.listar().isEmpty());
        assertTrue(tipos.versionVigente().isEmpty());
    }

    @Test
    @DisplayName("HU-034 Esc. 12: leer una versión nueva y no confirmarla deja el catálogo igual")
    void esc12_cancelarLaVersionNueva() throws IOException {
        tipos.confirmarCarga(tipos.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        tipos.leerCarga(csv(Files.readString(HOJAS).replace("CE;Cédula de extranjería\n", "")),
                VERSION_2022);
        assertEquals(Catalogo.Estado.ACTIVO, estado("CE"));
        assertEquals(LocalDate.of(2021, 6, 25),
                tipos.versionVigente().orElseThrow().getFechaPublicacion());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-034 Esc. 12: cancelar una creación en el menú deja el catálogo igual")
    void esc12_cancelarLaCreacion() {
        menu("2\nZZ\n" + PRUEBA + "\n" + REFERENCIA + "\nn\n0\n");
        assertTrue(tipos.listar().isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-034 Esc. 12: cancelar un cambio de nombre en el menú deja el catálogo igual")
    void esc12_cancelarElCambio() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        menu("3\nZZ\nOtro nombre\n" + REFERENCIA + "\nn\n0\n");
        assertEquals(PRUEBA, tipos.buscarPorCodigo("ZZ").orElseThrow().getNombre());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-034 Esc. 12: cancelar una inactivación en el menú deja el catálogo igual")
    void esc12_cancelarLaInactivacion() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        menu("5\nZZ\n1\n" + REFERENCIA + "\nn\n0\n");
        assertEquals(Catalogo.Estado.ACTIVO, estado("ZZ"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-034 Esc. 12: cancelar una reactivación en el menú deja el catálogo igual")
    void esc12_cancelarLaReactivacion() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        tipos.inactivar("ZZ", Motivo.CREADO_POR_ERROR, null);
        menu("6\nZZ\n\nLa referencia retiraba otro código\nn\n0\n");
        assertEquals(Catalogo.Estado.INACTIVO, estado("ZZ"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-034 Esc. 12: cancelar una carga en el menú deja el catálogo igual")
    void esc12_cancelarLaCargaEnElMenu() {
        menu("8\n" + HOJAS + "\nFuente\n\n2026-11-02\nResolución 866 de 2021\n2021-06-25\n"
                + "n\n0\n");
        assertTrue(tipos.listar().isEmpty());
        assertTrue(tipos.versionVigente().isEmpty());
    }

    @Test
    @DisplayName("HU-034 Esc. 13: la consulta trae el filtro, cuántas hay activas y la versión vigente")
    void esc13_consulta() {
        tipos.confirmarCarga(tipos.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        tipos.inactivar("CD", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        int cambios = tipos.historial("CD").size();
        Consulta<TipoDocumento> todas = tipos.consultar(CrudCatalogo.Filtro.TODOS);
        assertEquals(14, todas.valores().size());
        assertEquals(13, todas.activos());
        assertEquals(LocalDate.of(2021, 6, 25),
                todas.vigente().orElseThrow().getFechaPublicacion());
        assertEquals(LocalDate.of(2026, 11, 2), todas.vigente().orElseThrow().getFechaDescarga());
        assertEquals(13, tipos.consultar(CrudCatalogo.Filtro.ACTIVOS).valores().size());
        assertEquals(List.of("CD"), codigos(
                tipos.consultar(CrudCatalogo.Filtro.INACTIVOS).valores()));
        assertEquals(cambios, tipos.historial("CD").size(), "la consulta no cambia nada");
    }

    @Test
    @DisplayName("HU-034 Esc. 13: el historial de versiones dice cuántos códigos creó, cambió e inactivó")
    void esc13_historialDeVersiones() throws IOException {
        tipos.confirmarCarga(tipos.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        tipos.inactivar("CD", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        tipos.confirmarCarga(tipos.leerCarga(csv(Files.readString(HOJAS)
                .replace("CE;Cédula de extranjería\n", "") + "ZZ;Tipo nuevo\n"), VERSION_2022),
                Map.of());
        List<ResumenVersion> versiones = tipos.historialDeVersiones();
        assertEquals(2, versiones.size());
        assertEquals(List.of(14L, 0L, 0L), cuentas(versiones.get(0)));
        assertEquals(List.of(1L, 1L, 1L), cuentas(versiones.get(1)));
        assertEquals("Resolución de prueba", versiones.get(1).version().getFuente());
    }

    @Test
    @DisplayName("HU-034 Esc. 14: la búsqueda por código trae solo la tipo con ese código")
    void esc14_busquedaPorCodigo() {
        tipos.confirmarCarga(tipos.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        assertEquals(List.of("TI"), codigos(tipos.buscar("TI")));
    }

    @Test
    @DisplayName("HU-034 Esc. 15: la búsqueda por nombre ignora mayúsculas y tildes y ordena por nombre")
    void esc15_busquedaPorNombre() {
        tipos.confirmarCarga(tipos.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        // Dos dicen "sin identificar"; "Sin identificación" no la contiene.
        assertEquals(List.of("AS", "MS"), codigos(tipos.buscar("identificar")));
        assertEquals(List.of("SI"), codigos(tipos.buscar("IDENTIFICACION")));
        assertEquals(List.of("CD"), codigos(tipos.buscar("carne")));
    }

    @Test
    @DisplayName("HU-034 Esc. 16: una búsqueda sin resultados lo dice")
    void esc16_sinResultados() {
        tipos.confirmarCarga(tipos.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        assertTrue(tipos.buscar("xyz").isEmpty());
        assertEquals("No hay tipos de documento que coincidan con la búsqueda",
                tipos.sinCoincidencias());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-034 Esc. 16: el menú dice que la búsqueda no trajo resultados")
    void esc16_sinResultadosEnElMenu() {
        tipos.confirmarCarga(tipos.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        assertTrue(menu("9\nxyz\n0\n")
                .contains("No hay tipos de documento que coincidan con la búsqueda"));
    }

    @Test
    @DisplayName("HU-034 Esc. 17: el catálogo vacío dice que falta cargar el archivo oficial")
    void esc17_catalogoVacio() {
        Consulta<TipoDocumento> consulta = tipos.consultar(CrudCatalogo.Filtro.TODOS);
        assertTrue(consulta.valores().isEmpty());
        assertEquals("El catálogo de tipos de documento está vacío: falta cargar el archivo"
                + " oficial", consulta.aviso());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-034 Esc. 17: la consulta del menú dice que el catálogo está vacío")
    void esc17_catalogoVacioEnElMenu() {
        assertTrue(menu("1\n3\n0\n").contains(
                "El catálogo de tipos de documento está vacío: falta cargar el archivo"
                        + " oficial"));
    }

    @Test
    @DisplayName("HU-034 Esc. 18: la comparación con la hoja 13 lista lo que falta, sobra o cambió")
    void esc18_comprobacionContraLaHoja() {
        ContratoTiposDocumento.HOJA_13.forEach((codigo, nombre) -> {
            if (!codigo.equals("SI")) {
                tipos.crear(new TipoDocumento(codigo, nombre), REFERENCIA);
            }
        });
        tipos.inactivar("CD", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        tipos.cambiarNombre("CC", "Cédula de ciudadanía", REFERENCIA);
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        List<String> antes = foto();
        Comprobacion comprobacion = tipos.comprobarContraLaHoja();
        assertEquals(List.of("SI"), comprobacion.faltan());
        assertEquals(List.of("CD"), comprobacion.inactivos());
        assertEquals(List.of("ZZ"), comprobacion.sobran());
        assertEquals(List.of("CC"), comprobacion.nombresDistintos());
        assertEquals(antes, foto(), "la comparación no cambia nada");
    }

    @Test
    @DisplayName("HU-034 Esc. 18: después de la primera carga, el catálogo coincide con la hoja 13")
    void esc18_coincideDespuesDeLaCarga() {
        tipos.confirmarCarga(tipos.leerCarga(HOJAS, RESOLUCION_866), Map.of());
        assertTrue(tipos.comprobarContraLaHoja().coincide());
    }

    @Test
    @DisplayName("HU-034 Esc. 19: inactivar con motivo deja de ofrecer el valor y lo conserva")
    void esc19_inactivacionConMotivo() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        assertEquals(0, tipos.contarUsos("ZZ"));
        tipos.inactivar("ZZ", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO, estado("ZZ"));
        assertTrue(activos().isEmpty());
        assertEquals(Motivo.RETIRADO_POR_LA_FUENTE, ultimoCambio("ZZ").getMotivo());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-034 Esc. 19: el menú muestra cuántos usan el tipo antes de pedir confirmar")
    void esc19_elMenuMuestraLosUsos() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        String salida = menu("5\nZZ\n1\n" + REFERENCIA + "\ns\n0\n");
        int usos = salida.indexOf("La usan " + tipos.contarUsos("ZZ") + " pacientes");
        assertTrue(usos >= 0, salida);
        assertTrue(usos < salida.indexOf("¿Confirma?"), "los usos van antes de confirmar");
        assertEquals(Catalogo.Estado.INACTIVO, estado("ZZ"));
    }

    @Test
    @DisplayName("HU-034 Esc. 20: sin motivo no se inactiva")
    void esc20_sinMotivo() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> tipos.inactivar("ZZ", null, REFERENCIA));
        assertEquals(Catalogo.Estado.ACTIVO, estado("ZZ"));
    }

    @Test
    @DisplayName("HU-034 Esc. 20: \"Retirado por la fuente oficial\" sin referencia no inactiva")
    void esc20_retiradoSinReferencia() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> tipos.inactivar("ZZ", Motivo.RETIRADO_POR_LA_FUENTE, ""));
        assertEquals(Catalogo.Estado.ACTIVO, estado("ZZ"));
    }

    @Test
    @DisplayName("HU-034 Esc. 20: \"Creado por error\" no vale para un código de la versión vigente")
    void esc20_creadoPorErrorEnLaVersionVigente() {
        enLaVersionVigente("ZZ");
        assertThrows(IllegalArgumentException.class,
                () -> tipos.inactivar("ZZ", Motivo.CREADO_POR_ERROR, null));
        assertEquals(Catalogo.Estado.ACTIVO, estado("ZZ"));
    }

    @Test
    @DisplayName("HU-034 Esc. 21: se reactiva diciendo por qué la referencia no lo retiraba")
    void esc21_reactivacion() {
        enLaVersionVigente("ZZ");
        tipos.inactivar("ZZ", Motivo.RETIRADO_POR_LA_FUENTE, "Referencia que no lo retiraba");
        tipos.reactivar("ZZ", null, "La referencia retiraba otro código");
        assertEquals(Catalogo.Estado.ACTIVO, estado("ZZ"));
        assertEquals(List.of("ZZ"), activos());
        assertEquals("La referencia retiraba otro código", ultimoCambio("ZZ").getJustificacion());
    }

    @Test
    @DisplayName("HU-034 Esc. 22: no se reactiva lo que no está en la versión vigente sin otra referencia")
    void esc22_fueraDeLaVersionVigente() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        // Una versión cargada después que ya no trae el código.
        version(LocalDateTime.now().plusMinutes(1));
        tipos.inactivar("ZZ", Motivo.CREADO_POR_ERROR, null);
        assertThrows(IllegalArgumentException.class,
                () -> tipos.reactivar("ZZ", null, "Se necesita"));
        assertEquals(Catalogo.Estado.INACTIVO, estado("ZZ"));
    }

    @Test
    @DisplayName("HU-034 Esc. 22: inactivado con referencia, no se reactiva sin otra ni sin decir por qué")
    void esc22_sinOtraReferenciaNiPorQue() {
        enLaVersionVigente("ZZ");
        tipos.inactivar("ZZ", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertThrows(IllegalArgumentException.class, () -> tipos.reactivar("ZZ", "", " "));
        assertEquals(Catalogo.Estado.INACTIVO, estado("ZZ"));
    }

    @Test
    @DisplayName("HU-034 Esc. 23: el historial lista cada cambio con su motivo, su referencia y la hora")
    void esc23_historial() {
        tipos.crear(new TipoDocumento("ZZ", PRUEBA), REFERENCIA);
        tipos.cambiarNombre("ZZ", "Nombre nuevo", REFERENCIA);
        tipos.inactivar("ZZ", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        List<CatalogoCambio> historial = tipos.historial("ZZ");
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
            new TipoDocumentoControlador(tipos, new Terminal(
                    new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8)),
                    new PrintStream(salida, true, StandardCharsets.UTF_8)), sesion).abrir();
        } finally {
            sesion.cerrar();
        }
        return salida.toString(StandardCharsets.UTF_8);
    }

    private static List<String> codigos(List<TipoDocumento> valores) {
        return valores.stream().map(Catalogo::getCodigo).toList();
    }

    private static List<Long> cuentas(ResumenVersion resumen) {
        return List.of(resumen.creados(), resumen.cambiados(), resumen.inactivados());
    }

    private List<String> foto() {
        return tipos.listar().stream()
                .map(c -> c.getCodigo() + c.getNombre() + c.getEstado() + c.getVersionId())
                .toList();
    }

    private Catalogo.Estado estado(String codigo) {
        return tipos.buscarPorCodigo(codigo).orElseThrow().getEstado();
    }

    private List<String> activos() {
        return tipos.listarActivos().stream().map(Catalogo::getCodigo).toList();
    }

    private CatalogoCambio ultimoCambio(String codigo) {
        List<CatalogoCambio> historial = tipos.historial(codigo);
        return historial.get(historial.size() - 1);
    }

    private CatalogoVersion version(LocalDateTime cargadoEn) {
        CatalogoVersion version = new CatalogoVersion();
        version.setCatalogo("TipoDocumento");
        version.setVersion("Versión de prueba");
        version.setFechaPublicacion(LocalDate.of(2021, 6, 25));
        version.setCargadoEn(cargadoEn);
        return registro.guardar(version);
    }

    // Como si el código hubiera llegado en la carga de la versión vigente, sin pasar por el CSV.
    private void enLaVersionVigente(String codigo) {
        TipoDocumento valor = new TipoDocumento(codigo, PRUEBA);
        valor.setVersionId(version(LocalDateTime.of(2026, 1, 1, 0, 0)).getId());
        repositorio.save(valor);
    }
}
