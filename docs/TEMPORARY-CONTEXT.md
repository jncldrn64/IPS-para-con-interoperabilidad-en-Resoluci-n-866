# TEMPORARY-CONTEXT.md: lo que se pierde si no se anota

> **Rol:** tránsito. Acá va lo que se observó y se perdería si nadie lo escribe, antes de saber si
> se quiere. No manda nunca. **Régimen:** tiende a cero; su medida es la velocidad con que se vacía,
> no lo que contiene. **Origen:** plantilla 1.2, sembrada el 2026-10-22 (`docs/DECISIONS.md`,
> 2026-10-22 "Se adopta la plantilla 1.2").
>
> **El criterio de entrada no es si está maduro: es si se pierde.**

## Entrada: barata

Una línea: qué se observó, quién lo aportó y por qué podría importar. **Sin evidencia obligatoria,
sin pregunta formulada, sin campos.** Si no se sabe por qué importa, se escribe igual y se dice.

**El modelo escribe sin pedir permiso**, el que implementa y el que revisa, y también lo que no
viene del autor. Nada de eso justifica interrumpir una conversación, y todo se pierde si no hay
dónde ponerlo.

**Lo obligatorio es la fecha y quién anotó. Y a veces una cosa más.**

## La cita textual, y cuándo es obligatoria

Toda anotación es el resumen que un modelo hizo de lo que alguien dijo. Cuando el autor describe
algo que todavía no sabe nombrar, su frase es ambigua, y esa ambigüedad es el estado real de la
idea. Un resumen limpio decide qué quiso decir, y la ambigüedad desaparece junto con el rastro de
que la hubo.

**Por eso, cuando la anotación sale de palabras del autor y esas palabras están a mano, van
textuales y sin corregir.** **Es obligatoria cuando la anotación nombra algo que todavía no tiene
línea en `docs/GLOSSARY.md`**: estrenar vocabulario mal es lo que cuesta una sesión deshacer.

**Una cita no se reconstruye.** Si las palabras no están, la anotación lo dice. **Se acota a lo
técnico:** se cita lo que se dijo sobre el trabajo, no la conversación.

## La prosa acá está exenta

**Este archivo no cumple las reglas de "Prosa" ni de "Guion largo" de `CLAUDE.md`.** Puede ser feo
y telegráfico, y está declarado para que nadie lo marque. Encarecer la escritura es lo que garantiza
que no se escriba.

## Salida: ahí está toda la disciplina

Cada línea se coloca o se descarta. Cuatro destinos y ninguno más:

- **Un PR que la ataque de inmediato.**
- **El Backlog de `docs/ROADMAP.md`**, con el porqué que la línea traía.
- **Una fase.**
- **El descarte**, si resulta duplicada o irrelevante.

**La cita se va con la línea.** Con una excepción: si al colocarla la cita muestra que el resumen
leyó la idea al revés, esa corrección se escribe en el destino.

## La frontera con el Backlog y los huecos

**Al Backlog va lo que ya se sabe que se quiere.** **A los huecos conocidos de
`docs/ARCHITECTURE.md` va lo que no se verificó contra datos reales.** **Acá va lo que todavía no
se sabe si se quiere.**

## Cuándo anotar y cuándo recoger

**Primero se pregunta, después se anota.** Si el alcance se abre y lo anterior no quedó escrito, lo
correcto es preguntarle al autor si conviene darle forma antes de seguir. Este archivo es la red
para cuando esa pregunta no se hace o no se acepta.

**Anotar** cuando una rama de alcance se está cerrando, a medida que aparece. **Recoger** cuando esa
rama se cierra.

**Dos números de red de seguridad, que no son el mecanismo.** Anotar si pasaron tres prompts con
discusión y nada anotado. Recoger si pasaron nueve PR sin fase activa y el archivo no se vació. La
regla se escribe para el peor lector: un modelo con ventana chica no reconoce la condición de estado
y uno grande sí.

---

## Anotaciones

- 2026-10-22, modelo de la sesión de siembra: `auditoria-crud-catalogos.md`, la tabla de escenarios
  por operación del chat anterior, no vino en el zip. Se rehízo como la viñeta "CRUD por escenario"
  de cada catálogo. Si aparece, compararla.
- 2026-10-22, modelo: HU-022 mide 15.688 caracteres contados con Python; el chat anterior decía
  15.295. No se sabe con qué se contó. El límite de 16.384 de Trello no está en la documentación
  oficial (memoria, 2026-10-20 "Revisión final cruzada aplicada").
- 2026-10-22, modelo: hilo abierto desde el 2026-10-18, el "pero discrepo" del autor sobre el nombre
  del diagnóstico del numeral 23. No toca las 12; importa cuando entre HU-030.
- 2026-10-22, modelo: el docente pidió el escenario de rol sin permisos en HU-025, HU-026, HU-028 y
  HU-030, y después dijo que el inicio de sesión no aporta valor en la primera entrega. Hoy ese
  escenario es HU-001 esc. 14. Pregunta 2 del plan de desarrollo.
- 2026-10-26, modelo: las horas de la tabla de cambios salen en la zona horaria del equipo que corre
  el programa. Las 7 etnias de `data/ips.mv.db` se cargaron en el contenedor de la sesión, en UTC:
  2026-10-27 02:25, que en Bogotá era 2026-10-26 21:25. No se sabe si importa para RC-5.
- 2026-10-27, modelo: el menú dice "La usan N registros" al inactivar, y el escenario 19 de HU-036 y
  el 18 de HU-037 dicen "cuántos pacientes la usan". No se cambió la palabra porque el autor eligió
  no tocar pantallas. Importa cuando HU-004 cuente pacientes.
