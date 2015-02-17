package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Request;

import java.util.List;

public interface IStrategyAssignation {

    public void assignReservables(List<Request> requests, IReservableRepository reservables);
}
