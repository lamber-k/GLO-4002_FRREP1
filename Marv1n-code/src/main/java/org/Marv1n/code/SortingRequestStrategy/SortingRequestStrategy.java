package org.Marv1n.code.SortingRequestStrategy;

import org.Marv1n.code.Request;

import java.util.ArrayList;
import java.util.List;

public interface SortingRequestStrategy {

    public ArrayList<Request> sortList(List<Request> input);
}
