package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.demo.modelo.Etnia;
import com.example.demo.servicio.CrudCatalogo;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Los escenarios de HU-036, en docs/context/trello/tarjetas/HU-036.md. Cada método se llama
 * esc<N>_<alternativa>, por el número del escenario y el caso de su "Dado" que prueba.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU036EtniasTest {

    @Autowired CrudCatalogo<Etnia> etnias;

    @Test
    @DisplayName("HU-036 Esc. 10: un código que no cumple el numeral 13 no se guarda")
    void esc10_datoQueNoCumpleSuNumeral() {
        for (String codigo : new String[] {"1", "123", "123456", "0A", null}) {
            assertThrows(ConstraintViolationException.class,
                    () -> etnias.guardar(new Etnia(codigo, "X")), "aceptó el código " + codigo);
        }
    }

    @Test
    @DisplayName("HU-036 Esc. 10: una etnia sin nombre no se guarda")
    void esc10_nombreVacio() {
        assertThrows(ConstraintViolationException.class,
                () -> etnias.guardar(new Etnia("02", " ")));
    }
}
