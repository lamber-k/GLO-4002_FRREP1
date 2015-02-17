package org.Marv1n.code;

import java.util.List;

public interface IReservableRepository extends IRepository<Reservable> {
    List<Reservable> findAll();
}
