package org.Marv1n.code;

public interface IRepository<T> {
    public void create(T object);


    public void remove(T object);
}
