package org.Marv1n.code;

import java.util.Comparator;
import java.util.List;

public class StrategySortRequestByPriority implements StrategySortRequest {

    @Override
    public void sortList(List<Request> requests) {
        Comparator<Request> comparator = (lhs, rhs) -> lhs.getPriority() - rhs.getPriority();
        requests.sort(comparator);
    }
}
