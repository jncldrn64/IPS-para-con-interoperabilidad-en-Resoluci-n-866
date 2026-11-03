package com.ipssaludyvida.hu005;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.modelos.Motivo;
import com.ipssaludyvida.hu001.Usuarios;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Cargo;
import com.ipssaludyvida.hu001.modelos.Permiso;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import com.ipssaludyvida.hu004.interfaces.ContratoPacientes;
import com.ipssaludyvida.hu004.modelos.Paciente;
import com.ipssaludyvida.hu004.servicios.DatosPaciente;
import com.ipssaludyvida.hu005.controladores.ConsultaPacienteControlador;
import com.ipssaludyvida.hu005.interfaces.ContratoConsultaPacientes;
import com.ipssaludyvida.hu005.servicios.Ficha;
import com.ipssaludyvida.hu005.servicios.ValorRegistrado;
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
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import com.ipssaludyvida.hu038.modelos.EntidadPlanBeneficios;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
 * Los 13 escenarios de HU-005 (docs/context/trello/tarjetas/HU-005.md). Cada test crea los valores
 * de los catálogos y registra al paciente con HU-004, y después lo consulta.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU005ConsultaPacienteTest {

    static final String REFERENCIA = "Resolución de prueba";

    @Autowired ContratoConsultaPacientes consulta;
    @Autowired ContratoPacientes pacientes;
    @Autowired ContratoPaises paises;
    @Autowired ContratoTiposDocumento tipos;
    @Autowired ContratoOcupaciones ocupaciones;
    @Autowired ContratoCategoriasDiscapacidad discapacidades;
    @Autowired ContratoMunicipios municipios;
    @Autowired DepartamentoRepositorio departamentos;
    @Autowired ContratoEtnias etnias;
    @Autowired ContratoEntidadesPlanBeneficios entidades;
    @Autowired UsuarioRepositorio usuarios;
    @Autowired ContratoSesion sesion;
    private final ByteArrayOutputStream impreso = new ByteArrayOutputStream();

    @BeforeEach
    void catalogos() {
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
        etnias.crear(new Etnia("01", "Indígena"), REFERENCIA);
        etnias.crear(new Etnia("99", "Ninguna de las anteriores"), REFERENCIA);
        entidades.crear(new EntidadPlanBeneficios("EPS037", "Entidad de prueba"), REFERENCIA);
    }

    @AfterEach
    void cerrarLaSesion() {
        sesion.cerrar();
    }

    @Test
    @DisplayName("HU-005 Esc. 1: la consulta trae los datos con el nombre de cada valor")
    void esc1_ficha() {
        registrar(d -> { });
        Ficha f = consulta.consultar("CC", "1020304050").orElseThrow();
        assertEquals("Cédula ciudadanía", f.tipoDocumento().nombre());
        assertEquals("Médicos", f.ocupacion().nombre());
        assertEquals("Ninguna de las anteriores", f.etnia().nombre());
        assertEquals("Entidad de prueba", f.entidadPlanBeneficios().nombre());
        assertEquals("Mujer", f.sexoBiologico().texto());
        assertEquals("Urbana", f.zonaTerritorial().texto());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 1: la pantalla muestra cada dato con su nombre")
    void esc1_pantalla() {
        registrar(d -> { });
        String salida = pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n");
        for (String linea : List.of("ANA PÉREZ GÓMEZ, CC 1020304050",
                "Documento: CC 1020304050 Cédula ciudadanía", "Sexo biológico: 02 Mujer",
                "Identidad de género: 02 Femenino", "Ocupación: 2221 Médicos",
                "Etnia: 99 Ninguna de las anteriores", "Comunidad étnica: No aplica",
                "Zona territorial de residencia: 01 Urbana",
                "Entidad del plan de beneficios: EPS037 Entidad de prueba")) {
            assertTrue(salida.contains(linea + "\n"), linea + " no está en\n" + salida);
        }
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 2: sin paciente con ese documento lo dice")
    void esc2_noExiste() {
        registrar(d -> { });
        assertTrue(consulta.consultar("CC", "999").isEmpty());
        assertTrue(consulta.consultar("PA", "1020304050").isEmpty());
        assertTrue(pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n999\n")
                .contains("No hay registro con CC 999.\n"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 3: sin el tipo de documento pide el tipo")
    void esc3_sinTipo() {
        registrar(d -> { });
        assertEquals("elija el tipo de documento", assertThrows(IllegalArgumentException.class,
                () -> consulta.consultar("", "1020304050")).getMessage());
        assertEquals("escriba el número de documento", assertThrows(
                IllegalArgumentException.class, () -> consulta.consultar("CC", " "))
                .getMessage());
        String salida = pantalla(Cargo.AUXILIAR_DE_ADMISION, "\n1020304050\n");
        assertTrue(salida.contains("No se pudo buscar: elija el tipo de documento. Escriba el"
                + " tipo y el número de documento para buscar."), salida);
        assertFalse(salida.contains("== Paciente"), salida);
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 3: el tipo se elige también por su número en la lista")
    void esc3_tipoPorNumero() {
        registrar(d -> { });
        assertTrue(pantalla(Cargo.AUXILIAR_DE_ADMISION, "1\n1020304050\n")
                .contains("== Paciente"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 4: un paciente con varias nacionalidades las muestra todas")
    void esc4_nacionalidades() {
        registrar(d -> d.setNacionalidades(List.of("862", "170")));
        assertEquals(List.of("170", "862"), consulta.consultar("CC", "1020304050").orElseThrow()
                .nacionalidades().stream().map(ValorRegistrado::codigo).toList());
        assertTrue(pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n")
                .contains("Nacionalidad: 170 COLOMBIA; 862 VENEZUELA\n"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 5: un paciente con varias discapacidades las muestra todas")
    void esc5_discapacidades() {
        registrar(d -> d.setDiscapacidades(List.of("02", "01")));
        assertTrue(pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n").contains(
                "Categoría de discapacidad: 01 Discapacidad física; 02 Discapacidad visual\n"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 6: la fecha y la hora salen por separado")
    void esc6_fechaYHora() {
        registrar(d -> { });
        String salida = pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n");
        assertTrue(salida.contains("Fecha de nacimiento: 1990-05-17\n"
                + "Hora de nacimiento: 08:30\n"), salida);
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 6: con solo la fecha, la hora sale sin dato")
    void esc6_soloFecha() {
        registrar(d -> d.setHoraNacimiento(""));
        String salida = pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n");
        assertTrue(salida.contains("Fecha de nacimiento: 1990-05-17\n"
                + "Hora de nacimiento: Sin dato\n"), salida);
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 7: la ciudad sale con su código DANE y su departamento")
    void esc7_ciudad() {
        registrar(d -> { });
        assertTrue(pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n")
                .contains("Ciudad de residencia: 05001 MEDELLÍN, ANTIOQUIA\n"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 7: fuera de Colombia la ciudad no aplica")
    void esc7_fueraDeColombia() {
        registrar(d -> {
            d.setPaisResidencia("862");
            d.setMunicipioResidencia("");
        });
        assertTrue(pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n")
                .contains("Ciudad de residencia: No aplica: reside fuera de Colombia\n"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 8: los datos opcionales que no se registraron quedan sin dato")
    void esc8_opcionales() {
        registrar(d -> {
            d.setSegundoApellido("");
            d.setOcupacion("");
        });
        String salida = pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n");
        for (String linea : List.of("Segundo apellido: Sin dato", "Segundo nombre: Sin dato",
                "Ocupación: Sin dato", "Primer nombre: ANA")) {
            assertTrue(salida.contains(linea + "\n"), linea + " no está en\n" + salida);
        }
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 9: un valor que se inactivó después sale con su marca")
    void esc9_inactivo() {
        registrar(d -> { });
        entidades.inactivar("EPS037", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertTrue(consulta.consultar("CC", "1020304050").orElseThrow()
                .entidadPlanBeneficios().inactivo());
        assertTrue(pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n").contains(
                "Entidad del plan de beneficios: EPS037 Entidad de prueba [inactivo en el"
                        + " catálogo]\n"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 10: el médico tratante ve los mismos datos, sin opciones de cambio")
    void esc10_medico() {
        registrar(d -> { });
        String auxiliar = ficha(pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n"));
        impreso.reset();
        String medico = pantalla(Cargo.MEDICO_TRATANTE, "CC\n1020304050\n");
        assertEquals(auxiliar, ficha(medico));
        assertFalse(medico.toLowerCase().contains("actualizar"), medico);
        assertFalse(medico.toLowerCase().contains("corregir"), medico);
        assertTrue(Cargo.MEDICO_TRATANTE.tiene(Permiso.CONSULTAR_PACIENTE));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 11: el profesional de estadística no consulta pacientes")
    void esc11_estadistica() {
        registrar(d -> { });
        Usuarios.entrar(usuarios, sesion, Cargo.PROFESIONAL_DE_ESTADISTICA);
        controlador(terminal("1\nCC\n1020304050\n0\n")).abrir();
        assertEquals("No se pudo: su cargo no tiene ese permiso\n", salida());
        assertFalse(Cargo.PROFESIONAL_DE_ESTADISTICA.tiene(Permiso.CONSULTAR_PACIENTE));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 12: consultar no cambia el paciente ni los catálogos")
    void esc12_noCambiaNada() {
        Paciente antes = registrar(d -> { });
        String foto = foto(antes);
        int cambios = tipos.historial("CC").size() + entidades.historial("EPS037").size();
        pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n");
        consulta.consultar("CC", "1020304050");
        assertEquals(foto, foto(pacientes.buscar("CC", "1020304050").orElseThrow()));
        assertEquals(cambios, tipos.historial("CC").size()
                + entidades.historial("EPS037").size());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-005 Esc. 13: un nombre que cambió después sale con el del día y el vigente")
    void esc13_nombreQueCambio() {
        registrar(d -> { });
        tipos.cambiarNombre("CC", "Cédula de ciudadanía", REFERENCIA);
        ValorRegistrado tipo = consulta.consultar("CC", "1020304050").orElseThrow()
                .tipoDocumento();
        assertEquals("Cédula ciudadanía", tipo.nombre());
        assertEquals("Cédula de ciudadanía", tipo.nombreVigente());
        assertTrue(pantalla(Cargo.AUXILIAR_DE_ADMISION, "CC\n1020304050\n").contains(
                "Documento: CC 1020304050 Cédula ciudadanía; nombre vigente en el catálogo:"
                        + " \"Cédula de ciudadanía\"\n"));
    }

    @Test
    @DisplayName("HU-005 Esc. 13: un nombre que cambió antes del registro sale solo")
    void esc13_cambioAntes() {
        tipos.cambiarNombre("CC", "Cédula de ciudadanía", REFERENCIA);
        registrar(d -> { });
        ValorRegistrado tipo = consulta.consultar("CC", "1020304050").orElseThrow()
                .tipoDocumento();
        assertEquals("Cédula de ciudadanía", tipo.nombre());
        assertNull(tipo.nombreVigente());
    }

    private Paciente registrar(Consumer<DatosPaciente> cambio) {
        DatosPaciente d = new DatosPaciente();
        d.setNacionalidades(List.of("170"));
        d.setTipoDocumento("CC");
        d.setNumeroDocumento("1020304050");
        d.setPrimerApellido("PÉREZ");
        d.setSegundoApellido("GÓMEZ");
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
        return pacientes.registrar(d);
    }

    private static String foto(Paciente p) {
        return String.join("|", p.getTipoDocumento(), p.getNumeroDocumento(),
                p.getPrimerApellido(), String.valueOf(p.getSegundoApellido()),
                p.getPrimerNombre(), String.valueOf(p.getFechaNacimiento()),
                String.valueOf(p.getHoraNacimiento()), p.getEntidadPlanBeneficios(),
                p.getNacionalidades().toString(), p.getDiscapacidades().toString(),
                String.valueOf(p.getRegistradoEn()));
    }

    // Lo que la pantalla muestra desde la ficha hasta la fecha del registro.
    private static String ficha(String salida) {
        int desde = salida.indexOf("== Paciente");
        return salida.substring(desde, salida.indexOf('\n', salida.indexOf("Registrado el",
                desde)));
    }

    // La pantalla de HU-005 con la sesión de ese cargo: elige 1, contesta y vuelve.
    // Un test del menú lleva @Timeout (docs/DESIGN.md, sección 3).
    private String pantalla(Cargo cargo, String respuestas) {
        Usuarios.entrar(usuarios, sesion, cargo);
        controlador(terminal("1\n" + respuestas + "0\n")).abrir();
        sesion.cerrar();
        return salida();
    }

    private ConsultaPacienteControlador controlador(Terminal terminal) {
        return new ConsultaPacienteControlador(consulta, tipos, terminal, sesion);
    }

    private Terminal terminal(String respuestas) {
        return new Terminal(new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8)),
                new PrintStream(impreso, true, StandardCharsets.UTF_8));
    }

    private String salida() {
        return impreso.toString(StandardCharsets.UTF_8);
    }
}
