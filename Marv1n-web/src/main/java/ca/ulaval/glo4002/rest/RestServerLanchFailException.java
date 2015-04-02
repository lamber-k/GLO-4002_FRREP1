package ca.ulaval.glo4002.rest;

public class RestServerLanchFailException extends RuntimeException {
    public RestServerLanchFailException(Exception e) {
        super(e);
    }
}
