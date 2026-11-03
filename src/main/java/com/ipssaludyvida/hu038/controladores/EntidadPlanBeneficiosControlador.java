package com.ipssaludyvida.hu038.controladores;

import com.ipssaludyvida.comun.controladores.ControladorCatalogo;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import com.ipssaludyvida.hu038.modelos.EntidadPlanBeneficios;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-038: las 11 opciones de un catálogo sobre ContratoEntidadesPlanBeneficios.
 * Lo suyo es la comparación, que pide la ruta del archivo oficial (escenario 17).
 */
@Component
public class EntidadPlanBeneficiosControlador
        extends ControladorCatalogo<EntidadPlanBeneficios> {

    private final ContratoEntidadesPlanBeneficios entidades;

    public EntidadPlanBeneficiosControlador(ContratoEntidadesPlanBeneficios entidades,
            Terminal terminal, ContratoSesion sesion) {
        super("HU-038", "Entidades del plan de beneficios", entidades, EntidadPlanBeneficios::new,
                terminal, sesion);
        this.entidades = entidades;
    }

    @Override
    protected Comprobacion comprobar() {
        return entidades.comprobarContraElArchivo(Path.of(leer("Ruta del archivo oficial: ")));
    }
}
