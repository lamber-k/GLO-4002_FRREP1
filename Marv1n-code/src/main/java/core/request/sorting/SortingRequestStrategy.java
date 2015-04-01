package core.request.sorting;

import core.request.Request;

import java.util.List;

@FunctionalInterface
public interface SortingRequestStrategy {

    List<Request> sortList(List<Request> input);
}
