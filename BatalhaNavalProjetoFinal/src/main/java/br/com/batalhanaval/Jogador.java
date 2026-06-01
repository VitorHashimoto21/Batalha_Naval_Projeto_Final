
package br.com.batalhanaval;

public abstract class Jogador {

    protected final String nome;
    protected final Tabuleiro tabuleiro;

    public Jogador(String nome) {
        this.nome = nome;
        this.tabuleiro = new Tabuleiro(Config.boardSize());
    }

    public String getNome() {
        return nome;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public abstract Coordenada jogar();

    public abstract String tipo();
}
