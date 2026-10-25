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
- **hueco conocido**: una entrada fechada de la sección "Huecos conocidos" de
  `docs/ARCHITECTURE.md`, con la corrida que la muestra. Se borra cuando se cierra.
- **Hipótesis**: el marcador de una inferencia hecha al reconstruir contexto, con su base a la
  vista. Su contraparte es **Sin origen recuperable**.
- **línea base**: un conteo de prosa medido en una fecha con un comando escrito. No puede subir.
- **memoria contextual**: `docs/context/MEMORIA-CONTEXTUAL.md`, la memoria de trabajo del modelo.
  Se lee entera antes de trabajar, se actualiza con cada decisión del autor y no manda sobre ningún
  documento canónico (2026-10-24 "La memoria contextual vuelve a ser la memoria del modelo").
- **normativo**: el documento que dice qué tiene que ser. Si el código lo contradice, el código
  está en falta.
- **PR doc-only**: un PR que no toca código ni tests. Abre su propia sección del CHANGELOG y no sube
  la versión.
- **terna plana**: tres oraciones seguidas cuyos largos caen dentro de 3 palabras. La unidad de la
  regla 8 de prosa.
- **tipo**: la palabra que abre un commit o el título de un PR: `add`, `chg`, `fix`, `rmv` o `doc`.

## 2. Producto

Todas las líneas salen de la sección v0.1 del CHANGELOG, salvo las que citan su decisión.

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
- **D-n**: una decisión de la hoja Decisiones del tablero del equipo
  (`docs/context/tablero-validacion-ips-20oct.xlsx`). La mayoría son propuestas sin confirmar; no
  son entradas de `docs/DECISIONS.md`.
- **escenario**: un criterio de aceptación en forma Dado, Cuando, Entonces, numerado dentro de su
  historia.
- **historia de usuario** o **HU**: un pedido con formato Como, Quiero, Para y sus escenarios. El
  backlog tiene 30, con ID de HU-001 a HU-043; los números que faltan son tarjetas archivadas.
- **inactivar**: dejar de ofrecer un valor de catálogo en registros nuevos sin borrarlo; los
  registros que lo usan lo conservan (RC-4, D-20).
- **lámina**: la imagen del mockup de una historia, en `docs/context/trello/mockups/`.
- **numeral**: uno de los 49 datos del anexo, con sus valores permitidos, formato y longitud. El
  tablero también lo llama elemento.
- **ocupación hija**: una ocupación de 4 dígitos, el nivel más específico de la CIUO-88 A.C. Es la
  única que se registra al paciente [2.1].
- **plan B**: el plan de desarrollo de las 12 historias en que el cuarto integrante hace HU-001,
  HU-005 y HU-006 (2026-10-22 "Se desarrollan las 12 historias del plan B").
- **RC-n**: una regla común del backlog, de RC-1 a RC-11, en la tanda 0, sección 3.
- **referencia oficial**: el documento de la fuente que respalda crear, cambiar o inactivar un valor
  de catálogo. Sin ella, la IPS no puede tocar el catálogo (artículo 7).
- **tanda**: uno de los seis archivos `docs/context/backlog-v3-tanda-*.md`. Es la fuente de las
  historias (2026-10-22 "El material del backlog entra en docs/context").
- **tarjeta**: el texto de una historia para Trello, en `docs/context/trello/tarjetas/`. Sale de su
  tanda, sin la trazabilidad.
- **versión de un catálogo**: un archivo oficial cargado, con su fuente, su fecha de publicación y
  su fecha de descarga (RC-8).
