package ca.ulaval.glo4002.rest;

public class RestServerLaunchFailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RestServerLaunchFailException(Exception e) {
        super(e);
    }
}
