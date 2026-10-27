package com.example.demo.hu037.controladores;

import com.example.demo.comun.controladores.ControladorCatalogo;
import com.example.demo.comun.menu.Terminal;
import com.example.demo.comun.servicios.Comprobacion;
import com.example.demo.hu037.interfaces.ContratoComunidadesEtnicas;
import com.example.demo.hu037.modelos.ComunidadEtnica;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-037: las 11 opciones de un catálogo sobre ContratoComunidadesEtnicas. Lo
 * suyo es la comparación, que pide la ruta del archivo oficial (escenario 17).
 */
@Component
public class ComunidadEtnicaControlador extends ControladorCatalogo<ComunidadEtnica> {

    private final ContratoComunidadesEtnicas comunidades;

    public ComunidadEtnicaControlador(ContratoComunidadesEtnicas comunidades, Terminal terminal) {
        super("HU-037", "Comunidades étnicas", comunidades, ComunidadEtnica::new, terminal);
        this.comunidades = comunidades;
    }

    @Override
    protected Comprobacion comprobar() {
        return comunidades.comprobarContraElArchivo(Path.of(leer("Ruta del archivo oficial: ")));
    }
}
