# Backlog IPS Salud y Vida, versión 3. Tanda 3: oposición a la donación y voluntad anticipada

Versión 5, 2026-10-21. Borrador corregido con la revisión independiente
(`revisiones/revision-tanda-3.md`) y con la revisión final cruzada. HU-010 a HU-017. Las reglas comunes (RC), la matriz de permisos,
el glosario y las convenciones están en la tanda 0. El actor de las ocho historias es el auxiliar de
admisión; el médico tratante también consulta.

La guía pide las dos como "estructura separada (CRUD)" [2.1]. Van en cuatro historias cada una
porque cada operación deja un estado distinto, tiene permisos distintos o entrega valor por separado,
que son los casos en que el Prompt 2 pide separar: registrar la primera manifestación, consultarla
(también el médico, sin cambiarla), cambiar la voluntad o corregir un dato, y anular un registro
hecho por error. El docente calificó cada una como funcional y con actor válido.

Decisiones de esta tanda (propuestas):

| Tema | Propuesta | Por qué |
|---|---|---|
| Qué guarda el sistema | La constancia que pide el anexo, no el documento: Sí o No, la fecha del documento y, en la voluntad anticipada, el prestador donde está y qué evento fue | Los numerales 8 y 9 piden esos datos (hoja 14) |
| Situaciones que se muestran | Oposición: "Se opone" (Sí), "No se opone" (No), "Sin registro" y "Sin registro vigente". Voluntad anticipada: "Tiene documento", "No tiene documento", "Revocado", "Sin registro" y "Sin registro vigente". "Sin registro" quiere decir que no se ha preguntado o no se sabe; no es "No". "Sin registro vigente" quiere decir que todas las versiones están anuladas | Comentarios del docente en HU-010, HU-011, HU-014 y HU-015 |
| Estados de cada versión | "Vigente", "Anterior" (hubo un cambio) y "Anulado" (se registró por error) | Comentario del docente en HU-011 |
| Fecha del documento de oposición | Obligatoria con "Sí". Con "No" se registra solo si hay un documento, por ejemplo uno que retira una oposición anterior | El numeral 8 define la oposición como una voluntad expresada "mediante un documento escrito" (hoja 14). La Ley 1805 de 2016 fija la forma de oponerse y no la de retirar la oposición. Inferencia del equipo |
| Cambiar la voluntad y corregir son distintos | Un cambio de voluntad crea una versión nueva. Una corrección arregla la fecha o el código del prestador mal registrados, sin crear versión (RC-6). Un "Sí" o "No" mal registrado no se corrige: se anula y se registra de nuevo | Comentario del docente en HU-012: "Distinguir corrección de error de cambio de voluntad" |
| Anular | Solo para registros hechos por error. Pide motivo, deja el registro como "Anulado" y no revoca la voluntad del paciente. Si se anula la versión vigente, vuelve a ser vigente la anterior más reciente que no esté anulada | El docente da por funcional anular un registro hecho por error (HU-013 c2, HU-017 c2) |
| Revocar la voluntad anticipada | Es una actualización (HU-016): la versión queda "No", con la fecha de revocación y el evento "revocación", y se muestra como "Revocado" | El numeral 9 pone la fecha de revocación en el mismo dato que la de suscripción, modificación y sustitución (hoja 14). Que el valor sea "No" es inferencia del equipo |
| Código del prestador donde está el documento | Se escribe y se valida con el formato del numeral 9: 12 caracteres. No hay catálogo de prestadores | D-05 |
| Fechas | Ninguna fecha de documento puede ser posterior al día del registro ni anterior a la de la versión anterior | Regla del equipo |

---

# HU-010 · Registro de la oposición a la presunción legal de donación

**ID:** HU-010
**Título:** Registro de la oposición a la presunción legal de donación
**Funcionalidad relacionada:** Oposición a la donación
**Descripción:**
> Como auxiliar de admisión
> Quiero registrar si el paciente se opone o no a ser donante y, si hay documento, su fecha
> Para que esa voluntad esté en el sistema antes de que alguien tenga que decidir sin ella

**Valor de negocio:** Es una manifestación de voluntad con efectos legales. Tenerla registrada evita
decidir sin conocerla. Que no esté en el sistema no prueba que el paciente no se haya opuesto.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Los datos son los dos del numeral 8 del anexo (hoja 14): la manifestación, "Sí" (se opone a ser
  donante) o "No" (no se opone), y la fecha en que se firmó el documento válido ante el Instituto
  Nacional de Salud o ante la Entidad Administradora del Plan de Beneficios.
- La manifestación es obligatoria. La fecha es obligatoria con "Sí"; con "No" se registra solo si el
  paciente trae un documento, por ejemplo uno que retira una oposición anterior (inferencia del
  equipo por la definición del numeral 8).
- La fecha no puede ser posterior al día del registro (regla del equipo).
- El registro parte sin valor marcado: el sistema no guarda "No" por omisión. Un paciente sin
  manifestación aparece como "Sin registro", que no es "No".
- Un paciente tiene una sola manifestación vigente; para cambiarla está HU-012.
- Solo se registra la oposición de un paciente que tiene registro (HU-004).
- La oposición se guarda aparte de los datos del paciente [2.1].
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora.
- RC-7. Cada dato se valida con los valores permitidos, el formato y la longitud de su numeral en el
  anexo.

**Precondiciones:**
- El auxiliar de admisión tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Registro de una oposición
- **Dado** que el paciente firmó un documento en el que se opone a ser donante
- **Cuando** el auxiliar de admisión registra "Sí" con la fecha del documento
- **Entonces** el sistema guarda la manifestación como vigente, con la fecha del documento

### Escenario 2: Registro de una no oposición
- **Dado** que el paciente manifiesta que no se opone a ser donante y no trae documento
- **Cuando** el auxiliar de admisión registra "No"
- **Entonces** el sistema guarda la manifestación como vigente
- **Y** no exige la fecha

### Escenario 3: No oposición con documento
- **Dado** que el paciente trae un documento en el que retira una oposición anterior
- **Cuando** el auxiliar de admisión registra "No" con la fecha de ese documento
- **Entonces** el sistema guarda la manifestación como vigente, con esa fecha

### Escenario 4: Sin valor por omisión
- **Dado** que el paciente no tiene manifestación registrada
- **Cuando** el auxiliar de admisión abre el registro de su oposición
- **Entonces** el formulario no trae marcado ni "Sí" ni "No"
- **Y** el sistema no guarda "No" si el auxiliar no lo elige

### Escenario 5: Falta un dato
- **Dado** que el auxiliar de admisión no indicó la manifestación, o indicó "Sí" sin la fecha del documento
- **Cuando** intenta registrar
- **Entonces** el sistema no registra
- **Y** dice cuál falta

### Escenario 6: Fecha no válida
- **Dado** que la fecha del documento no cumple el formato del numeral 8, o es posterior al día del registro (regla del equipo)
- **Cuando** el auxiliar de admisión intenta registrar
- **Entonces** el sistema la rechaza
- **Y** dice por qué

### Escenario 7: Paciente que ya tiene manifestación
- **Dado** que el paciente ya tiene una manifestación vigente
- **Cuando** el auxiliar de admisión intenta registrar otra
- **Entonces** el sistema no la registra
- **Y** avisa que ya existe y que un cambio se registra como actualización

### Escenario 8: Paciente que no existe
- **Dado** que no hay paciente con ese documento
- **Cuando** el auxiliar de admisión intenta registrar su oposición
- **Entonces** el sistema no lo permite
- **Y** avisa que el paciente debe registrarse primero (HU-004)

### Escenario 9: Registro a nombre de quien lo hizo
- **Dado** que el auxiliar de admisión tiene sesión iniciada
- **Cuando** registra una manifestación
- **Entonces** el sistema guarda con ella el nombre y la identificación del auxiliar, la fecha y la hora del registro

### Escenario 10: Los datos del paciente no cambian
- **Dado** que el paciente tiene sus datos registrados
- **Cuando** el auxiliar de admisión registra su oposición
- **Entonces** sus datos quedan iguales

### Escenario 11: Registro cancelado
- **Dado** que el auxiliar de admisión empezó el registro
- **Cuando** cancela antes de guardar
- **Entonces** el sistema no guarda nada

### Escenario 12: Cargo sin permiso
- **Dado** que un médico tratante tiene sesión iniciada
- **Cuando** intenta registrar una oposición
- **Entonces** el sistema no lo permite

**Dependencias:** HU-001 y HU-004.
**Supuestos o asuntos pendientes:**
- Que la fecha sea obligatoria solo con "Sí" es inferencia del equipo. Cómo se retira una oposición no
  lo fija la Ley 1805 de 2016; si el docente pide un documento, la fecha pasa a obligatoria también
  con "No" (pregunta al docente).
- La regla de la fecha futura es del equipo.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-010 c1 (obligatorios del antiguo escenario 9, hoy el 5; valores para
  oposición, no oposición y sin manifestación: reglas y escenarios 1 a 4; ausencia de registro no es
  no oposición: regla y escenario 4) y c2 (quitar "si no está registrada no existe": valor de
  negocio; Sí o No y fecha de firma del documento escrito: reglas y escenarios 1 a 3; obligatorios:
  regla y escenario 5; estructura separada con resultado funcional: escenario 10).
- Escenarios antiguos: el 1 es el 1; el 2 es el 2; el 3 (sin manifestación) es el 4, que prueba que
  el registro no guarda "No" por omisión, y la presentación queda en HU-011; el 4 (fecha de la
  manifestación) pasa a fecha del documento en el 1 y el 3; el 5 es el 7; el 6 es el 8; el 7 es el
  9; el 8 (sin sesión) lo cubre HU-001 escenario 4; el 9 es el 5; el 10 es el 10.
- Versión 2: sale la precondición del paciente registrado, que chocaba con el escenario 8.
- Versión 5 de la tanda (límite de 12 historias, 2026-10-21): los escenarios 7 y 10 ya no remiten a HU-011 ni a HU-012, que van después. El orden queda registrar,
  consultar, actualizar y anular: cada historia solo remite a las anteriores.
- Tablero, Ajustes por historia: filas con destino HU-010.

---

# HU-011 · Consulta de la oposición a la presunción legal de donación

**ID:** HU-011
**Título:** Consulta de la oposición a la presunción legal de donación
**Funcionalidad relacionada:** Oposición a la donación
**Descripción:**
> Como auxiliar de admisión
> Quiero ver si el paciente se opone a ser donante y desde cuándo
> Para saber si esa voluntad ya está en el sistema o hay que preguntársela

**Valor de negocio:** Distinguir un "No" de un "no se sabe" evita tomar la falta de registro como
voluntad del paciente. El médico tratante también la consulta, sin cambiarla.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-010
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- La consulta muestra la situación vigente: "Se opone" (Sí), "No se opone" (No), "Sin registro" o
  "Sin registro vigente", con la fecha del documento cuando la hay (numeral 8, hoja 14).
- El historial muestra cada versión con su estado ("Vigente", "Anterior" o "Anulado"), la fecha del
  documento, quién la registró y cuándo; las anuladas, con su motivo.
- El historial muestra también las correcciones: el valor anterior y el nuevo, con la fecha, la
  hora, el nombre y la identificación de quien corrigió (RC-6).
- "Sin registro" y "Sin registro vigente" no quieren decir que el paciente no se haya manifestado.
- Pueden consultar el auxiliar de admisión y el médico tratante (matriz de permisos).

**Precondiciones:**
- La persona tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Consulta de una oposición vigente
- **Dado** que el paciente tiene registrado "Sí"
- **Cuando** el auxiliar de admisión consulta su oposición
- **Entonces** el sistema muestra "Se opone", la fecha del documento, quién la registró y cuándo

### Escenario 2: Consulta de una no oposición sin documento
- **Dado** que el paciente tiene registrado "No" sin la fecha de un documento
- **Cuando** el auxiliar de admisión consulta su oposición
- **Entonces** el sistema muestra "No se opone", quién lo registró y cuándo, sin fecha del documento

### Escenario 3: Consulta de una no oposición con documento
- **Dado** que el paciente registró "No" con la fecha de un documento
- **Cuando** el auxiliar de admisión consulta su oposición
- **Entonces** el sistema muestra "No se opone" con la fecha del documento, quién lo registró y cuándo

### Escenario 4: Paciente sin manifestación
- **Dado** que el paciente nunca tuvo manifestación registrada
- **Cuando** el auxiliar de admisión consulta su oposición
- **Entonces** el sistema muestra "Sin registro: no se ha preguntado o no se sabe si el paciente se opone"

### Escenario 5: Historial de la manifestación
- **Dado** que el paciente tiene una manifestación registrada
- **Cuando** el auxiliar de admisión consulta el historial
- **Entonces** el sistema muestra cada versión con su estado, la fecha del documento, quién la registró y cuándo
- **Y** marca como "Vigente" la que rige hoy

### Escenario 6: Búsqueda incompleta
- **Dado** que el auxiliar de admisión escribe solo el tipo o solo el número de documento
- **Cuando** consulta la oposición
- **Entonces** el sistema pide los dos datos y no muestra ninguna manifestación

### Escenario 7: Consulta del médico tratante
- **Dado** que un médico tratante tiene sesión iniciada
- **Cuando** consulta la oposición de un paciente
- **Entonces** ve lo mismo
- **Y** no tiene opciones para cambiarla

### Escenario 8: Cargo sin permiso
- **Dado** que un profesional de estadística tiene sesión iniciada
- **Cuando** intenta consultar la oposición de un paciente
- **Entonces** el sistema no lo permite

### Escenario 9: Paciente que no existe
- **Dado** que no hay paciente con ese documento
- **Cuando** el auxiliar de admisión consulta su oposición
- **Entonces** el sistema dice que el paciente no existe

### Escenario 10: La consulta no cambia nada
- **Dado** que el auxiliar de admisión consultó la oposición de un paciente
- **Cuando** cierra la consulta
- **Entonces** la manifestación queda igual que antes

**Dependencias:** HU-001, HU-004 y HU-010.
**Supuestos o asuntos pendientes:** Ninguno.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-011 c1 (qué muestra el historial del antiguo escenario 5 y cómo
  distinguir vigente, anterior y eliminada: reglas y escenario 5; la versión anterior la prueba
  HU-012 escenarios 1 y 5, y la anulada HU-013 escenario 5) y c2 (fecha del documento escrito:
  escenarios 1 y 3; permisos: escenarios 7 y 8; mantener el historial y no tomar la falta de
  registro vigente como falta de voluntad: escenario 4 y HU-013 escenario 5).
- Escenarios antiguos: el 1 es el 1; el 2 ("informa que no existe") pasa a "Sin registro" en el 4;
  el 3 (no oposición) va en el 2 y el 3; el 4 (quién registró) va en el 1; el 5 (historial) es el
  5, y los cambios que muestra los prueban HU-012 escenarios 1 y 5; el 6 (paciente que no existe)
  es el 9; el 7 (sin sesión) lo cubre HU-001 escenario 4; el 8 (no modifica) es el 10; el 9
  (consulta junto con el registro del paciente) sale, porque ataba esta historia a HU-005; el 10
  (manifestación eliminada) lo prueba HU-013 escenario 5.
- Versión 2: vuelve el "Para" del auxiliar de la versión anterior; entra el historial de correcciones
  (escenario 5); sale la precondición del paciente registrado, que chocaba con el escenario 10.
- Versión 5 de la tanda (límite de 12 historias, 2026-10-21): salen los escenarios que miraban
  lo que hacen otras historias (historial con cambio de voluntad y con corrección, versiones
  anuladas y acceso desde la consulta del paciente); cada cambio lo prueba la historia que lo
  hace (HU-012 y HU-013). Entran la no oposición con y sin documento por separado, el historial
  con la versión registrada y la búsqueda incompleta. Así HU-011 solo depende de HU-010.
- Tablero, Ajustes por historia: filas con destino HU-011.

---

# HU-012 · Actualización de la oposición a la presunción legal de donación

**ID:** HU-012
**Título:** Actualización de la oposición a la presunción legal de donación
**Funcionalidad relacionada:** Oposición a la donación
**Descripción:**
> Como auxiliar de admisión
> Quiero registrar que el paciente cambió su manifestación sobre la donación, o corregir la fecha del documento si quedó mal
> Para que el sistema refleje su voluntad actual sin perder lo que había antes

**Valor de negocio:** Una manifestación de voluntad puede cambiar. Si el sistema muestra la antigua
como vigente, obra contra la voluntad del paciente.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-010
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Los datos son los del numeral 8 (hoja 14). Hay dos casos y el auxiliar dice cuál es:
  - **Cambio de voluntad:** el paciente manifiesta lo contrario de lo vigente, o firma un documento
    nuevo con la misma manifestación. Se crea una versión nueva y la anterior pasa a "Anterior". Si
    la nueva es "Sí", la fecha del documento nuevo es obligatoria; si es "No", se registra la fecha
    del documento si lo hay (inferencia del equipo, como en HU-010).
  - **Corrección:** solo la fecha del documento de la versión vigente, si quedó mal; no crea versión.
    Un "Sí" o "No" mal registrado no se corrige: se anula (HU-013) y se registra de nuevo.
- La fecha del documento, nueva o corregida, no puede ser posterior al día del registro ni anterior a
  la de la versión anterior (regla del equipo).
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora.
- RC-6. Al corregir un dato mal registrado "se agregará el nuevo dato con la fecha, hora, nombre e
  identificación de quien hizo la corrección" (artículo 10, parágrafo 2, de la Resolución 866). El
  dato anterior no se borra.
- RC-7. Cada dato se valida con los valores permitidos, el formato y la longitud de su numeral en el
  anexo.

**Precondiciones:**
- El auxiliar de admisión tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Cambio de "Sí" a "No"
- **Dado** que el paciente tenía registrado "Sí" y manifiesta que retira su oposición
- **Cuando** el auxiliar de admisión registra el cambio, con la fecha del documento si lo trae
- **Entonces** el sistema deja "No" como versión vigente, con el nombre y la identificación de quien registró el cambio, la fecha y la hora
- **Y** la versión anterior queda como "Anterior", con su fecha y con quién la registró
- **Y** la consulta (HU-011) muestra la versión anterior y la vigente, cada una con su estado, la fecha del documento, quién la registró y cuándo

### Escenario 2: Cambio de "No" a "Sí"
- **Dado** que el paciente tenía registrado "No" y firmó un documento en el que se opone
- **Cuando** el auxiliar de admisión registra el cambio con la fecha del documento nuevo
- **Entonces** el sistema deja "Sí" como versión vigente, con quién registró el cambio, la fecha y la hora
- **Y** la versión anterior queda como "Anterior", con su fecha y con quién la registró

### Escenario 3: Cambio a "Sí" sin fecha
- **Dado** que el auxiliar de admisión registra un cambio a "Sí" sin la fecha del documento nuevo
- **Cuando** confirma
- **Entonces** el sistema no lo acepta
- **Y** dice que falta la fecha del documento

### Escenario 4: Fecha no válida
- **Dado** que la fecha del documento nuevo es anterior a la de la versión vigente, o posterior al día del registro (regla del equipo)
- **Cuando** el auxiliar de admisión confirma el cambio
- **Entonces** el sistema lo rechaza
- **Y** dice por qué

### Escenario 5: Corregir la fecha del documento
- **Dado** que la fecha del documento de la versión vigente se registró mal
- **Cuando** el auxiliar de admisión la corrige e indica que es una corrección
- **Entonces** el sistema guarda la fecha nueva con la fecha, la hora, el nombre y la identificación de quien corrigió, sin crear otra versión
- **Y** la consulta (HU-011) muestra en el historial la fecha anterior y la nueva, con la fecha, la hora, el nombre y la identificación de quien corrigió

### Escenario 6: Manifestación mal registrada
- **Dado** que el auxiliar de admisión intenta corregir el "Sí" o el "No" de la versión vigente
- **Cuando** lo indica como corrección
- **Entonces** el sistema no lo permite
- **Y** explica que un valor mal registrado se anula y se registra de nuevo

### Escenario 7: Documento nuevo con la misma manifestación
- **Dado** que el paciente tenía registrado "Sí" y firmó otro documento de oposición
- **Cuando** el auxiliar de admisión lo registra como cambio, con la fecha nueva
- **Entonces** el sistema crea una versión nueva "Sí" con esa fecha
- **Y** la anterior queda como "Anterior"

### Escenario 8: Dejar vacía la manifestación o la fecha de un "Sí"
- **Dado** que el auxiliar de admisión deja vacía la manifestación, o la fecha de una versión "Sí"
- **Cuando** confirma
- **Entonces** el sistema no lo acepta y dice cuál falta
- **Y** conserva la versión vigente

### Escenario 9: Paciente sin manifestación vigente
- **Dado** que el paciente no tiene manifestación vigente
- **Cuando** el auxiliar de admisión intenta actualizarla
- **Entonces** el sistema no lo permite
- **Y** ofrece registrarla (HU-010)

### Escenario 10: Paciente que no existe
- **Dado** que no hay paciente con ese documento
- **Cuando** el auxiliar de admisión intenta actualizar su oposición
- **Entonces** el sistema dice que el paciente no existe

### Escenario 11: Actualización cancelada
- **Dado** que el auxiliar de admisión hizo cambios sin confirmar
- **Cuando** cancela
- **Entonces** el sistema conserva la versión vigente

### Escenario 12: Cargo sin permiso
- **Dado** que un médico tratante tiene sesión iniciada
- **Cuando** intenta actualizar una oposición
- **Entonces** el sistema no lo permite

### Escenario 13: Los datos del paciente no cambian
- **Dado** que el auxiliar de admisión actualiza la oposición de un paciente
- **Cuando** guarda el cambio
- **Entonces** los demás datos del paciente quedan intactos

**Dependencias:** HU-001, HU-010 y HU-011.
**Supuestos o asuntos pendientes:**
- Que la fecha con "No" se registre solo si hay documento es inferencia del equipo; cómo se retira
  una oposición no lo fija la Ley 1805 de 2016 (pregunta al docente).
- Las reglas de fechas de los escenarios 4 y 5 son del equipo.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-012 c1 (escenario de no oposición a oposición: escenario 2; autor del
  registro anterior en el escenario 2: escenarios 1 y 2) y c2 (evidencia de la nueva manifestación y
  fecha del documento: escenarios 1 a 3; fecha, hora, nombre e identificación de quien corrige:
  escenario 5; distinguir corrección de cambio de voluntad y mantener versiones: reglas y escenarios
  1, 5 y 6).
- Escenarios antiguos: el 1 es el 1; el 2 va en el 1 y el 2; el 3 (fecha de la nueva manifestación)
  va en los escenarios 1 a 3; el 4 es el 9; el 5 es el 11; el 6 (atribución) va en el 1 y el 2; el 7
  (sin sesión) lo cubre HU-001 escenario 4; el 8 es el 8; el 9 es el 13; el 10 es el 10.
- Versión 2: sale la precondición de manifestación vigente, que chocaba con el escenario 9; retirar
  una oposición ya no exige documento; la corrección se limita a la fecha; los datos del corrector van
  con el dato nuevo (RC-6); el numeral 8 y la hoja 14 en la regla (RC-7).
- Versión 5 de la tanda (límite de 12 historias, 2026-10-21): el escenario 1 prueba cómo muestra la consulta (HU-011) la versión anterior, y el 5 la corrección; el 6 ya no remite a HU-013. El orden queda registrar,
  consultar, actualizar y anular: cada historia solo remite a las anteriores.
- Tablero, Ajustes por historia: filas con destino HU-012.

---

# HU-013 · Anulación de un registro de oposición hecho por error

**ID:** HU-013
**Título:** Anulación de un registro de oposición hecho por error
**Funcionalidad relacionada:** Oposición a la donación
**Descripción:**
> Como auxiliar de admisión
> Quiero anular una manifestación que se registró por error, diciendo el motivo
> Para que no quede vigente una voluntad que el paciente nunca expresó

**Valor de negocio:** Una manifestación registrada por error es peor que ninguna, porque nadie
vuelve a preguntar si el sistema dice que ya está. Anular deja el rastro de qué pasó.

**Prioridad:** Media
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-010
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Anular es solo para registros hechos por error, por ejemplo en el paciente equivocado o con el
  valor contrario al que dijo el paciente. Si el paciente cambió de voluntad, se usa HU-012. El
  docente da por funcional anular una oposición registrada por error (HU-013, comentario 2).
- Anular no revoca la voluntad del paciente.
- Se pide confirmación y motivo. El registro queda como "Anulado" y se sigue viendo en el historial.
- Si se anula la versión vigente, vuelve a ser vigente la anterior más reciente que no esté anulada;
  si no hay, el paciente queda "Sin registro vigente". Una versión "Anterior" también se puede
  anular, y la vigente no cambia (regla del equipo).
- RC-4. Nada se borra (D-20).
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora.

**Precondiciones:**
- El auxiliar de admisión tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Anulación de un registro hecho por error
- **Dado** que una manifestación se registró por error
- **Cuando** el auxiliar de admisión la anula, confirma y escribe el motivo
- **Entonces** el sistema la deja como "Anulado"
- **Y** guarda el motivo, la fecha, la hora, el nombre y la identificación de quien anuló

### Escenario 2: Anulación sin motivo
- **Dado** que el auxiliar de admisión no escribió el motivo
- **Cuando** intenta anular
- **Entonces** el sistema no anula
- **Y** pide el motivo

### Escenario 3: Aviso antes de anular
- **Dado** que el auxiliar de admisión eligió la manifestación que quiere anular
- **Cuando** pide anularla
- **Entonces** el sistema le recuerda que anular es solo para errores y que un cambio de voluntad se registra como actualización
- **Y** no anula nada hasta que el auxiliar confirme

### Escenario 4: Anulación cancelada
- **Dado** que el auxiliar de admisión pidió anular
- **Cuando** cancela antes de confirmar
- **Entonces** la manifestación queda igual

### Escenario 5: Estado después de anular la única versión
- **Dado** que la manifestación vigente era "Se opone" y era la única
- **Cuando** el auxiliar de admisión confirma la anulación
- **Entonces** la situación del paciente pasa de "Se opone" a "Sin registro vigente"
- **Y** la consulta (HU-011) muestra "Sin registro vigente" y, en el historial, el registro anulado con su motivo, quién lo anuló y cuándo

### Escenario 6: Vuelve la versión anterior
- **Dado** que el paciente tiene versiones anteriores sin anular
- **Cuando** el auxiliar de admisión confirma la anulación de la versión vigente
- **Entonces** vuelve a ser vigente la anterior más reciente que no esté anulada

### Escenario 7: Anular una versión anterior
- **Dado** que una versión "Anterior" se registró por error
- **Cuando** el auxiliar de admisión la anula con su motivo
- **Entonces** el sistema la deja como "Anulado"
- **Y** la versión vigente no cambia

### Escenario 8: Registro después de anular
- **Dado** que se anuló la única manifestación del paciente y no le queda una vigente
- **Cuando** el auxiliar de admisión registra una nueva (HU-010)
- **Entonces** el sistema la acepta como vigente

### Escenario 9: Nada que anular
- **Dado** que el paciente no tiene manifestación registrada
- **Cuando** el auxiliar de admisión intenta anular
- **Entonces** el sistema dice que no hay nada que anular

### Escenario 10: Paciente que no existe
- **Dado** que no hay paciente con ese documento
- **Cuando** el auxiliar de admisión intenta anular su manifestación
- **Entonces** el sistema dice que el paciente no existe

### Escenario 11: Cargo sin permiso
- **Dado** que un médico tratante tiene sesión iniciada
- **Cuando** intenta anular una manifestación
- **Entonces** el sistema no lo permite

### Escenario 12: Los datos del paciente no cambian
- **Dado** que el auxiliar de admisión pidió anular una manifestación
- **Cuando** confirma la anulación
- **Entonces** los demás datos del paciente quedan intactos

**Dependencias:** HU-001, HU-010 y HU-011.
**Supuestos o asuntos pendientes:** D-20 (nada se borra). Las reglas de los escenarios 6 y 7 son del
equipo.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-013 c1 (mensajes del diálogo de confirmación, estado posterior y
  registro histórico: escenarios 3 y 5, y el mockup) y c2 (motivo, permisos, fecha, hora y autor
  identificado, estado anulado y consulta: escenarios 1, 2, 5 y 11; anular un registro erróneo no
  revoca la voluntad: reglas y escenario 3).
- Escenarios antiguos: el 1 (eliminación) pasa a anulación en el 1; el 2 (no elimina nada hasta que
  se confirme) va en el 3; el 3 es el 4; el 4 (rastro) va en el 1 y el 5; el 5 es el 9; el 6
  (consulta después) es el 5, que ahora prueba el cambio de estado; el 7 es el 8; el 8 (sin sesión)
  lo cubre HU-001 escenario 4; el 9 es el 12; el 10 es el 10.
- El título cambia de "Eliminación" a "Anulación" porque nada se borra (D-20).
- Versión 5 de la tanda (límite de 12 historias, 2026-10-21): el escenario 5 prueba cómo muestra la consulta (HU-011) la versión anulada; el 3 ya no remite a HU-012. El orden queda registrar,
  consultar, actualizar y anular: cada historia solo remite a las anteriores.
- Tablero, Ajustes por historia: filas con destino HU-013.

---

# HU-014 · Registro del documento de voluntad anticipada

**ID:** HU-014
**Título:** Registro del documento de voluntad anticipada
**Funcionalidad relacionada:** Voluntad anticipada
**Descripción:**
> Como auxiliar de admisión
> Quiero registrar si el paciente tiene documento de voluntad anticipada, cuándo lo firmó o lo cambió y dónde está
> Para que quien lo atienda sepa que existe y dónde pedirlo

**Valor de negocio:** Un documento de voluntad anticipada solo sirve si quien atiende sabe que existe
y dónde encontrarlo.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Los datos son los tres del numeral 9 del anexo (hoja 14): si tiene documento ("Sí" o "No"); la
  fecha de suscripción, modificación, sustitución o revocación del documento; y el código del
  prestador donde está el documento, de 12 caracteres (D-05).
- Con la fecha se guarda qué evento fue: suscripción, modificación, sustitución o revocación
  (inferencia del equipo: el numeral 9 pone los cuatro en un solo dato de fecha).
- Si tiene documento, son obligatorios la fecha, el evento y el prestador; si no tiene, no se piden.
  El docente pide esa obligatoriedad condicional (HU-014, comentario 1). Si lo revocó antes de
  llegar, se registra "No" con la fecha y el evento "revocación".
- La fecha no puede ser posterior al día del registro (regla del equipo).
- El registro parte sin respuesta marcada: el sistema no guarda "No tiene documento" por omisión. Las
  situaciones son "Tiene documento", "No tiene documento", "Revocado" y "Sin registro"; "Sin
  registro" no es "No tiene documento".
- El sistema guarda la constancia, no el documento.
- Solo se registra la voluntad anticipada de un paciente que tiene registro (HU-004).
- La voluntad anticipada se guarda aparte de los datos del paciente [2.1].
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora.
- RC-7. Cada dato se valida con los valores permitidos, el formato y la longitud de su numeral en el
  anexo.

**Precondiciones:**
- El auxiliar de admisión tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: El paciente tiene documento
- **Dado** que el paciente firmó un documento de voluntad anticipada
- **Cuando** el auxiliar de admisión registra "Sí", la fecha de firma, el evento "suscripción" y el código del prestador donde está
- **Entonces** el sistema guarda el registro como vigente

### Escenario 2: Documento que ya cambió
- **Dado** que el paciente modificó o sustituyó su documento antes de llegar
- **Cuando** el auxiliar de admisión registra "Sí" con la fecha y el evento del último cambio, y el prestador
- **Entonces** el sistema lo guarda como vigente, con ese evento

### Escenario 3: Documento revocado antes del registro
- **Dado** que el paciente revocó su documento antes de llegar
- **Cuando** el auxiliar de admisión registra "No" con la fecha de revocación y el evento "revocación"
- **Entonces** el sistema guarda la situación "Revocado"

### Escenario 4: El paciente no tiene documento
- **Dado** que el paciente dice que nunca ha tenido documento
- **Cuando** el auxiliar de admisión registra "No"
- **Entonces** el sistema guarda "No tiene documento"
- **Y** no exige la fecha ni el prestador

### Escenario 5: Tiene documento pero falta un dato
- **Dado** que el auxiliar de admisión registra "Sí" sin la fecha, sin el evento o sin el código del prestador
- **Cuando** intenta guardar
- **Entonces** el sistema no guarda
- **Y** dice cuál falta

### Escenario 6: Fecha no válida
- **Dado** que la fecha no cumple el formato del numeral 9, o es posterior al día del registro (regla del equipo)
- **Cuando** el auxiliar de admisión intenta guardar
- **Entonces** el sistema la rechaza
- **Y** dice por qué

### Escenario 7: Código del prestador que no cumple su numeral
- **Dado** que el código del prestador no tiene 12 caracteres
- **Cuando** el auxiliar de admisión intenta guardar
- **Entonces** el sistema lo rechaza
- **Y** dice que el código debe tener 12 caracteres

### Escenario 8: Sin respuesta por omisión
- **Dado** que nunca se registró nada sobre la voluntad anticipada del paciente
- **Cuando** el auxiliar de admisión abre el registro
- **Entonces** el formulario no trae ninguna respuesta marcada
- **Y** el sistema no guarda "No tiene documento" si el auxiliar no lo elige

### Escenario 9: Paciente que ya tiene registro
- **Dado** que el paciente ya tiene un registro vigente
- **Cuando** el auxiliar de admisión intenta registrar otro
- **Entonces** el sistema no lo registra
- **Y** ofrece actualizarlo (HU-016)

### Escenario 10: Paciente que no existe
- **Dado** que no hay paciente con ese documento
- **Cuando** el auxiliar de admisión intenta registrar su voluntad anticipada
- **Entonces** el sistema no lo permite
- **Y** avisa que el paciente debe registrarse primero (HU-004)

### Escenario 11: Registro a nombre de quien lo hizo
- **Dado** que el auxiliar de admisión tiene sesión iniciada
- **Cuando** registra la voluntad anticipada de un paciente
- **Entonces** el sistema guarda con ella el nombre y la identificación del auxiliar, la fecha y la hora del registro

### Escenario 12: Los datos del paciente no cambian
- **Dado** que el paciente tiene sus datos registrados
- **Cuando** el auxiliar de admisión registra su voluntad anticipada
- **Entonces** sus datos quedan iguales
- **Y** la voluntad anticipada se consulta en su propia sección (HU-015)

### Escenario 13: Registro cancelado
- **Dado** que el auxiliar de admisión empezó el registro
- **Cuando** cancela antes de guardar
- **Entonces** el sistema no guarda nada

### Escenario 14: Cargo sin permiso
- **Dado** que un médico tratante tiene sesión iniciada
- **Cuando** intenta registrar una voluntad anticipada
- **Entonces** el sistema no lo permite

**Dependencias:** HU-001 y HU-004; HU-015 para el escenario 12.
**Supuestos o asuntos pendientes:** D-05 (sin catálogo de prestadores). Guardar el evento de la fecha
y registrar la revocación como "No" son inferencias del equipo. La fecha futura es regla del equipo.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-014 c1 (datos obligatorios y obligatoriedad condicional cuando no
  existe documento, antiguos escenarios 2 y 9: reglas y escenarios 4 y 5; validación de la fecha y
  del código del prestador: escenarios 6 y 7) y c2 (formato y código del custodio: escenario 7;
  distinguir documento, ausencia y desconocido: reglas y escenarios 1, 4 y 8; separación con
  resultado funcional: escenario 12).
- Escenarios antiguos: el 1 es el 1; el 2 es el 4; el 3 (fecha) y el 4 (prestador) van en el 1, y
  su validación en el 6 y el 7; el 5 es el 9; el 6 es el 10; el 7 es el 11; el 8 (sin sesión) lo
  cubre HU-001 escenario 4; el 9 es el 5; el 10 es el 12.
- Versión 2: entran el evento de la fecha y los escenarios 2 y 3; la revocación vuelve al dato de
  fecha, como dice la hoja 14; sale la precondición del paciente registrado, que chocaba con el
  escenario 10.
- Tablero, Ajustes por historia: filas con destino HU-014.

---

# HU-015 · Consulta del documento de voluntad anticipada

**ID:** HU-015
**Título:** Consulta del documento de voluntad anticipada
**Funcionalidad relacionada:** Voluntad anticipada
**Descripción:**
> Como auxiliar de admisión
> Quiero ver si el paciente tiene documento de voluntad anticipada vigente y dónde está
> Para informar a quien lo atiende sin suponer que no existe

**Valor de negocio:** Quien atiende necesita saber si hay un documento vigente, si fue revocado y
dónde está, y no confundir "no tiene" con "no se sabe".

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-014
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- La consulta muestra la situación vigente: "Tiene documento", "No tiene documento", "Revocado", "Sin
  registro" o "Sin registro vigente", con la fecha, el evento y el prestador cuando los hay (numeral
  9, hoja 14).
- El historial muestra cada versión con su estado ("Vigente", "Anterior" o "Anulado"), su evento
  (suscripción, modificación, sustitución o revocación), su fecha, su prestador, quién la registró y
  cuándo; las anuladas, con su motivo.
- El historial muestra también los cambios de prestador y las correcciones: el valor anterior y el
  nuevo, con la fecha, la hora, el nombre y la identificación de quien lo hizo (RC-6).
- "Sin registro" y "Sin registro vigente" no quieren decir que el documento no exista.
- Pueden consultar el auxiliar de admisión y el médico tratante (matriz de permisos).

**Precondiciones:**
- La persona tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Documento vigente
- **Dado** que el paciente tiene documento vigente
- **Cuando** el auxiliar de admisión consulta su voluntad anticipada
- **Entonces** el sistema muestra "Tiene documento", la fecha y el evento del último cambio, el código del prestador donde está, quién lo registró y cuándo

### Escenario 2: No tiene documento
- **Dado** que el paciente dijo que nunca ha tenido documento
- **Cuando** el auxiliar de admisión consulta su voluntad anticipada
- **Entonces** el sistema muestra "No tiene documento" y cuándo se registró esa respuesta

### Escenario 3: Sin registro
- **Dado** que nunca se registró nada sobre la voluntad anticipada del paciente
- **Cuando** el auxiliar de admisión consulta su voluntad anticipada
- **Entonces** el sistema muestra "Sin registro: no se ha preguntado o no se sabe si tiene documento"

### Escenario 4: Documento revocado
- **Dado** que el paciente revocó su documento
- **Cuando** el auxiliar de admisión consulta su voluntad anticipada
- **Entonces** el sistema muestra "Revocado" con la fecha de revocación
- **Y** el historial conserva el documento revocado con su fecha y su prestador

### Escenario 5: Historial de eventos
- **Dado** que el documento del paciente se firmó, se modificó y después se sustituyó
- **Cuando** el auxiliar de admisión consulta el historial
- **Entonces** el sistema muestra cada versión con su evento, su fecha, su prestador, quién la registró y cuándo

### Escenario 6: Historial con un cambio de prestador o una corrección
- **Dado** que el documento cambió de prestador, o la fecha de la versión vigente se corrigió
- **Cuando** el auxiliar de admisión consulta el historial
- **Entonces** el sistema muestra el valor anterior y el nuevo, con la fecha, la hora, el nombre y la identificación de quien lo hizo

### Escenario 7: Todas las versiones anuladas
- **Dado** que el único registro de voluntad anticipada del paciente se anuló porque se había hecho por error
- **Cuando** el auxiliar de admisión consulta su voluntad anticipada
- **Entonces** el sistema muestra "Sin registro vigente"
- **Y** el historial muestra el registro anulado con su motivo

### Escenario 8: Acceso desde la consulta del paciente
- **Dado** que el auxiliar de admisión está consultando a un paciente (HU-005)
- **Cuando** abre la sección de voluntad anticipada
- **Entonces** ve la situación vigente de ese paciente

### Escenario 9: Consulta del médico tratante
- **Dado** que un médico tratante tiene sesión iniciada
- **Cuando** consulta la voluntad anticipada de un paciente
- **Entonces** ve lo mismo
- **Y** no tiene opciones para cambiarla

### Escenario 10: Cargo sin permiso
- **Dado** que un profesional de estadística tiene sesión iniciada
- **Cuando** intenta consultar la voluntad anticipada de un paciente
- **Entonces** el sistema no lo permite

### Escenario 11: Paciente que no existe
- **Dado** que no hay paciente con ese documento
- **Cuando** el auxiliar de admisión consulta su voluntad anticipada
- **Entonces** el sistema dice que el paciente no existe

### Escenario 12: La consulta no cambia nada
- **Dado** que el auxiliar de admisión consultó la voluntad anticipada de un paciente
- **Cuando** cierra la consulta
- **Entonces** el registro queda igual que antes

**Dependencias:** HU-001, HU-004 y HU-014; HU-005 para el escenario 8; HU-016 y HU-017 para los
escenarios 4 a 7.
**Supuestos o asuntos pendientes:** Ninguno.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-015 c1 (consultar "no tiene documento" distinto de "no se ha
  preguntado" y de "registro eliminado": escenarios 2, 3 y 7) y c2 (vigente, modificación,
  sustitución y revocación con fechas y custodio: escenarios 1, 4 y 5; conservar el historial:
  escenarios 5 a 7; la falta de dato no prueba que el documento no exista: regla y escenario 3;
  permisos: escenarios 9 y 10).
- Escenarios antiguos: el 1 y el 3 van en el 1; el 2 es el 3; el 4 (quién registró) va en el 1; el
  5 es el 5; el 6 es el 11; el 7 (sin sesión) lo cubre HU-001 escenario 4; el 8 es el 12; el 9 es el
  8; el 10 es el 7.
- Versión 2: entran el evento, el historial de cambios de prestador y correcciones (escenario 6) y
  el prestador del documento revocado (escenario 4); sale la precondición del paciente registrado,
  que chocaba con el escenario 11.
- Tablero, Ajustes por historia: filas con destino HU-015.

---

# HU-016 · Actualización del documento de voluntad anticipada: modificación, sustitución y revocación

**ID:** HU-016
**Título:** Actualización del documento de voluntad anticipada: modificación, sustitución y revocación
**Funcionalidad relacionada:** Voluntad anticipada
**Descripción:**
> Como auxiliar de admisión
> Quiero registrar que el paciente modificó, sustituyó o revocó su documento de voluntad anticipada, o que el documento cambió de prestador, y corregir un dato mal registrado
> Para que el sistema muestre el documento que está vigente y no uno que ya cambió

**Valor de negocio:** Un documento de voluntad anticipada puede modificarse, sustituirse o
revocarse. Si el sistema muestra el anterior como vigente, informa mal a quien deba aplicarlo.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-014
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Los eventos son los del numeral 9 (hoja 14). Cada uno crea una versión nueva y la anterior queda
  como "Anterior". Datos obligatorios de cada evento:
  - Modificación: la fecha. El prestador sigue siendo el mismo, salvo que se registre otro.
  - Sustitución: la fecha y el prestador donde está el documento nuevo.
  - Revocación: la fecha. La versión queda "No", con el evento "revocación", y se muestra "Revocado"
    (inferencia del equipo).
  - Suscripción de un documento nuevo, cuando el paciente tenía "No tiene documento" o "Revocado":
    la fecha y el prestador.
- El cambio de prestador no es un evento del numeral 9: guarda el código nuevo, de 12 caracteres, con
  la fecha y la hora en que se registra (RC-5), y no pide fecha de documento.
- La fecha de un evento no puede ser anterior a la del evento vigente ni posterior al día del
  registro (regla del equipo).
- Corregir es solo para la fecha o el código del prestador mal registrados, y no crea un evento
  nuevo. Un "Sí" o "No" mal registrado no se corrige: se anula (HU-017) y se registra de nuevo.
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora.
- RC-6. Al corregir un dato mal registrado "se agregará el nuevo dato con la fecha, hora, nombre e
  identificación de quien hizo la corrección" (artículo 10, parágrafo 2, de la Resolución 866). El
  dato anterior no se borra.
- RC-7. Cada dato se valida con los valores permitidos, el formato y la longitud de su numeral en el
  anexo.

**Precondiciones:**
- El auxiliar de admisión tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Modificación del documento
- **Dado** que el paciente modificó su documento
- **Cuando** el auxiliar de admisión registra la modificación con su fecha
- **Entonces** el sistema crea una versión nueva y la deja vigente, con el nombre y la identificación de quien la registró, la fecha y la hora
- **Y** la anterior queda como "Anterior", con su fecha y con quién la registró

### Escenario 2: Sustitución del documento
- **Dado** que el paciente firmó un documento nuevo que reemplaza al anterior
- **Cuando** el auxiliar de admisión registra la sustitución con su fecha y el prestador donde está el nuevo
- **Entonces** el sistema deja vigente la versión nueva, con quién la registró
- **Y** la anterior queda como "Anterior", con su fecha y con quién la registró

### Escenario 3: Revocación del documento
- **Dado** que el paciente revocó su documento
- **Cuando** el auxiliar de admisión registra la revocación con su fecha
- **Entonces** la versión vigente queda "No", con el evento "revocación", y se muestra "Revocado"
- **Y** la versión anterior queda como "Anterior"

### Escenario 4: Documento nuevo después de no tener o de revocar
- **Dado** que el paciente tenía "No tiene documento" o "Revocado" y firmó un documento nuevo
- **Cuando** el auxiliar de admisión registra la suscripción con su fecha y el prestador
- **Entonces** el sistema deja vigente "Tiene documento"

### Escenario 5: Cambio del prestador donde está el documento
- **Dado** que el documento pasó a otro prestador
- **Cuando** el auxiliar de admisión registra el código del nuevo prestador
- **Entonces** el sistema guarda el código nuevo con la fecha y la hora del registro y quién lo hizo
- **Y** el anterior queda en el historial (HU-015)

### Escenario 6: Código del prestador que no cumple su numeral
- **Dado** que el código del prestador nuevo no tiene 12 caracteres
- **Cuando** el auxiliar de admisión confirma
- **Entonces** el sistema lo rechaza
- **Y** dice que el código debe tener 12 caracteres

### Escenario 7: Falta un dato del evento
- **Dado** que el auxiliar de admisión registra una sustitución sin prestador, o cualquier evento sin fecha
- **Cuando** confirma
- **Entonces** el sistema no lo acepta
- **Y** dice cuál dato falta

### Escenario 8: Fecha no válida
- **Dado** que la fecha del evento nuevo es anterior a la del evento vigente, o posterior al día del registro (regla del equipo)
- **Cuando** el auxiliar de admisión confirma
- **Entonces** el sistema lo rechaza
- **Y** dice por qué

### Escenario 9: Corregir la fecha o el prestador
- **Dado** que la fecha de la versión vigente se registró mal
- **Cuando** el auxiliar de admisión la corrige e indica que es una corrección
- **Entonces** el sistema guarda la fecha nueva con la fecha, la hora, el nombre y la identificación de quien corrigió, sin crear otro evento
- **Y** conserva la anterior en el historial

### Escenario 10: "Sí" o "No" mal registrado
- **Dado** que el auxiliar de admisión intenta corregir si el paciente tiene documento
- **Cuando** lo indica como corrección
- **Entonces** el sistema no lo permite
- **Y** explica que un valor mal registrado se anula (HU-017) y se registra de nuevo

### Escenario 11: Paciente sin registro previo
- **Dado** que el paciente no tiene registro de voluntad anticipada
- **Cuando** el auxiliar de admisión intenta actualizarla
- **Entonces** el sistema no lo permite
- **Y** ofrece registrarlo (HU-014)

### Escenario 12: Actualización cancelada
- **Dado** que el auxiliar de admisión hizo cambios sin confirmar
- **Cuando** cancela
- **Entonces** el sistema conserva la versión vigente

### Escenario 13: Cargo sin permiso
- **Dado** que un médico tratante tiene sesión iniciada
- **Cuando** intenta actualizar una voluntad anticipada
- **Entonces** el sistema no lo permite

### Escenario 14: Los datos del paciente no cambian
- **Dado** que el auxiliar de admisión actualiza la voluntad anticipada de un paciente
- **Cuando** guarda el cambio
- **Entonces** los demás datos del paciente quedan intactos

**Dependencias:** HU-001 y HU-014; HU-015 para el escenario 5 y HU-017 para el escenario 10.
**Supuestos o asuntos pendientes:** Las reglas de fechas del escenario 8 son del equipo. Que la
revocación quede como "No" es inferencia del equipo.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-016 c1 (escenario y resultado para el documento revocado: escenario
  3; campos obligatorios del antiguo escenario 9: reglas y escenario 7; autor del registro anterior
  en el escenario 2: escenarios 1 y 2) y c2 (modificación, sustitución y revocación con fechas,
  custodio y versiones: reglas y escenarios 1 a 5; fecha, hora, nombre e identificación del
  corrector: escenario 9; "documento nuevo no cubre todos los eventos": un escenario por evento).
- Escenarios antiguos: el 1 es el 2; el 2 (el anterior no se pierde) va en los escenarios 1 a 3; el
  3 (fecha nueva) va en los escenarios 1 a 4 y en el 7; el 4 es el 5; el 5 es el 11; el 6 es el 12;
  el 7 (atribución) va en el 1 y el 2; el 8 (sin sesión) lo cubre HU-001 escenario 4; el 9 es el 7;
  el 10 es el 14.
- Versión 2: datos obligatorios por evento; validación del prestador nuevo; documento nuevo después
  de revocar; la corrección se limita a la fecha y al prestador; los datos del corrector van con el
  dato nuevo (RC-6); sale la precondición que chocaba con el escenario 11.
- Cierra el pendiente 2 de la tanda 3 antigua: revocar es una actualización.
- Tablero, Ajustes por historia: filas con destino HU-016.

---

# HU-017 · Anulación de un registro de voluntad anticipada hecho por error

**ID:** HU-017
**Título:** Anulación de un registro de voluntad anticipada hecho por error
**Funcionalidad relacionada:** Voluntad anticipada
**Descripción:**
> Como auxiliar de admisión
> Quiero anular un registro de voluntad anticipada que se hizo por error, diciendo el motivo
> Para que nadie suponga que existe un documento que el paciente nunca firmó

**Valor de negocio:** Un registro falso hace que el médico busque un documento que no existe y
puede demorar una decisión. Anular deja el rastro de qué pasó.

**Prioridad:** Media
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-014
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Anular es solo para registros hechos por error, por ejemplo en el paciente equivocado. Si el
  paciente revocó su documento, se usa HU-016. El docente da por funcional anular un registro
  documental erróneo (HU-017, comentario 2).
- Se pide confirmación y motivo. El registro queda como "Anulado" y se sigue viendo en el historial.
- Si se anula la versión vigente, vuelve a ser vigente la anterior más reciente que no esté anulada;
  si no hay, el paciente queda "Sin registro vigente". Una versión "Anterior" también se puede
  anular, y la vigente no cambia (regla del equipo).
- Que no haya registro vigente no prueba que el documento no exista.
- RC-4. Nada se borra (D-20).
- RC-5. Toda acción queda atribuida a quien la hizo, con fecha y hora.

**Precondiciones:**
- El auxiliar de admisión tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Anulación de un registro hecho por error
- **Dado** que un registro de voluntad anticipada se hizo por error
- **Cuando** el auxiliar de admisión lo anula, confirma y escribe el motivo
- **Entonces** el sistema lo deja como "Anulado"
- **Y** guarda el motivo, la fecha, la hora, el nombre y la identificación de quien anuló

### Escenario 2: Anulación sin motivo
- **Dado** que el auxiliar de admisión no escribió el motivo
- **Cuando** intenta anular
- **Entonces** el sistema no anula
- **Y** pide el motivo

### Escenario 3: Aviso antes de anular
- **Dado** que el auxiliar de admisión eligió el registro que quiere anular
- **Cuando** pide anularlo
- **Entonces** el sistema le recuerda que anular es solo para errores y que una revocación se registra como actualización (HU-016)
- **Y** no anula nada hasta que el auxiliar confirme

### Escenario 4: Anulación cancelada
- **Dado** que el auxiliar de admisión pidió anular
- **Cuando** cancela antes de confirmar
- **Entonces** el registro queda igual

### Escenario 5: Estado después de anular la única versión
- **Dado** que la situación vigente era "Tiene documento" y era la única versión
- **Cuando** el auxiliar de admisión confirma la anulación
- **Entonces** la situación del paciente pasa de "Tiene documento" a "Sin registro vigente"
- **Y** el registro anulado queda en el historial (HU-015)

### Escenario 6: Vuelve la versión anterior
- **Dado** que el paciente tiene versiones anteriores sin anular
- **Cuando** el auxiliar de admisión confirma la anulación de la versión vigente
- **Entonces** vuelve a ser vigente la anterior más reciente que no esté anulada

### Escenario 7: Anular una versión anterior
- **Dado** que una versión "Anterior" se registró por error
- **Cuando** el auxiliar de admisión la anula con su motivo
- **Entonces** el sistema la deja como "Anulado"
- **Y** la versión vigente no cambia

### Escenario 8: Registro después de anular
- **Dado** que se anuló el único registro del paciente y no le queda uno vigente
- **Cuando** el auxiliar de admisión registra uno nuevo (HU-014)
- **Entonces** el sistema lo acepta como vigente

### Escenario 9: Nada que anular
- **Dado** que el paciente no tiene registro de voluntad anticipada
- **Cuando** el auxiliar de admisión intenta anular
- **Entonces** el sistema dice que no hay nada que anular

### Escenario 10: Paciente que no existe
- **Dado** que no hay paciente con ese documento
- **Cuando** el auxiliar de admisión intenta anular su registro de voluntad anticipada
- **Entonces** el sistema dice que el paciente no existe

### Escenario 11: Cargo sin permiso
- **Dado** que un médico tratante tiene sesión iniciada
- **Cuando** intenta anular un registro de voluntad anticipada
- **Entonces** el sistema no lo permite

### Escenario 12: Los datos del paciente no cambian
- **Dado** que el auxiliar de admisión pidió anular un registro de voluntad anticipada
- **Cuando** confirma la anulación
- **Entonces** los demás datos del paciente quedan intactos

**Dependencias:** HU-001 y HU-014; HU-015 para el escenario 5.
**Supuestos o asuntos pendientes:** D-20 (nada se borra). Las reglas de los escenarios 6 y 7 son del
equipo.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-017 c1 (diálogo de eliminación, estado sin registro vigente, cómo se
  conserva y consulta el historial: escenarios 3 y 5, y el mockup) y c2 (distinguir anulación por
  error de revocación: reglas y escenario 3; evento, motivo, fecha y autor identificado: escenario
  1; mantener historial y consulta correcta: escenario 5; la falta de registro vigente no prueba que
  el documento no exista: regla).
- Escenarios antiguos: el 1 pasa a anulación en el 1; el 2 (no elimina nada hasta que se confirme)
  va en el 3; el 3 es el 4; el 4 va en el 1 y el 5; el 5 es el 9; el 6 (consulta después) es el 5,
  que ahora prueba el cambio de estado; el 7 es el 8; el 8 (sin sesión) lo cubre HU-001 escenario 4;
  el 9 es el 12; el 10 es el 10.
- El título cambia de "Eliminación" a "Anulación" porque nada se borra (D-20). El valor de negocio
  nombra el riesgo concreto: el sistema guarda la constancia y no el documento, así que el daño es
  buscar un documento que no existe.
- Tablero, Ajustes por historia: filas con destino HU-017.

---

## Registro

| Fecha | Versión | Cambio |
|---|---|---|
| 2026-10-20 | 1 | Primera escritura: HU-010 a HU-017 editadas con los ajustes del tablero y los comentarios del docente; HU-013 y HU-017 pasan de eliminación a anulación |
| 2026-10-20 | 2 | Correcciones de la revisión independiente: salen las precondiciones que chocaban con escenarios; vuelven los escenarios de paciente que no existe; inferencias marcadas en las tarjetas; retirar una oposición ya no exige documento; la corrección se limita a la fecha y al prestador, y un Sí o No mal registrado se anula; datos del corrector con el dato nuevo (RC-6); historial de correcciones y de cambios de prestador; evento de la fecha y revocación como "No"; datos obligatorios por evento; anular una versión anterior; etiquetas únicas por estructura; escenarios de registro y anulación que prueban la transición y no repiten la consulta |
| 2026-10-20 | 3 | El cargo de los catálogos pasa a profesional de estadística (D-02) en los escenarios de cargo sin permiso de HU-011 y HU-015 |
| 2026-10-20 | 4 | Revisión final cruzada (`revisiones/revision-final-cruzada.md`, hallazgo B11, que el revisor señaló en HU-004 y HU-008 y aquí se aplica igual): cada Cuando es una acción del auxiliar de admisión, no del sistema ni impersonal; los escenarios de registro a nombre de quien lo hizo y de aviso antes de anular se reordenan para eso |
| 2026-10-20 | 4 (verificación) | Verificación de la aplicación (`revisiones/verificacion-aplicacion-final.md`): los escenarios de la consulta que no cambia nada (HU-011 y HU-015) con el auxiliar en el Dado y el Cuando; "el nombre y la identificación del auxiliar" en HU-010 y HU-014; "el paciente tiene versiones anteriores" en HU-013 y HU-017; el Dado del escenario 12 de HU-013 y HU-017 ya no hace lo que hace el Cuando |
| 2026-10-21 | 5 | Límite de 12 historias: la oposición queda en orden registrar, consultar, actualizar y anular (HU-010 a HU-013), y cada historia solo remite a las anteriores. HU-011 pierde los escenarios que miraban lo que hacen HU-012, HU-013 y HU-005, y gana la no oposición con y sin documento, el historial con la versión registrada y la búsqueda incompleta (sigue con 10). La voluntad anticipada (HU-014 a HU-017) mantiene sus remisiones cruzadas: no entra en el plan de 12 |
