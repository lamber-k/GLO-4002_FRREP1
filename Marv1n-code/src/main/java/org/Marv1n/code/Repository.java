package org.Marv1n.code;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public abstract class Repository<T> implements IRepository<T> {
    private List<T> objectContainer = new LinkedList<T>();

    protected Stream<T> query() {
        return this.objectContainer.stream();
    }

    public void create(T object) {
        this.objectContainer.add(object);
    }

    public void remove(T object) {
        if (!this.objectContainer.remove(object))
            throw new ObjectNotFoundException();
    }
}
