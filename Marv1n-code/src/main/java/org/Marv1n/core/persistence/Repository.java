package org.Marv1n.core.persistence;

public interface Repository<Type> {

    void persist(Type object);

    void remove(Type object);
}
