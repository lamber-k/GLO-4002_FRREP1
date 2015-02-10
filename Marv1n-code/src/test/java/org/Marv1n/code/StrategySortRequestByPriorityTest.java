package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StrategySortRequestByPriorityTest {

    private static final int ONE_TIME = 1;
    private static final int HIGH_PRIORITY = 1;
    private static final int MEDIUM_PRIORITY = 3;
    private static final int LOW_PRIORITY = 5;
    @Mock
    private List<Request> mockListRequest;
    @Mock
    private Request REQUEST_WITH_HIGH_PRIORITY;
    @Mock
    private Request REQUEST_WITH_MEDIUM_PRIORITY;
    @Mock
    private Request REQUEST_WITH_MEDIUM_PRIORITY_2;
    @Mock
    private Request REQUEST_WITH_LOW_PRIORITY;
    private StrategySortRequestByPriority requestSorter;
    private List<Request> listRequest;

    @Before
    public void init() {
        this.requestSorter = new StrategySortRequestByPriority();
        this.listRequest = new ArrayList<>();
        when(REQUEST_WITH_HIGH_PRIORITY.getPriority()).thenReturn(HIGH_PRIORITY);
        when(REQUEST_WITH_MEDIUM_PRIORITY.getPriority()).thenReturn(MEDIUM_PRIORITY);
        when(REQUEST_WITH_MEDIUM_PRIORITY_2.getPriority()).thenReturn(MEDIUM_PRIORITY);
        when(REQUEST_WITH_LOW_PRIORITY.getPriority()).thenReturn(LOW_PRIORITY);
        this.listRequest.add(REQUEST_WITH_LOW_PRIORITY);
        this.listRequest.add(REQUEST_WITH_MEDIUM_PRIORITY);
        this.listRequest.add(REQUEST_WITH_HIGH_PRIORITY);
    }

    @Test
    public void whenStrategySort_SortIsCalledOnList_ThenCallToListSortIsDone() {
        this.requestSorter.sortList(this.mockListRequest);
        verify(this.mockListRequest, times(ONE_TIME)).sort(any());
    }

    @Test
    public void whenStrategySort_SortIsCalledOnList_ContainingMoreThanOneRequestThenListIsSorted() {
        this.requestSorter.sortList(this.listRequest);

        assertEquals(REQUEST_WITH_HIGH_PRIORITY, this.listRequest.get(0));
        assertEquals(REQUEST_WITH_MEDIUM_PRIORITY, this.listRequest.get(1));
        assertEquals(REQUEST_WITH_LOW_PRIORITY, this.listRequest.get(2));
    }

    @Test
    public void whenStrategySort_SortIsCalledOnListContainingElementOfSamePriority_ThenListIsSortedWithFirstInFistOutOrderForSamePriorityRequest() {
        this.listRequest.add(REQUEST_WITH_MEDIUM_PRIORITY_2);

        this.requestSorter.sortList(this.listRequest);

        assertEquals(REQUEST_WITH_HIGH_PRIORITY, this.listRequest.get(0));
        assertEquals(REQUEST_WITH_MEDIUM_PRIORITY, this.listRequest.get(1));
        assertEquals(REQUEST_WITH_MEDIUM_PRIORITY_2, this.listRequest.get(2));
        assertEquals(REQUEST_WITH_LOW_PRIORITY, this.listRequest.get(3));
    }

}