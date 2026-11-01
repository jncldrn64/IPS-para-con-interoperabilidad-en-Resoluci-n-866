package com.ipssaludyvida.comun.interfaces;

import com.ipssaludyvida.hu001.modelos.Permiso;

/**
 * Lo que el menú principal necesita del controlador de cada historia: con qué número y qué título
 * la lista, y cómo la abre. Cada historia tiene el suyo en hu0NN/controladores/.
 */
public interface Controlador {

    /** El número de la historia, como "HU-036". El menú principal las ordena por él. */
    String historia();

    String titulo();

    /**
     * El permiso con que el menú principal muestra la historia. Un cargo que no lo tiene no la ve
     * (HU-001, escenarios 1 y 5 a 7).
     */
    Permiso permisoParaVer();

    void abrir();
}
