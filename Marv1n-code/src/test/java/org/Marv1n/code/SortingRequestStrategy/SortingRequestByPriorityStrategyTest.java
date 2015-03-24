package org.Marv1n.code.SortingRequestStrategy;

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
public class SortingRequestByPriorityStrategyTest {

    private static final int HIGH_PRIORITY = 1;
    private static final int MEDIUM_PRIORITY = 3;
    private static final int LOW_PRIORITY = 5;
    private static final int REQUEST_INDEX_FIRST = 0;
    private static final int REQUEST_INDEX_SECOND = 1;
    private static final int REQUEST_INDEX_THIRD = 2;
    private static final int REQUEST_INDEX_FOURTH = 3;
    private SortingRequestByPriorityStrategyStrategy requestSorter;
    private List<Request> requestList;
    @Mock
    private Request requestWithHighPriorityMock;
    @Mock
    private Request requestWithMediumPriorityMock;
    @Mock
    private Request requestWithMediumPriority2Mock;
    @Mock
    private Request requestWithLowPriorityMock;

    @Before
    public void initializeSortingRequestByPriorityStrategy() {
        requestSorter = new SortingRequestByPriorityStrategyStrategy();
        requestList = new ArrayList<>();
        when(requestWithHighPriorityMock.getPriority()).thenReturn(HIGH_PRIORITY);
        when(requestWithMediumPriorityMock.getPriority()).thenReturn(MEDIUM_PRIORITY);
        when(requestWithMediumPriority2Mock.getPriority()).thenReturn(MEDIUM_PRIORITY);
        when(requestWithLowPriorityMock.getPriority()).thenReturn(LOW_PRIORITY);
        requestList.add(requestWithLowPriorityMock);
        requestList.add(requestWithMediumPriorityMock);
        requestList.add(requestWithHighPriorityMock);
    }

    @Test
    public void givenSortingRequestByPriority_WhenStrategySortIsCalledOnListContainingMoreThanOneRequest_ThenListIsSorted() {
        List<Request> sortedArray = requestSorter.sortList(requestList);

        assertEquals(requestWithHighPriorityMock, sortedArray.get(REQUEST_INDEX_FIRST));
        assertEquals(requestWithMediumPriorityMock, sortedArray.get(REQUEST_INDEX_SECOND));
        assertEquals(requestWithLowPriorityMock, sortedArray.get(REQUEST_INDEX_THIRD));
    }

    @Test
    public void givenSortingRequestByPriority_WhenStrategySortIsCalledOnListContainingElementOfSamePriority_ThenListIsSortedWithFirstInFistOutOrderForSamePriorityRequest() {
        requestList.add(requestWithMediumPriority2Mock);

        List<Request> sortedArray = requestSorter.sortList(requestList);

        assertEquals(requestWithHighPriorityMock, sortedArray.get(REQUEST_INDEX_FIRST));
        assertEquals(requestWithMediumPriorityMock, sortedArray.get(REQUEST_INDEX_SECOND));
        assertEquals(requestWithMediumPriority2Mock, sortedArray.get(REQUEST_INDEX_THIRD));
        assertEquals(requestWithLowPriorityMock, sortedArray.get(REQUEST_INDEX_FOURTH));
    }
}