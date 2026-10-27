# tests/: cómo se corren los tests

Este README solo describe esta carpeta, así que no es un documento canónico ni pide permiso. Cómo
se escriben los tests es `docs/DESIGN.md`.

Los tests de Java viven donde Maven los busca, en `src/test/java/`. Esta carpeta guarda solo este
README hasta que haga falta otra cosa.

## 1. Correr todo

`./mvnw test`, desde la raíz del repo, corre todas las suites y termina con `BUILD SUCCESS` o
`BUILD FAILURE`. `./mvnw test -Dtest=CatalogosTest` corre una sola.

Las condiciones: Java 21 en el `PATH`, y red la primera vez, para que el envoltorio baje Maven
3.9.11 y las dependencias a `~/.m2/`. Si `./mvnw` responde "Permission denied", `sh ./mvnw test`
hace lo mismo.

Se comprobó el 2026-10-26 en el contenedor de la sesión, con Linux: 10 tests, y `BUILD SUCCESS`.
En Windows va `mvnw.cmd test`, que no se corrió (`docs/ARCHITECTURE.md`, sección 6).

Los tests no tocan el archivo de la base: con `@ActiveProfiles("test")` leen
`src/test/resources/application-test.properties`, que abre una base en memoria. Esa base arranca
vacía, cada test crea los valores que usa y `@Transactional` los deshace al terminar.

## 2. Las suites

Todas están en `src/test/java/com/example/demo/`. Cómo se nombra el test de un escenario está en
`docs/DESIGN.md`, sección 3.

- `DemoApplicationTests.contextLoads`: comprueba que el contexto de Spring arranca. La generó Spring
  Initializr.
- `BaseComunTest`, con 5 tests de las reglas de `docs/DESIGN.md` que no son un escenario: los ceros
  iniciales del código, inactivar sin borrar, que cambiar el nombre no reactive un valor, que ningún
  repositorio de catálogo tenga un método para borrar y el largo de la columna del código.
- `HU036EtniasTest` y `HU037ComunidadesEtnicasTest`: los escenarios de cada historia. Hoy prueban el
  escenario 10 de HU-036 y el 9 de HU-037, en los casos del código que no cumple el numeral 13 y del
  nombre vacío.

## 3. Qué escenarios tienen test

Este comando lista los números de escenario de HU-036 que tienen al menos un método:

```sh
grep -ohE 'esc[0-9]+_' src/test/java/com/example/demo/HU036*.java | sort -uV
```

El 2026-10-26 imprimió `esc10_`: de los 23 escenarios de la tarjeta, uno tiene test. Que un número
aparezca no basta, porque el escenario se completa cuando cada caso de su "Dado" tiene su método.

## 4. El CI

`.github/workflows/tests.yml` corre `./mvnw -B test` en GitHub Actions, con Java 21, en cada PR y en
cada cambio de `main`. El PR muestra el check `tests` en verde o en rojo, y el registro de la
corrida está en la pestaña Actions del fork. El check informa y no bloquea la mezcla
(`docs/DECISIONS.md`, 2026-10-26 "main queda abierta a los integrantes").
