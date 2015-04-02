package ca.ulaval.glo4002.core;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException() {
        super("Object not found");
    }
}
