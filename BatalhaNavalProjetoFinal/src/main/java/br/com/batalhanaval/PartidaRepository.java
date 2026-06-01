
package br.com.batalhanaval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PartidaRepository {

    public void salvarPartida(Jogo jogo, String vencedor) {
        if (!Config.dbEnabled()) {
            return;
        }

        try (Connection c = Database.conectar()) {
            c.setAutoCommit(false);

            long partidaId = inserirPartida(c, vencedor, jogo.getInicio(), jogo.getFim(), jogo.getSeed());
            inserirJogador(c, partidaId, jogo.getHumano());
            inserirJogador(c, partidaId, jogo.getCpu());

            for (Jogada jogada : jogo.getJogadas()) {
                inserirJogada(c, partidaId, jogada);
            }

            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long inserirPartida(Connection c,
                                String vencedor,
                                long inicio,
                                long fim,
                                long seed) throws Exception {
        try (PreparedStatement ps = c.prepareStatement(
                "INSERT INTO partidas(inicio, fim, vencedor, seed) VALUES(?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, inicio);
            ps.setLong(2, fim);
            ps.setString(3, vencedor);
            ps.setLong(4, seed);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }

            throw new IllegalStateException("Não foi possível salvar a partida");
        }
    }

    private void inserirJogador(Connection c, long partidaId, Jogador jogador) throws Exception {
        try (PreparedStatement ps = c.prepareStatement(
                "INSERT INTO jogadores(partida_id, nome, tipo) VALUES(?, ?, ?)")
        ) {
            ps.setLong(1, partidaId);
            ps.setString(2, jogador.getNome());
            ps.setString(3, jogador.tipo());
            ps.executeUpdate();
        }
    }

    private void inserirJogada(Connection c, long partidaId, Jogada jogada) throws Exception {
        try (PreparedStatement ps = c.prepareStatement(
                "INSERT INTO jogadas(partida_id, turno, jogador, coordenada, resultado) VALUES(?, ?, ?, ?, ?)")
        ) {
            ps.setLong(1, partidaId);
            ps.setInt(2, jogada.turno());
            ps.setString(3, jogada.jogador());
            ps.setString(4, jogada.coordenada().toString());
            ps.setString(5, jogada.resultado().name());
            ps.executeUpdate();
        }
    }

    public List<Partida> listarPartidas() {
        List<Partida> partidas = new ArrayList<>();

        if (!Config.dbEnabled()) {
            return partidas;
        }

        try (Connection c = Database.conectar();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT id, inicio, fim, vencedor, seed FROM partidas ORDER BY id DESC")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                partidas.add(new Partida(
                        rs.getLong("id"),
                        rs.getLong("inicio"),
                        rs.getLong("fim"),
                        rs.getString("vencedor"),
                        rs.getLong("seed")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return partidas;
    }

    public Partida buscarPartida(long id) {
        if (!Config.dbEnabled()) {
            return null;
        }

        try (Connection c = Database.conectar();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT id, inicio, fim, vencedor, seed FROM partidas WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Partida(
                        rs.getLong("id"),
                        rs.getLong("inicio"),
                        rs.getLong("fim"),
                        rs.getString("vencedor"),
                        rs.getLong("seed")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Jogada> listarJogadas(long partidaId) {
        List<Jogada> jogadas = new ArrayList<>();

        if (!Config.dbEnabled()) {
            return jogadas;
        }

        try (Connection c = Database.conectar();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT turno, jogador, coordenada, resultado FROM jogadas " +
                             "WHERE partida_id = ? ORDER BY turno ASC")) {
            ps.setLong(1, partidaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Coordenada coordenada = Coordenada.parse(rs.getString("coordenada"));
                ResultadoTiro resultado = ResultadoTiro.valueOf(rs.getString("resultado"));

                jogadas.add(new Jogada(
                        rs.getInt("turno"),
                        rs.getString("jogador"),
                        coordenada,
                        resultado
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jogadas;
    }
}
