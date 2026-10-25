# Backlog IPS Salud y Vida, versión 3. Tanda 1: inicio de sesión y paciente

Versión 6, 2026-10-22. Borrador para revisión. HU-001, HU-004, HU-005 y HU-006. Las reglas
comunes (RC), la matriz de permisos, el glosario y las convenciones están en la tanda 0 (versión 4).

HU-002 y HU-003 se fusionan en HU-001; sus tarjetas se archivan en Trello con una nota que remite
a HU-001.

---

# HU-001 · Inicio de sesión con permisos por cargo

**ID:** HU-001
**Título:** Inicio de sesión con permisos por cargo
**Funcionalidad relacionada:** Inicio de sesión
**Descripción:**
> Como persona que trabaja en la IPS con un cargo asignado (auxiliar de admisión, médico tratante o profesional de estadística)
> Quiero identificarme antes de usar el sistema
> Para trabajar solo con lo que mi cargo me permite y que cada registro quede a mi nombre

**Valor de negocio:** Sin identificación no se sabe quién registró cada dato ni se puede limitar
quién ve la información de un paciente. Los tres cargos entran de la misma forma y con el mismo
objetivo; lo que cambia es lo que pueden hacer después. Por eso es una sola historia: los permisos
de cada cargo se prueban aquí. Las historias de catálogo y el registro del paciente no repiten
ese escenario (escenario 14); las demás historias de trabajo traen el suyo.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** N/A
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El sistema tiene un inicio de sesión básico [2.4].
- No hay registro de usuarios ni recuperación de cuenta en esta fase [2.4].
- Cada cargo tiene los permisos de la matriz:

| Capacidad | Auxiliar de admisión | Médico tratante | Profesional de estadística |
|---|---|---|---|
| Registrar, actualizar y corregir los datos del paciente | Sí | No | No |
| Consultar los datos del paciente | Sí | Sí | No |
| Registrar, actualizar, corregir y anular la oposición y la voluntad anticipada | Sí | No | No |
| Consultar la oposición y la voluntad anticipada | Sí | Sí | No |
| Registrar, corregir y anular atenciones | No | Sí | No |
| Consultar atenciones | No | Sí | No |
| Cargar y mantener catálogos (crear, actualizar, inactivar, reactivar) | No | No | Sí |
| Consultar catálogos | Sí | Sí | Sí |

- Las opciones de la matriz se muestran y se prueban a medida que su historia se desarrolla.
  Mientras no existan las atenciones, la oposición o la voluntad anticipada, los escenarios 5 y 6 no
  las muestran.
- La sesión se cierra sola después de 15 minutos sin actividad (D-06, valor propuesto).
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora.
- Las historias de catálogo y el registro del paciente (HU-004) no dependen de esta historia: el
  inicio de sesión no va en la primera entrega (indicación del docente en clase). Cuando esta
  historia se desarrolla, sus escenarios 9 y 14 se aplican también a ellas; lo registrado antes
  queda sin autor.

**Precondiciones:**
- La persona tiene usuario, contraseña y un cargo asignado en el sistema.

**Criterios de aceptación BDD:**

### Escenario 1: Ingreso con datos correctos
- **Dado** que la persona tiene usuario y contraseña válidos
- **Cuando** los ingresa
- **Entonces** el sistema la deja entrar
- **Y** le muestra solo las opciones de su cargo

### Escenario 2: Datos incorrectos
- **Dado** que la persona escribe mal el usuario o la contraseña
- **Cuando** intenta entrar
- **Entonces** el sistema no la deja entrar
- **Y** le dice que los datos no son válidos, sin decir cuál de los dos falló

### Escenario 3: Datos vacíos
- **Dado** que la persona deja vacío el usuario o la contraseña
- **Cuando** intenta entrar
- **Entonces** el sistema le dice cuál falta

### Escenario 4: Uso sin sesión
- **Dado** que nadie ha iniciado sesión
- **Cuando** una persona intenta registrar, consultar o cambiar cualquier dato
- **Entonces** el sistema no lo permite y pide iniciar sesión

### Escenario 5: Opciones del auxiliar de admisión
- **Dado** que entró un auxiliar de admisión
- **Cuando** ve las opciones del sistema
- **Entonces** tiene registrar, consultar, actualizar y corregir pacientes, y registrar, consultar, actualizar, corregir y anular la oposición a la donación y la voluntad anticipada
- **Y** no tiene opciones de atenciones ni de mantenimiento de catálogos

### Escenario 6: Opciones del médico tratante
- **Dado** que entró un médico tratante
- **Cuando** ve las opciones del sistema
- **Entonces** tiene registrar, consultar, corregir y anular atenciones
- **Y** tiene consultar pacientes, oposición y voluntad anticipada, sin opciones para cambiarlos

### Escenario 7: Opciones del profesional de estadística
- **Dado** que entró un profesional de estadística
- **Cuando** ve las opciones del sistema
- **Entonces** tiene cargar, consultar, crear, actualizar, inactivar y reactivar valores de los catálogos
- **Y** no tiene opciones de pacientes ni de atenciones

### Escenario 8: Acción que el cargo no permite
- **Dado** que una persona intenta una acción que su cargo no tiene, por ejemplo un médico que quiere actualizar los datos del paciente
- **Cuando** la intenta
- **Entonces** el sistema no la deja
- **Y** le dice que su cargo no tiene ese permiso

### Escenario 9: Registro a nombre de quien lo hizo
- **Dado** que la persona tiene sesión iniciada
- **Cuando** registra o cambia un dato
- **Entonces** el sistema guarda quién lo hizo, la fecha y la hora

### Escenario 10: La sesión sigue al cambiar de función
- **Dado** que la persona tiene sesión iniciada
- **Cuando** pasa de una opción a otra de su cargo
- **Entonces** el sistema no le pide identificarse de nuevo

### Escenario 11: Cierre de sesión
- **Dado** que la persona tiene sesión iniciada
- **Cuando** cierra la sesión
- **Entonces** el sistema la termina
- **Y** pide identificarse de nuevo para seguir

### Escenario 12: Sesión cerrada por inactividad
- **Dado** que la persona lleva 15 minutos sin usar el sistema
- **Cuando** intenta seguir trabajando
- **Entonces** el sistema le pide identificarse de nuevo

### Escenario 13: Sin recuperación de clave
- **Dado** que la persona olvidó su contraseña
- **Cuando** busca cómo recuperarla
- **Entonces** el sistema no ofrece esa opción [2.4]

### Escenario 14: Cargo sin permiso en los catálogos
- **Dado** que un auxiliar de admisión o un médico tratante tiene sesión iniciada
- **Cuando** intenta cargar, crear, cambiar, inactivar o reactivar un valor de cualquier catálogo
- **Entonces** el sistema no lo permite y le dice que su cargo no tiene ese permiso
- **Y** sí le deja consultar y buscar en el catálogo

**Dependencias:** Ninguna. Las historias de catálogo y el registro del paciente (HU-004) no
dependen de esta; las demás historias sí.
**Supuestos o asuntos pendientes:**
- Los usuarios y sus cargos los deja creados el equipo técnico al instalar el sistema, porque el
  registro de usuarios no hace parte de esta fase [2.4].
- D-02: el cargo que mantiene los catálogos es el profesional de estadística (propuesta).
- D-04: una sola historia de inicio de sesión para los tres cargos.
- D-06: 15 minutos de inactividad, valor propuesto.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-001 c1, c2 y c3 (tiempo de inactividad, permisos, diferencia con
  HU-002 y HU-003; el escenario de la clave se queda); HU-002 c1 y c2 (tiempo, qué ve el médico, por
  qué el acceso iba aparte); HU-003 c1 y c2 (cargo real, operaciones permitidas, tiempo).
- Escenarios antiguos de HU-001: el 1 (acceso) es el 1, y lo que el auxiliar puede hacer está en
  el 5; el 2 es el 2; el 3 (persona sin credenciales) va en el 2, y no ofrecer crear una cuenta es
  regla [2.4]; el 4 es el 4; el 5 (atribución) es el 9; el 6 (cierre) es el 11; el 7 (continuidad)
  es el 10; el 8 (información clínica) lo cubren el 5 y el 8; el 9 (sin recuperación) es el 13; el
  10 (inactividad) es el 12.
- De HU-002: el 1 (acceso) va en el 1 y el 6; el 2 es el 2; el 3 (sin sesión) es el 4; el 4
  (atribución) es el 9; el 5 (qué ve el médico) es el 6; el 6 (actualizar datos del paciente) es el
  8; el 7 (cierre) es el 11; el 8 (continuidad entre pacientes) va en el 10, y lo de no mezclar la
  información de los dos pacientes sale, porque cada consulta es de un paciente (HU-005 y HU-008);
  el 9 (sin recuperación) es el 13; el 10 (inactividad) es el 12.
- De HU-003: el 1 (acceso y operaciones) va en el 1 y el 7; el 2 es el 2; el 3 (sin sesión) es el
  4; el 4 (atribución) es el 9; el 5 (registrar un paciente) y el 6 (información clínica) van en el
  7 y el 8; el 7 (consulta de un catálogo) va en el 7 y en la consulta de cada historia de catálogo;
  el 8 (cierre) es el 11; el 9 (sin recuperación) es el 13; el 10 (inactividad) es el 12.
- Versión 5 de la tanda (límite de 12 historias, 2026-10-21): entra el escenario 14, que reúne el
  escenario "Cargo sin permiso" de las 13 historias de catálogo. El docente lo pidió en HU-025,
  HU-026, HU-028 y HU-030: "rol autenticado sin permisos: ausencia de sesión no cubre
  autorización". Pasa aquí para que los catálogos y HU-004 no dependan del inicio de sesión.
- Tablero, Ajustes por historia: filas con destino HU-001.

---

# HU-004 · Registro de un paciente

**ID:** HU-004
**Título:** Registro de un paciente
**Funcionalidad relacionada:** Gestión de pacientes
**Descripción:**
> Como auxiliar de admisión
> Quiero registrar a un paciente nuevo con los datos que pide la Resolución 866
> Para que quien lo atienda después no tenga que volver a preguntarle sus datos

**Valor de negocio:** Sin el registro del paciente no hay a quién asociar una atención. Registrar
con los datos y catálogos del anexo deja la información lista para intercambiarse.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-005
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Los datos del paciente son estos. Cada uno se valida con los valores permitidos, el formato y la
  longitud de su numeral del anexo (RC-7):

| Dato | Numeral | Hoja del anexo | ¿Obligatorio? (D-19, propuesta) |
|---|---|---|---|
| País de nacionalidad, uno o varios | 1 | 13 | Sí |
| Tipo de documento | 2 | 13 | Sí |
| Número de documento | 2 | 13 | Sí |
| Primer apellido y primer nombre | 3 | 13 | Sí |
| Segundo apellido y segundo nombre | 3 | 13 | No |
| Fecha de nacimiento | 4 | 13 | Sí |
| Hora de nacimiento | 4 | 13 | No |
| Sexo biológico | 5 | 13 | Sí |
| Identidad de género | 6 | 14 | Sí; la lista tiene "No lo declara" |
| Ocupación al momento de la atención | 7 | 14 | Si la persona tiene ocupación |
| Categoría de discapacidad, una o varias | 10 | 14 y 15 | Sí; la lista tiene "Sin discapacidad" |
| País de residencia habitual | 11 | 15 | Sí |
| Ciudad (municipio) de residencia habitual | 12 | 15 | Si reside en Colombia |
| Etnia | 13 | 15 | Sí; la lista tiene "Ninguna de las anteriores" |
| Comunidad étnica | 13 | 15 | Si la etnia no es "Ninguna de las anteriores" y el catálogo de comunidades tiene valores (HU-037) |
| Zona territorial de residencia | 14 | 15 | Sí |
| Entidad que responde por el plan de beneficios (la EPS u otra que paga) | 15 | 15 y 16 | Sí |

- La oposición a la donación (numeral 8) y la voluntad anticipada (numeral 9) se registran en sus
  propias historias, HU-010 y HU-014 [2.1].
- Un paciente puede tener más de un país de nacionalidad [2.1].
- La fecha de nacimiento va separada de la hora [2.1]. La hora puede faltar: el numeral 4 acepta la
  fecha sola.
- La ocupación solo admite ocupaciones hijas, las del nivel más específico del catálogo CIUO-88 A.C.
  [2.1].
- Un paciente puede tener más de una categoría de discapacidad [2.1].
- La ciudad de residencia se registra con su código DANE [2.1].
- Un paciente ya existe si tiene el mismo tipo y número de documento (D-07).
- Si el servicio se presta de forma particular, la entidad que se registra es una secretaría de salud
  del catálogo de entidades (HU-038): "En el caso de que la prestación del servicio de salud se
  preste de forma particular, el código de la entidad administradora corresponderá a las secretarías
  departamentales, distritales o locales de salud, donde se realizó la prestación del servicio de
  salud" (hoja 16). La elige el auxiliar; el sistema no tiene un dato que diga "particular".
- RC-2. Un dato que sale de un catálogo solo acepta valores activos de ese catálogo.
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora. Quién lo hizo lo agrega el
  inicio de sesión (HU-001, escenario 9); esta historia no depende de él.
- RC-7. Cada dato se valida con los valores permitidos, el formato y la longitud de su numeral en el
  anexo.
- RC-11. Un registro conserva el código y el nombre que tenía el valor el día en que se registró; si
  la fuente cambia el nombre después, su consulta muestra también el vigente (D-25).

**Precondiciones:**
- Ninguna. Si un catálogo que usa no tiene valores, aplica el escenario 14.

**Criterios de aceptación BDD:**

### Escenario 1: Registro con los datos obligatorios
- **Dado** que el auxiliar de admisión tiene los datos obligatorios de un paciente nuevo
- **Cuando** lo registra
- **Entonces** el sistema guarda el registro
- **Y** confirma el registro con el tipo y el número de documento del paciente

### Escenario 2: Más de una nacionalidad
- **Dado** que el paciente tiene doble nacionalidad
- **Cuando** el auxiliar de admisión registra los dos países
- **Entonces** el sistema guarda los dos

### Escenario 3: Fecha y hora de nacimiento por separado
- **Dado** que el auxiliar de admisión tiene la fecha y la hora de nacimiento
- **Cuando** registra al paciente
- **Entonces** el sistema guarda la fecha y la hora por separado

### Escenario 4: Fecha de nacimiento sin hora
- **Dado** que el paciente no sabe su hora de nacimiento
- **Cuando** el auxiliar de admisión registra solo la fecha
- **Entonces** el sistema acepta el registro

### Escenario 5: Solo ocupaciones hijas
- **Dado** que el auxiliar de admisión elige una ocupación general, que agrupa otras más específicas
- **Cuando** intenta guardarla
- **Entonces** el sistema la rechaza
- **Y** le pide elegir una ocupación del nivel más específico

### Escenario 6: Más de una discapacidad
- **Dado** que el paciente tiene dos categorías de discapacidad
- **Cuando** el auxiliar de admisión registra las dos
- **Entonces** el sistema guarda las dos

### Escenario 7: Ciudad con su código DANE
- **Dado** que el paciente reside en Colombia
- **Cuando** el auxiliar de admisión elige la ciudad de residencia
- **Entonces** el sistema guarda la ciudad con su código DANE

### Escenario 8: Valor que no está en su catálogo
- **Dado** que el auxiliar de admisión ingresa un valor que no está activo en su catálogo, por ejemplo un tipo de documento que no existe o una ciudad que no está en el catálogo de municipios
- **Cuando** intenta guardar
- **Entonces** el sistema lo rechaza
- **Y** dice cuál dato está mal
- **Y** aplica igual para país, tipo de documento, ocupación, discapacidad, ciudad, etnia, comunidad étnica y entidad del plan de beneficios

### Escenario 9: Dato que no cumple su numeral
- **Dado** que un dato no cumple los valores permitidos, el formato o la longitud de su numeral, por ejemplo un número de documento de 2 caracteres o un sexo biológico que no está en la lista
- **Cuando** el auxiliar de admisión intenta guardar
- **Entonces** el sistema lo rechaza
- **Y** dice cuál dato está mal

### Escenario 10: Falta un dato obligatorio
- **Dado** que falta un dato obligatorio de la lista de datos
- **Cuando** el auxiliar de admisión intenta guardar
- **Entonces** el sistema no guarda
- **Y** dice cuáles faltan

### Escenario 11: Se conservan los datos válidos
- **Dado** que un dato está mal y los demás están bien
- **Cuando** el auxiliar de admisión intenta guardar
- **Entonces** el sistema conserva los datos válidos en pantalla
- **Y** marca solo el que hay que corregir

### Escenario 12: Paciente que ya existe
- **Dado** que ya hay un paciente con el mismo tipo y número de documento
- **Cuando** el auxiliar de admisión intenta registrarlo
- **Entonces** el sistema no crea otro registro
- **Y** avisa que ya hay un paciente registrado con ese documento

### Escenario 13: Registro cancelado
- **Dado** que el auxiliar de admisión empezó un registro
- **Cuando** lo cancela antes de guardar
- **Entonces** el sistema no guarda nada

### Escenario 14: Catálogo sin valores
- **Dado** que uno de los catálogos del paciente todavía no tiene valores activos, por ejemplo el de municipios
- **Cuando** el auxiliar de admisión abre el registro de un paciente
- **Entonces** el sistema dice qué catálogo falta cargar
- **Y** no deja guardar al paciente mientras ese dato sea obligatorio para él (D-19)

### Escenario 15: Buscar y elegir un valor de un catálogo grande
- **Dado** que el auxiliar de admisión registra la ocupación, el país, la ciudad de residencia o la entidad del plan de beneficios
- **Cuando** escribe el código o parte del nombre
- **Entonces** el sistema muestra solo los valores activos que coinciden: por código, el exacto; por nombre, todos los que contienen lo escrito, sin distinguir mayúsculas ni tildes (D-23)
- **Y** cada ciudad aparece con su departamento, para distinguir las que tienen el mismo nombre
- **Y** el auxiliar elige uno de la lista; lo escrito no se guarda como valor

**Dependencias:** Catálogos de países (HU-018), tipos de documento (HU-034), ocupaciones
(HU-022), discapacidad (HU-035), municipios (HU-026), etnias (HU-036), comunidades étnicas (HU-037)
y entidades que responden por el plan de beneficios (HU-038).
**Supuestos o asuntos pendientes:**
- D-19: la columna de obligatorios es una propuesta. El anexo no tiene columna de obligatoriedad.
- D-19: con los tipos de documento "Adulto sin identificar", "Menor sin identificar" y "Sin
  identificación" (numeral 2) pueden faltar el número, los nombres, la fecha de nacimiento o la
  nacionalidad, y la regla del paciente repetido no sirve. Falta decidir qué se exige en esos casos.
- La comunidad étnica aplica a las etnias 01 a 06, porque la hoja 15 la define para comunidades
  indígenas, pueblo ROM y comunidades negras, afro, palenqueras y raizales.
- Con una sola sede (D-05), el lugar donde se presta el servicio es el de la IPS, salvo en la
  atención extramural. Que la entidad del servicio particular se registre una vez con el paciente,
  y no en cada atención, es inferencia del equipo: el numeral 15 está entre los datos de
  identificación. Se supone que el catálogo de entidades trae las secretarías de salud (HU-038).
- Mientras no se confirme el catálogo de comunidades étnicas (HU-037), el registro deja guardar sin
  ese dato: el anexo de la Resolución 1888 de 2025 lo pide "cuando aplique".
- Identidad de género: D-19 la deja obligatoria porque la lista tiene "No lo declara". El anexo de la
  Resolución 1888 de 2025 dice que "es un dato opcional que será diligenciado a criterio del
  profesional de la salud de acuerdo con el escenario de atención". Se pregunta al docente.
- D-25: propuesta del equipo (RC-11).

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-004 c1 (obligatorios del antiguo escenario 9, hoy el 10; clave del
  duplicado del antiguo 11, hoy el 12) y c2 (enumerar los datos del grupo paciente, formatos y
  obligatoriedad: la lista de datos con numeral y hoja; fecha sin hora: escenario 4).
- Escenarios antiguos: del 1 al 8 conservan su número; el 9 (obligatorios incompletos) es hoy el
  10; el 10 (se conservan los válidos) es el 11; el 11 (paciente que ya existe) es el 12; el 12
  (ciudad sin código DANE en el catálogo) lo absorbe el 8, porque una ciudad sin código no puede
  estar activa en el catálogo de municipios (HU-026).
- Versión 4 de la tanda: sale la precondición de catálogos con valores, que chocaba con el aviso de
  catálogo vacío de las historias de catálogo (escenario 14, mismo defecto que el docente señaló en
  HU-008); el servicio particular de la hoja 16 pasa aquí desde HU-038 como regla, por la revisión de
  la tanda 4. Tuvo un escenario 16 que no probaba una conducta del sistema; salió con la revisión
  final cruzada.
- Versión 5 de la tanda (límite de 12 historias, 2026-10-21): deja de depender del inicio de
  sesión. Sale la precondición de sesión iniciada; el antiguo escenario 14 (el médico no registra
  pacientes) lo cubren HU-001 escenarios 6 y 8; el 15 (catálogo sin valores) pasa a ser el 14.
- Versión 6 de la tanda (correcciones mínimas, 2026-10-22): escenario 15 nuevo, buscar y elegir un
  valor de un catálogo grande, con el departamento de cada ciudad (HU-027 c2 lo dejaba "en HU-004 y
  HU-006" sin escenario). Los escenarios 1 y 12 ya no miran la consulta del paciente (HU-005), que
  depende de esta historia.
- Tablero, Ajustes por historia: filas con destino HU-004.

---

# HU-005 · Consulta de un paciente

**ID:** HU-005
**Título:** Consulta de un paciente
**Funcionalidad relacionada:** Gestión de pacientes
**Descripción:**
> Como auxiliar de admisión
> Quiero buscar a un paciente por su documento y ver sus datos
> Para no pedirle de nuevo lo que ya entregó y ver qué le falta antes de remitirlo a atención

**Valor de negocio:** Evita que el paciente repita sus datos y deja ver a tiempo lo que falta.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Se busca por tipo y número de documento (numeral 2; D-07).
- La consulta muestra los datos de la lista de HU-004 que tenga registrados, con el nombre de cada
  valor y no solo su código: el nombre con que se registró y, si el catálogo lo cambió después, también
  el vigente (RC-11, escenario 13). La oposición a la donación y la voluntad anticipada se consultan en
  HU-011 y HU-015.
- Los datos con varios valores (nacionalidad, discapacidad) se muestran completos [2.1].
- Pueden consultar el auxiliar de admisión y el médico tratante; solo el auxiliar puede cambiar
  datos (matriz de permisos).
- RC-4. Un valor que se inactivó después del registro se conserva en el paciente.

**Precondiciones:**
- La persona tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Consulta de un paciente registrado
- **Dado** que el paciente tiene registro
- **Cuando** el auxiliar de admisión lo busca por tipo y número de documento
- **Entonces** el sistema muestra sus datos de la lista de HU-004
- **Y** cada valor de catálogo aparece con su nombre

### Escenario 2: Paciente que no existe
- **Dado** que no hay paciente con ese tipo y número de documento
- **Cuando** el auxiliar de admisión lo busca
- **Entonces** el sistema dice que no hay registro con ese documento

### Escenario 3: Búsqueda incompleta
- **Dado** que el auxiliar de admisión escribe el número pero no elige el tipo de documento
- **Cuando** busca
- **Entonces** el sistema pide el tipo de documento

### Escenario 4: Varias nacionalidades
- **Dado** que el paciente tiene más de un país de nacionalidad
- **Cuando** el auxiliar de admisión lo consulta
- **Entonces** el sistema muestra todos

### Escenario 5: Varias discapacidades
- **Dado** que el paciente tiene más de una categoría de discapacidad
- **Cuando** el auxiliar de admisión lo consulta
- **Entonces** el sistema muestra todas

### Escenario 6: Fecha y hora de nacimiento
- **Dado** que el paciente tiene fecha y hora de nacimiento
- **Cuando** el auxiliar de admisión lo consulta
- **Entonces** el sistema muestra la fecha y la hora por separado
- **Y** si solo hay fecha, muestra la fecha sin hora

### Escenario 7: Ciudad con su código
- **Dado** que el paciente reside en Colombia
- **Cuando** el auxiliar de admisión lo consulta
- **Entonces** el sistema muestra la ciudad con su código DANE

### Escenario 8: Datos opcionales vacíos
- **Dado** que el paciente se registró sin algunos datos opcionales, por ejemplo sin segundo nombre ni hora de nacimiento
- **Cuando** el auxiliar de admisión lo consulta
- **Entonces** el sistema muestra lo registrado
- **Y** deja en blanco los datos opcionales que no se registraron

### Escenario 9: Valor que se inactivó después
- **Dado** que el paciente tiene un valor que luego se inactivó en su catálogo, por ejemplo una entidad del plan de beneficios que dejó de operar
- **Cuando** el auxiliar de admisión lo consulta
- **Entonces** el sistema muestra el valor con la marca "inactivo"

### Escenario 10: Consulta del médico tratante
- **Dado** que un médico tratante tiene sesión iniciada
- **Cuando** consulta a un paciente
- **Entonces** ve los mismos datos
- **Y** no tiene opciones para cambiarlos

### Escenario 11: Cargo sin permiso
- **Dado** que un profesional de estadística tiene sesión iniciada
- **Cuando** intenta consultar un paciente
- **Entonces** el sistema no lo permite

### Escenario 12: La consulta no cambia nada
- **Dado** que el auxiliar de admisión consultó un paciente
- **Cuando** cierra la consulta
- **Entonces** sus datos quedan igual que antes

### Escenario 13: Nombre que cambió en el catálogo
- **Dado** que el Ministerio cambió el nombre de un valor después de registrar al paciente, por ejemplo el de su tipo de documento
- **Cuando** el auxiliar de admisión consulta al paciente
- **Entonces** ve el nombre con que se registró el valor
- **Y** ve también el nombre vigente, marcado como tal (RC-11)

**Dependencias:** HU-001 y HU-004.
**Supuestos o asuntos pendientes:** D-07: la búsqueda es por tipo y número de documento.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-005 c1 (campos de búsqueda: regla y escenarios 1 y 3; qué incluye "la
  información completa": escenario 1; el antiguo escenario 7, hoy el 8) y c2 (datos, criterios de
  búsqueda y permisos: reglas y escenarios 10 y 11; paciente incompleto frente a HU-004: escenario
  8). HU-002 c1 (qué datos ve el médico): escenario 10.
- Escenarios antiguos: el 1 y el 2 conservan su número; el 3 al 6 son hoy el 4 al 7; el 7 (registro
  incompleto) pasa al 8, porque un paciente no se guarda sin sus datos obligatorios (HU-004) y solo
  pueden faltar los opcionales; el 8 (sin sesión) lo cubre HU-001 escenario 4;
  el 9 (paciente sin información clínica) lo cubre el 1, que no depende de las atenciones; el 10 es
  hoy el 12.
- Tablero, Ajustes por historia: filas con destino HU-005.

---

# HU-006 · Actualización y corrección de los datos de un paciente

**ID:** HU-006
**Título:** Actualización y corrección de los datos de un paciente
**Funcionalidad relacionada:** Gestión de pacientes
**Descripción:**
> Como auxiliar de admisión
> Quiero cambiar los datos de un paciente cuando cambian o cuando se registraron mal
> Para que un dato viejo o equivocado no pase a las atenciones siguientes

**Valor de negocio:** Un dato errado se repite en cada atención. Guardar quién cambió qué y cuándo
permite saber de dónde salió cada valor.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Se pueden cambiar todos los datos de la lista de HU-004, con las mismas reglas del registro. La
  oposición y la voluntad anticipada se cambian en sus propias historias.
- Hay dos casos y el auxiliar dice cuál es:
  - **Actualizar:** el dato cambió en la realidad, por ejemplo el paciente se mudó.
  - **Corregir:** el dato se registró mal.
- En los dos casos el dato anterior se conserva.
- RC-2. Un dato que sale de un catálogo solo acepta valores activos de ese catálogo.
- RC-4. Un valor que se inactivó en su catálogo se conserva en el paciente mientras no se cambie ese
  dato.
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora.
- RC-6. Al corregir un dato mal registrado "se agregará el nuevo dato con la fecha, hora, nombre e
  identificación de quien hizo la corrección" (artículo 10, parágrafo 2, de la Resolución 866). El
  dato anterior no se borra.
- RC-7. Cada dato se valida con los valores permitidos, el formato y la longitud de su numeral en el
  anexo.

**Precondiciones:**
- El auxiliar de admisión tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Actualizar un dato que cambió
- **Dado** que el paciente se mudó a otra ciudad
- **Cuando** el auxiliar de admisión actualiza la ciudad de residencia
- **Entonces** el sistema guarda la ciudad nueva con su código DANE, la fecha, la hora y quién hizo el cambio
- **Y** conserva la anterior en el historial

### Escenario 2: Corregir un dato mal registrado
- **Dado** que el segundo apellido se registró mal
- **Cuando** el auxiliar de admisión lo corrige e indica que es una corrección
- **Entonces** el sistema guarda el dato nuevo con la fecha, la hora, el nombre y la identificación de quien corrigió
- **Y** conserva el anterior en el historial

### Escenario 3: Ver el historial de un dato
- **Dado** que un dato del paciente se actualizó o se corrigió
- **Cuando** el auxiliar de admisión consulta su historial
- **Entonces** el sistema muestra el valor vigente y los anteriores
- **Y** para cada cambio dice si fue actualización o corrección, la fecha, la hora, el nombre y la identificación de quien lo hizo

### Escenario 4: Agregar una nacionalidad
- **Dado** que el paciente tiene una nacionalidad registrada
- **Cuando** el auxiliar de admisión agrega otra
- **Entonces** el sistema guarda las dos

### Escenario 5: Retirar una discapacidad
- **Dado** que el paciente tiene dos categorías de discapacidad
- **Cuando** el auxiliar de admisión retira una
- **Entonces** el sistema conserva la otra
- **Y** la retirada queda en el historial

### Escenario 6: Ocupación general
- **Dado** que el auxiliar de admisión cambia la ocupación por una general, que agrupa otras más específicas
- **Cuando** confirma el cambio
- **Entonces** el sistema lo rechaza
- **Y** conserva la ocupación anterior

### Escenario 7: Dato que no cumple su numeral
- **Dado** que el valor nuevo no cumple los valores permitidos, el formato o la longitud de su numeral, o no está activo en su catálogo
- **Cuando** el auxiliar de admisión confirma el cambio
- **Entonces** el sistema lo rechaza
- **Y** conserva el valor anterior

### Escenario 8: Dejar vacío un dato obligatorio
- **Dado** que el auxiliar de admisión deja vacío un dato obligatorio de la lista de HU-004
- **Cuando** confirma el cambio
- **Entonces** el sistema no lo acepta
- **Y** conserva el valor anterior y dice cuál dato falta

### Escenario 9: Documento que ya tiene otro paciente
- **Dado** que el auxiliar de admisión cambia el tipo y número de documento por unos que ya tiene otro paciente
- **Cuando** confirma el cambio
- **Entonces** el sistema lo rechaza
- **Y** avisa que ese documento ya está registrado

### Escenario 10: Valor inactivo que no se toca
- **Dado** que el paciente tiene una etnia que luego se inactivó en su catálogo
- **Cuando** el auxiliar de admisión actualiza otro dato, por ejemplo la zona de residencia
- **Entonces** el sistema guarda el cambio
- **Y** conserva la etnia como estaba

### Escenario 11: Cambio cancelado
- **Dado** que el auxiliar de admisión cambió datos sin confirmar
- **Cuando** cancela
- **Entonces** el sistema conserva los valores anteriores

### Escenario 12: Paciente que no existe
- **Dado** que no hay paciente con ese documento
- **Cuando** el auxiliar de admisión intenta cambiar sus datos
- **Entonces** el sistema no lo permite

### Escenario 13: Cargo sin permiso
- **Dado** que un médico tratante tiene sesión iniciada
- **Cuando** intenta cambiar los datos de un paciente
- **Entonces** el sistema no lo permite

**Dependencias:** HU-001 y HU-004.
**Supuestos o asuntos pendientes:** D-19 (datos obligatorios).

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-006 c1 (hora de la corrección en el escenario 2; campos obligatorios
  del antiguo escenario 7, hoy el 8) y c2 (campos editables: regla; dato anterior y nuevo, fecha,
  hora, nombre e identificación de quien corrige: escenarios 2 y 3; ciudad y ocupación como
  criterios del paciente: escenarios 1 y 6).
- Escenarios antiguos: el 1 y el 2 son hoy el 1, 2 y 3; el 3 al 6 son el 4, 5, 6 y 1; el 7 es el 8;
  el 8 es el 11; el 9 es el 12; el 10 (la actualización no afecta la información clínica) pasa a
  HU-008 escenario 9, que consulta la atención después del cambio.
- Resuelto en la tanda 2 (D-24): cada atención guarda la ocupación que el paciente tenía al crearla;
  cambiar o corregir la del paciente aquí no cambia la de sus atenciones.
- Versión 3 de la tanda: sale la precondición "El paciente tiene registro", que chocaba con el
  escenario 12 (hallazgo de la revisión de la tanda 2), y los datos de quien cambia van junto al
  dato nuevo, como dice RC-6.
- Versión 5 de la tanda (límite de 12 historias, 2026-10-21): sale el escenario 14 (las
  atenciones siguen con el paciente), que miraba la pantalla de HU-008 y hacía que HU-006 y HU-008
  dependieran una de otra; lo prueba HU-008 escenario 9.
- Tablero, Ajustes por historia: filas con destino HU-006.

---

## Registro

| Fecha | Versión | Cambio |
|---|---|---|
| 2026-10-20 | 1 | Primera escritura: HU-001 fusiona HU-001 a HU-003; HU-004 a HU-006 editadas con los ajustes del tablero y los comentarios del docente |
| 2026-10-20 | 2 | Correcciones de la revisión independiente: la consulta excluye los numerales 8 y 9; validación por valores permitidos además de formato y longitud; hoja del anexo en la lista de datos; escenarios de valores inactivados (HU-005 esc. 9, HU-006 esc. 10); continuidad de sesión recuperada (HU-001 esc. 10); supuestos de documentos sin identificación y de usuarios creados al instalar; trazabilidad con números de escenario antiguos y nuevos |
| 2026-10-20 | 3 | HU-006: sale la precondición que chocaba con el escenario 12; los datos de quien actualiza o corrige van junto al dato nuevo (RC-6); el pendiente de la ocupación queda resuelto con D-24 (tanda 2) |
| 2026-10-20 | 3 (continuación) | Matriz de HU-001 y escenarios 5 y 7: el auxiliar también corrige la oposición y la voluntad anticipada; el administrador reactiva valores (revisión de la tanda 3, hallazgo 18) |
| 2026-10-20 | 4 | Revisiones de las tandas 4 y 5: el cargo de los catálogos es el profesional de estadística (HU-001, matriz y escenarios 6 y 7; HU-005, escenario 11); el médico anula atenciones (HU-043); HU-004 sin la precondición de catálogos con valores, con los escenarios 15 (catálogo sin valores) y 16 (paciente particular, hoja 16); nombre del momento del registro (RC-11, D-25) en HU-004 y HU-005 |
| 2026-10-20 | 4 (continuación) | Revisión final cruzada: el escenario 16 de HU-004 sale (no probaba una conducta del sistema) y el servicio particular queda como regla; comunidad étnica opcional mientras no haya catálogo; identidad de género como pregunta; HU-004 escenario 11 con la acción del auxiliar; HU-005 escenario 13 (nombre que cambió en el catálogo, RC-11); correspondencia completa de los escenarios de HU-001, HU-002 y HU-003, que va en el comentario al docente y en las notas de archivo; cada Cuando de HU-001, HU-005 y HU-006 es una acción del usuario, no del sistema ni impersonal |
| 2026-10-20 | 4 (verificación) | Verificación de la aplicación (`revisiones/verificacion-aplicacion-final.md`): HU-004 escenario 11 con el sistema como sujeto del Entonces; HU-005 escenario 12 con el auxiliar en el Dado y el Cuando; correspondencia completa de los escenarios antiguos de HU-004 y HU-005 |
| 2026-10-21 | 5 | Límite de 12 historias: HU-004 deja de depender del inicio de sesión (sale su escenario 14 y la precondición de sesión); HU-001 recibe el escenario 14, cargo sin permiso en los catálogos, que antes repetían las 13 historias de catálogo; HU-006 pierde el escenario 14, que la hacía depender de HU-008 |
| 2026-10-22 | 6 | Correcciones mínimas para el plan de 12 historias: HU-004 gana el escenario 15 (buscar y elegir un valor de un catálogo grande, con el departamento de cada ciudad) y sus escenarios 1 y 12 dejan de remitir a HU-005; HU-001 dice que las opciones de la matriz se prueban a medida que su historia se desarrolla |
