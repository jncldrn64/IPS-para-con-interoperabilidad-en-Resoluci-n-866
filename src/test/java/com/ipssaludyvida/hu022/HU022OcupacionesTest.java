package com.ipssaludyvida.hu022;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.ipssaludyvida.comun.servicios.ResultadoCarga;
import com.ipssaludyvida.comun.servicios.ResumenVersion;
import com.ipssaludyvida.hu001.Usuarios;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Cargo;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import com.ipssaludyvida.hu022.controladores.OcupacionControlador;
import com.ipssaludyvida.hu022.interfaces.ContratoOcupaciones;
import com.ipssaludyvida.hu022.modelos.Ocupacion;
import com.ipssaludyvida.hu022.servicios.Nodo;
import jakarta.validation.ConstraintViolationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Los 28 escenarios de HU-022 (docs/context/trello/tarjetas/HU-022.md). El archivo oficial de la
 * CIUO-88 A.C. no se ha descargado, así que la primera carga usa una estructura de prueba con las
 * cantidades por nivel del DANE: 10, 34, 117 y 401. Solo 2, 22, 222 y 2221 llevan nombres reales,
 * los de la tarjeta y la lámina.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU022OcupacionesTest {

    static final String REFERENCIA = "Resolución de prueba";
    static final DatosVersion CIUO = new DatosVersion("CIUO-88 A.C., DANE",
            "https://www.dane.gov.co/files/sen/nomenclatura/ciuo/EstCIUO88AC.pdf",
            LocalDate.of(2026, 10, 31), "2006", LocalDate.of(2006, 6, 1));
    static final DatosVersion VERSION_NUEVA = new DatosVersion("CIUO-88 A.C., DANE",
            "https://www.dane.gov.co/", LocalDate.of(2026, 10, 31), "2026-03",
            LocalDate.of(2026, 3, 1));

    @Autowired ContratoOcupaciones ocupaciones;
    @Autowired UsuarioRepositorio usuarios;
    @Autowired ContratoSesion sesion;
    @TempDir Path carpeta;

    @Test
    @DisplayName("HU-022 Esc. 1: la primera carga crea cada ocupación activa con su versión")
    void esc1_primeraCarga() {
        CatalogoVersion version = cargar(estructura());
        assertEquals(562, ocupaciones.listarActivos().size());
        assertEquals(version.getId(),
                ocupaciones.buscarPorCodigo("2221").orElseThrow().getVersionId());
        assertEquals("Médicos", ocupaciones.buscarPorCodigo("2221").orElseThrow().getNombre());
    }

    @Test
    @DisplayName("HU-022 Esc. 2: un código de 5 caracteres y una fila sin nombre se rechazan")
    void esc2_filasConErrores() {
        Map<String, String> filas = estructura();
        filas.put("2221", "");
        filas.put("22211", "Código de cinco");
        ResultadoCarga carga = ocupaciones.leerCarga(csv(filas), CIUO);
        assertEquals(563, carga.getFilasLeidas());
        assertEquals(561, carga.getAceptadas().size());
        assertEquals(List.of("2221", "22211"),
                carga.getRechazadas().stream().map(ResultadoCarga.Rechazo::codigo).toList());
        assertTrue(carga.getRechazadas().get(0).motivo().contains("vacío"),
                carga.getRechazadas().get(0).motivo());
        assertTrue(carga.getRechazadas().get(1).motivo().contains("de 1 a 4 dígitos"));
        assertTrue(carga.sePuedeGuardar(), carga.getBloqueos().toString());
    }

    @Test
    @DisplayName("HU-022 Esc. 2: la lista de rechazadas se descarga con fila y motivo")
    void esc2_descargarLaLista() throws IOException {
        Map<String, String> filas = estructura();
        filas.put("22211", "Código de cinco");
        ResultadoCarga carga = ocupaciones.leerCarga(csv(filas), CIUO);
        Path lista = ocupaciones.escribirRechazadas(carga, carpeta.resolve("rechazadas.csv"));
        List<String> lineas = Files.readAllLines(lista);
        assertEquals("fila;código;motivo", lineas.get(0));
        assertTrue(lineas.get(1).startsWith("563;22211;"), lineas.get(1));
    }

    @Test
    @DisplayName("HU-022 Esc. 2: guardar las aceptadas y completar con el archivo corregido")
    void esc2_completarConLaRecarga() {
        Map<String, String> filas = estructura();
        filas.put("2221", "");
        ocupaciones.confirmarCarga(ocupaciones.leerCarga(csv(filas), CIUO), Map.of());
        assertTrue(ocupaciones.buscarPorCodigo("2221").isEmpty());
        ResultadoCarga recarga = ocupaciones.leerCarga(csv(estructura()), CIUO);
        assertEquals(ResultadoCarga.Tipo.RECARGA, recarga.getTipo());
        assertEquals(List.of("2221"), recarga.getNuevos());
        assertTrue(recarga.getYaNoEstan().isEmpty());
        ocupaciones.confirmarCarga(recarga, Map.of());
        assertEquals(562, ocupaciones.listarActivos().size());
        assertEquals(1, ocupaciones.historialDeVersiones().size());
    }

    @Test
    @DisplayName("HU-022 Esc. 3: sin la columna del nombre se rechaza el archivo completo")
    void esc3_faltaLaColumna() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ocupaciones.leerCarga(csv("codigo;titulo\n2;Algo\n"), CIUO));
        assertEquals("al archivo le falta la columna nombre", e.getMessage());
    }

    @Test
    @DisplayName("HU-022 Esc. 3: una fecha de publicación futura rechaza el archivo")
    void esc3_fechaFutura() {
        DatosVersion futura = new DatosVersion("CIUO-88 A.C., DANE", "", LocalDate.of(2026, 10,
                31), "2099", LocalDate.of(2099, 1, 1));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ocupaciones.leerCarga(csv(estructura()), futura));
        assertTrue(e.getMessage().contains("2099-01-01 es futura"), e.getMessage());
    }

    @Test
    @DisplayName("HU-022 Esc. 3: una fecha anterior a la vigente dice cuál es la vigente")
    void esc3_fechaAnterior() {
        cargar(estructura());
        DatosVersion vieja = new DatosVersion("CIUO-88 A.C., DANE", "", LocalDate.of(2026, 10,
                31), "1998", LocalDate.of(1998, 1, 1));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ocupaciones.leerCarga(csv(estructura()), vieja));
        assertTrue(e.getMessage().contains("la versión vigente es 2006, publicada el 2006-06-01"),
                e.getMessage());
    }

    @Test
    @DisplayName("HU-022 Esc. 4: dos filas con el mismo código se rechazan las dos")
    void esc4_codigoRepetido() {
        cargar(estructura());
        String archivo = texto(estructura()) + "2221;Médicos otra vez\n";
        ResultadoCarga carga = ocupaciones.leerCarga(csv(archivo), VERSION_NUEVA);
        List<ResultadoCarga.Rechazo> repetidas = carga.getRechazadas().stream()
                .filter(r -> r.codigo().equals("2221")).toList();
        assertEquals(2, repetidas.size());
        assertTrue(repetidas.stream().allMatch(r -> r.motivo().equals("código repetido")));
    }

    @Test
    @DisplayName("HU-022 Esc. 5: la 2221 antes que su grupo 222 entra bajo él")
    void esc5_grupoDespuesDeLaOcupacion() {
        Map<String, String> filas = new LinkedHashMap<>();
        filas.put("2221", "Médicos");
        filas.putAll(estructura());
        ResultadoCarga carga = ocupaciones.leerCarga(csv(filas), CIUO);
        assertTrue(carga.getRechazadas().isEmpty(), carga.getRechazadas().toString());
        ocupaciones.confirmarCarga(carga, Map.of());
        assertEquals("222", Ocupacion.grupoDe("2221"));
        assertTrue(ocupaciones.buscarPorCodigo("2221").isPresent());
        assertTrue(ocupaciones.buscarPorCodigo("222").isPresent());
    }

    @Test
    @DisplayName("HU-022 Esc. 5: un grupo nuevo y su ocupación en una versión nueva")
    void esc5_grupoNuevoEnUnaVersion() {
        cargar(estructura());
        Map<String, String> filas = new LinkedHashMap<>();
        filas.put("2290", "Ocupación nueva");
        filas.putAll(estructura());
        filas.put("229", "Grupo nuevo");
        ResultadoCarga carga = ocupaciones.leerCarga(csv(filas), VERSION_NUEVA);
        assertTrue(carga.getRechazadas().isEmpty(), carga.getRechazadas().toString());
        assertTrue(carga.getNuevos().containsAll(List.of("229", "2290")));
    }

    @Test
    @DisplayName("HU-022 Esc. 6: una ocupación sin su grupo se rechaza")
    void esc6_sinSuGrupo() {
        cargar(estructura());
        Map<String, String> filas = estructura();
        filas.put("2290", "Ocupación sin grupo");
        ResultadoCarga carga = ocupaciones.leerCarga(csv(filas), VERSION_NUEVA);
        assertEquals("su grupo 229 no está activo", motivo(carga, "2290"));
    }

    @Test
    @DisplayName("HU-022 Esc. 6: si la fila del grupo se rechaza, la ocupación también")
    void esc6_grupoRechazado() {
        cargar(estructura());
        Map<String, String> filas = estructura();
        filas.put("2290", "Ocupación nueva");
        filas.put("229", "");
        ResultadoCarga carga = ocupaciones.leerCarga(csv(filas), VERSION_NUEVA);
        assertTrue(motivo(carga, "229").contains("vacío"));
        assertEquals("su grupo 229 no está activo", motivo(carga, "2290"));
    }

    @Test
    @DisplayName("HU-022 Esc. 7: un archivo que perdió los ceros iniciales no cuadra por nivel")
    void esc7_cantidadesPorNivel() {
        // El CSV conserva las filas repetidas: la hoja de cálculo pierde los ceros, no las filas.
        StringBuilder archivo = new StringBuilder("codigo;nombre\n");
        estructura().forEach((c, n) -> archivo.append(c.replaceFirst("^0+(?=\\d)", ""))
                .append(';').append(n).append('\n'));
        ResultadoCarga carga = ocupaciones.leerCarga(csv(archivo.toString()), CIUO);
        assertFalse(carga.sePuedeGuardar());
        assertTrue(carga.getBloqueos().stream().anyMatch(b -> b.startsWith(
                "el nivel 1 no cuadra:")),
                carga.getBloqueos().toString());
        assertThrows(IllegalStateException.class,
                () -> ocupaciones.confirmarCarga(carga, Map.of()));
        assertTrue(ocupaciones.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-022 Esc. 7: con las cantidades del DANE la carga cuadra")
    void esc7_cuadra() {
        ResultadoCarga carga = ocupaciones.leerCarga(csv(estructura()), CIUO);
        assertTrue(carga.getBloqueos().isEmpty(), carga.getBloqueos().toString());
    }

    @Test
    @DisplayName("HU-022 Esc. 8: la versión nueva muestra los cambios y los aplica con su motivo")
    void esc8_versionNueva() {
        cargar(estructura());
        ocupaciones.inactivar("2220", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        Map<String, String> filas = estructura();
        filas.remove("2222");
        filas.remove("3330");
        filas.put("2221", "Médicos generales");
        filas.put("2229", "Ocupación agregada por la fuente");
        ResultadoCarga carga = ocupaciones.leerCarga(csv(filas), VERSION_NUEVA);
        assertEquals(List.of("2229"), carga.getNuevos());
        assertEquals("2221", carga.getCambianDeNombre().get(0).codigo());
        assertEquals(List.of("2220"), carga.getVuelven());
        assertEquals(List.of("2222", "3330"), carga.getYaNoEstan());
        assertTrue(carga.getAvisos().stream().anyMatch(a -> a.contains("deshace el cambio oficial")
                && a.contains("2220")), carga.getAvisos().toString());
        CatalogoVersion version = ocupaciones.confirmarCarga(carga, Map.of("2222", "2229"));
        assertEquals("Médicos generales", ocupaciones.buscarPorCodigo("2221").orElseThrow()
                .getNombre());
        assertEquals(Motivo.VUELVE_EN_LA_VERSION, ultimo("2220").getMotivo());
        assertEquals(Motivo.REEMPLAZADO_POR, ultimo("2222").getMotivo());
        assertEquals("2229", ultimo("2222").getCodigoReemplazo());
        assertEquals(Motivo.NO_ESTA_EN_LA_VERSION, ultimo("3330").getMotivo());
        assertEquals(version.getId(), ocupaciones.versionVigente().orElseThrow().getId());
    }

    @Test
    @DisplayName("HU-022 Esc. 9: un código existente rechazado conserva su nombre y su estado")
    void esc9_filaRechazadaConservaElCodigo() {
        cargar(estructura());
        Map<String, String> filas = estructura();
        filas.put("2221", "");
        ResultadoCarga carga = ocupaciones.leerCarga(csv(filas), VERSION_NUEVA);
        assertFalse(carga.getYaNoEstan().contains("2221"));
        assertTrue(carga.getRechazadas().stream().anyMatch(r -> r.codigo().equals("2221")));
        ocupaciones.confirmarCarga(carga, Map.of());
        Ocupacion medicos = ocupaciones.buscarPorCodigo("2221").orElseThrow();
        assertEquals("Médicos", medicos.getNombre());
        assertEquals(Catalogo.Estado.ACTIVO, medicos.getEstado());
    }

    @Test
    @DisplayName("HU-022 Esc. 10: un cambio oficial de código inactiva el viejo y crea el nuevo")
    void esc10_cambioDeCodigo() {
        cadena("2221", "Médicos");
        ocupaciones.cambiarCodigo("2221", new Ocupacion("2229", "Médicos"), REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO,
                ocupaciones.buscarPorCodigo("2221").orElseThrow().getEstado());
        assertEquals(Motivo.REEMPLAZADO_POR, ultimo("2221").getMotivo());
        assertEquals("2229", ultimo("2221").getCodigoReemplazo());
        assertEquals(Catalogo.Estado.ACTIVO,
                ocupaciones.buscarPorCodigo("2229").orElseThrow().getEstado());
    }

    @Test
    @DisplayName("HU-022 Esc. 10: un grupo con ocupaciones activas no cambia de código")
    void esc10_grupoConOcupacionesNoCambiaDeCodigo() {
        cadena("2221", "Médicos");
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> ocupaciones
                .cambiarCodigo("222", new Ocupacion("229", "Otro grupo"), REFERENCIA));
        assertEquals("el grupo 222 tiene 1 ocupaciones activas: no se inactiva", e.getMessage());
    }

    @Test
    @DisplayName("HU-022 Esc. 11: una ocupación creada por cambio oficial queda activa")
    void esc11_creacion() {
        cadena("2221", "Médicos");
        ocupaciones.crear(new Ocupacion("2229", "Ocupación nueva"), REFERENCIA);
        assertEquals(Catalogo.Estado.ACTIVO,
                ocupaciones.buscarPorCodigo("2229").orElseThrow().getEstado());
        assertEquals(REFERENCIA, ocupaciones.historial("2229").get(0).getReferenciaOficial());
    }

    @Test
    @DisplayName("HU-022 Esc. 12: sin la referencia oficial no se crea nada")
    void esc12_sinReferencia() {
        cadena("222", "Médicos y profesionales afines");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ocupaciones.crear(new Ocupacion("2221", "Médicos"), ""));
        assertEquals("falta la referencia oficial", e.getMessage());
        assertTrue(ocupaciones.buscarPorCodigo("2221").isEmpty());
    }

    @Test
    @DisplayName("HU-022 Esc. 12: un código que ya existe no se vuelve a crear")
    void esc12_yaExiste() {
        cadena("2221", "Médicos");
        assertThrows(IllegalStateException.class,
                () -> ocupaciones.crear(new Ocupacion("2221", "Otros médicos"), REFERENCIA));
    }

    @Test
    @DisplayName("HU-022 Esc. 12: sin su grupo activo no se crea")
    void esc12_sinGrupo() {
        cadena("22", "Profesionales de las ciencias biológicas, la medicina y la salud");
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> ocupaciones.crear(new Ocupacion("2291", "Sin grupo"), REFERENCIA));
        assertEquals("su grupo 229 no está activo", e.getMessage());
        assertTrue(ocupaciones.buscarPorCodigo("2291").isEmpty());
    }

    @Test
    @DisplayName("HU-022 Esc. 12: un código o un nombre que no cumple el numeral 7 no se guarda")
    void esc12_numeral() {
        cadena("222", "Médicos y profesionales afines");
        assertThrows(ConstraintViolationException.class,
                () -> ocupaciones.crear(new Ocupacion("22A1", "Médicos"), REFERENCIA));
        assertThrows(ConstraintViolationException.class,
                () -> ocupaciones.crear(new Ocupacion("2221", "Mé"), REFERENCIA));
        assertThrows(ConstraintViolationException.class,
                () -> ocupaciones.cambiarNombre("222", "", REFERENCIA));
        assertTrue(ocupaciones.buscarPorCodigo("2221").isEmpty());
    }

    @Test
    @DisplayName("HU-022 Esc. 13: el nombre nuevo de un grupo deja sus ocupaciones bajo él")
    void esc13_cambioDeNombre() {
        cadena("2221", "Médicos");
        ocupaciones.cambiarNombre("222", "Médicos (nombre nuevo)", REFERENCIA);
        CatalogoCambio cambio = ultimo("222");
        assertEquals("Médicos y profesionales afines", cambio.getValorAnterior());
        assertEquals("Médicos (nombre nuevo)", cambio.getValorNuevo());
        Nodo medicos = ocupaciones.jerarquia().stream()
                .filter(n -> n.valor().getCodigo().equals("2221")).findFirst().orElseThrow();
        assertEquals(4, medicos.nivel());
        assertEquals("222", Ocupacion.grupoDe(medicos.valor().getCodigo()));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-022 Esc. 14: cancelar una creación deja el catálogo igual")
    void esc14_cancelarCreacion() {
        cadena("222", "Médicos y profesionales afines");
        String salida = menu("2\n2221\nMédicos\n" + REFERENCIA + "\nn\n0\n");
        assertTrue(salida.contains("Cancelado: el catálogo queda igual."), salida);
        assertTrue(ocupaciones.buscarPorCodigo("2221").isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-022 Esc. 14: cancelar una inactivación deja el catálogo igual")
    void esc14_cancelarInactivacion() {
        cadena("2221", "Médicos");
        menu("5\n2221\n1\n" + REFERENCIA + "\nn\n0\n");
        assertEquals(Catalogo.Estado.ACTIVO,
                ocupaciones.buscarPorCodigo("2221").orElseThrow().getEstado());
        assertEquals(1, ocupaciones.historial("2221").size());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-022 Esc. 14: cancelar una carga no guarda nada")
    void esc14_cancelarCarga() {
        Path archivo = csv(estructura());
        menu("8\n" + archivo + "\nCIUO-88 A.C., DANE\n\n2026-10-31\n2006\n2006-06-01\nn\n0\n");
        assertTrue(ocupaciones.listar().isEmpty());
    }

    @Test
    @DisplayName("HU-022 Esc. 15: la consulta filtra y muestra la versión vigente")
    void esc15_consulta() {
        cargar(estructura());
        ocupaciones.inactivar("2220", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        Consulta<Ocupacion> todas = ocupaciones.consultar(CrudCatalogo.Filtro.TODOS);
        assertEquals(562, todas.valores().size());
        assertEquals(561, todas.activos());
        assertEquals("2006", todas.vigente().orElseThrow().getVersion());
        assertEquals(List.of("2220"), codigos(
                ocupaciones.consultar(CrudCatalogo.Filtro.INACTIVOS).valores()));
        assertEquals(561, ocupaciones.consultar(CrudCatalogo.Filtro.ACTIVOS).valores().size());
    }

    @Test
    @DisplayName("HU-022 Esc. 15: el historial de versiones dice cuántos creó cada una")
    void esc15_historialDeVersiones() {
        cargar(estructura());
        List<ResumenVersion> versiones = ocupaciones.historialDeVersiones();
        assertEquals(1, versiones.size());
        assertEquals(562, versiones.get(0).creados());
        assertEquals("CIUO-88 A.C., DANE", versiones.get(0).version().getFuente());
    }

    @Test
    @DisplayName("HU-022 Esc. 16: la jerarquía marca los grupos sin ocupaciones y las hijas")
    void esc16_jerarquia() {
        cadena("2221", "Médicos");
        ocupaciones.crear(new Ocupacion("223", "Grupo sin ocupaciones activas"), REFERENCIA);
        Map<String, Nodo> nodos = ocupaciones.jerarquia().stream()
                .collect(Collectors.toMap(n -> n.valor().getCodigo(), n -> n));
        assertEquals(List.of("2", "22", "222", "2221", "223"), ocupaciones.jerarquia().stream()
                .map(n -> n.valor().getCodigo()).toList());
        assertTrue(nodos.get("2221").seRegistra());
        assertFalse(nodos.get("222").seRegistra());
        assertFalse(nodos.get("222").sinOcupaciones());
        assertTrue(nodos.get("223").sinOcupaciones());
        assertEquals(3, nodos.get("222").nivel());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-022 Esc. 16: la opción 12 muestra la jerarquía con sangría y marcas")
    void esc16_pantalla() {
        cadena("2221", "Médicos");
        ocupaciones.crear(new Ocupacion("223", "Grupo sin ocupaciones activas"), REFERENCIA);
        String salida = menu("12\n0\n");
        assertTrue(salida.contains("12. Ver la jerarquía"), salida);
        assertTrue(salida.contains("\n      2221  Médicos  ACTIVO  [se registra al paciente]\n"),
                salida);
        assertTrue(salida.contains(
                "\n    223  Grupo sin ocupaciones activas  ACTIVO  [sin ocupaciones]\n"), salida);
    }

    @Test
    @DisplayName("HU-022 Esc. 17: la búsqueda por código devuelve solo ese código")
    void esc17_porCodigo() {
        cadena("2221", "Médicos");
        assertEquals(List.of("2221"), codigos(ocupaciones.buscar("2221")));
    }

    @Test
    @DisplayName("HU-022 Esc. 18: la búsqueda por nombre ignora mayúsculas y tildes")
    void esc18_porNombre() {
        cadena("2221", "Médicos");
        ocupaciones.crear(new Ocupacion("2222", "Auxiliares de los médicos"), REFERENCIA);
        assertEquals(List.of("2222", "2221", "222"), codigos(ocupaciones.buscar("MEDICOS")));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-022 Esc. 19: una búsqueda sin resultados lo dice")
    void esc19_sinResultados() {
        cadena("2221", "Médicos");
        String salida = menu("9\nastronauta\n0\n");
        assertTrue(salida.contains("No hay ocupaciones que coincidan con la búsqueda"), salida);
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-022 Esc. 20: el catálogo vacío dice que falta cargar el archivo oficial")
    void esc20_vacio() {
        String salida = menu("1\n3\n12\n0\n");
        assertEquals(2, salida.split("El catálogo de ocupaciones está vacío: falta cargar el"
                + " archivo oficial", -1).length - 1, salida);
    }

    @Test
    @DisplayName("HU-022 Esc. 21: la comprobación lista las diferencias sin cambiar nada")
    void esc21_comprobacion() {
        cadena("2221", "Médicos");
        ocupaciones.crear(new Ocupacion("2222", "Sobra"), REFERENCIA);
        ocupaciones.crear(new Ocupacion("2223", "Inactiva"), REFERENCIA);
        ocupaciones.inactivar("2223", Motivo.CREADO_POR_ERROR, null);
        Path oficial = csv("codigo;nombre\n2;Profesionales científicos e intelectuales\n"
                + "22;Profesionales de las ciencias biológicas, la medicina y la salud\n"
                + "222;Médicos y profesionales afines\n2221;Médicos generales\n2223;Inactiva\n"
                + "2224;Falta\n");
        Comprobacion c = ocupaciones.comprobarContraElArchivo(oficial);
        assertEquals(List.of("2224"), c.faltan());
        assertEquals(List.of("2223"), c.inactivos());
        assertEquals(List.of("2222"), c.sobran());
        assertEquals(List.of("2221"), c.nombresDistintos());
        assertEquals("Médicos", ocupaciones.buscarPorCodigo("2221").orElseThrow().getNombre());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-022 Esc. 22: inactivar dice cuántos pacientes y atenciones la usan")
    void esc22_inactivacion() {
        cadena("2221", "Médicos");
        String salida = menu("5\n2221\n1\n" + REFERENCIA + "\ns\n0\n");
        assertTrue(salida.contains("Inactivar 2221 Médicos. La usan 0 pacientes y 0 atenciones:"
                + " la conservan, pero no se ofrece en registros nuevos."), salida);
        assertEquals(Catalogo.Estado.INACTIVO,
                ocupaciones.buscarPorCodigo("2221").orElseThrow().getEstado());
        assertEquals(Motivo.RETIRADO_POR_LA_FUENTE, ultimo("2221").getMotivo());
        assertFalse(codigos(ocupaciones.listarActivos()).contains("2221"));
    }

    @Test
    @DisplayName("HU-022 Esc. 23: un grupo con ocupaciones activas no se inactiva")
    void esc23_grupoConOcupaciones() {
        cadena("2221", "Médicos");
        ocupaciones.crear(new Ocupacion("2222", "Otra ocupación"), REFERENCIA);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> ocupaciones.inactivar("222", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA));
        assertEquals("el grupo 222 tiene 2 ocupaciones activas: no se inactiva", e.getMessage());
        assertEquals(Catalogo.Estado.ACTIVO,
                ocupaciones.buscarPorCodigo("222").orElseThrow().getEstado());
    }

    @Test
    @DisplayName("HU-022 Esc. 23: una versión que retira un grupo con ocupaciones no lo inactiva")
    void esc23_versionNuevaNoRetiraElGrupo() {
        cargar(estructura());
        Map<String, String> filas = estructura();
        filas.remove("222");
        ResultadoCarga carga = ocupaciones.leerCarga(csv(filas), VERSION_NUEVA);
        assertFalse(carga.getYaNoEstan().contains("222"), carga.getYaNoEstan().toString());
        assertTrue(carga.getAvisos().contains(
                "el grupo 222 tiene 4 ocupaciones activas: no se inactiva"),
                carga.getAvisos().toString());
        ocupaciones.confirmarCarga(carga, Map.of());
        assertEquals(Catalogo.Estado.ACTIVO,
                ocupaciones.buscarPorCodigo("222").orElseThrow().getEstado());
    }

    @Test
    @DisplayName("HU-022 Esc. 23: una versión que retira un grupo y sus ocupaciones los inactiva")
    void esc23_versionNuevaRetiraElGrupoEntero() {
        cargar(estructura());
        Map<String, String> filas = estructura();
        filas.keySet().removeIf(c -> c.startsWith("222"));
        ResultadoCarga carga = ocupaciones.leerCarga(csv(filas), VERSION_NUEVA);
        assertEquals(List.of("222", "2220", "2221", "2222", "2223"), carga.getYaNoEstan());
        ocupaciones.confirmarCarga(carga, Map.of());
        assertEquals(Catalogo.Estado.INACTIVO,
                ocupaciones.buscarPorCodigo("222").orElseThrow().getEstado());
    }

    @Test
    @DisplayName("HU-022 Esc. 24: sin motivo, o sin la referencia del motivo, no se inactiva")
    void esc24_sinMotivoOSinReferencia() {
        cadena("2221", "Médicos");
        assertEquals("elija un motivo", assertThrows(IllegalArgumentException.class,
                () -> ocupaciones.inactivar("2221", null, REFERENCIA)).getMessage());
        assertTrue(assertThrows(IllegalArgumentException.class,
                () -> ocupaciones.inactivar("2221", Motivo.RETIRADO_POR_LA_FUENTE, ""))
                .getMessage().contains("pide la referencia oficial"));
        assertEquals(Catalogo.Estado.ACTIVO,
                ocupaciones.buscarPorCodigo("2221").orElseThrow().getEstado());
    }

    @Test
    @DisplayName("HU-022 Esc. 24: \"Creado por error\" no vale para un código de la versión")
    void esc24_creadoPorErrorEnLaVersion() {
        cargar(estructura());
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ocupaciones.inactivar("2221", Motivo.CREADO_POR_ERROR, null));
        assertTrue(e.getMessage().startsWith("\"Creado por error\" solo vale"), e.getMessage());
    }

    @Test
    @DisplayName("HU-022 Esc. 25: reactivar con el porqué deja el motivo en el historial")
    void esc25_reactivacion() {
        cargar(estructura());
        ocupaciones.inactivar("2221", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        ocupaciones.reactivar("2221", "", "La resolución citada no retiraba esta ocupación");
        assertEquals(Catalogo.Estado.ACTIVO,
                ocupaciones.buscarPorCodigo("2221").orElseThrow().getEstado());
        assertEquals("La resolución citada no retiraba esta ocupación",
                ultimo("2221").getJustificacion());
    }

    @Test
    @DisplayName("HU-022 Esc. 26: una ocupación no se reactiva con su grupo inactivo")
    void esc26_grupoInactivo() {
        cadena("2221", "Médicos");
        ocupaciones.inactivar("2221", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        ocupaciones.inactivar("222", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> ocupaciones.reactivar("2221", "Otra resolución", null));
        assertEquals("su grupo 222 está inactivo: primero se reactiva el grupo", e.getMessage());
        ocupaciones.reactivar("222", "Otra resolución", null);
        ocupaciones.reactivar("2221", "Otra resolución", null);
        assertEquals(Catalogo.Estado.ACTIVO,
                ocupaciones.buscarPorCodigo("2221").orElseThrow().getEstado());
    }

    @Test
    @DisplayName("HU-022 Esc. 27: sin respaldo no se reactiva lo que retiró una versión")
    void esc27_retiradaPorUnaVersion() {
        cargar(estructura());
        Map<String, String> filas = estructura();
        filas.remove("2222");
        ocupaciones.confirmarCarga(ocupaciones.leerCarga(csv(filas), VERSION_NUEVA), Map.of());
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ocupaciones.reactivar("2222", "", "Porque sí"));
        assertTrue(e.getMessage().startsWith("no se puede reactivar"), e.getMessage());
    }

    @Test
    @DisplayName("HU-022 Esc. 27: inactivada con referencia, pide otra o el porqué")
    void esc27_inactivadaConReferencia() {
        cadena("2221", "Médicos");
        ocupaciones.inactivar("2221", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ocupaciones.reactivar("2221", "", ""));
        assertTrue(e.getMessage().startsWith("se inactivó con una referencia oficial"),
                e.getMessage());
    }

    @Test
    @DisplayName("HU-022 Esc. 28: el historial dice con qué versión se creó y cada cambio")
    void esc28_historial() {
        CatalogoVersion version = cargar(estructura());
        ocupaciones.cambiarNombre("2221", "Médicos generales", REFERENCIA);
        ocupaciones.inactivar("2221", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        List<CatalogoCambio> historial = ocupaciones.historial("2221");
        assertEquals(List.of(CatalogoCambio.Tipo.CREADO, CatalogoCambio.Tipo.NOMBRE,
                CatalogoCambio.Tipo.INACTIVADO),
                historial.stream().map(CatalogoCambio::getTipo).toList());
        assertEquals(version.getId(), historial.get(0).getVersionId());
        assertNull(historial.get(0).getReferenciaOficial());
        assertEquals(REFERENCIA, historial.get(1).getReferenciaOficial());
        assertEquals(Motivo.RETIRADO_POR_LA_FUENTE, historial.get(2).getMotivo());
        assertNotNull(historial.get(2).getFechaHora());
    }

    /**
     * Una estructura con las cantidades por nivel de la CIUO-88 A.C.: 10 grandes grupos, del 0 al
     * 9; 34 subgrupos principales, 3 por gran grupo y uno más en los grupos 1 a 4; 117 subgrupos,
     * 3 por subgrupo principal y uno más en los 15 primeros; y 401 grupos primarios, 3 por
     * subgrupo y uno más en los 50 primeros. El grupo 222 tiene 2220 a 2223, y el 333, 3330 a
     * 3332.
     */
    static Map<String, String> estructura() {
        Map<String, String> filas = new LinkedHashMap<>();
        List<String> nivel1 = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        List<String> nivel2 = hijos(nivel1, 3, 4, filas);
        List<String> nivel3 = hijos(nivel2, 3, 15, filas);
        hijos(nivel3, 3, 50, filas);
        nivel1.forEach(c -> filas.put(c, "Gran grupo " + c));
        filas.put("2", "Profesionales científicos e intelectuales");
        filas.put("22", "Profesionales de las ciencias biológicas, la medicina y la salud");
        filas.put("222", "Médicos y profesionales afines");
        filas.put("2221", "Médicos");
        // Ordenadas por código, como las trae el archivo oficial.
        return filas.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a,
                        LinkedHashMap::new));
    }

    // Cada padre tiene "cada" hijos, y los "conUnoMas" primeros, uno más. El dígito del grupo 0
    // empieza en 0, así que el gran grupo 0 tiene 00, 01 y 02.
    private static List<String> hijos(List<String> padres, int cada, int conUnoMas,
            Map<String, String> filas) {
        List<String> hijos = new java.util.ArrayList<>();
        for (int i = 0; i < padres.size(); i++) {
            int cuantos = cada + (i < conUnoMas ? 1 : 0);
            if (padres.get(i).length() == 1 && conUnoMas == 4) {
                cuantos = cada + (i >= 1 && i <= 4 ? 1 : 0);
            }
            for (int d = 0; d < cuantos; d++) {
                String codigo = padres.get(i) + d;
                hijos.add(codigo);
                filas.put(codigo, "Ocupación " + codigo);
            }
        }
        return hijos;
    }

    private CatalogoVersion cargar(Map<String, String> filas) {
        ResultadoCarga carga = ocupaciones.leerCarga(csv(filas), CIUO);
        assertTrue(carga.sePuedeGuardar(), carga.getBloqueos().toString());
        return ocupaciones.confirmarCarga(carga, Map.of());
    }

    // Crea por cambio oficial la cadena de grupos del código y el código, con los nombres reales
    // de 2, 22 y 222.
    private void cadena(String codigo, String nombre) {
        Map<String, String> nombres = estructura();
        for (int largo = 1; largo < codigo.length(); largo++) {
            String grupo = codigo.substring(0, largo);
            if (ocupaciones.buscarPorCodigo(grupo).isEmpty()) {
                ocupaciones.crear(new Ocupacion(grupo, nombres.get(grupo)), REFERENCIA);
            }
        }
        ocupaciones.crear(new Ocupacion(codigo, nombre), REFERENCIA);
    }

    private CatalogoCambio ultimo(String codigo) {
        List<CatalogoCambio> historial = ocupaciones.historial(codigo);
        return historial.get(historial.size() - 1);
    }

    private static String motivo(ResultadoCarga carga, String codigo) {
        return carga.getRechazadas().stream().filter(r -> r.codigo().equals(codigo))
                .map(ResultadoCarga.Rechazo::motivo).findFirst().orElse("aceptada");
    }

    private static List<String> codigos(List<Ocupacion> valores) {
        return valores.stream().map(Catalogo::getCodigo).toList();
    }

    private static String texto(Map<String, String> filas) {
        StringBuilder archivo = new StringBuilder("codigo;nombre\n");
        filas.forEach((c, n) -> archivo.append(c).append(';').append(n).append('\n'));
        return archivo.toString();
    }

    private Path csv(Map<String, String> filas) {
        return csv(texto(filas));
    }

    private Path csv(String contenido) {
        try {
            return Files.writeString(Files.createTempFile(carpeta, "carga", ".csv"), contenido,
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    // Lo que el menú imprimió con esas respuestas, con la sesión del profesional de estadística.
    // Un test del menú lleva @Timeout (docs/DESIGN.md, sección 3).
    private String menu(String respuestas) {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Usuarios.entrar(usuarios, sesion, Cargo.PROFESIONAL_DE_ESTADISTICA);
        try {
            new OcupacionControlador(ocupaciones, new Terminal(
                    new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8)),
                    new PrintStream(salida, true, StandardCharsets.UTF_8)), sesion).abrir();
        } finally {
            sesion.cerrar();
        }
        return salida.toString(StandardCharsets.UTF_8);
    }
}
