# Plan de desarrollo: 12 historias en lo que queda del semestre

Versión 3, 2026-10-24. Propuesta del equipo para llevar a clase. Sale del backlog v3 (30
historias, 529 escenarios) y de las reglas que el docente dio para el desarrollo. Las historias y
sus dependencias están en las tandas del backlog v3 y en el kit de Trello.

**Nota del 2026-10-24:** el equipo pasó a 3 integrantes con 4 historias cada uno. El reparto de
las secciones 3 y 4, pensado para 4 integrantes, quedó viejo; el vigente está en
`docs/ROADMAP.md`, sección "Orden de las 30 historias" (`docs/DECISIONS.md`, 2026-10-24 "Tres
integrantes con cuatro historias cada uno").

## 1. Reglas del desarrollo

Las reglas son del docente, según lo que contó el autor:

- Cada integrante desarrolla 3 historias en los 2 meses que quedan: 12 en total para 4 integrantes.
  Las 30 del backlog sirven para exponer la lógica del producto completo.
- No se desarrolla una historia que dependa directamente de otra que no esté desarrollada, aunque le
  dé valor al cliente.
- No se vale un dato provisional, por ejemplo el país como texto, para crear su catálogo en otro
  sprint.
- La complejidad se estima de 1 a 10 y sube con la cantidad de dependencias directas de cada
  historia, sin importar su prioridad.
- El inicio de sesión es necesario, pero no aporta valor en la primera entrega.
- El paciente tiene 16 atributos.

## 2. Los 16 atributos del paciente

El conteo de 16 es una hipótesis del equipo: cuadra si los apellidos cuentan como un dato, los
nombres como otro y la fecha y hora de nacimiento como uno solo.

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

La oposición a la donación (numeral 8) y la voluntad anticipada (numeral 9) no son atributos: son
estructuras aparte, con su propio CRUD [2.1], que dependen del paciente.

## 3. Las 9 historias obligatorias

Para registrar un paciente con sus 16 atributos hacen falta los 8 catálogos y el registro. Los
catálogos no dependen de nada; el registro depende de los 8.

| Orden | Historia | Depende de | Dependencias directas | Escenarios | Integrante |
|---|---|---|---|---|---|
| 1 | HU-022 Catálogo de ocupaciones | ninguna | 0 | 28 | 1 |
| 1 | HU-036 Catálogo de etnias | ninguna | 0 | 23 | 1 |
| 1 | HU-018 Catálogo de países | ninguna | 0 | 22 | 2 |
| 1 | HU-026 Catálogo de municipios | ninguna | 0 | 25 | 2 |
| 1 | HU-038 Catálogo de entidades del plan de beneficios | ninguna | 0 | 22 | 2 |
| 1 | HU-034 Catálogo de tipos de documento | ninguna | 0 | 23 | 3 |
| 1 | HU-035 Catálogo de categorías de discapacidad | ninguna | 0 | 23 | 3 |
| 1 | HU-037 Catálogo de comunidades étnicas | ninguna | 0 | 22 | 3 |
| 2 | HU-004 Registro de un paciente | los 8 catálogos | 8 | 15 | 1 |

Escenarios por integrante: 66, 69 y 68.

Con estas 9, la IPS registra pacientes con los 16 atributos del anexo y mantiene los 8 catálogos
oficiales. Todavía no puede consultar ni corregir al paciente; eso depende del plan del cuarto
integrante.

## 4. El cuarto integrante: dos planes

En los dos planes, el cuarto integrante empieza el primer día con HU-001, porque no depende de nada,
y hace sus otras dos historias cuando HU-004 esté desarrollada.
La columna Orden es la del plan completo: 1 empieza el primer día, 2 es HU-004, y 3 y 4 vienen
después.

### Plan A: inicio de sesión y oposición

| Orden | Historia | Depende de | Dependencias directas | Escenarios |
|---|---|---|---|---|
| 1 | HU-001 Inicio de sesión con permisos por cargo | ninguna | 0 | 14 |
| 3 | HU-010 Registro de la oposición | HU-001 y HU-004 | 2 | 12 |
| 4 | HU-011 Consulta de la oposición | HU-001, HU-004 y HU-010 | 3 | 10 |

La IPS recibe además el inicio de sesión con los permisos de cada cargo, que se aplican también a los
catálogos y al registro del paciente (HU-001, escenarios 9 y 14), y el registro y la consulta de la
oposición, que la guía pide como estructura aparte [2.1]. Queda sin consulta del paciente.

### Plan B: el paciente completo

| Orden | Historia | Depende de | Dependencias directas | Escenarios |
|---|---|---|---|---|
| 1 | HU-001 Inicio de sesión con permisos por cargo | ninguna | 0 | 14 |
| 3 | HU-005 Consulta de un paciente | HU-001 y HU-004 | 2 | 13 |
| 3 | HU-006 Actualización y corrección de los datos de un paciente | HU-001 y HU-004 | 2 | 13 |

La IPS recibe el inicio de sesión y el paciente completo: registrar, consultar, actualizar y
corregir. La corrección guarda quién la hizo, como exige el parágrafo 2 del artículo 10 de la
Resolución 866. Queda sin oposición ni voluntad anticipada.

### Diferencia entre los dos planes

| | Plan A | Plan B |
|---|---|---|
| Lo que gana la IPS | La oposición (numeral 8) | Consultar y corregir al paciente |
| Lo que le falta | Consultar al paciente | Las estructuras del numeral 2.1 |
| Escenarios del cuarto integrante | 36 | 40 |
| Historia con más dependencias | HU-011 (3) | HU-005 y HU-006 (2) |

## 5. Lo que no entra en 12

- La atención (HU-007) necesita el paciente y 6 historias más (HU-039, HU-040, HU-041, HU-042,
  HU-030 y HU-031): son 17 en total.
- La voluntad anticipada tiene un componente, el código del prestador donde está el documento (12
  dígitos), que puede pedir un catálogo de prestadores que no está en el backlog (D-05). Además, sus
  historias (HU-014 a HU-017) todavía remiten a historias posteriores.

## 6. Cambios hechos en el backlog para este plan

1. **HU-037 sin HU-036.** El catálogo de comunidades étnicas no depende del de etnias: la hoja 15
   no dice a qué etnia pertenece cada comunidad. La regla que las une es del registro del paciente
   (HU-004 pide la comunidad si la etnia es de la 01 a la 06).
2. **Los catálogos y HU-004 no dependen del inicio de sesión.** El escenario "Cargo sin permiso"
   de las 13 historias de catálogo pasa a HU-001 (escenario 14). El de HU-004 sale, porque lo
   cubren HU-001 escenarios 6 y 8. Quién hizo cada registro o cambio lo agrega HU-001 (escenario
   9); lo registrado antes de tener el inicio de sesión queda sin autor.
3. **La oposición en una sola dirección.** El orden es registrar (HU-010), consultar (HU-011),
   actualizar (HU-012) y anular (HU-013), y cada historia solo remite a las anteriores. El cambio
   que hace una historia lo prueba esa misma historia mirando la consulta. HU-011 sigue con 10
   escenarios.
4. **HU-006 sin HU-008.** Su escenario 14, que miraba la consulta de atenciones, sale; lo prueba
   HU-008 escenario 9.

Conteo después de los cambios: 30 historias y 528 escenarios (antes 543).

## 7. Preguntas para el docente

1. ¿"Desarrollada" quiere decir terminada antes de empezar la otra, o antes de entregarla? Con la
   primera, el cuarto integrante espera entre HU-001 y HU-004.
2. ¿Acepta que el escenario de cargo sin permiso de los catálogos esté en HU-001 (escenario 14)? Lo
   pidió en HU-025, HU-026, HU-028 y HU-030: "rol autenticado sin permisos: ausencia de sesión no
   cubre autorización".
3. La comunidad étnica, ¿catálogo (hoja 15 de la Resolución 866) o texto (anexo de la Resolución
   1888 de 2025, 13.2: "Texto Alfanumérico")? Con texto, las historias obligatorias bajan a 8 y
   queda un cupo libre, pero el backlog baja a 29; la historia que lo repondría es el catálogo de
   prestadores (D-05).
4. ¿La estimación cuenta solo las dependencias directas, o también el tamaño? Los catálogos tienen 0
   dependencias y entre 22 y 28 escenarios; HU-004 tiene 8 dependencias y 15 escenarios.

## 8. Correcciones mínimas del 2026-10-22

El tablero de Trello del 2026-10-22 pone en To do las 12 del plan B: los 8 catálogos, HU-004,
HU-005, HU-006 y HU-001. Antes de desarrollarlas se revisó que ninguna dependa de una historia de
fuera del grupo, y que ninguna remita a otra que depende de ella.

| Historia | Corrección | Por qué |
|---|---|---|
| Los 8 catálogos | Una línea en las reglas dice qué escenarios son crear, leer, actualizar e inactivar | Las cuatro operaciones del CRUD estaban, pero ninguna tarjeta decía dónde |
| Los 8 catálogos | Los escenarios de primera carga, cambio oficial de nombre y catálogo vacío dejan de mirar el registro y la consulta del paciente | HU-004 y HU-005 dependen de los catálogos; un escenario del catálogo que mira su pantalla cierra un círculo |
| HU-022 | Crear a mano una ocupación cuyo grupo no está activo se rechaza (escenario 12) | La carga lo validaba y la creación a mano no |
| HU-026 | El municipio creado a mano toma el departamento de sus dos primeros dígitos, y se rechaza si ese departamento no está en el catálogo (escenarios 10 y 11) | La carga lo validaba y la creación a mano no |
| HU-004 | Escenario 15 nuevo: buscar y elegir un valor de un catálogo grande, con el departamento de cada ciudad | El docente lo dejó en HU-004 (HU-027 c2) y no tenía escenario |
| HU-004 | Los escenarios 1 y 12 ya no miran la consulta del paciente | HU-005 depende de HU-004 |
| HU-001 | Las opciones de la matriz se muestran y se prueban a medida que su historia se desarrolla | Los escenarios 5 y 6 nombraban atenciones, oposición y voluntad anticipada, que no entran en las 12 |

Conteo: 30 historias y 529 escenarios. Las dependencias que quedan son las de las tablas de las
secciones 3 y 4, todas dentro de las 12. La lámina de HU-004 no tiene todavía el escenario 15, y la
hoja Backlog v3 del tablero sigue con el conteo anterior.

## 9. Enunciados de los catálogos, 2026-10-24

El autor pidió que la descripción de las historias de catálogo dijera qué operaciones cubren:
"mantenimiento" no lo decía. En la versión 2 el docente revisó una tarjeta por operación (carga,
consulta, actualización y eliminación), y la tarjeta fusionada solo nombraba la carga. El cambio se
aplicó a las 13 historias de catálogo, aunque este semestre se desarrollan 8.

| Qué | Antes | Ahora |
|---|---|---|
| Título | Catálogo de países: carga y mantenimiento | Catálogo de países: carga, consulta, actualización e inactivación |
| Quiero | cargar el catálogo de países desde su archivo oficial y mantenerlo igual a la norma ISO 3166-1 | cargar el catálogo de países desde su archivo oficial, consultarlo y agregar, actualizar, inactivar o reactivar un país, siempre igual a la norma ISO 3166-1 |
| Línea de operaciones | "CRUD por escenario", con la versión nueva solo en crear | "Operaciones por escenario", con los nombres del título; la versión nueva cuenta en las cuatro |

Ningún escenario cambió: siguen 30 historias y 529 escenarios. "Inactivación" ocupa el lugar de
"eliminación" porque nada se borra (RC-10). El Quiero conserva el límite del artículo 7, que no deja
a la IPS modificar, reducir ni adicionar un catálogo oficial: solo sigue los cambios de la fuente.

Los dos pendientes de la sección 8 quedan así:

- La lámina de HU-004 tiene el escenario 15, y las 13 láminas de catálogo tienen el título nuevo.
- La hoja Backlog v3 del tablero no cuenta escenarios, así que no había un conteo que actualizar.
  Cambiaron los 13 títulos, y la hoja Decisiones cita las historias del backlog v3 en D-02, D-04,
  D-17, D-20 y D-23.
