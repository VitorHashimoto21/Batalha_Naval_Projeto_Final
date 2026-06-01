package br.com.batalhanaval;

public record Jogada(
        int turno,
        String jogador,
        Coordenada coordenada,
        ResultadoTiro resultado
) {
}
