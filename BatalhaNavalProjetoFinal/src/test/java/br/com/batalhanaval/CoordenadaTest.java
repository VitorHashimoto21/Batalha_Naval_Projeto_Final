package br.com.batalhanaval;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoordenadaTest {

    @Test
    void deveParsearCoordenadaValida() {
        Coordenada coordenada = Coordenada.parse("B3");

        assertEquals(2, coordenada.linha());
        assertEquals(1, coordenada.coluna());
    }

    @Test
    void deveRejeitarCoordenadaComFormatoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Coordenada.parse("1A"));
        assertThrows(IllegalArgumentException.class, () -> Coordenada.parse("Z9"));
        assertThrows(IllegalArgumentException.class, () -> Coordenada.parse("A11"));
    }
}
