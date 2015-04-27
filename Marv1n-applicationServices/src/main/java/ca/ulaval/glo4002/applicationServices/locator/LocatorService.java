package ca.ulaval.glo4002.applicationServices.locator;

public class LocatorService {
    private static LocatorService instance = null;
    private LocatorContainer container;

    public LocatorService() {
        this.container = new LocatorContainer();
    }

    public static LocatorService getInstance() {
        if (instance == null) {
            instance = new LocatorService();
        }
        return instance;
    }

    public <T> void register(Class<T> service, T instance) {
        this.container.register(service, instance);
    }

    public <T> T resolve(Class<T> service) {
        return this.container.resolve(service);
    }

    public void unregisterAll() {
        this.container.clear();
    }

    public void registerModule(LocatorModule module) {
        LocatorContainer loadedContainer = new LocatorContainer();
        module.load(loadedContainer);
        this.container.merge(loadedContainer);
    }
}
