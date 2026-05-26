
package br.com.batalhanaval;

import java.util.Random;

public class JogadorCPU extends Jogador {

    private final Random random = new Random();

    public JogadorCPU(String nome) {
        super(nome);
    }

    @Override
    public Coordenada jogar() {

        return new Coordenada(
                random.nextInt(Config.boardSize()),
                random.nextInt(Config.boardSize())
        );
    }
}
