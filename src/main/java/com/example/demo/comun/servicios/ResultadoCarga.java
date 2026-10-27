package com.example.demo.comun.servicios;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;

/**
 * Lo que trae un archivo antes de guardarlo: las filas aceptadas y las rechazadas (RC-8) y, si el
 * catálogo ya tiene valores, qué cambiaría (RC-9). Leerlo no cambia nada; guardar es
 * CrudCatalogo.confirmarCarga, y no llamarla es cancelar.
 */
@Getter
public class ResultadoCarga {

    public enum Tipo { PRIMERA, VERSION_NUEVA, RECARGA }

    public record Fila(int numero, String codigo, String nombre) {}

    public record Rechazo(int numero, String codigo, String motivo) {}

    public record CambioDeNombre(String codigo, String antes, String ahora) {}

    private final Tipo tipo;
    private final DatosVersion datos;
    private int filasLeidas;
    private final List<Fila> aceptadas = new ArrayList<>();
    private final List<Rechazo> rechazadas = new ArrayList<>();
    /** Los códigos de todas las filas, aceptadas o no: con ellos están en la versión (RC-10). */
    private final Set<String> codigosDelArchivo = new LinkedHashSet<>();
    private final List<String> nuevos = new ArrayList<>();
    private final List<CambioDeNombre> cambianDeNombre = new ArrayList<>();
    private final List<String> vuelven = new ArrayList<>();
    private final List<String> yaNoEstan = new ArrayList<>();
    private final List<String> avisos = new ArrayList<>();
    /** Lo que impide guardar la carga, como un código de la hoja que falta (HU-036, esc. 2). */
    private final List<String> bloqueos = new ArrayList<>();

    ResultadoCarga(Tipo tipo, DatosVersion datos) {
        this.tipo = tipo;
        this.datos = datos;
    }

    void contarFila() {
        filasLeidas++;
    }

    public boolean sePuedeGuardar() {
        return bloqueos.isEmpty();
    }
}
