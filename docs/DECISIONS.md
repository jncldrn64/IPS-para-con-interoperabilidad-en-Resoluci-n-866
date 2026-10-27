# DECISIONS.md: por qué el repo es como es

> **Rol:** historia. Dice por qué el repo es como es. Una entrada vigente gana sobre un normativo
> que la contradice: el normativo quedó viejo y se corrige. **Régimen:** append-only. Una entrada no
> se edita ni se borra aunque quede obsoleta; una nueva la reemplaza y la nombra por fecha y título.
> **Origen:** plantilla 1.2, sembrada el 2026-10-22 (`docs/DECISIONS.md`, 2026-10-22 "Se adopta la
> plantilla 1.2").

Cada entrada abre con `## AAAA-MM-DD: título` y lleva estos campos:

- **Contexto:** qué problema o pregunta la motivó.
- **Decisión:** qué se decidió.
- **Alternativas:** qué más se pesó y por qué quedó afuera. Si no se sabe, `**Sin origen
  recuperable.**`. La entrada que adopta la plantilla y las que solo registran, como las ideas
  descartadas, no las llevan.
- **Estado:** `vigente`. Una entrada no se edita y sigue diciendo `vigente` aunque otra la
  reemplace; la nueva la nombra al abrir su **Contexto:**, con `Reemplaza a AAAA-MM-DD "título"`.

Una entrada que introduce o refina un término escribe su línea en `docs/GLOSSARY.md` en el mismo
PR.

Las decisiones del equipo sobre el producto (D-01 a D-27) viven en `docs/REQUIREMENTS.md`, sección
6, casi todas como propuestas para el docente. Acá entran solo las del repo y las que el autor ya
tomó.

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

## 2026-10-24: docs/context guarda solo lo que usan las historias

**Contexto:** La entrada 2026-10-22 "El material del backlog entra en docs/context" copió todo el
material del backlog e hizo de las tandas la fuente de las historias. Esa entrada no se edita, y
esta la reemplaza en esos dos puntos. El autor, el 2026-10-24:

- "realmente no, de hecho el hecho que en docs/context hayas subido todo los archivos, me deja
  mal, por que no se la capacidada de los modelos de los companeeros, entindo que el .xlsx ni es
  necesario, por que ya cumplio su funcion, igual que preguntas docuente, quizas tenga sentido
  loos backlogs, pero ya tenemos la carpeta de trello, que de hecho deberia solo listar las HU,
  por que sus instrucciones es para el propio tablero de prelo, los mockups los podriamso dejar,,
  hay mucho archivos que no se si realmente deberian estar en el repo, y si tiene informacion
  relevante, para eso hay otra documentacion"
- "los comentarios del docente realmente pasaron por pracitamente todo los cambios que hemos
  hecho, podrias llevan en tu memoria ideas de si recueperamso eso o que tenemos, no?. por otro si
  en requerimentes.md supongo que tiene sentido, pero no habia un md only append o algo asi mas
  apto, no no tiene sentido?."

**Decisión:** `docs/context/` guarda las tarjetas de las 30 historias con la de reglas comunes, sus
láminas, el modelo E-R, que la guía pide en el repositorio [3.6], y la memoria contextual. Las
tarjetas pasan a ser la fuente de las historias. Salen las seis tandas, el tablero, las preguntas al
docente, el plan de desarrollo, el plan de la reescritura del backlog, las revisiones y las
instrucciones del kit de Trello. Se recuperan del commit fa95eaf, y `docs/context/README.md` dice
cómo.

Lo que valía se mudó: los 16 atributos del paciente y las 27 decisiones del equipo pasan a
`docs/REQUIREMENTS.md`, secciones 3 y 6. La matriz de permisos y las reglas RC-1 a RC-11 ya estaban
en la tarjeta de reglas comunes. Qué comentario del docente atiende cada escenario queda solo en la
historia de git, y la memoria anota cómo traerlo de vuelta.

Las decisiones del equipo van en `docs/REQUIREMENTS.md` porque cambian de estado: casi todas son
propuestas que el docente o el equipo todavía tienen que confirmar. Este archivo es append-only y
guarda lo que ya se decidió, así que cuando una D-n se decida entra acá su entrada y su fila en
`docs/REQUIREMENTS.md` cambia de estado. Con menos archivos en el corpus, las líneas base de prosa
de `CLAUDE.md`, sección 9, se vuelven a medir con esta fecha.

**Alternativas:** Dejar las tandas como fuente y sacar las tarjetas, descartado porque las tarjetas
son lo que se pega en Trello y el autor quiere esa carpeta con las historias. Llevar las D-n a este
archivo, descartado porque sus estados cambian y este archivo no se edita.

**Estado:** `vigente`

## 2026-10-24: El primer sprint arranca en terminal

**Contexto:** La guía pide "desarrollar una aplicación web" [2], y D-10 quedó abierta hasta que el
docente dijera qué debía mostrar el primer sprint. El autor, el 2026-10-24:

- "el profesor no espera que hagamoos todo a la primera, es mas nos dijo que por poder puede ser
  en terminal"
- "y si en terminal, se que tenemos los mukpts, pero podemos al menos tener el esqueleuto en
  terminal, pero ya entraremos en las deseciones de arquitectura"

**Decisión:** El primer sprint tiene al menos el esqueleto en terminal, y las láminas siguen
diciendo qué muestra cada pantalla. La base de datos, el paquete y el resto de la arquitectura se
deciden después, y mientras tanto siguen en `docs/DESIGN.md`, sección 6. D-10 queda respondida.

**Alternativas:** Hacer la aplicación web desde el primer sprint, como dice la guía, descartado
porque el docente acepta terminal y el primer sprint busca un mínimo que funcione.

**Estado:** `vigente`

## 2026-10-24: La fase 0 son las 12 historias

**Contexto:** En `docs/ROADMAP.md`, la fase 0 era la siembra del repositorio, cerrada el
2026-10-24, y las 12 historias del plan B iban en las fases 1 a 3. El autor ya llamaba a ese plan
"plan 0 o fase 0 del proyecto" (`docs/context/MEMORIA-CONTEXTUAL.md`, entrada del 2026-10-24 "Lo
mínimo para el cliente, y docs/context sobra"). El 2026-10-24 escribió:

- "la fase 0 se define como completa cuando las 12 historias de hu tenga sus respectivos
  desarrollos"

**Decisión:** La fase 0 son las 12 historias del plan B. Se cierra cuando cada escenario de las 12
tiene una corrida que lo muestra, la prueba que ya pide `docs/DESIGN.md`, sección 3. Las fases 1 a
3 pasan a ser tres tramos de la fase 0, con los mismos 5 pasos, y antes del paso 1 va la base común
de los catálogos (2026-10-24 "Los catálogos comparten una base común").

La siembra se queda en el ROADMAP como etapa previa cerrada, sin número, porque una fase cerrada no
se borra. Qué entiende el docente por "desarrollada" sigue abierto.

**Alternativas:** Dejar la siembra como fase 0 y las 12 historias en las fases 1 a 3, descartado
porque el autor llama fase 0 al plan de las 12.

**Estado:** `vigente`

## 2026-10-24: La base es H2 en un archivo que va en git

**Contexto:** `pom.xml` trae los controladores de H2, SQL Server, MySQL y MariaDB, y la entrada
2026-10-24 "El primer sprint arranca en terminal" dejó la base para después. El 2026-10-18 el autor
ya pedía "que sea terminal y una db en H2" (`docs/context/MEMORIA-CONTEXTUAL.md`, entrada del
2026-10-18 "El autor quiere rehacer todo desde el anexo y hacer el sprint 1 en terminal con H2").
El 2026-10-24 escribió:

- "como bien sabras manejaremos H2, pero no en memoria, en archivo, asi que comenzarimaso primero
  nostros poblando estos detalles, por si los modelos no encuentran las intruciicones, al menos se
  preguntes de por que estos cambios y llegen a los documentos.... por que en H2? para que
  tengamos todo la misma copia...."

El modelo le dio dos caminos para tener la misma copia: dejar el archivo de la base fuera de git y
compartir lo que lo crea, o subir el archivo. Recomendó el primero, y el autor eligió el segundo.

**Decisión:** La base de datos es H2 en modo archivo, así que los datos quedan entre una corrida y
la siguiente. El archivo de la base va en el repositorio, para que todos trabajen sobre la misma
copia. Los controladores de SQL Server, MySQL y MariaDB quedan sin uso.

Lo que cuesta, dicho al autor antes de elegir:

- El archivo es binario. Si dos PR lo cambian, git no puede mezclarlos, y el segundo tiene que
  rehacer sus cambios sobre el archivo del primero.
- Correr el programa lo cambia: un paciente de prueba queda guardado en él, y git lo cuenta como
  un cambio más.
- Cómo se turnan los integrantes para cambiarlo no está decidido (`docs/DESIGN.md`, sección "Sin
  escribir todavía").

**Alternativas:** H2 en memoria, descartado por el autor: la base se borra al cerrar el programa, y
cada prueba de HU-005 o HU-006 pide registrar otra vez al paciente. Dejar el archivo fuera de git y
subir lo que lo crea, que son las entidades, los CSV oficiales de los catálogos y los usuarios
iniciales: era la recomendación del modelo, y el autor eligió subir el archivo.

**Estado:** `vigente`

## 2026-10-24: El código se separa en modelo, vista y controlador

**Contexto:** El autor, el 2026-10-24:

- "quiero que usemos modelo vista controlador,, entiedo que eso no tiene sentido mas cuando
  presinament no usaremos vistas para la fase 0, pero realmente el desarrollo de estas Hu deberia
  ser simples,, la mayoria son un crud, por lo que necistamos una interface repositorf que se
  extieda con crud, luego un cristo de reglas de negocio como interfaces, cada una con su logica,
  aun que entiendo que seria mejor definir reglas de negocio de la HU en si a separarala, por que
  relamente no se si se puede hacer implements de varias interfaces, y ahi si, que cada uno
  instancie la interface repository de su entidad en su servicio y que haga implemtens en las
  reglas de negocio.... controlles no necesitamos, por que relamente con los metodos resultantes
  podriamos llamarlos desde un menu en terminal con swich,,, se critico con todo lo que he dicho y
  si quieres corrigeme"

**Decisión:** El código tiene tres partes:

- **Modelo:** las entidades, una interfaz de repositorio por entidad y un servicio por historia
  con las reglas de negocio de esa historia.
- **Vista:** lo que el programa imprime en la terminal, que sigue la lámina de cada historia.
- **Controlador:** el menú en terminal, que lee la opción con un switch y llama al servicio. En la
  fase 0 no hay controladores web.

El modelo corrigió tres puntos, que el autor no discutió. Lo que se imprime es la vista y el menú
es el controlador, así que el MVC sigue entero sin web, y cuando llegue la web que pide la guía [2]
se cambia el menú y los servicios se quedan. Un servicio no instancia la interfaz de su
repositorio, porque una interfaz no tiene código: Spring Data JPA, que está en `pom.xml`, genera la
implementación y se la pasa al servicio. Esto no se probó, porque el build no corre
(`docs/ARCHITECTURE.md`, sección 6).

El tercero: Java deja que una clase implemente varias interfaces, pero una interfaz por regla con
un solo servicio que la implementa es un método con un archivo más. Las reglas que se repiten van
en la base común de los catálogos, y las de una sola historia, en su servicio.

**Alternativas:** Controladores web desde la fase 0, descartado porque la fase 0 corre en terminal.

**Estado:** `vigente`

## 2026-10-24: Los catálogos comparten una base común

**Contexto:** 8 de las 12 historias de la fase 0 son catálogos con las mismas reglas, RC-1 a RC-11
de la tarjeta de reglas comunes y la búsqueda de D-23. El autor propuso escribir las reglas de
negocio como interfaces (cita de la entrada anterior). El modelo le dio tres lugares donde ponerlas
y dos momentos para armar lo común, y el autor eligió la base común de catálogos, armada antes del
paso 1.

**Decisión:** Las reglas que comparten los 8 catálogos se escriben una vez, en una base que usan
sus 8 servicios. Cada servicio agrega solo las reglas de su historia, como el padre leído del
código en HU-022 (D-26). La base no es una historia: es trabajo técnico de la fase 0, y la arma una
persona antes del paso 1. Quién no está decidido.

**Alternativas:**

- Una interfaz por regla, con su lógica, que cada servicio implementa. Java lo permite, pero una
  interfaz no guarda el repositorio, así que cada regla se lo tendría que pedir al servicio.
- Cada servicio con todas sus reglas. Es lo más rápido de empezar, y las reglas comunes quedan
  copiadas en 8 servicios.
- Armar la base dentro de HU-034, en el paso 1. HU-036, también del paso 1, tendría que esperarla.

**Estado:** `vigente`

## 2026-10-24: El archivo de la base trae los catálogos cargados

**Contexto:** La entrada 2026-10-24 "La base es H2 en un archivo que va en git" dio como razón que
todos tengan la misma copia. El autor aclaró para qué, el 2026-10-24:

- "haber realmente es para que si uno tiene que ingresar un catalogo, la db este ya ahi, mas facil
  que poner como lista de ejemplo todo un documento"

**Decisión:** El archivo de la base que va en git trae los catálogos ya cargados. Quien necesita
uno, como HU-004 los 8, lo encuentra en el archivo, y nadie copia un documento oficial entero como
lista de ejemplo.

Lo que el modelo agregó, sin respuesta del autor todavía:

- Cada catálogo entra al archivo con la carga de su propia historia, desde el archivo oficial
  (RC-8). Así no es un dato provisional, que el docente no acepta (`docs/ROADMAP.md`, sección
  "Principio de orden").
- En los pasos 1 a 3 dos o tres integrantes cargan catálogos a la vez, y cada PR cambia el
  archivo. Git no los mezcla, así que el segundo PR toma el archivo de `main` y vuelve a correr su
  carga.
- El archivo copia datos de terceros, como la DIVIPOLA, la CIUO-88 A.C. y las tablas de SISPRO.
  Antes del primer commit con un catálogo se revisa con qué licencia se publica cada uno
  (`CLAUDE.md`, sección 10).

**Alternativas:** Poner el documento oficial como lista de ejemplo, descartado por el autor: es más
trabajo que tener la base ya cargada.

**Estado:** `vigente`

## 2026-10-24: La complejidad se estima de 1 a 10

**Contexto:** El 2026-10-21 el docente dijo que la complejidad va de 1 a 10 y sube con las
dependencias directas, independientemente de la prioridad (`docs/context/MEMORIA-CONTEXTUAL.md`,
entrada del 2026-10-21 "Límite de 12 historias; dependencias y el paciente"). D-12 seguía con la
serie 1, 2, 3, 5, 8, 13. La guía pide historias "priorizadas y estimadas correctamente en clase", o
un video con todos [2.5]. El autor, el 2026-10-24:

- "aun faltan detalles como la estimacion, si bien tenemos la prioridad, por que la prioridad
  entiedo que es aqueyo que le da segun el principio must valor a la entrega al cliente, la
  estimacion de complejidad de 1 a 10 donde 10 es muy dificil realmente es lo que podemos definir"

**Decisión:** La complejidad va de 1 a 10, donde 10 es la más difícil. El modelo propuso un
criterio, trabajo propio de 1 a 3 más las dependencias directas con tope 10, y una tabla con las
12 historias. El autor la aceptó como propuesta para votar en clase, y está en `docs/ROADMAP.md`,
sección "Estimación de las 12 historias". El número que vale es el que vote el equipo, y hasta
entonces las tarjetas dicen "Pendiente de estimación en clase". D-12 pasa a esta escala.

**Alternativas:** Escribir solo el criterio, sin números, descartado por el autor. Mantener la
serie 1, 2, 3, 5, 8, 13 de D-12, descartado porque el docente pidió de 1 a 10.

**Estado:** `vigente`

## 2026-10-24: El integrante 3 arma la base común

**Contexto:** La entrada 2026-10-24 "Los catálogos comparten una base común" dejó sin decidir quién
la arma. Con la propuesta de estimación, el integrante 3 suma 9, el 1 suma 13 y el 2 suma 15
(`docs/ROADMAP.md`, sección "Estimación de las 12 historias").

**Decisión:** El integrante 3 arma la base común antes del paso 1. Sus tres catálogos, HU-036,
HU-037 y HU-035, son los primeros que la usan.

**Alternativas:** Dejarlo para después, descartado por el autor.

**Estado:** `vigente`

## 2026-10-25: Se adopta la plantilla 1.3

**Contexto:** Reemplaza a 2026-10-22 "Se adopta la plantilla 1.2" en lo que la plantilla 1.3
cambia. La 1.3 salió de comparar este repo con otros tres que usan el mismo método, y corrige
frases que la 1.2 dejaba sin escribir o escribía de dos maneras.

**Decisión:** `CLAUDE.md` toma de la 1.3 cinco cosas que no tienen entrada propia:

- Una decisión nueva nombra a la que reemplaza al abrir su **Contexto:**, y los campos de una
  entrada están al principio de este archivo, con la regla de **Alternativas:**.
- Un hueco abre con su fecha y un título en negrita, que es la forma que ya tenía el único hueco
  de `docs/ARCHITECTURE.md`.
- Las subsecciones de una sección del CHANGELOG van en el orden de Keep a Changelog.
- La sección de un PR doc-only abre con `Solo documentación.`, como ya abrían las siete del
  CHANGELOG.
- Un commit hecho desde la web de GitHub también lleva su tipo.

Lo que la 1.3 cambia y tiene entrada propia: `AGENTS.md`, la versión de un PR doc-only y el
trailer del commit, en las tres entradas siguientes.

**Estado:** `vigente`

## 2026-10-25: AGENTS.md es una isla que CLAUDE.md no nombra

**Contexto:** Reemplaza a 2026-10-22 "Se adopta la plantilla 1.2" en lo que dice de `AGENTS.md`.
La plantilla lo trajo como documento canónico de tipo puerta, con su bloque de cita, y `CLAUDE.md`
lo nombraba en tres lugares. El autor dijo el 2026-10-25 para qué sirve, con sus palabras sin
corregir:

- "agents.mi deberia ser una entrada barata y bastardizada, una isla para cuando el agente que
  este leyendo comienze por ese archivo y no sepa por donde ir, pero si el modelo lee claude.md
  realmente no es necesario que ni sepa ra existencia de agents.md"

**Decisión:** `AGENTS.md` queda en dos oraciones que mandan a `CLAUDE.md`, sin bloque de cita. Sale
de la tabla de tipos, de la regla 6 de la jerarquía y de la lista de documentos de la raíz.
`README.md` manda a `CLAUDE.md`. El piso lo sigue midiendo, porque su corpus son todos los `.md`
versionados y no hace falta nombrarlo.

**Alternativas:** Dejarlo como puerta con su bloque, que es lo que el autor señaló como error de la
plantilla. Sacarlo también del corpus del piso obligaría a nombrarlo en el comando, justo lo que
esta decisión evita.

**Estado:** `vigente`

## 2026-10-25: La sección de un PR doc-only lleva la versión siguiente

**Contexto:** `CLAUDE.md`, sección 6, decía que un PR solo de documentación "no sube la versión", y
una línea más abajo, que puede dejar el CHANGELOG adelante de la versión que muestra el programa.
Las dos frases chocaban, y se leyó la primera: el CHANGELOG tiene siete secciones `v0.1`, del
2026-10-22 y del 2026-10-24.

**Decisión:** La sección de un PR doc-only lleva la versión siguiente del CHANGELOG, y lo que no se
toca es la versión que muestra el programa. Las siete secciones `v0.1` quedan como están, porque
una sección publicada no se reescribe. La próxima es `v0.2`.

**Alternativas:** Numerar las secciones de documentación con un tercer segmento, como `v0.1.1`.
Resolvía el choque igual, pero suma una forma de versión que el método no usa en ningún lado.

**Estado:** `vigente`

## 2026-10-25: El commit conserva su trailer

**Contexto:** La herramienta cierra cada commit con dos líneas, `Co-Authored-By` y
`Claude-Session`, que nombran al coautor y la sesión. Los 9 commits hechos desde una sesión desde
la siembra las llevan, y nada en `CLAUDE.md` decía si se quedan. El 2026-10-25 el autor eligió
escribirlo.

**Decisión:** El commit conserva esas dos líneas al final del mensaje, después de la referencia al
CHANGELOG. `CLAUDE.md`, sección 7, lo dice.

**Alternativas:** Dejarlo sin escribir, como estaba. La práctica no cambiaba, pero una sesión que
reescribiera mensajes podía borrarlas sin romper ninguna regla.

**Estado:** `vigente`

## 2026-10-25: docs/context sale del corpus del piso

**Contexto:** El piso de prosa medía todos los `.md` versionados, y 34 de ellos viven en
`docs/context/`: las tarjetas de Trello, el modelo E-R y `MEMORIA-CONTEXTUAL.md`. La línea base
del 2026-10-24 decía que todo lo que sumaba estaba ahí, y que los documentos canónicos daban cero.
Un día después la regla 1 había pasado de 8 a 9 líneas y la regla 8 de 99 a 100 planas, las dos en
`MEMORIA-CONTEXTUAL.md`. Esa memoria guarda las palabras del autor sin corregir, y las tarjetas son
el texto que se pega en Trello.

**Decisión:** `docs/context/` sale del corpus, como `docs/TEMPORARY-CONTEXT.md`. El comando de
`CLAUDE.md`, sección 9, la deja afuera con `':!:docs/context/*'`, y las líneas base se miden de
nuevo sobre los 11 archivos que quedan. Dan cero en las ocho reglas.

**Alternativas:** El autor pesó dos más. Dejarla adentro y reescribir la oración que sumó la terna
100 arreglaba el número de hoy, pero la memoria vuelve a crecer con cada decisión. Dejarla adentro
sin tocar nada y anotar la subida en el PR dejaba una línea base que sube sin prosa nueva en los
documentos que el piso cuida.

**Estado:** `vigente`

## 2026-10-25: Un catálogo se carga desde un CSV, con la ruta en la terminal

**Contexto:** La entrada 2026-10-24 "El archivo de la base trae los catálogos cargados" dejó sin
respuesta del autor que cada catálogo entre al archivo con la carga de su historia. RC-8, en la
tarjeta de reglas comunes, ya pide subir el archivo oficial en CSV. El autor, el 2026-10-25:

- "¿Cada catálogo se carga al archivo desde su propia historia? Bueno esa es la idea en parte? se
  que cada catalogo tiene su estandar, pero entiendo que si bien la idea es tener el crud donde
  subirlo elemento por elemento, entiendo que primero tenemos que convertir los documentos
  nuevmante antes de moverlo, y por lo que veo podriamos solicitar en terminal archivos, (aun que
  seria mejor en vistas), pero supongo que via terminal, se puede especificar una ruta a una hoja
  de calculo de extencion de codigo abierto..."
- "Haber si CSV, pero no quiere decir que no tenga alternativas, como ir uno por uno"

**Decisión:** Cada catálogo entra al archivo de la base con la carga de su propia historia, desde
el CSV de RC-8. En la terminal, el menú pide la ruta del archivo. Los datos de la versión que pide
RC-8 también se escriben ahí, y el servicio hace la carga con todo eso.

Convertir el archivo oficial a CSV es un paso previo: RC-8 ya lo describe para el que viene en
Excel. Cargar uno por uno sigue existiendo como la creación a mano de un valor, que RC-3 permite
solo con la referencia de un cambio oficial.

**Alternativas:** Una hoja de cálculo ODS, que el autor mencionó. Java necesita una biblioteca
aparte para leerla, y RC-8 y las 8 tarjetas de catálogo pasarían de CSV a ODS; el autor se quedó
con el CSV.

**Estado:** `vigente`

## 2026-10-25: El archivo de la base se cambia por turnos

**Contexto:** Reemplaza a 2026-10-24 "El archivo de la base trae los catálogos cargados" en cómo se
resuelve un choque. Esa entrada proponía que, cuando dos PR cambian el archivo, el segundo vuelva a
correr su carga sobre el de `main`. El autor, el 2026-10-25:

- "es un lio la verdad, por que por eso pense que de una vez el h2 en el repo, asi si necesutas
  algo literalmente puedes saber si esta en la db o no, por que otro integrante esta desarrolando
  su parte. decir que cada uno carga el csv a la vez en su entorto de desarrollo por separado no
  me suena, diria por turnos, pero quizas hasta necesitemos saber quien en que momente esta
  haciendo que."

**Decisión:** El archivo de la base se cambia por turnos: un solo PR que lo cambia abierto a la
vez, y el siguiente espera a que ese se mezcle. Trabajar en paralelo sigue siendo posible: lo único
que espera turno es el commit del archivo. Cómo se sabe quién tiene el turno no está decidido.

El modelo propuso que el turno lo tenga quien tiene abierto el PR que cambia el archivo, porque la
lista de PR de GitHub ya muestra quién es y desde cuándo.

**Alternativas:** Que el segundo PR rehaga su carga sobre el archivo de `main`, que era la
recomendación del modelo. El autor la descartó porque cada integrante cargaría su CSV por separado,
en su propio entorno.

**Estado:** `vigente`

## 2026-10-25: Las pruebas corren sobre otra base

**Contexto:** La entrada 2026-10-24 "La base es H2 en un archivo que va en git" dejó escrito que
correr el programa cambia el archivo, así que un paciente de prueba puede llegar a git. El autor,
el 2026-10-25:

- "haber en las apliciacion propietateus podemos ya definir cosas, como el autoudtare y el
  ifdontexiste, si el usuario quiere probar, solo tiene que cambiar el nombre de la db en
  aplicgation proietes y el modelo puede guiarlo o hacerlo automaticamente"

**Decisión:** `application.properties` define que las tablas se actualicen solas según las
entidades y que la base se cree si no existe. Para probar, se cambia el nombre de la base en ese
archivo, y el modelo guía el cambio o lo hace. Qué propiedades exactas lo logran se escribe con su
corrida en el primer PR de código, porque el build todavía no corre (`docs/ARCHITECTURE.md`,
sección 6).

Lo que el modelo agregó, sin respuesta del autor todavía:

- Una base de prueba nueva nace vacía, sin catálogos, y ahí no se puede probar HU-004. Tiene que
  arrancar como copia del archivo de la base.
- `application.properties` va en git. Si alguien sube el nombre de la base de prueba, todos quedan
  apuntando a ella. Un perfil de Spring o una variable de entorno cambian el nombre sin tocar ese
  archivo; no se probó en este repo.

**Alternativas:** **Sin origen recuperable.**

**Estado:** `vigente`

## 2026-10-25: El autor es el integrante 3

**Contexto:** `docs/ROADMAP.md` numera a los integrantes del 1 al 3 y dejaba al equipo decidir qué
persona es cada número. La base común es del integrante 3 (2026-10-24 "El integrante 3 arma la base
común"), y el autor quería armar él el catálogo de ejemplo. El modelo le preguntó cómo quedaba eso,
y el autor eligió la opción "Yo soy el integrante 3".

**Decisión:** El autor es el integrante 3: arma la base común y sus cuatro historias, HU-036,
HU-037, HU-035 y HU-006. Quién es el 1 y quién el 2 lo decide el equipo.

**Alternativas:** Que el autor armara la base común y el ejemplo aparte del reparto, o solo el CSV
del ejemplo. El autor eligió ser el integrante 3, y así ninguna decisión anterior cambia.

**Estado:** `vigente`

## 2026-10-25: HU-036 es el catálogo de ejemplo

**Contexto:** El autor, el 2026-10-25, sobre quién convierte los archivos oficiales a CSV:

- "yo me encargare del 3ro, por que tiene pinta que puede rompper la compatibilida, bueno no
  realmente yo deberia encargarme de hacer la base de jemplo de un catalogo, como minimo"

Esa conversión es donde se rompe la compatibilidad: Excel cambia "01" por "1" y puede dañar las
tildes. Por eso RC-8, en la tarjeta de reglas comunes, pide guardar en CSV UTF-8 con las columnas
de código en formato texto.

**Decisión:** HU-036, etnias, es el catálogo de ejemplo: el primer CSV y la primera carga en el
archivo de la base, que los otros 7 copian. Va primero en el paso 1, con la base común. Tiene 7
valores, y su lista sale de la hoja 15 del anexo, que es parte de la resolución. Si los CSV
convertidos van en git lo decide el autor con este ejemplo.

**Alternativas:** HU-034, tipos de documento, con 14 valores de la hoja 13, y HU-035, discapacidad,
con 8. El autor eligió HU-036, que además es el primer catálogo del integrante 3.

**Estado:** `vigente`

## 2026-10-25: El turno del archivo de la base es del PR abierto

**Contexto:** La entrada 2026-10-25 "El archivo de la base se cambia por turnos" dejó sin decidir
cómo se sabe quién tiene el turno, con una propuesta del modelo. El autor la aceptó el 2026-10-25.

**Decisión:** El turno lo tiene quien tenga abierto el PR que cambia el archivo de la base. La lista
de PR de GitHub muestra quién es y desde cuándo, y el siguiente abre el suyo cuando ese se mezcla o
se cierra.

**Alternativas:** **Sin origen recuperable.**

**Estado:** `vigente`

## 2026-10-25: La base de prueba arranca como copia del archivo

**Contexto:** La entrada 2026-10-25 "Las pruebas corren sobre otra base" dejó sin respuesta que una
base de prueba nueva nace vacía, sin catálogos, y que con ella no se puede probar HU-004. El autor
aceptó el 2026-10-25 la propuesta del modelo.

**Decisión:** La base de prueba arranca como copia del archivo de la base, así que ya trae los
catálogos cargados. Cómo se hace la copia se escribe con su corrida en el primer PR de código.

**Alternativas:** Una base de prueba vacía, como la que se crea sola cuando no existe. No sirve para
probar HU-004, que necesita los 8 catálogos.

**Estado:** `vigente`

## 2026-10-25: La base común es una clase con código, nombre, estado y versión

**Contexto:** La entrada 2026-10-24 "Los catálogos comparten una base común" dijo dónde van las
reglas de los catálogos, y no cómo se guarda un valor. El autor empezó por ahí el 2026-10-25:

- "yo comenzaria por la entidad que tengdra todo, algo sumple, realmente el catalogo son enums,
  pero no creo que pueda almacenar enums de forma dinamica, por lo que entiedo que necesitamos
  almacenera una especie de array multidimmencional."

Un enum de Java fija sus valores al compilar, así que agregar o inactivar un valor obligaría a
cambiar el código. Los valores de un catálogo llegan de un archivo oficial mientras el programa
corre (RC-8), y el arreglo que describe el autor es una tabla: una fila por valor y una columna
por dato. El modelo le hizo cuatro preguntas, y el autor eligió en las cuatro la opción
recomendada.

**Decisión:** Cada catálogo tiene su propia entidad y su tabla, como pide [2.3]. Las 8 heredan de
una clase base con cuatro campos:

- **código:** la llave, guardada como texto con sus ceros (D-18).
- **nombre:** el que trae la fuente.
- **estado:** activo o inactivo, porque un valor no se borra (RC-4).
- **versión:** la del archivo oficial que trajo el código (RC-8).

Cada catálogo agrega lo suyo, como el padre de una ocupación (D-26) o el departamento de un
municipio. El historial de cada valor va en una tabla de cambios común a los catálogos, de la que
sale el nombre que un valor tenía en una fecha (RC-11). Qué anotación de JPA hace la herencia se
escribe con su corrida en el primer PR de código.

**Alternativas:**

- Una sola tabla para todos los catálogos. Ahorra la clase base, pero ningún catálogo tendría la
  entidad propia que pide [2.3].
- Solo código y nombre en la base. Los escenarios de HU-036 piden inactivar y cargar con versión,
  así que los otros dos campos llegarían antes de cerrarla.
- Un número interno como llave. El paciente guardaría ese número, y para intercambiar el dato
  habría que buscar su código.
- El último motivo dentro de cada valor, sin tabla de cambios. No alcanza para RC-11.

**Estado:** `vigente`

## 2026-10-26: El PR va contra main del fork

**Contexto:** La entrada 2026-10-24 "Se trabaja en el fork del autor hasta que Atervolgo lo
habilite" decidió trabajar en el fork, pero `CLAUDE.md` no lo decía, y un modelo nuevo empieza por
ahí. En un fork, la web de GitHub propone como destino el repositorio original: así llegó a
Atervolgo el PR #3 del 2026-10-24. El autor preguntó el 2026-10-25, y pidió escribirlo el
2026-10-26:

- "anhes de continuar discutiendo, como me aseguro que el modelo de mis contribuidores, son
  capaces de hacer los PR?"
- "procede con el Pr con dicha informacion"

**Decisión:** `CLAUDE.md`, sección 7, dice que el PR va contra `main` del fork y nunca contra
Atervolgo. También dice cómo se fija el destino con `gh pr create`.

**Alternativas:** Dejarlo solo en `docs/DECISIONS.md`, como estaba. Un modelo que sigue lo que
GitHub le propone abriría el PR en Atervolgo.

**Estado:** `vigente`

## 2026-10-26: Un integrante nuevo abre un PR de prueba

**Contexto:** El 2026-10-25 la lista de colaboradores del fork tenía un solo nombre, el del autor.
Ningún compañero podía abrir un PR, y nadie sabía si sus modelos siguen el método. Las citas del
autor están en la entrada anterior.

**Decisión:** Antes de su primera historia, cada integrante abre un PR de prueba con el pedido de
`CLAUDE.md`, sección 7. El autor lo revisa contra la lista de esa sección y lo cierra sin mezclar.
Las condiciones van junto a los pasos: ser colaborador con rol Write, conectar la cuenta de GitHub
si usa la nube, y tener git, bash y python3 si trabaja en su equipo.

**Alternativas:**

- Darle la primera historia sin prueba. Un error de método aparecería en un PR con código, donde
  cuesta más separarlo del resto.
- Escribir la prueba en un documento aparte. Un documento nuevo pide permiso al autor
  (`CLAUDE.md`, sección 3), y el pedido cabe en la sección 7.

**Estado:** `vigente`

## 2026-10-26: El primer código arma la base común con etnias y comunidades étnicas

**Contexto:** El autor propuso el 2026-10-26 el código de su primera parte, HU-036 y HU-037, en 9
puntos. La entidad llevaba la llave como `Integer` con `@Column(length = 99)`, el repositorio
extendía `ListCrudRepository` y un servicio implementaba la interfaz `CrudEntidad`, con el `switch`
en el controlador. El modelo lo probó en una copia aparte y le contestó que cuatro cosas chocaban
con lo escrito: el código como número, una entidad sin estado, versión ni clase base, un
repositorio que borra y las reglas en el controlador. El autor respondió:

- "y no crees que choca mucho con tu catalogo "funndamental" de donde hacemos super?  entiedo el
  concepto, y la verdad es hasta interesante, puedes usarlo, pero realmente no se hasta que puento
  ese catalodo maestro o mas bien la entidad padre pueda cambiar o alterar los datos de los varoles
  que obitene, por que  si el padre dice @Column(length = 2), el hijo lo .puede cambiar?... y tienes
  razon, no es int la primaria , es strnig para almacenar codigos, que no sean 000, pero no estoy
  seguro, supongo que la logica podria evitar eso, , asi que supongo que debe haber anotaciones
  adicinales que impidan valores en el int, si no las encuentra,s procede con el string, y codigfica
  tu, que la logica ya te la he puesto, realmente entraremos a  discutir de lleno el swict, por que
  realmente una vez la base de todas la HU estes hecha, lo mas importarte a codificar grupalmente es
  el como se presentaria la info, aun que supongco que eso no da valor a la entrega final, pero no
  se si si literalmenet el profesor me permita hacer todo via postman, de ahi que quiera la version
  bastardizada en terminal, y por lo que veo tienes que importar otras librerias para poder que
  usemos la terminal...."

**Decisión:**

- La llave es el código como `String`. Ninguna anotación de un `Integer` guarda "01", porque 01 y 1
  son el mismo entero, y `@Column(length = 2)` sobre un `Integer` deja la columna como `INTEGER`.
- `Catalogo` es la clase padre, con `@MappedSuperclass`, y cada hija tiene su tabla. La hija cambia
  el largo que hereda con `@AttributeOverride`, y su formato va con `@Pattern` sobre `getCodigo`:
  la columna del código sale de 2 caracteres en `Etnia` y de 3 en `ComunidadEtnica`. Lo comprueba
  `CatalogosTest.cadaCatalogoTieneSuTablaConElLargoDeSuCodigo`.
- El 99 de la hoja 15 del anexo es la columna Formato: dos dígitos. El largo de una etnia es 2, y el
  de una comunidad étnica, 3.
- La terminal no pide librerías nuevas. El menú es un `CommandLineRunner` que lee con `Scanner`, y
  los dos vienen con Spring Boot y con Java.
- El menú en terminal va igual, por si el docente no acepta probar con Postman. El `switch` y la
  presentación se discuten después entre los tres integrantes, así que el menú de hoy es
  provisional.

Lo que el modelo agregó, sin respuesta del autor todavía:

- El repositorio extiende `Repository` y declara `findAllByOrderByCodigoAsc`, `findById` y `save`.
  `ListCrudRepository` trae `deleteById`, y `docs/DESIGN.md`, sección 6, dice que ningún
  repositorio borra.
- Una sola interfaz genérica, `CrudCatalogo<T>`, en vez de una `CrudEntidad` por catálogo, y
  `ServicioCatalogo` como la base común de los servicios. Spring entrega el servicio de etnias a
  quien pide `CrudCatalogo<Etnia>`.
- El servicio valida con `Validator` antes de guardar. Validado solo al escribir en la base, el
  error llega envuelto en una excepción de la transacción, y el menú no puede mostrar el motivo.
- Cambiar el nombre de un valor no le cambia el estado ni la versión. Sin esa regla, el nombre nuevo
  de un código inactivo lo reactivaba, como mostró
  `CatalogosTest.cambiarElNombreNoReactivaNiBorraLaVersion` antes del arreglo.
- `spring.main.web-application-type=none`, para que no arranquen Tomcat en el puerto 8080 ni Spring
  Security. `@Profile("!test")` en el menú y `@ActiveProfiles("test")` en los tests, para que un
  test no se quede esperando el teclado.
- La base de prueba se copia con `cp` y se abre pasando su URL al arrancar, sin tocar
  `application.properties` (`docs/DESIGN.md`, sección 8). Las entradas 2026-10-25 "Las pruebas
  corren sobre otra base" y "La base de prueba arranca como copia del archivo" lo dejaron para el
  primer PR de código.

**Alternativas:**

- La llave `Integer` con `@Column(length = 99)`, como propuso el autor. Pierde el cero de "01" y no
  limita el largo.
- `ListCrudRepository`, como propuso el autor. Trae `deleteById`, `delete`, `deleteAll` y
  `deleteAllById`.
- Validar solo al escribir en la base. El menú recibe la excepción de la transacción sin el motivo.

**Estado:** `vigente`

## 2026-10-26: Un escenario se prueba con un test que lleva su número

**Contexto:** El autor preguntó el 2026-10-26 qué le falta a HU-036, y si los escenarios podían ser
interfaces o pruebas:

- "que nos falta para que la historia de usario mia propuesta este hecha? el HU tiene, que sucuede
  con los ecenario?, por que esa podrian ser tambien interfaces especificas que implementar como
  reglas de negocio que habia dicho, pero ya veo por donde hiciste el cambio... crees que con todo
  esto el repositoro ya es auto suficiente, que mis companeros plantean sus ideas, o siguen mis
  lineamentros establecidos? no crees que los ecenarios tambien podrian ser pruebas en si mismo que
  el modelo deberia correr, como intentar meter valores donde no son?"

`docs/DESIGN.md`, sección 3, ya pedía una corrida por escenario, y su sección 9 dejaba sin escribir
cómo se nombra el test. Una interfaz fija nombres y tipos; no puede exigir que un código tenga 2
dígitos, y Java compila una implementación que lo ignore. El autor eligió:

- "Uno por escenario (Recomendado)"

**Decisión:** Cada historia tiene su clase de test y cada escenario sus métodos,
`esc<N>_<alternativa>`, con un `@DisplayName` como "HU-036 Esc. 10". Un escenario se cumple cuando
cada caso de su "Dado" tiene su método y pasa. Las reglas de `docs/DESIGN.md` que no son el
escenario de una historia van en `BaseComunTest`, que reemplaza a `CatalogosTest`.

**Alternativas:**

- Una interfaz por escenario, como sugirió el autor. Serían 23 en HU-036, casi todas iguales en los
  8 catálogos, y ninguna obliga a cumplir la regla.
- Una clase abstracta con los escenarios de RC-8 a RC-11, que corre para los 8 catálogos. Ahorra
  código, pero el número del escenario no queda en el nombre del test.

**Estado:** `vigente`

## 2026-10-26: Un CI corre los tests en cada PR

**Contexto:** Hasta el 2026-10-26, los tests los corría el modelo que escribía el PR, y nadie los
volvía a correr en GitHub. Dos integrantes ya tienen rol Write en el fork, Atervolgo y
jorge-a-restrepo. El autor eligió:

- "Sí, con GitHub Actions (Recomendado)"

**Decisión:** `.github/workflows/tests.yml` corre `./mvnw -B test` con Java 21 en cada PR y en cada
cambio de `main`. El PR muestra el check `tests` en verde o en rojo.

**Alternativas:** Que cada modelo corra los tests y pegue el resultado en el PR. Nadie comprueba
que esa corrida existió.

**Estado:** `vigente`

## 2026-10-26: main queda abierta a los integrantes

**Contexto:** `main` no tiene protección: un integrante con rol Write puede mezclar su propio PR o
empujar a `main` sin revisión. El modelo propuso exigir un PR con la aprobación del autor. El autor
contestó:

- "realmente el fork es para que trabajen libremente, ya lo habiamos discutiod, no quiero limitar lo
  que puedan hacer, es mas permito que hagan cambios, de hecho quiero que hagan cambios, de igual
  manera todo quedara cosignado en la documentacion."

**Decisión:** `main` sigue sin protección. Cada integrante trabaja con libertad, y lo que cambie
queda escrito en los documentos y en su PR, como pide `CLAUDE.md`. El CI de la entrada 2026-10-26
"Un CI corre los tests en cada PR" informa el resultado y no bloquea la mezcla.

**Alternativas:** Una regla de rama en GitHub con PR obligatorio y una aprobación. El autor la
descartó, porque el fork es para que los integrantes trabajen libremente.

**Estado:** `vigente`

## 2026-10-26: La terminal dice qué archivo espera antes de pedir la ruta

**Contexto:** La entrada 2026-10-25 "Un catálogo se carga desde un CSV, con la ruta en la terminal"
dejó la carga en CSV. El modelo preguntó qué pieza de HU-036 sigue, la tabla de cambios o la carga,
y el autor contestó:

- "lo habia olvidado, realmente amabas, por que si termino encontrando el documento de comunidad
  etnica que imagcino que sera mas de 100 valores como sueguiere le otro modelo, debo ser capaz de
  subir o parsear el docmuento y establecer un reglamento o que la terminal me diga que tipo de
  archivo espera, quizas en json o algo asi, an que bueno jsno no es csv, mi idea es que pueda
  editar todo en una tabla de hoja de calculo y luego submir el archivo y sea capaz ed procesarlo y
  ver si se puede surbir completo o genera errores..."

**Decisión:** Siguen las dos piezas de HU-036: la tabla de cambios y la carga desde CSV. Antes de
pedir la ruta, la terminal dice qué archivo espera: CSV en UTF-8, separado por punto y coma, con
las columnas de su historia, guardado desde la hoja de cálculo con el código como texto (RC-8).
Después de leerlo dice si se puede cargar completo, o qué filas fallan y por qué, como pide el
escenario 3 de HU-036.

**Alternativas:** JSON. El autor lo nombró y lo dejó en la misma frase: su idea es editar el
catálogo en una hoja de cálculo, que guarda CSV.

**Estado:** `vigente`
