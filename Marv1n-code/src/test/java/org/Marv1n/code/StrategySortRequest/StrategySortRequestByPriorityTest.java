package org.Marv1n.code.StrategySortRequest;

import org.Marv1n.code.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StrategySortRequestByPriorityTest {

    private static final Integer HIGH_PRIORITY = 1;
    private static final Integer MEDIUM_PRIORITY = 3;
    private static final Integer LOW_PRIORITY = 5;
    private static final Integer REQUEST_INDEX_FIRST = 0;
    private static final Integer REQUEST_INDEX_SECOND = 1;
    private static final Integer REQUEST_INDEX_THIRD = 2;
    private static final Integer REQUEST_INDEX_FOURTH = 3;

    @Mock
    private List<Request> mockListRequest;
    @Mock
    private Request requestWithHighPriority;
    @Mock
    private Request requestWithMediumPriority;
    @Mock
    private Request requestWithMediumPriority_2;
    @Mock
    private Request requestWithLowPriority;
    private StrategySortRequestByPriority requestSorter;
    private List<Request> listRequest;

    @Before
    public void init() {
        requestSorter = new StrategySortRequestByPriority();
        listRequest = new ArrayList<>();
        when(requestWithHighPriority.getPriority()).thenReturn(HIGH_PRIORITY);
        when(requestWithMediumPriority.getPriority()).thenReturn(MEDIUM_PRIORITY);
        when(requestWithMediumPriority_2.getPriority()).thenReturn(MEDIUM_PRIORITY);
        when(requestWithLowPriority.getPriority()).thenReturn(LOW_PRIORITY);
        listRequest.add(requestWithLowPriority);
        listRequest.add(requestWithMediumPriority);
        listRequest.add(requestWithHighPriority);
    }

    @Test
    public void whenStrategySort_SortIsCalledOnList_ContainingMoreThanOneRequestThenListIsSorted() {
        ArrayList<Request> sortedArray = requestSorter.sortList(listRequest);

        assertEquals(requestWithHighPriority, sortedArray.get(REQUEST_INDEX_FIRST));
        assertEquals(requestWithMediumPriority, sortedArray.get(REQUEST_INDEX_SECOND));
        assertEquals(requestWithLowPriority, sortedArray.get(REQUEST_INDEX_THIRD));
    }

    @Test
    public void whenStrategySort_SortIsCalledOnListContainingElementOfSamePriority_ThenListIsSortedWithFirstInFistOutOrderForSamePriorityRequest() {
        listRequest.add(requestWithMediumPriority_2);

        ArrayList<Request> sortedArray = requestSorter.sortList(listRequest);

        assertEquals(requestWithHighPriority, sortedArray.get(REQUEST_INDEX_FIRST));
        assertEquals(requestWithMediumPriority, sortedArray.get(REQUEST_INDEX_SECOND));
        assertEquals(requestWithMediumPriority_2, sortedArray.get(REQUEST_INDEX_THIRD));
        assertEquals(requestWithLowPriority, sortedArray.get(REQUEST_INDEX_FOURTH));
    }

}