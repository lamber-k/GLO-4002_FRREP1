package ca.ulaval.glo4002.locator;

public class UnregisteredServiceException extends RuntimeException {
    public <T> UnregisteredServiceException(Class<T> service) {
        super("Cannot find service name '" + service.getCanonicalName() + "'. Did you register it?");
    }
}
