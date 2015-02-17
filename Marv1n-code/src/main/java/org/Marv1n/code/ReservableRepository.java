package org.Marv1n.code;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nate on 15-02-16.
 */
public class ReservableRepository extends Repository<Reservable> implements IReservableRepository {
    @Override
    public List<Reservable> findAll() {
        return this.query().collect(Collectors.toList());
    }
}
