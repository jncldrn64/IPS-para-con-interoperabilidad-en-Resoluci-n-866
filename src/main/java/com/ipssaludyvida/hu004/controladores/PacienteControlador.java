package com.ipssaludyvida.hu004.controladores;

import com.ipssaludyvida.comun.interfaces.Controlador;
import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.menu.Terminal;
import com.ipssaludyvida.comun.modelos.Catalogo;
import com.ipssaludyvida.hu001.controladores.SesionControlador;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Permiso;
import com.ipssaludyvida.hu004.interfaces.ContratoPacientes;
import com.ipssaludyvida.hu004.modelos.IdentidadGenero;
import com.ipssaludyvida.hu004.modelos.ListaFija;
import com.ipssaludyvida.hu004.modelos.Paciente;
import com.ipssaludyvida.hu004.modelos.SexoBiologico;
import com.ipssaludyvida.hu004.modelos.ZonaTerritorial;
import com.ipssaludyvida.hu004.servicios.Campo;
import com.ipssaludyvida.hu004.servicios.DatosPaciente;
import com.ipssaludyvida.hu004.servicios.RegistroInvalido;
import com.ipssaludyvida.hu018.interfaces.ContratoPaises;
import com.ipssaludyvida.hu022.interfaces.ContratoOcupaciones;
import com.ipssaludyvida.hu026.interfaces.ContratoMunicipios;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu035.interfaces.ContratoCategoriasDiscapacidad;
import com.ipssaludyvida.hu036.interfaces.ContratoEtnias;
import com.ipssaludyvida.hu037.interfaces.ContratoComunidadesEtnicas;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.springframework.stereotype.Component;

/**
 * El controlador de HU-004: pide los datos del paciente en el orden de la tarjeta, los manda a
 * ContratoPacientes y, si algo está mal, vuelve a pedir solo esos datos (escenario 11). Un dato de
 * catálogo se elige de una lista de valores activos: lo escrito sirve para buscar y no se guarda
 * (escenario 15). Lo abre el auxiliar de admisión (HU-001, matriz de permisos).
 */
@Component
public class PacienteControlador implements Controlador {

    /** Un catálogo con más valores activos que este se busca; uno con menos se lista entero. */
    static final int LISTA_ENTERA = 20;

    private final ContratoPacientes pacientes;
    private final ContratoPaises paises;
    private final ContratoTiposDocumento tiposDocumento;
    private final ContratoOcupaciones ocupaciones;
    private final ContratoCategoriasDiscapacidad discapacidades;
    private final ContratoMunicipios municipios;
    private final ContratoEtnias etnias;
    private final ContratoComunidadesEtnicas comunidades;
    private final ContratoEntidadesPlanBeneficios entidades;
    private final Terminal terminal;
    private final ContratoSesion sesion;

    public PacienteControlador(ContratoPacientes pacientes, ContratoPaises paises,
            ContratoTiposDocumento tiposDocumento, ContratoOcupaciones ocupaciones,
            ContratoCategoriasDiscapacidad discapacidades, ContratoMunicipios municipios,
            ContratoEtnias etnias, ContratoComunidadesEtnicas comunidades,
            ContratoEntidadesPlanBeneficios entidades, Terminal terminal, ContratoSesion sesion) {
        this.pacientes = pacientes;
        this.paises = paises;
        this.tiposDocumento = tiposDocumento;
        this.ocupaciones = ocupaciones;
        this.discapacidades = discapacidades;
        this.municipios = municipios;
        this.etnias = etnias;
        this.comunidades = comunidades;
        this.entidades = entidades;
        this.terminal = terminal;
        this.sesion = sesion;
    }

    @Override
    public String historia() {
        return "HU-004";
    }

    @Override
    public String titulo() {
        return "Registro de un paciente";
    }

    /** Solo el auxiliar de admisión registra pacientes (matriz de HU-001). */
    @Override
    public Permiso permisoParaVer() {
        return Permiso.CAMBIAR_PACIENTE;
    }

    @Override
    public void abrir() {
        try {
            sesion.exigir(Permiso.CAMBIAR_PACIENTE);
        } catch (IllegalStateException e) {
            escribir("No se pudo: " + e.getMessage());
            return;
        }
        while (true) {
            escribir("");
            escribir("== Registro de un paciente");
            // Escenario 14: antes de empezar, qué catálogo falta cargar.
            pacientes.catalogosSinValores().forEach(f -> escribir("Aviso: " + f));
            escribir("1. Registrar un paciente nuevo");
            escribir("0. Volver");
            String opcion = leer("Opción: ");
            if (opcion.equals("0") || opcion.isEmpty()) {
                return;
            }
            if (!sesion.seguir()) {
                escribir(SesionControlador.VENCIDA);
                return;
            }
            if (opcion.equals("1")) {
                registrar();
            } else {
                escribir("Opción no válida");
            }
        }
    }

    private void registrar() {
        DatosPaciente datos = new DatosPaciente();
        for (Campo campo : Campo.values()) {
            if (aplica(campo, datos)) {
                pedir(campo, datos);
            }
        }
        while (true) {
            // Escenario 13: sin confirmar no se guarda nada.
            if (!leer("¿Guardar el paciente? (s/n): ").equalsIgnoreCase("s")) {
                escribir("Cancelado: no se guardó el paciente.");
                return;
            }
            if (!sesion.seguir()) {
                escribir(SesionControlador.VENCIDA);
                return;
            }
            try {
                Paciente p = pacientes.registrar(datos);
                escribir("Registrado el paciente " + p.getTipoDocumento() + " "
                        + p.getNumeroDocumento() + ".");
                return;
            } catch (RegistroInvalido e) {
                escribir("No se guardó. Corrija estos datos; los demás se conservan:");
                e.getErrores().forEach((campo, motivo) ->
                        escribir("  - " + campo.texto() + ": " + motivo));
                for (Campo campo : e.getErrores().keySet()) {
                    pedir(campo, datos);
                }
            } catch (IllegalStateException e) {
                escribir("No se guardó: " + e.getMessage() + ".");
                return;
            }
        }
    }

    // El municipio se pide solo a quien reside en Colombia, y la comunidad, con una etnia de la 01
    // a la 06 y si su catálogo tiene valores (D-19).
    private boolean aplica(Campo campo, DatosPaciente d) {
        return switch (campo) {
            case MUNICIPIO_RESIDENCIA -> ContratoPacientes.COLOMBIA.equals(d.getPaisResidencia());
            case COMUNIDAD_ETNICA -> Set.of("01", "02", "03", "04", "05", "06")
                    .contains(d.getEtnia()) && !comunidades.listarActivos().isEmpty();
            default -> true;
        };
    }

    private void pedir(Campo campo, DatosPaciente d) {
        escribir("");
        switch (campo) {
            case NACIONALIDAD -> d.setNacionalidades(varios("País de nacionalidad", "¿Otro país"
                    + " de nacionalidad? (s/n): ", paises, v -> ""));
            case TIPO_DOCUMENTO -> d.setTipoDocumento(elegir("Tipo de documento", tiposDocumento,
                    v -> ""));
            case NUMERO_DOCUMENTO -> d.setNumeroDocumento(leer("Número de documento: "));
            case PRIMER_APELLIDO -> d.setPrimerApellido(leer("Primer apellido: "));
            case SEGUNDO_APELLIDO -> d.setSegundoApellido(leer("Segundo apellido (Enter si no"
                    + " tiene): "));
            case PRIMER_NOMBRE -> d.setPrimerNombre(leer("Primer nombre: "));
            case SEGUNDO_NOMBRE -> d.setSegundoNombre(leer("Segundo nombre (Enter si no tiene): "));
            case FECHA_NACIMIENTO -> d.setFechaNacimiento(
                    leer("Fecha de nacimiento (AAAA-MM-DD): "));
            case HORA_NACIMIENTO -> d.setHoraNacimiento(leer("Hora de nacimiento (HH:MM, Enter si"
                    + " no se sabe): "));
            case SEXO_BIOLOGICO -> d.setSexoBiologico(lista("Sexo biológico",
                    SexoBiologico.class));
            case IDENTIDAD_GENERO -> d.setIdentidadGenero(lista("Identidad de género",
                    IdentidadGenero.class));
            case OCUPACION -> d.setOcupacion(elegir("Ocupación (Enter si no tiene)", ocupaciones,
                    v -> ""));
            case DISCAPACIDAD -> d.setDiscapacidades(varios("Categoría de discapacidad",
                    "¿Otra categoría de discapacidad? (s/n): ", discapacidades, v -> ""));
            case PAIS_RESIDENCIA -> d.setPaisResidencia(elegir("País de residencia habitual",
                    paises, v -> ""));
            case MUNICIPIO_RESIDENCIA -> d.setMunicipioResidencia(elegir("Ciudad (municipio) de"
                    + " residencia", municipios, v -> municipios.departamento(v.getCodigo())
                    .map(dep -> "  " + dep.getNombre() + " (" + dep.getCodigo() + ")")
                    .orElse("")));
            case ETNIA -> d.setEtnia(elegir("Etnia", etnias, v -> ""));
            case COMUNIDAD_ETNICA -> d.setComunidadEtnica(elegir("Comunidad étnica", comunidades,
                    v -> ""));
            case ZONA_TERRITORIAL -> d.setZonaTerritorial(lista("Zona territorial de residencia",
                    ZonaTerritorial.class));
            case ENTIDAD_PLAN_BENEFICIOS -> d.setEntidadPlanBeneficios(elegir("Entidad que"
                    + " responde por el plan de beneficios", entidades, v -> ""));
        }
    }

    /**
     * Un valor activo de un catálogo, o vacío. Un catálogo corto se lista entero; uno largo se
     * busca por código, exacto, o por parte del nombre (D-23), y cada valor sale con su detalle,
     * como el departamento de una ciudad. Lo escrito no se guarda: se guarda el código elegido.
     */
    private <T extends Catalogo> String elegir(String titulo, CrudCatalogo<T> crud,
            Function<T, String> detalle) {
        escribir(titulo + ":");
        List<T> activos = crud.listarActivos();
        if (activos.isEmpty()) {
            escribir("  No hay " + crud.nombreEnPlural() + " activos: falta cargar el catálogo.");
            return "";
        }
        if (activos.size() <= LISTA_ENTERA) {
            return deLaLista(activos, detalle, "Número o código (Enter para dejarlo vacío): ");
        }
        while (true) {
            String texto = leer("Código o parte del nombre (Enter para dejarlo vacío): ");
            if (texto.isEmpty()) {
                return "";
            }
            List<T> hallados = crud.buscar(texto).stream()
                    .filter(v -> v.getEstado() == Catalogo.Estado.ACTIVO).toList();
            // Por código, el exacto (D-23): se elige sin pasar por la lista.
            if (hallados.size() == 1 && hallados.get(0).getCodigo().equalsIgnoreCase(texto)) {
                T v = hallados.get(0);
                escribir("  Elegido: " + v.getCodigo() + "  " + v.getNombre() + detalle.apply(v));
                return v.getCodigo();
            }
            if (hallados.isEmpty()) {
                escribir("  " + crud.sinCoincidencias() + ".");
                continue;
            }
            if (hallados.size() > LISTA_ENTERA) {
                escribir("  Hay " + hallados.size() + " " + crud.nombreEnPlural()
                        + " que coinciden; escriba más letras.");
                continue;
            }
            String elegido = deLaLista(hallados, detalle, "Número o código (Enter para buscar"
                    + " otra vez): ");
            if (!elegido.isEmpty()) {
                return elegido;
            }
        }
    }

    private <T extends Catalogo> String deLaLista(List<T> valores, Function<T, String> detalle,
            String pregunta) {
        for (int i = 0; i < valores.size(); i++) {
            T v = valores.get(i);
            escribir("  " + (i + 1) + ". " + v.getCodigo() + "  " + v.getNombre()
                    + detalle.apply(v));
        }
        while (true) {
            String respuesta = leer(pregunta);
            if (respuesta.isEmpty()) {
                return "";
            }
            for (T v : valores) {
                if (v.getCodigo().equalsIgnoreCase(respuesta)) {
                    return v.getCodigo();
                }
            }
            try {
                int numero = Integer.parseInt(respuesta);
                if (numero >= 1 && numero <= valores.size()) {
                    return valores.get(numero - 1).getCodigo();
                }
            } catch (NumberFormatException e) {
                // No es un número de la lista: se pregunta otra vez.
            }
            escribir("  Elija un número de la lista o un código de los que aparecen.");
        }
    }

    private <T extends Catalogo> List<String> varios(String titulo, String otra,
            CrudCatalogo<T> crud, Function<T, String> detalle) {
        List<String> elegidos = new ArrayList<>();
        do {
            String codigo = elegir(titulo, crud, detalle);
            if (codigo.isEmpty()) {
                break;
            }
            if (!elegidos.contains(codigo)) {
                elegidos.add(codigo);
            }
        } while (leer(otra).equalsIgnoreCase("s"));
        return elegidos;
    }

    private <E extends Enum<E> & ListaFija> String lista(String titulo, Class<E> lista) {
        escribir(titulo + ":");
        for (E valor : lista.getEnumConstants()) {
            escribir("  " + valor.codigo() + ". " + valor.texto());
        }
        return leer("Código: ");
    }

    private String leer(String pregunta) {
        return terminal.leer(pregunta);
    }

    private void escribir(String linea) {
        terminal.escribir(linea);
    }
}
