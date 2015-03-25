package org.Marv1n.core.persistence;

public interface Repository<Type> {

    void create(Type object);

    void remove(Type object);
}
