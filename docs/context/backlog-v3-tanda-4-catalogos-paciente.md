# Backlog IPS Salud y Vida, versión 3. Tanda 4: catálogos del paciente

Versión 4, 2026-10-22. Borrador corregido con la revisión independiente
(`revisiones/revision-tanda-4.md`) y con la revisión final cruzada
(`revisiones/revision-final-cruzada.md`). HU-018, HU-022, HU-026 y HU-034 a HU-038: los ocho catálogos de
los datos del paciente. Las reglas comunes (RC), la matriz de permisos, el glosario y las
convenciones están en la tanda 0 (versión 4). El actor de las ocho historias es el profesional de
estadística.

## Cómo están hechas

- **Un catálogo, una historia (D-17).** El Prompt 2 dice "No dividas historias únicamente por: [...]
  Operaciones de base de datos". Cada historia junta las cuatro operaciones del CRUD [2.3]: cargar y
  crear, consultar, cambiar e inactivar. Las cuatro tarjetas antiguas de países, ocupaciones y
  municipios se funden en la de ID más bajo, y su sección de trazabilidad dice a qué escenario nuevo
  pasó cada escenario antiguo.
- **Los mismos escenarios en todas.** Las ocho historias comparten los escenarios de carga, versión,
  cambio oficial, consulta, búsqueda, comprobación, inactivación, reactivación, permisos e historial.
  Así el equipo construye una sola pantalla de catálogo y la prueba igual en todos. Las particulares
  van aparte: grupos leídos del código en ocupaciones, cero inicial y departamento en municipios, y
  comparación con la hoja del anexo en tipos de documento, discapacidad y etnias.
- **Crear y cambiar solo por cambio oficial.** El artículo 7 de la Resolución 866 prohíbe a los
  prestadores modificar, reducir o adicionar los catálogos. Por eso la C y la U del CRUD piden la
  referencia oficial del cambio, y la D es inactivar con un motivo de una lista cerrada (RC-10, D-20).
- **Cómo se cargan los valores.** El docente lo pidió en HU-018, HU-022, HU-026 y HU-030: formato de
  importación, campos, clave de duplicado, versión, validación contra la fuente oficial y reporte de
  rechazados. La respuesta son RC-8 (carga), RC-9 (versión nueva) y RC-10 (inactivar y reactivar),
  que cada tarjeta enlaza en la tarjeta "Reglas comunes", más las columnas de cada catálogo.
- **Validar contra la fuente oficial, no solo la longitud.** Lo pide el docente en HU-018 c2 y HU-022
  c4. El sistema no puede consultar a la ISO ni al DANE. Lo que hace es guardar de dónde y cuándo se
  descargó cada archivo (RC-8); en las listas del anexo, comparar la primera carga con los códigos
  de la hoja; y comparar el catálogo con un archivo oficial recién descargado (escenario
  "Comprobación contra el archivo oficial"), que muestra lo que falta, lo que está inactivo, lo que
  sobra y los nombres distintos. Contar los valores no prueba que estén todos (HU-019 c2).

## Decisiones que esta tanda propone

| Decisión | Propuesta | Por qué |
|---|---|---|
| D-02, cargo | Profesional de estadística de la IPS. Carga, crea y cambia por cambio oficial, inactiva y reactiva; no ve datos de pacientes ni de atenciones, solo cuántos registros usan un valor | El docente pide "Definir un cargo funcional real de la IPS, su función y permisos" (c2 de HU-018 a HU-030) y dice que "responsable de catálogos" no es un cargo de su IPS (HU-022 c2). Se supone que el área de estadística es la que reporta los datos de la IPS con las tablas oficiales; no hay fuente. Pregunta al docente |
| D-03, código de país | El numérico de 3 dígitos de la ISO 3166-1: Colombia es 170. Una fila con letras en el código se rechaza | El numeral 1 dice "Código a tres caracteres según estándar ISO 3166-1", con formato 999 (hoja 13), y el anexo de la Resolución 1888 de 2025 remite a la tabla Pais de SISPRO. Que esa tabla use el numérico se confirma al descargarla |
| D-18, cómo se guardan los códigos | Con la longitud del numeral y sus ceros iniciales: Medellín es 05001 | El anexo da los códigos como alfanuméricos; como número, 05001 pierde el cero |
| D-21, fuente, versión y formato | Fuentes: la tabla Pais de SISPRO (ISO 3166-1); la CIUO-88 A.C. y la DIVIPOLA del DANE; el catálogo de referencia de comunidades étnicas y el de entidades del Ministerio; las listas del anexo, en su hoja: si el archivo no coincide con la hoja, manda la hoja y se guardan sus códigos y nombres. Formato: RC-8. Versión nueva y recarga: RC-9 | El artículo 8 de la Resolución 866 pone los catálogos a cargo del Ministerio de Salud, que definirá el procedimiento para individualizarlos en SISPRO. El anexo de la Resolución 1888 de 2025 remite a tablas de SISPRO para país, municipio, etnia, discapacidad y ocupación; para la comunidad étnica dice "Texto Alfanumérico" |
| D-23, búsqueda | Por código, exacta. Por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas las coincidencias | El docente pide definir si la búsqueda es exacta o parcial (HU-019 c1, HU-023 c1, HU-027 c1) |
| D-25, nombre del momento del registro | RC-11: un registro conserva el nombre que tenía el valor cuando se registró; si la fuente lo cambia después, la consulta muestra también el vigente | El anexo trae el nombre como componente del dato en los numerales 1, 7, 11, 12, 15 y 23; en los demás catálogos, conservarlo es decisión del equipo. El docente pidió no reinterpretar lo ya registrado al cambiar un código (HU-020 c2, HU-028 c2), y la regla aplica ese criterio al nombre |
| D-26, grupo leído del código | En ocupaciones y en el CIE-10, el padre de cada valor se lee de su código: 2221 está bajo 222; A000 bajo A00. El archivo no necesita columna de padre y no puede haber ciclos | Para la CIUO-88 A.C. lo dice el DANE: "integrada en 4 niveles de agregación, identificados mediante códigos numéricos de 1, 2, 3 y 4 dígitos" (documento de 2006). Para el CIE-10 es inferencia del equipo y se confirma con la tabla de SISPRO |

Nota sobre ocupaciones: el anexo de la Resolución 1888 de 2025 (Diario Oficial 53.244, elemento
7.1) remite el código de la ocupación a la CUOC 2024 del DANE y a la tabla PSRGOCUPACION de SISPRO;
la CIUO-88 no aparece. La guía [2.1], el numeral 7 de la Resolución 866 (hoja 14) y el docente
(HU-022 c4) piden la CIUO-88 A.C., así que el backlog la sigue. Queda como pregunta al docente.

## Pendientes de las tandas antiguas que esta tanda cierra

| Pendiente antiguo | Cómo queda |
|---|---|
| De dónde se obtienen los catálogos oficiales | D-21 |
| Cuántos niveles tiene la CIUO-88 A.C. | Cuatro, con códigos de 1 a 4 dígitos: 10, 34, 117 y 401 (DANE, 2006). El grupo se lee del código (D-26), la primera carga se compara con esas cantidades y solo se registran las de 4 dígitos |
| Qué hacer con los registros que usan un valor que debe retirarse | Se inactiva y los registros lo conservan (RC-4, D-20); un grupo con ocupaciones activas no se inactiva |
| Qué hacer cuando un municipio no trae código DANE | La fila se rechaza, porque no cumple el numeral 12 (RC-8) |

## Escenarios por historia

| Historia | Escenarios | Particulares |
|---|---|---|
| HU-018 Catálogo de países: carga y mantenimiento | 22 | Código numérico de país (D-03) |
| HU-022 Catálogo de ocupaciones: carga y mantenimiento | 28 | Grupo leído del código, grupo y ocupación en el mismo archivo, ocupación sin su grupo, cantidades por nivel, jerarquía, grupo con ocupaciones activas, ocupación con su grupo inactivo |
| HU-026 Catálogo de municipios: carga y mantenimiento | 25 | Cero inicial, departamento, municipios con el mismo nombre |
| HU-034 Catálogo de tipos de documento: carga y mantenimiento | 23 | Lista del anexo: primera carga y comprobación contra la hoja 13 |
| HU-035 Catálogo de categorías de discapacidad: carga y mantenimiento | 23 | Lista del anexo: primera carga y comprobación contra las hojas 14 y 15 |
| HU-036 Catálogo de etnias: carga y mantenimiento | 23 | Lista del anexo: primera carga y comprobación contra la hoja 15 |
| HU-037 Catálogo de comunidades étnicas: carga y mantenimiento | 22 | Archivo oficial por verificar |
| HU-038 Catálogo de entidades que responden por el plan de beneficios: carga y mantenimiento | 22 | Secretarías de salud para el paciente particular (regla de HU-004) |
| **Total** | **188** | |

---

# HU-018 · Catálogo de países: carga y mantenimiento

**ID:** HU-018
**Título:** Catálogo de países: carga y mantenimiento
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de países desde su archivo oficial y mantenerlo igual a la norma ISO 3166-1
> Para que la nacionalidad y la residencia del paciente se registren con los códigos que pide la Resolución 866

**Valor de negocio:** Sin este catálogo no se puede registrar la nacionalidad ni la residencia
habitual del paciente (HU-004). Un código que no es el de la norma impide intercambiar los datos con
otros prestadores.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Cada país tiene un código de 3 caracteres y un nombre de 3 a 200 caracteres (numerales 1 y 11 del
  anexo, hojas 13 y 15). Un solo catálogo sirve para la nacionalidad y para la residencia habitual.
- Fuente oficial: la tabla Pais de SISPRO, del Ministerio de Salud, que sigue la norma ISO 3166-1
  (D-21). El código es el numérico de 3 dígitos de esa norma: Colombia es 170 (D-03, propuesta). Una
  fila con letras en el código se rechaza.
- Columnas del archivo: código; nombre. La clave de duplicado es el código.
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- CRUD por escenario: crear 1 a 6, 8 y 9; leer 12 a 17 y 22; actualizar 7, 9 y 10; inactivar y
  reactivar 18 a 21 (RC-10, nada se borra); cancelar cualquiera de ellas 11.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística descargó el archivo oficial
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea un país por cada fila aceptada, con esa versión
- **Y** los países quedan activos para los registros nuevos

### Escenario 2: Filas con errores en la primera carga
- **Dado** que en la primera carga el archivo tiene una fila con un código de 2 caracteres y otra sin nombre
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

### Escenario 7: Cambio oficial de código
- **Dado** que la fuente oficial reemplazó el código de un país por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** los pacientes que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 8: Creación por cambio oficial
- **Dado** que la fuente oficial agregó un país después de la versión cargada
- **Cuando** el profesional de estadística lo crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema lo guarda activo, con esa referencia

### Escenario 9: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 10: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de un país
- **Cuando** el profesional de estadística lo actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya lo usan no cambian: conservan el código y el nombre con que se hicieron (RC-11)
- **Y** el nombre anterior queda en el historial del país

### Escenario 11: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 12: Consulta del catálogo
- **Dado** que el catálogo tiene países cargados
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada país, cuántos hay activos y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo los activos, solo los inactivos o todos, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 13: Búsqueda por código
- **Dado** que el catálogo tiene países cargados
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo el país con ese código exacto

### Escenario 14: Búsqueda por nombre con varias coincidencias
- **Dado** que varios países tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todos los que la contienen, ordenados por nombre

### Escenario 15: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay países que coincidan con la búsqueda"

### Escenario 16: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial

### Escenario 17: Comprobación contra el archivo oficial
- **Dado** que el profesional de estadística descargó de nuevo el archivo oficial
- **Cuando** lo sube para comprobar el catálogo
- **Entonces** el sistema, sin cambiar nada, lista los códigos del archivo que faltan en el catálogo, los que están inactivos en el catálogo, los activos que no están en el archivo y los nombres distintos

### Escenario 18: Inactivación con motivo
- **Dado** que la fuente oficial retiró un país
- **Cuando** el profesional de estadística lo inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántos pacientes lo usan y pide confirmar
- **Y** al confirmar lo deja inactivo: no se ofrece en registros nuevos y los pacientes que lo tienen lo conservan

### Escenario 19: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar el país
- **Entonces** el sistema no lo inactiva y dice qué falta (RC-10)

### Escenario 20: Reactivación
- **Dado** que un país que está en la versión vigente se inactivó citando una referencia oficial que no lo retiraba
- **Cuando** el profesional de estadística lo reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** el país vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 21: Reactivación sin respaldo
- **Dado** que un país inactivo no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva lo retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no lo retiraba
- **Cuando** el profesional de estadística intenta reactivarlo
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 22: Historial de un país
- **Dado** que un país tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-004, HU-005 y HU-006.
**Supuestos o asuntos pendientes:**
- D-02, D-03, D-18, D-21, D-23 y D-25: propuestas del equipo.
- Pendiente de verificar al descargar la tabla Pais: que el código sea el numérico. Si es el
  alfabético (COL), se cambia D-03 antes de la primera carga.
- Si una fila del archivo oficial no cumple su numeral, no se carga y sale en la lista de rechazadas
  (RC-8); qué manda en ese caso, la fuente o el numeral, es pregunta al docente.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-018 c1 (cargo funcional: regla del cargo; formato de importación y
  campos, clave de duplicidad, validación contra la fuente oficial además de la longitud y reporte
  de rechazados: RC-8, columnas y escenarios 1 a 4 y 17; reglas comunes enlazadas) y c2 (cargo con
  función y permisos: regla del cargo y HU-001 escenario 14; validar contra la ISO 3166-1 y no solo
  la longitud: fuente oficial, D-03 y escenario 17, que compara con un archivo recién descargado;
  importación y errores: escenarios 2 a 4).
- HU-019 c1 (búsqueda exacta o parcial y varias coincidencias: escenarios 13 y 14; catálogo vacío:
  escenario 16) y c2 (fuente y versión, y el conteo no prueba que el catálogo esté completo:
  escenarios 12 y 17).
- HU-020 c1 (cambio de código exitoso y su efecto en los registros: escenario 7) y c2
  (correspondencia oficial y versiones: escenarios 5, 6 y 10; no reinterpretar la nacionalidad ni la
  residencia ya registradas: escenarios 7 y 10, con RC-11).
- HU-021 c1 (país en uso con la cantidad de registros: escenario 18) y c2 ("Bien bloquear uso y
  conservar rastro": el valor en uso ya no se bloquea: como nada se borra (D-20), se inactiva con un
  motivo de RC-10 y los registros lo conservan; lo que se bloquea es inactivar sin respaldo,
  escenario 19; inactivación y recarga, motivo y autor, sin retirar valores oficiales sin razón:
  escenarios 18, 19, 20, 21 y 2, con RC-9 y RC-10).
- Escenarios antiguos de HU-018: el 1 (carga de un país) es el 8; el 2 (carga masiva) es el 1; el 3
  (país que ya existe) va en el 9 y el 5; el 4 y el 5 (formato, sin nombre) van en el 2 y el 9; el 6
  (atribución) va en el 22; el 7 (sin sesión) lo cubre HU-001 escenario 4; el 8 (otro cargo) va en
  HU-001 escenario 14; el 9 (se conservan los válidos) va en el 2; el 10 (disponible de inmediato)
  va en el 1.
- De HU-019: el 1 es el 12; el 2 es el 16; el 3 va en el 13 y el 14; el 4 es el 15; el 5 (conteo) va
  en el 12, que muestra cuántos hay activos, y en el 17; el 6 (quién cargó) va en el 22; el 7
  (consulta del auxiliar) va en HU-001 escenario 14; el 8 (sin sesión) lo cubre HU-001 escenario 4;
  el 9 (no modifica) va en el 12; el 10 (eliminados) es el filtro de inactivos del 12.
- De HU-020: el 1 es el 10; el 2 (los registros conservan su referencia) va en el 10 y el 7; el 3,
  el 4 y el 10 (código repetido, formato, valor incompleto) van en el 9; el 5 (país que no existe)
  sale, porque se cambia un país elegido de la lista; el 6 (abandonada) es el 11; el 7 (atribución)
  va en el 22; el 8 (sin sesión) lo cubre HU-001 escenario 4; el 9 (otro cargo) va en HU-001
  escenario 14.
- De HU-021: el 1, el 2, el 3 y el 7 (sin uso, en uso, confirmación, registro después) van en el 18;
  el 4 (cancelada) es el 11; el 5 (rastro) va en el 22; el 6 (valor que no existe) sale, porque se
  inactiva un país elegido de la lista; el 8 (sin sesión) lo cubre HU-001 escenario 4; el 9 (otro
  cargo) va en HU-001 escenario 14; el 10 (recarga) es el 20.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 4).
- Se fusionan HU-018 a HU-021 (D-17): las tarjetas HU-019 a HU-021 se archivan con una nota que
  remite a HU-018. El título pasa a "Catálogo de países: carga y mantenimiento".
- Tablero, Ajustes por historia: filas con destino HU-018 a HU-021 y la fila general de catálogos.
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.

---

# HU-022 · Catálogo de ocupaciones: carga y mantenimiento

**ID:** HU-022
**Título:** Catálogo de ocupaciones: carga y mantenimiento
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de ocupaciones desde el archivo oficial de la CIUO-88 A.C. y mantenerlo igual a esa clasificación, con sus niveles
> Para que el auxiliar de admisión registre la ocupación del paciente con una ocupación hija, como pide la guía

**Valor de negocio:** La guía exige elegir la ocupación entre las ocupaciones hijas de la CIUO-88
A.C. [2.1]. Sin los niveles cargados no se sabe cuáles son hijas.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Las ocupaciones que se registran al paciente tienen un código de 4 caracteres y un nombre de 3 a
  200 (numeral 7 del anexo, hoja 14).
- Fuente oficial: la CIUO-88 A.C., Clasificación Internacional Uniforme de Ocupaciones adaptada para
  Colombia por el DANE (numeral 7; D-21).
- Columnas del archivo: código; nombre. La clave de duplicado es el código.
- La CIUO-88 A.C. está "integrada en 4 niveles de agregación, identificados mediante códigos
  numéricos de 1, 2, 3 y 4 dígitos": 10 grandes grupos, 34 subgrupos principales, 117 subgrupos y
  401 grupos primarios (DANE, 2006). El código de cada ocupación empieza por el de su grupo: 2221
  Médicos está bajo 222, 222 bajo 22 y 22 bajo 2. El sistema lee el grupo del código, así que el
  archivo no necesita una columna de padre (D-26, propuesta).
- Las filas de 1 a 3 dígitos son grupos: se cargan para la jerarquía y se validan con esta regla, no
  con la longitud del numeral 7 (RC-8). Las de 4 son las ocupaciones hijas, las únicas que se
  registran al paciente [2.1].
- La primera carga se compara con esas cantidades por nivel: cada fila leída cuenta en el nivel que
  da la longitud de su código, aunque se rechace por otro motivo, y así se pueden guardar las
  aceptadas y completar con una recarga (RC-9). Si un nivel no cuadra, no se guarda. La cuenta
  detecta un archivo que perdió los ceros del gran grupo 0 (01, 011, 0110), que el aviso de RC-8 no
  ve.
- Una fila se acepta si su grupo está activo en el catálogo o se acepta en el mismo archivo, sin
  importar el orden de las filas.
- Un grupo con ocupaciones activas no se inactiva, tampoco por una versión nueva. Una ocupación no
  se reactiva con su grupo inactivo.
- Una ocupación no cambia de grupo sin cambiar de código: ese caso es un cambio oficial de código.
- La ocupación también la guarda cada atención, con la que el paciente tenía ese día (D-24). Por
  eso, al inactivar, el sistema cuenta pacientes y atenciones.
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- CRUD por escenario: crear 1 a 9, 11 y 12; leer 15 a 21 y 28; actualizar 10, 12 y 13; inactivar y
  reactivar 22 a 27 (RC-10, nada se borra); cancelar cualquiera de ellas 14.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística descargó el archivo oficial
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea una ocupación por cada fila aceptada, con esa versión
- **Y** las ocupaciones quedan activas para los registros nuevos

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

### Escenario 5: Grupo y ocupación en el mismo archivo
- **Dado** que el archivo trae la ocupación 2221 antes que su grupo 222
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema acepta las dos filas
- **Y** la 2221 queda bajo el grupo 222

### Escenario 6: Ocupación sin su grupo
- **Dado** que el archivo trae la ocupación 2221 y su grupo 222 no está activo en el catálogo ni se acepta en el archivo, por ejemplo porque su fila se rechazó
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza la 2221 con el motivo "su grupo 222 no está activo"

### Escenario 7: Cantidades por nivel en la primera carga
- **Dado** que el archivo de la primera carga perdió los ceros iniciales, por ejemplo el subgrupo principal 01, Fuerzas Militares, llegó como 1
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema cuenta las filas leídas por nivel y dice cuál no cuadra con los de la CIUO-88 A.C.: 10 grandes grupos, 34 subgrupos principales, 117 subgrupos y 401 grupos primarios
- **Y** no deja guardar la carga

### Escenario 8: Versión nueva
- **Dado** que el catálogo tiene una versión vigente y la fuente oficial publicó otra
- **Cuando** el profesional de estadística sube el archivo de la versión nueva
- **Entonces** el sistema muestra, antes de guardar, los códigos nuevos, los que cambian de nombre, los inactivos que vuelven y los que ya no están, y avisa si la versión deshace un cambio oficial registrado después de su fecha de publicación
- **Y** deja anotar qué código nuevo reemplaza a uno que ya no está
- **Y** al confirmar, y confirmar aparte la lista de los que ya no están, crea, actualiza, reactiva e inactiva según esa lista, con los motivos "Vuelve en la versión", "Reemplazado por" o "No está en la versión"
- **Y** la versión nueva queda vigente, y los registros hechos con la anterior siguen igual

### Escenario 9: Versión nueva con filas rechazadas
- **Dado** que el archivo de una versión nueva trae una fila rechazada con un código que ya está en el catálogo
- **Cuando** el profesional de estadística confirma la versión
- **Entonces** el sistema aplica las demás filas
- **Y** deja ese código con su nombre y su estado, sin inactivarlo, y lo muestra en la lista de rechazadas

### Escenario 10: Cambio oficial de código
- **Dado** que la fuente oficial reemplazó el código de una ocupación por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** los pacientes y atenciones que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 11: Creación por cambio oficial
- **Dado** que la fuente oficial agregó una ocupación después de la versión cargada
- **Cuando** el profesional de estadística la crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema la guarda activa, con esa referencia

### Escenario 12: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe, su grupo no está activo en el catálogo o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 13: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de una ocupación
- **Cuando** el profesional de estadística la actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya la usan no cambian: conservan el código y el nombre con que se hicieron (RC-11)
- **Y** si es un grupo, sus ocupaciones siguen bajo él
- **Y** el nombre anterior queda en el historial de la ocupación

### Escenario 14: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 15: Consulta del catálogo
- **Dado** que el catálogo tiene ocupaciones cargadas
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada ocupación, cuántas hay activas y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo las activas, solo las inactivas o todas, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 16: Consulta de la jerarquía
- **Dado** que el catálogo tiene ocupaciones de varios niveles
- **Cuando** el profesional de estadística consulta la jerarquía
- **Entonces** ve cada grupo con sus ocupaciones, y los grupos sin ocupaciones activas marcados "sin ocupaciones"
- **Y** las de 4 caracteres aparecen marcadas como las que se registran al paciente

### Escenario 17: Búsqueda por código
- **Dado** que el catálogo tiene ocupaciones cargadas
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo la ocupación con ese código exacto

### Escenario 18: Búsqueda por nombre con varias coincidencias
- **Dado** que varias ocupaciones tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todas las que la contienen, ordenadas por nombre

### Escenario 19: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay ocupaciones que coincidan con la búsqueda"

### Escenario 20: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial

### Escenario 21: Comprobación contra el archivo oficial
- **Dado** que el profesional de estadística descargó de nuevo el archivo oficial
- **Cuando** lo sube para comprobar el catálogo
- **Entonces** el sistema, sin cambiar nada, lista los códigos del archivo que faltan en el catálogo, los que están inactivos en el catálogo, los activos que no están en el archivo y los nombres distintos

### Escenario 22: Inactivación con motivo
- **Dado** que la fuente oficial retiró una ocupación
- **Cuando** el profesional de estadística la inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántos pacientes y atenciones la usan y pide confirmar
- **Y** al confirmar la deja inactiva: no se ofrece en registros nuevos y los pacientes y atenciones que la tienen la conservan

### Escenario 23: Grupo con ocupaciones activas
- **Dado** que el grupo 222 tiene ocupaciones activas
- **Cuando** el profesional de estadística intenta inactivarlo, o una versión nueva lo retira y deja activas sus ocupaciones
- **Entonces** el sistema no lo inactiva
- **Y** dice cuántas ocupaciones activas tiene

### Escenario 24: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar la ocupación
- **Entonces** el sistema no la inactiva y dice qué falta (RC-10)

### Escenario 25: Reactivación
- **Dado** que una ocupación que está en la versión vigente se inactivó citando una referencia oficial que no la retiraba
- **Cuando** el profesional de estadística la reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** la ocupación vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 26: Ocupación con su grupo inactivo
- **Dado** que el grupo de una ocupación está inactivo
- **Cuando** el profesional de estadística intenta reactivar la ocupación
- **Entonces** el sistema no lo permite
- **Y** dice que primero se reactiva el grupo

### Escenario 27: Reactivación sin respaldo
- **Dado** que una ocupación inactiva no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva la retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no la retiraba
- **Cuando** el profesional de estadística intenta reactivarla
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 28: Historial de una ocupación
- **Dado** que una ocupación tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-004, HU-005 y HU-006, y la atención, que guarda la ocupación
(HU-007 y HU-009).
**Supuestos o asuntos pendientes:**
- D-02, D-18, D-21, D-23, D-24, D-25 y D-26: propuestas del equipo.
- Falta confirmar que el archivo que se descargue traiga los cuatro niveles. Si solo trae las
  ocupaciones de 4 dígitos, los grupos se cargan antes desde el documento del DANE, como fuente
  aparte con su versión (como el plan B de HU-030), y la cuenta por niveles suma las dos cargas.
- El anexo de la Resolución 1888 de 2025 (Diario Oficial 53.244, elemento 7.1) remite la ocupación a
  la CUOC 2024 del DANE y a la tabla PSRGOCUPACION de SISPRO. La guía [2.1] y el numeral 7 de la
  Resolución 866 piden la CIUO-88 A.C., y la historia la sigue; queda como pregunta al docente.
- Si una fila del archivo oficial no cumple su numeral, no se carga y sale en la lista de rechazadas
  (RC-8); qué manda en ese caso, la fuente o el numeral, es pregunta al docente.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-022 c1 ("No es una funcionalidad, no explica como se deben cargar los
  valores": RC-8 y escenarios 1 a 7), c2 ("responsable de catálogos" no es un cargo de la IPS:
  profesional de estadística, D-02), c3 (formato de archivo o formulario, campos código, nombre y
  padre, clave de duplicado, padre e hija en el mismo lote: RC-8, columnas y escenarios 4 y 5; el
  padre no es columna porque se lee del código, D-26) y c4 (validar los códigos oficiales de la
  CIUO-88 A.C. y las reglas de raíces e importación padre e hija: fuente, regla de niveles con las
  cantidades del DANE y escenarios 5, 6, 7 y 21; cargo con función y permisos: regla del cargo y
  HU-001 escenario 14).
- HU-023 c1 (búsqueda exacta o parcial y ocupación que no existe: escenarios 17 a 19) y c2 (fuente y
  versión y ramas incompletas: escenarios 15 y 16).
- HU-024 c1 (padre inexistente o ciclo: el padre inexistente es el escenario 6; un ciclo no puede
  ocurrir, porque el grupo se lee del código; cargo con sesión y sin permiso: HU-001 escenario 14) y
  c2 (relaciones oficiales, ciclos, padre inexistente y referencias históricas: regla de niveles y
  escenarios 6, 10 y 13).
- HU-025 c1 (bloqueos por pacientes y por ocupaciones dependientes: escenarios 22 y 23; cargo sin
  permiso: HU-001 escenario 14) y c2 ("Bien bloqueo de uso y padres con hijas": el valor en uso ya
  no se bloquea: como nada se borra (D-20), se inactiva con un motivo de RC-10 y los registros lo
  conservan; lo que se bloquea es inactivar sin respaldo, escenario 24, y sigue bloqueado el grupo
  con ocupaciones activas, escenario 23; vigencia, recarga, motivo, autor e historial: escenarios
  22, 24, 25, 26 y 28).
- Escenarios antiguos de HU-022: el 1 (carga de una ocupación) es el 11; el 2 (carga masiva) es el
  1; el 3 (jerarquía) va en el 5 y el 16; el 4 (hija sin padre) es el 6; el 5 (al registrar solo se
  ofrecen hijas) lo cubren la regla de niveles y HU-004 escenario 5; el 6 (ocupación que ya existe)
  va en el 12 y el 8; el 7 (sin nombre) y el 10 (se conservan los válidos) van en el 2; el 8
  (atribución) va en el 28; el 9 (sin sesión) lo cubre HU-001 escenario 4.
- De HU-023: el 1 es el 15; el 2, el 3 y el 6 (jerarquía, asociables, padre sin hijas) van en el 16;
  el 4 es el 20; el 5 va en el 17 y el 18; el 7 (quién cargó) va en el 28; el 8 (sin sesión) lo
  cubre HU-001 escenario 4; el 9 (no modifica) y el 10 (eliminados) van en el 15.
- De HU-024: el 1 y el 3 (nombre de una ocupación o de un grupo, cuyas hijas siguen bajo él) son el
  13; el 2 (referencias) va en el 13 y el 10; el 4 (cambio de padre) sale: una ocupación no cambia
  de grupo sin cambiar de código, y eso es el 10; el 5 y el 10 (código repetido, valor incompleto)
  van en el 12; el 6 (ocupación que no existe) sale, porque se cambia una ocupación elegida de la
  lista; el 7 (abandonada) es el 14; el 8 (atribución) va en el 28; el 9 (sin sesión) lo cubre
  HU-001 escenario 4.
- De HU-025: el 1, el 2, el 4 y el 8 (sin uso, en uso, confirmación, registro después) van en el 22;
  el 3 (padre con hijas) es el 23; el 5 (cancelada) es el 14; el 6 (rastro) va en el 28; el 7 (valor
  que no existe) sale; el 9 (sin sesión) lo cubre HU-001 escenario 4; el 10 (recarga) es el 25.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 4). En esta
  historia, además, el grupo se lee del código (D-26): salen el cambio de padre y el ciclo de la
  versión 1.
- Se fusionan HU-022 a HU-025 (D-17): las tarjetas HU-023 a HU-025 se archivan con una nota que
  remite a HU-022. El título pasa a "Catálogo de ocupaciones: carga y mantenimiento".
- Tablero, Ajustes por historia: filas con destino HU-022 a HU-025 y la fila general de catálogos.
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.

---

# HU-026 · Catálogo de municipios: carga y mantenimiento

**ID:** HU-026
**Título:** Catálogo de municipios: carga y mantenimiento
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de municipios desde el archivo oficial de la DIVIPOLA y mantenerlo igual a esa lista del DANE
> Para que la ciudad de residencia del paciente se registre con su código oficial DANE, como pide la guía

**Valor de negocio:** La guía pide registrar la ciudad de residencia junto con su código oficial
DANE [2.1]. Sin el departamento no se distinguen los municipios que tienen el mismo nombre.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Cada municipio tiene un código de 5 caracteres y un nombre de 3 a 200 (numeral 12 del anexo, hoja
  15). Los dos primeros caracteres son el departamento y los tres siguientes el municipio.
- Fuente oficial: la DIVIPOLA, División Político Administrativa de Colombia, del DANE (numeral 12;
  D-21). El anexo de la Resolución 1888 de 2025 remite a la tabla Municipio de SISPRO.
- Columnas del archivo: código del municipio; nombre del municipio; código del departamento; nombre
  del departamento. La clave de duplicado es el código.
- El código tiene 5 dígitos y conserva su cero inicial: Medellín es 05001, no 5001 (D-18). El
  sistema no completa ceros: un código de 4 dígitos se rechaza con el aviso de RC-8, en la carga y
  al crearlo o cambiarlo a mano.
- Los dos primeros caracteres del código del municipio son el código del departamento de la misma
  fila, y cada código de departamento tiene un solo nombre en el archivo.
- Puede haber municipios con el mismo nombre en departamentos distintos. El sistema muestra siempre
  el departamento junto al municipio: en la consulta, en la búsqueda y en el registro del paciente.
- El catálogo es de municipios de Colombia. Si la residencia habitual está fuera de Colombia, el
  municipio no aplica (HU-004; D-19, propuesta).
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- CRUD por escenario: crear 1 a 8, 10 y 11; leer 14 a 20 y 25; actualizar 9, 11 y 12; inactivar y
  reactivar 21 a 24 (RC-10, nada se borra); cancelar cualquiera de ellas 13.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística descargó el archivo oficial
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea un municipio por cada fila aceptada, con esa versión
- **Y** los municipios quedan activos para los registros nuevos

### Escenario 2: Filas con errores en la primera carga
- **Dado** que en la primera carga el archivo tiene una fila con un código de 6 caracteres y otra sin nombre
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema muestra cuántas filas leyó y cuántas aceptó, y lista las rechazadas con su número de fila y su motivo
- **Y** el profesional puede descargar esa lista y elegir entre guardar las filas aceptadas o cancelar la carga completa
- **Y** si guardó las aceptadas, puede subir el archivo corregido de la misma versión para completar la carga, sin inactivar nada (RC-9)

### Escenario 3: Archivo rechazado completo
- **Dado** que al archivo le falta la columna del departamento, o su fecha de publicación es futura o anterior a la de la versión vigente
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza el archivo completo
- **Y** dice qué columna falta o qué fecha no sirve, y cuál es la versión vigente

### Escenario 4: Código repetido en el archivo
- **Dado** que dos filas del archivo tienen el mismo código
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza las dos filas con el motivo "código repetido"

### Escenario 5: Código sin el cero inicial
- **Dado** que el archivo trae el código 5001, que perdió el cero inicial de 05001
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza esa fila con el aviso "revise que el archivo no haya perdido los ceros iniciales"
- **Y** no completa el cero por su cuenta

### Escenario 6: Departamento que no corresponde
- **Dado** que una fila trae un municipio cuyo código no empieza por el código de su departamento, o un departamento con un nombre distinto del que tiene en otras filas
- **Cuando** el profesional de estadística lo sube
- **Entonces** el sistema rechaza esa fila con el motivo "el código no corresponde al departamento" o "el departamento tiene dos nombres"

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
- **Dado** que la fuente oficial reemplazó el código de un municipio por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** los pacientes que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 10: Creación por cambio oficial
- **Dado** que la fuente oficial agregó un municipio después de la versión cargada
- **Cuando** el profesional de estadística lo crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema lo guarda activo, con esa referencia y con el departamento que dan los dos primeros dígitos del código

### Escenario 11: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe, sus dos primeros dígitos no son de un departamento que esté en el catálogo o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 12: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de un municipio
- **Cuando** el profesional de estadística lo actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya lo usan no cambian: conservan el código y el nombre con que se hicieron (RC-11)
- **Y** el nombre anterior queda en el historial del municipio

### Escenario 13: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 14: Consulta del catálogo
- **Dado** que el catálogo tiene municipios cargados
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada municipio con su departamento, cuántos hay activos y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo los activos, solo los inactivos o todos, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 15: Municipios con el mismo nombre
- **Dado** que dos municipios de departamentos distintos tienen el mismo nombre
- **Cuando** el profesional de estadística busca ese nombre
- **Entonces** el sistema muestra los dos, cada uno con su código y su departamento

### Escenario 16: Búsqueda por código
- **Dado** que el catálogo tiene municipios cargados
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo el municipio con ese código exacto

### Escenario 17: Búsqueda por nombre con varias coincidencias
- **Dado** que varios municipios tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todos los que la contienen, ordenados por nombre

### Escenario 18: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay municipios que coincidan con la búsqueda"

### Escenario 19: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial

### Escenario 20: Comprobación contra el archivo oficial
- **Dado** que el profesional de estadística descargó de nuevo el archivo oficial
- **Cuando** lo sube para comprobar el catálogo
- **Entonces** el sistema, sin cambiar nada, lista los códigos del archivo que faltan en el catálogo, los que están inactivos en el catálogo, los activos que no están en el archivo y los nombres distintos, también en el nombre del departamento

### Escenario 21: Inactivación con motivo
- **Dado** que la fuente oficial retiró un municipio
- **Cuando** el profesional de estadística lo inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántos pacientes lo usan y pide confirmar
- **Y** al confirmar lo deja inactivo: no se ofrece en registros nuevos y los pacientes que lo tienen lo conservan

### Escenario 22: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar el municipio
- **Entonces** el sistema no lo inactiva y dice qué falta (RC-10)

### Escenario 23: Reactivación
- **Dado** que un municipio que está en la versión vigente se inactivó citando una referencia oficial que no lo retiraba
- **Cuando** el profesional de estadística lo reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** el municipio vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 24: Reactivación sin respaldo
- **Dado** que un municipio inactivo no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva lo retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no lo retiraba
- **Cuando** el profesional de estadística intenta reactivarlo
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 25: Historial de un municipio
- **Dado** que un municipio tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-004, HU-005 y HU-006.
**Supuestos o asuntos pendientes:**
- D-02, D-18, D-19, D-21, D-23 y D-25: propuestas del equipo.
- Si una fila del archivo oficial no cumple su numeral, no se carga y sale en la lista de rechazadas
  (RC-8); qué manda en ese caso, la fuente o el numeral, es pregunta al docente.

**Trazabilidad (no va en la tarjeta):**
- Comentarios del docente: HU-026 c1 (homónimos y duplicado por código, que valora; formato de
  importación, campos obligatorios y regla exacta del código DANE con sus ceros iniciales: RC-8,
  columnas, reglas y escenarios 4, 5 y 6; cargo con sesión y sin permiso: HU-001 escenario 14) y c2
  (cinco posiciones con ceros iniciales y correspondencia de nombre, código y departamento:
  escenarios 5, 6 y 20; cargo con función y permisos: regla del cargo).
- HU-027 c1 (homónimos, búsqueda exacta o parcial, departamento además del código: escenarios 15, 16
  y 17) y c2 (departamento para los homónimos, filtros y vigencia: escenarios 15 y 14; elegir la
  ciudad del paciente, con su departamento, es HU-004 escenario 15 desde el 2026-10-22).
- HU-028 c1 (regla exacta del formato del código en el antiguo escenario 5, que era un cambio a
  mano: regla del código, que aplica el aviso de RC-8 también a mano, y escenario 11; cargo sin
  permiso: HU-001 escenario 14) y c2 (el antiguo escenario 3 reasociaba los registros al código
  nuevo: ahora el viejo se inactiva y las residencias quedan como estaban, escenario 9; versiones:
  escenarios 7 y 8; fuente oficial y cinco posiciones: fuente y escenarios 5 y 20).
- HU-029 c1 (bloqueo con la cantidad de registros e historial: escenarios 21 y 25) y c2 ("Bien
  bloqueo en uso e historial": el valor en uso ya no se bloquea: como nada se borra (D-20), se
  inactiva con un motivo de RC-10 y los registros lo conservan; lo que se bloquea es inactivar sin
  respaldo, escenario 22; inactivación y recarga, motivo y autor, preservar los valores oficiales:
  escenarios 21, 22, 23, 24 y 2, con RC-9 y RC-10).
- Escenarios antiguos de HU-026: el 1 (carga de un municipio) es el 10; el 2 (carga masiva) es el 1;
  el 3 (sin código DANE) va en el 2 y el 11; el 6 (formato) va en el 5; el 9 (se conservan los
  válidos) va en el 2; el 4 (código que ya existe) va en el 11 y el 7; el 5 (mismo nombre) es el 15;
  el 7 (atribución) va en el 25; el 8 (sin sesión) lo cubre HU-001 escenario 4; el 10 (disponible de
  inmediato) va en el 1.
- De HU-027: el 1, el 2 (el código acompaña al nombre), el 9 (no modifica) y el 10 (eliminados) van
  en el 14; el 3 es el 19; el 4 va en el 17 y el 15; el 5 es el 16; el 6 es el 18; el 7 (quién
  cargó) va en el 25; el 8 (sin sesión) lo cubre HU-001 escenario 4.
- De HU-028: el 1 es el 12; el 2 (referencias) va en el 12 y el 9; el 3 (cambio del código DANE) es
  el 9; el 4, el 5 y el 10 (código repetido, formato, valor incompleto) van en el 11; el 6
  (municipio que no existe) sale; el 7 (abandonada) es el 13; el 8 (atribución) va en el 25; el 9
  (sin sesión) lo cubre HU-001 escenario 4.
- De HU-029: el 1, el 2, el 3 y el 7 van en el 21; el 4 (cancelada) es el 13; el 5 (rastro) va en el
  25; el 6 (valor que no existe) sale; el 8 (sin sesión) lo cubre HU-001 escenario 4; el 9 (otro
  cargo) va en HU-001 escenario 14; el 10 (recarga) es el 23.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 4).
- Se fusionan HU-026 a HU-029 (D-17): las tarjetas HU-027 a HU-029 se archivan con una nota que
  remite a HU-026. El título pasa a "Catálogo de municipios: carga y mantenimiento".
- Tablero, Ajustes por historia: filas con destino HU-026 a HU-029 y la fila general de catálogos.
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.

---

# HU-034 · Catálogo de tipos de documento: carga y mantenimiento

**ID:** HU-034
**Título:** Catálogo de tipos de documento: carga y mantenimiento
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de tipos de documento con los valores del anexo y mantenerlo igual a la fuente oficial
> Para que el documento del paciente se registre con uno de los tipos que admite la Resolución 866

**Valor de negocio:** El tipo y el número de documento identifican al paciente y sirven para
buscarlo (D-07). Un tipo que no está en el anexo deja al paciente sin una identificación válida para
el intercambio.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Cada tipo tiene un código de 2 caracteres y el nombre que le da el anexo (numeral 2, hoja 13). La
  lista tiene 14 valores; incluye los de adulto y menor sin identificar y el de sin identificación,
  cuyas reglas de uso en el registro del paciente están pendientes (HU-004, D-19).
- Fuente oficial: la lista del numeral 2, en la hoja 13, que fija los 14 valores. El archivo es esa
  lista en CSV, transcrita por el equipo o tomada de la tabla de SISPRO que la publique; si no
  coinciden, manda la hoja y se guardan sus códigos y nombres (D-21). La primera carga se compara
  con la hoja contando solo las filas aceptadas. Un cambio solo puede venir de una resolución que
  modifique el anexo, que es la referencia oficial del cambio.
- Columnas del archivo: código; nombre. La clave de duplicado es el código.
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- CRUD por escenario: crear 1 a 7, 9 y 10; leer 13 a 18 y 23; actualizar 8, 10 y 11; inactivar y
  reactivar 19 a 22 (RC-10, nada se borra); cancelar cualquiera de ellas 12.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística tiene el archivo con la lista de la hoja 13
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea un tipo de documento por cada fila aceptada, con esa versión
- **Y** los tipos de documento quedan activos para los registros nuevos
- **Y** si un nombre no coincide con el de la hoja, guarda el de la hoja y lo avisa

### Escenario 2: Primera carga que no trae la lista del anexo
- **Dado** que las filas aceptadas de la primera carga no traen uno de los 14 códigos de la hoja 13, o traen uno que la hoja no tiene
- **Cuando** el profesional de estadística sube el archivo
- **Entonces** el sistema dice qué código falta o sobra, y no deja guardar la carga

### Escenario 3: Filas con errores en la primera carga
- **Dado** que en la primera carga el archivo trae los 14 códigos de la hoja 13 y además una fila con un código de 3 caracteres
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
- **Dado** que la fuente oficial reemplazó el código de un tipo de documento por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** los pacientes que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 9: Creación por cambio oficial
- **Dado** que la fuente oficial agregó un tipo de documento después de la versión cargada
- **Cuando** el profesional de estadística lo crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema lo guarda activo, con esa referencia

### Escenario 10: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 11: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de un tipo de documento
- **Cuando** el profesional de estadística lo actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya lo usan no cambian: conservan el código y el nombre con que se hicieron (RC-11)
- **Y** el nombre anterior queda en el historial del tipo de documento

### Escenario 12: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 13: Consulta del catálogo
- **Dado** que el catálogo tiene tipos de documento cargados
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada tipo de documento, cuántos hay activos y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo los activos, solo los inactivos o todos, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 14: Búsqueda por código
- **Dado** que el catálogo tiene tipos de documento cargados
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo el tipo de documento con ese código exacto

### Escenario 15: Búsqueda por nombre con varias coincidencias
- **Dado** que varios tipos de documento tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todos los que la contienen, ordenados por nombre

### Escenario 16: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay tipos de documento que coincidan con la búsqueda"

### Escenario 17: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial

### Escenario 18: Comprobación contra la hoja del anexo
- **Dado** que el profesional de estadística quiere saber si el catálogo sigue igual a la hoja 13
- **Cuando** pide la comprobación
- **Entonces** el sistema, sin cambiar nada, compara el catálogo con los 14 códigos y nombres de la hoja
- **Y** lista los que faltan, los que están inactivos, los que sobran y los nombres distintos

### Escenario 19: Inactivación con motivo
- **Dado** que la fuente oficial retiró un tipo de documento
- **Cuando** el profesional de estadística lo inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántos pacientes lo usan y pide confirmar
- **Y** al confirmar lo deja inactivo: no se ofrece en registros nuevos y los pacientes que lo tienen lo conservan

### Escenario 20: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar el tipo de documento
- **Entonces** el sistema no lo inactiva y dice qué falta (RC-10)

### Escenario 21: Reactivación
- **Dado** que un tipo de documento que está en la versión vigente se inactivó citando una referencia oficial que no lo retiraba
- **Cuando** el profesional de estadística lo reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** el tipo de documento vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 22: Reactivación sin respaldo
- **Dado** que un tipo de documento inactivo no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva lo retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no lo retiraba
- **Cuando** el profesional de estadística intenta reactivarlo
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 23: Historial de un tipo de documento
- **Dado** que un tipo de documento tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-004, HU-005 y HU-006.
**Supuestos o asuntos pendientes:**
- D-02, D-07, D-18, D-19, D-21, D-23 y D-25: propuestas del equipo.

**Trazabilidad (no va en la tarjeta):**
- Historia nueva: fila V-02 del tablero, una lista de 14 valores que por RC-1 tiene su CRUD [2.3].
  No tiene comentarios del docente porque la tarjeta no existía. Usa los mismos escenarios de las
  demás historias de catálogo, para que todos se prueben igual.
- La primera carga se compara con los códigos de la hoja (escenario 2) y la comprobación también
  (escenario 18): así se prueba que el catálogo está completo. Los cambios sueltos (escenarios 8, 9
  y 11) solo se aceptan con la resolución que modifique el anexo como referencia.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 4).
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.

---

# HU-035 · Catálogo de categorías de discapacidad: carga y mantenimiento

**ID:** HU-035
**Título:** Catálogo de categorías de discapacidad: carga y mantenimiento
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de categorías de discapacidad con los valores del anexo y mantenerlo igual a la fuente oficial
> Para que se registren una o varias categorías de discapacidad del paciente con los valores de la Resolución 866

**Valor de negocio:** Sin este catálogo no se registran las discapacidades del paciente, y la guía
permite registrar más de una [2.1].

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Cada categoría tiene un código de 2 caracteres y el nombre que le da el anexo (numeral 10, hojas
  14 y 15). La lista tiene 8 valores, entre ellos "Sin discapacidad".
- Fuente oficial: la lista del numeral 10, en las hojas 14 y 15, que fija los 8 valores. El archivo
  es esa lista en CSV, transcrita por el equipo o tomada de la tabla de SISPRO que la publique; si
  no coinciden, manda la hoja y se guardan sus códigos y nombres (D-21). La primera carga se compara
  con la hoja contando solo las filas aceptadas. El anexo de la Resolución 1888 de 2025 remite a la
  tabla CategoriaDiscapacidad de SISPRO. Un cambio solo puede venir de una resolución que modifique
  el anexo, que es la referencia oficial del cambio.
- Columnas del archivo: código; nombre. La clave de duplicado es el código.
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- CRUD por escenario: crear 1 a 7, 9 y 10; leer 13 a 18 y 23; actualizar 8, 10 y 11; inactivar y
  reactivar 19 a 22 (RC-10, nada se borra); cancelar cualquiera de ellas 12.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística tiene el archivo con la lista de las hojas 14 y 15
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea una categoría de discapacidad por cada fila aceptada, con esa versión
- **Y** las categorías de discapacidad quedan activas para los registros nuevos
- **Y** si un nombre no coincide con el de esas hojas, guarda el de esas hojas y lo avisa

### Escenario 2: Primera carga que no trae la lista del anexo
- **Dado** que las filas aceptadas de la primera carga no traen uno de los 8 códigos de las hojas 14 y 15, o traen uno que esas hojas no tienen
- **Cuando** el profesional de estadística sube el archivo
- **Entonces** el sistema dice qué código falta o sobra, y no deja guardar la carga

### Escenario 3: Filas con errores en la primera carga
- **Dado** que en la primera carga el archivo trae los 8 códigos de las hojas 14 y 15 y además una fila con un código de 3 caracteres
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
- **Dado** que la fuente oficial reemplazó el código de una categoría de discapacidad por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** los pacientes que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 9: Creación por cambio oficial
- **Dado** que la fuente oficial agregó una categoría de discapacidad después de la versión cargada
- **Cuando** el profesional de estadística la crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema la guarda activa, con esa referencia

### Escenario 10: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 11: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de una categoría de discapacidad
- **Cuando** el profesional de estadística la actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya la usan no cambian: conservan el código y el nombre con que se hicieron (RC-11)
- **Y** el nombre anterior queda en el historial de la categoría de discapacidad

### Escenario 12: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 13: Consulta del catálogo
- **Dado** que el catálogo tiene categorías de discapacidad cargadas
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada categoría de discapacidad, cuántas hay activas y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo las activas, solo las inactivas o todas, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 14: Búsqueda por código
- **Dado** que el catálogo tiene categorías de discapacidad cargadas
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo la categoría de discapacidad con ese código exacto

### Escenario 15: Búsqueda por nombre con varias coincidencias
- **Dado** que varias categorías de discapacidad tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todas las que la contienen, ordenadas por nombre

### Escenario 16: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay categorías de discapacidad que coincidan con la búsqueda"

### Escenario 17: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial

### Escenario 18: Comprobación contra la hoja del anexo
- **Dado** que el profesional de estadística quiere saber si el catálogo sigue igual a las hojas 14 y 15
- **Cuando** pide la comprobación
- **Entonces** el sistema, sin cambiar nada, compara el catálogo con los 8 códigos y nombres de la hoja
- **Y** lista los que faltan, los que están inactivos, los que sobran y los nombres distintos

### Escenario 19: Inactivación con motivo
- **Dado** que la fuente oficial retiró una categoría de discapacidad
- **Cuando** el profesional de estadística la inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántos pacientes la usan y pide confirmar
- **Y** al confirmar la deja inactiva: no se ofrece en registros nuevos y los pacientes que la tienen la conservan

### Escenario 20: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar la categoría de discapacidad
- **Entonces** el sistema no la inactiva y dice qué falta (RC-10)

### Escenario 21: Reactivación
- **Dado** que una categoría de discapacidad que está en la versión vigente se inactivó citando una referencia oficial que no la retiraba
- **Cuando** el profesional de estadística la reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** la categoría de discapacidad vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 22: Reactivación sin respaldo
- **Dado** que una categoría de discapacidad inactiva no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva la retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no la retiraba
- **Cuando** el profesional de estadística intenta reactivarla
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 23: Historial de una categoría de discapacidad
- **Dado** que una categoría de discapacidad tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-004, HU-005 y HU-006.
**Supuestos o asuntos pendientes:**
- D-02, D-18, D-21, D-23 y D-25: propuestas del equipo.

**Trazabilidad (no va en la tarjeta):**
- Historia nueva: fila V-11 del tablero, una lista de 8 valores que por RC-1 tiene su CRUD [2.3]. No
  tiene comentarios del docente porque la tarjeta no existía. Usa los mismos escenarios de las demás
  historias de catálogo, para que todos se prueben igual.
- La primera carga se compara con los códigos de la hoja (escenario 2) y la comprobación también
  (escenario 18): así se prueba que el catálogo está completo. Los cambios sueltos (escenarios 8, 9
  y 11) solo se aceptan con la resolución que modifique el anexo como referencia.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 4).
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.

---

# HU-036 · Catálogo de etnias: carga y mantenimiento

**ID:** HU-036
**Título:** Catálogo de etnias: carga y mantenimiento
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de etnias con los valores del anexo y mantenerlo igual a la fuente oficial
> Para que la pertenencia étnica del paciente se registre con los valores de la Resolución 866

**Valor de negocio:** Sin este catálogo no se registra la pertenencia étnica del paciente, que
además decide si se pide su comunidad étnica (HU-004).

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Cada etnia tiene un código de 2 caracteres y el nombre que le da el anexo (numeral 13, componente
  etnia, hoja 15). La lista tiene 7 valores, entre ellos "Ninguna de las anteriores", con el código
  99.
- Fuente oficial: la lista del numeral 13, en la hoja 15, que fija los 7 valores. El archivo es esa
  lista en CSV, transcrita por el equipo o tomada de la tabla de SISPRO que la publique; si no
  coinciden, manda la hoja y se guardan sus códigos y nombres (D-21). La primera carga se compara
  con la hoja contando solo las filas aceptadas. El anexo de la Resolución 1888 de 2025 remite a la
  tabla Etnia de SISPRO. Un cambio solo puede venir de una resolución que modifique el anexo, que es
  la referencia oficial del cambio.
- Columnas del archivo: código; nombre. La clave de duplicado es el código.
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- CRUD por escenario: crear 1 a 7, 9 y 10; leer 13 a 18 y 23; actualizar 8, 10 y 11; inactivar y
  reactivar 19 a 22 (RC-10, nada se borra); cancelar cualquiera de ellas 12.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística tiene el archivo con la lista de la hoja 15
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea una etnia por cada fila aceptada, con esa versión
- **Y** las etnias quedan activas para los registros nuevos
- **Y** si un nombre no coincide con el de la hoja, guarda el de la hoja y lo avisa

### Escenario 2: Primera carga que no trae la lista del anexo
- **Dado** que las filas aceptadas de la primera carga no traen uno de los 7 códigos de la hoja 15, o traen uno que la hoja no tiene
- **Cuando** el profesional de estadística sube el archivo
- **Entonces** el sistema dice qué código falta o sobra, y no deja guardar la carga

### Escenario 3: Filas con errores en la primera carga
- **Dado** que en la primera carga el archivo trae los 7 códigos de la hoja 15 y además una fila con un código de 3 caracteres
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
- **Dado** que la fuente oficial reemplazó el código de una etnia por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** los pacientes que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 9: Creación por cambio oficial
- **Dado** que la fuente oficial agregó una etnia después de la versión cargada
- **Cuando** el profesional de estadística la crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema la guarda activa, con esa referencia

### Escenario 10: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 11: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de una etnia
- **Cuando** el profesional de estadística la actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya la usan no cambian: conservan el código y el nombre con que se hicieron (RC-11)
- **Y** el nombre anterior queda en el historial de la etnia

### Escenario 12: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 13: Consulta del catálogo
- **Dado** que el catálogo tiene etnias cargadas
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada etnia, cuántas hay activas y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo las activas, solo las inactivas o todas, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 14: Búsqueda por código
- **Dado** que el catálogo tiene etnias cargadas
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo la etnia con ese código exacto

### Escenario 15: Búsqueda por nombre con varias coincidencias
- **Dado** que varias etnias tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todas las que la contienen, ordenadas por nombre

### Escenario 16: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay etnias que coincidan con la búsqueda"

### Escenario 17: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial

### Escenario 18: Comprobación contra la hoja del anexo
- **Dado** que el profesional de estadística quiere saber si el catálogo sigue igual a la hoja 15
- **Cuando** pide la comprobación
- **Entonces** el sistema, sin cambiar nada, compara el catálogo con los 7 códigos y nombres de la hoja
- **Y** lista los que faltan, los que están inactivos, los que sobran y los nombres distintos

### Escenario 19: Inactivación con motivo
- **Dado** que la fuente oficial retiró una etnia
- **Cuando** el profesional de estadística la inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántos pacientes la usan y pide confirmar
- **Y** al confirmar la deja inactiva: no se ofrece en registros nuevos y los pacientes que la tienen la conservan

### Escenario 20: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar la etnia
- **Entonces** el sistema no la inactiva y dice qué falta (RC-10)

### Escenario 21: Reactivación
- **Dado** que una etnia que está en la versión vigente se inactivó citando una referencia oficial que no la retiraba
- **Cuando** el profesional de estadística la reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** la etnia vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 22: Reactivación sin respaldo
- **Dado** que una etnia inactiva no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva la retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no la retiraba
- **Cuando** el profesional de estadística intenta reactivarla
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 23: Historial de una etnia
- **Dado** que una etnia tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-004, HU-005 y HU-006.
**Supuestos o asuntos pendientes:**
- D-02, D-18, D-21, D-23 y D-25: propuestas del equipo.

**Trazabilidad (no va en la tarjeta):**
- Historia nueva: fila V-14 del tablero, una lista de 7 valores que por RC-1 tiene su CRUD [2.3]. No
  tiene comentarios del docente porque la tarjeta no existía. Usa los mismos escenarios de las demás
  historias de catálogo, para que todos se prueben igual.
- La primera carga se compara con los códigos de la hoja (escenario 2) y la comprobación también
  (escenario 18): así se prueba que el catálogo está completo. Los cambios sueltos (escenarios 8, 9
  y 11) solo se aceptan con la resolución que modifique el anexo como referencia.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 4).
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.

---

# HU-037 · Catálogo de comunidades étnicas: carga y mantenimiento

**ID:** HU-037
**Título:** Catálogo de comunidades étnicas: carga y mantenimiento
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de comunidades étnicas desde su archivo oficial y mantenerlo igual a la fuente
> Para que la comunidad étnica del paciente se registre con el código del catálogo de referencia vigente

**Valor de negocio:** Cuando la etnia del paciente es de la 01 a la 06, se registra su comunidad
étnica con el código del catálogo de referencia (HU-004). Sin este catálogo ese dato no se puede
registrar.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Cada comunidad tiene un código de 3 caracteres (numeral 13, componente comunidad étnica, hoja 15).
  El anexo no le da un componente de nombre; el nombre lo traería el catálogo de referencia, que no
  se ha encontrado, y que sea obligatorio en el catálogo, para poder buscar la comunidad, es
  propuesta del equipo.
- Fuente oficial: el catálogo de referencia de comunidades étnicas vigente que nombra la hoja 15, a
  cargo del Ministerio de Salud (artículo 8 de la Resolución 866; D-21).
- Columnas del archivo: código; nombre. La clave de duplicado es el código.
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- CRUD por escenario: crear 1 a 6, 8 y 9; leer 12 a 17 y 22; actualizar 7, 9 y 10; inactivar y
  reactivar 18 a 21 (RC-10, nada se borra); cancelar cualquiera de ellas 11.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística descargó el archivo oficial
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea una comunidad étnica por cada fila aceptada, con esa versión
- **Y** las comunidades étnicas quedan activas para los registros nuevos

### Escenario 2: Filas con errores en la primera carga
- **Dado** que en la primera carga el archivo tiene una fila con un código de 2 caracteres y otra sin nombre
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

### Escenario 7: Cambio oficial de código
- **Dado** que la fuente oficial reemplazó el código de una comunidad étnica por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** los pacientes que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 8: Creación por cambio oficial
- **Dado** que la fuente oficial agregó una comunidad étnica después de la versión cargada
- **Cuando** el profesional de estadística la crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema la guarda activa, con esa referencia

### Escenario 9: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 10: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de una comunidad étnica
- **Cuando** el profesional de estadística la actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya la usan no cambian: conservan el código y el nombre con que se hicieron (RC-11)
- **Y** el nombre anterior queda en el historial de la comunidad étnica

### Escenario 11: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 12: Consulta del catálogo
- **Dado** que el catálogo tiene comunidades étnicas cargadas
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada comunidad étnica, cuántas hay activas y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo las activas, solo las inactivas o todas, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 13: Búsqueda por código
- **Dado** que el catálogo tiene comunidades étnicas cargadas
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo la comunidad étnica con ese código exacto

### Escenario 14: Búsqueda por nombre con varias coincidencias
- **Dado** que varias comunidades étnicas tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todas las que la contienen, ordenadas por nombre

### Escenario 15: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay comunidades étnicas que coincidan con la búsqueda"

### Escenario 16: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial

### Escenario 17: Comprobación contra el archivo oficial
- **Dado** que el profesional de estadística descargó de nuevo el archivo oficial
- **Cuando** lo sube para comprobar el catálogo
- **Entonces** el sistema, sin cambiar nada, lista los códigos del archivo que faltan en el catálogo, los que están inactivos en el catálogo, los activos que no están en el archivo y los nombres distintos

### Escenario 18: Inactivación con motivo
- **Dado** que la fuente oficial retiró una comunidad étnica
- **Cuando** el profesional de estadística la inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántos pacientes la usan y pide confirmar
- **Y** al confirmar la deja inactiva: no se ofrece en registros nuevos y los pacientes que la tienen la conservan

### Escenario 19: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar la comunidad étnica
- **Entonces** el sistema no la inactiva y dice qué falta (RC-10)

### Escenario 20: Reactivación
- **Dado** que una comunidad étnica que está en la versión vigente se inactivó citando una referencia oficial que no la retiraba
- **Cuando** el profesional de estadística la reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** la comunidad étnica vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 21: Reactivación sin respaldo
- **Dado** que una comunidad étnica inactiva no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva la retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no la retiraba
- **Cuando** el profesional de estadística intenta reactivarla
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 22: Historial de una comunidad étnica
- **Dado** que una comunidad étnica tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-004, HU-005 y HU-006.
**Supuestos o asuntos pendientes:**
- D-02, D-18, D-21, D-23 y D-25: propuestas del equipo.
- Pendiente de verificar: el equipo no ha encontrado el archivo de este catálogo. Antes del sprint
  lo busca en SISPRO; si no existe, pregunta al docente. El anexo de la Resolución 1888 de 2025 dice
  "13.2 Comunidad étnica Texto Alfanumérico" y "Diligenciar con el nombre de la comunidad étnica a
  la que pertenece el paciente, cuando aplique"; que ahí no se use un código es lectura del equipo.
  Mientras no haya catálogo, el registro del paciente deja guardar sin este dato (HU-004).
- La hoja 15 no dice a qué etnia pertenece cada comunidad. El catálogo no lo exige: sería inferencia
  del equipo y no se agrega como regla.
- Si una fila del archivo oficial no cumple su numeral, no se carga y sale en la lista de rechazadas
  (RC-8); qué manda en ese caso, la fuente o el numeral, es pregunta al docente.

**Trazabilidad (no va en la tarjeta):**
- Historia nueva: fila V-15 del tablero, un catálogo externo que por RC-1 tiene su CRUD [2.3]. No
  tiene comentarios del docente porque la tarjeta no existía. Usa los mismos escenarios de las demás
  historias de catálogo, para que todos se prueben igual.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 4). Además,
  la regla de datos ya no atribuye al numeral 13 un nombre que la hoja 15 no tiene, y la prioridad
  sube a Alta porque HU-004 depende de este catálogo.
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.

---

# HU-038 · Catálogo de entidades que responden por el plan de beneficios: carga y mantenimiento

**ID:** HU-038
**Título:** Catálogo de entidades que responden por el plan de beneficios: carga y mantenimiento
**Funcionalidad relacionada:** Catálogos
**Descripción:**
> Como profesional de estadística
> Quiero cargar el catálogo de entidades que responden por el plan de beneficios desde su archivo oficial y mantenerlo igual a la fuente
> Para que se registre qué entidad responde por el plan de beneficios del paciente con el código que pide la Resolución 866

**Valor de negocio:** La entidad que responde por el plan de beneficios se registra con el paciente
(HU-004). Con un código errado, la atención queda a nombre de quien no responde por ella.

**Prioridad:** Alta
**Estimación relativa:** Pendiente de estimación en clase
**Historia de usuario asociada:** HU-004
**Mockup:** Pendiente: se agrega antes de pasar la historia a Trello

**Reglas de negocio aplicables:**
- El profesional de estadística de la IPS mantiene este catálogo: lo carga, crea y cambia valores
  por cambio oficial, los inactiva y los reactiva. No ve datos de pacientes ni de atenciones, solo
  cuántos registros usan un valor. Los demás cargos solo consultan (D-02, propuesta; matriz de
  permisos). Esos permisos y quién hizo cada cambio los prueba el inicio de sesión (HU-001,
  escenarios 9 y 14); esta historia no depende de él.
- Cada entidad tiene un código de 6 caracteres y un nombre de 3 a 200 (numeral 15, hojas 15 y 16).
  El numeral 15 incluye entre las entidades a las EPS y a las secretarías departamentales,
  distritales y locales de salud.
- Fuente oficial: el catálogo que establezca el Ministerio de Salud (numeral 15, hoja 16; D-21).
- Columnas del archivo: código; nombre. La clave de duplicado es el código.
- La hoja 16 dice: "En el caso de que la prestación del servicio de salud se preste de forma
  particular, el código de la entidad administradora corresponderá a las secretarías
  departamentales, distritales o locales de salud, donde se realizó la prestación del servicio de
  salud". El registro del paciente particular está en HU-004.
- Búsqueda: por código, exacta; por nombre, parcial, sin distinguir mayúsculas ni tildes, con todas
  las coincidencias (D-23).
- Reglas comunes que aplica, en la tarjeta "Reglas comunes": RC-1 a RC-5, RC-8 (carga), RC-9
  (versión nueva), RC-10 (inactivar y reactivar) y RC-11 (nombre del momento del registro).
- CRUD por escenario: crear 1 a 6, 8 y 9; leer 12 a 17 y 22; actualizar 7, 9 y 10; inactivar y
  reactivar 18 a 21 (RC-10, nada se borra); cancelar cualquiera de ellas 11.

**Precondiciones:**
- Para cargar, el profesional de estadística tiene el archivo oficial en CSV y los datos de su versión (RC-8).

**Criterios de aceptación BDD:**

### Escenario 1: Primera carga del archivo oficial
- **Dado** que el catálogo está vacío y el profesional de estadística descargó el archivo oficial
- **Cuando** lo sube con los datos de su versión (RC-8) y confirma
- **Entonces** el sistema crea una entidad por cada fila aceptada, con esa versión
- **Y** las entidades quedan activas para los registros nuevos

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

### Escenario 7: Cambio oficial de código
- **Dado** que la fuente oficial reemplazó el código de una entidad por otro
- **Cuando** el profesional de estadística registra el cambio con la referencia oficial
- **Entonces** el sistema inactiva el código viejo con el motivo "Reemplazado por" y el código nuevo, y crea el nuevo si no existe
- **Y** los pacientes que tenían el código viejo lo conservan; los registros nuevos usan el nuevo

### Escenario 8: Creación por cambio oficial
- **Dado** que la fuente oficial agregó una entidad después de la versión cargada
- **Cuando** el profesional de estadística la crea con su código, su nombre y la referencia oficial
- **Entonces** el sistema la guarda activa, con esa referencia

### Escenario 9: Creación o cambio rechazado
- **Dado** que falta la referencia oficial, el código que se crea ya existe o un dato no cumple su numeral
- **Cuando** el profesional de estadística confirma la creación o el cambio
- **Entonces** el sistema no guarda nada y dice qué falló

### Escenario 10: Cambio oficial de nombre
- **Dado** que la fuente oficial cambió el nombre de una entidad
- **Cuando** el profesional de estadística la actualiza con la referencia oficial
- **Entonces** el sistema usa el nombre nuevo en los registros nuevos
- **Y** los registros que ya la usan no cambian: conservan el código y el nombre con que se hicieron (RC-11)
- **Y** el nombre anterior queda en el historial de la entidad

### Escenario 11: Cancelar antes de confirmar
- **Dado** que el profesional de estadística empezó una carga, una versión nueva, una creación, un cambio, una inactivación o una reactivación
- **Cuando** cancela antes de confirmar
- **Entonces** el catálogo queda igual que antes

### Escenario 12: Consulta del catálogo
- **Dado** que el catálogo tiene entidades cargadas
- **Cuando** el profesional de estadística lo consulta
- **Entonces** ve la lista con el código, el nombre y el estado de cada entidad, cuántas hay activas y la versión vigente con sus fechas de publicación y de descarga
- **Y** puede ver solo las activas, solo las inactivas o todas, sin cambiar nada
- **Y** puede abrir el historial de versiones: cada una con su fuente, sus fechas y cuántos códigos creó, cambió e inactivó

### Escenario 13: Búsqueda por código
- **Dado** que el catálogo tiene entidades cargadas
- **Cuando** el profesional de estadística busca por código
- **Entonces** el sistema muestra solo la entidad con ese código exacto

### Escenario 14: Búsqueda por nombre con varias coincidencias
- **Dado** que varias entidades tienen la misma palabra en el nombre
- **Cuando** el profesional de estadística busca esa palabra, con o sin mayúsculas y tildes
- **Entonces** el sistema muestra todas las que la contienen, ordenadas por nombre

### Escenario 15: Búsqueda sin resultados
- **Dado** que ningún código ni nombre coincide con lo buscado
- **Cuando** el profesional de estadística busca
- **Entonces** el sistema dice "No hay entidades que coincidan con la búsqueda"

### Escenario 16: Catálogo vacío
- **Dado** que todavía no se ha cargado el catálogo
- **Cuando** el profesional de estadística lo consulta
- **Entonces** el sistema dice que el catálogo está vacío y que falta cargar el archivo oficial

### Escenario 17: Comprobación contra el archivo oficial
- **Dado** que el profesional de estadística descargó de nuevo el archivo oficial
- **Cuando** lo sube para comprobar el catálogo
- **Entonces** el sistema, sin cambiar nada, lista los códigos del archivo que faltan en el catálogo, los que están inactivos en el catálogo, los activos que no están en el archivo y los nombres distintos

### Escenario 18: Inactivación con motivo
- **Dado** que la fuente oficial retiró una entidad
- **Cuando** el profesional de estadística la inactiva con el motivo "Retirado por la fuente oficial" y la referencia
- **Entonces** el sistema muestra cuántos pacientes la usan y pide confirmar
- **Y** al confirmar la deja inactiva: no se ofrece en registros nuevos y los pacientes que la tienen la conservan

### Escenario 19: Inactivación sin motivo o sin respaldo
- **Dado** que el profesional de estadística no eligió motivo, eligió "Retirado por la fuente oficial" sin la referencia, o eligió "Creado por error" para un código que está en la versión vigente
- **Cuando** intenta inactivar la entidad
- **Entonces** el sistema no la inactiva y dice qué falta (RC-10)

### Escenario 20: Reactivación
- **Dado** que una entidad que está en la versión vigente se inactivó citando una referencia oficial que no la retiraba
- **Cuando** el profesional de estadística la reactiva y escribe por qué esa referencia no aplicaba
- **Entonces** la entidad vuelve a ofrecerse en los registros nuevos, y el motivo queda en su historial

### Escenario 21: Reactivación sin respaldo
- **Dado** que una entidad inactiva no está en la versión vigente ni lo creó un cambio oficial posterior a ella, por ejemplo porque una versión nueva la retiró, o se inactivó con una referencia oficial y el profesional no da otra ni dice por qué esa no la retiraba
- **Cuando** el profesional de estadística intenta reactivarla
- **Entonces** el sistema no lo permite y dice qué respaldo falta (RC-10)

### Escenario 22: Historial de una entidad
- **Dado** que una entidad tuvo cambios
- **Cuando** el profesional de estadística consulta su historial
- **Entonces** ve con qué versión o con qué referencia se creó, y cada cambio con su motivo, su referencia, la fecha y la hora

**Dependencias:** Ninguna. Lo usan HU-004, HU-005 y HU-006.
**Supuestos o asuntos pendientes:**
- D-02, D-18, D-21, D-23 y D-25: propuestas del equipo.
- Se supone que el archivo oficial trae las secretarías de salud, porque el numeral 15 las incluye;
  se confirma al descargarlo. Sin ellas no se puede registrar a un paciente particular (HU-004).
- Si una fila del archivo oficial no cumple su numeral, no se carga y sale en la lista de rechazadas
  (RC-8); qué manda en ese caso, la fuente o el numeral, es pregunta al docente.

**Trazabilidad (no va en la tarjeta):**
- Historia nueva: fila V-17 del tablero, un catálogo externo que por RC-1 tiene su CRUD [2.3]. No
  tiene comentarios del docente porque la tarjeta no existía. Usa los mismos escenarios de las demás
  historias de catálogo, para que todos se prueben igual.
- Versión 2 de la tanda: el cargo pasa a profesional de estadística (D-02); las reglas comunes se
  enlazan; entran la comprobación contra la fuente con los inactivos, la versión nueva que deja como
  estaban los códigos rechazados, y la reactivación sin respaldo (revisión de la tanda 4). Sale el
  escenario de la secretaría de salud del municipio de la IPS: la hoja 16 habla de las secretarías
  del lugar donde se prestó el servicio, y el caso del paciente particular pasó a HU-004.
- Versión 3 (límite de 12 historias, 2026-10-21): la historia deja de depender del inicio de sesión,
  que el docente no pone en la primera entrega. El escenario "Cargo sin permiso" pasa a HU-001,
  escenario 14, y quién hizo cada cambio lo agrega HU-001, escenario 9; el historial de esta
  historia guarda el motivo, la referencia, la fecha y la hora.

---

## Registro

| Fecha | Versión | Cambio |
|---|---|---|
| 2026-10-20 | 1 | Primera escritura: HU-018, HU-022 y HU-026 funden las cuatro tarjetas de su catálogo; HU-034 a HU-038, nuevas; carga desde el archivo oficial con RC-8; propuestas para D-02, D-03, D-18, D-21 y D-23 |
| 2026-10-20 | 2 | Correcciones de la revisión independiente (`revisiones/revision-tanda-4.md`): cargo profesional de estadística; reglas comunes enlazadas (RC-8 a RC-11); la versión nueva deja como estaban los códigos rechazados; comprobación contra un archivo recién descargado, con los inactivos; listas del anexo comparadas con su hoja; inactivación y reactivación con respaldo oficial (RC-10); grupo de la ocupación leído del código (D-26); nombre del momento del registro (D-25); D-03 numérico; HU-037 sin nombre atribuido al numeral 13; HU-038 con el texto de la hoja 16 y el paciente particular en HU-004; trazabilidad corregida |
| 2026-10-20 | 2 (continuación) | Revisión final cruzada (`revisiones/revision-final-cruzada.md`): grupos de 1 a 3 dígitos validados con la regla de HU-022 y cantidades por nivel del DANE (escenario nuevo); recarga de la versión vigente para completar una primera carga; versión nueva con reemplazos anotados, motivos y vigencia; reactivación de lo inactivado con referencia oficial; listas del anexo comparadas solo con las filas aceptadas; el bloqueo de valores en uso explicado en la trazabilidad; D-21, D-25 y D-26 con su fuente exacta |
| 2026-10-20 | 2 (verificación) | Verificación de la aplicación (`revisiones/verificacion-aplicacion-final.md`): HU-022 cuenta las filas leídas por nivel y, si el archivo no trae los grupos, los carga antes como fuente aparte; reactivación sin respaldo con el caso de la referencia oficial; el código de una fila rechazada conserva su nombre y su estado; en las listas del anexo, el nombre distinto pasa a la primera carga |
| 2026-10-21 | 3 | Límite de 12 historias: las historias de catálogo dejan de depender del inicio de sesión; el escenario "Cargo sin permiso" pasa a HU-001 (escenario 14) y el historial guarda motivo, referencia, fecha y hora (quién lo hizo lo agrega HU-001, escenario 9); HU-037 ya no depende de HU-036 |
| 2026-10-22 | 4 | Correcciones mínimas para el plan de 12 historias: cada historia dice qué escenarios son cada operación del CRUD; los escenarios de primera carga, cambio oficial de nombre y catálogo vacío dejan de mirar el registro y la consulta del paciente (HU-004 y HU-005), que dependen de estas historias; HU-022 rechaza crear a mano una ocupación cuyo grupo no está activo; HU-026 toma el departamento de los dos primeros dígitos y rechaza crear un municipio de un departamento que no está en el catálogo |
