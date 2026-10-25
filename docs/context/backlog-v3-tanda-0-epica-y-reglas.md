# Backlog IPS Salud y Vida, versión 3. Tanda 0: épica, reglas comunes y plantilla

Versión 5, 2026-10-21. Borrador para revisión del equipo. Reemplaza las secciones de alcance,
roles, matriz de permisos y reglas comunes de las tandas anteriores.

Fuentes: guía del docente "Proyecto de Clase, IPS Salud y Vida" (los corchetes remiten a su
numeral), anexo técnico de la Resolución 866 de 2021 (se cita como "numeral N del anexo"), el
articulado de esa resolución (normograma de la Superintendencia Nacional de Salud), el Prompt 2 del
docente para historias de usuario y sus comentarios en Trello del 10 de octubre.

## 1. Épica

**Nombre:** Registro interoperable del paciente y de su contacto con el servicio de salud.

**Contexto de negocio.** La IPS Salud y Vida necesita registrar a sus pacientes y sus atenciones
con los datos que fija la Resolución 866 de 2021, para que la información se pueda intercambiar con
otros prestadores. Hoy no tiene un sistema que lo haga.

**Objetivo.** Que el personal de la IPS registre, consulte y actualice los datos del paciente y de
cada atención con los valores, formatos y catálogos del anexo técnico [2].

**Valor esperado.** El paciente no repite sus datos en cada atención; el médico ve lo que ya se
hizo antes de decidir; los datos salen listos para intercambiarse porque usan los catálogos
oficiales.

**Actores.**

| Cargo | Qué hace en el sistema |
|---|---|
| Auxiliar de admisión | Registra, consulta, actualiza y corrige los datos del paciente; registra, consulta, actualiza, corrige y anula su oposición a la donación y su documento de voluntad anticipada |
| Médico tratante | Registra, consulta, corrige y anula las atenciones; consulta los datos del paciente, su oposición y su voluntad anticipada sin cambiarlos |
| Profesional de estadística | Carga los catálogos desde el archivo oficial y los mantiene iguales a la fuente: crea y cambia valores solo por cambio oficial, inactiva y reactiva. No ve datos de pacientes ni de atenciones: solo cuántos registros usan un valor. El docente pidió "un cargo funcional real de la IPS". Se propone este porque se supone que el área de estadística es la que reporta los datos de la IPS con las tablas oficiales; no hay fuente que lo diga (decisión D-02, propuesta) |

**Alcance.** Primera fase [Contexto del desarrollo]: módulo de gestión de pacientes y módulo de
contacto con el servicio de salud, con el inicio de sesión básico [2.4] y los catálogos que exige
[2.3]. Que el primer módulo corresponda a los numerales 1 a 15 del anexo y el segundo a los 16 a 23
es una inferencia del equipo: la guía no nombra numerales (decisión D-16, pregunta abierta al
docente).

**Fuera de alcance.**

- Registro de usuarios y recuperación de cuenta [2.4]. Los usuarios y sus cargos los deja creados
  el equipo técnico al instalar el sistema (supuesto).
- Los numerales 24 a 49 del anexo (tecnologías en salud y resultados), hasta que el docente
  confirme el alcance (D-16).

**Dependencias y restricciones.**

- Las historias de catálogo y el registro del paciente (HU-004) no dependen del inicio de sesión
  (HU-001), porque no va en la primera entrega (indicación del docente en clase). Sus permisos y
  quién hizo cada cambio los prueba HU-001 (escenarios 9 y 14). Las demás historias sí dependen de
  HU-001.
- En la oposición (HU-010 a HU-013), cada historia solo remite a las anteriores: registrar,
  consultar, actualizar y anular. El cambio que hace una historia lo prueba esa misma historia,
  mirando la consulta que ya existe. La voluntad anticipada (HU-014 a HU-017) y la atención
  (HU-007 a HU-009) todavía tienen escenarios que remiten a historias posteriores; se ordenan
  igual cuando entren en un plan de desarrollo.
- Registrar un paciente depende de que los catálogos del paciente tengan valores activos; registrar
  una atención depende de que el paciente exista y de que los catálogos de la atención tengan
  valores activos. Si falta un catálogo, el registro lo avisa (HU-004 y HU-007).
- Los catálogos dependen de los archivos oficiales: la norma ISO 3166-1 para países, la CIUO-88
  A.C. del DANE para ocupaciones, la DIVIPOLA del DANE para municipios, la tabla CIE-10 de SISPRO
  para diagnósticos, el listado de enfermedades huérfanas del Ministerio de Salud, las listas del
  propio anexo y los catálogos del Ministerio para los demás (decisiones D-09, D-14 y D-21).
- La guía pide que la estimación se haga en clase o con video [2.5] y que cada historia tenga su
  mockup (comentarios del docente en las 30 tarjetas).

## 2. Matriz de permisos

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

## 3. Reglas comunes

El docente pidió "Enlazar o incluir" las reglas comunes. Las historias de catálogo (HU-018, HU-022,
HU-026, HU-030 y HU-034 a HU-042) comparten las mismas; para no repetirlas en trece tarjetas, en
Trello las enlazan a una tarjeta "Reglas comunes" que las trae completas. Las demás historias copian
las pocas que aplican.

- **RC-1.** Todo dato con más de 5 opciones tiene su propio catálogo con su CRUD [2.3]. Propuestas
  del equipo: una historia por catálogo (D-17) y eliminar es inactivar (D-20). El CIE-10 tiene
  además la historia del médico que busca y elige un diagnóstico (HU-031), porque su actor es otro.
- **RC-2.** Un dato que sale de un catálogo solo acepta valores activos de ese catálogo.
- **RC-3.** Los catálogos son los del anexo y los administra el Ministerio de Salud (artículos 6 y 8
  de la Resolución 866); los prestadores no los pueden modificar, reducir ni adicionar (artículo 7).
  La IPS carga cada catálogo desde su archivo oficial, con su versión, y crea o actualiza un valor
  solo cuando cambia la fuente oficial (artículo 12).
- **RC-4.** Nada se borra. Un valor de catálogo se inactiva con su motivo (RC-10), la fecha, la
  hora y quién lo hizo; deja de ofrecerse para registros nuevos y los registros que ya lo usan lo
  conservan (D-20).
- **RC-5.** Toda acción queda atribuida a quien la hizo, con fecha y hora. La fecha y la hora las
  guarda cada historia; quién lo hizo lo agrega el inicio de sesión (HU-001, escenario 9). Lo que se
  registre antes de tenerlo queda sin autor.
- **RC-6.** Al corregir un dato mal registrado "se agregará el nuevo dato con la fecha, hora, nombre
  e identificación de quien hizo la corrección" (artículo 10, parágrafo 2). El dato anterior no se
  borra: se agrega el nuevo, y el docente pide mostrar el anterior y el nuevo (comentario de HU-006).
- **RC-7.** Cada dato se valida con los valores permitidos, el formato y la longitud de su numeral
  en el anexo. La tarjeta nombra el numeral y la hoja; no copia el formato.
- **RC-8. Carga desde el archivo oficial.** El profesional de estadística descarga el archivo
  oficial y lo sube en CSV (UTF-8, separado por punto y coma). Escribe la fuente, la dirección y la
  fecha de descarga, y la versión y la fecha de publicación que trae la fuente; si la fuente no trae
  fecha de publicación, vale la de descarga. Ninguna de las dos puede ser futura. La primera fila
  trae los nombres de las columnas: el sistema busca las que lista cada historia, sin distinguir
  mayúsculas ni tildes, e ignora las demás. Si el archivo oficial viene en Excel, el profesional lo
  guarda como "CSV UTF-8" con las columnas de código en formato de texto, para que no pierdan los
  ceros; si los nombres de las columnas son otros, cambia solo la fila de encabezado, nunca los datos
  (supuesto del equipo). El código es la clave: no puede haber dos iguales, y
  conserva sus ceros iniciales: 05001 (D-18). El sistema rechaza el archivo completo si le falta una
  columna; rechaza cada fila que no cumple su numeral, con el número de fila y el motivo, y las
  filas con un código repetido. Un código de solo dígitos más corto que su numeral se rechaza con el
  aviso "revise que el archivo no haya perdido los ceros iniciales". En los catálogos con niveles
  (ocupaciones y CIE-10), las filas de los niveles de arriba se validan con la regla de su historia,
  no con la longitud del numeral, y ese aviso no aplica. Muestra cuántas filas leyó y cuántas aceptó,
  y la lista de rechazadas se puede descargar. En la primera carga, el profesional guarda las
  aceptadas o cancela todo, salvo que su historia lo impida: las listas del anexo (la hoja completa)
  y las ocupaciones (cantidades por nivel).
- **RC-9. Versión nueva y recarga.** Si el catálogo ya tiene valores, el archivo es:
  - una versión nueva, si su fecha de publicación es posterior a la de la versión vigente. Antes de
    guardar, el sistema muestra los códigos nuevos, los que cambian de nombre, los inactivos que
    vuelven y los que ya no están, y avisa si la versión deshace un cambio oficial registrado después
    de su fecha. El profesional puede anotar qué código nuevo reemplaza a uno que ya no está. Al
    confirmar, y después de confirmar aparte la lista de los que se inactivan, el sistema crea,
    actualiza, reactiva con el motivo "Vuelve en la versión" e inactiva con "No está en la versión" o
    "Reemplazado por"; la versión nueva queda vigente (D-21);
  - una recarga de la versión vigente, si su fecha es la misma: el sistema solo crea los códigos que
    faltan, avisa los nombres distintos y no inactiva nada. Sirve para completar una primera carga que
    tuvo filas rechazadas;
  - un archivo rechazado, si su fecha es anterior o futura.

  En los dos primeros casos, el código de una fila rechazada no se crea, no se inactiva y conserva
  su nombre y su estado; sale en la lista de rechazadas, y como vino en el archivo, queda en esa
  versión (RC-10).
- **RC-10. Inactivar y reactivar.**
  - Motivos de inactivación: "Retirado por la fuente oficial", con la referencia oficial;
    "Reemplazado por", con el código nuevo y la referencia oficial; "No está en la versión", que lo
    pone el sistema (RC-9); "Creado por error", solo para un código que no está en la versión
    vigente.
  - Un código está en la versión vigente si vino en su archivo, aunque su fila se haya rechazado.
  - Reactivar pide motivo. Se reactiva un código que está en la versión vigente o que creó un cambio
    oficial posterior a esa versión. Un código creado por un cambio oficial que una versión posterior
    ya no trae no se reactiva por haberse creado así: necesita otra referencia oficial. Si se inactivó
    con una referencia oficial, se reactiva solo con otra referencia oficial o diciendo por qué esa
    referencia no lo retiraba.
  - Antes de inactivar, el sistema muestra cuántos registros usan el valor, incluidos los borradores
    y los anulados; el profesional de estadística ve la cantidad, no los registros.
- **RC-11. Nombre del momento del registro.** Un registro conserva el código y el nombre que tenía
  el valor el día en que se registró. Si después la fuente oficial cambia el nombre, los registros
  ya hechos no cambian, y su consulta muestra también el nombre vigente (D-25; HU-005 y HU-008).

## 4. Convenciones

- **Lenguaje simple.** Si una historia necesita muchas reglas de formato, no las copia: dice el
  numeral del anexo que las fija.
- **Un catálogo, una historia.** El Prompt 2 del docente dice "No dividas historias únicamente
  por: [...] Operaciones de base de datos". Las operaciones se separan donde el Prompt 2 pide
  separar porque hay "actores distintos con objetivos diferentes" o "ciclos de vida o estados
  distintos":
  - Oposición y voluntad anticipada: la guía las pide como "estructura separada (CRUD)" [2.1]; las
    registra el auxiliar y las consulta también el médico, y cambiar la voluntad del paciente no es
    lo mismo que anular un registro hecho por error.
  - Atención: registrar, consultar, corregir y anular van en historias distintas (HU-007, HU-008,
    HU-009 y HU-043). Corregir cambia un dato y deja rastro; anular cambia el estado de toda la
    atención.
  - Diagnósticos CIE-10: la carga y el mantenimiento son una historia, como en los demás catálogos
    (HU-030); buscar y elegir un diagnóstico es otra, porque la hace el médico (HU-031).
- **Numeración.** Se conservan los ID de las tarjetas que siguen, para no perder los comentarios del
  docente que cuelgan de cada una en Trello. Las fusionadas conservan el ID más bajo y las nuevas
  empiezan en HU-034, así que la numeración tiene huecos (HU-002, HU-003, HU-019 a HU-021, HU-023 a
  HU-025, HU-027 a HU-029, HU-032 y HU-033, archivadas con una nota).
- **Estimación:** "Pendiente de estimación en clase". El Prompt 2 la inicia en 0; el docente pidió
  completarla o indicar que está pendiente, y la guía pide estimar en clase [2.5].
- **Mockup:** cada tarjeta enlaza el suyo. Mientras no exista, dice "Pendiente: se agrega antes de
  pasar la historia a Trello".
- **Decisiones pendientes:** se citan con su código del tablero (D-n). En Trello, cada tarjeta cierra
  con la lista de las decisiones que cita, con su tema, para que el docente sepa qué es cada código.
- **Prioridad:** Alta, Media o Baja.

## 5. Palabras que usan las historias

| Palabra | Qué quiere decir |
|---|---|
| Numeral del anexo | Cada uno de los 49 datos del anexo técnico de la Resolución 866. Fija los valores permitidos, el formato y la longitud |
| Catálogo | Lista oficial de valores de un dato, por ejemplo los países |
| Versión de un catálogo | Un archivo oficial cargado, con su fuente, su fecha de publicación y su fecha de descarga (RC-8) |
| CIUO-88 A.C. | Clasificación de ocupaciones que el DANE adaptó para Colombia. Tiene niveles: una ocupación general agrupa otras más específicas, y el código de cada una empieza por el de su grupo (2221 está bajo 222) |
| Ocupación hija | Ocupación del nivel más específico del catálogo. La guía solo admite esas [2.1] |
| DIVIPOLA | Lista del DANE con el código de 5 caracteres de cada municipio |
| Entidad que responde por el plan de beneficios | La EPS u otra entidad que paga la atención del paciente (numeral 15) |
| CIE-10 | Clasificación internacional de enfermedades, décima revisión |
| Categoría padre | Código general que agrupa otros más específicos. En el CIE-10, la categoría de 3 caracteres agrupa los códigos de 4 que empiezan por ella: A00 agrupa A000, A001 y A009 |
| Triage | Clasificación de la prioridad de la atención en un servicio de urgencias, de Triage I a Triage V (numeral 22, hojas 17 y 18) |
| Enfermedad huérfana | Enfermedad del listado oficial de enfermedades huérfanas del Ministerio de Salud; la atención la registra con su código si el paciente la presenta (numeral 23) |
| Borrador | Atención guardada sin todos sus datos obligatorios; se completa después y pasa a "Registrada" (D-08) |
| Inactivar | Dejar de ofrecer un valor para registros nuevos sin borrarlo |
| Anular | Marcar como no válido un registro hecho por error, sin borrarlo |

## 6. Mapa del backlog (alcance 1 a 23)

| ID | Historia | Origen | Numerales del anexo |
|---|---|---|---|
| HU-001 | Inicio de sesión con permisos por cargo | [2.4] | |
| HU-004 a HU-006 | Registrar, consultar, y actualizar y corregir al paciente | [2] | 1 a 15 |
| HU-007 a HU-009 y HU-043 | Registrar (con borrador), consultar, modificar (corregir con rastro) y anular información clínica | [2] | 16 a 23 |
| HU-010 a HU-013 | Oposición a la donación: registrar, consultar, actualizar, anular | [2.1] | 8 |
| HU-014 a HU-017 | Voluntad anticipada: registrar, consultar, actualizar, anular | [2.1] | 9 |
| HU-018 | Catálogo de países | [2.3] | 1 y 11 |
| HU-022 | Catálogo de ocupaciones | [2.3] | 7 |
| HU-026 | Catálogo de municipios | [2.3] | 12 |
| HU-030 | Catálogo de diagnósticos CIE-10 | [2.2] y [2.3] | 23 |
| HU-031 | Búsqueda y selección de un diagnóstico (médico) | [2.2] | 23 |
| HU-034 | Catálogo de tipos de documento | [2.3] | 2 |
| HU-035 | Catálogo de categorías de discapacidad | [2.3] | 10 |
| HU-036 | Catálogo de etnias | [2.3] | 13 |
| HU-037 | Catálogo de comunidades étnicas | [2.3] | 13 |
| HU-038 | Catálogo de entidades que responden por el plan de beneficios | [2.3] | 15 |
| HU-039 | Catálogo de modalidades de realización | [2.3] | 18 |
| HU-040 | Catálogo de vías de ingreso | [2.3] | 20 |
| HU-041 | Catálogo de causas que motivan la atención | [2.3] | 21 |
| HU-042 | Catálogo de enfermedades huérfanas | [2.3] | 23 |
| | **Total: 30 historias** | | |

## 7. Plantilla de historia

```
# HU-XXX · Título

**ID:** HU-XXX
**Título:** nombre funcional
**Funcionalidad relacionada:** bloque del mapa
**Descripción:**
> Como [cargo]
> Quiero [lo que necesita hacer]
> Para [para qué le sirve]
**Valor de negocio:** una o dos frases
**Prioridad:** Alta | Media | Baja
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-XXX o N/A
**Mockup:** enlace, o Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Regla con su fuente entre corchetes o con su numeral del anexo.
- Reglas comunes que aplican: copiadas (RC-n) o, en las historias de catálogo, enlazadas a la
  tarjeta "Reglas comunes".

**Precondiciones:**
- Estado que debe existir antes.

**Criterios de aceptación BDD:** (mínimo 10, ninguno de relleno)

### Escenario N: nombre
- **Dado** contexto
- **Y** condición adicional, si hace falta
- **Cuando** acción del usuario
- **Entonces** resultado que se ve
- **Y** efecto adicional, si hace falta

**Dependencias:** historias o condiciones previas
**Supuestos o asuntos pendientes:** con su código de decisión (D-n)
```

Cada historia de las tandas cierra con una sección **Trazabilidad**, que no va en la tarjeta de
Trello: qué comentario del docente y qué fila del tablero resuelve, y qué escenarios antiguos
cambiaron de número o salieron.

## Registro

| Fecha | Versión | Cambio |
|---|---|---|
| 2026-10-20 | 1 | Primera escritura |
| 2026-10-20 | 2 | Correcciones de la revisión independiente: la carga desde el archivo oficial vuelve al alcance (el docente la da por funcional en HU-018 y HU-022); sección de dependencias y restricciones; RC-1, RC-3, RC-6 y RC-7 con su fuente exacta; convención de división con el texto del Prompt 2; numeración con huecos explicada; glosario; mapa con el catálogo de cada ID |
| 2026-10-20 | 3 | RC-8 (carga desde el archivo oficial), que responde al pedido del docente de definir formato de importación, campos, clave de duplicado, versión y reporte de rechazados; administrador de catálogos con su cargo, función y permisos (D-02); glosario con categoría padre, triage, enfermedad huérfana y borrador; fuente de las enfermedades huérfanas; mapa de HU-007 a HU-009 con las palabras de la guía |
| 2026-10-20 | 3 (continuación) | RC-8: en la primera carga se guardan las aceptadas o se cancela; una versión nueva con filas rechazadas no se aplicaba. Matriz: el auxiliar corrige y anula la oposición y la voluntad; el administrador reactiva |
| 2026-10-20 | 4 | Correcciones de las revisiones de las tandas 4 y 5: el cargo de los catálogos es el profesional de estadística (D-02); RC-8 se parte en RC-8 (carga), RC-9 (versión nueva: el código de una fila rechazada queda como estaba) y RC-10 (motivos cerrados de inactivación y reactivación); RC-11, nombre del momento del registro (D-25); las historias de catálogo enlazan las reglas comunes; el CIE-10 queda en dos historias (HU-032 y HU-033 se funden en HU-030) y entra HU-043, anulación de una atención: 30 historias |
| 2026-10-20 | 4 (continuación) | Revisión final cruzada (`revisiones/revision-final-cruzada.md`): RC-8 deja validar los niveles de arriba con la regla de su historia; RC-9 agrega la recarga de la versión vigente y los motivos de la versión nueva; RC-10 en lista, con lo que significa estar en la versión vigente y la reactivación de lo inactivado con referencia oficial; trece tarjetas de catálogo; D-02 marcada como supuesto sin fuente |
| 2026-10-20 | 4 (verificación) | Verificación de la aplicación (`revisiones/verificacion-aplicacion-final.md`): RC-8 dice qué historias impiden guardar una primera carga parcial y recoge el supuesto del archivo en Excel, que repetían las 13 historias de catálogo; RC-9, que el código de una fila rechazada conserva su nombre y su estado y queda en la versión; RC-10, que un código creado por un cambio oficial y retirado después por una versión necesita otra referencia oficial para volver |
| 2026-10-21 | 5 | Límite de 12 historias por semestre: los catálogos y HU-004 dejan de depender del inicio de sesión, que no va en la primera entrega; RC-5 dice quién agrega el autor; en la oposición, cada historia solo remite a las anteriores |
