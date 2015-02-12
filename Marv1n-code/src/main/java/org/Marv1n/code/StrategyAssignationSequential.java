package org.Marv1n.code;

import java.util.Iterator;
import java.util.List;

public abstract class StrategyAssignationSequential implements StrategyAssignation {

    @Override
    public void assignReservables(List<Request> requests, List<Reservable> reservables) {
        for (Iterator<Request> iterator = requests.iterator(); iterator.hasNext(); ) {
            Request evaluatedRequest = iterator.next();
            AssignationResult result = this.evaluateOneRequest(reservables, evaluatedRequest);
            if (result.matchFound()) {
                this.treatAssignationResult(result, evaluatedRequest);
                iterator.remove();
            }
        }
    }

    protected abstract AssignationResult evaluateOneRequest(List<Reservable> reservables, Request request);

    protected abstract void treatAssignationResult(AssignationResult result, Request request);
}
