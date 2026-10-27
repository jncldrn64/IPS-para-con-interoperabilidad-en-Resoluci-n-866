package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.modelo.Catalogo;
import com.example.demo.modelo.CatalogoVersion;
import com.example.demo.modelo.ComunidadEtnica;
import com.example.demo.modelo.Etnia;
import com.example.demo.modelo.Motivo;
import com.example.demo.repositorio.CatalogoCambioRepositorio;
import com.example.demo.repositorio.CatalogoVersionRepositorio;
import com.example.demo.repositorio.ComunidadEtnicaRepositorio;
import com.example.demo.repositorio.EtniaRepositorio;
import com.example.demo.servicio.CrudCatalogo;
import com.example.demo.servicio.RegistroCatalogos;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
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
