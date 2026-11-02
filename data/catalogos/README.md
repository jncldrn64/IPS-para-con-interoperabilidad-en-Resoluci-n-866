# data/catalogos/: los archivos oficiales que se cargan

Este README solo describe esta carpeta, así que no es un documento canónico ni pide permiso. Cómo
se carga un archivo está en `docs/DESIGN.md`, secciones 7 y 8.

Cada archivo es un CSV de RC-8: UTF-8, separado por punto y coma, con las columnas `codigo` y
`nombre`. Un catálogo que publica una entidad del Estado se copia citando la entidad y la dirección
de descarga (`CLAUDE.md`, sección 10), y la carga las guarda en su versión.

## 1. ciuo-88-ac.csv

- **Entidad:** DANE, Clasificación Internacional Uniforme de Ocupaciones Adaptada para Colombia,
  CIUO-88 A.C., estructura general.
- **Dirección:** https://www.dane.gov.co/files/sen/nomenclatura/ciuo/EstCIUO88AC.pdf
- **Recibido:** el 2026-11-01, adjunto por Jorge. Su fecha de creación, según `pdfinfo`, es el
  2006-10-12, y la carga la usa como fecha de publicación.
- **Filas:** 562, con 10 códigos de 1 dígito, 34 de 2, 117 de 3 y 401 de 4, los de la CIUO-88 A.C.
- **Cómo se pasó a CSV:** `pdftotext -layout`, una fila por código y las líneas sin código
  pegadas al nombre anterior. Los nombres quedan como en el PDF, con los grupos en mayúsculas. La fila de la
  3229 se parte entre las páginas 8 y 9, y la 9 repite al comienzo la última línea de su nombre: el
  CSV la lleva una sola vez (`docs/DECISIONS.md`, 2026-11-01 "La CIUO-88 A.C. entra al archivo de
  la base").
