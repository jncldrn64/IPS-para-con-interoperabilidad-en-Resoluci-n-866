# Revisión independiente de la tanda 4: catálogos del paciente

Archivo revisado: `/home/claude/ips-validacion/backlog-v3/backlog-v3-tanda-4-catalogos-paciente.md`,
versión 1, guardado el 2026-10-20 a las 04:29 (110.913 bytes). Revisión del 2026-10-20. Los números
de escenario de este informe son los de esa versión.

Cambios ajenos durante la revisión: a las 04:33 la tanda 0 agregó dos frases a RC-8 ("En la primera
carga, el administrador guarda las aceptadas o cancela todo" y "Una versión nueva con filas
rechazadas no se aplica, porque un código rechazado quedaría como retirado") sin fila nueva en su
Registro, y `generar_catalogos.py` ya trae el escenario "Versión nueva con filas rechazadas". La
tanda 4 revisada no tiene ninguno de los dos. Al regenerarla, los escenarios desde el 7 suben un
número; la trazabilidad usa marcadores y se recalcula sola.

## 1. Veredicto

**Necesita cambios.** Cubre la mayoría de los comentarios del docente y es fiel al anexo en
longitudes, conteos y hojas, pero tiene seis hallazgos altos (A1 a A6): carga por versión, jerarquía
de ocupaciones, comprobación contra el archivo, artículo 7, nombre de la comunidad étnica y
secretaría de salud.

## 2. Cobertura de los comentarios del docente (HU-018 a HU-029)

Sin mockup ni estimación. "En parte" remite al hallazgo que explica el hueco.

| Comentario | Qué pide | Estado | Dónde, o qué falta |
|---|---|---|---|
| c1 de las 12 | Cambiar "responsable de catálogos" en descripción, precondiciones y escenarios | Atendido | "Como administrador de catálogos", precondiciones y escenarios; el término solo queda en la tabla D-02 y en la trazabilidad |
| c1 de las 12 | Enlazar o incluir las RC que nombra cada comentario | Atendido | RC-1 a RC-5 y RC-8 copiadas en las tres historias |
| c2 de las 12; HU-022 c2 y c4 | Cargo funcional real de la IPS con su función y permisos; "no basta cambiar el nombre" | En parte | Función y permisos: regla del administrador, matriz y escenario de cargo sin permiso. Cargo: dos áreas unidas por "o" (M8) |
| c2 de las 12 | Conservar los CA y precisar resultados | Atendido | Trazabilidad escenario por escenario, con ocho correspondencias imprecisas (B1) |
| HU-018 c1 | Formato de importación y campos | Atendido | RC-8 y "Columnas del archivo"; falta el texto exacto del encabezado (M4) |
| HU-018 c1 | Clave de duplicidad | Atendido | RC-8, esc. 4 y 9 |
| HU-018 c1 y c2 | Validar código y nombre contra la fuente oficial (ISO 3166-1), no solo la longitud | En parte | Esc. 17 compara con el mismo archivo que se cargó y no ve los inactivos (A3); D-03 sin decidir (M9) |
| HU-018 c2 | Precisar importación y errores | En parte | Esc. 2 a 6; en una versión nueva, una fila rechazada inactiva un valor oficial (A1) |
| HU-019 c1 | Búsqueda exacta o parcial y varias coincidencias | Atendido | D-23, esc. 13 y 14 |
| HU-019 c2 | Fuente y versión | Atendido | D-21 y RC-8; el esc. 12 muestra la versión y su fecha |
| HU-019 c2 | Cómo comprobar que el catálogo está completo | En parte | Esc. 17 (A3) |
| HU-020 c1 | Escenario exitoso de cambio de código y su efecto en las referencias | Atendido | Esc. 7 |
| HU-020 c2 | Correspondencia oficial y versionado | En parte | Versionado: RC-8, esc. 5 y 6. Correspondencia: la referencia oficial es texto que nadie coteja, y el mismo cambio deja rastros distintos por versión y por cambio suelto (M10) |
| HU-020 c2 | No reinterpretar nacionalidad ni residencia por cambiar un código | Atendido | Esc. 7; el cambio de nombre sí cambia lo que muestran los registros (M5) |
| HU-021 c2 | Inactivación y recarga, motivo y autor | Atendido | Esc. 18, 19, 20 y 22 |
| HU-021 c2 | Sin retirar valores oficiales arbitrariamente | En parte | La explicación libre permite inactivar un valor del archivo oficial; la reactivación no se coteja (A4) |
| HU-022 c1 | Explicar cómo se cargan los valores | En parte | RC-8 y esc. 1 a 6; tal como está escrita, RC-8 rechaza las filas de nivel superior (A2) |
| HU-022 c3 | Formato, campos código, nombre y padre, clave de duplicidad | Atendido | RC-8, columnas y esc. 4 |
| HU-022 c3 | Padre e hija en el mismo lote | En parte | Esc. 5 y 6; faltan el padre que viene en una fila rechazada y el ciclo dentro del archivo (M2) |
| HU-022 c4 | Validar los códigos oficiales de la CIUO-88 A.C. | En parte | A2 y A3 |
| HU-022 c4 | Reglas de raíces e importación padre e hija | En parte | Toda fila sin padre entra como raíz, sin importar su longitud (A2) |
| HU-023 c1 | Exacta o parcial; ocupación que no existe | Atendido | Esc. 18 a 20 |
| HU-023 c2 | Fuente y versión; ramas incompletas | Atendido | Esc. 16 y 17 ("sin hijas") |
| HU-024 c1 | Rechazar padre inexistente o ciclo | En parte | Esc. 14; "padre de su propio padre" solo cubre ciclos de dos (M2) |
| HU-024 c1, HU-025 c1, HU-026 c1, HU-028 c1 | Intento de un cargo con sesión y sin permiso | Atendido | HU-022 esc. 27; HU-026 esc. 24 |
| HU-024 c2 | Validar relaciones oficiales | En parte | La versión nueva y la comprobación no miran el padre (M2) |
| HU-024 c2 | Preservar referencias históricas | Atendido | Esc. 9 y 13 |
| HU-025 c2 | Vigencia, recarga, motivo, autor e historial | Atendido | Esc. 16, 23, 25, 26 y 28 |
| HU-026 c1 | Formato de importación y campos obligatorios | En parte | Columnas listadas; no dice qué columna puede venir vacía ni el texto del encabezado (M3, M4) |
| HU-026 c1 y c2 | Regla exacta del código DANE: cinco posiciones y ceros iniciales | Atendido | Regla del código y esc. 5 |
| HU-026 c2 | Correspondencia nombre, código y departamento | En parte | El esc. 6 solo compara el prefijo; el nombre del departamento no se valida y la comprobación no lo mira (M3) |
| HU-027 c1 | Exacta o parcial; departamento además del código | En parte | Esc. 16 y 17; la regla dice "siempre muestra el departamento", el esc. 14 lista código, nombre y estado (M3) |
| HU-027 c2 | Departamento en homónimos, filtros y vigencia; elegir la ciudad sigue en HU-004 y HU-006 | Atendido | Esc. 14 y 15; regla de HU-026 |
| HU-028 c1 | Regla exacta del formato en el antiguo esc. 5 (actualización) | En parte | Ese escenario pasa al esc. 11 genérico; el motivo exacto habla de "el archivo" (M3) |
| HU-028 c2 | No reasociar residencias al código nuevo; versionado y vigencia | Atendido | Esc. 9, 7 y 8 |
| HU-028 c2 | Validar fuente oficial y cinco posiciones | En parte | Cinco posiciones: esc. 5. Fuente: A3 |
| HU-029 c2 | Inactivación y recarga, motivo y autor | Atendido | Esc. 21, 22, 23 y 25 |
| HU-029 c2 | Preservar valores oficiales | En parte | A1 y A4 |
| HU-029 c2 | Gestionar el catálogo es distinto de elegir la ciudad | Atendido | Regla de HU-026; elegir sigue en HU-004 |

## 3. Hallazgos

### Alta

**A1. La RC-8 copiada en las ocho tarjetas está desactualizada y, leída como está, retira valores oficiales.**
- Dónde: RC-8 de las ocho; escenario "Versión nueva" (5 en HU-018 y HU-034 a HU-038; 7 en HU-022 y HU-026).
- Cita: "el administrador guarda las aceptadas o cancela todo. Si el catálogo ya tiene valores, el archivo es una versión nueva [...] al confirmar, crea, actualiza e inactiva según esa lista".
- Por qué falla: en una versión nueva, un código existente cuya fila se rechaza (nombre vacío, longitud) no está entre las aceptadas. Si "los que ya no están" se calcula sobre las aceptadas, el sistema lo inactiva con "No está en la versión" aunque sí está. Eso reduce el catálogo y, por RC-2, nadie puede volver a registrar ese valor. La tanda 0 de las 04:33 ya lo corrige, así que las ocho copias no dicen lo que dice la regla que copian.
- Fuente: Resolución 866, artículo 7, "no podrán ser modificados, reducidos o adicionados" (PDF del Ministerio); tanda 0, RC-8 de las 04:33; convención "Cada historia copia en su tarjeta las reglas que aplica".
- Corrección: regenerar con la RC-8 vigente. Mejor que bloquear la versión entera: "el código de una fila rechazada no se crea, no se cambia ni se inactiva; queda como estaba y sale en la lista de rechazadas", porque con la regla de las 04:33 una sola fila oficial que no cumpla el numeral bloquea todas las versiones futuras (ver A2).

**A2. HU-022: RC-8 rechaza las filas de nivel superior, así que la jerarquía no se puede cargar.**
- Dónde: HU-022, reglas del numeral 7 y de niveles frente a RC-8; esc. 1, 2, 5 y 6.
- Cita: RC-8, "rechaza cada fila que no cumple su numeral"; HU-022, "Que los niveles de arriba tengan códigos de 1 a 3 caracteres es inferencia del equipo; la hoja 14 solo fija los 4 caracteres de las que se registran".
- Por qué falla: el numeral 7 fija el código en 4 y 4. Una fila de un grupo general con 1 a 3 caracteres no cumple el numeral, RC-8 la rechaza y sus hijas caen en "el padre no existe" (esc. 6). Con la frase nueva de la tanda 0, ninguna versión nueva de la CIUO se aplicaría. Tampoco hay regla de raíces: toda fila con el padre vacío entra como primer nivel, aunque tenga 4 caracteres, y queda como hija registrable sin grupo.
- Fuente: anexo, hoja 14 (PDF, página 2), "Código de la ocupación", formato 9999, longitudes 4 y 4; HU-022 c4, "reglas de raíces/importación padre-hijo".
- Corrección: regla propia en HU-022: "Las filas de nivel superior se aceptan con 1 a 3 caracteres; solo las de 4 se registran al paciente. Una fila sin padre se acepta solo con 1 carácter (inferencia, se confirma con el archivo)". Un escenario de raíz aceptada y otro de raíz de 4 caracteres rechazada.

**A3. La comprobación contra el archivo oficial no puede confirmar lo que las historias le atribuyen.**
- Dónde: escenario "Comprobación contra el archivo oficial" (HU-018 17, HU-022 22, HU-026 20, HU-034 a HU-036 16, HU-037 17, HU-038 18); cabecera, "Validar contra la fuente oficial"; regla de fuente de HU-034, HU-035 y HU-036.
- Cita: HU-034, "La primera carga debe traer esos 14 valores con el código y el nombre de la hoja 13; la comprobación contra el archivo oficial lo confirma"; escenario, "lo compara con el archivo de la versión vigente".
- Por qué falla: el archivo de la versión vigente es el mismo que subió el administrador. Si trae 13 de los 14 tipos de documento, nada lo rechaza y la comprobación contra ese mismo archivo no muestra faltantes. Además, el escenario lista "los códigos del archivo que faltan en el catálogo" y "los activos que no están en el archivo", pero no los códigos del archivo que están inactivos: un valor oficial inactivado por error no aparece y, por RC-2, no se puede registrar. Es la respuesta que la tanda da a HU-018 c2 y a HU-019 c2.
- Fuente: HU-019 c2, "cómo comprobar catálogo completo: el conteo no demuestra completitud"; HU-018 c2; RC-2.
- Corrección: en las tres listas del anexo, "la primera carga se acepta solo si trae los N códigos de la hoja con su nombre; si falta o sobra uno, dice cuál". La comprobación recibe un archivo oficial recién descargado y lista también "los códigos del archivo que están inactivos en el catálogo".

**A4. Inactivar con "la explicación" y reactivar sin cotejo permiten reducir o adicionar el catálogo.**
- Dónde: "Inactivación con motivo" (HU-018 18, HU-022 23, HU-026 21, HU-034 a HU-036 17, HU-037 18, HU-038 19) y "Reactivación" (HU-018 20, HU-022 26, HU-026 23, HU-034 a HU-036 19, HU-037 20, HU-038 21).
- Cita: "Dado que un país se cargó por error o la fuente oficial lo retiró [...] escribe la referencia oficial o la explicación"; "Dado que un país se inactivó por error [...] lo reactiva y escribe el motivo".
- Por qué falla: el sistema no mira si el código está en el archivo de la versión vigente. El administrador puede inactivar un país oficial con cualquier explicación y reactivar uno que la fuente retiró. La cabecera dice que la C y la U piden referencia oficial; la D y la reactivación no la piden.
- Fuente: artículo 7; HU-021 c2, "sin retirar valores oficiales arbitrariamente"; HU-029 c2, "preservar valores oficiales".
- Corrección: "Un código que está en el archivo de la versión vigente solo se inactiva con referencia oficial; 'cargado por error' vale para códigos que no están en ese archivo. Solo se reactiva un código que está en ese archivo". Un escenario de rechazo para cada caso.

**A5. HU-037 atribuye al numeral 13 un nombre que el numeral no tiene.**
- Dónde: HU-037, regla de datos; esc. 2 ("otra sin nombre").
- Cita: "Cada comunidad tiene un código de 3 caracteres y un nombre (numeral 13, componente comunidad étnica, hoja 15)".
- Por qué falla: en la hoja 15 la comunidad étnica es un solo componente, el código (formato 999, longitudes 3 y 3). No hay componente de nombre ni longitud de nombre, a diferencia de los numerales 1, 7, 11, 12 y 15. La tarjeta presenta como del anexo una exigencia del equipo, y el esc. 2 rechaza la fila sin nombre con una regla que el numeral no da.
- Fuente: anexo, hoja 15 (PDF, página 3), fila "Comunidad étnica".
- Corrección: "Cada comunidad tiene un código de 3 caracteres (numeral 13, hoja 15). El nombre lo trae el catálogo de referencia; que sea obligatorio y de 3 a 200 caracteres es decisión del equipo (D-n)".

**A6. HU-038 reduce la regla de la hoja 16 a la secretaría del municipio de la IPS.**
- Dónde: HU-038, regla de la atención particular; esc. 15.
- Cita: "el código que se registra es el de la secretaría de salud del lugar donde se prestó (hoja 16). Por eso el catálogo debe tener la secretaría de salud del municipio de la IPS".
- Por qué falla: la hoja 16 dice "el código de la entidad administradora corresponderá a las secretarías departamentales, distritales o locales de salud, donde se realizó la prestación del servicio de salud". La tarjeta deja solo la municipal y fija el lugar en el municipio de la IPS, cuando una atención extramural (numeral 18, valores 02 a 05) se presta en otro sitio. Es inferencia sin marcar. El esc. 15 prueba que el archivo traiga un dato, no una conducta del sistema: si la secretaría no viene, la historia no dice qué pasa y el artículo 7 impide crearla. Ninguna historia aplica la regla: HU-004 no tiene escenario de paciente particular.
- Fuente: anexo, hoja 16 (PDF, página 4), primera fila. La transcripción de referencia (fuente 4) trae la misma simplificación y conviene corregirla también.
- Corrección: copiar el texto de la hoja; llevar el caso particular a HU-004 con su escenario; en HU-038, pasar el esc. 15 a la comprobación ("la secretaría del lugar de atención está en el archivo"), marcado como inferencia.

### Media

**M1. RC-8: la versión depende de una fecha escrita a mano, y hay casos sin regla.**
- Dónde: RC-8; "Versión nueva" y "Versión más antigua que la vigente" (HU-018 5 y 6; HU-022 y HU-026 7 y 8; HU-034 a HU-038 5 y 6).
- Cita: "escribe la fuente, la versión y la fecha de publicación [...] rechaza el archivo completo si [...] su fecha de publicación es anterior a la de la versión vigente".
- Por qué falla: (1) un error en la fecha (2062 por 2026) bloquea toda versión futura y no hay forma de corregir los datos de la versión; (2) la misma fecha no tiene regla; (3) un archivo parcial, con solo las filas corregidas, entra como versión nueva e inactiva todo lo demás: la vista previa lo muestra, pero ninguna regla pide confirmación aparte; (4) los cambios sueltos (esc. 7, 8 y 10 de HU-018) no mueven la fecha de la versión vigente, así que un archivo posterior a la última carga y anterior a ese cambio lo deshace, y la vista previa no avisa que el valor vino de un cambio oficial más reciente.
- Fuente: RC-8; artículo 7.
- Corrección: corregir los datos de una versión con motivo; rechazar fechas futuras; confirmación aparte cuando una versión inactive más de N valores o deshaga un cambio suelto posterior a su fecha.

**M2. HU-022: la jerarquía no entra en la versión nueva ni en la comprobación, y los ciclos quedan abiertos.**
- Dónde: HU-022, reglas de padre, ciclo y general con hijas; esc. 5, 7, 14, 22 y 24.
- Cita: "Una fila se acepta si su padre ya está en el catálogo o viene en el mismo archivo, sin importar el orden"; "no puede dejar a una ocupación como padre de su propio padre".
- Por qué falla: (1) la versión nueva lista nuevos, nombres cambiados y retirados, no padres cambiados, y la comprobación tampoco compara el padre: una ocupación que la fuente mueve de grupo queda mal sin aviso; (2) si una versión retira un grupo cuyas hijas siguen en el archivo bajo otro padre, choca "inactiva los que ya no están" con "No se inactiva una ocupación que tiene hijas activas", sin orden definido; (3) "viene en el mismo archivo" acepta a una hija cuyo padre viene en una fila rechazada, y acepta A con padre B y B con padre A; (4) "padre de su propio padre" solo cubre ciclos de dos: A, B, C y de nuevo A pasa. El ajuste acordado decía "viene antes en el mismo archivo"; el cambio a "sin importar el orden" no tiene decisión registrada y es el que abre el ciclo dentro del archivo.
- Fuente: HU-024 c2, "Validar códigos/relaciones oficiales, impedir ciclos"; HU-022 c3; `llenar_tablero.py`, línea 304.
- Corrección: "La versión nueva y la comprobación muestran también los cambios de padre"; "el padre está en el catálogo o en una fila aceptada del mismo archivo"; "una ocupación no puede quedar debajo de sí misma ni de ninguna de sus hijas o nietas"; registrar la decisión sobre el orden.

**M3. HU-026: la correspondencia con el departamento es parcial y el aviso del cero solo sirve para el archivo.**
- Dónde: HU-026, reglas de departamento y de cero; esc. 6, 11, 14 y 20.
- Cita: "El sistema siempre muestra el departamento junto al municipio"; esc. 14, "ve la lista con el código, el nombre y el estado de cada municipio"; motivo, "revise que el archivo no haya perdido el cero inicial".
- Por qué falla: el esc. 6 compara el prefijo con el código del departamento de la fila; nadie valida el nombre del departamento (dos filas con 05 pueden traer "Antioquia" y "Atlántico"), y ni la versión nueva ni la comprobación miran el departamento. El esc. 14 contradice el "siempre" de la regla. El motivo del cero habla del archivo, pero el antiguo esc. 5 de HU-028, un cambio suelto, pasó al esc. 11 genérico, donde no hay archivo.
- Fuente: HU-026 c2, "correspondencia nombre/código/departamento DIVIPOLA"; HU-027 c1; HU-028 c1.
- Corrección: regla "un código de departamento tiene un solo nombre en el archivo"; departamento en el esc. 14 y en la comprobación; en el esc. 11, el motivo exacto para un código de 4 dígitos escrito a mano.

**M4. El supuesto de Excel choca con D-18 y con el encabezado.**
- Dónde: supuesto de las ocho; RC-8; "Encabezado incompleto" (esc. 3).
- Cita: "Si el archivo oficial viene en otro formato, por ejemplo Excel, el administrador lo guarda como CSV sin cambiar su contenido".
- Por qué falla: Excel quita los ceros de una columna guardada como número (05001 pasa a 5001, 01 a 1), y su formato "CSV UTF-8" escribe una marca BOM al inicio, que hace fallar el primer encabezado. La tarjeta no dice el texto exacto de cada encabezado; si el sistema lo exige, el administrador tiene que cambiar el encabezado oficial, contra el "sin cambiar su contenido". El aviso del cero perdido solo existe en HU-026: en HU-035 y HU-036 un "01" convertido en "1" cae con el motivo genérico, y el esc. 2 de HU-035 usa justo "un código de 1 carácter".
- Fuente: D-18; RC-8; conducta conocida de Excel, no probada en este entorno.
- Corrección: llevar a RC-8 el aviso del cero para todo código de solo dígitos más corto que su numeral; fijar en RC-8 el texto de cada encabezado (o leer las columnas por posición) e ignorar la marca BOM; supuesto: "guardar como CSV UTF-8 con las columnas de código como texto".

**M5. El cambio de nombre reescribe lo que muestran los registros ya hechos, sin decisión registrada.**
- Dónde: "Cambio oficial de nombre" de las ocho (HU-018 10, HU-022 12, HU-026 12, HU-034 a HU-036 9, HU-037 10, HU-038 10).
- Cita: "muestra el nombre nuevo para ese código, también en los registros que ya lo usan, porque el código es el mismo".
- Por qué falla: el anexo trae el nombre como componente del dato ("Nombre correspondiente al código", hojas 13 a 15). Mostrar el nombre de hoy en un registro de ayer es una decisión del equipo, no una consecuencia del código. En la CIUO, un código renombrado puede cambiar de sentido; en la atención, D-24 guarda la ocupación "al momento de la atención", pero el nombre se leería del catálogo actual. El docente pidió no reinterpretar ni alterar lo histórico (HU-020 c2, HU-028 c2), aunque lo dijo del código.
- Fuente: hojas 13 a 15; HU-020 c2; HU-028 c2; tanda 2, D-24.
- Corrección: decisión nueva con las dos opciones (nombre vigente con aviso del anterior, o nombre del momento del registro) y cuál viaja en el intercambio.

**M6. D-21 afirma de la Resolución 1888 de 2025 algo que solo vale para parte de los catálogos; la nota de la CUOC se queda corta.**
- Dónde: tabla de decisiones, D-21, columna "Por qué"; nota sobre ocupaciones.
- Cita: "la Resolución 1888 de 2025, que adopta el resumen digital de atención sobre la 866, remite a las tablas de referencia de SISPRO"; nota: "El PDF tiene el texto dañado y no se pudo confirmar".
- Lo que verifiqué: el artículo 1 dice "adoptar el Resumen Digital de Atención en Salud (RDA)" y el artículo 3, que el RDA "estará conformado por los elementos de datos clínicos relevantes definidos en la Resolución número 866 de 2021"; la resolución no modifica ni deroga la 866. El anexo técnico, en la copia del Diario Oficial 53.244 del 15 de septiembre de 2025 (página 17) que enlaza el normograma de la Supersalud, tiene capa de texto legible con columnas revueltas. En la tabla "Estructura de RDA de hospitalización", columna "Fuente del dato: valores permitidos", el elemento "7.1 Código de la ocupación" remite a clasificaciones.dane.gov.co/cuoc, a la tabla de SISPRO "PSRGOCUPACION" y al documento "clasificacion-unica-ocupaciones-colombia-CUOC-2024"; la palabra CIUO no aparece en el anexo. La misma tabla remite a tablas de SISPRO para país ("Estándar ISO 3166-1"), etnia, categoría de discapacidad, municipio y prestador; para "13.2 Comunidad étnica" dice "Texto alfanumérico" y anota "Diligenciar con el nombre de la comunidad étnica a la que pertenece el paciente, cuando aplique"; el tipo de documento sale de la "fuente Maestro Persona", y la entidad del plan, de los valores de la 866 y la guía de implementación.
- Respuesta: la nota de la CUOC no sobrepasa lo que se sabe; se queda corta, porque el anexo publicado sí remite a la CUOC 2024 para el elemento 7. Lo que sobrepasa es D-21: "remite a las tablas de referencia de SISPRO" va sin reserva y es falso para la comunidad étnica, que HU-037 trata como catálogo. La tanda 2 repite la frase en D-09.
- Corrección: apoyar D-21 en el artículo 8 de la 866 ("el procedimiento que los individualice en el Sistema Integrado de Información de la Protección Social- SISPRO"); citar en la nota la página 17 del Diario Oficial y poner primero la hoja 14 de la 866 como base de la CIUO-88 A.C.; la pregunta al docente se mantiene.

**M7. HU-037: no está verificado que exista el archivo oficial que la historia carga.**
- Dónde: HU-037, regla de fuente y precondición.
- Cita: "Fuente oficial: el 'catálogo de referencia de comunidades étnicas vigente' que nombra la hoja 15".
- Por qué falla: la hoja nombra el catálogo, pero no encontré el archivo, y el anexo de la Resolución 1888 trata el mismo elemento como texto libre (M6). Si el archivo no existe, la historia no se puede cumplir y el artículo 7 impide armarlo a mano. La cita tampoco es literal: la hoja dice "Según catálogo de referencia de comunidades étnicas, según catálogo de comunidades étnicas vigente".
- Fuente: anexo, hoja 15; Diario Oficial 53.244.
- Corrección: supuesto "pendiente de verificación: el equipo ubica la tabla en SISPRO antes del sprint; si no existe, pregunta al docente"; cita literal o sin comillas.

**M8. D-02 nombra dos áreas, no un cargo.**
- Dónde: regla del administrador de las ocho; D-02.
- Cita: "la persona del área de sistemas de información o de estadística de la IPS".
- Por qué falla: el docente pide "un cargo funcional real de la IPS" y escribe "no basta cambiar el nombre". "La persona del área de una u otra" deja dos opciones y ninguna es un cargo. El tablero registra la idea como "Hipótesis del modelo, sin fuente" (`llenar_tablero.py`, línea 627) y la tanda la presenta como propuesta del equipo.
- Fuente: c2 de HU-018 a HU-029; HU-022 c2.
- Corrección: elegir un cargo, por ejemplo "profesional de estadística", usarlo en la regla y dejar "administrador de catálogos" como el permiso que tiene ese cargo; la pregunta al docente sigue.

**M9. D-03 no decide, y una versión con el otro tipo de código vacía el catálogo.**
- Dónde: D-03; HU-018, regla de fuente.
- Cita: "sirve el alfabético (COL) o el numérico (170); el equipo anota cuál trae el archivo al descargarlo".
- Por qué falla: con RC-8, si una versión trae el otro tipo, todos los códigos viejos quedan "ya no están" y se inactivan, y los nuevos se crean; los pacientes quedan con códigos inactivos. Ninguna regla lo detiene. El anexo de la Resolución 1888 remite la nacionalidad a la tabla "Pais" de SISPRO; no pude abrirla, así que no sé qué tipo usa.
- Fuente: numeral 1, hoja 13; Diario Oficial 53.244.
- Corrección: fijar D-03 al tipo de la tabla "Pais" de SISPRO y rechazar el archivo cuyos códigos son del otro tipo.

**M10. El cambio de código no admite reemplazo por un código que ya existe, y la versión nueva pierde el vínculo.**
- Dónde: "Cambio oficial de código" y "Creación o cambio rechazado" (HU-018 7 y 9; HU-022 y HU-026 9 y 11; HU-037 y HU-038 7 y 9).
- Cita: "inactiva el código viejo con el motivo 'Reemplazado por' y el código nuevo, y crea el nuevo"; "el código ya existe [...] no guarda nada".
- Por qué falla: cuando la fuente funde dos valores (una EPS que se liquida y pasa sus afiliados a otra que ya existe), el código destino ya está y el esc. 9 rechaza el cambio; queda una inactivación sin vínculo. Si el mismo cambio llega por versión nueva, se ve como un código nuevo y uno retirado con "No está en la versión", sin "Reemplazado por". El mismo hecho deja dos rastros distintos.
- Fuente: HU-020 c2, "Validar correspondencia oficial".
- Corrección: permitir "Reemplazado por" hacia un código existente; en la vista previa de la versión, dejar que el administrador marque qué código reemplaza a cuál.

### Baja

**B1. Trazabilidad: ocho correspondencias no coinciden con el escenario que nombran (punto G).**

| Historia | Dice | Problema | Debe decir |
|---|---|---|---|
| HU-018 | "el 3 (país que ya existe) va en el 9 y el 4" | El 4 es "Código repetido en el archivo", dos filas iguales dentro del archivo. Un país que ya está y vuelve en un archivo cae en el 5, donde se compara | "va en el 9 y el 5" |
| HU-022 | "el 6 (ocupación que ya existe) va en el 11 y el 4" | Igual | "va en el 11 y el 7" |
| HU-026 | "el 4 (código que ya existe) va en el 11 y el 4" | Igual | "va en el 11 y el 7" |
| HU-018, de HU-019 | "el 5 (conteo) va en el 12 y el 17" | El 12 no muestra cuántos valores hay; el único conteo está en la carga | "lo reemplaza el 17", o agregar el total al 12 |
| HU-022 | "el 5 (padre e hija) va en el 17" | El antiguo 5 es "Distinción entre padre e hija": al registrar solo se ofrecen hijas. Eso lo cubren la regla de hijas y HU-004 esc. 5; el 17 solo las marca. La etiqueta se confunde con el nuevo esc. 5 | "el 5 (solo se ofrecen hijas) lo cubren la regla de hijas y HU-004 escenario 5" |
| HU-022, de HU-024 | "el 1 y el 3 [...] son el 12" | El antiguo 3 comprobaba que "las hijas conservan su relación con ella"; el 12 no lo dice | Agregar al esc. 12: "Y sus hijas siguen bajo ella" |
| HU-018, HU-022, HU-026 | "el 4 y el 5 (formato, sin nombre) van en el 2"; "el 7 (sin nombre) [...] van en el 2"; "el 3 (sin código DANE) [...] van en el 2 y el 5" | Esos antiguos eran cargas sueltas ("indica el código sin el nombre"); su conducta hoy está en "Creación o cambio rechazado". En HU-026 el 5 es el cero perdido, no la falta de código | Agregar el 9 (HU-018) o el 11 (HU-022, HU-026); en HU-026, el antiguo 3 va en el 2 y el 11 |
| HU-026 | HU-028 c1: "en el antiguo escenario 5: RC-8 y escenario 5"; lista de antiguos: "el 4, el 5 y el 10 [...] van en el 11" | El mismo antiguo 5 va a dos escenarios distintos | Una sola correspondencia, con el motivo exacto en el 11 (M3) |

Las demás correspondencias de HU-018, HU-022 y HU-026 coinciden con las tandas 4a y 4b y con los
números de la tanda nueva; HU-001 escenario 4 es "Uso sin sesión".

**B2. Tarjetas largas y con texto repetido (puntos H e I).**
- Dónde: las ocho.
- Dato: cada tarjeta tiene entre 10.400 y 13.600 caracteres sin la trazabilidad; 2.331 son idénticos en las ocho (17 % a 22 % de cada una): RC-1 a RC-5 y RC-8, la regla del administrador, la de búsqueda y el supuesto de Excel. Dentro de cada tarjeta, RC-8 dice en prosa lo mismo que los escenarios 1 a 6; en HU-022 la inferencia de niveles está en reglas y en supuestos; en HU-026 la longitud del código está en dos reglas. Todas caben en la descripción: la más larga, HU-022, tiene unos 13.600 caracteres y el límite que se cita es 16.384, dato de un hilo de la comunidad de Atlassian, no de la documentación oficial.
- Por qué importa: quien abre la tarjeta lee un quinto de texto que ya vio en la anterior antes de llegar a lo propio del catálogo. El Prompt 2 pide "No haya duplicidad significativa entre historias".
- Corrección: una tarjeta fija "Reglas comunes RC-1 a RC-8" y, en cada historia, la línea "RC-1 a RC-5 y RC-8: ver la tarjeta de reglas comunes" (el docente acepta "Enlazar o incluir"); en la tarjeta solo las reglas propias; las inferencias solo en supuestos. Quita unos 2.300 caracteres por tarjeta sin tocar un escenario.
- Separar o fusionar: no fusionar HU-034 a HU-036; el backlog bajaría de 31 a 29 historias, por debajo del mínimo de 30 de [2.5]. Si se quiere acortar HU-022, la candidata es sacar a otra historia el mantenimiento de la jerarquía (esc. 13, 14, 17 y 24); la carga de padre e hija en el mismo archivo se queda, porque la pidió el docente en c3. El Prompt 2 lo permite cuando "Una historia resulte excesivamente amplia", y el backlog pasaría a 32.

**B3. Propuestas e inferencias presentadas como hechos.** HU-018 ("que son obligatorias en HU-004"), HU-036 ("La etnia es obligatoria") y HU-038 ("es obligatoria en el registro del paciente") dependen de D-19, que HU-004 marca como propuesta. HU-034 dice que las reglas de uso de AS, MS y SI "están en HU-004", y HU-004 dice "Falta decidir qué se exige en esos casos". HU-026, "Si la residencia habitual está fuera de Colombia, el municipio no aplica", está registrada como hipótesis en la memoria del proyecto. Corrección: agregar "(D-19, propuesta)" e "(inferencia)" donde corresponda.

**B4. Dependencias, prioridad y conteos.** "Lo usan HU-004 y HU-006" deja fuera a HU-005, que muestra el nombre de todos los catálogos (esc. 1); solo HU-034 la nombra. La ocupación también la guarda la atención (D-24, HU-007 y HU-009), así que en HU-022 "cuántos pacientes la usan" se queda corto. HU-037 tiene prioridad Media y HU-004, Alta, depende de ella. Corrección: HU-005 en las ocho; en HU-022, "cuántos pacientes y atenciones"; HU-037 en Alta o la dependencia explicada.

**B5. Permisos y motivos.** La matriz de la tanda 0 dice "crear, actualizar, inactivar" y HU-001 esc. 7, "cargar, consultar, crear, actualizar e inactivar"; falta reactivar, que las ocho dan al administrador. La lista de motivos de inactivación no está escrita, y "Inactivación sin motivo" la necesita para probarse. "Cancelar antes de confirmar" no incluye la reactivación. El conteo de pacientes al inactivar debe quedar en número, sin lista, porque el administrador no ve pacientes. Corrección: agregar reactivar a la matriz y a HU-001; escribir los motivos.

**B6. Listas del anexo: la razón para quitar el cambio de código vale también para nombres y altas.** HU-034 a HU-036: "los códigos de esta lista los fija el anexo". El anexo fija igual los nombres y la cantidad, pero las tres conservan "Creación por cambio oficial" (esc. 7) y "Cambio oficial de nombre" (esc. 9). Corrección: o todo cambio entra como versión nueva, o los tres cambios sueltos se conservan con la resolución que modifica el anexo como referencia.

**B7. Estado de decisiones y registro.** RC-1 llama "Decisiones del equipo" a D-17 y D-20. El tablero deja D-20 en "Pendiente" con la nota "En contra: el numeral 2.3 escribe 'Delete'; confirmar con el docente" (`llenar_tablero.py`, líneas 462 a 465), y la memoria del proyecto da D-17 como "Pendiente" (`MEMORIA-CONTEXTUAL.md`, línea 401). La tanda 0 cambió RC-8 a las 04:33 sin fila nueva en su Registro. Corrección: actualizar el tablero o la palabra; registrar el cambio de la tanda 0.

**B8. Implementación dentro de los criterios.** HU-022 esc. 5: "crea primero el padre y después la hija" (el orden interno no se ve); RC-8: "se guarda como texto" (D-18). El Prompt 2 pide resultados observables y "No se incluyan decisiones técnicas como requisitos funcionales". Corrección: "las dos quedan en el catálogo, la hija bajo su padre"; "el código conserva sus ceros: 05001".

**B9. Actores mezclados en la consulta.** Los escenarios de consulta y búsqueda dicen "alguien con sesión iniciada", en historias cuyo actor es el administrador; el Prompt 2 pide "Evitar mezclar necesidades de actores diferentes", y la búsqueda del auxiliar al registrar vive en HU-004. Corrección: escribirlos desde el administrador y dejar el permiso del auxiliar y del médico en "Cargo sin permiso".

**B10. Valor de negocio de HU-035.** "La lista tiene 8 valores, así que es catálogo con su CRUD" es la razón de que exista la historia, no su valor. Corrección: "Sin este catálogo no se registran las discapacidades del paciente, que la guía permite en plural [2.1]".

## 4. Lo que está bien y no se debe tocar

- Longitudes, conteos y hojas coinciden con el PDF del anexo (hojas 13 a 16, páginas 1 a 4): códigos de 3, 2, 4, 2, 3, 5, 2, 3 y 6; nombres de 3 a 200 en los numerales 1, 7, 11, 12 y 15; 14, 8 y 7 valores; "Ninguna de las anteriores" con 99; "Sin discapacidad".
- La comunidad étnica para las etnias 01 a 06 la sostiene la definición de la hoja 15 ("comunidad indígena, pueblo ROM o comunidad negra, Afro, palanquera y raizal"), y HU-037 no inventa a qué etnia pertenece cada comunidad.
- El cambio de código como inactivar el viejo y crear el nuevo, con los pacientes conservando el viejo: responde HU-020 c1 y c2 y HU-028 c2.
- El cero inicial de HU-026: regla, motivo con 5001 y 05001, "El sistema no completa ceros".
- "Ocupación general con hijas activas" (HU-022 esc. 24) y "sin hijas" en la jerarquía (esc. 17).
- Escenario de cargo con sesión y sin permiso en las ocho.
- Artículos 7, 8 y 12 de la 866 bien citados, cotejados en el PDF del Ministerio: "no podrán ser modificados, reducidos o adicionados"; "estarán a cargo del Ministerio de Salud y Protección Social"; "serán revisados y ajustados cuando el Ministerio de Salud y Protección Social lo considere pertinente".
- Los trece campos del Prompt 2, más el mockup, en las ocho; numeración continua, de 21 a 28 escenarios; sin guion largo ni intensificadores.
- La trazabilidad se genera con marcadores: al agregar el escenario de versión con rechazos, los números se recalculan solos.

## 5. Lo que no pude verificar

- Contenido de las tablas de SISPRO (país, etnia, discapacidad, municipio, ocupación) y si existe una tabla de comunidades étnicas: SISPRO bloquea la consulta automática. Por eso D-03 y M7 quedan abiertos.
- El anexo de la Resolución 1888 lo leí en la copia del Diario Oficial 53.244 enlazada desde el normograma de la Supersalud, con pdftotext; no lo cotejé con la edición de la Imprenta Nacional.
- Artículos 6, 7, 8 y 12 de la 866: cotejados en la capa de texto (OCR) del PDF del Ministerio, que tiene errores menores de letra.
- La conducta de Excel (marca BOM, ceros) es conocida; no la probé aquí.
- El límite de 16.384 caracteres de Trello sale de un hilo de la comunidad de Atlassian.
- Si D-17 y D-20 ya se decidieron fuera de los archivos: el tablero las deja "Pendiente".

Fuentes consultadas fuera del repositorio:
- [Resolución 866 de 2021, PDF del Ministerio de Salud](https://www.minsalud.gov.co/sites/rid/Lists/BibliotecaDigital/RIDE/DE/DIJ/resolucion-866-de-2021.pdf)
- [Resolución 866 de 2021, normograma de la Supersalud](https://normograma.supersalud.gov.co/compilacion/docs/resolucion_minsaludps_0866_2021.htm)
- [Resolución 1888 de 2025, normograma de la Supersalud](https://normograma.supersalud.gov.co/compilacion/docs/resolucion_minsaludps_1888_2025.htm)
- [Resolución 1888 de 2025, anexo técnico, copia del Diario Oficial 53.244](https://normograma.com/documentospdf/PDF/R_MSPS_1888_2025_ANEXO.pdf)
- [Límite de la descripción de Trello, comunidad de Atlassian](https://community.atlassian.com/forums/Trello-questions/Is-there-a-text-limit-in-the-quot-Description-quot-field-of/qaq-p/838401)
