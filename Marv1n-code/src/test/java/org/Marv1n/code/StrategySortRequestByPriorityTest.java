package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StrategySortRequestByPriorityTest {

    @Mock
    private List mockListRequest;
    @Mock
    private Request REQUEST_WITH_HIGH_PRIORITY;
    @Mock
    private Request REQUEST_WITH_MEDIUM_PRIORITY;
    @Mock
    private Request REQUEST_WITH_MEDIUM_PRIORITY_2;
    @Mock
    private Request REQUEST_WITH_LOW_PRIORITY;
    private int HIGH_PRIORITY = 1;
    private int MEDIUM_PRIORITY = 3;
    private int LOW_PRIORITY = 5;
    private StrategySortRequestByPriority requestSorter;
    private List listRequest;

    @Before
    public void init() {
        requestSorter = new StrategySortRequestByPriority();
        listRequest = new ArrayList();
        when(REQUEST_WITH_HIGH_PRIORITY.getPriority()).thenReturn(HIGH_PRIORITY);
        when(REQUEST_WITH_MEDIUM_PRIORITY.getPriority()).thenReturn(MEDIUM_PRIORITY);
        when(REQUEST_WITH_MEDIUM_PRIORITY_2.getPriority()).thenReturn(MEDIUM_PRIORITY);
        when(REQUEST_WITH_LOW_PRIORITY.getPriority()).thenReturn(LOW_PRIORITY);
        listRequest.add(REQUEST_WITH_LOW_PRIORITY);
        listRequest.add(REQUEST_WITH_MEDIUM_PRIORITY);
        listRequest.add(REQUEST_WITH_HIGH_PRIORITY);
    }

    @Test
    public void whenStrategySortSortIsCalledOnListThenCallToListSortIsDone() {
        requestSorter.sortList(mockListRequest);
        verify(mockListRequest, times(1)).sort(any());
    }

    @Test
    public void whenStrategySortSortIsCalledOnListContainingMoreThanOneRequestThenListIsSorted() {
        requestSorter.sortList(listRequest);

        assertEquals(REQUEST_WITH_HIGH_PRIORITY, listRequest.get(0));
        assertEquals(REQUEST_WITH_MEDIUM_PRIORITY, listRequest.get(1));
        assertEquals(REQUEST_WITH_LOW_PRIORITY, listRequest.get(2));
    }

    @Test
    public void whenStrategySortSortIsCalledOnListContainingElementOfSamePriorityThenListIsSortedWithFirstInFistOutOrderForSamePriorityRequest(){
        listRequest.add(REQUEST_WITH_MEDIUM_PRIORITY_2);

        requestSorter.sortList(listRequest);

        assertEquals(REQUEST_WITH_HIGH_PRIORITY, listRequest.get(0));
        assertEquals(REQUEST_WITH_MEDIUM_PRIORITY, listRequest.get(1));
        assertEquals(REQUEST_WITH_MEDIUM_PRIORITY_2, listRequest.get(2));
        assertEquals(REQUEST_WITH_LOW_PRIORITY, listRequest.get(3));
    }

}