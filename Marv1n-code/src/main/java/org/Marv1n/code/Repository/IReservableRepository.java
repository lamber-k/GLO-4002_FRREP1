package org.Marv1n.code.Repository;

import org.Marv1n.code.Reservable.Reservable;

import java.util.List;

public interface IReservableRepository extends IRepository<Reservable> {
    List<Reservable> findAll();
}
