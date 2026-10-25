# Reglas comunes del backlog

Tarjeta de referencia. Las historias de catálogo enlazan estas reglas; las demás copian las pocas que aplican. Los corchetes remiten a la guía del docente y "numeral N" al anexo de la Resolución 866.

## Matriz de permisos

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

## Reglas comunes

El docente pidió "Enlazar o incluir" las reglas comunes. Las historias de catálogo (HU-018, HU-022, HU-026, HU-030 y HU-034 a HU-042) comparten las mismas; para no repetirlas en trece tarjetas, en Trello las enlazan a una tarjeta "Reglas comunes" que las trae completas. Las demás historias copian las pocas que aplican.

- **RC-1.** Todo dato con más de 5 opciones tiene su propio catálogo con su CRUD [2.3]. Propuestas del equipo: una historia por catálogo (D-17) y eliminar es inactivar (D-20). El CIE-10 tiene además la historia del médico que busca y elige un diagnóstico (HU-031), porque su actor es otro.
- **RC-2.** Un dato que sale de un catálogo solo acepta valores activos de ese catálogo.
- **RC-3.** Los catálogos son los del anexo y los administra el Ministerio de Salud (artículos 6 y 8 de la Resolución 866); los prestadores no los pueden modificar, reducir ni adicionar (artículo 7). La IPS carga cada catálogo desde su archivo oficial, con su versión, y crea o actualiza un valor solo cuando cambia la fuente oficial (artículo 12).
- **RC-4.** Nada se borra. Un valor de catálogo se inactiva con su motivo (RC-10), la fecha, la hora y quién lo hizo; deja de ofrecerse para registros nuevos y los registros que ya lo usan lo conservan (D-20).
- **RC-5.** Toda acción queda atribuida a quien la hizo, con fecha y hora. La fecha y la hora las guarda cada historia; quién lo hizo lo agrega el inicio de sesión (HU-001, escenario 9). Lo que se registre antes de tenerlo queda sin autor.
- **RC-6.** Al corregir un dato mal registrado "se agregará el nuevo dato con la fecha, hora, nombre e identificación de quien hizo la corrección" (artículo 10, parágrafo 2). El dato anterior no se borra: se agrega el nuevo, y el docente pide mostrar el anterior y el nuevo (comentario de HU-006).
- **RC-7.** Cada dato se valida con los valores permitidos, el formato y la longitud de su numeral en el anexo. La tarjeta nombra el numeral y la hoja; no copia el formato.
- **RC-8. Carga desde el archivo oficial.** El profesional de estadística descarga el archivo oficial y lo sube en CSV (UTF-8, separado por punto y coma). Escribe la fuente, la dirección y la fecha de descarga, y la versión y la fecha de publicación que trae la fuente; si la fuente no trae fecha de publicación, vale la de descarga. Ninguna de las dos puede ser futura. La primera fila trae los nombres de las columnas: el sistema busca las que lista cada historia, sin distinguir mayúsculas ni tildes, e ignora las demás. Si el archivo oficial viene en Excel, el profesional lo guarda como "CSV UTF-8" con las columnas de código en formato de texto, para que no pierdan los ceros; si los nombres de las columnas son otros, cambia solo la fila de encabezado, nunca los datos (supuesto del equipo). El código es la clave: no puede haber dos iguales, y conserva sus ceros iniciales: 05001 (D-18). El sistema rechaza el archivo completo si le falta una columna; rechaza cada fila que no cumple su numeral, con el número de fila y el motivo, y las filas con un código repetido. Un código de solo dígitos más corto que su numeral se rechaza con el aviso "revise que el archivo no haya perdido los ceros iniciales". En los catálogos con niveles (ocupaciones y CIE-10), las filas de los niveles de arriba se validan con la regla de su historia, no con la longitud del numeral, y ese aviso no aplica. Muestra cuántas filas leyó y cuántas aceptó, y la lista de rechazadas se puede descargar. En la primera carga, el profesional guarda las aceptadas o cancela todo, salvo que su historia lo impida: las listas del anexo (la hoja completa) y las ocupaciones (cantidades por nivel).
- **RC-9. Versión nueva y recarga.** Si el catálogo ya tiene valores, el archivo es:
  - una versión nueva, si su fecha de publicación es posterior a la de la versión vigente. Antes de guardar, el sistema muestra los códigos nuevos, los que cambian de nombre, los inactivos que vuelven y los que ya no están, y avisa si la versión deshace un cambio oficial registrado después de su fecha. El profesional puede anotar qué código nuevo reemplaza a uno que ya no está. Al confirmar, y después de confirmar aparte la lista de los que se inactivan, el sistema crea, actualiza, reactiva con el motivo "Vuelve en la versión" e inactiva con "No está en la versión" o "Reemplazado por"; la versión nueva queda vigente (D-21);
  - una recarga de la versión vigente, si su fecha es la misma: el sistema solo crea los códigos que faltan, avisa los nombres distintos y no inactiva nada. Sirve para completar una primera carga que tuvo filas rechazadas;
  - un archivo rechazado, si su fecha es anterior o futura.

  En los dos primeros casos, el código de una fila rechazada no se crea, no se inactiva y conserva su nombre y su estado; sale en la lista de rechazadas, y como vino en el archivo, queda en esa versión (RC-10).
- **RC-10. Inactivar y reactivar.**
  - Motivos de inactivación: "Retirado por la fuente oficial", con la referencia oficial; "Reemplazado por", con el código nuevo y la referencia oficial; "No está en la versión", que lo pone el sistema (RC-9); "Creado por error", solo para un código que no está en la versión vigente.
  - Un código está en la versión vigente si vino en su archivo, aunque su fila se haya rechazado.
  - Reactivar pide motivo. Se reactiva un código que está en la versión vigente o que creó un cambio oficial posterior a esa versión. Un código creado por un cambio oficial que una versión posterior ya no trae no se reactiva por haberse creado así: necesita otra referencia oficial. Si se inactivó con una referencia oficial, se reactiva solo con otra referencia oficial o diciendo por qué esa referencia no lo retiraba.
  - Antes de inactivar, el sistema muestra cuántos registros usan el valor, incluidos los borradores y los anulados; el profesional de estadística ve la cantidad, no los registros.
- **RC-11. Nombre del momento del registro.** Un registro conserva el código y el nombre que tenía el valor el día en que se registró. Si después la fuente oficial cambia el nombre, los registros ya hechos no cambian, y su consulta muestra también el nombre vigente (D-25; HU-005 y HU-008).

## Palabras que usan las historias

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

**Decisiones pendientes del equipo que cita esta tarjeta:** D-08, la atención sin terminar; D-17, una historia por catálogo; D-18, cómo se guardan los códigos; D-20, eliminar sin borrar; D-21, la fuente, la versión y el formato de los catálogos; D-25, el nombre que conserva un registro. Están en la hoja Decisiones del tablero del equipo.
