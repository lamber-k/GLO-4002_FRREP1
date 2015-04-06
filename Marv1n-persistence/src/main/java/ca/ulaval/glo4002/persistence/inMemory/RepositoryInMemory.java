package ca.ulaval.glo4002.persistence.inMemory;

import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.persistence.Repository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

public abstract class RepositoryInMemory<T> implements Repository<T> {

    private Collection<T> objectContainer = new LinkedList<>();

    protected Stream<T> query() {
        return objectContainer.stream();
    }

    @Override
    public void persist(T object) throws InvalidFormatException {
        objectContainer.add(object);
    }

    @Override
    public void remove(T object) {
        if (!objectContainer.remove(object)) {
            throw new ObjectNotFoundException();
        }
    }
}
