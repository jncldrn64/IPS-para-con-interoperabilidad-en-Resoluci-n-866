# docs/context/: las historias, sus láminas, el modelo E-R y la memoria

Este README solo explica esta carpeta. Qué manda sobre qué está en `CLAUDE.md`, sección 2, y por qué
la carpeta tiene esto y nada más, en `docs/DECISIONS.md`, 2026-10-24 "docs/context guarda solo lo
que usan las historias".

## 1. Qué es cada cosa

| Ruta | Qué es | Tipo |
|---|---|---|
| `trello/tarjetas/` | Una tarjeta por historia, de HU-001 a HU-043, más la de reglas comunes, con la matriz de permisos y RC-1 a RC-11. Es el mismo texto que se pega en Trello | Fuente |
| `trello/mockups/` | Una lámina PNG por historia, con etiquetas "Esc. N" que dicen qué escenario muestra cada panel | Sale de las tarjetas |
| `modelo-datos/` | Modelo entidad-relación propuesto, 24 tablas en Mermaid, y dos PNG. La guía pide el diagrama E-R en el repositorio [3.6] | Borrador |
| `MEMORIA-CONTEXTUAL.md` | Memoria de trabajo del modelo desde el 2026-10-18: decisiones del autor con sus palabras, bitácora y pendientes | Memoria del modelo |

"Fuente" quiere decir que una historia se corrige ahí primero, y su lámina se rehace si el cambio la
toca. "Memoria del modelo" quiere decir que se actualiza con cada decisión del autor y no manda
sobre los documentos de `docs/` (`docs/DECISIONS.md`, 2026-10-24 "La memoria contextual vuelve a
ser la memoria del modelo").

Las decisiones del equipo que citan las tarjetas, de D-01 a D-27, están en `docs/REQUIREMENTS.md`,
sección 6.

## 2. Lo que salió

El 2026-10-24 salieron de esta carpeta las seis tandas del backlog v3, el tablero del equipo, las
preguntas al docente, el plan de desarrollo, el plan de la reescritura del backlog, las revisiones
y las instrucciones del kit de Trello. Todo sigue en la historia de git, en el commit fa95eaf, y se
recupera con `git show fa95eaf:docs/context/<archivo>`.

Lo que no está en ninguna otra parte del repositorio:

- Qué comentario del docente atiende cada escenario. Está en la sección de trazabilidad de cada
  historia en las tandas y en las hojas Retroalimentación y Ajustes por historia del tablero.
- Cómo se armó cada historia y por qué: las tandas y las revisiones.

## 3. Lo que nunca estuvo

- Los scripts que generaron las láminas, las tarjetas y el tablero. Las láminas, las tarjetas y el
  tablero que cambiaron el 2026-10-24 se regeneraron con ellos fuera del repo.
- La guía del docente, el PDF del anexo técnico y las transcripciones del anexo. El autor decidió
  no subirlos el 2026-10-24.
