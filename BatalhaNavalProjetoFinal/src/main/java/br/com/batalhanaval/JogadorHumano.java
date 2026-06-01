
package br.com.batalhanaval;

public class JogadorHumano extends Jogador {

    private Coordenada proximoTiro;

    public JogadorHumano(String nome) {
        super(nome);
    }

    public void definirProximoTiro(Coordenada tiro) {
        this.proximoTiro = tiro;
    }

    @Override
    public Coordenada jogar() {
        if (proximoTiro == null) {
            throw new IllegalStateException("Nenhuma coordenada definida para o jogador humano");
        }

        Coordenada tiro = proximoTiro;
        proximoTiro = null;
        return tiro;
    }

    @Override
    public String tipo() {
        return "HUMANO";
    }
}
