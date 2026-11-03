package com.ipssaludyvida.hu018.controladores;

import com.ipssaludyvida.comun.controladores.ControladorCatalogo;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu018.interfaces.ContratoPaises;
import com.ipssaludyvida.hu018.modelos.Pais;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-018: las 11 opciones de un catálogo sobre ContratoPaises. Lo suyo es la
 * comparación, que pide la ruta del archivo oficial (escenario 17).
 */
@Component
public class PaisControlador extends ControladorCatalogo<Pais> {

    private final ContratoPaises paises;

    public PaisControlador(ContratoPaises paises, Terminal terminal, ContratoSesion sesion) {
        super("HU-018", "Países", paises, Pais::new, terminal, sesion);
        this.paises = paises;
    }

    @Override
    protected Comprobacion comprobar() {
        return paises.comprobarContraElArchivo(Path.of(leer("Ruta del archivo oficial: ")));
    }
}
