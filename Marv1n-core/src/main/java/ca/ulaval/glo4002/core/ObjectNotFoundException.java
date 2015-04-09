package ca.ulaval.glo4002.core;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException() {
        super("Object not found");
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
