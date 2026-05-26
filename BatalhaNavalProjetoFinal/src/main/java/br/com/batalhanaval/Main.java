
package br.com.batalhanaval;

public class Main {

    public static void main(String[] args) {

        Database.migrate();

        Jogo jogo = new Jogo();
        jogo.iniciar();
    }
}
