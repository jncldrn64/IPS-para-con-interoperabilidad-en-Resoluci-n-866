package com.ipssaludyvida.hu018.interfaces;

import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu018.modelos.Pais;
import java.nio.file.Path;

/**
 * El contrato de HU-018, catálogo de países. Lo implementa PaisServicio y lo usa PaisControlador.
 * Las operaciones comunes vienen de CrudCatalogo; aquí va lo propio de la historia.
 *
 * <p>Escenarios de docs/context/trello/tarjetas/HU-018.md y la operación que los cumple: 1 a 6,
 * leerCarga, confirmarCarga y escribirRechazadas; 7, cambiarCodigo; 8 y 9, crear y cambiarNombre;
 * 10, cambiarNombre e historial; 11, no confirmar, en el controlador; 12, consultar e
 * historialDeVersiones; 13 y 14, buscar; 15, buscar y sinCoincidencias; 16, consultar; 17,
 * comprobarContraElArchivo; 18 a 21, contarUsos, inactivar y reactivar; 22, historial.
 */
public interface ContratoPaises extends CrudCatalogo<Pais> {

    /**
     * Compara el catálogo con un archivo oficial descargado otra vez, sin cambiar nada: el anexo no
     * trae la lista, la trae la tabla Pais de SISPRO (escenario 17).
     */
    Comprobacion comprobarContraElArchivo(Path archivo);

    @Override
    default String nombreEnPlural() {
        return "países";
    }
}
