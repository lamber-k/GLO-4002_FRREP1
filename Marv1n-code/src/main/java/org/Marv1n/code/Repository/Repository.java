package org.Marv1n.code.Repository;

public interface Repository<Type> {

    void create(Type object);

    void remove(Type object);
}
