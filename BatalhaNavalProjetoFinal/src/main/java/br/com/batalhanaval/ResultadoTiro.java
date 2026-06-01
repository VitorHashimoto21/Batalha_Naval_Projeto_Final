
package br.com.batalhanaval;

public enum ResultadoTiro {
    AGUA("Água!"),
    ACERTO("Acerto!"),
    AFUNDOU("Navio afundado!");

    private final String mensagem;

    ResultadoTiro(String mensagem) {
        this.mensagem = mensagem;
    }

    public String mensagem() {
        return mensagem;
    }
}
