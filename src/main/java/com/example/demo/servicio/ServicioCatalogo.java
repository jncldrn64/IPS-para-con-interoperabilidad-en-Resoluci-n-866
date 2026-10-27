package com.example.demo.servicio;

import com.example.demo.modelo.Catalogo;
import com.example.demo.repositorio.CatalogoRepositorio;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/** La base común de los catálogos: la lógica que comparten los 8 (docs/DESIGN.md, sección 7). */
public abstract class ServicioCatalogo<T extends Catalogo> implements CrudCatalogo<T> {

    private final CatalogoRepositorio<T> datos;
    private final Validator validador;

    protected ServicioCatalogo(CatalogoRepositorio<T> datos, Validator validador) {
        this.datos = datos;
        this.validador = validador;
    }

    @Override
    public List<T> listar() {
        return datos.findAllByOrderByCodigoAsc();
    }

    @Override
    public Optional<T> buscarPorCodigo(String codigo) {
        return datos.findById(codigo);
    }

    // Valida aquí y no solo al persistir: al persistir, el error llega envuelto en una excepción de
    // la transacción y el menú no puede mostrar el motivo.
    @Override
    public T guardar(T valor) {
        Set<ConstraintViolation<T>> errores = validador.validate(valor);
        if (!errores.isEmpty()) {
            throw new ConstraintViolationException(errores);
        }
        // Sin esto, cambiar el nombre de un código inactivo lo reactiva y le borra la versión: el
        // valor nuevo trae ACTIVO y versionId vacío, y save los copia sobre el guardado.
        datos.findById(valor.getCodigo()).ifPresent(previo -> {
            valor.setEstado(previo.getEstado());
            valor.setVersionId(previo.getVersionId());
        });
        return datos.save(valor);
    }

    @Override
    public void inactivar(String codigo) {
        T valor = datos.findById(codigo)
                .orElseThrow(() -> new IllegalArgumentException("no existe el código " + codigo));
        if (valor.getEstado() == Catalogo.Estado.INACTIVO) {
            throw new IllegalStateException("el código " + codigo + " ya está inactivo");
        }
        valor.setEstado(Catalogo.Estado.INACTIVO);
        datos.save(valor);
    }
}
