package org.Marv1n.code.Repository;

import org.Marv1n.code.ObjectNotFoundException;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public abstract class Repository<Type> implements IRepository<Type> {

    private List<Type> objectContainer = new LinkedList<>();

    protected Stream<Type> query() {
        return objectContainer.stream();
    }

    public void create(Type object) {
        objectContainer.add(object);
    }

    public void remove(Type object) {
        if (!objectContainer.remove(object))
            throw new ObjectNotFoundException();
    }
}
