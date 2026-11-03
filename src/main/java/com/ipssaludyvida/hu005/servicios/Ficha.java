package com.ipssaludyvida.hu005.servicios;

import com.ipssaludyvida.hu004.modelos.IdentidadGenero;
import com.ipssaludyvida.hu004.modelos.Paciente;
import com.ipssaludyvida.hu004.modelos.SexoBiologico;
import com.ipssaludyvida.hu004.modelos.ZonaTerritorial;
import java.util.List;

/**
 * Lo que muestra la consulta de un paciente: el registro de HU-004 y cada valor de catálogo con su
 * nombre. Un dato opcional sin registrar viene en null o en una lista vacía (escenario 8). El
 * departamento acompaña a la ciudad, para distinguir las que tienen el mismo nombre.
 */
public record Ficha(Paciente paciente, ValorRegistrado tipoDocumento,
        List<ValorRegistrado> nacionalidades, SexoBiologico sexoBiologico,
        IdentidadGenero identidadGenero, ValorRegistrado ocupacion,
        List<ValorRegistrado> discapacidades, ValorRegistrado paisResidencia,
        ValorRegistrado municipioResidencia, String departamento, ValorRegistrado etnia,
        ValorRegistrado comunidadEtnica, ZonaTerritorial zonaTerritorial,
        ValorRegistrado entidadPlanBeneficios) {
}
