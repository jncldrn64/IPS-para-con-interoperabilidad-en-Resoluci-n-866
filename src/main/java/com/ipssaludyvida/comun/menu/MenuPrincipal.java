package com.ipssaludyvida.comun.menu;

import com.ipssaludyvida.comun.interfaces.Controlador;
import com.ipssaludyvida.hu001.controladores.SesionControlador;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * El menú común: dice qué base abrió, pide iniciar sesión, lista las historias que el cargo puede
 * ver y abre el controlador de la elegida (docs/DESIGN.md, sección 6). No sabe qué hace cada una,
 * así que una historia nueva entra con su controlador sin cambiar este archivo.
 */
@Component
// Sin esto, cada @SpringBootTest arranca el menú y el test se queda esperando el teclado.
@Profile("!test")
public class MenuPrincipal implements CommandLineRunner {

    private final List<Controlador> controladores;
    private final Terminal terminal;
    private final DataSource base;
    private final ContratoSesion sesion;
    private final SesionControlador entrada;

    public MenuPrincipal(List<Controlador> controladores, Terminal terminal, DataSource base,
            ContratoSesion sesion, SesionControlador entrada) {
        this.controladores = controladores.stream()
                .sorted(Comparator.comparing(Controlador::historia)).toList();
        this.terminal = terminal;
        this.base = base;
        this.sesion = sesion;
        this.entrada = entrada;
    }

    @Override
    public void run(String... args) throws SQLException {
        // Con el log de Spring en warn no sale la línea "Database JDBC URL", y quien tiene el turno
        // del archivo de la base la necesita para saber si abrió data/ips o data/prueba
        // (docs/DESIGN.md, sección 4).
        try (Connection conexion = base.getConnection()) {
            terminal.escribir("Base abierta: " + conexion.getMetaData().getURL());
        }
        while (true) {
            // HU-001, escenario 4: sin sesión no hay menú.
            if (!sesion.abierta()) {
                if (!entrada.entrar()) {
                    return;
                }
                continue;
            }
            // Solo las historias que el cargo puede ver (HU-001, escenarios 1 y 5 a 7). La lista
            // se arma de nuevo en cada vuelta porque otra persona puede haber entrado.
            List<Controlador> visibles = controladores.stream()
                    .filter(c -> sesion.puede(c.permisoParaVer())).toList();
            terminal.escribir("");
            for (int i = 0; i < visibles.size(); i++) {
                Controlador c = visibles.get(i);
                terminal.escribir((i + 1) + ". " + c.titulo() + " (" + c.historia() + ")");
            }
            terminal.escribir("C. Cerrar sesión");
            terminal.escribir("0. Salir");
            String opcion = terminal.leer("Opción: ");
            if (opcion.equals("0") || opcion.isEmpty()) {
                sesion.cerrar();
                return;
            }
            // HU-001, escenario 12: con 15 minutos sin actividad pide entrar de nuevo.
            if (!sesion.seguir()) {
                terminal.escribir(SesionControlador.VENCIDA);
                continue;
            }
            if (opcion.equalsIgnoreCase("C")) {
                sesion.cerrar();
                terminal.escribir("Sesión cerrada.");
                continue;
            }
            elegido(visibles, opcion).ifPresentOrElse(this::abrir,
                    () -> terminal.escribir("Opción no válida"));
        }
    }

    // Un error que nadie esperaba en una historia da un mensaje y vuelve a este menú, en vez de
    // cerrar el programa (docs/DESIGN.md, sección 4).
    private void abrir(Controlador controlador) {
        try {
            controlador.abrir();
        } catch (NoSuchElementException e) {
            throw e;
        } catch (RuntimeException e) {
            terminal.escribir(inesperado(e));
        }
    }

    /** El mensaje de un error que nadie esperaba: dice qué pasó y que el programa sigue. */
    public static String inesperado(RuntimeException e) {
        return "No se pudo, por un error que el programa no esperaba ("
                + e.getClass().getSimpleName()
                + (e.getMessage() == null ? "" : ": " + e.getMessage())
                + "). Lo demás sigue igual.";
    }

    private Optional<Controlador> elegido(List<Controlador> visibles, String opcion) {
        try {
            int numero = Integer.parseInt(opcion);
            return numero >= 1 && numero <= visibles.size()
                    ? Optional.of(visibles.get(numero - 1))
                    : Optional.empty();
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
