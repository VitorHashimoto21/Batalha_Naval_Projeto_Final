
package br.com.batalhanaval;

import java.util.Scanner;

public class JogadorHumano extends Jogador {

    private final Scanner sc = new Scanner(System.in);

    public JogadorHumano(String nome) {
        super(nome);
    }

    @Override
    public Coordenada jogar() {
        System.out.print("Digite coordenada: ");
        return Coordenada.parse(sc.nextLine());
    }
}
