package org.Marv1n.code.Repository.Reservable;

import org.Marv1n.code.Repository.RepositoryInMemory;
import org.Marv1n.code.Reservable.IReservable;

import java.util.List;
import java.util.stream.Collectors;

public class ReservableRepositoryInMemory extends RepositoryInMemory<IReservable> implements ReservableRepository {

    public List<IReservable> findAll() {
        return query().collect(Collectors.toList());
    }
}
