package infrastructure.persistence;

import core.persistence.Repository;

import javax.persistence.EntityManager;

public class RepositoryHibernate<Type> implements Repository<Type> {
    protected EntityManager entityManager;

    public RepositoryHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persist(Type object) {
        this.entityManager.persist(object);
    }

    @Override
    public void remove(Type object) {
        this.entityManager.remove(object);
    }
}
