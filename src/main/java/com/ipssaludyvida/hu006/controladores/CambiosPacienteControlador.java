package com.ipssaludyvida.hu006.controladores;

import com.ipssaludyvida.comun.interfaces.Controlador;
import com.ipssaludyvida.comun.menu.MenuPrincipal;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.modelos.HistorialCambio;
import com.ipssaludyvida.hu001.controladores.SesionControlador;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Permiso;
import com.ipssaludyvida.hu004.controladores.FormularioPaciente;
import com.ipssaludyvida.hu004.servicios.Campo;
import com.ipssaludyvida.hu004.servicios.DatosPaciente;
import com.ipssaludyvida.hu004.servicios.RegistroInvalido;
import com.ipssaludyvida.hu006.interfaces.ContratoCambiosPaciente;
import com.ipssaludyvida.hu018.interfaces.ContratoPaises;
import com.ipssaludyvida.hu022.interfaces.ContratoOcupaciones;
import com.ipssaludyvida.hu026.interfaces.ContratoMunicipios;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu034.modelos.TipoDocumento;
import com.ipssaludyvida.hu035.interfaces.ContratoCategoriasDiscapacidad;
import com.ipssaludyvida.hu036.interfaces.ContratoEtnias;
import com.ipssaludyvida.hu037.interfaces.ContratoComunidadesEtnicas;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-006: busca al paciente, pregunta si es una actualización o una corrección,
 * deja cambiar los datos que elija con las mismas preguntas del registro, muestra lo que cambia y
 * pide confirmar. También muestra el historial de cada dato. Lo abre el auxiliar de admisión.
 */
@Component
public class CambiosPacienteControlador implements Controlador {

    private final ContratoCambiosPaciente cambios;
    private final ContratoTiposDocumento tiposDocumento;
    private final FormularioPaciente formulario;
    private final Terminal terminal;
    private final ContratoSesion sesion;

    public CambiosPacienteControlador(ContratoCambiosPaciente cambios, ContratoPaises paises,
            ContratoTiposDocumento tiposDocumento, ContratoOcupaciones ocupaciones,
            ContratoCategoriasDiscapacidad discapacidades, ContratoMunicipios municipios,
            ContratoEtnias etnias, ContratoComunidadesEtnicas comunidades,
            ContratoEntidadesPlanBeneficios entidades, Terminal terminal, ContratoSesion sesion) {
        this.cambios = cambios;
        this.tiposDocumento = tiposDocumento;
        this.terminal = terminal;
        this.sesion = sesion;
        this.formulario = new FormularioPaciente(paises, tiposDocumento, ocupaciones,
                discapacidades, municipios, etnias, comunidades, entidades, terminal);
    }

    @Override
    public String historia() {
        return "HU-006";
    }

    @Override
    public String titulo() {
        return "Actualización y corrección de un paciente";
    }

    /** Solo el auxiliar de admisión cambia los datos del paciente (matriz de HU-001). */
    @Override
    public Permiso permisoParaVer() {
        return Permiso.CAMBIAR_PACIENTE;
    }

    @Override
    public void abrir() {
        // Escenario 13: el médico tratante consulta, pero no cambia.
        try {
            sesion.exigir(Permiso.CAMBIAR_PACIENTE);
        } catch (IllegalStateException e) {
            escribir("No se pudo: " + e.getMessage());
            return;
        }
        while (true) {
            escribir("");
            escribir("== Actualización y corrección de un paciente");
            escribir("1. Actualizar o corregir datos");
            escribir("2. Ver el historial de cambios");
            escribir("0. Volver");
            String opcion = leer("Opción: ");
            if (opcion.equals("0") || opcion.isEmpty()) {
                return;
            }
            if (!sesion.seguir()) {
                escribir(SesionControlador.VENCIDA);
                return;
            }
            try {
                switch (opcion) {
                    case "1" -> cambiar();
                    case "2" -> historial();
                    default -> escribir("Opción no válida");
                }
            } catch (IllegalArgumentException e) {
                // Escenario 12: sin paciente con ese documento no se cambia nada.
                escribir("No se pudo: " + e.getMessage() + ".");
            } catch (NoSuchElementException e) {
                throw e;
            } catch (RuntimeException e) {
                // El respaldo de docs/DESIGN.md, sección 4: el error da un mensaje y la pantalla
                // sigue.
                escribir(MenuPrincipal.inesperado(e));
            }
        }
    }

    private void cambiar() {
        String[] documento = documento();
        DatosPaciente datos = cambios.datos(documento[0], documento[1]);
        Map<Campo, String> antes = datos.valores();
        HistorialCambio.Tipo tipo = tipo();
        if (tipo == null) {
            escribir("Cancelado: el paciente queda igual.");
            return;
        }
        while (true) {
            escribir("");
            escribir("Datos del paciente:");
            Campo[] campos = Campo.values();
            Map<Campo, String> ahora = datos.valores();
            for (int i = 0; i < campos.length; i++) {
                escribir("  " + (i + 1) + ". " + campos[i].texto() + ": " + valor(ahora,
                        campos[i]));
            }
            String respuesta = leer("Número del dato que cambia (Enter para terminar): ");
            if (respuesta.isEmpty()) {
                break;
            }
            try {
                int n = Integer.parseInt(respuesta);
                if (n >= 1 && n <= campos.length) {
                    formulario.pedir(campos[n - 1], datos);
                    continue;
                }
            } catch (NumberFormatException e) {
                // No es un número de la lista: se pregunta otra vez.
            }
            escribir("Elija un número de la lista.");
        }
        Map<Campo, String> despues = datos.valores();
        Map<Campo, String[]> diferencias = new LinkedHashMap<>();
        for (Campo campo : Campo.values()) {
            if (!Objects.equals(antes.get(campo), despues.get(campo))) {
                diferencias.put(campo, new String[] {antes.get(campo), despues.get(campo)});
            }
        }
        if (diferencias.isEmpty()) {
            escribir("No cambió ningún dato.");
            return;
        }
        escribir(tipo.texto() + " de " + documento[0] + " " + documento[1] + ":");
        diferencias.forEach((campo, valores) -> escribir("  - " + campo.texto() + ": de \""
                + texto(valores[0]) + "\" a \"" + texto(valores[1]) + "\""));
        // Escenario 11: sin confirmar, el paciente queda igual.
        if (!leer("¿Guardar los cambios? (s/n): ").equalsIgnoreCase("s")) {
            escribir("Cancelado: el paciente queda igual.");
            return;
        }
        if (!sesion.seguir()) {
            escribir(SesionControlador.VENCIDA);
            return;
        }
        try {
            List<HistorialCambio> filas = cambios.cambiar(documento[0], documento[1], datos,
                    tipo);
            escribir("Guardado: " + filas.size() + (filas.size() == 1 ? " dato" : " datos")
                    + ". El valor anterior queda en el historial.");
        } catch (RegistroInvalido e) {
            // Escenarios 6 a 8: nada se guarda y el valor anterior se conserva.
            escribir("No se guardó; el paciente queda igual. Revise estos datos:");
            e.getErrores().forEach((campo, motivo) ->
                    escribir("  - " + campo.texto() + ": " + motivo));
        } catch (IllegalStateException e) {
            escribir("No se guardó: " + e.getMessage() + ".");
        }
    }

    private HistorialCambio.Tipo tipo() {
        escribir("¿Qué es el cambio?");
        escribir("  1. Actualizar: el dato cambió en la realidad, por ejemplo se mudó");
        escribir("  2. Corregir: el dato se registró mal");
        while (true) {
            switch (leer("Tipo (Enter para cancelar): ")) {
                case "1" -> {
                    return HistorialCambio.Tipo.ACTUALIZACION;
                }
                case "2" -> {
                    return HistorialCambio.Tipo.CORRECCION;
                }
                case "" -> {
                    return null;
                }
                default -> escribir("Elija 1 o 2.");
            }
        }
    }

    /**
     * Escenario 3: por cada dato que cambió, el valor vigente y cada cambio con su tipo, la fecha,
     * la hora, el nombre y la identificación de quien lo hizo.
     */
    private void historial() {
        String[] documento = documento();
        List<HistorialCambio> filas = cambios.historial(documento[0], documento[1]);
        if (filas.isEmpty()) {
            escribir("El paciente no tiene cambios: sus datos son los del registro.");
            return;
        }
        Map<Campo, String> vigentes = cambios.datos(documento[0], documento[1]).valores();
        Map<String, List<HistorialCambio>> porDato = new LinkedHashMap<>();
        filas.forEach(f -> porDato.computeIfAbsent(f.getDato(), k -> new ArrayList<>()).add(f));
        porDato.forEach((dato, cambiosDelDato) -> {
            Campo campo = Campo.valueOf(dato);
            escribir("");
            escribir(campo.texto() + ", vigente: \"" + texto(vigentes.get(campo)) + "\"");
            for (HistorialCambio f : cambiosDelDato) {
                escribir("  " + f.getFechaHora().withSecond(0).withNano(0).toString()
                        .replace('T', ' ') + "  " + f.getTipo().texto() + ": de \""
                        + texto(f.getValorAnterior()) + "\" a \"" + texto(f.getValorNuevo())
                        + "\", por " + cambios.autor(f));
            }
        });
    }

    // El tipo, de la lista o por su código, y el número del documento.
    private String[] documento() {
        escribir("Tipo de documento:");
        List<TipoDocumento> tipos = tiposDocumento.listarActivos();
        for (int i = 0; i < tipos.size(); i++) {
            escribir("  " + (i + 1) + ". " + tipos.get(i).getCodigo() + "  "
                    + tipos.get(i).getNombre());
        }
        String respuesta = leer("Número o código: ");
        String tipo = respuesta.trim().toUpperCase(Locale.ROOT);
        try {
            int n = Integer.parseInt(respuesta);
            if (n >= 1 && n <= tipos.size()) {
                tipo = tipos.get(n - 1).getCodigo();
            }
        } catch (NumberFormatException e) {
            // Es un código, no un número de la lista.
        }
        return new String[] {tipo, leer("Número de documento: ").trim()};
    }

    private static String valor(Map<Campo, String> valores, Campo campo) {
        String v = valores.get(campo);
        return v == null || v.isEmpty() ? "Sin dato" : v;
    }

    private static String texto(String valor) {
        return valor == null || valor.isEmpty() ? "Sin dato" : valor;
    }

    private String leer(String pregunta) {
        return terminal.leer(pregunta);
    }

    private void escribir(String linea) {
        terminal.escribir(linea);
    }
}
