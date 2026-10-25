# Revisión independiente. Tanda 3 del backlog v3 (HU-010 a HU-017)

Archivo revisado: `/home/claude/ips-validacion/backlog-v3/backlog-v3-tanda-3-oposicion-y-voluntad.md`,
versión 1 del 2026-10-20 (última modificación 04:04 UTC; no cambió durante la revisión). El revisor
no escribió el texto. Los números de línea ("l.") son de ese archivo salvo que se diga otro.

Contexto: las tandas 0, 1 y 2 cambiaron mientras revisaba (04:21 y 04:22 UTC). Cito sus versiones
vigentes: tanda 0 v3, tanda 1 v3 y tanda 2 v2. La tanda 3 se escribió antes de esos cambios y por
eso conserva dos defectos que las otras ya corrigieron (hallazgos 1 y 5). No leí
`revisiones/revision-tanda-2.md`, para no sesgar esta revisión.

## 1. Veredicto

**Necesita cambios**: los 16 comentarios del docente quedan atendidos (12) o en parte (4) y el anexo está bien transcrito, pero las 8 historias tienen precondiciones que niegan sus propios escenarios, defecto que el docente ya objetó en HU-008, y hay 9 problemas de coherencia entre historias y reglas (20 hallazgos: 1 grave, 9 de gravedad media, 10 menores).

## 2. Cobertura de los comentarios del docente

Mockup y estimación no se cuentan (convención de la tanda 0).

| Historia | Comentario | Estado | Dónde |
|---|---|---|---|
| HU-010 | c1: obligatorios del esc. 9; valores para oposición, no oposición y sin manifestación; ausencia de registro no es "No" | Atendido | Reglas l. 45-53; esc. 4 (l. 82-86); esc. 1 a 3 (l. 65-80) |
| HU-010 | c2: quitar "si no está registrada no existe"; Sí o No y fecha del documento escrito; obligatorios; estructura separada con resultado | Atendido | Valor l. 36-37; reglas l. 45-51; esc. 9 (l. 109-113). La obligatoriedad no está marcada como inferencia (hallazgo 2) |
| HU-011 | c1: qué muestra el historial; cómo distinguir vigente, anterior y eliminada | Atendido | Regla l. 163-165; esc. 4 (l. 191-194) y 5 (l. 196-200) |
| HU-011 | c2: fecha del documento; permisos; historial; sin registro vigente no es falta de voluntad | Atendido | l. 161-162 y esc. 1-2; l. 167 y esc. 7-8; esc. 4-5; l. 166 y esc. 3 |
| HU-012 | c1: escenario de No a Sí; autor del registro anterior en el esc. 2 | En parte | El esc. 2 (l. 287-291) agrega el paso de No a Sí; el autor quedó en el esc. 1 (l. 285) y no en el esc. 2 nuevo (hallazgo 15) |
| HU-012 | c2: evidencia y fecha del documento nuevo; fecha, hora, nombre e identificación de quien corrige; corrección frente a cambio de voluntad; versiones | Atendido | Reglas l. 262-266; esc. 1 a 3; esc. 5 (l. 304-308). Ver hallazgos 4 y 5 |
| HU-013 | c1: diálogo, mensajes, estado posterior e historial (pedido para el mockup) | Atendido en lo que no es mockup | Esc. 3 (l. 401-404) y 5 (l. 411-415) |
| HU-013 | c2: motivo, permisos, fecha, hora y autor, estado anulado y consulta; anular no revoca la voluntad | Atendido | Esc. 1 (l. 389-393), 2, 5 y 9; regla l. 376 |
| HU-014 | c1: obligatorios y obligatoriedad condicional (esc. 2 y 9); validación de fecha y código del prestador | Atendido | Reglas l. 477-482; esc. 2 a 5 (l. 502-522) |
| HU-014 | c2: formato del código del custodio; documento, ausencia y desconocido; separación con resultado comprobable | Atendido | l. 481 y esc. 5; l. 483-484 y esc. 1, 2 y 6; esc. 10 (l. 546-550) |
| HU-015 | c1: "no tiene documento" distinto de "no se ha preguntado" y de "registro eliminado" | Atendido | Esc. 2, 3 y 6 (l. 616-640) |
| HU-015 | c2: vigente, modificación, sustitución y revocación con fechas y custodio; historial; ausencia de dato; permisos | En parte | Esc. 1 y 5 cumplen; el esc. 4 muestra la revocación sin custodio y HU-016 no define el prestador de una revocación (hallazgo 8); permisos l. 603 y esc. 8-9; ausencia l. 602. El historial no muestra correcciones (hallazgo 6) |
| HU-016 | c1: escenario del documento revocado; obligatorios del esc. 9; autor del anterior en el esc. 2 | En parte | Revocado: esc. 3 (l. 737-741). Obligatorios: el esc. 7 solo pide la fecha (hallazgo 9). Autor: esc. 1 (l. 729), no en el esc. 2 (hallazgo 15) |
| HU-016 | c2: modificación, sustitución y revocación con fechas, custodio y versiones; datos del corrector; un escenario por evento | En parte | Esc. 1 a 3 con fecha y versión; custodio solo en el esc. 2 y en el cambio del esc. 4; corrector en el esc. 6 (hallazgo 5) |
| HU-017 | c1: diálogo, estado sin registro vigente, cómo se conserva y consulta el historial (pedido para el mockup) | Atendido en lo que no es mockup | Esc. 3 (l. 855-858) y 5 (l. 865-869) |
| HU-017 | c2: anulación frente a revocación; evento, motivo, fecha y autor; historial y consulta; ausencia no prueba inexistencia | Atendido | Reglas l. 828-829 y esc. 3; esc. 1 (l. 843-847); esc. 5; regla l. 833 (solo como regla: el esc. 5 no lo dice) |

Total: 12 atendidos, 4 en parte, ninguno sin atender.

## 3. Hallazgos

### Lectura de las cuatro reglas del equipo (punto C)

| Regla | ¿Marcada como del equipo? | ¿Defendible? | Hallazgos |
|---|---|---|---|
| (1) Fecha obligatoria con "Sí"; con "No", solo si hay documento de retiro | En el encabezado sí (l. 18); en la tarjeta de HU-010 no | Sí: el numeral 8 define la oposición "mediante un documento escrito" | 2, 14 |
| (2) Retirar una oposición pide la fecha del documento nuevo | Sí (l. 338-339) | Poco: la Ley 1805 de 2016 no fija forma para retirar la oposición | 3, 4 |
| (3) Anular la versión vigente devuelve la anterior no anulada | Sí (l. 378-379 y 831-832) | Sí, con huecos: choca con HU-010 y HU-014 y le faltan casos | 7, 14 |
| (4) Revocar la voluntad anticipada es actualizar | En el encabezado (l. 21) | Sí: la hoja 14 pone la revocación en el mismo dato de fecha | 8 |

### Grave

**1. Las ocho historias tienen una precondición que su propio escenario niega**

- Ubicación: HU-012, precondición (l. 277) y esc. 7 (l. 316-320); HU-013 (l. 385) y esc. 8 (l. 427-430); HU-016 (l. 721) y esc. 9 (l. 770-774); HU-017 (l. 839) y esc. 8 (l. 881-884). Variante del paciente: HU-010 (l. 61) y esc. 7 (l. 99-102); HU-011 (l. 171) y esc. 9 (l. 218-221); HU-014 (l. 493) y esc. 8 (l. 536-539); HU-015 (l. 607) y esc. 10 (l. 658-661).
- Cita: HU-012, precondición "El paciente tiene una manifestación vigente (HU-010)"; esc. 7, "Dado que el paciente no tiene manifestación vigente".
- Por qué: una precondición es el estado que existe antes de usar la historia (Prompt 2, l. 262: "los estados o condiciones que deben cumplirse antes de ejecutar la funcionalidad"). Un escenario cuyo Dado la niega no puede ocurrir dentro de la historia, y la tarjeta se contradice. El docente objetó este mismo defecto en HU-008: "Ajustar la precondición «al menos una atención» para permitir el caso sin atenciones del escenario 3". El equipo ya lo corrigió en HU-006 (tanda 1 v3, registro: "sale la precondición que chocaba con el escenario 12") y en la tanda 2 v2 ("precondiciones que chocaban con escenarios"); la tanda 3 lo repite en las 8 tarjetas.
- Fuente: comentarios del docente, HU-008 c1 (l. 196); tablero, fila de HU-008 (`llenar_tablero.py`, l. 245); tanda 1 v3, HU-006 (l. 454-455) y registro (l. 563).
- Corrección: dejar como única precondición "El auxiliar de admisión tiene sesión iniciada (HU-001)" (en HU-011 y HU-015, "La persona tiene sesión iniciada"), como HU-005 y HU-006. Los escenarios ya cubren la falta de paciente o de registro.

### Media

**2. La obligatoriedad de la fecha y el documento de retiro aparecen en HU-010 como parte del numeral 8**

- Ubicación: HU-010, reglas (l. 45-51) y supuestos (l. 126-127).
- Cita: "Los datos son los dos del numeral 8 del anexo (hoja 14): [...] Obligatoria cuando la manifestación es "Sí". Con "No" solo se registra si el paciente trae un documento que retira una oposición anterior."
- Por qué: la hoja 14 no tiene columna de obligatoriedad, y su fecha es la "Fecha en que se suscribe el documento válido ante el Instituto Nacional de Salud o la Entidad Administradora del Plan de Beneficios"; un documento que retira la oposición no aparece en el anexo. La tabla del encabezado dice "Inferencia del equipo" (l. 18), pero esa tabla no va a Trello. En la tarjeta la regla queda bajo el título del numeral 8, y los supuestos solo nombran la regla de la fecha futura. El Prompt 2 pide: "Cuando una regla sea inferida, indícalo expresamente como supuesto pendiente de validación" (l. 87). La tanda 1 lo hace en HU-004 con la columna "¿Obligatorio? (D-19, propuesta)" (l. 168 y 291). No lo califico de grave porque los dos datos, sus valores y su formato coinciden con la hoja 14: lo que falta es la marca.
- Fuente: hoja 14 del PDF (página 2); tablero, l. 255 ("inferencia del equipo por la definición del numeral 8"); Prompt 2, l. 87 y 355.
- Corrección: sacar la obligatoriedad de la lista del numeral 8 a una regla propia con "(inferencia del equipo)" y repetirla en supuestos. En HU-014 (l. 482) basta citar el comentario 1 del docente, que propone esa obligatoriedad condicional.

**3. La regla (2) puede dejar vigente una oposición que el paciente ya retiró**

- Ubicación: HU-012, reglas (l. 263-265), esc. 3 (l. 293-296) y supuestos (l. 338-339).
- Cita: "Se crea una versión nueva con la manifestación y la fecha del documento nuevo, que es obligatoria".
- Por qué: la Ley 1805 de 2016, que el numeral 8 cita, fija la forma de oponerse (art. 4: "mediante un documento escrito que deberá autenticarse ante Notario Público y radicarse ante el Instituto Nacional de Salud (INS)", o al afiliarse a la EPS) y ninguna disposición fija la forma de retirarla; su única mención de revocar (art. 3, parágrafo 1) trata la voluntad de donar. Si el paciente retira su oposición sin un documento fechado, el esc. 3 rechaza el cambio y el sistema sigue mostrando "Se opone", que es lo que el valor de negocio de la misma historia dice evitar: "Si el sistema muestra la antigua como vigente, obra contra la voluntad del paciente" (l. 253-254). Crea además una asimetría: el "No" que se registra primero no pide fecha (HU-010, esc. 2) y el mismo "No" que llega desde "Sí" la exige.
- Fuente: [Ley 1805 de 2016, normograma del INVIMA](https://normograma.invima.gov.co/normograma/compilacion/docs/ley_1805_2016.htm), arts. 3 y 4; HU-012, l. 253-254. No revisé reglamentación del INS sobre el retiro; si existe un trámite escrito, citarlo respalda la regla.
- Corrección: de "No" a "Sí", fecha obligatoria; de "Sí" a "No", fecha cuando haya documento y cambio sin fecha cuando no lo haya, como el "No" de HU-010. Si el equipo prefiere la regla actual, llevarla como pregunta al docente.

**4. "Corregir" no tiene límite: cambia la voluntad sin documento y se cruza con "anular"**

- Ubicación: HU-012, reglas (l. 262-266); HU-013, reglas (l. 374-375); HU-016, regla (l. 711).
- Cita: "Hay dos casos y el auxiliar dice cuál es: [...] Corrección: un dato de la versión vigente se registró mal, por ejemplo la fecha."
- Por qué: ninguna regla dice qué datos se corrigen. Si el auxiliar marca un paso de "Sí" a "No" como corrección, el sistema cambia la manifestación sin la fecha del documento que exige la regla (2) y sin crear la versión "Anterior", así que el historial de HU-011 (l. 163-165) no muestra ningún cambio de voluntad. El mismo error (marcó "Sí" en vez de "No") cabe en "un dato mal registrado" (HU-012) y en "registros hechos por error" (HU-013), y los resultados difieren: la corrección deja la versión vigente con otro valor; la anulación la marca "Anulado" y devuelve la anterior (l. 378-379). En HU-016, corregir "No tiene documento" a "Tiene documento" se salta la suscripción con fecha y prestador del esc. 5.
- Fuente: comentario 2 del docente en HU-012, "Distinguir corrección de error de cambio de voluntad" (l. 286).
- Corrección: escribir que solo se corrigen la fecha y el código del prestador; un "Sí" o "No" mal registrado se anula (HU-013, HU-017) y se registra de nuevo.

**5. Los datos de quien corrige quedan con el valor anterior y no con el nuevo**

- Ubicación: HU-012, esc. 5 (l. 304-308); HU-016, esc. 6 (l. 754-758).
- Cita: "Y conserva la anterior con la fecha, la hora, el nombre y la identificación de quien corrigió".
- Por qué: el artículo 10, parágrafo 2, de la Resolución 866 dice "se agregará el nuevo dato con la fecha, hora, nombre e identificación de quien hizo la corrección" (cotejado en el normograma de la Supersalud). Con la redacción actual, el historial atribuye el valor equivocado a quien lo corrigió. La tanda 1 v3 cambió la misma frase en HU-006 esc. 2: "guarda el dato nuevo con la fecha, la hora, el nombre y la identificación de quien corrigió / Y conserva el anterior en el historial" (l. 468-469), y la tanda 2 v2 también ("datos del corrector junto al dato nuevo (RC-6)").
- Fuente: [Resolución 866 de 2021, normograma de la Supersalud](https://normograma.supersalud.gov.co/compilacion/docs/resolucion_minsaludps_0866_2021.htm), art. 10, par. 2; tanda 1 v3, l. 468-469.
- Corrección: copiar la redacción de HU-006 esc. 2 en HU-012 esc. 5 y HU-016 esc. 6.

**6. Ninguna consulta muestra las correcciones ni el cambio de prestador**

- Ubicación: HU-012 esc. 5 (l. 304-308); HU-016 esc. 4 (l. 743-747) y esc. 6 (l. 754-758); frente a HU-011, regla (l. 163-165), y HU-015, regla (l. 600-601).
- Cita: HU-015, "El historial muestra cada evento del numeral 9 (suscripción, modificación, sustitución, revocación) y cada anulación"; HU-016 esc. 4, "Y el anterior queda en el historial".
- Por qué: una corrección no crea versión (HU-012) ni evento (HU-016), y cambiar de prestador no es ninguno de los cuatro eventos del numeral 9. Con las reglas de HU-011 y HU-015 nada de eso aparece en pantalla, y los resultados de HU-012 esc. 5 y HU-016 esc. 4 y 6 no se pueden comprobar (Prompt 2, l. 308: "Expresar resultados observables por el usuario o por el negocio"). RC-6 en la tanda 0 termina con "el docente pide mostrar el anterior y el nuevo (comentario de HU-006)" (l. 88-90); las copias en HU-012 (l. 269-271) y HU-016 (l. 713-715) omiten esa frase. El docente pidió lo mismo para la consulta de atenciones (HU-008 c2: "completar fecha/hora/nombre/identificación del corrector junto con anterior/vigente"), y la tanda 2 v2 ya lo resolvió en HU-008 con una regla RC-6 (l. 242-243) y el escenario 7 "Historial de correcciones" (l. 283-287). Tampoco se sabe si el cambio de prestador pide fecha: la regla de versiones de HU-016 (l. 703-704) cubre solo los tres eventos.
- Fuente: tanda 0 v3, l. 88-90; comentarios del docente, l. 166 y 206; tanda 2 v2, HU-008.
- Corrección: en HU-011 y HU-015, regla y escenario "Historial con una corrección" (valor anterior y nuevo, fecha, hora, nombre e identificación de quien corrigió); en HU-015, agregar "cambio de prestador" a la lista del historial; en HU-016, decir si ese cambio lleva fecha.

**7. Después de anular, HU-013 y HU-017 piden aceptar un registro que HU-010 y HU-014 rechazan**

- Ubicación: HU-013 esc. 6 y 7 (l. 417-425) frente a HU-010 esc. 6 (l. 93-97); HU-017 esc. 6 y 7 (l. 871-879) frente a HU-014 esc. 7 (l. 530-534).
- Cita: HU-013 esc. 7, "Dado que la manifestación de un paciente se anuló / Cuando el auxiliar de admisión registra una nueva (HU-010) / Entonces el sistema la acepta como vigente"; HU-010 esc. 6, "Dado que el paciente ya tiene una manifestación vigente [...] Entonces el sistema no la registra".
- Por qué: si la versión anulada tenía una anterior sin anular, el esc. 6 la vuelve vigente; desde ese momento HU-010 esc. 6 rechaza el registro nuevo que HU-013 esc. 7 exige aceptar. Una prueba que siga las dos historias no puede pasar ambas. Lo mismo ocurre entre HU-017 y HU-014.
- Fuente: las cuatro historias citadas.
- Corrección: Dado de HU-013 esc. 7: "se anuló la única manifestación del paciente y no le queda una vigente"; igual en HU-017 esc. 7.

**8. "Revocado" no dice qué datos del numeral 9 guarda y deja casos sin camino**

- Ubicación: HU-015, regla (l. 598-599); HU-016, esc. 3 (l. 737-741); HU-014, reglas (l. 479-480); tabla del encabezado (l. 16).
- Cita: HU-016 esc. 3, "registra la revocación con su fecha / Entonces el sistema deja el documento como "Revocado""; encabezado, "Qué guarda el sistema: [...] Sí o No, la fecha del documento y, en la voluntad anticipada, el prestador donde está".
- Por qué: la hoja 14 tiene tres componentes: documento (01 Sí, 02 No), la fecha "de suscripción, modificación, sustitución o revocación" y el código del prestador (12 caracteres). La tanda dice que guarda esos tres, pero ninguna historia dice qué valor de Sí o No ni qué prestador lleva un documento revocado; el esc. 3 pide solo la fecha. Quien desarrolle tendrá que elegir entre "01 Sí con fecha de revocación" y "02 No". Quedan además tres casos sin camino: (a) un paciente con "Revocado" que firma un documento nuevo: HU-014 esc. 7 lo manda a HU-016 (l. 534) y HU-016 solo tiene la suscripción desde "No tiene" (esc. 5); (b) HU-014 admite como primera fecha la de la última modificación o sustitución (l. 479-480) pero no guarda qué evento fue, y HU-015 esc. 5 muestra los eventos por tipo; (c) HU-014 quita "revocación" del dato de fecha, así que un documento ya revocado cuando el paciente llega no se puede registrar, aunque la fila del tablero la incluía ("de firma, cambio, reemplazo o revocación", l. 271).
- Fuente: hoja 14 del PDF (página 2); tablero, l. 271.
- Corrección: regla en HU-016: "Revocado guarda Documento = [01 o 02, decisión del equipo], la fecha de revocación y [qué prestador]"; escenario "De Revocado a documento nuevo"; en HU-014, el tipo de evento de la primera fecha.

**9. HU-016 no enumera los datos obligatorios de cada evento ni valida el prestador nuevo; las actualizaciones aceptan fechas futuras**

- Ubicación: HU-016, esc. 7 (l. 760-763), regla (l. 709), esc. 1 (l. 725-729) y esc. 3 (l. 737-741); HU-012 y HU-016 frente a HU-010 esc. 5 (l. 88-91) y HU-014 esc. 4 (l. 514-517).
- Cita: HU-016, "Escenario 7: Evento sin fecha"; regla, "el código nuevo tiene 12 caracteres".
- Por qué: el docente pidió "Enumerar campos obligatorios del escenario 9" (HU-016 c1) y el tablero lo recoge ("Escenario 9: decir cuáles datos son obligatorios", l. 283). El antiguo esc. 9 cubría cualquier dato obligatorio; el nuevo esc. 7 solo la fecha. Una sustitución sin prestador, o con un código de 10 caracteres, no tiene resultado esperado, y la modificación y la revocación no dicen si piden prestador. Aparte, HU-010 y HU-014 rechazan una fecha posterior al día del registro, y HU-012 y HU-016 no tienen esa regla: un cambio de voluntad fechado el año siguiente se acepta.
- Fuente: comentarios del docente, l. 356; tablero, l. 283; Prompt 2, l. 274 ("Datos inválidos").
- Corrección: en HU-016, una línea por evento (modificación: fecha, conserva el prestador; sustitución: fecha y prestador; revocación: según el hallazgo 8) y un escenario "Código del prestador que no cumple su numeral"; llevar la regla de la fecha futura a HU-012 y HU-016.

**10. Trazabilidad: tres correspondencias equivocadas y criterios que se perdieron**

- Ubicación: HU-012 (l. 349-350), HU-013 (l. 452) y HU-017 (l. 907); HU-016 (l. 802-803); HU-013 (l. 450) y HU-017 (l. 905); HU-010 (l. 135-136); HU-012 (l. 348); comentarios en HU-015 (l. 673-674) y HU-016 (l. 795-798).
- Cita: HU-012, "el 10 (paciente que no existe) queda en la precondición: sin paciente no hay manifestación que actualizar (escenario 7)".
- Por qué:
  - El antiguo esc. 10 de HU-012, HU-013 y HU-017 (paciente que no existe; fuente 7, l. 314-317, 402-405 y 763-766) no está en los esc. 7 u 8 nuevos: esos prueban un paciente que existe y no tiene registro, y sus resultados ("ofrece registrarla (HU-010)", "no hay nada que anular") no sirven para un paciente inexistente; HU-011 esc. 9 y HU-015 esc. 10 dicen "el paciente no existe". Una precondición no es un criterio de aceptación, y el docente pidió "conservando los CA" (l. 284 y 304) y, en HU-017, "Conservarlos y precisar resultados" (l. 382).
  - HU-016, "el 7 (atribución) lo cubre RC-5": ningún escenario de HU-016 dice quién registró el evento nuevo (el esc. 1 dice quién registró el anterior). "el 9 es el 7": el antiguo 9 cubría cualquier dato obligatorio; el 7 solo la fecha (hallazgo 9).
  - HU-013 y HU-017, "el 2 es el 3": el antiguo 2 afirmaba "no elimina nada hasta que el auxiliar la confirme" (fuente 7, l. 362-365); el 3 nuevo afirma el aviso, otra cosa.
  - Imprecisas, sin pérdida: HU-010, "el 4 [...] pasa a fecha del documento en los escenarios 1 y 2" (el esc. 2 no registra fecha); HU-012, "el 6 (atribución) lo cubre RC-5" (está en el esc. 1, l. 284).
  - La parte de comentarios afirma de más en tres casos: HU-015 c2, "con fechas y custodio: escenarios 1, 4 y 5" (el 4 no trae custodio); HU-016 c2, "con fechas, custodio y versiones: escenarios 1 a 4" (custodio solo en el 2 y el 4); HU-016 c1, "campos obligatorios [...]: escenario 7" (solo la fecha).
  - Correctas: las 10 correspondencias de HU-011, HU-014 y HU-015.
- Fuente: backlog anterior (fuente 7), líneas citadas; comentarios del docente, l. 284, 304 y 382.
- Corrección: con el hallazgo 1 resuelto, devolver "Paciente que no existe" a HU-012, HU-013 y HU-017 con el resultado de HU-011 esc. 9; en HU-016, agregar quién registró el evento nuevo; en HU-013 y HU-017 esc. 3, "Y no anula nada hasta que el auxiliar confirme"; ajustar las líneas imprecisas.

### Menor

**11. Las etiquetas de estado cambian entre la regla, los escenarios y las historias paralelas**

- Ubicación: HU-011, regla (l. 161-162) y esc. 1 y 2 (l. 178 y 183); HU-010 esc. 3 (l. 80); HU-011 esc. 3 (l. 189) frente a HU-015 esc. 3 (l. 624) y HU-014 (l. 483-484); encabezado (l. 17).
- Cita: regla de HU-011, "La consulta muestra la manifestación vigente ("Sí", "No" o "Sin registro")"; esc. 1, "el sistema muestra "Se opone""; HU-015 esc. 3, "Sin registro: no se ha preguntado".
- Por qué: quien pruebe con la regla espera "Sí"; quien pruebe con el escenario espera "Se opone". "No se ha preguntado" afirma algo que el sistema no sabe (pudo preguntarse sin registrarse, o el paciente no quiso responder); HU-011 dice "no se sabe" y el encabezado admite las dos causas (l. 17). "Sin registro vigente" falta en las listas de situaciones de HU-011 (l. 161-162), HU-015 (l. 598-599) y el encabezado ("Tres situaciones"), y "Revocado" falta en el encabezado. En la oposición las versiones se llaman "Vigente", "Anterior" y "Anulado"; en la voluntad anticipada no se nombran.
- Fuente: las líneas citadas; HU-015, l. 602.
- Corrección: una sola lista de etiquetas por estructura, usada igual en reglas y escenarios; "Sin registro: no se sabe si tiene documento".

**12. Tres frases se pueden leer al revés**

- Ubicación: HU-011 esc. 5 (l. 197) y HU-015 esc. 6 (l. 637); HU-010 esc. 2 (l. 74); HU-012 esc. 5 (l. 305).
- Cita: "la única manifestación del paciente se anuló por error"; "no pide la fecha del documento"; "la fecha de la manifestación vigente se registró mal".
- Por qué: "se anuló por error" dice que el error fue anular. "No pide" se puede entender como que el campo no aparece, y la regla (l. 50-51) admite la fecha con "No" cuando hay documento de retiro. "Fecha de la manifestación" es el término que el tablero mandó cambiar por "fecha del documento" (l. 255).
- Fuente: líneas citadas; tablero, l. 255.
- Corrección: "se anuló porque se había registrado por error"; "no exige la fecha"; "la fecha del documento de la versión vigente".

**13. Escenarios de validación: un título mezcla el anexo con una regla del equipo y varios resultados no dicen qué se ve**

- Ubicación: HU-010 esc. 5 (l. 88-91) y HU-014 esc. 4 (l. 514-517); HU-010 esc. 7 (l. 102); HU-014 esc. 5 (l. 522); HU-012 esc. 3 (l. 296); HU-016 esc. 7 y 8 (l. 763 y 768).
- Cita: título "Fecha que no cumple su numeral", con el Dado "o es posterior al día del registro"; resultado "Entonces el sistema la rechaza".
- Por qué: el título hace creer que el numeral prohíbe fechas futuras; es regla del equipo (supuestos, l. 126-127 y 563-564). Los resultados sin mensaje son los "resultados pendientes o genéricos" que el docente pidió precisar en cada c2; HU-010 esc. 4 ("Y dice cuál falta") y HU-012 esc. 4 sí dicen qué se ve. HU-010 esc. 7 perdió el mensaje del antiguo esc. 6: "Y señala que el paciente debe registrarse primero" (fuente 7, l. 116).
- Fuente: comentarios del docente, c2 de cada historia; tanda 1, HU-004 esc. 9 ("Y dice cuál dato está mal").
- Corrección: título "Fecha no válida", con la regla del equipo nombrada en el Dado; agregar a cada rechazo un "Y dice [...]".

**14. Casos de las reglas (1) a (3) sin resolver**

- Ubicación: HU-010 (l. 50-51 y esc. 2); HU-012 esc. 6 (l. 310-314) y reglas (l. 263-267); HU-013 y HU-017, regla (l. 378-379 y 831-832) y esc. 6.
- Cita: HU-013, "Si la versión anulada era la vigente y había una anterior, la anterior vuelve a ser la vigente (regla del equipo)".
- Por qué:
  - Regla (1): HU-010 admite "No" con la fecha de un documento de retiro, pero ningún escenario lo prueba; HU-011 esc. 2 promete mostrar esa fecha "si se registró una".
  - HU-012 esc. 6 rechaza siempre una fecha vacía, también al corregir un "No" sin documento, que HU-010 permite.
  - Regla (3): la regla no dice "no anulada" (el esc. 6 sí) ni cuál vuelve si hay varias anteriores. "Si la versión anulada era la vigente" supone que también se puede anular una versión "Anterior", y ningún escenario dice qué pasa entonces.
  - Regla (2): no se dice qué pasa si el paciente firma un documento nuevo con el mismo valor (renueva su oposición). La regla de orden de fechas (l. 267) no cubre las correcciones: corregir la fecha de la vigente a una anterior a la de la versión "Anterior" rompe el orden que protege el esc. 4.
- Fuente: las líneas citadas.
- Corrección: regla (3) como "vuelve la anterior más reciente que no esté anulada"; decir si se anula una versión "Anterior"; un escenario de "No" con documento de retiro; limitar el esc. 6 de HU-012 al cambio de voluntad.

**15. El autor de la versión anterior está en el escenario 1 y falta en el 2**

- Ubicación: HU-012 esc. 2 (l. 291); HU-016 esc. 2 (l. 735).
- Cita: HU-012 esc. 2, "Y la versión anterior queda como "Anterior"".
- Por qué: el docente pidió el autor del registro anterior "en el escenario 2" (HU-012 c1, l. 276; HU-016 c1, l. 356) y el tablero lo repite (l. 264 y 282). La renumeración lo dejó en el esc. 1; el esc. 2 nuevo (de No a Sí en HU-012, sustitución en HU-016) no lo trae. Si el docente busca el escenario 2, no lo encuentra.
- Fuente: comentarios del docente, l. 276 y 356; tablero, l. 264 y 282.
- Corrección: agregar "con su fecha y con quién la registró" al esc. 2 de las dos historias.

**16. Dependencias incompletas**

- Ubicación: HU-011 esc. 6 (l. 203) y HU-015 esc. 7 (l. 643) usan HU-005; HU-010 esc. 3 y 9 (l. 78 y 113) y HU-014 esc. 6 y 10 (l. 526 y 550) usan HU-011 y HU-015. Dependencias en l. 125, 228, 562 y 668.
- Cita: HU-011, "Dependencias: HU-001, HU-004 y HU-010."
- Por qué: HU-013 y HU-017 sí nombran la consulta que usan sus escenarios (l. 442 y 896), y la tanda 1 escribe "HU-008 para el escenario 14" (HU-006, l. 538). Sin esa línea no se sabe en qué orden se puede probar cada escenario.
- Corrección: "HU-005 para el escenario 6" en HU-011, y lo mismo en las otras tres.

**17. Cuatro escenarios repiten los de otra historia**

- Ubicación: HU-010 esc. 3 y HU-011 esc. 3; HU-013 esc. 5 y HU-011 esc. 5; HU-014 esc. 6 y HU-015 esc. 3; HU-017 esc. 5 y HU-015 esc. 6.
- Cita: HU-013 esc. 5 y HU-011 esc. 5 tienen el mismo resultado: "Sin registro vigente" y el historial con el registro anulado y su motivo.
- Por qué: el Prompt 2 pide que no haya "duplicidad significativa entre historias" (l. 358). Se defienden como "Efectos posteriores en el proceso" (l. 283), pero hoy prueban lo mismo. HU-011, HU-013 y HU-017 tienen 10 escenarios justos, así que quitar uno las deja bajo el mínimo.
- Corrección: en HU-013 y HU-017, probar la transición (estado antes y después de anular); en HU-011 y HU-015, la presentación.

**18. La tanda 0, RC-7 y D-20 no acompañan a la tanda 3**

- Ubicación: tanda 0 v3, actores (l. 30) y matriz (l. 66); tanda 1, matriz de HU-001 (l. 40) y esc. 5 (l. 80); HU-012, reglas (l. 261-273); `llenar_tablero.py`, D-20 (l. 462).
- Cita: matriz, "Registrar, actualizar y anular la oposición y la voluntad anticipada".
- Por qué: HU-012 y HU-016 dejan corregir al auxiliar, pero la matriz y HU-001 no listan "corregir" para estas estructuras. RC-7 dice "La tarjeta nombra el numeral y la hoja" (tanda 0, l. 91-92), y HU-012 no nombra el numeral 8 ni la hoja 14. D-20 dice en el tablero que en las estructuras separadas "Eliminar" se hace "como inactivación", y HU-013 y HU-017 anulan. "Anular" tiene respaldo directo del docente ("Anular oposición registrada por error es funcional", l. 306), mejor fuente que D-20, que sigue pendiente.
- Corrección: agregar "corregir" a la fila de la matriz y a HU-001 esc. 5; "(numeral 8, hoja 14)" en HU-012; ajustar el texto de D-20 y citar el comentario del docente en HU-013 y HU-017.

**19. La prioridad de HU-017 no sigue a su valor de negocio**

- Ubicación: HU-017, valor (l. 819-820) y prioridad (l. 822).
- Cita: "Un registro falso de voluntad anticipada puede llevar a decisiones clínicas sobre un documento que no existe." y "Prioridad: Media".
- Por qué: el Prompt 2 pide asignar la prioridad "con base en el valor de negocio, la criticidad" (l. 237); el valor describe un riesgo clínico y la prioridad es Media. El riesgo además está sobredimensionado: el sistema guarda la constancia y no el documento (l. 485), así que el médico pide el documento al prestador antes de actuar, y el daño concreto es buscar un documento que no existe.
- Corrección: escribir ese riesgo concreto y dejar Media, o subir a Alta con su justificación.

**20. Separación de historias (punto I): no fusionar ni separar; corregir la justificación**

- Ubicación: encabezado (l. 7-10); HU-011, descripción (l. 148-150) y valor (l. 152-153); HU-016.
- Cita: "cada operación tiene un estado o un actor distinto".
- Por qué: la separación se sostiene con el texto del Prompt 2: actualizar y anular dejan estados distintos ("Anterior" o "Revocado" frente a "Anulado": "Se presenten ciclos de vida o estados distintos", l. 327); registrar la primera manifestación y cambiar de voluntad son "flujos de negocio independientes" (l. 324); consultar tiene permisos propios ("Existan permisos [...] diferenciados", l. 326: el médico consulta y no escribe) y entrega valor por sí sola (l. 325). El docente calificó cada una de las ocho como "funcional y actor válido". La justificación por actor falla en HU-011 y HU-015 tal como están escritas: el "Como" es el auxiliar y el valor de HU-011 es la necesidad de "Quien deba decidir sobre una donación", lo que el Prompt 2 pide evitar ("Evitar mezclar necesidades de actores diferentes", l. 195). HU-016 es la más amplia (cinco comportamientos en 12 escenarios) y cabría partirla por "excesivamente amplia" (l. 328), pero el docente pidió esos criterios dentro de ella (HU-016 c2, l. 366). Fusionar una estructura en una sola historia bajaría el backlog de 31 (tanda 0, l. 169) a 28, bajo el mínimo de 30 de la guía [2.5]; ese motivo no va en las tarjetas porque el Prompt 2 no lo reconoce.
- Corrección: cambiar el encabezado a "cada operación deja un estado distinto, tiene permisos distintos o entrega valor por separado"; devolver a HU-011 un "Para" del auxiliar, como el de la versión anterior: "Para saber si esa voluntad ya está en el sistema o hay que preguntársela" (fuente 7, l. 158).

## 4. Lo que está bien y no se debe tocar

- Fidelidad al anexo (punto B): los valores Sí y No, la fecha AAAA-MM-DD de 10 caracteres, el código del prestador de 12 y los cuatro eventos de la fecha del numeral 9 coinciden con la hoja 14 (página 2 del PDF, cotejada a 200 ppp). Las tarjetas no escriben los códigos 01 y 02 y los dejan a RC-7, como manda la convención de la tanda 0. Ningún dato atribuido al anexo es ajeno a él.
- "Sin registro" separado de "No" en las cuatro historias de registro y consulta, y el valor de negocio de HU-010 corregido. Encaja con la Ley 1805, art. 4, parágrafo: ante la duda, "el médico tratante tendrá la obligación de consultar el Registro Nacional de Donantes".
- Anular en lugar de eliminar, con motivo, autor, fecha y hora, estado "Anulado" visible y el aviso del esc. 3 que separa anular de cambiar la voluntad o revocar: responde a los c2 de HU-013 y HU-017.
- Revocar como actualización con su propia fecha (HU-016 esc. 3), respaldado por la hoja 14, y un escenario por evento en HU-016.
- "El sistema guarda la constancia, no el documento" cierra el pendiente 1 de la tanda anterior con una razón del anexo: el numeral 9 pide el "Código del Prestador donde se encuentra el documento".
- La cita de RC-6 coincide palabra por palabra con el artículo 10, parágrafo 2, de la Resolución 866.
- Permisos (punto E): el médico consulta sin opciones de cambio, el administrador de catálogos no consulta (HU-011 esc. 8, HU-015 esc. 9) y cada historia de escritura prueba que el médico no puede.
- Campos del Prompt 2 completos en las ocho tarjetas (punto D); escenarios por historia: 11, 10, 10, 10, 12, 11, 12 y 10.
- Estilo (punto H): sin guion largo ni las palabras vetadas (búsqueda en el archivo).
- Trazabilidad de HU-011, HU-014 y HU-015: las 10 correspondencias de cada una son correctas.

## Lo que no pude verificar

- Si la tabla "Decisiones de esta tanda" viaja a Trello. Supuse que no, porque la plantilla de la tanda 0 (l. 171-207) no la incluye.
- Reglamentación del INS o del Ministerio sobre cómo se retira una oposición; solo revisé la Ley 1805 de 2016.
- El tablero en Excel: usé `llenar_tablero.py`, como pedía el encargo.
- El contenido actual de las tarjetas en Trello.

Fuentes externas consultadas:

- [Resolución 866 de 2021, normograma de la Superintendencia Nacional de Salud](https://normograma.supersalud.gov.co/compilacion/docs/resolucion_minsaludps_0866_2021.htm), artículo 10, parágrafo 2.
- [Ley 1805 de 2016, normograma del INVIMA](https://normograma.invima.gov.co/normograma/compilacion/docs/ley_1805_2016.htm), artículos 3 y 4.
