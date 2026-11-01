package com.ipssaludyvida.hu001.interfaces;

import com.ipssaludyvida.hu001.modelos.Permiso;
import com.ipssaludyvida.hu001.modelos.Usuario;
import java.util.Optional;

/**
 * El contrato de HU-001, inicio de sesión con permisos por cargo. Lo implementa SesionServicio y lo
 * usan el menú principal, los controladores y la tabla de cambios de los catálogos.
 *
 * <p>Escenarios de docs/context/trello/tarjetas/HU-001.md y la operación que los cumple: 1, 2 y 3,
 * iniciar; 4, abierta, en el menú y en cada controlador; 5, 6 y 7, puede, con la matriz de Cargo; 8
 * y 14, exigir; 9, actual, que lee RegistroCatalogos; 10, seguir; 11, cerrar; 12, seguir; 13, la
 * pantalla de SesionControlador, que no ofrece recuperar la clave.
 */
public interface ContratoSesion {

    /** El mensaje de los escenarios 8 y 14. */
    String SIN_PERMISO = "su cargo no tiene ese permiso";

    /** El mensaje del escenario 4. */
    String SIN_SESION = "inicie sesión para usar el sistema";

    /**
     * Abre la sesión. Si falta un dato dice cuál (escenario 3); si el usuario o la clave no
     * coinciden, no dice cuál de los dos falló (escenario 2).
     */
    Usuario iniciar(String usuario, String clave);

    void cerrar();

    boolean abierta();

    Optional<Usuario> actual();

    /**
     * Anota una acción. Devuelve false, y cierra la sesión, si pasaron 15 minutos sin actividad
     * (escenario 12, D-06).
     */
    boolean seguir();

    /** Si el cargo de quien tiene la sesión tiene ese permiso. Sin sesión, ninguno. */
    boolean puede(Permiso permiso);

    /** Lanza IllegalStateException con SIN_SESION o SIN_PERMISO si no puede. */
    void exigir(Permiso permiso);
}
