
package br.com.batalhanaval;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PartidaRepository {

    public void salvar(String vencedor) {

        try (Connection c = Database.conectar()) {

            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO partidas(vencedor) VALUES(?)"
            );

            ps.setString(1, vencedor);
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
