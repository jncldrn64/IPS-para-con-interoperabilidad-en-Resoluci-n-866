package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.demo.modelo.ComunidadEtnica;
import com.example.demo.servicio.CrudCatalogo;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Los escenarios de HU-037, en docs/context/trello/tarjetas/HU-037.md. Cada método se llama
 * esc<N>_<alternativa>, por el número del escenario y el caso de su "Dado" que prueba.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HU037ComunidadesEtnicasTest {

    @Autowired CrudCatalogo<ComunidadEtnica> comunidades;

    @Test
    @DisplayName("HU-037 Esc. 9: un código que no cumple el numeral 13 no se guarda")
    void esc9_datoQueNoCumpleSuNumeral() {
        for (String codigo : new String[] {"01", "1234", "0AB", null}) {
            assertThrows(ConstraintViolationException.class,
                    () -> comunidades.guardar(new ComunidadEtnica(codigo, "X")),
                    "aceptó el código " + codigo);
        }
    }

    @Test
    @DisplayName("HU-037 Esc. 9: una comunidad sin nombre no se guarda")
    void esc9_nombreVacio() {
        assertThrows(ConstraintViolationException.class,
                () -> comunidades.guardar(new ComunidadEtnica("001", " ")));
    }
}
