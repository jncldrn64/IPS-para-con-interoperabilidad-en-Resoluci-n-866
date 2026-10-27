# ARCHITECTURE.md: qué es el código hoy

> **Rol:** descriptivo. Dice qué es el código hoy, y cada sección nombra la fecha en que se comprobó
> contra él. Si contradice al código, el código gana y este archivo es el que está mal. Cómo tiene
> que escribirse el código es `docs/DESIGN.md`. **Régimen:** se corrige, y un hueco se borra en el
> PR que lo cierra. **Origen:** plantilla 1.2, sembrada el 2026-10-22 (`docs/DECISIONS.md`,
> 2026-10-22 "Se adopta la plantilla 1.2").

Una sección se describe cuando un PR de código toca su área, no antes. Lo que falta describir se
lista en la sección 5.

## 1. El repo, archivo por archivo

Comprobado el 2026-10-26 contra `git ls-files`.

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
| `src/test/resources/cargas/etnias-hoja-15.csv` | Las 7 etnias de la hoja 15, en el CSV de RC-8 |
| `data/ips.mv.db` | El archivo de la base, con las 7 etnias cargadas (sección 3) |
| `tests/README.md` | Cómo se corren los tests |
| `docs/*.md` | Los siete documentos canónicos de `docs/` |
| `docs/context/` | Las tarjetas y las láminas de las 30 historias, el modelo E-R y la memoria contextual; lo explica su `README.md` |

Dependencias que declara `pom.xml`: Spring Web MVC, Thymeleaf con su extra de Spring Security,
Spring Security, Spring Data JPA, Validation, DevTools, la consola de H2, Lombok y los controladores
de H2, SQL Server, MySQL y MariaDB, más sus pares de test. Para listarlas:
`grep -A1 '<groupId>' pom.xml | grep artifactId`.

## 2. Dónde vive cada cosa

Comprobado el 2026-10-27. `grep -c '^[a-z]' src/main/resources/application.properties` cuenta
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
| `comun/servicios/` | La base `ServicioCatalogo`, la carga y sus resultados, como `Consulta` |
| `comun/menu/` | `MenuPrincipal`, que elige la historia, y `Terminal`, la entrada y la salida |
| `comun/controladores/` | `ControladorCatalogo`: las 11 opciones que comparten los catálogos |
| `hu036/` | Etnias: `ContratoEtnias`, `Etnia`, su repositorio, su servicio y `EtniaControlador` |
| `hu037/` | Comunidades étnicas: `ContratoComunidadesEtnicas`, `ComunidadEtnica` y lo demás |

`application.properties` abre la base de prueba, `jdbc:h2:file:./data/prueba`, relativa a la
carpeta donde se corre el programa, y el menú principal la muestra en su primera línea, la de
`Base abierta`. `IpsApplication.main` la copia de `data/ips.mv.db` antes de arrancar Spring, si no
existe. El log de Spring está en `warn` y sin el cartel de arranque, así que por la terminal sale
solo el menú.

`spring.jpa.hibernate.ddl-auto=update` crea las tablas que faltan según las
entidades, y `spring.main.web-application-type=none` deja sin arrancar Tomcat y Spring Security.
Con el perfil `test`, los tests abren `jdbc:h2:mem:pruebas`.

El menú arranca con `sh ./mvnw spring-boot:run` desde la raíz del repo. Lista las historias por
su número, con "Etnias (HU-036)" en la opción 1 y "Comunidades étnicas (HU-037)" en la 2, y la 0
sale. El controlador de cada catálogo tiene 11 opciones:

- consultar, con el filtro de activos, inactivos o todos, cuántos hay activos y la versión vigente;
- crear por cambio oficial, cambiar el nombre o el código, inactivar y reactivar, y cada una pide
  confirmar antes de guardar;
- el historial de un código y el de las versiones;
- cargar un CSV, buscar por código o por nombre y comparar con la lista oficial.

Se comprobó el 2026-10-26 con la entrada desde un `printf`, en el contenedor de la sesión, que corre
Linux sin `LANG`. El 2026-10-27, `printf '0\n' | sh ./mvnw -q spring-boot:run` imprimió la línea de
la base y el menú, sin líneas de log, y nada en stderr.

La carga dice primero qué archivo espera y pide la ruta y los datos de la versión.
`ServicioCatalogo.leerCarga` lee y compara sin guardar, y `confirmarCarga` guarda. Cada operación
deja su fila en `CatalogoCambio`, la tabla de cambios común, y cada carga, la suya en
`CatalogoVersion`. `ServicioCatalogo.contarUsos` devuelve 0 hasta que exista el registro del
paciente (HU-004). Cada controlador pide su catálogo por el contrato, `ContratoEtnias` o
`ContratoComunidadesEtnicas`.

## 3. Recursos y cómo se reinicia cada uno

Comprobado el 2026-10-26 con corridas del menú en copias del repo, en el contenedor de la sesión.

- `data/ips.mv.db`: el archivo de la base, en git desde la v0.10. Tiene las tablas `ETNIA`,
  `COMUNIDAD_ETNICA`, `CATALOGO_VERSION` y `CATALOGO_CAMBIO` y dos secuencias para los números de
  las dos últimas. Trae las 7 etnias de la hoja 15, activas, con la versión 1, "Resolución 866 de
  2021"; ninguna comunidad étnica; y 7 filas de cambios, una por etnia creada. Se cargó con la
  opción 8 del menú de etnias y `src/test/resources/cargas/etnias-hoja-15.csv`. Se cargó en UTC, y
  el 2026-10-27 sus 8 horas, las 7 de los cambios y la de la carga, pasaron a la de Colombia: de las
  02:25 del 2026-10-27 a las 21:25 del 2026-10-26, con un `UPDATE` en la consola de H2. Se reinicia
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

Comprobado el 2026-10-27 con `git ls-files 'src/*' | xargs wc -l`: 3611 líneas en 39 archivos. De
Spring Initializr queda la mayor parte de `IpsApplicationTests.java`.

## 5. Sin describir todavía

Nada de lo que lista `git ls-files src` el 2026-10-26. Cada archivo nuevo que aparezca ahí se
describe en el PR que lo trae.

## 6. Huecos conocidos

Lo que está abierto o no se verificó contra datos reales. Cada hueco abre con su fecha y trae el
comando o la corrida que lo muestra, para que el próximo lector compruebe si sigue abierto. Se borra
en el PR que lo cierra, y el CHANGELOG guarda el rastro. Un PR que anuncia algo en el CHANGELOG mira
si eso cierra un hueco de esta lista.

- **2026-10-22: ningún catálogo oficial se descargó.** No se comprobó que la tabla Pais de SISPRO
  use el código numérico (D-03), que el archivo de la CIUO-88 A.C. traiga los cuatro niveles
  (HU-022), ni que exista el catálogo de comunidades étnicas (HU-037). Comando:
  `git ls-files '*.csv'` devuelve solo `src/test/resources/cargas/etnias-hoja-15.csv`, que se
  transcribió de la hoja 15 del anexo y no se descargó.
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
- **2026-10-27: dos partes de HU-036 y HU-037 esperan el registro del paciente, HU-004.** Cuántos
  pacientes usan un valor, en el escenario 19 de HU-036 y el 18 de HU-037: `contarUsos` devuelve 0.
  Y lo que RC-11 pide de un registro ya guardado, en el 11 de HU-036 y el 10 de HU-037, que no
  tiene código porque todavía no hay registros. Comando:
  `grep -n -A2 'public long contarUsos' src/main/java/com/ipssaludyvida/comun/servicios/*.java`.
