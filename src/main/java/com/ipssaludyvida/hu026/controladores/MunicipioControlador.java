package com.ipssaludyvida.hu026.controladores;

import com.ipssaludyvida.comun.controladores.ControladorCatalogo;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu026.interfaces.ContratoMunicipios;
import com.ipssaludyvida.hu026.modelos.Municipio;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-026: las 11 opciones de un catálogo sobre ContratoMunicipios. Cada municipio
 * se muestra con su departamento, porque hay municipios con el mismo nombre (escenarios 14 y 15),
 * y la comparación pide la ruta del archivo oficial (escenario 20).
 */
@Component
public class MunicipioControlador extends ControladorCatalogo<Municipio> {

    private final ContratoMunicipios municipios;

    public MunicipioControlador(ContratoMunicipios municipios, Terminal terminal,
            ContratoSesion sesion) {
        super("HU-026", "Municipios", municipios, Municipio::new, terminal, sesion);
        this.municipios = municipios;
    }

    @Override
    protected Comprobacion comprobar() {
        return municipios.comprobarContraElArchivo(leerRuta("Ruta del archivo oficial: "));
    }

    @Override
    protected String detalle(Municipio valor) {
        return municipios.departamento(valor.getCodigo())
                .map(d -> "  " + d.getNombre() + " (" + d.getCodigo() + ")")
                .orElse("  departamento " + valor.getDepartamento());
    }

    @Override
    protected void escribirAyudaDeCarga() {
        escribir("La primera fila trae los nombres de las columnas: código;nombre;"
                + ContratoMunicipios.CODIGO_DEPARTAMENTO + ";"
                + ContratoMunicipios.NOMBRE_DEPARTAMENTO + ". Las demás se ignoran.");
    }
}
