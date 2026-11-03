package com.ipssaludyvida.hu004.servicios;

import com.ipssaludyvida.comun.interfaces.UsosDeCatalogo;
import com.ipssaludyvida.comun.modelos.Catalogo;
import com.ipssaludyvida.hu018.modelos.Pais;
import com.ipssaludyvida.hu022.modelos.Ocupacion;
import com.ipssaludyvida.hu026.modelos.Municipio;
import com.ipssaludyvida.hu034.modelos.TipoDocumento;
import com.ipssaludyvida.hu035.modelos.CategoriaDiscapacidad;
import com.ipssaludyvida.hu036.modelos.Etnia;
import com.ipssaludyvida.hu037.modelos.ComunidadEtnica;
import com.ipssaludyvida.hu038.modelos.EntidadPlanBeneficios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Cuántos pacientes usan un valor de cada catálogo que guarda el paciente, para la consulta y la
 * inactivación de los catálogos (RC-4, D-20). Un paciente cuenta una vez aunque use el valor en
 * dos datos, como un país de nacionalidad que es también el de residencia.
 */
@Component
public class UsosEnPacientes implements UsosDeCatalogo {

    private static final Map<Class<? extends Catalogo>, String> CONSULTAS = Map.of(
            TipoDocumento.class, "where p.tipoDocumento = :codigo",
            Pais.class, "left join p.nacionalidades n"
                    + " where p.paisResidencia = :codigo or n.codigo = :codigo",
            Ocupacion.class, "where p.ocupacion = :codigo",
            CategoriaDiscapacidad.class, "join p.discapacidades d where d.codigo = :codigo",
            Municipio.class, "where p.municipioResidencia = :codigo",
            Etnia.class, "where p.etnia = :codigo",
            ComunidadEtnica.class, "where p.comunidadEtnica = :codigo",
            EntidadPlanBeneficios.class, "where p.entidadPlanBeneficios = :codigo");

    @PersistenceContext
    private EntityManager base;

    @Override
    @Transactional(readOnly = true)
    public long contar(Class<? extends Catalogo> catalogo, String codigo) {
        String donde = CONSULTAS.get(catalogo);
        if (donde == null || codigo == null) {
            return 0;
        }
        return base.createQuery("select count(distinct p) from Paciente p " + donde, Long.class)
                .setParameter("codigo", codigo).getSingleResult();
    }
}
