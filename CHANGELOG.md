# CHANGELOG.md: qué cambió y cuándo

> **Rol:** historia. **Régimen:** crece por secciones, lo más nuevo arriba, y una sección publicada
> no se reescribe. Formato [Keep a Changelog](https://keepachangelog.com): cada sección abre con `##
> vX.Y — AAAA-MM-DD`. **Origen:** plantilla 1.2, sembrada el 2026-10-22 (`docs/DECISIONS.md`,
> 2026-10-22 "Se adopta la plantilla 1.2").

## v0.5 — 2026-10-25

Solo documentación. La base común de los catálogos es una clase con código, nombre, estado y
versión, de la que hereda la entidad de cada catálogo. No hay código del producto que muestre una
versión.

### Added
- `docs/DECISIONS.md`: la entrada 2026-10-25 "La base común es una clase con código, nombre, estado
  y versión", con sus cuatro alternativas.

### Changed
- `docs/DESIGN.md`, sección 7: la clase base, la llave como texto, la tabla de cambios común y por
  qué un catálogo no es un enum.
- `docs/GLOSSARY.md`: "base común de catálogos" nombra la clase y sus cuatro campos.
- La memoria contextual anota la pregunta del autor sobre los enums y sus cuatro respuestas.

## v0.4 — 2026-10-25

Solo documentación. La fase 0 tiene su orden antes del paso 1, el autor es el integrante 3 y HU-036
es el catálogo de ejemplo. No hay código del producto que muestre una versión.

### Added
- `docs/DECISIONS.md`: cuatro entradas del 2026-10-25, de "El autor es el integrante 3" a "La base
  de prueba arranca como copia del archivo".
- `docs/GLOSSARY.md`: "catálogo de ejemplo" y "turno".

### Changed
- `docs/ROADMAP.md`: antes del paso 1 van, en orden, el build, el esqueleto en terminal y la base
  común; HU-036 abre el paso 1 como ejemplo, y el autor es el integrante 3.
- `docs/DESIGN.md`, sección 8: quién tiene el turno y cómo arranca la base de prueba. Su lista "Sin
  escribir todavía" pierde esos dos ítems y suma cómo probar sin subir el nombre de la base.
- `docs/GLOSSARY.md`: "base de prueba" dice cómo arranca.
- La memoria contextual anota el orden y las tres respuestas del autor.

## v0.3 — 2026-10-25

Solo documentación. Un catálogo se carga desde un CSV con la ruta en la terminal, el archivo de la
base se cambia por turnos y las pruebas corren sobre otra base. No hay código del producto que
muestre una versión.

### Added
- `docs/DECISIONS.md`: tres entradas del 2026-10-25, "Un catálogo se carga desde un CSV, con la
  ruta en la terminal", "El archivo de la base se cambia por turnos" y "Las pruebas corren sobre
  otra base".
- `docs/GLOSSARY.md`: "base de prueba".

### Changed
- `docs/DESIGN.md`, sección 8: el archivo de la base se cambia por turnos y las pruebas usan otra
  base. Su lista "Sin escribir todavía" cambia dos ítems por tres más precisos.
- `docs/ROADMAP.md`: el tramo 1 de la fase 0 dice cómo entra cada catálogo al archivo de la base.
- `docs/GLOSSARY.md`: "archivo de la base" suma los turnos.
- La memoria contextual anota las respuestas del autor y lo que el modelo agregó sin respuesta.

## v0.2 — 2026-10-25

Solo documentación. El repo pasa a la plantilla 1.3, `AGENTS.md` queda como isla y `docs/context/`
sale del corpus del piso. No hay código del producto que muestre una versión.

### Added
- `docs/DECISIONS.md`: cinco entradas del 2026-10-25, de "Se adopta la plantilla 1.3" a
  "docs/context sale del corpus del piso", todas salvo la primera con sus alternativas.
- `CLAUDE.md`, sección 7: el commit conserva su trailer, y uno hecho desde la web también lleva
  tipo.

### Changed
- `AGENTS.md` queda en dos oraciones que mandan a `CLAUDE.md`, sin bloque de cita, y `README.md`
  manda a `CLAUDE.md`. `CLAUDE.md` deja de nombrarlo en la tabla de tipos, la regla 6 y la raíz.
- `CLAUDE.md`, sección 6: la sección de un PR doc-only lleva la versión siguiente, abre con
  `Solo documentación.` y sus subsecciones van en orden.
- `CLAUDE.md`, sección 5, y el encabezado de `docs/DECISIONS.md`: cómo se nombra una entrada
  reemplazada, cuándo van las alternativas y cómo abre un hueco.
- `CLAUDE.md`, sección 9: el corpus deja afuera `docs/context/`, y las líneas base del 2026-10-25
  dan cero en las ocho reglas.
- `docs/GLOSSARY.md`: el grupo "Método" dice su fuente, "PR doc-only" cambia con la sección 6, y
  "prioridad" y "vista" citan su decisión.
- `docs/ARCHITECTURE.md`, sección 1: las filas de la raíz.

## v0.1 — 2026-10-24

Solo documentación. La complejidad se estima de 1 a 10, con una propuesta para votar en clase, y el
integrante 3 arma la base común de los catálogos.

### Added
- `docs/ROADMAP.md`, sección "Estimación de las 12 historias": el criterio, trabajo propio más
  dependencias directas con tope 10, y la propuesta del modelo para cada historia.
- `docs/GLOSSARY.md`: "estimación" y "prioridad".

### Changed
- D-12, en `docs/REQUIREMENTS.md`, sección 6, pasa a la escala de 1 a 10 que dio el docente.
- La fase 0 del ROADMAP dice que el integrante 3 arma la base común, y el ítem del Backlog sobre la
  estimación pide votarla en clase.
- La memoria contextual anota la pregunta del autor sobre la prioridad y el paciente, y la
  respuesta.

## v0.1 — 2026-10-24

Solo documentación. El archivo de la base va en git con los catálogos ya cargados.

### Added
- `docs/DECISIONS.md`, entrada 2026-10-24 "El archivo de la base trae los catálogos cargados", con
  la razón del autor y lo que el modelo agregó sin respuesta todavía.

### Changed
- `docs/DESIGN.md`, sección 8, dice para qué va en git el archivo de la base, y su lista "Sin
  escribir todavía" suma el paciente de prueba que puede llegar a ese archivo.
- El ítem del Backlog que descarga los catálogos oficiales pide revisar su licencia antes de
  subirlos (`CLAUDE.md`, sección 10).
- La memoria contextual y la línea "archivo de la base" del glosario anotan la aclaración.

## v0.1 — 2026-10-24

Solo documentación. La fase 0 son las 12 historias, la base es H2 en un archivo que va en git, y el
código se separa en modelo, vista y controlador, con una base común para los catálogos.

### Added
- `docs/DESIGN.md`, secciones 6 a 8: modelo, vista y controlador; la base común de los catálogos; y
  la base H2 en archivo, con lo que cuesta subirlo a git.
- `docs/GLOSSARY.md`: "fase 0", los tres sentidos de "modelo" y el grupo Código, con archivo de la
  base, base común de catálogos, controlador, repositorio, servicio y vista.

### Changed
- `docs/ROADMAP.md`: la fase 0 pasa a ser las 12 historias, en tres tramos y 5 pasos, con la base
  común antes del paso 1. La siembra queda como etapa previa cerrada, sin número.
- La memoria contextual anota las decisiones del autor y lo que el modelo le corrigió sobre H2, el
  MVC, las relaciones del paciente y la historia clínica.

### Fixed
- El encabezado de `docs/DECISIONS.md` remitía al tablero, que salió en el PR #4 del fork. Ahora
  remite a `docs/REQUIREMENTS.md`, sección 6.

### Removed
- La línea de `docs/TEMPORARY-CONTEXT.md` sobre el paquete y las cuatro bases de datos: la base
  quedó decidida, y el paquete sigue en `docs/DESIGN.md`, sección 9.

## v0.1 — 2026-10-24

Solo documentación. `docs/context/` se queda con lo que usan las historias, y el primer sprint
arranca en terminal.

### Changed
- Las tarjetas de `docs/context/trello/tarjetas/` pasan a ser la fuente de las historias.
- `docs/REQUIREMENTS.md` trae los 16 atributos del paciente y las 27 decisiones del equipo, D-01 a
  D-27, y dice que el primer sprint tiene al menos el esqueleto en terminal.
- Las líneas base de prosa de `CLAUDE.md`, sección 9, se miden de nuevo con el corpus más chico.
- La memoria contextual anota dónde quedó la relación entre los comentarios del docente y cada
  escenario, y cómo recuperarla.

### Removed
- De `docs/context/`: las seis tandas del backlog v3, el tablero del equipo, las preguntas al
  docente, el plan de desarrollo, el plan de la reescritura del backlog, las revisiones y las
  instrucciones del kit de Trello. Se recuperan del commit fa95eaf.

## v0.1 — 2026-10-24

Solo documentación. Las 12 historias del semestre se reparten entre 3 integrantes, y el trabajo
queda en el fork del autor.

### Added
- `docs/GLOSSARY.md`: la palabra fork, y en el Backlog del ROADMAP, llevar el trabajo del fork a
  Atervolgo cuando su dueño lo habilite.

### Changed
- `docs/ROADMAP.md` reparte las 12 historias del plan B entre 3 integrantes, con 4 historias y 80,
  82 y 81 escenarios cada uno. HU-005 y HU-006 se hacen a la vez en el último paso.
- La fase 0 del ROADMAP, la siembra, queda cerrada con el PR #2 del fork.
- La memoria contextual anota el fork, el equipo de 3 y las ramas borradas.

## v0.1 — 2026-10-24

Solo documentación. Los títulos de los catálogos nombran sus cuatro operaciones, la memoria
contextual vuelve a ser la memoria del modelo y el ROADMAP ordena las 30 historias.

### Added
- `docs/ROADMAP.md`, sección "Orden de las 30 historias": el paso de cada una de las 12 del
  semestre por integrante, y un orden propuesto para las otras 18, con lo que falta antes de empezar
  cada grupo.

### Changed
- `docs/context/MEMORIA-CONTEXTUAL.md` vuelve a ser la memoria de trabajo del modelo. Entra en el
  orden de lectura de `CLAUDE.md`, sección 1, y se actualiza con cada decisión del autor. La copia
  pasa a la del autor del 2026-10-24.
- Las 13 historias de catálogo se titulan "carga, consulta, actualización e inactivación", y su
  Quiero nombra esas operaciones. La línea de operaciones por escenario usa los mismos nombres y
  pone la versión nueva en las cuatro. Ningún escenario cambia.
- La hoja Decisiones del tablero cita las historias del backlog v3 en D-02, D-04, D-17, D-20 y D-23.

### Fixed
- La lámina de HU-004 muestra el escenario 15, y su aviso de paciente repetido ya no ofrece abrir la
  consulta, como dice el escenario 12.

## v0.1 — 2026-10-22

Solo documentación. Siembra los documentos de la plantilla 1.2 con el contexto del backlog v3.

### Added
- Los documentos canónicos llenos con el proyecto: `AGENTS.md`, `CLAUDE.md`, este archivo y los
  siete de `docs/`, más `tests/README.md`.
- `docs/context/`: el material del backlog v3 importado tal cual el 2026-10-22, con un `README.md`
  que dice qué es cada archivo.

### Changed
- Las 12 historias del plan B quedan sin remisiones a historias que dependen de ellas. Los 8
  catálogos dicen qué escenarios son cada operación del CRUD, y HU-022 y HU-026 validan la creación
  a mano. Detalle en `docs/context/plan-desarrollo-12-historias.md`, sección 8.
- HU-004 gana el escenario 15, buscar y elegir un valor de un catálogo grande. El backlog pasa a 30
  historias y 529 escenarios.

### Removed
- `PLANTILLA.md` y `docs/modulo/`: la plantilla pide no copiarlos a un repo sembrado sin módulo.
