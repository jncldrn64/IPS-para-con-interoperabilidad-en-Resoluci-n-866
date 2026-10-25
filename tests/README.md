# tests/: cómo se corren los tests

Este README solo describe esta carpeta, así que no es un documento canónico ni pide permiso. Cómo
se escriben los tests es `docs/DESIGN.md`.

Los tests de Java viven donde Maven los busca, en `src/test/java/`. Esta carpeta guarda solo este
README hasta que haga falta otra cosa.

## 1. Correr todo

`./mvnw test` corre todas las suites y termina con `BUILD SUCCESS` o `BUILD FAILURE`.

Hoy no corre: falta `.mvn/wrapper/maven-wrapper.properties` (`docs/ARCHITECTURE.md`, sección 6). Con
Maven instalado, `mvn test` desde la raíz debería servir; no se verificó, porque el contenedor de la
sesión del 2026-10-22 no tenía `mvn`.

## 2. Las suites

- `DemoApplicationTests.contextLoads`, en `src/test/java/com/example/demo/`: comprueba que el
  contexto de Spring arranca. La generó Spring Initializr y no se ha corrido.
