package com.example.demo.comun.menu;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * La entrada y la salida que comparten el menú principal y los controladores. Spring la arma con
 * el teclado y la pantalla, y un test, con las respuestas escritas y una salida que después lee.
 */
@Component
public class Terminal {

    // Un menú sale con la primera línea vacía, y una opción a medias lee unas pocas más antes de
    // fallar. Pasado este número, el menú no está saliendo.
    private static final int LECTURAS_SIN_ENTRADA = 100;

    private final Scanner teclado;
    private final PrintStream salida;
    private int sinEntrada;

    @Autowired
    public Terminal() {
        this(System.in, System.out);
    }

    public Terminal(InputStream entrada, PrintStream salida) {
        this.teclado = new Scanner(entrada);
        this.salida = salida;
    }

    // Una línea vacía cuando se acaba la entrada, para que el menú salga en vez de fallar.
    public String leer(String pregunta) {
        salida.print(pregunta);
        if (teclado.hasNextLine()) {
            return teclado.nextLine().trim();
        }
        // Un menú que no sale con la línea vacía pregunta para siempre, y el @Timeout de un test no
        // corta un bucle: el 2026-10-27, un test así llenó la memoria. Los menús atrapan
        // IllegalStateException y siguen, así que el corte es otra excepción (docs/DESIGN.md,
        // sección 3).
        if (++sinEntrada > LECTURAS_SIN_ENTRADA) {
            throw new NoSuchElementException("se acabó la entrada y el menú sigue preguntando");
        }
        return "";
    }

    public void escribir(String linea) {
        salida.println(linea);
    }

    // Cancelar antes de confirmar deja el catálogo igual (escenario 12 de HU-036).
    public boolean confirmar() {
        if (leer("¿Confirma? (s/n): ").equalsIgnoreCase("s")) {
            return true;
        }
        escribir("Cancelado: el catálogo queda igual.");
        return false;
    }
}
