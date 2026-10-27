package com.example.demo.comun.interfaces;

import com.example.demo.comun.modelos.CatalogoCambio;
import com.example.demo.comun.modelos.CatalogoVersion;
import com.example.demo.comun.modelos.Motivo;
import com.example.demo.comun.servicios.Consulta;
import com.example.demo.comun.servicios.DatosVersion;
import com.example.demo.comun.servicios.ResultadoCarga;
import com.example.demo.comun.servicios.ResumenVersion;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Las operaciones de un catálogo. El controlador depende de esta interfaz y no del servicio. Una
 * operación que no cumple su regla no guarda nada y lanza una excepción con el motivo:
 * ConstraintViolation si un dato no cumple su numeral, IllegalArgument si falta un dato o el
 * código no existe, e IllegalState si el valor no está en el estado que la operación pide.
 */
public interface CrudCatalogo<T> {

    /** Qué valores muestra la consulta (escenario 13 de HU-036). */
    enum Filtro { ACTIVOS, INACTIVOS, TODOS }

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
     * Los valores del filtro, cuántos hay activos y la versión vigente, sin cambiar nada. Si el
     * catálogo está vacío, trae el aviso de que falta cargar el archivo oficial (escenarios 13 y 17
     * de HU-036).
     */
    Consulta<T> consultar(Filtro filtro);

    /** Cada versión cargada, de la más vieja a la vigente, con lo que hizo (escenario 13). */
    List<ResumenVersion> historialDeVersiones();

    /**
     * Por código, exacta; si ningún código coincide, por nombre, parcial y sin distinguir
     * mayúsculas ni tildes, con todas las coincidencias ordenadas por nombre (D-23; escenarios 14 y
     * 15 de HU-036).
     */
    List<T> buscar(String texto);

    /** El nombre del catálogo en plural, como "etnias". Lo pone el contrato de cada historia. */
    String nombreEnPlural();

    /** Lo que dice una búsqueda sin resultados (escenario 16 de HU-036). */
    default String sinCoincidencias() {
        return "No hay " + nombreEnPlural() + " que coincidan con la búsqueda";
    }

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
