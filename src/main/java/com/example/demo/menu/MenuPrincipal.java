package com.example.demo.menu;

import com.example.demo.modelo.Catalogo;
import com.example.demo.modelo.CatalogoCambio;
import com.example.demo.modelo.ComunidadEtnica;
import com.example.demo.modelo.Etnia;
import com.example.demo.modelo.Motivo;
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
            System.out.println("2. Crear por cambio oficial");
            System.out.println("3. Cambiar el nombre");
            System.out.println("4. Cambiar el código (Reemplazado por)");
            System.out.println("5. Inactivar");
            System.out.println("6. Reactivar");
            System.out.println("7. Historial de un código");
            System.out.println("0. Volver");
            try {
                switch (leer("Opción: ")) {
                    case "1" -> crud.listar().forEach(v -> System.out.println(
                            v.getCodigo() + "  " + v.getNombre() + "  " + v.getEstado()));
                    case "2" -> crear(crud, nuevo);
                    case "3" -> cambiarNombre(crud);
                    case "4" -> cambiarCodigo(crud, nuevo);
                    case "5" -> inactivar(crud);
                    case "6" -> reactivar(crud);
                    case "7" -> crud.historial(leer("Código: ")).forEach(this::imprimir);
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

    private <T extends Catalogo> void crear(CrudCatalogo<T> crud,
            BiFunction<String, String, T> nuevo) {
        String codigo = leer("Código: ");
        String nombre = leer("Nombre: ");
        String referencia = leer("Referencia oficial: ");
        if (confirmar()) {
            crud.crear(nuevo.apply(codigo, nombre), referencia);
            System.out.println("Creado: " + codigo);
        }
    }

    private <T extends Catalogo> void cambiarNombre(CrudCatalogo<T> crud) {
        String codigo = leer("Código: ");
        String nombre = leer("Nombre nuevo: ");
        String referencia = leer("Referencia oficial: ");
        if (confirmar()) {
            crud.cambiarNombre(codigo, nombre, referencia);
            System.out.println("Nombre cambiado: " + codigo);
        }
    }

    private <T extends Catalogo> void cambiarCodigo(CrudCatalogo<T> crud,
            BiFunction<String, String, T> nuevo) {
        String viejo = leer("Código viejo: ");
        String codigo = leer("Código nuevo: ");
        String nombre = leer("Nombre del código nuevo, si no existe: ");
        String referencia = leer("Referencia oficial: ");
        if (confirmar()) {
            crud.cambiarCodigo(viejo, nuevo.apply(codigo, nombre), referencia);
            System.out.println("Reemplazado: " + viejo + " por " + codigo);
        }
    }

    private <T extends Catalogo> void inactivar(CrudCatalogo<T> crud) {
        String codigo = leer("Código: ");
        T valor = crud.buscarPorCodigo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("no existe el código " + codigo));
        System.out.println("Inactivar " + codigo + " " + valor.getNombre() + ". La usan "
                + crud.contarUsos(codigo) + " registros: la conservan, pero no se ofrece en"
                + " registros nuevos.");
        System.out.println("1. " + Motivo.RETIRADO_POR_LA_FUENTE.texto());
        System.out.println("2. " + Motivo.CREADO_POR_ERROR.texto());
        Motivo motivo = switch (leer("Motivo: ")) {
            case "1" -> Motivo.RETIRADO_POR_LA_FUENTE;
            case "2" -> Motivo.CREADO_POR_ERROR;
            default -> null;
        };
        String referencia = leer("Referencia oficial: ");
        if (confirmar()) {
            crud.inactivar(codigo, motivo, referencia);
            System.out.println("Inactivado: " + codigo);
        }
    }

    private <T extends Catalogo> void reactivar(CrudCatalogo<T> crud) {
        String codigo = leer("Código: ");
        String referencia = leer("Referencia oficial, o Enter si no hay otra: ");
        String justificacion = leer("Por qué se reactiva: ");
        if (confirmar()) {
            crud.reactivar(codigo, referencia, justificacion);
            System.out.println("Reactivado: " + codigo);
        }
    }

    private void imprimir(CatalogoCambio c) {
        StringBuilder linea = new StringBuilder(c.getFechaHora().withNano(0) + "  " + c.getTipo());
        if (c.getValorAnterior() != null) {
            linea.append("  de \"").append(c.getValorAnterior()).append('"');
        }
        if (c.getValorNuevo() != null) {
            linea.append("  a \"").append(c.getValorNuevo()).append('"');
        }
        if (c.getMotivo() != null) {
            linea.append("  ").append(c.getMotivo().texto());
        }
        if (c.getCodigoReemplazo() != null) {
            linea.append(" ").append(c.getCodigoReemplazo());
        }
        if (c.getReferenciaOficial() != null) {
            linea.append("  referencia: ").append(c.getReferenciaOficial());
        }
        if (c.getJustificacion() != null) {
            linea.append("  porque: ").append(c.getJustificacion());
        }
        System.out.println(linea);
    }

    // Cancelar antes de confirmar deja el catálogo igual (escenario 12 de HU-036).
    private boolean confirmar() {
        if (leer("¿Confirma? (s/n): ").equalsIgnoreCase("s")) {
            return true;
        }
        System.out.println("Cancelado: el catálogo queda igual.");
        return false;
    }

    // Una línea vacía cuando se acaba la entrada, para que el menú salga en vez de fallar.
    private String leer(String pregunta) {
        System.out.print(pregunta);
        return teclado.hasNextLine() ? teclado.nextLine().trim() : "";
    }
}
