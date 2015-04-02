package infrastructure.locator;

public class LocatorService {
    private static LocatorService instance = null;

    public static LocatorService getInstance() {
        if (instance == null) {
            instance = new LocatorService();
        }
        return instance;
    }

    private LocatorContainer container;

    private LocatorService() {
        this.container = new LocatorContainer();
    }

    public <T> void register(Class<T> service, T instance) throws MultipleRegistrationException {
        this.container.register(service, instance);
    }

    public <T> T resolve(Class<T> service) {
        return this.container.resolve(service);
    }

    public void RegisterModule(LocatorModule module) {
        LocatorContainer loadedContainer = new LocatorContainer();
        module.load(loadedContainer);
        this.container.merge(loadedContainer);
    }
}
