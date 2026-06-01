package br.com.batalhanaval;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidadorDeFrotaTest {

    @Test
    void deveValidarFrotaCorreta() {
        Tabuleiro tabuleiro = new Tabuleiro(10);

        tabuleiro.posicionarNavio(new Navio("PortaAvioes", 5), new Coordenada(0, 0), true);
        tabuleiro.posicionarNavio(new Navio("Encouracado", 4), new Coordenada(1, 0), true);
        tabuleiro.posicionarNavio(new Navio("Cruzador", 3), new Coordenada(2, 0), true);
        tabuleiro.posicionarNavio(new Navio("Submarino", 3), new Coordenada(3, 0), true);
        tabuleiro.posicionarNavio(new Navio("Destroyer", 2), new Coordenada(4, 0), true);

        ResultadoValidacao resultado = new ValidadorDeFrota().validar(tabuleiro);

        assertTrue(resultado.ok());
        assertTrue(resultado.erros().isEmpty());
    }

    @Test
    void deveDetectarFrotaIncorreta() {
        Tabuleiro tabuleiro = new Tabuleiro(10);

        tabuleiro.posicionarNavio(new Navio("Destroyer", 2), new Coordenada(0, 0), true);

        ResultadoValidacao resultado = new ValidadorDeFrota().validar(tabuleiro);

        assertFalse(resultado.ok());
        assertFalse(resultado.erros().isEmpty());
    }
}
