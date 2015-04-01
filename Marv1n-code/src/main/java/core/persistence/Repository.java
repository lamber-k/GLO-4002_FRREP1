package core.persistence;

public interface Repository<T> {

    void persist(T object);

    void remove(T object);
}
