package com.ipssaludyvida.comun;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.ipssaludyvida.comun.interfaces.Controlador;
import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.menu.MenuPrincipal;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.servicios.DatosVersion;
import com.ipssaludyvida.hu001.Usuarios;
import com.ipssaludyvida.hu001.controladores.SesionControlador;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Cargo;
import com.ipssaludyvida.hu001.modelos.Permiso;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import com.ipssaludyvida.hu018.interfaces.ContratoPaises;
import com.ipssaludyvida.hu022.interfaces.ContratoOcupaciones;
import com.ipssaludyvida.hu026.interfaces.ContratoMunicipios;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu035.interfaces.ContratoCategoriasDiscapacidad;
import com.ipssaludyvida.hu036.controladores.EtniaControlador;
import com.ipssaludyvida.hu036.interfaces.ContratoEtnias;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * El programa entero, de la entrada al cierre, con un teclado que contesta cada pregunta con algo
 * que una persona escribiría mal: letras donde va un número, fechas imposibles, rutas que no
 * existen, códigos de otro catálogo. Lo pidió el autor el 2026-11-02: que un valor equivocado dé
 * un mensaje y el menú siga, en todas las historias. No es el escenario de una historia
 * (docs/DESIGN.md, sección 3).
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EntradasInvalidasTest {

    // Respuestas por pregunta antes de salir. Con 1.500 cada corrida pasa por todos los menús
    // varias veces, y con tres semillas por cargo tarda unos segundos.
    private static final int RESPUESTAS = 1500;

    @Autowired ApplicationContext contexto;
    @Autowired List<Controlador> controladores;
    @Autowired UsuarioRepositorio usuarios;
    @Autowired ContratoSesion sesion;
    @Autowired DataSource base;
    @Autowired ContratoEtnias etnias;
    @TempDir Path carpeta;

    @ParameterizedTest
    @EnumSource(Cargo.class)
    @Timeout(120)
    void elProgramaSigueConCualquierEntrada(Cargo cargo) throws Exception {
        recorrer(cargo);
    }

    /**
     * Lo mismo con los siete catálogos oficiales cargados, para que el registro de un paciente
     * llegue a guardar y la consulta y la corrección tengan a quién mostrar (HU-004 a HU-006).
     */
    @ParameterizedTest
    @EnumSource(Cargo.class)
    @Timeout(180)
    void elProgramaSigueConLosCatalogosCargados(Cargo cargo) throws Exception {
        cargar(ContratoPaises.class, "data/catalogos/pais-sispro.xlsx");
        cargar(ContratoTiposDocumento.class, "data/catalogos/tipos-documento-hoja-13.csv");
        cargar(ContratoOcupaciones.class, "data/catalogos/ciuo-88-ac.csv");
        cargar(ContratoMunicipios.class, "data/catalogos/divipola-municipios.csv");
        cargar(ContratoCategoriasDiscapacidad.class, "data/catalogos/discapacidad-hojas-14-15.csv");
        cargar(ContratoEtnias.class, "data/catalogos/etnias-hoja-15.csv");
        cargar(ContratoEntidadesPlanBeneficios.class, "data/catalogos/eapb-sispro.xlsx");
        recorrer(cargo);
    }

    private void cargar(Class<? extends CrudCatalogo<?>> contrato, String archivo) {
        CrudCatalogo<?> crud = contexto.getBean(contrato);
        crud.confirmarCarga(crud.leerCarga(Path.of(archivo), new DatosVersion("Fuente de prueba",
                null, LocalDate.of(2026, 11, 2), "Versión de prueba", LocalDate.of(2021, 6, 25))),
                Map.of());
    }

    private void recorrer(Cargo cargo) throws Exception {
        String usuario = Usuarios.crear(usuarios, cargo).getUsuario();
        List<String> archivos = archivosDePrueba();
        for (long semilla = 1; semilla <= 3; semilla++) {
            TecladoAlAzar teclado = new TecladoAlAzar(new Random(semilla * 31 + cargo.ordinal()),
                    usuario, archivos);
            MenuPrincipal menu = new MenuPrincipal(conTeclado(teclado), teclado, base, sesion,
                    new SesionControlador(sesion, teclado));
            try {
                menu.run();
            } catch (RuntimeException e) {
                fail("con " + usuario + " y la semilla " + semilla + " el programa se cayó: " + e
                        + "\nÚltimas preguntas y respuestas:\n"
                        + String.join("\n", teclado.ultimas), e);
            } finally {
                sesion.cerrar();
            }
            String salida = teclado.salida();
            assertFalse(salida.contains("Exception"), "la terminal mostró una excepción:\n"
                    + salida.lines().filter(l -> l.contains("Exception")).findFirst().orElse(""));
            assertFalse(salida.contains("error que el programa no esperaba"),
                    "un error llegó al respaldo:\n" + salida.lines()
                            .filter(l -> l.contains("no esperaba")).findFirst().orElse(""));
            assertTrue(teclado.respuestas >= RESPUESTAS, "el menú salió antes de tiempo");
        }
    }

    @Test
    void unErrorInesperadoEnUnaHistoriaVuelveAlMenuPrincipal() throws Exception {
        Usuarios.crear(usuarios, Cargo.PROFESIONAL_DE_ESTADISTICA);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Terminal teclado = new Terminal(new ByteArrayInputStream(
                "1\nprofesional_de_estadistica\nClave123\n1\n0\n".getBytes(StandardCharsets.UTF_8)),
                new PrintStream(bytes, true, StandardCharsets.UTF_8));
        Controlador roto = new Controlador() {
            public String historia() {
                return "HU-000";
            }

            public String titulo() {
                return "Historia que falla";
            }

            public Permiso permisoParaVer() {
                return Permiso.CONSULTAR_CATALOGOS;
            }

            public void abrir() {
                throw new NullPointerException("un dato que no llegó");
            }
        };
        new MenuPrincipal(List.of(roto), teclado, base, sesion,
                new SesionControlador(sesion, teclado)).run();
        String salida = bytes.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("No se pudo, por un error que el programa no esperaba"
                + " (NullPointerException: un dato que no llegó). Lo demás sigue igual."), salida);
        assertTrue(salida.indexOf("C. Cerrar sesión") < salida.lastIndexOf("C. Cerrar sesión"),
                "no volvió al menú principal");
    }

    @Test
    void unErrorInesperadoEnUnaOpcionVuelveAlMenuDelCatalogo() {
        Usuarios.entrar(usuarios, sesion, Cargo.PROFESIONAL_DE_ESTADISTICA);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Terminal teclado = new Terminal(new ByteArrayInputStream(
                "12\n0\n".getBytes(StandardCharsets.UTF_8)),
                new PrintStream(bytes, true, StandardCharsets.UTF_8));
        try {
            new EtniaControlador(etnias, teclado, sesion) {
                @Override
                protected Map<String, OpcionPropia> opcionesPropias() {
                    return Map.of("12", new OpcionPropia("Opción que falla", () -> {
                        throw new UnsupportedOperationException("sin hacer");
                    }));
                }
            }.abrir();
        } finally {
            sesion.cerrar();
        }
        String salida = bytes.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("(UnsupportedOperationException: sin hacer)"), salida);
        assertTrue(salida.indexOf("== Etnias") < salida.lastIndexOf("== Etnias"),
                "no volvió al menú del catálogo");
    }

    /** Los controladores del programa, cada uno con el teclado del test en vez del de Spring. */
    private List<Controlador> conTeclado(Terminal teclado) throws Exception {
        List<Controlador> lista = new ArrayList<>();
        for (Controlador bean : controladores) {
            Constructor<?> constructor = bean.getClass().getConstructors()[0];
            Class<?>[] tipos = constructor.getParameterTypes();
            Object[] argumentos = new Object[tipos.length];
            for (int i = 0; i < tipos.length; i++) {
                argumentos[i] = tipos[i] == Terminal.class ? teclado : contexto.getBean(tipos[i]);
            }
            lista.add((Controlador) constructor.newInstance(argumentos));
        }
        return lista;
    }

    /** Rutas que una persona podría escribir: buenas, vacías, de otro tipo o que no existen. */
    private List<String> archivosDePrueba() throws IOException {
        Path csv = Files.writeString(carpeta.resolve("tres.csv"),
                "código;nombre\n001;Uno\n02;Dos\nABCDEF;Tres\n", StandardCharsets.UTF_8);
        Path vacio = Files.writeString(carpeta.resolve("vacio.csv"), "", StandardCharsets.UTF_8);
        Path soloEncabezado = Files.writeString(carpeta.resolve("encabezado.csv"),
                "código;nombre\n", StandardCharsets.UTF_8);
        Path comas = Files.writeString(carpeta.resolve("comas.csv"), "codigo,nombre\n01,Uno\n",
                StandardCharsets.UTF_8);
        Path falsoExcel = Files.writeString(carpeta.resolve("falso.xlsx"), "no soy un Excel",
                StandardCharsets.UTF_8);
        Path binario = Files.write(carpeta.resolve("binario.csv"), new byte[] {(byte) 0xFF, 0, 1});
        return List.of(csv.toString(), vacio.toString(), soloEncabezado.toString(),
                comas.toString(), falsoExcel.toString(), binario.toString(),
                carpeta.toString(), "/no/existe.csv", "data/catalogos/etnias-hoja-15.csv",
                "data/catalogos/pais-sispro.xlsx", "pom.xml", "C:\\Users\\nadie\\x.csv",
                "\u0000raro.csv");
    }

    /**
     * Un teclado que contesta al azar, con una semilla para que la falla se repita. Inicia sesión
     * casi siempre, nunca sale antes de tiempo, y pasadas las respuestas contesta 0 hasta salir.
     */
    static final class TecladoAlAzar extends Terminal {

        private static final List<String> OPCIONES = List.of("1", "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "11", "12", "13", "14", "C", "c", "x", "-1", "99", "1.5", " 1",
                "ñ", "١");
        private static final List<String> TEXTOS = List.of("", " ", "0", "1", "2", "3", "01",
                "001", "99", "170", "ZZ", "CC", "EPS037", "05001", "13-5", "2221", "x", "abc;def",
                "ñandú", "Ñ".repeat(300), "-1", "999999999999999999999", "1e9", "%", "_",
                "'; DROP TABLE PAIS; --", "2026-13-45", "ayer", "2099-01-01", "2026-11-02",
                "2021-06-25", "26-11-02", "s", "n", "S", "si", "NULL", "\t", "Nombre de prueba",
                "12345678", "1090123456", "1990-05-17", "08:30", "25:99", "01", "02", "03", "05",
                "06", "08", "05001", "11001", "170", "862", "EPS037", "2221", "Pérez", "María José",
                "Ana", "medellin", "bogota");

        private final Random azar;
        private final String usuario;
        private final List<String> archivos;
        private final ByteArrayOutputStream bytes;
        private final Deque<String> ultimas = new ArrayDeque<>();
        private String ultimaLinea = "";
        // El último título de un dato, como "Etnia:", que el formulario de HU-004 escribe antes
        // de pedir su código.
        private String titulo = "";
        // La pantalla en que está, como "== Consulta de un paciente", y los documentos que
        // registró, para que la consulta y la corrección (HU-005 y HU-006) den con uno.
        private String pantalla = "";
        private final List<String> registrados = new ArrayList<>();
        private int respuestas;

        TecladoAlAzar(Random azar, String usuario, List<String> archivos) {
            this(azar, usuario, archivos, new ByteArrayOutputStream());
        }

        private TecladoAlAzar(Random azar, String usuario, List<String> archivos,
                ByteArrayOutputStream bytes) {
            super(new ByteArrayInputStream(new byte[0]),
                    new PrintStream(bytes, true, StandardCharsets.UTF_8));
            this.azar = azar;
            this.usuario = usuario;
            this.archivos = archivos;
            this.bytes = bytes;
        }

        String salida() {
            return bytes.toString(StandardCharsets.UTF_8);
        }

        @Override
        public void escribir(String linea) {
            super.escribir(linea);
            ultimaLinea = linea;
            if (linea.endsWith(":") && !linea.startsWith(" ")) {
                titulo = linea;
            }
            if (linea.startsWith("== ")) {
                pantalla = linea;
            }
            if (linea.startsWith("Registrado el paciente CC ")) {
                registrados.add(linea.substring("Registrado el paciente CC ".length(),
                        linea.length() - 1));
            }
        }

        @Override
        public String leer(String pregunta) {
            if (++respuestas > RESPUESTAS + 2000) {
                throw new NoSuchElementException("el menú no sale: " + pregunta);
            }
            String respuesta = responder(pregunta);
            if (ultimas.size() == 40) {
                ultimas.removeFirst();
            }
            ultimas.addLast(pregunta + respuesta);
            return respuesta;
        }

        @Override
        public String leerClave(String pregunta) {
            return leer(pregunta);
        }

        private String responder(String pregunta) {
            boolean menu = pregunta.equals("Opción: ");
            boolean sale = ultimaLinea.equals("0. Salir");
            if (respuestas > RESPUESTAS) {
                return menu ? "0" : (pregunta.contains("(s/n)") ? "n" : "");
            }
            if (pregunta.equals("Usuario: ")) {
                return azar.nextInt(5) == 0 ? uno(TEXTOS) : usuario;
            }
            if (pregunta.equals("Contraseña: ")) {
                return azar.nextInt(5) == 0 ? uno(TEXTOS) : Usuarios.CLAVE;
            }
            if (menu) {
                // En el menú principal y en la entrada, 0 y Enter salen del programa.
                String opcion = uno(OPCIONES);
                return sale || azar.nextInt(8) != 0 ? opcion : "0";
            }
            String bueno = valorBueno(pregunta);
            if (bueno != null && azar.nextInt(10) < 7) {
                return bueno;
            }
            if (pregunta.contains("(s/n)")) {
                return azar.nextBoolean() ? "s" : uno(TEXTOS);
            }
            if (pregunta.startsWith("Ruta")) {
                return uno(archivos);
            }
            return uno(TEXTOS);
        }

        /**
         * Lo que contestaría quien llena bien el registro de un paciente, para que el recorrido
         * llegue a guardar uno y la consulta y la corrección lo encuentren. Null si no sabe.
         */
        private String valorBueno(String pregunta) {
            boolean buscaUno = !pantalla.equals("== Registro de un paciente");
            if (pregunta.startsWith("Número de documento")) {
                return buscaUno && !registrados.isEmpty() ? uno(registrados)
                        : String.valueOf(10_000_000 + azar.nextInt(90_000_000));
            }
            if (buscaUno && (pregunta.startsWith("Número o código") || pregunta.startsWith("Tipo"))
                    && !registrados.isEmpty()) {
                return "CC";
            }
            if (pregunta.startsWith("Primer apellido") || pregunta.startsWith("Primer nombre")) {
                return "Pérez";
            }
            if (pregunta.startsWith("Segundo")) {
                return "";
            }
            if (pregunta.startsWith("Fecha de nacimiento")) {
                return "1990-05-17";
            }
            if (pregunta.startsWith("Hora de nacimiento")) {
                return "08:30";
            }
            if (pregunta.startsWith("¿Guardar el paciente?")) {
                return "s";
            }
            if (!pregunta.startsWith("Código") && !pregunta.startsWith("Número o código")) {
                return null;
            }
            return switch (titulo) {
                case "País de nacionalidad:", "País de residencia habitual:" -> "170";
                case "Tipo de documento:" -> "CC";
                case "Sexo biológico:", "Identidad de género:", "Zona territorial de residencia:"
                        -> "01";
                case "Ocupación (Enter si no tiene):" -> "2221";
                case "Categoría de discapacidad:" -> "08";
                case "Ciudad (municipio) de residencia:" -> "05001";
                case "Etnia:" -> "99";
                case "Entidad que responde por el plan de beneficios:" -> "EPS037";
                default -> null;
            };
        }

        private String uno(List<String> lista) {
            return lista.get(azar.nextInt(lista.size()));
        }
    }
}
