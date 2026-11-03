package com.ipssaludyvida.comun.controladores;

import com.ipssaludyvida.comun.interfaces.Controlador;
import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.menu.MenuPrincipal;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.modelos.Catalogo;
import com.ipssaludyvida.comun.modelos.CatalogoCambio;
import com.ipssaludyvida.comun.modelos.CatalogoVersion;
import com.ipssaludyvida.comun.modelos.Motivo;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.comun.servicios.Consulta;
import com.ipssaludyvida.comun.servicios.DatosVersion;
import com.ipssaludyvida.comun.servicios.ResultadoCarga;
import com.ipssaludyvida.comun.servicios.ResumenVersion;
import com.ipssaludyvida.hu001.controladores.SesionControlador;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Permiso;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Las 11 opciones que comparten los controladores de los catálogos: cada una pide sus datos,
 * llama al contrato e imprime por la Terminal (docs/DESIGN.md, sección 6). El controlador de cada
 * historia, como EtniaControlador, pone su número, su título, cómo se arma un valor y contra qué se
 * compara.
 *
 * <p>Las opciones que cambian el catálogo, 2 a 6 y 8, se muestran y se hacen solo con el permiso de
 * mantener catálogos. Las demás consultan, y las tienen los tres cargos (HU-001, escenario 14).
 */
public abstract class ControladorCatalogo<T extends Catalogo> implements Controlador {

    private final String historia;
    private final String titulo;
    private final CrudCatalogo<T> crud;
    private final BiFunction<String, String, T> nuevo;
    private final Terminal terminal;
    private final ContratoSesion sesion;

    protected ControladorCatalogo(String historia, String titulo, CrudCatalogo<T> crud,
            BiFunction<String, String, T> nuevo, Terminal terminal, ContratoSesion sesion) {
        this.historia = historia;
        this.titulo = titulo;
        this.crud = crud;
        this.nuevo = nuevo;
        this.terminal = terminal;
        this.sesion = sesion;
    }

    /** La opción 11: cada historia compara con su lista oficial. */
    protected abstract Comprobacion comprobar();

    /** Una opción propia de una historia, después de la 11, como la jerarquía de HU-022. */
    public record OpcionPropia(String texto, Runnable accion) {}

    /**
     * Las opciones propias de la historia, por su número, en el orden en que se muestran. Solo
     * consultan, así que las ven los tres cargos (HU-001, escenario 14).
     */
    protected Map<String, OpcionPropia> opcionesPropias() {
        return Map.of();
    }

    /**
     * Lo que se muestra de un valor después de su estado, en la consulta y en la búsqueda, como el
     * departamento de un municipio en HU-026.
     */
    protected String detalle(T valor) {
        return "";
    }

    /** Qué columnas pide la carga. Una historia con columnas propias, como HU-026, la cambia. */
    protected void escribirAyudaDeCarga() {
        escribir("La primera fila trae los nombres de las columnas: código;nombre. Las"
                + " demás columnas se ignoran.");
    }

    /** Lo que se dice antes de inactivar: cuántos registros usan el valor (RC-10). */
    protected String usos(String codigo) {
        return "La usan " + crud.contarUsos(codigo) + " pacientes";
    }

    @Override
    public String historia() {
        return historia;
    }

    @Override
    public String titulo() {
        return titulo;
    }

    /** Los tres cargos consultan los catálogos (matriz de HU-001). */
    @Override
    public Permiso permisoParaVer() {
        return Permiso.CONSULTAR_CATALOGOS;
    }

    @Override
    public void abrir() {
        // HU-001, escenario 4: sin sesión no se consulta ni se cambia nada.
        if (!sesion.abierta()) {
            escribir("No se pudo: " + ContratoSesion.SIN_SESION);
            return;
        }
        boolean mantiene = sesion.puede(Permiso.MANTENER_CATALOGOS);
        while (true) {
            escribir("");
            escribir("== " + titulo);
            escribir("1. Consultar");
            if (mantiene) {
                escribir("2. Crear por cambio oficial");
                escribir("3. Cambiar el nombre");
                escribir("4. Cambiar el código (Reemplazado por)");
                escribir("5. Inactivar");
                escribir("6. Reactivar");
            }
            escribir("7. Historial de un código");
            if (mantiene) {
                escribir("8. Cargar desde un archivo, CSV o Excel");
            }
            escribir("9. Buscar por código o por nombre");
            escribir("10. Historial de versiones");
            escribir("11. Comparar con la lista oficial");
            Map<String, OpcionPropia> propias = opcionesPropias();
            propias.forEach((numero, o) -> escribir(numero + ". " + o.texto()));
            escribir("0. Volver");
            try {
                String opcion = leer("Opción: ");
                // HU-001, escenario 12: con 15 minutos sin actividad vuelve al menú principal,
                // que pide entrar de nuevo.
                if (!sesion.seguir()) {
                    escribir(SesionControlador.VENCIDA);
                    return;
                }
                // Una opción que cambia el catálogo se rechaza aunque no se haya mostrado: quien
                // escribe su número sabe que existe (HU-001, escenarios 8 y 14).
                if (CAMBIAN.contains(opcion)) {
                    sesion.exigir(Permiso.MANTENER_CATALOGOS);
                }
                switch (opcion) {
                    case "1" -> consultar();
                    case "2" -> crear();
                    case "3" -> cambiarNombre();
                    case "4" -> cambiarCodigo();
                    case "5" -> inactivar();
                    case "6" -> reactivar();
                    case "7" -> crud.historial(leer("Código: ")).forEach(this::imprimir);
                    case "8" -> cargar();
                    case "9" -> buscar();
                    case "10" -> crud.historialDeVersiones().forEach(this::imprimir);
                    case "11" -> imprimir(comprobar());
                    case "0", "" -> {
                        return;
                    }
                    default -> {
                        if (propias.containsKey(opcion)) {
                            propias.get(opcion).accion().run();
                        } else {
                            escribir("Opción no válida");
                        }
                    }
                }
            } catch (ConstraintViolationException e) {
                e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                        .forEach(m -> escribir("No se guardó: " + m));
            } catch (IllegalArgumentException | IllegalStateException e) {
                escribir("No se pudo: " + e.getMessage());
            } catch (DateTimeParseException e) {
                escribir("No se pudo: la fecha \"" + e.getParsedString()
                        + "\" no sirve; escríbala como AAAA-MM-DD");
            } catch (NoSuchElementException e) {
                // El corte de Terminal cuando se acaba la entrada: tiene que salir del menú.
                throw e;
            } catch (RuntimeException e) {
                escribir(MenuPrincipal.inesperado(e));
            }
        }
    }

    // Las opciones que cambian el catálogo: piden el permiso de mantener catálogos.
    private static final Set<String> CAMBIAN = Set.of("2", "3", "4", "5", "6", "8");

    /** Una ruta escrita en la terminal. Una que el sistema no acepta da un mensaje, no un error. */
    protected Path leerRuta(String pregunta) {
        String ruta = leer(pregunta);
        try {
            return Path.of(ruta);
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("la ruta \"" + ruta.replace("\0", "")
                    + "\" tiene un carácter que no puede ir en una ruta");
        }
    }

    protected String leer(String pregunta) {
        return terminal.leer(pregunta);
    }

    protected void escribir(String linea) {
        terminal.escribir(linea);
    }

    // La terminal dice qué archivo espera antes de pedir la ruta (docs/DECISIONS.md, 2026-10-26
    // "La terminal dice qué archivo espera antes de pedir la ruta").
    private void cargar() {
        escribir("El archivo puede ser el Excel (.xlsx) tal como lo exporta la fuente, como una"
                + " tabla de referencia de SISPRO, o un CSV en UTF-8 separado por punto y coma"
                + " (;).");
        escribirAyudaDeCarga();
        escribir("Si es CSV, desde la hoja de cálculo: Guardar como, \"CSV UTF-8\", con la"
                + " columna del código en formato de texto para que no pierda los ceros.");
        Path archivo = leerRuta("Ruta del archivo: ");
        String fuente = leer("Fuente: ");
        String direccion = leer("Dirección de descarga: ");
        LocalDate descarga = LocalDate.parse(leer("Fecha de descarga (AAAA-MM-DD): "));
        String version = leer("Versión: ");
        String publicacion = leer("Fecha de publicación (AAAA-MM-DD), o Enter si no la trae: ");
        ResultadoCarga carga = crud.leerCarga(archivo, new DatosVersion(fuente, direccion, descarga,
                version, publicacion.isEmpty() ? null : LocalDate.parse(publicacion)));
        imprimir(carga);
        if (!carga.sePuedeGuardar()) {
            carga.getBloqueos().forEach(b -> escribir("No se puede guardar: " + b));
            return;
        }
        if (!carga.getRechazadas().isEmpty()
                && leer("¿Descargar la lista de rechazadas? (s/n): ").equalsIgnoreCase("s")) {
            String nombre = archivo.getFileName().toString().replaceFirst("\\.csv$", "");
            Path destino = archivo.resolveSibling(nombre + "-rechazadas.csv");
            // escribirRechazadas reemplaza el archivo que ya existe: sin esta pregunta, un archivo
            // propio con ese nombre se pierde sin aviso (docs/DESIGN.md, sección 4).
            if (Files.exists(destino) && !leer("Ya existe " + destino
                    + ". ¿Reemplazarlo? (s/n): ").equalsIgnoreCase("s")) {
                escribir("No se escribió la lista: " + destino + " queda como estaba.");
            } else {
                escribir("Escrita en " + crud.escribirRechazadas(carga, destino));
            }
        }
        Map<String, String> reemplazos = new HashMap<>();
        for (String codigo : carga.getYaNoEstan()) {
            String nuevoCodigo = leer(
                    "¿Qué código nuevo reemplaza a " + codigo + "? (Enter si ninguno): ");
            if (!nuevoCodigo.isEmpty()) {
                reemplazos.put(codigo, nuevoCodigo);
            }
        }
        if (!terminal.confirmar()) {
            return;
        }
        // RC-9: la lista de los que ya no están se confirma aparte.
        if (!carga.getYaNoEstan().isEmpty()) {
            escribir("Se inactivan: " + String.join(", ", carga.getYaNoEstan()));
            if (!terminal.confirmar()) {
                return;
            }
        }
        CatalogoVersion guardada = crud.confirmarCarga(carga, reemplazos);
        escribir("Guardada la versión " + guardada.getVersion() + ", publicada el "
                + guardada.getFechaPublicacion() + ".");
    }

    private void imprimir(ResultadoCarga carga) {
        escribir("Carga: " + switch (carga.getTipo()) {
            case PRIMERA -> "primera carga";
            case VERSION_NUEVA -> "versión nueva";
            case RECARGA -> "recarga de la versión vigente";
        });
        escribir("Filas leídas: " + carga.getFilasLeidas() + ". Aceptadas: "
                + carga.getAceptadas().size() + ". Rechazadas: " + carga.getRechazadas().size()
                + ".");
        carga.getRechazadas().forEach(r -> escribir(
                "  Fila " + r.numero() + ", código \"" + r.codigo() + "\": " + r.motivo()));
        if (!carga.getDeshabilitados().isEmpty()) {
            escribir("Deshabilitadas por la fuente, entran o quedan inactivas: "
                    + carga.getDeshabilitados().size() + ".");
        }
        if (carga.getTipo() == ResultadoCarga.Tipo.VERSION_NUEVA) {
            escribir("Nuevos: " + carga.getNuevos());
            carga.getCambianDeNombre().forEach(c -> escribir("Cambia de nombre: "
                    + c.codigo() + ", de \"" + c.antes() + "\" a \"" + c.ahora() + "\""));
            escribir("Vuelven: " + carga.getVuelven());
            escribir("Ya no están: " + carga.getYaNoEstan());
        }
        carga.getAvisos().forEach(a -> escribir("Aviso: " + a));
    }

    private void consultar() {
        CrudCatalogo.Filtro filtro = switch (leer("Mostrar 1. activas, 2. inactivas, 3. todas: ")) {
            case "1" -> CrudCatalogo.Filtro.ACTIVOS;
            case "2" -> CrudCatalogo.Filtro.INACTIVOS;
            default -> CrudCatalogo.Filtro.TODOS;
        };
        Consulta<T> consulta = crud.consultar(filtro);
        if (consulta.aviso() != null) {
            escribir(consulta.aviso());
            return;
        }
        consulta.valores().forEach(this::imprimir);
        escribir("Activos: " + consulta.activos() + ".");
        consulta.vigente().ifPresent(v -> escribir("Versión vigente: " + v.getVersion()
                + ", publicada el " + v.getFechaPublicacion() + " y descargada el "
                + v.getFechaDescarga() + "."));
    }

    private void buscar() {
        List<T> encontrados = crud.buscar(leer("Código o nombre: "));
        if (encontrados.isEmpty()) {
            escribir(crud.sinCoincidencias());
        }
        encontrados.forEach(this::imprimir);
    }

    private void imprimir(T valor) {
        escribir(valor.getCodigo() + "  " + valor.getNombre() + "  " + valor.getEstado()
                + detalle(valor));
    }

    private void imprimir(ResumenVersion resumen) {
        CatalogoVersion v = resumen.version();
        escribir(v.getVersion() + ", publicada el " + v.getFechaPublicacion()
                + ", descargada el " + v.getFechaDescarga() + ", de " + v.getFuente() + ": "
                + resumen.creados() + " creados, " + resumen.cambiados() + " cambiados, "
                + resumen.inactivados() + " inactivados.");
    }

    private void imprimir(Comprobacion comprobacion) {
        if (comprobacion.coincide()) {
            escribir("El catálogo coincide con la lista oficial.");
            return;
        }
        escribir("Faltan: " + comprobacion.faltan());
        escribir("Inactivos en el catálogo: " + comprobacion.inactivos());
        escribir("Sobran: " + comprobacion.sobran());
        escribir("Nombres distintos: " + comprobacion.nombresDistintos());
    }

    private void crear() {
        String codigo = leer("Código: ");
        String nombre = leer("Nombre: ");
        String referencia = leer("Referencia oficial: ");
        if (terminal.confirmar()) {
            crud.crear(nuevo.apply(codigo, nombre), referencia);
            escribir("Creado: " + codigo);
        }
    }

    private void cambiarNombre() {
        String codigo = leer("Código: ");
        String nombre = leer("Nombre nuevo: ");
        String referencia = leer("Referencia oficial: ");
        if (terminal.confirmar()) {
            crud.cambiarNombre(codigo, nombre, referencia);
            escribir("Nombre cambiado: " + codigo);
        }
    }

    private void cambiarCodigo() {
        String viejo = leer("Código viejo: ");
        String codigo = leer("Código nuevo: ");
        String nombre = leer("Nombre del código nuevo, si no existe: ");
        String referencia = leer("Referencia oficial: ");
        if (terminal.confirmar()) {
            crud.cambiarCodigo(viejo, nuevo.apply(codigo, nombre), referencia);
            escribir("Reemplazado: " + viejo + " por " + codigo);
        }
    }

    private void inactivar() {
        String codigo = leer("Código: ");
        T valor = crud.buscarPorCodigo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("no existe el código " + codigo));
        escribir("Inactivar " + codigo + " " + valor.getNombre() + ". " + usos(codigo)
                + ": la conservan, pero no se ofrece en registros nuevos.");
        escribir("1. " + Motivo.RETIRADO_POR_LA_FUENTE.texto());
        escribir("2. " + Motivo.CREADO_POR_ERROR.texto());
        Motivo motivo = switch (leer("Motivo: ")) {
            case "1" -> Motivo.RETIRADO_POR_LA_FUENTE;
            case "2" -> Motivo.CREADO_POR_ERROR;
            default -> null;
        };
        String referencia = leer("Referencia oficial: ");
        if (terminal.confirmar()) {
            crud.inactivar(codigo, motivo, referencia);
            escribir("Inactivado: " + codigo);
        }
    }

    private void reactivar() {
        String codigo = leer("Código: ");
        String referencia = leer("Referencia oficial, o Enter si no hay otra: ");
        String justificacion = leer("Por qué se reactiva: ");
        if (terminal.confirmar()) {
            crud.reactivar(codigo, referencia, justificacion);
            escribir("Reactivado: " + codigo);
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
        escribir(linea.toString());
    }
}
