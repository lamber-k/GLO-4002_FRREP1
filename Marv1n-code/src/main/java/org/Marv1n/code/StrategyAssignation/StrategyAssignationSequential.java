package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.IAssignationResult;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;

import java.util.Iterator;
import java.util.List;

public abstract class StrategyAssignationSequential implements IStrategyAssignation {

    @Override
    public void assignReservables(List<Request> requests, List<IReservable> IReservables) {
        for (Iterator<Request> iterator = requests.iterator(); iterator.hasNext(); ) {
            Request evaluatedRequest = iterator.next();
            IAssignationResult result = this.evaluateOneRequest(IReservables, evaluatedRequest);
            if (result.matchFound()) {
                this.treatAssignationResult(result, evaluatedRequest);
                iterator.remove();
            }
        }
    }

    protected abstract IAssignationResult evaluateOneRequest(List<IReservable> IReservables, Request request);

    protected abstract void treatAssignationResult(IAssignationResult result, Request request);
}
