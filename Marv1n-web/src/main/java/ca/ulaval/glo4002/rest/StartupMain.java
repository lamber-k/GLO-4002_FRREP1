package ca.ulaval.glo4002.rest;

public class StartupMain {

    private static final int DEFAULT_PORT = 8080;

    private StartupMain() {

    }

    public static void main(String[] args) throws Exception {
        new StartupRest(DEFAULT_PORT).start();
    }
}
