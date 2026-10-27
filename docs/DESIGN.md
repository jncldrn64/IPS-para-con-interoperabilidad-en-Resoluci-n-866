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
(`CLAUDE.md`, sección 8). Cada historia tiene su clase de test en su carpeta, como
`src/test/java/com/example/demo/hu036/HU036EtniasTest.java`, y cada escenario sus métodos,
`esc<N>_<alternativa>`: el número del escenario y el caso de su "Dado" que prueba. `@DisplayName`
repite la historia y el escenario, como "HU-036 Esc. 10".

Un escenario se cumple cuando cada caso de su "Dado" tiene su método y pasa, y una historia, cuando
se cumplen todos sus escenarios. Las reglas de este archivo que no son el escenario de una historia
van en `BaseComunTest`, y los casos límite que pidió el autor, como cargar las 7 etnias en las dos
entidades, en `CasosLimiteTest`. Las dos están en `src/test/java/com/example/demo/comun/`.

Un test que arranca Spring lleva `@ActiveProfiles("test")`. Sin él, el menú arranca dentro del test
y lo deja esperando el teclado, y el test abre el archivo de la base en vez de la base en memoria de
`src/test/resources/application-test.properties`. El CI corre `./mvnw test` en cada PR y en cada
cambio de `main`, con `.github/workflows/tests.yml`; cómo se corren a mano está en
`tests/README.md`.

Un escenario que pasa por la terminal, como cancelar antes de confirmar, se prueba abriendo el
controlador de su historia, como `new EtniaControlador(etnias, terminal)`, con una `Terminal` que
lee las respuestas de un `ByteArrayInputStream`. El test usa el constructor de `Terminal` que
recibe la entrada, y Spring, el que usa el teclado.

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
la terminal, según la lámina de la historia, y el controlador, uno por historia, pide los datos de
cada opción, llama al contrato e imprime. El menú principal solo elige la historia y abre su
controlador.

Un servicio no lee del teclado ni imprime: devuelve un valor o avisa el error con su mensaje, y el
controlador lo muestra. Ningún repositorio borra. El Delete de un catálogo es inactivar, y lo hace
su servicio (`docs/REQUIREMENTS.md`, sección 2).

Un repositorio de catálogo extiende `CatalogoRepositorio`, que extiende `Repository` de Spring Data
y declara solo `findAllByOrderByCodigoAsc`, `findById` y `save`. `CrudRepository`, y con él
`ListCrudRepository`, trae `deleteById`, `delete`, `deleteAll` y `deleteAllById`.

El código de una historia vive en su carpeta, como `hu036/`, y lo que usan varias historias, en
`comun/`. Las dos cuelgan de `src/main/java/com/example/demo/`, donde Maven y Spring buscan las
clases, y tienen las mismas subcarpetas:

- `interfaces/`: el contrato;
- `modelos/`: las entidades;
- `repositorios/`: el acceso a la base;
- `servicios/`: los métodos de la historia y sus validaciones;
- `controladores/`: el controlador, que lee la terminal y llama al contrato.

El contrato de una historia es una interfaz que extiende `CrudCatalogo` y agrega lo propio de la
historia, como la lista de la hoja 15 en `ContratoEtnias`. Su comentario dice qué escenario cumple
cada operación. El servicio implementa el contrato, y el controlador lo pide por la interfaz, nunca
por el servicio.

El menú principal vive en `comun/menu/`, con `Terminal`, la entrada que comparte con los
controladores. Lista las historias por su número y abre el controlador de la elegida, que cumple la
interfaz `Controlador`. Las 11 opciones que comparten los catálogos están en
`comun/controladores/ControladorCatalogo`, y el controlador de cada historia pone lo suyo, como la
comparación con la hoja 15 en `EtniaControlador`.

**Por qué:** la guía pide una aplicación web [2] y la fase 0 corre en terminal. Si los servicios no
tocan la terminal, la web reemplaza los controladores y deja los servicios como están. Cada
opción de un controlador de terminal ya dice qué datos pide y a qué operación del contrato llama, y
eso es lo que repite su versión web. Si un repositorio puede borrar, basta una llamada para romper
"Nada se borra".

Con una carpeta por historia, cada integrante trabaja en la suya, y lo que tiene una historia se
lee sin abrir las demás. El contrato dice qué hace la historia sin leer cómo lo hace. Con un solo
menú para todas, los 3 integrantes cambiaban el mismo archivo, y ahora el menú principal encuentra
el controlador de cada historia sin que nadie lo edite.

**Dónde está escrito:** `docs/DECISIONS.md`, 2026-10-24 "El código se separa en modelo, vista y
controlador", y del 2026-10-26 "El primer código arma la base común con etnias y comunidades
étnicas", "El código se ordena por historia, con lo común aparte", "Cada historia tiene su
contrato, y lo implementa su servicio" y "Cada historia tiene su controlador, y el menú común lo
abre".

**Cómo se comprueba:** a mano, en cada PR de código, que ningún servicio use la entrada o la salida
de la terminal. Que ningún repositorio ofrezca borrar lo comprueba
`BaseComunTest.ningunRepositorioPuedeBorrar`, y un repositorio nuevo se agrega a su lista. Que el
menú principal ordene las historias y abra la elegida lo comprueba
`BaseComunTest.elMenuPrincipalOrdenaLasHistoriasYAbreLaElegida`.

## 7. La base común de los catálogos

**Regla:** las reglas que comparten los 8 catálogos de la fase 0, RC-1 a RC-11 y la búsqueda de
D-23, viven en una base común que usan sus 8 servicios. Cada servicio agrega solo lo de su
historia, como el padre leído del código en HU-022 (D-26). Una regla que usa una sola historia va
en el servicio de esa historia.

Cada catálogo tiene su entidad y su tabla, y las 8 heredan de una clase base con el código, el
nombre, el estado y la versión. El código es la llave y se guarda como texto (sección 1). Cada
catálogo agrega lo suyo, como el padre de una ocupación, y el historial de los valores va en una
tabla de cambios común.

Un catálogo nuevo trae su carpeta con cinco clases, con `hu036/` de ejemplo (`docs/DECISIONS.md`,
2026-10-25 "HU-036 es el catálogo de ejemplo"):

- el contrato extiende `CrudCatalogo` de su entidad, como `ContratoEtnias`;
- la entidad extiende `Catalogo`, fija el largo del código con `@AttributeOverride` y su formato con
  `@Pattern` sobre `getCodigo`;
- el repositorio extiende `CatalogoRepositorio`;
- el servicio extiende `ServicioCatalogo`, implementa el contrato y le pasa a la base la clase de su
  entidad, como `super(Etnia.class, datos, validador, registro)`;
- el controlador extiende `ControladorCatalogo` y le pasa el número de la historia, el título y
  cómo se arma un valor, como `EtniaControlador`. El menú principal lo encuentra sin que se edite.

La base valida cada dato con su numeral, pide la referencia oficial para crear o cambiar un valor
(RC-3) y aplica RC-10 al inactivar y al reactivar. Cada operación deja su fila en la tabla de
cambios común, `CatalogoCambio`, por medio de `RegistroCatalogos`: el tipo de cambio, el nombre
anterior y el nuevo, el motivo, la referencia y la hora. Ninguna operación cambia un valor sin
dejar su fila.

La base también carga el CSV de RC-8 y RC-9. `leerCarga` lee y compara sin guardar, y
`confirmarCarga` guarda. Una historia con una lista fija del anexo, como HU-036, cambia dos métodos
de la base, `nombreOficial` y `revisarPrimeraCarga`.

La consulta con filtro, la búsqueda de D-23 y la comparación con una lista oficial también son de
la base. Cada contrato pone lo suyo: el nombre del catálogo en plural para los mensajes y la lista
contra la que se compara, que en HU-036 es la hoja 15 y en HU-037, el archivo que se sube.

**Por qué:** escritas en cada servicio, las mismas reglas quedan en 8 lugares, y un error se
corrige 8 veces. La base se arma antes del paso 1, así que ningún catálogo empieza sin ella. Un
enum no sirve para un catálogo: fija sus valores al compilar, y los del catálogo llegan de un
archivo oficial con el programa corriendo. Los motivos de RC-10 sí son un enum, `Motivo`, porque los
fija la regla común y no llegan de un archivo.

**Dónde está escrito:** `docs/DECISIONS.md`, 2026-10-24 "Los catálogos comparten una base común",
2026-10-25 "La base común es una clase con código, nombre, estado y versión", y del 2026-10-26 "El
primer código arma la base común con etnias y comunidades étnicas", "Los motivos de RC-10 son un
enum", "La tabla de cambios y RC-10 entran en la base común", "La carga lee primero y guarda al
confirmar" y "La consulta, la búsqueda y la comparación entran en la base común".

## 8. La base de datos

**Regla:** H2 en modo archivo, y el archivo de la base va en el repositorio. Se cambia por turnos,
y las pruebas corren sobre otra base, con otro nombre. El turno lo tiene quien tenga abierto el PR
que cambia el archivo, y la base de prueba arranca como copia del archivo.

El programa corre sobre la base de prueba, `data/prueba.mv.db`. `application.properties` abre
`jdbc:h2:file:./data/prueba`, y `DemoApplication.main` la copia de `data/ips.mv.db` si no existe.
`.gitignore` la deja fuera de git, así que una corrida no cambia el archivo de la base. Las rutas
son relativas a la carpeta donde se corre, y por eso el programa se corre desde la raíz del repo.

Quien tiene el turno abre el archivo de la base pasando su URL al arrancar:

```sh
sh ./mvnw spring-boot:run \
  -Dspring-boot.run.arguments=--spring.datasource.url=jdbc:h2:file:./data/ips
```

Esa corrida cambia los bytes de `data/ips.mv.db` aunque no guarde nada, porque H2 lo reescribe al
abrirlo y al cerrarlo. Para traer a la base de prueba los catálogos nuevos de `main`, se borra
`data/prueba.mv.db` y la corrida siguiente la copia otra vez. `spring.jpa.hibernate.ddl-auto=update`
crea en las dos las tablas que faltan según las entidades.

Se comprobó el 2026-10-26 desde la raíz del repo. Sin `data/prueba.mv.db`, una corrida la creó con
las 7 etnias, y `git status --short data` no mostró cambios; la corrida con el URL de `ips` dejó
`M data/ips.mv.db`.

**Por qué:** los datos quedan entre una corrida y la siguiente, y quien necesita un catálogo lo
encuentra ya cargado en el archivo.

El autor aceptó lo que cuesta. Git no fusiona dos cambios del archivo, y por eso los cambios van
por turnos. Correr el programa sobre el archivo también lo cambia, así que el programa corre sobre
la base de prueba, y un paciente de prueba no llega a git.

**Dónde está escrito:** `docs/DECISIONS.md`, 2026-10-24 "La base es H2 en un archivo que va en
git", 2026-10-24 "El archivo de la base trae los catálogos cargados", y del 2026-10-25 "El archivo
de la base se cambia por turnos", "Las pruebas corren sobre otra base", "El turno del archivo de la
base es del PR abierto" y "La base de prueba arranca como copia del archivo", y 2026-10-26 "El
programa trabaja sobre una copia del archivo de la base".

## 9. Sin escribir todavía

Este archivo es una semilla mientras esta lista tenga algo.

- Qué imprime cada pantalla del esqueleto en terminal, a qué stream y qué se registra.
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
