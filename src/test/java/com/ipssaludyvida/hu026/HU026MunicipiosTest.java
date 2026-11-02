package com.ipssaludyvida.hu026;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.modelos.Catalogo;
import com.ipssaludyvida.comun.modelos.CatalogoCambio;
import com.ipssaludyvida.comun.modelos.CatalogoVersion;
import com.ipssaludyvida.comun.modelos.Motivo;
import com.ipssaludyvida.comun.servicios.Comprobacion;
import com.ipssaludyvida.comun.servicios.Consulta;
import com.ipssaludyvida.comun.servicios.DatosVersion;
import com.ipssaludyvida.comun.servicios.ResultadoCarga;
import com.ipssaludyvida.comun.servicios.ResumenVersion;
import com.ipssaludyvida.hu001.Usuarios;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Cargo;
import com.ipssaludyvida.hu001.repositorios.UsuarioRepositorio;
import com.ipssaludyvida.hu026.controladores.MunicipioControlador;
import com.ipssaludyvida.hu026.interfaces.ContratoMunicipios;
import com.ipssaludyvida.hu026.modelos.Municipio;
import com.ipssaludyvida.hu026.repositorios.DepartamentoRepositorio;
import jakarta.validation.ConstraintViolationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Los 25 escenarios de HU-026 (docs/context/trello/tarjetas/HU-026.md). El archivo oficial de la
 * DIVIPOLA no se ha descargado, así que los tests usan un archivo de prueba con su forma: 9
 * municipios de 8 departamentos, con Albania en tres de ellos.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU026MunicipiosTest {

    static final String REFERENCIA = "Resolución de prueba";
    static final String ENCABEZADO = "codigo;nombre;codigo_departamento;nombre_departamento\n";
    static final String FILAS = """
            05001;MEDELLÍN;05;ANTIOQUIA
            05002;ABEJORRAL;05;ANTIOQUIA
            08001;BARRANQUILLA;08;ATLÁNTICO
            11001;BOGOTÁ, D.C.;11;BOGOTÁ, D.C.
            13001;CARTAGENA DE INDIAS;13;BOLÍVAR
            18029;ALBANIA;18;CAQUETÁ
            44035;ALBANIA;44;LA GUAJIRA
            68013;ALBANIA;68;SANTANDER
            76001;CALI;76;VALLE DEL CAUCA
            """;
    static final DatosVersion DIVIPOLA = new DatosVersion("DIVIPOLA, DANE",
            "https://www.dane.gov.co/", LocalDate.of(2026, 10, 31), "2025",
            LocalDate.of(2025, 6, 1));
    static final DatosVersion VERSION_NUEVA = new DatosVersion("DIVIPOLA, DANE",
            "https://www.dane.gov.co/", LocalDate.of(2026, 10, 31), "2026",
            LocalDate.of(2026, 3, 1));

    @Autowired ContratoMunicipios municipios;
    @Autowired DepartamentoRepositorio departamentos;
    @Autowired UsuarioRepositorio usuarios;
    @Autowired ContratoSesion sesion;
    @TempDir Path carpeta;

    @Test
    @DisplayName("HU-026 Esc. 1: la primera carga crea cada municipio activo con su versión")
    void esc1_primeraCarga() {
        CatalogoVersion version = cargar(FILAS);
        assertEquals(9, municipios.listarActivos().size());
        Municipio medellin = municipios.buscarPorCodigo("05001").orElseThrow();
        assertEquals(version.getId(), medellin.getVersionId());
        assertEquals("05", medellin.getDepartamento());
    }

    @Test
    @DisplayName("HU-026 Esc. 1: la carga guarda los 8 departamentos con la versión")
    void esc1_guardaLosDepartamentos() {
        CatalogoVersion version = cargar(FILAS);
        assertEquals(8, departamentos.findAllByOrderByCodigoAsc().size());
        assertEquals("ANTIOQUIA", municipios.departamento("05001").orElseThrow().getNombre());
        assertEquals(version.getId(), departamentos.findById("05").orElseThrow().getVersionId());
    }

    @Test
    @DisplayName("HU-026 Esc. 2: un código de 6 caracteres y una fila sin nombre se rechazan")
    void esc2_filasConErrores() {
        ResultadoCarga carga = municipios.leerCarga(csv(FILAS + "050011;SEIS;05;ANTIOQUIA\n"
                + "05004;;05;ANTIOQUIA\n"), DIVIPOLA);
        assertEquals(11, carga.getFilasLeidas());
        assertEquals(9, carga.getAceptadas().size());
        assertEquals(List.of(10, 11), carga.getRechazadas().stream()
                .map(ResultadoCarga.Rechazo::numero).toList());
        assertTrue(carga.getRechazadas().get(0).motivo().contains("5 dígitos"));
        assertTrue(carga.getRechazadas().get(1).motivo().contains("vacío"));
        assertTrue(carga.sePuedeGuardar());
    }

    @Test
    @DisplayName("HU-026 Esc. 2: la lista de rechazadas se descarga con fila y motivo")
    void esc2_descargarLaLista() throws IOException {
        ResultadoCarga carga = municipios.leerCarga(csv(FILAS + "050011;SEIS;05;ANTIOQUIA\n"),
                DIVIPOLA);
        List<String> lineas = Files.readAllLines(
                municipios.escribirRechazadas(carga, carpeta.resolve("rechazadas.csv")));
        assertTrue(lineas.get(1).startsWith("10;050011;"), lineas.get(1));
    }

    @Test
    @DisplayName("HU-026 Esc. 2: guardar las aceptadas y completar con el archivo corregido")
    void esc2_completarConLaRecarga() {
        municipios.confirmarCarga(municipios.leerCarga(csv(FILAS + "05004;;05;ANTIOQUIA\n"),
                DIVIPOLA), Map.of());
        ResultadoCarga recarga = municipios.leerCarga(
                csv(FILAS + "05004;ABRIAQUÍ;05;ANTIOQUIA\n"), DIVIPOLA);
        assertEquals(ResultadoCarga.Tipo.RECARGA, recarga.getTipo());
        assertEquals(List.of("05004"), recarga.getNuevos());
        assertTrue(recarga.getYaNoEstan().isEmpty());
        municipios.confirmarCarga(recarga, Map.of());
        assertEquals(10, municipios.listarActivos().size());
    }

    @Test
    @DisplayName("HU-026 Esc. 3: sin la columna del departamento se rechaza el archivo completo")
    void esc3_faltaLaColumna() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> municipios.leerCarga(csv("codigo;nombre;nombre_departamento\n"
                        + "05001;MEDELLÍN;ANTIOQUIA\n", ""), DIVIPOLA));
        assertEquals("al archivo le falta la columna codigo_departamento", e.getMessage());
        e = assertThrows(IllegalArgumentException.class,
                () -> municipios.leerCarga(csv("codigo;nombre;codigo_departamento\n"
                        + "05001;MEDELLÍN;05\n", ""), DIVIPOLA));
        assertEquals("al archivo le falta la columna nombre_departamento", e.getMessage());
    }

    @Test
    @DisplayName("HU-026 Esc. 3: una fecha futura o anterior a la vigente rechaza el archivo")
    void esc3_fechas() {
        DatosVersion futura = new DatosVersion("DIVIPOLA, DANE", "", LocalDate.of(2026, 10, 31),
                "2099", LocalDate.of(2099, 1, 1));
        assertTrue(assertThrows(IllegalArgumentException.class,
                () -> municipios.leerCarga(csv(FILAS), futura)).getMessage().contains("futura"));
        cargar(FILAS);
        DatosVersion vieja = new DatosVersion("DIVIPOLA, DANE", "", LocalDate.of(2026, 10, 31),
                "2020", LocalDate.of(2020, 1, 1));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> municipios.leerCarga(csv(FILAS), vieja));
        assertTrue(e.getMessage().contains("la versión vigente es 2025, publicada el 2025-06-01"),
                e.getMessage());
    }

    @Test
    @DisplayName("HU-026 Esc. 4: dos filas con el mismo código se rechazan las dos")
    void esc4_codigoRepetido() {
        ResultadoCarga carga = municipios.leerCarga(csv(FILAS + "05001;MEDELLÍN;05;ANTIOQUIA\n"),
                DIVIPOLA);
        assertEquals(List.of("código repetido", "código repetido"), carga.getRechazadas()
                .stream().map(ResultadoCarga.Rechazo::motivo).toList());
    }

    @Test
    @DisplayName("HU-026 Esc. 5: un código sin el cero inicial se rechaza y no se completa")
    void esc5_sinElCeroInicial() {
        ResultadoCarga carga = municipios.leerCarga(csv(
                "5001;MEDELLÍN;5;ANTIOQUIA\n11001;BOGOTÁ, D.C.;11;BOGOTÁ, D.C.\n", ENCABEZADO),
                DIVIPOLA);
        assertTrue(motivo(carga, "5001").contains(
                "revise que el archivo no haya perdido los ceros iniciales"),
                motivo(carga, "5001"));
        municipios.confirmarCarga(carga, Map.of());
        assertTrue(municipios.buscarPorCodigo("05001").isEmpty());
        assertTrue(municipios.buscarPorCodigo("5001").isEmpty());
    }

    @Test
    @DisplayName("HU-026 Esc. 6: un código que no empieza por su departamento se rechaza")
    void esc6_noCorresponde() {
        ResultadoCarga carga = municipios.leerCarga(csv(FILAS + "05004;ABRIAQUÍ;08;ATLÁNTICO\n"),
                DIVIPOLA);
        assertEquals("el código no corresponde al departamento", motivo(carga, "05004"));
    }

    @Test
    @DisplayName("HU-026 Esc. 6: un departamento con otro nombre en una fila se rechaza")
    void esc6_dosNombres() {
        ResultadoCarga carga = municipios.leerCarga(csv(FILAS + "05004;ABRIAQUÍ;05;ANTIOQIA\n"),
                DIVIPOLA);
        assertEquals("el departamento tiene dos nombres", motivo(carga, "05004"));
        assertEquals("aceptada", motivo(carga, "05001"));
    }

    @Test
    @DisplayName("HU-026 Esc. 7: la versión nueva muestra los cambios y los aplica con su motivo")
    void esc7_versionNueva() {
        cargar(FILAS);
        municipios.inactivar("05002", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        String nueva = FILAS.replace("76001;CALI;76;VALLE DEL CAUCA\n",
                "76001;SANTIAGO DE CALI;76;VALLE DEL CAUCA\n76002;MUNICIPIO NUEVO;76;VALLE DEL"
                        + " CAUCA\n").replace("13001;CARTAGENA DE INDIAS;13;BOLÍVAR\n", "")
                .replace("08001;BARRANQUILLA;08;ATLÁNTICO\n", "")
                .replace("ANTIOQUIA", "ANTIOQUIA (NOMBRE NUEVO)");
        ResultadoCarga carga = municipios.leerCarga(csv(nueva), VERSION_NUEVA);
        assertEquals(List.of("76002"), carga.getNuevos());
        assertEquals("76001", carga.getCambianDeNombre().get(0).codigo());
        assertEquals(List.of("05002"), carga.getVuelven());
        assertEquals(List.of("08001", "13001"), carga.getYaNoEstan());
        assertTrue(carga.getAvisos().stream().anyMatch(a -> a.contains("deshace")
                && a.contains("05002")), carga.getAvisos().toString());
        assertTrue(carga.getAvisos().contains("el departamento 05 cambia de nombre, de"
                + " \"ANTIOQUIA\" a \"ANTIOQUIA (NOMBRE NUEVO)\""), carga.getAvisos().toString());
        CatalogoVersion version = municipios.confirmarCarga(carga, Map.of("13001", "76002"));
        assertEquals(Motivo.VUELVE_EN_LA_VERSION, ultimo("05002").getMotivo());
        assertEquals(Motivo.REEMPLAZADO_POR, ultimo("13001").getMotivo());
        assertEquals("76002", ultimo("13001").getCodigoReemplazo());
        assertEquals(Motivo.NO_ESTA_EN_LA_VERSION, ultimo("08001").getMotivo());
        assertEquals("SANTIAGO DE CALI", municipios.buscarPorCodigo("76001").orElseThrow()
                .getNombre());
        assertEquals("ANTIOQUIA (NOMBRE NUEVO)",
                municipios.departamento("05001").orElseThrow().getNombre());
        assertEquals(version.getId(), municipios.versionVigente().orElseThrow().getId());
    }

    @Test
    @DisplayName("HU-026 Esc. 8: un código existente rechazado conserva su nombre y su estado")
    void esc8_filaRechazadaConservaElCodigo() {
        cargar(FILAS);
        ResultadoCarga carga = municipios.leerCarga(csv(FILAS.replace("76001;CALI;", "76001;;")),
                VERSION_NUEVA);
        assertFalse(carga.getYaNoEstan().contains("76001"));
        assertTrue(motivo(carga, "76001").contains("vacío"));
        municipios.confirmarCarga(carga, Map.of());
        Municipio cali = municipios.buscarPorCodigo("76001").orElseThrow();
        assertEquals("CALI", cali.getNombre());
        assertEquals(Catalogo.Estado.ACTIVO, cali.getEstado());
    }

    @Test
    @DisplayName("HU-026 Esc. 9: un cambio oficial de código inactiva el viejo y crea el nuevo")
    void esc9_cambioDeCodigo() {
        cargar(FILAS);
        municipios.cambiarCodigo("05002", new Municipio("05003", "ABEJORRAL"), REFERENCIA);
        assertEquals(Catalogo.Estado.INACTIVO,
                municipios.buscarPorCodigo("05002").orElseThrow().getEstado());
        assertEquals(Motivo.REEMPLAZADO_POR, ultimo("05002").getMotivo());
        assertEquals("05003", ultimo("05002").getCodigoReemplazo());
        assertEquals(Catalogo.Estado.ACTIVO,
                municipios.buscarPorCodigo("05003").orElseThrow().getEstado());
    }

    @Test
    @DisplayName("HU-026 Esc. 10: un municipio creado por cambio oficial toma su departamento")
    void esc10_creacion() {
        cargar(FILAS);
        Municipio nuevo = municipios.crear(new Municipio("05004", "ABRIAQUÍ"), REFERENCIA);
        assertEquals(Catalogo.Estado.ACTIVO, nuevo.getEstado());
        assertEquals("05", nuevo.getDepartamento());
        assertEquals("ANTIOQUIA", municipios.departamento("05004").orElseThrow().getNombre());
        assertEquals(REFERENCIA, municipios.historial("05004").get(0).getReferenciaOficial());
    }

    @Test
    @DisplayName("HU-026 Esc. 11: sin referencia, repetido o sin departamento no se crea")
    void esc11_creacionRechazada() {
        cargar(FILAS);
        assertEquals("falta la referencia oficial", assertThrows(IllegalArgumentException.class,
                () -> municipios.crear(new Municipio("05004", "ABRIAQUÍ"), "")).getMessage());
        assertThrows(IllegalStateException.class,
                () -> municipios.crear(new Municipio("05001", "OTRO"), REFERENCIA));
        assertEquals("los dos primeros dígitos, 99, no son de un departamento del catálogo",
                assertThrows(IllegalStateException.class, () -> municipios.crear(
                        new Municipio("99001", "SIN DEPARTAMENTO"), REFERENCIA)).getMessage());
        assertTrue(municipios.buscarPorCodigo("05004").isEmpty());
        assertTrue(municipios.buscarPorCodigo("99001").isEmpty());
    }

    @Test
    @DisplayName("HU-026 Esc. 11: un código de 4 dígitos se rechaza con el aviso de los ceros")
    void esc11_cuatroDigitos() {
        cargar(FILAS);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> municipios.crear(new Municipio("5004", "ABRIAQUÍ"), REFERENCIA));
        assertTrue(e.getMessage().contains("revise que no haya perdido los ceros iniciales"));
        assertThrows(IllegalArgumentException.class, () -> municipios.cambiarCodigo("05002",
                new Municipio("5003", "ABEJORRAL"), REFERENCIA));
        assertEquals(Catalogo.Estado.ACTIVO,
                municipios.buscarPorCodigo("05002").orElseThrow().getEstado());
    }

    @Test
    @DisplayName("HU-026 Esc. 11: un dato que no cumple el numeral 12 no se guarda")
    void esc11_numeral() {
        cargar(FILAS);
        assertThrows(ConstraintViolationException.class,
                () -> municipios.crear(new Municipio("05A04", "ABRIAQUÍ"), REFERENCIA));
        assertThrows(ConstraintViolationException.class,
                () -> municipios.crear(new Municipio("05004", "AB"), REFERENCIA));
        assertThrows(ConstraintViolationException.class,
                () -> municipios.cambiarNombre("05001", "", REFERENCIA));
    }

    @Test
    @DisplayName("HU-026 Esc. 12: un cambio oficial de nombre deja el anterior en el historial")
    void esc12_cambioDeNombre() {
        cargar(FILAS);
        municipios.cambiarNombre("76001", "SANTIAGO DE CALI", REFERENCIA);
        assertEquals("CALI", ultimo("76001").getValorAnterior());
        assertEquals("SANTIAGO DE CALI", ultimo("76001").getValorNuevo());
        assertEquals("SANTIAGO DE CALI",
                municipios.buscarPorCodigo("76001").orElseThrow().getNombre());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-026 Esc. 13: cancelar una creación o una inactivación deja todo igual")
    void esc13_cancelar() {
        cargar(FILAS);
        menu("2\n05004\nABRIAQUÍ\n" + REFERENCIA + "\nn\n5\n05001\n1\n" + REFERENCIA
                + "\nn\n0\n");
        assertTrue(municipios.buscarPorCodigo("05004").isEmpty());
        assertEquals(Catalogo.Estado.ACTIVO,
                municipios.buscarPorCodigo("05001").orElseThrow().getEstado());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-026 Esc. 13: cancelar una carga no guarda nada")
    void esc13_cancelarCarga() {
        String salida = menu("8\n" + csv(FILAS) + "\nDIVIPOLA, DANE\n\n2026-10-31\n2025\n"
                + "2025-06-01\nn\n0\n");
        assertTrue(salida.contains("código;nombre;codigo_departamento;nombre_departamento"),
                salida);
        assertTrue(municipios.listar().isEmpty());
        assertTrue(departamentos.findAllByOrderByCodigoAsc().isEmpty());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-026 Esc. 14: la consulta muestra cada municipio con su departamento")
    void esc14_consulta() {
        cargar(FILAS);
        municipios.inactivar("05002", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        String salida = menu("1\n3\n0\n");
        assertTrue(salida.contains("05001  MEDELLÍN  ACTIVO  ANTIOQUIA (05)\n"), salida);
        assertTrue(salida.contains("05002  ABEJORRAL  INACTIVO  ANTIOQUIA (05)\n"), salida);
        assertTrue(salida.contains("Activos: 8."), salida);
        assertTrue(salida.contains("Versión vigente: 2025, publicada el 2025-06-01"), salida);
        Consulta<Municipio> inactivos = municipios.consultar(CrudCatalogo.Filtro.INACTIVOS);
        assertEquals(List.of("05002"), codigos(inactivos.valores()));
    }

    @Test
    @DisplayName("HU-026 Esc. 14: el historial de versiones dice cuántos creó cada una")
    void esc14_historialDeVersiones() {
        cargar(FILAS);
        List<ResumenVersion> versiones = municipios.historialDeVersiones();
        assertEquals(1, versiones.size());
        assertEquals(9, versiones.get(0).creados());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-026 Esc. 15: los municipios con el mismo nombre salen con su departamento")
    void esc15_mismoNombre() {
        cargar(FILAS);
        assertEquals(List.of("18029", "44035", "68013"), codigos(municipios.buscar("Albania")));
        String salida = menu("9\nalbania\n0\n");
        assertTrue(salida.contains("18029  ALBANIA  ACTIVO  CAQUETÁ (18)\n"), salida);
        assertTrue(salida.contains("44035  ALBANIA  ACTIVO  LA GUAJIRA (44)\n"), salida);
        assertTrue(salida.contains("68013  ALBANIA  ACTIVO  SANTANDER (68)\n"), salida);
    }

    @Test
    @DisplayName("HU-026 Esc. 16: la búsqueda por código devuelve solo ese código")
    void esc16_porCodigo() {
        cargar(FILAS);
        assertEquals(List.of("05001"), codigos(municipios.buscar("05001")));
    }

    @Test
    @DisplayName("HU-026 Esc. 17: la búsqueda por nombre ignora mayúsculas y tildes")
    void esc17_porNombre() {
        cargar(FILAS);
        assertEquals(List.of("11001"), codigos(municipios.buscar("bogota")));
        // ABEJORRAL, las tres ALBANIA y CALI tienen "al"; salen ordenados por nombre.
        assertEquals(List.of("05002", "18029", "44035", "68013", "76001"),
                codigos(municipios.buscar("AL")));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-026 Esc. 18: una búsqueda sin resultados lo dice")
    void esc18_sinResultados() {
        cargar(FILAS);
        assertTrue(menu("9\nmacondo\n0\n").contains(
                "No hay municipios que coincidan con la búsqueda"));
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-026 Esc. 19: el catálogo vacío dice que falta cargar el archivo oficial")
    void esc19_vacio() {
        assertTrue(menu("1\n3\n0\n").contains(
                "El catálogo de municipios está vacío: falta cargar el archivo oficial"));
    }

    @Test
    @DisplayName("HU-026 Esc. 20: la comprobación ve también el nombre del departamento")
    void esc20_comprobacion() {
        cargar(FILAS);
        municipios.inactivar("05002", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        String oficial = FILAS.replace("08001;BARRANQUILLA;08;ATLÁNTICO\n", "")
                .replace("76001;CALI;", "76001;SANTIAGO DE CALI;")
                .replace("68013;ALBANIA;68;SANTANDER", "68013;ALBANIA;68;SANTANDER (OTRO)")
                + "05004;ABRIAQUÍ;05;ANTIOQUIA\n";
        Comprobacion c = municipios.comprobarContraElArchivo(csv(oficial));
        assertEquals(List.of("05004"), c.faltan());
        assertEquals(List.of("05002"), c.inactivos());
        assertEquals(List.of("08001"), c.sobran());
        assertEquals(List.of("68013", "76001"), c.nombresDistintos());
        assertEquals("CALI", municipios.buscarPorCodigo("76001").orElseThrow().getNombre());
        assertEquals("SANTANDER", municipios.departamento("68013").orElseThrow().getNombre());
    }

    @Test
    @Timeout(10)
    @DisplayName("HU-026 Esc. 21: inactivar dice cuántos pacientes lo usan y pide confirmar")
    void esc21_inactivacion() {
        cargar(FILAS);
        String salida = menu("5\n05002\n1\n" + REFERENCIA + "\ns\n0\n");
        assertTrue(salida.contains("Inactivar 05002 ABEJORRAL. La usan 0 pacientes: la"
                + " conservan, pero no se ofrece en registros nuevos."), salida);
        assertEquals(Catalogo.Estado.INACTIVO,
                municipios.buscarPorCodigo("05002").orElseThrow().getEstado());
        assertFalse(codigos(municipios.listarActivos()).contains("05002"));
    }

    @Test
    @DisplayName("HU-026 Esc. 22: sin motivo, sin referencia o \"Creado por error\" no inactiva")
    void esc22_sinRespaldo() {
        cargar(FILAS);
        assertEquals("elija un motivo", assertThrows(IllegalArgumentException.class,
                () -> municipios.inactivar("05002", null, REFERENCIA)).getMessage());
        assertThrows(IllegalArgumentException.class,
                () -> municipios.inactivar("05002", Motivo.RETIRADO_POR_LA_FUENTE, ""));
        assertTrue(assertThrows(IllegalArgumentException.class,
                () -> municipios.inactivar("05002", Motivo.CREADO_POR_ERROR, null))
                .getMessage().startsWith("\"Creado por error\" solo vale"));
        assertEquals(Catalogo.Estado.ACTIVO,
                municipios.buscarPorCodigo("05002").orElseThrow().getEstado());
    }

    @Test
    @DisplayName("HU-026 Esc. 23: reactivar con el porqué deja el motivo en el historial")
    void esc23_reactivacion() {
        cargar(FILAS);
        municipios.inactivar("05002", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        municipios.reactivar("05002", "", "La resolución citada no retiraba este municipio");
        assertEquals(Catalogo.Estado.ACTIVO,
                municipios.buscarPorCodigo("05002").orElseThrow().getEstado());
        assertEquals("La resolución citada no retiraba este municipio",
                ultimo("05002").getJustificacion());
    }

    @Test
    @DisplayName("HU-026 Esc. 24: sin respaldo no se reactiva lo que retiró una versión")
    void esc24_retiradoPorUnaVersion() {
        cargar(FILAS);
        municipios.confirmarCarga(municipios.leerCarga(csv(FILAS.replace(
                "08001;BARRANQUILLA;08;ATLÁNTICO\n", "")), VERSION_NUEVA), Map.of());
        assertTrue(assertThrows(IllegalArgumentException.class,
                () -> municipios.reactivar("08001", "", "Porque sí")).getMessage()
                .startsWith("no se puede reactivar"));
    }

    @Test
    @DisplayName("HU-026 Esc. 24: inactivado con referencia, pide otra o el porqué")
    void esc24_inactivadoConReferencia() {
        cargar(FILAS);
        municipios.inactivar("05002", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        assertTrue(assertThrows(IllegalArgumentException.class,
                () -> municipios.reactivar("05002", "", "")).getMessage()
                .startsWith("se inactivó con una referencia oficial"));
    }

    @Test
    @DisplayName("HU-026 Esc. 25: el historial dice con qué versión se creó y cada cambio")
    void esc25_historial() {
        CatalogoVersion version = cargar(FILAS);
        municipios.cambiarNombre("76001", "SANTIAGO DE CALI", REFERENCIA);
        municipios.inactivar("76001", Motivo.RETIRADO_POR_LA_FUENTE, REFERENCIA);
        List<CatalogoCambio> historial = municipios.historial("76001");
        assertEquals(List.of(CatalogoCambio.Tipo.CREADO, CatalogoCambio.Tipo.NOMBRE,
                CatalogoCambio.Tipo.INACTIVADO),
                historial.stream().map(CatalogoCambio::getTipo).toList());
        assertEquals(version.getId(), historial.get(0).getVersionId());
        assertNull(historial.get(0).getReferenciaOficial());
        assertEquals(REFERENCIA, historial.get(1).getReferenciaOficial());
        assertNotNull(historial.get(2).getFechaHora());
    }

    private CatalogoVersion cargar(String filas) {
        ResultadoCarga carga = municipios.leerCarga(csv(filas), DIVIPOLA);
        assertTrue(carga.sePuedeGuardar(), carga.getBloqueos().toString());
        assertTrue(carga.getRechazadas().isEmpty(), carga.getRechazadas().toString());
        return municipios.confirmarCarga(carga, Map.of());
    }

    private CatalogoCambio ultimo(String codigo) {
        List<CatalogoCambio> historial = municipios.historial(codigo);
        return historial.get(historial.size() - 1);
    }

    private static String motivo(ResultadoCarga carga, String codigo) {
        return carga.getRechazadas().stream().filter(r -> r.codigo().equals(codigo))
                .map(ResultadoCarga.Rechazo::motivo).findFirst().orElse("aceptada");
    }

    private static List<String> codigos(List<Municipio> valores) {
        return valores.stream().map(Catalogo::getCodigo).toList();
    }

    private Path csv(String filas) {
        return csv(filas, ENCABEZADO);
    }

    private Path csv(String filas, String encabezado) {
        try {
            return Files.writeString(Files.createTempFile(carpeta, "carga", ".csv"),
                    encabezado + filas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    // Lo que el menú imprimió con esas respuestas, con la sesión del profesional de estadística.
    // Un test del menú lleva @Timeout (docs/DESIGN.md, sección 3).
    private String menu(String respuestas) {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Usuarios.entrar(usuarios, sesion, Cargo.PROFESIONAL_DE_ESTADISTICA);
        try {
            new MunicipioControlador(municipios, new Terminal(
                    new ByteArrayInputStream(respuestas.getBytes(StandardCharsets.UTF_8)),
                    new PrintStream(salida, true, StandardCharsets.UTF_8)), sesion).abrir();
        } finally {
            sesion.cerrar();
        }
        return salida.toString(StandardCharsets.UTF_8);
    }
}
