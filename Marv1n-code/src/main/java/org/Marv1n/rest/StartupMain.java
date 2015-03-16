package org.Marv1n.rest;

public class StartupMain {
    private static final int DEFAULT_PORT = 8080;

    public static void main(String []args) throws Exception {
        new StartupRest(DEFAULT_PORT).start();
    }
}
