# docs/context/: el material del backlog v3

Este README solo explica esta carpeta. Qué manda sobre qué está en `CLAUDE.md`, sección 2, y por qué
el material está acá, en `docs/DECISIONS.md`, 2026-10-22 "El material del backlog entra en
docs/context".

Llegó en `contexto.zip` el 2026-10-22. La siembra lo copió y, en el mismo commit, le aplicó las
correcciones a las 12 historias, una por fila en `plan-desarrollo-12-historias.md`, sección 8. Los
cambios posteriores se ven con `git log -- docs/context`. Los nombres de archivo son los originales,
porque los textos se citan entre sí por ruta.

## 1. Qué es cada cosa

| Ruta | Qué es | Tipo |
|---|---|---|
| `backlog-v3-tanda-0-epica-y-reglas.md` | Épica, matriz de permisos, reglas comunes RC-1 a RC-11, convenciones, glosario de las historias y plantilla | Fuente |
| `backlog-v3-tanda-1-acceso-y-paciente.md` | HU-001, HU-004, HU-005 y HU-006, con su trazabilidad | Fuente |
| `backlog-v3-tanda-2-atencion.md` | HU-007, HU-008, HU-009 y HU-043 | Fuente |
| `backlog-v3-tanda-3-oposicion-y-voluntad.md` | HU-010 a HU-017 | Fuente |
| `backlog-v3-tanda-4-catalogos-paciente.md` | Los 8 catálogos del paciente: HU-018, HU-022, HU-026 y HU-034 a HU-038 | Fuente |
| `backlog-v3-tanda-5-diagnosticos-y-catalogos-atencion.md` | HU-030, HU-031 y HU-039 a HU-042 | Fuente |
| `trello/tarjetas/` | El texto de cada tarjeta para pegar en Trello, sin la trazabilidad, más la tarjeta de reglas comunes | Sale de las tandas |
| `trello/mockups/` | Una lámina PNG por historia, con etiquetas "Esc. N" | Sale de las tandas |
| `trello/LEEME-kit-trello.md` | Pasos para pasar el backlog a Trello y tabla de tarjetas | Guía |
| `trello/respuestas-al-docente.md` | Borrador de comentario por tarjeta para el docente | Borrador |
| `plan-desarrollo-12-historias.md` | Las 12 historias del semestre, sus dependencias, las correcciones del 2026-10-22 y los enunciados de catálogo del 2026-10-24 | Plan |
| `PLAN-BACKLOG-V3.md` | Plan de la reescritura del backlog y su avance | Historia |
| `preguntas-docente.md` | Las 14 preguntas abiertas para el docente | Pendiente |
| `modelo-datos/` | Modelo entidad-relación propuesto, 24 tablas en Mermaid, y dos PNG | Borrador |
| `revisiones/` | Las revisiones independientes de cada tanda y su aplicación | Historia |
| `tablero-validacion-ips-20oct.xlsx` | Tablero del equipo: validación del anexo, decisiones D-01 a D-27, ajustes por historia | Historia |
| `MEMORIA-CONTEXTUAL.md` | Registro de trabajo del modelo en el chat donde se escribió todo, del 2026-10-18 al 2026-10-22 | Historia |

"Fuente" quiere decir que una historia se corrige ahí primero. Una tarjeta que choca con su tanda
está mal y se corrige en el mismo PR.

## 2. Lo que no está

- La guía del docente, el PDF del anexo técnico y la transcripción de sus hojas. La memoria los cita
  con rutas de otro contenedor.
- Los scripts que generaron las láminas, las tarjetas y el tablero. Las láminas, las tarjetas y el
  tablero que cambiaron el 2026-10-24 se regeneraron con ellos fuera del repo.
- `auditoria-crud-catalogos.md`, del último chat.

## 3. Estado al 2026-10-22

30 historias y 529 escenarios. Las 12 que se desarrollan este semestre son las del plan B y están en
`docs/ROADMAP.md`.
