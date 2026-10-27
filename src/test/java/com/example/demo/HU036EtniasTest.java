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
import com.example.demo.servicio.RegistroCatalogos;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
