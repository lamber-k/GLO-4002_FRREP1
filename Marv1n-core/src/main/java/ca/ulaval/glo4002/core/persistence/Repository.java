package ca.ulaval.glo4002.core.persistence;

import ca.ulaval.glo4002.core.ObjectNotFoundException;

public interface Repository<T> {

    void persist(T object);

    void remove(T object) throws ObjectNotFoundException;
}
