
package br.com.batalhanaval;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TabuleiroTest {

    @Test
    void devePosicionarNavio() {

        Tabuleiro t = new Tabuleiro(10);

        boolean ok = t.posicionarNavio(
                new Navio("Destroyer", 2),
                new Coordenada(0,0),
                true
        );

        assertTrue(ok);
    }

    @Test
    void deveDetectarAcerto() {

        Tabuleiro t = new Tabuleiro(10);

        t.posicionarNavio(
                new Navio("Destroyer", 2),
                new Coordenada(0,0),
                true
        );

        ResultadoTiro r =
                t.atirar(new Coordenada(0,0));

        assertEquals(ResultadoTiro.ACERTO, r);
    }

    @Test
    void deveDetectarFimDeJogo() {

        Tabuleiro t = new Tabuleiro(10);

        Navio n = new Navio("Destroyer", 1);

        t.posicionarNavio(
                n,
                new Coordenada(0,0),
                true
        );

        t.atirar(new Coordenada(0,0));

        assertTrue(t.fimDeJogo());
    }
}
