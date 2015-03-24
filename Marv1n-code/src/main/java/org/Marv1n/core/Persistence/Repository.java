package org.Marv1n.core.Persistence;

public interface Repository<Type> {

    void create(Type object);

    void remove(Type object);
}
