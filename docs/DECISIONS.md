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

## 2026-10-24: Los títulos de los catálogos nombran sus cuatro operaciones

**Contexto:** El autor, sobre el título "carga y mantenimiento" de las historias de catálogo:

- "aun no estoy convencido que literalmente se llamen mantenimiento, por que el modelo debio hacer
  arreglos para que con el encunciado de la descripcion quedara clado, por que creo que
  manentimiento es muy general"

El prompt 2 del docente pide un título "específico y orientado al resultado". En la versión 2 del
backlog el docente revisó una tarjeta por operación de cada catálogo: carga, consulta, actualización
y eliminación. La tarjeta fusionada solo nombraba la carga. La línea "CRUD por escenario" del
2026-10-22 iba en las reglas de negocio, después de la descripción, y ponía la versión nueva solo en
crear, aunque ese escenario crea, actualiza, reactiva e inactiva.

**Decisión:** Las 13 historias de catálogo se titulan "<catálogo>: carga, consulta, actualización e
inactivación". Su Quiero nombra las operaciones sin perder el límite de la fuente oficial, por
ejemplo "cargar el catálogo de países desde su archivo oficial, consultarlo y agregar, actualizar,
inactivar o reactivar un país, siempre igual a la norma ISO 3166-1". "Inactivación" ocupa el lugar
de "eliminación" porque nada se borra (RC-10).

La línea de cada tarjeta pasa a "Operaciones por escenario", con los nombres del título, y la
versión nueva cuenta en las cuatro. Se aplica a las 13 aunque este semestre se desarrollan 8, para
que Trello no muestre dos estilos de título. Ningún escenario cambia: 30 historias y 529 escenarios.

La hoja Backlog v3 del tablero no cuenta escenarios: el pendiente de actualizarla a 529, escrito el
2026-10-22, no tenía objeto.

**Alternativas:** Cambiar solo el Quiero, descartado porque el título seguía diciendo
"mantenimiento". Poner "CRUD" en el título, descartado porque es la palabra de la guía [2.3] pero un
término técnico. Cambiar solo las 8 del plan, descartado por los dos estilos de título.

**Estado:** `vigente`

## 2026-10-24: La memoria contextual vuelve a ser la memoria del modelo

**Contexto:** La entrada 2026-10-22 "El material del backlog entra en docs/context" dejó
`docs/context/MEMORIA-CONTEXTUAL.md` como historia y el estado del proyecto en `docs/`. Esa
entrada no se edita y sigue diciendo `vigente`; esta la reemplaza en ese punto. El autor, el
2026-10-24:

- "por otro lado desde ahora usaras MEMORIA-CONTEXTUAL.md como tu propia memoria, e instrucciones."

Sobre subir su carpeta tal cual a un repositorio público, que la memoria había dejado abierto:

- "si bien siento que subir tal cual archivos de mi estructura post, me parece raro, lo permitire"

**Decisión:** La memoria es la memoria de trabajo del modelo. Se lee entera antes de trabajar en el
proyecto, como pide su sección 0, y se actualiza en el mismo PR que trae una decisión del autor,
con sus palabras textuales. No manda sobre ningún documento canónico: si choca con uno, manda el
canónico y el choque se anota en la bitácora de la memoria. Entra en el orden de lectura de
`CLAUDE.md`, sección 1, antes de `docs/TEMPORARY-CONTEXT.md`.

La copia del repositorio pasa a la del autor del 2026-10-24, con la entrada de la sesión de Claude
Code que la sube. No retoma la idea descartada de confiar en la memoria del modelo entre sesiones
(2026-10-22 "Ideas descartadas"): esta memoria es un archivo del repo.

**Alternativas:** Dejarla como historia y llevar todo en `docs/`, lo del 2026-10-22, descartado
por el autor. Sacarla del repositorio público por los nombres del equipo y la ruta local,
descartado porque el autor permite subir su carpeta tal cual.

**Estado:** `vigente`

## 2026-10-24: Se trabaja en el fork del autor hasta que Atervolgo lo habilite

**Contexto:** El repositorio original es
`Atervolgo/IPS-para-con-interoperabilidad-en-Resoluci-n-866`. Ahí el autor no puede darles permisos
a los colaboradores ni a sus modelos, así que hizo una copia en su cuenta. El autor, el 2026-10-24:

- "mientras aparece Atervolgo para habilitar claude, trabajare en este repo, y buscare la manera de
  que el claude code de los demas tambien tengan los mismo permisos.."
- "cuando el dueno de Atervolgo aparezca ahi si hacemos un pull para llevar todo el trabajo mio y de
  los colaboradores que no pueden estar en el repo, hacia el original"
- "si las ramas no las necesitas borraras, por que puede haber confucion, pero recuerda dejar donde
  deberia datos de esto, por que creo que sera muy importante."

**Decisión:** El trabajo vive en `jncldrn64/IPS-para-con-interoperabilidad-en-Resoluci-n-866`, el
fork del autor, y cada PR va contra el `main` del fork. Quien abre un PR a mano elige el fork en
"base repository", porque el botón "Compare & pull request" de GitHub propone el original. El
primer PR real fue el #2 del fork, fusionado el 2026-10-24, con commits nuevos encima de d70a6f8.

Antes de ese PR hubo dos ramas que se borraron para que nadie trabaje sobre ellas. Sus commits no se
pierden: siguen en la historia de Atervolgo.

| Rama borrada | Último commit | Qué tenía | Dónde siguen sus commits |
|---|---|---|---|
| `test` | f0ab79a | Siembra, corrección del README de `docs/context/` y enunciados de catálogo | Atervolgo: su PR #3 la fusionó en 2e2faa8 y su PR #4 lo revirtió en 69675e9 |
| `claude/wizardly-newton-ztyb85` | c8b9d9c | Siembra y corrección del README; era el PR #1 del fork, cerrado sin fusionar | Atervolgo, porque c8b9d9c es padre de f0ab79a |

Para llevar el trabajo a Atervolgo va un PR del `main` del fork al `main` de Atervolgo. La base
común de los dos es d70a6f8, porque el fork no usa los commits revertidos, así que no hace falta
revertir el revert. Mientras tanto no se aprieta "Sync fork" en el fork: traería al `main` del fork
los commits revertidos de Atervolgo, que repiten el mismo contenido con otra historia.

**Alternativas:** Trabajar directo en Atervolgo, descartado porque ahí el autor no puede dar
permisos. Seguir en la rama `test`, descartado porque sus commits ya están revertidos en Atervolgo y
llevarlos de nuevo pedía revertir el revert.

**Estado:** `vigente`

## 2026-10-24: Tres integrantes con cuatro historias cada uno

**Contexto:** La entrada 2026-10-22 "Se desarrollan las 12 historias del plan B" partía de 4
integrantes con 3 historias cada uno, repartidas en `docs/context/plan-desarrollo-12-historias.md`,
secciones 3 y 4. Esa entrada no se edita; esta la reemplaza en el número de integrantes. El autor,
el 2026-10-24:

- "ahora mismo, realmente no tenemos decidido quien hace que, pasamos de 4 integrantes a 3,
  entoces 4 historias de usuario sean hechas por cada integrante, asi que crea un plan estrategico
  que quien deberia tener que carga"

**Decisión:** Las 12 historias del plan B no cambian. Se reparten entre 3 integrantes, con 4
historias cada uno. El reparto, sus criterios y el turno de cada historia están en
`docs/ROADMAP.md`, sección "Orden de las 30 historias", como propuesta del modelo pedida por el
autor. Qué persona es cada integrante no está decidido.

**Alternativas:** Dejar a una sola persona HU-001 con las dos historias que dependen de ella, HU-005
y HU-006, descartado porque esa persona espera a HU-004 sin nada que hacer y después hace las dos
una detrás de otra: el semestre pasa de 5 pasos a 6.

**Estado:** `vigente`
