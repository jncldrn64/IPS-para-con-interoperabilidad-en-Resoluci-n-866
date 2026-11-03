package com.ipssaludyvida.comun;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.TreeSet;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 * Todas las llaves foráneas de la base, como dibuja el modelo E-R (docs/context/modelo-datos/),
 * leídas de la base misma. Las del paciente las prueba además hu004/LlavesDelPacienteTest. Solo
 * historial_cambio.registro_id queda sin llave, porque apunta a filas de tablas distintas. No es
 * el escenario de una historia (docs/DESIGN.md, sección 3).
 */
@SpringBootTest
@ActiveProfiles("test")
class LlavesDeLaBaseTest {

    @Autowired DataSource base;

    @Test
    void laBaseTieneLasLlavesForaneasDelModelo() {
        Set<String> llaves = new TreeSet<>(new JdbcTemplate(base).queryForList("""
                SELECT k.TABLE_NAME || '.' || k.COLUMN_NAME || ' -> ' || u.TABLE_NAME
                FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS r
                JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE k
                  ON k.CONSTRAINT_NAME = r.CONSTRAINT_NAME
                JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS u
                  ON u.CONSTRAINT_NAME = r.UNIQUE_CONSTRAINT_NAME
                """, String.class));
        Set<String> esperadas = new TreeSet<>(Set.of(
                "PACIENTE.TIPO_DOCUMENTO -> TIPO_DOCUMENTO",
                "PACIENTE.OCUPACION -> OCUPACION",
                "PACIENTE.PAIS_RESIDENCIA -> PAIS",
                "PACIENTE.MUNICIPIO_RESIDENCIA -> MUNICIPIO",
                "PACIENTE.ETNIA -> ETNIA",
                "PACIENTE.COMUNIDAD_ETNICA -> COMUNIDAD_ETNICA",
                "PACIENTE.ENTIDAD_PLAN_BENEFICIOS -> ENTIDAD_PLAN_BENEFICIOS",
                "PACIENTE.REGISTRADO_POR -> USUARIO",
                "PACIENTE_NACIONALIDAD.PACIENTE_ID -> PACIENTE",
                "PACIENTE_NACIONALIDAD.PAIS -> PAIS",
                "PACIENTE_DISCAPACIDAD.PACIENTE_ID -> PACIENTE",
                "PACIENTE_DISCAPACIDAD.CATEGORIA -> CATEGORIA_DISCAPACIDAD",
                "PAIS.VERSION_ID -> CATALOGO_VERSION",
                "OCUPACION.VERSION_ID -> CATALOGO_VERSION",
                "MUNICIPIO.VERSION_ID -> CATALOGO_VERSION",
                "TIPO_DOCUMENTO.VERSION_ID -> CATALOGO_VERSION",
                "CATEGORIA_DISCAPACIDAD.VERSION_ID -> CATALOGO_VERSION",
                "ETNIA.VERSION_ID -> CATALOGO_VERSION",
                "COMUNIDAD_ETNICA.VERSION_ID -> CATALOGO_VERSION",
                "ENTIDAD_PLAN_BENEFICIOS.VERSION_ID -> CATALOGO_VERSION",
                "DEPARTAMENTO.VERSION_ID -> CATALOGO_VERSION",
                "MUNICIPIO.DEPARTAMENTO -> DEPARTAMENTO",
                "CATALOGO_CAMBIO.VERSION_ID -> CATALOGO_VERSION",
                "CATALOGO_CAMBIO.USUARIO_ID -> USUARIO",
                "CATALOGO_VERSION.USUARIO_ID -> USUARIO",
                "HISTORIAL_CAMBIO.USUARIO_ID -> USUARIO"));
        assertEquals(26, esperadas.size());
        assertEquals(esperadas, llaves);
    }
}
