# Plan de reescritura del backlog: IPS Salud y Vida

Versión 7, 2026-10-21. Documento de seguimiento: dice qué se va a hacer, en qué orden, con qué
decisiones asumidas y cómo va el avance. Se actualiza al cerrar cada fase. El registro de lo que se
discutió en el chat sigue en `MEMORIA-CONTEXTUAL.md`.

**Estado:** fases 0 a 4 hechas en borrador: tandas 0 a 5 escritas y revisadas por revisores
independientes, 30 mockups, modelo entidad-relación y kit para Trello. Falta lo que no puede hacer
el modelo: la revisión del equipo, las respuestas del docente, la estimación en clase y el paso a
Trello.

## TL;DR

- El análisis está hecho: el tablero clasifica los 49 elementos del anexo (1 a 23 revisados; 24 a
  49 preliminares) y cruza los 63 comentarios del docente. Lo que falta es escribir.
- Propuesta: **30 historias** para los elementos 1 a 23. Una historia por catálogo, también en el
  CIE-10, más la búsqueda del médico (otro actor). Las operaciones van separadas en la oposición y
  la voluntad anticipada (2.1) y en la atención, donde anular cambia el estado de toda la atención.
  15 se editan, 5 se fusionan y 10 son nuevas.
- Plan B, si el alcance es 1 a 49: **entre 35 y 39 historias** que cubren los 49 elementos: 35 si
  las listas del 24 al 49 se agrupan en dos. Para caber, la oposición y la voluntad anticipada pasan
  a una historia cada una, y los catálogos externos del 24 al 49 se agrupan por familia (sección 6).
  El CIE-10 sigue en dos historias, porque la búsqueda es de otro actor.
- Las tandas de acceso, paciente y atención son iguales en los dos planes: se pueden escribir antes
  de que el docente responda D-16.

## 1. Lo que hay

| Pieza | Dónde | Estado |
|---|---|---|
| Clasificación de los elementos 1 a 23 (28 filas) | Tablero, hoja Validación | Hecha y revisada por un revisor independiente contra el PDF |
| Clasificación de los elementos 24 a 49 (49 filas) | Tablero, hoja Anexo 1 a 49 | Preliminar, sin revisión independiente; sujeta a D-16 |
| Entidades y conteo de historias | Tablero, hoja Entidades | Hecho para 1 a 23 |
| Qué cambiar en cada una de las 33 historias | Tablero, hoja Ajustes por historia (104 filas) | Hecho, en lenguaje simple |
| Comentarios del docente (63, HU-001 a HU-030) | Tablero, hoja Retroalimentación; `entrada/` | Cruzados: cada comentario citado por al menos un ajuste |
| Anexo transcrito | `anexo-res866-elementos-1-23-referencia.md` y `...-24-49-referencia.md` | El de 24 a 49 es textual; el de 1 a 23 resume en 18 de 28 filas |
| Historias del otro grupo (semestre 2026-1) | `comparacion-companeros-anexo.md` | Referencia de lo que hizo otro grupo, no de lo que pide el docente ahora |

## 2. Reglas que mandan para escribir

| Regla | Texto y fuente |
|---|---|
| Cuántas | "Cada grupo documentara como mínimo 30 historias de usuario en el primer sprint." (guía, 2.5). Es un piso: el plan queda en 30, sin margen (sección 7) |
| Qué es entidad | "Todos los campos que contienen más de 5 opciones seleccionables deben tener una entidad propia en la base de datos" (guía, 2.3). Más de 5, es decir 6 o más; la revisión General del docente dice lo mismo |
| Cuándo separar historias | Prompt 2 del docente: separar cuando "Existan actores distintos con objetivos diferentes", "Se presenten ciclos de vida o estados distintos", entre otros. "No dividas historias únicamente por: [...] Operaciones de base de datos." |
| Estructuras separadas | Oposición y voluntad anticipada: "Debe representarse como una estructura separada (CRUD)" (guía, 2.1) |
| CIE-10 | "última versión del CIE-10, respetando la jerarquía padre-hijo" (guía, 2.2) |
| Inicio de sesión | "funcionalidad básica de inicio de sesión"; sin registro de usuarios ni recuperación de cuenta (guía, 2.4) |
| Estimación | "estimadas correctamente en clase. Cuando no se realice en clase deberá dejar evidencia mediante grabación de video" (guía, 2.5). No la puede poner un modelo |
| Lenguaje | Simple. Si una historia necesita muchas reglas, no las copia: dice el numeral del anexo (pedido del autor, 2026-10-20) |
| Catálogos oficiales | Artículo 7 de la Resolución 866: no se modifican, reducen ni adicionan. Se crea o actualiza solo para reflejar el catálogo oficial; eliminar es inactivar (D-20) |

**Regla de compresión propuesta.** Una historia por catálogo (consultar, crear, actualizar por
cambio oficial, inactivar). Las operaciones se separan solo donde la guía le da a la entidad una
sección propia, porque ahí cada operación tiene actor o estado distinto, que es el criterio del
Prompt 2:

- Oposición y voluntad anticipada (2.1): el registro lo hace el auxiliar y la consulta el médico; un
  cambio de voluntad no es lo mismo que anular un error. El docente lo dice en sus comentarios a
  HU-012, HU-013, HU-016 y HU-017.
- CIE-10 (2.2): el médico busca y elige (HU-031); la carga y el mantenimiento son una historia,
  como en los demás catálogos (HU-030). Hasta la revisión de la tanda 5 eran cuatro historias.
- Paciente y atención: la guía nombra sus operaciones (registrar, consultar, actualizar; crear,
  consultar, modificar). El paciente se queda en 3; la atención suma la anulación (HU-043, D-27).

## 3. Decisiones que cambian el conteo

| ID | Pregunta | Valor asumido para planear | Quién la cierra | Efecto en el conteo |
|---|---|---|---|---|
| D-16 | Alcance: elementos 1 a 23 o 1 a 49 | 1 a 23 | Docente | Con 1 a 49 y las mismas reglas serían entre 49 y 57; con el plan B, entre 35 y 39 (sección 6) |
| D-17 | Una historia por operación o por entidad | Por entidad, salvo 2.1 y 2.2 | Equipo; avisar al docente | Con una por operación serían 69 |
| D-04 | Inicio de sesión: una historia o una por cargo | Una, con permisos por cargo en los escenarios | Equipo | Con una por cargo, 32 |
| D-05 | Prestador como catálogo propio | No: el prestador de la atención es la propia IPS y el código se valida por su formato | Equipo | Si es catálogo, 31 |
| D-27 | Anular una atención registrada por error | Sí, con HU-043 | Docente | Si no la acepta, 29: hace falta otra historia |
| D-01 | Umbral del 2.3 | Más de 5 (texto de la guía y del docente) | Cerrada por texto | Con 5 o más, 34 |
| D-20 | Eliminar es inactivar | Sí | Equipo; el docente lo pide en HU-021, HU-025 y HU-029 | No cambia el conteo; cambia el texto |

Conteo con los valores asumidos:

| Grupo | Historias |
|---|---|
| Inicio de sesión | 1 |
| Paciente (elementos 1 a 15) | 3 |
| Atención (elementos 16 a 23) | 4 |
| Oposición a la donación (elemento 8) | 4 |
| Voluntad anticipada (elemento 9) | 4 |
| Diagnósticos CIE-10 (elemento 23) | 2 |
| Otros catálogos, uno cada uno (12) | 12 |
| **Total** | **30** |

## 4. Inventario propuesto

Se conservan los ID que siguen vivos, para no perder los comentarios del docente que cuelgan de
cada tarjeta en Trello. Las fusionadas conservan el ID más bajo; las nuevas empiezan en HU-034.

| ID | Historia | Elementos o numeral | Viene de | Acción | Tanda |
|---|---|---|---|---|---|
| HU-001 | Inicio de sesión con permisos por cargo | Guía 2.4 | HU-001, HU-002, HU-003 | Fusionar | T1 |
| HU-004 | Registro de un paciente | 1 a 15 | HU-004 | Editar | T1 |
| HU-005 | Consulta de un paciente | 1 a 15 | HU-005 | Editar | T1 |
| HU-006 | Actualización y corrección de un paciente | 1 a 15 | HU-006 | Editar | T1 |
| HU-007 | Registro de información clínica | 16 a 23 | HU-007 | Editar | T2 |
| HU-008 | Consulta de la información clínica de un paciente | 16 a 23 | HU-008 | Editar | T2 |
| HU-009 | Modificación de la información clínica registrada | 16 a 23 | HU-009 | Editar | T2 |
| HU-043 | Anulación de una atención registrada por error | 16 a 23 | Nueva | Escribir | T2 |
| HU-010 a HU-013 | Oposición: registrar, consultar, actualizar, anular | 8 | Las mismas | Editar | T3 |
| HU-014 a HU-017 | Voluntad anticipada: registrar, consultar, actualizar (cambio, reemplazo, revocación), anular | 9 | Las mismas | Editar | T3 |
| HU-018 | Catálogo de países: carga y mantenimiento | 1 y 11 | HU-018 a HU-021 | Fusionar | T4 |
| HU-022 | Catálogo de ocupaciones: carga y mantenimiento | 7 | HU-022 a HU-025 | Fusionar | T4 |
| HU-026 | Catálogo de municipios: carga y mantenimiento | 12 | HU-026 a HU-029 | Fusionar | T4 |
| HU-034 | Catálogo de tipos de documento | 2 | Nueva | Escribir | T4 |
| HU-035 | Catálogo de categorías de discapacidad | 10 | Nueva | Escribir | T4 |
| HU-036 | Catálogo de etnias | 13 | Nueva | Escribir | T4 |
| HU-037 | Catálogo de comunidades étnicas | 13 | Nueva | Escribir | T4 |
| HU-038 | Catálogo de entidades administradoras de planes de beneficios | 15 | Nueva | Escribir | T4 |
| HU-030 | Catálogo de diagnósticos CIE-10: carga y mantenimiento | 23 | HU-030, HU-032, HU-033 | Fusionar | T5 |
| HU-031 | Búsqueda y selección de un diagnóstico | 23 | HU-031 | Editar | T5 |
| HU-039 | Catálogo de modalidades de realización | 18 | Nueva | Escribir | T5 |
| HU-040 | Catálogo de vías de ingreso | 20 | Nueva | Escribir | T5 |
| HU-041 | Catálogo de causas que motivan la atención | 21 | Nueva | Escribir | T5 |
| HU-042 | Catálogo de enfermedades huérfanas | 23 | Nueva | Escribir | T5 |

Salen como tarjetas propias (se archivan en Trello con una nota que diga en cuál quedaron):
HU-002, HU-003, HU-019, HU-020, HU-021, HU-023, HU-024, HU-025, HU-027, HU-028, HU-029, HU-032 y
HU-033. Sus comentarios del docente pasan como escenarios a la historia que las absorbe.

Cerrado en la fase 1: la numeración queda con huecos, para no perder los comentarios de Trello.

## 5. Fases

| Fase | Qué se hace | Entregable | Criterio para cerrarla |
|---|---|---|---|
| 0 | Fijar las decisiones asumidas y preguntarle al docente lo que solo él decide (D-16, D-17, D-04, qué acepta como mockup) | Este plan con valores asumidos; borrador del mensaje al docente, con las palabras del autor | El autor aprueba el inventario y envía o descarta el mensaje |
| 1 | Plantilla de historia en lenguaje simple, sobre el Prompt 1 y el Prompt 2 del docente; tabla de qué ajuste del tablero y qué comentario del docente va a cada historia nueva o fusionada | Tanda 0 (épica, reglas, plantilla); hoja Backlog v3 del tablero | Cada una de las 104 filas de Ajustes y cada comentario tienen historia de destino |
| 2 | Redacción por tandas, en orden de dependencia: T1 acceso y paciente (4), T2 atención (4), T3 estructuras del 2.1 (8), T4 catálogos del paciente (8), T5 CIE-10 y catálogos de la atención (6) | Un .md por tanda, como las tandas anteriores | Cada tanda pasa una revisión independiente contra el anexo, los comentarios del docente y la plantilla |
| 3 | Mockups y estimación | Una lámina de mockup por historia (30), con etiquetas que remiten a sus escenarios; estimación hecha por el equipo en clase o con video | Cada historia enlaza su mockup; la estimación queda como evidencia del equipo |
| 4 | Paso a Trello | Tarjetas editadas, fusionadas y nuevas, con un comentario de qué cambió por cada observación del docente | Las 30 tarjetas y la de reglas comunes en la lista del backlog; las 13 absorbidas, archivadas con su nota |
| B | Solo si el docente dice 1 a 49 | Tandas extra del plan B (sección 6) y fusión de las estructuras del 2.1 y del CIE-10 | Se decide antes de la tanda T3, que es la primera que cambia entre los dos planes |

## 6. Plan B: alcance 1 a 49 en 35 a 39 historias

El otro grupo (semestre 2026-1) tenía 30 historias y tocaba elementos del 24 en adelante, pero no
porque comprimiera: dejó por fuera los elementos 26 a 36, 39, 40, 45, 46 y 49, los catálogos de más
de 5 valores del 24 al 49 salvo uno, y la oposición y la voluntad anticipada que pide el 2.1; y 6
de sus 30 historias no salen del anexo (`comparacion-companeros-anexo.md`). El plan B cubre los 49
elementos así:

| Grupo | Historias | Cómo |
|---|---|---|
| Inicio de sesión | 1 | Igual que el plan A |
| Paciente | 3 | Igual que el plan A |
| Atención (16 a 23) | 4 | Igual que el plan A |
| Oposición y voluntad anticipada | 2 | Una historia cada una, con escenarios por operación |
| CIE-10 | 2 | Igual que el plan A |
| Catálogos de los elementos 1 a 23 | 12 | Igual que el plan A |
| Tecnologías en salud de una atención (24 a 36) | 1 | Registro de las tecnologías; consultar y modificar entran en las historias de la atención |
| Egreso de una atención (37, 38, 40 a 46, 49) | 1 | Diagnósticos de egreso, condición y destino, fecha de cierre, incapacidad, licencia |
| Resultado de la valoración clínica (39) | 1 | |
| Antecedentes de salud (47) | 1 | Alergias y antecedentes familiares |
| Exposición a factores de riesgo (48) | 1 | |
| Listas de más de 5 del 24 al 49 | 2 a 6 | Tipo de tecnología, finalidad, unidad de tiempo, condición de egreso, tipo de alérgeno y tipo de factor de riesgo: una cada una, o agrupadas en dos (las de la tecnología y las del egreso y los antecedentes) |
| Catálogos externos del 24 al 49 | 4 | Por familia: medicamentos (IUM, DCI, UMM, VAD, FMM); procedimientos (CUPS); otras tecnologías del Ministerio; resultados (instrumentos y parámetros) |
| **Total** | **35 a 39** | 35 con las listas agrupadas en dos; 39 con una historia por lista |

Lo que cuesta el plan B:

- Una historia por estructura del 2.1 queda grande: registrar, consultar, cambiar la voluntad y
  anular en una sola tarjeta. El Prompt 2 pide separar cuando "Se presenten ciclos de vida o estados
  distintos" o "Una historia resulte excesivamente amplia".
- Agrupar catálogos por familia es lo que hizo el otro grupo en su HU13. Que el docente lo acepte no
  está verificado: no se conoce la nota de ese grupo ni sus comentarios.
- El incremento del producto crece: el 2.5 pide que cada integrante desarrolle mínimo 4 historias
  por sprint.
- Pasa de 37 si cada lista del 24 al 49 va en su propia historia. Para no pasar, las listas van
  agrupadas en dos.

## 6b. Lo que el plan A deja para el modelo de datos (adelanto)

La guía evalúa "la correcta modelación de datos" [2.7] y pide el diagrama entidad-relación [3.6].
Con el plan A el modelo queda en 24 tablas (`modelo-datos/modelo-entidad-relacion.md`, versión 2):

| Tipo | Tablas |
|---|---|
| Centrales | Paciente, Atención |
| Estructuras del 2.1 | Oposición a la donación y Voluntad anticipada, cada una con varias versiones por paciente |
| Varios valores por paciente [2.1] | Nacionalidades del paciente (paciente con país) y Discapacidades del paciente (paciente con categoría) |
| Catálogos [2.3] | Países, Tipos de documento, Ocupaciones (con su grupo leído del código), Discapacidad, Departamentos, Municipios, Etnias, Comunidades étnicas, Entidades del plan de beneficios, Modalidades, Vías de ingreso, Causas, Diagnósticos CIE-10 (con su categoría), Enfermedades huérfanas |
| Mantenimiento de catálogos | Versiones cargadas (fuente, descarga, publicación) y cambios de cada valor (motivo, referencia) |
| Acceso y rastro | Usuarios con su cargo; Historial de cambios (quién, cuándo, valor anterior y nuevo, actualización o corrección) |

Los datos de 5 opciones o menos (sexo, identidad de género, zona, grupo de servicios, entorno,
clasificación del triage, tipo de diagnóstico) son columnas con su lista fija, no tablas. Las
relaciones salen casi todas del paciente y de la atención hacia los catálogos. El diagrama está
hecho en borrador; se ajusta cuando el equipo apruebe las historias.

## 7. Riesgos

| Riesgo | Qué se hace |
|---|---|
| El docente pide los elementos 24 a 49 | Plan B de la sección 6. Las señales actuales apuntan a 1 a 23: la guía habla de "una primera fase" con dos módulos, el comentario ampliado de HU-007 enumera solo datos del 16 al 23, y ninguno de los 63 comentarios menciona tecnologías, egreso, alergias ni factores de riesgo |
| Quedar justo en 30 | El plan queda en 30. Si el docente rechaza HU-043 (D-27) o el catálogo de comunidades étnicas no existe (HU-037), baja a 29 y hace falta otra historia; preguntas 7 y 10 al docente |
| Perder la trazabilidad de los comentarios al fusionar | Tabla de destino de cada comentario en la fase 1; las tarjetas absorbidas se archivan, no se borran |
| El docente lee "formatos" de forma literal en HU-004 | El mockup muestra el formato en cada campo; la tarjeta remite al numeral |
| Mockups para 30 historias | Hechos: una lámina por historia con sus escenarios; D-11 dice qué acepta el docente |
| Una sola historia por catálogo puede parecer amplia | Cada una con escenarios por operación y el mismo cargo; el Prompt 2 respalda no dividir por operaciones de base de datos |

## 8. Avance

| Fecha | Fase | Qué se hizo | Siguiente paso |
|---|---|---|---|
| 2026-10-20 | Plan | Primera versión de este plan | Luz verde del autor para la fase 0 |
| 2026-10-20 | Plan | Versión 2: plan B con los 49 elementos en 33 a 37 historias | Saber la nota y los comentarios del docente al otro grupo; preguntar D-16 |
| 2026-10-20 | 0 | Luz verde del autor. Dato nuevo: el otro grupo (2026-1) sacó 4,0 y sus tarjetas no tienen comentarios. Valores propuestos en D-02, D-04, D-06, D-07, D-17 y D-19. `preguntas-docente.md` con 6 preguntas | El autor las envía por Teams |
| 2026-10-20 | 1 | `backlog-v3/backlog-v3-tanda-0-epica-y-reglas.md` (épica, matriz de permisos, RC-1 a RC-7, plantilla). Hoja Backlog v3 en el tablero; las 104 filas de Ajustes y los 69 textos de Retroalimentación tienen historia destino (0 sin destino) | Tanda 1 |
| 2026-10-20 | 2 | Tanda 1 en borrador: HU-001 (fusiona HU-001 a HU-003), HU-004, HU-005, HU-006 | Revisión independiente de las tandas 0 y 1 |
| 2026-10-20 | 2 | Revisión independiente de las tandas 0 y 1: 13 comentarios del docente resueltos salvo el mockup; 24 filas del tablero, 22 resueltas y 2 en parte. Corregido, entre otras cosas, un error mío: había leído solo el comentario 1 de HU-022 y quitado la carga de catálogos; los comentarios 3 y 4 la dan por funcional y piden precisarla. La carga vuelve al alcance; HU-030 pasa de reescribir a editar | Tanda 2: atención (HU-007 a HU-009) |
| 2026-10-20 | 2 | Tandas 2 a 5 escritas y revisadas por revisores independientes (una por tanda). Decisiones propuestas de D-02 a D-24; RC-8 (carga desde el archivo oficial) | Fases 3 y 4 |
| 2026-10-20 | 3 y 4 | 31 mockups, modelo entidad-relación de 24 tablas y kit para Trello | Revisiones de las tandas 4 y 5 |
| 2026-10-20 | 2 a 4 | Revisiones de las tandas 4 y 5 aplicadas: el CIE-10 pasa a 2 historias (HU-032 y HU-033 se funden en HU-030) y entra HU-043 (anulación de una atención): 30 historias y 541 escenarios. Cargo: profesional de estadística. RC-8 a RC-11. D-25 a D-27 nuevas. Tablero, 30 mockups, modelo E-R versión 2 y kit versión 2 regenerados | Revisión del equipo; preguntas al docente; descargar las tablas de SISPRO y del DANE para confirmar D-03 y D-26; estimación en clase; Trello |
| 2026-10-20 | 2 a 4 | Revisión final cruzada (`revisiones/revision-final-cruzada.md`) aplicada: niveles de ocupaciones y CIE-10 fuera de la longitud del numeral; recarga de la versión vigente; reactivación con respaldo; RC-11 con escenario en HU-005 y HU-008; artículo 10 citado por lo que dice; HU-004 sin el escenario del paciente particular; HU-043 sin el escenario repetido. 30 historias y 543 escenarios. Mockups regenerados (cada escenario tiene su etiqueta o su nota); kit versión 3, con la correspondencia de los escenarios antiguos en los comentarios al docente y en las notas de archivo, y con las imágenes dentro del kit | Lo mismo de la fila anterior |
| 2026-10-20 | 2 a 4 | Verificación independiente de la aplicación (`revisiones/verificacion-aplicacion-final.md`): "lista con ajustes menores"; 16 puntos corregidos, entre ellos la suma del plan B: su tabla da 35 a 39 historias y el total decía 32 a 36 (también en la pregunta 1 al docente) | Lo mismo de la fila anterior |
| 2026-10-21 | Desarrollo | El docente limita el desarrollo a 3 historias por integrante (12 en el semestre) y no acepta historias con dependencias sin desarrollar. Plan en `plan-desarrollo-12-historias.md`: 9 obligatorias (8 catálogos y HU-004) y dos planes para el cuarto integrante. Cambios en el backlog: HU-037 sin HU-036; catálogos y HU-004 sin depender del inicio de sesión (permisos en HU-001, escenario 14); oposición en una sola dirección; HU-006 sin HU-008. 30 historias y 528 escenarios | Llevar a clase las 4 preguntas del plan; elegir el plan A o el B |
| 2026-10-22 | Desarrollo | El tablero pone en To do las 12 del plan B. Correcciones mínimas a esas 12 (`plan-desarrollo-12-historias.md`, sección 8): CRUD visible en los 8 catálogos, sin escenarios que miren HU-004 o HU-005 desde los catálogos, creación a mano validada en HU-022 y HU-026, HU-004 escenario 15 y HU-001 con las opciones que existan. 30 historias y 529 escenarios. El material pasa al repositorio del proyecto, `docs/context/` | Rehacer la lámina de HU-004; actualizar la hoja Backlog v3 del tablero |
