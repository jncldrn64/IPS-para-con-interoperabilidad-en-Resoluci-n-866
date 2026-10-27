package com.example.demo.servicio;

import com.example.demo.modelo.CatalogoCambio;
import com.example.demo.modelo.Motivo;
import java.util.List;
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
}
