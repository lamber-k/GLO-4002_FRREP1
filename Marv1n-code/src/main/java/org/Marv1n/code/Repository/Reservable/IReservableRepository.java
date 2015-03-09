package org.Marv1n.code.Repository.Reservable;

import org.Marv1n.code.Repository.IRepository;
import org.Marv1n.code.Reservable.IReservable;

import java.util.List;

public interface IReservableRepository extends IRepository<IReservable> {

    List<IReservable> findAll();
}
