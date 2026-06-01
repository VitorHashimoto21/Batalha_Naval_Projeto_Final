
package br.com.batalhanaval;

import java.util.ArrayList;
import java.util.List;

public class Navio {

    private final String nome;
    private final int tamanho;
    private final List<Coordenada> partes = new ArrayList<>();
    private int hits;

    public Navio(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
    }

    public String getNome() {
        return nome;
    }

    public void adicionar(Coordenada c) {
        partes.add(c);
    }

    public List<Coordenada> getPartes() {
        return List.copyOf(partes);
    }

    public boolean contem(Coordenada c) {
        return partes.contains(c);
    }

    public void acertar() {
        hits++;
    }

    public boolean afundou() {
        return hits >= tamanho;
    }

    public int getTamanho() {
        return tamanho;
    }
}
