package ca.ulaval.glo4002.applicationServices.locator;

public class LocatorService {

    private static LocatorService instance = null;
    private LocatorContainer container;

    public LocatorService() {
        container = new LocatorContainer();
    }

    public static LocatorService getInstance() {
        if (instance == null) {
            instance = new LocatorService();
        }
        return instance;
    }

    public <T> void register(Class<T> service, T instance) {
        container.register(service, instance);
    }

    public <T> T resolve(Class<T> service) {
        return container.resolve(service);
    }

    public void unregisterAll() {
        container.clear();
    }

    public void registerModule(LocatorModule module) {
        LocatorContainer loadedContainer = new LocatorContainer();
        module.load(loadedContainer);
        container.merge(loadedContainer);
    }
}
