package ca.ulaval.glo4002.Marv1n.web.rest;

public class RestServerLanchFailException extends RuntimeException {
    public RestServerLanchFailException(Exception e) {
        super(e);
    }
}
