package com.example.demo.servicio;

import com.example.demo.modelo.CatalogoCambio;
import com.example.demo.modelo.CatalogoVersion;
import com.example.demo.modelo.Motivo;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Las operaciones de un catálogo. El menú depende de esta interfaz y no del servicio. Una operación
 * que no cumple su regla no guarda nada y lanza una excepción con el motivo: ConstraintViolation si
 * un dato no cumple su numeral, IllegalArgument si falta un dato o el código no existe, e
 * IllegalState si el valor no está en el estado que la operación pide.
 */
public interface CrudCatalogo<T> {

    List<T> listar();

    /** Los valores que se ofrecen en registros nuevos (RC-2). */
    List<T> listarActivos();

    Optional<T> buscarPorCodigo(String codigo);

    /** Crea un valor por cambio oficial, con su referencia (escenarios 9 y 10 de HU-036). */
    T crear(T valor, String referencia);

    /** Cambia el nombre por cambio oficial; el anterior queda en el historial (RC-11). */
    T cambiarNombre(String codigo, String nombre, String referencia);

    /**
     * Inactiva el código viejo con "Reemplazado por" y crea el nuevo si no existe (escenario 8 de
     * HU-036).
     */
    void cambiarCodigo(String viejo, T nuevo, String referencia);

    /** Cuántos registros usan el valor, para mostrarlo antes de inactivar (RC-10). */
    long contarUsos(String codigo);

    /** Inactiva con uno de los motivos que elige una persona (RC-10). */
    void inactivar(String codigo, Motivo motivo, String referencia);

    /** Reactiva con otra referencia oficial, o diciendo por qué se reactiva (RC-10). */
    void reactivar(String codigo, String referencia, String justificacion);

    /** Cada cambio del código, del primero al último. */
    List<CatalogoCambio> historial(String codigo);

    Optional<CatalogoVersion> versionVigente();

    /**
     * Lee el CSV de RC-8 y dice qué trae y qué cambiaría, sin guardar nada. Rechaza el archivo
     * completo si le falta una columna o si una fecha no sirve (escenario 4 de HU-036).
     */
    ResultadoCarga leerCarga(Path archivo, DatosVersion datos);

    /**
     * Guarda una carga leída. En una versión nueva, reemplazos dice qué código nuevo reemplaza a
     * uno que ya no está (RC-9); los demás que ya no están se inactivan con "No está en la
     * versión".
     */
    CatalogoVersion confirmarCarga(ResultadoCarga carga, Map<String, String> reemplazos);

    /** Escribe la lista de filas rechazadas en un CSV, para descargarla (RC-8). */
    Path escribirRechazadas(ResultadoCarga carga, Path destino);
}
