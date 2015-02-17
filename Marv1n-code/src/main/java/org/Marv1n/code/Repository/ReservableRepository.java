package org.Marv1n.code.Repository;

import org.Marv1n.code.Reservable.IReservable;

import java.util.List;
import java.util.stream.Collectors;

public class ReservableRepository extends Repository<IReservable> implements IReservableRepository {
    public List<IReservable> findAll() {
        return this.query().collect(Collectors.toList());
    }
}
