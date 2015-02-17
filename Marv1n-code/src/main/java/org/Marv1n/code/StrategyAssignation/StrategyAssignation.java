package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.Reservable;

import java.util.List;

public interface StrategyAssignation {

    public void assignReservables(List<Request> requests, List<Reservable> reservables);
}
