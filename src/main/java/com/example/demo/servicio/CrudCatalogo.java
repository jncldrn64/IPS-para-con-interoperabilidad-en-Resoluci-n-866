package com.example.demo.servicio;

import java.util.List;
import java.util.Optional;

/** Las operaciones de un catálogo. El menú depende de esta interfaz y no del servicio. */
public interface CrudCatalogo<T> {

    List<T> listar();

    Optional<T> buscarPorCodigo(String codigo);

    /**
     * Crea el valor o cambia su nombre; a un valor que ya existe no le cambia el estado ni la
     * versión. Lanza ConstraintViolationException si no cumple su numeral.
     */
    T guardar(T valor);

    /**
     * Lanza IllegalArgumentException si el código no existe, e IllegalStateException si ya estaba
     * inactivo.
     */
    void inactivar(String codigo);
}
