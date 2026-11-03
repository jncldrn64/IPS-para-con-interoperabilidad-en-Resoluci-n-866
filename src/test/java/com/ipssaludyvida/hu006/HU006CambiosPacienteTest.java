package com.ipssaludyvida.hu006;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.modelos.HistorialCambio;
import com.ipssaludyvida.comun.modelos.Motivo;
import com.ipssaludyvida.hu001.Usuarios;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Cargo;
import com.ipssaludyvida.hu001.modelos.Usuario;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import com.ipssaludyvida.hu004.interfaces.ContratoPacientes;
import com.ipssaludyvida.hu004.modelos.Paciente;
import com.ipssaludyvida.hu004.servicios.Campo;
import com.ipssaludyvida.hu004.servicios.DatosPaciente;
import com.ipssaludyvida.hu004.servicios.RegistroInvalido;
import com.ipssaludyvida.hu006.controladores.CambiosPacienteControlador;
import com.ipssaludyvida.hu006.interfaces.ContratoCambiosPaciente;
import com.ipssaludyvida.hu018.interfaces.ContratoPaises;
import com.ipssaludyvida.hu018.modelos.Pais;
import com.ipssaludyvida.hu022.interfaces.ContratoOcupaciones;
import com.ipssaludyvida.hu022.modelos.Ocupacion;
import com.ipssaludyvida.hu026.interfaces.ContratoMunicipios;
import com.ipssaludyvida.hu026.modelos.Departamento;
import com.ipssaludyvida.hu026.modelos.Municipio;
import com.ipssaludyvida.hu026.repositorios.DepartamentoRepositorio;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu034.modelos.TipoDocumento;
import com.ipssaludyvida.hu035.interfaces.ContratoCategoriasDiscapacidad;
import com.ipssaludyvida.hu035.modelos.CategoriaDiscapacidad;
import com.ipssaludyvida.hu036.interfaces.ContratoEtnias;
import com.ipssaludyvida.hu036.modelos.Etnia;
import com.ipssaludyvida.hu037.interfaces.ContratoComunidadesEtnicas;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import com.ipssaludyvida.hu038.modelos.EntidadPlanBeneficios;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Los 13 escenarios de HU-006 (docs/context/trello/tarjetas/HU-006.md). Cada test crea los valores
 * de los catálogos, registra a la paciente con HU-004 y la cambia con la sesión del auxiliar de
 * admisión, por el servicio o por la pantalla.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU006CambiosPacienteTest {

    static final String REFERENCIA = "Resolución de prueba";
    static final String DOC = "CC\n1020304050\n";

    @Autowired ContratoCambiosPaciente cambios;
    @Autowired ContratoPacientes pacientes;
    @Autowired ContratoPaises paises;
    @Autowired ContratoTiposDocumento tipos;
    @Autowired ContratoOcupaciones ocupaciones;
    @Autowired ContratoCategoriasDiscapacidad discapacidades;
    @Autowired ContratoMunicipios municipios;
    @Autowired DepartamentoRepositorio departamentos;
    @Autowired ContratoEtnias etnias;
    @Autowired ContratoComunidadesEtnicas comunidades;
    @Autowired ContratoEntidadesPlanBeneficios entidades;
    @Autowired UsuarioRepositorio usuarios;
    @Autowired ContratoSesion sesion;
    private final ByteArrayOutputStream impreso = new ByteArrayOutputStream();
    private Usuario auxiliar;

    @BeforeEach
    void catalogosYPaciente() {
        paises.crear(new Pais("170", "COLOMBIA"), REFERENCIA);
        paises.crear(new Pais("862", "VENEZUELA"), REFERENCIA);
        tipos.crear(new TipoDocumento("CC", "Cédula ciudadanía"), REFERENCIA);
        tipos.crear(new TipoDocumento("PA", "Pasaporte"), REFERENCIA);
        ocupaciones.crear(new Ocupacion("2", "PROFESIONALES"), REFERENCIA);
        ocupaciones.crear(new Ocupacion("22", "PROFESIONALES DE LA SALUD"), REFERENCIA);
        ocupaciones.crear(new Ocupacion("222", "Médicos y afines"), REFERENCIA);
        ocupaciones.crear(new Ocupacion("2221", "Médicos"), REFERENCIA);
        discapacidades.crear(new CategoriaDiscapacidad("01", "Discapacidad física"), REFERENCIA);
        discapacidades.crear(new CategoriaDiscapacidad("02", "Discapacidad visual"), REFERENCIA);
        discapacidades.crear(new CategoriaDiscapacidad("08", "Sin discapacidad"), REFERENCIA);
        departamentos.save(new Departamento("05", "ANTIOQUIA"));
        municipios.crear(new Municipio("05001", "MEDELLÍN"), REFERENCIA);
        municipios.crear(new Municipio("05002", "ABEJORRAL"), REFERENCIA);
        etnias.crear(new Etnia("01", "Indígena"), REFERENCIA);
        etnias.crear(new Etnia("99", "Ninguna de las anteriores"), REFERENCIA);
        entidades.crear(new EntidadPlanBeneficios("EPS037", "Entidad de prueba"), REFERENCIA);
        registrar(d -> { });
        auxiliar = Usuarios.entrar(usuarios, sesion, Cargo.AUXILIAR_DE_ADMISION);
    }

    @AfterEach
    void cerrarLaSesion() {
        sesion.cerrar();
    }

    @Test
    @DisplayName("HU-006 Esc. 1: actualizar la ciudad guarda la nueva, la anterior, quién y cuándo")
    void esc1_actualizarCiudad() {
        List<HistorialCambio> filas = cambiar(HistorialCambio.Tipo.ACTUALIZACION,
                d -> d.setMunicipioResidencia("05002"));
        assertEquals("05002", paciente().getMunicipioResidencia());
        HistorialCambio fila = filas.get(0);
        assertEquals(1, filas.size());
        assertEquals("MUNICIPIO_RESIDENCIA", fila.getDato());
        assertEquals("05001", fila.getValorAnterior());
        assertEquals("05002", fila.getValorNuevo());
        assertEquals(HistorialCambio.Tipo.ACTUALIZACION, fila.getTipo());
        assertEquals(auxiliar.getId(), fila.getUsuarioId());
        assertNotNull(fila.getFechaHora());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-006 Esc. 1: la pantalla muestra el cambio y lo guarda al confirmar")
    void esc1_pantalla() {
        String salida = pantalla("1\n" + DOC + "1\n15\n05002\n\ns\n");
        assertTrue(salida.contains("Actualización de CC 1020304050:\n"
                + "  - ciudad (municipio) de residencia: de \"05001\" a \"05002\"\n"), salida);
        assertTrue(salida.contains("Guardado: 1 dato. El valor anterior queda en el historial."),
                salida);
        assertEquals("05002", paciente().getMunicipioResidencia());
    }

    @Test
    @DisplayName("HU-006 Esc. 2: corregir guarda el dato nuevo con el nombre y la identificación")
    void esc2_corregir() {
        HistorialCambio fila = cambiar(HistorialCambio.Tipo.CORRECCION,
                d -> d.setSegundoApellido("GÓMEZ")).get(0);
        assertEquals("GÓMEZ", paciente().getSegundoApellido());
        assertEquals("GOMES", fila.getValorAnterior());
        assertEquals(HistorialCambio.Tipo.CORRECCION, fila.getTipo());
        assertEquals("Auxiliar de admisión de prueba (identificación id-auxiliar_de_admision)",
                cambios.autor(fila));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-006 Esc. 3: el historial muestra el vigente y cada cambio con su autor")
    void esc3_historial() {
        cambiar(HistorialCambio.Tipo.CORRECCION, d -> d.setSegundoApellido("GÓMEZ"));
        cambiar(HistorialCambio.Tipo.ACTUALIZACION, d -> d.setMunicipioResidencia("05002"));
        String salida = pantalla("2\n" + DOC);
        assertTrue(salida.contains("segundo apellido, vigente: \"GÓMEZ\"\n"), salida);
        assertTrue(salida.contains("Corrección: de \"GOMES\" a \"GÓMEZ\", por Auxiliar de"
                + " admisión de prueba (identificación id-auxiliar_de_admision)\n"), salida);
        assertTrue(salida.contains("ciudad (municipio) de residencia, vigente: \"05002\"\n"),
                salida);
        assertTrue(salida.contains("Actualización: de \"05001\" a \"05002\""), salida);
        assertEquals(2, cambios.historial("CC", "1020304050").size());
    }

    @Test
    @DisplayName("HU-006 Esc. 3: un paciente sin cambios tiene el historial vacío")
    void esc3_sinCambios() {
        assertTrue(cambios.historial("CC", "1020304050").isEmpty());
    }

    @Test
    @DisplayName("HU-006 Esc. 4: agregar una nacionalidad guarda las dos")
    void esc4_agregarNacionalidad() {
        HistorialCambio fila = cambiar(HistorialCambio.Tipo.ACTUALIZACION,
                d -> d.setNacionalidades(List.of("170", "862"))).get(0);
        assertEquals(Set.of("170", "862"), paciente().getNacionalidades());
        assertEquals("170", fila.getValorAnterior());
        assertEquals("170, 862", fila.getValorNuevo());
    }

    @Test
    @DisplayName("HU-006 Esc. 5: retirar una discapacidad conserva la otra y deja el rastro")
    void esc5_retirarDiscapacidad() {
        cambiar(HistorialCambio.Tipo.ACTUALIZACION, d -> d.setDiscapacidades(List.of("01", "02")));
        HistorialCambio fila = cambiar(HistorialCambio.Tipo.ACTUALIZACION,
                d -> d.setDiscapacidades(List.of("01"))).get(0);
        assertEquals(Set.of("01"), paciente().getDiscapacidades());
        assertEquals("01, 02", fila.getValorAnterior());
        assertEquals("01", fila.getValorNuevo());
    }

    @Test
    @DisplayName("HU-006 Esc. 6: una ocupación general se rechaza y queda la anterior")
    void esc6_ocupacionGeneral() {
        RegistroInvalido e = assertThrows(RegistroInvalido.class,
                () -> cambiar(HistorialCambio.Tipo.ACTUALIZACION, d -> d.setOcupacion("222")));
        assertTrue(e.getErrores().get(Campo.OCUPACION).contains("nivel más específico"));
        assertEquals("2221", paciente().getOcupacion());
        assertTrue(cambios.historial("CC", "1020304050").isEmpty());
    }

    @Test
    @DisplayName("HU-006 Esc. 7: un valor que no cumple su numeral o no está activo se rechaza")
    void esc7_numeral() {
        assertThrows(RegistroInvalido.class, () -> cambiar(HistorialCambio.Tipo.CORRECCION,
                d -> d.setFechaNacimiento("1990-02-30")));
        assertThrows(RegistroInvalido.class, () -> cambiar(HistorialCambio.Tipo.CORRECCION,
                d -> d.setSexoBiologico("09")));
        entidades.crear(new EntidadPlanBeneficios("EPS999", "Otra entidad"), REFERENCIA);
        entidades.inactivar("EPS999", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        RegistroInvalido e = assertThrows(RegistroInvalido.class, () -> cambiar(
                HistorialCambio.Tipo.ACTUALIZACION, d -> d.setEntidadPlanBeneficios("EPS999")));
        assertTrue(e.getErrores().get(Campo.ENTIDAD_PLAN_BENEFICIOS).contains("no es un valor"
                + " activo"));
        Paciente p = paciente();
        assertEquals("1990-05-17", p.getFechaNacimiento().toString());
        assertEquals("02", p.getSexoBiologico());
        assertEquals("EPS037", p.getEntidadPlanBeneficios());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-006 Esc. 8: dejar vacío un dato obligatorio no se acepta y dice cuál falta")
    void esc8_obligatorioVacio() {
        RegistroInvalido e = assertThrows(RegistroInvalido.class,
                () -> cambiar(HistorialCambio.Tipo.CORRECCION, d -> d.setPrimerNombre("")));
        assertEquals("falta", e.getErrores().get(Campo.PRIMER_NOMBRE));
        assertEquals("ANA", paciente().getPrimerNombre());
        String salida = pantalla("1\n" + DOC + "2\n6\n\n\ns\n");
        assertTrue(salida.contains("No se guardó; el paciente queda igual. Revise estos datos:\n"
                + "  - primer nombre: falta\n"), salida);
        assertEquals("ANA", paciente().getPrimerNombre());
    }

    @Test
    @DisplayName("HU-006 Esc. 9: un documento que ya tiene otro paciente se rechaza")
    void esc9_documentoDeOtro() {
        registrar(d -> {
            d.setTipoDocumento("PA");
            d.setNumeroDocumento("X12345");
        });
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> cambiar(HistorialCambio.Tipo.CORRECCION, d -> {
                    d.setTipoDocumento("PA");
                    d.setNumeroDocumento("X12345");
                }));
        assertEquals("ese documento ya está registrado: hay otro paciente con PA X12345",
                e.getMessage());
        assertTrue(pacientes.buscar("CC", "1020304050").isPresent());
    }

    @Test
    @DisplayName("HU-006 Esc. 9: corregir el documento a uno libre sí se acepta")
    void esc9_documentoLibre() {
        cambiar(HistorialCambio.Tipo.CORRECCION, d -> d.setNumeroDocumento("1020304051"));
        assertTrue(pacientes.buscar("CC", "1020304051").isPresent());
        assertEquals(1, cambios.historial("CC", "1020304051").size());
    }

    @Test
    @DisplayName("HU-006 Esc. 10: una etnia que se inactivó se conserva al cambiar otro dato")
    void esc10_valorInactivoNoTocado() {
        etnias.inactivar("99", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        cambiar(HistorialCambio.Tipo.ACTUALIZACION, d -> d.setZonaTerritorial("02"));
        Paciente p = paciente();
        assertEquals("02", p.getZonaTerritorial());
        assertEquals("99", p.getEtnia());
        assertEquals(List.of("ZONA_TERRITORIAL"), cambios.historial("CC", "1020304050").stream()
                .map(HistorialCambio::getDato).toList());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-006 Esc. 11: cancelar antes de confirmar deja los valores anteriores")
    void esc11_cancelar() {
        String salida = pantalla("1\n" + DOC + "1\n15\n05002\n\nn\n");
        assertTrue(salida.contains("Cancelado: el paciente queda igual."), salida);
        assertEquals("05001", paciente().getMunicipioResidencia());
        assertTrue(cambios.historial("CC", "1020304050").isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-006 Esc. 12: sin paciente con ese documento no se cambia nada")
    void esc12_noExiste() {
        assertEquals("no hay registro con CC 999", assertThrows(IllegalArgumentException.class,
                () -> cambios.cambiar("CC", "999", new DatosPaciente(),
                        HistorialCambio.Tipo.CORRECCION)).getMessage());
        assertTrue(pantalla("1\nCC\n999\n").contains("No se pudo: no hay registro con CC 999."));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-006 Esc. 13: el médico tratante no cambia los datos de un paciente")
    void esc13_medico() {
        sesion.cerrar();
        Usuarios.entrar(usuarios, sesion, Cargo.MEDICO_TRATANTE);
        controlador(terminal("1\n" + DOC + "0\n")).abrir();
        assertEquals("No se pudo: su cargo no tiene ese permiso\n", salida());
        assertFalse(Cargo.MEDICO_TRATANTE.tiene(
                com.ipssaludyvida.hu001.modelos.Permiso.CAMBIAR_PACIENTE));
    }

    @Test
    @DisplayName("HU-006: sin un dato distinto no se guarda nada")
    void sinCambios() {
        assertEquals("no cambió ningún dato", assertThrows(IllegalArgumentException.class,
                () -> cambiar(HistorialCambio.Tipo.CORRECCION, d -> { })).getMessage());
    }

    private List<HistorialCambio> cambiar(HistorialCambio.Tipo tipo,
            Consumer<DatosPaciente> cambio) {
        DatosPaciente d = cambios.datos("CC", "1020304050");
        cambio.accept(d);
        return cambios.cambiar("CC", "1020304050", d, tipo);
    }

    private Paciente paciente() {
        return pacientes.buscar("CC", "1020304050")
                .or(() -> pacientes.buscar("CC", "1020304051")).orElseThrow();
    }

    private void registrar(Consumer<DatosPaciente> cambio) {
        DatosPaciente d = new DatosPaciente();
        d.setNacionalidades(List.of("170"));
        d.setTipoDocumento("CC");
        d.setNumeroDocumento("1020304050");
        d.setPrimerApellido("PÉREZ");
        d.setSegundoApellido("GOMES");
        d.setPrimerNombre("ANA");
        d.setFechaNacimiento("1990-05-17");
        d.setHoraNacimiento("08:30");
        d.setSexoBiologico("02");
        d.setIdentidadGenero("02");
        d.setOcupacion("2221");
        d.setDiscapacidades(List.of("08"));
        d.setPaisResidencia("170");
        d.setMunicipioResidencia("05001");
        d.setEtnia("99");
        d.setZonaTerritorial("01");
        d.setEntidadPlanBeneficios("EPS037");
        cambio.accept(d);
        pacientes.registrar(d);
    }

    // La pantalla de HU-006 con la sesión del auxiliar de admisión: contesta y vuelve.
    // Un test del menú lleva @Timeout (docs/DESIGN.md, sección 3).
    private String pantalla(String respuestas) {
        controlador(terminal(respuestas + "0\n")).abrir();
        return salida();
    }

    private CambiosPacienteControlador controlador(Terminal terminal) {
        return new CambiosPacienteControlador(cambios, paises, tipos, ocupaciones,
                discapacidades, municipios, etnias, comunidades, entidades, terminal, sesion);
    }

    private Terminal terminal(String respuestas) {
        return new Terminal(new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8)),
                new PrintStream(impreso, true, StandardCharsets.UTF_8));
    }

    private String salida() {
        return impreso.toString(StandardCharsets.UTF_8);
    }
}
