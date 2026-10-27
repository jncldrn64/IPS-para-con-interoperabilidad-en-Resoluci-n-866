package com.example.demo.comun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.DemoApplication;
import com.example.demo.comun.interfaces.CrudCatalogo;
import com.example.demo.comun.modelos.Catalogo;
import com.example.demo.comun.modelos.CatalogoVersion;
import com.example.demo.comun.modelos.Motivo;
import com.example.demo.comun.repositorios.CatalogoCambioRepositorio;
import com.example.demo.comun.repositorios.CatalogoVersionRepositorio;
import com.example.demo.comun.servicios.RegistroCatalogos;
import com.example.demo.hu036.modelos.Etnia;
import com.example.demo.hu036.repositorios.EtniaRepositorio;
import com.example.demo.hu037.modelos.ComunidadEtnica;
import com.example.demo.hu037.repositorios.ComunidadEtnicaRepositorio;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
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
    @Autowired EtniaRepositorio repositorio;
    @Autowired RegistroCatalogos registro;
    @Autowired EntityManager em;

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
    void cadaCatalogoTieneSuTablaConElLargoDeSuCodigo() {
        assertEquals("CHARACTER VARYING:2", columna("ETNIA"));
        assertEquals("CHARACTER VARYING:3", columna("COMUNIDAD_ETNICA"));
    }

    private Object columna(String tabla) {
        return em.createNativeQuery("select data_type || ':' || character_maximum_length"
                        + " from information_schema.columns"
                        + " where table_name = ?1 and column_name = 'CODIGO'")
                .setParameter(1, tabla).getSingleResult();
    }
}
