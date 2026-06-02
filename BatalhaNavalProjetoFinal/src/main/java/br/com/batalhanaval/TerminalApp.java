package br.com.batalhanaval;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TerminalApp {

    private final PartidaRepository repository = new PartidaRepository();

    public void run() {
        String mode = Config.gameMode();

        if (mode.equals("LIST")) {
            listarHistorico();
            return;
        }

        if (mode.equals("REPLAY")) {
            replayPartida();
            return;
        }

        while (true) {
            mostrarMenu();

            int opcao = InterfaceTerminal.lerInteiro("Digite a opção: ", 1, 5);

            switch (opcao) {
                case 1 -> jogarPartida();
                case 2 -> listarHistorico();
                case 3 -> replayPartida();
                case 4 -> mostrarAjuda();
                case 5 -> {
                    InterfaceTerminal.mostrarMensagem("Saindo do jogo. Obrigado por jogar!");
                    return;
                }
                default -> InterfaceTerminal.mostrarErro("Opção inválida.");
            }
        }
    }

    private void mostrarMenu() {
        InterfaceTerminal.mostrarMensagem("\n=== MENU PRINCIPAL ===");
        InterfaceTerminal.mostrarMensagem("1 - Jogar partida");
        InterfaceTerminal.mostrarMensagem("2 - Listar histórico de partidas");
        InterfaceTerminal.mostrarMensagem("3 - Reproduzir partida (replay)");
        InterfaceTerminal.mostrarMensagem("4 - Ajuda / legenda");
        InterfaceTerminal.mostrarMensagem("5 - Sair");
    }

    private void jogarPartida() {
        Jogo jogo = new Jogo();
        boolean manual = InterfaceTerminal.lerSimNao("Deseja posicionar a frota manualmente? (S/N): ");

        if (manual) {
            posicionarFrotaManual(jogo);
        } else {
            jogo.configurarFrotaAutomaticamente(jogo.getHumano());
        }

        jogo.configurarFrotaAutomaticamente(jogo.getCpu());
        jogo.iniciarPartida();
        long partidaId = repository.iniciarPartida(jogo);

        while (true) {
            InterfaceTerminal.mostrarDoisTabuleiros(
                    jogo.getHumano().getTabuleiro(),
                    jogo.getCpu().getTabuleiro(),
                    Config.uiShowOwnShips()
            );

            InterfaceTerminal.mostrarMensagem(
                    String.format("Navios vivos - Jogador: %d | CPU: %d",
                            jogo.naviosVivos(jogo.getHumano()),
                            jogo.naviosVivos(jogo.getCpu()))
            );

            Coordenada tiro = InterfaceTerminal.lerCoordenadaOuVoltar("Digite a coordenada do tiro (0 para voltar ao menu): ");
            if (tiro == null) {
                jogo.encerrarPartida();
                repository.atualizarPartida(partidaId, "ABORTED", jogo.getFim());
                InterfaceTerminal.mostrarMensagem("Voltando ao menu principal. Partida parcial salva no banco.");
                return;
            }
            jogo.getHumano().definirProximoTiro(tiro);
            ResultadoTiro resultado = jogo.atacar(jogo.getHumano(), jogo.getCpu(), tiro);
            repository.inserirJogada(partidaId, jogo.getJogadas().get(jogo.getJogadas().size() - 1));
            InterfaceTerminal.mensagem(resultado);

            if (jogo.fimDeJogo(jogo.getCpu())) {
                InterfaceTerminal.mostrarMensagem("Vitória do jogador!");
                jogo.encerrarPartida();
                repository.atualizarPartida(partidaId, "Jogador", jogo.getFim());
                return;
            }

            Coordenada cpuTiro = jogo.getCpu().jogar();
            ResultadoTiro resultadoCpu = jogo.atacar(jogo.getCpu(), jogo.getHumano(), cpuTiro);
            repository.inserirJogada(partidaId, jogo.getJogadas().get(jogo.getJogadas().size() - 1));
            InterfaceTerminal.mostrarMensagem(
                    "CPU atacou: " + cpuTiro
            );
            InterfaceTerminal.mensagem(resultadoCpu);

            if (jogo.fimDeJogo(jogo.getHumano())) {
                InterfaceTerminal.mostrarMensagem("Vitória da CPU!");
                jogo.encerrarPartida();
                repository.atualizarPartida(partidaId, "CPU", jogo.getFim());
                return;
            }
        }
    }

    private void posicionarFrotaManual(Jogo jogo) {
        String[] nomes = Config.fleetNames();
        int[] tamanhos = Config.fleetSizes();

        for (int i = 0; i < tamanhos.length; i++) {
            String nomeNavio = nomes[i];
            int tamanho = tamanhos[i];
            boolean posicionado = false;

            while (!posicionado) {
                InterfaceTerminal.mostrarTabuleiro(jogo.getHumano().getTabuleiro(), true);
                InterfaceTerminal.mostrarMensagem(String.format("Posicionando %s (tamanho %d)", nomeNavio, tamanho));

                Coordenada inicio = InterfaceTerminal.lerCoordenada("Digite a posição inicial (ex: A1): ");
                boolean horizontal = InterfaceTerminal.lerSimNao("Posicionar horizontalmente? (S/N): ");

                posicionado = jogo.tentarPosicionarNavio(
                        jogo.getHumano(),
                        new Navio(nomeNavio, tamanho),
                        inicio,
                        horizontal
                );

                if (!posicionado) {
                    InterfaceTerminal.mostrarErro("Posição inválida ou área ocupada. Tente novamente.");
                }
            }
        }

        ResultadoValidacao validacao = new ValidadorDeFrota().validar(jogo.getHumano().getTabuleiro());

        if (!validacao.ok()) {
            validacao.erros().forEach(InterfaceTerminal::mostrarErro);
            InterfaceTerminal.mostrarErro("Posicionamento manual inválido. O jogo será reiniciado.");
            jogarPartida();
        }
    }

    private void listarHistorico() {
        if (!Config.dbEnabled()) {
            InterfaceTerminal.mostrarMensagem("Persistência em banco de dados está desativada.");
            return;
        }

        List<Partida> partidas = repository.listarPartidas();

        if (partidas.isEmpty()) {
            InterfaceTerminal.mostrarMensagem("Nenhuma partida foi registrada ainda.");
            return;
        }

        InterfaceTerminal.mostrarMensagem("\n=== HISTÓRICO DE PARTIDAS ===");

        for (Partida partida : partidas) {
            InterfaceTerminal.mostrarMensagem(
                    String.format(
                            "ID: %d | Vencedor: %s | Início: %s | Fim: %s | Seed: %d",
                            partida.id(),
                            partida.vencedor(),
                            Instant.ofEpochMilli(partida.inicio()),
                            Instant.ofEpochMilli(partida.fim()),
                            partida.seed()
                    )
            );
        }
    }

    private void replayPartida() {
        if (!Config.dbEnabled()) {
            InterfaceTerminal.mostrarMensagem("Persistência em banco de dados está desativada.");
            return;
        }

        List<Partida> partidas = repository.listarPartidas();

        if (partidas.isEmpty()) {
            InterfaceTerminal.mostrarMensagem("Não há partidas para reproduzir.");
            return;
        }

        listarHistorico();
        long partidaId = InterfaceTerminal.lerInteiro("Digite o ID da partida para replay: ", 1, Integer.MAX_VALUE);
        Partida partida = repository.buscarPartida(partidaId);

        if (partida == null) {
            InterfaceTerminal.mostrarErro("Partida não encontrada.");
            return;
        }

        List<Jogada> jogadas = repository.listarJogadas(partida.id());
        char[][] meu = criarMapaVazio();
        char[][] ataque = criarMapaVazio();

        InterfaceTerminal.mostrarMensagem("Iniciando replay da partida " + partida.id());

        for (Jogada jogada : jogadas) {
            char simbolo = jogada.resultado() == ResultadoTiro.AGUA ? 'o' : 'X';

            if (jogada.jogador().equalsIgnoreCase("Jogador")) {
                ataque[jogada.coordenada().linha()][jogada.coordenada().coluna()] = simbolo;
            } else {
                meu[jogada.coordenada().linha()][jogada.coordenada().coluna()] = simbolo;
            }

            InterfaceTerminal.mostrarDoisMapas(meu, ataque);
            InterfaceTerminal.mostrarMensagem(
                    String.format("Turno %d | %s | %s | %s",
                            jogada.turno(),
                            jogada.jogador(),
                            jogada.coordenada(),
                            jogada.resultado().mensagem())
            );
            InterfaceTerminal.lerTexto("Pressione Enter para continuar...");
        }

        InterfaceTerminal.mostrarMensagem("Replay concluído.");
    }

    private char[][] criarMapaVazio() {
        int size = Config.boardSize();
        char[][] mapa = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mapa[i][j] = '.';
            }
        }

        return mapa;
    }

    private void salvarPartida(Jogo jogo, String vencedor) {
        if (!Config.dbEnabled()) {
            InterfaceTerminal.mostrarMensagem("Persistência desativada; partida não foi salva.");
            return;
        }

        new PartidaRepository().salvarPartida(jogo, vencedor);
        InterfaceTerminal.mostrarMensagem("Partida salva no banco de dados.");
    }

    private void mostrarAjuda() {
        InterfaceTerminal.mostrarMensagem("\n=== AJUDA ===");
        InterfaceTerminal.mostrarMensagem("Escolha a opção 1 para iniciar uma nova partida.");
        InterfaceTerminal.mostrarMensagem("Use a opção 2 para ver o histórico salvo.");
        InterfaceTerminal.mostrarMensagem("Use a opção 3 para reproduzir uma partida registrada.");
        InterfaceTerminal.mostrarMensagem("Ao jogar, insira coordenadas no formato A1 até J10.");
        InterfaceTerminal.mostrarMensagem("Digite 0 a qualquer momento durante a partida para voltar ao menu principal.");
        InterfaceTerminal.mostrarMensagem("A nave será mostrada apenas no seu tabuleiro, não no tabuleiro de ataque.");
    }
}
