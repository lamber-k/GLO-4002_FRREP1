package org.Marv1n.code.Repository;

public interface IRepository<Type> {

    public void create(Type object);

    public void remove(Type object);
}
