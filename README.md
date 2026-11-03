# IPS Salud y Vida: registro interoperable bajo la Resolución 866

Proyecto de clase de Fundamentos de Ingeniería de Software, TdeA 2026-2. Es un programa de
terminal para una IPS: registra, consulta y corrige pacientes con los datos que pide la Resolución
866 de 2021, y mantiene los ocho catálogos oficiales de esos datos. Quien trabaja en el código
empieza por `CLAUDE.md`.

## Cómo correrlo

Pide Java 21. Desde la carpeta del repo, en Linux o macOS:

```sh
sh ./mvnw spring-boot:run
```

En Windows, en PowerShell. Si las tildes salen como "?", la consola no tiene las letras del
español, y `chcp 65001` antes del programa la pasa a UTF-8:

```sh
chcp 65001
.\mvnw.cmd spring-boot:run
```

El programa trabaja sobre `data/prueba.mv.db`, una copia de `data/ips.mv.db` que crea la primera
vez, así que se puede ensayar sin dañar el archivo de la base. Para usar MariaDB, la guía es
`docs/MARIADB-GUIDE.md`.

## Cómo se usa

La primera línea dice qué base abrió, y después el programa pide el usuario y la clave, que no se
ve al escribirla. Los tres usuarios de prueba usan la clave `Clave123`:

| Usuario | Cargo | Qué ve |
|---|---|---|
| `admision` | Auxiliar de admisión | Registro, consulta y corrección de pacientes, y la consulta de los catálogos |
| `medico` | Médico tratante | Consulta de pacientes y de los catálogos |
| `estadistica` | Profesional de estadística | Los ocho catálogos, con todas sus opciones |

Cada menú se maneja con el número de la opción y Enter. En el menú principal, `C` cierra la sesión y
`0` sale; dentro de una pantalla, `0` vuelve. Tras 15 minutos sin actividad, la sesión pide entrar
de nuevo. Un valor que no sirve da un mensaje y el menú sigue.

**Los catálogos** (países, ocupaciones, municipios, tipos de documento, categorías de discapacidad,
etnias, comunidades étnicas y entidades del plan de beneficios) tienen las mismas opciones:

| Opción | Qué hace |
|---|---|
| 1 | Consultar los valores activos, los inactivos o todos |
| 2 | Crear un valor por un cambio oficial |
| 3 | Cambiar el nombre de un valor |
| 4 | Cambiar el código: el viejo queda inactivo, "Reemplazado por" el nuevo |
| 5 | Inactivar un valor, con su motivo; nunca se borra |
| 6 | Reactivar un valor inactivo |
| 7 | Ver el historial de un código |
| 8 | Cargar un archivo oficial, CSV o Excel |
| 9 | Buscar por código, exacto, o por parte del nombre |
| 10 | Ver las versiones cargadas |
| 11 | Comparar el catálogo con su lista oficial |

Antes de pedir cada dato, el programa dice qué espera: el formato del código, el largo del nombre,
un ejemplo del catálogo y lo que no se debe escribir. Las opciones 2 a 6 y la 8 son del profesional
de estadística; los demás cargos solo consultan. Los archivos oficiales están en `data/catalogos/`,
con su fuente en `data/catalogos/README.md`.

**El paciente**: el registro pide los 16 datos de la resolución, uno por uno, y en los de catálogo
deja elegir de una lista o buscar por código o por nombre. Si algo está mal, dice qué y pide solo
eso otra vez. La consulta busca por tipo y número de documento y muestra cada dato con el nombre que
tenía el día del registro. La corrección cambia uno o varios datos y guarda quién lo hizo y cuándo.

## Documentación del repo

| Documento | Qué tiene |
|---|---|
| `docs/REQUIREMENTS.md` | Qué tiene que hacer el programa y para quién |
| `docs/ARCHITECTURE.md` | Cómo es el código hoy y qué está abierto |
| `docs/DESIGN.md` | Cómo se escribe el código |
| `docs/ROADMAP.md` | Las historias, quién las hace y qué falta |
| `docs/DECISIONS.md` | Por qué el repo es como es |
| `docs/MARIADB-GUIDE.md` | Cómo pasar la base a MariaDB y cargar los catálogos |
| `docs/context/modelo-datos/` | El modelo entidad-relación |
| `tests/README.md` | Cómo se corren los tests y qué prueba cada uno |
| `CHANGELOG.md` | Qué cambió en cada versión |

## Fuentes oficiales y documentación externa

- [Resolución 866 de 2021, con su anexo técnico](https://www.minsalud.gov.co/sites/rid/Lists/BibliotecaDigital/RIDE/DE/DIJ/resolucion-866-de-2021.pdf),
  en el sitio del Ministerio de Salud y Protección Social.
- [La misma resolución en el normograma de la Superintendencia de Salud](https://normograma.supersalud.gov.co/compilacion/docs/resolucion_minsaludps_0866_2021.htm),
  en texto, más fácil de leer que el PDF.
- Tablas de referencia de SISPRO:
  [Pais](https://web.sispro.gov.co/WebPublico/Consultas/ConsultarDetalleReferenciaBasica.aspx?Code=Pais),
  [CodigoEAPByNit](https://web.sispro.gov.co/WebPublico/Consultas/ConsultarDetalleReferenciaBasica.aspx?Code=CodigoEAPByNit)
  y [Municipio](https://web.sispro.gov.co/WebPublico/Consultas/ConsultarDetalleReferenciaBasica.aspx?Code=Municipio).
- [CIUO-88 A.C., estructura general](https://www.dane.gov.co/files/sen/nomenclatura/ciuo/EstCIUO88AC.pdf),
  del DANE.
- [DIVIPOLA](https://geoportal.dane.gov.co/servicios/descarga-y-metadatos/datos-geoestadisticos/?cod=112),
  en el geoportal del DANE.
- [Spring Boot y JPA](https://docs.spring.io/spring-boot/4.0.8/reference/data/sql.html#data.sql.jpa-and-spring-data)
  y la guía [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/), que vale
  también para MariaDB.
