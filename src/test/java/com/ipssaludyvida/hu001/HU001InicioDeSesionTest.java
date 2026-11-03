package com.ipssaludyvida.hu001;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ipssaludyvida.comun.menu.MenuPrincipal;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.modelos.CatalogoCambio;
import com.ipssaludyvida.comun.servicios.DatosVersion;
import com.ipssaludyvida.hu001.controladores.SesionControlador;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Cargo;
import com.ipssaludyvida.hu001.modelos.Permiso;
import com.ipssaludyvida.hu001.modelos.Usuario;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import com.ipssaludyvida.hu001.servicios.SesionServicio;
import com.ipssaludyvida.hu001.servicios.UsuariosIniciales;
import com.ipssaludyvida.hu036.controladores.EtniaControlador;
import com.ipssaludyvida.hu036.interfaces.ContratoEtnias;
import com.ipssaludyvida.hu036.modelos.Etnia;
import com.ipssaludyvida.hu037.controladores.ComunidadEtnicaControlador;
import com.ipssaludyvida.hu037.interfaces.ContratoComunidadesEtnicas;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Los 14 escenarios de HU-001 (docs/context/trello/tarjetas/HU-001.md). Los escenarios 5 y 6
 * prueban las opciones que existen hoy: las de pacientes, atenciones, oposición y voluntad
 * anticipada se prueban en la matriz y se mostrarán con su historia.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU001InicioDeSesionTest {

    static final String REFERENCIA = "Resolución de prueba";
    static final String ESTADISTICA = "profesional_de_estadistica";
    static final String AUXILIAR = "auxiliar_de_admision";
    static final String MEDICO = "medico_tratante";

    @Autowired UsuarioRepositorio usuarios;
    @Autowired SesionServicio sesionServicio;
    @Autowired ContratoSesion sesion;
    @Autowired ContratoEtnias etnias;
    @Autowired ContratoComunidadesEtnicas comunidades;
    @Autowired DataSource base;
    private final ByteArrayOutputStream impreso = new ByteArrayOutputStream();

    // La sesión es un bean de Spring que vive entre tests: uno que la deja abierta cambia el
    // siguiente.
    @AfterEach
    void cerrarLaSesion() {
        sesion.cerrar();
    }

    @Test
    @DisplayName("HU-001 Precondición: al instalar quedan un usuario de prueba por cargo")
    void precondicion_unUsuarioDePruebaPorCargo() {
        new UsuariosIniciales(usuarios, sesionServicio).run();
        assertEquals(3, usuarios.count());
        for (String usuario : List.of("admision", "medico", "estadistica")) {
            sesion.iniciar(usuario, UsuariosIniciales.CLAVE_DE_PRUEBA);
            sesion.cerrar();
        }
        assertFalse(usuarios.findByUsuario("admision").orElseThrow().getClaveHash()
                .contains(UsuariosIniciales.CLAVE_DE_PRUEBA), "la clave se guarda cifrada");
        new UsuariosIniciales(usuarios, sesionServicio).run();
        assertEquals(3, usuarios.count(), "con usuarios en la base no crea más");
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 1: con datos correctos entra y ve las opciones de su cargo")
    void esc1_entraConDatosCorrectos() throws Exception {
        Usuarios.crear(usuarios, Cargo.PROFESIONAL_DE_ESTADISTICA);
        String salida = menu("1\n" + ESTADISTICA + "\n" + Usuarios.CLAVE + "\n0\n");
        assertTrue(salida.contains("Bienvenido, Profesional de estadística de prueba. Cargo:"
                + " Profesional de estadística."), salida);
        assertTrue(salida.contains("1. Etnias (HU-036)"), salida);
        assertTrue(salida.contains("2. Comunidades étnicas (HU-037)"), salida);
        assertTrue(salida.contains("C. Cerrar sesión"), salida);
    }

    @Test
    @DisplayName("HU-001 Esc. 2: con la contraseña equivocada no entra y no dice cuál falló")
    void esc2_contrasenaEquivocada() {
        Usuarios.crear(usuarios, Cargo.MEDICO_TRATANTE);
        assertNoEntra(MEDICO, "OtraClave", "el usuario o la contraseña no son válidos");
    }

    @Test
    @DisplayName("HU-001 Esc. 2: con un usuario que no existe da el mismo mensaje")
    void esc2_usuarioQueNoExiste() {
        assertNoEntra("nadie", Usuarios.CLAVE, "el usuario o la contraseña no son válidos");
    }

    @Test
    @DisplayName("HU-001 Esc. 2: con un usuario inactivo da el mismo mensaje")
    void esc2_usuarioInactivo() {
        Usuario u = Usuarios.crear(usuarios, Cargo.MEDICO_TRATANTE);
        u.setActivo(false);
        usuarios.save(u);
        assertNoEntra(MEDICO, Usuarios.CLAVE, "el usuario o la contraseña no son válidos");
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 2: la pantalla dice que los datos no son válidos")
    void esc2_laPantallaLoDice() throws Exception {
        Usuarios.crear(usuarios, Cargo.MEDICO_TRATANTE);
        String salida = menu("1\n" + MEDICO + "\nOtraClave\n0\n");
        assertTrue(salida.contains(
                "No se pudo entrar: el usuario o la contraseña no son válidos"), salida);
        assertFalse(salida.contains("Bienvenido"), salida);
    }

    @Test
    @DisplayName("HU-001 Esc. 3: sin usuario dice que falta el usuario")
    void esc3_faltaElUsuario() {
        assertNoEntra("", Usuarios.CLAVE, "falta el usuario");
        assertNoEntra("   ", Usuarios.CLAVE, "falta el usuario");
    }

    @Test
    @DisplayName("HU-001 Esc. 3: sin contraseña dice que falta la contraseña")
    void esc3_faltaLaContrasena() {
        Usuarios.crear(usuarios, Cargo.MEDICO_TRATANTE);
        assertNoEntra(MEDICO, "", "falta la contraseña");
    }

    @Test
    @DisplayName("HU-001 Esc. 3: sin los dos dice que faltan los dos")
    void esc3_faltanLosDos() {
        assertNoEntra("", "", "faltan el usuario y la contraseña");
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 4: sin sesión el menú no muestra ninguna historia")
    void esc4_elMenuPideIniciarSesion() throws Exception {
        String salida = menu("0\n");
        assertTrue(salida.contains("== Inicio de sesión"), salida);
        assertFalse(salida.contains("(HU-036)"), salida);
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 4: sin sesión un catálogo no se consulta ni se cambia")
    void esc4_elCatalogoPideIniciarSesion() {
        etnias.crear(new Etnia("01", "Indígena"), REFERENCIA);
        new EtniaControlador(etnias, terminal("1\n3\n0\n"), sesion).abrir();
        assertEquals("No se pudo: inicie sesión para usar el sistema\n", salida());
    }

    @Test
    @DisplayName("HU-001 Esc. 4: sin sesión no hay ningún permiso")
    void esc4_sinSesionNoHayPermisos() {
        for (Permiso p : Permiso.values()) {
            assertFalse(sesion.puede(p));
            IllegalStateException e = assertThrows(IllegalStateException.class,
                    () -> sesion.exigir(p));
            assertEquals(ContratoSesion.SIN_SESION, e.getMessage());
        }
    }

    @Test
    @DisplayName("HU-001 Esc. 5: el auxiliar de admisión tiene pacientes, oposición y voluntad")
    void esc5_matrizDelAuxiliar() {
        assertEquals(EnumSet.of(Permiso.CAMBIAR_PACIENTE, Permiso.CONSULTAR_PACIENTE,
                Permiso.CAMBIAR_OPOSICION_Y_VOLUNTAD, Permiso.CONSULTAR_OPOSICION_Y_VOLUNTAD,
                Permiso.CONSULTAR_CATALOGOS), permisos(Cargo.AUXILIAR_DE_ADMISION));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 5: el auxiliar de admisión no ve el mantenimiento de catálogos")
    void esc5_elAuxiliarNoVeElMantenimiento() {
        Usuarios.entrar(usuarios, sesion, Cargo.AUXILIAR_DE_ADMISION);
        assertSoloConsulta(opcionesDelCatalogo());
    }

    @Test
    @DisplayName("HU-001 Esc. 6: el médico tratante tiene atenciones y consulta lo demás")
    void esc6_matrizDelMedico() {
        assertEquals(EnumSet.of(Permiso.CONSULTAR_PACIENTE, Permiso.CONSULTAR_OPOSICION_Y_VOLUNTAD,
                Permiso.CAMBIAR_ATENCIONES, Permiso.CONSULTAR_ATENCIONES,
                Permiso.CONSULTAR_CATALOGOS), permisos(Cargo.MEDICO_TRATANTE));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 6: el médico tratante no ve el mantenimiento de catálogos")
    void esc6_elMedicoNoVeElMantenimiento() {
        Usuarios.entrar(usuarios, sesion, Cargo.MEDICO_TRATANTE);
        assertSoloConsulta(opcionesDelCatalogo());
    }

    @Test
    @DisplayName("HU-001 Esc. 7: el profesional de estadística tiene solo los catálogos")
    void esc7_matrizDeEstadistica() {
        assertEquals(EnumSet.of(Permiso.MANTENER_CATALOGOS, Permiso.CONSULTAR_CATALOGOS),
                permisos(Cargo.PROFESIONAL_DE_ESTADISTICA));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 7: el profesional de estadística ve las 11 opciones del catálogo")
    void esc7_estadisticaVeElMantenimiento() {
        Usuarios.entrar(usuarios, sesion, Cargo.PROFESIONAL_DE_ESTADISTICA);
        String salida = opcionesDelCatalogo();
        for (String opcion : List.of("1. Consultar", "2. Crear por cambio oficial",
                "3. Cambiar el nombre", "4. Cambiar el código", "5. Inactivar", "6. Reactivar",
                "7. Historial de un código", "8. Cargar desde un archivo, CSV o Excel", "9. Buscar",
                "10. Historial de versiones", "11. Comparar con la lista oficial")) {
            assertTrue(salida.contains(opcion), opcion + " no está en\n" + salida);
        }
    }

    @Test
    @DisplayName("HU-001 Esc. 8: un médico no puede actualizar los datos del paciente")
    void esc8_elMedicoNoActualizaAlPaciente() {
        Usuarios.entrar(usuarios, sesion, Cargo.MEDICO_TRATANTE);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> sesion.exigir(Permiso.CAMBIAR_PACIENTE));
        assertEquals(ContratoSesion.SIN_PERMISO, e.getMessage());
    }

    @Test
    @DisplayName("HU-001 Esc. 8: el profesional de estadística no puede consultar atenciones")
    void esc8_estadisticaNoConsultaAtenciones() {
        Usuarios.entrar(usuarios, sesion, Cargo.PROFESIONAL_DE_ESTADISTICA);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> sesion.exigir(Permiso.CONSULTAR_ATENCIONES));
        assertEquals(ContratoSesion.SIN_PERMISO, e.getMessage());
    }

    @Test
    @DisplayName("HU-001 Esc. 9: un cambio de catálogo guarda quién lo hizo, la fecha y la hora")
    void esc9_elCambioGuardaQuienLoHizo() {
        Usuario u = Usuarios.entrar(usuarios, sesion, Cargo.PROFESIONAL_DE_ESTADISTICA);
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        CatalogoCambio cambio = etnias.historial("07").get(0);
        assertEquals(u.getId(), cambio.getUsuarioId());
        assertTrue(cambio.getFechaHora() != null);
    }

    @Test
    @DisplayName("HU-001 Esc. 9: una carga guarda quién la hizo")
    void esc9_laCargaGuardaQuienLaHizo() {
        Usuario u = Usuarios.entrar(usuarios, sesion, Cargo.PROFESIONAL_DE_ESTADISTICA);
        DatosVersion datos = new DatosVersion("Resolución 866 de 2021", "", LocalDate.of(2026,
                10, 18), "Resolución 866 de 2021", LocalDate.of(2021, 6, 25));
        etnias.confirmarCarga(etnias.leerCarga(
                Path.of("data/catalogos/etnias-hoja-15.csv"), datos), Map.of());
        assertEquals(u.getId(), etnias.versionVigente().orElseThrow().getUsuarioId());
        assertEquals(u.getId(), etnias.historial("01").get(0).getUsuarioId());
    }

    @Test
    @DisplayName("HU-001 Esc. 9: lo que se registra sin sesión queda sin autor")
    void esc9_sinSesionQuedaSinAutor() {
        etnias.crear(new Etnia("07", "Etnia de prueba"), REFERENCIA);
        assertEquals(null, etnias.historial("07").get(0).getUsuarioId());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 10: pasar de un catálogo a otro no pide entrar de nuevo")
    void esc10_laSesionSigueAlCambiarDeOpcion() throws Exception {
        Usuarios.crear(usuarios, Cargo.PROFESIONAL_DE_ESTADISTICA);
        String salida = menu("1\n" + ESTADISTICA + "\n" + Usuarios.CLAVE
                + "\n1\n0\n2\n0\n1\n0\n0\n");
        assertEquals(1, veces(salida, "== Inicio de sesión"), salida);
        assertEquals(2, veces(salida, "== Etnias"), salida);
        assertEquals(1, veces(salida, "== Comunidades étnicas"), salida);
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 11: cerrar la sesión pide identificarse de nuevo")
    void esc11_cerrarLaSesion() throws Exception {
        Usuarios.crear(usuarios, Cargo.PROFESIONAL_DE_ESTADISTICA);
        String salida = menu("1\n" + ESTADISTICA + "\n" + Usuarios.CLAVE + "\nC\n0\n");
        assertTrue(salida.contains("Sesión cerrada."), salida);
        assertEquals(2, veces(salida, "== Inicio de sesión"), salida);
        assertFalse(sesion.abierta());
    }

    @Test
    @DisplayName("HU-001 Esc. 12: con 15 minutos sin actividad la sesión se cierra")
    void esc12_seCierraALos15Minutos() {
        Reloj reloj = new Reloj();
        SesionServicio propia = new SesionServicio(usuarios, reloj);
        Usuarios.crear(usuarios, Cargo.MEDICO_TRATANTE);
        propia.iniciar(MEDICO, Usuarios.CLAVE);
        reloj.avanzar(Duration.ofMinutes(15));
        assertFalse(propia.seguir());
        assertFalse(propia.abierta());
    }

    @Test
    @DisplayName("HU-001 Esc. 12: con 14 minutos sin actividad la sesión sigue, y vuelve a contar")
    void esc12_sigueAntesDeLos15Minutos() {
        Reloj reloj = new Reloj();
        SesionServicio propia = new SesionServicio(usuarios, reloj);
        Usuarios.crear(usuarios, Cargo.MEDICO_TRATANTE);
        propia.iniciar(MEDICO, Usuarios.CLAVE);
        reloj.avanzar(Duration.ofMinutes(14));
        assertTrue(propia.seguir());
        reloj.avanzar(Duration.ofMinutes(14));
        assertTrue(propia.seguir(), "cada acción vuelve a contar los 15 minutos");
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 12: después de 15 minutos el menú pide identificarse de nuevo")
    void esc12_elMenuPideEntrarDeNuevo() throws Exception {
        Reloj reloj = new Reloj();
        SesionServicio propia = new SesionServicio(usuarios, reloj);
        Usuarios.crear(usuarios, Cargo.PROFESIONAL_DE_ESTADISTICA);
        propia.iniciar(ESTADISTICA, Usuarios.CLAVE);
        reloj.avanzar(Duration.ofMinutes(15));
        Terminal terminal = terminal("1\n0\n");
        new MenuPrincipal(List.of(new EtniaControlador(etnias, terminal, propia)), terminal,
                base, propia, new SesionControlador(propia, terminal)).run();
        assertTrue(salida().contains(SesionControlador.VENCIDA), salida());
        assertTrue(salida().contains("== Inicio de sesión"), salida());
        assertFalse(salida().contains("== Etnias"), salida());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 12: dentro de un catálogo, después de 15 minutos vuelve al menú")
    void esc12_elCatalogoVuelveAlMenu() {
        Reloj reloj = new Reloj();
        SesionServicio propia = new SesionServicio(usuarios, reloj);
        Usuarios.crear(usuarios, Cargo.PROFESIONAL_DE_ESTADISTICA);
        propia.iniciar(ESTADISTICA, Usuarios.CLAVE);
        Terminal terminal = terminal("2\n");
        reloj.avanzar(Duration.ofMinutes(15));
        new EtniaControlador(etnias, terminal, propia).abrir();
        assertTrue(salida().endsWith(SesionControlador.VENCIDA + "\n"), salida());
        assertTrue(etnias.listar().isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 13: la pantalla de inicio no ofrece recuperar la clave")
    void esc13_sinRecuperacionDeClave() throws Exception {
        String salida = menu("0\n").toLowerCase();
        assertFalse(salida.contains("recuper"), salida);
        assertFalse(salida.contains("olvid"), salida);
        assertTrue(salida.contains("1. iniciar sesión\n0. salir\n"), salida);
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 14: el auxiliar de admisión no crea ni carga un catálogo")
    void esc14_elAuxiliarNoCambiaElCatalogo() {
        Usuarios.entrar(usuarios, sesion, Cargo.AUXILIAR_DE_ADMISION);
        new EtniaControlador(etnias, terminal("2\n8\n0\n"), sesion).abrir();
        assertEquals(2, veces(salida(), "No se pudo: su cargo no tiene ese permiso\n"), salida());
        assertFalse(salida().contains("Código: "), salida());
        assertTrue(etnias.listar().isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 14: el médico tratante no cambia, inactiva ni reactiva un valor")
    void esc14_elMedicoNoCambiaElCatalogo() {
        etnias.crear(new Etnia("01", "Indígena"), REFERENCIA);
        Usuarios.entrar(usuarios, sesion, Cargo.MEDICO_TRATANTE);
        new ComunidadEtnicaControlador(comunidades, terminal("2\n0\n"), sesion).abrir();
        new EtniaControlador(etnias, terminal("3\n4\n5\n6\n0\n"), sesion).abrir();
        assertEquals(5, veces(salida(), "No se pudo: su cargo no tiene ese permiso\n"), salida());
        assertEquals("Indígena", etnias.buscarPorCodigo("01").orElseThrow().getNombre());
        assertEquals(1, etnias.historial("01").size());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-001 Esc. 14: el auxiliar y el médico sí consultan y buscan en el catálogo")
    void esc14_siConsultanYBuscan() {
        etnias.crear(new Etnia("01", "Indígena"), REFERENCIA);
        for (Cargo cargo : List.of(Cargo.AUXILIAR_DE_ADMISION, Cargo.MEDICO_TRATANTE)) {
            impreso.reset();
            Usuarios.entrar(usuarios, sesion, cargo);
            new EtniaControlador(etnias, terminal("1\n3\n9\nIndíg\n0\n"), sesion).abrir();
            assertEquals(2, veces(salida(), "01  Indígena  ACTIVO"), cargo + "\n" + salida());
            assertFalse(salida().contains("No se pudo"), salida());
            sesion.cerrar();
        }
    }

    private void assertNoEntra(String usuario, String clave, String mensaje) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> sesion.iniciar(usuario, clave));
        assertEquals(mensaje, e.getMessage());
        assertFalse(sesion.abierta());
    }

    private static void assertSoloConsulta(String salida) {
        for (String opcion : List.of("1. Consultar", "7. Historial de un código", "9. Buscar",
                "10. Historial de versiones", "11. Comparar con la lista oficial")) {
            assertTrue(salida.contains(opcion), opcion + " no está en\n" + salida);
        }
        for (String opcion : List.of("2. ", "3. ", "4. ", "5. ", "6. ", "8. ")) {
            assertFalse(salida.contains("\n" + opcion), opcion + " está en\n" + salida);
        }
    }

    private String opcionesDelCatalogo() {
        new EtniaControlador(etnias, terminal("0\n"), sesion).abrir();
        return salida();
    }

    private String menu(String respuestas) throws Exception {
        Terminal terminal = terminal(respuestas);
        new MenuPrincipal(List.of(new ComunidadEtnicaControlador(comunidades, terminal, sesion),
                new EtniaControlador(etnias, terminal, sesion)), terminal, base, sesion,
                new SesionControlador(sesion, terminal)).run();
        return salida();
    }

    private static Set<Permiso> permisos(Cargo cargo) {
        Set<Permiso> tiene = EnumSet.noneOf(Permiso.class);
        for (Permiso p : Permiso.values()) {
            if (cargo.tiene(p)) {
                tiene.add(p);
            }
        }
        return tiene;
    }

    private static int veces(String texto, String buscado) {
        return texto.split(java.util.regex.Pattern.quote(buscado), -1).length - 1;
    }

    private Terminal terminal(String respuestas) {
        return new Terminal(new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8)),
                new PrintStream(impreso, true, StandardCharsets.UTF_8));
    }

    private String salida() {
        return impreso.toString(StandardCharsets.UTF_8);
    }

    /** Un reloj que avanza solo cuando el test lo pide (escenario 12). */
    private static final class Reloj extends Clock {

        private Instant ahora = Instant.parse("2026-11-01T15:00:00Z");

        void avanzar(Duration cuanto) {
            ahora = ahora.plus(cuanto);
        }

        @Override
        public ZoneId getZone() {
            return ZoneId.of("America/Bogota");
        }

        @Override
        public Clock withZone(ZoneId zona) {
            return this;
        }

        @Override
        public Instant instant() {
            return ahora;
        }
    }
}
