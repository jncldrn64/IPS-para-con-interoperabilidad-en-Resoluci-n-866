package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException {
		copiarSiFalta(Path.of("data/ips.mv.db"), Path.of("data/prueba.mv.db"));
		SpringApplication.run(DemoApplication.class, args);
	}

	// El programa abre la base de prueba (application.properties). Sin esta copia arranca con la
	// base vacía, y abrir data/ips.mv.db directo lo cambia en cada corrida aunque no guarde nada
	// (docs/DESIGN.md, sección 8).
	public static boolean copiarSiFalta(Path archivo, Path copia) throws IOException {
		if (Files.exists(copia) || !Files.exists(archivo)) {
			return false;
		}
		Files.copy(archivo, copia);
		return true;
	}
}
