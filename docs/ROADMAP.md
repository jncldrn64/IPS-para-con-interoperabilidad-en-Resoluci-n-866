# ROADMAP.md: qué sigue, en qué orden

> **Rol:** plan. Ordena el trabajo en fases y no cambia una norma: una fase que necesita otra la
> cambia por la regla 2 de la jerarquía de `CLAUDE.md`. **Régimen:** se corrige. Una fase cerrada no
> se borra. **Origen:** plantilla 1.2, sembrada el 2026-10-22 (`docs/DECISIONS.md`, 2026-10-22 "Se
> adopta la plantilla 1.2").

## Principio de orden

El docente no acepta que se desarrolle una historia que dependa directamente de otra que no esté
desarrollada, aunque le dé valor al cliente, ni un dato provisional para crear su catálogo en otro
sprint. Por eso los pasos de la fase 0 siguen las dependencias de las 12 historias del plan B y no
su prioridad: primero lo que no depende de nada, después lo que depende de eso. La complejidad se
estima de 1 a 10 y sube con las dependencias directas (`docs/context/MEMORIA-CONTEXTUAL.md`,
entrada del 2026-10-21 "Límite de 12 historias; dependencias y el paciente").

Desde el 2026-10-24 el equipo es de 3 integrantes con 4 historias cada uno (`docs/DECISIONS.md`,
2026-10-24 "Tres integrantes con cuatro historias cada uno"). Los integrantes van numerados del 1 al
3; qué persona es cada número lo decide el equipo.

## Orden de las 30 historias

Cada historia tiene su turno, y ninguna empieza antes de que sus dependencias estén desarrolladas.
Las dependencias salen de la línea **Dependencias:** de cada tarjeta, en
`docs/context/trello/tarjetas/`.

**Las 12 del semestre**, las del plan B, repartidas entre 3 integrantes. Son la fase 0. La columna
Paso es el turno, y cada integrante hace sus historias en orden de paso. La columna Tramo dice en
qué tramo de la fase 0 cae cada historia.

| Paso | Historia | Integrante | Depende de | Escenarios | Tramo |
|---|---|---|---|---|---|
| 1 | HU-001 Inicio de sesión con permisos por cargo | 1 | ninguna | 14 | 1 |
| 1 | HU-034 Catálogo de tipos de documento | 2 | ninguna | 23 | 1 |
| 1 | HU-036 Catálogo de etnias | 3 | ninguna | 23 | 1 |
| 2 | HU-022 Catálogo de ocupaciones | 1 | ninguna | 28 | 1 |
| 2 | HU-018 Catálogo de países | 2 | ninguna | 22 | 1 |
| 2 | HU-037 Catálogo de comunidades étnicas | 3 | ninguna | 22 | 1 |
| 3 | HU-026 Catálogo de municipios | 1 | ninguna | 25 | 1 |
| 3 | HU-038 Catálogo de entidades que responden por el plan de beneficios | 2 | ninguna | 22 | 1 |
| 3 | HU-035 Catálogo de categorías de discapacidad | 3 | ninguna | 23 | 1 |
| 4 | HU-004 Registro de un paciente | 2 | los 8 catálogos | 15 | 2 |
| 5 | HU-005 Consulta de un paciente | 1 | HU-001 y HU-004 | 13 | 3 |
| 5 | HU-006 Actualización y corrección de los datos de un paciente | 3 | HU-001 y HU-004 | 13 | 3 |

La carga de cada uno:

| Integrante | Historias | Escenarios | Dependencias directas | Complejidad propuesta |
|---|---|---|---|---|
| 1 | HU-001, HU-022, HU-026 y HU-005 | 80 | 2, las de HU-005 | 13 |
| 2 | HU-034, HU-018, HU-038 y HU-004 | 82 | 8, las de HU-004 | 15 |
| 3 | HU-036, HU-037, HU-035 y HU-006, más la base común de los catálogos | 81 | 2, las de HU-006 | 9, sin la base común |

El reparto es una propuesta del modelo del 2026-10-24, pedida por el autor, con estos criterios:

- Las 9 historias que no dependen de nada caben en los pasos 1 a 3, tres por integrante. En esos
  tres pasos cada uno carga 67 o 68 escenarios.
- HU-001 va primero porque HU-005 y HU-006 dependen de ella.
- HU-005 y HU-006 quedan en integrantes distintos para hacerse a la vez en el paso 5. Con la misma
  persona harían falta 6 pasos.
- HU-004 es la de más dependencias directas, 8, y la complejidad que pide el docente sube con
  ellas. Por eso su integrante no lleva los dos catálogos con jerarquía, ocupaciones y municipios,
  que son los de más escenarios (28 y 25).
- Etnias y comunidades étnicas van con la misma persona, porque HU-004 pide la comunidad solo si la
  etnia es de la 01 a la 06.
- El orden de los catálogos dentro de cada integrante se puede cambiar sin romper nada, porque
  ningún catálogo depende de otro.

En el paso 4 solo trabaja el integrante 2, con HU-004, y en el paso 5 solo los integrantes 1 y 3.
Quien espera puede revisar los PR de los demás, porque la memoria ya pide que quien aprueba no sea
quien analizó (`docs/context/MEMORIA-CONTEXTUAL.md`, sección 12). Cuánto dura cada espera depende
de qué quiere decir "desarrollada" para el docente: terminada antes de empezar la siguiente, o antes
de entregarla.

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
no hay un orden que respete la regla del docente.

Se arregla como se arregló la oposición el 2026-10-21, en orden registrar, consultar, actualizar y
anular, y cada historia mirando solo a las anteriores (`docs/context/MEMORIA-CONTEXTUAL.md`,
entrada del 2026-10-21 "Límite de 12 historias; dependencias y el paciente"). Su código de
prestador puede pedir un catálogo que no está en el backlog (D-05).

## Estimación de las 12 historias

La complejidad va de 1 a 10, donde 10 es la más difícil, y sube con las dependencias directas,
independientemente de la prioridad. La vota el equipo en clase, o con un video de todos [2.5]. La
tabla de abajo es la propuesta del modelo del 2026-10-24 para esa votación, aceptada por el autor
como punto de partida (`docs/DECISIONS.md`, 2026-10-24 "La complejidad se estima de 1 a 10"). Las
tarjetas dicen "Pendiente de estimación en clase" hasta que el equipo vote.

El criterio: complejidad = trabajo propio + dependencias directas, con tope 10. El trabajo propio
va de 1 a 3:

- **1:** catálogo cuya lista está en la hoja del anexo, sobre la base común.
- **2:** catálogo que se carga desde un archivo oficial externo.
- **3:** archivo externo con niveles, o una historia con reglas propias pesadas.

| Historia | Trabajo propio | Dependencias directas | Propuesta |
|---|---|---|---|
| HU-034 Catálogo de tipos de documento | 1 | 0 | 1 |
| HU-036 Catálogo de etnias | 1 | 0 | 1 |
| HU-035 Catálogo de categorías de discapacidad | 1 | 0 | 1 |
| HU-018 Catálogo de países | 2 | 0 | 2 |
| HU-037 Catálogo de comunidades étnicas | 2 | 0 | 2 |
| HU-038 Catálogo de entidades que responden por el plan de beneficios | 2 | 0 | 2 |
| HU-022 Catálogo de ocupaciones, con 4 niveles (D-26) | 3 | 0 | 3 |
| HU-026 Catálogo de municipios, con sus departamentos | 3 | 0 | 3 |
| HU-001 Inicio de sesión, con la matriz de permisos entera | 3 | 0 | 3 |
| HU-005 Consulta de un paciente, con el nombre del día (RC-11) | 2 | 2 | 4 |
| HU-006 Actualización y corrección, con su historial (artículo 10) | 3 | 2 | 5 |
| HU-004 Registro de un paciente, con 16 atributos y 3 condicionados | 3 | 8 | 10, por el tope (11) |

La prioridad no ordena estas 12: las tarjetas dicen "Alta" en todas. La base común de los
catálogos no es una historia y no se estima; la arma el integrante 3, que con 9 es el de menos
complejidad (`docs/DECISIONS.md`, 2026-10-24 "El integrante 3 arma la base común").

## Reglas de fase

**Estados:** `pendiente`, `en curso`, `cerrada (AAAA-MM-DD)`.

**Entrada a una fase en curso:** un ítem parqueado entra a una fase que ya arrancó solo si dejarlo
afuera hace imposible un incremento pendiente, o si obliga a rehacer trabajo ya entregado. Lo
demás espera a la fase siguiente.

**Una fase trae** un objetivo de una línea, su alcance, un criterio de aceptación verificable, qué
bloquea y qué la bloquea, y su estado.

## Siembra del repositorio

**Estado:** `cerrada (2026-10-24)`

Hasta el 2026-10-24 se llamó fase 0. El nombre pasó a las 12 historias (`docs/DECISIONS.md`,
2026-10-24 "La fase 0 son las 12 historias").

**Objetivo:** que el repo tenga la plantilla llena y todo el contexto del backlog.

**Alcance:**
- Documentos canónicos llenos, líneas base de prosa medidas y `docs/context/` con su `README.md`.
- Correcciones mínimas a las 12 historias del plan B.

**Criterio de aceptación:** `grep -rn '{''{' --include='*.md' .` devuelve cero y el PR de siembra
está mergeado.

**Bloquea:** fase 0.

**Bloqueada por:** nada.

## Fase 0: Las 12 historias del plan B

**Estado:** `pendiente`

**Objetivo:** los 8 catálogos del paciente, el inicio de sesión, y registrar, consultar y corregir
al paciente.

**Alcance:**
- Antes del paso 1: la base común de los catálogos (`docs/DESIGN.md`, sección 7), que arma el
  integrante 3. HU-001 no es un catálogo, así que no la espera.
- Tramo 1, pasos 1 a 3, lo que no depende de nada, en paralelo:
  - Integrante 1: HU-001 inicio de sesión con permisos por cargo (14 escenarios), HU-022
    ocupaciones (28) y HU-026 municipios (25).
  - Integrante 2: HU-034 tipos de documento (23), HU-018 países (22) y HU-038 entidades del plan
    de beneficios (22).
  - Integrante 3: HU-036 etnias (23), HU-037 comunidades étnicas (22) y HU-035 discapacidad (23).
- Tramo 2, paso 4, el registro del paciente con sus 16 atributos: el integrante 2 con HU-004 (15
  escenarios, 8 dependencias directas).
- Tramo 3, paso 5, consultar y corregir al paciente, con quien hizo cada cambio: el integrante 1
  con HU-005 (13) y el 3 con HU-006 (13), a la vez. Las dos dependen de HU-001 y HU-004.

**Criterio de aceptación:** cada escenario de las 12 historias tiene una corrida que lo muestra.

**Bloquea:** las 18 historias del Backlog.

**Bloqueada por:** el envoltorio de Maven que no corre (`docs/ARCHITECTURE.md`, sección 6).

## Backlog

Lo que ya se sabe que se quiere y todavía no tiene fase. Cada ítem nace con su línea de entrada.

- Pasar a Trello los textos corregidos de las 12 tarjetas, los 13 títulos de catálogo y las 14
  láminas nuevas. Las instrucciones del kit de Trello no están en el repositorio: las tiene el
  autor.
  **Entró:** 2026-10-22, PR de siembra.
- Aplicar a HU-030 y HU-039 a HU-042 lo que les falta de las correcciones de los 8 catálogos: sacar
  las remisiones a la atención y, en HU-030, validar la creación a mano con su categoría activa. La
  línea "Operaciones por escenario" ya la tienen.
  **Entró:** 2026-10-22, PR de siembra.
- Votar en clase la complejidad de las 12 historias, a partir de la propuesta de la sección
  "Estimación de las 12 historias", y pasar el número votado al campo "Estimación relativa" de cada
  tarjeta. D-12 ya está en la escala de 1 a 10.
  **Entró:** 2026-10-22, PR de siembra.
- Descargar los catálogos oficiales para la primera carga y cerrar el hueco de
  `docs/ARCHITECTURE.md`, sección 6. Antes de subir uno, o el archivo de la base que lo trae, se
  revisa su licencia y su atribución (`CLAUDE.md`, sección 10).
  **Entró:** 2026-10-22, PR de siembra.
- Las 18 historias que no entran en las 12, en el orden de la sección "Orden de las 30 historias":
  la oposición (HU-010 a HU-013), los catálogos de la atención con el CIE-10 (HU-030, HU-039 a
  HU-042 y HU-031), la atención (HU-007, HU-009, HU-008 y HU-043) y la voluntad anticipada (HU-014
  a HU-017).
  **Entró:** 2026-10-22, PR de siembra.
- Llevar el trabajo del fork al repositorio original de Atervolgo cuando su dueño habilite a Claude
  Code y a los colaboradores: un PR del `main` del fork al `main` de Atervolgo (`docs/DECISIONS.md`,
  2026-10-24 "Se trabaja en el fork del autor hasta que Atervolgo lo habilite").
  **Entró:** 2026-10-24, PR #3 del fork.
