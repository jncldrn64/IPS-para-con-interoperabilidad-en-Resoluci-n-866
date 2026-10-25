# Revisión independiente de la tanda 2 (HU-007 a HU-009)

Revisor: agente independiente, 2026-10-20. Copia del informe que entregó; no modificó archivos del
proyecto. Recortes del PDF para el triage: scratchpad `revisorT2/h17-bottom.png` y `h18-top.png`.

## Resumen del revisor

1. Docente: 4 de 6 comentarios resueltos. En parte: HU-007 c1 (no se nombra la versión del CIE-10)
   y HU-009 c2 (qué pasa con los campos que pone el sistema). Tablero: 12 de 15 filas resueltas, 3
   en parte (32, 36 y 41).
2. La lista de datos de HU-007 cuadra con V-18 a V-28. Fallan dos afirmaciones: el triage se define
   entre las hojas 17 y 18, no solo en la 17; la frase de D-22 sobre 4 caracteres choca con el
   código de enfermedad huérfana, que va de 1 a 4.
3. Formato del Prompt 2 completo (15, 13 y 12 escenarios). Falla la coherencia: una precondición de
   HU-008 repite el defecto que marcó el docente; hay propuestas sin marcar (ocupación, fecha de
   inicio, D-09); el tablero no tiene las decisiones que la tanda dice haber dejado allí.

## Cambios por gravedad

### Alta
- A1. D-13: "(hoja 17)" pasa a "(hojas 17 y 18)".
- A2. D-22: acotar al CIE-10; el código de enfermedad huérfana del mismo numeral va de 1 a 4.
- A3. D-09: nombrar una versión como propuesta, con su archivo oficial y su fecha; citar D-09 en
  HU-009 (escenario 4 y supuestos).
- A4. HU-008: quitar la precondición "El paciente tiene registro (HU-004)", que choca con el
  escenario 11.
- A5. HU-009: decir si se corrige el código del prestador, que el nombre del diagnóstico cambia con
  el código, y si la ocupación guardada en la atención se puede corregir.

### Media
- M1. HU-007: la precondición "El paciente tiene registro" choca con el escenario 2; la precondición
  de catálogos activos choca con el escenario 12.
- M2. Ocupación del día: marcarla como propuesta; qué pasa si se corrige la ocupación en HU-006;
  guardarla al crear la atención.
- M3. D-13: según el manual de habilitación de MinSalud (Tomo II, 6.5), "Atención inmediata" incluye
  urgencias, transporte asistencial, atención prehospitalaria y atención del parto. Con la regla
  actual se pediría triage fuera de urgencias. Llevarlo a preguntas-docente.md y dejar la salvedad.
- M4. Supuesto: D-22 depende de que el archivo de D-09 tenga códigos de 4 caracteres para todas las
  categorías.
- M5. Actualizar el tablero (Decisiones y Backlog v3).
- M6. Tabla de propuestas: orden de HU-008, fecha de inicio no posterior al registro, valor de D-09.
- M7. Supuestos: HU-008 con D-08, D-13 y la ocupación; HU-009 con D-09, D-13 y D-15.
- M8. D-05: cómo sabe el sistema la sede; limitar "no hay catálogo de prestadores" al numeral 16.
- M9. HU-007 escenario 6: "no la registra sin ellas" pasa a "no la deja como Registrada sin ellas".
- M10. HU-009 escenario 7: "o no está activo en su catálogo" y "conserva el valor anterior".
- M11. Dependencias: HU-008 con HU-009 y HU-006; HU-009 con HU-030 y HU-031.
- M12. HU-007: escenario "Registro cancelado", distinto de guardar a medias.
- M13. Antiguo escenario 5 de HU-008: declararlo transformado en el 6.
- M14. Pregunta: completar un borrador como escenario de HU-007 y HU-009 solo para corregir.
- M15. Atención registrada al paciente equivocado: estado "Anulada" o pendiente declarado.

### Baja
- B1. Glosario: categoría padre, triage, enfermedad huérfana.
- B2. "En registro" pasa a "Borrador".
- B3. Explicar por qué una atención registrada no se actualiza sino que se corrige.
- B4. HU-007 escenario 1: "la fecha y la hora del registro"; escenario 8: "con el nombre que le da
  el catálogo".
- B5. HU-009: escenario 5 "conserva el diagnóstico anterior"; escenario 6 "dice cuál dato falta";
  escenario 1 quién completó, con fecha y hora.
- B6. HU-009 escenario 3: no "guarda" la categoría padre.
- B7. HU-008 escenario 2: el Dado no repite la acción del Cuando.
- B8. Detalle de HU-008: agregar la ocupación del día.
- B9. Trazabilidad: cambios de título, motivo de la salida del antiguo escenario 5 de HU-007, destino
  de "sigue asociada al mismo paciente" (antiguo 10 de HU-009), c1 de HU-009 cita solo el escenario 2.
- B10. Nombre del dato del numeral 16 como en el anexo.
- B11. HU-009 escenario 8: qué pasa con el triage si el grupo deja de ser atención inmediata.
- B12. Preguntas abiertas: ¿cualquier médico completa o corrige una atención de otro? ¿Quién figura
  como autor al completar? ¿Cambiar un valor en un borrador deja rastro?
- B13. RC-6 pega los datos del corrector al dato nuevo; los escenarios, al anterior.
- B14. Tanda 0 y HU-001 escenario 6: unificar "modificar y corregir" con "completar y corregir".

## Fuentes externas que citó
- Manual de Inscripción de Prestadores y Habilitación, Tomo II (MinSalud), numeral 6.5:
  https://www.minsalud.gov.co/sites/rid/Lists/BibliotecaDigital/RIDE/DE/CA/manual-inscripcion-prestadores-habilitacion-ss-t2.pdf
  (leído con una herramienta que resume; falta confirmarlo en la página 224).
- La búsqueda sobre el formato de 4 caracteres del CIE-10 ("I10X") no dio fuente primaria
  colombiana: sigue sin verificar.
