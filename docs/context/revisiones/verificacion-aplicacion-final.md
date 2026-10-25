# Verificación de la aplicación de la revisión final cruzada

Verificador independiente, 2026-10-20. No escribí ninguno de los textos revisados y no modifiqué
archivos del proyecto: este informe es el único archivo nuevo. "l." es el número de línea en los
archivos como estaban al verificar: tandas 1 y 3, tarjetas, LEEME y respuestas (06:43); tandas 0, 2,
4 y 5, preguntas y modelo E-R (06:24 a 06:29); plan (06:36); tablero (06:38). No usé
`mapa-anexo-vs-backlog.md`.

Método:

- Leí `revision-final-cruzada.md` y la sección "Revisión final cruzada" de
  `aplicacion-revisiones-tandas-4-y-5.md`, y comprobé cada fila contra los archivos actuales.
- Conté con un script los escenarios de las 30 historias ("### Escenario N", con Dado, Cuando y
  Entonces) y comparé cada tarjeta de `trello/tarjetas/` con su sección de la tanda, sin la línea del
  mockup ni la trazabilidad.
- Comparé con diff las copias de `antes-cuando/` con las tandas 1 y 3 y leí completo cada escenario
  que cambió.
- Crucé las etiquetas "Esc. N" y las notas de los 30 mockups con los títulos de los escenarios, y
  abrí 2 PNG.
- Leí con openpyxl (data_only=True) las hojas Leeme, Backlog v3, Reglas comunes, Decisiones, Ajustes
  por historia, Retroalimentación y Escenarios.
- Cotejé las citas de esta ronda en los textos de la Resolución 866 (`descarga_res866/res866.txt`),
  del anexo de la 1888 (`externos/res1888_anexo_raw.txt`), de la 2625 (`externos/res2625_raw.txt`) y
  de la CIUO-88 A.C. del DANE (`revfinal_ciuo/ciuo88ac.txt`), y la hoja 16 en la página 4 del PDF
  del anexo.

## 1. Veredicto y TL;DR

**Lista con ajustes menores.** Los ajustes son locales: una frase de regla, un escenario de
rechazo, cifras del plan y textos de mockup. Ninguno obliga a rehacer una historia, pero los tres de
gravedad media conviene cerrarlos antes de pasar las tarjetas a Trello y de enviar las preguntas.

TL;DR:

- De los 30 hallazgos, 29 están corregidos y 1 en parte (A2): falta el escenario de rechazo que pidió
  la revisión y RC-10 deja reactivar con solo un motivo un código creado por cambio oficial que una
  versión posterior ya no trae.
- Los conteos cuadran: 30 historias, 543 escenarios (55, 57, 100, 196 y 135 por tanda) y 13 tarjetas
  archivadas. Las 30 tarjetas y la de reglas comunes dicen lo mismo que las tandas. Las 13 notas de
  archivo son iguales a la trazabilidad, y las correspondencias que muestreé caen en escenarios que
  dicen lo que se afirma.
- Los 44 Cuando reescritos son acciones del cargo que la matriz permite y no cambian lo que prueba
  cada escenario. Quedan 3 Cuando impersonales ("termina la consulta") en HU-005, HU-011 y HU-015, y
  la corrección de B11 dejó sin sujeto el Entonces de HU-004 esc. 11.
- Errores nuevos o residuales de gravedad media: la regla de cantidades por nivel de HU-022 no dice
  qué filas cuenta y su mockup muestra 520 filas leídas con "Guardar las aceptadas" junto a la nota
  "no cuadra y no se guarda"; la tabla del plan B suma 35 a 39 historias y el plan y la pregunta 1 al
  docente dicen "entre 32 y 36"; el caso abierto de A2.
- Sin guion largo ni los cinco intensificadores en los textos revisados.

## 2. Estado de cada hallazgo

### 2.1 Tabla

| Hallazgo | Estado verificado | Evidencia |
|---|---|---|
| A1. RC-8 rechazaba los niveles de arriba | Corregido | Tanda 0, RC-8 l.106-108: "En los catálogos con niveles (ocupaciones y CIE-10), las filas de los niveles de arriba se validan con la regla de su historia, no con la longitud del numeral, y ese aviso no aplica"; igual en `RC-reglas-comunes.md` l.29. HU-022 l.336-341 y esc. 7 "Cantidades por nivel en la primera carga" (tanda 4 l.395-399); HU-030 l.82-84. Lo que la corrección dejó abierto está en N1 |
| A2. "Estar en la versión vigente" | En parte | Hecho: RC-10 l.131 "Un código está en la versión vigente si vino en su archivo, aunque su fila se haya rechazado" y l.132-134; modelo E-R l.88-90; esc. "Reactivación" de las 13 historias (p. ej. HU-018 esc. 20, tanda 4 l.228-231) y "Reactivación sin respaldo" con "por ejemplo porque una versión nueva lo retiró" (l.233-236). Falta el escenario de rechazo y queda un caso sin respaldo (N3) |
| M1. Artículo 10 | Corregido | Tanda 2: HU-007 l.71-73 ("Mientras la atención es borrador, cambiar un dato no es una corrección (D-08, propuesta)"); HU-009 l.401-403; D-25 l.21 con "de forma permanente" literal; HU-043 l.550-555 con "Otra inconsistencia", con su explicación, y "Anular no borra" sin el artículo 10. Cotejado en `res866.txt` l.406-429. No queda "datos son permanentes" en ningún archivo |
| M2. RC-11 sin escenario donde se ve | Corregido | HU-005 esc. 13 (tanda 1 l.443-447) y HU-008 esc. 15 (tanda 2 l.347-351). El escenario de cambio de nombre de las 13 historias remite a HU-005, a HU-008 o a las dos según dónde se registra el valor (HU-018 l.176, HU-022 l.435, HU-030 l.174). Mockups HU-005 y HU-008 con "Esc. 13" y "Esc. 15" |
| M3. HU-004 esc. 16 | Corregido | HU-004 tiene 15 escenarios. Regla l.209-213 con la cita de la hoja 16, literal (página 4 del PDF), y "el sistema no tiene un dato que diga "particular""; ayuda en el campo del mockup HU-004. Queda una mención vieja en la tanda 4 (N6) |
| M4. Listas del anexo, esc. 2 y 3 | Corregido | Regla de las 6 listas: "La primera carga se compara con la hoja contando solo las filas aceptadas" (p. ej. tanda 5 l.499-500); esc. 2 "las filas aceptadas de la primera carga no traen uno de los 9 códigos" (l.526). Mockups HU-034 y HU-039: el aviso "No se puede guardar hasta que traigan los 14/9" va como "Otro archivo", aparte del panel que se puede guardar |
| M5. Primera carga parcial | Corregido | RC-9 l.119-121: "una recarga de la versión vigente, si su fecha es la misma: el sistema solo crea los códigos que faltan". Esc. 2 de los 7 catálogos externos: "si guardó las aceptadas, puede subir el archivo corregido de la misma versión" (HU-018 l.129, HU-022 l.371, HU-030 l.116, igual en HU-026, HU-037, HU-038 y HU-042). Las 6 listas no lo llevan porque su esc. 2 no deja guardar una carga incompleta |
| M6. Precondición de HU-009 | Corregido | Tanda 2 l.422: "La atención existe (HU-007)" |
| M7. Bloqueo de valores en uso | Corregido | Trazabilidad de HU-018 (tanda 4 l.273-277), HU-022 (l.558-563), HU-026 (l.811-813) y HU-030 (tanda 5 l.295-299): "el valor en uso ya no se bloquea [...] lo que se bloquea es inactivar sin respaldo". Respuestas l.115, l.125 y l.135; nota de archivo de HU-033 (LEEME l.92); pregunta 6 (l.15) |
| M8. Trazabilidad de HU-030 | Corregido | Cabecera de la tanda 5 l.8-11 (HU-032 sin retroalimentación; "propuesta del equipo"); l.291-299 (quién y cuándo en el 28; "21 y 23"); esc. 7 l.146 "la versión nueva queda vigente"; antiguo esc. 6 de HU-032 "cambia" (l.309-311); historial de versiones en el esc. 14 (l.188 y l.322-323). Cotejé las 30 correspondencias de HU-030, HU-032 y HU-033 con la hoja Escenarios, filas 295 a 334 |
| M9. Respuesta que remitía a la trazabilidad | Corregido | Respuestas l.145-150 traen las correspondencias de HU-030, HU-032 y HU-033, y l.10-13 las de HU-001 a HU-003. Las 13 notas del LEEME (l.80-92) son iguales, palabra por palabra, a la trazabilidad de las tandas y están también en las respuestas. Hueco menor en HU-004 (N13) |
| M10. Plan B del CIE-10 | Corregido | Tanda 5 l.272-276: las categorías se cargan "como una fuente propia con su versión" y "la comprobación y las versiones de la tabla de SISPRO no las cuentan como sobrantes ni como retiradas". Efecto lateral en N8 |
| M11. Comunidad étnica sin catálogo | Corregido | HU-004 l.195 ("y el catálogo de comunidades tiene valores (HU-037)") y l.324-325; tablero D-19 (celda D20); mockup HU-004: "El catálogo de comunidades étnicas no tiene valores: el registro sigue sin ese dato (HU-037)"; pregunta 10 |
| B1. "Diez" por trece | Corregido | Tanda 0 l.76 "trece tarjetas"; tarjeta RC l.20; preguntas 4, 6 y 11: "13 historias de catálogo" |
| B2. D-25 y las hojas | Corregido | Tanda 4 l.44, tablero D-25 (I26) y pregunta 13: "numerales 1, 7, 11, 12, 15 y 23", que son los que tienen componente de nombre en el anexo de referencia |
| B3. Artículo 8 | Corregido | Tanda 4 l.42 y tablero D-21 (D22): "definirá el procedimiento para individualizarlos en SISPRO". Artículo cotejado (`res866.txt` l.366-372) |
| B4. "Texto libre, no un código" | Corregido | HU-037 l.1621-1624 cita "13.2 Comunidad étnica Texto Alfanumérico" y "Diligenciar con el nombre de la comunidad étnica [...] cuando aplique" y marca la lectura del equipo; tanda 4 l.42; pregunta 10. Citas en `res1888_anexo_raw.txt` l.734 y l.906-907 |
| B5. D-02 como hecho | Corregido | Tanda 0 l.32 y tanda 4 l.39: "se supone que [...] no hay fuente"; tablero D-02 (D3); pregunta 4 |
| B6. "Manda la hoja" | Corregido | D-21 (tanda 4 l.42 y tablero): "si el archivo no coincide con la hoja, manda la hoja y se guardan sus códigos y nombres"; la regla de las 6 listas lo repite y su esc. 2 dice "guarda el de la hoja". Ver N14 |
| B7. [2.2] | Corregido | HU-030 l.76 y HU-031 l.351-353: [2.2] se cita para "la jerarquía padre e hijo que pide la guía"; los 4 caracteres van con el numeral 23 y D-22 |
| B8. Lecturas como texto de la fuente | Corregido | HU-042 l.1275-1277: el artículo 3 dice que el número "es exclusivo y no puede ser asignado a ninguna otra" y "que una enfermedad no cambie de número es lectura del equipo" (cotejado, `res2625_raw.txt` l.79-84); D-26 del CIE-10 como inferencia (tanda 5 l.33); D-26 de la CIUO con la cita del DANE (tanda 4 l.45; `ciuo88ac.txt` l.405-406); HU-037 "el nombre lo traería" (l.1467). Queda el modelo E-R (N11) |
| B9. Reglas sin escenario | Corregido | RC-9 l.122: "un archivo rechazado, si su fecha es anterior o futura", y esc. 3 de las 13 historias con "su fecha de publicación es futura"; motivos "Vuelve en la versión", "Reemplazado por" y "No está en la versión" (l.117-118); el Dado de "Inactivación sin motivo o sin respaldo" trae "eligió "Retirado por la fuente oficial" sin la referencia" |
| B10. Escenario repetido en HU-043 | Corregido | HU-043 tiene 10 escenarios, sin el que repetía HU-008 esc. 14; esc. 3: "lo deja en estado "Anulada"" (tanda 2 l.583); D-27 con los borradores (l.22 y tablero D28); RC-10 l.135-136 cuenta borradores y anulados |
| B11. Cuando sin acción del usuario | Corregido | HU-004 esc. 11 (l.284) y HU-008 esc. 5, 6, 8, 9 y 10 con el actor en el Cuando; HU-007 esc. 18 y 19 (l.187-197); 44 Cuando de HU-001, HU-005, HU-006 y HU-010 a HU-017 (sección 2.2). La corrección de HU-004 esc. 11 dejó un error nuevo (N4) y quedan 3 Cuando impersonales (N5) |
| B12. HU-031 sin RC-1 | Corregido | Tanda 5 l.359-360: RC-1 copiada, con "El del CIE-10 se mantiene en HU-030" |
| B13. Celdas, plan y kit desactualizados | Corregido | Hoja Reglas comunes, C1: "Historias del backlog anterior que la citan (las del v3 están en las tandas)", y la fórmula cuenta en Historias!K:K; Leeme B19 "RC-1 a RC-11"; Ajustes fila 100 sin columna de padre; D-22 C23 "HU-007 escenario 9", que es "Código de categoría padre"; plan l.134 "T2 atención (4)" y "T5 [...] (6)"; `trello/mockups/` con 30 PNG idénticos byte a byte a `mockups/png/`. Quedan celdas viejas del mismo tipo (N12) |
| B14. Mockups | Corregido | Sin "los atenciones" en los 30 HTML; HU-030 "Rechazadas 4" con las filas 211, 212, 900 y 901 (14.802 − 14.798 = 4); HU-042 "2025-12-18" (Diario Oficial 53.339, `res2625_raw.txt` l.2-3); listas del anexo con "Resolución 866 de 2021" y "2021-06-25" (encabezado de la hoja 16: "25 JUN 2021"); el valor inactivo de las 6 listas es un código que la hoja no tiene, "creado por un cambio oficial (ejemplo)". Residuales en N10 |
| B15. [2.7] y HU-007 esc. 8 | Corregido | Modelo E-R l.91-93 sin [2.7]; HU-007 esc. 8 l.132: "registra el código y muestra el nombre que le da el catálogo". La frase vieja sigue en HU-009 esc. 2 (N7) |
| B16. Identidad de género | Corregido | HU-004 l.326-328 con la cita de la 1888 (cotejada, `res1888_anexo_raw.txt` l.903-905); D-19 en el tablero; pregunta 14 |
| B17. Conjunción y reglas largas | Corregido | Tanda 5 l.295 "escenarios 21 y 23"; RC-10 en renglones (l.126-136); reglas de categorías de HU-030 en viñetas separadas (l.82-88) |

### 2.2 Comprobaciones del encargo

| Comprobación | Resultado |
|---|---|
| Escenarios por historia | 543 en 30 historias: tanda 1, 55; tanda 2, 57; tanda 3, 100; tanda 4, 196; tanda 5, 135. Numeración continua y Dado, Cuando y Entonces en todos. Las tablas de las tandas 4 y 5 y las 30 cifras del LEEME coinciden con el conteo |
| 543, 30 y 13 en cada archivo | Tanda 0: 30 en el mapa (l.211) y los 13 ID archivados en la convención de numeración (l.159-160); no trae total de escenarios. Plan: 30 (l.88), 13 (l.122-124) y 543 (l.217). LEEME: 30 y 543 (l.41) y 13 notas. Hoja Backlog v3: 30 filas con los mismos ID y títulos que las tandas y D39 = 13; no tiene columna de escenarios, así que el 543 no se puede cotejar ahí |
| Tarjetas frente a tandas | Las 30 tarjetas son iguales a su sección de la tanda, sin la línea del mockup y sin la trazabilidad. La tarjeta RC es igual a la sección 3, la matriz y el glosario de la tanda 0. En las 31, la lista final de decisiones coincide con los D-n que cita la tarjeta; HU-010, HU-011, HU-012, HU-015 y HU-016 no citan ninguno y no traen lista |
| Los 44 Cuando | 9 en la tanda 1 y 35 en la tanda 3, según diff. En todos el actor es el auxiliar de admisión, salvo HU-001 esc. 4 ("una persona", sin sesión), y la matriz se lo permite. Ningún escenario cambió lo que prueba: los diez en que cambió también el Dado (HU-010 esc. 9, HU-012 esc. 13, HU-013 esc. 3, 6 y 12, HU-014 esc. 11, HU-016 esc. 14 y HU-017 esc. 3, 6 y 12) prueban lo mismo que antes. Detalles de redacción en N15 |
| Correspondencias de escenarios antiguos | Comparé con la hoja Escenarios las de HU-001 a HU-003 (filas 2 a 31), HU-018 a HU-021 (175 a 214), HU-022 a HU-025 (215 a 254), HU-026 a HU-029 (255 a 294), HU-030 a HU-033 (295 a 334), HU-004 (32 a 43), HU-008 (75 a 84) y HU-013 (125 a 134). Cada número apunta a un escenario que existe y que dice lo que la correspondencia afirma; los que "salen" o "cambian" lo dicen. Las referencias entre historias con número de escenario (HU-001 esc. 4, HU-004 esc. 5, HU-008 esc. 4 y 14, HU-030 esc. 14, 15 y 20, HU-005 esc. 13, HU-008 esc. 15) existen y dicen lo citado |
| Mockups | En los 30, cada escenario tiene etiqueta o nota y no hay números de más; en los 13 de catálogo cada etiqueta cae en el escenario de su panel; cada PNG es posterior a su HTML |
| Fuentes | Confirmado: artículos 7, 8 y 10 de la 866; anexo de la 1888 (identidad de género, 13.2, frase del CIE-11, Diario Oficial 53.244); 2625 (artículo 3, cita de la 1895 de 2001, versión 5.0, números del 1 al 2273, D71X y D67X); DANE (cuatro niveles; 10, 34, 117 y 401; 0, 01, 011 y 0110) |
| Estilo | Sin guion largo ni "muy", "realmente", "simplemente", "claramente" o "absolutamente" en tandas, tarjetas, LEEME, respuestas, preguntas, plan, modelo E-R, registro de aplicación, HTML de los mockups y hojas Leeme, Decisiones, Reglas comunes, Ajustes y Backlog v3 |

## 3. Errores nuevos o residuales

Ordenados por gravedad. "Nuevo" quiere decir que lo introdujo una corrección de esta ronda;
"residual", que ya estaba y la corrección no lo alcanzó.

### Media

**N1. HU-022: la regla de cantidades por nivel no dice qué cuenta, y su mockup la contradice
(nuevo, de A1)**

- Ubicación: tanda 4, HU-022 regla l.339-341, esc. 2 l.366-371, esc. 7 l.395-399, supuesto
  l.534-536; tanda 0, RC-8 l.109-110; mockup HU-022 (HTML y PNG), panel "Carga del archivo oficial";
  tablero D-26 (I27).
- Cita: regla, "La primera carga debe traer las cantidades por nivel del DANE (10, 34, 117 y 401)";
  esc. 2, "elegir entre guardar las filas aceptadas o cancelar la carga completa"; mockup, "Filas
  leídas 520", "Aceptadas 517", botón "Guardar las aceptadas" y, en el mismo panel, "La primera carga
  debe cuadrar con los niveles del DANE: 10, 34, 117 y 401; si el archivo perdió los ceros del gran
  grupo 0, no cuadra y no se guarda"; supuesto, "si no los trae, se vuelve a una columna de padre";
  esc. 7, "por ejemplo el subgrupo 01 de la Fuerza Pública llegó como 1".
- Por qué falla:
  - La regla no dice si cuenta filas leídas o aceptadas; las listas del anexo sí lo dicen ("contando
    solo las filas aceptadas"). Si cuenta aceptadas, una fila de nivel rechazada (la "3111 Falta el
    nombre" del mockup) deja 400 grupos primarios y el esc. 7 impide lo que el esc. 2 ofrece: es el
    mismo choque que la revisión marcó en M4.
  - 10 + 34 + 117 + 401 = 562. Con 520 filas leídas el ejemplo no cuadra en ninguna de las dos
    lecturas, y el panel muestra el botón de guardar junto a la nota que dice que no se guarda.
  - Un archivo que no trae los cuatro niveles nunca pasa el esc. 7, y una columna de padre no crea
    las filas de los grupos; el supuesto y el tablero proponen una salida que la regla nueva cierra.
  - El DANE llama a 01 "subgrupo principal" (FUERZAS MILITARES, `ciuo88ac.txt` l.454) y el Entonces
    del mismo escenario distingue "34 subgrupos principales, 117 subgrupos".
  - RC-8 dice "En la primera carga, el profesional guarda las aceptadas o cancela todo" sin avisar
    que HU-022 y las listas del anexo lo restringen.
- Corrección: en la regla, "se cuentan las filas leídas, cada una en el nivel que da la longitud de
  su código" (o "las aceptadas", y entonces el esc. 2 dice que una fila de nivel rechazada impide
  guardar). Mockup con 562 filas leídas, o con el botón desactivado y el aviso del nivel que no
  cuadra. Supuesto y D-26: si el archivo no trae los cuatro niveles, los grupos se cargan desde el
  documento del DANE como fuente aparte, como en el plan B del CIE-10, o se pregunta. "El subgrupo
  principal 01 (Fuerzas Militares)". En RC-8, "salvo lo que diga la historia".

**N2. El plan B no suma lo que dice, y la cifra va al docente (residual)**

- Ubicación: `PLAN-BACKLOG-V3.md` l.20-22 (TL;DR), l.69 (D-16), l.139 (título de la sección 6) y
  l.147-162 (tabla); `preguntas-docente.md`, pregunta 1 (l.10).
- Cita: tabla, "Inicio de sesión 1 | Paciente 3 | Atención (16 a 23) 4 | Oposición y voluntad
  anticipada 2 | CIE-10 2 | Catálogos de los elementos 1 a 23 12 | [cinco grupos de 1] | Listas de
  más de 5 del 24 al 49 2 a 6 | Catálogos externos del 24 al 49 4 | Total 32 a 36"; TL;DR, "la
  oposición, la voluntad anticipada y el CIE-10 pasan a una historia cada una"; pregunta 1, "Con 1 a
  49, entre 32 y 36 agrupando catálogos (plan B)".
- Por qué falla: 1 + 3 + 4 + 2 + 2 + 12 + 5 + (2 a 6) + 4 = 35 a 39, no 32 a 36. El TL;DR deja el
  CIE-10 en una historia y la tabla en dos ("Igual que el plan A"); aun con una, la suma da 34 a 38.
  La versión 2 del plan decía "33 a 37" (registro del plan, l.209); el total actual no corresponde a
  las filas actuales, que cuentan la atención en 4 y el CIE-10 en 2. La pregunta 1 lleva al docente
  una cifra que la tabla del propio plan no sostiene.
- Corrección: decidir si el CIE-10 del plan B es una o dos historias, rehacer la suma y cambiar la
  cifra en el TL;DR, en D-16, en el título y la tabla de la sección 6 y en la pregunta 1.

**N3. A2 en parte: falta el escenario de rechazo y queda un caso sin respaldo (residual)**

- Ubicación: tanda 0, RC-10 l.127-134; escenarios "Reactivación" y "Reactivación sin respaldo" de
  las 13 historias de catálogo (p. ej. HU-018 esc. 20 y 21, tanda 4 l.228-236; HU-030 esc. 24 y 26,
  tanda 5 l.239-253).
- Cita: RC-10, "Se reactiva un código que está en la versión vigente o que se creó por un cambio
  oficial. Si se inactivó con una referencia oficial, se reactiva solo con otra referencia oficial o
  diciendo por qué esa referencia no lo retiraba"; "No está en la versión", que lo pone el sistema
  (RC-9)". La revisión pidió esa regla "con su escenario de rechazo".
- Por qué falla:
  - Los 13 escenarios "Reactivación" prueban solo el camino aceptado (escribe por qué la referencia
    no aplicaba). No hay escenario de un código inactivado con referencia oficial que se intenta
    reactivar sin otra referencia ni explicación, ni de reactivar sin motivo.
  - Un código creado por cambio oficial y que una versión posterior ya no trae queda con "No está en
    la versión". RC-10 no dice que ese motivo lleve referencia oficial, y el código "se creó por un
    cambio oficial", así que se reactiva con un motivo escrito. El catálogo queda con un valor que la
    versión vigente no tiene, contra el artículo 7 ("no podrán ser modificados, reducidos o
    adicionados", `res866.txt` l.363). Es el caso "uno creado por cambio oficial y retirado después"
    que la revisión listó en A2.
- Corrección: en RC-10, "La versión que retira un código es su referencia oficial" o "un código que
  una versión posterior a su creación no trae no se reactiva por haberse creado por un cambio
  oficial". En el Dado de "Reactivación sin respaldo", agregar "o se inactivó con una referencia
  oficial y no se da otra ni se dice por qué no aplicaba"; así no cambia el conteo de escenarios.

### Baja

**N4. HU-004 esc. 11: el Entonces quedó sin sujeto (nuevo, de B11)**

- Ubicación: tanda 1 l.282-286; `trello/tarjetas/HU-004.md`.
- Cita: "Cuando el auxiliar de admisión intenta guardar / Entonces conserva los datos válidos en
  pantalla / Y marca solo el que hay que corregir".
- Por qué falla: antes el Cuando era "el sistema rechaza el registro" y el Entonces heredaba ese
  sujeto. Ahora el sujeto que hereda es el auxiliar: el texto dice que el auxiliar conserva y marca.
  Es el único Entonces de las 30 historias con esa forma.
- Corrección: "Entonces el sistema conserva los datos válidos en pantalla".

**N5. Quedan 3 Cuando impersonales en las historias que el registro da por revisadas (residual)**

- Ubicación: tanda 1, HU-005 esc. 12 (l.438-441); tanda 3, HU-011 esc. 11 (l.245-248) y HU-015
  esc. 12 (l.756-759).
- Cita: "Dado que se consultó un paciente / Cuando termina la consulta"; igual con "la oposición" y
  "la voluntad anticipada". Registro de la tanda 1 (l.611): "cada Cuando de HU-001, HU-005 y HU-006
  es una acción del usuario, no del sistema ni impersonal"; el de la tanda 3 dice lo mismo.
- Por qué falla: con un Dado impersonal, "termina la consulta" se lee como "la consulta termina",
  sin actor. En HU-008 esc. 13 el Dado nombra al médico y el Cuando sí tiene sujeto.
- Corrección: "Dado que el auxiliar de admisión consultó [...] / Cuando cierra la consulta".

**N6. La tanda 4 todavía dice que el paciente particular tiene escenario en HU-004 (residual de M3)**

- Ubicación: tanda 4 l.72, tabla "Escenarios por historia", fila de HU-038.
- Cita: "Secretarías de salud para el paciente particular (regla; el escenario está en HU-004)".
- Por qué falla: el esc. 16 de HU-004 salió; hoy es solo una regla (tanda 1 l.209-213).
- Corrección: "(regla de HU-004)".

**N7. HU-009 esc. 2 sigue diciendo que guarda el nombre (residual de B15)**

- Ubicación: tanda 2 l.432-436; `trello/tarjetas/HU-009.md`.
- Cita: "Entonces el sistema guarda el código nuevo con el nombre que le da el catálogo"; modelo E-R
  l.91, "los registros guardan solo el código".
- Por qué falla: es la misma diferencia que B15 señaló en HU-007 esc. 8, que sí se corrigió.
- Corrección: "registra el código nuevo y muestra el nombre que le da el catálogo, con la fecha [...]".

**N8. Plan B del CIE-10: dos fuentes y una sola versión vigente (nuevo, de M10)**

- Ubicación: tanda 5 l.272-276; RC-9 y RC-10 (tanda 0 l.111-134); modelo E-R l.88-90.
- Cita: "las categorías se cargan aparte, como una fuente propia con su versión".
- Por qué falla: RC-9 compara cada archivo con "la versión vigente" del catálogo, y RC-10 decide
  "Creado por error" y la reactivación por "estar en la versión vigente"; el modelo guarda un
  `version_id` por código. Cuando la tabla de SISPRO queda vigente, las categorías no están en esa
  versión: RC-10 deja inactivarlas como "Creado por error" sin referencia y no deja reactivarlas.
  Tampoco se dice que las categorías se cargan primero, y el esc. 6 rechaza todo código cuya
  categoría no existe.
- Corrección: agregar al supuesto "las categorías se cargan antes que la tabla de SISPRO y, para
  ellas, la versión vigente es la de su fuente".

**N9. RC-9 dice que el código de una fila rechazada "queda como estaba", y el modelo le cambia la
versión (nuevo, de A2)**

- Ubicación: tanda 0 l.124-125; modelo E-R l.88-89.
- Cita: RC-9, "el código de una fila rechazada no se crea, no se cambia ni se inactiva: queda como
  estaba"; modelo, "`version_id` de cada catálogo es la última versión cuyo archivo oficial trajo el
  código, aunque su fila se haya rechazado".
- Por qué falla: actualizar `version_id` es cambiar el código; las dos frases se contradicen al pie
  de la letra.
- Corrección: en RC-9, "queda con su nombre y su estado; su versión pasa a la nueva".

**N10. Mockups de catálogo: contadores de la versión nueva y avisos junto al ejemplo (residual de
B14 y nuevo)**

- Ubicación: los 13 mockups de catálogo, panel "Versión nueva"; los 6 de las listas del anexo
  (HU-034 a HU-036, HU-039 a HU-041), panel "Inactivar y reactivar".
- Cita: contadores "Nuevos 2" y "Ya no están 1", con una fila "Nuevo" y dos filas "Ya no está" ("se
  inactiva" y "reemplazado"). En HU-034: "Inactivar ZZ Tipo creado por un cambio oficial (ejemplo)"
  y, debajo, ""Creado por error" solo vale para un código que no está en la versión vigente" y "No
  se puede reactivar: el código no está en la versión vigente ni se creó por un cambio oficial".
- Por qué falla: los contadores no cuadran con su tabla, la misma clase de error que B14 corrigió en
  "Rechazadas". El ejemplo de las listas, después del cambio de B14, es un código creado por cambio
  oficial que no está en la versión vigente (la del panel de consulta es la Resolución 866 de 2021):
  a ese valor RC-10 sí le deja "Creado por error" y la reactivación, así que los dos avisos del
  mismo panel no aplican al ejemplo que se ve.
- Corrección: contadores "Nuevos 1" y "Ya no están 2", o una fila por cada código contado; los dos
  avisos como "Otro código: [...]".

**N11. Modelo E-R: nombre de la comunidad étnica y orden del registro (residual de B8)**

- Ubicación: `modelo-datos/modelo-entidad-relacion.md` l.318 y l.399-401.
- Cita: `string nombre "lo trae el catalogo, no el anexo"`; en el registro, la fila "2
  (continuación)" va antes de la fila "2".
- Por qué falla: el catálogo de comunidades no se ha encontrado; HU-037 ya dice "lo traería".
- Corrección: "lo traeria el catalogo (por verificar)"; fila "2 (continuación)" después de la "2".

**N12. Tablero: celdas que siguen en el backlog anterior (residual de B13)**

- Ubicación: hoja Ajustes por historia, fila 2, columna Estado en v3; hoja Decisiones, columna
  "Afecta a" de D-06 (C7), D-07 (C8) y D-08 (C9).
- Cita: "En parte: falta el mockup (fase 3)"; "HU-001 a HU-003, escenario 10"; "HU-004, escenario
  11"; "HU-007, escenario 10".
- Por qué falla: los 30 mockups existen. En el v3, el esc. 10 de HU-001 es la sesión que sigue al
  cambiar de función (la inactividad es el 12), el 11 de HU-004 es "Se conservan los datos válidos"
  (el duplicado es el 12) y el 10 de HU-007 es la enfermedad huérfana (el borrador está en el 11 y el
  12). D-22, en la misma columna, ya usa la numeración del v3.
- Corrección: "Aplicado: 30 mockups (fase 3)"; numeración del v3 en D-06, D-07 y D-08, o una nota en
  el encabezado de la columna que diga que esas tres usan la numeración anterior.

**N13. HU-004: la correspondencia omite el antiguo esc. 10 (residual de M9)**

- Ubicación: `trello/respuestas-al-docente.md` l.17-20; tanda 1, trazabilidad de HU-004 l.332-336.
- Cita: se dicen el 9 (hoy 10), el 11 (hoy 12) y el 12 (absorbido por el 8). La hoja Escenarios
  (filas 32 a 43) muestra 12 escenarios antiguos; el 10 es "Conservación de los datos válidos".
- Por qué falla: el antiguo 10 es hoy el 11. Sin decirlo, el docente lo buscaría en el 10 nuevo,
  que es "Falta un dato obligatorio". El registro afirma que "cada comentario trae dónde quedó cada
  escenario antiguo".
- Corrección: agregar "el 10 (se conservan los válidos) es hoy el 11".

**N14. Listas del anexo, esc. 2: dos casos en un escenario (residual)**

- Ubicación: esc. 2 de HU-034 a HU-036 (tanda 4, p. ej. l.890-894) y de HU-039 a HU-041 (tanda 5,
  p. ej. l.525-529); HU-035 l.1091 y HU-040 l.730.
- Cita: "Dado que las filas aceptadas [...] no traen uno de los 9 códigos [...] / Entonces el
  sistema dice qué código falta o sobra, y no deja guardar la carga / Y si un nombre no coincide con
  el de la hoja, lo avisa y guarda el de la hoja"; HU-035, "de las hojas 14 y 15, o traen uno que la
  hoja no tiene".
- Por qué falla: el segundo caso no está en el Dado y su resultado ("guarda") choca al leerlo con
  el primero ("no deja guardar"); es el defecto que B11 señaló en HU-007 esc. 18. "La hoja" no
  concuerda con "las hojas 14 y 15" ni con "las hojas 16 y 17".
- Corrección: pasar el nombre distinto al esc. 1 ("Y si un nombre no coincide con el de la hoja,
  guarda el de la hoja y lo avisa"); "que esas hojas no tienen".

**N15. Redacción en escenarios reescritos (nuevo, de los 44 Cuando)**

- Ubicación: tanda 3, HU-010 esc. 9 (l.116-119) y HU-014 esc. 11 (l.622-625); HU-013 esc. 6
  (l.468-471) y HU-017 esc. 6 (l.992-995); HU-013 esc. 12 (l.499-502) y HU-017 esc. 12
  (l.1023-1026).
- Cita: "el sistema guarda con ella su nombre e identificación"; "Dado que la manifestación tiene
  versiones anteriores sin anular"; "Dado que el auxiliar de admisión anula una manifestación /
  Cuando confirma la anulación".
- Por qué falla: con "el sistema" como sujeto, "su nombre" puede ser del paciente; en la tanda 3 una
  manifestación es una versión (esc. 5, "la manifestación vigente [...] era la única"), así que no
  "tiene versiones"; en el esc. 12 el Dado ya hace lo que hace el Cuando. Ninguno cambia lo que se
  prueba.
- Corrección: "el nombre y la identificación del auxiliar"; "Dado que el paciente tiene versiones
  anteriores sin anular"; "Dado que el auxiliar de admisión pidió anular una manifestación".

**N16. El registro de aplicación dice algo distinto de los archivos en dos filas**

- Ubicación: `aplicacion-revisiones-tandas-4-y-5.md` l.82 (M5) y l.85 (M8).
- Cita: M5, "Escenario de filas con errores de las 13 historias"; M8, "su fila de ajustes es
  propuesta del modelo".
- Por qué falla: la recarga está en el esc. 2 de los 7 catálogos externos, no de las 13 (las listas
  no la necesitan); la tanda 5 l.10-11 y l.292 dicen "propuesta del equipo", y la hoja Ajustes, fila
  104, "propuesta del modelo".
- Corrección: "de los 7 catálogos externos"; un solo término en el registro, la tanda y la fila 104.

## 4. Lo que no pude verificar

- El texto del Prompt 2 del docente: no está en los archivos del proyecto. Usé la cita del encargo
  y de la revisión ("Cuando [acción realizada por el usuario]").
- El contenido de las tablas de SISPRO (CIE10 con o sin categorías de 3 caracteres; Pais con código
  numérico) y si existe el archivo de comunidades étnicas. No intenté descargarlos.
- Si el archivo de la CIUO-88 A.C. que se descargue trae los cuatro niveles y suma 562 filas: solo
  hay el PDF de 2006.
- El texto de la Resolución 1895 de 2001.
- Los textos de retroalimentación de HU-031 y HU-033 en Trello: el tablero los marca sin verificar.
- El límite de caracteres de la descripción de una tarjeta de Trello. La tarjeta más larga es ahora
  HU-022, con 15.801 caracteres; HU-030 tiene 15.396.
- Vi 2 de los 30 PNG (HU-022 y la copia de HU-005 en `trello/mockups/`). De los otros comprobé que se
  generaron después de su HTML y que las copias del kit son idénticas a `mockups/png/`.
- Los valores de las fórmulas del tablero: los leí en caché (data_only=True). Cuadran con los datos
  que revisé (30 historias, 13 archivadas, 27 decisiones pendientes, 104 filas de ajustes), pero no
  recalculé el libro.
- Las hojas 13, 14, 15 y 18 del anexo: no las volví a abrir en el PDF. Esta ronda no trae citas
  nuevas de esas hojas; las longitudes y componentes que usé salen del anexo de referencia.
- Cuándo entró la cifra "32 a 36" del plan B: el plan no tiene copia anterior en la carpeta de
  trabajo.
