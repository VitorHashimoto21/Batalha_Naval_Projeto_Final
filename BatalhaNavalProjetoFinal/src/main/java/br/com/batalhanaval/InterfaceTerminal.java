
package br.com.batalhanaval;

public class InterfaceTerminal {

    public static void mostrarTabuleiro(Tabuleiro t) {

        char[][] mapa = t.getMapa();

        System.out.println("\n=== TABULEIRO ===");

        System.out.print("  ");

        for (char c = 'A'; c < 'A' + Config.boardSize(); c++) {
            System.out.print(c + " ");
        }

        System.out.println();

        for (int i = 0; i < mapa.length; i++) {

            System.out.printf("%2d", i + 1);

            for (int j = 0; j < mapa.length; j++) {
                System.out.print(mapa[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println("\nLegenda:");
        System.out.println(". = vazio");
        System.out.println("N = navio");
        System.out.println("X = acerto");
        System.out.println("o = água");
    }

    public static void mensagem(ResultadoTiro r) {

        switch (r) {

            case AGUA -> System.out.println("Água!");
            case ACERTO -> System.out.println("Acerto!");
            case AFUNDOU -> System.out.println("Navio afundado!");
        }
    }
}
