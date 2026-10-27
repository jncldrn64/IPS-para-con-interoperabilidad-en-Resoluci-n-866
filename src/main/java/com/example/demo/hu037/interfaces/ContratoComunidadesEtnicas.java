package com.example.demo.hu037.interfaces;

import com.example.demo.comun.interfaces.CrudCatalogo;
import com.example.demo.comun.servicios.Comprobacion;
import com.example.demo.hu037.modelos.ComunidadEtnica;
import java.nio.file.Path;

/**
 * El contrato de HU-037, catálogo de comunidades étnicas. Lo implementa ComunidadEtnicaServicio y
 * lo usa el menú. Las operaciones comunes vienen de CrudCatalogo; aquí va lo propio de la historia.
 *
 * <p>Escenarios de docs/context/trello/tarjetas/HU-037.md y la operación que los cumple: 1 a 6,
 * leerCarga, confirmarCarga y escribirRechazadas; 7, cambiarCodigo; 8 y 9, crear y cambiarNombre;
 * 10, cambiarNombre e historial; 11, no confirmar, en el menú; 12, consultar e
 * historialDeVersiones; 13 y 14, buscar; 15, buscar y sinCoincidencias; 16, consultar; 17,
 * comprobarContraElArchivo; 18 a 21, contarUsos, inactivar y reactivar; 22, historial.
 */
public interface ContratoComunidadesEtnicas extends CrudCatalogo<ComunidadEtnica> {

    /**
     * Compara el catálogo con un archivo oficial descargado otra vez, sin cambiar nada: el
     * catálogo de referencia no tiene una lista fija en el anexo (escenario 17).
     */
    Comprobacion comprobarContraElArchivo(Path archivo);

    @Override
    default String nombreEnPlural() {
        return "comunidades étnicas";
    }
}
