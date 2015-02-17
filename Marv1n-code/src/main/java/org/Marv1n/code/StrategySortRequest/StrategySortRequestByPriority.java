package org.Marv1n.code.StrategySortRequest;

import org.Marv1n.code.Request;

import java.util.Comparator;
import java.util.List;

public class StrategySortRequestByPriority implements IStrategySortRequest {

    @Override
    public void sortList(List<Request> requests) {
        Comparator<Request> comparator = (lhs, rhs) -> lhs.getPriority() - rhs.getPriority();
        requests.sort(comparator);
    }
}
