# Cómo se aplicaron las revisiones de las tandas 4 y 5 y la revisión final cruzada

Versión 3, 2026-10-20. Lo escribe quien redactó las tandas, no los revisores. Dice qué se hizo con
cada hallazgo de `revision-tanda-4.md` (A = alta, M = media, B = baja), de `revision-tanda-5.md`
(numerados del 1 al 22), de `revision-final-cruzada.md` (A, M y B, como la de la tanda 4) y de
`verificacion-aplicacion-final.md` (N1 a N16). Estado:
**Aplicado**, **En parte** (con lo que falta) o **No aplicado** (con la razón). Nadie aprueba lo que
analizó él mismo: esta tabla dice qué se cambió, no que el cambio esté bien; eso lo dice la revisión
del equipo.

## Revisión de la tanda 4 (catálogos del paciente)

| Hallazgo | Estado | Qué se hizo |
|---|---|---|
| A1. Una versión nueva con filas rechazadas retiraba valores oficiales | Aplicado | RC-9: el código de una fila rechazada no se crea, no se cambia ni se inactiva; la versión aplica las demás filas. Escenario "Versión nueva con filas rechazadas" |
| A2. RC-8 rechazaba los niveles superiores de la CIUO-88 | Aplicado | El grupo se lee del código (D-26): 2221 está bajo 222. Los grupos de 1 a 3 caracteres se cargan para la jerarquía; solo las de 4 se registran al paciente |
| A3. La comprobación comparaba con el mismo archivo cargado | Aplicado | Compara con un archivo recién descargado y lista también los inactivos. En las listas del anexo, la primera carga y la comprobación se comparan con la hoja |
| A4. Inactivar y reactivar sin respaldo oficial | Aplicado | RC-10: motivos cerrados; "Creado por error" solo para un código que no está en la versión vigente; solo se reactiva con respaldo. Escenarios de rechazo para los dos casos |
| A5. HU-037 atribuía un nombre al numeral 13 | Aplicado | Cotejado en la hoja 15: la comunidad étnica solo tiene código. El nombre obligatorio en el catálogo queda como propuesta |
| A6. HU-038 reducía la hoja 16 al municipio de la IPS | Aplicado | Texto literal de la hoja 16 (cotejado). El paciente particular pasa a HU-004, escenario 16; con la revisión final (M3) ese escenario sale y queda como regla de HU-004 |
| M1. La versión dependía de una fecha escrita a mano | En parte | Fechas futuras rechazadas; la fecha debe ser posterior a la vigente; aviso si la versión deshace un cambio oficial posterior; las inactivaciones se confirman aparte. Falta: corregir los datos de una versión ya cargada |
| M2. Jerarquía fuera de la versión y la comprobación; ciclos | Aplicado | Con D-26 no hay cambio de padre ni ciclos. Regla única: ningún valor activo bajo un padre inactivo o inexistente, en la carga, la versión, la inactivación y la reactivación |
| M3. Departamento en HU-026 | Aplicado | Un solo nombre por código de departamento; departamento en la consulta y en la comprobación; el aviso de ceros vale también en los cambios a mano |
| M4. Excel, encabezado y marca BOM | En parte | Las columnas se buscan por nombre, sin mayúsculas ni tildes, y se ignoran las demás; el aviso de ceros perdidos pasa a RC-8 para todos los códigos de dígitos; supuesto de "CSV UTF-8" con columnas de código como texto. La marca BOM no se nombra: es detalle de la implementación |
| M5. El cambio de nombre reescribía registros | Aplicado | RC-11 y D-25: cada registro conserva el nombre de su fecha y la consulta muestra también el vigente |
| M6. D-21 y la nota de la CUOC | Aplicado | D-21 se apoya en el artículo 8 de la 866. La nota cita el elemento 7.1 del anexo de la 1888 (CUOC 2024, tabla PSRGOCUPACION). Pregunta 9 al docente |
| M7. Archivo de comunidades étnicas sin verificar | Aplicado | Supuesto "pendiente de verificar" en HU-037; pregunta 10 al docente |
| M8. D-02 nombraba dos áreas | Aplicado | Profesional de estadística, en todas las tandas, los mockups, el modelo y el tablero |
| M9. D-03 sin decidir | Aplicado | Código numérico (170); una fila con letras se rechaza; se confirma al descargar la tabla Pais |
| M10. Reemplazo por un código que ya existe | Aplicado | "Crea el nuevo si no existe"; en una versión, el profesional puede anotar qué código reemplaza a cuál (RC-9) |
| B1. Ocho correspondencias de trazabilidad | Aplicado | Corregidas en el generador |
| B2. Tarjetas largas y repetidas | Aplicado | Las reglas comunes se enlazan a una tarjeta propia. HU-022 no se dividió: con D-26 quedó en 28 escenarios |
| B3. Propuestas presentadas como hechos | Aplicado | D-19 e inferencias marcadas |
| B4. Dependencias, prioridad y conteos | Aplicado | HU-005 en las dependencias; HU-022 cuenta pacientes y atenciones; HU-037 sube a Alta |
| B5. Permisos y motivos | Aplicado | Lista cerrada de motivos (RC-10); cancelar incluye la reactivación; el conteo de uso es solo un número |
| B6. Cambios sueltos en las listas del anexo | Aplicado | Se aceptan con la resolución que modifique el anexo como referencia; vuelve el cambio de código, para que todos los catálogos se prueben igual |
| B7. Estado de las decisiones y registro | Aplicado | RC-1 dice "propuestas del equipo"; fila nueva en el registro de la tanda 0 |
| B8. Implementación dentro de los criterios | Aplicado | "Queda bajo su grupo"; "conserva sus ceros iniciales" |
| B9. Actores mezclados en la consulta | Aplicado | La consulta es del profesional de estadística; el auxiliar y el médico quedan en "Cargo sin permiso" |
| B10. Valor de negocio de HU-035 | Aplicado | Reescrito |

## Revisión de la tanda 5 (CIE-10 y catálogos de la atención)

| Hallazgo | Estado | Qué se hizo |
|---|---|---|
| 1. La versión nueva cambiaba el nombre en atenciones ya registradas | Aplicado | RC-11 y D-25; regla en HU-030 |
| 2. Estructura del archivo de SISPRO sin fuente | Aplicado | Supuesto por verificar; si la tabla no trae las categorías de 3, se cargan de la CIE-10 adoptada por la Resolución 1895 de 2001 (citada en la 2625), sin cambiar códigos ni nombres |
| 3. División del CIE-10 apoyada en el mínimo de 30 | Aplicado | HU-032 y HU-033 se funden en HU-030; HU-031 sigue aparte por actor; entra HU-043 (anulación de una atención). Backlog: 30 |
| 4. Inactivos fuera de la comparación y la comprobación | Aplicado | "Inactivos que vuelven" en la versión nueva; "inactivos en el catálogo" en la comprobación |
| 5. Regla de jerarquía solo en HU-033 | Aplicado | Regla única en HU-022 y HU-030, con rechazo de la hija cuyo padre no se acepta |
| 6. Jerarquía del CIE-10 copiada de la CIUO-88 | Aplicado | La categoría son los 3 primeros caracteres (D-26); salen el cambio de padre y el ciclo |
| 7. Fila oficial que no cumple el numeral | En parte | La fila se rechaza con su motivo y la versión aplica las demás (RC-9); supuesto en cada catálogo externo y pregunta 11 al docente. No se escribió una regla que separe los errores de conversión de los del contenido oficial |
| 8. Validación circular de la versión | Aplicado | La carga guarda la dirección y la fecha de descarga; la comprobación usa un archivo recién descargado |
| 9. Borradores con un código que se inactiva | Aplicado | El conteo incluye los borradores; HU-007, escenario 13, pide elegir un valor activo |
| 10. Fuentes de modalidad, vía y causa | Aplicado | Modalidad: valores de la 866; vía: tabla ViaIngresoUsuario; causa: tabla RIPSCausaExterna; manda la hoja |
| 11. HU-042 con cambio de código | Aplicado | Sale; regla del artículo 3 de la Resolución 2625 |
| 12. Precondición de HU-007 | Aplicado | Sale; escenario 18 de catálogo sin valores. Lo mismo en HU-004 (escenario 15) |
| 13. Encabezado contradictorio | Aplicado | Reescrito |
| 14. HU-030 repetía escenarios de HU-032 | Aplicado | Resuelto con la fusión |
| 15. Cuando del sistema, orden interno, "muchos" | Aplicado | Esc. 13 fundido en la jerarquía; "queda bajo su categoría"; resultados de 20 en 20 |
| 16. Cuatro correspondencias de trazabilidad | Aplicado | Reescritas con la fusión |
| 17. Citas de [2.2] para reglas que la guía no dice | Aplicado | Numeral 23 y D-22 para los 4 caracteres; [2.2] solo para la jerarquía |
| 18. Componente de la enfermedad huérfana | Aplicado | "Segunda fila del componente código del diagnóstico principal" (cotejado en la hoja 18); columnas número y nombre; fuente en el Repositorio Institucional Digital |
| 19. Errores de plantilla | Aplicado | "El código que se crea ya existe"; "el médico tratante registre"; "el nombre que le da el anexo" |
| 20. Nombre o descripción | Aplicado | "Nombre", como el anexo |
| 21. Salvedad de D-09 | Aplicado | Cita literal de la frase del CIE-11; Resolución 1895 de 2001 por confirmar; "el día de la carga" |
| 22. Motivos sin lista cerrada | Aplicado | RC-10; cada tarjeta lista en supuestos las decisiones que usa |

## Revisión final cruzada (todo el backlog v3 y los entregables)

Veredicto del revisor: "Necesita cambios", acotados. Conteos después de aplicar: 30 historias, 543
escenarios (antes 541: sale el esc. 16 de HU-004 y el esc. 9 de HU-043; entran HU-005 esc. 13,
HU-007 esc. 19, HU-008 esc. 15 y un escenario de HU-022).

| Hallazgo | Estado | Qué se hizo |
|---|---|---|
| A1. RC-8 rechazaba los niveles de arriba de ocupaciones y CIE-10 | Aplicado | RC-8: en los catálogos con niveles, las filas de arriba se validan con la regla de su historia, no con la longitud del numeral, y el aviso de ceros no aplica. HU-022, escenario nuevo: la primera carga debe traer las cantidades por nivel del DANE (10, 34, 117 y 401) |
| A2. "Estar en la versión vigente" no protegía lo que RC-10 promete | Aplicado | RC-10 en lista: un código está en la versión vigente si vino en su archivo, aunque su fila se haya rechazado; lo inactivado con una referencia oficial se reactiva solo con otra referencia o diciendo por qué esa no lo retiraba. Escenarios de reactivación con ese caso; la definición del modelo E-R, igual |
| M1. Artículo 10 citado para algo que no dice | Aplicado | HU-007, HU-009, HU-043 y D-25: el artículo obliga a disponer los datos de forma permanente; el parágrafo 2 agrega el dato nuevo al corregir y no admite tratar los datos "por otras razones diferentes a inconsistencias demostradas". El borrador sin rastro y la anulación quedan como propuestas (D-08, D-27). HU-043: "Otro" pasa a "Otra inconsistencia", con su explicación |
| M2. RC-11 sin escenario donde se ve | Aplicado | HU-005 esc. 13 y HU-008 esc. 15 (nombre con que se registró y el vigente, marcado); el escenario de cambio de nombre de las 13 historias de catálogo remite a ellos. Mockups de HU-005 y HU-008 con la etiqueta |
| M3. HU-004 esc. 16 no probaba una conducta | Aplicado | Sale; el servicio particular queda como regla de HU-004 y como ayuda en el campo del mockup |
| M4. En las listas del anexo, los escenarios 2 y 3 se contradecían | Aplicado | La primera carga compara con la hoja solo las filas aceptadas; el archivo completo se rechaza solo por columnas o fechas |
| M5. Una primera carga parcial no se podía completar | Aplicado | RC-9: recarga de la versión vigente (misma fecha): solo crea los códigos que faltan, avisa los nombres distintos y no inactiva nada. Escenario de filas con errores de los 7 catálogos externos; las 6 listas del anexo no dejan guardar una primera carga incompleta (su escenario 2) |
| M6. Precondición de HU-009 | Aplicado | "La atención existe (HU-007)" |
| M7. El bloqueo de valores en uso cambiaba sin decirlo | Aplicado | Trazabilidad de HU-018, HU-022, HU-026 y HU-030: el valor en uso ya no se bloquea, se inactiva con su motivo y los registros lo conservan; lo que se bloquea es inactivar sin respaldo. Pregunta 6 al docente |
| M8. Trazabilidad de HU-030 | Aplicado | HU-032 sin retroalimentación del docente: su fila de ajustes es propuesta del modelo (cabecera y trazabilidad); quién y cuándo, escenario 28; "21 y 23"; el esc. 7 dice que la versión nueva queda vigente; el código que ya existe, como cambió; el historial de versiones, en la consulta |
| M9. La respuesta al docente remitía a un texto que no va a Trello | Aplicado | Kit versión 3: cada comentario trae dónde quedó cada escenario antiguo, y cada nota de archivo, los de su tarjeta. HU-001 con la correspondencia completa de HU-001 a HU-003 |
| M10. Plan B del CIE-10 con dos fuentes en un archivo | Aplicado | Si la tabla de SISPRO no trae las categorías, se cargan como fuente aparte con su versión, y la comprobación y las versiones no las cuentan como sobrantes ni retiradas |
| M11. Sin catálogo de comunidades étnicas, HU-004 no registraba a las etnias 01 a 06 | Aplicado | La comunidad étnica se pide solo si el catálogo de HU-037 tiene valores (HU-004 y D-19 en el tablero); mockup de HU-004 con el aviso |
| B1. "Diez" por trece | Aplicado | Tanda 0, tarjeta de reglas comunes y preguntas 4, 6 y 11 |
| B2. D-25 decía hojas 13 a 15 y 18 | Aplicado | Numerales 1, 7, 11, 12, 15 y 23; en los demás catálogos, conservar el nombre es decisión del equipo |
| B3. Artículo 8 | Aplicado | "Definirá el procedimiento para individualizarlos en SISPRO" |
| B4. "Texto libre, no un código" | Aplicado | Cita de la 1888 ("Texto Alfanumérico"; "Diligenciar con el nombre...") y la lectura marcada como del equipo, en HU-037 y en la pregunta 10 |
| B5. D-02 como hecho | Aplicado | "Se supone"; sin fuente |
| B6. "Manda la hoja" sin estar en D-21 | Aplicado | D-21: si el archivo no coincide con la hoja, manda la hoja y se guardan sus códigos y nombres |
| B7. [2.2] para raíces y para mostrar la categoría | Aplicado | [2.2] solo para la jerarquía; D-22 y D-26 para lo demás |
| B8. Lecturas presentadas como texto de la fuente | Aplicado | Artículo 3 de la 2625: que una enfermedad no cambie de número es lectura del equipo; D-26 en el CIE-10, inferencia; en la CIUO-88, con la cita del DANE; HU-037: "el nombre lo traería" |
| B9. Reglas sin escenario o sin consecuencia | Aplicado | RC-9: la fecha futura rechaza el archivo; motivos "Vuelve en la versión", "Reemplazado por" y "No está en la versión"; el escenario de inactivación sin motivo o sin respaldo trae en su Dado el motivo sin referencia |
| B10. Escenario repetido en HU-043; borradores en D-27 | Aplicado | Sale el esc. 9 (queda en HU-008 esc. 14); "lo deja en estado Anulada"; D-27 con los borradores; las anuladas cuentan en el uso de un valor (RC-10) |
| B11. Cuando sin la acción del usuario; HU-007 esc. 18 con dos casos | Aplicado | Cada Cuando de HU-004 y HU-008 es una acción del usuario, no del sistema; HU-007 esc. 18 se parte en 18 y 19. Con el mismo criterio, 44 escenarios más de HU-001, HU-005, HU-006 y HU-010 a HU-017, que el revisor no leyó con esa lupa ("Cuando se consulta", "Cuando el sistema la guarda") |
| B12. HU-031 sin RC-1 | Aplicado | RC-1 copiada, con la remisión a HU-030 |
| B13. Celdas del tablero, plan y kit desactualizados | Aplicado | Encabezado de la hoja Reglas comunes (las citas son del backlog anterior); Leeme B19 con RC-1 a RC-11; fila 100 sin columna de padre; D-22 con HU-007 escenario 9; plan con T2 (4) y T5 (6); kit con `mockups/` dentro |
| B14. Mockups: concordancia, conteos, fechas y ejemplo imposible | Aplicado, con otra solución en el ejemplo | "Las atenciones"; rechazadas contadas de la tabla; versión y fecha de cada fuente (Resolución 866 de 2021, 2021-06-25; listado 5.0, 2025-12-18). En las listas del anexo, el valor del ejemplo se rotula "creado por un cambio oficial (ejemplo)" con un código que la hoja no tiene, en lugar de usar un código real que el ejemplo daría por retirado |
| B15. [2.7] en el modelo E-R; HU-007 esc. 8 | Aplicado | Sale [2.7]; "registra el código y muestra el nombre que le da el catálogo" |
| B16. Identidad de género | Aplicado | Sigue obligatoria (D-19), con la cita de la 1888 en HU-004 y en el tablero, y pregunta 14 al docente |
| B17. Conjunción y reglas largas | Aplicado | "21 y 23"; RC-10 y la regla de categorías de HU-030 en renglones |

Siguen en parte, de las revisiones de las tandas 4 y 5: corregir los datos de una versión ya
cargada (tanda 4, M1) y separar los errores de conversión de los del contenido oficial (tanda 5,
7). Los dos dependen de la pregunta 11 al docente.

## Verificación de la aplicación (`verificacion-aplicacion-final.md`)

Veredicto del verificador independiente: "lista con ajustes menores". De los 30 hallazgos de la
revisión final, 29 corregidos y A2 en parte; además, 16 puntos nuevos o residuales (N1 a N16). Se
aplicaron todos.

| Punto | Estado | Qué se hizo |
|---|---|---|
| N1. HU-022: la regla de niveles no decía qué filas cuenta | Aplicado | Cuenta las filas leídas, cada una en el nivel que da la longitud de su código, aunque se rechace por otro motivo; así se pueden guardar las aceptadas y completar con una recarga. RC-8 dice qué historias impiden guardar una primera carga parcial. "Subgrupo principal 01, Fuerzas Militares", como lo llama el DANE. Si el archivo no trae los grupos, se cargan antes desde el documento del DANE como fuente aparte (supuesto de HU-022 y D-26). Mockup con 562 filas leídas, que cuadran por nivel |
| N2. La tabla del plan B sumaba 35 a 39 y el total decía 32 a 36 | Aplicado | 35 a 39 (35 con las listas agrupadas en dos) en el TL;DR, D-16, la sección 6 y la pregunta 1; en el plan B el CIE-10 sigue en dos historias, porque la búsqueda es de otro actor |
| N3. A2 en parte | Aplicado | RC-10: se reactiva lo que está en la versión vigente o lo que creó un cambio oficial posterior a ella; un código creado por un cambio oficial que una versión posterior ya no trae necesita otra referencia oficial. El Dado de "Reactivación sin respaldo" de las 13 historias trae el caso de la referencia oficial sin otra ni explicación; el conteo no cambia |
| N4. HU-004 esc. 11 sin sujeto | Aplicado | "Entonces el sistema conserva los datos válidos en pantalla" |
| N5. Tres Cuando impersonales | Aplicado | HU-005 esc. 12, HU-011 esc. 11 y HU-015 esc. 12: el auxiliar en el Dado y "Cuando cierra la consulta"; HU-008 esc. 13 con el mismo verbo |
| N6. La tanda 4 citaba el escenario que salió | Aplicado | "(regla de HU-004)" |
| N7. HU-009 esc. 2 decía que guarda el nombre | Aplicado | "registra el código nuevo [...] y muestra el nombre que le da el catálogo", como HU-007 esc. 8 |
| N8. Plan B del CIE-10 con una sola versión vigente | Aplicado | Las categorías se cargan antes que la tabla y, para ellas, la versión vigente es la de su fuente |
| N9. RC-9 contra el modelo E-R | Aplicado | El código de una fila rechazada no se crea, no se inactiva y conserva su nombre y su estado; queda en la versión porque vino en el archivo. Igual en el escenario, en el tablero (RC-9 y D-21) y en el mockup |
| N10. Mockups de catálogo | Aplicado | Contadores "Nuevos 1" y "Ya no están 2"; los avisos que no son del ejemplo empiezan con "Otro código:"; el valor inactivo de los catálogos externos se rotula "retirado por la fuente (ejemplo)" en lugar de "de prueba" |
| N11. Modelo E-R | Aplicado | "lo traeria el catalogo (por verificar)"; filas del registro en orden |
| N12. Celdas del tablero con la numeración anterior | Aplicado | Ajustes, fila 2: "Aplicado: 30 mockups"; D-06, D-07 y D-08 con la numeración del v3 y la anterior entre paréntesis |
| N13. Correspondencia de HU-004 incompleta | Aplicado | Completa, del 1 al 12. También la de HU-005, que omitía el 1, el 2 y el 7. Las de HU-006, HU-007, HU-009 a HU-012 y HU-014 a HU-017 se cotejaron con la hoja Escenarios y están completas |
| N14. Listas del anexo: dos casos en el esc. 2 | Aplicado | El nombre distinto pasa al esc. 1 ("guarda el de la hoja y lo avisa"); "que esas hojas no tienen" en HU-035 y HU-040 |
| N15. Redacción de escenarios reescritos | Aplicado | "el nombre y la identificación del auxiliar"; "el paciente tiene versiones anteriores sin anular"; "pidió anular" en el Dado del esc. 12 de HU-013 y HU-017 |
| N16. Este registro decía cosas distintas de los archivos | Aplicado | M5 dice los 7 catálogos externos; HU-032 queda como "propuesta del modelo" en la tanda 5, en este registro y en el tablero |

Además, por el límite de la descripción en Trello: con estos cambios la tarjeta de HU-022 llegó a
16.558 caracteres, y Trello admite 16.384 según la comunidad de Trello y un desarrollador que
encontró el límite en la API (no está en la documentación oficial que se consultó). El supuesto del
archivo en Excel, que repetían las 13 historias de catálogo, pasó a RC-8, y dos textos de HU-022 se
acortaron: hoy tiene 15.879.

## Lo que se verificó en la fuente antes de aplicar

- Hojas 15, 16 y 18 del anexo (recortes del PDF): comunidad étnica sin componente de nombre; texto
  del paciente particular; enfermedad huérfana como segunda fila del código del diagnóstico.
- Artículo 8 de la Resolución 866: "se definirá y establecerá el procedimiento que los individualice
  en el Sistema Integrado de Información de la Protección Social- SISPRO".
- Anexo de la Resolución 1888 de 2025 (texto extraído de la copia del Diario Oficial 53.244): el
  elemento 7.1 remite a la CUOC 2024 y a la tabla PSRGOCUPACION; el 13.2 es texto alfanumérico; las
  tablas Pais, Municipio, Etnia, CategoriaDiscapacidad, CIE10, ViaIngresoUsuario y RIPSCausaExterna;
  la frase "el plazo para la transición de la codificación de diagnósticos de CIE 10 a CIE 11 ha
  sido ampliado"; la identidad de género "es un dato opcional que será diligenciado a criterio del
  profesional de la salud de acuerdo con el escenario de atención".
- Artículo 10 de la Resolución 866: disponer los datos "de forma permanente" en el mecanismo de
  interoperabilidad; parágrafo 2: el dato nuevo con fecha, hora, nombre e identificación de quien
  corrige, y "En ningún caso se podrá solicitar tratamiento a los datos [...] por otras razones
  diferentes a inconsistencias demostradas".
- Artículo 3 de la Resolución 2625 de 2025: el número de cada enfermedad "es exclusivo y no puede ser
  asignado a ninguna otra".
- DANE, CIUO-88 A.C. (2006): "integrada en 4 niveles de agregación, identificados mediante códigos
  numéricos de 1, 2, 3 y 4 dígitos"; 10 grandes grupos, 34 subgrupos principales, 117 subgrupos y
  401 grupos primarios; gran grupo 0 (Fuerza Pública).

## Lo que queda sin verificar

- Que la tabla Pais de SISPRO use el código numérico (D-03) y qué columnas trae la tabla CIE10,
  incluidas las categorías de 3 caracteres.
- Que el CIE-10 arme sus códigos por prefijo (D-26 en el CIE-10). En la CIUO-88 A.C. ya está
  confirmado.
- Que exista un archivo del catálogo de comunidades étnicas.
- El texto de la Resolución 1895 de 2001.
- Que el ejemplo de cada mockup sea un dato real: los valores son de ejemplo y lo dicen.
- El límite de caracteres de la descripción de una tarjeta de Trello en la documentación oficial.
