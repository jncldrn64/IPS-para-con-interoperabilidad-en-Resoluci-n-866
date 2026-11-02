# tests/: cómo se corren los tests

Este README solo describe esta carpeta, así que no es un documento canónico ni pide permiso. Cómo
se escriben los tests es `docs/DESIGN.md`.

Los tests de Java viven donde Maven los busca, en `src/test/java/`. Esta carpeta guarda solo este
README hasta que haga falta otra cosa.

## 1. Correr todo

`./mvnw test`, desde la raíz del repo, corre todas las suites y termina con `BUILD SUCCESS` o
`BUILD FAILURE`. `./mvnw test -Dtest=HU036EtniasTest` corre una sola.

Las condiciones: Java 21 en el `PATH`, y red la primera vez, para que el envoltorio baje Maven
3.9.11 y las dependencias a `~/.m2/`. Si `./mvnw` responde "Permission denied", `sh ./mvnw test`
hace lo mismo.

Se comprobó el 2026-11-01 en el contenedor de la sesión, con Linux: 228 tests, y `BUILD SUCCESS`.
En Windows va `mvnw.cmd test`, que no se corrió (`docs/ARCHITECTURE.md`, sección 6).

Los tests no tocan el archivo de la base: con `@ActiveProfiles("test")` leen
`src/test/resources/application-test.properties`, que abre una base en memoria. Esa base arranca
vacía, cada test crea los valores que usa y `@Transactional` los deshace al terminar.

## 2. Las suites

Están bajo `src/test/java/com/ipssaludyvida/`, en la carpeta de su historia o en `comun/`. Cómo se
nombra el test de un escenario está en `docs/DESIGN.md`, sección 3.

- `IpsApplicationTests.contextLoads`: comprueba que el contexto de Spring arranca. La generó Spring
  Initializr.
- `comun/BaseComunTest`, con 16 tests de las reglas que no son un escenario: los ceros iniciales del
  código, la hora de Colombia en la tabla de cambios y en la carga, inactivar sin borrar, que
  cambiar el nombre no reactive un valor, los motivos que pone el sistema, "Creado por error" fuera
  de la versión vigente, reactivar con otra referencia, la copia de la base de prueba, que ningún
  repositorio tenga un método para borrar, que el menú principal ordene las historias, abra la
  elegida y diga qué base abrió, el corte de la terminal cuando se acaba la entrada, los tres casos
  de la lista de rechazadas que ya existe y el largo de la columna del código.
- `hu036/HU036EtniasTest`, con 44 tests, y `hu037/HU037ComunidadesEtnicasTest`, con 38: los
  escenarios de cada historia, de la carga a la consulta, la búsqueda y la comparación con la
  lista oficial. Los de cancelar, y los que comprueban lo que el menú dice en la búsqueda sin
  resultados, el catálogo vacío y la inactivación, pasan por el controlador de la historia, con
  las respuestas escritas en el test. Esos tests leen lo que el menú imprimió y llevan
  `@Timeout(10)` (`docs/DESIGN.md`, sección 3).
- `hu001/HU001InicioDeSesionTest`, con 33 tests: los 14 escenarios de HU-001 y la precondición de
  un usuario de prueba por cargo. Los de la pantalla pasan por el menú principal o por el
  controlador de un catálogo, y los del escenario 12 usan su propio `SesionServicio` con un reloj
  que el test adelanta. `hu001/Usuarios` crea el usuario de cada cargo y abre su sesión; los tests
  de HU-036 y HU-037 que pasan por el controlador entran como profesional de estadística.
- `hu022/HU022OcupacionesTest`, con 48 tests: los 28 escenarios de HU-022. Casi todos usan una
  estructura de prueba que arma el test, con 10 grandes grupos, 34 subgrupos principales, 117
  subgrupos y 401 grupos primarios, y que cada test cambia. `esc1_archivoOficial` carga
  `data/catalogos/ciuo-88-ac.csv`.
- `hu026/HU026MunicipiosTest`, con 35 tests: los 25 escenarios de HU-026, con un archivo de prueba
  de 9 municipios de 8 departamentos y Albania en tres, porque el de la DIVIPOLA no se ha
  descargado.
- `comun/CasosLimiteTest`, con 13 tests: las 7 etnias en las dos entidades, por el menú y por el
  CSV, con y sin valores previos, y los archivos que una hoja de cálculo guarda distinto, como el
  que trae la marca del "CSV UTF-8", el separado por comas o el que no está en UTF-8.

Los tests de carga leen `src/test/resources/cargas/etnias-hoja-15.csv`, o escriben su CSV en una
carpeta temporal de JUnit, así que no dependen de ningún archivo fuera del repo.

## 3. Qué escenarios tienen test

Este comando lista los números de escenario de HU-036 que tienen al menos un método:

```sh
grep -ohE 'esc[0-9]+_' src/test/java/com/ipssaludyvida/hu036/*.java | sort -uV
```

El 2026-10-26 imprimió los 23 números de la tarjeta, y con `hu037` en la ruta, los 22 de HU-037.
Que un número aparezca no basta, porque el escenario se completa cuando cada caso de su "Dado"
tiene su método. Ese día a HU-037 le faltaban 4 casos, que trae la v0.13. Dos partes esperan el
registro del paciente, HU-004: cuántos pacientes usan un valor, en el escenario 19 de HU-036, y lo
que RC-11 pide de un registro ya guardado, en el 11.

El 2026-10-31, con `hu001` en la ruta, imprimió los 14 números de HU-001, y el 2026-11-01, con
`hu022`, los 28 de HU-022, y con `hu026`, los 25 de HU-026.

## 4. El CI

`.github/workflows/tests.yml` corre `./mvnw -B test` en GitHub Actions, con Java 21, en cada PR y en
cada cambio de `main`. El PR muestra el check `tests` en verde o en rojo, y el registro de la
corrida está en la pestaña Actions del fork. El check informa y no impide fusionar
(`docs/DECISIONS.md`, 2026-10-26 "main queda abierta a los integrantes").
