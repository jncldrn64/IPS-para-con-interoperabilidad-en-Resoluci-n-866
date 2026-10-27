package com.example.demo.comun.menu;

import java.io.InputStream;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * La entrada que comparten el menú principal y los controladores. Spring la arma con el teclado,
 * y un test, con las respuestas escritas.
 */
@Component
public class Terminal {

    private final Scanner teclado;

    @Autowired
    public Terminal() {
        this(System.in);
    }

    public Terminal(InputStream entrada) {
        this.teclado = new Scanner(entrada);
    }

    // Una línea vacía cuando se acaba la entrada, para que el menú salga en vez de fallar.
    public String leer(String pregunta) {
        System.out.print(pregunta);
        return teclado.hasNextLine() ? teclado.nextLine().trim() : "";
    }

    // Cancelar antes de confirmar deja el catálogo igual (escenario 12 de HU-036).
    public boolean confirmar() {
        if (leer("¿Confirma? (s/n): ").equalsIgnoreCase("s")) {
            return true;
        }
        System.out.println("Cancelado: el catálogo queda igual.");
        return false;
    }
}
