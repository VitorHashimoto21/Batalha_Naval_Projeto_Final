
package br.com.batalhanaval;

import java.util.ArrayList;
import java.util.List;

public class ValidadorDeFrota {

    public ResultadoValidacao validar(Tabuleiro tabuleiro) {

        List<String> erros = new ArrayList<>();

        int[] esperado = Config.fleetSizes();

        if (tabuleiro.getNavios().size() != esperado.length) {
            erros.add("Quantidade incorreta de navios");
        }

        return new ResultadoValidacao(erros.isEmpty(), erros);
    }
}
