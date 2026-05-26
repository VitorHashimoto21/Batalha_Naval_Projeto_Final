
package br.com.batalhanaval;

public record Coordenada(int linha, int coluna) {

    public static Coordenada parse(String valor) {

        valor = valor.toUpperCase();

        if (valor.length() < 2)
            throw new IllegalArgumentException("Coordenada inválida");

        int coluna = valor.charAt(0) - 'A';
        int linha = Integer.parseInt(valor.substring(1)) - 1;

        if (linha < 0 || linha >= Config.boardSize())
            throw new IllegalArgumentException("Linha inválida");

        if (coluna < 0 || coluna >= Config.boardSize())
            throw new IllegalArgumentException("Coluna inválida");

        return new Coordenada(linha, coluna);
    }
}
