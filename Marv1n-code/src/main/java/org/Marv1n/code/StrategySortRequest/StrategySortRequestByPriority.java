package org.Marv1n.code.StrategySortRequest;

import org.Marv1n.code.Request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StrategySortRequestByPriority implements IStrategySortRequest {

    private static final Comparator<Request> comparator = (lhs, rhs) -> lhs.getPriority() - rhs.getPriority();

    @Override
    public ArrayList<Request> sortList(List<Request> requests) {
        ArrayList<Request> unsortedRequests = new ArrayList<>(requests);

        unsortedRequests.sort(comparator);
        return (unsortedRequests);
    }
}
