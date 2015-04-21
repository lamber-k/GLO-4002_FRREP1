package ca.ulaval.glo4002.core.request.sorting;

import ca.ulaval.glo4002.core.request.Request;

import java.util.List;

public class NoSortingSortingRequestStrategy implements SortingRequestStrategy {
    @Override
    public List<Request> sortList(List<Request> input) {
        return input;
    }
}