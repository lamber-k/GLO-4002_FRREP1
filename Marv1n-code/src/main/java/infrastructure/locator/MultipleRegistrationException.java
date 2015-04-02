package infrastructure.locator;

public class MultipleRegistrationException extends RuntimeException {
    public <T> MultipleRegistrationException(Class<T> service) {
        super("A implementation for the service '" + service.getCanonicalName() + "' is already present.");
    }
}
