package com.ipssaludyvida.hu006.servicios;

import com.ipssaludyvida.comun.modelos.HistorialCambio;
import com.ipssaludyvida.comun.repositorios.HistorialCambioRepositorio;
import com.ipssaludyvida.comun.servicios.RegistroCatalogos;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Usuario;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import com.ipssaludyvida.hu004.interfaces.ContratoPacientes;
import com.ipssaludyvida.hu004.modelos.Paciente;
import com.ipssaludyvida.hu004.servicios.Campo;
import com.ipssaludyvida.hu004.servicios.DatosPaciente;
import com.ipssaludyvida.hu006.interfaces.ContratoCambiosPaciente;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * HU-006: cambia los datos de un paciente con las reglas de HU-004 y deja el dato anterior en el
 * historial, con el tipo, quién y cuándo. No lee del teclado ni imprime (docs/DESIGN.md,
 * sección 6).
 */
@Service
public class CambiosPacienteServicio implements ContratoCambiosPaciente {

    private final ContratoPacientes pacientes;
    private final HistorialCambioRepositorio historial;
    private final UsuarioRepositorio usuarios;
    private final ContratoSesion sesion;
    private final RegistroCatalogos registro;

    public CambiosPacienteServicio(ContratoPacientes pacientes,
            HistorialCambioRepositorio historial, UsuarioRepositorio usuarios,
            ContratoSesion sesion, RegistroCatalogos registro) {
        this.pacientes = pacientes;
        this.historial = historial;
        this.usuarios = usuarios;
        this.sesion = sesion;
        this.registro = registro;
    }

    @Override
    @Transactional
    public List<HistorialCambio> cambiar(String tipoDocumento, String numeroDocumento,
            DatosPaciente nuevos, HistorialCambio.Tipo tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("diga si es una actualización o una corrección");
        }
        Paciente p = paciente(tipoDocumento, numeroDocumento);
        Map<Campo, String> antes = DatosPaciente.de(p).valores();
        Map<Campo, String> despues = nuevos.valores();
        List<Campo> cambiados = new ArrayList<>();
        for (Campo campo : Campo.values()) {
            if (!Objects.equals(antes.get(campo), despues.get(campo))) {
                cambiados.add(campo);
            }
        }
        if (cambiados.isEmpty()) {
            throw new IllegalArgumentException("no cambió ningún dato");
        }
        // Valida y guarda, o lanza sin tocar el paciente: así el valor anterior se conserva
        // (escenarios 6 a 9).
        pacientes.actualizar(p, nuevos);
        Long quien = sesion.actual().map(Usuario::getId).orElse(null);
        LocalDateTime ahora = registro.ahora();
        List<HistorialCambio> filas = new ArrayList<>();
        for (Campo campo : cambiados) {
            HistorialCambio fila = new HistorialCambio();
            fila.setTabla(TABLA);
            fila.setRegistroId(p.getId());
            fila.setDato(campo.name());
            fila.setValorAnterior(vacioANulo(antes.get(campo)));
            fila.setValorNuevo(vacioANulo(despues.get(campo)));
            fila.setTipo(tipo);
            fila.setUsuarioId(quien);
            fila.setFechaHora(ahora);
            filas.add(historial.save(fila));
        }
        return filas;
    }

    @Override
    @Transactional(readOnly = true)
    public DatosPaciente datos(String tipoDocumento, String numeroDocumento) {
        return DatosPaciente.de(paciente(tipoDocumento, numeroDocumento));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialCambio> historial(String tipoDocumento, String numeroDocumento) {
        Paciente p = paciente(tipoDocumento, numeroDocumento);
        return historial.findByTablaAndRegistroIdOrderByIdAsc(TABLA, p.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public String autor(HistorialCambio cambio) {
        if (cambio.getUsuarioId() == null) {
            return "sin sesión";
        }
        return usuarios.findById(cambio.getUsuarioId())
                .map(u -> u.getNombre() + " (identificación " + u.getIdentificacion() + ")")
                .orElse("usuario " + cambio.getUsuarioId());
    }

    // Escenario 12: sin paciente con ese documento no se cambia nada.
    private Paciente paciente(String tipoDocumento, String numeroDocumento) {
        if (tipoDocumento == null || tipoDocumento.isBlank()) {
            throw new IllegalArgumentException("elija el tipo de documento");
        }
        if (numeroDocumento == null || numeroDocumento.isBlank()) {
            throw new IllegalArgumentException("escriba el número de documento");
        }
        return pacientes.buscar(tipoDocumento, numeroDocumento)
                .orElseThrow(() -> new IllegalArgumentException("no hay registro con "
                        + tipoDocumento.trim().toUpperCase(java.util.Locale.ROOT) + " "
                        + numeroDocumento.trim()));
    }

    private static String vacioANulo(String valor) {
        return valor == null || valor.isEmpty() ? null : valor;
    }
}
