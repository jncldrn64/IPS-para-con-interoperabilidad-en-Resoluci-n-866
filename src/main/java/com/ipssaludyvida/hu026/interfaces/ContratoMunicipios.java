package com.ipssaludyvida.hu026.interfaces;

import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu026.modelos.Departamento;
import com.ipssaludyvida.hu026.modelos.Municipio;
import java.nio.file.Path;
import java.util.Optional;

/**
 * El contrato de HU-026, catálogo de municipios. Lo implementa MunicipioServicio y lo usa
 * MunicipioControlador. Las operaciones comunes vienen de CrudCatalogo; aquí va lo propio de la
 * historia.
 *
 * <p>Escenarios de docs/context/trello/tarjetas/HU-026.md y la operación que los cumple: 1 a 6,
 * leerCarga, confirmarCarga y escribirRechazadas, con el departamento de cada fila; 7 y 8,
 * leerCarga y confirmarCarga en una versión nueva; 9, cambiarCodigo; 10 y 11, crear y
 * cambiarNombre, con el departamento en el catálogo; 12, cambiarNombre e historial; 13, no
 * confirmar, en el controlador; 14, consultar, departamento e historialDeVersiones; 15 a 17,
 * buscar y departamento; 18, buscar y sinCoincidencias; 19, consultar; 20,
 * comprobarContraElArchivo; 21, contarUsos e inactivar; 22, inactivar; 23 y 24, reactivar; 25,
 * historial.
 */
public interface ContratoMunicipios extends CrudCatalogo<Municipio> {

    /** Las columnas del departamento en el CSV de RC-8, sin mayúsculas ni tildes. */
    String CODIGO_DEPARTAMENTO = "codigo_departamento";
    String NOMBRE_DEPARTAMENTO = "nombre_departamento";

    /** El departamento de un municipio, por los dos primeros caracteres de su código. */
    Optional<Departamento> departamento(String codigoMunicipio);

    /**
     * Compara el catálogo con un archivo oficial descargado otra vez, sin cambiar nada. Un nombre
     * de departamento distinto cuenta como nombre distinto de cada municipio suyo (escenario 20).
     */
    Comprobacion comprobarContraElArchivo(Path archivo);

    @Override
    default String nombreEnPlural() {
        return "municipios";
    }
}
