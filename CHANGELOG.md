# CHANGELOG.md: qué cambió y cuándo

> **Rol:** historia. **Régimen:** crece por secciones, lo más nuevo arriba, y una sección publicada
> no se reescribe. Formato [Keep a Changelog](https://keepachangelog.com): cada sección abre con `##
> vX.Y — AAAA-MM-DD`. **Origen:** plantilla 1.2, sembrada el 2026-10-22 (`docs/DECISIONS.md`,
> 2026-10-22 "Se adopta la plantilla 1.2").

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
