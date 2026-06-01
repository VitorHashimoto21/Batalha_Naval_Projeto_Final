
package br.com.batalhanaval;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader()
                .getResourceAsStream("game.properties")) {
            if (in == null) {
                throw new RuntimeException("game.properties not found");
            }
            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static int getInt(String key, int defaultValue) {
        return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(props.getProperty(key, String.valueOf(defaultValue)));
    }

    public static int boardSize() {
        return getInt("board.size", 10);
    }

    public static String boardColumnsStart() {
        return props.getProperty("board.columns.start", "A");
    }

    public static String boardColumnsEnd() {
        return props.getProperty("board.columns.end",
                String.valueOf((char) ('A' + boardSize() - 1)));
    }

    public static String[] fleetNames() {
        return props.getProperty("fleet.names", "PortaAvioes,Encouracado,Cruzador,Submarino,Destroyer")
                .split(",");
    }

    public static int[] fleetSizes() {
        String[] data = props.getProperty("fleet.sizes", "5,4,3,3,2").split(",");
        int[] v = new int[data.length];

        for (int i = 0; i < data.length; i++) {
            v[i] = Integer.parseInt(data[i]);
        }

        return v;
    }

    public static long gameSeed() {
        String value = props.getProperty("game.seed");
        if (value == null || value.isBlank()) {
            return new java.util.Random().nextLong();
        }
        return Long.parseLong(value);
    }

    public static String gameMode() {
        return props.getProperty("game.mode", "PLAY").toUpperCase();
    }

    public static boolean uiShowOwnShips() {
        return getBoolean("ui.show_own_ships", true);
    }

    public static boolean uiShowLegend() {
        return getBoolean("ui.show_legend", true);
    }

    public static boolean dbEnabled() {
        return getBoolean("db.enabled", true);
    }

    public static String dbType() {
        return props.getProperty("db.type", "sqlite");
    }

    public static String dbFile() {
        return props.getProperty("db.sqlite.file", "batalha.db");
    }

    public static boolean dbAutoMigrate() {
        return getBoolean("db.auto_migrate", true);
    }

    public static boolean dbSaveInitialFleet() {
        return getBoolean("db.save_initial_fleet", false);
    }

    public static String cpuStrategy() {
        return props.getProperty("cpu.strategy", "RANDOM").toUpperCase();
    }

    public static boolean cpuUseParityPreference() {
        return getBoolean("cpu.use_parity_preference", false);
    }

    public static String fleetAdjacencyRule() {
        return props.getProperty("fleet.adjacency_rule", "NONE").toUpperCase();
    }

    public static boolean rulesHitGrantsExtraShot() {
        return getBoolean("rules.hit_grants_extra_shot", false);
    }

    public static int rulesMaxExtraShots() {
        return getInt("rules.max_extra_shots", 0);
    }
}
