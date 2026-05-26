
package br.com.batalhanaval;

import java.util.List;

public record ResultadoValidacao(
        boolean ok,
        List<String> erros
) {
}
