package infrastructure.persistence;

import org.Marv1n.core.ObjectNotFoundException;
import org.Marv1n.core.persistence.Repository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

public abstract class RepositoryInMemory<Type> implements Repository<Type> {

    private Collection<Type> objectContainer = new LinkedList<>();

    protected Stream<Type> query() {
        return objectContainer.stream();
    }

    @Override
    public void persist(Type object) {
        objectContainer.add(object);
    }

    @Override
    public void remove(Type object) {
        if (!objectContainer.remove(object)) {
            throw new ObjectNotFoundException();
        }
    }
}
