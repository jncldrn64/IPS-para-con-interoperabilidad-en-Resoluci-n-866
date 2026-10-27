package com.ipssaludyvida.comun.interfaces;

/**
 * Lo que el menú principal necesita del controlador de cada historia: con qué número y qué título
 * la lista, y cómo la abre. Cada historia tiene el suyo en hu0NN/controladores/.
 */
public interface Controlador {

    /** El número de la historia, como "HU-036". El menú principal las ordena por él. */
    String historia();

    String titulo();

    void abrir();
}
