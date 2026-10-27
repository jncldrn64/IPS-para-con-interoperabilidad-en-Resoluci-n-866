package com.example.demo.comun.menu;

import com.example.demo.comun.interfaces.Controlador;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * El menú común: lista las historias y abre el controlador de la elegida (docs/DESIGN.md,
 * sección 6). No sabe qué hace cada una, así que una historia nueva entra con su controlador sin
 * cambiar este archivo.
 */
@Component
// Sin esto, cada @SpringBootTest arranca el menú y el test se queda esperando el teclado.
@Profile("!test")
public class MenuPrincipal implements CommandLineRunner {

    private final List<Controlador> controladores;
    private final Terminal terminal;

    public MenuPrincipal(List<Controlador> controladores, Terminal terminal) {
        this.controladores = controladores.stream()
                .sorted(Comparator.comparing(Controlador::historia)).toList();
        this.terminal = terminal;
    }

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println();
            for (int i = 0; i < controladores.size(); i++) {
                Controlador c = controladores.get(i);
                System.out.println((i + 1) + ". " + c.titulo() + " (" + c.historia() + ")");
            }
            System.out.println("0. Salir");
            String opcion = terminal.leer("Opción: ");
            if (opcion.equals("0") || opcion.isEmpty()) {
                return;
            }
            elegido(opcion).ifPresentOrElse(Controlador::abrir,
                    () -> System.out.println("Opción no válida"));
        }
    }

    private Optional<Controlador> elegido(String opcion) {
        try {
            int numero = Integer.parseInt(opcion);
            return numero >= 1 && numero <= controladores.size()
                    ? Optional.of(controladores.get(numero - 1))
                    : Optional.empty();
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
