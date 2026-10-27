# REQUIREMENTS.md: qué tiene que ser verdad, y para quién

> **Rol:** normativo, el más alto sobre el producto. Dice qué tiene que ser verdad para que el
> programa esté bien hecho, y para quién. No lleva fechas ni orden de trabajo, que van en
> `docs/ROADMAP.md`. No dice por qué ganó una forma, que va en `docs/DECISIONS.md`. **Régimen:** se
> corrige, y cada cambio lleva su decisión. **Origen:** plantilla 1.2, sembrada el 2026-10-22
> (`docs/DECISIONS.md`, 2026-10-22 "Se adopta la plantilla 1.2").

Los corchetes, como [2.3], remiten a los numerales de la guía del docente, "Proyecto de Clase, IPS
Salud y Vida". "Numeral N del anexo" es un dato del anexo técnico de la Resolución 866 de 2021.

## 1. Para quién es

La IPS Salud y Vida necesita registrar a sus pacientes y sus atenciones con los datos que fija la
Resolución 866 de 2021, para que la información se pueda intercambiar con otros prestadores. Hoy no
tiene un sistema que lo haga.

Lo usan tres cargos, con los permisos de la matriz de la tarjeta de reglas comunes
(`docs/context/trello/tarjetas/RC-reglas-comunes.md`, sección "Matriz de permisos"):

- **Auxiliar de admisión:** registra, consulta, actualiza y corrige los datos del paciente, y
  registra, actualiza, corrige y anula su oposición a la donación y su voluntad anticipada.
- **Médico tratante:** registra, consulta, corrige y anula las atenciones, y consulta al paciente,
  la oposición y la voluntad anticipada sin cambiarlos.
- **Profesional de estadística:** carga los catálogos desde el archivo oficial y los mantiene
  iguales a la fuente. No ve pacientes ni atenciones, solo cuántos pacientes usan un valor (D-02,
  propuesta del equipo).

Los tres consultan los catálogos, y ninguno crea usuarios ni asigna cargos, porque no hay un cargo
de administrador (sección 4). El docente los llama roles.

Si falla, el paciente repite sus datos en cada atención y los datos guardados no sirven para
intercambiarse, porque no usan los códigos oficiales.

También lo lee el docente, que evalúa el backlog, la modelación de datos y el código del semestre.

## 2. Qué tiene que ser verdad

El detalle de cada historia, con sus escenarios, está en su tarjeta
(`docs/context/trello/tarjetas/`), y las reglas comunes RC-1 a RC-11, en la tarjeta de reglas
comunes. Lo de abajo es lo que no puede ser falso en ninguna historia.

1. **Cada dato se valida con su numeral del anexo:** valores permitidos, formato y longitud (RC-7).
2. **Todo campo con más de 5 opciones seleccionables tiene entidad propia y su CRUD** [2.3]. "Más de
   5" quiere decir 6 o más valores (`docs/DECISIONS.md`, 2026-10-22 "El umbral de catálogo es más
   de 5 valores").
3. **La IPS no modifica, reduce ni adiciona los catálogos oficiales** (artículo 7 de la Resolución
   866). Un catálogo se carga desde su archivo oficial, con su versión, y un valor se crea o cambia
   solo con la referencia de un cambio oficial (RC-3, RC-8 y RC-9).
4. **Nada se borra.** El Delete de un catálogo es inactivar con un motivo de lista cerrada, y los
   registros que ya usan el valor lo conservan (RC-4 y RC-10; D-20, propuesta del equipo).
5. **Corregir agrega, no reemplaza:** "se agregará el nuevo dato con la fecha, hora, nombre e
   identificación de quien hizo la corrección" (artículo 10, parágrafo 2; RC-6).
6. **Un registro conserva el código y el nombre que tenía el valor el día en que se hizo** (RC-11;
   D-25, propuesta del equipo).
7. **El paciente cumple las reglas de la guía** [2.1]: una o varias nacionalidades, fecha y hora de
   nacimiento por separado, solo ocupaciones hijas de la CIUO-88 A.C., una o varias categorías de
   discapacidad y la ciudad de residencia con su código DANE.
8. **La oposición a la donación y la voluntad anticipada son estructuras aparte, con su CRUD**
   [2.1].

## 3. El paciente y sus 16 atributos

Según el docente, el paciente tiene 16 atributos. El primer sprint no puede entregarlo si le falta
un catálogo del que depende un atributo: por eso entran los 8 catálogos con el registro. El conteo
de 16 es una hipótesis del equipo, y cuadra si los apellidos cuentan como un dato, los nombres como
otro y la fecha y hora de nacimiento como uno solo.

| # | Atributo | Numeral del anexo | Qué es en el modelo | Historia del catálogo |
|---|---|---|---|---|
| 1 | Nacionalidad | 1 | Relación de muchos a muchos con Países (una o varias) | HU-018 |
| 2 | Tipo de documento | 2 | Relación con catálogo (14 valores) | HU-034 |
| 3 | Número de documento | 2 | Valor del paciente | ninguna |
| 4 | Apellidos | 3 | Valor del paciente (dos columnas) | ninguna |
| 5 | Nombres | 3 | Valor del paciente (dos columnas) | ninguna |
| 6 | Fecha y hora de nacimiento | 4 | Valor del paciente (dos columnas) | ninguna |
| 7 | Sexo biológico | 5 | Lista fija de 3 valores | ninguna |
| 8 | Identidad de género | 6 | Lista fija de 5 valores | ninguna |
| 9 | Ocupación | 7 | Relación con catálogo jerárquico; solo ocupaciones hijas | HU-022 |
| 10 | Discapacidad | 10 | Relación de muchos a muchos (8 valores) | HU-035 |
| 11 | País de residencia | 11 | Relación con Países, un solo valor | HU-018 |
| 12 | Municipio de residencia | 12 | Relación con catálogo (DIVIPOLA), si reside en Colombia | HU-026 |
| 13 | Etnia | 13 | Relación con catálogo (7 valores) | HU-036 |
| 14 | Comunidad étnica | 13 | Relación con catálogo, si la etnia es de la 01 a la 06 | HU-037 |
| 15 | Zona de residencia | 14 | Lista fija de 2 valores | ninguna |
| 16 | Entidad del plan de beneficios | 15 | Relación con catálogo | HU-038 |

La oposición a la donación (numeral 8) y la voluntad anticipada (numeral 9) quedan fuera de los 16:
cada una es una estructura aparte, con su propio CRUD [2.1], que depende del paciente.

## 4. Qué no es

- No tiene registro de usuarios ni recuperación de cuenta [2.4]. Los usuarios y sus cargos se crean
  al instalar el sistema (supuesto del equipo).
- No cubre los numerales 24 a 49 del anexo, tecnologías en salud y resultados, mientras el docente
  no confirme el alcance (D-16).
- No decide qué entra en un catálogo: lo decide la fuente oficial.

## 5. Requisitos no funcionales

- **Un código se entrega con la forma del anexo.** Un código alfanumérico conserva su longitud y sus
  ceros iniciales: Medellín es 05001, no 5001. El medio está en `docs/DESIGN.md`, sección 1.
- **El primer sprint arranca en terminal.** La guía pide una aplicación web, y el docente aceptó que
  el producto sea de terminal; el primer sprint tiene al menos el esqueleto en terminal
  (`docs/DECISIONS.md`, 2026-10-24 "El primer sprint arranca en terminal"). Las láminas siguen
  diciendo qué muestra cada pantalla.

## 6. Decisiones del equipo

Las tarjetas citan estas decisiones por su número. Es la copia de la hoja Decisiones del tablero del
equipo al 2026-10-24, con el valor propuesto tal como está allí, salvo estos cambios:

- Las remisiones a archivos que ya no están en el repositorio, como las tandas o las preguntas al
  docente, apuntan a las tarjetas o salen.
- D-10 trae la respuesta del docente, y D-12 la escala de 1 a 10 que dio el docente el 2026-10-21.
- D-16 pierde la frase sobre un plan para los elementos 1 a 49, que salió con su archivo.
- D-02 dice con otras palabras, y el mismo sentido, que el docente pidió un cargo real.

Lo que el docente respondió y todavía no está escrito acá sigue `pendiente`.

| N.º | Decisión | Valor propuesto | Estado |
|---|---|---|---|
| D-01 | Umbral del numeral 2.3: cuántas opciones hacen falta para que un campo sea entidad | Más de 5, según el texto del numeral 2.3 | `decidida` (`docs/DECISIONS.md`, 2026-10-22 "El umbral de catálogo es más de 5 valores") |
| D-02 | Cargo que mantiene los catálogos, con su función y sus permisos | Profesional de estadística de la IPS. Carga los catálogos desde el archivo oficial; crea y cambia valores solo por cambio oficial; inactiva y reactiva; no ve datos de pacientes ni de atenciones, solo cuántos registros usan un valor. Se supone que el área de estadística es la que reporta los datos de la IPS con las tablas oficiales; no hay fuente. El docente pidió un cargo real en lugar de un nombre (HU-022 c2). Pregunta al docente. | `pendiente` |
| D-03 | Código ISO 3166-1 de país: alfabético (COL) o numérico (170) | El numérico de 3 dígitos de la ISO 3166-1 (Colombia es 170); una fila con letras en el código se rechaza. El numeral 1 tiene formato 999 y el anexo de la Resolución 1888 de 2025 remite a la tabla Pais de SISPRO; que esa tabla use el numérico se confirma al descargarla. | `pendiente` |
| D-04 | Inicio de sesión: una historia o una por cargo | Una sola historia (HU-001) con permisos por cargo en los escenarios. | `pendiente` |
| D-05 | Prestador como catálogo propio | La IPS se trata como un solo prestador con una sede, porque la guía no menciona sedes. El equipo técnico deja su código de 12 caracteres configurado al instalar y el sistema lo pone en cada atención. No hay catálogo de prestadores. El prestador donde está la voluntad anticipada (numeral 9) se escribe y se valida por formato. | `pendiente` |
| D-06 | Tiempo de inactividad que cierra la sesión | 15 minutos sin actividad. | `pendiente` |
| D-07 | Clave que identifica a un paciente duplicado | Tipo y número de documento, como sugiere el docente en HU-004. | `pendiente` |
| D-08 | Estado de una atención que no se terminó de registrar | Dos estados: Borrador (le falta algo) y Registrada (completa). El borrador se completa en HU-007; la atención registrada se corrige en HU-009. | `pendiente` |
| D-09 | Versión del CIE-10 que se usa en las pruebas | La tabla CIE10 de SISPRO, en la versión vigente el día de la carga (HU-030). La carga guarda la dirección y la fecha de descarga (RC-8). El anexo de la Resolución 1888 de 2025 remite a esa tabla y dice que la transición al CIE-11 se amplió; la Resolución 2625 de 2025 cita la 1895 de 2001 como la que adoptó la CIE-10 (por confirmar). | `pendiente` |
| D-10 | Incremento del sprint 1 en consola o en web, confirmado por el docente | El docente acepta terminal. El primer sprint tiene al menos el esqueleto en terminal; las demás decisiones de arquitectura vienen después. | `respondida por el docente` (`docs/DECISIONS.md`, 2026-10-24 "El primer sprint arranca en terminal") |
| D-11 | Qué acepta el docente como mockup | Una imagen PNG por historia, pegada en la tarjeta, con la pantalla y sus estados principales, ligada a los escenarios. Se dibujan como maquetas HTML y se exportan a imagen. Si el docente pide otra herramienta, se rehacen. | `pendiente` |
| D-12 | Escala de estimación | Complejidad de 1 a 10, donde 10 es la más difícil. Sube con las dependencias directas, independientemente de la prioridad. Se estima en clase con todo el equipo; si no se hace en clase, con video de todos [2.5]. La propuesta del modelo para las 12 está en `docs/ROADMAP.md`, sección "Estimación de las 12 historias"; vale el número que vote el equipo. | `respondida por el docente` (`docs/DECISIONS.md`, 2026-10-24 "La complejidad se estima de 1 a 10") |
| D-13 | Cuándo se exige el triage en una atención | Se pide cuando el grupo de servicios es 05 Atención inmediata: el anexo define el triage para un servicio de urgencias (hojas 17 y 18). Salvedad: ese grupo también incluye servicios que no son de urgencias, como la atención del parto, según el manual de habilitación del Ministerio (por confirmar). Pregunta al docente. | `pendiente` |
| D-14 | Catálogo de enfermedades huérfanas: fuente y versión | El listado de enfermedades huérfanas de la Resolución 2625 de 2025 del Ministerio de Salud, versión 5.0, que derogó la 023 de 2023. El código es el número que el listado da a cada enfermedad, que no se reasigna (artículo 3); que ese número sea el código de 1 a 4 caracteres del numeral 23 es inferencia del equipo. | `pendiente` |
| D-15 | Datos obligatorios de la atención | Columna "¿Obligatorio?" de la lista de datos de HU-007, en su tarjeta. | `pendiente` |
| D-16 | Alcance de la primera fase: elementos 1 a 23 (grupos 1 y 2 del anexo) o 1 a 49 | Se asume 1 a 23 (grupos 1 y 2 del anexo) mientras el docente no diga otra cosa. Pregunta al docente. | `pendiente` |
| D-17 | Historias por entidad de catálogo: una por operación del CRUD (4) o una por entidad | Una historia por catálogo, también en el CIE-10 (HU-030); la búsqueda del médico va aparte por actor (HU-031). Operaciones separadas solo en oposición y voluntad anticipada (2.1) y en la atención (registrar, consultar, corregir, anular). Backlog v3: 30 historias. | `pendiente` |
| D-18 | Cómo se guardan los componentes del anexo | Cada componente con el tipo de la columna Tipo de la hoja: Alfanumérico como texto de la longitud indicada, Numérico como entero, Fecha como fecha y hora. | `pendiente` |
| D-19 | Datos obligatorios del paciente | Tabla de HU-004, en su tarjeta: obligatorios los datos que siempre tienen respuesta en su lista (incluidos los valores de no declara, sin discapacidad y ninguna); opcionales el segundo nombre, el segundo apellido y la hora de nacimiento; condicionados la ocupación, el municipio y la comunidad étnica, que se pide solo si el catálogo de HU-037 tiene valores (mientras no lo tenga, el paciente se registra sin ese dato). La identidad de género queda obligatoria porque su lista tiene "No lo declara"; el anexo de la Resolución 1888 de 2025 dice que "es un dato opcional que será diligenciado a criterio del profesional de la salud de acuerdo con el escenario de atención". | `pendiente` |
| D-20 | "Eliminar" no borra: en los catálogos se inactiva y en las estructuras separadas se anula | Nada se borra. Un valor de catálogo se inactiva: deja de ofrecerse para registros nuevos y los registros que ya lo usan lo conservan; una versión nueva de un catálogo oficial inactiva los valores que retira. En la oposición y la voluntad anticipada, un registro hecho por error se anula con motivo y queda en el historial; el docente da por funcional anular (HU-013 c2, HU-017 c2). | `pendiente` |
| D-21 | Fuente y versión de los catálogos externos | Fuentes: la tabla Pais de SISPRO (ISO 3166-1); la CIUO-88 A.C. y la DIVIPOLA del DANE; el catálogo de referencia de comunidades étnicas y el de entidades del Ministerio; las listas del anexo, en su hoja: si el archivo no coincide con la hoja, manda la hoja y se guardan sus códigos y nombres. Formato de carga: CSV UTF-8 separado por punto y coma, con la fuente, la dirección y la fecha de descarga y la fecha de publicación (RC-8). Versión nueva y recarga: RC-9 (el código de una fila rechazada conserva su nombre y su estado). Apoyo: artículo 8 de la Resolución 866, que pone los catálogos a cargo del Ministerio, que definirá el procedimiento para individualizarlos en SISPRO. | `pendiente` |
| D-22 | ¿Un código padre del CIE-10 se puede asociar a una atención? | No. El numeral 23 fija para el código CIE-10 longitud 4 y 4, así que un código de 3 caracteres se rechaza. El código de enfermedad huérfana, del mismo numeral, va de 1 a 4. | `pendiente` |
| D-23 | Búsqueda en los catálogos: exacta o parcial | Por código, exacta. Por nombre, parcial, sin distinguir mayúsculas ni tildes, y muestra todas las coincidencias. | `pendiente` |
| D-24 | Ocupación que guarda la atención | Al crear la atención, el sistema guarda la ocupación que el paciente tiene en ese momento. Si después cambia la del paciente, la atención conserva la suya; si quedó mal, se corrige en HU-009. | `pendiente` |
| D-25 | Nombre que muestra un registro cuando el catálogo cambia el nombre de un valor | Un registro conserva el código y el nombre que tenía el valor el día en que se registró; si después la fuente oficial cambia el nombre, los registros ya hechos no cambian y su consulta muestra también el nombre vigente (RC-11; HU-005 escenario 13, HU-008 escenario 15). | `pendiente` |
| D-26 | Padre de un valor en los catálogos con niveles (ocupaciones y CIE-10) | El padre se lee del código: en la CIUO-88 A.C., el código de cada ocupación empieza por el de su grupo (2221 está bajo 222); en el CIE-10, la categoría son los 3 primeros caracteres (A000 está bajo A00). El archivo no necesita columna de padre y no puede haber ciclos. La primera carga de la CIUO-88 se compara con las cantidades por nivel del DANE (10, 34, 117 y 401), contando las filas leídas. | `pendiente` |
| D-27 | Anulación de una atención registrada por error | Una atención registrada por error, o un borrador que no debía existir (a nombre del paciente equivocado, repetida o que no ocurrió), se anula con un motivo que es una inconsistencia: queda en estado "Anulada", no se borra, no se corrige y no se pasa a otro paciente. Las anuladas siguen contando como registros que usan un valor de catálogo. Historia nueva HU-043. | `pendiente` |

## 7. Sin escribir todavía

Este archivo es una semilla mientras esta lista tenga algo.

- Las respuestas del docente que todavía no están escritas en el repositorio. Las que llegaron hasta
  el 2026-10-24 están en `docs/context/MEMORIA-CONTEXTUAL.md`; varias cambian decisiones de la
  sección 6.
- Qué se exige al registrar un paciente con un tipo de documento sin identificación (D-19).
