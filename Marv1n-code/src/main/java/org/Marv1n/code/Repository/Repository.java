package org.Marv1n.code.Repository;

public interface Repository<Type> {

    public void create(Type object);

    public void remove(Type object);
}
