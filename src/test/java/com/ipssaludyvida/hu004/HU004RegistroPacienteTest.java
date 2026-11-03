package com.ipssaludyvida.hu004;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.modelos.Motivo;
import com.ipssaludyvida.hu001.Usuarios;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Cargo;
import com.ipssaludyvida.hu001.modelos.Usuario;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import com.ipssaludyvida.hu004.controladores.PacienteControlador;
import com.ipssaludyvida.hu004.interfaces.ContratoPacientes;
import com.ipssaludyvida.hu004.modelos.Paciente;
import com.ipssaludyvida.hu004.servicios.Campo;
import com.ipssaludyvida.hu004.servicios.DatosPaciente;
import com.ipssaludyvida.hu004.servicios.RegistroInvalido;
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
import com.ipssaludyvida.hu037.modelos.ComunidadEtnica;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import com.ipssaludyvida.hu038.modelos.EntidadPlanBeneficios;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
 * Los 15 escenarios de HU-004 (docs/context/trello/tarjetas/HU-004.md). La base en memoria arranca
 * vacía, así que cada test crea por cambio oficial los valores de los catálogos que usa: pocos,
 * para que la pantalla los liste enteros, salvo el test del escenario 15 que necesita buscar.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU004RegistroPacienteTest {

    static final String REFERENCIA = "Resolución de prueba";

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
    @DisplayName("HU-004 Esc. 1: con los datos obligatorios el paciente se guarda")
    void esc1_registro() {
        Paciente p = pacientes.registrar(completo());
        assertNotNull(p.getId());
        Paciente guardado = pacientes.buscar("CC", "1020304050").orElseThrow();
        assertEquals("PÉREZ", guardado.getPrimerApellido());
        assertEquals("170", guardado.getPaisResidencia());
        assertEquals("EPS037", guardado.getEntidadPlanBeneficios());
        assertEquals("2221", guardado.getOcupacion());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-004 Esc. 1: la pantalla confirma con el tipo y el número de documento")
    void esc1_pantalla() {
        String salida = pantalla(RESPUESTAS + "s\n0\n");
        assertTrue(salida.contains("Registrado el paciente CC 1020304050."), salida);
        assertTrue(pacientes.buscar("CC", "1020304050").isPresent());
    }

    @Test
    @DisplayName("HU-004 Esc. 1: el registro guarda quién lo hizo, la fecha y la hora (RC-5)")
    void esc1_quienLoHizo() {
        Usuario auxiliar = Usuarios.entrar(usuarios, sesion, Cargo.AUXILIAR_DE_ADMISION);
        Paciente p = pacientes.registrar(completo());
        assertEquals(auxiliar.getId(), p.getRegistradoPor());
        assertNotNull(p.getRegistradoEn());
    }

    @Test
    @DisplayName("HU-004 Esc. 2: un paciente con dos nacionalidades guarda los dos países")
    void esc2_dosNacionalidades() {
        Paciente p = pacientes.registrar(con(d -> d.setNacionalidades(List.of("170", "862"))));
        assertEquals(Set.of("170", "862"), pacientes.buscar("CC", "1020304050").orElseThrow()
                .getNacionalidades());
        assertEquals(2, p.getNacionalidades().size());
    }

    @Test
    @DisplayName("HU-004 Esc. 3: la fecha y la hora de nacimiento se guardan por separado")
    void esc3_fechaYHora() {
        Paciente p = pacientes.registrar(completo());
        assertEquals(LocalDate.of(1990, 5, 17), p.getFechaNacimiento());
        assertEquals(LocalTime.of(8, 30), p.getHoraNacimiento());
    }

    @Test
    @DisplayName("HU-004 Esc. 4: sin la hora de nacimiento el registro se acepta")
    void esc4_sinHora() {
        Paciente p = pacientes.registrar(con(d -> d.setHoraNacimiento("")));
        assertNull(p.getHoraNacimiento());
        assertEquals(LocalDate.of(1990, 5, 17), p.getFechaNacimiento());
    }

    @Test
    @DisplayName("HU-004 Esc. 5: una ocupación que agrupa otras se rechaza")
    void esc5_ocupacionGeneral() {
        for (String grupo : List.of("2", "22", "222")) {
            Map<Campo, String> errores = pacientes.validar(con(d -> d.setOcupacion(grupo)));
            assertTrue(errores.get(Campo.OCUPACION).contains("elija una del nivel más"
                    + " específico"), grupo + ": " + errores);
        }
    }

    @Test
    @DisplayName("HU-004 Esc. 5: sin ocupación el registro se acepta")
    void esc5_sinOcupacion() {
        assertNull(pacientes.registrar(con(d -> d.setOcupacion(""))).getOcupacion());
    }

    @Test
    @DisplayName("HU-004 Esc. 6: un paciente con dos discapacidades guarda las dos")
    void esc6_dosDiscapacidades() {
        Paciente p = pacientes.registrar(con(d -> d.setDiscapacidades(List.of("01", "02"))));
        assertEquals(Set.of("01", "02"), p.getDiscapacidades());
    }

    @Test
    @DisplayName("HU-004 Esc. 6: \"Sin discapacidad\" no va con otra categoría")
    void esc6_sinDiscapacidadConOtra() {
        Map<Campo, String> errores = pacientes.validar(
                con(d -> d.setDiscapacidades(List.of("01", "08"))));
        assertEquals("\"Sin discapacidad\" no va con otra categoría",
                errores.get(Campo.DISCAPACIDAD));
    }

    @Test
    @DisplayName("HU-004 Esc. 7: quien reside en Colombia guarda la ciudad con su código DANE")
    void esc7_ciudad() {
        assertEquals("05001", pacientes.registrar(completo()).getMunicipioResidencia());
        assertEquals("falta", pacientes.validar(con(d -> d.setMunicipioResidencia("")))
                .get(Campo.MUNICIPIO_RESIDENCIA));
    }

    @Test
    @DisplayName("HU-004 Esc. 7: quien reside fuera de Colombia no lleva ciudad")
    void esc7_fueraDeColombia() {
        assertEquals("solo se registra si reside en Colombia", pacientes.validar(
                con(d -> d.setPaisResidencia("862"))).get(Campo.MUNICIPIO_RESIDENCIA));
        Paciente p = pacientes.registrar(con(d -> {
            d.setPaisResidencia("862");
            d.setMunicipioResidencia("");
        }));
        assertNull(p.getMunicipioResidencia());
    }

    @Test
    @DisplayName("HU-004 Esc. 8: un valor que no está en su catálogo se rechaza en cada dato")
    void esc8_valorQueNoEsta() {
        comunidades.crear(new ComunidadEtnica("001", "Comunidad de prueba"), REFERENCIA);
        Map<Campo, Consumer<DatosPaciente>> malos = Map.of(
                Campo.NACIONALIDAD, d -> d.setNacionalidades(List.of("999")),
                Campo.TIPO_DOCUMENTO, d -> d.setTipoDocumento("XX"),
                Campo.OCUPACION, d -> d.setOcupacion("9999"),
                Campo.DISCAPACIDAD, d -> d.setDiscapacidades(List.of("06")),
                Campo.PAIS_RESIDENCIA, d -> d.setPaisResidencia("999"),
                Campo.MUNICIPIO_RESIDENCIA, d -> d.setMunicipioResidencia("99999"),
                Campo.ETNIA, d -> d.setEtnia("07"),
                Campo.ENTIDAD_PLAN_BENEFICIOS, d -> d.setEntidadPlanBeneficios("EPS999"));
        malos.forEach((campo, cambio) -> {
            Map<Campo, String> errores = pacientes.validar(con(cambio));
            assertEquals(Set.of(campo), errores.keySet(), campo + ": " + errores);
            assertTrue(errores.get(campo).contains("no es un valor activo")
                    || errores.get(campo).contains("no son valores activos"), errores.toString());
        });
        Map<Campo, String> errores = pacientes.validar(con(d -> {
            d.setEtnia("01");
            d.setComunidadEtnica("999");
        }));
        assertEquals(Set.of(Campo.COMUNIDAD_ETNICA), errores.keySet(), errores.toString());
    }

    @Test
    @DisplayName("HU-004 Esc. 8: un valor inactivo de su catálogo se rechaza (RC-2)")
    void esc8_valorInactivo() {
        tipos.inactivar("PA", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        Map<Campo, String> errores = pacientes.validar(con(d -> d.setTipoDocumento("PA")));
        assertEquals("\"PA\" no es un valor activo del catálogo de tipos de documento",
                errores.get(Campo.TIPO_DOCUMENTO));
    }

    @Test
    @DisplayName("HU-004 Esc. 9: un dato que no cumple su numeral se rechaza y se dice cuál")
    void esc9_numeral() {
        Map<Campo, Consumer<DatosPaciente>> malos = Map.of(
                Campo.NUMERO_DOCUMENTO, d -> d.setNumeroDocumento("12"),
                Campo.PRIMER_APELLIDO, d -> d.setPrimerApellido("P"),
                Campo.SEGUNDO_NOMBRE, d -> d.setSegundoNombre("A".repeat(61)),
                Campo.FECHA_NACIMIENTO, d -> d.setFechaNacimiento("1990-13-01"),
                Campo.HORA_NACIMIENTO, d -> d.setHoraNacimiento("25:00"),
                Campo.SEXO_BIOLOGICO, d -> d.setSexoBiologico("09"),
                Campo.IDENTIDAD_GENERO, d -> d.setIdentidadGenero("06"),
                Campo.ZONA_TERRITORIAL, d -> d.setZonaTerritorial("03"));
        malos.forEach((campo, cambio) -> assertEquals(Set.of(campo),
                pacientes.validar(con(cambio)).keySet(), campo.toString()));
        assertEquals("tiene de 3 a 20 letras o dígitos (numeral 2)", pacientes.validar(
                con(d -> d.setNumeroDocumento("12"))).get(Campo.NUMERO_DOCUMENTO));
        assertEquals("\"09\" no está en la lista del numeral 5", pacientes.validar(
                con(d -> d.setSexoBiologico("09"))).get(Campo.SEXO_BIOLOGICO));
    }

    @Test
    @DisplayName("HU-004 Esc. 9: una fecha de nacimiento futura se rechaza")
    void esc9_fechaFutura() {
        assertTrue(pacientes.validar(con(d -> d.setFechaNacimiento("2099-01-01")))
                .get(Campo.FECHA_NACIMIENTO).contains("es futura"));
    }

    @Test
    @DisplayName("HU-004 Esc. 10: sin los datos obligatorios no se guarda y se dicen cuáles faltan")
    void esc10_faltanDatos() {
        RegistroInvalido e = assertThrows(RegistroInvalido.class,
                () -> pacientes.registrar(new DatosPaciente()));
        assertEquals(List.of(Campo.NACIONALIDAD, Campo.TIPO_DOCUMENTO, Campo.NUMERO_DOCUMENTO,
                Campo.PRIMER_APELLIDO, Campo.PRIMER_NOMBRE, Campo.FECHA_NACIMIENTO,
                Campo.SEXO_BIOLOGICO, Campo.IDENTIDAD_GENERO, Campo.DISCAPACIDAD,
                Campo.PAIS_RESIDENCIA, Campo.ETNIA, Campo.ZONA_TERRITORIAL,
                Campo.ENTIDAD_PLAN_BENEFICIOS), new ArrayList<>(e.getErrores().keySet()));
        assertTrue(e.getErrores().values().stream().allMatch("falta"::equals));
        assertTrue(pacientes.buscar("CC", "1020304050").isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-004 Esc. 10: la pantalla dice cuáles faltan")
    void esc10_pantalla() {
        String salida = pantalla(RESPUESTAS.replace("Ana\n", "\n") + "s\nAna\ns\n0\n");
        assertTrue(salida.contains("  - primer nombre: falta\n"), salida);
        assertTrue(salida.contains("Registrado el paciente CC 1020304050."), salida);
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-004 Esc. 11: se conservan los datos válidos y se pide solo el que está mal")
    void esc11_conservaLosValidos() {
        String salida = pantalla(RESPUESTAS.replace("1020304050\n", "12\n") + "s\n1020304050\n"
                + "s\n0\n");
        assertTrue(salida.contains("Corrija estos datos; los demás se conservan:\n"
                + "  - número de documento: tiene de 3 a 20 letras o dígitos (numeral 2)\n"),
                salida);
        assertEquals(2, veces(salida, "Número de documento: "), salida);
        assertEquals(1, veces(salida, "Primer apellido: "), salida);
        Paciente p = pacientes.buscar("CC", "1020304050").orElseThrow();
        assertEquals("PÉREZ", p.getPrimerApellido());
        assertEquals("05001", p.getMunicipioResidencia());
    }

    @Test
    @DisplayName("HU-004 Esc. 12: un paciente con el mismo tipo y número no se registra otra vez")
    void esc12_repetido() {
        pacientes.registrar(completo());
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> pacientes.registrar(con(d -> d.setPrimerNombre("Otra"))));
        assertEquals("ya hay un paciente registrado con el documento CC 1020304050",
                e.getMessage());
        assertEquals("ANA", pacientes.buscar("CC", "1020304050").orElseThrow()
                .getPrimerNombre());
    }

    @Test
    @DisplayName("HU-004 Esc. 12: el mismo número con otro tipo de documento sí se registra")
    void esc12_otroTipo() {
        pacientes.registrar(completo());
        pacientes.registrar(con(d -> d.setTipoDocumento("PA")));
        assertTrue(pacientes.buscar("PA", "1020304050").isPresent());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-004 Esc. 13: cancelar antes de guardar no guarda nada")
    void esc13_cancelar() {
        String salida = pantalla(RESPUESTAS + "n\n0\n");
        assertTrue(salida.contains("Cancelado: no se guardó el paciente."), salida);
        assertTrue(pacientes.buscar("CC", "1020304050").isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-004 Esc. 14: la pantalla dice qué catálogo falta cargar")
    void esc14_avisoAlAbrir() {
        String salida = pantalla("0\n");
        assertTrue(salida.contains("Aviso: falta cargar el catálogo de comunidades étnicas:"
                + " mientras esté vacío, se registra sin comunidad étnica"), salida);
        assertFalse(salida.contains("catálogo de municipios"), salida);
    }

    @Test
    @DisplayName("HU-004 Esc. 14: sin municipios no se registra a quien reside en Colombia")
    void esc14_sinMunicipios() {
        municipios.inactivar("05001", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertTrue(pacientes.catalogosSinValores().contains("falta cargar el catálogo de"
                + " municipios: es obligatorio para quien reside en Colombia"));
        assertEquals("falta cargar el catálogo de municipios", pacientes.validar(
                con(d -> d.setMunicipioResidencia(""))).get(Campo.MUNICIPIO_RESIDENCIA));
        Paciente p = pacientes.registrar(con(d -> {
            d.setPaisResidencia("862");
            d.setMunicipioResidencia("");
        }));
        assertNotNull(p.getId());
    }

    @Test
    @DisplayName("HU-004 Esc. 14: con comunidades cargadas, una etnia 01 a 06 la pide")
    void esc14_comunidad() {
        Paciente sinComunidad = pacientes.registrar(con(d -> d.setEtnia("01")));
        assertNull(sinComunidad.getComunidadEtnica());
        comunidades.crear(new ComunidadEtnica("001", "Comunidad de prueba"), REFERENCIA);
        assertEquals("falta", pacientes.validar(con(d -> {
            d.setNumeroDocumento("555666");
            d.setEtnia("01");
        })).get(Campo.COMUNIDAD_ETNICA));
        assertEquals("solo se registra con una etnia de la 01 a la 06", pacientes.validar(
                con(d -> d.setComunidadEtnica("001"))).get(Campo.COMUNIDAD_ETNICA));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-004 Esc. 15: la ciudad se busca por su nombre y sale con su departamento")
    void esc15_buscarCiudad() {
        for (int i = 2; i <= 22; i++) {
            municipios.crear(new Municipio(String.format("05%03d", i), "MUNICIPIO " + i),
                    REFERENCIA);
        }
        municipios.crear(new Municipio("05099", "MEDELLINCITO"), REFERENCIA);
        municipios.inactivar("05099", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        String salida = pantalla(RESPUESTAS.replace("05001\n", "xyz\nmedellin\n1\n") + "s\n0\n");
        assertTrue(salida.contains("  No hay municipios que coincidan con la búsqueda."), salida);
        assertTrue(salida.contains("  1. 05001  MEDELLÍN  ANTIOQUIA (05)\n"), salida);
        assertFalse(salida.contains("MEDELLINCITO"), salida);
        assertEquals("05001", pacientes.buscar("CC", "1020304050").orElseThrow()
                .getMunicipioResidencia());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-004 Esc. 15: el código exacto elige el valor sin pasar por la lista")
    void esc15_codigoExacto() {
        for (int i = 2; i <= 22; i++) {
            municipios.crear(new Municipio(String.format("05%03d", i), "MUNICIPIO " + i),
                    REFERENCIA);
        }
        String salida = pantalla(RESPUESTAS + "s\n0\n");
        assertTrue(salida.contains("  Elegido: 05001  MEDELLÍN  ANTIOQUIA (05)\n"), salida);
        assertFalse(salida.contains("  2. 05"), salida);
        assertEquals("05001", pacientes.buscar("CC", "1020304050").orElseThrow()
                .getMunicipioResidencia());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-004: solo el auxiliar de admisión abre el registro (HU-001, escenario 8)")
    void permiso() {
        impreso.reset();
        Usuarios.entrar(usuarios, sesion, Cargo.MEDICO_TRATANTE);
        controlador(terminal("1\n0\n")).abrir();
        assertEquals("No se pudo: su cargo no tiene ese permiso\n", salida());
    }

    // Lo que contesta el auxiliar, dato por dato, con los catálogos de prueba listados enteros:
    // Colombia, CC, el documento, dos apellidos, un nombre, la fecha y la hora, mujer, femenino,
    // médicos, sin discapacidad, Colombia, Medellín, ninguna etnia, urbana y la entidad.
    static final String RESPUESTAS = "170\nn\nCC\n1020304050\nPÉREZ\nGÓMEZ\nAna\n\n1990-05-17\n"
            + "08:30\n02\n02\n2221\n08\nn\n170\n05001\n99\n01\nEPS037\n";

    private static DatosPaciente completo() {
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
        return d;
    }

    private static DatosPaciente con(Consumer<DatosPaciente> cambio) {
        DatosPaciente d = completo();
        cambio.accept(d);
        return d;
    }

    // La pantalla de HU-004 con la sesión del auxiliar de admisión, que la abre, elige 1 y
    // contesta. Un test del menú lleva @Timeout (docs/DESIGN.md, sección 3).
    private String pantalla(String respuestas) {
        Usuarios.entrar(usuarios, sesion, Cargo.AUXILIAR_DE_ADMISION);
        String entrada = respuestas.equals("0\n") ? respuestas : "1\n" + respuestas;
        controlador(terminal(entrada)).abrir();
        return salida();
    }

    private PacienteControlador controlador(Terminal terminal) {
        return new PacienteControlador(pacientes, paises, tipos, ocupaciones, discapacidades,
                municipios, etnias, comunidades, entidades, terminal, sesion);
    }

    private Terminal terminal(String respuestas) {
        return new Terminal(new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8)),
                new PrintStream(impreso, true, StandardCharsets.UTF_8));
    }

    private String salida() {
        return impreso.toString(StandardCharsets.UTF_8);
    }

    private static int veces(String texto, String buscado) {
        return texto.split(java.util.regex.Pattern.quote(buscado), -1).length - 1;
    }
}
