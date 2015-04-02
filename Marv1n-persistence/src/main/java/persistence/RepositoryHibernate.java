package persistence;

import core.persistence.InvalidFormatException;
import core.persistence.Repository;

import javax.persistence.EntityManager;

public abstract class RepositoryHibernate<T> implements Repository<T> {
    protected EntityManager entityManager;

    public RepositoryHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persist(T object) throws InvalidFormatException {
        this.entityManager.persist(object);
    }

    @Override
    public void remove(T object) {
        this.entityManager.remove(object);
    }
}
