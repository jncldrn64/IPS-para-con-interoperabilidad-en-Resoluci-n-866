package com.example.demo.comun.menu;

import com.example.demo.comun.interfaces.CrudCatalogo;
import com.example.demo.comun.modelos.Catalogo;
import com.example.demo.comun.modelos.CatalogoCambio;
import com.example.demo.comun.modelos.CatalogoVersion;
import com.example.demo.comun.modelos.Motivo;
import com.example.demo.comun.servicios.Comprobacion;
import com.example.demo.comun.servicios.Consulta;
import com.example.demo.comun.servicios.DatosVersion;
import com.example.demo.comun.servicios.ResultadoCarga;
import com.example.demo.comun.servicios.ResumenVersion;
import com.example.demo.hu036.interfaces.ContratoEtnias;
import com.example.demo.hu036.modelos.Etnia;
import com.example.demo.hu037.interfaces.ContratoComunidadesEtnicas;
import com.example.demo.hu037.modelos.ComunidadEtnica;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final ContratoEtnias etnias;
    private final ContratoComunidadesEtnicas comunidades;
    private final Scanner teclado;

    @Autowired
    public MenuPrincipal(ContratoEtnias etnias, ContratoComunidadesEtnicas comunidades) {
        this(etnias, comunidades, System.in);
    }

    /** Con otra entrada que el teclado, para que un test escriba las respuestas. */
    public MenuPrincipal(ContratoEtnias etnias, ContratoComunidadesEtnicas comunidades,
            InputStream entrada) {
        this.etnias = etnias;
        this.comunidades = comunidades;
        this.teclado = new Scanner(entrada);
    }

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println();
            System.out.println("1. Etnias");
            System.out.println("2. Comunidades étnicas");
            System.out.println("0. Salir");
            switch (leer("Opción: ")) {
                case "1" -> catalogo("Etnias", etnias, Etnia::new,
                        () -> imprimir(etnias.comprobarContraLaHoja()));
                case "2" -> catalogo("Comunidades étnicas", comunidades, ComunidadEtnica::new,
                        () -> imprimir(comunidades.comprobarContraElArchivo(
                                Path.of(leer("Ruta del archivo oficial: ")))));
                case "0", "" -> {
                    return;
                }
                default -> System.out.println("Opción no válida");
            }
        }
    }

    private <T extends Catalogo> void catalogo(String titulo, CrudCatalogo<T> crud,
            BiFunction<String, String, T> nuevo, Runnable comprobar) {
        while (true) {
            System.out.println();
            System.out.println("== " + titulo);
            System.out.println("1. Consultar");
            System.out.println("2. Crear por cambio oficial");
            System.out.println("3. Cambiar el nombre");
            System.out.println("4. Cambiar el código (Reemplazado por)");
            System.out.println("5. Inactivar");
            System.out.println("6. Reactivar");
            System.out.println("7. Historial de un código");
            System.out.println("8. Cargar desde un CSV");
            System.out.println("9. Buscar por código o por nombre");
            System.out.println("10. Historial de versiones");
            System.out.println("11. Comparar con la lista oficial");
            System.out.println("0. Volver");
            try {
                switch (leer("Opción: ")) {
                    case "1" -> consultar(crud);
                    case "2" -> crear(crud, nuevo);
                    case "3" -> cambiarNombre(crud);
                    case "4" -> cambiarCodigo(crud, nuevo);
                    case "5" -> inactivar(crud);
                    case "6" -> reactivar(crud);
                    case "7" -> crud.historial(leer("Código: ")).forEach(this::imprimir);
                    case "8" -> cargar(crud);
                    case "9" -> buscar(crud);
                    case "10" -> crud.historialDeVersiones().forEach(this::imprimir);
                    case "11" -> comprobar.run();
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
            } catch (DateTimeParseException e) {
                System.out.println("No se pudo: la fecha \"" + e.getParsedString()
                        + "\" no sirve; escríbala como AAAA-MM-DD");
            }
        }
    }

    // La terminal dice qué archivo espera antes de pedir la ruta (docs/DECISIONS.md, 2026-10-26
    // "La terminal dice qué archivo espera antes de pedir la ruta").
    private <T extends Catalogo> void cargar(CrudCatalogo<T> crud) {
        System.out.println("El archivo tiene que ser CSV en UTF-8, separado por punto y coma (;).");
        System.out.println("La primera fila trae los nombres de las columnas: código;nombre. Las"
                + " demás columnas se ignoran.");
        System.out.println("Desde la hoja de cálculo: Guardar como, \"CSV UTF-8\", con la columna"
                + " del código en formato de texto para que no pierda los ceros.");
        Path archivo = Path.of(leer("Ruta del archivo: "));
        String fuente = leer("Fuente: ");
        String direccion = leer("Dirección de descarga: ");
        LocalDate descarga = LocalDate.parse(leer("Fecha de descarga (AAAA-MM-DD): "));
        String version = leer("Versión: ");
        String publicacion = leer("Fecha de publicación (AAAA-MM-DD), o Enter si no la trae: ");
        ResultadoCarga carga = crud.leerCarga(archivo, new DatosVersion(fuente, direccion, descarga,
                version, publicacion.isEmpty() ? null : LocalDate.parse(publicacion)));
        imprimir(carga);
        if (!carga.sePuedeGuardar()) {
            carga.getBloqueos().forEach(b -> System.out.println("No se puede guardar: " + b));
            return;
        }
        if (!carga.getRechazadas().isEmpty()
                && leer("¿Descargar la lista de rechazadas? (s/n): ").equalsIgnoreCase("s")) {
            String nombre = archivo.getFileName().toString().replaceFirst("\\.csv$", "");
            Path destino = crud.escribirRechazadas(carga,
                    archivo.resolveSibling(nombre + "-rechazadas.csv"));
            System.out.println("Escrita en " + destino);
        }
        Map<String, String> reemplazos = new HashMap<>();
        for (String codigo : carga.getYaNoEstan()) {
            String nuevo = leer(
                    "¿Qué código nuevo reemplaza a " + codigo + "? (Enter si ninguno): ");
            if (!nuevo.isEmpty()) {
                reemplazos.put(codigo, nuevo);
            }
        }
        if (!confirmar()) {
            return;
        }
        // RC-9: la lista de los que ya no están se confirma aparte.
        if (!carga.getYaNoEstan().isEmpty()) {
            System.out.println("Se inactivan: " + String.join(", ", carga.getYaNoEstan()));
            if (!confirmar()) {
                return;
            }
        }
        CatalogoVersion guardada = crud.confirmarCarga(carga, reemplazos);
        System.out.println("Guardada la versión " + guardada.getVersion() + ", publicada el "
                + guardada.getFechaPublicacion() + ".");
    }

    private void imprimir(ResultadoCarga carga) {
        System.out.println("Carga: " + switch (carga.getTipo()) {
            case PRIMERA -> "primera carga";
            case VERSION_NUEVA -> "versión nueva";
            case RECARGA -> "recarga de la versión vigente";
        });
        System.out.println("Filas leídas: " + carga.getFilasLeidas() + ". Aceptadas: "
                + carga.getAceptadas().size() + ". Rechazadas: " + carga.getRechazadas().size()
                + ".");
        carga.getRechazadas().forEach(r -> System.out.println(
                "  Fila " + r.numero() + ", código \"" + r.codigo() + "\": " + r.motivo()));
        if (carga.getTipo() == ResultadoCarga.Tipo.VERSION_NUEVA) {
            System.out.println("Nuevos: " + carga.getNuevos());
            carga.getCambianDeNombre().forEach(c -> System.out.println("Cambia de nombre: "
                    + c.codigo() + ", de \"" + c.antes() + "\" a \"" + c.ahora() + "\""));
            System.out.println("Vuelven: " + carga.getVuelven());
            System.out.println("Ya no están: " + carga.getYaNoEstan());
        }
        carga.getAvisos().forEach(a -> System.out.println("Aviso: " + a));
    }

    private <T extends Catalogo> void consultar(CrudCatalogo<T> crud) {
        CrudCatalogo.Filtro filtro = switch (leer("Mostrar 1. activas, 2. inactivas, 3. todas: ")) {
            case "1" -> CrudCatalogo.Filtro.ACTIVOS;
            case "2" -> CrudCatalogo.Filtro.INACTIVOS;
            default -> CrudCatalogo.Filtro.TODOS;
        };
        Consulta<T> consulta = crud.consultar(filtro);
        if (consulta.aviso() != null) {
            System.out.println(consulta.aviso());
            return;
        }
        consulta.valores().forEach(this::imprimir);
        System.out.println("Activos: " + consulta.activos() + ".");
        consulta.vigente().ifPresent(v -> System.out.println("Versión vigente: " + v.getVersion()
                + ", publicada el " + v.getFechaPublicacion() + " y descargada el "
                + v.getFechaDescarga() + "."));
    }

    private <T extends Catalogo> void buscar(CrudCatalogo<T> crud) {
        List<T> encontrados = crud.buscar(leer("Código o nombre: "));
        if (encontrados.isEmpty()) {
            System.out.println(crud.sinCoincidencias());
        }
        encontrados.forEach(this::imprimir);
    }

    private void imprimir(Catalogo valor) {
        System.out.println(valor.getCodigo() + "  " + valor.getNombre() + "  " + valor.getEstado());
    }

    private void imprimir(ResumenVersion resumen) {
        CatalogoVersion v = resumen.version();
        System.out.println(v.getVersion() + ", publicada el " + v.getFechaPublicacion()
                + ", descargada el " + v.getFechaDescarga() + ", de " + v.getFuente() + ": "
                + resumen.creados() + " creados, " + resumen.cambiados() + " cambiados, "
                + resumen.inactivados() + " inactivados.");
    }

    private void imprimir(Comprobacion comprobacion) {
        if (comprobacion.coincide()) {
            System.out.println("El catálogo coincide con la lista oficial.");
            return;
        }
        System.out.println("Faltan: " + comprobacion.faltan());
        System.out.println("Inactivos en el catálogo: " + comprobacion.inactivos());
        System.out.println("Sobran: " + comprobacion.sobran());
        System.out.println("Nombres distintos: " + comprobacion.nombresDistintos());
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
