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
- Las 18 historias que no entran en las 12: la atención (HU-007 a HU-009 y HU-043), la oposición
  (HU-010 a HU-013), la voluntad anticipada (HU-014 a HU-017), el CIE-10 (HU-030 y HU-031) y los
  catálogos de la atención (HU-039 a HU-042).
  **Entró:** 2026-10-22, PR de siembra.
