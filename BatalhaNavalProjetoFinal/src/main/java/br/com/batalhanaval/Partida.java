package br.com.batalhanaval;

public record Partida(
        long id,
        long inicio,
        long fim,
        String vencedor,
        long seed
) {
}
