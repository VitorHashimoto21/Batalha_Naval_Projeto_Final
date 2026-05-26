
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

        try (Connection c = conectar();
             Statement s = c.createStatement()) {

            s.execute(
                    "CREATE TABLE IF NOT EXISTS partidas (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "vencedor TEXT)"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
