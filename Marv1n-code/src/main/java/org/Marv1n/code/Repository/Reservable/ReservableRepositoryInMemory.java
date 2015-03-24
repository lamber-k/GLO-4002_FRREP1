package org.Marv1n.code.Repository.Reservable;

import org.Marv1n.code.Repository.RepositoryInMemory;
import org.Marv1n.code.Reservable.Reservable;

import java.util.List;
import java.util.stream.Collectors;

public class ReservableRepositoryInMemory extends RepositoryInMemory<Reservable> implements ReservableRepository {

    @Override
    public List<Reservable> findAll() {
        return query().collect(Collectors.toList());
    }
}
