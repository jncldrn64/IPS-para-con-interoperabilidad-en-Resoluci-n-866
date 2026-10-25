# MEMORIA-CONTEXTUAL.md: estado de la validación del anexo y decisiones del autor

> **Rol:** registro de trabajo del modelo en este chat. Guarda lo que el autor decidió, lo que el
> modelo propuso y corrigió, y el estado de cada numeral del anexo (1 a 23; desde el 2026-10-20,
> los cuatro bloques), para no perderlo si el chat se comprime. No manda sobre la hoja del PDF del anexo ni sobre el tablero en línea.
> **Régimen:** la sección 9 es append-only, con entradas fechadas; las secciones 6, 7, 8 y 12 se
> corrigen a medida que avanza el trabajo. **Origen:** creado el 2026-10-18 a pedido del autor.
> Toma de la plantilla 1.1 el bloque de cita, las entradas fechadas de `docs/DECISIONS.md`, la cita
> textual sin corregir de `docs/TEMPORARY-CONTEXT.md` y las marcas de honestidad de `CLAUDE.md`,
> sección 8. Es un solo archivo; no se siembra el resto de la plantilla.

## 0. Cómo se usa (instrucciones para el modelo)

1. Leer este archivo entero antes de responder sobre el proyecto, y siempre después de una
   compresión del chat.
2. Actualizarlo después de cada numeral discutido y de cada decisión del autor, en el mismo turno.
3. Las palabras del autor van textuales y sin corregir. Lo que el modelo infiere va marcado
   **Hipótesis:**. Lo que no se puede reconstruir va marcado **Sin origen recuperable.**
4. Si choca con la hoja del PDF, manda la hoja. Si choca con el tablero en línea, se pregunta al
   autor el valor actual de la celda.
5. El contenedor es efímero. Este archivo se envía al chat en cada versión importante; si el
   contenedor se reinicia, el autor lo vuelve a subir.
6. Con el autor se habla por **numeral del anexo**. La fila V solo aparece cuando se le dice dónde
   pegar.
7. Preferencias del autor que aplican aquí: él responde primero cada numeral y el modelo corrige
   después; correcciones nombradas de frente y explicadas por mecanismo; sin guion largo ni
   intensificadores; TL;DR en respuestas largas; no adelantarse.

## 1. Proyecto y fuentes

- Curso: Fundamentos de Ingeniería de Software, TdeA 2026-2, docente Andres Alberto Restrepo.
- Proyecto: aplicación para la IPS Salud y Vida bajo la Resolución 866 de 2021. Dos módulos: gestión
  de pacientes y contacto con el servicio de salud.
- Equipo: Juan Fernando (el autor), Juan José, Juliana, Jorge. El autor tomó el **bloque D**.
- Backlog entregado: 33 historias, HU-001 a HU-033, en cinco tandas. Retroalimentación del docente
  en la hoja Retroalimentación del tablero (columna "Compartida en el chat": 2026-10-13).
- Nota del backlog en Trello: 3,0/5, tarjeta "NOTA REVISIÓN HU 3,0 / 5.", comentario del docente
  del 2026-10-11. En la rúbrica de la guía, 3 = "Los objetivos y el desarrollo del contenido son
  desarrollados parcialmente, pero no aplica los conocimientos teóricos correctamente."

Archivos en el contenedor (efímeros):

| Archivo | Ruta | Qué es |
|---|---|---|
| PDF del anexo | `/home/claude/zip_info/contenido/adminverblobawa-2.pdf` | 13 páginas = hojas 13 a 25, sin capa de texto. Página n = hoja n + 12 |
| Proyecto de Clase | `/home/claude/zip_info/contenido/Proyecto_de_Clase-2.pdf` | Documento del docente. Texto idéntico al subido antes |
| Transcripción de referencia | `/home/claude/zip_info/contenido/anexo-res866-elementos-1-23-referencia.md` | Hecha con IA. Solo orientación |
| Tandas del backlog | `/home/claude/zip_info/contenido/backlog-tanda-*.md` | 5 archivos, 33 historias |
| Prompt de validación v2.1 | `/home/claude/zip_info/contenido/prompt-validacion-anexo-crud.md` | Escrito por el autor |
| Tablero (copia del 2026-10-18) | `/home/claude/tablero/tablero.xlsx` | Copia de referencia. El vivo está en línea y cambia |
| Tablero llenado (2026-10-20) | `/home/claude/ips-validacion/tablero-validacion-ips-20oct.xlsx` | Las 28 filas y las 33 historias analizadas por el modelo; hoja Entidades |
| Scripts | `/home/claude/scripts/llenar_tablero.py`, `verificar_llenado.py`, `leer_comentarios.py` | Llenado y verificación del 2026-10-20 |
| Comentarios del docente | `/home/claude/ips-validacion/entrada/comentarios-profesor-por-HU.md` | 63 comentarios, HU-001 a HU-030, exportación del 19 oct 2026 |
| Anexo 24 a 49 | `/home/claude/ips-validacion/anexo-res866-elementos-24-49-referencia.md` | Textual, de dos lecturas conciliadas; brutos en `transcripciones/` |
| Historias de los compañeros | `/home/claude/ips-validacion/entrada/hu-companeros-sin-equivalente.md` | 20 de 30 HU del tablero "Trabajo equipo" |
| Comparación | `/home/claude/ips-validacion/comparacion-companeros-anexo.md` | Compañeros frente al anexo 1 a 49 |
| Script hoja 49 | `/home/claude/scripts/agregar_anexo_49.py`, `datos_anexo_24_49.py` | Se corre después de `llenar_tablero.py` |
| Plantilla 1.1 | `/home/claude/plantilla/contenido/plantilla-1.1/` | Fuente de forma de este archivo |

Jerarquía de fuentes (del prompt v2.1): 1) hoja del PDF del anexo; 2) documento del docente;
3) retroalimentación del docente; 4) decisiones del equipo Acordadas o Confirmadas; 5) la
transcripción; 6) inferencia del modelo, siempre marcada.

## 1b. Glosario

- **Elemento o numeral:** una fila numerada del anexo, del 1 al 49.
- **Componente:** una parte de un elemento con su propio tipo, formato y longitudes. El triage
  (elemento 22) tiene dos componentes.
- **Fila V-XX:** fila de la hoja Validación del tablero. Una por componente, V-01 a V-28. Fila de
  Excel = número V + 1.
- **Bloque:** reparto de filas entre los cuatro integrantes. A, B, C, D.
- **Campo o atributo:** dato que vive dentro de otra entidad (paciente o atención). No da historias:
  sus límites se vuelven escenarios de las historias de su entidad.
- **Entidad:** algo con tabla propia y CRUD. Da 4 historias: crear, consultar, actualizar, eliminar.
- **Atención:** un contacto con el servicio de salud. Elementos 16 a 23. La crean, consultan y
  modifican HU-007, HU-008 y HU-009.
- **Historia clínica:** en este modelo no es entidad. Es el conjunto de atenciones de un paciente,
  lo que entrega HU-008. Analogía usada con el autor: la carpeta; cada atención es una hoja.
- **Mapa:** `mapa-anexo-vs-backlog.md`, clasificación previa hecha por un modelo. Sellado hasta que
  cada uno termine su bloque. El modelo no lo ha visto.
- **D-XX:** decisión de la hoja Decisiones del tablero.

## 2. Mapa del anexo: 49 elementos en cuatro grupos

Encabezados de grupo vistos en las hojas 13, 16, 18 y 22. El último elemento, el 49, está en la
hoja 25.

| Grupo | Elementos | Hoja donde empieza | ¿En esta fase? |
|---|---|---|---|
| Identificación del usuario de los servicios de salud | 1 a 15 | 13 | Sí: módulo de pacientes |
| Contacto con el servicio de salud | 16 a 23 | 16 | Sí: módulo de contacto |
| Tecnologías en salud | 24 a 36 | 18 | No |
| Resultados del uso de las tecnologías en salud | 37 a 49 | 22 | No |

Nombres de 24 a 49, leídos a 80 ppp, solo para orientación: 24 tecnología en salud; 25 finalidad;
26 descripción común del medicamento; 27 fecha de prescripción; 28 dosis prescrita; 29 vía de
administración; 30 duración del tratamiento; 31 frecuencia; 32 dosis administrada; 33 fecha de
entrega; 34 dosis entregadas; 35 unidades entregadas o aplicadas; 36 talento humano que realiza o
entrega la tecnología; 37 diagnóstico principal de egreso; 38 diagnóstico relacionado; 39 resultado
de valoración clínica; 40 diagnóstico de complicación; 41 condición y destino al egreso;
42 causa básica de muerte; 43 fecha y hora de finalización de la atención; 44 prestador a donde se
refiere; 45 incapacidad; 46 licencia de maternidad; 47 antecedentes; 48 exposición a factores de
riesgo; 49 talento humano que realizó el egreso.

**De dónde sale el corte en 23 (corregido el 2026-10-18).** El número 23 no está en la guía del
docente ni en Trello. Lo escribió un modelo en `anexo-res866-elementos-1-23-referencia.md` y en el
prompt v2.1 ("elementos 1 a 23"). Es una inferencia. Piezas, por tipo:

| Pieza | Tipo | Dónde |
|---|---|---|
| "La IPS ha solicitado, en una primera fase, el diseño e implementación de los siguientes módulos funcionales" | Literal, guía, sin negrita | Página 1, bajo "Contexto del desarrollo" |
| "No es necesario, en esta fase, desarrollar el módulo de registro de usuarios" | Literal, guía | Página 2, numeral 2.4 |
| Títulos de grupo del anexo | Literal, anexo | Hojas 13, 16, 18 y 22 |
| Módulo de contacto = elementos 16 a 23 | Inferencia por nombre: "Módulo de contacto con el servicio de salud" (negrita en la guía) = título de la hoja 16 | |
| Módulo de pacientes = elementos 1 a 15 | Inferencia por contenido: la tabla 2.1 solo nombra datos de los elementos 1 a 12 | |
| Numeral 2.2, diagnóstico CIE-10 | No decide: el CIE-10 aparece en el 23 y también en 37, 38, 40 y 42 | |
| Retro HU-004 y HU-007, revisión ampliada | Verificadas con capturas de Trello del autor (comentarios del 2026-10-11, 2:53 AM). HU-004: "Enumerar todos los datos del grupo paciente del anexo". HU-007: "Concretar prestador, fecha/hora, modalidad/grupo, entorno, vía, causa, triage cuando corresponda y diagnóstico principal/tipo" | Tarjetas HU-004 y HU-007 |
| Tablero "Trabajo equipo" de otro grupo (captura del autor) | Contraejemplo: tiene HU de elementos 24 (tecnologías), 38 (diagnóstico relacionado) y 47 (antecedentes y alergias); agrupa el CRUD de una entidad en una sola HU. Fechas de abril y mayo de 2026. **Hipótesis:** es de un semestre anterior; nota desconocida | Captura del 2026-10-18 |
| Retro General ampliada | Verificada con captura de Trello del autor: coincide con la fila 3 del tablero. Dice "todos los catálogos para campos con más de cinco opciones", sin límite de grupos | Tarjeta "NOTA REVISIÓN HU 3,0 / 5.", comentario del 2026-10-11 |

Riesgos: "todos los catálogos" se puede leer como el anexo entero; los elementos 41 y 43 describen
la atención y están en el grupo 4. Se resuelve preguntando al docente (D-16). El bloque D cae dentro
de 16 a 23 con cualquier lectura, así que su análisis no espera esa respuesta.

## 3. Bloques y equivalencia numeral ↔ fila V

La V cuenta componentes; el numeral cuenta elementos. Los elementos 2, 13 y 18 ocupan dos filas
cada uno, por eso a partir del 18 la V va 3 adelante del numeral, y el 23 ocupa tres filas.
El prompt v2.1, la hoja Leeme y la transcripción dicen el mismo reparto: no hay discrepancia por
compresión de contexto.

| Bloque | Numerales | Filas V | Filas de Excel |
|---|---|---|---|
| A | 1 a 6 | V-01 a V-07 | 2 a 8 |
| B | 7 a 12 | V-08 a V-13 | 9 a 14 |
| C | 13 a 18 | V-14 a V-21 | 15 a 22 |
| D | 19 a 23 | V-22 a V-28 | 23 a 29 |

Detalle del bloque D:

| Numeral | Fila V | Fila Excel | Componente |
|---|---|---|---|
| 19 | V-22 | 23 | Entorno donde se realiza la atención |
| 20 | V-23 | 24 | Vía de ingreso del usuario al servicio de salud |
| 21 | V-24 | 25 | Causa que motiva la atención |
| 22 | V-25 | 26 | Triage: fecha y hora, y clasificación |
| 23 | V-26 | 27 | Código y nombre del diagnóstico principal |
| 23 | V-27 | 28 | Código de enfermedad huérfana |
| 23 | V-28 | 29 | Tipo de diagnóstico principal |

## 4. Reglas que deciden atributo o entidad

Docente, numeral 2.3, textual: "Todos los campos que contienen más de 5 opciones seleccionables
deben tener una entidad propia en la base de datos." "Para cada una de estas entidades se debe
construir su correspondiente interfaz CRUD (Create, Read, Update, Delete)."

Docente, revisión General ampliada (2026-10-13), textual: "Falta demostrar cobertura de todos los
catálogos para campos con más de cinco opciones."

Regla del profesor relayada por el autor, textual y sin corregir: "puesto que el axeno es realmente
el punto de partida de las histordias de usario y asi deberia serlo, se deben analizar cada punto
de este axeno y determinar inicalmente si son un campo de una entidad o si tiene mas de 5
elementos y es algo que pueda crecer o alterarse dividir en su propia entidade, y cada entidad es
una accion de un crud, y cada parte del crud puede ser tomado literalmente como una historida de
usuario individual."

Preguntas del prompt v2.1, en orden: 1) ¿2.1 la nombra como estructura separada? 2) ¿catálogo
externo? 3) ¿lista cerrada con más de 5? 4) ¿reutiliza otra entidad? 5) campo del paciente (1 a 15)
o de la atención (16 a 23). Defecto conocido: la 4 nunca se alcanza para catálogos. Orden vigente
desde el ajuste 2 al prompt (2026-10-18): 1, 4, 2, 3, 5; la entidad compartida es de la fila V más
baja. Excepción aplicada el 2026-10-20: el prestador queda en V-18 y no en V-10 (ver sección 6).

Conteo: entidad = 4 HU; paciente = 3 (registrar, consultar, actualizar); atención = 3 (crear,
consultar, modificar); campo = 0, sus límites van como escenarios; inicio de sesión = 1 o 3 (D-04).
Con D-17 = una por entidad, cada entidad da 1 (hoja Entidades del tablero).

## 5. Lo que el autor entiende del método, textual, y su corrección

Autor, textual: "HU=Funcionalidad=Accion=AlgoqueCumpleconElAnexo, cada funcionalidad es un crud y
cada parte del crud puede ser su propia accion si tiene 5 o mas valores posibles, literalmente los
criterios salen de los propios limites de cada caracteriristica de la funcionalidad"

Correcciones dadas el 2026-10-18:
- "5 o más" no es lo que dice el docente: "más de 5", es decir 6 o más. Dos textos del docente.
- No toda HU sale del anexo: inicio de sesión sale de 2.4; operaciones del paciente y la atención
  salen del numeral 2. El anexo decide campos y catálogos.
- HU no es igual a funcionalidad: una funcionalidad da varias HU (catálogo de países da cuatro).
- Cierto: los criterios de cada campo salen de sus límites en la hoja.
- Formato Connextra (Como, quiero, para), INVEST (calidad) y MoSCoW (prioridad) son tres cosas
  distintas. El docente usa "Para", no "porque".

## 6. Estado del bloque D, numeral por numeral

### Numeral 19, entorno (V-22, fila 23)

- Hoja 16: 01 Hogar, 02 Comunitario, 03 Escolar, 04 Laboral, 05 Institucional. Alfanumérico, 99,
  2 y 2. Definición: "lugar donde se realiza la atención de salud".
- Autor, textual: "el numeral 19 dice entorino donde se realiza la antencion, solo tiene un campo
  con 5 valores, es un atributo, supongo que de la historia clinica, ya que hablaria de donde fue
  hecho el procedimento, no amerita crud"
- Modelo: atributo sin CRUD, correcto. Entidad corregida: **Atención**, no historia clínica (el
  mismo paciente puede atenderse en casa un día y en la institución otro). "Procedimiento" es del
  numeral 21.
- Clasificación: Campo de la atención. Pregunta 5. HU existentes: HU-007, HU-008, HU-009. Nuevas:
  ninguna. Depende de D-01.
- Estado: el autor respondió; **la corrección a Atención no tiene respuesta todavía.**
- Valores entregados para pegar (fila 23): L Sí; M 5; N Campo de la atención; O Atención; P 5;
  Q HU-007, HU-008, HU-009; R ninguna; W Prompt con IA; X "Hoja 16, elemento 19: 5 valores,
  alfanumérico, formato 99, longitud 2 y 2. Numeral 2.3: más de 5 opciones. D-01. Retroalimentación
  HU-007, revisión ampliada: concretar entorno."; AC "Lista de exactamente 5 valores. Depende de
  D-01: si el umbral pasa a 5 o más, cambia a Entidad por 2.3 con 4 historias nuevas.
  Obligatoriedad del dato: PENDIENTE D-15."
- **Sin confirmar:** si el autor pegó N a X y AC, y si cambió T23 a Analizado.

### Numeral 20, vía de ingreso (V-23, fila 24)

- Hojas 16 y 17: 14 valores, 01 a 14. Alfanumérico, 99, 2 y 2. Definición: "puerta de entrada de
  la persona al servicio de salud". La hoja escribe "Contra referido" en el 14.
- Ya pegado (confirmado por el autor): L "No: hay diferencias", M 14, AC con la diferencia del 14.
- Autor, textual: "el numeral 20 del anexo via  de ingreso al servicio de salud tiene mas de 7
  elementos en su campo y dice alfanumerico, pese a que se almacenara en numeros estoy tentado a
  decir que es un crud, ya que es algo que cambiaria con el tiempo, que es algo que no hemos
  tratado, por que si bien al usuario en terminal le mostraremos texto y ocpicoes, estas
  almacenarar numeros en la DB por el tema de la interoperabilidad, sin embargo estoy tentado a
  decir que es un atributo de una entidad, pero entidad de quien? de la historia clinica? similar
  al 19? yo nunca he visto que me pregunte por donde ingrese al hospital."
- Modelo: CRUD sí, correcto. Correcciones: son 14 valores, no "más de 7"; decide el conteo contra
  el umbral, no que cambie en el tiempo; se guarda como texto de 2 caracteres, no como número,
  porque un entero pierde el cero de "01"; es las dos cosas: el catálogo es entidad con CRUD y la
  atención tiene un atributo que apunta a una fila del catálogo (analogía: la carta del restaurante
  y la comanda); la entidad que lo usa es Atención, no historia clínica; al paciente no se le
  pregunta: lo registra el personal según cómo llegó.
- Pregunta 3 (¿lo cubre alguna de las 33?): el autor no la contestó. Respuesta del modelo: ninguna
  cubre el CRUD; HU-007 a HU-009 usan el dato y quedan en Ajustar.
- Clasificación: Entidad por 2.3. Pregunta 3. Entidad: Vía de ingreso. 4 HU nuevas.
- IDs propuestos en Por redactar: HU-062 a HU-065 (filas 30 a 33), **condicionado a que el equipo
  no los haya tomado**. Cargo del CRUD: PENDIENTE D-02.
- Estado: valores para pegar entregados el 2026-10-18; **sin confirmar si se pegaron.**

### Numeral 21, causa (V-24, fila 25)

- Hoja 17: 29 valores, 21 a 49. Alfanumérico, 99, 2 y 2. Redacción de la hoja difiere de la
  referencia en 29, 40, 43 a 47.
- Ya pegado: L "No: hay diferencias", M 29, AC con las diferencias.
- Autor, textual: "el 21 tambien aparentemente y este ultimo tiene pinta de ser una entidada en si
  mismo. Aqui no hay nigun tipo de duda en el porque debe ser una entidad (objetivamente tiene mas
  de 3 campos), si bien en el 20 puede haber dudas por el como esta construido, esta lista muestra
  claramente que crece; al ser grande debe tener id (los dos digitos) como prmaria y lo que se
  almacenada y un string para almacener la descripcion claramente (Aun que este ultimo seria abrir
  el scope de lo que es necesario del anexo)." Y: "de hecho ahora que lo pienso hay "anexos de
  anexos". seria raro que este no tenga una norma, asi como la iso para los paises, donde se usa
  es la iso."
- Modelo: entidad, correcto. Correcciones: el umbral es más de 5 valores, no "más de 3 campos"; el
  21 tiene un componente con 29 valores. "Crece": la lista no crece a voluntad de la IPS; por el
  artículo 7 solo cambia si cambia la norma; que los códigos empiecen en 21 sugiere historia previa,
  **Hipótesis**. Diseño propuesto por el autor (código como llave primaria más descripción):
  correcto; la descripción no abre el alcance, porque los nombres están en los valores permitidos
  de la hoja y hacen falta para mostrar; la hoja no da longitud para ese nombre (decisión de
  diseño). En el 21 un entero no rompe la longitud, porque todos los códigos tienen dos cifras; en
  19, 20 y 22 sí.
- Catálogo externo para el 21: la hoja 17 no cita ninguno. Dos búsquedas web (SISPRO, RIPS
  Res. 2275 de 2023) sin resultado. **No verificado.** No cambia la clasificación.
- Pregunta 3: el autor no la contestó. Ninguna de las 33 cubre el CRUD; HU-007 a HU-009 usan el
  dato.
- Clasificación: Entidad por 2.3, pregunta 3. Entidad: Causa que motiva la atención. 4 HU nuevas;
  IDs en espera de D-17.
- Estado: valores para pegar entregados el 2026-10-18; **sin confirmar si se pegaron.**

### Numeral 22, triage (V-25, fila 26)

- Hojas 17 y 18: fecha y hora (Fecha, AAAA-MM-DD HH:MM, 16 y 16) y clasificación (01 Triage I a 05
  Triage V, 99, 2 y 2). Definición de la clasificación: prioridad en un servicio de urgencias.
- Autor, textual: "El numeral 22 dela anexo dice algo de clasificacion y tiene 2 elementos, fecha y
  nivel de tiage, no tiene pinta que sea lago que cambie con el tiempo, por lo que no amerita crud o
  que sea una entidad en si misma, mas  bien parecen atributos de una entidad, de que entidad aun
  estariamos por ver, aun que ya sugeriste...."
- Modelo: conclusión correcta. Ajuste al razonamiento: decide el conteo (5, no más de 5), no que
  cambie en el tiempo. La fecha no se elige de lista. Vocabulario: son componentes, no elementos.
  Entidad: Atención.
- Escenarios derivados: hora obligatoria (mínima 16, contraste con nacimiento mínima 10); formato y
  fecha inexistente se rechazan; clasificación fuera de 01 a 05 se rechaza; cuándo se exige espera
  D-13. Descartado por falta de fuente: triage anterior al inicio de la atención.
- Valores entregados para pegar (fila 26): N Campo de la atención; O Atención; P 5; Q HU-007,
  HU-008, HU-009; R ninguna; W Prompt con IA; X y AC entregados en el chat del 2026-10-18.
- **Sin confirmar:** si el autor los pegó.

### Numeral 23, diagnóstico (V-26, V-27, V-28, filas 27 a 29)

- Hoja 18. V-26: código CIE-10 (4 y 4) y nombre (200 y 3). V-27: código de enfermedad huérfana
  (9999, 4 y 1), solo "en caso de presentar". V-28: tipo, 3 valores.
- Ya pegado: L y M en las tres; AC en V-26 y V-27.
- Autor, textual: "tiene pinta de ser una entidada, yo lo llamaria como entidad, es algo muy
  concreto, no es spmilemente un campo de una historia clinica, y el hecho que nos den a buscar un
  catalogo como asexo nos confirma que seguramente tenga mas de 5 campos..... estoy haciendo esto
  mal, el 23 tiene 3 composiciones, y los valores permitidos son exactamente eso, y tiene mas de 5
  valores permitidos en la composicion principal, de hecho el 22 tiene 2 composiicones, pero como
  es fecha, supongo que lo dejaremos pasar... algo que me llama la atencion es el nombre principal
  del diagnostico, dice alfanumeria sin formato, pero longitud maxima, ese si parece ser un espacio
  para texto, pero discrepo"
- Modelo sobre V-26: entidad, correcto; decide la pregunta 2 (catálogo externo CIE-10) más el 2.2
  (jerarquía padre-hijo). Correcciones: son valores, no campos; tres composiciones con cuatro filas
  de valores (el código tiene dos: CIE-10 y huérfanas); el tablero agrupa distinto (V-26 código y
  nombre, V-27 la fila de huérfanas, V-28 tipo); la entidad que usa el dato es la atención, no la
  historia clínica; el 22 no se dejó pasar, se clasificó; el nombre es texto pero no texto libre:
  "Nombre correspondiente al código", vive en la tabla del catálogo. El "discrepo" del autor quedó
  sin aclarar: el modelo preguntó si era contra el texto libre.
- V-26: Entidad por 2.3, pregunta 2, entidad Diagnósticos CIE-10. Cubierta por HU-030 a HU-033
  (Ajustar). HU-007 a HU-009 usan el código (Ajustar). Nuevas: ninguna. Valores entregados.
- V-27 y V-28: preguntas abiertas al autor.
- **Hipótesis del modelo, no dicha al autor:** V-26 Entidad por 2.3 ya cubierta por HU-030 a
  HU-033; V-27 Entidad por 2.3 sin HU (4 nuevas, D-14); V-28 campo de la atención. HU-008
  escenario 5 (varios diagnósticos) choca con "diagnóstico principal".
- **2026-10-20:** V-27 y V-28 las decide el modelo a pedido del autor (bitácora del 2026-10-20).
  V-27: Entidad por 2.3, pregunta 2, catálogo distinto del CIE-10 (la hoja 18 lo nombra aparte;
  longitud 1 a 4 frente a 4 y 4), dato opcional, 4 HU nuevas. V-28: 3 valores, campo de la atención.

### Numerales 1 a 18, decididos por el modelo el 2026-10-20

El autor pidió que el modelo resolviera el tablero entero (cita en la bitácora). Lo que sigue es
**clasificación del modelo**, no respuesta del autor; la revisión la hacen los compañeros.
Conteo M hecho en las hojas 13 a 16. L, después de la revisión independiente (bitácora del
2026-10-20): "Sí" solo en V-02, V-06, V-07, V-08, V-12, V-14 (lectura dudosa de "Indigena"),
V-16, V-22, V-25 y V-28; las otras 18 filas, "No: hay diferencias". Criterio: F a K coinciden con
la hoja letra por letra, salvo separadores.

| Numeral | Fila V | Valores en la hoja | Clasificación | Pregunta | Entidad | HU |
|---|---|---|---|---|---|---|
| 1 | V-01 | Catálogo ISO 3166-1 | Entidad por 2.3 | 2 | Países | HU-018 a 021 (Ajustar) |
| 2 | V-02 | 14 | Entidad por 2.3 | 3 | Tipos de documento | 4 nuevas |
| 2 | V-03 | Dato abierto, 3 a 20 | Campo del paciente | 5 | Paciente | 0 |
| 3 | V-04 | 4 componentes, 2 a 60 | Campo del paciente | 5 | Paciente | 0 |
| 4 | V-05 | Fecha, mínima 10 | Campo del paciente | 5 | Paciente | 0 |
| 5 | V-06 | 3 | Campo del paciente | 5 | Paciente | 0 |
| 6 | V-07 | 5 | Campo del paciente (depende de D-01) | 5 | Paciente | 0 |
| 7 | V-08 | Catálogo CIUO-88 A.C., código de 4 | Entidad por 2.3 | 2 | Ocupaciones | HU-022 a 025 (Ajustar) |
| 8 | V-09 | 2 | Entidad por 2.1 | 1 | Oposición | HU-010 a 013 (Ajustar) |
| 9 | V-10 | 2 | Entidad por 2.1 | 1 | Voluntad anticipada | HU-014 a 017 (Ajustar) |
| 10 | V-11 | 8 | Entidad por 2.3 | 3 | Discapacidad | 4 nuevas |
| 11 | V-12 | Catálogo ISO 3166-1 | Reutiliza otra entidad | 4 | Países | 0 |
| 12 | V-13 | Catálogo DIVIPOLA, código de 5 | Entidad por 2.3 | 2 | Municipios | HU-026 a 029 (Ajustar) |
| 13 | V-14 | 7 | Entidad por 2.3 | 3 | Etnias | 4 nuevas |
| 13 | V-15 | Catálogo de comunidades étnicas | Entidad por 2.3 | 2 | Comunidades étnicas | 4 nuevas |
| 14 | V-16 | 2 | Campo del paciente | 5 | Paciente | 0 |
| 15 | V-17 | Catálogo del Ministerio | Entidad por 2.3 | 2 | Entidades del plan de beneficios | 4 nuevas |
| 16 | V-18 | REPS, código de 12 | Pendiente de decisión (Bloqueado, D-05) | | Prestadores o Atención | 4 si D-05 lo hace catálogo |
| 17 | V-19 | Fecha y hora, 16 y 16 | Campo de la atención | 5 | Atención | 0 |
| 18 | V-20 | 9 | Entidad por 2.3 | 3 | Modalidades | 4 nuevas |
| 18 | V-21 | 5 | Campo de la atención (depende de D-01) | 5 | Atención | 0 |

Razonamientos que no están en la tabla:
- V-10, revocación: es una actualización, porque la hoja 14 pone "suscripción, modificación,
  sustitución o revocación" en el mismo componente de fecha. Resuelve el pendiente 2 de la tanda 3.
- V-10 y V-18, prestador: por la regla de la fila más baja lo propondría V-10; se deja en V-18
  porque allí el código es el dato de la fila y en V-10 es un componente de una estructura.
  **Hipótesis:** para una sola IPS, el prestador que realiza la atención puede ser fijo; el del
  elemento 9 puede ser cualquier prestador del país.
- V-08: código de exactamente 4 caracteres; que la CIUO-88 A.C. tenga niveles de 1 a 3 por encima
  es **Hipótesis** del modelo, no está en la hoja.
- V-13: **Hipótesis:** si la residencia es fuera de Colombia, el municipio no aplica.
- V-15: **Hipótesis:** cada comunidad pertenece a una etnia; no se agrega como regla.

Totales (hoja Entidades del tablero): 15 entidades seguras (6 con historias y 9 sin), 36 HU
nuevas. Con una HU por operación: 33 + 36 = 69 (73 con prestador). Con una por entidad: 15 + 3
paciente + 3 atención + 3 inicio de sesión = 24 (25 con prestador). Desde 24 hacen falta al menos 2
entidades con sus 4 HU para llegar a 30.

## 7. Ajustes acumulados a historias existentes

**Desde el 2026-10-20 el registro vivo es la hoja "Ajustes por historia" del tablero: 104 filas
en lenguaje simple, una por historia y punto a cambiar, con el comentario del docente o el numeral
del anexo como fuente, más dos filas generales.** Regla del autor: si una historia necesita muchas
reglas, no las define; dice el numeral del anexo. La tabla de abajo queda como historia del bloque D al 2026-10-18.

Propuesta del modelo: acumular aquí y pegar una sola vez al cerrar el bloque D, leyendo antes lo que
haya puesto el bloque C. **El autor no respondió si acepta acumular.**

| HU | Resultado | Ajustes acumulados |
|---|---|---|
| HU-007 | Ajustar | Numeral 23: código de diagnóstico que no esté en el catálogo se rechaza; el nombre se toma del catálogo, no se escribe. Numeral 21: causa que no esté en el catálogo se rechaza. Numeral 20: vía de ingreso que no esté en el catálogo se rechaza. Numeral 19: escenario nuevo, entorno fuera de los 5 valores se rechaza y se conserva el resto; escenario 1 debe nombrar los datos del anexo. Numeral 22: escenarios de fecha y hora del triage (formato, fecha inexistente, hora obligatoria) y de clasificación fuera de 01 a 05; cuándo se exige, PENDIENTE D-13. Ya conocido: escenario 11 con resultado pendiente, prohibido por el prompt |
| HU-008 | Ajustar | Escenario 1 entrega solo fecha y diagnóstico: debe entregar entorno, vía de ingreso, causa y triage. Ya conocido: escenario 5 de varios diagnósticos choca con la hoja 18 |
| HU-030 | Ajustar | Numeral 23: "Carga de valores" pasa a creación de un diagnóstico; fuera los escenarios de carga masiva; crear solo para reflejar el catálogo oficial con su versión (artículo 7, D-09); cargo D-02 |
| HU-031 | Ajustar | Numeral 23: escenario 4 (código padre) necesita resultado concreto; cargo D-02 |
| HU-032 | Ajustar | Numeral 23: cargo D-02; actualizar solo por cambio oficial con versión |
| HU-033 | Ajustar | Numeral 23: cargo D-02; escenario 10 "lo carga de nuevo" pasa a "lo crea de nuevo"; verificar en Trello el texto "Precisar inactivación/recarga" |
| HU-009 | Ajustar | Numerales 20 y 21: cambiar la vía o la causa por una que no esté en su catálogo se rechaza. Numeral 19: cambio de entorno fuera de la lista se rechaza y se conserva el anterior. Numeral 22: mismas validaciones del triage al modificar |

## 8. Decisiones del tablero

| ID | Tema | Estado conocido | Nota |
|---|---|---|---|
| D-01 | Umbral 2.3 | Pendiente en la copia | El modelo propuso marcarla Confirmada por el docente; evidencia en I2 |
| D-02 | Cargo que mantiene catálogos | Pendiente | |
| D-03 | ISO 3166-1 alfabético o numérico | Pendiente | |
| D-04 | Inicio de sesión: 1 o 3 HU | Pendiente | |
| D-05 | Prestador como catálogo | Pendiente | Elementos 9 y 16 |
| D-06 a D-12 | Varias | Pendientes | Ver tablero |
| D-13 | Cuándo se exige el triage | Creada por el autor | Confirmado: "ya hice eso" |
| D-14 | Catálogo de enfermedades huérfanas | Creada por el autor | Confirmado: "ya hice eso" |
| D-15 | Datos obligatorios de la atención | En el tablero desde el 2026-10-18 | |
| D-16 | Alcance: elementos 1 a 23 | Pendiente | Confirmar con el docente en Teams |
| D-17 | Una HU por operación o por entidad | Pendiente | 69 frente a 24; mezcla obligatoria |
| D-18 | Cómo se guardan los componentes | Pendiente, con valor propuesto | El autor aceptó texto para Alfanumérico |
| D-19 | Datos obligatorios del paciente | Creada el 2026-10-20 | Segundo nombre y segundo apellido |
| D-20 | Eliminar = inactivar | Creada el 2026-10-20, propuesta del autor | Contra: el 2.3 dice "Delete" |
| D-21 | Fuente y versión de catálogos externos | Creada el 2026-10-20 | ISO, CIUO, DIVIPOLA, comunidades, EAPB, REPS |
| D-22 | Código CIE-10 padre en una atención | Creada el 2026-10-20, valor propuesto | No: el numeral 23 pide 4 y 4 |
| D-23 | Búsqueda en catálogos | Creada el 2026-10-20, valor propuesto | Código exacto, nombre parcial |

## 9. Bitácora (append-only)

## 2026-10-18: El autor elige empezar por el bloque D

**Contexto:** El prompt v2.1 reparte las 28 filas en cuatro bloques. **Decisión:** el autor toma
el D, numerales 19 a 23. **Estado:** `vigente`

## 2026-10-18: Ediciones de control del bloque D en el tablero

**Contexto:** El modelo cotejó la transcripción contra las hojas 16 a 18. **Decisión:** el autor
pegó S, T, AA, L, M y AC en las filas 23 a 29, I2 de D-01, y creó D-13 y D-14. **Estado:**
`vigente`

## 2026-10-18: No se empieza de cero

**Contexto:** Autor, textual: "siento tantas ganas de comenzar desde 0.... pero sea lo que sea que
eligas decime". **Decisión:** el modelo decide seguir. Razones: la clasificación del tablero está
vacía (columnas L a R de Validación y R a T de Historias sin celdas llenas en la copia); el corte en
23 tiene respaldo del docente; los compañeros ya tienen el Excel. **Alternativas:** reiniciar,
descartado por costo y porque lo que el autor quiere hacer cabe en el tablero. **Estado:** `vigente`

## 2026-10-18: Se discute por numeral del anexo

**Contexto:** Autor, textual: "estamos discutiendo esta numeracion rara de v-n y no los numerales
de los axenos". **Decisión:** con el autor se habla por numeral; la V solo para pegar. El autor
responde primero cada numeral y el modelo corrige. **Estado:** `vigente`

## 2026-10-18: Se crea este archivo

**Contexto:** Autor, textual: "crea un documento que usaras de forma interna para llevar tu mismo
la contabilidad de mis deciciones, y asi no perder lo que llemavos aun si comprimiera el chat".
**Decisión:** un solo .md con la forma de la plantilla 1.1. **Estado:** `vigente`

## 2026-10-18: El corte en 23 pasa a ser pregunta para el docente

**Contexto:** Autor, textual: "en ninguno de mis apuntos cito 23 como limite, si algo lo que esta en
negrita parece ser los puntos mas claves de todo el asunto en el PDF... tampoco se de donde sacas lo
de la primera fase." **Decisión:** el 23 se trata como inferencia de un modelo, no como dato del
docente. "Primera fase" es literal (página 1). El alcance se confirma con el docente (D-16). El
bloque D sigue, porque cae dentro de 16 a 23 con cualquier lectura. **Estado:** `vigente`

## 2026-10-18: El autor quiere rehacer todo desde el anexo y hacer el sprint 1 en terminal con H2

**Contexto:** Autor, textual: "toda la retro-alimentacion que tengo que nos complicamos con las
historias de usuario, todo lo que teniamso que hacer es ir al anexo de la resolucion para
redactarlas,  ya que esa es tecnicamente de donde saldria la epica, da igual el resto de puntos,
por que serian tratados por las otras fgases del spring, es decir que ni quierea hacia falta que
nos complicaramos con acceso web de la app, con que sea terminal y una db en H2 literalmente
deberia funcionar... si rehacer todo en base al anexo (algo que parece que hicieron algunos
companeros) seguramente complique aun mas, es un tema complejo, pero estoy muy tentato a
hacerlo...." **Decisión:** ninguna todavía. El modelo propuso: el tablero ya es el rehacer desde
el anexo; reescribir tarjetas después de clasificar las 28 filas. Señaló que la retro también pide
mockups, cargo real y estimación, que no salen del anexo; y que la guía dice "aplicación web"
(terminal depende de D-10, mockups de D-11). **Estado:** `abierta, sin respuesta del autor`

## 2026-10-18: Disputa sobre cómo se guardan los códigos de las listas

**Contexto:** Autor, textual: "El numeral 20 es en numero (y de paso de todo el resto que
disncuturemos y distutimos), si te fijas la longitud ayuda a saber los ecenarios, por que deben
amacenarse en numeros, de ahi la interoperabilidad, literalmente todo el pastel esta ahi, que
sucede si se ingresa un numero que no es? que como se debe almacenar? como se debe presentar, es
evidente que en el almacenaje es una cosa para el tema de la interoperabilida, y otra en el tema de
la presentacion en la interface o gui". **Decisión:** ninguna. El modelo sostiene: el autor acierta
en que los escenarios salen de los límites y en separar almacenamiento (código) de presentación
(nombre); no acierta en "número". Evidencia: el elemento 20 tiene Tipo Alfanumérico (hoja 16); el
anexo usa Numérico para cantidades (elemento 46, "Entre 1 y 999", mínima 1, hoja 24); incluso el
elemento 48, tipado Numérico, lleva códigos 01 a 06 con mínima 2 (hoja 24), así que el valor que se
intercambia es "01" en ambos casos. "Todo en números" se rompe en el elemento 2 (CC, TI), en el 12
(DIVIPOLA de Medellín 05001 pasa a 5001) y en el 23 (CIE-10 con letras). Recalibración del modelo:
antes lo dijo como regla absoluta; el anexo no es uniforme (47 Alfanumérico y 48 Numérico, misma
forma). Disputa enviada al auditor externo. **Estado:** `abierta`

## 2026-10-18: El autor acepta que la clasificación decide qué se comprime, se descarta o entra

**Contexto:** Autor, textual: "de todo este trabajo sale lo que se comprime, se descarta, lo nuevo
que entra, y lo que puede quedarse por que ni ayuda ni hace dan~o". También: "bajo el supuento que
sea hasta el 23, de ahi salen tranquilamente las 30 historias de usario reales que necesitamos para
el minimo viable y la epica, incluzo daria para comprimir cruds en una sola historia de usuario".
**Decisión:** se sigue clasificando. Aritmética del modelo, **Hipótesis** (bloques A a C sin
discutir, D-01 y D-05 abiertos): 13 entidades por 2.3 (14 si el prestador es catálogo) más 2
estructuras del 2.1. Con 4 HU por estructura: 67 a 73 HU. Con todo el CRUD comprimido en una HU por
estructura: 22 a 25 HU, por debajo de 30. La compresión solo puede ser parcial. "Ni ayuda ni hace
daño" no existe en el sprint: una HU sin valor consume estimación y capacidad; el prompt v2.1 solo
tiene Cumple, Ajustar o Sobra. **Estado:** `vigente`

## 2026-10-18: Prompt de auditoría crítica para otro modelo

**Contexto:** Autor, textual: "si quieres puedes darme un prompt para el otro modelo para que trates
de rescatar ideas erradas y que el otro modelo asuma la responsabilidad critica de todo esto..".
**Decisión:** se entrega `prompt-auditoria-critica.md`. Clasificación independiente primero con solo
fuentes primarias; después inventario y veredicto de afirmaciones con escala graduada; seis
disputas presentadas sin decir quién sostiene cuál; destino de cada una de las 33 HU. Incluye este
registro como afirmaciones a auditar. **Estado:** `vigente`

## 2026-10-18: El prompt pedido era para el modelo original, no para un auditor nuevo

**Contexto:** Autor, textual: "me referia a un prompt para el modelo que inicialmente redacto todos
los documentos, el que generaste, parace mas bien el que debio ser este chat". **Decisión:** se
entrega `prompt-relevo-modelo-original.md`, para pegar en el chat del modelo que escribió tandas,
prompt v2.1, transcripción y tablero. Pide: estado de su contexto; veredicto y origen de 13
hallazgos (H-01 a H-13) con hoja o página; su posición en la disputa número o texto; ideas de su
chat que no llegaron a los documentos; plan de corrección sin ejecutar. `prompt-auditoria-critica.md`
queda como auditoría independiente opcional. **Estado:** `vigente`

## 2026-10-18: Riesgo de los otros bloques, anexo para sus modelos y continuidad del bloque D

**Contexto:** Autor, textual: "crees que los modelos de los otros companeros pueden confundirse o
hacer el trabajo bien de los troso 3 bloques? ponemos en el execel alguna informacion adicional que
el los otros modelos de los companeros puedan usar? contiuamos con el bloque que estabamos
haciendo? o continuar esta fuera des scope actual sin la auditoria del modelo original?"
**Decisión propuesta por el modelo, sin respuesta del autor:** el bloque B es el de más riesgo
(V-12 por la pregunta 4; ocupación, discapacidad y municipio por las reglas del 2.1 que faltan). Los
modelos no ven el Excel: el arreglo es un texto de ajustes al prompt v2.1 que cada compañero pega
en su chat, y una copia en el tablero para dejar registro. El bloque D sigue: la clasificación
depende de las hojas 16 a 18 y del 2.3, que la auditoría no cambia. Lo que espera: la hoja Por
redactar (depende de la granularidad del CRUD), el paso 4 de redacción y los resultados Cumple,
Ajustar o Sobra en Historias. Decisiones propuestas: D-17 granularidad del CRUD; D-18 representación
de los códigos. **Hipótesis:** con "más de 5", las historias nuevas de los cuatro bloques suman 36
(40 si el prestador es catálogo), justo los 40 IDs de HU-034 a HU-073. **Estado:** `abierta`

## 2026-10-18: Respuesta del modelo original a la auditoría

**Contexto:** el autor subió `respuesta-auditoria-backlog-ips.md` (copia en
`/home/claude/ips-validacion/`). **Contenido:** acepta los 13 hallazgos (H-11 en parte), con hoja,
página o línea. Declara que su chat se compactó antes de T297 y que releyó el historial con una
herramienta. **Verificado por este modelo:** conteos de H-02; reparto 17/12/4 de actores; líneas
citadas de las tandas (4a:35, 1:15-19, 1:26, 1:647, 2:145, 2:354, 1:368-373, 4a:119 y 158);
H-13 adicional (ovulo, pre hospitalaria, Telexperticia, Telemonitoreo); Parte B coincide con las
hojas. Artículos 5, 7, 10 y 13 de la Res. 866 corroborados en sustancia en el normograma de la
Supersalud vía WebFetch; **el texto literal no se pudo transcribir completo por límite de la
herramienta**. **No verificable aquí:** los turnos T del otro chat y las citas del autor que trae
(T162, T198, T242, T286): las confirma el autor. **Hechos nuevos:** artículo 7, los catálogos "no
podrán ser modificados, reducidos o adicionados"; artículo 13.2, el contacto se asocia a
tecnologías en salud (pesa contra el alcance 1 a 23); artículo 10 parágrafo 2 regula la corrección
de inconsistencias, más estrecho que HU-006 y HU-009; la partición del CRUD por operación se eligió
para llegar a 30 (su T203). **Puntos débiles señalados al autor:** la precisión de H-09 se apoya en
el texto de HU-033 que llegó "sin encabezado" y cuya asignación es inferida, sin verificar en
Trello; los escenarios 10 "Recarga después de eliminar" son volver a crear un valor borrado por
error ("lo carga de nuevo"), o sea Crear, no carga masiva; partir V-04 y V-09 no cambia ninguna
clasificación; desliz literal en el valor 40 (la hoja 17 dice "salud-Intervenciones individuales.",
el informe escribe "salud- intervenciones"). **Propuesta al autor:** que el modelo original escriba
la v2.2 del prompt y este modelo la revise; las tandas no se reescriben hasta cerrar la validación
y D-02, D-16, D-17. **Estado:** `abierta, sin respuesta del autor`

## 2026-10-18: Ocupaciones y llenado inicial de catálogos (bloque B, anotado para su dueño)

**Contexto:** Autor, textual: "de hecho para las ocupaciones, no dice que hasta los 3 digitos, por
que de ahi hacia adelante en los anidados, es inmanejable manualmente." También: "Sabemos que el
numeral 19, 20, 22 almacena numeros". **Respuesta del modelo:** la hoja 14 fija el código de
ocupación en 4 y 4 (formato 9999): solo se aceptan códigos de 4 cifras, que es el nivel más
detallado y coincide con "solo las ocupaciones hijas" del 2.1. Que la CIUO-88 tenga niveles de 1 a
4 cifras es conocimiento del modelo, **no verificado en la hoja**. Llenar a mano catálogos grandes
(CIUO, CIE-10, DIVIPOLA, ISO) no es viable: el llenado inicial se propone como tarea técnica del
equipo (datos iniciales de la base), no como historia de usuario; el CRUD de las historias cubre
el mantenimiento cuando cambia el catálogo oficial (artículo 7). Sobre "almacena numeros": sigue
siendo la disputa abierta, propuesta como D-18; no se da por sabido. **Estado:** `abierta`

## 2026-10-18: El autor acepta que Alfanumérico se guarda como texto (cierra la disputa de D-18)

**Contexto:** Autor, textual: "Si dice alfanumerico, es por que seguramente no se almacene como un
INT si no mas bien como un Sting, 99 INdica el maximo del formato, el limite superiro e inferior de
las longitudes corroboran eso, hay tipos que no son alfanumericos y citan textualmenet numerico,
esos si deben ser INT, de hecho no hay srings en ningun lado, lo que dicta que un string no es
interoperable, es algo que si almacenamos es para poder mostrar en la interface, ya sea en la DB o
no, imagino que la idea es poder abrir la db en cualqeuier lado por este tema de
interoperabilidad, asi que descarto el almacenar un string ahi, aun que podriamos tener una
referecnia cruzada en otro lado de sus definciiones." **Decisión del autor:** el tipo lo da la
columna Tipo: Alfanumérico como texto, Numérico como entero. Valor propuesto para D-18, pendiente
del equipo. **Correcciones del modelo:** Alfanumérico es texto, así que sí hay texto en todo el
anexo; los nombres son componentes interoperables en 1, 7, 11, 12, 15 y 23 (Alfanumérico, 200 y
3); en 19, 20, 21, 22 y 28 el anexo solo trae el código y el nombre es para mostrar. La
"referencia cruzada" que propone el autor es la tabla de la entidad (código y nombre); la atención
guarda solo el código. Interoperar es intercambiar el conjunto de datos con la estructura del
anexo por el mecanismo del artículo 10, no abrir la base en cualquier lado; la estructura interna
de la base es libre (**Hipótesis** razonada desde el título del anexo y el artículo 10). Fecha se
guarda como fecha u hora y se formatea al intercambiar. **Estado:** `vigente para el autor;
D-18 pendiente del equipo`

## 2026-10-18: Kit para pasar al tablero lo cerrado del bloque D

**Contexto:** Autor, textual: "antes de continuar, lo que sepamos y que llevemos pasemos al
execl". **Decisión:** se entrega `kit-pegado-bloque-D.xlsx` (en `/home/claude/ips-validacion/`),
que no se sube al tablero: se copia por rangos con las mismas coordenadas. Contiene: Validación
N23:R27, T23:T27 (Analizado), W23:X27 y AC23:AC27 (texto combinado de lo ya pegado más lo nuevo);
Decisiones A16:I19 (D-15 a D-18, D-18 con valor propuesto); hoja nueva "Ajustes por historia"
A1:H20 (19 filas de ajustes del bloque D, para no escribir en las celdas compartidas de Historias);
hoja opcional "Ajustes al prompt" A1:B8 (el punto 5 cambia: los ajustes van a la hoja nueva). No
incluye Por redactar (espera D-17) ni el resultado en Historias (se consolida al final).
Encabezados y filas cotejados contra la copia del tablero. **Sin confirmar:** si el autor pegó.
**Estado:** `vigente`

## 2026-10-18: Tablero editado directamente y ajustes al prompt en .md

**Contexto:** Autor, textual: "por suerte mis companeros aun no han tomado sus bloques, si queres
puedes editar este con los cambios, y darme nuevamente el el ajuste del prompt, y se los entrego
en el *.md, y lo referencias en el execel por si los otros modelos leen, y aun no han visto ello."
**Decisión:** se edita su copia (`tablero-validacion-ips1.xlsx`) y se entrega como
`tablero-validacion-ips.xlsx` en `/home/claude/ips-validacion/`. El kit de pegado queda superado.
Cambios: Validación filas 23 a 27 con N a R, T (Analizado), W, X y AC (AC conserva el texto del
autor y agrega lo nuevo); S23 = Juan Fernando (V-22 no estaba tomada); M27 y M28 de "Null" a "No
los enumera". **Limpiezas:** L2:L22 vaciadas (llenadas por arrastre antes de que nadie tomara esas
filas; cuatro tenían texto roto, "IBREd: hay aiferencias"); Decisiones tenía la columna A y E
arrastradas hasta la fila 1048576 (archivo de 5,5 MB): se borraron las filas 20 en adelante y la
validación y el formato condicional de E quedaron en E2:E50 (archivo de 78 KB). Decisiones 16 a
19 = D-15 a D-18, con el estilo de la fila 13. Retroalimentación, columna Nota: verificado contra
Trello en filas 2, 3, 7, 8, 9 y 10; "Sin verificar en Trello" en las demás. Leeme: fila 7 (apunta a
los ajustes al prompt), fila 15 (estado del bloque D y la limpieza de L), B19 ampliada, fila 37
(conteo de Ajustes por historia). Hojas nuevas: "Ajustes al prompt" (15 ajustes) y "Ajustes por
historia" (19 filas, validaciones de Bloque, Resultado y Responsable). Recalculado con LibreOffice:
289 fórmulas, 0 errores; validaciones y formato condicional comprobados después del recálculo.
`ajustes-prompt-v2.1.md` lleva el mismo texto de la hoja. **Estado:** `vigente`

## 2026-10-20: El autor pide inactivar en vez de eliminar y que el modelo llene el tablero entero

**Contexto:** Autor, textual: "supongo que no tiene sentido el tema de eliminar en un crud de estos
tipos, ya que seria mejor desactivar, no tiene sentido que un 'estandar' que es esta norma elimite,
si algo supersede es decir que desactiva lo anteior en base a lo nuevo.... creo que deberias ser
capaz a mano tu mismo sacar si los numerares del axene estan no llenos, te pondre en modo EXTRA para
que tengas tiempo de analizarlo, de saber si son entidades (es decir que si merecen su crud), o
abritubos de entidades... es mejor inicila desde 0, ninguno de los modelos de mis companeros son
capaces con las instrucciones, son muy densas para elaborar, asi que tu tratares de resolver todo el
execel actual.... supongo que podria incluzo pasarte la retro alimentacion crompleta de mi profesor
en trello, pero creo que en un ejercicio inucoo, mas cuando sabemos que es el anexo la ultima
palabra... sobre el resto de numerales hasta el 49, vere si veo en el trello de mis otros
companeros indicios de ellos... asi que procede analiaz y debate si hace falta para que tu mismo
llenes el excel.."
**Decisión:** el modelo llena el tablero desde
`tablero-validacion-ips.xlsx` (2026-10-18) y entrega `tablero-validacion-ips-20oct.xlsx`:
Validación V-01 a V-21, V-27 y V-28 (L, M, N a R, S, T, W, X, AA y AC; V-18 Bloqueado por D-05);
hoja nueva Entidades con totales por fórmula; Ajustes por historia reconstruida (87 filas, conserva
los textos del bloque D del 2026-10-18 salvo HU-032 y HU-033, que pasan a inactivación); Historias
R a T, U, V, Y, Z y AC de las 33 (S es un resumen y remite a Ajustes por historia, como pide B19 de
Leeme); Decisiones D-19, D-20 y D-21 y notas en D-05 y D-07; Leeme A15, B15, B16, B19, filas 38 y
39. Recalculado: 296 fórmulas, 0 errores. Verificado con `verificar_llenado.py`: 0 cambios fuera de
lo previsto, listas desplegables respetadas, 0 alertas.
**Inactivar:** registrada como D-20 con el apoyo del artículo 7 (catálogos "no podrán ser
modificados, reducidos o adicionados") y del artículo 10 (permanencia), y la revisión ampliada de
HU-033 ("Precisar inactivación/recarga, motivo/autor", sin verificar en Trello). En contra: el
numeral 2.3 escribe "Delete". **Fuera:** elementos 24 a 49, que el autor va a mirar en los
tableros de sus compañeros. **Estado:** `vigente`

## 2026-10-20: Revisión independiente del tablero llenado

**Contexto:** un agente que no participó en el análisis cotejó las 28 filas contra las hojas 13 a
18 (escaneos leídos a 110, 220 y 400 ppp), la hoja Entidades y 10 filas de Ajustes por historia
(semilla 7). **Resultado:** M, N y P correctas en las 28 filas; Entidades completa, 69 y 24
cuadran; las diferencias de V-01, V-11, V-19 y V-20 existen en la hoja. **Errores que encontró y
se corrigieron:** L decía "Sí" en 12 filas con diferencias (V-21 "Atención Inmediata"; V-09 y
V-10 "01: Si" sin tilde, comprobado en recorte; V-03, V-04, V-05, V-13, V-15, V-17, V-18, V-26 y
V-27 por paráfrasis de la referencia; V-26 y V-27 venían "Sí" desde el tablero del 18); X decía
"Numeral 2.3: más de 5 opciones" en 7 filas de 5 o menos (pasa a "no llega a más de 5
opciones"); el escenario nuevo de HU-004 para la fecha de nacimiento rechazaba la fecha sola
(corregido: la fecha sola es válida); AC de V-24 sin "orígen" (comprobado en recorte); AC de V-11
citaba solo la hoja 14; nombres de vía y causa distintos entre Validación y Entidades. **No se
cambió:** V-10 deja el prestador en V-18 contra el ajuste 2; queda declarado como excepción para
que el equipo decida. **Sin resolver:** tilde de "Indigena" (V-14). Recalculado: 296 fórmulas,
0 errores; `verificar_llenado.py` sin problemas. **Estado:** `vigente`

## 2026-10-20: Lenguaje simple y comentarios completos del docente

**Contexto:** Autor, textual: "1. recuerda que necesitamos lenguaje simple, asi que si una historia
necesia mucho, no lo define, solo dice lo del numerar. 2. Nadie toco el trabajo en excel o trello,
por eso vine donde ti, es decir a efectos practicos tu mismo llenaste todo... ... de hecho como
puedo exportar el tablero de trello si no me jeda hacerlo en... pero encotre mi manera,  ahi estan
todos los comentarios de mi profesor en las historias de usuario actuales." Adjuntó
`comentarios-profesor-por-HU.md` (copia en `entrada/`): 63 comentarios del docente, HU-001 a HU-030,
de la lista "Product backlog (HU) selectas", sacados de páginas HTML guardadas el 19 oct 2026.
Escritos la noche del 10 oct en hora de Colombia; Trello muestra la hora en UTC (11 oct).
**Decisión:** Ajustes por historia se rehízo en lenguaje simple (104 filas): la tarjeta lista los
datos por nombre y numeral y dice que cada uno se valida con su numeral; no copia formatos.
Cada comentario del docente quedó citado por al menos una fila (fuente "Docente: cN"; comprobado
63 de 63). Retroalimentación: 69 filas (2 generales, 63 de la exportación, 4 de HU-031 y HU-033 sin
verificar). Decisiones nuevas: D-22 (código CIE-10 de 3 caracteres no se asocia: el numeral 23 pide
4 y 4) y D-23 (búsqueda en catálogos). Notas del docente en D-02, D-04, D-08, D-20 y D-21; D-20 pasa
a fuente "Retroalimentación del docente". **Hallazgos del cruce:** las 6 filas que ya estaban en
Retroalimentación coinciden con la exportación, salvo la revisión ampliada de HU-007, que estaba
cortada (le faltaban los párrafos de mockup y cierre). HU-031 a HU-033 no están en la exportación.
El docente valora en HU-021 el bloqueo de valores en uso ("Bien bloquear uso y conservar rastro").
**Hipótesis sin verificar:** las categorías CIE-10 de 3 caracteres sin subdivisión se escriben con
X en Colombia (I10X); el cargo de D-02 podría ser el profesional de estadística o el líder de
sistemas de información. **Estado:** `vigente`

## 2026-10-20: Los 49 numerales y el tablero de los compañeros

**Contexto:** Autor, textual: "no hay nada que cite los 49 numerales en total?, por que todo lo que
hemos decho es valorar los cambios para que todas nuestras historias tengan sentido con el anexo,
saber que tenemos bien y que no, que se modifica y que se queda.... te paso el tablero de mis
companeros para que veas si de casualidad toman algunode 24 al 49? y de hacerlo ver que falta?"
Después adjuntó `hu-companeros-sin-equivalente.md` ("metranscripcion"; copia en `entrada/`): 20 de
las 30 historias del tablero "Trabajo equipo", textuales, y la lista de las 10 con equivalente en
este backlog. Luego, textual: "para evitar que se pierdan las transcripciones que haras, deberias
almacenarlas en un .md asi como te habia entregado el numeral 1 al 23".
**Decisión:** dos lecturas independientes de las hojas 18 a 25 (agentes sin acceso entre sí); 65
filas y 99 valores de lista coincidieron sin ninguna diferencia. Resultado en
`anexo-res866-elementos-24-49-referencia.md` (textual, con 23 lecturas dudosas y 13 observaciones
de la hoja), lecturas en bruto en `transcripciones/`. Hoja nueva "Anexo 1 a 49" en el tablero: 77
filas (28 del 1 al 23 desde Validación; 49 del 24 al 49 con clasificación preliminar sujeta a D-16)
y conteos por fórmula. Nota de D-16 con la aritmética. Comparación en
`comparacion-companeros-anexo.md`.
**Hallazgos:** los compañeros toman 24 y 25 (HU21), 37 (HU28), 38 (HU17), 41 a 44 (HU22), 47
(HU20) y 48 (HU29). Nadie toma 26 a 36, 39, 40, 45, 46 ni 49. Errores suyos: 44 finalidades por 34;
49 causas por 29; 13 tipos de documento por 14; tres telemedicinas por cuatro; fecha de cierre
automática contra "En caso de muerte fecha y hora del deceso"; "Sin alergias conocidas" no es valor
del 47; condición de egreso exigida en toda atención contra "a la salida de la atención de urgencia
o de hospitalización". No tienen oposición ni voluntad anticipada (numeral 2.1). Del 24 al 49 salen
6 listas de más de 5 y entre 9 y 17 catálogos externos: entre 60 y 92 historias más con una por
operación. **Hipótesis:** el tablero "Trabajo equipo" es del semestre 2026-1 (tarjetas de abril y
mayo de 2026 en la captura del 18). **Estado:** `vigente`

## 2026-10-20: Recapitulación del autor y plan de reescritura

**Contexto:** Autor, textual (extractos): "Ya sabes que tenemos calificacion, los del otro grupo fue
el semestre pasado..."; "2. la confirmacion de que los numerares 24 al 49 son necesario, por que
corrir lo que no tenia similutud con nuestras historias de usuario actuales."; "mejor tratar de
definir las 4 acciones en una sola historia de usuario, quizas como hizo el otro grupo, por que ya
tenemos demaciadas historias de usuario. por que que tratemos de comprimir a 37 historias o menos,
se que puedes hacerlo, incluzo a 30, imagino que el profesor fue experto en darnos ese numero exacto
por algun motivo."; "tambien pienso que si dijo 5 o mas es entidad, es tambien un posible indicio";
"con todo lo que tenemos parece que evefcitamente habra que reescribir la mayoria... ya ves por que
ela buena idea comenzar desde 0"; "asi que inicialmente plantea un plan estarategico, cualquiero
cosa me dices, y te doy luz verde para que comienzes con la primera fase."
**Decisión:** se escribe `PLAN-BACKLOG-V3.md` (documento de seguimiento, aparte de esta memoria).
Propuesta: 31 historias para 1 a 23; una por catálogo; operaciones separadas solo en oposición y
voluntad (2.1) y CIE-10 (2.2), con el criterio del Prompt 2 ("No dividas historias únicamente por:
[...] Operaciones de base de datos"; separar por actores o estados distintos). Se conservan los ID
vivos; nuevas desde HU-034; 11 tarjetas se archivan con nota. Cinco fases y una contingencia.
**Correcciones al autor:** (1) la comparación no confirma que 24 a 49 sean necesarios: el otro grupo
es de 2026-1, y ninguno de los 63 comentarios del docente menciona tecnologías, egreso, alergias ni
factores de riesgo; (2) "mínimo 30" es un piso (guía 2.5), no una cifra exacta; con 1 a 23 todo
comprimido el backlog queda en 22 a 24, por debajo del piso; (3) "más de 5", no "5 o más" (guía 2.3,
revisión General); (4) reescribir: 13 o 14 historias desde cero o fusionadas, 17 editadas. La
estimación la hace el equipo en clase o con video (guía 2.5). **Estado:** `propuesta`, esperando
luz verde.

## 2026-10-20: El autor duda del corte en 23 por el otro grupo; plan B

**Contexto:** Autor, textual: "hmm... pero los companeros en total tambien tenian 30 historias, y
realmente el conteo del 1-23 aun puede ser un  farol, por que ellos efectiwanement tienen
aparentemnet del 24 en adelente... preferira pensar que ellos comprimieron acuadamente estos, pero
si quieres te puedo pasar en raw sus historias, pero no ayudaria de mucho la verdad, ya tienes
transcipcion de los puntos 24 en adelante.... que dices"
**Decisión:** `PLAN-BACKLOG-V3.md` pasa a versión 2 con un plan B: los 49 elementos en 33 a 37
historias (estructuras del 2.1 y CIE-10 en una historia cada una; catálogos externos del 24 al 49
agrupados por familia). Lo respondido: el conteo de 31 no es farol, es aritmética dado el alcance;
lo incierto es el alcance. El otro grupo no comprimió, omitió (26 a 36, 39, 40, 45, 46, 49, cinco
listas de más de 5, oposición y voluntad), y 6 de sus 30 historias no salen del anexo. Sus
historias en bruto no hacen falta; lo que serviría es su nota y los comentarios del docente en sus
tarjetas, si es el mismo docente. Las tandas de acceso, paciente y atención son iguales en los dos
planes. **Estado:** `propuesta`

## 2026-10-20: Luz verde; fases 0 y 1, y tanda 1 en borrador

**Contexto:** Autor, textual: "desafortunadamente no tienen comentarios, o alguien los borro, y
sacaron 4.0, es decir 1 punto mas que nosotros. procede, por que realmente hay algo que me preocupa
y es luego la cantidada de entidades y sus relaciones que saldran, pero imagino que para eso primero
solucionamos las historias de usuarios, comienza, cualquie tema con los planes me dices, si ves algo
que no se pueda comprimiro o no se deberia comprir, si el conteo para llegar o sub 37 es complejo, o
si es posibel llegal a 30.... inicial por donde tengas planeado..."
**Decisión:** fase 0: valores propuestos en D-02, D-04, D-06 (15 minutos), D-07, D-17 y D-19;
`preguntas-docente.md`. Fase 1: `backlog-v3/backlog-v3-tanda-0-epica-y-reglas.md` (épica, matriz
de permisos con el médico consultando al paciente sin cambiarlo, RC-1 a RC-7, plantilla del Prompt
2 con mockup y trazabilidad) y hoja Backlog v3 en el tablero (`scripts/agregar_backlog_v3.py`,
después de `agregar_anexo_49.py`); 0 ajustes y 0 comentarios sin historia destino. Fase 2:
`backlog-v3/backlog-v3-tanda-1-acceso-y-paciente.md` con HU-001 (fusión, 12 escenarios), HU-004
(14, tabla de datos por numeral con obligatorios propuestos), HU-005 (11) y HU-006 (13, actualizar
frente a corregir). En el plan: adelanto del modelo de datos, unas 21 tablas con el plan A.
Hallazgos en la guía: 2.7 evalúa "la correcta modelación de datos"; 3.6 pide el diagrama E-R; 3.2
dice que en Teams hay archivos Excel complementarios. **Estado:** `vigente`

## 2026-10-20: Revisión independiente de las tandas 0 y 1; la carga vuelve

**Contexto:** un agente revisor cotejó las tandas 0 y 1 contra el anexo, la guía, los 13
comentarios del docente de HU-001 a HU-006, el Prompt 2 y 24 filas de Ajustes. Resultado: tabla de
HU-004 completa y fiel; 22 de 24 filas resueltas; todos los comentarios resueltos salvo el mockup.
**Error mío que encontró:** la tanda 0 decía que el docente dejaba la carga masiva fuera de alcance
(HU-022 c1: "No es una funcionalidad, no explica como se deben cargar los valores"). Los comentarios
c3 y c4 de HU-022 y c2 de HU-018 ("Carga de países funcional") dicen lo contrario: piden precisar el
formato de importación. **Decisión:** la carga desde el archivo oficial vuelve a las historias de
catálogos; se corrigieron la tanda 0, los ajustes de HU-018, HU-022, HU-026 y HU-030, la nota de
D-21, las observaciones de V-01, V-08, V-13 y V-26, y el plan (HU-030 pasa de reescribir a editar:
18 editadas, 4 fusionadas, 9 nuevas). Otras correcciones: épica con dependencias y restricciones;
RC-3 y RC-6 con el texto de los artículos 6, 7, 8, 10 y 12, cotejado en el normograma de la
Supersalud el 2026-10-20; RC-7 con valores permitidos; consulta del paciente sin los numerales 8 y
9; escenarios de valores inactivados; continuidad de sesión recuperada; supuestos de documentos
sin identificación (AS, MS, SI) y de usuarios creados al instalar; trazabilidad con números de
escenario antiguos y nuevos; glosario; hoja Reglas comunes con RC-1 a RC-7. Pendiente para la tanda
2: el numeral 7 es la ocupación "al momento de la atención". **Estado:** `vigente`

## 2026-10-20: Tandas 2 a 5, decisiones propuestas, mockups, modelo E-R y kit de Trello

**Contexto:** Autor, textual: "continua, creo que tienes la suficiente informacion para hacer las
proximas fases y decidir." **Decisión:** se ejecutó el resto del plan v3 y se propusieron valores
para todas las decisiones abiertas (hoja Decisiones, marcadas como propuesta; D-24 nueva).
- Tanda 2 (HU-007 a HU-009), v2 con la revisión independiente (`revisiones/revision-tanda-2.md`):
  estado "Borrador" en vez de "En registro"; completar el borrador pasa a HU-007 y HU-009 queda solo
  para corregir; títulos de Trello originales ("Registro de información clínica", etc.); D-22 acotado
  al CIE-10; D-13 con la salvedad de que "Atención inmediata" incluye parto y prehospitalaria (manual
  de habilitación, por confirmar); D-24 (la atención guarda la ocupación del momento).
- Tanda 3 (HU-010 a HU-017), v2 con su revisión (`revisiones/revision-tanda-3.md`, 20 hallazgos):
  fecha de oposición obligatoria solo con Sí (inferencia por "mediante un documento escrito");
  retirar una oposición no exige documento (Ley 1805 no fija forma); corregir solo fecha o prestador,
  un Sí o No mal registrado se anula; revocación = "No" con evento revocación, se muestra "Revocado";
  evento de la fecha guardado; anular la vigente devuelve la anterior más reciente no anulada.
- Patrón corregido en las tandas 1, 2 y 3: precondiciones que negaban escenarios ("El paciente
  tiene registro" con escenario "Paciente que no existe"); datos del corrector junto al dato nuevo.
- Tanda 0 v3: RC-8 (carga CSV UTF-8 con punto y coma; versión; una versión nueva con filas
  rechazadas no se aplica); actor administrador de catálogos = área de sistemas de información o
  estadística (D-02, propuesta); glosario; matriz con corregir y reactivar.
- Tanda 4 (8 catálogos del paciente) y tanda 5 (CIE-10 en 4 historias + 4 catálogos), generadas con
  `scripts/generar_catalogos.py` (plantilla común) y `armar_tandas_catalogos.py`. CIE-10 en 4
  historias porque [2.2], actor distinto (médico en HU-031) y porque fusionarlo deja 29 (< 30).
- Fuentes nuevas verificadas en la web: Resolución 1888 de 2025 (RDA sobre la 866, no la deroga;
  remite a tablas de SISPRO; su anexo parece citar la CUOC para ocupación: PDF dañado, sin
  confirmar). Resolución 2625 de 2025 (17 dic 2025): listado de enfermedades huérfanas, deroga la 023
  de 2023, IDs consecutivos (art. 3) → D-14.
- Mockups: 31 láminas PNG en `mockups/png/` (generadores `scripts/mockups_*.py`,
  `generar_mockups.py`), cada panel con etiquetas "Esc. N".
- Modelo E-R: `modelo-datos/modelo-entidad-relacion.md` (24 tablas, Mermaid) y 2 PNG.
- Kit de Trello: `trello/` (31 tarjetas sin trazabilidad, respuestas al docente, notas de archivo).
**Estado:** `superada en parte` (la entrada siguiente cambia el CIE-10, el cargo, RC-8 y los conteos)

## 2026-10-20: Revisiones de las tandas 4 y 5; el CIE-10 en dos historias y HU-043

**Contexto:** llegaron las revisiones independientes (`revisiones/revision-tanda-4.md`: 6 hallazgos
altos, 10 medios, 10 bajos; `revisiones/revision-tanda-5.md`: 3 altos, 9 medios, 10 bajos). El autor
dijo "Continue from where you left off" y antes había dado vía libre para decidir. **Decisión:** se
aplicaron todos los altos y medios, y los bajos que no pedían más historias; todo queda como
propuesta. Mensaje al autor con los seis cambios de rumbo antes de aplicarlos.
- CIE-10 en dos historias: HU-032 y HU-033 se funden en HU-030 (plantilla común de catálogo, 28
  escenarios); HU-031 sigue aparte por actor. El argumento "fusionarlo deja 29" salió: el revisor
  mostró que dividía para llegar al número. Entra HU-043, anulación de una atención registrada por
  error (D-27), que era un pendiente de HU-009. Backlog: 30 historias, 541 escenarios.
- Cargo de los catálogos: profesional de estadística (D-02). Matriz, HU-001, tanda 3 y mockups.
- RC-8 se parte en RC-8 (carga: dirección y fecha de descarga, columnas por nombre, aviso de ceros
  perdidos), RC-9 (versión nueva: el código de una fila rechazada queda como estaba; inactivos que
  vuelven; inactivaciones confirmadas aparte) y RC-10 (motivos cerrados; "Creado por error" solo
  fuera de la versión vigente; reactivar solo con respaldo). RC-11: un registro conserva el nombre
  de su fecha (D-25). Las historias de catálogo enlazan las reglas comunes a una tarjeta propia.
- Jerarquías: el padre se lee del código (D-26): 2221 bajo 222; A000 bajo A00. Salen el cambio de
  padre y los ciclos. La estructura del archivo de SISPRO y de la CIUO-88 queda como supuesto.
- Listas del anexo: la primera carga y la comprobación se comparan con la hoja; manda la hoja.
- HU-037 ya no atribuye al numeral 13 un nombre que la hoja 15 no trae (cotejado en el PDF).
  HU-038 copia la hoja 16 ("donde se realizó la prestación del servicio de salud", cotejado); el
  paciente particular pasa a HU-004 (escenario 16).
- HU-004 y HU-007 pierden la precondición de catálogos con valores y ganan el escenario de catálogo
  sin valores (mismo defecto que el docente señaló en HU-008). HU-042 sin cambio de código
  (artículo 3 de la Resolución 2625).
- Verificado en fuente primaria (texto extraído por los revisores y cotejado aquí): artículo 8 de
  la 866 (SISPRO); anexo de la 1888 (Diario Oficial 53.244): ocupación en CUOC 2024 y tabla
  PSRGOCUPACION, comunidad étnica en texto libre, tablas Pais, Municipio, Etnia,
  CategoriaDiscapacidad, CIE10, ViaIngresoUsuario y RIPSCausaExterna, frase de la transición al
  CIE-11 "ha sido ampliado".
- Tablero: D-25, D-26 y D-27 nuevas; D-02, D-03, D-09, D-14, D-17 y D-21 con su propuesta nueva;
  RC-1 a RC-11; hoja Backlog v3 con 30 historias y 13 archivadas. 376 fórmulas, 0 errores, 0
  problemas en `verificar_llenado.py` (entrada: `tablero-antes-20oct.xlsx` del scratchpad).
- Mockups: 30 láminas (HU-043 nueva; HU-032 y HU-033 retiradas); cada escenario de las 30 historias
  aparece en su lámina. Modelo E-R versión 2 (24 tablas). Kit de Trello versión 2, con la tarjeta de
  reglas comunes. Preguntas al docente: 13.
**Estado:** `superada en parte` (la entrada siguiente cambia RC-8 a RC-10, los conteos y el kit)

## 2026-10-20: Revisión final cruzada aplicada

**Contexto:** un revisor independiente leyó todo el backlog v3 y los entregables
(`revisiones/revision-final-cruzada.md`): "Necesita cambios", con 2 hallazgos altos, 11 medios y 17
bajos, todos acotados. El autor había dicho "Continue from where you left off". **Decisión:** se
aplicaron todos; lo que quedó en parte está en `revisiones/aplicacion-revisiones-tandas-4-y-5.md`.
- RC-8: en ocupaciones y CIE-10, las filas de los niveles de arriba se validan con la regla de su
  historia, no con la longitud del numeral (A1). HU-022 gana un escenario: la primera carga debe
  traer las cantidades por nivel del DANE (10, 34, 117 y 401).
- RC-9: un archivo es versión nueva (fecha posterior), recarga de la vigente (misma fecha: completa
  una primera carga sin inactivar nada) o rechazo (fecha anterior o futura). Motivos "Vuelve en la
  versión", "No está en la versión" y "Reemplazado por"; la versión nueva queda vigente (M5, B9).
- RC-10 en lista: un código "está en la versión vigente" si vino en su archivo, aunque su fila se
  haya rechazado; lo inactivado con una referencia oficial se reactiva solo con otra referencia; el
  conteo de uso incluye borradores y anuladas (A2).
- Artículo 10 citado por lo que dice: disponer los datos de forma permanente; el parágrafo 2 agrega
  el dato nuevo al corregir y limita el tratamiento a "inconsistencias demostradas". HU-043 cambia
  "Otro" por "Otra inconsistencia", con explicación (M1).
- RC-11 con escenario donde se ve: HU-005 esc. 13 y HU-008 esc. 15 (M2). HU-004 pierde el esc. 16 y
  el servicio particular queda como regla (M3). HU-043 pierde el escenario repetido con HU-008
  (B10). HU-007 esc. 18 se parte en 18 y 19 (B11). HU-009 con la precondición "La atención existe
  (HU-007)" (M6). Con el criterio de B11 ("Cuando [acción realizada por el usuario]", Prompt 2) se
  reescribieron además 44 Cuando impersonales o del sistema en HU-001, HU-005, HU-006 y la tanda 3.
- Comunidad étnica: se pide solo si el catálogo de HU-037 tiene valores (M11). Identidad de género:
  sigue obligatoria y pasa a pregunta 14, con la cita de la 1888 (B16).
- HU-030: trazabilidad corregida (HU-032 no tiene retroalimentación del docente: su fila de ajustes
  es propuesta del modelo; el bloqueo de valores en uso que el docente valoró se cambió por
  inactivar con motivo, y ahora se dice); categorías del plan B como fuente aparte (M7, M8, M10).
  HU-031 con RC-1 (B12).
- Kit de Trello versión 3: el comentario de cada tarjeta dice dónde quedó cada escenario antiguo y
  las notas de archivo dicen dónde quedó cada escenario de la tarjeta archivada (M9). Las imágenes
  van dentro del kit (`trello/mockups/`). HU-001 tiene ahora la correspondencia completa de HU-001
  a HU-003.
- Conteos: 30 historias y 543 escenarios (T1 55, T2 57, T3 100, T4 196, T5 135); 13 archivadas; 14
  preguntas al docente. Tablero: 376 fórmulas, 0 errores, 0 problemas; D-19 con la condición de la
  comunidad étnica y la cita de la 1888. Mockups regenerados: cada escenario tiene su etiqueta o su
  nota en la lámina.
- Fuente nueva verificada: DANE, CIUO-88 A.C. (2006): "integrada en 4 niveles de agregación,
  identificados mediante códigos numéricos de 1, 2, 3 y 4 dígitos"; 10, 34, 117 y 401; gran grupo 0
  (Fuerza Pública). Confirma D-26 en ocupaciones; en el CIE-10 sigue como inferencia.
- Verificación independiente de lo aplicado (`revisiones/verificacion-aplicacion-final.md`): "lista
  con ajustes menores"; 29 de 30 hallazgos corregidos, A2 en parte y 16 puntos nuevos o residuales
  (N1 a N16), todos aplicados. Los de más peso: HU-022 cuenta las filas leídas por nivel (y el
  mockup ya no contradice la regla); RC-10 no deja reactivar sin otra referencia un código creado
  por cambio oficial que una versión posterior retiró; el plan B suma 35 a 39 historias, no 32 a 36
  (también en la pregunta 1 al docente).
- Límite de Trello: la descripción admite 16.384 caracteres (comunidad de Trello y un desarrollador
  que lo encontró en la API; no en la documentación oficial consultada). HU-022 llegó a 16.558; el
  supuesto del archivo en Excel pasó de las 13 historias de catálogo a RC-8 y HU-022 quedó en
  15.879.
**Estado:** `vigente`

## 2026-10-20: Entrega, auditoría del chat y descarga verificada

**Contexto:** el autor sospechó que algo "se jodió" por las compactaciones o por dar clic varias
veces (reintentar, continuar) mientras un agente revisor trabajaba más de una hora sin mostrar
nada; su mensaje "continua, creo que tienes la suficiente informacion..." reapareció con un error
de conexión. Preguntó si el chat tenía ramas.
- Lo que el modelo puede ver y lo que no: el historial de claude.ai muestra solo la rama activa (72
  turnos, 32 del autor); no muestra ramas, reintentos ni el registro de fallos de la plataforma. El
  registro local de la sesión solo guarda desde el 2026-10-20 06:19; lo anterior quedó en los
  resúmenes de compactación.
- Auditoría de los 72 turnos (agente aparte): sin mensajes repetidos ni señales de un mensaje
  editado. Turnos vacíos: 5 (paso del chat web al espacio de trabajo), 30 (solo adjunto), 46 y 68
  (del asistente, en esperas de agentes). Hilo suelto: el autor cerró el turno 36 (numeral 23) con
  "pero discrepo"; el modelo preguntó si era contra tratar el nombre del diagnóstico como texto
  libre y no hubo respuesta.
- Error del modelo: no avisó cuánto tardaría cada agente ni cómo saber que seguía corriendo; por
  eso pareció que todo se había caído.
- Revisión de completitud de las 30 tarjetas: 14 campos con contenido en cada una, 543 escenarios
  con Dado, Cuando y Entonces, mínimo 10 por historia, 30 imágenes, referencias válidas. 26 de 30
  en prioridad Alta: la prioridad casi no ordena.
- El autor descargó todo en su equipo (`/media/ct/DATOS/Untitled Folder`): los 86 archivos
  coinciden en tamaño con la versión final. Nada se subió a un repositorio.
- Aclarado: son 30 historias aunque los ID lleguen a HU-043; los 13 números sin archivo son las
  tarjetas que se archivan.
**Estado:** `vigente`

## 2026-10-21: Límite de 12 historias; dependencias y el paciente

**Contexto:** el docente cambió las reglas del desarrollo. Autor, textual: "3 historias de usario
por 4 integrantes en los 2 meses que quedan de semestre"; el docente "no aceptara que desarrolemos
una hustorias de usuario que dependa directamente de otra que no esta desarrollada, por mucho que
sea una historia de usuario de aporte valor al cliente"; no se vale poner el país como texto y
crear el catálogo en otro sprint; la estimación de complejidad va de 1 a 10 y "aumenta por la
cantidad de dependencias direcctas que tiene cada HU, independientemnet de su prioridad"; según el
profesor el paciente tiene 16 atributos. Hoy, 2026-10-21, el autor agregó: "el profesor nos ha
dicho que el login si bien en necesario tecnicamente, en la primera entrega no es algo que aporte
valor... entonces por lo que dice las depencias cambian en cada spring".
- Los 30 son el backlog del producto; se desarrollan 12 en el semestre. El docente quiere ver la
  lógica del orden.
- Los 16 atributos (hipótesis del modelo, cuadra exacto): apellidos y nombres como dos datos, fecha
  y hora como uno. 7 son valores del paciente (número de documento, apellidos, nombres, fecha y
  hora, sexo, identidad de género, zona) y 9 son relaciones con catálogos; nacionalidad y
  discapacidad son de muchos a muchos (tabla intermedia); país de nacionalidad y de residencia usan
  el mismo catálogo (HU-018). La oposición (numeral 8) y la voluntad anticipada (9) son estructuras
  aparte que dependen del paciente.
- Cadena para registrar un paciente: login, 8 catálogos (HU-018, HU-034, HU-022, HU-035, HU-026,
  HU-036, HU-037, HU-038) y HU-004 = 10 historias. Con la comunidad étnica como texto (anexo de la
  Res. 1888, 13.2) son 9, pero el backlog baja a 29 y hace falta otra (candidata: catálogo de
  prestadores, D-05, numerales 9 y 16).
- Error del modelo encontrado: HU-037 dice depender de HU-036 (etnias), pero su propio texto dice
  que la hoja 15 no liga comunidades con etnias; la regla etnia 01 a 06 es de HU-004. La
  dependencia sobra. Sin corregir todavía.
- Círculos encontrados (escenarios que miran la pantalla de otra historia): HU-006 y HU-008;
  HU-010 a HU-013; HU-014 a HU-017. Sin corregir todavía.
- La atención (HU-007) no cabe en 12: necesita el paciente y 6 historias más (HU-039 a HU-042,
  HU-030, HU-031). Los catálogos de la atención no dependen del paciente, pero no le sirven a la
  IPS este semestre; el único con argumento es el CIE-10 (la guía lo nombra en 2.2).
- La voluntad anticipada trae un componente que puede pedir catálogo propio: "Código del prestador
  donde se encuentra el documento" (12 dígitos). La oposición no.
- El login: la guía lo exige (2.4) y el docente pidió en HU-025, HU-026, HU-028 y HU-030 un
  "escenario de intento de eliminación por rol autenticado sin permisos: ausencia de sesión no
  cubre autorización". Lo que dijo después (no aporta valor en la primera entrega) choca con ese
  pedido mientras el escenario siga en cada historia de catálogo. Propuesta del modelo: invertir la
  dependencia, con los permisos y la atribución como escenarios de HU-001 que se aplican a lo que ya
  exista, para que los catálogos y el paciente no dependan del login; HU-006 sí depende, porque el
  parágrafo 2 del artículo 10 exige identificar a quien corrige.
- Plan propuesto por el autor: 3 integrantes para los atributos del paciente y sus catálogos, el
  cuarto con una tarea aparte (login y oposición). Pendiente de que el autor confirme el enfoque.
- Pregunta para el docente: "desarrollada" ¿quiere decir terminada antes de empezar la otra, o
  antes de entregarla? Con la primera, el plan va en tandas y el cuarto integrante espera.
- D-12 (serie 1, 2, 3, 5, 8, 13) queda vieja: el docente usa una escala de 1 a 10.
- Aplicado el 2026-10-21, a pedido del autor ("realiza los cambios minomso que propones y dame el
  plan de las 9 historias que necesitasmos hacer si o si, y los dos planes para las 12 del side
  quest"): HU-037 sin HU-036; las 13 historias de catálogo y HU-004 sin depender del inicio de
  sesión (el escenario "Cargo sin permiso" de los catálogos pasa a HU-001 escenario 14; el de
  HU-004 sale, cubierto por HU-001 escenarios 6 y 8; RC-5: el autor lo agrega HU-001 escenario 9 y
  lo registrado antes queda sin autor); oposición en orden registrar, consultar, actualizar y anular,
  sin remisiones hacia adelante (HU-011 rehecha, sigue con 10); HU-006 sin su escenario 14 (lo
  prueba HU-008 escenario 9). Voluntad anticipada y atención conservan remisiones hacia adelante.
  Conteo: 30 historias y 528 escenarios. Mockups, kit y tablero regenerados.
- Plan en `plan-desarrollo-12-historias.md`: 9 obligatorias (los 8 catálogos del paciente y
  HU-004), repartidas en 3 integrantes con 65, 69 y 68 escenarios. Cuarto integrante: plan A
  (HU-001, HU-010, HU-011: inicio de sesión y oposición) o plan B (HU-001, HU-005, HU-006: paciente
  completo). Pendiente que el autor elija.
- 2026-10-22, criterio del autor, textual: "si se puede almacenar uno, se deberia poder cambiar,, 4
  va con 5 y 6, el resto de catalogos dice mantenimiento, por lo que son su crud auto completo, el
  unico que esta separado es el de paciente". Con eso suma 11 (los 8 catálogos, HU-004, HU-005 y
  HU-006) y deja 1 cupo. El modelo señaló que ese cupo no es libre: HU-005 y HU-006 dependen de
  HU-001, y HU-006 con razón propia (el docente pidió en HU-006 c2 el nombre y la identificación
  de quien corrige; artículo 10, parágrafo 2). Resultado: es el plan B; la oposición y la voluntad
  anticipada no se desarrollan este semestre.
**Estado:** `vigente`

## 2026-10-22: Correcciones mínimas a las 12 del plan B y siembra del repositorio

**Contexto:** el tablero de Trello (capturas del autor) pone en To do las 12 historias del plan B:
HU-022, HU-036, HU-018, HU-026, HU-038, HU-034, HU-035, HU-037, HU-004, HU-005, HU-006 y HU-001.
Autor, textual: "Contextualiza el Proyecto, y dime si estas doces Historias de usuario no tienen
dependencias entre ellas... Paciente tiene 16 Atributos, de los cuales algunos dependen de unos
catalogos; sin embargo de los 16 atributos para que un atributo sea considerado catalogo o una
entidad en si misma, necesita 5 o mas composiciones, o mas de 5 valores." Y: "quiero que hagas
inicialmente las correciones minimas a las historias de usuario que usaremos, y cuando lo hagas,
puebla el proyexto de github con toda la informacion contextual para que sea autosuficiente en la
plantilla." El autor pegó la respuesta anterior del modelo: las cuatro operaciones del CRUD siguen
en las 13 historias de catálogo y faltan tres detalles de borde; `auditoria-crud-catalogos.md` no
vino en el zip. **Sin origen recuperable:** su tabla de escenarios por operación; se rehízo aquí.
**Decisión:** se aplicó lo propuesto, solo en las 12. Los 8 catálogos dicen qué escenarios son cada
operación y dejan de mirar a HU-004 y HU-005 (primera carga, cambio de nombre, catálogo vacío);
HU-022 escenario 12 y HU-026 escenarios 10 y 11 validan la creación a mano; HU-004 gana el
escenario 15 (buscar y elegir un valor) y sus escenarios 1 y 12 dejan de remitir a HU-005; HU-001
prueba solo las opciones que existan. 30 historias y 529 escenarios. Detalle en
`plan-desarrollo-12-historias.md`, sección 8. HU-030 y HU-039 a HU-042 quedan sin estos cambios.
**Respuesta sobre dependencias:** las 12 tienen dependencias, pero todas dentro del grupo: los 8
catálogos y HU-001 no dependen de nada; HU-004 depende de los 8; HU-005 y HU-006, de HU-001 y
HU-004. **Corrección al autor, repetida:** "más de 5" valores, no "5 o más" ni composiciones.
**Repositorio:** este material pasa a `docs/context/` del repositorio del proyecto y la plantilla
1.2 se siembra con él. Desde ahí, los documentos de `docs/` del repositorio son la referencia y este
archivo queda como historia. **Sin regenerar:** la lámina de HU-004 (le falta el escenario 15) y la
hoja Backlog v3 del tablero; los generadores no vinieron en el zip. **Estado:** `vigente`

## 2026-10-22: Revisión de la siembra hecha en otra sesión

**Contexto:** el autor subió `pr-siembra.md`, `INSTRUCCIONES-subir-con-gitbash.md`,
`cambios-historias.zip` y `siembra-archivos.zip`, hechos en otra sesión a partir de lo entregado
aquí. Autor, textual: "inicialmente pense que por la compresion los encunciados de las HU no dejaban
claram ente implicicto que los catalogos son crud o 4 HU juntan en agregar, editar, desactviar,
leer, pero ya veras".
- Comprobado: el material de partida es igual al de este espacio salvo los 18 archivos que cambió
  la otra sesión. En un clon del repositorio (público; `main` sigue en d70a6f8), los pasos de las
  instrucciones dan 100 cambios (85 A, 12 M, 3 D), 0 llaves dobles y la tabla del PR archivo por
  archivo.
- Las 12 historias: ningún escenario remite fuera del grupo ni a una historia que depende de la
  suya; HU-005 escenario 13 prueba lo que los catálogos dejaron de mirar; 529 escenarios.
  Observación del modelo: la línea "CRUD por escenario" pone la versión nueva solo en crear, y ese
  escenario crea, actualiza, reactiva e inactiva.
- Señalado al autor: el repositorio es público y `MEMORIA-CONTEXTUAL.md` sube completa (nombres del
  equipo, citas textuales sobre el docente y los modelos de los compañeros, ruta local). Con el zip
  todo queda en un commit, y `pr-siembra.md` y `docs/context/README.md` describen tres.
- Tarjeta HU-022: 15.623 caracteres el 2026-10-21 (sin "Cargo sin permiso") y 15.688 con la línea
  CRUD. El 15.295 que cita la otra sesión no está en esta memoria.
- Los generadores de este espacio no tienen las correcciones del 2026-10-22: regenerar la tanda 4
  desde ellos las borraría. Desde la subida, la fuente es el repositorio. La copia del repositorio
  no trae la última línea de la entrada anterior (el cupo 12 es HU-001): el zip se armó antes.
**Estado:** `vigente`

## 2026-10-24: Copia local del autor y el título de los catálogos

**Contexto:** el autor aclaró que a la otra sesión le pidió recuperar lo perdido en la compresión y
poblar el repositorio, y que esa sesión decidió el cómo; para la subida solo usará los 4 elementos
de `Subir/`. Mandó el `ls -Ral` de su carpeta `POST`. Autor, textual:
- "aun no estoy convencido que literalmente se llamen mantenimiento, por que el modelo debio hacer
  arreglos para que con el encunciado de la descripcion quedara clado, por que creo que
  manentimiento es muy general"
- Comparación por tamaño: 89 de 93 archivos iguales a la versión del zip. Distintos:
  `MEMORIA-CONTEXTUAL.md`, `PLAN-BACKLOG-V3.md`, `LEEME-kit-trello.md` y
  `respuestas-al-docente.md`; los suyos tienen el tamaño de los de este espacio, y el zip trae los
  de la otra sesión.
- Corrección del modelo a sí mismo: el título "carga y mantenimiento" y el Quiero "cargar... y
  mantenerlo igual a la fuente" son suyos, del 2026-10-20, al fundir las cuatro tarjetas de cada
  catálogo; no los perdió la compresión. El prompt 2 pide un título "específico y orientado al
  resultado" (línea 211), y el docente revisó en la v2 cuatro tarjetas por catálogo (Carga,
  Consulta, Actualización, Eliminación).
- Propuesto: título y Quiero con las cuatro operaciones, la línea de operaciones con las mismas
  palabras y la versión nueva en las cuatro; en un segundo PR después de la siembra.
- Autor, textual: "a todas, asi solo vayamos a utilizar ciertas para el primire spring, y cuando
  termines, dame todo en forma estructurada asi como tengo yo, para sobreescribir los archivos que
  necesitan cambios y lo que le enviare a claude code". El modelo tomó la opción 1 (título y
  Quiero), la que había recomendado; el autor no eligió entre las tres.
- Aplicado a las 13 historias de catálogo: tanda 4 en versión 5 y tanda 5 en versión 4. Ningún
  escenario cambió: 30 historias y 529 escenarios. Las tarjetas salen del kit, que antes reproducía
  las 31 de la siembra byte a byte; cambian 13. Láminas: 13 de catálogo con el título nuevo y la de
  HU-004 con el escenario 15, el aviso del escenario 12 sin enlace a la consulta y la confirmación
  con el documento. Tablero regenerado con la cadena: frente al de la siembra cambian 18 celdas (13
  títulos en Backlog v3; D-02, D-04, D-17, D-20 y D-23 en Decisiones); 376 fórmulas, 0 errores,
  verificador sin problemas.
- Corrección a la otra sesión: la hoja Backlog v3 del tablero no cuenta escenarios, así que el
  pendiente de "actualizarla a 529" no tenía objeto.
- Prosa medida con los comandos de `CLAUDE.md`: las líneas base de la siembra se reproducen; con
  estos cambios quedan 190.068 palabras y la regla 8 en 120 de 862 (13,9 %); las demás, iguales.
- Entregado: un zip con la estructura de la carpeta `POST` del autor y, adentro, `Subir-2/` con el
  zip de rutas del repositorio y el handoff para Claude Code. Las entradas de `docs/` las escribe
  Claude Code. Esta memoria y `PLAN-BACKLOG-V3.md` no van en ese PR: el repositorio los trata como
  historia.
- Generadores: las versiones de láminas y tablero con estos cambios están en `/home/claude/scripts`.
  `generar_catalogos.py` y `armar_tandas_catalogos.py` no producen las correcciones del 2026-10-22
  ni el Quiero nuevo: la fuente de las tandas son los `.md` del repositorio.
**Estado:** `vigente`

## 2026-10-24: Claude Code siembra el fork; el PR #3 entra en Atervolgo y se revierte

**Contexto:** en una sesión de Claude Code en la nube, el autor entregó el handoff y
`enunciados-archivos.zip` de `Subir_Actual`, y después los 4 de `Subir_Old`. Las fechas van en
hora de Colombia, aunque los commits muestran UTC. Autor, textual:

- "hice un fork exacto del repo de Atervolgo,, por que claude code no tiene acceso ahi por algun
  motivo, en teoria lo que esta en Atervolgo ahora lo tengo yo, en teoria solo deberia tener una
  plantilla, no la siembra real de los documentos, mientras aparece Atervolgo para habilitar
  claude, trabajare en este repo, y buscare la manera de que el claude code de los demas tambien
  tengan los mismo permisos.."
- Fork: `jncldrn64/IPS-para-con-interoperabilidad-en-Resoluci-n-866`, con `main` en d70a6f8, igual
  al de Atervolgo. La siembra no estaba en ninguno de los dos.
- Siembra en la rama `claude/wizardly-newton-ztyb85`: d6169b8 con el zip y los 3 borrados (100
  cambios: 85 A, 12 M, 3 D; 0 llaves dobles; numstat igual a `pr-siembra.md`; líneas base
  reproducidas) y c8b9d9c, que corrige `docs/context/README.md` porque describía tres commits. Va
  en el PR #1 del fork, en borrador, contra el `main` del fork.
- Enunciados en f0ab79a, encima de la siembra: los 32 archivos y las secciones 3.1 a 3.5 del
  handoff. Comprobado: 529 escenarios sin cambio de texto, bloque por bloque en las 30 tarjetas;
  13 títulos nuevos y 0 "carga y mantenimiento"; 18 celdas y 376 fórmulas en el tablero, sin
  recalcular; lámina de HU-004 con "Esc. 15"; HU-022 con 15.894 caracteres. Prosa: ninguna línea
  base sube, y la regla 8 queda en 120 de 871 (13,8 %) sobre 190.552 palabras. Del borrador del
  handoff cambió solo la forma: la Decisión en dos párrafos (tenía 8 oraciones) y tres líneas
  llevadas a 100 columnas.
- Al preguntarle cómo publicar los enunciados, el autor respondió, textual:
- "Recuerda que los primeros dos archivos son las instrucciones reales, los 4 ultimos solo son de
  referencia, asi que si las nuevas instrucciones son son claras referentie a las viejas crea una
  rama test con tu commit, que entiendo que el PR me hace ami tambien hacerla."
- Se subió la rama `test` en f0ab79a, sin PR. La rama de la sesión,
  `claude/wonderful-galileo-12jxag`, quedó en el mismo commit.
- El autor abrió un PR desde `test` con el botón "Compare & pull request", que en un fork propone
  como destino el repositorio original. Quedó como Atervolgo#3 y se fusionó (2e2faa8). Autor,
  textual:
- "test hice merge, pero no no estoy seguro de el por que, como te he dicho, no estamos trabajando
  en el Atervolgo, no se por que la pull reques es para el repo padre, voy y vamos a trabajar en
  mi fork, por que ahi tengo los permisos para darle autorizacion al resto de colaboradores, en
  Atervolgo no tengo permisos de absolutamente nada, si quiera puedo hacer merge a sus
  pullrequest, me explico?"
- Comprobado: los merges de Atervolgo#3 y #4 son de la cuenta jncldrn64, y la lista de
  repositorios de la sesión daba permiso de escritura en Atervolgo. A Claude Code se le negó
  agregar Atervolgo con escritura en esta sesión. Autor, textual:
- "primero quiero revertir lo que quiera que hayamos hecho en Atervolgo, y quiero saber que hago
  con lo de la captura; trabajaremos en el test en mi fork, y cuando el dueno de Atervolgo
  aparezca ahi si hacemos un pull para llevar todo el trabajo mio y de los colaboradores que no
  pueden estar en el repo, hacia el original"
- El autor revirtió con el botón Revert: Atervolgo#4, con `main` en 69675e9 y el mismo árbol que
  d70a6f8 (comprobado con `git diff`). La captura mostraba el aviso "Compare & pull request" de la
  rama de la sesión: no se aprieta, y se va solo.
- d6169b8, c8b9d9c y f0ab79a quedaron en la historia de Atervolgo, con el revert encima. Un PR
  armado con esos mismos commits traería a Atervolgo solo lo nuevo, y antes habría que revertir
  el revert. Mientras tanto, no se usa "Sync fork" en el fork.
- `POST.zip` (92 archivos) frente a `test`: 86 iguales; distinta solo esta memoria, porque la del
  repositorio es la de la siembra, sin la entrada del 2026-10-22 "Revisión de la siembra hecha en
  otra sesión" ni la del 2026-10-24; fuera del repositorio, los 5 de trabajo (los dos antes y
  después, los dos LEEME de cambios y el patch); `trello/tandas/` repite tres tandas de la raíz.
  Autor, textual:
- "No se utilizar, forks, este en un nuevo mundo para mi, quiero saber que  el pull reques que
  esta en draft en mi repo es lo que realmente quiero, y ya hice el revert en el repo de
  Atervolgo.... por otro lado desde ahora usaras MEMORIA-CONTEXTUAL.md como tu propia memoria, e
  instrucciones."
- **Choca con el repositorio:** `docs/DECISIONS.md`, 2026-10-22 "El material del backlog entra en
  docs/context", deja esta memoria como historia y el estado del proyecto en `docs/`. Usarla otra
  vez como memoria cambia esa decisión, así que el PR que la suba lleva una entrada nueva.
- El autor aclaró qué es cada carpeta y pidió el primer PR completo. Autor, textual:
- "Si bien dichos archivos en parte son de post, habia creado dos carpetas, para la siembra, donde
  el Subir_old tiene instrucciones desfadas, y el Subir_actual tenia correciones, y explicaciones
  de nuevos archivos, y cambios que habia hecho, sobre todo a los encunciados de las 12 historias
  de usario que utilizaremos... cierra todo los PR, por que ahora mismo si bien tienes cambios,
  quiero asegurarme que el primer PR real que aceptare en mi fork, tendra todos los archivos en
  siembra necesarios con los cambios propiestos por los promts; y si bien siento que subir tal
  cual archivos de mi estructura post, me parece raro, lo permitire, puesto que pensaba que en la
  siembra todo estaria listado tal cual los archivos se listarian, no pense que tal cual subirian
  HU de forma contextual; yo hubiese como minimo en el roadmap especificar cada HU que debe ser
  atacado y en que orden... es mas tu memoria contextual actual en cierta forma ayuda a poblar el
  resto de aspectos de las demas documentaciones propuestas por las plantillas."
- Hecho: se cerró el PR #1 del fork, el único abierto. El primer PR real va contra el `main` del
  fork, con commits nuevos desde d70a6f8: siembra, enunciados, esta memoria como memoria del
  modelo y el orden de las 30 historias en `docs/ROADMAP.md`. Con commits nuevos, llevarlo a
  Atervolgo no pide revertir el revert, porque la base común sigue siendo d70a6f8.
- Respondido con eso: la memoria puede estar en el repositorio público, porque el autor permite
  subir su carpeta `POST` tal cual. Los 5 archivos de trabajo de `trello/` y la copia de las tandas
  en `trello/tandas/` no entran: su contenido ya está en el plan, el CHANGELOG y las tandas.
**Estado:** `vigente`

## 2026-10-24: El primer PR real se fusiona; el equipo pasa a 3

**Contexto:** el autor fusionó el PR #2 del fork, que dejó su `main` en d7f6284 con la siembra, los
enunciados, esta memoria y el orden de las 30 historias. Después escribió, textual:

- "ahora mismo, realmente no tenemos decidido quien hace que, pasamos de 4 integrantes a 3,
  entoces 4 historias de usuario sean hechas por cada integrante, asi que crea un plan estrategico
  que quien deberia tener que carga; si las ramas no las necesitas borraras, por que puede haber
  confucion, pero recuerda dejar donde deberia datos de esto, por que creo que sera muy
  importante. dicho esto, entiedo que tienes contexto del proyecto? por que quiero establecer
  reglas para que los modelos de los companeros sepan trabajar en lo mismo... quieres te que pase
  el pdf de el proyecto y tambien las transcripciones? o ya crees que tienes todo el contexto?....
  he hecho mis archivos son aun mas: te mostrare mi estructura al completo, realmente en POST es
  todo aquello que ya te he subido y explicado, por que supone el salto que aquello qque haremos,
  pero la carpeta PRE tiene ahi estan los previos a esa intervencios dicha en POST, sin envargo
  entiendo que las transcripcones junto al proyecto que clase que ensta en base te deberian dar
  una idea con lo que estamos lidianto..."
- Reparto propuesto por el modelo, en `docs/ROADMAP.md`, sección "Orden de las 30 historias":
  integrante 1 con HU-001, HU-022, HU-026 y HU-005 (80 escenarios); integrante 2 con HU-034,
  HU-018, HU-038 y HU-004 (82); integrante 3 con HU-036, HU-037, HU-035 y HU-006 (81). Son 5
  pasos, con HU-005 y HU-006 a la vez en el último. Qué persona es cada número no está decidido.
- Decisiones nuevas en `docs/DECISIONS.md`: 2026-10-24 "Se trabaja en el fork del autor hasta que
  Atervolgo lo habilite", con la tabla de las ramas borradas, y 2026-10-24 "Tres integrantes con
  cuatro historias cada uno". La fase 0 del ROADMAP queda cerrada.
- Ramas borradas del fork: `test` (f0ab79a) y `claude/wizardly-newton-ztyb85` (c8b9d9c). Sus
  commits siguen en la historia de Atervolgo, por su PR #3 y el revert de su PR #4. La rama de la
  sesión, `claude/wonderful-galileo-12jxag`, se queda para los PR de Claude Code.
- Sobre el contexto: el modelo tiene el backlog, las tandas, las tarjetas, los documentos de
  `docs/` y esta memoria. No tiene las fuentes que esta memoria pone arriba en su jerarquía (sección
  1): el PDF del anexo, la guía del docente "Proyecto de Clase", las transcripciones de los
  elementos 1 a 23 y 24 a 49, los comentarios del docente por historia y el Prompt 2 del docente.
  Se le pidieron al autor antes de escribir reglas para los modelos de los compañeros, junto con
  el `ls -Ral` de su carpeta `PRE`.
**Estado:** `vigente`

## 2026-10-24: La guía del docente y las transcripciones llegan a Claude Code

**Contexto:** el autor fusionó el PR #3 del fork (fa95eaf, el reparto en 3 integrantes) y subió
`Proyecto_de_Clase.pdf` y `transcripciones.zip`. Autor, textual:

- "realmente comenzemos por el proyecto, con el pdf del profesor y todas las trascripiciones que he
  decho de las normas"
- Recibido: la guía (4 páginas; `pdftotext` da el mismo texto que la copia del zip, 1.482
  palabras), las transcripciones del anexo de los elementos 1 a 23 y 24 a 49 con sus lecturas en
  bruto y 13 recortes, y los textos de las resoluciones 866 de 2021 (PDF escaneado de 25 páginas
  y su texto) y 1888 de 2025. El LEEME del zip nombra una carpeta `originales-subidos/`, con los
  prompts 1 y 2 del docente, que no vino en el zip.
- Cotejado con el texto de la 866: el artículo 7 ("no podrán ser modificados, reducidos o
  adicionados"), el parágrafo 2 del artículo 10 (el dato nuevo con fecha, hora, nombre e
  identificación de quien corrige) y el 13.2 (el contacto se asocia a tecnologías en salud) dicen
  lo que citan el backlog y `docs/REQUIREMENTS.md`.
- La guía trae cosas que ningún documento del repositorio recoge:
  - [2.5]: "Cada integrante del grupo debe desarrollar mínimo 4 Historias de Usuario para cada
    Sprint para generar el incremento correspondiente." El plan cuenta 12 historias en el
    semestre, por la regla que el docente dio de palabra el 2026-10-21. Con sprints de tres
    semanas [2.7] y unos dos meses de semestre, quedan dos o tres sprints. **Pregunta para el
    docente:** si las 12 reemplazan al mínimo de 4 por integrante en cada sprint.
  - [2.6]: los roles de Scrum, Product Owner, Scrum Master y Scrum Developers. Nadie los tiene
    asignados en el material.
  - [2.7]: el sprint dura tres semanas y arranca un martes con el Sprint Planning; el último jueves
    van el Sprint Review y la Retrospective. Sin ceremonia en clase, queda un video con todos.
    [3.4]: el Daily, el Review y la Retrospective se evalúan.
  - [3.7]: cada estudiante sustenta en cada Sprint Review las historias que desarrolló, así que el
    reparto decide qué defiende cada uno.
  - [3.6]: se entrega la URL del repositorio, el diagrama E-R en un adjunto o en el repositorio, y
    un correo con los nombres completos y la URL de las historias.
  - [3.1]: un integrante que no aporta no tiene que aparecer en las entregas.
**Estado:** `vigente`

## 2026-10-24: Lo mínimo para el cliente, y docs/context sobra

**Contexto:** el autor contestó las preguntas del modelo sobre la guía. Autor, textual:

- "Obviamente, el proyecto se ha refinado, por que hemos hecho muchas preguntas al profesor."
- "supongo que el documento del profesor puede ser en si mismo las fases del roadmap, sin
  embargo ya entraremos en detalles, detnalles que ya deberias saber"
- "realmente no, de hecho el hecho que en docs/context hayas subido todo los archivos, me deja
  mal, por que no se la capacidada de los modelos de los companeeros, entindo que el .xlsx ni es
  necesario, por que ya cumplio su funcion, igual que preguntas docuente, quizas tenga sentido
  loos backlogs, pero ya tenemos la carpeta de trello, que de hecho deberia solo listar las HU,
  por que sus instrucciones es para el propio tablero de prelo, los mockups los podriamso dejar,,
  hay mucho archivos que no se si realmente deberian estar en el repo, y si tiene informacion
  relevante, para eso hay otra documentacion"
- "ahora mismo ni importa, tecnicamente yo soy todo ahora mismo"
- "en realidad el tiempo de los promps tambien paso, la idea era generar las 30 historias de
  usuario, que al final se generacion de otra forma."
- "realmente la idea de este ejercico es cumplir con lo minimo, por que las metodologias agiles
  estas para presentar entregas de valor a los clientes, no llegar al final unicamente con
  documentacion, el profesor no espera que hagamoos todo a la primera, es mas nos dijo que por
  poder puede ser en terminal, de hecho luego de las 30 nos dijo que solo seleccionaramos 3 por
  integrante, y las eligieramos bien, por que la idea es en el primer spring presentar un minimo
  al cliente, y que no tiene sentido hacer la entrega de los 16 campos del paciente, si estos
  camppos dependian de otras entidades, que debiamos elegir bien, por que sin por ejemplo nos
  faltaba paises y pacientes tiene el campo de pais, estamos jodidos, por que es algo mal hecho,
  entonces pasamos por el proceso de saber cuales son esos 16 atributos directos de paciente y
  dependen de catalogos, y no son simplemente una  relacion con otra entidad, de ahi que
  llegaramos al plan de las 12 propuestas, que basicamente ese es nuestro plan 0 o fase 0 del
  proyecto,"
- Lo que queda decidido: la guía vale con lo que el docente fue respondiendo después, y esas
  respuestas están en esta memoria (2026-10-21). Las transcripciones y los textos de las
  resoluciones no entran al repositorio. Los roles de Scrum no se asignan todavía: el autor los
  lleva todos. Los prompts 1 y 2 ya no hacen falta. El docente acepta que el producto sea de
  terminal.
- Lo que el autor pide revisar: qué sale de `docs/context/`. Para él no hacen falta el tablero ni
  las preguntas al docente; `trello/` debería traer solo las historias, porque el resto son
  instrucciones para el tablero de Trello; las láminas se quedan; lo que valga de lo demás va a
  los documentos de `docs/`.
- Pendiente de detalle: que la guía y el plan de las 12, que el autor llama "plan 0 o fase 0 del
  proyecto", ordenen las fases del ROADMAP.
**Estado:** `vigente`

## 2026-10-24: docs/context se reduce, las D-n van a REQUIREMENTS y el esqueleto va en terminal

**Contexto:** el modelo propuso qué sacar de `docs/context/`, y el autor contestó, textual:

- "los comentarios del docente realmente pasaron por pracitamente todo los cambios que hemos
  hecho, podrias llevan en tu memoria ideas de si recueperamso eso o que tenemos, no?. por otro si
  en requerimentes.md supongo que tiene sentido, pero no habia un md only append o algo asi mas
  apto, no no tiene sentido?. y si en terminal, se que tenemos los mukpts, pero podemos al menos
  tener el esqueleuto en terminal, pero ya entraremos en las deseciones de arquitectura"
- Sobre el archivo append-only: `docs/DECISIONS.md` lo es, pero guarda lo que ya se decidió. Las
  D-n son propuestas que cambian de estado, así que van en `docs/REQUIREMENTS.md`, sección 6; cuando
  una se decide, entra su entrada en `docs/DECISIONS.md` y su fila cambia de estado.
- Hecho en el PR que sigue a fa95eaf: salen las seis tandas, el tablero, las preguntas al docente,
  el plan de desarrollo, el plan de la reescritura, las revisiones y las instrucciones del kit de
  Trello. Quedan las tarjetas, que pasan a ser la fuente, las láminas, el modelo E-R y esta memoria.
  Los 16 atributos del paciente y las 27 decisiones del equipo pasan a `docs/REQUIREMENTS.md`.
  Decisiones nuevas: 2026-10-24 "docs/context guarda solo lo que usan las historias" y 2026-10-24
  "El primer sprint arranca en terminal".
- **Comentarios del docente, qué hay y dónde.** Todo en el commit fa95eaf, con `git show
  fa95eaf:docs/context/<archivo>`:
  - Las tandas 1 a 5 traen, por historia, una sección de trazabilidad que liga escenarios y reglas
    con los comentarios del docente (c1, c2...) y con las filas de Ajustes por historia.
  - El tablero trae la hoja Retroalimentación (69 filas: el texto del docente como llegó y la
    historia destino en el backlog v3) y la hoja Ajustes por historia (104 filas: qué ajustar, con
    número de escenario y su fuente).
  - La exportación con los 63 comentarios, `comentarios-profesor-por-HU.md` (2026-10-19), quedó en
    el contenedor del chat y no está en el repositorio ni en el zip de transcripciones. Si el autor
    la tiene en `PRE`, es la fuente primaria.
- **Ideas para recuperarlo, sin hacer todavía; decide el autor:** una línea "Comentarios del
  docente" en cada tarjeta, sacada de la trazabilidad de su tanda; una tabla por historia en esta
  memoria; o dejarlo en la historia de git, si las tarjetas ya atienden todo y el docente evalúa lo
  que se entrega.
**Estado:** `vigente`

## 10. Correcciones registradas

- **Del modelo a sí mismo:** entró a la primera fila del bloque D sin mostrar antes el mapa de 49 a
  23 a 28 filas a 4 bloques. Dicho al autor el 2026-10-18.
- **Del modelo a sí mismo:** presentó el corte en 23 como "respaldado por cuatro textos del
  docente". Dos de esos textos (retro HU-004 y HU-007) no los verificó contra Trello, y ninguno dice
  23. El número viene de la transcripción y del prompt v2.1, escritos con un modelo. Dicho al autor
  el 2026-10-18.
- **Al autor:** umbral "5 o más" por "más de 5".
- **Al autor:** entidad del numeral 19, historia clínica por Atención.
- **Al autor:** su hipótesis de que la diferencia entre numeral y V viene de un contexto comprimido
  es falsa: viene de contar componentes.
- **Al autor:** HU no es igual a funcionalidad.
- **Del modelo a sí mismo:** dijo "se guarda como texto, no como número" como regla absoluta. El
  anexo no es uniforme: el elemento 48 tipa Numérico una lista con códigos 01 a 06. Lo preciso es
  que el valor de intercambio es "01" de dos posiciones y que el elemento 20 está tipado
  Alfanumérico.
- **Del modelo a sí mismo:** leyó "un prompt para el otro modelo" como una auditoría independiente.
  El autor quería un prompt para el modelo que redactó los documentos.
- **Del modelo a sí mismo (2026-10-20, antes de escribir el tablero):** en el borrador llamó al
  escenario 10 de HU-007 "atención sin terminar". Se llama "Catálogo CIE-10 no disponible" y
  conserva el resto de la atención para terminarla después; D-08 sale de ahí. Corregido.
- **Del modelo a sí mismo (2026-10-20, antes de escribir el tablero):** planeó copiar todos los
  ajustes en la columna S de Historias. Leeme B19 dice que los ajustes van en Ajustes por historia
  "para que dos bloques no escriban en la misma celda"; S quedó como resumen con remisión.
- **Del modelo a sí mismo (2026-10-20, lo encontró el revisor independiente):** llenó L sin un
  criterio escrito y marcó "Sí" en filas cuya propia observación describía una diferencia (V-03,
  V-17, V-21). Se fijó el criterio en Leeme B15 y se corrigieron 12 filas.
- **Del modelo a sí mismo (2026-10-20, lo encontró el revisor de la tanda 1):** tomó el comentario
  1 de HU-022 ("No es una funcionalidad...") como prueba de que la carga sobraba, sin pesar los
  comentarios 3 y 4 de la misma tarjeta ni el 2 de HU-018, que la dan por funcional. Se le había
  dicho al autor que los comentarios confirmaban quitar la carga masiva; eso era falso.

- **Del modelo a sí mismo (2026-10-20, revisor de la tanda 5):** dividió el CIE-10 en cuatro
  historias y lo justificó con el mínimo de 30. El catálogo de ocupaciones tiene niveles, versiones
  e inactivación en una sola historia, así que la división era por operación de base de datos.
- **Del modelo a sí mismo (2026-10-20, revisor de la tanda 4):** atribuyó al numeral 13 un nombre de
  comunidad étnica que la hoja 15 no trae; escribió que la hoja 16 pide la secretaría "del municipio
  de la IPS", cuando dice "donde se realizó la prestación del servicio de salud"; y dejó que una
  sola fila oficial fuera del numeral bloqueara toda versión nueva.
- **Del modelo a sí mismo (2026-10-20, revisores de las tandas 4 y 5):** la comprobación "contra el
  archivo oficial" comparaba el catálogo con el mismo archivo que se había cargado, así que no
  probaba nada. Ahora compara con un archivo recién descargado y lista también los inactivos.
- **Del modelo a sí mismo (2026-10-20):** dio por "PDF dañado, sin confirmar" la remisión de la
  Resolución 1888 a la CUOC; la copia del Diario Oficial 53.244 tiene texto legible y la remisión
  está en el elemento 7.1.
- **Del modelo a sí mismo (2026-10-20):** corrió el pipeline del tablero con la copia del 18 de
  octubre como entrada; la entrada correcta es `tablero-antes-20oct.xlsx`. La aserción de V-26 lo
  detuvo antes de escribir, y se volvió a correr con la entrada correcta.
- **Del modelo a sí mismo (2026-10-20, revisor final cruzado):** citó el artículo 10 de la 866 para
  "sus datos son permanentes", que el artículo no dice; atribuyó a "retroalimentación sin
  verificar" de HU-032 lo que el tablero marca "propuesta del modelo"; quitó sin decirlo el bloqueo
  de valores en uso que el docente había valorado; escribió "diez" por trece historias de catálogo,
  también en la tarjeta de reglas comunes y en las preguntas al docente; y registró "comunidad
  étnica en texto libre" como si lo dijera la 1888, que dice "Texto Alfanumérico".
- **Del modelo a sí mismo (2026-10-20, verificador independiente):** la tabla del plan B sumaba 35
  a 39 historias y el total decía 32 a 36, cifra que iba en la pregunta 1 al docente; la regla de
  cantidades por nivel de HU-022 no decía qué filas contaba y su mockup mostraba 520 filas con
  "Guardar las aceptadas" junto a "no se guarda".
- **Del modelo a sí mismo (2026-10-21):** dijo que la hoja "Anexo 1 a 49" del tablero ya dice qué
  historias cubren los elementos 1 a 23. Sus columnas "Historias de este backlog" y "Qué falta"
  son del backlog anterior de 33 (por ejemplo, "Ninguna cubre el CRUD" en etnia y comunidad
  étnica). La cobertura del v3 está en la hoja Backlog v3, columna de numerales del anexo.
- **Del modelo a sí mismo (2026-10-21):** HU-037 depende de HU-036 sin razón; su propio texto lo
  contradice. Lo encontró al revisar las dependencias para el límite de 12 historias.
- **Del modelo a sí mismo (2026-10-20):** afirmó que el resumen de compactación y el informe del
  verificador aparecen en el chat como mensajes del autor; no lo puede confirmar.
- **Al autor (2026-10-21):** la dependencia de los catálogos al login no es solo invención del
  equipo: la guía la exige (2.4) y el docente pidió el escenario de rol autenticado sin permisos en
  4 tarjetas. Sí es del equipo que el registro del paciente dependa del login.
- **Al autor (2026-10-21):** la oposición no está aislada: depende del paciente (HU-004).
- **Del modelo a sí mismo (2026-10-20, al regenerar los mockups):** una variable nueva tapó otra con
  el mismo nombre y el panel de versión nueva salió repetido en las 13 láminas de catálogo. Se vio
  al mirar la imagen de HU-034 y se corrigió antes de entregar.
- **Del modelo a sí mismo (2026-10-22, al revisar la siembra):** la columna de dónde se cita cada
  decisión, en la hoja Decisiones del tablero, nombra historias archivadas en D-02, D-04, D-17, D-20
  y D-23, y D-23 no nombra las 14 historias que la citan. Viene del llenado del 2026-10-20.
- **Del modelo a sí mismo (2026-10-24, Claude Code):** subió esta memoria a ramas públicas del
  fork, y por el PR #3 a Atervolgo, sin haber leído la advertencia del 2026-10-22 sobre el
  repositorio público. La leyó al recibir `POST.zip`.
- **Del modelo a sí mismo (2026-10-24, Claude Code):** recomendó "Sync fork" para poner al día el
  fork. Con el revert en Atervolgo, sincronizar trae el revert, y después `test` ya no entra en
  ese `main`. Corregido en el mensaje siguiente.
- **Al autor (2026-10-24):** "en Atervolgo no tengo permisos": los merges de Atervolgo#3 y #4 son
  de su cuenta.
- **Del modelo a sí mismo (2026-10-24, Claude Code):** propuso preguntarle al docente si las 12
  historias reemplazan el "mínimo 4 Historias de Usuario para cada Sprint" de la guía [2.5]. Esta
  memoria ya registraba, en la entrada del 2026-10-21, que el docente cambió esa regla de palabra.
  Leyó la guía como si mandara sobre lo que el docente respondió después.

## 11. Hallazgos abiertos sobre el prompt v2.1 y el backlog

Prompt v2.1, en orden de gravedad:
1. La pregunta 4 (reutilizar) nunca se alcanza para catálogos: V-12 saldría con 4 HU repetidas.
2. "Reglas del docente que mandan" copia 2 de las 7 filas de 2.1; faltan varias nacionalidades,
   fecha y hora separadas, solo ocupaciones hijas, varias discapacidades, ciudad con código DANE.
3. RC-3 dice "sincronizada" y el prompt prohíbe "sincronizar" y obliga a copiar RC completas.
4. V-25 junta dos componentes; solo importa si D-01 cambia a 5 o más.
5. Varias personas escriben la misma celda de Historias (HU-004 por A, B, C; HU-007 a 009 por C, D).
6. Sin criterio de prioridad con un backlog que pasa de 33 a 57 o más.
7. Prestador (elementos 9 y 16) no está en la pregunta 2; artículo 10 de la Res. 866 citado en
   HU-006 y HU-009 sin lugar en la jerarquía de fuentes.
Se ofreció una v2.2 con los arreglos 1 a 4. **El autor no respondió.**

Backlog: HU-018, 022, 026 y 030 construidas sobre "cargar" (131 líneas en tandas 4a y 4b);
HU-007 escenario 11 pendiente; HU-008 escenario 5 contra la hoja 18; tanda 4a afirma que ningún
otro campo del 2.1 pasa de 5 opciones, y discapacidad tiene 8.

Seis listas cerradas de más de 5 sin HU en el backlog: tipo de documento 14 (hoja 13), discapacidad
8 (14 y 15), etnia 7 (15), modalidad 9 (16), vía de ingreso 14 (16 y 17), causa 29 (17). Desde el
2026-10-20 están en Validación con "4 historias; IDs en espera de D-17", junto con tres catálogos
externos sin HU: comunidades étnicas, entidades del plan de beneficios y enfermedades huérfanas.

## 12. Pendientes y próximo paso

0000000000. **Próximo (2026-10-24, con docs/context reducido):** que el autor revise y fusione el
   PR que reduce `docs/context/`. Después, en detalle: las fases del ROADMAP a partir de la guía y
   del plan 0, las decisiones de arquitectura del esqueleto en terminal, si se recupera la
   trazabilidad de los comentarios del docente, y las reglas para los modelos de los compañeros.
000000000. **Anterior (2026-10-24, docs/context):** que el autor apruebe qué sale de
   `docs/context/` y a qué documento va lo que vale de cada archivo. Después, en detalle: las
   fases del ROADMAP a partir de la guía y del plan 0, y las reglas para los modelos de los
   compañeros.
00000000. **Anterior (2026-10-24, con la guía leída):** que el autor decida si lo de la guía que el
   repositorio no recoge (mínimo de 4 por sprint, roles, ceremonias, sustentación y entregables)
   entra en `docs/REQUIREMENTS.md` y en el ROADMAP, y si las transcripciones y los textos de las
   resoluciones entran en `docs/context/`. La guía es "COPIA CONTROLADA" y no trae licencia: no se
   sube sin su visto bueno (`CLAUDE.md`, sección 10). Faltan los prompts 1 y 2 del docente, los
   comentarios del docente por historia, qué persona es cada integrante y quién tiene cada rol de
   Scrum. Después, las reglas para los modelos de los compañeros.
0000000. **Anterior (2026-10-24, con el PR #2 fusionado):** que el autor revise el reparto en 3
   integrantes y diga qué persona es cada número; que mande las fuentes primarias (guía del
   docente, PDF del anexo, transcripciones, comentarios del docente y Prompt 2) y el `ls -Ral` de
   su carpeta `PRE`. Con eso, escribir las reglas para que los modelos de los compañeros trabajen
   en el mismo repositorio. En Trello sigue lo del 2026-10-24.
000000. **Anterior (2026-10-24, después del revert):** que el autor revise y fusione el primer PR
   real del fork, contra su `main`. Después, cada cambio en una rama con su PR en borrador. Por
   decidir: qué persona es cada integrante del plan (el ROADMAP los numera), y qué se hace con las
   ramas viejas `test` y `claude/wizardly-newton-ztyb85`, que ya no hacen falta. En Trello sigue lo
   del 2026-10-24.
00000. **Anterior (2026-10-24):** subir la siembra (los 4 elementos de `Subir/`) y después darle a
   Claude Code `Subir-2/`. En Trello: renombrar las 13 tarjetas de catálogo, pegar sus descripciones
   y cambiar 14 láminas (`LEEME-kit-trello.md`, sección "Si el tablero ya tiene las tarjetas").
   Sigue abierto si `MEMORIA-CONTEXTUAL.md` debe quedar en un repositorio público.
0000. **Anterior (2026-10-22):** el plan B está en To do y sus 12 historias, corregidas. Falta
   rehacer la lámina de HU-004 y la hoja Backlog v3 del tablero, y decidir si HU-030 y HU-039 a
   HU-042 reciben las mismas correcciones. El seguimiento sigue en `docs/ROADMAP.md` del repositorio.
000. **Anterior (2026-10-21, límite de 12 historias):**
   - Hecho: los cambios del backlog y `plan-desarrollo-12-historias.md`. Falta que el autor elija el
     plan A o el B. Pendiente si entran en un plan: ordenar la voluntad anticipada y la atención
     como la oposición.
   - Preguntas nuevas al docente: qué significa "desarrollada"; si acepta la comunidad étnica como
     texto (Res. 1888); si el login sale de la primera entrega aunque pidió escenarios de rol sin
     permisos en los catálogos.
   - Actualizar D-12 a la escala de 1 a 10 y estimar con las dependencias directas.
   - Hilo abierto del 2026-10-18: el "pero discrepo" del autor sobre el nombre del diagnóstico.
00. **Anterior (2026-10-20, después de la revisión final cruzada):**
   - Enviar al docente las 14 preguntas de `preguntas-docente.md` con las palabras del autor.
   - Revisión del equipo: cada tanda la aprueba alguien que no la analizó.
   - Descargar a mano la tabla Pais y la tabla CIE10 de SISPRO: confirman D-03 (código numérico) y
     si el CIE-10 trae las categorías de 3. D-26 ya está confirmado en ocupaciones por el documento
     del DANE. Buscar el catálogo de comunidades étnicas. Leer la Resolución 1895 de 2001. Revisar
     los Excel de Teams.
   - Estimar en clase (D-12) y pasar a Trello con `trello/LEEME-kit-trello.md` (versión 3).
   - Si el docente rechaza HU-043, el backlog baja a 29: hace falta otra historia para el mínimo.
0. **Anterior (2026-10-20):** que el autor y sus compañeros revisen el tablero llenado (Revisor
   distinto de Juan Fernando) y decidan D-05, D-17 y D-20, que cambian el número de historias.
   Abierto: si HU-031 a HU-033 están en Trello (otra lista) o solo en las tandas; nadie tocó el
   tablero ni Trello antes del llenado (dicho por el autor). Abierto: de qué semestre es el
   tablero "Trabajo equipo".
   Después, reservar IDs en Por redactar. Los puntos 1 a 6 de abajo son del 2026-10-18; los que
   quedan cerrados por el llenado: 1, 3 y 5.
1. ~~**Próximo:** V-27 (enfermedad huérfana) y V-28 (tipo de diagnóstico). El autor responde
   primero. Cerrados en análisis: 19, 20, 21, 22 y V-26 del 23.~~ Cerrado el 2026-10-20 por el
   modelo, a pedido del autor.
1b. Respuesta del autor a la propuesta de no reescribir tarjetas hasta clasificar las 28 filas.
2. Que el autor confirme Atención como entidad del numeral 19.
3. Que el autor diga si acepta acumular los ajustes de HU-007 a HU-009.
4. Saber si existe D-15 y si crea D-16. D-16 queda como pregunta al docente: si la primera fase
   cubre solo los grupos "Identificación del usuario" y "Contacto con el servicio de salud".
4b. Hecho: HU-004 y HU-007 verificadas en Trello. Pendiente: abrir HU-033 en Trello y confirmar
   el texto "Precisar inactivación/recarga", hoy en una fila "sin encabezado".
4c. Que el autor confirme sus citas que trae el modelo original: T162 (terminal), T242 (no detallar
   el anexo), T286 (regla del profesor y "si dice alfanumerico, no debe almacenar algo que no sea
   alfanumerico").
4d. Agregar el artículo 13.2 a la pregunta de alcance al docente (D-16).
4e. Implicación del artículo 7 para las HU de catálogos: crear o actualizar solo para reflejar el
   catálogo oficial, con fuente y versión (coincide con la retro de HU-020: "Validar
   correspondencia oficial y versionado"). Afecta los escenarios de HU-062 a HU-065, no la
   clasificación del numeral 20.
5. Pendiente de sesión: numerales 21 y 23; cierre del bloque con tabla consolidada; IDs en Por
   redactar (sugerido tomarlos desde HU-073 hacia atrás, acordándolo con el equipo).
6. Otros bloques: los analizan los compañeros; el autor puede recorrer los 23 numerales como
   revisor, porque quien aprueba no puede ser quien analizó.

**Sin origen recuperable:** qué bloque tiene cada compañero, y si alguno ya corrió el prompt v2.1.
