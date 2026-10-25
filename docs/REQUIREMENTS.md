# REQUIREMENTS.md: qué tiene que ser verdad, y para quién

> **Rol:** normativo, el más alto sobre el producto. Dice qué tiene que ser verdad para que el
> programa esté bien hecho, y para quién. No lleva fechas ni orden de trabajo, que van en
> `docs/ROADMAP.md`. No dice por qué ganó una forma, que va en `docs/DECISIONS.md`. **Régimen:** se
> corrige, y cada cambio lleva su decisión. **Origen:** plantilla 1.2, sembrada el 2026-10-22
> (`docs/DECISIONS.md`, 2026-10-22 "Se adopta la plantilla 1.2").

Los corchetes, como [2.3], remiten a los numerales de la guía del docente, "Proyecto de Clase, IPS
Salud y Vida". "Numeral N del anexo" es un dato del anexo técnico de la Resolución 866 de 2021.

## 1. Para quién es

La IPS Salud y Vida necesita registrar a sus pacientes y sus atenciones con los datos que fija la
Resolución 866 de 2021, para que la información se pueda intercambiar con otros prestadores. Hoy no
tiene un sistema que lo haga (épica en `docs/context/backlog-v3-tanda-0-epica-y-reglas.md`,
sección 1).

Lo usan tres cargos, con los permisos de la matriz de la misma tanda 0, sección 2:

| Cargo | Para qué |
|---|---|
| Auxiliar de admisión | Registra, consulta, actualiza y corrige los datos del paciente |
| Médico tratante | Consulta al paciente sin cambiarlo; registra y consulta las atenciones |
| Profesional de estadística | Carga los catálogos desde el archivo oficial y los mantiene iguales a la fuente (D-02, propuesta del equipo) |

Si falla, el paciente repite sus datos en cada atención y los datos guardados no sirven para
intercambiarse, porque no usan los códigos oficiales.

También lo lee el docente, que evalúa el backlog, la modelación de datos y el código del semestre.

## 2. Qué tiene que ser verdad

El detalle de cada historia, con sus escenarios, está en las tandas del backlog v3
(`docs/context/backlog-v3-tanda-*.md`), y las reglas comunes RC-1 a RC-11 en la tanda 0, sección 3.
Lo de abajo es lo que no puede ser falso en ninguna historia.

1. **Cada dato se valida con su numeral del anexo:** valores permitidos, formato y longitud (RC-7).
2. **Todo campo con más de 5 opciones seleccionables tiene entidad propia y su CRUD** [2.3]. "Más de
   5" quiere decir 6 o más valores (`docs/DECISIONS.md`, 2026-10-22 "El umbral de catálogo es más
   de 5 valores").
3. **La IPS no modifica, reduce ni adiciona los catálogos oficiales** (artículo 7 de la Resolución
   866). Un catálogo se carga desde su archivo oficial, con su versión, y un valor se crea o cambia
   solo con la referencia de un cambio oficial (RC-3, RC-8 y RC-9).
4. **Nada se borra.** El Delete de un catálogo es inactivar con un motivo de lista cerrada, y los
   registros que ya usan el valor lo conservan (RC-4 y RC-10; D-20, propuesta del equipo y pregunta
   6 al docente).
5. **Corregir agrega, no reemplaza:** "se agregará el nuevo dato con la fecha, hora, nombre e
   identificación de quien hizo la corrección" (artículo 10, parágrafo 2; RC-6).
6. **Un registro conserva el código y el nombre que tenía el valor el día en que se hizo** (RC-11;
   D-25, propuesta del equipo).
7. **El paciente cumple las reglas de la guía** [2.1]: una o varias nacionalidades, fecha y hora de
   nacimiento por separado, solo ocupaciones hijas de la CIUO-88 A.C., una o varias categorías de
   discapacidad y la ciudad de residencia con su código DANE.
8. **La oposición a la donación y la voluntad anticipada son estructuras aparte, con su CRUD**
   [2.1].

## 3. Qué no es

- No tiene registro de usuarios ni recuperación de cuenta [2.4]. Los usuarios y sus cargos se crean
  al instalar el sistema (supuesto del equipo).
- No cubre los numerales 24 a 49 del anexo, tecnologías en salud y resultados, mientras el docente
  no confirme el alcance (D-16, pregunta 1 al docente).
- No decide qué entra en un catálogo: lo decide la fuente oficial.

## 4. Requisitos no funcionales

- **Un código se entrega con la forma del anexo.** Un código alfanumérico conserva su longitud y sus
  ceros iniciales: Medellín es 05001, no 5001. El medio está en `docs/DESIGN.md`, sección 1.
- **La guía pide una aplicación web.** El autor propuso una terminal con H2 para el primer sprint;
  sigue abierto (D-10).

## 5. Sin escribir todavía

Este archivo es una semilla mientras esta lista tenga algo.

- Las respuestas del docente a las 14 preguntas de `docs/context/preguntas-docente.md` y a las 4 de
  `docs/context/plan-desarrollo-12-historias.md`, sección 7. Varias cambian las reglas de la
  sección 2.
- Qué se exige al registrar un paciente con un tipo de documento sin identificación (D-19).
- Si el producto es web o de terminal (D-10).
