package infrastructure.rest.configuration;

import infrastructure.hibernate.EntityManagerFactoryProvider;
import javax.persistence.EntityManagerFactory;

public class StartupApplication {

    private EntityManagerFactory entityManagerFactory;

    public void init() {
        entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        this.startOrganizer();
    }

    private void startOrganizer() {

    }
}
