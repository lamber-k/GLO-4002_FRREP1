package org.Marv1n.code.Repository;

import org.Marv1n.code.ObjectNotFoundException;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public abstract class Repository<T> implements IRepository<T> {
    private List<T> objectContainer = new LinkedList<>();

    protected Stream<T> query() {
        return objectContainer.stream();
    }

    public void create(T object) {
        objectContainer.add(object);
    }

    public void remove(T object) {
        if (!objectContainer.remove(object))
            throw new ObjectNotFoundException();
    }
}
