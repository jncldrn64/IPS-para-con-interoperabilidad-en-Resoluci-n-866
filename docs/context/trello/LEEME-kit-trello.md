# Kit para pasar el backlog v3 a Trello

Versión 4, 2026-10-24. Trae las correcciones del 2026-10-22 a las 12 historias del plan de
desarrollo y los títulos nuevos de las 13 historias de catálogo. Todo lo que hay que hacer en el
tablero de Trello para que las 30 historias del backlog v3 queden publicadas, con su mockup y con la
respuesta a los comentarios del docente.

## Qué trae el kit

- `tarjetas/`: 30 archivos, uno por tarjeta, con el texto listo para pegar en la descripción, más
  `RC-reglas-comunes.md`, la tarjeta de reglas comunes. No traen la sección de trazabilidad, que es
  solo para el equipo. Cada tarjeta cierra con la lista de las decisiones pendientes (D-n) que cita,
  con su tema, para que el docente sepa qué es cada código.
- `mockups/`: 30 imágenes PNG, una por tarjeta. Cada panel es un estado de la pantalla y lleva
  etiquetas amarillas con los escenarios que muestra, que es lo que pide el docente ("vinculado a los
  CA").
- `respuestas-al-docente.md`: un borrador de comentario por tarjeta que dice dónde quedó cada pedido y,
  cuando la tarjeta ya tenía escenarios, en qué escenario nuevo quedó cada uno. El docente pidió
  "Conservar los CA y precisar resultados"; la trazabilidad de las tandas no va en Trello, por eso
  esas correspondencias van en el comentario.
- Este archivo, con los pasos y la tabla de tarjetas.

## Si el tablero ya tiene las tarjetas

El 2026-10-24 cambian 13 títulos y 14 láminas. En Trello:

1. Renombrar las 13 tarjetas de catálogo (HU-018, HU-022, HU-026, HU-030 y HU-034 a HU-042) con el
   título de la tabla de abajo: "carga y mantenimiento" pasa a "carga, consulta, actualización e
   inactivación".
2. Reemplazar la descripción de esas 13 con su archivo de `tarjetas/`.
3. Reemplazar la imagen adjunta de esas 13 y la de HU-004 con su PNG de `mockups/`. La de HU-004
   gana el panel del escenario 15.
4. Si todavía no se pegaron las correcciones del 2026-10-22, pegar también las descripciones de
   HU-001 y HU-004.

## Pasos

1. Antes de tocar Trello, el equipo revisa las tandas del backlog v3 y el tablero (hoja Decisiones).
   Nadie aprueba lo que analizó él mismo.
2. Crear la tarjeta "Reglas comunes del backlog" con el texto de `tarjetas/RC-reglas-comunes.md` y
   dejarla de primera en la lista. Las historias de catálogo la enlazan: el docente pidió "Enlazar o
   incluir" las reglas comunes.
3. Para cada una de las 20 tarjetas que se quedan (editar o fusionar): reemplazar la descripción con
   el texto de `tarjetas/HU-0XX.md`, cambiar el título si cambió, adjuntar `mockups/HU-0XX.png` y
   publicar el comentario de `respuestas-al-docente.md`.
4. Crear las 10 tarjetas nuevas (HU-034 a HU-043) con su texto y su mockup.
5. Archivar las 13 tarjetas que se funden en otras, con la nota de la sección "Tarjetas que se
   archivan". Archivar, no borrar: así se conservan los comentarios del docente.
6. Estimar en clase con planning poker (decisión pendiente del equipo sobre la escala de
   estimación); si no se hace en clase, grabar el video con todo el equipo [2.5]. Escribir el valor
   en "Estimación relativa" de cada tarjeta.
7. Repartir las historias: la guía pide como mínimo 4 por integrante en cada sprint [2.5].

## Tarjetas

Total: 30 historias y 529 escenarios.

| ID | Título | Escenarios | Acción en Trello | Texto | Mockup |
|---|---|---|---|---|---|
| HU-001 | Inicio de sesión con permisos por cargo | 14 | Fusiona HU-002, HU-003 | tarjetas/HU-001.md | mockups/HU-001.png |
| HU-004 | Registro de un paciente | 15 | Editar | tarjetas/HU-004.md | mockups/HU-004.png |
| HU-005 | Consulta de un paciente | 13 | Editar | tarjetas/HU-005.md | mockups/HU-005.png |
| HU-006 | Actualización y corrección de los datos de un paciente | 13 | Editar | tarjetas/HU-006.md | mockups/HU-006.png |
| HU-007 | Registro de información clínica | 19 | Editar | tarjetas/HU-007.md | mockups/HU-007.png |
| HU-008 | Consulta de la información clínica de un paciente | 15 | Editar | tarjetas/HU-008.md | mockups/HU-008.png |
| HU-009 | Modificación de la información clínica registrada | 13 | Editar | tarjetas/HU-009.md | mockups/HU-009.png |
| HU-010 | Registro de la oposición a la presunción legal de donación | 12 | Editar | tarjetas/HU-010.md | mockups/HU-010.png |
| HU-011 | Consulta de la oposición a la presunción legal de donación | 10 | Editar | tarjetas/HU-011.md | mockups/HU-011.png |
| HU-012 | Actualización de la oposición a la presunción legal de donación | 13 | Editar | tarjetas/HU-012.md | mockups/HU-012.png |
| HU-013 | Anulación de un registro de oposición hecho por error | 12 | Editar | tarjetas/HU-013.md | mockups/HU-013.png |
| HU-014 | Registro del documento de voluntad anticipada | 14 | Editar | tarjetas/HU-014.md | mockups/HU-014.png |
| HU-015 | Consulta del documento de voluntad anticipada | 12 | Editar | tarjetas/HU-015.md | mockups/HU-015.png |
| HU-016 | Actualización del documento de voluntad anticipada: modificación, sustitución y revocación | 14 | Editar | tarjetas/HU-016.md | mockups/HU-016.png |
| HU-017 | Anulación de un registro de voluntad anticipada hecho por error | 12 | Editar | tarjetas/HU-017.md | mockups/HU-017.png |
| HU-018 | Catálogo de países: carga, consulta, actualización e inactivación | 22 | Fusiona HU-019, HU-020, HU-021 | tarjetas/HU-018.md | mockups/HU-018.png |
| HU-022 | Catálogo de ocupaciones: carga, consulta, actualización e inactivación | 28 | Fusiona HU-023, HU-024, HU-025 | tarjetas/HU-022.md | mockups/HU-022.png |
| HU-026 | Catálogo de municipios: carga, consulta, actualización e inactivación | 25 | Fusiona HU-027, HU-028, HU-029 | tarjetas/HU-026.md | mockups/HU-026.png |
| HU-030 | Catálogo de diagnósticos CIE-10: carga, consulta, actualización e inactivación | 27 | Fusiona HU-032, HU-033 | tarjetas/HU-030.md | mockups/HU-030.png |
| HU-031 | Búsqueda y selección de un diagnóstico | 13 | Editar | tarjetas/HU-031.md | mockups/HU-031.png |
| HU-034 | Catálogo de tipos de documento: carga, consulta, actualización e inactivación | 23 | Nueva | tarjetas/HU-034.md | mockups/HU-034.png |
| HU-035 | Catálogo de categorías de discapacidad: carga, consulta, actualización e inactivación | 23 | Nueva | tarjetas/HU-035.md | mockups/HU-035.png |
| HU-036 | Catálogo de etnias: carga, consulta, actualización e inactivación | 23 | Nueva | tarjetas/HU-036.md | mockups/HU-036.png |
| HU-037 | Catálogo de comunidades étnicas: carga, consulta, actualización e inactivación | 22 | Nueva | tarjetas/HU-037.md | mockups/HU-037.png |
| HU-038 | Catálogo de entidades que responden por el plan de beneficios: carga, consulta, actualización e inactivación | 22 | Nueva | tarjetas/HU-038.md | mockups/HU-038.png |
| HU-039 | Catálogo de modalidades de realización: carga, consulta, actualización e inactivación | 23 | Nueva | tarjetas/HU-039.md | mockups/HU-039.png |
| HU-040 | Catálogo de vías de ingreso: carga, consulta, actualización e inactivación | 23 | Nueva | tarjetas/HU-040.md | mockups/HU-040.png |
| HU-041 | Catálogo de causas que motivan la atención: carga, consulta, actualización e inactivación | 23 | Nueva | tarjetas/HU-041.md | mockups/HU-041.png |
| HU-042 | Catálogo de enfermedades huérfanas: carga, consulta, actualización e inactivación | 21 | Nueva | tarjetas/HU-042.md | mockups/HU-042.png |
| HU-043 | Anulación de una atención registrada por error | 10 | Nueva | tarjetas/HU-043.md | mockups/HU-043.png |

## Tarjetas que se archivan

| Tarjeta | Se funde en | Nota para pegar como comentario antes de archivar |
|---|---|---|
| HU-002 | HU-001 | Archivada: su contenido pasó a HU-001 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-001: el 1 (acceso) va en el 1 y el 6; el 2 es el 2; el 3 (sin sesión) es el 4; el 4 (atribución) es el 9; el 5 (qué ve el médico) es el 6; el 6 (actualizar datos del paciente) es el 8; el 7 (cierre) es el 11; el 8 (continuidad entre pacientes) va en el 10, y lo de no mezclar la información de los dos pacientes sale, porque cada consulta es de un paciente (HU-005 y HU-008); el 9 (sin recuperación) es el 13; el 10 (inactividad) es el 12. Los comentarios del docente quedan aquí como registro. |
| HU-003 | HU-001 | Archivada: su contenido pasó a HU-001 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-001: el 1 (acceso y operaciones) va en el 1 y el 7; el 2 es el 2; el 3 (sin sesión) es el 4; el 4 (atribución) es el 9; el 5 (registrar un paciente) y el 6 (información clínica) van en el 7 y el 8; el 7 (consulta de un catálogo) va en el 7 y en la consulta de cada historia de catálogo; el 8 (cierre) es el 11; el 9 (sin recuperación) es el 13; el 10 (inactividad) es el 12. Los comentarios del docente quedan aquí como registro. |
| HU-019 | HU-018 | Archivada: su contenido pasó a HU-018 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-018: el 1 es el 12; el 2 es el 16; el 3 va en el 13 y el 14; el 4 es el 15; el 5 (conteo) va en el 12, que muestra cuántos hay activos, y en el 17; el 6 (quién cargó) va en el 22; el 7 (consulta del auxiliar) va en HU-001 escenario 14; el 8 (sin sesión) lo cubre HU-001 escenario 4; el 9 (no modifica) va en el 12; el 10 (eliminados) es el filtro de inactivos del 12. Los comentarios del docente quedan aquí como registro. |
| HU-020 | HU-018 | Archivada: su contenido pasó a HU-018 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-018: el 1 es el 10; el 2 (los registros conservan su referencia) va en el 10 y el 7; el 3, el 4 y el 10 (código repetido, formato, valor incompleto) van en el 9; el 5 (país que no existe) sale, porque se cambia un país elegido de la lista; el 6 (abandonada) es el 11; el 7 (atribución) va en el 22; el 8 (sin sesión) lo cubre HU-001 escenario 4; el 9 (otro cargo) va en HU-001 escenario 14. Los comentarios del docente quedan aquí como registro. |
| HU-021 | HU-018 | Archivada: su contenido pasó a HU-018 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-018: el 1, el 2, el 3 y el 7 (sin uso, en uso, confirmación, registro después) van en el 18; el 4 (cancelada) es el 11; el 5 (rastro) va en el 22; el 6 (valor que no existe) sale, porque se inactiva un país elegido de la lista; el 8 (sin sesión) lo cubre HU-001 escenario 4; el 9 (otro cargo) va en HU-001 escenario 14; el 10 (recarga) es el 20. Los comentarios del docente quedan aquí como registro. |
| HU-023 | HU-022 | Archivada: su contenido pasó a HU-022 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-022: el 1 es el 15; el 2, el 3 y el 6 (jerarquía, asociables, padre sin hijas) van en el 16; el 4 es el 20; el 5 va en el 17 y el 18; el 7 (quién cargó) va en el 28; el 8 (sin sesión) lo cubre HU-001 escenario 4; el 9 (no modifica) y el 10 (eliminados) van en el 15. Los comentarios del docente quedan aquí como registro. |
| HU-024 | HU-022 | Archivada: su contenido pasó a HU-022 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-022: el 1 y el 3 (nombre de una ocupación o de un grupo, cuyas hijas siguen bajo él) son el 13; el 2 (referencias) va en el 13 y el 10; el 4 (cambio de padre) sale: una ocupación no cambia de grupo sin cambiar de código, y eso es el 10; el 5 y el 10 (código repetido, valor incompleto) van en el 12; el 6 (ocupación que no existe) sale, porque se cambia una ocupación elegida de la lista; el 7 (abandonada) es el 14; el 8 (atribución) va en el 28; el 9 (sin sesión) lo cubre HU-001 escenario 4. Los comentarios del docente quedan aquí como registro. |
| HU-025 | HU-022 | Archivada: su contenido pasó a HU-022 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-022: el 1, el 2, el 4 y el 8 (sin uso, en uso, confirmación, registro después) van en el 22; el 3 (padre con hijas) es el 23; el 5 (cancelada) es el 14; el 6 (rastro) va en el 28; el 7 (valor que no existe) sale; el 9 (sin sesión) lo cubre HU-001 escenario 4; el 10 (recarga) es el 25. Los comentarios del docente quedan aquí como registro. |
| HU-027 | HU-026 | Archivada: su contenido pasó a HU-026 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-026: el 1, el 2 (el código acompaña al nombre), el 9 (no modifica) y el 10 (eliminados) van en el 14; el 3 es el 19; el 4 va en el 17 y el 15; el 5 es el 16; el 6 es el 18; el 7 (quién cargó) va en el 25; el 8 (sin sesión) lo cubre HU-001 escenario 4. Los comentarios del docente quedan aquí como registro. |
| HU-028 | HU-026 | Archivada: su contenido pasó a HU-026 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-026: el 1 es el 12; el 2 (referencias) va en el 12 y el 9; el 3 (cambio del código DANE) es el 9; el 4, el 5 y el 10 (código repetido, formato, valor incompleto) van en el 11; el 6 (municipio que no existe) sale; el 7 (abandonada) es el 13; el 8 (atribución) va en el 25; el 9 (sin sesión) lo cubre HU-001 escenario 4. Los comentarios del docente quedan aquí como registro. |
| HU-029 | HU-026 | Archivada: su contenido pasó a HU-026 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-026: el 1, el 2, el 3 y el 7 van en el 21; el 4 (cancelada) es el 13; el 5 (rastro) va en el 25; el 6 (valor que no existe) sale; el 8 (sin sesión) lo cubre HU-001 escenario 4; el 9 (otro cargo) va en HU-001 escenario 14; el 10 (recarga) es el 23. Los comentarios del docente quedan aquí como registro. |
| HU-032 | HU-030 | Archivada: su contenido pasó a HU-030 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-030: el 1 (descripción) es el 12; el 2 (las atenciones conservan su referencia) va en el 9 y el 12, que ahora conserva también el nombre (RC-11); el 3 (versión nueva, y lo registrado con la anterior se sigue consultando) es el 7; el 4 (actualizar un padre) va en el 12: cambiar el nombre de una categoría no cambia sus códigos; el 5 (cambio del padre de un hijo) sale: un código no cambia de categoría sin cambiar de código, y eso es el 9; el 6 (poner un código que ya usa otro diagnóstico) cambia: un reemplazo oficial puede apuntar a un código que ya existe (9), y crear un código que ya existe sigue rechazado (11); el 7 (código que no existe) sale, porque se cambia un código elegido de la lista; el 8 (abandonada) es el 13; el 9 (atribución) va en el 27; el 10 (sin sesión) lo cubre HU-001 escenario 4. Los comentarios del docente quedan aquí como registro. |
| HU-033 | HU-030 | Archivada: su contenido pasó a HU-030 (una historia por catálogo o por inicio de sesión, decisión del equipo). Dónde quedó cada escenario de esta tarjeta en HU-030: el 1 (sin uso), el 4 (confirmación) y el 8 (registro después) van en el 21; el 2 (en uso, que antes se bloqueaba) va en el 21: ya no se bloquea, se inactiva con su motivo y las atenciones lo conservan; el 3 (padre con hijos) es el 22; el 5 (cancelada) es el 13; el 6 (rastro) es el 27; el 7 (código que no existe) sale; el 9 (sin sesión) lo cubre HU-001 escenario 4; el 10 (recarga) es el 24. Los comentarios del docente quedan aquí como registro. |

## Antes de publicar

- Confirmar con el docente las 14 preguntas abiertas (archivo `preguntas-docente.md`): alcance 1 a
  23 o 1 a 49, una historia por catálogo, inicio de sesión, cargo que mantiene los catálogos, formato
  del mockup, eliminar como inactivar, anulación de una atención, triage, CIUO-88 frente a CUOC,
  archivo de comunidades étnicas, filas oficiales que no cumplen su numeral, retiro de la oposición,
  nombre que conserva un registro e identidad de género.
- Si el docente responde distinto de lo propuesto, se cambia primero el tablero (hoja Decisiones) y
  después las tarjetas afectadas.
