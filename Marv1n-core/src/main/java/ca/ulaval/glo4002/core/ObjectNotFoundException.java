package ca.ulaval.glo4002.core;

public class ObjectNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException() {
        super("Object not found");
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(Exception exception) {
        super(exception);
    }

    public ObjectNotFoundException(String what, Exception exception) {
        super(what, exception);
    }
}
