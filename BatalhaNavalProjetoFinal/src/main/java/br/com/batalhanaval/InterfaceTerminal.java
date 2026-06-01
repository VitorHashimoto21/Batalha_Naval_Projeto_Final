
package br.com.batalhanaval;

import java.util.Scanner;

public class InterfaceTerminal {

    private static final Scanner SC = new Scanner(System.in);

    public static void mostrarTabuleiro(Tabuleiro t, boolean showShips) {
        char[][] mapa = t.getMapa();
        int size = Config.boardSize();

        System.out.println("\n=== TABULEIRO ===");
        System.out.print("   ");
        char start = Config.boardColumnsStart().charAt(0);

        for (int c = 0; c < size; c++) {
            System.out.print((char) (start + c) + " ");
        }

        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.printf("%2d ", i + 1);
            for (int j = 0; j < size; j++) {
                char cell = mapa[i][j];
                if (!showShips && cell == 'N') {
                    cell = '.';
                }
                System.out.print(cell + " ");
            }
            System.out.println();
        }

        if (Config.uiShowLegend()) {
            mostrarLegenda();
        }
    }

    public static void mostrarDoisTabuleiros(Tabuleiro meu, Tabuleiro ataques, boolean showOwnShips) {
        int size = Config.boardSize();
        String header = "   ";
        char start = Config.boardColumnsStart().charAt(0);

        for (int c = 0; c < size; c++) {
            header += (char) (start + c) + " ";
        }

        System.out.printf("%s   %s%n", "SEU TABULEIRO", "SEU ATAQUE");
        System.out.printf("%s   %s%n", header, header);

        for (int i = 0; i < size; i++) {
            String leftRow = String.format("%2d ", i + 1);
            String rightRow = String.format("%2d ", i + 1);

            for (int j = 0; j < size; j++) {
                char cell = meu.getMapa()[i][j];
                if (!showOwnShips && cell == 'N') {
                    cell = '.';
                }
                leftRow += cell + " ";
            }

            for (int j = 0; j < size; j++) {
                char cell = ataques.getMapa()[i][j];
                if (cell == 'N') {
                    cell = '.';
                }
                rightRow += cell + " ";
            }

            System.out.printf("%s   %s%n", leftRow, rightRow);
        }

        if (Config.uiShowLegend()) {
            mostrarLegenda();
        }
    }

    public static void mostrarDoisMapas(char[][] meu, char[][] ataques) {
        int size = Config.boardSize();
        String header = "   ";
        char start = Config.boardColumnsStart().charAt(0);

        for (int c = 0; c < size; c++) {
            header += (char) (start + c) + " ";
        }

        System.out.println("\n=== REPLAY ===");
        System.out.printf("%s   %s%n", "JOGADOR", "CPU");
        System.out.printf("%s   %s%n", header, header);

        for (int i = 0; i < size; i++) {
            String leftRow = String.format("%2d ", i + 1);
            String rightRow = String.format("%2d ", i + 1);

            for (int j = 0; j < size; j++) {
                leftRow += meu[i][j] + " ";
            }
            for (int j = 0; j < size; j++) {
                rightRow += ataques[i][j] + " ";
            }

            System.out.printf("%s   %s%n", leftRow, rightRow);
        }
    }

    public static String lerTexto(String prompt) {
        System.out.print(prompt);
        return SC.nextLine().trim();
    }

    public static int lerInteiro(String prompt, int min, int max) {
        while (true) {
            try {
                int valor = Integer.parseInt(lerTexto(prompt));
                if (valor < min || valor > max) {
                    mostrarErro("Valor deve estar entre " + min + " e " + max + ".");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                mostrarErro("Digite um número válido.");
            }
        }
    }

    public static boolean lerSimNao(String prompt) {
        while (true) {
            String valor = lerTexto(prompt);
            if (valor.equalsIgnoreCase("s") || valor.equalsIgnoreCase("sim")) {
                return true;
            }
            if (valor.equalsIgnoreCase("n") || valor.equalsIgnoreCase("nao") || valor.equalsIgnoreCase("não")) {
                return false;
            }
            mostrarErro("Digite S para sim ou N para não.");
        }
    }

    public static Coordenada lerCoordenada(String prompt) {
        while (true) {
            try {
                return Coordenada.parse(lerTexto(prompt));
            } catch (IllegalArgumentException e) {
                mostrarErro("Coordenada inválida. Use o formato A1 até J10.");
            }
        }
    }

    public static void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public static void mensagem(ResultadoTiro resultado) {
        System.out.println(resultado.mensagem());
    }

    public static void mostrarErro(String mensagem) {
        System.out.println("[ERRO] " + mensagem);
    }

    public static void mostrarLegenda() {
        System.out.println("\nLegenda:");
        System.out.println(". = vazio");
        System.out.println("N = navio");
        System.out.println("X = acerto");
        System.out.println("o = água");
    }
}
