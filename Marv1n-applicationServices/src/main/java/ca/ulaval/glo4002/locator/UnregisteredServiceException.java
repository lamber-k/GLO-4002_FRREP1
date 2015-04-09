package ca.ulaval.glo4002.locator;

public class UnregisteredServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public <T> UnregisteredServiceException(Class<T> service) {
        super("Cannot find service name '" + service.getCanonicalName() + "'. Did you register it?");
    }
}
