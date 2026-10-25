# DESIGN.md: cómo se escribe el código

> **Rol:** normativo sobre el código. Cada principio dice cómo se escribe el código y por qué. Manda
> sobre el código; `docs/REQUIREMENTS.md` manda sobre él. Describe reglas; el estado, qué rutas,
> recursos o funciones existen hoy, va en `docs/ARCHITECTURE.md`. **Régimen:** se corrige, y un
> principio nuevo o cambiado lleva su decisión. **Origen:** plantilla 1.2, sembrada el 2026-10-22
> (`docs/DECISIONS.md`, 2026-10-22 "Se adopta la plantilla 1.2").

Un cambio que rompe un principio de acá se rechaza, lo haya escrito una persona o un modelo. Ante la
duda, se copia la forma del código que ya existe en vez de inventar un idioma nuevo.

Todavía no hay código del producto. Los principios 1 y 2 salen de decisiones que el equipo ya
propuso para el modelo de datos, y los principios 6 a 8, de lo que el autor decidió el 2026-10-24
para la fase 0. El código que los cumpla no existe.

## 1. Los códigos del anexo

**Regla:** un componente que el anexo tipa Alfanumérico se guarda como texto, con su longitud y sus
ceros iniciales; uno tipado Numérico se guarda como número. La atención y el paciente guardan el
código, y el nombre para mostrar vive en la tabla del catálogo.

**Por qué:** como número, el municipio 05001 pasa a 5001 y el código de vía de ingreso "01" pasa a
1, y ninguno de los dos se puede entregar con la forma del anexo. El autor aceptó la regla el
2026-10-18 y quedó como propuesta D-18 en el tablero del equipo.

**Dónde está escrito:** `docs/context/MEMORIA-CONTEXTUAL.md`, entrada del 2026-10-18 "El autor
acepta que Alfanumérico se guarda como texto", y D-18 en `docs/REQUIREMENTS.md`, sección 6.

## 2. Un catálogo no borra

**Regla:** cada catálogo es una tabla con código, nombre y estado. Un valor se inactiva con su
motivo; no hay borrado físico. Un registro que usa un valor guarda el código y el nombre del día en
que se hizo.

**Por qué:** el artículo 7 de la Resolución 866 prohíbe a la IPS reducir los catálogos, y un
registro viejo tiene que seguir mostrando el valor con que se hizo (RC-4, RC-10 y RC-11; propuestas
D-20 y D-25).

**Dónde está escrito:** `docs/context/modelo-datos/modelo-entidad-relacion.md`, secciones "Tablas"
y "Reglas que el modelo tiene que cumplir".

## 3. Los tests

**Regla:** un escenario de una historia se da por cumplido solo con una corrida que lo muestra
(`CLAUDE.md`, sección 8). Hoy el único test es `DemoApplicationTests.contextLoads`, que generó
Spring Initializr, y no se corrió (`docs/ARCHITECTURE.md`, sección 6). Cómo se escribe el test de un
escenario está en la sección 9 de este archivo.

## 4. Presentación y registro

**Regla:** la pantalla de cada historia sigue su lámina en `docs/context/trello/mockups/`, donde una
etiqueta "Esc. N" dice qué escenario muestra cada panel.

La guía pide una aplicación web, y el docente aceptó terminal: el primer sprint tiene al menos el
esqueleto en terminal (`docs/DECISIONS.md`, 2026-10-24 "El primer sprint arranca en terminal").
Qué imprime el programa, a qué stream y qué se registra está en la sección 9, sin escribir todavía.

## 5. Los comentarios

**Regla:** un comentario que protege una línea dice qué se rompe sin ella, para que nadie la borre
sin reproducir ese fallo antes. Un comentario que afirma algo del código es una afirmación como
cualquier otra, y cambia en el mismo commit que el código que describe.

**Por qué:** las dos salen de comparar los scripts y los repos del autor el 2026-10-20. La primera
ya era la costumbre de los que más se habían roto, donde la línea que evita un fallo traía un
comentario que lo nombraba.

La segunda responde a un comentario que afirmó durante meses lo contrario de lo que hacía el código
de abajo.

**Dónde está escrito:** el idioma y la voz de un comentario están en `CLAUDE.md`, sección 9.

**Cómo se comprueba:** a mano, en cada PR que toca código. Quien cambia una línea lee los
comentarios que tiene encima, porque ninguna herramienta los revisa.

## 6. Modelo, vista y controlador

**Regla:** el código tiene tres partes. El modelo son las entidades, una interfaz de repositorio
por entidad y un servicio por historia con sus reglas de negocio. La vista es lo que se imprime en
la terminal, según la lámina de la historia, y el controlador es el menú, que lee la opción y llama
al servicio.

Un servicio no lee del teclado ni imprime: devuelve un valor o avisa el error con su mensaje, y el
menú lo muestra. Ningún repositorio borra. El Delete de un catálogo es inactivar, y lo hace su
servicio (`docs/REQUIREMENTS.md`, sección 2).

**Por qué:** la guía pide una aplicación web [2] y la fase 0 corre en terminal. Si los servicios no
tocan la terminal, la web reemplaza el menú y deja los servicios como están. Si un repositorio
puede borrar, basta una llamada para romper "Nada se borra".

**Dónde está escrito:** `docs/DECISIONS.md`, 2026-10-24 "El código se separa en modelo, vista y
controlador".

**Cómo se comprueba:** a mano, en cada PR de código. Ningún servicio usa la entrada o la salida de
la terminal, y ningún repositorio ofrece borrar.

## 7. La base común de los catálogos

**Regla:** las reglas que comparten los 8 catálogos de la fase 0, RC-1 a RC-11 y la búsqueda de
D-23, viven en una base común que usan sus 8 servicios. Cada servicio agrega solo lo de su
historia, como el padre leído del código en HU-022 (D-26). Una regla que usa una sola historia va
en el servicio de esa historia.

**Por qué:** escritas en cada servicio, las mismas reglas quedan en 8 lugares, y un error se
corrige 8 veces. La base se arma antes del paso 1, así que ningún catálogo empieza sin ella.

**Dónde está escrito:** `docs/DECISIONS.md`, 2026-10-24 "Los catálogos comparten una base común".

## 8. La base de datos

**Regla:** H2 en modo archivo, y el archivo de la base va en el repositorio. Se cambia por turnos,
y las pruebas corren sobre otra base, con otro nombre. El turno lo tiene quien tenga abierto el PR
que cambia el archivo, y la base de prueba arranca como copia del archivo.

**Por qué:** los datos quedan entre una corrida y la siguiente, y quien necesita un catálogo lo
encuentra ya cargado en el archivo.

El autor aceptó lo que cuesta. Git no mezcla dos cambios del archivo, y por eso los cambios van
por turnos. Correr el programa también lo cambia: las pruebas usan otra base para que un paciente de
prueba no llegue a git.

**Dónde está escrito:** `docs/DECISIONS.md`, 2026-10-24 "La base es H2 en un archivo que va en
git", 2026-10-24 "El archivo de la base trae los catálogos cargados", y del 2026-10-25 "El archivo
de la base se cambia por turnos", "Las pruebas corren sobre otra base", "El turno del archivo de la
base es del PR abierto" y "La base de prueba arranca como copia del archivo".

## 9. Sin escribir todavía

Este archivo es una semilla mientras esta lista tenga algo.

- Qué imprime cada pantalla del esqueleto en terminal, a qué stream y qué se registra.
- Qué propiedades de `application.properties` actualizan las tablas, crean la base y cambian su
  nombre para probar, con la corrida que lo muestre.
- Cómo se cambia el nombre de la base para probar sin subir ese cambio a git, porque
  `application.properties` va en el repositorio.
- Qué interfaz de Spring extiende cada repositorio, con la corrida que lo muestre, en el primer PR
  de código.
- Cómo se arma el menú: uno solo para las 12 historias, o uno principal que llama a un menú por
  entidad. Con uno solo, los 3 integrantes cambian el mismo archivo.
- Qué hace la parte web de `pom.xml`, con Spring Web MVC, Thymeleaf y Spring Security, mientras la
  fase 0 corre en terminal, y cómo pide HU-001 el usuario y la clave en la terminal.
- El paquete y el nombre del artefacto: hoy son `com.example.demo` y `demo`, los de Spring
  Initializr.
- El idioma de los identificadores del código.
- Cómo se escribe y se nombra el test de cada escenario, para que se vea qué escenario prueba.
- Cómo comparten la pantalla los 8 catálogos del paciente, que tienen los mismos escenarios en sus
  tarjetas (`docs/context/trello/tarjetas/`). Sus reglas ya tienen lugar en la sección 7.
