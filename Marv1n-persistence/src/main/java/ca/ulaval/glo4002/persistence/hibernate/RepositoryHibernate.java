package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.persistence.Repository;

import javax.persistence.EntityManager;

public abstract class RepositoryHibernate<T> implements Repository<T> {
    protected EntityManager entityManager;

    public RepositoryHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persist(T object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(T object) {
        entityManager.getTransaction().begin();
        entityManager.remove(object);
        entityManager.getTransaction().commit();
    }
}
