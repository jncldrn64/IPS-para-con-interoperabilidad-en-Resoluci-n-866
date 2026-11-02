package com.ipssaludyvida.hu035.controladores;

import com.ipssaludyvida.comun.controladores.ControladorCatalogo;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu035.interfaces.ContratoCategoriasDiscapacidad;
import com.ipssaludyvida.hu035.modelos.CategoriaDiscapacidad;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-035: las 11 opciones de un catálogo sobre ContratoCategoriasDiscapacidad. Lo
 * suyo es la comparación, que se hace contra las hojas 14 y 15 (escenario 18).
 */
@Component
public class CategoriaDiscapacidadControlador extends ControladorCatalogo<CategoriaDiscapacidad> {

    private final ContratoCategoriasDiscapacidad categorias;

    public CategoriaDiscapacidadControlador(ContratoCategoriasDiscapacidad categorias,
            Terminal terminal, ContratoSesion sesion) {
        super("HU-035", "Categorías de discapacidad", categorias, CategoriaDiscapacidad::new,
                terminal, sesion);
        this.categorias = categorias;
    }

    @Override
    protected Comprobacion comprobar() {
        return categorias.comprobarContraLaHoja();
    }
}
