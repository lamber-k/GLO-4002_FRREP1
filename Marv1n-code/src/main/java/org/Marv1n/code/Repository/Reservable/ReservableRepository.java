package org.Marv1n.code.Repository.Reservable;

import org.Marv1n.code.Repository.Repository;
import org.Marv1n.code.Reservable.Reservable;

import java.util.List;

public interface ReservableRepository extends Repository<Reservable> {

    List<Reservable> findAll();
}
