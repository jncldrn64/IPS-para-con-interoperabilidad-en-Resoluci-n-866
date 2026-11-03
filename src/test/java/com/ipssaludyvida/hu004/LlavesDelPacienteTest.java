package com.ipssaludyvida.hu004;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ipssaludyvida.hu004.interfaces.ContratoPacientes;
import com.ipssaludyvida.hu004.servicios.DatosPaciente;
import com.ipssaludyvida.hu018.interfaces.ContratoPaises;
import com.ipssaludyvida.hu018.modelos.Pais;
import com.ipssaludyvida.hu022.interfaces.ContratoOcupaciones;
import com.ipssaludyvida.hu022.modelos.Ocupacion;
import com.ipssaludyvida.hu026.interfaces.ContratoMunicipios;
import com.ipssaludyvida.hu026.modelos.Departamento;
import com.ipssaludyvida.hu026.modelos.Municipio;
import com.ipssaludyvida.hu026.repositorios.DepartamentoRepositorio;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu034.modelos.TipoDocumento;
import com.ipssaludyvida.hu035.interfaces.ContratoCategoriasDiscapacidad;
import com.ipssaludyvida.hu035.modelos.CategoriaDiscapacidad;
import com.ipssaludyvida.hu036.interfaces.ContratoEtnias;
import com.ipssaludyvida.hu036.modelos.Etnia;
import com.ipssaludyvida.hu037.interfaces.ContratoComunidadesEtnicas;
import com.ipssaludyvida.hu037.modelos.ComunidadEtnica;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import com.ipssaludyvida.hu038.modelos.EntidadPlanBeneficios;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Las llaves foráneas del paciente hacia sus catálogos y hacia quien lo registró, como dibuja el
 * modelo E-R (docs/context/modelo-datos/), leídas de la base misma, y cuántos pacientes usan un
 * valor de cada catálogo (`contarUsos`). No es el escenario de una historia, aunque el conteo es
 * el que piden los escenarios de consulta e inactivación de cada catálogo (docs/DESIGN.md,
 * sección 3).
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LlavesDelPacienteTest {

    static final String REFERENCIA = "Resolución de prueba";

    @Autowired DataSource base;
    @Autowired ContratoPacientes pacientes;
    @Autowired ContratoPaises paises;
    @Autowired ContratoTiposDocumento tipos;
    @Autowired ContratoOcupaciones ocupaciones;
    @Autowired ContratoCategoriasDiscapacidad discapacidades;
    @Autowired ContratoMunicipios municipios;
    @Autowired DepartamentoRepositorio departamentos;
    @Autowired ContratoEtnias etnias;
    @Autowired ContratoComunidadesEtnicas comunidades;
    @Autowired ContratoEntidadesPlanBeneficios entidades;

    @Test
    void elPacienteTieneUnaLlaveForaneaPorCadaCatalogoYPorQuienLoRegistro() {
        Set<String> llaves = new TreeSet<>(new JdbcTemplate(base).queryForList("""
                SELECT k.TABLE_NAME || '.' || k.COLUMN_NAME || ' -> ' || u.TABLE_NAME
                FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS r
                JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE k
                  ON k.CONSTRAINT_NAME = r.CONSTRAINT_NAME
                JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS u
                  ON u.CONSTRAINT_NAME = r.UNIQUE_CONSTRAINT_NAME
                WHERE k.TABLE_NAME LIKE 'PACIENTE%'
                """, String.class));
        assertEquals(new TreeSet<>(Set.of(
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
                "PACIENTE_DISCAPACIDAD.CATEGORIA -> CATEGORIA_DISCAPACIDAD")), llaves);
    }

    @Test
    void laBaseRechazaUnCodigoQueNoEstaEnSuCatalogo() {
        JdbcTemplate jdbc = new JdbcTemplate(base);
        assertThrows(DataIntegrityViolationException.class, () -> jdbc.update(
                "INSERT INTO PACIENTE_DISCAPACIDAD (PACIENTE_ID, CATEGORIA) VALUES (999, '77')"));
    }

    @Test
    void cadaCatalogoCuentaLosPacientesQueUsanUnValor() {
        paises.crear(new Pais("170", "COLOMBIA"), REFERENCIA);
        paises.crear(new Pais("862", "VENEZUELA"), REFERENCIA);
        tipos.crear(new TipoDocumento("CC", "Cédula ciudadanía"), REFERENCIA);
        tipos.crear(new TipoDocumento("PA", "Pasaporte"), REFERENCIA);
        ocupaciones.crear(new Ocupacion("2", "PROFESIONALES"), REFERENCIA);
        ocupaciones.crear(new Ocupacion("22", "PROFESIONALES DE LA SALUD"), REFERENCIA);
        ocupaciones.crear(new Ocupacion("222", "Médicos y afines"), REFERENCIA);
        ocupaciones.crear(new Ocupacion("2221", "Médicos"), REFERENCIA);
        discapacidades.crear(new CategoriaDiscapacidad("01", "Discapacidad física"), REFERENCIA);
        discapacidades.crear(new CategoriaDiscapacidad("02", "Discapacidad visual"), REFERENCIA);
        departamentos.save(new Departamento("05", "ANTIOQUIA"));
        municipios.crear(new Municipio("05001", "MEDELLÍN"), REFERENCIA);
        municipios.crear(new Municipio("05002", "ABEJORRAL"), REFERENCIA);
        etnias.crear(new Etnia("01", "Indígena"), REFERENCIA);
        etnias.crear(new Etnia("99", "Ninguna de las anteriores"), REFERENCIA);
        comunidades.crear(new ComunidadEtnica("010", "ACHAGUA"), REFERENCIA);
        comunidades.crear(new ComunidadEtnica("020", "AMORÚA"), REFERENCIA);
        entidades.crear(new EntidadPlanBeneficios("EPS037", "Entidad de prueba"), REFERENCIA);
        entidades.crear(new EntidadPlanBeneficios("05001", "Dirección de salud"), REFERENCIA);
        DatosPaciente d = new DatosPaciente();
        // Colombia como nacionalidad y como residencia: el país cuenta un paciente, no dos.
        d.setNacionalidades(List.of("170", "862"));
        d.setTipoDocumento("CC");
        d.setNumeroDocumento("1020304050");
        d.setPrimerApellido("PÉREZ");
        d.setPrimerNombre("ANA");
        d.setFechaNacimiento("1990-05-17");
        d.setSexoBiologico("02");
        d.setIdentidadGenero("02");
        d.setOcupacion("2221");
        d.setDiscapacidades(List.of("01", "02"));
        d.setPaisResidencia("170");
        d.setMunicipioResidencia("05001");
        d.setEtnia("01");
        d.setComunidadEtnica("010");
        d.setZonaTerritorial("01");
        d.setEntidadPlanBeneficios("EPS037");
        pacientes.registrar(d);

        assertEquals(1, paises.contarUsos("170"));
        assertEquals(1, paises.contarUsos("862"));
        assertEquals(1, tipos.contarUsos("CC"));
        assertEquals(0, tipos.contarUsos("PA"));
        assertEquals(1, ocupaciones.contarUsos("2221"));
        assertEquals(0, ocupaciones.contarUsos("222"));
        assertEquals(1, discapacidades.contarUsos("01"));
        assertEquals(1, discapacidades.contarUsos("02"));
        assertEquals(1, municipios.contarUsos("05001"));
        assertEquals(0, municipios.contarUsos("05002"));
        assertEquals(1, etnias.contarUsos("01"));
        assertEquals(0, etnias.contarUsos("99"));
        assertEquals(1, comunidades.contarUsos("010"));
        assertEquals(0, comunidades.contarUsos("020"));
        assertEquals(1, entidades.contarUsos("EPS037"));
        // El mismo código en otro catálogo no cuenta: 05001 es un municipio y una entidad.
        assertEquals(0, entidades.contarUsos("05001"));
    }
}
