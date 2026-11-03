package com.ipssaludyvida.hu005.controladores;

import com.ipssaludyvida.comun.interfaces.Controlador;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.hu001.controladores.SesionControlador;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Permiso;
import com.ipssaludyvida.hu004.interfaces.ContratoPacientes;
import com.ipssaludyvida.hu004.modelos.ListaFija;
import com.ipssaludyvida.hu004.modelos.Paciente;
import com.ipssaludyvida.hu005.interfaces.ContratoConsultaPacientes;
import com.ipssaludyvida.hu005.servicios.Ficha;
import com.ipssaludyvida.hu005.servicios.ValorRegistrado;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu034.modelos.TipoDocumento;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-005: pide el tipo y el número de documento y muestra la ficha del paciente,
 * dato por dato. La pantalla es la misma para el auxiliar de admisión y el médico tratante, y no
 * ofrece cambiar nada: actualizar y corregir es de HU-006 (escenarios 10 y 12).
 */
@Component
public class ConsultaPacienteControlador implements Controlador {

    /** Lo que muestra un dato opcional que no se registró (escenario 8). */
    public static final String SIN_DATO = "Sin dato";

    private final ContratoConsultaPacientes consulta;
    private final ContratoTiposDocumento tiposDocumento;
    private final Terminal terminal;
    private final ContratoSesion sesion;

    public ConsultaPacienteControlador(ContratoConsultaPacientes consulta,
            ContratoTiposDocumento tiposDocumento, Terminal terminal, ContratoSesion sesion) {
        this.consulta = consulta;
        this.tiposDocumento = tiposDocumento;
        this.terminal = terminal;
        this.sesion = sesion;
    }

    @Override
    public String historia() {
        return "HU-005";
    }

    @Override
    public String titulo() {
        return "Consulta de un paciente";
    }

    /** El auxiliar de admisión y el médico tratante consultan pacientes (matriz de HU-001). */
    @Override
    public Permiso permisoParaVer() {
        return Permiso.CONSULTAR_PACIENTE;
    }

    @Override
    public void abrir() {
        // Escenario 11: el profesional de estadística no ve datos de pacientes.
        try {
            sesion.exigir(Permiso.CONSULTAR_PACIENTE);
        } catch (IllegalStateException e) {
            escribir("No se pudo: " + e.getMessage());
            return;
        }
        while (true) {
            escribir("");
            escribir("== Consulta de un paciente");
            escribir("1. Buscar por tipo y número de documento");
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
                buscar();
            } else {
                escribir("Opción no válida");
            }
        }
    }

    private void buscar() {
        escribir("Tipo de documento:");
        List<TipoDocumento> tipos = tiposDocumento.listarActivos();
        for (int i = 0; i < tipos.size(); i++) {
            escribir("  " + (i + 1) + ". " + tipos.get(i).getCodigo() + "  "
                    + tipos.get(i).getNombre());
        }
        String tipo = tipo(tipos, leer("Número o código (Enter si no lo sabe): "));
        String numero = leer("Número de documento: ");
        try {
            Optional<Ficha> ficha = consulta.consultar(tipo, numero);
            if (ficha.isEmpty()) {
                escribir("No hay registro con " + tipo + " " + numero.trim() + ".");
                return;
            }
            imprimir(ficha.get());
        } catch (IllegalArgumentException e) {
            // Escenario 3: sin el tipo, o sin el número, no se busca.
            escribir("No se pudo buscar: " + e.getMessage() + ". Escriba el tipo y el número de"
                    + " documento para buscar.");
        }
    }

    // El tipo elegido por su número en la lista o por su código. Lo que no está queda tal cual, y
    // la búsqueda no encuentra a nadie con él.
    private static String tipo(List<TipoDocumento> tipos, String respuesta) {
        try {
            int n = Integer.parseInt(respuesta);
            if (n >= 1 && n <= tipos.size()) {
                return tipos.get(n - 1).getCodigo();
            }
        } catch (NumberFormatException e) {
            // Es un código, no un número de la lista.
        }
        return respuesta.trim().toUpperCase(Locale.ROOT);
    }

    private void imprimir(Ficha f) {
        Paciente p = f.paciente();
        escribir("");
        escribir("== Paciente");
        escribir(nombreCompleto(p) + ", " + p.getTipoDocumento() + " "
                + p.getNumeroDocumento());
        dato("Documento", p.getTipoDocumento() + " " + p.getNumeroDocumento() + " "
                + nombre(f.tipoDocumento()));
        dato("Primer apellido", p.getPrimerApellido());
        dato("Segundo apellido", p.getSegundoApellido());
        dato("Primer nombre", p.getPrimerNombre());
        dato("Segundo nombre", p.getSegundoNombre());
        // Escenario 6: la fecha y la hora van por separado, y la hora puede faltar.
        dato("Fecha de nacimiento", p.getFechaNacimiento().toString());
        LocalTime hora = p.getHoraNacimiento();
        dato("Hora de nacimiento", hora == null ? null : hora.toString());
        dato("Sexo biológico", lista(f.sexoBiologico()));
        dato("Identidad de género", lista(f.identidadGenero()));
        dato("Nacionalidad", varios(f.nacionalidades()));
        dato("Ocupación", texto(f.ocupacion()));
        dato("Categoría de discapacidad", varios(f.discapacidades()));
        dato("País de residencia habitual", texto(f.paisResidencia()));
        dato("Ciudad de residencia", f.municipioResidencia() == null
                ? (ContratoPacientes.COLOMBIA.equals(p.getPaisResidencia()) ? null
                        : "No aplica: reside fuera de Colombia")
                : texto(f.municipioResidencia()) + (f.departamento() == null ? ""
                        : ", " + f.departamento()));
        dato("Zona territorial de residencia", lista(f.zonaTerritorial()));
        dato("Etnia", texto(f.etnia()));
        dato("Comunidad étnica", f.comunidadEtnica() == null && "99".equals(p.getEtnia())
                ? "No aplica" : texto(f.comunidadEtnica()));
        dato("Entidad del plan de beneficios", texto(f.entidadPlanBeneficios()));
        dato("Registrado el", p.getRegistradoEn().withNano(0).toString().replace('T', ' '));
    }

    private void dato(String etiqueta, String valor) {
        escribir(etiqueta + ": " + (valor == null || valor.isBlank() ? SIN_DATO : valor));
    }

    private static String nombreCompleto(Paciente p) {
        return Stream.of(p.getPrimerNombre(), p.getSegundoNombre(),
                p.getPrimerApellido(), p.getSegundoApellido())
                .filter(s -> s != null && !s.isBlank()).collect(Collectors.joining(" "));
    }

    /**
     * El código, el nombre con que se registró y, si cambió, el vigente (RC-11, escenario 13). Si
     * el valor está inactivo hoy, lo marca (escenario 9).
     */
    static String texto(ValorRegistrado v) {
        return v == null ? null : v.codigo() + " " + nombre(v);
    }

    private static String nombre(ValorRegistrado v) {
        String texto = v.nombre() == null ? "" : v.nombre();
        if (v.nombreVigente() != null) {
            texto += "; nombre vigente en el catálogo: \"" + v.nombreVigente() + "\"";
        }
        if (v.inactivo()) {
            texto += " [inactivo en el catálogo]";
        }
        return texto;
    }

    private static String varios(List<ValorRegistrado> valores) {
        return valores.stream().map(ConsultaPacienteControlador::texto)
                .collect(Collectors.joining("; "));
    }

    private static String lista(ListaFija valor) {
        return valor == null ? null : valor.codigo() + " " + valor.texto();
    }

    private String leer(String pregunta) {
        return terminal.leer(pregunta);
    }

    private void escribir(String linea) {
        terminal.escribir(linea);
    }
}
