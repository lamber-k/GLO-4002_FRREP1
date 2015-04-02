package infrastructure.locator;

import java.util.HashMap;
import java.util.Map;

public class LocatorContainer {
    private Map<Class<?>, Object> serviceInstance;

    public LocatorContainer() {
        this.serviceInstance = new HashMap<>();
    }

    public <T> void register(Class<T> service, T instance) throws MultipleRegistrationException {
        if (serviceInstance.containsKey(service)) {
            throw new MultipleRegistrationException(service);
        }
        serviceInstance.put(service, instance);
    }

    public <T> T resolve(Class<T> service) {
        if (!serviceInstance.containsKey(service)) {
            throw new UnregisteredServiceException(service);
        }
        return (T) serviceInstance.get(service);
    }

    public void merge(LocatorContainer otherContainer) {
        this.serviceInstance.putAll(otherContainer.serviceInstance);
    }
}
