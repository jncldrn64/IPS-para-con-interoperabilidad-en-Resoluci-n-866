package com.ipssaludyvida.hu004.controladores;

import com.ipssaludyvida.comun.interfaces.Controlador;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.hu001.controladores.SesionControlador;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Permiso;
import com.ipssaludyvida.hu004.interfaces.ContratoPacientes;
import com.ipssaludyvida.hu004.modelos.Paciente;
import com.ipssaludyvida.hu004.servicios.Campo;
import com.ipssaludyvida.hu004.servicios.DatosPaciente;
import com.ipssaludyvida.hu004.servicios.RegistroInvalido;
import com.ipssaludyvida.hu018.interfaces.ContratoPaises;
import com.ipssaludyvida.hu022.interfaces.ContratoOcupaciones;
import com.ipssaludyvida.hu026.interfaces.ContratoMunicipios;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu035.interfaces.ContratoCategoriasDiscapacidad;
import com.ipssaludyvida.hu036.interfaces.ContratoEtnias;
import com.ipssaludyvida.hu037.interfaces.ContratoComunidadesEtnicas;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-004: pide los datos del paciente en el orden de la tarjeta, los manda a
 * ContratoPacientes y, si algo está mal, vuelve a pedir solo esos datos (escenario 11). Un dato de
 * catálogo se elige de una lista de valores activos: lo escrito sirve para buscar y no se guarda
 * (escenario 15). Lo abre el auxiliar de admisión (HU-001, matriz de permisos).
 */
@Component
public class PacienteControlador implements Controlador {

    private final ContratoPacientes pacientes;
    private final Terminal terminal;
    private final ContratoSesion sesion;
    private final FormularioPaciente formulario;

    public PacienteControlador(ContratoPacientes pacientes, ContratoPaises paises,
            ContratoTiposDocumento tiposDocumento, ContratoOcupaciones ocupaciones,
            ContratoCategoriasDiscapacidad discapacidades, ContratoMunicipios municipios,
            ContratoEtnias etnias, ContratoComunidadesEtnicas comunidades,
            ContratoEntidadesPlanBeneficios entidades, Terminal terminal, ContratoSesion sesion) {
        this.pacientes = pacientes;
        this.terminal = terminal;
        this.sesion = sesion;
        this.formulario = new FormularioPaciente(paises, tiposDocumento, ocupaciones,
                discapacidades, municipios, etnias, comunidades, entidades, terminal);
    }

    @Override
    public String historia() {
        return "HU-004";
    }

    @Override
    public String titulo() {
        return "Registro de un paciente";
    }

    /** Solo el auxiliar de admisión registra pacientes (matriz de HU-001). */
    @Override
    public Permiso permisoParaVer() {
        return Permiso.CAMBIAR_PACIENTE;
    }

    @Override
    public void abrir() {
        try {
            sesion.exigir(Permiso.CAMBIAR_PACIENTE);
        } catch (IllegalStateException e) {
            escribir("No se pudo: " + e.getMessage());
            return;
        }
        while (true) {
            escribir("");
            escribir("== Registro de un paciente");
            // Escenario 14: antes de empezar, qué catálogo falta cargar.
            pacientes.catalogosSinValores().forEach(f -> escribir("Aviso: " + f));
            escribir("1. Registrar un paciente nuevo");
            escribir("0. Volver");
            String opcion = leer("Opción: ");
            if (opcion.equals("0") || opcion.isEmpty()) {
                return;
            }
            if (!sesion.seguir()) {
                escribir(SesionControlador.VENCIDA);
                return;
            }
            if (opcion.equals("1")) {
                registrar();
            } else {
                escribir("Opción no válida");
            }
        }
    }

    private void registrar() {
        DatosPaciente datos = new DatosPaciente();
        for (Campo campo : Campo.values()) {
            if (formulario.aplica(campo, datos)) {
                formulario.pedir(campo, datos);
            }
        }
        while (true) {
            // Escenario 13: sin confirmar no se guarda nada.
            if (!leer("¿Guardar el paciente? (s/n): ").equalsIgnoreCase("s")) {
                escribir("Cancelado: no se guardó el paciente.");
                return;
            }
            if (!sesion.seguir()) {
                escribir(SesionControlador.VENCIDA);
                return;
            }
            try {
                Paciente p = pacientes.registrar(datos);
                escribir("Registrado el paciente " + p.getTipoDocumento() + " "
                        + p.getNumeroDocumento() + ".");
                return;
            } catch (RegistroInvalido e) {
                escribir("No se guardó. Corrija estos datos; los demás se conservan:");
                e.getErrores().forEach((campo, motivo) ->
                        escribir("  - " + campo.texto() + ": " + motivo));
                for (Campo campo : e.getErrores().keySet()) {
                    formulario.pedir(campo, datos);
                }
            } catch (IllegalStateException e) {
                escribir("No se guardó: " + e.getMessage() + ".");
                return;
            }
        }
    }

    private String leer(String pregunta) {
        return terminal.leer(pregunta);
    }

    private void escribir(String linea) {
        terminal.escribir(linea);
    }
}
