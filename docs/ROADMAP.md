# ROADMAP.md: qué sigue, en qué orden

> **Rol:** plan. Ordena el trabajo en fases y no cambia una norma: una fase que necesita otra la
> cambia por la regla 2 de la jerarquía de `CLAUDE.md`. **Régimen:** se corrige. Una fase cerrada no
> se borra. **Origen:** plantilla 1.2, sembrada el 2026-10-22 (`docs/DECISIONS.md`, 2026-10-22 "Se
> adopta la plantilla 1.2").

## Principio de orden

El docente no acepta que se desarrolle una historia que dependa directamente de otra que no esté
desarrollada, aunque le dé valor al cliente, ni un dato provisional para crear su catálogo en otro
sprint. Por eso las fases siguen las dependencias de las 12 historias del plan B y no su prioridad:
primero lo que no depende de nada, después lo que depende de eso. La complejidad se estima de 1 a 10
y sube con las dependencias directas (`docs/context/plan-desarrollo-12-historias.md`, sección 1).

Los números de integrante son los del plan; qué persona es cada uno no está escrito en el material.

## Orden de las 30 historias

Cada historia tiene su turno, y ninguna empieza antes de que sus dependencias estén desarrolladas.
Las dependencias salen de la línea **Dependencias:** de cada tarjeta, en
`docs/context/trello/tarjetas/`.

**Las 12 del semestre**, del plan B (`docs/context/plan-desarrollo-12-historias.md`, secciones 3 y
4). La columna Paso es el turno: cada integrante hace sus historias en orden de paso.

| Paso | Historia | Integrante | Depende de | Escenarios | Fase |
|---|---|---|---|---|---|
| 1 | HU-022 Catálogo de ocupaciones | 1 | ninguna | 28 | 1 |
| 1 | HU-018 Catálogo de países | 2 | ninguna | 22 | 1 |
| 1 | HU-034 Catálogo de tipos de documento | 3 | ninguna | 23 | 1 |
| 1 | HU-001 Inicio de sesión con permisos por cargo | 4 | ninguna | 14 | 1 |
| 2 | HU-036 Catálogo de etnias | 1 | ninguna | 23 | 1 |
| 2 | HU-026 Catálogo de municipios | 2 | ninguna | 25 | 1 |
| 2 | HU-035 Catálogo de categorías de discapacidad | 3 | ninguna | 23 | 1 |
| 3 | HU-038 Catálogo de entidades que responden por el plan de beneficios | 2 | ninguna | 22 | 1 |
| 3 | HU-037 Catálogo de comunidades étnicas | 3 | ninguna | 22 | 1 |
| 4 | HU-004 Registro de un paciente | 1 | los 8 catálogos | 15 | 2 |
| 5 | HU-005 Consulta de un paciente | 4 | HU-001 y HU-004 | 13 | 3 |
| 6 | HU-006 Actualización y corrección de los datos de un paciente | 4 | HU-001 y HU-004 | 13 | 3 |

Los catálogos de cada integrante van en el orden de la tabla del plan, y cambiarlo no rompe nada,
porque ningún catálogo depende de otro. HU-005 va antes que HU-006 por el mismo orden que se le dio
a la oposición el 2026-10-21: registrar, consultar y después cambiar.

Entre su paso 2 y HU-004, el integrante 1 espera a que estén los 8 catálogos; entre HU-001 y
HU-005, el integrante 4 espera a HU-004. Cuánto dura esa espera depende de qué quiere decir
"desarrollada", la pregunta 1 del plan al docente.

**Las 18 que no entran en el semestre**, sin fecha ni integrante. El orden es una propuesta del
modelo del 2026-10-24, que pone primero lo que solo depende de historias ya desarrolladas. Los
grupos de la oposición y de los catálogos de la atención no dependen uno del otro, así que con más
de una persona van en paralelo.

| Orden | Historia | Depende de | Escenarios | Antes de empezar |
|---|---|---|---|---|
| 13 | HU-010 Registro de la oposición a la presunción legal de donación | HU-001 y HU-004 | 12 | nada |
| 14 | HU-011 Consulta de la oposición | HU-001, HU-004 y HU-010 | 10 | nada |
| 15 | HU-012 Actualización de la oposición | HU-001, HU-010 y HU-011 | 13 | nada |
| 16 | HU-013 Anulación de un registro de oposición hecho por error | HU-001, HU-010 y HU-011 | 12 | nada |
| 17 | HU-030 Catálogo de diagnósticos CIE-10 | ninguna | 27 | Las correcciones que les faltan a HU-030 y HU-039 a HU-042 (Backlog) |
| 18 | HU-039 Catálogo de modalidades de realización | ninguna | 23 | Las mismas |
| 19 | HU-040 Catálogo de vías de ingreso | ninguna | 23 | Las mismas |
| 20 | HU-041 Catálogo de causas que motivan la atención | ninguna | 23 | Las mismas |
| 21 | HU-042 Catálogo de enfermedades huérfanas | ninguna | 21 | Las mismas |
| 22 | HU-031 Búsqueda y selección de un diagnóstico | HU-001 y HU-030 | 13 | nada |
| 23 | HU-007 Registro de información clínica | HU-001, HU-004, HU-030, HU-031 y HU-039 a HU-042 | 19 | nada |
| 24 | HU-009 Modificación de la información clínica registrada | HU-001, HU-007, HU-030, HU-031 y HU-039 a HU-042 | 13 | nada |
| 25 | HU-008 Consulta de la información clínica de un paciente | HU-001, HU-004, HU-006, HU-007 y HU-009 | 15 | nada |
| 26 | HU-043 Anulación de una atención registrada por error | HU-001, HU-007 y HU-008 | 10 | nada |
| 27 | HU-014 Registro del documento de voluntad anticipada | HU-001, HU-004 y HU-015 | 14 | Sacar las remisiones hacia adelante del grupo, y decidir el catálogo de prestadores (D-05) |
| 28 | HU-015 Consulta del documento de voluntad anticipada | HU-001, HU-004, HU-005, HU-014, HU-016 y HU-017 | 12 | Las mismas |
| 29 | HU-016 Actualización del documento de voluntad anticipada | HU-001, HU-014, HU-015 y HU-017 | 14 | Las mismas |
| 30 | HU-017 Anulación de un registro de voluntad anticipada hecho por error | HU-001, HU-014 y HU-015 | 12 | Las mismas |

HU-008 va después de HU-009 porque su escenario 7 mira las correcciones que hace HU-009. La
voluntad anticipada va al final porque sus cuatro historias se miran entre sí: HU-014 depende de
HU-015 por su escenario 12, y HU-015 de HU-016 y HU-017 por sus escenarios 4 a 7. Mientras eso siga,
no hay un orden que respete la regla del docente. Se arregla como se arregló la oposición el
2026-10-21 (`docs/context/plan-desarrollo-12-historias.md`, sección 6), y su código de prestador
puede pedir un catálogo que no está en el backlog (D-05).

## Reglas de fase

**Estados:** `pendiente`, `en curso`, `cerrada (AAAA-MM-DD)`.

**Entrada a una fase en curso:** un ítem parqueado entra a una fase que ya arrancó solo si dejarlo
afuera hace imposible un incremento pendiente, o si obliga a rehacer trabajo ya entregado. Lo
demás espera a la fase siguiente.

**Una fase trae** un objetivo de una línea, su alcance, un criterio de aceptación verificable, qué
bloquea y qué la bloquea, y su estado.

## Fase 0: Siembra del repositorio

**Estado:** `en curso`

**Objetivo:** que el repo tenga la plantilla llena y todo el contexto del backlog.

**Alcance:**
- Documentos canónicos llenos, líneas base de prosa medidas y `docs/context/` con su `README.md`.
- Correcciones mínimas a las 12 historias del plan B.

**Criterio de aceptación:** `grep -rn '{''{' --include='*.md' .` devuelve cero y el PR de siembra
está mergeado.

**Bloquea:** fase 1.

**Bloqueada por:** nada.

## Fase 1: Lo que no depende de nada

**Estado:** `pendiente`

**Objetivo:** los 8 catálogos del paciente y el inicio de sesión, desarrollados en paralelo.

**Alcance:**
- Integrante 1: HU-022 ocupaciones (28 escenarios) y HU-036 etnias (23).
- Integrante 2: HU-018 países (22), HU-026 municipios (25) y HU-038 entidades del plan de
  beneficios (22).
- Integrante 3: HU-034 tipos de documento (23), HU-035 discapacidad (23) y HU-037 comunidades
  étnicas (22).
- Integrante 4: HU-001 inicio de sesión con permisos por cargo (14).

**Criterio de aceptación:** cada escenario de las 9 historias tiene una corrida que lo muestra.

**Bloquea:** fases 2 y 3.

**Bloqueada por:** fase 0, y el envoltorio de Maven que no corre (`docs/ARCHITECTURE.md`,
sección 6).

## Fase 2: Registro del paciente

**Estado:** `pendiente`

**Objetivo:** registrar un paciente con sus 16 atributos.

**Alcance:**
- Integrante 1: HU-004 registro de un paciente (15 escenarios, 8 dependencias directas).

**Criterio de aceptación:** cada escenario de HU-004 tiene una corrida que lo muestra.

**Bloquea:** fase 3.

**Bloqueada por:** los 8 catálogos de la fase 1.

## Fase 3: Consultar y corregir al paciente

**Estado:** `pendiente`

**Objetivo:** que la IPS consulte, actualice y corrija al paciente, con quien hizo cada cambio.

**Alcance:**
- Integrante 4: HU-005 consulta de un paciente (13) y HU-006 actualización y corrección (13).

**Criterio de aceptación:** cada escenario de HU-005 y HU-006 tiene una corrida que lo muestra.

**Bloquea:** nada.

**Bloqueada por:** HU-001 (fase 1) y HU-004 (fase 2).

## Backlog

Lo que ya se sabe que se quiere y todavía no tiene fase. Cada ítem nace con su línea de entrada.

- Llevar al docente las 14 preguntas de `docs/context/preguntas-docente.md` y las 4 del plan de
  desarrollo, entre ellas qué quiere decir "desarrollada".
  **Entró:** 2026-10-22, PR de siembra.
- Pasar a Trello los textos corregidos de las 12 tarjetas, los 13 títulos de catálogo y las 14
  láminas nuevas, con la sección "Si el tablero ya tiene las tarjetas" de
  `docs/context/trello/LEEME-kit-trello.md`.
  **Entró:** 2026-10-22, PR de siembra.
- Aplicar a HU-030 y HU-039 a HU-042 lo que les falta de las correcciones de los 8 catálogos: sacar
  las remisiones a la atención y, en HU-030, validar la creación a mano con su categoría activa. La
  línea "Operaciones por escenario" ya la tienen.
  **Entró:** 2026-10-22, PR de siembra.
- Estimar las 12 historias de 1 a 10 en clase y poner D-12 en esa escala.
  **Entró:** 2026-10-22, PR de siembra.
- Descargar los catálogos oficiales para la primera carga y cerrar el hueco de
  `docs/ARCHITECTURE.md`, sección 6.
  **Entró:** 2026-10-22, PR de siembra.
- Las 18 historias que no entran en las 12, en el orden de la sección "Orden de las 30 historias":
  la oposición (HU-010 a HU-013), los catálogos de la atención con el CIE-10 (HU-030, HU-039 a
  HU-042 y HU-031), la atención (HU-007, HU-009, HU-008 y HU-043) y la voluntad anticipada (HU-014
  a HU-017).
  **Entró:** 2026-10-22, PR de siembra.
