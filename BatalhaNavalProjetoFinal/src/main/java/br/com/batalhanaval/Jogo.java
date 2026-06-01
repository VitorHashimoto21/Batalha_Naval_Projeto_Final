
package br.com.batalhanaval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogo {

    private final JogadorHumano humano;
    private final JogadorCPU cpu;
    private final Random random;
    private final long seed;
    private long inicio;
    private long fim;
    private int turno;
    private final List<Jogada> jogadas = new ArrayList<>();

    public Jogo() {
        this(new Random(Config.gameSeed()));
    }

    public Jogo(Random random) {
        this.random = random;
        this.seed = Config.gameSeed();
        this.humano = new JogadorHumano("Jogador");
        this.cpu = new JogadorCPU("CPU", random);
    }

    public JogadorHumano getHumano() {
        return humano;
    }

    public JogadorCPU getCpu() {
        return cpu;
    }

    public long getSeed() {
        return seed;
    }

    public long getInicio() {
        return inicio;
    }

    public long getFim() {
        return fim;
    }

    public List<Jogada> getJogadas() {
        return List.copyOf(jogadas);
    }

    public void iniciarPartida() {
        this.inicio = System.currentTimeMillis();
        this.fim = 0;
        this.turno = 1;
        this.jogadas.clear();
    }

    public void encerrarPartida() {
        this.fim = System.currentTimeMillis();
    }

    public boolean configurarFrotaAutomaticamente(Jogador jogador) {

        String[] nomes = Config.fleetNames();
        int[] tamanhos = Config.fleetSizes();

        for (int i = 0; i < tamanhos.length; i++) {

            Navio navio = new Navio(nomes[i], tamanhos[i]);
            boolean colocado = false;

            while (!colocado) {
                boolean horizontal = random.nextBoolean();
                int linha = random.nextInt(Config.boardSize());
                int coluna = random.nextInt(Config.boardSize());

                colocado = jogador.getTabuleiro().posicionarNavio(
                        navio,
                        new Coordenada(linha, coluna),
                        horizontal
                );
            }
        }

        return true;
    }

    public boolean tentarPosicionarNavio(Jogador jogador,
                                         Navio navio,
                                         Coordenada inicio,
                                         boolean horizontal) {

        return jogador.getTabuleiro().posicionarNavio(navio, inicio, horizontal);
    }

    public ResultadoTiro atacar(Jogador atacante,
                                Jogador defensor,
                                Coordenada tiro) {

        ResultadoTiro resultado = defensor.getTabuleiro().atirar(tiro);
        jogadas.add(new Jogada(turno++, atacante.getNome(), tiro, resultado));
        return resultado;
    }

    public int naviosVivos(Jogador jogador) {
        return jogador.getTabuleiro().naviosVivos();
    }

    public boolean fimDeJogo(Jogador jogador) {
        return jogador.getTabuleiro().fimDeJogo();
    }
}
