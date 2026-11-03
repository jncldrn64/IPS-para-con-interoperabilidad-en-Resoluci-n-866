package com.ipssaludyvida.hu038.servicios;

import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.comun.servicios.RegistroCatalogos;
import com.ipssaludyvida.comun.servicios.ServicioCatalogo;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import com.ipssaludyvida.hu038.modelos.EntidadPlanBeneficios;
import com.ipssaludyvida.hu038.repositorios.EntidadPlanBeneficiosRepositorio;
import jakarta.validation.Validator;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class EntidadPlanBeneficiosServicio extends ServicioCatalogo<EntidadPlanBeneficios>
        implements ContratoEntidadesPlanBeneficios {

    public EntidadPlanBeneficiosServicio(EntidadPlanBeneficiosRepositorio datos,
            Validator validador, RegistroCatalogos registro) {
        super(EntidadPlanBeneficios.class, datos, validador, registro);
    }

    @Override
    public Comprobacion comprobarContraElArchivo(Path archivo) {
        return comprobarContra(leerListaOficial(archivo));
    }
}
