package org.Marv1n.code;

import java.util.Comparator;
import java.util.List;

public class StrategySortRequestByPriority implements StrategySortRequest {

    @Override
    public void sortList(List<Request> input) {
        Comparator<Request> comparator = (a, b) -> a.getPriority() - b.getPriority();
        input.sort(comparator);
    }

}
