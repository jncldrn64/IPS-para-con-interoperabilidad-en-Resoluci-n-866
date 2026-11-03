package com.ipssaludyvida.hu034.controladores;

import com.ipssaludyvida.comun.controladores.ControladorCatalogo;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu034.modelos.TipoDocumento;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-034: las 11 opciones de un catálogo sobre ContratoTiposDocumento. Lo
 * suyo es la comparación, que se hace contra la hoja 13 (escenario 18).
 */
@Component
public class TipoDocumentoControlador extends ControladorCatalogo<TipoDocumento> {

    private final ContratoTiposDocumento tipos;

    public TipoDocumentoControlador(ContratoTiposDocumento tipos,
            Terminal terminal, ContratoSesion sesion) {
        super("HU-034", "Tipos de documento", tipos, TipoDocumento::new,
                terminal, sesion);
        this.tipos = tipos;
    }

    @Override
    protected Comprobacion comprobar() {
        return tipos.comprobarContraLaHoja();
    }
}
