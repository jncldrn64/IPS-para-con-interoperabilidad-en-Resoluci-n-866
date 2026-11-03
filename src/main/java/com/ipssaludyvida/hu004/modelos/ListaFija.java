package com.ipssaludyvida.hu004.modelos;

/**
 * Un valor de una lista del anexo de 5 opciones o menos, que no es catálogo ([2.3]): tiene su
 * código de 2 dígitos y su nombre, y el paciente guarda el código (D-18).
 */
public interface ListaFija {

    String codigo();

    String texto();

    /** El valor de la lista con ese código, o null si no está. */
    static <E extends Enum<E> & ListaFija> E desde(Class<E> lista, String codigo) {
        for (E valor : lista.getEnumConstants()) {
            if (valor.codigo().equals(codigo)) {
                return valor;
            }
        }
        return null;
    }
}
