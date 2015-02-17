package org.Marv1n.code.Repository;

import org.Marv1n.code.Reservable.Reservable;

import java.util.List;
import java.util.stream.Collectors;

public class ReservableRepository extends Repository<Reservable> implements IReservableRepository {
    public List<Reservable> findAll() {
        return this.query().collect(Collectors.toList());
    }
}
