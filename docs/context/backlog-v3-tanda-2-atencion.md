# Backlog IPS Salud y Vida, versión 3. Tanda 2: atención

Versión 4, 2026-10-21. Borrador corregido con la revisión independiente
(`revisiones/revision-tanda-2.md`) y con las de las tandas 4 y 5. HU-007, HU-008, HU-009 y HU-043.
Las reglas comunes (RC), la matriz de permisos, el glosario y las convenciones están en la tanda 0
(versión 4). El actor de las cuatro historias es el médico tratante. Los títulos de HU-007 a HU-009
son los de Trello, que repiten las palabras de la guía: "consultar, crear y modificar información
clínica" [2]. HU-043 es nueva.

Decisiones que esta tanda propone (se pasan a la hoja Decisiones del tablero como propuesta):

| Decisión | Propuesta | Por qué |
|---|---|---|
| D-05, prestador que realiza la atención | Es la IPS. Se supone una sola sede, porque la guía habla de "la nueva IPS Salud y Vida" y no menciona sedes [2]. El equipo técnico deja su código de 12 caracteres configurado al instalar y el sistema lo pone en cada atención. Para el numeral 16 no hay catálogo de prestadores. El prestador donde está la voluntad anticipada (numeral 9) es otro dato: se escribe y se valida por formato (tanda 3) | Todas las atenciones de este sistema las presta la propia IPS |
| D-08, atención sin terminar | Dos estados: "Borrador" (le falta algo) y "Registrada" (completa). Un borrador se completa en HU-007; una atención registrada se corrige en HU-009 | El docente pidió "definir estado y finalización del borrador del escenario 10" (HU-007 c2) |
| D-09, versión del CIE-10 | La tabla CIE10 de SISPRO, en la versión vigente el día de la carga (HU-030). La carga guarda la dirección y la fecha de descarga (RC-8) | La guía pide "la última versión del CIE-10" [2.2]; el anexo de la Resolución 1888 de 2025, que adopta el resumen digital de atención sobre la 866, remite a esa tabla |
| D-13, triage | Se pide cuando el grupo de servicios es "05 Atención inmediata" | El anexo define la clasificación del triage para "un servicio de urgencias" (hojas 17 y 18); de los cinco grupos de servicios, el que incluye urgencias es atención inmediata (inferencia del equipo). Ese grupo también incluye servicios que no son de urgencias, como la atención del parto, según el manual de habilitación del Ministerio (por confirmar): pregunta al docente |
| D-15, obligatorios de la atención | Columna "¿Obligatorio?" de la lista de datos de HU-007 | El anexo no tiene columna de obligatoriedad |
| D-22, código padre del CIE-10 | El código CIE-10 del diagnóstico principal solo acepta 4 caracteres. El código de enfermedad huérfana, del mismo numeral, va de 1 a 4 | El numeral 23 fija para el código CIE-10 longitud mínima y máxima de 4, y para el de enfermedad huérfana máxima de 4 y mínima de 1 (hoja 18) |
| D-24, ocupación de la atención | Al crear la atención, el sistema guarda la ocupación que el paciente tiene en ese momento. Si después cambia la del paciente, la atención conserva la suya; si quedó mal, se corrige en HU-009 | El numeral 7 se llama "Ocupación al momento de la atención" (hoja 14). Inferencia del equipo |
| D-25, nombre del momento del registro | RC-11: la atención conserva el código y el nombre de cada valor tal como se registró; si el catálogo cambia el nombre después, la consulta muestra también el vigente | El anexo trae el nombre del diagnóstico como componente del dato ("Nombre del diagnóstico principal", hoja 18), y el artículo 10 de la Resolución 866 obliga a disponer los datos de las atenciones "de forma permanente" |
| D-27, anulación de una atención | Una atención registrada por error, o un borrador que no debía existir (a nombre del paciente equivocado, repetida o que no ocurrió), se anula con motivo: queda en estado "Anulada", no se borra, no se corrige y no se pasa a otro paciente (HU-043). Las anuladas siguen contando como registros que usan un valor de catálogo | La guía y el docente no lo mencionan; la versión 2 de esta tanda lo dejó pendiente en HU-009, y la oposición y la voluntad anticipada tienen su anulación (HU-013 y HU-017). Pregunta al docente |
| Reglas del equipo | La fecha y hora de inicio no puede ser posterior al momento del registro. Las atenciones se listan de la más reciente a la más antigua. Cualquier médico tratante puede completar un borrador, corregir o anular una atención, y el sistema guarda quién lo hizo | Supuestos pendientes de validar con el docente |

---

# HU-007 · Registro de información clínica

**ID:** HU-007
**Título:** Registro de información clínica
**Funcionalidad relacionada:** Contacto con el servicio de salud
**Descripción:**
> Como médico tratante
> Quiero registrar la atención de un paciente con los datos que pide la Resolución 866
> Para dejar constancia de lo que encontré antes de que se pierda con el cambio de turno

**Valor de negocio:** Asocia cada atención al paciente con los datos y catálogos del anexo. Sin ella,
la siguiente atención no tiene historia que consultar.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Los datos de la atención son estos. Cada uno se valida con los valores permitidos, el formato y la
  longitud de su numeral del anexo (RC-7):

| Dato | Numeral | Hoja del anexo | ¿Obligatorio? (D-15, propuesta) |
|---|---|---|---|
| Código del prestador de servicios de salud que realiza la atención (el de la IPS) | 16 | 16 | Sí; lo pone el sistema (D-05) |
| Fecha y hora de inicio de la atención | 17 | 16 | Sí, con la hora |
| Modalidad de realización | 18 | 16 | Sí |
| Grupo de servicios | 18 | 16 | Sí |
| Entorno donde se realiza la atención | 19 | 16 | Sí |
| Vía de ingreso del usuario al servicio de salud | 20 | 16 y 17 | Sí |
| Causa que motiva la atención | 21 | 17 | Sí |
| Fecha, hora y clasificación del triage | 22 | 17 y 18 | Solo si el grupo de servicios es "Atención inmediata" (D-13) |
| Diagnóstico principal de ingreso: código y nombre | 23 | 18 | Sí; el nombre lo pone el sistema |
| Código de enfermedad huérfana | 23 | 18 | Solo si el paciente la presenta |
| Tipo de diagnóstico principal de ingreso | 23 | 18 | Sí |

- Solo se registran atenciones de pacientes que tienen registro (HU-004).
- La atención guarda la ocupación que el paciente tiene en el momento de crearla (numeral 7,
  "Ocupación al momento de la atención", hoja 14; D-24).
- El diagnóstico principal es uno, de la versión vigente del CIE-10 (D-09), respetando su jerarquía
  [2.2]. Del CIE-10 solo se aceptan códigos de 4 caracteres (numeral 23; D-22).
- Una atención a la que le falta un dato obligatorio se puede guardar como "Borrador"; solo pasa a
  "Registrada" con todos sus datos obligatorios (D-08). Cualquier médico tratante puede completar un
  borrador; el sistema guarda quién empezó la atención y quién la completó, con fecha y hora.
- Mientras la atención es borrador, cambiar un dato no es una corrección (D-08, propuesta). Desde que
  queda "Registrada", todo cambio es una corrección (HU-009): el dato nuevo se agrega con quién y
  cuándo (artículo 10, parágrafo 2, de la Resolución 866).
- La fecha y hora de inicio no puede ser posterior al momento en que se registra (regla del equipo).
- RC-2. Un dato que sale de un catálogo solo acepta valores activos de ese catálogo.
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora.
- RC-7. Cada dato se valida con los valores permitidos, el formato y la longitud de su numeral en el
  anexo.
- RC-11. Un registro conserva el código y el nombre que tenía el valor el día en que se registró; si
  la fuente cambia el nombre después, su consulta muestra también el vigente (D-25).

**Precondiciones:**
- El médico tratante tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Registro de una atención completa
- **Dado** que el paciente tiene registro
- **Y** el médico tratante tiene todos los datos obligatorios de la atención
- **Cuando** la registra
- **Entonces** el sistema la guarda como "Registrada" y la asocia al paciente
- **Y** guarda el código de la IPS, quién la registró, y la fecha y la hora del registro

### Escenario 2: Paciente sin registro
- **Dado** que el paciente no tiene registro
- **Cuando** el médico tratante intenta registrar una atención
- **Entonces** el sistema no lo permite
- **Y** avisa que el paciente debe registrarse primero (HU-004)

### Escenario 3: Dato que no cumple su numeral
- **Dado** que un dato no cumple los valores permitidos, el formato o la longitud de su numeral, por ejemplo un entorno que no está en la lista
- **Cuando** el médico tratante intenta registrar la atención
- **Entonces** el sistema lo rechaza
- **Y** dice cuál dato está mal y conserva los demás

### Escenario 4: Valor que no está activo en su catálogo
- **Dado** que el médico tratante ingresa una modalidad, una vía de ingreso, una causa, un diagnóstico o una enfermedad huérfana que no está activa en su catálogo
- **Cuando** intenta registrar la atención
- **Entonces** el sistema lo rechaza
- **Y** dice cuál dato está mal

### Escenario 5: Fecha de inicio sin hora o en el futuro
- **Dado** que la fecha de inicio de la atención no tiene hora, o es posterior al momento del registro
- **Cuando** el médico tratante intenta registrar la atención
- **Entonces** el sistema la rechaza
- **Y** pide la fecha y la hora de inicio

### Escenario 6: Triage en atención inmediata
- **Dado** que el grupo de servicios es "Atención inmediata"
- **Cuando** el médico tratante registra la atención
- **Entonces** el sistema pide la fecha, la hora y la clasificación del triage, de Triage I a Triage V
- **Y** no la deja como "Registrada" sin ellas

### Escenario 7: Sin triage en los demás grupos
- **Dado** que el grupo de servicios es otro, por ejemplo "Consulta externa"
- **Cuando** el médico tratante registra la atención
- **Entonces** el sistema no pide triage

### Escenario 8: Diagnóstico principal
- **Dado** que el médico tratante elige un código del CIE-10 vigente, de 4 caracteres
- **Cuando** registra la atención
- **Entonces** el sistema registra el código y muestra el nombre que le da el catálogo
- **Y** guarda el tipo de diagnóstico que el médico indicó

### Escenario 9: Código de categoría padre
- **Dado** que el médico tratante escribe un código de 3 caracteres, que es una categoría padre del CIE-10
- **Cuando** intenta registrarlo como diagnóstico principal
- **Entonces** el sistema lo rechaza
- **Y** le pide un código de 4 caracteres de esa categoría

### Escenario 10: Enfermedad huérfana
- **Dado** que el paciente presenta una enfermedad huérfana
- **Cuando** el médico tratante registra su código del catálogo de enfermedades huérfanas
- **Entonces** el sistema lo guarda junto al diagnóstico principal
- **Y** si el paciente no la presenta, el dato queda vacío sin impedir el registro

### Escenario 11: Falta un dato obligatorio
- **Dado** que falta un dato obligatorio
- **Cuando** el médico tratante intenta registrar la atención
- **Entonces** el sistema no la deja como "Registrada"
- **Y** dice cuáles faltan y ofrece guardarla como "Borrador"

### Escenario 12: Atención guardada como borrador
- **Dado** que el médico tratante no puede completar la atención, por ejemplo porque todavía no tiene el diagnóstico
- **Cuando** la guarda como "Borrador"
- **Entonces** el sistema conserva lo que ya tiene
- **Y** la atención aparece con la marca "Borrador" para completarla después

### Escenario 13: Completar un borrador
- **Dado** que una atención es "Borrador" porque le faltaba la causa
- **Cuando** un médico tratante registra la causa y confirma
- **Entonces** el sistema la deja como "Registrada"
- **Y** guarda quién la completó, con la fecha y la hora
- **Y** si un valor que ya tenía el borrador se inactivó en su catálogo, el sistema pide elegir uno activo antes de registrarla

### Escenario 14: Registro cancelado
- **Dado** que el médico tratante empezó a registrar una atención
- **Cuando** cancela antes de guardar
- **Entonces** el sistema no guarda nada, ni siquiera como borrador

### Escenario 15: Ocupación del momento de la atención
- **Dado** que el paciente tiene una ocupación registrada
- **Cuando** el médico tratante crea la atención
- **Entonces** el sistema guarda con la atención la ocupación que el paciente tiene en ese momento

### Escenario 16: Otra atención del mismo paciente
- **Dado** que el paciente ya tiene atenciones registradas
- **Cuando** el médico tratante registra otra
- **Entonces** el sistema la agrega como una atención nueva
- **Y** no cambia las anteriores

### Escenario 17: Cargo sin permiso
- **Dado** que un auxiliar de admisión tiene sesión iniciada
- **Cuando** intenta registrar una atención
- **Entonces** el sistema no lo permite

### Escenario 18: Catálogo obligatorio sin valores
- **Dado** que el catálogo de modalidades, vías de ingreso, causas o diagnósticos todavía no tiene valores activos
- **Cuando** el médico tratante abre el registro de una atención
- **Entonces** el sistema dice qué catálogo falta cargar (HU-030, HU-039 a HU-041)
- **Y** deja guardar la atención como "Borrador", pero no como "Registrada"

### Escenario 19: Catálogo de enfermedades huérfanas sin valores
- **Dado** que el catálogo de enfermedades huérfanas todavía no tiene valores activos
- **Cuando** el médico tratante registra una atención con todos sus datos obligatorios
- **Entonces** el sistema la guarda como "Registrada", porque ese dato es opcional
- **Y** avisa que ese catálogo falta cargar (HU-042)

**Dependencias:** HU-001 y HU-004; catálogos de modalidades (HU-039), vías de ingreso (HU-040),
causas (HU-041), diagnósticos (HU-030 y HU-031) y enfermedades huérfanas (HU-042).
**Supuestos o asuntos pendientes:**
- D-05, D-08, D-13, D-15, D-22, D-24 y D-25: propuestas del equipo.
- D-09: la versión del CIE-10 para las pruebas es la tabla de SISPRO vigente el día de la carga
  (HU-030).
- D-13: el grupo "Atención inmediata" también incluye servicios que no son de urgencias, como la
  atención del parto, según el manual de habilitación del Ministerio (por confirmar). Se pregunta al
  docente si el triage se exige en todo ese grupo.
- D-22 depende de que el archivo oficial del CIE-10 (D-09) traiga con 4 caracteres todos los códigos
  que se pueden elegir; las categorías sin subdivisión vienen con una X al final (A33X), como en el
  listado de la Resolución 2625 de 2025. Se confirma con la tabla (HU-030).
- La IPS tiene una sola sede y su código se configura al instalar (D-05).

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-007 c1 (resultado verificable del antiguo escenario 11, hoy el 9;
  versión del catálogo para las pruebas: D-09 con la tabla de SISPRO; campos obligatorios: columna
  de la lista) y c2 (prestador, fecha y hora, modalidad y grupo, entorno, vía, causa, triage cuando
  corresponda, diagnóstico principal y tipo: lista de datos y escenarios 1 y 3 a 10; estado y
  finalización del borrador del antiguo escenario 10: escenarios 11 a 13).
- Escenarios antiguos: el 1 es hoy el 1; el 2 es el 2; el 3 es el 8; el 4 es el 4; el 5 (jerarquía)
  pasa a HU-008 escenario 4 y a HU-031, porque mostrar la jerarquía es de la consulta y de la
  búsqueda del diagnóstico: el registro guarda el código y la jerarquía sale del catálogo; el 6 es el
  11; el 7 es el 16; el 8 (atribución) va en el 1; el 9 (sin sesión) lo cubre HU-001 escenario 4; el
  10 (borrador porque faltaba un catálogo) va en el 12, el 18 y el 19; el 11 es el 9.
- Versión 3 de la tanda: sale la precondición de catálogos con valores, que chocaba con el aviso de
  catálogo vacío de las historias de catálogo (escenario 18; revisión de la tanda 5, hallazgo 12, el
  mismo defecto que el docente señaló en HU-008); el borrador con un valor que se inactivó (escenario
  13; hallazgo 9); RC-11.
- El título se queda como estaba en Trello.
- Tablero, Ajustes por historia: filas con destino HU-007 (V-18 a V-28).

---

# HU-008 · Consulta de la información clínica de un paciente

**ID:** HU-008
**Título:** Consulta de la información clínica de un paciente
**Funcionalidad relacionada:** Contacto con el servicio de salud
**Descripción:**
> Como médico tratante
> Quiero ver las atenciones anteriores de un paciente
> Para no repetir lo que ya se hizo ni contradecir un diagnóstico previo sin saberlo

**Valor de negocio:** Un diagnóstico hecho sin conocer los anteriores rompe la continuidad de la
atención dentro de la IPS.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-007
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Se busca al paciente por tipo y número de documento (D-07).
- Las atenciones se listan de la más reciente a la más antigua, por fecha y hora de inicio (regla
  del equipo).
- El detalle de cada atención muestra todos sus datos de la lista de HU-007, con el nombre de cada
  valor y no solo su código, más su estado ("Registrada", "Borrador" o "Anulada"), la ocupación que
  guardó (D-24) y quién la registró. Cada valor se muestra con el nombre con que se registró y, si el
  catálogo lo cambió después, también con el vigente (RC-11, escenario 15).
- El diagnóstico principal se muestra con su categoría padre, para que se vea la jerarquía del CIE-10
  que pide la guía [2.2].
- Solo el médico tratante consulta atenciones (matriz de permisos).
- RC-4. Un valor que se inactivó después del registro se conserva en la atención.
- RC-6. Si un dato se corrigió, se ven el valor vigente y el anterior, con la fecha, la hora, el
  nombre y la identificación de quien corrigió.

**Precondiciones:**
- El médico tratante tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Lista de atenciones
- **Dado** que el paciente tiene varias atenciones
- **Cuando** el médico tratante lo busca por tipo y número de documento
- **Entonces** el sistema lista sus atenciones de la más reciente a la más antigua
- **Y** cada una muestra la fecha y hora de inicio, el diagnóstico principal y su estado

### Escenario 2: Detalle de una atención
- **Dado** que el paciente tiene una atención registrada
- **Cuando** el médico tratante la abre desde la lista
- **Entonces** el sistema muestra todos sus datos de la lista de HU-007, cada valor de catálogo con su nombre
- **Y** quién la registró, con la fecha y la hora

### Escenario 3: Paciente sin atenciones
- **Dado** que el paciente tiene registro pero no tiene atenciones
- **Cuando** el médico tratante lo consulta
- **Entonces** el sistema dice que el paciente no tiene atenciones registradas

### Escenario 4: Diagnóstico con su categoría padre
- **Dado** que la atención tiene un diagnóstico principal
- **Cuando** el médico tratante la consulta
- **Entonces** el sistema muestra el código, su nombre, su tipo y la categoría padre a la que pertenece

### Escenario 5: Triage cuando lo hay
- **Dado** que la atención fue de atención inmediata y tiene triage
- **Cuando** el médico tratante la consulta
- **Entonces** el sistema muestra la fecha, la hora y la clasificación del triage
- **Y** en las atenciones sin triage ese dato no aparece

### Escenario 6: Enfermedad huérfana cuando la hay
- **Dado** que la atención tiene código de enfermedad huérfana
- **Cuando** el médico tratante la consulta
- **Entonces** el sistema lo muestra con su nombre junto al diagnóstico principal

### Escenario 7: Historial de correcciones
- **Dado** que un dato de la atención se corrigió
- **Cuando** el médico tratante consulta el historial de la atención
- **Entonces** el sistema muestra el valor vigente y el anterior
- **Y** la fecha, la hora, el nombre y la identificación de quien corrigió

### Escenario 8: Atención en borrador
- **Dado** que una atención quedó como "Borrador"
- **Cuando** el médico tratante lista las atenciones del paciente
- **Entonces** el sistema la muestra con esa marca
- **Y** dice qué datos le faltan

### Escenario 9: Ocupación del momento de la atención
- **Dado** que la ocupación del paciente cambió después de una atención
- **Cuando** el médico tratante consulta esa atención
- **Entonces** el sistema muestra la ocupación que guardó la atención
- **Y** la atención sigue asociada al mismo paciente

### Escenario 10: Valor que se inactivó después
- **Dado** que una atención tiene un valor que luego se inactivó en su catálogo, por ejemplo una causa
- **Cuando** el médico tratante la consulta
- **Entonces** el sistema muestra el valor con la marca "inactivo"

### Escenario 11: Paciente que no existe
- **Dado** que no hay paciente con ese tipo y número de documento
- **Cuando** el médico tratante lo busca
- **Entonces** el sistema dice que no hay registro con ese documento

### Escenario 12: Cargo sin permiso
- **Dado** que un auxiliar de admisión o un profesional de estadística tiene sesión iniciada
- **Cuando** intenta consultar las atenciones de un paciente
- **Entonces** el sistema no lo permite

### Escenario 13: La consulta no cambia nada
- **Dado** que el médico tratante consultó las atenciones de un paciente
- **Cuando** cierra la consulta
- **Entonces** las atenciones quedan igual que antes

### Escenario 14: Atención anulada
- **Dado** que una atención del paciente se anuló (HU-043)
- **Cuando** el médico tratante lista las atenciones
- **Entonces** el sistema la muestra con la marca "Anulada", su motivo y quién la anuló
- **Y** al abrirla ve sus datos tal como estaban, sin opciones para cambiarlos

### Escenario 15: Nombre que cambió en el catálogo
- **Dado** que el Ministerio cambió el nombre de un diagnóstico después de registrar la atención
- **Cuando** el médico tratante consulta esa atención
- **Entonces** ve el nombre con que se registró el diagnóstico
- **Y** ve también el nombre vigente, marcado como tal (RC-11)

**Dependencias:** HU-001, HU-004 y HU-007; HU-009 (correcciones, escenario 7) y HU-006 (cambios de
ocupación, escenario 9).
**Supuestos o asuntos pendientes:** El orden de la más reciente a la más antigua es regla del
equipo. D-08 (borrador), D-13 (triage), D-24 (ocupación), D-25 (nombre del momento del registro) y
D-27 (anulación) son propuestas del equipo.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-008 c1 (orden ascendente o descendente: regla y escenario 1;
  precondición "al menos una atención", que chocaba con el escenario 3: retirada, y la precondición
  de paciente registrado también sale porque chocaba con el escenario 11; cómo se ve el historial y
  sus marcas de fecha y hora: escenario 7 y mockup) y c2 (todo el detalle del contacto: escenario 2;
  diagnóstico principal y tipo: escenario 4; sentido del orden: escenario 1; acceso: escenario 12;
  el antiguo escenario 6 con fecha, hora, nombre e identificación del corrector junto con el valor
  anterior y el vigente: escenario 7).
- Escenarios antiguos: el 1 y el 2 son hoy el 1; el 3 es el 3; el 4 es el 4; el 5 (varios
  diagnósticos) se transforma en el 6, porque el numeral 23 pide un diagnóstico principal y admite
  además un código de enfermedad huérfana; el 6 es el 7; el 7 (sin sesión) lo cubre HU-001 escenario
  4; el 8 es el 11; el 9 es el 2; el 10 es el 13.
- Versión 3 de la tanda: atención anulada (escenario 14, HU-043) y nombre del momento del registro
  (RC-11, escenario 15).
- El título se queda como estaba en Trello.
- Tablero, Ajustes por historia: filas con destino HU-008.

---

# HU-009 · Modificación de la información clínica registrada

**ID:** HU-009
**Título:** Modificación de la información clínica registrada
**Funcionalidad relacionada:** Contacto con el servicio de salud
**Descripción:**
> Como médico tratante
> Quiero corregir un dato que quedó mal en una atención registrada
> Para que la información clínica quede sin errores, sin perder lo que decía antes

**Valor de negocio:** Un dato clínico mal registrado se arrastra a las decisiones siguientes.
Corregir dejando rastro permite saber qué decía antes, quién lo cambió y cuándo.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-007
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Se pueden corregir todos los datos de la atención registrada: los de la lista de HU-007 y la
  ocupación que guardó (D-24), con las mismas reglas del registro. El código del prestador no se
  corrige, porque lo pone el sistema (D-05). Al cambiar el código del diagnóstico cambia también su
  nombre, que sale del catálogo.
- Al corregir un dato de una atención registrada, el dato nuevo se agrega con quién y cuándo, y el
  anterior se conserva (artículo 10, parágrafo 2, de la Resolución 866). Por eso una atención
  registrada no se actualiza: se corrige.
- Un borrador no se corrige: se completa en HU-007 (D-08). Una atención anulada no se corrige
  (HU-043).
- Cualquier médico tratante puede corregir una atención; el sistema guarda quién lo hizo (regla del
  equipo).
- Si la corrección deja el grupo de servicios en "Atención inmediata", se piden los datos del triage
  (D-13). Si el grupo deja de ser "Atención inmediata", el triage deja de mostrarse como dato vigente
  y queda en el historial.
- RC-2. Un dato que sale de un catálogo solo acepta valores activos de ese catálogo.
- RC-4. Un valor que se inactivó en su catálogo se conserva mientras no se cambie ese dato.
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora.
- RC-6. Al corregir un dato mal registrado "se agregará el nuevo dato con la fecha, hora, nombre e
  identificación de quien hizo la corrección" (artículo 10, parágrafo 2, de la Resolución 866). El
  dato anterior no se borra.
- RC-7. Cada dato se valida con los valores permitidos, el formato y la longitud de su numeral en el
  anexo.

**Precondiciones:**
- El médico tratante tiene sesión iniciada (HU-001).
- La atención existe (HU-007).

**Criterios de aceptación BDD:**

### Escenario 1: Corregir un dato de una atención registrada
- **Dado** que en una atención registrada la vía de ingreso quedó mal
- **Cuando** el médico tratante la corrige
- **Entonces** el sistema guarda el valor nuevo con la fecha, la hora, el nombre y la identificación de quien corrigió
- **Y** conserva el anterior en el historial de la atención

### Escenario 2: Corregir el diagnóstico principal
- **Dado** que el diagnóstico principal de una atención quedó mal
- **Cuando** el médico tratante lo cambia por otro código vigente de 4 caracteres y su tipo
- **Entonces** el sistema registra el código nuevo, con la fecha, la hora, el nombre y la identificación de quien corrigió, y muestra el nombre que le da el catálogo
- **Y** conserva el anterior en el historial

### Escenario 3: Código que no está vigente
- **Dado** que el código nuevo no está activo en la versión vigente del catálogo de diagnósticos (D-09)
- **Cuando** el médico tratante intenta usarlo
- **Entonces** el sistema lo rechaza [2.2]
- **Y** conserva el diagnóstico anterior

### Escenario 4: Código de categoría padre
- **Dado** que el código nuevo tiene 3 caracteres, porque es una categoría padre
- **Cuando** el médico tratante intenta usarlo
- **Entonces** el sistema lo rechaza, igual que en el registro (HU-007)
- **Y** conserva el diagnóstico anterior

### Escenario 5: Dejar vacío un dato obligatorio
- **Dado** que el médico tratante deja vacío el diagnóstico u otro dato obligatorio de una atención registrada
- **Cuando** confirma la corrección
- **Entonces** el sistema no la acepta y dice cuál dato falta
- **Y** conserva el valor anterior

### Escenario 6: Valor que no cumple su numeral o no está activo
- **Dado** que el valor nuevo no cumple los valores permitidos, el formato o la longitud de su numeral, o no está activo en su catálogo
- **Cuando** el médico tratante confirma la corrección
- **Entonces** el sistema la rechaza y dice cuál dato está mal
- **Y** conserva el valor anterior

### Escenario 7: Cambio a atención inmediata
- **Dado** que el médico tratante corrige el grupo de servicios a "Atención inmediata"
- **Cuando** confirma la corrección sin los datos del triage
- **Entonces** el sistema no la acepta y pide la fecha, la hora y la clasificación del triage
- **Y** conserva los valores anteriores

### Escenario 8: Corregir la ocupación de la atención
- **Dado** que la ocupación que guardó la atención quedó mal
- **Cuando** el médico tratante la corrige
- **Entonces** el sistema guarda la nueva con la fecha, la hora, el nombre y la identificación de quien corrigió, y conserva la anterior
- **Y** la ocupación del paciente no cambia (esa se corrige en HU-006)

### Escenario 9: Valor inactivo que no se toca
- **Dado** que la atención tiene una modalidad que luego se inactivó en su catálogo
- **Cuando** el médico tratante corrige otro dato
- **Entonces** el sistema guarda la corrección
- **Y** conserva la modalidad como estaba

### Escenario 10: Un borrador se completa, no se corrige
- **Dado** que una atención es "Borrador"
- **Cuando** el médico tratante la abre para cambiarla
- **Entonces** el sistema la abre para completarla, como en el registro (HU-007)
- **Y** no guarda los cambios como corrección

### Escenario 11: Corrección abandonada
- **Dado** que el médico tratante cambió datos sin confirmar
- **Cuando** cancela
- **Entonces** el sistema conserva los valores anteriores

### Escenario 12: Cargo sin permiso
- **Dado** que un auxiliar de admisión tiene sesión iniciada
- **Cuando** intenta corregir una atención
- **Entonces** el sistema no lo permite

### Escenario 13: Los datos del paciente no cambian
- **Dado** que el médico tratante corrige una atención
- **Cuando** confirma la corrección
- **Entonces** los datos del paciente quedan intactos
- **Y** la atención sigue asociada al mismo paciente

**Dependencias:** HU-001 y HU-007; catálogos de diagnósticos (HU-030 y HU-031) y los demás catálogos
de la atención (HU-039 a HU-042).
**Supuestos o asuntos pendientes:**
- D-05, D-08, D-09, D-13, D-15, D-22 y D-24: propuestas del equipo.
- Una atención registrada al paciente equivocado no se corrige dato por dato: se anula (HU-043,
  D-27).

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-009 c1 (fecha y hora de la modificación además del autor y los
  valores anterior y nuevo: escenario 1; comportamiento al cambiar a un código padre: escenario 4) y
  c2 (llevar a escenarios la fecha, hora, nombre e identificación del corrector, que el antiguo
  escenario 8 solo expresaba como identificación: escenarios 1, 2 y 8; campos editables: regla;
  diagnóstico principal y tipo: escenario 2).
- Escenarios antiguos: el 1 y el 2 son hoy el 1; el 3 es el 2; el 4 es el 3; el 5 (jerarquía al
  modificar) lo cubre el catálogo: el código nuevo trae su padre del catálogo (HU-030) y HU-008
  escenario 4 lo muestra; el 6 es el 5; el 7 es el 11; el 8 (atribución) va en el 1; el 9 (sin
  sesión) lo cubre HU-001 escenario 4; el 10 es el 13.
- En la versión 1 de esta tanda, completar un borrador estaba aquí. Pasó a HU-007 (escenario 13),
  donde el docente pidió definir la finalización del borrador, y esta historia queda solo para
  corregir. El Prompt 2 pide separar por "ciclos de vida o estados distintos". El título vuelve al
  de Trello.
- Tablero, Ajustes por historia: filas con destino HU-009.

---

# HU-043 · Anulación de una atención registrada por error

**ID:** HU-043
**Título:** Anulación de una atención registrada por error
**Funcionalidad relacionada:** Contacto con el servicio de salud
**Descripción:**
> Como médico tratante
> Quiero anular una atención que quedó registrada por error, por ejemplo a nombre de otro paciente, diciendo el motivo
> Para que no se tome como parte de la historia del paciente, sin borrar lo que se registró

**Valor de negocio:** Una atención a nombre del paciente equivocado mezcla los diagnósticos de dos
personas. Corregir dato por dato no la saca de la historia de quien no fue atendido; anularla sí, y
deja rastro de quién lo hizo y por qué.

**Prioridad:** Media
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-007
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Se anula una atención que no debía existir: la registrada a nombre del paciente equivocado, una
  repetida o una que no ocurrió. Puede estar "Registrada" o en "Borrador". Un dato mal registrado no
  se anula: se corrige (HU-009). Propuesta del equipo (D-27).
- El motivo es obligatorio y es uno de estos: "Paciente equivocado", "Atención repetida" u "Otra
  inconsistencia", con su explicación (regla del equipo). Son inconsistencias porque el parágrafo 2
  del artículo 10 de la Resolución 866 no admite tratar los datos de una atención "por otras razones
  diferentes a inconsistencias demostradas".
- Anular no borra: la atención queda en estado "Anulada", con sus datos como estaban, el motivo, la
  fecha, la hora, el nombre y la identificación de quien la anuló (D-20, D-27).
- Una atención anulada no se corrige, no se completa, no vuelve a "Registrada" y no se pasa a otro
  paciente. Si era de otro paciente, el médico registra la atención en el paciente que corresponde
  (HU-007).
- Cualquier médico tratante puede anular una atención; el sistema guarda quién lo hizo (regla del
  equipo).
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora.

**Precondiciones:**
- El médico tratante tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Anular una atención del paciente equivocado
- **Dado** que una atención quedó registrada a nombre de otro paciente
- **Cuando** el médico tratante pide anularla con el motivo "Paciente equivocado"
- **Entonces** el sistema le muestra el paciente, la fecha y hora de inicio y el diagnóstico de esa atención, y pide confirmar
- **Y** al confirmar la deja "Anulada", con el motivo, la fecha, la hora, el nombre y la identificación de quien la anuló

### Escenario 2: Atención repetida
- **Dado** que la misma atención quedó registrada dos veces
- **Cuando** el médico tratante anula una con el motivo "Atención repetida"
- **Entonces** esa queda "Anulada"
- **Y** la otra sigue "Registrada"

### Escenario 3: Anular un borrador
- **Dado** que quedó un borrador de una atención que no ocurrió
- **Cuando** el médico tratante lo anula con el motivo "Otra inconsistencia" y su explicación
- **Entonces** el sistema lo deja en estado "Anulada"
- **Y** deja de mostrarlo como pendiente de completar

### Escenario 4: Anulación sin motivo
- **Dado** que el médico tratante no eligió el motivo, o eligió "Otra inconsistencia" sin explicarla
- **Cuando** intenta anular la atención
- **Entonces** el sistema no la anula y pide el motivo

### Escenario 5: Anulación cancelada
- **Dado** que el médico tratante pidió anular una atención
- **Cuando** cancela antes de confirmar
- **Entonces** la atención queda como estaba

### Escenario 6: La atención correcta va en el paciente correcto
- **Dado** que el médico tratante anuló una atención por "Paciente equivocado"
- **Cuando** registra la atención en el paciente que corresponde (HU-007)
- **Entonces** el sistema la guarda como una atención nueva de ese paciente
- **Y** la anulada sigue en el paciente equivocado, con su marca, y no se mueve

### Escenario 7: Una atención anulada no se cambia
- **Dado** que una atención está "Anulada"
- **Cuando** un médico tratante intenta corregirla o completarla
- **Entonces** el sistema no lo permite y dice que la atención está anulada

### Escenario 8: Anular dos veces
- **Dado** que una atención ya está "Anulada"
- **Cuando** un médico tratante intenta anularla otra vez
- **Entonces** el sistema dice que ya está anulada y no cambia nada

### Escenario 9: Historial de la atención anulada
- **Dado** que una atención se anuló después de una corrección
- **Cuando** el médico tratante consulta su historial
- **Entonces** ve el registro, la corrección y la anulación, cada una con su fecha, su hora y el nombre e identificación de quien la hizo

### Escenario 10: Cargo sin permiso
- **Dado** que un auxiliar de admisión o un profesional de estadística tiene sesión iniciada
- **Cuando** intenta anular una atención
- **Entonces** el sistema no lo permite

**Dependencias:** HU-001, HU-007 y HU-008.
**Supuestos o asuntos pendientes:**
- D-27: que una atención se pueda anular, y sus motivos, son propuesta del equipo; la guía y el
  docente no lo mencionan. Se pregunta al docente.
- Revertir una anulación hecha por error no está en esta historia; si hace falta, se pregunta al
  docente.

**Trazabilidad (no va en la tarjeta):**
- Historia nueva. Responde el pendiente que la versión 2 de esta tanda dejó en HU-009 (cómo se anula
  una atención registrada al paciente equivocado). El Prompt 2 pide separar por "ciclos de vida o
  estados distintos": corregir cambia un dato (HU-009); anular cambia el estado de toda la atención.
  La oposición y la voluntad anticipada ya tienen su anulación (HU-013 y HU-017).
- Con HU-032 y HU-033 fundidas en HU-030 (revisión de la tanda 5), el backlog queda en 30 historias
  con esta.
- La consulta de una atención anulada está en HU-008 (escenario 14); en la versión 3 de la tanda
  también estaba aquí y salió por repetida (revisión final cruzada).
- Tablero: historia nueva, sin fila de ajustes; decisión D-27.

---

## Registro

| Fecha | Versión | Cambio |
|---|---|---|
| 2026-10-20 | 1 | Primera escritura: HU-007 a HU-009 editadas con los ajustes del tablero y los comentarios del docente; propuestas para D-05, D-08, D-13, D-15, D-22 y la ocupación al momento de la atención |
| 2026-10-20 | 2 | Correcciones de la revisión independiente: hojas 17 y 18 en D-13, con la salvedad del grupo atención inmediata; D-22 acotado al CIE-10; D-09 con la tabla de SISPRO; D-24 para la ocupación; estado "Borrador"; completar el borrador pasa a HU-007 y HU-009 queda para corregir; precondiciones que chocaban con escenarios; registro cancelado; corrección de la ocupación; datos del corrector junto al dato nuevo (RC-6); títulos de Trello |
| 2026-10-20 | 3 | Revisiones de las tandas 4 y 5: HU-043 nueva, anulación de una atención registrada por error (D-27); HU-007 sin la precondición de catálogos con valores, con el escenario 18 (catálogo sin valores) y el borrador con un valor inactivo (escenario 13); RC-11 (D-25) en HU-007 y HU-008; HU-008 muestra las atenciones anuladas (escenario 14); D-09 con la carga del día y la fecha de descarga; profesional de estadística en HU-008 |
| 2026-10-20 | 3 (continuación) | Revisión final cruzada: el artículo 10 se cita por lo que dice (disponer los datos de forma permanente y, al corregir, agregar el dato nuevo); D-27 incluye los borradores; HU-007 escenario 8 sin decir que guarda el nombre y escenario 18 partido en 18 y 19; HU-008 con la acción del médico en cada Cuando y escenario 15 (RC-11); HU-009 con la precondición "La atención existe"; HU-043 con el motivo "Otra inconsistencia" y sin el escenario que repetía HU-008 |
| 2026-10-20 | 3 (verificación) | Verificación de la aplicación (`revisiones/verificacion-aplicacion-final.md`): HU-009 escenario 2 registra el código y muestra el nombre, como HU-007 escenario 8; HU-008 escenario 13 dice "cierra la consulta", como HU-005, HU-011 y HU-015 |
| 2026-10-21 | 4 | Límite de 12 historias: HU-008 escenario 9 prueba también que la atención sigue con el paciente después de cambiar sus datos (antes era HU-006 escenario 14, que hacía depender a HU-006 de HU-008) |
