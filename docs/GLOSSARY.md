# GLOSSARY.md: qué significa cada palabra hoy

> **Rol:** descriptivo. Dice qué significa un término hoy; por qué cambió vive en
> `docs/DECISIONS.md`. Cada línea cita su fuente por fecha y título de la decisión, o la sección del
> CHANGELOG que trajo el término. Una palabra que nombra algo que todavía no existe lo dice.
> **Régimen:** se corrige. **Origen:** plantilla 1.2, sembrada el 2026-10-22 (`docs/DECISIONS.md`,
> 2026-10-22 "Se adopta la plantilla 1.2").

Una línea por término, en orden alfabético dentro de cada grupo. Los grupos son secciones
numeradas, como en todo documento descriptivo; el primero es "Método" y los demás se abren cuando
el producto trae sus propias palabras.

## 1. Método

Fuente de las líneas de este grupo que no citan otra: 2026-10-22 "Se adopta la plantilla 1.2".

- **borrador**: el estado en que se abre un PR. El autor lo marca listo y lo mergea.
- **cita textual**: palabras del autor copiadas tal cual, en una viñeta propia que abre con
  comillas o con su etiqueta, como `**A13.**`. No se reescribe ni se resume, y no cuenta para las
  reglas 1 y 2 de prosa.
- **contexto temporal**: lo que se observó y se perdería si nadie lo escribe, antes de saber si se
  quiere. Vive en `docs/TEMPORARY-CONTEXT.md`, y cada línea se coloca o se descarta.
- **decisión**: una entrada de `docs/DECISIONS.md` que dice por qué algo es como es. No se edita
  nunca.
- **desfase**: la distancia entre la última versión del CHANGELOG y la que muestra el programa. La
  abre un PR doc-only y la cierra el próximo PR de código.
- **descriptivo**: el documento que dice qué es. Pierde contra el código.
- **Entró**: la línea con que nace un ítem del Backlog, con fecha y PR sacados de `git log -S`.
- **fase 0**: las 12 historias del plan B, en tres tramos y 5 pasos. Se cierra cuando cada
  escenario de las 12 tiene una corrida que lo muestra. Hasta el 2026-10-24 era el nombre de la
  siembra del repositorio (2026-10-24 "La fase 0 son las 12 historias").
- **fork**: `jncldrn64/IPS-para-con-interoperabilidad-en-Resoluci-n-866`, la copia del repositorio
  original en la cuenta del autor. Ahí se trabaja y se abren los PR hasta que el original, en
  Atervolgo, lo habilite (2026-10-24 "Se trabaja en el fork del autor hasta que Atervolgo lo
  habilite").
- **hueco conocido**: una entrada fechada de la sección "Huecos conocidos" de
  `docs/ARCHITECTURE.md`, con la corrida que la muestra. Se borra cuando se cierra.
- **Hipótesis**: el marcador de una inferencia hecha al reconstruir contexto, con su base a la
  vista. Su contraparte es **Sin origen recuperable**.
- **línea base**: un conteo de prosa medido en una fecha con un comando escrito. No puede subir.
- **memoria contextual**: `docs/context/MEMORIA-CONTEXTUAL.md`, la memoria de trabajo del modelo.
  Se lee entera antes de trabajar, se actualiza con cada decisión del autor y no manda sobre ningún
  documento canónico (2026-10-24 "La memoria contextual vuelve a ser la memoria del modelo").
- **modelo**: tres cosas distintas. El modelo de lenguaje que trabaja en el repositorio, como en
  "propuesta del modelo"; el modelo E-R de `docs/context/modelo-datos/`; y la parte del código con
  las entidades, los repositorios y los servicios (2026-10-24 "El código se separa en modelo, vista
  y controlador").
- **normativo**: el documento que dice qué tiene que ser. Si el código lo contradice, el código
  está en falta.
- **PR doc-only**: un PR que no toca código ni tests. Abre su propia sección del CHANGELOG, con la
  versión siguiente, y no toca la versión que muestra el programa (2026-10-25 "La sección de un PR
  doc-only lleva la versión siguiente").
- **terna plana**: tres oraciones seguidas cuyos largos caen dentro de 3 palabras. La unidad de la
  regla 8 de prosa.
- **tipo**: la palabra que abre un commit o el título de un PR: `add`, `chg`, `fix`, `rmv` o `doc`.

## 2. Producto

Todas las líneas salen de la primera sección del CHANGELOG, la v0.1 del 2026-10-22, salvo las
que citan su fuente.

- **anexo técnico**: el anexo de la Resolución 866 de 2021, con 49 datos en cuatro grupos. Este
  backlog cubre los numerales 1 a 23 (D-16, pregunta abierta al docente).
- **anular**: marcar como no válido un registro hecho por error, sin borrarlo. Aplica a la
  oposición, la voluntad anticipada y la atención; no a un valor de catálogo, que se inactiva.
- **atención**: un contacto del paciente con el servicio de salud, numerales 16 a 23. La historia
  clínica es el conjunto de atenciones de un paciente y no es una tabla.
- **campo**: un dato que vive dentro del paciente o de la atención. No da historias propias: sus
  límites son escenarios de la historia de su entidad.
- **catálogo**: la lista oficial de valores de un dato con más de 5 opciones, con su tabla y su CRUD
  ([2.3]; 2026-10-22 "El umbral de catálogo es más de 5 valores").
- **componente**: una parte de un numeral con su propio tipo, formato y longitud. El triage tiene
  dos.
- **CRUD de catálogo**: carga (crear: el archivo oficial o un valor por cambio oficial), consulta
  (leer: consulta, búsqueda, comprobación e historial), actualización (cambio oficial de código o de
  nombre) e inactivación y reactivación, que es el Delete. Son las cuatro palabras del título de
  cada historia de catálogo; la línea "Operaciones por escenario" de su tarjeta dice qué escenario
  es cada una, y la versión nueva hace las cuatro (2026-10-24 "Los títulos de los catálogos nombran
  sus cuatro operaciones").
- **D-n**: una decisión de la hoja Decisiones del tablero del equipo. La copia del 2026-10-24 está
  en `docs/REQUIREMENTS.md`, sección 6. La mayoría son propuestas sin confirmar; no son entradas de
  `docs/DECISIONS.md`.
- **escenario**: un criterio de aceptación en forma Dado, Cuando, Entonces, numerado dentro de su
  historia.
- **estimación**: la complejidad de una historia, de 1 a 10, donde 10 es la más difícil. Sube con
  las dependencias directas y no depende de la prioridad. La vota el equipo en clase [2.5]; la del
  modelo es una propuesta (2026-10-24 "La complejidad se estima de 1 a 10").
- **historia de usuario** o **HU**: un pedido con formato Como, Quiero, Para y sus escenarios. El
  backlog tiene 30, con ID de HU-001 a HU-043; los números que faltan son tarjetas archivadas.
- **inactivar**: dejar de ofrecer un valor de catálogo en registros nuevos sin borrarlo; los
  registros que lo usan lo conservan (RC-4, D-20).
- **lámina**: la imagen del mockup de una historia, en `docs/context/trello/mockups/`.
- **numeral**: uno de los 49 datos del anexo, con sus valores permitidos, formato y longitud. El
  tablero también lo llama elemento.
- **ocupación hija**: una ocupación de 4 dígitos, el nivel más específico de la CIUO-88 A.C. Es la
  única que se registra al paciente [2.1].
- **plan B**: el plan de desarrollo de las 12 historias del semestre: los 8 catálogos del
  paciente, HU-004, HU-001, HU-005 y HU-006 (2026-10-22 "Se desarrollan las 12 historias del plan
  B"). Desde el 2026-10-24 se reparte entre 3 integrantes (2026-10-24 "Tres integrantes con cuatro
  historias cada uno").
- **prioridad**: el valor de una historia para el cliente, en el campo Prioridad de su tarjeta. Las
  12 de la fase 0 dicen "Alta", así que no las ordena: las ordenan sus dependencias (2026-10-24
  "La complejidad se estima de 1 a 10").
- **RC-n**: una regla común del backlog, de RC-1 a RC-11, en
  `docs/context/trello/tarjetas/RC-reglas-comunes.md`.
- **referencia oficial**: el documento de la fuente que respalda crear, cambiar o inactivar un valor
  de catálogo. Sin ella, la IPS no puede tocar el catálogo (artículo 7).
- **tanda**: uno de los seis archivos en que se escribió el backlog v3, con la trazabilidad de cada
  escenario. Salieron del repositorio el 2026-10-24, y las historias se leen en sus tarjetas
  (2026-10-24 "docs/context guarda solo lo que usan las historias").
- **tarjeta**: el texto de una historia, en `docs/context/trello/tarjetas/`, el mismo que se pega
  en Trello. Desde el 2026-10-24 es la fuente de la historia (2026-10-24 "docs/context guarda solo
  lo que usan las historias").
- **versión de un catálogo**: un archivo oficial cargado, con su fuente, su fecha de publicación y
  su fecha de descarga (RC-8).

## 3. Código

Ninguna de estas palabras nombra código que ya exista: el repositorio todavía no tiene código del
producto.

- **archivo de la base**: el archivo de H2 donde quedan los datos entre una corrida y otra. Va en
  git con los catálogos ya cargados, y como git no mezcla dos cambios suyos, se cambia por turnos
  (2026-10-24 "La base es H2 en un archivo que va en git"; 2026-10-24 "El archivo de la base trae
  los catálogos cargados"; 2026-10-25 "El archivo de la base se cambia por turnos").
- **base común de catálogos**: el código con las reglas que comparten los 8 catálogos de la fase
  0, RC-1 a RC-11 y la búsqueda de D-23, y la clase de la que heredan sus 8 entidades, con código,
  nombre, estado y versión. La arma el integrante 3 antes del paso 1 (2026-10-24 "Los catálogos
  comparten una base común"; 2026-10-24 "El integrante 3 arma la base común"; 2026-10-25 "La base
  común es una clase con código, nombre, estado y versión").
- **base de prueba**: la base con otro nombre donde se prueba, para que un dato de prueba no llegue
  al archivo de la base que va en git. Arranca como copia de ese archivo (2026-10-25 "Las pruebas
  corren sobre otra base"; 2026-10-25 "La base de prueba arranca como copia del archivo").
- **catálogo de ejemplo**: HU-036, etnias, con el primer CSV y la primera carga en el archivo de la
  base, que copian los otros 7 catálogos (2026-10-25 "HU-036 es el catálogo de ejemplo").
- **controlador**: en la fase 0, el menú en terminal que lee la opción y llama al servicio. No hay
  controladores web (2026-10-24 "El código se separa en modelo, vista y controlador").
- **repositorio**: dos cosas. En git, el repositorio del proyecto en GitHub. En el código, la
  interfaz con que una entidad se guarda y se busca; ninguna borra (`docs/DESIGN.md`, sección 6).
- **servicio**: el código de una historia con sus reglas de negocio. Usa el repositorio de su
  entidad, y no lee del teclado ni imprime (`docs/DESIGN.md`, sección 6).
- **turno**: el permiso de cambiar el archivo de la base. Lo tiene quien tenga abierto el PR que lo
  cambia (2026-10-25 "El turno del archivo de la base es del PR abierto").
- **vista**: en la fase 0, lo que el programa imprime en la terminal, según la lámina de la
  historia (2026-10-24 "El código se separa en modelo, vista y controlador").
