package org.Marv1n.rest.Configuration;

import org.Marv1n.hibernate.EntityManagerFactoryProvider;

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
