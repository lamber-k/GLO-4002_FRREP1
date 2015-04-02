package ca.ulaval.glo4002.core.persistence;

public interface Repository<T> {

    void persist(T object) throws InvalidFormatException;

    void remove(T object);
}
