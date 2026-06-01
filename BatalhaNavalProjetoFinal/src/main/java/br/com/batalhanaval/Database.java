
package br.com.batalhanaval;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {

    public static Connection conectar() throws Exception {
        return DriverManager.getConnection(
                "jdbc:sqlite:" + Config.dbFile()
        );
    }

    public static void migrate() {
        if (!Config.dbEnabled() || !"sqlite".equalsIgnoreCase(Config.dbType()) || !Config.dbAutoMigrate()) {
            return;
        }

        try (Connection c = conectar();
             Statement s = c.createStatement()) {

            s.execute(
                    "CREATE TABLE IF NOT EXISTS partidas (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "inicio INTEGER, " +
                            "fim INTEGER, " +
                            "vencedor TEXT, " +
                            "seed INTEGER)"
            );

            s.execute(
                    "CREATE TABLE IF NOT EXISTS jogadores (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "partida_id INTEGER, " +
                            "nome TEXT, " +
                            "tipo TEXT, " +
                            "FOREIGN KEY(partida_id) REFERENCES partidas(id))"
            );

            s.execute(
                    "CREATE TABLE IF NOT EXISTS jogadas (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "partida_id INTEGER, " +
                            "turno INTEGER, " +
                            "jogador TEXT, " +
                            "coordenada TEXT, " +
                            "resultado TEXT, " +
                            "FOREIGN KEY(partida_id) REFERENCES partidas(id))"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
