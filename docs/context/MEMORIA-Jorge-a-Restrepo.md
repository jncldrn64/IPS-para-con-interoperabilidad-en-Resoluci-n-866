# MEMORIA-Jorge-a-Restrepo.md: lo que decide el integrante 1 y lo pendiente

> **Rol:** memoria del modelo que trabaja con Jorge, el integrante 1. Guarda lo que él decide y lo
> pendiente. No manda sobre un documento canónico. **Régimen:** las decisiones son append-only, con
> entradas fechadas; lo pendiente se corrige. **Origen:** creada el 2026-10-27 en su primera sesión
> (`CLAUDE.md`, sección 1; `docs/DECISIONS.md`, 2026-10-27 "Cada integrante trabaja con su
> modelo").

## 0. Cómo se usa

1. El modelo de Jorge lee este archivo entero antes de trabajar, después de lo que pide
   `CLAUDE.md`, sección 1.
2. Las palabras de Jorge van textuales y sin corregir. Lo que el modelo infiere va marcado
   **Hipótesis:**.

## 1. Integrante

- Nombre: Jorge. Usuario de GitHub: Jorge-a-Restrepo. Integrante 1.
- Sus historias, en orden de paso (`docs/ROADMAP.md`, fase 0): HU-001, HU-022 y HU-026 en el
  tramo 1; HU-005 en el tramo 3.

## 2. Decisiones

## 2026-10-27: primera sesión

- "Soy el integrante # 1 de ese proyecto, puedes hacer una pull resquet en mi nombre? Soy
  Jorge-a-Restrepo"
- El modelo lo leyó como el PR de prueba de `CLAUDE.md`, sección 7. **Hipótesis:** no pidió otro
  cambio, y ese PR es lo que toca antes de su primera historia.

## 2026-10-31: HU-001

- "te pregunto si es en terminal no deveria de trabajarlo en visual y despues subir al repo y el
  profesor como hace despues para utilizar ese repo"
- El modelo le explicó que la terminal es la interfaz del programa y no dónde se programa, que el
  docente aceptó terminal y que cambiarla es común. Le recomendó confirmarlo con el profesor.
- "Falta algo: el README no explica cómo correrlo. Hoy solo dice “empezá por CLAUDE.md tengamos
  este pendiente ud me puede recordar mañana 1 d enoveimbre a las 12 pm"
- "empesemos la primera historia"
- El modelo le propuso cinco respuestas: terminal, BCrypt, tres usuarios de prueba, 15 minutos
  medidos antes de cada acción y el menú con lo que ya existe. Jorge no las contestó antes de pedir
  que se empezara, y el modelo las tomó. Están en `docs/DECISIONS.md`, 2026-10-31 "HU-001 abre la
  sesión en la terminal", en "Lo que decidió el modelo". **Hipótesis:** fusionar el PR las acepta.
- Para compilar en la nube, Jorge activó en claude.ai el acceso de red saliente de la ejecución de
  código. Maven Central contestó a veces 429 por la IP compartida, y Maven reintenta con
  `-Daether.connector.http.retryHandler.count=8`.

## 2026-11-01: HU-022

- "ya podriamos pasar a la historia de usaurio 2"
- El modelo lo leyó como HU-022, su segunda historia. **Hipótesis:** no hay HU-002 entre las 12.
- El PR #24 seguía en borrador, así que la rama de HU-022 sale de la de HU-001.
- Lo que decidió el modelo está en `docs/DECISIONS.md`, 2026-11-01 "HU-022 lee el grupo del código
  y lo revisa con el archivo entero".

## 3. Pendiente

- Que el autor cierre sin fusionar el PR de prueba. **Hipótesis:** el PR #23 se fusionó el
  2026-10-27, según `git log`, y la regla pedía cerrarlo sin fusionar.
- Que Jorge revise el PR de HU-001, con la lista "Lo que decidió el modelo", y lo fusione.
- Una sección "Cómo correr el programa" en `README.md`, para el profesor. Es común, y Jorge pidió
  que se le recordara el 2026-11-01 a las 12:00.
- Preguntar al profesor si acepta que el programa siga en terminal.
- Que Jorge revise y fusione el PR de HU-022, después del de HU-001.
- Bajar la estructura de la CIUO-88 A.C. del DANE y cargarla en el archivo de la base, con su
  turno. Jorge puede adjuntar el PDF para que el modelo lo pase a CSV.
- Después: HU-026, municipios; HU-005 cuando esté HU-004.
