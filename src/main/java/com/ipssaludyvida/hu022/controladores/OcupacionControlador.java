package com.ipssaludyvida.hu022.controladores;

import com.ipssaludyvida.comun.controladores.ControladorCatalogo;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu022.interfaces.ContratoOcupaciones;
import com.ipssaludyvida.hu022.modelos.Ocupacion;
import com.ipssaludyvida.hu022.servicios.Nodo;
import java.nio.file.Path;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-022: las 11 opciones de un catálogo sobre ContratoOcupaciones, más la 12,
 * la jerarquía (escenario 16). La comparación pide la ruta del archivo oficial (escenario 21), y
 * antes de inactivar dice cuántos pacientes y atenciones usan la ocupación (escenario 22).
 */
@Component
public class OcupacionControlador extends ControladorCatalogo<Ocupacion> {

    private final ContratoOcupaciones ocupaciones;

    public OcupacionControlador(ContratoOcupaciones ocupaciones, Terminal terminal,
            ContratoSesion sesion) {
        super("HU-022", "Ocupaciones", ocupaciones, Ocupacion::new, terminal, sesion);
        this.ocupaciones = ocupaciones;
    }

    @Override
    protected Comprobacion comprobar() {
        return ocupaciones.comprobarContraElArchivo(Path.of(leer("Ruta del archivo oficial: ")));
    }

    @Override
    protected Map<String, OpcionPropia> opcionesPropias() {
        return Map.of("12", new OpcionPropia("Ver la jerarquía", this::jerarquia));
    }

    @Override
    protected String usos(String codigo) {
        return "La usan " + ocupaciones.contarUsos(codigo) + " pacientes y "
                + ocupaciones.contarAtenciones(codigo) + " atenciones";
    }

    private void jerarquia() {
        if (ocupaciones.listar().isEmpty()) {
            escribir(ocupaciones.consultar(ContratoOcupaciones.Filtro.TODOS).aviso());
            return;
        }
        for (Nodo n : ocupaciones.jerarquia()) {
            StringBuilder linea = new StringBuilder("  ".repeat(n.nivel() - 1))
                    .append(n.valor().getCodigo()).append("  ").append(n.valor().getNombre())
                    .append("  ").append(n.valor().getEstado());
            if (n.seRegistra()) {
                linea.append("  [se registra al paciente]");
            }
            if (n.sinOcupaciones()) {
                linea.append("  [sin ocupaciones]");
            }
            escribir(linea.toString());
        }
    }
}
