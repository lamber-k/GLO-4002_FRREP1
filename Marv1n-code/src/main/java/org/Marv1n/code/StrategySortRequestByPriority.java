package org.Marv1n.code;

import java.util.Comparator;
import java.util.List;

public class StrategySortRequestByPriority implements StrategySortRequest {

    @Override
    public void sortList(List input) {
        ComparatorRequest comparator = new ComparatorRequest();
        input.sort(comparator);
    }

    private static class ComparatorRequest implements Comparator<Request>{
        @Override
        public int compare(Request o1, Request o2) {
            return o1.getPriority() - o2.getPriority();
        }
    }

}
