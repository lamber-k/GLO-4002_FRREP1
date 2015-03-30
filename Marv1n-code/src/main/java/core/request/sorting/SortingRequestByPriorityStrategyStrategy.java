package core.request.sorting;

import core.request.Request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingRequestByPriorityStrategyStrategy implements SortingRequestStrategy {

    private static final Comparator<Request> REQUEST_COMPARATOR = (lhs, rhs) -> lhs.getPriority() - rhs.getPriority();

    @Override
    public List<Request> sortList(List<Request> requests) {
        List<Request> unsortedRequests = new ArrayList<>(requests);
        unsortedRequests.sort(REQUEST_COMPARATOR);
        return unsortedRequests;
    }
}
