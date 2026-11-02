package com.ipssaludyvida.hu026.repositorios;

import com.ipssaludyvida.hu026.modelos.Departamento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

/** Los departamentos. Busca y guarda, y no borra (docs/DESIGN.md, sección 6). */
public interface DepartamentoRepositorio extends Repository<Departamento, String> {

    List<Departamento> findAllByOrderByCodigoAsc();

    Optional<Departamento> findById(String codigo);

    Departamento save(Departamento departamento);
}
