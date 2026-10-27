# DESIGN.md: cómo se escribe el código

> **Rol:** normativo sobre el código. Cada principio dice cómo se escribe el código y por qué. Manda
> sobre el código; `docs/REQUIREMENTS.md` manda sobre él. Describe reglas; el estado, qué rutas,
> recursos o funciones existen hoy, va en `docs/ARCHITECTURE.md`. **Régimen:** se corrige, y un
> principio nuevo o cambiado lleva su decisión. **Origen:** plantilla 1.2, sembrada el 2026-10-22
> (`docs/DECISIONS.md`, 2026-10-22 "Se adopta la plantilla 1.2").

Un cambio que rompe un principio de acá se rechaza, lo haya escrito una persona o un modelo. Ante la
duda, se copia la forma del código que ya existe en vez de inventar un idioma nuevo.

Los principios 1 y 2 salen de decisiones que el equipo ya propuso para el modelo de datos, y los
principios 6 a 8, de lo que el autor decidió para la fase 0. Qué código los cumple hoy está en
`docs/ARCHITECTURE.md`, sección 2.

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
(`CLAUDE.md`, sección 8). Cada historia tiene su clase de test, como `HU036EtniasTest`, y cada
escenario sus métodos, `esc<N>_<alternativa>`: el número del escenario y el caso de su "Dado" que
prueba. `@DisplayName` repite la historia y el escenario, como "HU-036 Esc. 10".

Un escenario se cumple cuando cada caso de su "Dado" tiene su método y pasa, y una historia, cuando
se cumplen todos sus escenarios. Las reglas de este archivo que no son el escenario de una historia
van en `BaseComunTest`.

Un test que arranca Spring lleva `@ActiveProfiles("test")`. Sin él, el menú arranca dentro del test
y lo deja esperando el teclado, y el test abre el archivo de la base en vez de la base en memoria de
`src/test/resources/application-test.properties`. El CI corre `./mvnw test` en cada PR y en cada
cambio de `main`, con `.github/workflows/tests.yml`; cómo se corren a mano está en
`tests/README.md`.

**Por qué:** con el número del escenario en el nombre, la salida de `./mvnw test` dice qué
escenarios de la tarjeta pasan. Un escenario que mete un valor donde no va, como el 10 de HU-036,
falla si alguien borra la regla que lo rechaza: se comprobó el 2026-10-26 quitando el `@Pattern` de
`Etnia`.

**Dónde está escrito:** `docs/DECISIONS.md`, 2026-10-26 "Un escenario se prueba con un test que
lleva su número" y 2026-10-26 "Un CI corre los tests en cada PR".

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

Un repositorio de catálogo extiende `CatalogoRepositorio`, que extiende `Repository` de Spring Data
y declara solo `findAllByOrderByCodigoAsc`, `findById` y `save`. `CrudRepository`, y con él
`ListCrudRepository`, trae `deleteById`, `delete`, `deleteAll` y `deleteAllById`.

**Por qué:** la guía pide una aplicación web [2] y la fase 0 corre en terminal. Si los servicios no
tocan la terminal, la web reemplaza el menú y deja los servicios como están. Si un repositorio
puede borrar, basta una llamada para romper "Nada se borra".

**Dónde está escrito:** `docs/DECISIONS.md`, 2026-10-24 "El código se separa en modelo, vista y
controlador" y 2026-10-26 "El primer código arma la base común con etnias y comunidades étnicas".

**Cómo se comprueba:** a mano, en cada PR de código, que ningún servicio use la entrada o la salida
de la terminal. Que ningún repositorio de catálogo ofrezca borrar lo comprueba
`BaseComunTest.ningunRepositorioDeCatalogoPuedeBorrar`, y un repositorio nuevo se agrega a su lista.

## 7. La base común de los catálogos

**Regla:** las reglas que comparten los 8 catálogos de la fase 0, RC-1 a RC-11 y la búsqueda de
D-23, viven en una base común que usan sus 8 servicios. Cada servicio agrega solo lo de su
historia, como el padre leído del código en HU-022 (D-26). Una regla que usa una sola historia va
en el servicio de esa historia.

Cada catálogo tiene su entidad y su tabla, y las 8 heredan de una clase base con el código, el
nombre, el estado y la versión. El código es la llave y se guarda como texto (sección 1). Cada
catálogo agrega lo suyo, como el padre de una ocupación, y el historial de los valores va en una
tabla de cambios común.

Un catálogo nuevo trae tres clases y su entrada en el menú, con `Etnia` de ejemplo
(`docs/DECISIONS.md`, 2026-10-25 "HU-036 es el catálogo de ejemplo"):

- la entidad extiende `Catalogo`, fija el largo del código con `@AttributeOverride` y su formato con
  `@Pattern` sobre `getCodigo`;
- el repositorio extiende `CatalogoRepositorio`;
- el servicio extiende `ServicioCatalogo`, que valida el valor antes de guardarlo y no le cambia el
  estado ni la versión a un código que ya existe.

**Por qué:** escritas en cada servicio, las mismas reglas quedan en 8 lugares, y un error se
corrige 8 veces. La base se arma antes del paso 1, así que ningún catálogo empieza sin ella. Un
enum no sirve para un catálogo: fija sus valores al compilar, y los del catálogo llegan de un
archivo oficial con el programa corriendo.

**Dónde está escrito:** `docs/DECISIONS.md`, 2026-10-24 "Los catálogos comparten una base común",
2026-10-25 "La base común es una clase con código, nombre, estado y versión" y 2026-10-26 "El primer
código arma la base común con etnias y comunidades étnicas".

## 8. La base de datos

**Regla:** H2 en modo archivo, y el archivo de la base va en el repositorio. Se cambia por turnos,
y las pruebas corren sobre otra base, con otro nombre. El turno lo tiene quien tenga abierto el PR
que cambia el archivo, y la base de prueba arranca como copia del archivo.

`application.properties` abre `jdbc:h2:file:./data/ips`. H2 crea el archivo si no existe, y
`spring.jpa.hibernate.ddl-auto=update` crea las tablas que faltan según las entidades. La ruta es
relativa a la carpeta donde se corre, así que el programa se corre desde la raíz del repo.

La base de prueba se copia del archivo y se abre pasando su URL al arrancar, sin tocar
`application.properties`:

```sh
cp data/ips.mv.db data/prueba.mv.db
sh ./mvnw spring-boot:run \
  -Dspring-boot.run.arguments=--spring.datasource.url=jdbc:h2:file:./data/prueba
```

Se comprobó el 2026-10-26 en una copia del repo: la lista de etnias salió de `prueba`, y la de
`ips` siguió sin el valor que se agregó en la prueba. `.gitignore` deja `data/prueba.*` fuera de
git.

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
- Cómo se arma el menú: uno solo para las 12 historias, o uno principal que llama a un menú por
  entidad. Con uno solo, los 3 integrantes cambian el mismo archivo.
- Si la fase 0 sigue en terminal o pasa a controladores web que se prueban con Postman, según lo
  que acepte el docente, y cómo pide HU-001 el usuario y la clave. Mientras corre en terminal,
  `spring.main.web-application-type=none` deja sin arrancar la parte web de `pom.xml`.
- El paquete y el nombre del artefacto: hoy son `com.example.demo` y `demo`, los de Spring
  Initializr.
- El idioma de los identificadores del código. El primer código usa español, salvo los nombres
  que pide Spring Data, como `findById` y `save`, y el autor no lo ha decidido.
- De dónde sacan los catálogos los tests de HU-004. La base en memoria de los tests arranca vacía,
  y cada test crea los valores que usa.
- Cómo comparten la pantalla los 8 catálogos del paciente, que tienen los mismos escenarios en sus
  tarjetas (`docs/context/trello/tarjetas/`). Sus reglas ya tienen lugar en la sección 7.
