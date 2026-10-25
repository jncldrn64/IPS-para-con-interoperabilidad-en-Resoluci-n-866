# ARCHITECTURE.md: qué es el código hoy

> **Rol:** descriptivo. Dice qué es el código hoy, y cada sección nombra la fecha en que se comprobó
> contra él. Si contradice al código, el código gana y este archivo es el que está mal. Cómo tiene
> que escribirse el código es `docs/DESIGN.md`. **Régimen:** se corrige, y un hueco se borra en el
> PR que lo cierra. **Origen:** plantilla 1.2, sembrada el 2026-10-22 (`docs/DECISIONS.md`,
> 2026-10-22 "Se adopta la plantilla 1.2").

Una sección se describe cuando un PR de código toca su área, no antes. Lo que falta describir se
lista en la sección 5.

## 1. El repo, archivo por archivo

Comprobado el 2026-10-25 contra `git ls-files`.

| Archivo o carpeta | Qué es |
|---|---|
| `CLAUDE.md`, `CHANGELOG.md` | Documentos canónicos de la raíz: método e historia |
| `AGENTS.md` | Dos oraciones que mandan a `CLAUDE.md` a quien abre ese archivo primero |
| `README.md` | Portada para GitHub; remite a `CLAUDE.md` |
| `HELP.md` | Enlaces de ayuda que genera Spring Initializr |
| `LICENSE` | GNU Affero General Public License, versión 3 |
| `pom.xml` | Proyecto Maven `com.example:demo`, padre `spring-boot-starter-parent` 4.0.8, Java 21 |
| `mvnw`, `mvnw.cmd` | Envoltorio de Maven, sin su carpeta `.mvn/` (sección 6) |
| `src/main/java/com/example/demo/DemoApplication.java` | Clase `DemoApplication`: solo arranca Spring |
| `src/main/resources/application.properties` | Una propiedad: `spring.application.name=demo` |
| `src/test/java/com/example/demo/DemoApplicationTests.java` | Test `contextLoads`, vacío |
| `tests/README.md` | Cómo se corren los tests |
| `docs/*.md` | Los siete documentos canónicos de `docs/` |
| `docs/context/` | Las tarjetas y las láminas de las 30 historias, el modelo E-R y la memoria contextual; lo explica su `README.md` |

Dependencias que declara `pom.xml`: Spring Web MVC, Thymeleaf con su extra de Spring Security,
Spring Security, Spring Data JPA, Validation, DevTools, la consola de H2, Lombok y los controladores
de H2, SQL Server, MySQL y MariaDB, más sus pares de test. Para listarlas:
`grep -A1 '<groupId>' pom.xml | grep artifactId`.

## 2. Dónde vive cada cosa

Comprobado el 2026-10-24.

| Qué | Variable o símbolo | Ruta |
|---|---|---|
| Configuración | `spring.application.name` | `src/main/resources/application.properties` |
| Punto de entrada | `DemoApplication.main` | `src/main/java/com/example/demo/` |
| Historias de usuario | Una tarjeta por historia, más la de reglas comunes | `docs/context/trello/tarjetas/` |
| Modelo de datos propuesto | 24 tablas en Mermaid | `docs/context/modelo-datos/modelo-entidad-relacion.md` |

No hay propiedad de base de datos. Qué base usa la aplicación al arrancar no se verificó.

## 3. Recursos y cómo se reinicia cada uno

Comprobado el 2026-10-22: el código no crea archivos ni tablas propias. Lo que Spring Boot cree por
su cuenta al arrancar no se verificó, porque la aplicación no se arrancó (sección 6).

## 4. Tamaño

Comprobado el 2026-10-22 con `git ls-files 'src/*' | xargs wc -l`: 27 líneas en 3 archivos, todas
generadas por Spring Initializr.

## 5. Sin describir todavía

Todo el producto: no hay código propio. La lista sale de `git ls-files src`, y cada archivo nuevo
que aparezca ahí se describe en el PR que lo trae.

## 6. Huecos conocidos

Lo que está abierto o no se verificó contra datos reales. Cada hueco abre con su fecha y trae el
comando o la corrida que lo muestra, para que el próximo lector compruebe si sigue abierto. Se borra
en el PR que lo cierra, y el CHANGELOG guarda el rastro. Un PR que anuncia algo en el CHANGELOG mira
si eso cierra un hueco de esta lista.

- **2026-10-22: el envoltorio de Maven no corre.** Falta `.mvn/wrapper/maven-wrapper.properties`, y
  el contenedor de la sesión no tiene `mvn`. Comando: `sh ./mvnw -v`, que responde "cannot open
  ./.mvn/wrapper/maven-wrapper.properties". Por eso ni el build ni `contextLoads` se corrieron.
- **2026-10-22: ningún catálogo oficial se descargó.** No se comprobó que la tabla Pais de SISPRO
  use el código numérico (D-03), que el archivo de la CIUO-88 A.C. traiga los cuatro niveles
  (HU-022), ni que exista el catálogo de comunidades étnicas (HU-037). Comando:
  `git ls-files '*.csv'` devuelve cero.
