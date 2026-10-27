package com.ipssaludyvida.hu036.controladores;

import com.ipssaludyvida.comun.controladores.ControladorCatalogo;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu036.interfaces.ContratoEtnias;
import com.ipssaludyvida.hu036.modelos.Etnia;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-036: las 11 opciones de un catálogo sobre ContratoEtnias. Lo suyo es la
 * comparación, que se hace contra la hoja 15 (escenario 18).
 */
@Component
public class EtniaControlador extends ControladorCatalogo<Etnia> {

    private final ContratoEtnias etnias;

    public EtniaControlador(ContratoEtnias etnias, Terminal terminal) {
        super("HU-036", "Etnias", etnias, Etnia::new, terminal);
        this.etnias = etnias;
    }

    @Override
    protected Comprobacion comprobar() {
        return etnias.comprobarContraLaHoja();
    }
}
