
package br.com.batalhanaval;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class JogadorCPU extends Jogador {

    private final Random random;
    private final Set<Coordenada> tirosRealizados = new HashSet<>();

    public JogadorCPU(String nome, Random random) {
        super(nome);
        this.random = random;
    }

    @Override
    public Coordenada jogar() {

        int size = Config.boardSize();
        Coordenada tiro;

        do {
            tiro = new Coordenada(
                    random.nextInt(size),
                    random.nextInt(size)
            );
        } while (!tirosRealizados.add(tiro));

        return tiro;
    }

    @Override
    public String tipo() {
        return "CPU";
    }
}
