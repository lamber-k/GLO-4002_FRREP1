package org.Marv1n.code;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public ObjectNotFoundException() {
        super("Object not found");
    }
}
