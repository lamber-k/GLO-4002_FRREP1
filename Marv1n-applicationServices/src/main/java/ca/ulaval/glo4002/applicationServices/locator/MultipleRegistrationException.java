package ca.ulaval.glo4002.applicationServices.locator;

public class MultipleRegistrationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public <T> MultipleRegistrationException(Class<T> service) {
        super("A implementation for the service '" + service.getCanonicalName() + "' is already present.");
    }
}
