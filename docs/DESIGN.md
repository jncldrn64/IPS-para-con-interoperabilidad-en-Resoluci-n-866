# DESIGN.md: cómo se escribe el código

> **Rol:** normativo sobre el código. Cada principio dice cómo se escribe el código y por qué. Manda
> sobre el código; `docs/REQUIREMENTS.md` manda sobre él. Describe reglas; el estado, qué rutas,
> recursos o funciones existen hoy, va en `docs/ARCHITECTURE.md`. **Régimen:** se corrige, y un
> principio nuevo o cambiado lleva su decisión. **Origen:** plantilla 1.2, sembrada el 2026-10-22
> (`docs/DECISIONS.md`, 2026-10-22 "Se adopta la plantilla 1.2").

Un cambio que rompe un principio de acá se rechaza, lo haya escrito una persona o un modelo. Ante la
duda, se copia la forma del código que ya existe en vez de inventar un idioma nuevo.

Todavía no hay código del producto. Los principios 1 y 2 salen de decisiones que el equipo ya
propuso para el modelo de datos; el código que los cumpla no existe.

## 1. Los códigos del anexo

**Regla:** un componente que el anexo tipa Alfanumérico se guarda como texto, con su longitud y sus
ceros iniciales; uno tipado Numérico se guarda como número. La atención y el paciente guardan el
código, y el nombre para mostrar vive en la tabla del catálogo.

**Por qué:** como número, el municipio 05001 pasa a 5001 y el código de vía de ingreso "01" pasa a
1, y ninguno de los dos se puede entregar con la forma del anexo. El autor aceptó la regla el
2026-10-18 y quedó como propuesta D-18 en el tablero del equipo.

**Dónde está escrito:** `docs/context/MEMORIA-CONTEXTUAL.md`, entrada del 2026-10-18 "El autor
acepta que Alfanumérico se guarda como texto", y D-18 en `docs/REQUIREMENTS.md`, sección 6.

## 2. Un catálogo no borra

**Regla:** cada catálogo es una tabla con código, nombre y estado. Un valor se inactiva con su
motivo; no hay borrado físico. Un registro que usa un valor guarda el código y el nombre del día en
que se hizo.

**Por qué:** el artículo 7 de la Resolución 866 prohíbe a la IPS reducir los catálogos, y un
registro viejo tiene que seguir mostrando el valor con que se hizo (RC-4, RC-10 y RC-11; propuestas
D-20 y D-25).

**Dónde está escrito:** `docs/context/modelo-datos/modelo-entidad-relacion.md`, secciones "Tablas"
y "Reglas que el modelo tiene que cumplir".

## 3. Los tests

**Regla:** un escenario de una historia se da por cumplido solo con una corrida que lo muestra
(`CLAUDE.md`, sección 8). Hoy el único test es `DemoApplicationTests.contextLoads`, que generó
Spring Initializr, y no se corrió (`docs/ARCHITECTURE.md`, sección 6). Cómo se escribe el test de un
escenario está en la sección 6 de este archivo.

## 4. Presentación y registro

**Regla:** la pantalla de cada historia sigue su lámina en `docs/context/trello/mockups/`, donde una
etiqueta "Esc. N" dice qué escenario muestra cada panel.

La guía pide una aplicación web, y el docente aceptó terminal: el primer sprint tiene al menos el
esqueleto en terminal (`docs/DECISIONS.md`, 2026-10-24 "El primer sprint arranca en terminal").
Qué imprime el programa, a qué stream y qué se registra está en la sección 6, sin escribir todavía.

## 5. Los comentarios

**Regla:** un comentario que protege una línea dice qué se rompe sin ella, para que nadie la borre
sin reproducir ese fallo antes. Un comentario que afirma algo del código es una afirmación como
cualquier otra, y cambia en el mismo commit que el código que describe.

**Por qué:** las dos salen de comparar los scripts y los repos del autor el 2026-10-20. La primera
ya era la costumbre de los que más se habían roto, donde la línea que evita un fallo traía un
comentario que lo nombraba.

La segunda responde a un comentario que afirmó durante meses lo contrario de lo que hacía el código
de abajo.

**Dónde está escrito:** el idioma y la voz de un comentario están en `CLAUDE.md`, sección 9.

**Cómo se comprueba:** a mano, en cada PR que toca código. Quien cambia una línea lee los
comentarios que tiene encima, porque ninguna herramienta los revisa.

## 6. Sin escribir todavía

Este archivo es una semilla mientras esta lista tenga algo.

- Las reglas de presentación y registro del esqueleto en terminal, que salen de las decisiones de
  arquitectura.
- Qué base de datos se usa: `pom.xml` trae controladores de H2, SQL Server, MySQL y MariaDB.
- El paquete y el nombre del artefacto: hoy son `com.example.demo` y `demo`, los de Spring
  Initializr.
- El idioma de los identificadores del código.
- Cómo se escribe y se nombra el test de cada escenario, para que se vea qué escenario prueba.
- Cómo se comparte la pantalla de catálogo entre los 8 catálogos del paciente, que tienen los mismos
  escenarios en sus tarjetas (`docs/context/trello/tarjetas/`).
