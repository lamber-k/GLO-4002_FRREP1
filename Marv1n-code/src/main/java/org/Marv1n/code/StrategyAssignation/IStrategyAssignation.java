package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;

import java.util.List;

public interface IStrategyAssignation {

    public void assignReservables(List<Request> requests, List<IReservable> IReservables);
}
