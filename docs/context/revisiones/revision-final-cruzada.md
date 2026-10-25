# Revisión final cruzada del backlog v3 (IPS Salud y Vida)

Revisor independiente, 2026-10-20. No escribí ninguno de los textos revisados y no modifiqué archivos
del proyecto. Archivos revisados, con su hora de guardado: tanda 0 v4 (05:19), tanda 1 v4 (05:29),
tanda 2 v3 (05:30), tanda 3 v3 (05:31; solo cargo y conteos), tanda 4 v2 (05:28), tanda 5 v2
(05:28), `backlog-v3/partes/tanda-5-cie10-v1-superada.md` (04:56), `tablero-validacion-ips-20oct.xlsx`
(05:33), kit de Trello (05:38), mockups HTML (05:36), modelo E-R (05:37), plan (05:40) y preguntas al
docente (05:39). "l." es el número de línea en esos archivos.

Método: conté los escenarios de las 30 historias con un script (Dado, Cuando y Entonces en cada uno);
comparé las 30 tarjetas del kit con las tandas uniendo las líneas partidas; crucé las etiquetas
"Esc. N" de los 30 mockups con los títulos de los escenarios; leí las hojas 13 a 16 y 18 del anexo en
los recortes del PDF; busqué cada afirmación externa en los textos extraídos de las Resoluciones 866,
1888 y 2625; leí los escenarios antiguos de Trello en la hoja Escenarios del tablero; descargué el
documento de la CIUO-88 A.C. del DANE para probar D-26.

## 1. Veredicto y TL;DR

Escala: lista para el equipo; lista con ajustes menores; necesita cambios; no lista.

**Necesita cambios.** Son cambios acotados: dos reglas comunes (RC-8 y RC-10) y los escenarios que las
aplican, dos escenarios nuevos de consulta (HU-005 y HU-008), el esc. 16 de HU-004, la precondición de
HU-009, tres trazabilidades y un comentario al docente. No hay que rehacer historias.

TL;DR:
- RC-8 rechaza las filas de grupo de la CIUO-88 y las categorías del CIE-10 que HU-022 y HU-030 dicen
  cargar (A1). RC-10 deja reactivar valores que la fuente retiró entre versiones y, con la definición
  del modelo E-R, deja inactivar como "Creado por error" un valor oficial (A2).
- El artículo 10 de la 866 se cita para "sus datos son permanentes", que el artículo no dice (M1);
  RC-11 no tiene escenario en las consultas donde se ve (M2).
- HU-030 atribuye a "retroalimentación sin verificar" de HU-032 lo que el tablero marca "propuesta del
  modelo", y el bloqueo de valores en uso que el docente elogió desaparece sin decirlo (M7, M8).
- Cuadran los conteos (30 historias, 541 escenarios, 13 tarjetas archivadas) donde aparecen; las 30
  tarjetas dicen lo mismo que las tandas; las hojas 15, 16 y 18 y las Resoluciones 1888 y 2625 están
  bien citadas. La cifra errada es "diez" por trece historias de catálogo (B1).

## 2. Respuesta corta a cada punto del encargo

| Punto | Respuesta | Hallazgos |
|---|---|---|
| 1. RC-8 a RC-11 | Coherentes entre sí salvo dos huecos: los niveles de los catálogos con jerarquía y lo que significa "estar en la versión vigente". Los escenarios genéricos aplican las reglas igual en las 13 historias, pero en las listas del anexo dos escenarios se contradicen y una primera carga parcial no se puede completar | A1, A2, M2, M4, M5, B9 |
| 2. HU-030 y HU-031 | Cubren los dos comentarios del docente a HU-030 y la retroalimentación sin verificar de HU-031 y HU-033, menos RC-1 en HU-031 y el bloqueo de uso de HU-033. HU-032 no tiene retroalimentación en el tablero. Trazabilidad exacta: HU-030 10 de 10, HU-031 10 de 10, HU-032 8 de 10, HU-033 9 de 10. Quedan sin escenario la versión nueva como vigente y el historial de versiones | M7, M8, M9, M10, B7, B12 |
| 3. HU-043 y cambios en HU-004, HU-007 a HU-009 | Coherentes con la matriz y con HU-001 esc. 6; tienen 16, 18, 14, 13 y 11 escenarios. Fallan el uso del artículo 10, el esc. 16 de HU-004, la precondición de HU-009 y un escenario duplicado en HU-043 | M1, M3, M6, M11, B10, B11 |
| 4. Coherencia entre archivos | Cuadran 30, 541 y 13; D-25 a D-27 están en la hoja Decisiones; el cargo es uno solo en todo el texto vigente; las referencias entre historias son exactas; las etiquetas de los mockups caen en su escenario. Errores: "diez", celdas desactualizadas del tablero y una ruta del kit | B1, B13, B14, B15 |
| 5. Fidelidad a las fuentes | Confirmado: hoja 15 sin nombre de comunidad, texto literal de la hoja 16, segunda fila de la hoja 18, tablas y frase del CIE-11 de la 1888, artículo 3 de la 2625. Dicen más que la fuente: artículo 10, artículo 8, D-25, "texto libre, no un código", D-02 | M1, B2 a B8, B16 |
| 6. Lenguaje | Sin guion largo ni intensificadores en tandas, kit, plan, preguntas, modelo E-R y mockups. Reglas largas en RC-10 y HU-030, y tres errores de concordancia o de conjunción | B10, B14, B17 |

Cobertura de los pedidos del docente y de la retroalimentación sin verificar (punto 2):

| Pedido | Dónde quedó | Estado |
|---|---|---|
| HU-030 c1: cargo funcional real en descripción, precondiciones y escenarios | Regla del cargo, precondición, esc. 27 | Atendido |
| HU-030 c1: formato de archivo o formulario, campos y orden de carga padre e hijo | RC-8, columnas, D-26, esc. 5, 6 y 10 | Atendido; choca con RC-8 (A1) |
| HU-030 c1: versión de referencia y cómo se valida que es la requerida | D-09, dirección y fecha de descarga (RC-8), esc. 20 con un archivo recién descargado | Atendido |
| HU-030 c1: intento de un rol autenticado sin permisos; RC-1, RC-3 y RC-5 | Esc. 27; reglas enlazadas | Atendido |
| HU-030 c1 y c2: mockup y estimación | `mockups/png/HU-030.png`; "Pendiente de estimación en clase" | Atendido |
| HU-030 c2: edición oficial adoptada; códigos y descripciones; raíces; formato | D-09 y Resolución 1895 por confirmar; regla de datos; esc. 15; RC-8 | Atendido; cita de [2.2] (B7) |
| HU-030 c2: conservar los CA y precisar resultados | Trazabilidad | En parte (M8, M9) |
| HU-031 (sin verificar): cargo, escenario 4, búsqueda sin coincidencias, códigos no vigentes | Actor médico tratante; esc. 4, 6 y 7 | Atendido |
| HU-031 (sin verificar): "Enlazar o incluir RC-1 y RC-2" | Solo RC-2 | En parte (B12) |
| HU-033 (sin verificar): rol sin permisos; RC-1, RC-4 y RC-5; inactivación, motivo y autor; padres con hijos | Esc. 27; reglas enlazadas; esc. 21, 23 y 28; esc. 22 | Atendido |
| HU-033 (sin verificar): "Bien bloqueo de uso" | El esc. 21 deja inactivar un valor en uso | Cambiado sin aviso (M7) |
| HU-033 (sin verificar): preservación de valores oficiales | RC-10 | En parte (A2) |
| HU-032 | El tablero no tiene retroalimentación de HU-032 | Mal atribuido (M8) |

## 3. Hallazgos

### Alta

**A1. RC-8 rechaza los niveles superiores que HU-022 y HU-030 dicen cargar**

- Ubicación: tanda 0, RC-8 (l. 104-107), copiada en `trello/tarjetas/RC-reglas-comunes.md`; tanda 4,
  HU-022, regla de niveles (l. 325-328) y esc. 5 (l. 370-374); tanda 5, HU-030, regla de datos
  (l. 72-75) y esc. 5 (l. 125-129).
- Cita: RC-8, "rechaza cada fila que no cumple su numeral, con el número de fila y el motivo, y las
  filas con un código repetido. Un código de solo dígitos más corto que su numeral se rechaza con el
  aviso "revise que el archivo no haya perdido los ceros iniciales"". HU-022, "Los grupos de 1 a 3
  caracteres se cargan para la jerarquía". Esc. 5 de las dos historias, "Entonces el sistema acepta
  las dos filas".
- Por qué falla: el numeral 7 fija el código de la ocupación en 4 y 4 (hoja 14: formato 9999,
  longitud máxima 4, mínima 4) y el numeral 23 fija el código CIE-10 en 4 y 4 (hoja 18). Leída como
  está, RC-8 rechaza el grupo 222 con el aviso de ceros y la categoría A00 por longitud; los esc. 5
  esperan lo contrario y ninguna de las tres reglas dice cuál manda. El aviso de ceros tampoco sirve en
  la CIUO. El documento del DANE dice que la clasificación está "integrada por 4 niveles de
  agregación, así: 10 grandes grupos a un dígito; 34 subgrupos principales a dos dígitos, 117
  subgrupos a tres dígitos y 401 grupos primarios a cuatro dígitos", y trae el gran grupo "0 FUERZA
  PÚBLICA" con los códigos 01, 011, 0110 y "02 POLICÍA NACIONAL". Si el archivo pierde los ceros, 01
  pasa a 1 y 02 pasa a 2, y chocan con los grandes grupos 1 y 2: RC-8 rechaza las filas repetidas y el
  esc. 6 rechaza en cascada todo lo que cuelga de 1 y de 2, sin el aviso de ceros, porque 1, 2 y 11
  son códigos de grupo válidos, con la longitud de su nivel.
- Corrección: agregar a RC-8 "en los catálogos con niveles, las filas de nivel superior se validan con
  la regla de su historia (HU-022, HU-030) y no con la longitud del numeral". En HU-022, cambiar el
  aviso de ceros por una comprobación de cantidades por nivel contra el documento del DANE (10, 34,
  117 y 401).

**A2. "Estar en la versión vigente" no protege lo que RC-10 promete**

- Ubicación: tanda 0, RC-10 (l. 118-123) y RC-9 (l. 113-114); `modelo-datos/modelo-entidad-relacion.md`
  (l. 88-90); escenario "Reactivación sin respaldo" de las 13 historias de catálogo, por ejemplo
  HU-030 esc. 26 (tanda 5, l. 244-247).
- Cita: RC-10, "Solo se reactiva, con motivo, un código que está en la versión vigente o que se creó
  por un cambio oficial". Modelo E-R, "`version_id` de cada catálogo es la última versión del archivo
  oficial que trajo el código. Si es la vigente, el código está en la versión vigente, que es lo que
  miran "Creado por error" y la reactivación (RC-10)". HU-030 esc. 26, "no está en la versión vigente
  ni se creó por un cambio oficial, por ejemplo porque la fuente lo retiró".
- Por qué falla: (1) Un código que la fuente retira entre dos versiones (escenario "Inactivación con
  motivo", con "Retirado por la fuente oficial" y su referencia) sigue en la versión vigente; RC-10
  deja reactivarlo con un motivo escrito y sin referencia. Pasa lo mismo con un código "Reemplazado
  por" y con uno creado por cambio oficial y retirado después. El ejemplo del esc. 26 solo es cierto
  si el retiro llegó en una versión nueva. (2) Si `version_id` cambia solo con filas aceptadas, como
  sugiere "queda como estaba" en RC-9, el código de una fila oficial rechazada deja de estar "en la
  versión vigente": se puede inactivar como "Creado por error" sin referencia y, si ya estaba inactivo,
  no se puede reactivar. En los dos casos el catálogo se adiciona o se reduce sin referencia oficial,
  contra el artículo 7 de la 866 ("no podrán ser modificados, reducidos o adicionados por los actores
  que generan, transportan, almacenan, disponen o usan datos") y contra HU-021 c2 ("sin retirar
  valores oficiales arbitrariamente").
- Corrección: definir en RC-10 "está en la versión vigente" como "vino en el archivo de la versión
  vigente, aceptado o rechazado", y ajustar la línea del modelo E-R. Agregar que un código inactivado
  con referencia oficial solo se reactiva con otra referencia oficial o diciendo por qué la anterior no
  aplicaba, con su escenario de rechazo.

### Media

**M1. El artículo 10 se cita para algo que no dice**

- Ubicación: tanda 2, HU-007 regla (l. 71-73), HU-009 regla (l. 390-392), D-25 en la tabla de
  decisiones (l. 21), HU-043 regla (l. 541-542).
- Cita: HU-007, "Desde que queda "Registrada", sus datos son permanentes (artículo 10 de la
  Resolución 866) y todo cambio es una corrección (HU-009)". HU-043, "Anular no borra: [...] (D-20;
  artículo 10 de la Resolución 866)".
- Por qué falla: el artículo 10 dice que los prestadores "deberán disponer los datos de las atenciones
  de salud realizadas de forma permanente, a través del mecanismo electrónico de interoperabilidad".
  Lo permanente es la disposición en el mecanismo, no que el dato quede fijo. Agregar el dato nuevo al
  corregir sale del parágrafo 2, que además limita: "En ningún caso se podrá solicitar tratamiento a
  los datos que sean generados en la atención por otras razones diferentes a inconsistencias
  demostradas". El artículo no habla de borradores ni de anular. La hoja Decisiones lo resume bien en
  D-20 ("los datos de las atenciones quedan disponibles de forma permanente"); la tanda 2 no. Con el
  motivo "Otro" de HU-043 se puede anular por una razón que no es una inconsistencia demostrada.
- Corrección: escribir "el artículo 10 obliga a disponer los datos de las atenciones de forma
  permanente y, al corregir, a agregar el dato nuevo con quién y cuándo (parágrafo 2); que un borrador
  cambie sin rastro y que una atención se anule son propuestas del equipo (D-08, D-27)". En HU-043, que
  "Otro" pida la inconsistencia que se demuestra.

**M2. RC-11 no se prueba donde se ve**

- Ubicación: tanda 1, HU-005 regla (l. 353-356); tanda 2, HU-008 regla (l. 250-253); escenario
  "Cambio oficial de nombre" de las 13 historias de catálogo, por ejemplo HU-018 esc. 10 (tanda 4,
  l. 174) y HU-030 esc. 12 (tanda 5, l. 169).
- Cita: escenario de catálogo, "Y los registros que ya lo usan conservan el nombre con que se
  hicieron, y su consulta muestra también el nuevo (RC-11)". Tanda 0, actor profesional de
  estadística, "No ve datos de pacientes ni de atenciones: solo cuántos registros usan un valor".
- Por qué falla: el resultado está en un escenario cuyo actor no lo puede ver, y el Prompt 2 pide
  "Expresar resultados observables por el usuario o por el negocio". HU-005 y HU-008, donde el auxiliar
  y el médico verían los dos nombres, tienen la regla pero ningún escenario; sus mockups tampoco lo
  muestran. La pregunta 13 al docente dice que RC-11 "Cambia [...] la consulta del paciente y de la
  atención".
- Corrección: un escenario en HU-005 y otro en HU-008 ("Dado que el catálogo cambió el nombre de un
  valor después del registro, Cuando el auxiliar de admisión [o el médico tratante] consulta el
  registro, Entonces ve el nombre con que se registró y el vigente, marcado como tal"). En las
  historias de catálogo, la segunda Y remite a esos escenarios.

**M3. HU-004 escenario 16 no prueba una conducta del sistema**

- Ubicación: tanda 1, HU-004 esc. 16 (l. 298-302) y regla del paciente particular (l. 198-201).
- Cita: "Dado que el paciente no tiene una entidad que responda por su plan de beneficios y paga la
  atención de forma particular [...] Entonces elige del catálogo de entidades la secretaría de salud
  del lugar donde se presta el servicio (HU-038)".
- Por qué falla: el Entonces es una acción del auxiliar; el Prompt 2 pide "Entonces [resultado
  funcional visible o verificable]". El sistema no tiene un dato que diga "particular", así que no
  ofrece, exige ni rechaza nada distinto del esc. 1: el escenario pasa con cualquier entidad. El Dado
  agrega "no tiene una entidad que responda por su plan de beneficios", que la hoja 16 no dice; la
  hoja habla del servicio: "En el caso de que la prestación del servicio de salud se preste de forma
  particular".
- Corrección: dar al sistema una conducta verificable, por ejemplo una marca "atención particular" que
  solo ofrece secretarías de salud, o dejar el caso como regla y reemplazar el escenario. En el Dado,
  la condición de la hoja.

**M4. En las listas del anexo, los escenarios 2 y 3 se contradicen**

- Ubicación: tanda 4, HU-034 esc. 2 y 3 (l. 858-868), igual en HU-035 y HU-036; tanda 5, HU-039
  esc. 2 y 3 (l. 506-516), igual en HU-040 y HU-041; mockups HU-034, HU-035 y HU-039, panel "Carga del
  archivo oficial".
- Cita: esc. 2, "no trae uno de los 14 códigos de la hoja 13, o trae uno que la hoja no tiene [...] no
  deja guardar la carga"; esc. 3, "trae los 14 códigos de la hoja 13 y además una fila con un código
  de 3 caracteres [...] elegir entre guardar las filas aceptadas o cancelar la carga completa".
- Por qué falla: un código de 3 caracteres es un código que la hoja no tiene. El archivo del esc. 3
  cumple el Dado del esc. 2, y los dos escenarios dan resultados opuestos. El mockup de HU-039 muestra
  en el mismo panel "Aceptadas 9", el botón "Guardar las aceptadas" y "No se puede guardar hasta que
  el archivo traiga los 9". Tampoco se dice qué pasa si la fila rechazada es un código de la hoja.
- Corrección: "la comparación con la hoja se hace con las filas aceptadas: una fila rechazada no cuenta
  como código de más, y si el código rechazado es de la hoja, la carga no se guarda". Ajustar los tres
  mockups.

**M5. Una primera carga parcial no se puede completar**

- Ubicación: tanda 0, RC-8 (l. 107-108) y RC-9 (l. 109-110); afecta a los siete catálogos externos
  (HU-018, HU-022, HU-026, HU-030, HU-037, HU-038 y HU-042).
- Cita: RC-8, "En la primera carga, el profesional guarda las aceptadas o cancela todo". RC-9, "Si el
  catálogo ya tiene valores, el archivo es una versión nueva, y su fecha de publicación debe ser
  posterior a la de la versión vigente". HU-030 esc. 10, "Dado que la fuente oficial agregó un
  diagnóstico después de la versión cargada".
- Por qué falla: si se guardan las aceptadas, los códigos oficiales rechazados (por ejemplo, por ceros
  perdidos al convertir el archivo) no tienen cómo entrar. Volver a subir el archivo corregido con la
  misma fecha se rechaza; crear un valor exige un cambio posterior a la versión; la comprobación los
  lista como "faltan" sin dejar hacer nada. El catálogo queda reducido hasta la próxima versión oficial
  (artículo 7). En las listas del anexo no pasa, porque su esc. 2 impide guardar una carga incompleta.
- Corrección: permitir "recargar la misma versión", que solo crea los códigos que faltan y avisa los
  nombres distintos, sin inactivar nada; o impedir guardar una primera carga incompleta en todos los
  catálogos, como en las listas del anexo.

**M6. HU-009: la precondición deja por fuera el escenario 10**

- Ubicación: tanda 2, HU-009 precondición (l. 411) y esc. 10 (l. 469-473).
- Cita: precondición, "La atención está registrada (HU-007)"; esc. 10, "Dado que una atención es
  "Borrador"".
- Por qué falla: es el defecto que el docente marcó en HU-008: "Ajustar la precondición «al menos una
  atención» para permitir el caso sin atenciones del escenario 3". Hoy se corrigió en HU-004 y HU-007,
  no en HU-009. Con HU-043, la precondición también deja por fuera a las anuladas.
- Corrección: "La atención existe (HU-007)", o llevar el esc. 10 a HU-007.

**M7. El bloqueo de valores en uso cambia sin decirlo**

- Ubicación: tanda 5, trazabilidad de HU-030 (l. 302-303); tanda 4, trazabilidad de HU-018 (l. 270),
  de HU-022 (l. 535) y de HU-026 (l. 782); escenario "Inactivación con motivo" de las 13 historias;
  `trello/respuestas-al-docente.md`, HU-018 (l. 68).
- Cita: trazabilidad de HU-030, "el 2 (en uso) [...] van en el 21". Escenario antiguo de HU-033
  (tablero, hoja Escenarios, fila 326): "el sistema no lo permite | señala cuántas atenciones lo están
  usando". HU-030 esc. 21, "el sistema muestra cuántas atenciones lo usan y pide confirmar".
- Por qué falla: el docente elogió el bloqueo en HU-021 c2 ("Bien bloquear uso y conservar rastro") y
  en HU-029 c2 ("Bien bloqueo en uso e historial"), y la retroalimentación sin verificar de HU-033 dice
  "Bien bloqueo de uso y padres con hijos, e historial" (tablero, Retroalimentación, fila 70). El
  backlog v3 deja inactivar un valor en uso después de confirmar, y la trazabilidad y la respuesta al
  docente presentan ese escenario como respuesta al bloqueo. La razón del cambio solo está en las
  observaciones de D-20, en la hoja Decisiones.
- Corrección: decirlo en la trazabilidad y en las respuestas ("el valor en uso ya no se bloquea: se
  inactiva solo con referencia oficial y los registros lo conservan") y sumarlo a la pregunta 6 al
  docente.

**M8. Trazabilidad de HU-030: una fuente mal atribuida, cuatro correspondencias inexactas y dos resultados sin escenario**

- Ubicación: tanda 5, cabecera (l. 8-10) y trazabilidad de HU-030 (l. 284-305); tablero, hoja Ajustes
  por historia, fila 104; hoja Retroalimentación, filas 65 a 70; hoja Escenarios, filas 317 y 320.
- Cita: cabecera, "lo que el tablero dice de ellas viene de la retroalimentación que no se pudo
  verificar"; trazabilidad, "Retroalimentación sin verificar (tablero) de HU-032: solo por cambio
  oficial, con su versión". Tablero, fila 104, columna Fuente: "Res. 866, art. 7; D-20; propuesta del
  modelo".
- Por qué falla:
  - La hoja Retroalimentación tiene textos de HU-030 (filas 65 y 66), HU-031 (67 y 68) y HU-033 (69 y
    70), ninguno de HU-032. Lo que se atribuye a su retroalimentación es una propuesta del modelo.
  - "inactivar con motivo, quién y cuándo: escenarios 21 e 23": quién y cuándo están en el 28.
  - El antiguo esc. 3 de HU-032 ("el sistema registra la versión nueva | los diagnósticos registrados
    con la anterior siguen siendo consultables") "es el 7", y el esc. 7 no dice ninguna de las dos
    cosas; la vigencia solo está en RC-9.
  - El antiguo esc. 6 de HU-032 ("intenta poner un código que ya usa otro diagnóstico [...] el sistema
    lo rechaza") "va en el 11", pero el esc. 9 ahora acepta reemplazar por un código que ya existe
    ("crea el nuevo si no existe"): la conducta se invirtió.
  - El antiguo esc. 2 de HU-033: ver M7.
  - De la versión 1 se pierde el esc. 13 de HU-032, "Historial de versiones" ("ve cada versión con su
    fecha de publicación, quién la cargó y cuándo", `partes/tanda-5-cie10-v1-superada.md`,
    l. 436-440). El esc. 14 muestra solo la versión vigente y el 28 es el historial de un código.
- Corrección: atribuir la fila 104 a "propuesta del equipo" en la cabecera y en la trazabilidad,
  corregir las cuatro correspondencias y agregar al esc. 7 "Y la versión nueva queda vigente". Para el
  historial de versiones, un escenario o la nota de que sale.

**M9. La respuesta al docente de HU-030 remite a un texto que no estará en Trello**

- Ubicación: `trello/respuestas-al-docente.md`, HU-030 (l. 80).
- Cita: "conservar los criterios y precisar resultados: correspondencias de abajo".
- Por qué falla: las correspondencias están en la sección "Trazabilidad (no va en la tarjeta)". El
  docente pidió en HU-030 c2 "Conservar los CA y precisar resultados según esta observación", y el
  comentario lo manda a un texto que no va a ver.
- Corrección: pegar en el comentario las correspondencias de los escenarios antiguos de HU-030, HU-032
  y HU-033, o ponerlas en las notas de archivo de HU-032 y HU-033.

**M10. El plan B del CIE-10 mezcla dos fuentes en un archivo**

- Ubicación: tanda 5, HU-030, supuestos (l. 263-269).
- Cita: "Si no las trae, el equipo arma el archivo con las categorías de la CIE-10 que adoptó la
  Resolución 1895 de 2001 (citada en la Resolución 2625 de 2025) y los códigos de la tabla de SISPRO,
  sin cambiar ningún código ni nombre". El supuesto anterior de la misma tarjeta dice "cambia solo la
  fila de encabezado, nunca los datos".
- Por qué falla: RC-8 guarda una fuente, una dirección y una fecha de descarga por archivo, y un
  archivo armado con dos fuentes no se puede describir así. La comprobación (esc. 20) contra un archivo
  de SISPRO recién descargado listaría cada categoría entre "los activos que no están en el archivo", y
  cada versión nueva (esc. 7) las pondría en "los que ya no están", bloqueadas una por una por el
  esc. 22. La 1895 no se ha leído (D-09, "por confirmar").
- Corrección: si se usa el plan B, cargar las categorías como fuente aparte, con su propia versión, y
  excluirlas de la comparación con la tabla de SISPRO.

**M11. Sin el catálogo de comunidades étnicas, HU-004 no registra pacientes de las etnias 01 a 06**

- Ubicación: tanda 1, HU-004 lista de datos (l. 184) y esc. 15 (l. 292-296); tanda 4, HU-037
  supuestos (l. 1577-1579).
- Cita: HU-004, "Comunidad étnica | 13 | 15 | Si la etnia no es "Ninguna de las anteriores""; esc. 15,
  "no deja guardar al paciente mientras ese dato sea obligatorio para él (D-19)"; HU-037, "el equipo no
  ha encontrado el archivo de este catálogo".
- Por qué falla: con el catálogo vacío, el esc. 15 impide registrar a todo paciente con etnia 01 a 06.
  El anexo de la Resolución 1888 trata el dato como "Texto Alfanumérico" y dice "Diligenciar con el
  nombre de la comunidad étnica a la que pertenece el paciente, cuando aplique". El plan solo anota el
  efecto en el conteo ("baja a 29"), no este bloqueo.
- Corrección: en D-19 y en los supuestos de HU-004, dejar la comunidad étnica como opcional hasta
  confirmar la fuente de HU-037, o decir qué se registra mientras tanto.

### Baja

| N.º | Ubicación | Cita | Por qué falla | Corrección |
|---|---|---|---|---|
| B1 | Tanda 0, l. 76; `trello/tarjetas/RC-reglas-comunes.md`, l. 20; `preguntas-docente.md`, preguntas 4 y 11 | "para no repetirlas en diez tarjetas"; "Cambia el actor de 10 historias de catálogo" | La lista de la misma frase (HU-018, HU-022, HU-026, HU-030 y HU-034 a HU-042) suma 13; el modelo E-R cuenta 12 más el CIE-10. La tarjeta y las preguntas van al docente; el encargo de esta revisión repite la cifra | "trece" |
| B2 | Tanda 4, D-25 (l. 44); hoja Decisiones, D-25 | "El anexo trae el nombre como parte del dato ("Nombre correspondiente al código", hojas 13 a 15 y 18)" | La frase está en las hojas 14 (numeral 7), 15 (numeral 11) y 18 (numeral 23); la hoja 13 tiene el componente de nombre sin ese texto. Los numerales 2, 10, 13, 18, 20 y 21 y el código de enfermedad huérfana no tienen componente de nombre. HU-020 c2 y HU-028 c2 hablan de cambiar un código, no un nombre | "El anexo trae el nombre como componente en los numerales 1, 7, 11, 12, 15 y 23; en los demás catálogos, conservarlo es decisión del equipo" |
| B3 | Tanda 4, D-21 (l. 42) | "El artículo 8 de la Resolución 866 pone los catálogos a cargo del Ministerio de Salud, que los individualiza en SISPRO" | El artículo dice "para lo cual se definirá y establecerá el procedimiento que los individualice en el Sistema Integrado de Información de la Protección Social- SISPRO": anuncia un procedimiento | "que definirá el procedimiento para individualizarlos en SISPRO" |
| B4 | Tanda 4, D-21 (l. 42) y HU-037 (l. 1578-1579); pregunta 10 | "pide para este dato el nombre de la comunidad en texto libre, no un código" | La 1888 dice "13.2 Comunidad étnica Texto Alfanumérico" y "Diligenciar con el nombre de la comunidad étnica a la que pertenece el paciente, cuando aplique"; "texto libre" y "no un código" son lectura del equipo | Citar la 1888 y marcar la lectura |
| B5 | Tanda 0, actores (l. 32); tanda 4, D-02 (l. 39) | "el área de estadística es la que suele reportar los datos de la IPS con las tablas oficiales" | Va como hecho; la hoja Decisiones la llama "Hipótesis del modelo, sin fuente" | "se supone que", o una fuente |
| B6 | HU-034 a HU-036 (tanda 4) y HU-039 a HU-041 (tanda 5), regla de fuente | "si no coinciden, manda la hoja (D-21)" | D-21 no trae esa regla, ni en las tandas ni en la hoja Decisiones; el esc. 2 avisa la diferencia de nombre sin decir cuál se guarda | Escribirla en D-21 y decir qué nombre se guarda |
| B7 | Tanda 5, HU-030 (l. 75) y HU-031 (l. 338-339) | "las categorías son las raíces de la jerarquía [2.2]"; "Ver la categoría es lo que pide la guía" | [2.2] dice "respetando la jerarquía padre-hijo de sus códigos diagnósticos"; no habla de raíces ni de mostrar la categoría | Citar D-26 y D-22; dejar [2.2] para la jerarquía |
| B8 | Tanda 5, HU-042 trazabilidad (l. 1240-1241) y D-26 (l. 32); tanda 4, HU-037 (l. 1427) | "el artículo 3 de la Resolución 2625 de 2025 dice que el número de cada enfermedad no se reasigna"; "El CIE-10 arma así sus códigos (glosario de la tanda 0)"; "el nombre lo trae el catálogo de referencia" | El artículo 3 dice que el número "es exclusivo y no puede ser asignado a ninguna otra"; que una enfermedad no cambie de número es lectura. El glosario es del equipo; la tanda 4 y la hoja dicen "inferencia". El catálogo de HU-037 no se ha encontrado | Marcar las tres como lectura o supuesto. En la CIUO, D-26 ya tiene fuente: el DANE dice "identificados mediante códigos numéricos de 1, 2, 3 y 4 dígitos" |
| B9 | Tanda 0, RC-8 (l. 100), RC-9 (l. 112-116), RC-10; escenario "Inactivación sin motivo o sin respaldo" de las 13 | "Ninguna de las dos puede ser futura"; "El profesional puede anotar qué código nuevo reemplaza a uno que ya no está"; RC-9 "reactiva" frente a RC-10 "Solo se reactiva, con motivo" | Reglas sin escenario o sin consecuencia: no se dice qué pasa con una fecha futura ni qué motivo deja la anotación de reemplazo; la reactivación por versión no dice qué motivo guarda; el Entonces de "Inactivación sin motivo o sin respaldo" nombra "la referencia oficial" y su Dado no trae el caso de un motivo sin referencia | Un escenario por regla, o quitarlas; en RC-9, el motivo que guarda al reactivar |
| B10 | Tanda 2, HU-043 esc. 9 (l. 599-603) y esc. 3 (l. 570); D-27 (l. 22) | Esc. 9 repite HU-008 esc. 14; esc. 3, "el sistema lo deja "Anulada""; D-27, "Una atención registrada por error" | El Prompt 2 pide "No haya duplicidad significativa entre historias"; sin el esc. 9 quedan 10. D-27 no menciona borradores y HU-043 los anula. No se dice si las anuladas cuentan en "cuántos registros usan el valor" | Quitar el esc. 9 de una de las dos; "lo deja en estado "Anulada""; borradores en D-27; decir si las anuladas cuentan |
| B11 | Tanda 1, HU-004 esc. 11 (l. 272); tanda 2, HU-008 esc. 5, 6, 8, 9 y 10; HU-007 esc. 18 (l. 187-192) | "Cuando el sistema rechaza el registro"; "Cuando se consulta"; "Cuando se lista"; "Y si el que falta es el de enfermedades huérfanas, el registro sigue" | El Prompt 2 pide "Cuando [acción realizada por el usuario]". El esc. 18 de HU-007 trae dos casos y el segundo no está en su Dado | El actor y su acción en cada Cuando; partir el esc. 18 o poner las enfermedades huérfanas en el Dado |
| B12 | Tanda 5, HU-031 reglas (l. 334-349) y trazabilidad (l. 432-434) | Retroalimentación sin verificar de HU-031 (tablero, Retroalimentación, fila 67): "Enlazar o incluir RC-1 y RC-2" | HU-031 trae RC-2, RC-4 y RC-11, no RC-1, y la trazabilidad no registra el pedido | Copiar RC-1 o decir por qué no aplica |
| B13 | Tablero, plan y kit | Hoja Reglas comunes, "Historias que la citan": 0 para RC-6 a RC-11; hoja Leeme, B19: "Reglas comunes: RC-1 a RC-5"; Ajustes, fila 100: "con código, descripción y padre", estado "Aplicado en borrador T5"; Decisiones, D-22, "Afecta a": "HU-007 escenario 11"; plan, l. 134: "T2 atención (3)" y "T5 [...] (8)"; LEEME del kit: "adjuntar `mockups/HU-0XX.png`" | RC-6 a RC-11 se citan en varias historias; las reglas van hasta RC-11; HU-030 ya no tiene columna de padre (D-26); el código padre es hoy el esc. 9 de HU-007; T2 tiene 4 historias y T5, 6; no existe `trello/mockups/`: las imágenes están en `mockups/png/` | Actualizar los seis puntos |
| B14 | Mockups HTML y PNG | "los atenciones que lo tenían lo conservan" (HU-030, HU-039, HU-040, HU-041); HU-030, "Rechazadas 3" con las filas 211, 212, 900 y 901; HU-042, fuente "Listado de la Resolución 2625 de 2025, versión 5.0" con "Fecha de publicación 2026-03-01"; HU-039, fuente "Numeral 18 del anexo, hoja 16" con la misma fecha; HU-034, "ZZ Tipo de prueba" que usan 12 pacientes | Error de concordancia; el conteo no cuadra con la tabla; la 2625 salió en el Diario Oficial 53.339 del 18 de diciembre de 2025 y la hoja 16 es de la 866 de 2021; un tipo ZZ no puede existir si la primera carga exige los 14 de la hoja (esc. 2) | "las atenciones"; cuatro rechazadas; la fecha de la fuente que se nombra; un código real en el ejemplo |
| B15 | Modelo E-R (l. 91-93); tanda 2, HU-007 esc. 8 (l. 132) | "los registros guardan solo el código [...] así el nombre no se repite en cada registro [2.7]"; "el sistema guarda el código con el nombre que le da el catálogo" | [2.7] habla de evaluar "la correcta modelación de datos", no de repetir nombres. La historia dice que guarda el nombre y el modelo dice que no; lo que ve el usuario es igual | Quitar [2.7]; en HU-007, "registra el código y muestra el nombre que le da el catálogo" |
| B16 | Tanda 1, HU-004 lista de datos (l. 178) | "Identidad de género \| 6 \| 14 \| Sí; la lista tiene "No lo declara"" | Fuera del encargo, pero toca D-19: el anexo de la 1888 dice "La información de identidad de género es un dato opcional que será diligenciado a criterio del profesional de la salud de acuerdo con el escenario de atención" | Citarlo en D-19 y preguntarlo al docente |
| B17 | Tanda 5, l. 287; tanda 0, RC-10 (l. 118-121); tanda 5, HU-030 regla (l. 83-86) | "escenarios 21 e 23"; RC-10, "con el código nuevo y la referencia oficial" | "e" va antes de i o hi, y "veintitrés" pide "y". En RC-10 no se sabe si el código nuevo y la referencia van con los dos motivos o con uno. La regla de categorías de HU-030 son tres reglas en una frase | "21 y 23"; RC-10 y esa regla como lista de un renglón por caso |

## 4. Lo que está bien y no se debe tocar

- Conteos: 30 historias en la tanda 0, el plan, el LEEME del kit y la hoja Backlog v3; 541 escenarios
  en el plan y el LEEME, y mi conteo da lo mismo; 13 tarjetas archivadas en el plan, el LEEME, la hoja
  y la lista de la tanda 0. Mi conteo por historia coincide con las tablas de las tandas 4 (195) y 5
  (135). La numeración de escenarios es continua en las 30, todos tienen Dado, Cuando y Entonces, y
  cada historia tiene entre 11 y 28.
- Las 30 tarjetas del kit tienen el mismo texto que las tandas; solo cambia la línea del mockup. La
  lista final de decisiones de cada tarjeta coincide con los códigos D-n que cita.
- Mockups: en los 30, las etiquetas "Esc. N" cubren del 1 al último escenario sin números de más; en
  los 13 de catálogo cada etiqueta cae en el escenario de su panel. El PNG de HU-043 coincide con su
  HTML.
- Cargo: "profesional de estadística" en descripciones, reglas, escenarios, matriz, HU-001, mockups,
  modelo E-R y preguntas. Los nombres viejos solo quedan en registros y trazabilidad.
- Referencias entre historias exactas: HU-001 esc. 4 (uso sin sesión), HU-004 esc. 5 (solo
  ocupaciones hijas), HU-008 esc. 4 (diagnóstico con su categoría), HU-030 esc. 14, 15 y 20, HU-005
  esc. 11.
- D-25, D-26 y D-27 están en la hoja Decisiones, en estado Pendiente y como propuesta.
- Fuentes nuevas, cotejadas: la comunidad étnica de la hoja 15 es solo código (formato 999, 3 y 3) y
  dice "Según catálogo de referencia de comunidades étnicas, según catálogo de comunidades étnicas
  vigente"; el texto de la hoja 16 citado en HU-004 y HU-038 es literal; la hoja 18 pone el código de
  enfermedad huérfana como segunda fila del código del diagnóstico principal, con 4 y 1; la 1888 remite
  la ocupación a la CUOC 2024 y a PSRGOCUPACION (la CIUO no aparece) y nombra las tablas Pais,
  Municipio, Etnia, CategoriaDiscapacidad, CIE10, ViaIngresoUsuario y RIPSCausaExterna; la frase del
  CIE-11 de D-09 es literal; los artículos 3, 5 y 7 de la 2625, la cita de la 1895 en sus considerandos
  y los códigos con X (D71X, D67X) están en su texto; RC-6 copia literal el parágrafo 2 del artículo
  10.
- D-26 en la CIUO: el documento del DANE confirma los códigos por prefijo, con el ejemplo de HU-022 (2,
  22, 222 y "2221 Médicos").
- HU-030: las correspondencias de los antiguos HU-030 y HU-031 son exactas (10 de 10 cada una), y
  quedan resueltos los hallazgos de la revisión de la tanda 5 sobre el nombre en la atención, el
  rechazo en cascada cuando la categoría se rechaza, los ciclos, las X como supuesto, HU-042 sin
  cambio de código y el catálogo vacío que deja guardar el borrador.
- HU-043: motivos cerrados, confirmación con los datos de la atención, rastro con fecha, hora, nombre
  e identificación, nada se borra, permisos iguales a la matriz y a HU-001 esc. 6, y los supuestos
  dicen que la guía y el docente no lo piden.
- HU-004 y HU-007 sin la precondición de catálogos: el "Catálogo vacío" de las 13 historias de
  catálogo concuerda con HU-004 esc. 15 y HU-007 esc. 18, incluido el dato opcional de enfermedad
  huérfana (HU-042 esc. 15).
- El riesgo de quedar en 30 sin margen está escrito en el plan (sección 7) y en las preguntas 7 y 10.
- Lenguaje: no encontré guion largo ni intensificadores en tandas, kit, plan, preguntas, modelo E-R y
  mockups.

## 5. Lo que no pude verificar

- El contenido de las tablas de SISPRO (CIE10, Pais, Etnia, CategoriaDiscapacidad, Municipio,
  ViaIngresoUsuario, RIPSCausaExterna, PSRGOCUPACION): si la del CIE10 trae categorías de 3 caracteres,
  códigos con X, qué encabezados usa y si trae fecha; si la de países usa el código numérico. No intenté
  descargarlas; las revisiones anteriores reportaron que SISPRO bloquea la consulta automática.
- Si existe un archivo publicado del catálogo de comunidades étnicas.
- Si los textos de retroalimentación de HU-031 y HU-033 existen en Trello: la exportación llega hasta
  HU-030 y el tablero los marca "Sin verificar en Trello".
- Si el DANE publica la CIUO-88 A.C. en un formato de tabla (CSV o Excel). Solo encontré el documento
  PDF de 2006.
- El texto de la Resolución 1895 de 2001.
- El límite de caracteres de la descripción de Trello; la tarjeta más larga, HU-030, tiene 14.627.
- Los 29 PNG restantes frente a su HTML; solo abrí el de HU-043.
- El texto de la Resolución 2625 lo leí en una extracción que pierde la ligadura "fi"
  ("identi cación"); por eso cito solo frases que no la tienen.
- La pérdida de ceros al abrir un CSV en Excel es una conducta conocida; no la probé aquí.
- La longitud del nombre más largo de la tabla del CIE-10 (si alguno pasa de 200 caracteres).

## 6. Fuentes externas consultadas

- [DANE, Clasificación Internacional Uniforme de Ocupaciones Adaptada para Colombia, CIUO-88 A.C., 2006 (PDF)](https://www.dane.gov.co/files/sen/nomenclatura/ciuo/CIUO_88A_C_2006.pdf).
  Descargado y leído con pdftotext; citas de la presentación de la estructura y de la lista de
  códigos (gran grupo 0, grupos 1, 11, 2, 22, 222 y 2221).
- Las Resoluciones 866, 1888 y 2625 las leí en las copias del proyecto (`descarga_res866/res866.txt`,
  `descarga_res1888/anexo1888.txt`, `externos/res2625_raw.txt`) y el anexo de la 866 en los recortes de
  `scratchpad/anexo/` y `scratchpad/revisor/`.
