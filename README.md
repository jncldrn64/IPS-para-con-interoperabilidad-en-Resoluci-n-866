# IPS Salud y Vida: registro interoperable bajo la Resolución 866

Proyecto de clase de Fundamentos de Ingeniería de Software, TdeA 2026-2. Empezá por `CLAUDE.md`.

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

Los usuarios de prueba, con la clave `Clave123`, son `estadistica`, `admision` y `medico`, uno por
cargo. El programa trabaja sobre `data/prueba.mv.db`, una copia de `data/ips.mv.db` que crea la
primera vez, así que se puede ensayar sin dañar el archivo de la base.
