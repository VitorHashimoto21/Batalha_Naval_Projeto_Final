
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
                            "inicio INTEGER DEFAULT 0, " +
                            "fim INTEGER DEFAULT 0, " +
                            "vencedor TEXT, " +
                            "seed INTEGER DEFAULT 0)"
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

            ensureColumn(c, "partidas", "inicio", "INTEGER DEFAULT 0");
            ensureColumn(c, "partidas", "fim", "INTEGER DEFAULT 0");
            ensureColumn(c, "partidas", "seed", "INTEGER DEFAULT 0");
            ensureColumn(c, "partidas", "vencedor", "TEXT");

            ensureColumn(c, "jogadores", "partida_id", "INTEGER");
            ensureColumn(c, "jogadores", "nome", "TEXT");
            ensureColumn(c, "jogadores", "tipo", "TEXT");

            ensureColumn(c, "jogadas", "partida_id", "INTEGER");
            ensureColumn(c, "jogadas", "turno", "INTEGER");
            ensureColumn(c, "jogadas", "jogador", "TEXT");
            ensureColumn(c, "jogadas", "coordenada", "TEXT");
            ensureColumn(c, "jogadas", "resultado", "TEXT");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ensureColumn(Connection connection, String tableName, String columnName, String definition) throws Exception {
        if (!hasColumn(connection, tableName, columnName)) {
            try (Statement s = connection.createStatement()) {
                s.execute(String.format("ALTER TABLE %s ADD COLUMN %s %s", tableName, columnName, definition));
            }
        }
    }

    private static boolean hasColumn(Connection connection, String tableName, String columnName) throws Exception {
        try (Statement s = connection.createStatement();
             java.sql.ResultSet rs = s.executeQuery("PRAGMA table_info(" + tableName + ")")) {
            while (rs.next()) {
                if (columnName.equalsIgnoreCase(rs.getString("name"))) {
                    return true;
                }
            }
        }
        return false;
    }
}
