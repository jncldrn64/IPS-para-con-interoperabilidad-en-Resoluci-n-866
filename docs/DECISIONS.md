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

## 2026-10-26: Los motivos de RC-10 son un enum

**Contexto:** RC-10 fija 4 motivos de inactivación, y RC-9 agrega "Vuelve en la versión" para el
valor que una versión nueva trae de vuelta. `docs/DESIGN.md`, sección 7, dice que un catálogo no es
un enum, y la memoria contextual tenía sin respuesta si una lista cerrada de 5 valores o menos lo
es. El modelo preguntó por los motivos, y el autor eligió:

- "Enum de Java (Recomendado)"

**Decisión:** `Motivo` es un enum con 5 valores: los 4 de RC-10 y "Vuelve en la versión".
`Motivo.loPoneElSistema` marca los 2 que pone la carga de una versión, y el servicio no deja que una
persona los elija.

**Alternativas:** Una tabla en la base, como un catálogo más. Cambiar un motivo no pediría
compilar, pero RC-10 no prevé que la IPS los cambie, y serían una entidad, un repositorio y una
carga más.

**Estado:** `vigente`

## 2026-10-26: La tabla de cambios y RC-10 entran en la base común

**Contexto:** La entrada 2026-10-26 "La terminal dice qué archivo espera antes de pedir la ruta"
puso la tabla de cambios como pieza siguiente de HU-036. El modelo E-R ya la describía, con dos
tablas: `catalogo_version`, una fila por carga, y `catalogo_cambio`, una fila por creación, cambio
de nombre, inactivación o reactivación (`docs/context/modelo-datos/modelo-entidad-relacion.md`,
diagrama 2).

**Decisión:**

- `CatalogoVersion` y `CatalogoCambio` siguen esas dos tablas, y `RegistroCatalogos` las usa por
  los 8 catálogos.
- `guardar` sale de `CrudCatalogo`. En su lugar, `crear`, `cambiarNombre` y `cambiarCodigo` piden la
  referencia oficial (RC-3); `inactivar` pide un motivo de RC-10, y `reactivar`, otra referencia o
  por qué se reactiva.
- La versión vigente de un catálogo es la de fecha de publicación más reciente. Un código está en
  ella si su `versionId` es el de esa versión.
- `contarUsos` devuelve 0 hasta que exista el registro del paciente (HU-004).
- El menú pide confirmar antes de guardar, y cualquier respuesta distinta de "s" cancela, como pide
  el escenario 12 de HU-036.

Lo que el modelo agregó, sin respuesta del autor todavía:

- El tipo "reemplazado" del modelo E-R no entra: el motivo "Reemplazado por" ya lo dice, con el
  código nuevo en `codigoReemplazo`.
- `CatalogoCambio` suma `justificacion`, que el modelo E-R no tenía, para reactivar sin otra
  referencia.
- Para saber si un cambio oficial es posterior a la versión vigente se compara su hora con la hora
  de carga de esa versión, porque un cambio oficial no guarda su fecha de publicación.

**Alternativas:** Dejar `guardar` con la referencia como dato opcional. Crear un código que ya
existe se seguiría tomando como cambio de nombre, y el escenario 10 de HU-036 pide rechazarlo.

**Estado:** `vigente`

## 2026-10-26: Lo que transcribe una norma se copia citando su fuente

**Contexto:** `CLAUDE.md`, sección 10, pide la licencia de lo que se copia de terceros, y el
Backlog de `docs/ROADMAP.md` pedía revisarla antes de subir un catálogo o el archivo de la base que
lo trae. El modelo preguntó de dónde salen los 7 nombres de etnia de la hoja 15, y el autor
contestó:

- "realmente que licencia puede tener un documento legal? etnias deberia ser los 7 valores que ya
  estan transcriptos, y los podemos dejar directamente en la h2 de persitencia en el repositorio ya
  escrtias, para que los otros modelos que las ceseciten las puedas referenciar... el problema es la
  comunidadad, que supongo que dependiendo de donde las extraiga pueden tener licencia o no, asi que
  entiedo que por poder puedes poner los 7 valores en ambas entidades, manualmente via el crud y via
  es cvs, (existiendo o no ya en la db previamente los varoles), para saber que pasa y como manera
  el programa los edgcasne... y cuando encienter el otro documento entiendo que poder poder ahi
  podiramso discutir... y luego de las pruebas, unicament dejar poblado la entidad etnias y no la de
  la comunidad, ademas por que te preocupa el tema de la licenica de un documento legal? si es una
  normativa, lo logico es que literamlete esten asi expreseadas no?"

La Ley 23 de 1982, artículo 41, permite reproducir leyes, decretos y actos administrativos, con la
condición de seguir la edición oficial. El modelo lo tomó de un concepto del Ministerio de
Educación del 2017 que cita el artículo, y no leyó el texto de la ley.

**Decisión:** Un valor que viene de una norma, como una lista del anexo de la Resolución 866, se
copia sin licencia aparte, citando la norma y su hoja. La licencia se revisa para lo que publica
otra fuente, como una tabla de SISPRO o el catálogo de comunidades étnicas cuando aparezca.
`CLAUDE.md`, sección 10, y el Backlog lo dicen.

**Alternativas:** Pedir también la licencia de la norma. Una resolución no trae un archivo de
licencia, así que la regla dejaría fuera las 7 etnias del anexo.

**Estado:** `vigente`

## 2026-10-26: El archivo de la base trae las 7 etnias y ninguna comunidad

**Contexto:** La cita del autor está en la entrada anterior. Pidió que las 7 etnias queden en el
archivo de la base que va en git, para que los modelos de los otros integrantes las usen. También
pidió probar la carga con esos 7 valores en las dos entidades, por el menú y por el CSV, con la base
vacía y con los valores ya cargados.

**Decisión:** El PR de la carga desde el CSV prueba esos casos y sube `data/ips.mv.db` con las 7
etnias de la hoja 15. Las carga con la versión "Resolución 866 de 2021", publicada el 2021-06-25
en el Diario Oficial 51.716. La tabla de comunidades étnicas queda vacía hasta que aparezca su
catálogo oficial (D-19).

Los 7 nombres salen de la transcripción del anexo del 2026-10-18, que no está en el repositorio.
Si alguno no coincide con la hoja 15, manda la hoja.

**Alternativas:** Dejar también comunidades de prueba en el archivo. Un valor de prueba llegaría a
git, que es lo que evita la entrada 2026-10-25 "Las pruebas corren sobre otra base".

**Estado:** `vigente`

## 2026-10-26: La carga lee primero y guarda al confirmar

**Contexto:** Las entradas 2026-10-26 "La terminal dice qué archivo espera antes de pedir la ruta"
y "El archivo de la base trae las 7 etnias y ninguna comunidad" pidieron la carga desde el CSV, con
casos límite en las dos entidades. RC-8 y RC-9, en la tarjeta de reglas comunes, ya dicen qué
acepta la carga y qué hace una versión nueva o una recarga.

**Decisión:**

- La carga tiene dos pasos. `leerCarga` lee el archivo, valida cada fila y compara con el catálogo
  sin guardar nada; `confirmarCarga` guarda. Cancelar es no confirmar (escenario 12 de HU-036).
- La carga es primera si el catálogo está vacío. Con valores, es una versión nueva si su fecha de
  publicación es posterior a la vigente, una recarga si es la misma, y se rechaza si es anterior o
  futura.
- HU-036 cambia dos métodos de la base: `nombreOficial`, que guarda el nombre de la hoja 15, y
  `revisarPrimeraCarga`, que exige sus 7 códigos. Las 7 etnias viven en `EtniaServicio.HOJA_15`.
- El archivo de la base sube con las 7 etnias, cargadas por el menú. La fecha de descarga es el
  2026-10-18, la de la transcripción de donde salen los nombres.

Lo que el modelo agregó, sin respuesta del autor todavía:

- Un catálogo con valores y sin versión, porque se crearon a mano, recibe su primer archivo como
  versión nueva: así compara contra lo que ya está y no crea dos veces el mismo código.
- La carga quita la marca invisible que la hoja de cálculo pone al comienzo de un "CSV UTF-8".
  Rechaza el archivo que no está en UTF-8 y avisa si parece separado por comas.
- Una primera carga sin ninguna fila aceptada no se puede guardar.

**Alternativas:** Leer y guardar en un solo paso. El escenario 3 pide mostrar las filas
rechazadas y dejar elegir entre guardar las aceptadas o cancelar, y el escenario 6 pide mostrar
los cambios antes de guardar.

**Estado:** `vigente`

## 2026-10-26: El código se ordena por historia, con lo común aparte

**Contexto:** El autor fusionó el PR #15 del fork y escribió:

- "hazme preguntas, por que learmente no se como exponerte ideas para poder que cerremos el HU 36
  y le demos autonomia al propio repositorio, y no uses Mezclar, que suena que estoy haciendo algo
  malo, hice algo malo? no me queda claro si debia hacer o no el PR, y no se si separar aun mas los
  codigos, por que si recuerdas en mis 9 puntos originales, tenia la intencios de que liretalemnet
  tubierasmos una interface coomo contrado que hiciera uso de los ecenarios (aun que creo que lo
  dije de ottra forma) y este se implementaria en el controllador, se que pasamos muchos detalles
  por alto de mis 9 puntos originales, pero esa mi idea de legibilida, donde estaba cada modulo y
  el por que, por que para mi una interface debia ser un contrato, y que este en servicio,
  dificulta la lectura, es mas, hasta que punto podemos separar en src/HU36/interfaces(con los
  contratos)  src/HU36/services(implementacion de las interfgaces)  src/HU36/models(entidades)
  src/HU36/repository(solo para el que permite el acceso al crud), y que hay de los otros 9
  puntos, como resolvirtes los detalles para costruir por otro lado?... y el tema del catalogo
  general y el cvs como son generales, imagino que pudene estar en la estrucura basica,
  src/interfaces y sus otras respectivas...."

El modelo le mostró cómo quedó cada uno de sus 9 puntos y le hizo cuatro preguntas. En la de las
carpetas, el autor eligió:

- "Por historia, como propones (Recomendado)"

**Decisión:** El código de una historia vive en su carpeta, como `hu036/`, con `interfaces/`,
`modelos/`, `repositorios/` y `servicios/`. Lo que usan varias historias, como `Catalogo`, la tabla
de cambios y la carga desde el CSV, vive en `comun/`, con las mismas subcarpetas y el menú en
`comun/menu/`. Las carpetas cuelgan de `src/main/java/com/example/demo/`, porque Maven compila lo
que está bajo `src/main/java/` y Spring busca las clases bajo el paquete de `DemoApplication`. Los
tests siguen el mismo orden bajo `src/test/java/com/example/demo/`.

En la cuarta pregunta el autor eligió el orden: primero las carpetas, después los escenarios 13 a
18 de HU-036.

**Alternativas:**

- Por capa, con las interfaces en su propia carpeta. Cada carpeta junta las 12 historias, y para
  leer una historia hay que abrir cuatro carpetas.
- Dejarla como estaba, con `CrudCatalogo` dentro de `servicio/`. Al autor le dificultaba la
  lectura.

**Estado:** `vigente`

## 2026-10-26: Cada historia tiene su contrato, y lo implementa su servicio

**Contexto:** La cita del autor de la entrada anterior pedía una interfaz como contrato que usara
los escenarios. En la segunda pregunta, sobre quién implementa ese contrato, el autor escribió:

- "haber no te digo que no exista puntos comunes, pero para facilitar la lectura, lo hyper
  especifico de la HU es su capreta, catalogo crud realmente puede y es general verdad? puede ser
  reutilizado por otros HU; por otro lado el controlador solo es una fany word por decir algo donde
  estaran los metodos reales de la HU y sus validacorios y toda la logica interna que pueda o no
  traer, ya sea de sus propios contratos interfaces o no,, al final tecnicamente servicio que sera
  expuesto por su interface de contrato sera lo que deba instanciar donde quiera llamar sus
  acciones no?"

En el repo, el controlador es el menú, que lee la opción y llama (`docs/DESIGN.md`, sección 6). Lo
que el autor describe, los métodos de la historia con sus validaciones, es el servicio.

**Decisión:** Cada historia tiene un contrato en `hu0NN/interfaces/`, que extiende `CrudCatalogo`
y agrega lo propio de la historia: `ContratoEtnias` trae la lista de la hoja 15. Su comentario dice
qué escenario cumple cada operación. El servicio implementa el contrato, y el menú lo pide por la
interfaz. `CrudCatalogo`, el contrato común, queda en `comun/interfaces/`.

**Alternativas:**

- Que el menú implemente el contrato. Las reglas quedarían en el menú, y la web o Postman tendría
  que copiarlas (`docs/DESIGN.md`, sección 6).
- Solo el contrato común. La historia no tendría dónde poner lo suyo sin tocar lo de las demás.

**Estado:** `vigente`

## 2026-10-26: El programa trabaja sobre una copia del archivo de la base

**Contexto:** Cada corrida cambiaba los bytes de `data/ips.mv.db`, aunque no guardara nada, y git
lo veía modificado: era un hueco de `docs/ARCHITECTURE.md`, sección 6. En la tercera pregunta, el
autor eligió:

- "Trabajar sobre una copia (Recomendado)"

**Decisión:** `application.properties` abre la base de prueba, `data/prueba.mv.db`, y
`DemoApplication.main` la copia de `data/ips.mv.db` si no existe. Quien tiene el turno abre
`data/ips` pasando su URL al arrancar. Para traer los catálogos nuevos de `main` a la base de
prueba, se borra y la corrida siguiente la copia otra vez.

**Alternativas:** Seguir abriendo `data/ips.mv.db` y deshacer el cambio con
`git checkout data/ips.mv.db` antes de cada commit. Dependía de que cada persona y cada modelo lo
recordara.

**Estado:** `vigente`

## 2026-10-26: El merge de un PR se dice fusionar

**Contexto:** El modelo llamaba "mezclar" al merge de un PR. En la cita de la entrada 2026-10-26
"El código se ordena por historia, con lo común aparte", el autor pidió no usar esa palabra, porque
le sonaba a que hacía algo malo.

**Decisión:** El modelo dice "fusionar", en el chat y en los documentos vigentes. `CLAUDE.md`,
`docs/GLOSSARY.md`, `docs/DESIGN.md` y `tests/README.md` cambian "mezclar" por "fusionar"; las
entradas de este archivo y las secciones del CHANGELOG ya escritas son historia y no se editan.

**Alternativas:** Dejar "mezclar". Al autor le sonaba a una falta.

**Estado:** `vigente`

## 2026-10-26: La consulta, la búsqueda y la comparación entran en la base común

**Contexto:** Los escenarios 13 a 18 de HU-036 no tenían test (`tests/README.md`, sección 3): la
consulta, la búsqueda por código y por nombre, la búsqueda sin resultados, el catálogo vacío y la
comprobación contra la hoja 15. HU-037 tiene los mismos en sus escenarios 12 a 17. Cancelar antes
de confirmar, el 12 de HU-036, se probaba en el servicio y no en el menú. Al ordenar el trabajo, el
autor eligió:

- "Primero las carpetas, después 13 a 18 (Recomendado)"

**Decisión:**

- `CrudCatalogo` agrega `consultar`, `historialDeVersiones` y `buscar`, y la base los implementa
  para los 8 catálogos. La búsqueda sigue a D-23: el código exacto, o el nombre que contiene lo
  buscado, sin mayúsculas ni tildes.
- La comparación con una lista oficial es de la base, y cada contrato dice contra qué lista:
  `ContratoEtnias.comprobarContraLaHoja` usa la hoja 15, y
  `ContratoComunidadesEtnicas.comprobarContraElArchivo`, el archivo que se sube, como pide el
  escenario 17 de HU-037.
- Cada escenario tiene su test, también los que pasan por el menú, como cancelar.

Lo que el modelo agregó, sin respuesta del autor todavía:

- El filtro de la consulta es un enum, `CrudCatalogo.Filtro`, con activos, inactivos y todos.
- Cada contrato da el nombre de su catálogo en plural, `nombreEnPlural`, y la base arma con él
  los mensajes de los escenarios 16 y 17.
- "Los que sobran", en el escenario 18 de HU-036, son los activos que no están en la hoja. Es lo
  que dice el escenario 17 de HU-037: un inactivo que no está en la lista ya coincide con ella.
- En el historial de versiones, "cambió" cuenta los cambios de nombre y las reactivaciones de esa
  versión.
- Si lo buscado es un código que existe, la búsqueda devuelve solo ese, y no busca en los nombres.
- El menú recibe la entrada por su constructor, y el test le pasa las respuestas escritas.

**Alternativas:** Que cada historia escriba su consulta y su búsqueda. Serían 8 copias de lo mismo,
y la entrada 2026-10-24 "Los catálogos comparten una base común" pidió lo contrario.

**Estado:** `vigente`

## 2026-10-26: Cada historia tiene su controlador, y el menú común lo abre

**Contexto:** Con el PR #17 fusionado, el autor preguntó qué les faltaba a HU-036 y a HU-037, y qué
parte del código común podía pasar a cada historia:

- "Primero confirmemos que aun tengo uddas. vale, estabamos trabajando en 2 HU, no en 1, penese
  que el tema de las etnias como terminara siendo un campo de usuario usuario podria tener una u
  otra... que falta para que ambas historias de usuario  esten listas? y que parte de el codigo de
  estas que esten en comun se puede mover a sus HU respectivas? por que entiendo la logica que que
  lo comun puede ser un punto de partida, que ahora que veo cada service de cada HU tiene tan poco
  declarado, que me parecio raro, pero tu sabras, supongo que en sierto sentido tambien ayuda que
  cualquier cambio con los ecenarios de las proximas HU contemplens nuevos edgcase para esto....
  si al final que lo hayamos hecho en terminal sera hasta peor,, pero bueno si lo hubiese hecho
  yo, hubiese sido muy directo con lo que pueda o no plantear, como mis 9 puntos originals, que
  eran simples en si mismo, unicamente para exponer el repositorio, y loo hubiese hecho de forma
  individual"

El modelo contestó que las tarjetas de los 8 catálogos comparten 21 o 22 títulos de escenario con
HU-037, y que lo único de `comun/` que se podía partir por historia sin copiar reglas era el menú.
Le hizo tres preguntas, y el autor eligió:

- "Dejarlo como está (Recomendado)"
- "Ahora, uno por historia"
- "Los 4 casos de HU-037"

Mientras el modelo partía el menú, el autor escribió:

- "de hecho ahora que lo pienso podemos recuperar el concepto de controller, que el menu comun
  llame a menu especifico de cada implementacion de la HU,, en este caso cada menu de cada HU seria
  nuestro controller, literalmente de ahi seria trivial cambiarlo a un controller real, por que
  sabriamos como es el uso de cada implementacion."

**Decisión:**

- La base común se queda en `comun/`, y sigue en vigor la entrada 2026-10-24 "Los catálogos
  comparten una base común".
- Cada historia tiene su controlador en `hu0NN/controladores/`, como `EtniaControlador`: pide en
  la terminal los datos de cada opción, llama al contrato e imprime. Las 11 opciones que comparten
  los catálogos están en `comun/controladores/ControladorCatalogo`, y el controlador de cada
  historia pone su número, su título, cómo se arma un valor y contra qué se compara.
- El menú común, `MenuPrincipal`, lista las historias por su número y abre el controlador de la
  elegida.
- HU-037 recibe los 4 casos del "Dado" que le faltaban: la fecha futura del escenario 3, y cancelar
  una versión nueva, un cambio y una reactivación en el 11.

Lo que el modelo agregó, sin respuesta del autor todavía:

- La interfaz `Controlador`, en `comun/interfaces/`, es lo que el menú pide de cada historia: su
  número, su título y cómo se abre. Una historia que no es un catálogo, como HU-001 o HU-004, entra
  al menú con su controlador sin cambiar `MenuPrincipal`.
- Spring le entrega al menú todos los controladores, y el menú los ordena por el número de la
  historia. Un controlador nuevo aparece en el menú sin que nadie lo edite.
- `Terminal`, en `comun/menu/`, lee la entrada que comparten el menú y los controladores, y pide la
  confirmación de cada operación.
- El test de un escenario abre el controlador de su historia, y no el menú principal. Que el menú
  ordene las historias y abra la elegida lo prueba `BaseComunTest`.

**Alternativas:**

- Un controlador por historia con sus 11 opciones copiadas. `wc -l` da 316 líneas para
  `ControladorCatalogo.java`, y estarían en 8 lugares.
- Seguir con un solo menú y su `switch` hasta decidirlo con el equipo. Los 3 integrantes cambiaban
  el mismo archivo (`docs/DESIGN.md`, sección 9, antes de esta entrada).

**Estado:** `vigente`

## 2026-10-27: Por la terminal sale solo el menú, y el menú dice qué base abrió

**Contexto:** El autor le preguntó a otra sesión de Claude, la que mantiene su plantilla y sus
scripts, si la terminal de este repo pedía el mismo manejo:

- "antes de ello, estoy "implementado una "pseudo terminal" en mi proyecto ips, deberia darle el
  mismo manejo en ciertos aspectos? o java ya se encarga de por ejemplo del XDG o cosas asi?"

Esa sesión midió la salida sobre `main` en `383564b` y escribió seis propuestas, P1 a P6. El modelo
de este repo las valoró sobre `cba3cc5`, y el autor eligió con esa valoración.

Sin terminal, `printf '0\n' | java -jar` dejaba 44 líneas en stdout, 19 de ellas de log, y 0 en
stderr. P6 confirmó lo que no aplica: XDG, porque la base vive en `./data` (`docs/DESIGN.md`,
sección 8), y la ayuda y las opciones, porque el programa es un menú. En la pregunta del log, el
autor eligió:

- "A + una línea del menú (Recomendado)"

**Decisión:**

- `application.properties` pone el log en `warn`, apaga el cartel de arranque y apaga el aviso de
  las plantillas de Thymeleaf, que la fase 0 no tiene.
- `MenuPrincipal` abre con `Base abierta: <URL>`, la URL de la conexión a la base. Con el log en
  `warn` no sale la línea `Database JDBC URL`, y quien tiene el turno del archivo de la base
  necesita saber si abrió `data/ips` o `data/prueba`.
- `docs/ARCHITECTURE.md`, sección 2, decía que la base se ve en `Database JDBC URL`, y se corrige.

Medido el 2026-10-27 con el jar, en el contenedor de la sesión: sin terminal, stdout trae 5 líneas,
la de la base y las del menú, y stderr nada; en una terminal de verdad, abierta con `script`, salen
0 secuencias de escape y 0 líneas de log. Con `--spring.datasource.url=jdbc:h2:file:./data/ips`, la
primera línea dice `Base abierta: jdbc:h2:file:./data/ips`.

**Alternativas:**

- El log a stderr con un `logback-spring.xml` (P1-B), y el color apagado con
  `spring.output.ansi.enabled=never` (P2). En una terminal las 19 líneas seguían en pantalla,
  porque stderr también va a ella. Sin apagar el color, `2> archivo` desde una terminal llenó el
  archivo con 228 secuencias de escape.
- El log en `warn` más `logging.level.org.hibernate.orm.connections.pooling=info`, que según la
  medición del modelo de este repo devuelve la línea de la URL dentro de un bloque de 11 de log.
- Forzar UTF-8 en la salida desde el código (P3). Sigue el hueco "las tildes salen como "?" en una
  terminal sin UTF-8" de `docs/ARCHITECTURE.md`, sección 6: Windows no se probó, y forzarlo podría
  dejar peor una consola que no use UTF-8.

**Estado:** `vigente`

## 2026-10-27: La terminal recibe también la salida, y un test lee lo que dice

**Contexto:** `Terminal` recibía las respuestas del test, y nadie leía lo que el menú imprimía. En
`src/main` había 59 `System.out`, 53 en `ControladorCatalogo`, 4 en `MenuPrincipal` y 2 en
`Terminal`. Lo que un escenario pide decir, como el aviso del catálogo vacío, se probaba en el
servicio y no en la terminal.

La propuesta, P4, citaba también una regla de la plantilla 1.4 del autor, la de que un test de algo
que puede quedarse esperando corre con un tiempo máximo. Este repo no adoptó esa plantilla, y la
regla entra por esta entrada. El autor eligió:

- "Ahora, sin tocar pantallas (Recomendado)"

**Decisión:**

- `Terminal` recibe un `PrintStream` junto con la entrada. Spring le da `System.out`, y un test, un
  `ByteArrayOutputStream`. El menú y los controladores imprimen con `Terminal.escribir`.
- Ninguna pantalla cambia: las líneas que imprime el menú son las de antes.
- Los tests nuevos comprueban por el menú lo que el escenario pide decir: el 16, el 17 y el 19 de
  HU-036, y el 15, el 16 y el 18 de HU-037. La pantalla entera la deciden los tres integrantes.
- Cada test del menú lleva `@Timeout(10)`.

Lo que el modelo agregó, sin respuesta del autor todavía:

- `@Timeout`, en el modo que deja la transacción del test, no corta un bucle: el 2026-10-27, un
  bucle de 8 segundos con un tope de 3 falló recién a los 8. Por eso `Terminal.leer`, acabada la
  entrada, contesta una línea vacía hasta 100 veces y a la siguiente lanza
  `NoSuchElementException`. Los menús atrapan `IllegalStateException` y siguen, y por eso el corte
  es otra excepción.
- Con un menú que no salía con la línea vacía y un test con las respuestas cortas, el test llenó
  2 GB de memoria sin el corte, y con el corte falló a los 2 segundos.

**Alternativas:**

- El `PrintStream` en `MenuPrincipal`, como decía la propuesta. Desde la entrada 2026-10-26 "Cada
  historia tiene su controlador, y el menú común lo abre", el menú principal solo elige la historia,
  y 53 de las 59 líneas estaban en `ControladorCatalogo`.
- `@Timeout` con `threadMode = SEPARATE_THREAD`, que cortó el bucle a los 3 segundos. Corre el test
  en otro hilo, y ahí `TransactionSynchronizationManager.isActualTransactionActive()` dio `false`:
  lo que el test guarda quedaría en la base de los demás tests.
- Esperar a que el equipo decida las pantallas.

**Estado:** `vigente`

## 2026-10-27: La lista de rechazadas no reemplaza un archivo sin preguntar

**Contexto:** El menú escribe `<nombre>-rechazadas.csv` junto al archivo de la carga, con
`ServicioCatalogo.escribirRechazadas`, que llama a `Files.write` sin opciones. Un programa aparte
con la misma llamada reemplazó sin aviso un archivo que ya existía. Pisar la lista de una carga
anterior del mismo archivo no pierde nada, y un archivo propio con ese nombre sí. El autor pidió un
solo PR, en la opción que decía que traía P1, P5 y P4:

- "Uno solo (Recomendado)"

**Decisión:** si el archivo existe, `ControladorCatalogo` pregunta "Ya existe <ruta>.
¿Reemplazarlo? (s/n)" antes de escribirlo. Con otra respuesta que "s", dice que no escribió la
lista, y el archivo queda como estaba. La pregunta va en el controlador, porque un servicio no lee
del teclado (`docs/DESIGN.md`, sección 6).

**Alternativas:**

- Avisar "Reemplaza el archivo que ya había" y escribir igual. El aviso llega con el archivo ya
  perdido.
- `StandardOpenOption.CREATE_NEW`, y pedir otro nombre si existe. Pide escribir una ruta nueva cada
  vez que se repite una carga, donde la pregunta pide una "s".

**Estado:** `vigente`

## 2026-10-27: HU-036 y HU-037 están completas

**Contexto:** Con el PR #19 fusionado, el autor escribió:

- "el otro modelo ya aplico los cambios que vio convenientes, valora y dime que tal, y dame
  preguntas para saber entncose que falta para tenel las dos hu complentas que estamos haciendo y
  que faltaria para que el repo sea autosuficiente"

A la pregunta de cuándo se dan por completas, eligió:

- "Ya, con HU-004 anotado (Recomendado)"

Y sobre el archivo oficial de comunidades étnicas, que nadie ha encontrado:

- "Buscare yo, con otro modelo en otra base de datso asi que dejala asi hasta nueva orden"

**Decisión:**

- HU-036 y HU-037 están completas: cada caso del "Dado" de sus escenarios tiene su test
  (`docs/DESIGN.md`, sección 3).
- Lo que espera el registro del paciente, cuántos pacientes usan un valor y lo que RC-11 pide de un
  registro ya guardado, es un hueco de `docs/ARCHITECTURE.md`, sección 6, y se prueba con HU-004.
- El archivo de comunidades lo busca el autor. Hasta que él diga otra cosa, HU-037 se prueba con
  valores inventados y el archivo de la base no trae comunidades.

**Alternativas:** Dejarlas abiertas hasta que HU-004 cuente pacientes, o hasta que el docente las
revise.

**Estado:** `vigente`

## 2026-10-27: El menú dice pacientes, como las tarjetas

**Contexto:** Al inactivar, el menú decía "La usan N registros", y el escenario 19 de HU-036 y el
18 de HU-037 dicen "cuántos pacientes la usan". Lo anotó en `docs/TEMPORARY-CONTEXT.md` el modelo
de la entrada 2026-10-27 "La terminal recibe también la salida, y un test lee lo que dice". El
autor eligió:

- "Pacientes, como las tarjetas (Recomendado)"

**Decisión:** el menú dice "La usan N pacientes: la conservan, pero no se ofrece en registros
nuevos." Hasta que exista HU-004, N es 0.

**Alternativas:** "Registros" hasta HU-004, y cambiarlo cuando el paciente exista.

**Estado:** `vigente`

## 2026-10-27: Los nombres del código van en español

**Contexto:** `docs/DESIGN.md`, sección 9, tenía pendiente el idioma de los identificadores. El
primer código usa español, salvo lo que pide Spring. El autor eligió:

- "Español, como ahora (Recomendado)"

**Decisión:** paquetes, clases, métodos y variables van en español. Quedan en inglés los nombres
que Spring Data lee para armar la consulta, como `findById` y `save`, y los métodos de Java o de
Spring que se sobrescriben, como `run`.

**Alternativas:** Inglés, renombrando las clases y los métodos de los 39 archivos de `src`.

**Estado:** `vigente`

## 2026-10-27: El paquete es com.ipssaludyvida y el artefacto ips

**Contexto:** El paquete era `com.example.demo` y el artefacto `demo`, los que pone Spring
Initializr. Sin PR abiertos y sin que los integrantes 1 y 2 hayan empezado, el autor eligió:

- "Cambiarlo ya (Recomendado)"

No escribió otro nombre, así que vale el que propuso el modelo en la pregunta.

**Decisión:** el paquete raíz es `com.ipssaludyvida`, el `groupId` de `pom.xml` también, el
`artifactId` es `ips` y `spring.application.name` es `ips`.

Lo que el modelo agregó, sin respuesta del autor todavía:

- `DemoApplication` pasa a `IpsApplication` y `DemoApplicationTests` a `IpsApplicationTests`, el
  nombre que Spring Initializr da a un artefacto `ips`. El jar es `target/ips-0.0.1-SNAPSHOT.jar`.

**Alternativas:** Dejar `com.example.demo`. Cambiarlo después toca cada archivo y choca con cada
PR abierto de los integrantes.

**Estado:** `vigente`

## 2026-10-27: La tabla de cambios guarda la hora de Colombia

**Contexto:** La fecha y la hora de cada cambio, y la de cada carga, salían de la zona horaria del
equipo que corre el programa. Las 7 etnias de `data/ips.mv.db` se cargaron en el contenedor de la
sesión, en UTC, a las 02:25 del 2026-10-27, cuando en Colombia eran las 21:25 del 2026-10-26
(`docs/TEMPORARY-CONTEXT.md`, nota del 2026-10-26). El autor eligió:

- "Hora de Colombia (Recomendado)"

**Decisión:**

- `RegistroCatalogos.ZONA` es `America/Bogota`. La hora de cada cambio, la de cada carga y la
  fecha con que una carga se rechaza por futura salen de esa zona, corra donde corra el programa.
- Las 8 horas de `data/ips.mv.db`, las 7 de los cambios y la de la carga, pasan a la de Colombia,
  5 horas menos. `RegistroCatalogos.creadoPorCambioPosterior` compara la hora de un cambio con la
  de la carga vigente (RC-10): con la carga en UTC y los cambios nuevos en hora de Colombia, un
  cambio hecho después de la carga quedaría antes.

Lo que el modelo agregó, sin respuesta del autor todavía:

- Corregir las 8 horas del archivo y no solo anotarlas. La pregunta decía "se anotan o se
  corrigen", y la regla de arriba no se cumple con las dos zonas mezcladas.
- La fecha futura de una carga también se mide con la fecha de Colombia.

Comprobado el 2026-10-27 en el contenedor de la sesión, que corre en UTC: con `LocalDateTime.now()`
en `RegistroCatalogos`, `BaseComunTest.laTablaDeCambiosYLaCargaGuardanLaHoraDeColombia` falló con
04:44 contra 23:44, y con la zona pasa.

**Alternativas:**

- La hora de cada equipo, como antes. Una carga en un equipo y un cambio en otro no se pueden
  comparar.
- UTC en todos lados. Las horas se comparan bien, y el historial muestra una hora que no es la de
  la IPS.

**Estado:** `vigente`

## 2026-10-27: El autor revisa lo que eligió el modelo

**Contexto:** Diez entradas tenían una lista de lo que el modelo agregó, sin respuesta del autor.
A la pregunta de cómo revisarlas, el autor eligió:

- "Preguntas por tandas (Recomendado)"

El modelo las agrupó en tres tandas, empezando por lo que afecta a los integrantes 1 y 2. El autor
eligió la opción recomendada en todas las preguntas, salvo en dos: la licencia de los catálogos y
los datos provisionales, que tienen su propia entrada del 2026-10-27, "Un catálogo del Estado se
copia citando su fuente" y "El archivo de la base admite valores provisionales".

**Decisión:** lo que el modelo agregó queda así, por entrada:

- 2026-10-24 "El archivo de la base trae los catálogos cargados": aceptado que el segundo PR que
  carga tome el archivo de `main` y vuelva a correr su carga. Las otras dos, cambiadas por las
  entradas de arriba.
- 2026-10-25 "Las pruebas corren sobre otra base": reemplazado por la entrada 2026-10-26 "El
  programa trabaja sobre una copia del archivo de la base".
- 2026-10-26 "El primer código arma la base común con etnias y comunidades étnicas": aceptados los
  repositorios sin borrar, el contrato genérico, la validación antes de guardar, que cambiar el
  nombre no cambie el estado ni la versión, el arranque sin la parte web y el perfil `test`. La
  copia con `cp`, reemplazada por `IpsApplication.copiarSiFalta`.
- 2026-10-26 "La tabla de cambios y RC-10 entran en la base común": aceptadas las tres.
- 2026-10-26 "La carga lee primero y guarda al confirmar": aceptadas las tres.
- 2026-10-26 "La consulta, la búsqueda y la comparación entran en la base común": aceptados el
  filtro como enum, `nombreEnPlural`, "los que sobran", qué cuenta "cambió" y la búsqueda por
  código exacto. El menú que recibía la entrada por su constructor, reemplazado por `Terminal`.
- 2026-10-26 "Cada historia tiene su controlador, y el menú común lo abre": aceptados los cuatro.
  HU-001 y HU-004 entran al menú con su propio controlador.
- 2026-10-27 "La terminal recibe también la salida, y un test lee lo que dice": aceptado el corte
  de `Terminal.leer`.
- 2026-10-27 "El paquete es com.ipssaludyvida y el artefacto ips": aceptado `IpsApplication`.
- 2026-10-27 "La tabla de cambios guarda la hora de Colombia": aceptadas las dos.

**Alternativas:** Dar todo por aceptado en una sola entrada, o que el autor las leyera solo.

**Estado:** `vigente`

## 2026-10-27: Un catálogo del Estado se copia citando su fuente

**Contexto:** La entrada 2026-10-24 "El archivo de la base trae los catálogos cargados" proponía
revisar la licencia de cada catálogo de terceros antes de subirlo: la DIVIPOLA del DANE (HU-026), la
CIUO-88 A.C. (HU-022) y las tablas de SISPRO (HU-018, HU-034 y HU-038). `CLAUDE.md`, sección 10,
pedía parar y avisar si una fuente no trae licencia. El autor eligió:

- "Citar la fuente basta"

**Decisión:** un catálogo que publica una entidad del Estado se copia citando la entidad y la
dirección de donde se bajó, sin buscar licencia aparte. La carga ya guarda la fuente y la dirección
de descarga en cada versión (RC-8). `CLAUDE.md`, sección 10, lo dice en el mismo PR. La regla de
LICENSE sigue para el código, las plantillas y las skills de terceros.

No verificado: el modelo no leyó los términos de publicación del DANE, del Ministerio de Salud ni
de SISPRO.

**Alternativas:** Revisar la licencia de cada fuente antes del primer commit que la traiga, y parar
si no se encuentra.

**Estado:** `vigente`

## 2026-10-27: El archivo de la base admite valores provisionales

**Contexto:** La entrada 2026-10-24 "El archivo de la base trae los catálogos cargados" proponía
que cada catálogo entrara al archivo de la base solo con la carga de su archivo oficial. Según
`docs/ROADMAP.md`, sección "Principio de orden", el docente no acepta "un dato provisional para
crear su catálogo en otro sprint". El autor eligió:

- "Permitir datos provisionales"

Al preguntarle si solo en la base de prueba, eligió:

- "También en el archivo de la base"

**Decisión:** un catálogo ya hecho puede entrar al archivo de la base con valores provisionales
mientras llega su archivo oficial, que después los reemplaza.

**Hipótesis:** la regla del docente es sobre usar un dato provisional para dejar el catálogo para
otro sprint. Aquí el catálogo está hecho y solo sus valores esperan al oficial. La base es la cita
de `docs/ROADMAP.md`; el docente no lo ha visto.

Falta decidir cómo entra un valor provisional sin trabar al oficial. Medido el 2026-10-27 con un
test que no quedó en el repo: después de una carga provisional fechada ese día, un archivo oficial
publicado en 2021 se rechaza como "anterior a la de la versión vigente"
(`ServicioCatalogo.tipoDeCarga`). Queda en `docs/DESIGN.md`, sección 9.

**Alternativas:**

- Solo en la base de prueba y en los tests, que no van a git.
- Ningún dato provisional, como proponía el modelo.

**Estado:** `vigente`

## 2026-10-27: El glosario define los cargos

**Contexto:** El autor escribió:

- "el tema de inicio por session aun me da ideas erradas, entre toda la documentacion, estos roles
  o supuesto roles dentro de la organizacion, todos estan correctamente definidos?"

El modelo revisó las 29 tarjetas, las láminas, `docs/REQUIREMENTS.md` y el modelo E-R. Los tres
cargos son los mismos en todos lados, y la matriz de permisos de la tarjeta de reglas comunes es
igual a la de HU-001. Lo que podía confundir: el glosario no definía "cargo" ni los tres cargos, el
docente dice "rol", ningún cargo crea usuarios, y la lista de `docs/REQUIREMENTS.md`, sección 1,
daba a cada cargo menos de lo que le da la matriz. A la pregunta de qué idea le daba el inicio de
sesión, el autor eligió:

- "Que hay un administrador,Rol y cargo son distintos,Quién ve qué,Cuándo entra HU-001"

Y a la de qué corregir:

- "Glosario de cargos (Recomendado),Tabla de REQUIREMENTS"

**Decisión:**

- `docs/GLOSSARY.md` define cargo, rol, los tres cargos, la matriz de permisos, el equipo técnico,
  el inicio de sesión y el usuario.
- `docs/REQUIREMENTS.md`, sección 1, dice todo lo que la matriz da a cada cargo, que los tres
  consultan los catálogos, que no hay un cargo de administrador y que el docente los llama roles.

**Alternativas:** El modelo también ofreció corregir el modelo E-R, que cita la guía [2.4] para un
supuesto del equipo, y juntar las preguntas al docente sobre D-02, D-04, D-06 y el escenario de rol
sin permisos. El autor no las eligió.

**Estado:** `vigente`

## 2026-10-27: Cada integrante trabaja con su modelo

**Contexto:** El repo estaba escrito para el autor y su modelo: `CLAUDE.md` decía "el autor" sin
definirlo, la memoria contextual era la del autor y se leía entera, y la costumbre de preguntar y de
anotar lo que el modelo agregaba vivía en esta historia y en la memoria. El autor escribió:

- "Ahora si, quiero pensar que cada integrate puede usar su modelo de forma autonoma y decirle como
  quiere las cosas, que partes le toca, y como quiere hacerla, (si es que quiere hacerla diferente
  de alguna forma, en la que se sienta mas agusto; pero por algo dejamos una base, para que aun asi
  pueda seguir algun tipo de estandar) o en caso contrario solo discutir y que el modelo programe
  segun los lineamientos, pero aun asi el usuario tiene que estar pendiente de cada paso del
  modelo, yo estubie ahi corrigiendo ideas, y es posible que los usuarios integrantes quiera aportar
  ideas (o no como te decia)... de ahi que el repositorio sea auto suficiente sin que este chat mio
  este presente, por que la documentacion ya le deja claro un camino al modelo."

El modelo le hizo cuatro preguntas, y el autor eligió:

- "Una por integrante (Recomendado)"
- "Su usuario; lo común, al PR (Recomendado)"
- "Decide y avisa en el PR"
- "El segundo se pone al día (Recomendado)"

**Decisión:**

- Cada integrante tiene su memoria en `docs/context/`. La de hoy queda como la del autor, y el
  modelo de otro integrante crea la suya en la primera sesión. Cada modelo lee entera solo la de su
  integrante (`CLAUDE.md`, sección 1).
- En sus historias decide su integrante. Lo común también lo cambia cualquiera, con su decisión en
  el mismo PR, y el commit y el título del PR lo marcan para que los otros dos lo vean
  (`CLAUDE.md`, sección 5).
- El modelo decide lo que necesita sin preguntar, y lo lista en su entrada de esta bitácora y en el
  cuerpo del PR. Su integrante lo revisa ahí antes de fusionar (`CLAUDE.md`, secciones 5 y 7).
- Si dos PR están abiertos a la vez, el que se fusiona después trae `main` a su rama y toma la
  siguiente versión libre del CHANGELOG (`CLAUDE.md`, sección 7).

Lo que decidió el modelo:

- La memoria de otro integrante se llama `MEMORIA-<su usuario de GitHub>.md`, porque el usuario de
  GitHub no se repite y no depende de quién sea el integrante 1 o el 2.
- Si el modelo no sabe quién es su integrante, le pregunta su nombre, su usuario de GitHub y qué
  número de integrante es.
- Lo común es `comun/`, `IpsApplication`, `pom.xml`, `src/main/resources/`, `.github/`,
  `CLAUDE.md`, `docs/REQUIREMENTS.md`, `docs/DESIGN.md` y `docs/ROADMAP.md`, y la marca es `común:`
  después del tipo. El archivo de la base queda fuera, porque tiene sus turnos (`docs/DESIGN.md`,
  sección 8).
- Fusionar el PR acepta lo que su sección "Lo que decidió el modelo" lista.
- `docs/GLOSSARY.md` define autor, integrante y común. La lista del PR de prueba pide también la
  memoria nueva de su integrante.

**Alternativas:**

- Una memoria compartida, con cada entrada firmada. Seguiría creciendo, y cada modelo la leería
  entera.
- Que lo común solo lo cambie el autor. El 2026-10-26 eligió dejar `main` abierta a los
  integrantes (`docs/DECISIONS.md`, 2026-10-26 "main queda abierta a los integrantes").
- Que el modelo pregunte antes de cada decisión, como hacía el modelo del autor.
- Un PR abierto a la vez. Los demás esperarían.

**Estado:** `vigente`
