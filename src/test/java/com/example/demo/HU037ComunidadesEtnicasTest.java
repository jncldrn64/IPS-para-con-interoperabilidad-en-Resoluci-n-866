package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.modelo.Catalogo;
import com.example.demo.modelo.CatalogoCambio;
import com.example.demo.modelo.CatalogoVersion;
import com.example.demo.modelo.ComunidadEtnica;
import com.example.demo.modelo.Motivo;
import com.example.demo.repositorio.ComunidadEtnicaRepositorio;
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
 * Los escenarios de HU-037, en docs/context/trello/tarjetas/HU-037.md. Cada método se llama
 * esc<N>_<alternativa>, por el número del escenario y el caso de su "Dado" que prueba.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU037ComunidadesEtnicasTest {

    static final String REFERENCIA = "Resolución de prueba";

    @Autowired CrudCatalogo<ComunidadEtnica> comunidades;
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
