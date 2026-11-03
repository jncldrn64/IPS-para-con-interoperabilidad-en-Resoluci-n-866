package com.ipssaludyvida.hu004.repositorios;

import com.ipssaludyvida.hu004.modelos.Paciente;
import java.util.Optional;
import org.springframework.data.repository.Repository;

/** Los pacientes. Busca y guarda, y no borra (docs/DESIGN.md, sección 6). */
public interface PacienteRepositorio extends Repository<Paciente, Long> {

    Optional<Paciente> findByTipoDocumentoAndNumeroDocumento(String tipoDocumento,
            String numeroDocumento);

    long count();

    Paciente save(Paciente paciente);
}
