package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.modelo.Catalogo;
import com.example.demo.modelo.ComunidadEtnica;
import com.example.demo.modelo.Etnia;
import com.example.demo.repositorio.ComunidadEtnicaRepositorio;
import com.example.demo.repositorio.EtniaRepositorio;
import com.example.demo.servicio.CrudCatalogo;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CatalogosTest {

    @Autowired CrudCatalogo<Etnia> etnias;
    @Autowired CrudCatalogo<ComunidadEtnica> comunidades;
    @Autowired EntityManager em;

    @Test
    void etniaConservaElCeroInicial() {
        etnias.guardar(new Etnia("01", "Indígena"));
        assertEquals("01", etnias.buscarPorCodigo("01").orElseThrow().getCodigo());
    }

    @Test
    void etniaRechazaUnCodigoDeOtroLargo() {
        assertThrows(ConstraintViolationException.class, () -> etnias.guardar(new Etnia("123456", "X")));
        assertThrows(ConstraintViolationException.class, () -> etnias.guardar(new Etnia("1", "X")));
    }

    @Test
    void etniaRechazaUnCodigoConLetras() {
        assertThrows(ConstraintViolationException.class, () -> etnias.guardar(new Etnia("0A", "X")));
    }

    @Test
    void etniaRechazaElCodigoYElNombreVacios() {
        assertThrows(ConstraintViolationException.class, () -> etnias.guardar(new Etnia(null, "X")));
        assertThrows(ConstraintViolationException.class, () -> etnias.guardar(new Etnia("02", " ")));
    }

    @Test
    void comunidadPideTresDigitos() {
        comunidades.guardar(new ComunidadEtnica("001", "Comunidad de prueba"));
        assertThrows(ConstraintViolationException.class,
                () -> comunidades.guardar(new ComunidadEtnica("01", "X")));
    }

    @Test
    void inactivarCambiaElEstadoSinBorrar() {
        etnias.guardar(new Etnia("02", "ROM (Gitanos)"));
        etnias.inactivar("02");
        assertEquals(Catalogo.Estado.INACTIVO, etnias.buscarPorCodigo("02").orElseThrow().getEstado());
        assertThrows(IllegalStateException.class, () -> etnias.inactivar("02"));
        assertThrows(IllegalArgumentException.class, () -> etnias.inactivar("77"));
    }

    @Test
    void cambiarElNombreNoReactivaNiBorraLaVersion() {
        Etnia rom = new Etnia("02", "ROM");
        rom.setVersionId(7L);
        etnias.guardar(rom);
        etnias.inactivar("02");
        em.flush();
        em.clear();
        etnias.guardar(new Etnia("02", "ROM (Gitanos)"));
        Etnia guardada = etnias.buscarPorCodigo("02").orElseThrow();
        assertEquals("ROM (Gitanos)", guardada.getNombre());
        assertEquals(Catalogo.Estado.INACTIVO, guardada.getEstado());
        assertEquals(7L, guardada.getVersionId());
    }

    @Test
    void ningunRepositorioDeCatalogoPuedeBorrar() {
        for (Class<?> repositorio : new Class<?>[] {EtniaRepositorio.class, ComunidadEtnicaRepositorio.class}) {
            assertTrue(Arrays.stream(repositorio.getMethods()).noneMatch(m -> m.getName().startsWith("delete")),
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
                + " from information_schema.columns where table_name = ?1 and column_name = 'CODIGO'")
                .setParameter(1, tabla).getSingleResult();
    }
}
