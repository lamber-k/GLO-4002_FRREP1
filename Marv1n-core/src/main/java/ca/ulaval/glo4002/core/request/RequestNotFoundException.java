package ca.ulaval.glo4002.core.request;

public class RequestNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public RequestNotFoundException() {
    }

    public RequestNotFoundException(Exception exception) {
        super(exception);
    }
}
