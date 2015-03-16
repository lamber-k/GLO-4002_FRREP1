package org.Marv1n.code.Repository.Reservable;

import org.Marv1n.code.Repository.Repository;
import org.Marv1n.code.Reservable.IReservable;

import java.util.List;

public interface ReservableRepository extends Repository<IReservable> {

    List<IReservable> findAll();
}
