
package br.com.batalhanaval;

public abstract class Jogador {

    protected String nome;
    protected Tabuleiro tabuleiro;

    public Jogador(String nome) {
        this.nome = nome;
        this.tabuleiro = new Tabuleiro(Config.boardSize());
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public abstract Coordenada jogar();
}
