package org.Marv1n.code.Repository.Reservable;

import org.Marv1n.code.Repository.Repository;
import org.Marv1n.code.Reservable.IReservable;

import java.util.List;
import java.util.stream.Collectors;

public class ReservableRepository extends Repository<IReservable> implements IReservableRepository {

    public List<IReservable> findAll() {
        return query().collect(Collectors.toList());
    }
}
