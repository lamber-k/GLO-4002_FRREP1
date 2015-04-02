package rest;

public class RestServerLanchFailException extends RuntimeException {
    public RestServerLanchFailException(Exception e) {
        super(e);
    }
}
