package org.Marv1n.core.request.sorting;

import org.Marv1n.core.request.Request;

import java.util.List;

@FunctionalInterface
public interface SortingRequestStrategy {

    List<Request> sortList(List<Request> input);
}
