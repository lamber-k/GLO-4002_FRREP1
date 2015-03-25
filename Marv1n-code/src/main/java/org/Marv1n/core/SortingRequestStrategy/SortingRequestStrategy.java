package org.Marv1n.core.SortingRequestStrategy;

import org.Marv1n.core.Request.Request;

import java.util.List;

@FunctionalInterface
public interface SortingRequestStrategy {

    List<Request> sortList(List<Request> input);
}
