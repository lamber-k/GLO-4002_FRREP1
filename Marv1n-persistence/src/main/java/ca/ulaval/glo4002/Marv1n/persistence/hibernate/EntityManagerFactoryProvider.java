package ca.ulaval.glo4002.Marv1n.persistence.hibernate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {
    private static EntityManagerFactory instance;

    private EntityManagerFactoryProvider() {
    }

    public static EntityManagerFactory getFactory() {
        if (instance == null) {
            instance = Persistence.createEntityManagerFactory("Marv1nRepository");
        }
        return instance;
    }
}
