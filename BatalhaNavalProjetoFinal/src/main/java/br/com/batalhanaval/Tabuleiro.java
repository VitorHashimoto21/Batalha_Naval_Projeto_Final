
package br.com.batalhanaval;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

    private final int tamanho;
    private final char[][] mapa;
    private final List<Navio> navios = new ArrayList<>();

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        mapa = new char[tamanho][tamanho];

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                mapa[i][j] = '.';
            }
        }
    }

    public boolean posicionarNavio(
            Navio navio,
            Coordenada inicio,
            boolean horizontal) {

        for (int i = 0; i < navio.getTamanho(); i++) {

            int linha = inicio.linha();
            int coluna = inicio.coluna();

            if (horizontal)
                coluna += i;
            else
                linha += i;

            if (linha >= tamanho || coluna >= tamanho)
                return false;

            if (mapa[linha][coluna] == 'N')
                return false;
        }

        for (int i = 0; i < navio.getTamanho(); i++) {

            int linha = inicio.linha();
            int coluna = inicio.coluna();

            if (horizontal)
                coluna += i;
            else
                linha += i;

            mapa[linha][coluna] = 'N';
            navio.adicionar(new Coordenada(linha, coluna));
        }

        navios.add(navio);

        return true;
    }

    public ResultadoTiro atirar(Coordenada c) {

        char valor = mapa[c.linha()][c.coluna()];

        if (valor == 'X' || valor == 'o') {
            return ResultadoTiro.AGUA;
        }

        if (valor == 'N') {
            mapa[c.linha()][c.coluna()] = 'X';

            for (Navio n : navios) {
                if (n.contem(c)) {
                    n.acertar();

                    if (n.afundou())
                        return ResultadoTiro.AFUNDOU;
                }
            }

            return ResultadoTiro.ACERTO;
        }

        mapa[c.linha()][c.coluna()] = 'o';

        return ResultadoTiro.AGUA;
    }

    public boolean fimDeJogo() {
        return navios.stream().allMatch(Navio::afundou);
    }

    public int naviosVivos() {
        return (int) navios.stream().filter(n -> !n.afundou()).count();
    }

    public char[][] getMapa() {
        return mapa;
    }

    public List<Navio> getNavios() {
        return navios;
    }
}
