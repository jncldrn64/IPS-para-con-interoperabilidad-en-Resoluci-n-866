package com.example.demo.comun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.DemoApplication;
import com.example.demo.comun.interfaces.CrudCatalogo;
import com.example.demo.comun.menu.MenuPrincipal;
import com.example.demo.comun.menu.Terminal;
import com.example.demo.comun.modelos.Catalogo;
import com.example.demo.comun.modelos.CatalogoVersion;
import com.example.demo.comun.modelos.Motivo;
import com.example.demo.comun.repositorios.CatalogoCambioRepositorio;
import com.example.demo.comun.repositorios.CatalogoVersionRepositorio;
import com.example.demo.comun.servicios.RegistroCatalogos;
import com.example.demo.hu036.controladores.EtniaControlador;
import com.example.demo.hu036.interfaces.ContratoEtnias;
import com.example.demo.hu036.modelos.Etnia;
import com.example.demo.hu036.repositorios.EtniaRepositorio;
import com.example.demo.hu037.controladores.ComunidadEtnicaControlador;
import com.example.demo.hu037.interfaces.ContratoComunidadesEtnicas;
import com.example.demo.hu037.modelos.ComunidadEtnica;
import com.example.demo.hu037.repositorios.ComunidadEtnicaRepositorio;
import jakarta.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Las reglas de docs/DESIGN.md y de RC-10 que comparten los catálogos y que no son el escenario de
 * una historia. Los escenarios van en la clase de su historia, como HU036EtniasTest.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BaseComunTest {

    static final String REFERENCIA = "Resolución de prueba";

    @Autowired CrudCatalogo<Etnia> etnias;
    @Autowired CrudCatalogo<ComunidadEtnica> comunidades;
    @Autowired ContratoEtnias contratoEtnias;
    @Autowired ContratoComunidadesEtnicas contratoComunidades;
    @Autowired EtniaRepositorio repositorio;
    @Autowired RegistroCatalogos registro;
    @Autowired EntityManager em;
    @Autowired DataSource base;
    // Lo que imprimió la terminal del test.
    private final ByteArrayOutputStream impreso = new ByteArrayOutputStream();

    @Test
    void elCodigoConservaSusCerosIniciales() {
        etnias.crear(new Etnia("01", "Indígena"), REFERENCIA);
        comunidades.crear(new ComunidadEtnica("001", "Comunidad de prueba"), REFERENCIA);
        assertEquals("01", etnias.buscarPorCodigo("01").orElseThrow().getCodigo());
        assertEquals("001", comunidades.buscarPorCodigo("001").orElseThrow().getCodigo());
    }

    @Test
    void inactivarCambiaElEstadoSinBorrar() {
        etnias.crear(new Etnia("02", "ROM (Gitanos)"), REFERENCIA);
        etnias.inactivar("02", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO,
                etnias.buscarPorCodigo("02").orElseThrow().getEstado());
        assertThrows(IllegalStateException.class,
                () -> etnias.inactivar("02", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA));
        assertThrows(IllegalArgumentException.class,
                () -> etnias.inactivar("77", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA));
    }

    @Test
    void cambiarElNombreNoReactivaNiBorraLaVersion() {
        Etnia rom = new Etnia("02", "ROM");
        rom.setVersionId(7L);
        repositorio.save(rom);
        etnias.inactivar("02", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        em.flush();
        em.clear();
        etnias.cambiarNombre("02", "ROM (Gitanos)", REFERENCIA);
        Etnia guardada = etnias.buscarPorCodigo("02").orElseThrow();
        assertEquals("ROM (Gitanos)", guardada.getNombre());
        assertEquals(Catalogo.Estado.INACTIVO, guardada.getEstado());
        assertEquals(7L, guardada.getVersionId());
    }

    @Test
    void losMotivosDeUnaVersionLosPoneElSistema() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        assertThrows(IllegalArgumentException.class,
                () -> etnias.inactivar("07", Motivo.NO_ESTA_EN_LA_VERSION, REFERENCIA));
        assertThrows(IllegalArgumentException.class,
                () -> etnias.inactivar("07", Motivo.VUELVE_EN_LA_VERSION, REFERENCIA));
    }

    @Test
    void creadoPorErrorValeParaUnCodigoFueraDeLaVersionVigente() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        etnias.inactivar("07", Motivo.CREADO_POR_ERROR, null);
        assertEquals(Catalogo.Estado.INACTIVO,
                etnias.buscarPorCodigo("07").orElseThrow().getEstado());
    }

    @Test
    void otraReferenciaOficialReactivaUnCodigoFueraDeLaVersionVigente() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        CatalogoVersion posterior = new CatalogoVersion();
        posterior.setCatalogo("Etnia");
        posterior.setFechaPublicacion(LocalDate.of(2027, 1, 1));
        posterior.setCargadoEn(LocalDateTime.now().plusMinutes(1));
        registro.guardar(posterior);
        etnias.inactivar("07", Motivo.CREADO_POR_ERROR, null);
        etnias.reactivar("07", "Otra resolución", null);
        assertEquals(Catalogo.Estado.ACTIVO,
                etnias.buscarPorCodigo("07").orElseThrow().getEstado());
    }

    @Test
    void laBaseDePruebaSeCopiaSoloSiFalta(@TempDir Path carpeta) throws IOException {
        Path archivo = Files.writeString(carpeta.resolve("ips.mv.db"), "archivo de la base");
        Path copia = carpeta.resolve("prueba.mv.db");
        assertTrue(DemoApplication.copiarSiFalta(archivo, copia));
        assertEquals("archivo de la base", Files.readString(copia));
        Files.writeString(copia, "datos de prueba");
        assertFalse(DemoApplication.copiarSiFalta(archivo, copia));
        assertEquals("datos de prueba", Files.readString(copia));
        assertFalse(DemoApplication.copiarSiFalta(carpeta.resolve("no-existe.mv.db"),
                carpeta.resolve("otra.mv.db")));
        assertFalse(Files.exists(carpeta.resolve("otra.mv.db")));
    }

    @Test
    void ningunRepositorioPuedeBorrar() {
        for (Class<?> repositorio : new Class<?>[] {EtniaRepositorio.class,
                ComunidadEtnicaRepositorio.class, CatalogoVersionRepositorio.class,
                CatalogoCambioRepositorio.class}) {
            assertTrue(Arrays.stream(repositorio.getMethods())
                            .noneMatch(m -> m.getName().startsWith("delete")),
                    repositorio.getSimpleName() + " tiene un método para borrar");
        }
    }

    @Test
    @Timeout(10)
    void elMenuPrincipalOrdenaLasHistoriasYAbreLaElegida() throws Exception {
        Terminal terminal = terminal("1\n2\n07\nEtnia de prueba\n" + REFERENCIA + "\ns\n0\n0\n");
        // Llegan con HU-037 primero, y la opción 1 abre HU-036.
        new MenuPrincipal(List.of(new ComunidadEtnicaControlador(contratoComunidades, terminal),
                new EtniaControlador(contratoEtnias, terminal)), terminal, base).run();
        assertTrue(etnias.buscarPorCodigo("07").isPresent());
        assertTrue(comunidades.listar().isEmpty());
    }

    @Test
    @Timeout(10)
    void elMenuPrincipalDiceQueBaseAbrio() throws Exception {
        Terminal terminal = terminal("0\n");
        new MenuPrincipal(List.of(new EtniaControlador(contratoEtnias, terminal)), terminal, base)
                .run();
        assertTrue(salida().startsWith("Base abierta: jdbc:h2:mem:pruebas\n"), salida());
    }

    @Test
    @Timeout(10)
    void laTerminalCortaUnMenuQueNoSaleCuandoSeAcabaLaEntrada() {
        Terminal terminal = terminal("");
        for (int i = 0; i < 100; i++) {
            assertEquals("", terminal.leer("Opción: "));
        }
        assertThrows(NoSuchElementException.class, () -> terminal.leer("Opción: "));
    }

    @Test
    @Timeout(10)
    void laListaDeRechazadasNoReemplazaUnArchivoSinPreguntar(@TempDir Path carpeta)
            throws IOException {
        Path archivo = conUnaFilaRechazada(carpeta);
        Path lista = Files.writeString(carpeta.resolve("carga-rechazadas.csv"), "archivo propio");
        cargarYDescargarLaLista(archivo, "n\n");
        assertTrue(salida().contains("Ya existe " + lista + ". ¿Reemplazarlo? (s/n): "), salida());
        assertEquals("archivo propio", Files.readString(lista));
    }

    @Test
    @Timeout(10)
    void laListaDeRechazadasReemplazaElArchivoSiSeConfirma(@TempDir Path carpeta)
            throws IOException {
        Path archivo = conUnaFilaRechazada(carpeta);
        Path lista = Files.writeString(carpeta.resolve("carga-rechazadas.csv"), "archivo propio");
        cargarYDescargarLaLista(archivo, "s\n");
        assertTrue(Files.readString(lista).contains("100"), Files.readString(lista));
    }

    @Test
    @Timeout(10)
    void laListaDeRechazadasNuevaSeEscribeSinPreguntar(@TempDir Path carpeta) throws IOException {
        Path archivo = conUnaFilaRechazada(carpeta);
        cargarYDescargarLaLista(archivo, "");
        assertFalse(salida().contains("Ya existe"), salida());
        assertTrue(Files.readString(carpeta.resolve("carga-rechazadas.csv")).contains("100"));
    }

    @Test
    void cadaCatalogoTieneSuTablaConElLargoDeSuCodigo() {
        assertEquals("CHARACTER VARYING:2", columna("ETNIA"));
        assertEquals("CHARACTER VARYING:3", columna("COMUNIDAD_ETNICA"));
    }

    private Terminal terminal(String respuestas) {
        return new Terminal(new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8)),
                new PrintStream(impreso, true, StandardCharsets.UTF_8));
    }

    private String salida() {
        return impreso.toString(StandardCharsets.UTF_8);
    }

    // Las 7 etnias de la hoja 15 y una fila con un código de 3 caracteres, que se rechaza.
    private static Path conUnaFilaRechazada(Path carpeta) throws IOException {
        return Files.writeString(carpeta.resolve("carga.csv"), Files.readString(
                Path.of("src/test/resources/cargas/etnias-hoja-15.csv"))
                + "100;Valor de 3 caracteres\n", StandardCharsets.UTF_8);
    }

    // La opción 8 con el archivo, "s" para descargar la lista, la respuesta a "Ya existe", si la
    // hay, y "n" para no guardar la carga.
    private void cargarYDescargarLaLista(Path archivo, String siYaExiste) {
        new EtniaControlador(contratoEtnias, terminal("8\n" + archivo + "\nFuente\n\n2026-10-18\n"
                + "Resolución 866 de 2021\n2021-06-25\ns\n" + siYaExiste + "n\n0\n")).abrir();
    }

    private Object columna(String tabla) {
        return em.createNativeQuery("select data_type || ':' || character_maximum_length"
                        + " from information_schema.columns"
                        + " where table_name = ?1 and column_name = 'CODIGO'")
                .setParameter(1, tabla).getSingleResult();
    }
}
