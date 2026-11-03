package com.ipssaludyvida.comun.repositorios;

import com.ipssaludyvida.comun.modelos.HistorialCambio;
import java.util.List;
import org.springframework.data.repository.Repository;

/** El historial de cambios de los registros. Solo agrega y busca: un cambio no se borra (RC-6). */
public interface HistorialCambioRepositorio extends Repository<HistorialCambio, Long> {

    List<HistorialCambio> findByTablaAndRegistroIdOrderByIdAsc(String tabla, Long registroId);

    HistorialCambio save(HistorialCambio cambio);
}
