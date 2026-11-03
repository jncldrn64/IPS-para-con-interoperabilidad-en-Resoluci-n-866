package com.ipssaludyvida.hu005.servicios;

import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.modelos.Catalogo;
import com.ipssaludyvida.comun.modelos.CatalogoCambio;
import com.ipssaludyvida.hu004.interfaces.ContratoPacientes;
import com.ipssaludyvida.hu004.modelos.IdentidadGenero;
import com.ipssaludyvida.hu004.modelos.ListaFija;
import com.ipssaludyvida.hu004.modelos.Paciente;
import com.ipssaludyvida.hu004.modelos.SexoBiologico;
import com.ipssaludyvida.hu004.modelos.ZonaTerritorial;
import com.ipssaludyvida.hu005.interfaces.ContratoConsultaPacientes;
import com.ipssaludyvida.hu018.interfaces.ContratoPaises;
import com.ipssaludyvida.hu022.interfaces.ContratoOcupaciones;
import com.ipssaludyvida.hu026.interfaces.ContratoMunicipios;
import com.ipssaludyvida.hu026.modelos.Departamento;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu035.interfaces.ContratoCategoriasDiscapacidad;
import com.ipssaludyvida.hu036.interfaces.ContratoEtnias;
import com.ipssaludyvida.hu037.interfaces.ContratoComunidadesEtnicas;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * HU-005: arma la ficha de un paciente con el nombre de cada valor de catálogo. El paciente guarda
 * solo los códigos (modelo E-R, RC-11), así que el nombre del día del registro sale de la tabla de
 * cambios del catálogo: el último nombre anotado hasta esa hora. Solo lee.
 */
@Service
public class ConsultaPacienteServicio implements ContratoConsultaPacientes {

    private final ContratoPacientes pacientes;
    private final ContratoPaises paises;
    private final ContratoTiposDocumento tiposDocumento;
    private final ContratoOcupaciones ocupaciones;
    private final ContratoCategoriasDiscapacidad discapacidades;
    private final ContratoMunicipios municipios;
    private final ContratoEtnias etnias;
    private final ContratoComunidadesEtnicas comunidades;
    private final ContratoEntidadesPlanBeneficios entidades;

    public ConsultaPacienteServicio(ContratoPacientes pacientes, ContratoPaises paises,
            ContratoTiposDocumento tiposDocumento, ContratoOcupaciones ocupaciones,
            ContratoCategoriasDiscapacidad discapacidades, ContratoMunicipios municipios,
            ContratoEtnias etnias, ContratoComunidadesEtnicas comunidades,
            ContratoEntidadesPlanBeneficios entidades) {
        this.pacientes = pacientes;
        this.paises = paises;
        this.tiposDocumento = tiposDocumento;
        this.ocupaciones = ocupaciones;
        this.discapacidades = discapacidades;
        this.municipios = municipios;
        this.etnias = etnias;
        this.comunidades = comunidades;
        this.entidades = entidades;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ficha> consultar(String tipoDocumento, String numeroDocumento) {
        if (tipoDocumento == null || tipoDocumento.isBlank()) {
            throw new IllegalArgumentException("elija el tipo de documento");
        }
        if (numeroDocumento == null || numeroDocumento.isBlank()) {
            throw new IllegalArgumentException("escriba el número de documento");
        }
        return pacientes.buscar(tipoDocumento, numeroDocumento).map(this::ficha);
    }

    private Ficha ficha(Paciente p) {
        LocalDateTime dia = p.getRegistradoEn();
        return new Ficha(p,
                valor(tiposDocumento, p.getTipoDocumento(), dia),
                valores(paises, p.getNacionalidades(), dia),
                ListaFija.desde(SexoBiologico.class, p.getSexoBiologico()),
                ListaFija.desde(IdentidadGenero.class, p.getIdentidadGenero()),
                valor(ocupaciones, p.getOcupacion(), dia),
                valores(discapacidades, p.getDiscapacidades(), dia),
                valor(paises, p.getPaisResidencia(), dia),
                valor(municipios, p.getMunicipioResidencia(), dia),
                p.getMunicipioResidencia() == null ? null
                        : municipios.departamento(p.getMunicipioResidencia())
                                .map(Departamento::getNombre).orElse(null),
                valor(etnias, p.getEtnia(), dia),
                valor(comunidades, p.getComunidadEtnica(), dia),
                ListaFija.desde(ZonaTerritorial.class, p.getZonaTerritorial()),
                valor(entidades, p.getEntidadPlanBeneficios(), dia));
    }

    private static List<ValorRegistrado> valores(CrudCatalogo<? extends Catalogo> crud,
            Collection<String> codigos, LocalDateTime dia) {
        return codigos.stream().sorted().map(c -> valor(crud, c, dia)).toList();
    }

    /**
     * El valor con el nombre que tenía el día del registro y, si cambió después, el vigente. Un
     * valor sin cambios anotados hasta ese día, como uno creado en la base a mano, toma el nombre
     * vigente.
     */
    static ValorRegistrado valor(CrudCatalogo<? extends Catalogo> crud, String codigo,
            LocalDateTime dia) {
        if (codigo == null) {
            return null;
        }
        Optional<? extends Catalogo> actual = crud.buscarPorCodigo(codigo);
        String vigente = actual.map(Catalogo::getNombre).orElse(null);
        String delRegistro = crud.historial(codigo).stream()
                .filter(c -> c.getTipo() == CatalogoCambio.Tipo.CREADO
                        || c.getTipo() == CatalogoCambio.Tipo.NOMBRE)
                .filter(c -> c.getValorNuevo() != null)
                .filter(c -> dia == null || !c.getFechaHora().isAfter(dia))
                .reduce((primero, segundo) -> segundo)
                .map(CatalogoCambio::getValorNuevo).orElse(vigente);
        boolean inactivo = actual.map(v -> v.getEstado() == Catalogo.Estado.INACTIVO)
                .orElse(false);
        return new ValorRegistrado(codigo, delRegistro,
                Objects.equals(delRegistro, vigente) ? null : vigente, inactivo);
    }
}
