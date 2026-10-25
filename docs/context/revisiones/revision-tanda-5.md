# Revisión independiente. Tanda 5 del backlog v3 (HU-030 a HU-033 y HU-039 a HU-042)

Archivo revisado: `/home/claude/ips-validacion/backlog-v3/backlog-v3-tanda-5-diagnosticos-y-catalogos-atencion.md`,
versión 1 del 2026-10-20. El revisor no escribió el texto. Los números de línea ("l.") son de ese
archivo salvo que se diga otro.

Cambios durante la revisión, comprobados antes de entregar:
- La tanda 5 se rearmó a las 04:56 UTC desde `partes/tanda-5-cie10.md`, con las mismas 1421 líneas.
  Releí HU-030 a HU-033: el único cambio es la l. 206, donde "(tanda 2)" pasó a "(HU-007)". HU-039 a
  HU-042 siguen iguales a la salida del generador. Las 97 citas de este informe siguen en sus líneas.
- La tanda 0 cambió a las 04:41 y a las 04:58. Sigue en versión 3; las 38 copias de RC de la tanda 5
  son idénticas, letra por letra, a sus RC-1 a RC-8; la matriz agrega "reactivar" a la fila de
  catálogos, lo que coincide con la tanda 5.
- La tanda 2 cambió a las 04:56. Sigue en versión 2 y las líneas que cito dicen lo mismo.
- `llenar_tablero.py` (04:42) y `generar_catalogos.py` (04:46): las filas del tablero siguen en las
  líneas 205 y 332 a 338.

No leí `revisiones/revision-tanda-2.md`; de `revision-tanda-3.md` leí el encabezado y el primer
hallazgo, para seguir su formato.

## 1. Veredicto

**Necesita cambios**: el anexo está bien transcrito y D-09 y D-14 se confirman en fuente oficial, pero HU-032 deja que una versión nueva cambie el nombre del diagnóstico en atenciones ya registradas, HU-030 supone sin fuente que la tabla oficial del CIE-10 trae la columna del padre, y la división en cuatro historias se apoya en el mínimo de 30 (22 hallazgos: 3 altos, 9 medios, 10 bajos).

TL;DR, por dónde empezar:
- HU-032: la atención conserva el nombre con que se registró (hallazgo 1).
- HU-030: pasar la estructura del archivo oficial a supuestos y definir el plan B del padre (hallazgo 2).
- Quitar el argumento de las 30 historias y decidir si HU-032 y HU-033 se funden en HU-030 (hallazgo 3).

## 2. Cobertura

El mockup y la estimación quedan pendientes a propósito y no se cuentan.

### Comentarios del docente de HU-030 y filas del tablero

| Fuente | Pedido | Estado | Dónde | Hallazgo |
|---|---|---|---|---|
| HU-030 c1 | Cargo funcional real en descripción, precondiciones y escenarios | Atendido | Descripción l. 55; regla l. 68-70; precondición l. 109 | |
| HU-030 c1 | Formato de archivo o formulario y campos | Atendido con reserva | RC-8 l. 95-106; columnas l. 76-77 | 2 |
| HU-030 c1 | Orden de carga padre e hijo | Atendido con hueco | Regla l. 81-83; esc. 3 (l. 126-130) | 5 |
| HU-030 c1 | Versión de referencia | Atendido | D-09 (l. 29); regla l. 74-75; esc. 8 (l. 153-157) | 21 |
| HU-030 c1 | Cómo se valida que corresponde a la requerida | En parte | l. 86-87; esc. 9 y 10 (l. 159-168) | 8 |
| HU-030 c1 | Rol autenticado sin permisos | Atendido | Esc. 15 (l. 191-195) | |
| HU-030 c1 | Enlazar o incluir RC-1, RC-3 y RC-5 | Atendido | l. 88-94 | 3 (RC-1 choca con la división) |
| HU-030 c2 | Conservar los CA y precisar resultados | Atendido | Trazabilidad l. 216-219 | 16 |
| HU-030 c2 | Edición oficial adoptada | Atendido | D-09 | 21 |
| HU-030 c2 | Códigos y descripciones | Atendido con reserva | l. 71-73 | 7 |
| HU-030 c2 | Raíces | Atendido | l. 78-80; esc. 2 (l. 120-124) | 2 |
| HU-030 c2 | Formato de importación | Atendido con reserva | RC-8 | 2 |
| Tablero l. 205 | Cargo con función y permisos (D-02) | Atendido en HU-030, HU-032 y HU-033; HU-031 pasa al médico | l. 68, 374 y 533 | |
| Tablero l. 332 | Archivo oficial con código, descripción y padre; padres antes que hijos; informe de rechazados | Atendido; el orden pasa a "sin importar el orden", declarado en la regla | l. 76-83; esc. 3 y 5 | 2, 5 |
| Tablero l. 333 | Qué versión y cómo se comprueba; raíces; solo códigos de 4 | En parte: falta "cómo se comprueba" | D-09; esc. 2, 10 y 13 | 8 |
| Tablero l. 334 | Cargo sin permiso; RC-1, RC-3 y RC-5 | Atendido | Esc. 15; l. 88-94 | |
| Tablero l. 336 (HU-031, retro sin verificar) | Esc. 4 con resultado concreto (D-22); búsqueda sin resultados; códigos inactivos | Atendido | Esc. 4, 6 y 7 (l. 278-298) | |
| Tablero l. 337 (HU-032, retro sin verificar) | Solo por cambio oficial con su versión; los retirados se inactivan; cargo sin permiso | Atendido | Reglas l. 377-388; esc. 2, 3, 9 y 12 | 1, 4 |
| Tablero l. 338 (HU-033, retro sin verificar) | Inactivar con motivo, quién y cuándo; reactivar; esc. 2 como HU-021; padre con hijos activos; cargo sin permiso | Atendido | Esc. 1, 2, 3, 4, 8 y 11 | 4, 5 |

Total: 19 pedidos; 17 atendidos, 2 en parte (cómo se valida la versión, que piden el c1 y la fila
333) y ninguno sin atender. Cuatro de los atendidos tienen una reserva que va en los hallazgos.

### Fidelidad al anexo (cotejado con el PDF, páginas 4 a 6)

| Dato | Tanda 5 | Hoja del anexo | Resultado |
|---|---|---|---|
| Código CIE-10 | 4 y 4 (l. 31, 72, 248) | Hoja 18: formato 9999, máxima 4, mínima 4 | Coincide |
| Código de enfermedad huérfana | 1 a 4 (l. 1239) | Hoja 18: 9999, 4 y 1 | Coincide; el "componente" que nombra no existe con ese nombre (hallazgo 18) |
| Nombre del diagnóstico | 3 a 200 (l. 72-73) | Hoja 18: 200 y 3 | Coincide; la tanda lo aplica al catálogo (hallazgo 7) |
| Tipo de diagnóstico | No aparece | Hoja 18: 01 a 03 | Sin defecto: 3 valores, no es catálogo; lo trata HU-007 |
| Modalidad de realización | 9 valores, hoja 16 | Hoja 16: 01 a 09 | Coincide |
| Grupo de servicios | 5 valores, no es catálogo | Hoja 16: 01 a 05 | Coincide |
| Vía de ingreso | 14 valores, hojas 16 y 17 | Hoja 16: 01 a 07; hoja 17: 08 a 14 | Coincide |
| Causa que motiva la atención | 29 valores, códigos 21 a 49, hoja 17 | Hoja 17: 21 a 49 | Coincide |

### Afirmaciones externas

| Afirmación | Resultado |
|---|---|
| D-09: la fuente es la tabla CIE-10 de SISPRO y el anexo de la Resolución 1888 de 2025 remite a ella | Confirmado. Los elementos 23.1, 23.2 y 23.3 (RDA de hospitalización) y 37.1 (RDA de pacientes) remiten a `ConsultarDetalleReferenciaBasica.aspx?Code=CIE10` (Diario Oficial 53.244, p. 16 y 17) |
| D-09: el anexo dice que la transición al CIE-11 se aplazó | Confirmado, con fecha: "Al momento de la publicación del presente manual, el plazo para la transición de la codificación de diagnósticos de CIE 10 a CIE 11 ha sido ampliado" (p. 17, 15 de septiembre de 2025). El CIE-11 figura como dato "Opcional" |
| D-09: "lectura de un PDF con texto dañado, por confirmar" | La salvedad sobra: la copia del Diario Oficial a la que remite el normograma de la Supersalud tiene texto legible (hallazgo 21) |
| D-14: la Resolución 2625 de 2025 derogó la 023 de 2023 | Confirmado (artículo 7, Diario Oficial 53.339, p. 16) |
| D-14: numera cada enfermedad de forma consecutiva y exclusiva (artículo 3) | Confirmado. El artículo dice además que el número no se regenera y no se reasigna aunque la enfermedad salga del listado (hallazgo 11) |
| D-14: los números caben en 1 a 4 caracteres (inferencia del equipo) | Compatible con la fuente: el último número del anexo, versión 5.0, es 2273. La tanda lo marca como inferencia |
| HU-030: el archivo oficial trae la columna del padre y los niveles por encima de la categoría | No verificado. Se afirma sin marcarlo como supuesto (hallazgo 2) |
| HU-039 a HU-041: "la tabla que publica el Ministerio de Salud" | Modalidad: contradicho por la 1888, que remite a los valores de la 866. Vía de ingreso y causa: la 1888 nombra las tablas ViaIngresoUsuario y RIPSCausaExterna; su contenido no se pudo verificar (hallazgo 10) |

¿Afirma el texto más de lo que sabe? En D-09 y D-14, no: lo que dicen se confirma y la inferencia
está marcada. Sí lo hace en la estructura del archivo del CIE-10 (hallazgo 2), en la fuente de la
modalidad (hallazgo 10) y en "la comprobación contra el archivo oficial lo confirma" (hallazgos 8 y
10).

### Trazabilidad de los escenarios antiguos (punto G)

Cotejé las 40 correspondencias con la fuente 7 (l. 417-776) y con el texto del escenario nuevo.

| Historia | Exactas | Inexactas |
|---|---|---|
| HU-030 | 9 de 10 | El antiguo 6 (código que ya existe en el catálogo) no "va en el 7": el 7 es un código repetido dentro del archivo |
| HU-031 | 9 de 10 | El antiguo 1 (entregar todos los códigos) no está en el 2 ni en el 5 |
| HU-032 | 8 de 10 | El antiguo 4 y el antiguo 9 (hallazgo 16) |
| HU-033 | 10 de 10 | El 2 pasa de bloquear a permitir con confirmación; lo explica la l. 621-623 |

El antiguo 9 de las cuatro historias (sin sesión) remite a HU-001 escenario 4, que es "Uso sin
sesión" (tanda 1, l. 72-75): correcto.

### Campos, escenarios y reglas comunes (puntos F y H)

- Campos: las ocho historias tienen los 13 del Prompt 2 (l. 203-317), más Mockup y Trazabilidad.
- Escenarios: de 11 a 23 por historia; la tabla de la l. 35-45 (142 en total) cuadra con el texto.
  Los que se repiten, no pueden ocurrir o contradicen la fuente están en los hallazgos 6, 11, 14, 15
  y 19.
- Reglas comunes copiadas: HU-030 (1, 3, 5 y 8), HU-031 (2 y 4), HU-032 (1, 3, 4, 5 y 8), HU-033 (1,
  4 y 5; falta la 3, hallazgo 4), HU-039 a HU-042 (1 a 5 y 8). Las 38 copias son idénticas a la
  tanda 0. RC-1 choca con la división del CIE-10 (hallazgo 3).
- Matriz de permisos: se aplica igual en las ocho, con escenario de cargo sin permiso en cada una.

## 3. Hallazgos

### Alta

**1. Una versión nueva cambia el nombre del diagnóstico en atenciones ya registradas**

- Ubicación: HU-032, regla l. 384-385 y esc. 4 (l. 434-438).
- Cita: "Si un código cambia de descripción, las atenciones lo muestran con la nueva, porque el código es el mismo."
- Por qué: el nombre del diagnóstico es un componente del registro de la atención: "Nombre del diagnóstico principal", de 3 a 200 caracteres (numeral 23, hoja 18). HU-007 lo guarda con la atención: "el sistema guarda el código con el nombre que le da el catálogo" (tanda 2, esc. 8, l. 129) y "Diagnóstico principal de ingreso: código y nombre ... el nombre lo pone el sistema" (tanda 2, l. 56). Desde que la atención queda registrada sus datos son permanentes y todo cambio es una corrección con rastro (tanda 2, l. 68-70; RC-6). Mostrar el nombre nuevo en una atención vieja cambia un dato registrado sin corrección y sin rastro. La misma historia promete lo contrario: "sin cambiar los que ya están registrados" (l. 362) y "si la versión nueva cambiara las atenciones ya registradas, se perdería lo que el médico diagnosticó" (l. 365-366). El RDA de la Resolución 1888 también trata el nombre como dato propio (elemento 23.2).
- Fuente: anexo, hoja 18; tanda 2, HU-007; RC-6 (tanda 0, l. 88-90).
- Corrección: "La atención conserva el nombre con que se registró; la consulta (HU-008) puede mostrar además la descripción vigente del catálogo, marcada como tal." Ajustar la regla y el esc. 4. En HU-039 a HU-042 (esc. 10 u 11) no pasa lo mismo, porque el anexo no define un nombre de modalidad, vía, causa ni enfermedad huérfana dentro de la atención; conviene escribirlo para que no se lea como contradicción.

**2. HU-030 supone, sin fuente, que la tabla oficial del CIE-10 trae la columna del padre y las categorías**

- Ubicación: HU-030, reglas l. 76-80; supuesto l. 207-208; RC-8, l. 98-99.
- Cita: "Columnas del archivo: código; descripción; código del padre (vacío en las raíces)" y "Los niveles que haya por encima de la categoría los trae el archivo oficial".
- Por qué: la regla describe el archivo oficial, porque el supuesto de l. 207-208 obliga a guardarlo "como CSV sin cambiar su contenido". Si la tabla CIE10 de SISPRO no trae columna de padre ni filas de 3 caracteres, pasa una de dos cosas: RC-8 rechaza el archivo completo por la columna que falta, o el administrador la agrega y cambia el contenido oficial, que el supuesto y RC-3 prohíben. Sin las categorías, cada fila cae en el esc. 4 ("el padre no existe") y la jerarquía que pide [2.2] no se carga. Hay un indicio en contra: el listado oficial de enfermedades huérfanas (Resolución 2625 de 2025) escribe los códigos CIE-10 siempre con 4 caracteres, con X en las categorías sin subdivisión (D71X en las filas 400 a 403, D67X en la 411), así que las tablas del Ministerio pueden traer solo códigos de 4. La tanda 4 dejó para la CIUO-88 el pendiente "los niveles se confirman con el archivo oficial" (tanda 4, l. 52); HU-030 no deja uno equivalente.
- Fuente: tabla CIE10 de SISPRO, sin acceso (conexión cerrada por el servidor y robots.txt que bloquea la lectura automática); Resolución 2625 de 2025, anexo. No verificado.
- Corrección: pasar la estructura del archivo a "Supuestos o asuntos pendientes", por verificar con la tabla de SISPRO, y escribir el plan B: el padre de un código de 4 caracteres es la categoría de sus 3 primeros caracteres (glosario de la tanda 0, l. 142), y los nombres de las categorías salen de una segunda fuente oficial que la historia nombra.

**3. La división del CIE-10 en cuatro historias descansa en el mínimo de 30 y no resiste la comparación con HU-022**

- Ubicación: l. 11-23; RC-1 copiada en HU-030 (l. 88-89), HU-032 (l. 389-390) y HU-033 (l. 544-545).
- Cita: "Con una sola historia de administración, el backlog quedaría en 29, por debajo del mínimo de 30 que pide la guía [2.5]."
- Respuesta corta al punto E: HU-031 se sostiene; HU-032 y HU-033 no, con lo que la tanda escribe; el mínimo de 30 no es un argumento para dividir.
- Por qué:
  - La cuenta es correcta: 31 historias (tanda 0, l. 171) menos HU-032 y HU-033 dan 29, y [2.5] pide "como mínimo 30". Pero el conteo no está entre los criterios del Prompt 2 para dividir (l. 321-329), y la guía pide "todas las historias de usuario necesarias para el desarrollo del Mínimo Producto Viable" [2.5]: una historia cuenta porque se necesita, no al revés. Mi lectura: el Prompt 2 rechaza completar un mínimo con escenarios artificiales (l. 291), y la misma lógica vale para historias. Escrito en la tanda, el argumento le dice al docente que la división sirve al número y debilita los otros tres.
  - El argumento de actores distintos sostiene HU-031 (el médico busca y elige) frente al administrador, por "actores distintos con objetivos diferentes" y por el criterio de permisos diferenciados (Prompt 2, l. 323 y 326). Aun así, sus esc. 3, 4 y 13 repiten HU-007 esc. 8 y 9 y HU-009 esc. 2 a 4.
  - El de ciclos de vida no separa al CIE-10 de la CIUO-88. HU-022 tiene niveles, padre e hija en el mismo archivo, cambio de padre, ciclos y "general con hijas activas" en una sola historia de 28 escenarios (tanda 4, l. 61). La frase "cosa que no pasa en las listas sin niveles" (l. 19) es cierta, pero la ocupación es una lista con niveles. Versiones e inactivación existen en todos los catálogos (RC-8 y RC-4), y la convención de la tanda 0 ("el catálogo cambia por versión", l. 117-118) vale para todos.
  - Quitado HU-031, lo que queda (HU-030 crear, HU-032 actualizar, HU-033 inactivar) es la división por "Operaciones de base de datos" que el Prompt 2 excluye como único criterio (l. 336) y que la tanda 0 rechaza para los demás catálogos.
  - "Excesivamente amplia" tampoco los separa sin un umbral escrito: una historia de administración fundida tendría unos 33 escenarios (40 menos 7 repetidos: versión antigua, comprobación, cancelar, sin permiso e historial; cuenta propia), frente a 28 de HU-022.
  - Las tres tarjetas copian RC-1, "una historia por catálogo (D-17)", al lado de un catálogo con cuatro.
  - Con 31 historias el margen sobre el mínimo es de una: si el docente fusiona una sola, el backlog baja de 30.
- Fuente: Prompt 2, l. 291 y 319-338; guía [2.5]; tanda 0, l. 110-118 y 171; tanda 4, l. 61 y 293-352.
- Corrección, como preguntas: ¿qué tal si HU-032 y HU-033 se funden en HU-030, HU-031 queda aparte por actor, y el conteo se completa con una historia que hoy falta, como la anulación de una atención registrada al paciente equivocado, que HU-009 deja pendiente (tanda 2, l. 469-471)? Si se mantienen las cuatro, ¿qué tal quitar la viñeta del mínimo, escribir un criterio que no aplique también a la CIUO-88 y agregar a la RC-1 de esas tres tarjetas "salvo el CIE-10 (convención de la tanda 0)"?

### Media

**4. La comparación de versiones y la comprobación no ven los códigos oficiales que están inactivos**

- Ubicación: HU-032, regla l. 377-379 y esc. 1 y 2; HU-030 esc. 10 (l. 164-168); plantilla esc. 5 y 17 (HU-039, l. 716-720 y 781-785); HU-033, reglas l. 536-537 y 542-543.
- Cita: "lista los códigos del archivo que faltan en el catálogo, los activos que no están en el archivo y las descripciones o padres que no coinciden".
- Por qué: un código inactivo en el catálogo y vigente en el archivo no es nuevo (existe, y la clave es única), no cambia y no "ya no está". No encaja en ninguna lista, y la historia no dice si se reactiva, si choca con la clave o si se ignora. Llega a ese estado por dos caminos: inactivado "por error" en HU-033 cuando era oficial, o retirado en una versión y repuesto en la siguiente. La comprobación tampoco lo muestra, porque solo lista códigos que faltan y activos que sobran. El resultado es un catálogo con menos valores que el oficial, que el artículo 7 prohíbe ("no los pueden ... reducir", RC-3). HU-033 no copia RC-3, aunque inactivar es la operación que reduce el catálogo, y no tiene escenario que impida reactivar un código que la fuente retiró: la regla de l. 542 solo habla del inactivado por error. La plantilla de HU-039 a HU-042 tiene el mismo hueco (esc. 20 o 21).
- Fuente: RC-3 y RC-8 (tanda 0, l. 81-84 y 93-104).
- Corrección: agregar a la comparación la lista "inactivos que la versión trae", que se reactivan al confirmar; que la comprobación liste "inactivos que están en el archivo"; en HU-033, copiar RC-3, aceptar "cargado por error" solo si el código no está en la versión vigente y agregar el escenario "no se reactiva un código que la fuente retiró".

**5. La regla de jerarquía de HU-033 no se aplica en la carga ni en la versión nueva**

- Ubicación: HU-033, l. 539 y 542-543 (esc. 3 y 9); HU-030, regla l. 81-83 y esc. 11 y 12 (l. 170-178); HU-032 esc. 2 (l. 422-426).
- Cita: "No se reactiva un código cuyo padre está inactivo: primero se reactiva el padre."
- Por qué: las reglas de HU-033 existen "para que la jerarquía no quede con hijos activos bajo un padre inactivo" (l. 616-617), pero otras dos historias permiten ese estado. (a) HU-030 esc. 12 rechaza la creación si el padre "no existe", no si está inactivo: el esc. 11 crea un código activo bajo una categoría inactiva. (b) HU-032 crea, cambia de padre e inactiva "según esa lista" sin decir qué pasa si la versión retira una categoría cuyos hijos siguen, o trae un hijo cuyo padre está inactivo. (c) En la carga, "una fila se acepta si su padre ... viene en el mismo archivo" no dice qué pasa si la fila del padre se rechaza. Ejemplo: el esc. 7 rechaza las dos filas de un código repetido; si ese código es una categoría, sus hijos quedan aceptados con un padre que no se creó.
- Fuente: HU-033, supuestos l. 616-617.
- Corrección: escribir la regla una vez ("ningún código activo bajo un padre inactivo o inexistente") y aplicarla en HU-030, HU-032 y HU-033; en la carga, "su padre viene aceptado en el mismo archivo", con rechazo en cascada de los hijos y su motivo.

**6. La jerarquía del CIE-10 se copió del molde de la CIUO-88 sin la regla propia del CIE-10**

- Ubicación: HU-030, l. 78-80; HU-032, reglas l. 386-388 y esc. 5 y 6 (l. 440-449).
- Cita: "Un cambio de padre no puede dejar a un código como padre de su propio padre (ciclo)."
- Por qué: en el CIE-10 el padre de un código de 4 caracteres está dentro del código: la categoría son sus 3 primeros caracteres (glosario, tanda 0, l. 142). La historia no lo exige. Acepta que el archivo diga que A001 cuelga de B20, porque B20 es una categoría de 3, y deja que el administrador cambie a mano el padre de un código de 4 "con la referencia oficial" (l. 387-388). Al mismo tiempo gasta dos escenarios (5 y 6) en casos que no ocurren entre los niveles de 4 y de 3: un código de 4 no puede ser padre de su categoría. Solo tienen sentido en niveles superiores, si el archivo los trae, y eso depende del hallazgo 2. La regla del ciclo viene de HU-022 (tanda 4, l. 327).
- Fuente: tanda 0, l. 142; tanda 4, l. 327.
- Corrección: regla "el padre de un código de 4 caracteres es la categoría de sus 3 primeros caracteres; se rechaza la fila o el cambio que diga otra cosa"; limitar los esc. 5 y 6 a los niveles superiores, o quitarlos si el archivo no los trae.

**7. Una fila oficial que no cumple el numeral bloquea las versiones o lleva a editar el archivo oficial**

- Ubicación: HU-030, l. 71-73 y RC-8; HU-032, l. 382-383 y esc. 8 (l. 456-460); plantilla esc. 7; supuesto de formato (l. 207-208).
- Cita: "pide corregir el archivo" (l. 460) frente a "lo guarda como CSV sin cambiar su contenido" (l. 207-208).
- Por qué: la regla "una versión nueva con filas rechazadas no se aplica" acierta para errores de conversión (ceros perdidos, codificación). Si la fila viene así de la fuente, no hay corrección legítima: corregir es modificar el catálogo oficial (artículo 7, RC-3), y no corregir deja el catálogo en la versión vieja para siempre. En la primera carga, "guardar las aceptadas" deja fuera códigos oficiales, que es reducir el catálogo. El caso es posible en el CIE-10, porque HU-030 aplica a cada fila del catálogo el límite de 3 a 200 que el numeral 23 fija para el nombre que guarda la atención.
- Pendiente de verificación: medir en la tabla de SISPRO la descripción más larga; las del grupo Y83 a Y84 son candidatas a pasar de 200. No pude abrir la tabla.
- Fuente: RC-3 y RC-8; hoja 18.
- Corrección: separar dos motivos de rechazo: error de conversión (se corrige la conversión y se vuelve a subir) y contenido oficial fuera del numeral (se carga y se informa, o se decide qué manda). Llevar esa decisión al docente como pregunta.

**8. La validación de "la versión requerida" que pidió el docente es circular**

- Ubicación: HU-030, l. 86-87 y esc. 9 y 10 (l. 159-168); trazabilidad l. 213.
- Cita: "Que la versión cargada sea la pedida se comprueba comparando el catálogo con el archivo oficial (escenario 10)."
- Por qué: el esc. 10 compara el catálogo con el mismo archivo que se cargó; prueba que la carga es fiel al archivo, no que el archivo sea la versión pedida. El esc. 9 compara fechas que escribe el propio administrador. El docente pidió "cómo se valida que corresponde a la requerida" (HU-030 c1), y la respuesta no lo valida. La tanda 4 ya reconoce el límite: "El sistema no puede consultar a la ISO ni al DANE" (tanda 4, l. 27).
- Fuente: comentarios del docente, l. 664.
- Corrección: decir que comprobar que es la última versión es un paso fuera del sistema: el administrador descarga la tabla de SISPRO el día de la carga y registra la dirección y la fecha de descarga, que el sistema guarda y muestra (esc. 8). El esc. 10 queda como prueba de que la carga es fiel al archivo.

**9. Borradores con un código que después se inactiva o que la versión retira**

- Ubicación: HU-032 esc. 3 (l. 428-432); HU-033, regla l. 538 y esc. 2 (l. 562-566).
- Cita: "Dado que hay atenciones registradas con un código que la versión nueva retira".
- Por qué: HU-007 permite guardar una atención como "Borrador" con el diagnóstico ya elegido (tanda 2, l. 65-67; su esc. 13, l. 156-160, es un borrador al que solo le falta la causa). Si ese código se inactiva antes de completarla, ni HU-032 ni HU-033 dicen si el borrador cuenta entre las atenciones que usan el código, ni qué pasa al completarlo. RC-2 y HU-007 esc. 4 (tanda 2, l. 103-107) rechazan un valor inactivo, pero el médico no lo eligió mal. Queda un caso sin resolver entre tres historias.
- Fuente: tanda 2, HU-007; D-08.
- Corrección: decidir y escribir: el conteo incluye borradores, y al completar uno con un código inactivo el sistema pide elegir otro (escenario en HU-007 o en HU-033).

**10. Las fuentes de HU-039 a HU-041 mezclan dos orígenes, y la comprobación no puede confirmar el anexo**

- Ubicación: HU-039, l. 658-660; HU-040, l. 853-855; HU-041, l. 1047-1049; generador, función `lista_anexo` (`generar_catalogos.py`, l. 552).
- Cita: "en la tabla que publica el Ministerio de Salud (D-21). La primera carga debe traer esos 9 valores con el código y el nombre de la hoja 16; la comprobación contra el archivo oficial lo confirma."
- Por qué: (a) para la modalidad, el anexo de la Resolución 1888 de 2025, que la D-21 de la tanda 4 usa como justificación, no remite a una tabla: escribe "Valores permitidos según Resolución 866 de 2021" y copia los 9 valores (Diario Oficial 53.244, p. 16 y 17). Para la vía de ingreso y la causa sí nombra tablas de SISPRO, ViaIngresoUsuario y RIPSCausaExterna (p. 17), que la historia no nombra; no pude verificar que su contenido sea igual al de las hojas 16 y 17. (b) La comprobación compara el catálogo con el archivo que se cargó: si la tabla del Ministerio difiere del anexo en un código o en un nombre, no lo ve, y "lo confirma" afirma algo que el mecanismo no hace. (c) La historia no dice qué fuente manda si la tabla y la hoja no coinciden.
- Fuente: anexo de la Resolución 1888 de 2025.
- Corrección: nombrar la fuente de cada lista (modalidad: hoja 16, transcrita por el equipo; vía: tabla ViaIngresoUsuario; causa: tabla RIPSCausaExterna), decir que manda el anexo y agregar una comprobación contra la lista del anexo: cantidad, códigos y nombres.

**11. HU-042 tiene un escenario de cambio de código que el artículo 3 de la Resolución 2625 no prevé**

- Ubicación: HU-042 esc. 8 (l. 1317-1321); D-14 (l. 30).
- Cita: "Dado que la fuente oficial reemplazó el código de una enfermedad huérfana por otro".
- Por qué: el artículo 3 dice que el número se asigna "sin generar un nuevo consecutivo" y que "es exclusivo y no puede ser asignado a ninguna otra, incluso si el número de identificación corresponde a una enfermedad excluida del listado". Una enfermedad no cambia de número entre versiones; que una salga y otra entre ya lo cubren los esc. 5, 9 y 19. El escenario viene de la plantilla de países y municipios (`generar_catalogos.py`, l. 106-110) y contradice la decisión que la misma tarjeta cita. Lo que el artículo 3 sí pide y la historia no dice: un número inactivo no se vuelve a usar para otra enfermedad.
- Fuente: Resolución 2625 de 2025, artículo 3 (Diario Oficial 53.339, p. 15).
- Corrección: quitar el esc. 8 (HU-042 queda con 22) y agregar a las reglas "un código inactivo no se reutiliza para otra enfermedad (artículo 3)".

**12. La precondición de HU-007 niega lo que HU-031 y la plantilla dicen del catálogo vacío**

- Ubicación: HU-031 esc. 9 (l. 305-308); plantilla esc. 16 o 17 (l. 779, 973, 1167 y 1369); tanda 2, HU-007, precondición l. 79-80.
- Cita: HU-007, "Los catálogos de modalidades, vías de ingreso, causas, diagnósticos y enfermedades huérfanas tienen valores activos"; plantilla, "el registro de la atención avisa que no hay modalidades para elegir (HU-007)".
- Por qué: si la precondición de HU-007 exige valores activos, el registro con un catálogo vacío no ocurre dentro de HU-007, y el aviso que describen HU-031 y la plantilla no tiene escenario en la historia que debe darlo. Es el mismo tipo de defecto que el docente objetó en HU-008: "Ajustar la precondición «al menos una atención» para permitir el caso sin atenciones del escenario 3". Con las enfermedades huérfanas el choque es mayor: el dato es opcional ("Solo si el paciente la presenta", tanda 2, l. 57), pero la precondición bloquea todo registro mientras ese catálogo esté vacío.
- Fuente: comentarios del docente, HU-008 c1 (l. 196).
- Corrección: en la tanda 2, quitar los catálogos de la precondición de HU-007 y agregar el escenario del aviso; en HU-042, decir que el catálogo vacío no impide registrar la atención.

### Baja

**13. El encabezado se contradice sobre los comentarios de HU-030**

- Ubicación: l. 7-9. Cita: "HU-030 a HU-033 no tienen comentarios del docente en la exportación [...] HU-030 sí tiene sus dos comentarios."
- Por qué: la exportación cubre HU-001 a HU-030 (comentarios, l. 4) y HU-030 tiene c1 y c2 (l. 660-681).
- Corrección: "HU-031 a HU-033 no tienen comentarios del docente en la exportación".

**14. HU-030 repite escenarios de HU-032, y uno no puede ocurrir en HU-030**

- Ubicación: HU-030 esc. 9 (l. 159-162), igual a HU-032 esc. 7; HU-030 esc. 10 (l. 164-168), igual en efecto a HU-032 esc. 10.
- Por qué: en una primera carga el catálogo está vacío y no hay versión vigente; con valores cargados, el archivo es una versión nueva y pertenece a HU-032 (RC-8). El Prompt 2 pide "No haya duplicidad significativa entre historias" (l. 358) y no incluir escenarios artificiales (l. 291).
- Corrección: quitar el esc. 9 de HU-030 (queda con 15) y dejar la comprobación en una sola historia, con referencia desde la otra.

**15. Escenarios con un Cuando del sistema, un resultado interno o un umbral sin número**

- Ubicación y cita: HU-030 esc. 13 (l. 182), "Cuando el sistema guarda los códigos"; HU-030 esc. 3 (l. 130), "crea primero el padre y después el hijo"; HU-031 regla l. 246 y esc. 2 (l. 271), "si son muchos, los muestra por páginas".
- Por qué: la plantilla (tanda 0, l. 203) y el Prompt 2 (l. 299) piden en el Cuando una acción del usuario; el orden interno de creación no es un resultado que vea el usuario (Prompt 2, l. 308); "muchos" no se puede verificar.
- Corrección: fundir el esc. 13 en el 1 ("los de 4 caracteres quedan marcados como elegibles"); en el 3, "acepta las dos filas y el hijo queda bajo su padre"; fijar el tamaño de página.

**16. Cuatro correspondencias de trazabilidad no coinciden con el texto**

- Ubicación y cita: HU-030 l. 218, el antiguo 6 "va en el 12 y el 7"; HU-031 l. 343, el antiguo 1 "va en el 2 y el 5"; HU-032 l. 501-502, el antiguo 4 "va en el 4: cambiar la descripción de un padre no cambia a sus hijos"; HU-032 l. 505, el antiguo 9 "va en el 13 y en RC-5".
- Por qué: el esc. 7 de HU-030 es un código repetido dentro del archivo, no un código que ya existe en el catálogo (eso lo trata la comparación de HU-032). Ningún escenario de HU-031 lista el catálogo completo. El esc. 4 de HU-032 no dice nada de padres ni de hijos. El esc. 13 de HU-032 atribuye versiones, no el cambio de un código entre versiones (esc. 9, que no nombra a quien lo hace).
- Corrección: decir que el antiguo 1 de HU-031 sale; agregar al esc. 4 de HU-032 "los hijos no cambian"; agregar al esc. 9 "con el nombre e identificación de quien lo hizo"; quitar "y el 7" en HU-030.

**17. Citas de [2.2] para reglas que la guía no dice**

- Ubicación y cita: HU-030 l. 78-79, "Cada código de 4 caracteres tiene por padre una categoría de 3 [2.2]"; HU-031 l. 247, "Cada resultado muestra el código, la descripción y su categoría padre [2.2]".
- Por qué: [2.2] dice "respetando la jerarquía padre-hijo de sus códigos diagnósticos" y "estructuras adecuadas para representar esta jerarquía"; no habla de 3 y 4 caracteres ni de qué muestra un resultado.
- Corrección: citar el numeral 23 y D-22, o el glosario, y dejar [2.2] para la jerarquía.

**18. HU-042 nombra un componente que la hoja 18 no tiene y calla dos datos de la fuente**

- Ubicación: l. 1239-1240, "numeral 23, componente código de enfermedad huérfana, hoja 18"; columnas l. 1245; supuesto l. 1407-1408.
- Por qué: en la hoja 18 la fila de la enfermedad huérfana está dentro del componente "Código del diagnóstico principal", como segunda fila de valores permitidos: "En caso de presentar una de las enfermedades huérfanas incluir el Código del catálogo de enfermedades huérfanas" (PDF, p. 6). No hay componente con ese nombre. Que sea un dato adicional y no un código que reemplaza al CIE-10 es una lectura del equipo, defendible por "incluir", que conviene anotar como supuesto. Además, el anexo de la Resolución 2625 tiene tres columnas (número, nombre y código CIE-10, con filas de dos códigos, como la 2271) y el artículo 5 dice que el listado se publica en el Repositorio Institucional Digital del Ministerio. La historia no dice si la tercera columna se ignora (RC-8 no trata columnas de más) ni de dónde sale el CSV si el repositorio solo publica PDF.
- Corrección: "segunda fila del componente código del diagnóstico principal (hoja 18)", con el supuesto; columnas "número de identificación; nombre", y la tercera se ignora; fuente: Repositorio Institucional Digital, listado versión 5.0.

**19. Errores de plantilla en HU-039 a HU-042**

- Ubicación y cita: esc. 9 o 10 (l. 739, 933, 1127 y 1329), "el código ya existe o un dato no cumple su numeral", que no aplica a un cambio de nombre, que el mismo escenario cubre. "Para que cada atención registre..." (l. 642, 837 y 1031): quien registra es el médico tratante. "el médico" (l. 1225) frente a "médico tratante" (glosario). "el nombre que le da la hoja" (l. 656 y 1045) frente a "el nombre que le da el anexo" (l. 851). La trazabilidad de HU-039 a HU-041 dice que no hay cambio de código porque "los códigos de esta lista los fija el anexo, y un cambio oficial entra como versión nueva"; el anexo fija también los nombres, y el cambio de nombre sí tiene escenario fuera de versión (esc. 10).
- La concordancia de género está bien en las cuatro: todos los sustantivos son femeninos y la plantilla los trata así.
- Corrección: separar "Creación rechazada" (código que ya existe) de "Cambio rechazado"; "Para que el médico tratante registre..."; un solo verbo para la fuente del nombre; quitar el esc. 10 de las listas del anexo o explicar por qué el nombre sí cambia fuera de versión.

**20. Nombre o descripción, y tres o cuatro listas en la misma tarjeta**

- Ubicación: HU-032, regla propia l. 377-379 frente a la RC-8 copiada l. 405-407.
- Por qué: la regla propia compara "descripciones que cambian" y "códigos que cambian de padre"; la RC-8 de la misma tarjeta dice "los que cambian de nombre" y tiene tres listas. El anexo usa "nombre" (hoja 18).
- Corrección: un solo término y una frase que diga que HU-032 amplía la comparación de RC-8 con el cambio de padre.

**21. D-09: la salvedad sobra, la frase tiene fecha y falta la norma de adopción**

- Ubicación: l. 29.
- Cita: "(lectura de un PDF con texto dañado, por confirmar)".
- Por qué: el texto se lee en la copia del Diario Oficial 53.244 a la que remite el normograma de la Supersalud, y se puede citar con página (p. 17) y fecha del manual (15 de septiembre de 2025). Para la "edición oficial adoptada" que pide c2, los considerandos de la Resolución 2625 de 2025 citan la Resolución 1895 de 2001 como la que adoptó la CIE-10 para la codificación de morbilidad en Colombia; no leí la 1895. Además, D-09 dice "vigente el día de la primera carga" y HU-030 "vigente el día de la carga" (l. 74-75); con HU-032 en el backlog, la segunda es la correcta.
- Corrección: cambiar la salvedad por la cita, nombrar la Resolución 1895 de 2001 como dato por confirmar y unificar "el día de la carga".

**22. Motivos de inactivación sin lista cerrada, y decisiones usadas que no figuran en supuestos**

- Ubicación: HU-033 l. 536-537; HU-032 l. 380-381; HU-042 l. 1320; supuestos l. 333, 492, 616 y 1406.
- Por qué: HU-033 admite "cargado por error" o "retirado por la fuente oficial"; HU-032 usa "No está en la versión" y HU-042 "Reemplazado por": dos motivos distintos para la misma causa (la fuente retiró el valor). En supuestos, HU-042 usa D-21 por RC-8 y no la lista; HU-033 usa D-02 y lista solo D-20; HU-031 usa D-20 por RC-4; HU-032 usa D-02 y D-18.
- Corrección: una lista cerrada de motivos en la tanda 0 y supuestos con todas las decisiones que la tarjeta usa.

## 4. Lo que está bien y no se debe tocar

- Conteos, códigos, longitudes y hojas de los numerales 18, 20, 21 y 23: coinciden con el PDF del anexo (p. 4 a 6).
- Las 38 copias de RC (RC-1 a RC-5 y RC-8) no tienen una letra de diferencia con la tanda 0 vigente.
- D-14: norma, artículos y derogatoria correctos, y la inferencia marcada como inferencia. La versión 5.0 del listado sirve como "versión" de RC-8.
- D-09: las dos afirmaciones externas se confirman en el anexo de la Resolución 1888.
- D-22 se aplica igual en HU-007, HU-009, HU-030 y HU-031. La hipótesis de la X del tablero (D-22) tiene un indicio a favor en el listado de la Resolución 2625.
- "Una versión nueva con filas rechazadas no se aplica": el motivo es correcto; solo le falta separar los dos tipos de rechazo (hallazgo 7).
- "Las atenciones no se migran" (l. 384): cierra el pendiente antiguo y coincide con HU-008 esc. 10 y con la RC-4 de HU-009.
- Las reglas de HU-033 sobre padre con hijos activos y reactivación con padre inactivo: hay que extenderlas a HU-030 y HU-032, no quitarlas.
- La matriz de permisos se aplica igual en las ocho historias: escenario de cargo sin permiso en cada una, el médico solo elige al registrar o corregir, el administrador solo ve conteos de atenciones.
- Los campos del Prompt 2 están en las ocho; cada una tiene de 11 a 23 escenarios; la tabla de conteo (142) cuadra.
- La retroalimentación sin verificar de HU-031 a HU-033 está marcada como tal en cada tarjeta.
- 36 de 40 correspondencias de trazabilidad son exactas, y las salidas de escenarios antiguos tienen su razón.
- Lenguaje simple: las reglas remiten al numeral y la hoja; el detalle técnico de RC-8 (UTF-8, separador) responde al pedido de formato del docente. Sin guion largo ni intensificadores.

## 5. Fuentes externas consultadas

- Resolución 1888 de 2025, normograma de la Superintendencia Nacional de Salud (texto y enlace al anexo): <https://normograma.supersalud.gov.co/compilacion/docs/resolucion_minsaludps_1888_2025.htm>
- Anexo técnico de la Resolución 1888 de 2025, Diario Oficial 53.244 del 15 de septiembre de 2025. Es el PDF al que remite el normograma de la Supersalud; está alojado en un dominio privado, normograma.com: <https://normograma.com/documentospdf/PDF/R_MSPS_1888_2025_ANEXO.pdf>. Usé la p. 16 (RDA de pacientes: 18.1 con "Valores permitidos según Resolución 866 de 2021"; 37.1 con la tabla CIE10) y la p. 17 (frase del CIE-11; RDA de hospitalización: 20 con ViaIngresoUsuario, 21 con RIPSCausaExterna, 23.1 a 23.3 con CIE10, CIE-11 opcional). El texto no menciona el código de enfermedad huérfana entre los datos del RDA.
- Resolución 2625 de 2025, Diario Oficial 53.339 del 18 de diciembre de 2025, copia en el Sistema de Información Normativa de la Rama Judicial: <https://sidn.ramajudicial.gov.co/SIDN/NORMATIVA/TEXTOS_COMPLETOS/8_RESOLUCIONES/RESOLUCIONES%202025/MSPS%20Resolucion%20002625%20de%202025.pdf>. Usé los artículos 3 (p. 15), 5 y 7 (p. 16), los considerandos y el anexo, versión 5.0.
- Tablas de referencia de SISPRO (CIE10, ViaIngresoUsuario, RIPSCausaExterna): <https://web.sispro.gov.co/WebPublico/Consultas/ConsultarDetalleReferenciaBasica.aspx?Code=CIE10>. Sin acceso: el servidor cerró la conexión y su robots.txt bloquea la lectura automática. Lo que depende de ellas queda sin verificar (hallazgos 2, 7 y 10).
- Resolución 1799 de 2026, que modifica la 1888 para el RDA de dispensación: solo encontré una copia en consultorsalud.com, sitio comercial de baja autoridad: <https://consultorsalud.com/wp-content/uploads/2026/08/Resolucion-1799-de-2026.pdf>. Su anexo repite la frase del CIE-11. No sostiene ningún hallazgo y no encontré copia en minsalud.gov.co.
- No encontré copias de la 1888 ni de la 2625 en minsalud.gov.co con la búsqueda web.
