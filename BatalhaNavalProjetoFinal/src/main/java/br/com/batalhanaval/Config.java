
package br.com.batalhanaval;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties props = new Properties();

    static {
        try {
            InputStream in = Config.class.getClassLoader()
                    .getResourceAsStream("game.properties");
            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int boardSize() {
        return Integer.parseInt(props.getProperty("board.size"));
    }

    public static String[] fleetNames() {
        return props.getProperty("fleet.names").split(",");
    }

    public static int[] fleetSizes() {
        String[] data = props.getProperty("fleet.sizes").split(",");
        int[] v = new int[data.length];

        for (int i = 0; i < data.length; i++) {
            v[i] = Integer.parseInt(data[i]);
        }

        return v;
    }

    public static String dbFile() {
        return props.getProperty("db.sqlite.file");
    }
}
