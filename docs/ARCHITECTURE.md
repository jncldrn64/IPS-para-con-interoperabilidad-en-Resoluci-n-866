# ARCHITECTURE.md: qué es el código hoy

> **Rol:** descriptivo. Dice qué es el código hoy, y cada sección nombra la fecha en que se comprobó
> contra él. Si contradice al código, el código gana y este archivo es el que está mal. Cómo tiene
> que escribirse el código es `docs/DESIGN.md`. **Régimen:** se corrige, y un hueco se borra en el
> PR que lo cierra. **Origen:** plantilla 1.2, sembrada el 2026-10-22 (`docs/DECISIONS.md`,
> 2026-10-22 "Se adopta la plantilla 1.2").

Una sección se describe cuando un PR de código toca su área, no antes. Lo que falta describir se
lista en la sección 5.

## 1. El repo, archivo por archivo

Comprobado el 2026-11-02 contra `git ls-files`.

| Archivo o carpeta | Qué es |
|---|---|
| `CLAUDE.md`, `CHANGELOG.md` | Documentos canónicos de la raíz: método e historia |
| `AGENTS.md` | Dos oraciones que mandan a `CLAUDE.md` a quien abre ese archivo primero |
| `README.md` | Portada para GitHub; remite a `CLAUDE.md` |
| `HELP.md` | Enlaces de ayuda que genera Spring Initializr |
| `LICENSE` | GNU Affero General Public License, versión 3 |
| `.gitignore` | Deja fuera de git `target/` y la base de prueba, `data/prueba.*` |
| `.github/workflows/tests.yml` | El CI: GitHub Actions corre `./mvnw -B test` en cada PR |
| `pom.xml` | Maven `com.ipssaludyvida:ips`, padre `spring-boot-starter-parent` 4.0.8, Java 21 |
| `mvnw`, `mvnw.cmd`, `.mvn/wrapper/` | Envoltorio de Maven: la primera corrida baja Maven 3.9.11 |
| `src/main/java/com/ipssaludyvida/` | `IpsApplication`: copia la base de prueba y arranca Spring |
| `src/main/resources/application.properties` | La base en archivo y el arranque sin servidor web |
| `src/test/java/com/ipssaludyvida/` | `comun/` y una carpeta por historia (`tests/README.md`) |
| `src/test/resources/application-test.properties` | La base de los tests, en memoria |
| `data/ips.mv.db` | El archivo de la base, con las 7 etnias, las 562 ocupaciones, las 8 categorías de discapacidad, los 249 países, los 14 tipos de documento y las 1.715 entidades del plan de beneficios (sección 3) |
| `data/catalogos/` | Los archivos que se cargan: las etnias, las categorías de discapacidad, los tipos de documento y la CIUO-88 A.C. en CSV, y las tablas Pais y CodigoEAPByNit de SISPRO en el Excel con que se descargaron, más las de Municipio y Departamento, sin cargar, con su fuente y cómo se rehace la base en su `README.md` |
| `tests/README.md` | Cómo se corren los tests |
| `docs/*.md` | Los siete documentos canónicos de `docs/` |
| `docs/context/` | Las tarjetas y las láminas, el modelo E-R y la memoria de cada integrante |

Dependencias que declara `pom.xml`: Spring Web MVC, Thymeleaf con su extra de Spring Security,
Spring Security, Spring Data JPA, Validation, DevTools, la consola de H2, Lombok y los controladores
de H2, SQL Server, MySQL y MariaDB, más sus pares de test. Para listarlas:
`grep -A1 '<groupId>' pom.xml | grep artifactId`.

## 2. Dónde vive cada cosa

Comprobado el 2026-10-31. `grep -c '^[a-z]' src/main/resources/application.properties` cuenta
las 7 claves de la configuración.

| Qué | Variable o símbolo | Ruta |
|---|---|---|
| Configuración | 7 claves, una por línea | `src/main/resources/application.properties` |
| Punto de entrada | `IpsApplication.main` | `src/main/java/com/ipssaludyvida/` |
| Historias de usuario | Una tarjeta por historia, más la de reglas comunes | `docs/context/trello/tarjetas/` |
| Modelo de datos propuesto | 24 tablas en Mermaid | `docs/context/modelo-datos/modelo-entidad-relacion.md` |

| Carpeta, bajo `src/main/java/com/ipssaludyvida/` | Qué guarda |
|---|---|
| `comun/interfaces/` | `CrudCatalogo`, el contrato de los 8 catálogos, y `Controlador` |
| `comun/modelos/` | `Catalogo`, la clase base, y `CatalogoVersion`, `CatalogoCambio` y `Motivo` |
| `comun/repositorios/` | `CatalogoRepositorio` y los de versiones y cambios; ninguno borra |
| `comun/servicios/` | La base `ServicioCatalogo`, la carga desde un CSV (`ArchivoCsv`) o un Excel (`ArchivoExcel`) y sus resultados, como `Consulta` |
| `comun/menu/` | `MenuPrincipal`, que pide la sesión y elige la historia, y `Terminal` |
| `comun/controladores/` | `ControladorCatalogo`: las 11 opciones que comparten los catálogos |
| `hu001/` | Sesión: `ContratoSesion`, `SesionServicio`, `Usuario`, `Cargo`, `Permiso` y lo demás |
| `hu004/` | Pacientes: `ContratoPacientes`, `Paciente`, sus tres listas fijas y lo demás |
| `hu018/` | Países: `ContratoPaises`, `Pais`, su repositorio, su servicio y `PaisControlador` |
| `hu022/` | Ocupaciones: `ContratoOcupaciones`, `Ocupacion`, su servicio, su controlador y `Nodo` |
| `hu026/` | Municipios: `ContratoMunicipios`, `Municipio`, `Departamento` y lo demás |
| `hu035/` | Categorías de discapacidad: `ContratoCategoriasDiscapacidad`, `CategoriaDiscapacidad` y lo demás |
| `hu034/` | Tipos de documento: `ContratoTiposDocumento`, `TipoDocumento` y lo demás |
| `hu036/` | Etnias: `ContratoEtnias`, `Etnia`, su repositorio, su servicio y `EtniaControlador` |
| `hu037/` | Comunidades étnicas: `ContratoComunidadesEtnicas`, `ComunidadEtnica` y lo demás |
| `hu038/` | Entidades del plan de beneficios: `ContratoEntidadesPlanBeneficios`, `EntidadPlanBeneficios` y lo demás |

`application.properties` abre la base de prueba, `jdbc:h2:file:./data/prueba`, relativa a la
carpeta donde se corre el programa, y el menú principal la muestra en su primera línea, la de
`Base abierta`. `IpsApplication.main` la copia de `data/ips.mv.db` antes de arrancar Spring, si no
existe. El log de Spring está en `warn` y sin el cartel de arranque, así que por la terminal sale
solo el menú.

`spring.jpa.hibernate.ddl-auto=update` crea las tablas que faltan según las
entidades, y `spring.main.web-application-type=none` deja sin arrancar Tomcat y Spring Security.
Con el perfil `test`, los tests abren `jdbc:h2:mem:pruebas`.

El menú arranca con `sh ./mvnw spring-boot:run` desde la raíz del repo. Antes de abrir el
menú, `UsuariosIniciales` crea los tres usuarios de prueba si la base no tiene ninguno, `admision`,
`medico` y `estadistica`, con la clave `Clave123`.

Los 8 catálogos que pide el registro del paciente (HU-004) ya tienen su clase. El paciente guarda
el código de cada uno, como texto (`docs/DESIGN.md`, sección 1), y los pide por su contrato:

| Numeral | Atributo del paciente | Entidad y tabla | Contrato | Datos en el archivo de la base |
|---|---|---|---|---|
| 1 y 11 | Nacionalidad, una o varias, y país de residencia | `Pais`, `PAIS` | `ContratoPaises` | 249 |
| 2 | Tipo de documento | `TipoDocumento`, `TIPO_DOCUMENTO` | `ContratoTiposDocumento` | 14 |
| 7 | Ocupación, solo las de 4 dígitos | `Ocupacion`, `OCUPACION` | `ContratoOcupaciones` | 562 |
| 10 | Discapacidad, una o varias | `CategoriaDiscapacidad`, `CATEGORIA_DISCAPACIDAD` | `ContratoCategoriasDiscapacidad` | 8 |
| 12 | Municipio de residencia | `Municipio`, `MUNICIPIO` | `ContratoMunicipios` | ninguno |
| 13 | Etnia | `Etnia`, `ETNIA` | `ContratoEtnias` | 7 |
| 13 | Comunidad étnica, si la etnia es de la 01 a la 06 | `ComunidadEtnica`, `COMUNIDAD_ETNICA` | `ContratoComunidadesEtnicas` | ninguna |
| 15 | Entidad del plan de beneficios | `EntidadPlanBeneficios`, `ENTIDAD_PLAN_BENEFICIOS` | `ContratoEntidadesPlanBeneficios` | 1.401 activas y 314 inactivas |

HU-004 ofrece solo los valores activos; uno inactivo sigue en el catálogo para los registros que
ya lo usan.

Los otros atributos de los 16 son valores del paciente o listas fijas de 5 o menos
(`docs/REQUIREMENTS.md`, sección 3). Un catálogo sin datos se llena creando valores a mano o con
una carga, en la base de prueba.

Sin sesión, el menú muestra la pantalla de inicio de sesión, con las opciones de entrar y de
salir. Con sesión, lista por su número las historias que el cargo ve. El 2026-11-02, una corrida
que entró como `admision` vio de la 1 a la 9: Registro de un paciente (HU-004), Países (HU-018),
Ocupaciones (HU-022), Municipios (HU-026), Tipos de documento (HU-034), Categorías de
discapacidad (HU-035), Etnias (HU-036), Comunidades étnicas (HU-037) y Entidades del plan de
beneficios (HU-038). Quien no es auxiliar de admisión no ve la primera.

Abajo van la letra C, que cierra la sesión, y el 0, que sale. Pasados 15 minutos sin actividad, la
opción siguiente pide entrar de nuevo.

El registro de un paciente pide los datos en el orden de la tarjeta y avisa al abrir qué catálogo
del paciente no tiene valores activos. Un catálogo de hasta 20 valores activos se lista entero, y
uno más largo se busca: el código exacto se elige de una vez, y una parte del nombre muestra la
lista de coincidencias, cada ciudad con su departamento. Si algo está mal, dice cada dato con su
motivo y vuelve a pedir solo esos. El paciente guarda los códigos de los catálogos, quién lo
registró y la hora de Colombia (`PacienteServicio.registrar`).

El controlador de cada catálogo tiene 11 opciones:

- consultar, con el filtro de activos, inactivos o todos, cuántos hay activos y la versión vigente;
- crear por cambio oficial, cambiar el nombre o el código, inactivar y reactivar, y cada una pide
  confirmar antes de guardar;
- el historial de un código y el de las versiones;
- cargar un CSV, buscar por código o por nombre y comparar con la lista oficial.

El auxiliar de admisión y el médico tratante ven solo las que consultan, 1, 7, 9, 10 y 11, y si
escriben el número de otra, el menú dice "No se pudo: su cargo no tiene ese permiso". Sin sesión,
un catálogo no abre.

El catálogo de ocupaciones agrega la opción 12, la jerarquía: cada código con una sangría de dos
espacios por nivel, la marca "se registra al paciente" en los de 4 dígitos y "sin ocupaciones" en
el grupo sin una de 4 activa. Antes de inactivar dice cuántos pacientes y atenciones la usan, los
dos en 0 hasta HU-004 y HU-007. El 2026-11-01, con `LANG=C.UTF-8` y una base en memoria, una
corrida creó 2, 22, 222, 2221 y 223, rechazó inactivar el grupo 222 con "tiene 1 ocupaciones
activas" y mostró la jerarquía al médico tratante, que no pudo crear un valor.

El catálogo de municipios muestra cada municipio con su departamento, como
`05001  MEDELLÍN  ACTIVO  ANTIOQUIA (05)`, y su carga pide un CSV con cuatro columnas:
`codigo;nombre;codigo_departamento;nombre_departamento`. El 2026-11-01, con una base en memoria,
una corrida cargó un archivo de 4 filas, rechazó `5002` con el aviso de los ceros iniciales y
mostró las dos Albania, cada una con su departamento.

El 2026-10-31, con `LANG=C.UTF-8`, una corrida con la entrada desde un `printf` rechazó una clave
equivocada, entró como `medico`, rechazó la opción 2 de etnias, cerró la sesión con "C" y entró como
`estadistica`, que vio las 11 opciones. `git status --short data` no mostró cambios.

Se comprobó el 2026-10-26 con la entrada desde un `printf`, en el contenedor de la sesión, que corre
Linux sin `LANG`. El 2026-10-27, `printf '0\n' | sh ./mvnw -q spring-boot:run` imprimió la línea de
la base y el menú, sin líneas de log, y nada en stderr.

La carga dice primero qué archivo espera y pide la ruta y los datos de la versión.
`ServicioCatalogo.leerCarga` lee y compara sin guardar, y `confirmarCarga` guarda. Cada operación
deja su fila en `CatalogoCambio`, la tabla de cambios común, y cada carga, la suya en
`CatalogoVersion`, y las dos guardan en `usuarioId` quién tenía la sesión, o nada si no había
(`RegistroCatalogos.anotar` y `RegistroCatalogos.guardar`). `ServicioCatalogo.contarUsos` devuelve 0
hasta que exista el registro del paciente (HU-004). Cada controlador pide su catálogo por su
contrato, como `ContratoEtnias`.

## 3. Recursos y cómo se reinicia cada uno

Comprobado el 2026-11-02 con el `Shell` de H2 sobre una copia de `data/ips.mv.db`, en el
contenedor de la sesión.

- `data/ips.mv.db`: el archivo de la base, en git desde la v0.10. Tiene las tablas `ETNIA`,
  `COMUNIDAD_ETNICA`, `OCUPACION`, `MUNICIPIO`, `DEPARTAMENTO`, `CATEGORIA_DISCAPACIDAD`, `PAIS`,
  `TIPO_DOCUMENTO`, `ENTIDAD_PLAN_BENEFICIOS`, `USUARIO`, `CATALOGO_VERSION` y `CATALOGO_CAMBIO`,
  y una secuencia para los números de cada una de las tres últimas. Trae las 7
  etnias de la hoja 15, activas, con la versión 1, "Resolución 866 de 2021"; ninguna comunidad
  étnica; las 562 ocupaciones de la CIUO-88 A.C., activas; las 8 categorías de discapacidad de las
  hojas 14 y 15, activas; los 249 países de la tabla Pais de SISPRO, activos; los 14 tipos de
  documento de la hoja 13, activos; las 1.715 entidades del plan de beneficios de la tabla
  CodigoEAPByNit de SISPRO, 1.401 activas y 314 inactivas; ningún municipio; los 3 usuarios de
  prueba; y 2.869 filas de cambios: una por valor creado y una por cada entidad que la fuente trae
  deshabilitada.
  Las etnias se cargaron con la
  opción 8 del menú de etnias y `data/catalogos/etnias-hoja-15.csv`, que entonces estaba en
  `src/test/resources/cargas/`. Se cargó en UTC, y
  el 2026-10-27 sus 8 horas, las 7 de los cambios y la de la carga, pasaron a la de Colombia: de las
  02:25 del 2026-10-27 a las 21:25 del 2026-10-26, con un `UPDATE` en la consola de H2. Las
  ocupaciones se cargaron el 2026-11-01 con la opción 8 de su menú y
  `data/catalogos/ciuo-88-ac.csv`, con la sesión de `estadistica`, así que su versión y sus 562
  cambios llevan su `usuarioId`. Entrar para cargar creó los usuarios de prueba. Las categorías de
  discapacidad se cargaron el 2026-11-02 con la opción 8 de su menú y
  `data/catalogos/discapacidad-hojas-14-15.csv`, también con `estadistica`. Los países, el mismo
  día, con la opción 8 de su menú y `data/catalogos/pais-sispro.xlsx`, el Excel tal como se exportó
  de SISPRO, también con `estadistica`. Los tipos de documento, el mismo día en hora de Colombia,
  con la opción 8 de su menú y `data/catalogos/tipos-documento-hoja-13.csv`. Las entidades del
  plan de beneficios, el mismo día, con la opción 8 de su menú y `data/catalogos/eapb-sispro.xlsx`,
  que rechazó 2 filas, `UT001_` y `UT004_`, por el guion bajo. Se reinicia
  con `git checkout data/ips.mv.db`. Solo lo abre quien tiene el turno, con su URL al arrancar
  (`docs/DESIGN.md`, sección 8). La consola de H2 lo abre con el usuario vacío y sin clave; con el
  usuario `sa` falla y deja `data/ips.trace.db`, que `.gitignore` deja fuera.
- `data/prueba.mv.db`: la base de prueba, donde corre el programa. `IpsApplication.main` la copia
  del archivo de la base si no existe, y `.gitignore` la deja fuera de git. Se reinicia borrándola:
  la corrida siguiente la copia otra vez con lo que traiga `data/ips.mv.db`.
- `target/`: lo que compila Maven. `.gitignore` lo deja fuera, y cada build lo rehace.
- `~/.m2/`, fuera del repo: ahí guarda el envoltorio Maven 3.9.11, en `wrapper/dists/`, y Maven las
  dependencias. La primera corrida los baja de `repo.maven.apache.org`, así que pide red.

## 4. Tamaño

Comprobado el 2026-11-02 con `git ls-files 'src/*' | xargs wc -l`: 11884 líneas en 101 archivos. De
Spring Initializr queda la mayor parte de `IpsApplicationTests.java`.

## 5. Sin describir todavía

Nada de lo que lista `git ls-files src` el 2026-11-02. Cada archivo nuevo que aparezca ahí se
describe en el PR que lo trae.

## 6. Huecos conocidos

Lo que está abierto o no se verificó contra datos reales. Cada hueco abre con su fecha y trae el
comando o la corrida que lo muestra, para que el próximo lector compruebe si sigue abierto. Se borra
en el PR que lo cierra, y el CHANGELOG guarda el rastro. Un PR que anuncia algo en el CHANGELOG mira
si eso cierra un hueco de esta lista.

- **2026-10-22: dos catálogos oficiales se descargaron.** No se comprobó que exista el catálogo de
  comunidades étnicas (HU-037). El 2026-11-01 entró la estructura de la CIUO-88 A.C. del DANE, con
  sus cuatro niveles (HU-022), y el 2026-11-02 las tablas Pais y CodigoEAPByNit de SISPRO (HU-018 y
  HU-038). Comando: `git ls-files data/catalogos` lista `ciuo-88-ac.csv`, `pais-sispro.xlsx` y
  `eapb-sispro.xlsx`, que se descargaron, y los CSV que se transcribieron de las hojas del anexo.
- **2026-11-02: la tabla Municipio de SISPRO no entra en la carga de HU-026.** El autor la exportó
  con la tabla Departamento. Municipio trae 1.124 códigos de 5 dígitos, uno deshabilitado, y el
  departamento en la columna `Extra_I:Departamento`, sin su nombre; Departamento trae los 33
  nombres y una fila `00`, `NO APLICA`, deshabilitada. La carga de HU-026 pide las cuatro
  columnas en un mismo archivo. Los dos Excel están en `data/catalogos/`, sin cargar, y cómo
  cargarlos lo decide Jorge, el integrante 1. Comando, con openpyxl:
  `python3 -c "import openpyxl; print(next(openpyxl.load_workbook('data/catalogos/municipio-sispro.xlsx').active.values))"`
  imprime las columnas, sin una del nombre del departamento.
- **2026-10-26: las tildes salen como "?" en una terminal sin UTF-8.** En el contenedor de la
  sesión, sin `LANG`, Java toma `ANSI_X3.4-1968` para la salida, y el menú imprime "Opci?n" e
  "Ind?gena". El dato se guarda bien: con `-Dspring-boot.run.jvmArguments=-Dstdout.encoding=UTF-8`,
  la misma lista imprime "Indígena". En Windows no se probó. Este comando usa una base en memoria y
  no toca el archivo de la base:

  ```sh
  printf '1\n2\n01\nIndígena\n1\n0\n0\n' | sh ./mvnw -q spring-boot:run \
    -Dspring-boot.run.arguments=--spring.datasource.url=jdbc:h2:mem:tildes | grep Ind
  ```
- **2026-10-26: `mvnw.cmd` no se corrió.** El contenedor de la sesión es Linux, así que el
  envoltorio solo se probó con `sh ./mvnw`. Comando, en Windows: `mvnw.cmd -v`, que tiene que
  imprimir "Apache Maven 3.9.11".
- **2026-10-31: la clave oculta no se probó en una terminal de verdad.** `Terminal.leerClave` usa
  `System.console()`, que con Java 21 es nulo si la entrada no es una terminal, y entonces lee la
  clave a la vista. Las corridas de la sesión fueron con `printf`, así que la rama de la consola no
  se corrió. Comando, en una terminal: `sh ./mvnw -q spring-boot:run`, elegir 1 y escribir la clave,
  que no tiene que verse.
- **2026-10-27: dos partes de HU-036 y HU-037 esperan el registro del paciente, HU-004.** Cuántos
  pacientes usan un valor, en el escenario 19 de HU-035 y de HU-036 y el 18 de HU-037: `contarUsos`
  devuelve 0. Desde el 2026-11-02 el paciente existe, pero ningún catálogo lo cuenta todavía. Desde
  el 2026-11-01 también el escenario 21 de HU-026 y el 22 de HU-022, que además cuenta atenciones:
  `ContratoOcupaciones.contarAtenciones` devuelve 0 hasta la atención, HU-007. Y lo que RC-11 pide
  de un registro ya guardado, en el 11 de HU-035 y de HU-036 y el 10 de HU-037, que no tiene código
  porque todavía no hay registros. Comando: `grep -n -A2 'public long contarUsos'
  src/main/java/com/ipssaludyvida/comun/servicios/*.java`.
- **2026-11-02: el registro de un paciente sin documento sigue la regla general.** Con los tipos
  AS, MS y SI de la hoja 13 la tarjeta de HU-004 dice que pueden faltar el número, los nombres, la
  fecha o la nacionalidad, y que falta decidir qué se exige (D-19). Hoy se exigen igual, y el
  paciente repetido se busca por tipo y número. Comando:
  `grep -n '"AS"\|"MS"\|"SI"' src/main/java/com/ipssaludyvida/hu004/servicios/*.java`, que no
  devuelve nada.
- **2026-11-02: con el archivo de la base, nadie que viva en Colombia se puede registrar.** Su
  catálogo de municipios está vacío, y el municipio es obligatorio para quien reside en Colombia.
  Una corrida del 2026-11-02 sobre una copia del archivo registró a un paciente que vive en
  Venezuela, con la entidad 05001 de la tabla de SISPRO, y para Colombia la pantalla dijo "No hay
  municipios activos: falta cargar el catálogo.". Comando: abrir HU-004 con
  `sh ./mvnw -q spring-boot:run`, que avisa "falta cargar el catálogo de municipios".
- **2026-11-02: los PR del modelo de Jorge salen a nombre de `jncldrn64`.** GitHub muestra como
  autor de los PR #23 a #26 la cuenta del autor, aunque los abrieron sesiones de Claude Code que no
  son suyas y el PR #23 lo fusionó `jorge-a-restrepo`. No se sabe por qué. **Hipótesis:** la cuenta
  de GitHub conectada en claude.ai, o la instalación de la app de Claude en el fork. No es
  prioritario: el cuerpo del PR nombra a su integrante (`CLAUDE.md`, sección 7). Se vio el
  2026-11-02 en el campo `user.login` de la API de GitHub. Comando, que no se corrió en la sesión:
  `gh api repos/jncldrn64/IPS-para-con-interoperabilidad-en-Resoluci-n-866/pulls/24 --jq
  .user.login`. Para comprobar la primera hipótesis, Jorge mira qué cuenta tiene conectada en
  https://claude.ai/connect-github.
