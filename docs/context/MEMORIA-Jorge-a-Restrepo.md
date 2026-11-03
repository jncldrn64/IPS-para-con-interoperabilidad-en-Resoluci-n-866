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

- Jorge adjuntó `EstCIUO88AC.pdf`, la estructura de la CIUO-88 A.C. del DANE, sin texto. El modelo
  lo pasó a CSV y lo cargó en el archivo de la base, en el mismo PR de HU-022 (`docs/DECISIONS.md`,
  2026-11-01 "La CIUO-88 A.C. entra al archivo de la base").

## 2026-11-01: HU-026

- "se puede decir que esa historia de usuario ya quedo?"
- El modelo le dijo que el código de HU-022 está terminado, pero que falta fusionar los PR #24 y
  #25, en ese orden, y que el conteo de pacientes y atenciones espera HU-004 y HU-007.
- Jorge adjuntó la tarjeta de HU-026, igual a la del repo: "sigamos con esta otra historia".
- La rama de HU-026 sale de la de HU-022. Lo que decidió el modelo está en `docs/DECISIONS.md`,
  2026-11-01 "HU-026 guarda el departamento en su tabla".

## 2026-11-02: HU-004

- "analisa el el repo haber si si lo an modificado los compañeros"
- "auq ehoras actualizaron el repo"
- El autor fusionó los PR #24 a #26 el 2026-11-02 a las 14:54, hizo HU-035, HU-018, HU-034 y
  HU-038, y pasó HU-004 a Jorge (`docs/DECISIONS.md`, 2026-11-02 "El autor termina los catálogos
  del integrante 2 y Jorge toma HU-004").
- "vuelve y analiza el repo haber si ya hay avenzes d elos demasa"
- "si inicia con HU-004"
- Lo que decidió el modelo está en `docs/DECISIONS.md`, 2026-11-02 "HU-004 guarda los códigos y
  junta todos los errores".
- "como se llama el anexo que necesitamos"
- Jorge adjuntó `resolucion-866-de-2021.pdf`. Sus hojas 13 a 15, miradas como imagen, confirman los
  valores y las longitudes de los numerales 2 a 6 y 14 que usa HU-004.

## 2026-11-02: HU-005

- "ahora podemos seguir con la otra historia o que ?"
- El modelo le pidió fusionar antes el PR #37, por la regla del profesor y la de `CLAUDE.md`,
  sección 7. Jorge lo fusionó desde la web, con "Crear una confirmación de fusión": "listo".
- Lo que decidió el modelo está en `docs/DECISIONS.md`, 2026-11-02 "HU-005 saca el nombre del día
  de la tabla de cambios".

## 2026-11-02: HU-006

- "dame la direcion repositorio"
- "pero donce tengo que aporbar la fusion"
- Jorge fusionó el PR #39, de HU-005.
- Jorge adjuntó la tarjeta de HU-006, del integrante 3: "vamos a hacer esta". El modelo revisó que
  no hubiera trabajo del autor en ella, y Jorge escribió: "no la esta haciendo".
- Lo que decidió el modelo está en `docs/DECISIONS.md`, 2026-11-02 "Jorge hace HU-006 con el
  historial común".

## 2026-11-02: la DIVIPOLA

- Jorge fusionó el PR #40, de HU-006.
- "explicame mas esto para comunicarlo con los otrsomiembros"
- "eso a quien le tocaria a mi cuando hice la hu 26?" El modelo le dijo que sí: la carga del
  archivo oficial es parte de HU-026.
- "dime como la busco". Jorge adjuntó `Listados_DIVIPOLA.xlsx`: "si esta".
- Lo que decidió el modelo está en `docs/DECISIONS.md`, 2026-11-02 "La DIVIPOLA entra al archivo
  de la base".

## 3. Pendiente

- Que Jorge revise el PR de la DIVIPOLA, con "Lo que decidió el modelo", y lo fusione. Mientras
  esté abierto tiene el turno de `data/ips.mv.db`: avisar al grupo.
- Avisar al autor que Jorge hizo HU-006, que era suya.
- Con HU-006 están las 12 historias de la fase 0. Lo que sigue lo decide el equipo.
- Una sección "Cómo correr el programa" en `README.md`, para el profesor. Es común.
- Preguntar al profesor si acepta que el programa siga en terminal, qué se exige con los tipos de
  documento AS, MS y SI (D-19) y si le basta la CIUO-88 A.C. completa.
- Revisar qué cuenta de GitHub está conectada en https://claude.ai/connect-github: los PR salen a
  nombre de `jncldrn64`.
