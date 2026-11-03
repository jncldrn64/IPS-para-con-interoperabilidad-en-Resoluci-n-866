package com.ipssaludyvida.hu004.servicios;

import com.ipssaludyvida.comun.interfaces.CrudCatalogo;
import com.ipssaludyvida.comun.modelos.Catalogo;
import com.ipssaludyvida.comun.servicios.RegistroCatalogos;
import com.ipssaludyvida.hu001.interfaces.ContratoSesion;
import com.ipssaludyvida.hu001.modelos.Usuario;
import com.ipssaludyvida.hu004.interfaces.ContratoPacientes;
import com.ipssaludyvida.hu004.modelos.IdentidadGenero;
import com.ipssaludyvida.hu004.modelos.ListaFija;
import com.ipssaludyvida.hu004.modelos.Paciente;
import com.ipssaludyvida.hu004.modelos.SexoBiologico;
import com.ipssaludyvida.hu004.modelos.ZonaTerritorial;
import com.ipssaludyvida.hu004.repositorios.PacienteRepositorio;
import com.ipssaludyvida.hu018.interfaces.ContratoPaises;
import com.ipssaludyvida.hu022.interfaces.ContratoOcupaciones;
import com.ipssaludyvida.hu022.modelos.Ocupacion;
import com.ipssaludyvida.hu026.interfaces.ContratoMunicipios;
import com.ipssaludyvida.hu034.interfaces.ContratoTiposDocumento;
import com.ipssaludyvida.hu035.interfaces.ContratoCategoriasDiscapacidad;
import com.ipssaludyvida.hu036.interfaces.ContratoEtnias;
import com.ipssaludyvida.hu037.interfaces.ContratoComunidadesEtnicas;
import com.ipssaludyvida.hu038.interfaces.ContratoEntidadesPlanBeneficios;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * HU-004: valida cada dato del paciente con su numeral (RC-7) y con su catálogo (RC-2), y lo guarda
 * con quien tiene la sesión. Junta todos los motivos antes de responder, para que la pantalla diga
 * cuáles faltan y marque solo los que hay que corregir (escenarios 10 y 11). No lee del teclado ni
 * imprime (docs/DESIGN.md, sección 6).
 */
@Service
public class PacienteServicio implements ContratoPacientes {

    // Numeral 2: Alfanumérico, longitud de 3 a 20. Numeral 3: Alfanumérico, de 2 a 60.
    private static final String DOCUMENTO = "[A-Za-z0-9]{3,20}";
    private static final int NOMBRE_MINIMO = 2;
    private static final int NOMBRE_MAXIMO = 60;
    // Las etnias con comunidad: indígenas, ROM y comunidades negras, afro, palenqueras y raizales
    // (hoja 15). La 99, "Ninguna de las anteriores", no la tiene.
    private static final Set<String> ETNIAS_CON_COMUNIDAD =
            Set.of("01", "02", "03", "04", "05", "06");

    private final PacienteRepositorio pacientes;
    private final ContratoPaises paises;
    private final ContratoTiposDocumento tiposDocumento;
    private final ContratoOcupaciones ocupaciones;
    private final ContratoCategoriasDiscapacidad discapacidades;
    private final ContratoMunicipios municipios;
    private final ContratoEtnias etnias;
    private final ContratoComunidadesEtnicas comunidades;
    private final ContratoEntidadesPlanBeneficios entidades;
    private final ContratoSesion sesion;
    private final RegistroCatalogos registro;

    public PacienteServicio(PacienteRepositorio pacientes, ContratoPaises paises,
            ContratoTiposDocumento tiposDocumento, ContratoOcupaciones ocupaciones,
            ContratoCategoriasDiscapacidad discapacidades, ContratoMunicipios municipios,
            ContratoEtnias etnias, ContratoComunidadesEtnicas comunidades,
            ContratoEntidadesPlanBeneficios entidades, ContratoSesion sesion,
            RegistroCatalogos registro) {
        this.pacientes = pacientes;
        this.paises = paises;
        this.tiposDocumento = tiposDocumento;
        this.ocupaciones = ocupaciones;
        this.discapacidades = discapacidades;
        this.municipios = municipios;
        this.etnias = etnias;
        this.comunidades = comunidades;
        this.entidades = entidades;
        this.sesion = sesion;
        this.registro = registro;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Campo, String> validar(DatosPaciente d) {
        return validar(d, Map.of());
    }

    // r: los códigos que el paciente ya tenía, por dato. Un valor que se inactivó en su
    // catálogo se conserva mientras no se cambie ese dato (RC-4, escenario 10 de HU-006).
    private Map<Campo, String> validar(DatosPaciente d, Map<Campo, Set<String>> r) {
        Map<Campo, String> errores = new LinkedHashMap<>();
        varios(errores, r, Campo.NACIONALIDAD, d.getNacionalidades(), paises);
        catalogo(errores, r, Campo.TIPO_DOCUMENTO, d.getTipoDocumento(), tiposDocumento, true);
        if (vacio(d.getNumeroDocumento())) {
            errores.put(Campo.NUMERO_DOCUMENTO, "falta");
        } else if (!d.getNumeroDocumento().trim().matches(DOCUMENTO)) {
            errores.put(Campo.NUMERO_DOCUMENTO, "tiene de 3 a 20 letras o dígitos (numeral 2)");
        }
        nombre(errores, Campo.PRIMER_APELLIDO, d.getPrimerApellido(), true);
        nombre(errores, Campo.SEGUNDO_APELLIDO, d.getSegundoApellido(), false);
        nombre(errores, Campo.PRIMER_NOMBRE, d.getPrimerNombre(), true);
        nombre(errores, Campo.SEGUNDO_NOMBRE, d.getSegundoNombre(), false);
        fecha(errores, d.getFechaNacimiento());
        if (!vacio(d.getHoraNacimiento()) && hora(d.getHoraNacimiento()) == null) {
            errores.put(Campo.HORA_NACIMIENTO, "\"" + d.getHoraNacimiento().trim()
                    + "\" no sirve; escríbala como HH:MM (numeral 4)");
        }
        lista(errores, Campo.SEXO_BIOLOGICO, d.getSexoBiologico(), SexoBiologico.class, 5);
        lista(errores, Campo.IDENTIDAD_GENERO, d.getIdentidadGenero(), IdentidadGenero.class, 6);
        ocupacion(errores, r, d.getOcupacion());
        varios(errores, r, Campo.DISCAPACIDAD, d.getDiscapacidades(), discapacidades);
        if (!errores.containsKey(Campo.DISCAPACIDAD) && d.getDiscapacidades().size() > 1
                && limpios(d.getDiscapacidades()).contains(SIN_DISCAPACIDAD)) {
            errores.put(Campo.DISCAPACIDAD, "\"Sin discapacidad\" no va con otra categoría");
        }
        catalogo(errores, r, Campo.PAIS_RESIDENCIA, d.getPaisResidencia(), paises, true);
        boolean enColombia = COLOMBIA.equals(limpio(d.getPaisResidencia()));
        if (enColombia) {
            catalogo(errores, r, Campo.MUNICIPIO_RESIDENCIA, d.getMunicipioResidencia(),
                    municipios, true);
        } else if (!vacio(d.getMunicipioResidencia())
                && !errores.containsKey(Campo.PAIS_RESIDENCIA)) {
            // Con un país que no sirve, el dato que hay que corregir es el país.
            errores.put(Campo.MUNICIPIO_RESIDENCIA, "solo se registra si reside en Colombia");
        }
        catalogo(errores, r, Campo.ETNIA, d.getEtnia(), etnias, true);
        comunidad(errores, r, d);
        lista(errores, Campo.ZONA_TERRITORIAL, d.getZonaTerritorial(), ZonaTerritorial.class, 14);
        catalogo(errores, r, Campo.ENTIDAD_PLAN_BENEFICIOS, d.getEntidadPlanBeneficios(),
                entidades, true);
        return errores;
    }

    @Override
    @Transactional
    public Paciente registrar(DatosPaciente d) {
        Map<Campo, String> errores = validar(d);
        if (!errores.isEmpty()) {
            throw new RegistroInvalido(errores);
        }
        String tipo = limpio(d.getTipoDocumento());
        String numero = d.getNumeroDocumento().trim();
        if (buscar(tipo, numero).isPresent()) {
            throw new IllegalStateException("ya hay un paciente registrado con el documento "
                    + tipo + " " + numero);
        }
        Paciente p = new Paciente();
        copiar(d, p);
        p.setRegistradoPor(sesion.actual().map(Usuario::getId).orElse(null));
        p.setRegistradoEn(registro.ahora());
        return pacientes.save(p);
    }

    // Pasa los datos validados al paciente. Las listas se vacían antes, para que un valor retirado
    // salga (escenario 5 de HU-006).
    private void copiar(DatosPaciente d, Paciente p) {
        p.setNacionalidades(limpios(d.getNacionalidades()).stream()
                .map(c -> valor(paises, c)).toList());
        p.setTipoDocumento(limpio(d.getTipoDocumento()));
        p.setNumeroDocumento(d.getNumeroDocumento().trim());
        p.setPrimerApellido(d.getPrimerApellido().trim());
        p.setSegundoApellido(opcional(d.getSegundoApellido()));
        p.setPrimerNombre(d.getPrimerNombre().trim());
        p.setSegundoNombre(opcional(d.getSegundoNombre()));
        p.setFechaNacimiento(LocalDate.parse(d.getFechaNacimiento().trim()));
        p.setHoraNacimiento(vacio(d.getHoraNacimiento()) ? null : hora(d.getHoraNacimiento()));
        p.setSexoBiologico(limpio(d.getSexoBiologico()));
        p.setIdentidadGenero(limpio(d.getIdentidadGenero()));
        p.setOcupacion(opcional(d.getOcupacion()));
        p.setDiscapacidades(limpios(d.getDiscapacidades()).stream()
                .map(c -> valor(discapacidades, c)).toList());
        p.setPaisResidencia(limpio(d.getPaisResidencia()));
        p.setMunicipioResidencia(opcional(d.getMunicipioResidencia()));
        p.setEtnia(limpio(d.getEtnia()));
        p.setComunidadEtnica(opcional(d.getComunidadEtnica()));
        p.setZonaTerritorial(limpio(d.getZonaTerritorial()));
        p.setEntidadPlanBeneficios(limpio(d.getEntidadPlanBeneficios()));
    }

    @Override
    @Transactional
    public Paciente actualizar(Paciente p, DatosPaciente d) {
        Map<Campo, Set<String>> registrados = new EnumMap<>(Campo.class);
        registrados.put(Campo.NACIONALIDAD, Set.copyOf(p.getNacionalidades()));
        registrados.put(Campo.DISCAPACIDAD, Set.copyOf(p.getDiscapacidades()));
        DatosPaciente.de(p).valores().forEach((campo, valor) ->
                registrados.putIfAbsent(campo, Set.of(valor)));
        Map<Campo, String> errores = validar(d, registrados);
        if (!errores.isEmpty()) {
            throw new RegistroInvalido(errores);
        }
        String tipo = limpio(d.getTipoDocumento());
        String numero = d.getNumeroDocumento().trim();
        Optional<Paciente> otro = buscar(tipo, numero);
        if (otro.isPresent() && !otro.get().getId().equals(p.getId())) {
            throw new IllegalStateException("ese documento ya está registrado: hay otro paciente"
                    + " con " + tipo + " " + numero);
        }
        copiar(d, p);
        return pacientes.save(p);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> buscar(String tipoDocumento, String numeroDocumento) {
        return pacientes.findByTipoDocumentoAndNumeroDocumento(limpio(tipoDocumento),
                numeroDocumento == null ? "" : numeroDocumento.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> catalogosSinValores() {
        List<String> faltan = new ArrayList<>();
        sinValores(faltan, paises, "es obligatorio para todos");
        sinValores(faltan, tiposDocumento, "es obligatorio para todos");
        sinValores(faltan, ocupaciones, "se pide solo si la persona tiene ocupación");
        sinValores(faltan, discapacidades, "es obligatorio para todos");
        sinValores(faltan, municipios, "es obligatorio para quien reside en Colombia");
        sinValores(faltan, etnias, "es obligatorio para todos");
        sinValores(faltan, comunidades, "mientras esté vacío, se registra sin comunidad étnica");
        sinValores(faltan, entidades, "es obligatorio para todos");
        return faltan;
    }

    // Un dato de catálogo: que esté, si se pide, y que sea un valor activo (RC-2). Con el catálogo
    // vacío no se puede elegir nada, y el motivo dice qué falta cargar (escenario 14).
    private void catalogo(Map<Campo, String> errores, Map<Campo, Set<String>> registrados,
            Campo campo, String codigo, CrudCatalogo<? extends Catalogo> crud,
            boolean obligatorio) {
        if (vacio(codigo)) {
            if (obligatorio) {
                errores.put(campo, crud.listarActivos().isEmpty()
                        ? "falta cargar el catálogo de " + crud.nombreEnPlural() : "falta");
            }
            return;
        }
        if (!activo(crud, limpio(codigo)) && !registrado(registrados, campo, limpio(codigo))) {
            errores.put(campo, "\"" + codigo.trim() + "\" no es un valor activo del catálogo de "
                    + crud.nombreEnPlural());
        }
    }

    // Uno o varios valores del mismo catálogo: hace falta al menos uno, y todos activos.
    private void varios(Map<Campo, String> errores, Map<Campo, Set<String>> registrados,
            Campo campo, List<String> codigos, CrudCatalogo<? extends Catalogo> crud) {
        Set<String> elegidos = limpios(codigos);
        if (elegidos.isEmpty()) {
            catalogo(errores, registrados, campo, "", crud, true);
            return;
        }
        List<String> malos = elegidos.stream()
                .filter(c -> !activo(crud, c) && !registrado(registrados, campo, c)).toList();
        if (!malos.isEmpty()) {
            errores.put(campo, String.join(", ", malos) + (malos.size() == 1 ? " no es un valor"
                    + " activo" : " no son valores activos") + " del catálogo de "
                    + crud.nombreEnPlural());
        }
    }

    // Numeral 7: la ocupación es opcional, pero si se da, tiene que ser una hija activa [2.1].
    private void ocupacion(Map<Campo, String> errores, Map<Campo, Set<String>> registrados,
            String codigo) {
        catalogo(errores, registrados, Campo.OCUPACION, codigo, ocupaciones, false);
        if (!errores.containsKey(Campo.OCUPACION) && !vacio(codigo)
                && limpio(codigo).length() != Ocupacion.LARGO_HIJA) {
            errores.put(Campo.OCUPACION, "agrupa otras ocupaciones; elija una del nivel más"
                    + " específico, de 4 dígitos");
        }
    }

    // Numeral 13: la comunidad se pide solo con una etnia de la 01 a la 06 y si su catálogo tiene
    // valores; mientras esté vacío, el registro sigue sin ella (D-19).
    private void comunidad(Map<Campo, String> errores, Map<Campo, Set<String>> registrados,
            DatosPaciente d) {
        boolean conComunidad = ETNIAS_CON_COMUNIDAD.contains(limpio(d.getEtnia()));
        if (!conComunidad) {
            if (!vacio(d.getComunidadEtnica())) {
                errores.put(Campo.COMUNIDAD_ETNICA, "solo se registra con una etnia de la 01 a la"
                        + " 06");
            }
            return;
        }
        catalogo(errores, registrados, Campo.COMUNIDAD_ETNICA, d.getComunidadEtnica(), comunidades,
                !comunidades.listarActivos().isEmpty());
    }

    private static void nombre(Map<Campo, String> errores, Campo campo, String valor,
            boolean obligatorio) {
        if (vacio(valor)) {
            if (obligatorio) {
                errores.put(campo, "falta");
            }
            return;
        }
        int largo = valor.trim().length();
        if (largo < NOMBRE_MINIMO || largo > NOMBRE_MAXIMO) {
            errores.put(campo, "tiene de 2 a 60 caracteres (numeral 3)");
        }
    }

    private void fecha(Map<Campo, String> errores, String valor) {
        if (vacio(valor)) {
            errores.put(Campo.FECHA_NACIMIENTO, "falta");
            return;
        }
        try {
            LocalDate fecha = LocalDate.parse(valor.trim());
            if (fecha.isAfter(registro.hoy())) {
                errores.put(Campo.FECHA_NACIMIENTO, "la fecha " + fecha + " es futura");
            }
        } catch (DateTimeParseException e) {
            errores.put(Campo.FECHA_NACIMIENTO, "\"" + valor.trim()
                    + "\" no sirve; escríbala como AAAA-MM-DD (numeral 4)");
        }
    }

    private static <E extends Enum<E> & ListaFija> void lista(Map<Campo, String> errores,
            Campo campo, String codigo, Class<E> lista, int numeral) {
        if (vacio(codigo)) {
            errores.put(campo, "falta");
        } else if (ListaFija.desde(lista, limpio(codigo)) == null) {
            errores.put(campo, "\"" + codigo.trim() + "\" no está en la lista del numeral "
                    + numeral);
        }
    }

    private static LocalTime hora(String valor) {
        try {
            return LocalTime.parse(valor.trim(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static void sinValores(List<String> faltan, CrudCatalogo<? extends Catalogo> crud,
            String cuando) {
        if (crud.listarActivos().isEmpty()) {
            faltan.add("falta cargar el catálogo de " + crud.nombreEnPlural() + ": " + cuando);
        }
    }

    private static boolean registrado(Map<Campo, Set<String>> registrados, Campo campo,
            String codigo) {
        return registrados.getOrDefault(campo, Set.of()).contains(codigo);
    }

    private static boolean activo(CrudCatalogo<? extends Catalogo> crud, String codigo) {
        return crud.buscarPorCodigo(codigo)
                .map(v -> v.getEstado() == Catalogo.Estado.ACTIVO).orElse(false);
    }

    // El valor de un código que validar ya revisó; si no está, es un error del programa.
    private static <T extends Catalogo> T valor(CrudCatalogo<T> catalogo, String codigo) {
        return catalogo.buscarPorCodigo(codigo).orElseThrow(() -> new IllegalStateException(
                "el código " + codigo + " pasó la validación y no está en "
                        + catalogo.nombreEnPlural()));
    }

    private static Set<String> limpios(List<String> codigos) {
        Set<String> limpios = new LinkedHashSet<>();
        codigos.stream().filter(c -> !vacio(c)).forEach(c -> limpios.add(limpio(c)));
        return limpios;
    }

    // Los códigos de los catálogos del paciente van en mayúsculas: cc es CC.
    private static String limpio(String codigo) {
        return codigo == null ? "" : codigo.trim().toUpperCase(java.util.Locale.ROOT);
    }

    private static String opcional(String valor) {
        return vacio(valor) ? null : valor.trim();
    }

    private static boolean vacio(String valor) {
        return valor == null || valor.isBlank();
    }
}
