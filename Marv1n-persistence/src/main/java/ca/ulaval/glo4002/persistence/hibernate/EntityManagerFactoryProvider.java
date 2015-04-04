package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.person.Person;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

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
