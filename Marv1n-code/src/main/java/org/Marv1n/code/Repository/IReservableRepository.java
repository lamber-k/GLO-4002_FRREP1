package org.Marv1n.code.Repository;

import org.Marv1n.code.Reservable.IReservable;

import java.util.List;

public interface IReservableRepository extends IRepository<IReservable> {
    List<IReservable> findAll();
}
