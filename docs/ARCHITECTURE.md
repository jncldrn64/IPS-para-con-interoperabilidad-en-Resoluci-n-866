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
| `pom.xml` | Proyecto Maven `com.example:demo`, padre `spring-boot-starter-parent` 4.0.8, Java 21 |
| `mvnw`, `mvnw.cmd`, `.mvn/wrapper/` | Envoltorio de Maven: la primera corrida baja Maven 3.9.11 |
| `src/main/java/com/example/demo/` | `DemoApplication`, que arranca Spring, y los paquetes |
| `src/main/resources/application.properties` | La base en archivo y el arranque sin servidor web |
| `src/test/java/com/example/demo/` | `DemoApplicationTests` y `CatalogosTest` (`tests/README.md`) |
| `src/test/resources/application-test.properties` | La base de los tests, en memoria |
| `tests/README.md` | Cómo se corren los tests |
| `docs/*.md` | Los siete documentos canónicos de `docs/` |
| `docs/context/` | Las tarjetas y las láminas de las 30 historias, el modelo E-R y la memoria contextual; lo explica su `README.md` |

Dependencias que declara `pom.xml`: Spring Web MVC, Thymeleaf con su extra de Spring Security,
Spring Security, Spring Data JPA, Validation, DevTools, la consola de H2, Lombok y los controladores
de H2, SQL Server, MySQL y MariaDB, más sus pares de test. Para listarlas:
`grep -A1 '<groupId>' pom.xml | grep artifactId`.

## 2. Dónde vive cada cosa

Comprobado el 2026-10-26.

| Qué | Variable o símbolo | Ruta |
|---|---|---|
| Configuración | `spring.datasource.url` y dos más | `src/main/resources/application.properties` |
| Punto de entrada | `DemoApplication.main` | `src/main/java/com/example/demo/` |
| Historias de usuario | Una tarjeta por historia, más la de reglas comunes | `docs/context/trello/tarjetas/` |
| Modelo de datos propuesto | 24 tablas en Mermaid | `docs/context/modelo-datos/modelo-entidad-relacion.md` |

| Paquete, bajo `src/main/java/com/example/demo/` | Qué guarda |
|---|---|
| `modelo/` | `Catalogo`, la clase base, y sus dos hijas: `Etnia` y `ComunidadEtnica` |
| `repositorio/` | `CatalogoRepositorio`, con tres métodos y ningún borrado, y uno por catálogo |
| `servicio/` | `CrudCatalogo`, que usa el menú, la base `ServicioCatalogo` y uno por catálogo |
| `menu/` | `MenuPrincipal`, el menú en terminal: un `switch` principal y un submenú por catálogo |

`application.properties` abre la base `jdbc:h2:file:./data/ips`, relativa a la carpeta donde se
corre el programa, y el registro de arranque la muestra en la línea `Database JDBC URL`.
`spring.jpa.hibernate.ddl-auto=update` crea las tablas que faltan según las entidades, y
`spring.main.web-application-type=none` deja sin arrancar Tomcat y Spring Security. Con el perfil
`test`, los tests abren `jdbc:h2:mem:pruebas`.

El menú arranca con `sh ./mvnw spring-boot:run` desde la raíz del repo: 1 abre etnias, 2
comunidades étnicas y 0 sale. Cada catálogo lista, agrega o cambia el nombre de un valor, e
inactiva. Se comprobó el 2026-10-26 con la entrada desde un `printf`, en una copia del repo en el
contenedor de la sesión, que corre Linux sin `LANG`.

## 3. Recursos y cómo se reinicia cada uno

Comprobado el 2026-10-26 con corridas del menú en copias del repo, en el contenedor de la sesión.

- `data/ips.mv.db`: el archivo de la base. H2 lo crea la primera vez que arranca el programa, y con
  dos etnias pesó 24.576 bytes. Este PR no lo sube, porque todavía no trae un catálogo cargado
  (`docs/DESIGN.md`, sección 8). Hoy se reinicia borrándolo, y la corrida siguiente lo crea vacío.
- `data/prueba.mv.db`: la base de prueba, una copia del archivo que se abre con otro URL
  (`docs/DESIGN.md`, sección 8). `.gitignore` la deja fuera de git, y se reinicia copiando otra vez
  el archivo de la base.
- `target/`: lo que compila Maven. `.gitignore` lo deja fuera, y cada build lo rehace.
- `~/.m2/`, fuera del repo: ahí guarda el envoltorio Maven 3.9.11, en `wrapper/dists/`, y Maven las
  dependencias. La primera corrida los baja de `repo.maven.apache.org`, así que pide red.

## 4. Tamaño

Comprobado el 2026-10-26 con `git ls-files 'src/*' | xargs wc -l`: 483 líneas en 16 archivos. De
Spring Initializr quedan `DemoApplication.java` entero y la mayor parte de
`DemoApplicationTests.java`.

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
  `git ls-files '*.csv'` devuelve cero.
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
