
package br.com.batalhanaval;

public class Main {

    public static void main(String[] args) {
        Database.migrate();
        new TerminalApp().run();
    }
}
