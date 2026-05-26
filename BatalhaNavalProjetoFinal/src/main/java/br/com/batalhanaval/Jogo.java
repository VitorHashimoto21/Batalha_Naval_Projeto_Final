
package br.com.batalhanaval;

public class Jogo {

    private final JogadorHumano humano;
    private final JogadorCPU cpu;

    public Jogo() {

        humano = new JogadorHumano("Jogador");
        cpu = new JogadorCPU("CPU");

        configurar(humano);
        configurar(cpu);
    }

    private void configurar(Jogador jogador) {

        String[] nomes = Config.fleetNames();
        int[] tamanhos = Config.fleetSizes();

        int linha = 0;

        for (int i = 0; i < tamanhos.length; i++) {

            jogador.getTabuleiro().posicionarNavio(
                    new Navio(nomes[i], tamanhos[i]),
                    new Coordenada(linha, 0),
                    true
            );

            linha += 2;
        }
    }

    public void iniciar() {

        while (true) {

            InterfaceTerminal.mostrarTabuleiro(
                    humano.getTabuleiro()
            );

            Coordenada tiro = humano.jogar();

            ResultadoTiro resultado =
                    cpu.getTabuleiro().atirar(tiro);

            InterfaceTerminal.mensagem(resultado);

            if (cpu.getTabuleiro().fimDeJogo()) {

                System.out.println("Vitória do jogador!");

                new PartidaRepository()
                        .salvar("Jogador");

                break;
            }

            Coordenada cpuTiro = cpu.jogar();

            ResultadoTiro r2 =
                    humano.getTabuleiro().atirar(cpuTiro);

            System.out.println(
                    "CPU atacou: " +
                    (char)('A' + cpuTiro.coluna()) +
                    (cpuTiro.linha() + 1)
            );

            InterfaceTerminal.mensagem(r2);

            if (humano.getTabuleiro().fimDeJogo()) {

                System.out.println("Vitória da CPU!");

                new PartidaRepository()
                        .salvar("CPU");

                break;
            }
        }
    }
}
