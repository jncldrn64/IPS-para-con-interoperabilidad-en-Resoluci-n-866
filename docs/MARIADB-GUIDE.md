# MARIADB-GUIDE.md: cómo pasar la base a MariaDB y cargar los catálogos

> **Rol:** descriptivo. Dice, paso a paso, cómo se conecta el programa a MariaDB y cómo se carga
> cada catálogo, archivo por archivo o valor por valor, con la corrida que lo mostró. Si el código
> cambia y esta guía no, gana el código. Cómo se corre el programa en general está en `README.md`,
> y de dónde sale cada archivo, en `data/catalogos/README.md`. **Régimen:** se corrige.
> **Origen:** pedida por el autor el 2026-11-03 (`docs/DECISIONS.md`, 2026-11-03 "Cada dato dice
> qué espera antes de pedirlo").

## 1. Lo que se necesita

- Java 21 y el repo clonado, como dice `README.md`.
- MariaDB. La demostración de esta guía usó MariaDB 10.11.14 en el contenedor de la sesión, con
  Linux. En Windows no se probó.
- El driver de MariaDB ya viene en `pom.xml` (`mariadb-java-client`), así que no hay que instalar
  nada más del lado de Java.

## 2. Crear la base y el usuario

Desde el cliente de MariaDB, con un usuario administrador. La base va en `utf8mb4`, para que las
tildes y la ñ se guarden bien:

```sql
CREATE DATABASE ips CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'ips'@'localhost' IDENTIFIED BY 'ClaveIps123';
GRANT ALL PRIVILEGES ON ips.* TO 'ips'@'localhost';
FLUSH PRIVILEGES;
```

`ClaveIps123` es la clave de la demostración. En la VM conviene otra, y no se escribe en ningún
archivo del repo.

## 3. Cambiar la conexión

El programa abre la base que diga `spring.datasource.url`. Se cambia al arrancar, sin tocar
`application.properties`, que es común a los tres integrantes. También sirve
`-Dspring-boot.run.arguments`, como dice `docs/DESIGN.md`, sección 8, para `data/ips`.

**Con variables de entorno**, la forma más simple en PowerShell, porque evita las comillas. Valen
para la ventana donde se escriben, y se probaron en Linux pero no en PowerShell:

```sh
$env:SPRING_DATASOURCE_URL="jdbc:mariadb://localhost:3306/ips"
$env:SPRING_DATASOURCE_USERNAME="ips"
$env:SPRING_DATASOURCE_PASSWORD="ClaveIps123"
chcp 65001
.\mvnw.cmd spring-boot:run
```

En Linux, la misma idea en una sola línea:

```sh
SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:3306/ips SPRING_DATASOURCE_USERNAME=ips \
  SPRING_DATASOURCE_PASSWORD=ClaveIps123 sh ./mvnw spring-boot:run
```

La primera línea del programa dice qué base abrió. La contraseña sale con asteriscos:

```
Base abierta: jdbc:mariadb://localhost/ips?user=ips&password=***
```

Si esa línea dice `jdbc:h2:file:./data/prueba`, las variables no llegaron: se escribieron en otra
ventana, o con otro nombre.

## 4. Lo que hace la primera corrida

Con la base vacía, Hibernate crea las tablas (`spring.jpa.hibernate.ddl-auto=update`) y el
programa crea los tres usuarios de prueba, con la clave `Clave123`. En la demostración quedaron 21
tablas, contando las secuencias, y las 26 llaves foráneas del modelo E-R. Los catálogos quedan
vacíos: se llenan como dicen las secciones 5 y 6.

El programa también copia `data/ips.mv.db` a `data/prueba.mv.db` si falta, aunque abra MariaDB.
Esa copia no se usa y no estorba.

## 5. Cargar catálogo por catálogo

Se entra con `estadistica`, se elige el catálogo en el menú principal y, dentro de él, la opción 8.
Antes de preguntar, el programa dice qué pide cada dato. Los datos de cada archivo son estos, los
mismos de `data/catalogos/README.md`:

| Menú | Catálogo | Archivo | Versión | Publicación | Descarga |
|---|---|---|---|---|---|
| 6 | Etnias | `data/catalogos/etnias-hoja-15.csv` | Resolución 866 de 2021 | 2021-06-25 | 2026-10-18 |
| 5 | Categorías de discapacidad | `data/catalogos/discapacidad-hojas-14-15.csv` | Resolución 866 de 2021 | 2021-06-25 | 2026-11-02 |
| 4 | Tipos de documento | `data/catalogos/tipos-documento-hoja-13.csv` | Resolución 866 de 2021 | 2021-06-25 | 2026-11-02 |
| 2 | Ocupaciones | `data/catalogos/ciuo-88-ac.csv` | CIUO-88 A.C., estructura general | 2006-10-12 | 2026-11-01 |
| 1 | Países | `data/catalogos/pais-sispro.xlsx` | Tabla Pais de SISPRO | 2024-02-26 | 2026-11-02 |
| 8 | Entidades del plan de beneficios | `data/catalogos/eapb-sispro.xlsx` | Tabla CodigoEAPByNit de SISPRO | 2026-01-30 | 2026-11-02 |
| 3 | Municipios | `data/catalogos/divipola-municipios.csv` | DIVIPOLA septiembre 2026 | 2026-09-30 | 2026-11-02 |

La fuente y la dirección de descarga de cada uno están en su sección de `data/catalogos/README.md`.
El orden no importa, porque ningún catálogo depende de otro. Comunidades étnicas, la opción 7, se
llena valor por valor mientras no tenga archivo (sección 6).

Esto imprimió la demostración en las siete cargas, con la línea de cada una:

```
== Etnias
Carga: primera carga
Filas leídas: 7. Aceptadas: 7. Rechazadas: 0.
Guardada la versión Resolución 866 de 2021, publicada el 2021-06-25.
== Categorías de discapacidad
Filas leídas: 8. Aceptadas: 8. Rechazadas: 0.
== Tipos de documento
Filas leídas: 14. Aceptadas: 14. Rechazadas: 0.
== Ocupaciones
Filas leídas: 562. Aceptadas: 562. Rechazadas: 0.
== Países
Filas leídas: 249. Aceptadas: 249. Rechazadas: 0.
== Entidades del plan de beneficios
Filas leídas: 1717. Aceptadas: 1715. Rechazadas: 2.
Deshabilitadas por la fuente, entran o quedan inactivas: 314.
== Municipios
Filas leídas: 1122. Aceptadas: 1122. Rechazadas: 0.
```

Las 2 rechazadas de las entidades son `UT001_` y `UT004_`, que la fuente trae deshabilitadas y con
un guion bajo que el numeral 15 no admite. A la pregunta "¿Descargar la lista de rechazadas?" se
puede contestar `s` para verlas en un archivo al lado del Excel.

## 6. Cargar valor por valor

En el catálogo, la opción 2, "Crear por cambio oficial". El programa dice primero qué pide cada
dato, con un ejemplo del catálogo si ya tiene valores. Así salió en Países:

```
Lo que pide cada dato:
  - Código: el código de un país tiene 3 dígitos (numerales 1 y 11). Solo el código, sin el
    nombre ni espacios, y con sus ceros a la izquierda. Ejemplo de este catálogo: 004 (AFGANISTÁN).
  - Nombre: el nombre de un país tiene de 3 a 200 caracteres (numerales 1 y 11). Escríbalo como
    la fuente oficial, con sus tildes; no agregue el código ni comentarios.
  - Referencia oficial: el documento que respalda el cambio, con su número y su fecha, como
    "Resolución 866 de 2021, anexo técnico, hoja 15". No puede quedar vacía.
```

En la demostración se creó así la comunidad étnica 010, ACHAGUA, la primera de la tabla
LCEResguardos de SISPRO:

```
== Comunidades étnicas
Código: 010
Nombre: ACHAGUA
Referencia oficial: Prueba de la guía: tabla LCEResguardos de SISPRO
¿Confirma? (s/n): s
Creado: 010
```

Un código que no cumple su regla no se guarda, y el programa dice cuál regla falló, como "No se
guardó: el código de una comunidad tiene 3 dígitos (numeral 13)". La opción 3 cambia el nombre, la
4 reemplaza un código por otro, la 5 inactiva y la 6 reactiva; cada una dice también qué pide.

## 7. Comprobar en MariaDB

El cliente de MariaDB muestra bien las tildes con `--default-character-set=utf8mb4`; sin eso,
"Indígena" puede salir con un signo raro aunque esté bien guardada. Cuántos valores quedaron:

```sql
SELECT 'pais', COUNT(*) FROM pais UNION ALL SELECT 'municipio', COUNT(*) FROM municipio
UNION ALL SELECT 'entidad_plan_beneficios', COUNT(*) FROM entidad_plan_beneficios;
```

En la demostración dio 249 países, 1.122 municipios con sus 33 departamentos, 1.715 entidades,
562 ocupaciones, 14 tipos de documento, 8 categorías, 7 etnias y la comunidad de prueba. Después
se registró un paciente como `admision`, con las respuestas de `HU004RegistroPacienteTest`, y un
`JOIN` por las llaves foráneas trajo el nombre de su municipio y de su entidad:

```
tipo_documento  numero_documento  primer_apellido  municipio  entidad
CC              1020304050        PÉREZ            MEDELLÍN   NUEVA EMPRESA PROMOTORA DE SALUD ...
```

La base rechaza un código que no está en su catálogo:

```
INSERT INTO paciente_discapacidad (paciente_id, categoria) VALUES (1, '77');
ERROR 1452 (23000): Cannot add or update a child row: a foreign key constraint fails
```

## 8. Lo que no se probó

- MariaDB en Windows, que es la VM del autor. La demostración fue en Linux.
- Una base de MariaDB que ya tenga datos de otra versión del programa. `ddl-auto=update` agrega lo
  que falta, pero no borra columnas ni cambia sus tipos.
- Los tests corren sobre H2 en memoria, no sobre MariaDB.
