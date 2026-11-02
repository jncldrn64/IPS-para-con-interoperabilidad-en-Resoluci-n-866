package com.ipssaludyvida.hu001.controladores;

import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Usuario;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-001: la pantalla de inicio de sesión que abre el menú principal cuando no
 * hay sesión. No es una opción del menú, así que no cumple la interfaz Controlador.
 */
@Component
public class SesionControlador {

    /** Lo que dice el menú cuando la sesión se cerró por inactividad (escenario 12). */
    public static final String VENCIDA =
            "La sesión se cerró por 15 minutos sin actividad. Inicie sesión de nuevo.";

    private final ContratoSesion sesion;
    private final Terminal terminal;

    public SesionControlador(ContratoSesion sesion, Terminal terminal) {
        this.sesion = sesion;
        this.terminal = terminal;
    }

    /**
     * Pide el usuario y la clave hasta que alguien entra. Devuelve false si eligió salir. Sin
     * opción para recuperar la clave (escenario 13, [2.4]).
     */
    public boolean entrar() {
        while (true) {
            terminal.escribir("");
            terminal.escribir("== Inicio de sesión");
            terminal.escribir("1. Iniciar sesión");
            terminal.escribir("0. Salir");
            switch (terminal.leer("Opción: ")) {
                case "1" -> {
                    String usuario = terminal.leer("Usuario: ");
                    String clave = terminal.leerClave("Contraseña: ");
                    try {
                        Usuario u = sesion.iniciar(usuario, clave);
                        terminal.escribir("Bienvenido, " + u.getNombre() + ". Cargo: "
                                + u.getCargo().texto() + ".");
                        return true;
                    } catch (IllegalArgumentException e) {
                        terminal.escribir("No se pudo entrar: " + e.getMessage());
                    }
                }
                case "0", "" -> {
                    return false;
                }
                default -> terminal.escribir("Opción no válida");
            }
        }
    }
}
