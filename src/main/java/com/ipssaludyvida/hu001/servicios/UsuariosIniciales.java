package com.ipssaludyvida.hu001.servicios;

import com.ipssaludyvida.hu001.modelos.Cargo;
import com.ipssaludyvida.hu001.modelos.Usuario;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Lo que hace el equipo técnico al instalar: si la base no tiene usuarios, crea uno de prueba por
 * cargo, para que alguien pueda entrar (precondición de HU-001). Corre antes del menú principal.
 * Con usuarios en la base no hace nada, así que no pisa una clave cambiada.
 */
@Component
// Sin esto, cada @SpringBootTest crea los tres usuarios y un test no arranca con la base vacía.
@Profile("!test")
@Order(0)
public class UsuariosIniciales implements CommandLineRunner {

    /** La clave de los tres usuarios de prueba. No sirve para una instalación de verdad. */
    public static final String CLAVE_DE_PRUEBA = "Clave123";

    private final UsuarioRepositorio usuarios;
    private final SesionServicio sesion;

    public UsuariosIniciales(UsuarioRepositorio usuarios, SesionServicio sesion) {
        this.usuarios = usuarios;
        this.sesion = sesion;
    }

    @Override
    public void run(String... args) {
        if (usuarios.count() > 0) {
            return;
        }
        String clave = sesion.cifrar(CLAVE_DE_PRUEBA);
        List.of(new Usuario("1001", "Auxiliar de prueba", "admision", clave,
                        Cargo.AUXILIAR_DE_ADMISION),
                new Usuario("1002", "Médico de prueba", "medico", clave, Cargo.MEDICO_TRATANTE),
                new Usuario("1003", "Estadística de prueba", "estadistica", clave,
                        Cargo.PROFESIONAL_DE_ESTADISTICA))
                .forEach(usuarios::save);
    }
}
