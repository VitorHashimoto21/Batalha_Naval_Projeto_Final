
package br.com.batalhanaval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValidadorDeFrota {

    public ResultadoValidacao validar(Tabuleiro tabuleiro) {
        List<String> erros = new ArrayList<>();
        int[] esperado = Config.fleetSizes();
        List<Navio> navios = tabuleiro.getNavios();

        if (navios.size() != esperado.length) {
            erros.add("Quantidade incorreta de navios");
        }

        Map<Integer, Integer> esperadoCount = new HashMap<>();
        for (int size : esperado) {
            esperadoCount.put(size, esperadoCount.getOrDefault(size, 0) + 1);
        }

        Map<Integer, Integer> atualCount = new HashMap<>();
        Set<Coordenada> coordenadas = new HashSet<>();

        for (Navio navio : navios) {
            atualCount.put(navio.getTamanho(), atualCount.getOrDefault(navio.getTamanho(), 0) + 1);
            validarNavio(navio, erros);

            for (Coordenada coordenada : navio.getPartes()) {
                if (!coordenadas.add(coordenada)) {
                    erros.add("Sobreposição de navios detectada");
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : esperadoCount.entrySet()) {
            int tamanho = entry.getKey();
            int quantidade = entry.getValue();
            int atual = atualCount.getOrDefault(tamanho, 0);
            if (atual != quantidade) {
                erros.add("Frota incorreta: esperado " + quantidade + " navio(s) de tamanho " + tamanho + ", mas encontrou " + atual + ".");
            }
        }

        return new ResultadoValidacao(erros.isEmpty(), erros);
    }

    private void validarNavio(Navio navio, List<String> erros) {
        List<Coordenada> partes = navio.getPartes();

        if (partes.isEmpty()) {
            erros.add("Navio " + navio.getNome() + " não possui posições.");
            return;
        }

        boolean mesmaLinha = partes.stream().allMatch(c -> c.linha() == partes.get(0).linha());
        boolean mesmaColuna = partes.stream().allMatch(c -> c.coluna() == partes.get(0).coluna());

        if (!mesmaLinha && !mesmaColuna) {
            erros.add("Navio " + navio.getNome() + " deve ser horizontal ou vertical.");
            return;
        }

        List<Integer> valores = new ArrayList<>();
        if (mesmaLinha) {
            for (Coordenada coordenada : partes) {
                valores.add(coordenada.coluna());
            }
        } else {
            for (Coordenada coordenada : partes) {
                valores.add(coordenada.linha());
            }
        }

        valores.sort(Integer::compareTo);

        for (int i = 1; i < valores.size(); i++) {
            if (valores.get(i) != valores.get(i - 1) + 1) {
                erros.add("Navio " + navio.getNome() + " deve ocupar posições consecutivas.");
                break;
            }
        }
    }
}
