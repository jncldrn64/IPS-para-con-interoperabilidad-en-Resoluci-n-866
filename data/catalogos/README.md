# data/catalogos/: los archivos que se cargan

Este README solo describe esta carpeta, así que no es un documento canónico ni pide permiso. Cómo
se carga un archivo está en `docs/DESIGN.md`, secciones 7 y 8, y por qué todos van aquí, en
`docs/DECISIONS.md`, 2026-11-02 "Los CSV de carga van en data/catalogos".

Cada archivo es un CSV de RC-8, en UTF-8, separado por punto y coma y con las columnas `codigo` y
`nombre`, o el Excel (.xlsx) tal como lo exporta la fuente, con esas mismas columnas. Si trae la
columna `Habilitado`, una fila con `NO` entra inactiva. Una lista que transcribe una norma se copia
citando la norma y su hoja, y un catálogo que publica una entidad del Estado, citando la entidad y
la dirección de descarga (`CLAUDE.md`, sección 10). La carga guarda la fuente en su versión, y los
tests leen estos mismos archivos.

## 1. Cómo se rehace la base con estos archivos

`data/ips.mv.db` ya trae los siete catálogos cargados (`docs/ARCHITECTURE.md`, sección 3). Para
cargarlos en una base vacía, como en una demostración:

1. Se arranca el programa desde la raíz del repo con una base vacía. En memoria, como abajo, no
   deja nada en el disco y se borra al salir. Si la base no tiene usuarios, el programa crea los de
   prueba:

   ```sh
   sh ./mvnw spring-boot:run \
     -Dspring-boot.run.arguments=--spring.datasource.url=jdbc:h2:mem:demo
   ```

2. Se entra con el usuario `estadistica` y la clave `Clave123`.
3. En cada catálogo, la opción 8 pide la ruta del archivo y los datos de su versión. Los de cada
   archivo están en su sección. El orden no importa, porque ningún catálogo depende de otro.

## 2. etnias-hoja-15.csv

- **Norma:** Resolución 866 de 2021, anexo técnico, numeral 13, componente etnia, hoja 15.
- **Filas:** 7, de 01 a 06 y 99.
- **Versión con que se cargó:** fuente "Resolución 866 de 2021, anexo técnico, numeral 13, hoja
  15"; dirección
  https://www.minsalud.gov.co/sites/rid/Lists/BibliotecaDigital/RIDE/DE/DIJ/resolucion-866-de-2021.pdf;
  descarga 2026-10-18; versión "Resolución 866 de 2021"; publicación 2021-06-25.

## 3. discapacidad-hojas-14-15.csv

- **Norma:** Resolución 866 de 2021, anexo técnico, numeral 10, hojas 14 y 15. El 05 empieza en la
  hoja 14 y su "(mental)" sigue en la 15; dice "sicosocial", como la hoja.
- **Filas:** 8, de 01 a 08.
- **Versión con que se cargó:** fuente "Resolución 866 de 2021, anexo técnico, numeral 10, hojas 14
  y 15"; la misma dirección que etnias; descarga 2026-11-02; versión "Resolución 866 de 2021";
  publicación 2021-06-25.

## 4. ciuo-88-ac.csv

- **Entidad:** DANE, Clasificación Internacional Uniforme de Ocupaciones Adaptada para Colombia,
  CIUO-88 A.C., estructura general.
- **Dirección:** https://www.dane.gov.co/files/sen/nomenclatura/ciuo/EstCIUO88AC.pdf
- **Recibido:** el 2026-11-01, adjunto por Jorge. Su fecha de creación, según `pdfinfo`, es el
  2006-10-12, y la carga la usa como fecha de publicación.
- **Filas:** 562, con 10 códigos de 1 dígito, 34 de 2, 117 de 3 y 401 de 4, los de la CIUO-88 A.C.
- **Cómo se pasó a CSV:** `pdftotext -layout`, una fila por código y las líneas sin código
  pegadas al nombre anterior. Los nombres quedan como en el PDF, con los grupos en mayúsculas. La
  fila de la 3229 se parte entre las páginas 8 y 9, y la 9 repite al comienzo la última línea de su
  nombre: el CSV la lleva una sola vez (`docs/DECISIONS.md`, 2026-11-01 "La CIUO-88 A.C. entra al
  archivo de la base").
- **Versión con que se cargó:** fuente "DANE, Clasificación Internacional Uniforme de Ocupaciones
  Adaptada para Colombia"; la dirección de arriba; descarga 2026-11-01; versión "CIUO-88 A.C.,
  estructura general"; publicación 2006-10-12.

## 5. pais-sispro.xlsx

- **Entidad:** Ministerio de Salud y Protección Social, SISPRO, tabla de referencia Pais
  (SISPRO-TRV, Estándar Minsalud: Sí).
- **Dirección:** https://web.sispro.gov.co/WebPublico/Consultas/ConsultarDetalleReferenciaBasica.aspx?Code=Pais
- **Recibido:** el 2026-11-02, exportado por el autor con "Exportar a Excel", sin cuenta. Va tal
  como se descargó, sin convertirlo.
- **Filas:** 249, con el código numérico de 3 dígitos de la ISO 3166-1 guardado como texto:
  Colombia es 170 y Afganistán 004. Los nombres van en mayúsculas, como los trae la fuente.
- **Columnas:** la hoja `Table` trae `Tabla`, `Codigo`, `Nombre` y 19 más. La carga usa `Codigo`
  y `Nombre`; `Extra_II` y `Extra_III` traen los códigos de 2 y 3 letras, que la hoja 15 no pide.
- **Versión con que se cargó:** fuente "Ministerio de Salud y Protección Social, SISPRO, tabla de
  referencia Pais"; la dirección de arriba; descarga 2026-11-02; versión "Tabla Pais de SISPRO";
  publicación 2024-02-26, la fecha de actualización de la tabla en el buscador de SISPRO. Cada
  fila dice 2018-10-03 en `Fecha_Actualizacion`.

## 6. tipos-documento-hoja-13.csv

- **Norma:** Resolución 866 de 2021, anexo técnico, numeral 2, hoja 13.
- **Filas:** 14, de CC a SI, en el orden de la hoja. Los nombres van como los escribe la hoja, con
  "Cédula ciudadanía" sin "de".
- **Versión con que se cargó:** fuente "Resolución 866 de 2021, anexo técnico, numeral 2, hoja
  13"; la misma dirección que etnias; descarga 2026-11-02; versión "Resolución 866 de 2021";
  publicación 2021-06-25.

## 7. eapb-sispro.xlsx

- **Entidad:** Ministerio de Salud y Protección Social, SISPRO, tabla de referencia CodigoEAPByNit.
- **Dirección:** https://web.sispro.gov.co/WebPublico/Consultas/ConsultarDetalleReferenciaBasica.aspx?Code=CodigoEAPByNit
- **Recibido:** el 2026-11-02, exportado por el autor sin cuenta. Va tal como se descargó.
- **Filas:** 1.717, 1.401 habilitadas y 316 no. Los códigos van de 4 a 6 caracteres: 1.175 de 5
  dígitos, las direcciones de salud; las EPS y cajas como EPS037 o CCFC07; y las aseguradoras con
  guion, como 13-5. La carga rechaza 2, `UT001_` y `UT004_`, deshabilitadas, por el guion bajo.
- **Columnas:** `Codigo`, `Nombre` y `Habilitado`, más 19 que la carga no usa, como el régimen y
  el NIT.
- **Versión con que se cargó:** fuente "Ministerio de Salud y Protección Social, SISPRO, tabla de
  referencia CodigoEAPByNit"; la dirección de arriba; descarga 2026-11-02; versión "Tabla
  CodigoEAPByNit de SISPRO"; publicación 2026-01-30, la fecha de actualización de todas sus filas.

## 8. municipio-sispro.xlsx y departamento-sispro.xlsx

- **Entidad:** Ministerio de Salud y Protección Social, SISPRO, tablas de referencia Municipio y
  Departamento.
- **Dirección:** https://web.sispro.gov.co/WebPublico/Consultas/ConsultarDetalleReferenciaBasica.aspx?Code=Municipio
  y la misma con `Code=Departamento`.
- **Recibido:** el 2026-11-02, exportadas por el autor sin cuenta. Van tal como se descargaron.
- **Filas:** Municipio, 1.124 códigos de 5 dígitos, uno deshabilitado, con el departamento en
  `Extra_I:Departamento`. Departamento, 33 nombres y la fila `00`, `NO APLICA`, deshabilitada.
- **Sin cargar:** la carga de HU-026 pide el nombre del departamento en el mismo archivo. Los
  municipios se cargaron de la DIVIPOLA del DANE (sección 9).

## 9. divipola-municipios.csv

- **Entidad:** DANE, Codificación de la División Político Administrativa de Colombia, DIVIPOLA,
  archivo `Listados_DIVIPOLA.xlsx`, hoja Municipios, "Actualizado al 30 de septiembre de 2026".
- **Dirección:** https://geoportal.dane.gov.co/servicios/descarga-y-metadatos/datos-geoestadisticos/?cod=112
  con el filtro "Todos los grupos", opción "Listado completos de Codificación Divipola". Es el
  camino que da el DANE en su respuesta https://www.dane.gov.co/files/pqr/Respuesta-202510034970.pdf.
- **Recibido:** el 2026-11-02, adjunto por Jorge.
- **Filas:** 1.122, de 33 departamentos: 1.103 de tipo Municipio, 18 Área no municipalizada y 1
  Isla, San Andrés. Entran las tres.
- **Cómo se pasó a CSV:** con openpyxl, de la hoja Municipios, las columnas del código y el nombre
  del municipio y del departamento. El Excel guarda como número algunos códigos, como el 27493;
  el CSV los lleva como texto de 5 dígitos. Los nombres quedan como en el Excel, sin el espacio de
  sobra de "SOTARÁ ". El nombre de cada departamento coincide con el de la hoja Departamentos.
- **Versión con que se cargó:** fuente "DANE, Codificación de la División Político Administrativa
  de Colombia, DIVIPOLA"; la dirección de arriba; descarga 2026-11-02; versión "DIVIPOLA
  septiembre 2026"; publicación 2026-09-30.
