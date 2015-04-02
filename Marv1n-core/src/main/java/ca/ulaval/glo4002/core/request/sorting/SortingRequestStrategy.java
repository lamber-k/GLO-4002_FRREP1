package ca.ulaval.glo4002.core.request.sorting;

import ca.ulaval.glo4002.core.request.Request;

import java.util.List;

@FunctionalInterface
public interface SortingRequestStrategy {

    List<Request> sortList(List<Request> input);
}
