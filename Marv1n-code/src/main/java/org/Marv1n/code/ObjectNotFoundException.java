package org.Marv1n.code;

/**
 * Created by nate on 15-02-16.
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException() {
        super("Object not found");
    }
}
