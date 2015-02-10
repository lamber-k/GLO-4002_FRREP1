package org.Marv1n.code;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Rafaël on 09/02/2015.
 */
public abstract class StrategyAssignationSequential implements StrategyAssignation {
    @Override
    public void assignRooms(List<Request> requests, List<Room> rooms) {
        for (Iterator<Request> iterator = requests.iterator(); iterator.hasNext(); ) {
            Request evaluatedRequest = iterator.next();
            AssignationResult result = this.evaluateOneRequest(rooms, evaluatedRequest);
            if (result.matchFound()) {
                this.treatAssignationResult(result, evaluatedRequest);
                iterator.remove();
            }
        }
    }

    protected abstract AssignationResult evaluateOneRequest(List<Room> rooms, Request request);

    protected abstract void treatAssignationResult(AssignationResult result, Request request);
}
