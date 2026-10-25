# DECISIONS.md: por qué el repo es como es

> **Rol:** historia. Dice por qué el repo es como es. Una entrada vigente gana sobre un normativo
> que la contradice: el normativo quedó viejo y se corrige. **Régimen:** append-only. Una entrada no
> se edita ni se borra aunque quede obsoleta; una nueva la reemplaza y la nombra por fecha y título.
> **Origen:** plantilla 1.2, sembrada el 2026-10-22 (`docs/DECISIONS.md`, 2026-10-22 "Se adopta la
> plantilla 1.2").

Cada entrada abre con `## AAAA-MM-DD: título` y lleva estos campos:

- **Contexto:** qué problema o pregunta la motivó.
- **Decisión:** qué se decidió.
- **Alternativas:** qué se descartó y por qué, si hubo.
- **Estado:** `vigente`, o `reemplazada por AAAA-MM-DD "título"`.

Una entrada que introduce o refina un término escribe su línea en `docs/GLOSSARY.md` en el mismo
PR.

Las decisiones del equipo sobre el producto (D-01 a D-27) viven en la hoja Decisiones del tablero,
`docs/context/tablero-validacion-ips-20oct.xlsx`, casi todas como propuestas para el docente. Acá
entran solo las del repo y las que el autor ya tomó.

## 2026-10-22: Se adopta la plantilla 1.2

**Contexto:** El repo arranca con el método de otros repos del autor, ya probado en uso. La
plantilla llegó en el commit "Add: Manejo de agente 1.2" con sus huecos sin llenar, con
`PLANTILLA.md` y con `docs/modulo/`.

**Decisión:** Se siembran los documentos canónicos con su jerarquía (`CLAUDE.md`, sección 2), su
formato común (`CLAUDE.md`, sección 4) y el piso de prosa. Las líneas base se miden en este mismo
PR. Salen `PLANTILLA.md`, que la plantilla pide no copiar, y `docs/modulo/`, porque no hay módulo.

La regla 9 de formato de `CLAUDE.md` ya no escribe las llaves dobles, y su comando las parte en
`'{''{'`. Con el texto de la plantilla el grep se encontraba a sí mismo y nunca devolvía cero. La
regla no cambia.

**Estado:** `vigente`

## 2026-10-22: Ideas descartadas

**Contexto:** Esta entrada existe para que una sesión no vuelva a proponer lo que ya se pesó y se
descartó. `CLAUDE.md` obliga a leerla antes de proponer un cambio de estructura o de método.

**Decisión:** Ninguna se retoma sin una razón nueva.

- Un repo maestro de reglas compartido entre proyectos. Cada repo es autosuficiente y no depende
  de otro para entenderse.
- Que `CLAUDE.md` guarde convenciones del código del producto. Van en `docs/DESIGN.md`.
- Confiar en la memoria del modelo entre sesiones. Lo que tiene que sobrevivir vive en el repo.
- Una historia por operación del CRUD de cada catálogo. Con el alcance 1 a 23 el backlog llega a 69
  historias, y el Prompt 2 del docente pide no dividir por operaciones de base de datos (D-17).
- Dividir el CIE-10 en cuatro historias para llegar a 30. La revisión de la tanda 5 mostró que era
  dividir por operación; quedó en HU-030 y HU-031, que tiene otro actor.
- Guardar como número los códigos alfanuméricos del anexo. Pierden los ceros iniciales
  (`docs/DESIGN.md`, sección 1).
- Poner en las historias de catálogo el escenario de elegir un valor al registrar al paciente. El
  catálogo se desarrolla antes que HU-004 y ese escenario lo haría depender de ella; va en HU-004
  (2026-10-22 "Correcciones mínimas a las 12 historias del plan B").

**Estado:** `vigente`

## 2026-10-22: El material del backlog entra en docs/context

**Contexto:** El autor pidió poblar el repo "con toda la informacion contextual para que sea
autosuficiente en la plantilla". El material estaba en un zip y en el chat donde se escribió: seis
tandas del backlog v3, 30 tarjetas con sus láminas, revisiones, modelo E-R, tablero y la memoria
contextual del modelo.

**Decisión:** Se copia tal cual en `docs/context/`, con un `README.md` que lo explica. Los archivos
conservan sus nombres en español, contra `CLAUDE.md`, sección 3, porque se citan entre sí por ruta.
Las tandas son la fuente de las historias, y una tarjeta que choca con su tanda está mal.

`MEMORIA-CONTEXTUAL.md` queda como historia. Desde esta fecha, el estado del proyecto vive en los
documentos de `docs/`.

**Alternativas:** Renombrar al inglés, descartado porque rompe las referencias internas. Dejar el
material fuera del repo, descartado porque el autor lo pidió adentro.

**Estado:** `vigente`

## 2026-10-22: El umbral de catálogo es más de 5 valores

**Contexto:** El autor escribió "5 o mas composiciones, o mas de 5 valores". El docente escribe
"Todos los campos que contienen más de 5 opciones seleccionables deben tener una entidad propia en
la base de datos" [2.3], y en la revisión general pide "cobertura de todos los catálogos para campos
con más de cinco opciones".

**Decisión:** Un dato es catálogo si tiene 6 o más valores, o si el anexo lo remite a un catálogo
externo. Se cuentan los valores del dato, y sus componentes no suman. Con esta regla, 9 de los 16 atributos
del paciente usan un catálogo y hay 8 catálogos, porque el de países sirve a nacionalidad y
residencia.

**Alternativas:** "5 o más", descartado porque contradice los dos textos del docente y haría
catálogo a la identidad de género, que tiene 5 valores.

**Estado:** `vigente`

## 2026-10-22: Se desarrollan las 12 historias del plan B

**Contexto:** El docente limitó el desarrollo a 3 historias por integrante, 12 en el semestre, y no
acepta una historia que dependa de otra sin desarrollar
(`docs/context/plan-desarrollo-12-historias.md`, sección 1). El plan dejaba elegir entre A (HU-001,
HU-010, HU-011) y B (HU-001, HU-005, HU-006).

**Decisión:** El tablero de Trello del 2026-10-22, en capturas del autor, pone en To do las 12 del
plan B: HU-018, HU-022, HU-026, HU-034, HU-035, HU-036, HU-037, HU-038, HU-004, HU-005, HU-006 y
HU-001. El orden está en `docs/ROADMAP.md`.

**Estado:** `vigente`

## 2026-10-22: Correcciones mínimas a las 12 historias del plan B

**Contexto:** Las cuatro operaciones del CRUD estaban en los 8 catálogos, pero ninguna tarjeta decía
qué escenario era cada una. Varios escenarios de los catálogos miraban la pantalla de HU-004 o de
HU-005, que dependen de ellos; HU-004 remitía a HU-005; y HU-001 nombraba opciones de historias que
no entran en las 12. Faltaban tres detalles de borde en la creación a mano y en la selección.

**Decisión:** Se corrigieron las tandas 1 y 4 y sus tarjetas sin cambiar el número de ningún
escenario. Las correcciones, una por fila, están en `docs/context/plan-desarrollo-12-historias.md`,
sección 8. El backlog pasa a 529 escenarios.

**Alternativas:** Aplicar lo mismo a HU-030 y HU-039 a HU-042, que no entran en las 12; queda en el
Backlog de `docs/ROADMAP.md`. Regenerar las láminas, que no se pudo porque sus generadores no
vinieron con el material.

**Estado:** `vigente`
