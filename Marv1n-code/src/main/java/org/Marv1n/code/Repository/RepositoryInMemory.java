package org.Marv1n.code.Repository;

import org.Marv1n.code.ObjectNotFoundException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

public abstract class RepositoryInMemory<Type> implements Repository<Type> {

    private Collection<Type> objectContainer = new LinkedList<>();

    protected Stream<Type> query() {
        return objectContainer.stream();
    }

    @Override
    public void create(Type object) {
        objectContainer.add(object);
    }

    @Override
    public void remove(Type object) {
        if (!objectContainer.remove(object)) {
            throw new ObjectNotFoundException();
        }
    }
}
