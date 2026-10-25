# Backlog IPS Salud y Vida, versión 3. Tanda 5: diagnósticos CIE-10 y catálogos de la atención

Versión 4, 2026-10-24. Borrador corregido con la revisión independiente
(`revisiones/revision-tanda-5.md`) y con la revisión final cruzada
(`revisiones/revision-final-cruzada.md`). HU-030 y HU-031 (diagnósticos CIE-10) y HU-039 a HU-042
(modalidades, vías de ingreso, causas y enfermedades huérfanas). Las reglas comunes (RC), la matriz
de permisos, el glosario y las convenciones están en la tanda 0 (versión 4).

HU-030 tiene sus dos comentarios en la exportación de Trello del 19 de octubre. HU-031 a HU-033 no
tienen comentarios en la exportación. Para HU-031 y HU-033 el tablero trae retroalimentación que no
se pudo verificar en Trello; para HU-032 no trae ninguna: su fila de ajustes es una propuesta del
modelo, como la marca el tablero.

## El CIE-10 en dos historias

- La carga y el mantenimiento del CIE-10 son una historia (HU-030), como en los demás catálogos:
  cargar, crear, cambiar, pasar a una versión nueva e inactivar son operaciones del mismo catálogo, y
  el Prompt 2 dice "No dividas historias únicamente por: [...] Operaciones de base de datos". En la
  versión 1 eran tres historias (HU-030, HU-032 y HU-033). La revisión mostró que esa división no
  resistía la comparación con el catálogo de ocupaciones, que también tiene niveles, versiones e
  inactivación en una sola historia. HU-032 y HU-033 se archivan con una nota que remite a HU-030.
- Buscar y elegir un diagnóstico es otra historia (HU-031), porque la hace el médico tratante: el
  Prompt 2 pide separar cuando hay "actores distintos con objetivos diferentes".
- La guía le da al CIE-10 un numeral propio, con su jerarquía padre e hijo [2.2]. La categoría de
  cada código se lee de sus 3 primeros caracteres (D-26).

## Decisiones que esta tanda propone

| Decisión | Propuesta | Por qué |
|---|---|---|
| D-09, versión del CIE-10 | La tabla CIE10 de SISPRO, en la versión vigente el día de la carga. La carga guarda la dirección y la fecha de descarga (RC-8), que es lo que demuestra qué versión se cargó | La guía pide "la última versión del CIE-10" [2.2]. El anexo de la Resolución 1888 de 2025 remite a esa tabla y dice: "Al momento de la publicación del presente manual, el plazo para la transición de la codificación de diagnósticos de CIE 10 a CIE 11 ha sido ampliado" (Diario Oficial 53.244). La Resolución 2625 de 2025 cita la 1895 de 2001 como la que adoptó la CIE-10 en Colombia (por confirmar en la 1895) |
| D-14, enfermedades huérfanas | El listado de la Resolución 2625 de 2025 del Ministerio de Salud, versión 5.0, que derogó la 023 de 2023. El código es el número que el listado da a cada enfermedad | La hoja 18 dice "catálogo de enfermedades huérfanas vigente" sin nombrarlo. La Resolución 2625 numera cada enfermedad de forma consecutiva y exclusiva, y el número no se asigna a otra enfermedad (artículo 3); que esos números sean el código de 1 a 4 caracteres del numeral 23 es inferencia del equipo |
| D-22, código padre del CIE-10 | En una atención solo se elige un código de 4 caracteres; las categorías de 3 sirven para la jerarquía | El numeral 23 fija para el código CIE-10 longitud mínima y máxima de 4 (hoja 18) |
| D-26, grupo leído del código | La categoría de cada código CIE-10 son sus 3 primeros caracteres: A000 está bajo A00. El archivo no necesita columna de padre | Inferencia del equipo por la forma de los códigos del CIE-10; se confirma con la tabla de SISPRO. En la CIUO-88 A.C. lo confirma el DANE (tanda 4) |

## Escenarios por historia

| Historia | Escenarios | Particulares |
|---|---|---|
| HU-030 Catálogo de diagnósticos CIE-10: carga, consulta, actualización e inactivación | 27 | Categoría leída del código, categoría y código en el mismo archivo, código sin su categoría, jerarquía, categoría con códigos activos, código con su categoría inactiva |
| HU-031 Búsqueda y selección de un diagnóstico | 13 | Actor: médico tratante |
| HU-039 Catálogo de modalidades de realización: carga, consulta, actualización e inactivación | 23 | Lista del anexo: primera carga y comprobación contra la hoja 16 |
| HU-040 Catálogo de vías de ingreso: carga, consulta, actualización e inactivación | 23 | Lista del anexo: primera carga y comprobación contra las hojas 16 y 17 |
| HU-041 Catálogo de causas que motivan la atención: carga, consulta, actualización e inactivación | 23 | Lista del anexo: primera carga y comprobación contra la hoja 17 |
| HU-042 Catálogo de enfermedades huérfanas: carga, consulta, actualización e inactivación | 21 | Sin cambio de código: el número no se reasigna |
| **Total** | **130** | |

---

# HU-030 · Catálogo de diagnósticos CIE-10: carga, consulta, actualización e inactivación

**ID:** HU-030
**Título:** Catálogo de diagnósticos CIE-10: carga, consulta, actualización e inactivación
**Funcionalidad relacionada:** Diagnósticos CIE-10
**Descripción:**
> Como profesional de estadística
> Quiero cargar la última versión del CIE-10 desde su archivo oficial, con su jerarquía de categorías y códigos, consultarla y agregar, actualizar, inactivar o reactivar un diagnóstico, siempre igual a la fuente
> Para que el médico tratante registre el diagnóstico de cada atención con un código vigente

**Valor de negocio:** Sin el catálogo cargado ninguna atención puede quedar como "Registrada",
porque el diagnóstico principal es obligatorio (HU-007; D-15, propuesta). La guía pide la última
versión del CIE-10 con su jerarquía padre e hijo [2.2].

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-007
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Los códigos que se registran en una atención tienen 4 caracteres, y el nombre del diagnóstico de 3
  a 200 (numeral 23, hoja 18). Las categorías de 3 caracteres agrupan los códigos que empiezan por
  ellas: A000, A001 y A009 están bajo A00. Solo los de 4 se eligen en una atención (D-22). Las
  categorías son el nivel de arriba de la jerarquía padre e hijo que pide la guía [2.2].
- Fuente oficial: la tabla CIE10 de SISPRO, del Ministerio de Salud, en la versión vigente el día de
  la carga (D-09). La guía pide "la última versión del CIE-10" [2.2], y el anexo de la Resolución
  1888 de 2025 remite a esa tabla. Lo que demuestra qué versión se cargó es la dirección y la fecha
  de descarga que guarda la carga (RC-8).
- Columnas del archivo: código; nombre. La clave de duplicado es el código.
- El sistema lee la categoría de los 3 primeros caracteres del código, así que el archivo no
  necesita una columna de padre (D-26, propuesta). Las categorías se validan con esta regla, no con
  la longitud del numeral 23 (RC-8).
- Una fila se acepta si su categoría está activa en el catálogo o se acepta en el mismo archivo, sin
  importar el orden de las filas.
- Una categoría con códigos activos no se inactiva, tampoco por una versión nueva. Un código no se
  reactiva con su categoría inactiva.
- Las atenciones conservan el código y el nombre con que se registraron y no se pasan a una versión
  nueva (RC-11).
- Al inactivar un valor, el conteo incluye las atenciones en borrador y las anuladas, porque
  conservan el valor; al completar un borrador con un valor inactivo, el sistema pide elegir uno
  activo (HU-007).
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- Operaciones por escenario (el CRUD de la guía [2.3]): carga 1 a 6, 10 y 11; consulta 14 a 20 y 27;
  actualización 9, 11 y 12; inactivación y reactivación 21 a 26 (RC-10, nada se borra); la versión
  nueva, 7 y 8, hace las cuatro; cancelar cualquiera de ellas, 13.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística descargó el archivo oficial
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea un diagnóstico por cada fila aceptada, con esa versión
- **Y** los diagnósticos quedan disponibles para la búsqueda del médico (HU-031) y el registro de la atención (HU-007)

### Escenario 2: Filas con errores en la primera carga
- **Dado** que en la primera carga el archivo tiene una fila con un código de 5 caracteres y otra sin nombre
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema muestra cuántas filas leyó y cuántas aceptó, y lista las rechazadas con su número de fila y su motivo
- **Y** el profesional puede descargar esa lista y elegir entre guardar las filas aceptadas o cancelar la carga completa
- **Y** si guardó las aceptadas, puede subir el archivo corregido de la misma versión para completar la carga, sin inactivar nada (RC-9)

### Escenario 3: Archivo rechazado completo
- **Dado** que al archivo le falta la columna del nombre, o su fecha de publicación es futura o anterior a la de la versión vigente
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza el archivo completo
- **Y** dice qué columna falta o qué fecha no sirve, y cuál es la versión vigente

### Escenario 4: Código repetido en el archivo
- **Dado** que dos filas del archivo tienen el mismo código
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza las dos filas con el motivo "código repetido"

### Escenario 5: Categoría y código en el mismo archivo
- **Dado** que el archivo trae el código A000 antes que su categoría A00
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema acepta las dos filas
- **Y** el A000 queda bajo la categoría A00

### Escenario 6: Código sin su categoría
- **Dado** que el archivo trae el código A000 y su categoría A00 no está activa en el catálogo ni se acepta en el archivo, por ejemplo porque su fila se rechazó
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza el A000 con el motivo "su categoría A00 no existe"

### Escenario 7: Versión nueva
- **Dado** que el catálogo tiene una versión vigente y la fuente oficial publicó otra
- **Cuando** el profesional de estadística sube el archivo de la versión nueva
- **Entonces** el sistema muestra, antes de guardar, los códigos nuevos, los que cambian de nombre, los inactivos que vuelven y los que ya no están, y avisa si la versión deshace un cambio oficial registrado después de su fecha de publicación
- **Y** deja anotar qué código nuevo reemplaza a uno que ya no está
- **Y** al confirmar, y confirmar aparte la lista de los que ya no están, crea, actualiza, reactiva e inactiva según esa lista, con los motivos "Vuelve en la versión", "Reemplazado por" o "No está en la versión"
- **Y** la versión nueva queda vigente, y los registros hechos con la anterior siguen igual

### Escenario 8: Versión nueva con filas rechazadas
- **Dado** que el archivo de una versión nueva trae una fila rechazada con un código que ya está en el catálogo
- **Cuando** el profesional de estadística confirma la versión
- **Entonces** el sistema aplica las demás filas
- **Y** deja ese código con su nombre y su estado, sin inactivarlo, y lo muestra en la lista de rechazadas

### Escenario 9: Cambio oficial de código
- **Dado** que la fuente oficial reemplazó el código de un diagnóstico por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** las atenciones que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 10: Creación por cambio oficial
- **Dado** que la fuente oficial agregó un diagnóstico después de la versión cargada
- **Cuando** el profesional de estadística lo crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema lo guarda activo, con esa referencia

### Escenario 11: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 12: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de un diagnóstico
- **Cuando** el profesional de estadística lo actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya lo usan no cambian: su consulta muestra el nombre con que se hicieron y también el nuevo (RC-11; HU-008)
- **Y** si es una categoría, sus códigos siguen bajo ella
- **Y** el nombre anterior queda en el historial del diagnóstico

### Escenario 13: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 14: Consulta del catálogo
- **Dado** que el catálogo tiene diagnósticos cargados
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada diagnóstico, cuántos hay activos y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo los activos, solo los inactivos o todos, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 15: Consulta de la jerarquía
- **Dado** que el catálogo tiene categorías y códigos
- **Cuando** el profesional de estadística consulta la jerarquía
- **Entonces** ve cada categoría con sus códigos, y las categorías sin códigos activos marcadas "sin códigos para elegir"
- **Y** los de 4 caracteres aparecen marcados como los que se eligen en una atención (D-22)

### Escenario 16: Búsqueda por código
- **Dado** que el catálogo tiene diagnósticos cargados
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo el diagnóstico con ese código exacto

### Escenario 17: Búsqueda por nombre con varias coincidencias
- **Dado** que varios diagnósticos tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todos los que la contienen, ordenados por nombre

### Escenario 18: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay diagnósticos que coincidan con la búsqueda"

### Escenario 19: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial
- **Y** mientras tanto la atención se puede guardar como "Borrador", pero no como "Registrada" (HU-007)

### Escenario 20: Comprobación contra el archivo oficial
- **Dado** que el profesional de estadística descargó de nuevo el archivo oficial
- **Cuando** lo sube para comprobar el catálogo
- **Entonces** el sistema, sin cambiar nada, lista los códigos del archivo que faltan en el catálogo, los que están inactivos en el catálogo, los activos que no están en el archivo y los nombres distintos

### Escenario 21: Inactivación con motivo
- **Dado** que la fuente oficial retiró un diagnóstico
- **Cuando** el profesional de estadística lo inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántas atenciones lo usan y pide confirmar
- **Y** al confirmar lo deja inactivo: no se ofrece en registros nuevos y las atenciones que lo tienen lo conservan

### Escenario 22: Categoría con códigos activos
- **Dado** que la categoría A00 tiene códigos activos
- **Cuando** el profesional de estadística intenta inactivarla, o una versión nueva la retira y deja activos sus códigos
- **Entonces** el sistema no la inactiva
- **Y** dice cuántos códigos activos tiene

### Escenario 23: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar el diagnóstico
- **Entonces** el sistema no lo inactiva y dice qué falta (RC-10)

### Escenario 24: Reactivación
- **Dado** que un diagnóstico que está en la versión vigente se inactivó citando una referencia oficial que no lo retiraba
- **Cuando** el profesional de estadística lo reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** el diagnóstico vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 25: Código con su categoría inactiva
- **Dado** que la categoría de un código está inactiva
- **Cuando** el profesional de estadística intenta reactivar el código
- **Entonces** el sistema no lo permite
- **Y** dice que primero se reactiva la categoría

### Escenario 26: Reactivación sin respaldo
- **Dado** que un diagnóstico inactivo no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva lo retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no lo retiraba
- **Cuando** el profesional de estadística intenta reactivarlo
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 27: Historial de un diagnóstico
- **Dado** que un diagnóstico tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-007, HU-008, HU-009 y HU-031.
**Supuestos o asuntos pendientes:**
- D-02, D-09, D-15, D-18, D-21, D-22, D-23, D-25 y D-26: propuestas del equipo.
- Pendiente de verificar con la tabla CIE10 de SISPRO: si trae las categorías de 3 caracteres con su
  nombre. Si no las trae, las categorías se cargan aparte y antes que la tabla, como una fuente
  propia con su versión (la CIE-10 que adoptó la Resolución 1895 de 2001, citada en la Resolución
  2625 de 2025, por confirmar); para ellas, la versión vigente es la de esa fuente (RC-10), y la
  comprobación y las versiones de la tabla de SISPRO no las cuentan como sobrantes ni como
  retiradas.
- Las categorías sin subdivisión vienen con una X como cuarto carácter (A33X), como en el listado de
  la Resolución 2625 de 2025; así D-22 vale para todas. Se confirma con la tabla.
- Si una fila del archivo oficial no cumple su numeral, no se carga y sale en la lista de rechazadas
  (RC-8); qué manda en ese caso, la fuente o el numeral, es pregunta al docente.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-030 c1 (cargo funcional: regla del cargo y HU-001 escenario 14;
  formato de archivo, campos y orden de carga padre e hijo: RC-8, columnas, regla de la categoría
  leída del código y escenarios 5 y 6; versión de referencia y cómo se valida que es la requerida:
  D-09, la dirección y la fecha de descarga que guarda la carga (RC-8) y escenario 20, que compara
  con un archivo recién descargado; reglas comunes enlazadas) y c2 (edición oficial adoptada: D-09 y
  el supuesto de la Resolución 1895 de 2001; códigos y nombres: regla de datos; raíces: las
  categorías, escenario 15; formato de importación: RC-8; conservar los criterios y precisar
  resultados: cada escenario antiguo tiene su escenario nuevo, en la lista que sigue).
- HU-032 y HU-033 no están en la exportación de Trello del 19 de octubre. Para HU-032 el tablero no
  tiene retroalimentación del docente: su fila de ajustes es una propuesta del modelo, como la marca
  el tablero (Ajustes por historia, fila 104): solo por cambio oficial, con su versión: escenarios 7
  y 12; los códigos que la versión retira se inactivan, no se borran: escenario 7; cargo sin
  permiso: HU-001 escenario 14. Retroalimentación sin verificar de HU-033: inactivar con motivo:
  escenarios 21 y 23; quién y cuándo: escenario 27; reactivar: escenario 24; "Bien bloqueo de uso":
  el valor en uso ya no se bloquea: como nada se borra (D-20), se inactiva con un motivo de RC-10 y
  los registros lo conservan; lo que se bloquea es inactivar sin respaldo, escenario 23; el padre
  con hijos se queda: escenario 22; cargo sin permiso: HU-001 escenario 14.
- Escenarios antiguos de HU-030: el 1 (carga de un código) es el 10; el 2 (carga masiva) es el 1; el
  3 (jerarquía) va en el 5 y el 15; el 4 (hijo sin padre) es el 6; el 5 (versión) va en el 1 y el
  14; el 6 (código que ya existe) va en el 11 y el 7; el 7 (sin descripción) y el 10 (se conservan
  los válidos) van en el 2; el 8 (atribución) va en el 27; el 9 (sin sesión) lo cubre HU-001
  escenario 4.
- De HU-032: el 1 (descripción) es el 12; el 2 (las atenciones conservan su referencia) va en el 9 y
  el 12, que ahora conserva también el nombre (RC-11); el 3 (versión nueva, y lo registrado con la
  anterior se sigue consultando) es el 7; el 4 (actualizar un padre) va en el 12: cambiar el nombre
  de una categoría no cambia sus códigos; el 5 (cambio del padre de un hijo) sale: un código no
  cambia de categoría sin cambiar de código, y eso es el 9; el 6 (poner un código que ya usa otro
  diagnóstico) cambia: un reemplazo oficial puede apuntar a un código que ya existe (9), y crear un
  código que ya existe sigue rechazado (11); el 7 (código que no existe) sale, porque se cambia un
  código elegido de la lista; el 8 (abandonada) es el 13; el 9 (atribución) va en el 27; el 10 (sin
  sesión) lo cubre HU-001 escenario 4.
- De HU-033: el 1 (sin uso), el 4 (confirmación) y el 8 (registro después) van en el 21; el 2 (en
  uso, que antes se bloqueaba) va en el 21: ya no se bloquea, se inactiva con su motivo y las
  atenciones lo conservan; el 3 (padre con hijos) es el 22; el 5 (cancelada) es el 13; el 6 (rastro)
  es el 27; el 7 (código que no existe) sale; el 9 (sin sesión) lo cubre HU-001 escenario 4; el 10
  (recarga) es el 24.
- Se funden HU-030, HU-032 y HU-033 (D-17): las tarjetas HU-032 y HU-033 se archivan con una nota
  que remite a HU-030. La revisión de la tanda 5 mostró que separarlas dividía por operación de base
  de datos, lo que el Prompt 2 excluye como único criterio, y que el catálogo de ocupaciones tiene
  niveles, versiones e inactivación en una sola historia. HU-031 sigue aparte porque su actor es el
  médico. El historial de versiones que tenía la versión 1 de HU-032 queda en el escenario 14. El
  título pasa a "Catálogo de diagnósticos CIE-10: carga y mantenimiento".
- Tablero, Ajustes por historia: filas con destino HU-030, HU-032 y HU-033 y la fila general de
  catálogos.
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.
- Versión 4 de la tanda (2026-10-24): el título pasa de "Catálogo de diagnósticos CIE-10: carga y
  mantenimiento" a "Catálogo de diagnósticos CIE-10: carga, consulta, actualización e inactivación",
  y el Quiero nombra las cuatro operaciones. Pedido del autor: "mantenimiento" no decía cuáles eran,
  y el docente revisó en la versión 2 una tarjeta por operación (carga, consulta, actualización y
  eliminación). La línea de operaciones usa los nombres del título y pone la versión nueva en las
  cuatro.

---

# HU-031 · Búsqueda y selección de un diagnóstico

**ID:** HU-031
**Título:** Búsqueda y selección de un diagnóstico
**Funcionalidad relacionada:** Diagnósticos CIE-10
**Descripción:**
> Como médico tratante
> Quiero buscar un diagnóstico del CIE-10 por código o por nombre y ver dónde está en la jerarquía
> Para elegir el código de 4 caracteres que corresponde, sin escribirlo de memoria

**Valor de negocio:** Un código mal elegido queda en la historia del paciente y viaja a otros
prestadores. Ver la categoría padre ayuda a confirmar que es el diagnóstico buscado.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-007
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23). Los resultados se muestran de 20 en 20 (regla del equipo).
- Cada resultado muestra el código, el nombre y su categoría padre, que son los 3 primeros caracteres
  del código (D-26). Mostrar la categoría ayuda a respetar la jerarquía padre e hijo que pide la guía
  [2.2].
- Solo se eligen códigos activos de 4 caracteres (numeral 23, hoja 18; D-22). Las categorías se abren
  para ver sus códigos, pero no se eligen.
- La consulta muestra la versión vigente del CIE-10 y su fecha de publicación (D-09).
- Todos los cargos pueden consultar el catálogo (matriz de permisos). Elegir un diagnóstico es parte
  del registro y la corrección de una atención (HU-007 y HU-009), que solo hace el médico tratante.
- RC-1. Todo dato con más de 5 opciones tiene su propio catálogo con su CRUD [2.3]. El del CIE-10 se
  mantiene en HU-030; esta historia es la búsqueda del médico.
- RC-2. Un dato que sale de un catálogo solo acepta valores activos de ese catálogo.
- RC-4. Nada se borra. Un valor de catálogo se inactiva con su motivo, la fecha, la hora y quién lo
  hizo; deja de ofrecerse para registros nuevos y los registros que ya lo usan lo conservan (D-20).
- RC-11. Un registro conserva el código y el nombre que tenía el valor el día en que se registró; si
  la fuente cambia el nombre después, su consulta muestra también el vigente (D-25).

**Precondiciones:**
- La persona tiene sesión iniciada (HU-001).

**Criterios de aceptación BDD:**

### Escenario 1: Búsqueda por código
- **Dado** que el catálogo tiene la versión vigente cargada
- **Cuando** el médico tratante escribe un código de 4 caracteres
- **Entonces** el sistema muestra ese código con su nombre y su categoría padre

### Escenario 2: Búsqueda por nombre
- **Dado** que varios diagnósticos tienen la misma palabra en el nombre
- **Cuando** el médico tratante busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todos los que la contienen, ordenados por código, cada uno con su categoría padre
- **Y** los muestra de 20 en 20, con el total de resultados

### Escenario 3: Elegir un diagnóstico
- **Dado** que el médico tratante encontró el código que busca y está registrando una atención
- **Cuando** lo elige
- **Entonces** el código queda como diagnóstico principal de la atención, con su nombre (HU-007)

### Escenario 4: Categoría padre
- **Dado** que el resultado es una categoría de 3 caracteres
- **Cuando** el médico tratante intenta elegirla
- **Entonces** el sistema no la deja elegir
- **Y** le muestra sus códigos de 4 caracteres para que elija uno

### Escenario 5: Recorrer la jerarquía
- **Dado** que el médico tratante abre una categoría padre
- **Cuando** la consulta
- **Entonces** ve sus códigos activos, cada uno con su nombre

### Escenario 6: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el médico tratante busca
- **Entonces** el sistema dice "No hay diagnósticos que coincidan con la búsqueda"

### Escenario 7: Código inactivo
- **Dado** que un código está inactivo
- **Cuando** el médico tratante lo busca
- **Entonces** el sistema lo muestra marcado como inactivo, con su motivo
- **Y** no lo deja elegir

### Escenario 8: Versión vigente a la vista
- **Dado** que el catálogo tiene una versión vigente
- **Cuando** el médico tratante lo consulta
- **Entonces** ve qué versión del CIE-10 está vigente y su fecha de publicación

### Escenario 9: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el médico tratante busca un diagnóstico
- **Entonces** el sistema dice que el catálogo está vacío
- **Y** que la atención se puede guardar como "Borrador", pero no como "Registrada", hasta que se cargue (HU-007 y HU-030)

### Escenario 10: Categoría sin códigos para elegir
- **Dado** que una categoría padre no tiene códigos activos
- **Cuando** el médico tratante la abre
- **Entonces** el sistema la marca como "sin códigos para elegir"

### Escenario 11: Consulta de otros cargos
- **Dado** que un auxiliar de admisión o el profesional de estadística tiene sesión iniciada
- **Cuando** consulta el catálogo de diagnósticos
- **Entonces** puede buscar y recorrer la jerarquía
- **Y** no tiene la opción de elegir un diagnóstico, porque no registra atenciones

### Escenario 12: La búsqueda no cambia nada
- **Dado** que alguien buscó en el catálogo
- **Cuando** cierra la búsqueda
- **Entonces** el catálogo queda igual que antes

### Escenario 13: Elegir al corregir una atención
- **Dado** que el médico tratante corrige el diagnóstico de una atención registrada
- **Cuando** busca y elige el código nuevo
- **Entonces** el sistema aplica las mismas reglas de búsqueda y de elección que en el registro
- **Y** la corrección queda con el rastro de HU-009

**Dependencias:** HU-001 y HU-030. Lo usan HU-007 y HU-009.
**Supuestos o asuntos pendientes:** D-09, D-20, D-22, D-23, D-25 y D-26: propuestas del equipo.
Mostrar de 20 en 20 es regla del equipo.

**Trazabilidad (no va en la tarjeta):**
- La tarjeta no está en la exportación de Trello del 19 de octubre. Retroalimentación sin verificar
  (tablero): resultado concreto para el antiguo escenario 4 con D-22: escenario 4; búsqueda sin
  resultados: escenario 6; códigos inactivos: escenario 7; enlazar o incluir RC-1 y RC-2: copiadas en
  las reglas.
- El actor cambia de "responsable de catálogos" a médico tratante, porque quien busca y elige un
  diagnóstico es el médico. Lo que el profesional de estadística necesita del catálogo (versión,
  ramas incompletas, comprobación) está en HU-030, escenarios 14,
  15 y 20.
- Escenarios antiguos: el 1 (catálogo completo) sale de esta historia, porque listar todo el catálogo
  es la consulta del profesional de estadística (HU-030, escenario 14); el 2
  (jerarquía) es el 5; el 3 (versión) es el 8; el 4 (código padre, pendiente) es el 4, ya resuelto
  con D-22; el 5 (vacío) es el 9; el 6 (búsqueda) va en el 1 y el 2; el 7 (padre sin hijos) es el
  10; el 8 (consulta del médico) es el actor de la historia; el 9 (sin sesión) lo cubre HU-001
  escenario 4; el 10 (no modifica) es el 12.
- Versión 2 de la tanda (revisión de la tanda 5): resultados de 20 en 20; [2.2] solo para la
  jerarquía, y el numeral 23 y D-22 para los 4 caracteres; "nombre" en lugar de "descripción", como
  dice el anexo; el catálogo vacío deja guardar la atención como borrador (HU-007).
- El título cambia de "Consulta del catálogo de diagnósticos" a "Búsqueda y selección de un
  diagnóstico".
- Tablero, Ajustes por historia: fila con destino HU-031.

---

# HU-039 · Catálogo de modalidades de realización: carga, consulta, actualización e inactivación

**ID:** HU-039
**Título:** Catálogo de modalidades de realización: carga, consulta, actualización e inactivación
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de modalidades de realización con los valores del anexo, consultarlo y agregar, actualizar, inactivar o reactivar una modalidad, siempre igual a la fuente oficial
> Para que el médico tratante registre la modalidad de cada atención con los valores de la Resolución 866

**Valor de negocio:** La modalidad dice si la atención fue intramural, extramural o por
telemedicina, y se registra en cada atención (HU-007). La lista tiene 9 valores, así que es catálogo
con su CRUD [2.3].

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-007
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Cada modalidad tiene un código de 2 caracteres y el nombre que le da el anexo (numeral 18,
  componente modalidad de realización, hoja 16). La lista tiene 9 valores.
- Fuente oficial: la lista del numeral 18, en la hoja 16, que fija los 9 valores. El archivo es esa
  lista en CSV, transcrita por el equipo o tomada de la tabla de SISPRO que la publique; si no
  coinciden, manda la hoja y se guardan sus códigos y nombres (D-21). La primera carga se compara
  con la hoja contando solo las filas aceptadas. El anexo de la Resolución 1888 de 2025 remite a los
  mismos valores de la Resolución 866. Un cambio solo puede venir de una resolución que modifique el
  anexo, que es la referencia oficial del cambio.
- Columnas del archivo: código; nombre. La clave de duplicado es el código.
- El grupo de servicios, del mismo numeral 18, tiene 5 valores y no es catálogo [2.3].
- Al inactivar un valor, el conteo incluye las atenciones en borrador y las anuladas, porque
  conservan el valor; al completar un borrador con un valor inactivo, el sistema pide elegir uno
  activo (HU-007).
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- Operaciones por escenario (el CRUD de la guía [2.3]): carga 1 a 5, 9 y 10; consulta 13 a 18 y 23;
  actualización 8, 10 y 11; inactivación y reactivación 19 a 22 (RC-10, nada se borra); la versión
  nueva, 6 y 7, hace las cuatro; cancelar cualquiera de ellas, 12.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística tiene el archivo con la lista de la hoja 16
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea una modalidad por cada fila aceptada, con esa versión
- **Y** las modalidades quedan disponibles para registrar la modalidad de la atención (HU-007)
- **Y** si un nombre no coincide con el de la hoja, guarda el de la hoja y lo avisa

### Escenario 2: Primera carga que no trae la lista del anexo
- **Dado** que las filas aceptadas de la primera carga no traen uno de los 9 códigos de la hoja 16, o traen uno que la hoja no tiene
- **Cuando** el profesional de estadística sube el archivo
- **Entonces** el sistema dice qué código falta o sobra, y no deja guardar la carga

### Escenario 3: Filas con errores en la primera carga
- **Dado** que en la primera carga el archivo trae los 9 códigos de la hoja 16 y además una fila con un código de 3 caracteres
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema muestra cuántas filas leyó y cuántas aceptó, y lista las rechazadas con su número de fila y su motivo
- **Y** el profesional puede descargar esa lista y elegir entre guardar las filas aceptadas o cancelar la carga completa

### Escenario 4: Archivo rechazado completo
- **Dado** que al archivo le falta la columna del nombre, o su fecha de publicación es futura o anterior a la de la versión vigente
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza el archivo completo
- **Y** dice qué columna falta o qué fecha no sirve, y cuál es la versión vigente

### Escenario 5: Código repetido en el archivo
- **Dado** que dos filas del archivo tienen el mismo código
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza las dos filas con el motivo "código repetido"

### Escenario 6: Versión nueva
- **Dado** que el catálogo tiene una versión vigente y la fuente oficial publicó otra
- **Cuando** el profesional de estadística sube el archivo de la versión nueva
- **Entonces** el sistema muestra, antes de guardar, los códigos nuevos, los que cambian de nombre, los inactivos que vuelven y los que ya no están, y avisa si la versión deshace un cambio oficial registrado después de su fecha de publicación
- **Y** deja anotar qué código nuevo reemplaza a uno que ya no está
- **Y** al confirmar, y confirmar aparte la lista de los que ya no están, crea, actualiza, reactiva e inactiva según esa lista, con los motivos "Vuelve en la versión", "Reemplazado por" o "No está en la versión"
- **Y** la versión nueva queda vigente, y los registros hechos con la anterior siguen igual

### Escenario 7: Versión nueva con filas rechazadas
- **Dado** que el archivo de una versión nueva trae una fila rechazada con un código que ya está en el catálogo
- **Cuando** el profesional de estadística confirma la versión
- **Entonces** el sistema aplica las demás filas
- **Y** deja ese código con su nombre y su estado, sin inactivarlo, y lo muestra en la lista de rechazadas

### Escenario 8: Cambio oficial de código
- **Dado** que la fuente oficial reemplazó el código de una modalidad por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** las atenciones que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 9: Creación por cambio oficial
- **Dado** que la fuente oficial agregó una modalidad después de la versión cargada
- **Cuando** el profesional de estadística la crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema la guarda activa, con esa referencia

### Escenario 10: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 11: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de una modalidad
- **Cuando** el profesional de estadística la actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya la usan no cambian: su consulta muestra el nombre con que se hicieron y también el nuevo (RC-11; HU-008)
- **Y** el nombre anterior queda en el historial de la modalidad

### Escenario 12: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 13: Consulta del catálogo
- **Dado** que el catálogo tiene modalidades cargadas
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada modalidad, cuántas hay activas y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo las activas, solo las inactivas o todas, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 14: Búsqueda por código
- **Dado** que el catálogo tiene modalidades cargadas
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo la modalidad con ese código exacto

### Escenario 15: Búsqueda por nombre con varias coincidencias
- **Dado** que varias modalidades tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todas las que la contienen, ordenadas por nombre

### Escenario 16: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay modalidades que coincidan con la búsqueda"

### Escenario 17: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial
- **Y** mientras tanto el registro de la atención avisa que falta este catálogo (HU-007)

### Escenario 18: Comprobación contra la hoja del anexo
- **Dado** que el profesional de estadística quiere saber si el catálogo sigue igual a la hoja 16
- **Cuando** pide la comprobación
- **Entonces** el sistema, sin cambiar nada, compara el catálogo con los 9 códigos y nombres de la hoja
- **Y** lista los que faltan, los que están inactivos, los que sobran y los nombres distintos

### Escenario 19: Inactivación con motivo
- **Dado** que la fuente oficial retiró una modalidad
- **Cuando** el profesional de estadística la inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántas atenciones la usan y pide confirmar
- **Y** al confirmar la deja inactiva: no se ofrece en registros nuevos y las atenciones que la tienen la conservan

### Escenario 20: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar la modalidad
- **Entonces** el sistema no la inactiva y dice qué falta (RC-10)

### Escenario 21: Reactivación
- **Dado** que una modalidad que está en la versión vigente se inactivó citando una referencia oficial que no la retiraba
- **Cuando** el profesional de estadística la reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** la modalidad vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 22: Reactivación sin respaldo
- **Dado** que una modalidad inactiva no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva la retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no la retiraba
- **Cuando** el profesional de estadística intenta reactivarla
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 23: Historial de una modalidad
- **Dado** que una modalidad tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-007, HU-008 y HU-009.
**Supuestos o asuntos pendientes:**
- D-02, D-18, D-21, D-23 y D-25: propuestas del equipo.

**Trazabilidad (no va en la tarjeta):**
- Historia nueva: fila V-20 del tablero, una lista de 9 valores que por RC-1 tiene su CRUD [2.3]. No
  tiene comentarios del docente porque la tarjeta no existía. Usa los mismos escenarios de las demás
  historias de catálogo, para que todos se prueben igual.
- La primera carga se compara con los códigos de la hoja (escenario 2) y la comprobación también
  (escenario 18): así se prueba que el catálogo está completo. Los cambios sueltos (escenarios 8, 9
  y 11) solo se aceptan con la resolución que modifique el anexo como referencia.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 5).
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.
- Versión 4 de la tanda (2026-10-24): el título pasa de "Catálogo de modalidades de realización:
  carga y mantenimiento" a "Catálogo de modalidades de realización: carga, consulta, actualización e
  inactivación", y el Quiero nombra las cuatro operaciones. Pedido del autor: "mantenimiento" no
  decía cuáles eran, y el docente revisó en la versión 2 una tarjeta por operación (carga, consulta,
  actualización y eliminación). La línea de operaciones usa los nombres del título y pone la versión
  nueva en las cuatro.

---

# HU-040 · Catálogo de vías de ingreso: carga, consulta, actualización e inactivación

**ID:** HU-040
**Título:** Catálogo de vías de ingreso: carga, consulta, actualización e inactivación
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de vías de ingreso con los valores del anexo, consultarlo y agregar, actualizar, inactivar o reactivar una vía de ingreso, siempre igual a la fuente oficial
> Para que el médico tratante registre por dónde llegó el paciente en cada atención con los valores de la Resolución 866

**Valor de negocio:** La vía de ingreso dice por dónde llegó el paciente al servicio y se registra
en cada atención (HU-007). La lista tiene 14 valores, así que es catálogo con su CRUD [2.3].

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-007
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Cada vía tiene un código de 2 caracteres y el nombre que le da el anexo (numeral 20, hojas 16 y
  17). La lista tiene 14 valores.
- Fuente oficial: la lista del numeral 20, en las hojas 16 y 17, que fija los 14 valores. El archivo
  es esa lista en CSV, transcrita por el equipo o tomada de la tabla de SISPRO que la publique; si
  no coinciden, manda la hoja y se guardan sus códigos y nombres (D-21). La primera carga se compara
  con la hoja contando solo las filas aceptadas. El anexo de la Resolución 1888 de 2025 remite a la
  tabla ViaIngresoUsuario de SISPRO; el equipo no ha cotejado su contenido con la hoja. Un cambio
  solo puede venir de una resolución que modifique el anexo, que es la referencia oficial del
  cambio.
- Columnas del archivo: código; nombre. La clave de duplicado es el código.
- Al inactivar un valor, el conteo incluye las atenciones en borrador y las anuladas, porque
  conservan el valor; al completar un borrador con un valor inactivo, el sistema pide elegir uno
  activo (HU-007).
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- Operaciones por escenario (el CRUD de la guía [2.3]): carga 1 a 5, 9 y 10; consulta 13 a 18 y 23;
  actualización 8, 10 y 11; inactivación y reactivación 19 a 22 (RC-10, nada se borra); la versión
  nueva, 6 y 7, hace las cuatro; cancelar cualquiera de ellas, 12.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística tiene el archivo con la lista de las hojas 16 y 17
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea una vía de ingreso por cada fila aceptada, con esa versión
- **Y** las vías de ingreso quedan disponibles para registrar la vía de ingreso de la atención (HU-007)
- **Y** si un nombre no coincide con el de esas hojas, guarda el de esas hojas y lo avisa

### Escenario 2: Primera carga que no trae la lista del anexo
- **Dado** que las filas aceptadas de la primera carga no traen uno de los 14 códigos de las hojas 16 y 17, o traen uno que esas hojas no tienen
- **Cuando** el profesional de estadística sube el archivo
- **Entonces** el sistema dice qué código falta o sobra, y no deja guardar la carga

### Escenario 3: Filas con errores en la primera carga
- **Dado** que en la primera carga el archivo trae los 14 códigos de las hojas 16 y 17 y además una fila con un código de 3 caracteres
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema muestra cuántas filas leyó y cuántas aceptó, y lista las rechazadas con su número de fila y su motivo
- **Y** el profesional puede descargar esa lista y elegir entre guardar las filas aceptadas o cancelar la carga completa

### Escenario 4: Archivo rechazado completo
- **Dado** que al archivo le falta la columna del nombre, o su fecha de publicación es futura o anterior a la de la versión vigente
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza el archivo completo
- **Y** dice qué columna falta o qué fecha no sirve, y cuál es la versión vigente

### Escenario 5: Código repetido en el archivo
- **Dado** que dos filas del archivo tienen el mismo código
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza las dos filas con el motivo "código repetido"

### Escenario 6: Versión nueva
- **Dado** que el catálogo tiene una versión vigente y la fuente oficial publicó otra
- **Cuando** el profesional de estadística sube el archivo de la versión nueva
- **Entonces** el sistema muestra, antes de guardar, los códigos nuevos, los que cambian de nombre, los inactivos que vuelven y los que ya no están, y avisa si la versión deshace un cambio oficial registrado después de su fecha de publicación
- **Y** deja anotar qué código nuevo reemplaza a uno que ya no está
- **Y** al confirmar, y confirmar aparte la lista de los que ya no están, crea, actualiza, reactiva e inactiva según esa lista, con los motivos "Vuelve en la versión", "Reemplazado por" o "No está en la versión"
- **Y** la versión nueva queda vigente, y los registros hechos con la anterior siguen igual

### Escenario 7: Versión nueva con filas rechazadas
- **Dado** que el archivo de una versión nueva trae una fila rechazada con un código que ya está en el catálogo
- **Cuando** el profesional de estadística confirma la versión
- **Entonces** el sistema aplica las demás filas
- **Y** deja ese código con su nombre y su estado, sin inactivarlo, y lo muestra en la lista de rechazadas

### Escenario 8: Cambio oficial de código
- **Dado** que la fuente oficial reemplazó el código de una vía de ingreso por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** las atenciones que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 9: Creación por cambio oficial
- **Dado** que la fuente oficial agregó una vía de ingreso después de la versión cargada
- **Cuando** el profesional de estadística la crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema la guarda activa, con esa referencia

### Escenario 10: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 11: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de una vía de ingreso
- **Cuando** el profesional de estadística la actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya la usan no cambian: su consulta muestra el nombre con que se hicieron y también el nuevo (RC-11; HU-008)
- **Y** el nombre anterior queda en el historial de la vía de ingreso

### Escenario 12: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 13: Consulta del catálogo
- **Dado** que el catálogo tiene vías de ingreso cargadas
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada vía de ingreso, cuántas hay activas y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo las activas, solo las inactivas o todas, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 14: Búsqueda por código
- **Dado** que el catálogo tiene vías de ingreso cargadas
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo la vía de ingreso con ese código exacto

### Escenario 15: Búsqueda por nombre con varias coincidencias
- **Dado** que varias vías de ingreso tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todas las que la contienen, ordenadas por nombre

### Escenario 16: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay vías de ingreso que coincidan con la búsqueda"

### Escenario 17: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial
- **Y** mientras tanto el registro de la atención avisa que falta este catálogo (HU-007)

### Escenario 18: Comprobación contra la hoja del anexo
- **Dado** que el profesional de estadística quiere saber si el catálogo sigue igual a las hojas 16 y 17
- **Cuando** pide la comprobación
- **Entonces** el sistema, sin cambiar nada, compara el catálogo con los 14 códigos y nombres de la hoja
- **Y** lista los que faltan, los que están inactivos, los que sobran y los nombres distintos

### Escenario 19: Inactivación con motivo
- **Dado** que la fuente oficial retiró una vía de ingreso
- **Cuando** el profesional de estadística la inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántas atenciones la usan y pide confirmar
- **Y** al confirmar la deja inactiva: no se ofrece en registros nuevos y las atenciones que la tienen la conservan

### Escenario 20: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar la vía de ingreso
- **Entonces** el sistema no la inactiva y dice qué falta (RC-10)

### Escenario 21: Reactivación
- **Dado** que una vía de ingreso que está en la versión vigente se inactivó citando una referencia oficial que no la retiraba
- **Cuando** el profesional de estadística la reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** la vía de ingreso vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 22: Reactivación sin respaldo
- **Dado** que una vía de ingreso inactiva no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva la retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no la retiraba
- **Cuando** el profesional de estadística intenta reactivarla
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 23: Historial de una vía de ingreso
- **Dado** que una vía de ingreso tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-007, HU-008 y HU-009.
**Supuestos o asuntos pendientes:**
- D-02, D-18, D-21, D-23 y D-25: propuestas del equipo.

**Trazabilidad (no va en la tarjeta):**
- Historia nueva: fila V-23 del tablero, una lista de 14 valores que por RC-1 tiene su CRUD [2.3].
  No tiene comentarios del docente porque la tarjeta no existía. Usa los mismos escenarios de las
  demás historias de catálogo, para que todos se prueben igual.
- La primera carga se compara con los códigos de la hoja (escenario 2) y la comprobación también
  (escenario 18): así se prueba que el catálogo está completo. Los cambios sueltos (escenarios 8, 9
  y 11) solo se aceptan con la resolución que modifique el anexo como referencia.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 5).
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.
- Versión 4 de la tanda (2026-10-24): el título pasa de "Catálogo de vías de ingreso: carga y
  mantenimiento" a "Catálogo de vías de ingreso: carga, consulta, actualización e inactivación", y
  el Quiero nombra las cuatro operaciones. Pedido del autor: "mantenimiento" no decía cuáles eran, y
  el docente revisó en la versión 2 una tarjeta por operación (carga, consulta, actualización y
  eliminación). La línea de operaciones usa los nombres del título y pone la versión nueva en las
  cuatro.

---

# HU-041 · Catálogo de causas que motivan la atención: carga, consulta, actualización e inactivación

**ID:** HU-041
**Título:** Catálogo de causas que motivan la atención: carga, consulta, actualización e inactivación
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de causas que motivan la atención con los valores del anexo, consultarlo y agregar, actualizar, inactivar o reactivar una causa, siempre igual a la fuente oficial
> Para que el médico tratante registre la causa que motiva cada atención con los valores de la Resolución 866

**Valor de negocio:** La causa que motiva la atención se registra en cada atención (HU-007). La
lista tiene 29 valores, así que es catálogo con su CRUD [2.3].

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-007
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Cada causa tiene un código de 2 caracteres y el nombre que le da el anexo (numeral 21, hoja 17).
  La lista tiene 29 valores, con códigos del 21 al 49.
- Fuente oficial: la lista del numeral 21, en la hoja 17, que fija los 29 valores. El archivo es esa
  lista en CSV, transcrita por el equipo o tomada de la tabla de SISPRO que la publique; si no
  coinciden, manda la hoja y se guardan sus códigos y nombres (D-21). La primera carga se compara
  con la hoja contando solo las filas aceptadas. El anexo de la Resolución 1888 de 2025 remite a la
  tabla RIPSCausaExterna de SISPRO; el equipo no ha cotejado su contenido con la hoja. Un cambio
  solo puede venir de una resolución que modifique el anexo, que es la referencia oficial del
  cambio.
- Columnas del archivo: código; nombre. La clave de duplicado es el código.
- Al inactivar un valor, el conteo incluye las atenciones en borrador y las anuladas, porque
  conservan el valor; al completar un borrador con un valor inactivo, el sistema pide elegir uno
  activo (HU-007).
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- Operaciones por escenario (el CRUD de la guía [2.3]): carga 1 a 5, 9 y 10; consulta 13 a 18 y 23;
  actualización 8, 10 y 11; inactivación y reactivación 19 a 22 (RC-10, nada se borra); la versión
  nueva, 6 y 7, hace las cuatro; cancelar cualquiera de ellas, 12.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística tiene el archivo con la lista de la hoja 17
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea una causa por cada fila aceptada, con esa versión
- **Y** las causas quedan disponibles para registrar la causa de la atención (HU-007)
- **Y** si un nombre no coincide con el de la hoja, guarda el de la hoja y lo avisa

### Escenario 2: Primera carga que no trae la lista del anexo
- **Dado** que las filas aceptadas de la primera carga no traen uno de los 29 códigos de la hoja 17, o traen uno que la hoja no tiene
- **Cuando** el profesional de estadística sube el archivo
- **Entonces** el sistema dice qué código falta o sobra, y no deja guardar la carga

### Escenario 3: Filas con errores en la primera carga
- **Dado** que en la primera carga el archivo trae los 29 códigos de la hoja 17 y además una fila con un código de 3 caracteres
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema muestra cuántas filas leyó y cuántas aceptó, y lista las rechazadas con su número de fila y su motivo
- **Y** el profesional puede descargar esa lista y elegir entre guardar las filas aceptadas o cancelar la carga completa

### Escenario 4: Archivo rechazado completo
- **Dado** que al archivo le falta la columna del nombre, o su fecha de publicación es futura o anterior a la de la versión vigente
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza el archivo completo
- **Y** dice qué columna falta o qué fecha no sirve, y cuál es la versión vigente

### Escenario 5: Código repetido en el archivo
- **Dado** que dos filas del archivo tienen el mismo código
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza las dos filas con el motivo "código repetido"

### Escenario 6: Versión nueva
- **Dado** que el catálogo tiene una versión vigente y la fuente oficial publicó otra
- **Cuando** el profesional de estadística sube el archivo de la versión nueva
- **Entonces** el sistema muestra, antes de guardar, los códigos nuevos, los que cambian de nombre, los inactivos que vuelven y los que ya no están, y avisa si la versión deshace un cambio oficial registrado después de su fecha de publicación
- **Y** deja anotar qué código nuevo reemplaza a uno que ya no está
- **Y** al confirmar, y confirmar aparte la lista de los que ya no están, crea, actualiza, reactiva e inactiva según esa lista, con los motivos "Vuelve en la versión", "Reemplazado por" o "No está en la versión"
- **Y** la versión nueva queda vigente, y los registros hechos con la anterior siguen igual

### Escenario 7: Versión nueva con filas rechazadas
- **Dado** que el archivo de una versión nueva trae una fila rechazada con un código que ya está en el catálogo
- **Cuando** el profesional de estadística confirma la versión
- **Entonces** el sistema aplica las demás filas
- **Y** deja ese código con su nombre y su estado, sin inactivarlo, y lo muestra en la lista de rechazadas

### Escenario 8: Cambio oficial de código
- **Dado** que la fuente oficial reemplazó el código de una causa por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** las atenciones que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 9: Creación por cambio oficial
- **Dado** que la fuente oficial agregó una causa después de la versión cargada
- **Cuando** el profesional de estadística la crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema la guarda activa, con esa referencia

### Escenario 10: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 11: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de una causa
- **Cuando** el profesional de estadística la actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya la usan no cambian: su consulta muestra el nombre con que se hicieron y también el nuevo (RC-11; HU-008)
- **Y** el nombre anterior queda en el historial de la causa

### Escenario 12: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 13: Consulta del catálogo
- **Dado** que el catálogo tiene causas cargadas
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada causa, cuántas hay activas y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo las activas, solo las inactivas o todas, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 14: Búsqueda por código
- **Dado** que el catálogo tiene causas cargadas
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo la causa con ese código exacto

### Escenario 15: Búsqueda por nombre con varias coincidencias
- **Dado** que varias causas tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todas las que la contienen, ordenadas por nombre

### Escenario 16: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay causas que coincidan con la búsqueda"

### Escenario 17: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial
- **Y** mientras tanto el registro de la atención avisa que falta este catálogo (HU-007)

### Escenario 18: Comprobación contra la hoja del anexo
- **Dado** que el profesional de estadística quiere saber si el catálogo sigue igual a la hoja 17
- **Cuando** pide la comprobación
- **Entonces** el sistema, sin cambiar nada, compara el catálogo con los 29 códigos y nombres de la hoja
- **Y** lista los que faltan, los que están inactivos, los que sobran y los nombres distintos

### Escenario 19: Inactivación con motivo
- **Dado** que la fuente oficial retiró una causa
- **Cuando** el profesional de estadística la inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántas atenciones la usan y pide confirmar
- **Y** al confirmar la deja inactiva: no se ofrece en registros nuevos y las atenciones que la tienen la conservan

### Escenario 20: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar la causa
- **Entonces** el sistema no la inactiva y dice qué falta (RC-10)

### Escenario 21: Reactivación
- **Dado** que una causa que está en la versión vigente se inactivó citando una referencia oficial que no la retiraba
- **Cuando** el profesional de estadística la reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** la causa vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 22: Reactivación sin respaldo
- **Dado** que una causa inactiva no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva la retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no la retiraba
- **Cuando** el profesional de estadística intenta reactivarla
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 23: Historial de una causa
- **Dado** que una causa tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-007, HU-008 y HU-009.
**Supuestos o asuntos pendientes:**
- D-02, D-18, D-21, D-23 y D-25: propuestas del equipo.

**Trazabilidad (no va en la tarjeta):**
- Historia nueva: fila V-24 del tablero, una lista de 29 valores que por RC-1 tiene su CRUD [2.3].
  No tiene comentarios del docente porque la tarjeta no existía. Usa los mismos escenarios de las
  demás historias de catálogo, para que todos se prueben igual.
- La primera carga se compara con los códigos de la hoja (escenario 2) y la comprobación también
  (escenario 18): así se prueba que el catálogo está completo. Los cambios sueltos (escenarios 8, 9
  y 11) solo se aceptan con la resolución que modifique el anexo como referencia.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 5).
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.
- Versión 4 de la tanda (2026-10-24): el título pasa de "Catálogo de causas que motivan la atención:
  carga y mantenimiento" a "Catálogo de causas que motivan la atención: carga, consulta,
  actualización e inactivación", y el Quiero nombra las cuatro operaciones. Pedido del autor:
  "mantenimiento" no decía cuáles eran, y el docente revisó en la versión 2 una tarjeta por
  operación (carga, consulta, actualización y eliminación). La línea de operaciones usa los nombres
  del título y pone la versión nueva en las cuatro.

---

# HU-042 · Catálogo de enfermedades huérfanas: carga, consulta, actualización e inactivación

**ID:** HU-042
**Título:** Catálogo de enfermedades huérfanas: carga, consulta, actualización e inactivación
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de enfermedades huérfanas desde el listado oficial, consultarlo y agregar, actualizar, inactivar o reactivar una enfermedad, siempre igual a la fuente
> Para que el médico tratante registre la enfermedad huérfana del paciente con el código del listado vigente

**Valor de negocio:** Cuando el paciente presenta una enfermedad huérfana, la atención la registra
con su código (HU-007). Sin el listado vigente cargado ese dato no se puede registrar.

**Prioridad:** Media
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-007
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Cada enfermedad tiene un código de 1 a 4 caracteres (numeral 23, segunda fila del componente
  código del diagnóstico principal, hoja 18) y su nombre.
- Fuente oficial: el listado de enfermedades huérfanas vigente del Ministerio de Salud, hoy el de la
  Resolución 2625 de 2025, versión 5.0, que derogó la 023 de 2023 (D-14). Cada enfermedad tiene en
  el listado un número propio, que es el código que se registra: van del 1 al 2273 y caben en los 1
  a 4 caracteres del numeral 23 (inferencia del equipo).
- Columnas del archivo: número (es el código); nombre. La columna del código CIE-10 que trae el
  listado se ignora. La clave de duplicado es el código.
- Este catálogo es distinto del CIE-10: la hoja 18 lo pone como segunda fila del código del
  diagnóstico principal, con otras longitudes. Que se registre además del código CIE-10, y no en su
  lugar, es lectura del equipo por la palabra "incluir" de la hoja (HU-007).
- Un número inactivo no se vuelve a usar para otra enfermedad (artículo 3 de la Resolución 2625 de
  2025): crear otra enfermedad con ese número se rechaza porque el código ya existe.
- Al inactivar un valor, el conteo incluye las atenciones en borrador y las anuladas, porque
  conservan el valor; al completar un borrador con un valor inactivo, el sistema pide elegir uno
  activo (HU-007).
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- Operaciones por escenario (el CRUD de la guía [2.3]): carga 1 a 4, 7 y 8; consulta 11 a 16 y 21;
  actualización 8 y 9; inactivación y reactivación 17 a 20 (RC-10, nada se borra); la versión nueva,
  5 y 6, hace las cuatro; cancelar cualquiera de ellas, 10.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística descargó el archivo oficial
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea una enfermedad huérfana por cada fila aceptada, con esa versión
- **Y** las enfermedades huérfanas quedan disponibles para registrar la enfermedad huérfana de la atención cuando el paciente la presenta (HU-007)

### Escenario 2: Filas con errores en la primera carga
- **Dado** que en la primera carga el archivo tiene una fila con un código de 5 caracteres y otra sin nombre
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema muestra cuántas filas leyó y cuántas aceptó, y lista las rechazadas con su número de fila y su motivo
- **Y** el profesional puede descargar esa lista y elegir entre guardar las filas aceptadas o cancelar la carga completa
- **Y** si guardó las aceptadas, puede subir el archivo corregido de la misma versión para completar la carga, sin inactivar nada (RC-9)

### Escenario 3: Archivo rechazado completo
- **Dado** que al archivo le falta la columna del nombre, o su fecha de publicación es futura o anterior a la de la versión vigente
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza el archivo completo
- **Y** dice qué columna falta o qué fecha no sirve, y cuál es la versión vigente

### Escenario 4: Código repetido en el archivo
- **Dado** que dos filas del archivo tienen el mismo código
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza las dos filas con el motivo "código repetido"

### Escenario 5: Versión nueva
- **Dado** que el catálogo tiene una versión vigente y la fuente oficial publicó otra
- **Cuando** el profesional de estadística sube el archivo de la versión nueva
- **Entonces** el sistema muestra, antes de guardar, los códigos nuevos, los que cambian de nombre, los inactivos que vuelven y los que ya no están, y avisa si la versión deshace un cambio oficial registrado después de su fecha de publicación
- **Y** deja anotar qué código nuevo reemplaza a uno que ya no está
- **Y** al confirmar, y confirmar aparte la lista de los que ya no están, crea, actualiza, reactiva e inactiva según esa lista, con los motivos "Vuelve en la versión", "Reemplazado por" o "No está en la versión"
- **Y** la versión nueva queda vigente, y los registros hechos con la anterior siguen igual

### Escenario 6: Versión nueva con filas rechazadas
- **Dado** que el archivo de una versión nueva trae una fila rechazada con un código que ya está en el catálogo
- **Cuando** el profesional de estadística confirma la versión
- **Entonces** el sistema aplica las demás filas
- **Y** deja ese código con su nombre y su estado, sin inactivarlo, y lo muestra en la lista de rechazadas

### Escenario 7: Creación por cambio oficial
- **Dado** que la fuente oficial agregó una enfermedad huérfana después de la versión cargada
- **Cuando** el profesional de estadística la crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema la guarda activa, con esa referencia

### Escenario 8: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 9: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de una enfermedad huérfana
- **Cuando** el profesional de estadística la actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya la usan no cambian: su consulta muestra el nombre con que se hicieron y también el nuevo (RC-11; HU-008)
- **Y** el nombre anterior queda en el historial de la enfermedad huérfana

### Escenario 10: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 11: Consulta del catálogo
- **Dado** que el catálogo tiene enfermedades huérfanas cargadas
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada enfermedad huérfana, cuántas hay activas y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo las activas, solo las inactivas o todas, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 12: Búsqueda por código
- **Dado** que el catálogo tiene enfermedades huérfanas cargadas
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo la enfermedad huérfana con ese código exacto

### Escenario 13: Búsqueda por nombre con varias coincidencias
- **Dado** que varias enfermedades huérfanas tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todas las que la contienen, ordenadas por nombre

### Escenario 14: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay enfermedades huérfanas que coincidan con la búsqueda"

### Escenario 15: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial
- **Y** mientras tanto el registro de la atención sigue sin este dato, porque es opcional (HU-007)

### Escenario 16: Comprobación contra el archivo oficial
- **Dado** que el profesional de estadística descargó de nuevo el archivo oficial
- **Cuando** lo sube para comprobar el catálogo
- **Entonces** el sistema, sin cambiar nada, lista los códigos del archivo que faltan en el catálogo, los que están inactivos en el catálogo, los activos que no están en el archivo y los nombres distintos

### Escenario 17: Inactivación con motivo
- **Dado** que la fuente oficial retiró una enfermedad huérfana
- **Cuando** el profesional de estadística la inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántas atenciones la usan y pide confirmar
- **Y** al confirmar la deja inactiva: no se ofrece en registros nuevos y las atenciones que la tienen la conservan

### Escenario 18: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar la enfermedad huérfana
- **Entonces** el sistema no la inactiva y dice qué falta (RC-10)

### Escenario 19: Reactivación
- **Dado** que una enfermedad huérfana que está en la versión vigente se inactivó citando una referencia oficial que no la retiraba
- **Cuando** el profesional de estadística la reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** la enfermedad huérfana vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 20: Reactivación sin respaldo
- **Dado** que una enfermedad huérfana inactiva no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva la retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no la retiraba
- **Cuando** el profesional de estadística intenta reactivarla
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 21: Historial de una enfermedad huérfana
- **Dado** que una enfermedad huérfana tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-007, HU-008 y HU-009.
**Supuestos o asuntos pendientes:**
- D-02, D-14, D-18, D-23 y D-25: propuestas del equipo.
- Si el Ministerio publica el listado solo en PDF, en su Repositorio Institucional Digital (artículo
  5 de la Resolución 2625 de 2025), el equipo lo transcribe a CSV sin cambiar ningún número ni
  nombre.
- Si una fila del archivo oficial no cumple su numeral, no se carga y sale en la lista de rechazadas
  (RC-8); qué manda en ese caso, la fuente o el numeral, es pregunta al docente.

**Trazabilidad (no va en la tarjeta):**
- Historia nueva: fila V-27 del tablero, un catálogo externo que por RC-1 tiene su CRUD [2.3]. No
  tiene comentarios del docente porque la tarjeta no existía. Usa los mismos escenarios de las demás
  historias de catálogo, para que todos se prueben igual.
- No tiene escenario de cambio de código: el artículo 3 de la Resolución 2625 de 2025 dice que el
  número "es exclusivo y no puede ser asignado a ninguna otra" enfermedad; que una enfermedad no
  cambie de número es lectura del equipo.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 5).
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.
- Versión 4 de la tanda (2026-10-24): el título pasa de "Catálogo de enfermedades huérfanas: carga y
  mantenimiento" a "Catálogo de enfermedades huérfanas: carga, consulta, actualización e
  inactivación", y el Quiero nombra las cuatro operaciones. Pedido del autor: "mantenimiento" no
  decía cuáles eran, y el docente revisó en la versión 2 una tarjeta por operación (carga, consulta,
  actualización y eliminación). La línea de operaciones usa los nombres del título y pone la versión
  nueva en las cuatro.

---

## Registro

| Fecha | Versión | Cambio |
|---|---|---|
| 2026-10-20 | 1 | Primera escritura: HU-030 a HU-033 editadas con los ajustes del tablero y los comentarios del docente; HU-039 a HU-042, nuevas; propuestas para D-09, D-14 y D-22 |
| 2026-10-20 | 2 | Correcciones de la revisión independiente (`revisiones/revision-tanda-5.md`): HU-032 y HU-033 se funden en HU-030, que pasa a la plantilla común de catálogo; las atenciones conservan el nombre del diagnóstico (RC-11); categoría leída de los 3 primeros caracteres (D-26), sin columna de padre ni ciclos; estructura del archivo de SISPRO como supuesto por verificar; borradores con valores inactivos; fuentes de modalidad, vía de ingreso y causa; HU-042 sin cambio de código (artículo 3 de la Resolución 2625); catálogo vacío sin bloquear la atención; HU-031 con resultados de 20 en 20 |
| 2026-10-20 | 2 (continuación) | Revisión final cruzada (`revisiones/revision-final-cruzada.md`): categorías validadas con la regla de HU-030; categorías del plan B como fuente aparte; trazabilidad de HU-030 corregida (HU-032 sin retroalimentación del docente, bloqueo de uso, código que ya existe, historial de versiones); HU-031 con RC-1 |
| 2026-10-20 | 2 (verificación) | Verificación de la aplicación (`revisiones/verificacion-aplicacion-final.md`): las categorías del plan B se cargan antes que la tabla y su versión vigente es la de su fuente; reactivación sin respaldo con el caso de la referencia oficial; el código de una fila rechazada conserva su nombre y su estado; en las listas del anexo, el nombre distinto pasa a la primera carga |
| 2026-10-21 | 3 | Límite de 12 historias: las historias de catálogo dejan de depender del inicio de sesión; el escenario "Cargo sin permiso" pasa a HU-001 (escenario 14) y el historial guarda motivo, referencia, fecha y hora (quién lo hizo lo agrega HU-001, escenario 9) |
| 2026-10-24 | 4 | Enunciados de las historias de catálogo: el título nombra las cuatro operaciones (carga, consulta, actualización e inactivación) en lugar de "carga y mantenimiento", el Quiero las nombra con el límite de la fuente oficial, y la línea de operaciones por escenario usa esos nombres y pone la versión nueva en las cuatro |
