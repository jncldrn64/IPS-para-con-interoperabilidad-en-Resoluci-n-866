package com.example.demo.menu;

import com.example.demo.modelo.Catalogo;
import com.example.demo.modelo.ComunidadEtnica;
import com.example.demo.modelo.Etnia;
import com.example.demo.servicio.CrudCatalogo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Scanner;
import java.util.function.BiFunction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * El controlador de la fase 0: lee la opción, llama al servicio e imprime (docs/DESIGN.md,
 * sección 6). Es provisional: cómo se presenta cada pantalla está por decidir.
 */
@Component
// Sin esto, cada @SpringBootTest arranca el menú y el test se queda esperando el teclado.
@Profile("!test")
public class MenuPrincipal implements CommandLineRunner {

    private final CrudCatalogo<Etnia> etnias;
    private final CrudCatalogo<ComunidadEtnica> comunidades;
    private final Scanner teclado = new Scanner(System.in);

    public MenuPrincipal(CrudCatalogo<Etnia> etnias, CrudCatalogo<ComunidadEtnica> comunidades) {
        this.etnias = etnias;
        this.comunidades = comunidades;
    }

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println();
            System.out.println("1. Etnias");
            System.out.println("2. Comunidades étnicas");
            System.out.println("0. Salir");
            switch (leer("Opción: ")) {
                case "1" -> catalogo("Etnias", etnias, Etnia::new);
                case "2" -> catalogo("Comunidades étnicas", comunidades, ComunidadEtnica::new);
                case "0", "" -> {
                    return;
                }
                default -> System.out.println("Opción no válida");
            }
        }
    }

    private <T extends Catalogo> void catalogo(String titulo, CrudCatalogo<T> crud,
            BiFunction<String, String, T> nuevo) {
        while (true) {
            System.out.println();
            System.out.println("== " + titulo);
            System.out.println("1. Listar");
            System.out.println("2. Agregar o cambiar el nombre");
            System.out.println("3. Inactivar");
            System.out.println("0. Volver");
            try {
                switch (leer("Opción: ")) {
                    case "1" -> crud.listar().forEach(v -> System.out.println(
                            v.getCodigo() + "  " + v.getNombre() + "  " + v.getEstado()));
                    case "2" -> {
                        T valor = crud.guardar(nuevo.apply(leer("Código: "), leer("Nombre: ")));
                        System.out.println("Guardado: " + valor.getCodigo());
                    }
                    case "3" -> {
                        String codigo = leer("Código: ");
                        crud.inactivar(codigo);
                        System.out.println("Inactivado: " + codigo);
                    }
                    case "0", "" -> {
                        return;
                    }
                    default -> System.out.println("Opción no válida");
                }
            } catch (ConstraintViolationException e) {
                e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                        .forEach(m -> System.out.println("No se guardó: " + m));
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("No se pudo: " + e.getMessage());
            }
        }
    }

    // Una línea vacía cuando se acaba la entrada, para que el menú salga en vez de fallar.
    private String leer(String pregunta) {
        System.out.print(pregunta);
        return teclado.hasNextLine() ? teclado.nextLine().trim() : "";
    }
}
