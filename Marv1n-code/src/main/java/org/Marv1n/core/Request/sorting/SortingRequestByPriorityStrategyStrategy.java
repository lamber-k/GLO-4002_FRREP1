package org.Marv1n.core.request.sorting;

import org.Marv1n.core.request.Request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingRequestByPriorityStrategyStrategy implements SortingRequestStrategy {

    private static final Comparator<Request> comparator = (lhs, rhs) -> lhs.getPriority() - rhs.getPriority();

    @Override
    public List<Request> sortList(List<Request> requests) {
        List<Request> unsortedRequests = new ArrayList<>(requests);
        unsortedRequests.sort(comparator);
        return unsortedRequests;
    }
}
