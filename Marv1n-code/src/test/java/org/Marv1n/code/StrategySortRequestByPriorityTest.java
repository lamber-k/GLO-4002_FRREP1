package org.Marv1n.code;

import org.Marv1n.code.StrategySortRequest.StrategySortRequestByPriority;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

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
        this.requestSorter = new StrategySortRequestByPriority();
        this.listRequest = new ArrayList<>();
        when(this.requestWithHighPriority.getPriority()).thenReturn(HIGH_PRIORITY);
        when(this.requestWithMediumPriority.getPriority()).thenReturn(MEDIUM_PRIORITY);
        when(this.requestWithMediumPriority_2.getPriority()).thenReturn(MEDIUM_PRIORITY);
        when(this.requestWithLowPriority.getPriority()).thenReturn(LOW_PRIORITY);
        this.listRequest.add(this.requestWithLowPriority);
        this.listRequest.add(this.requestWithMediumPriority);
        this.listRequest.add(this.requestWithHighPriority);
    }

    @Test
    public void whenStrategySort_SortIsCalledOnList_ThenCallToListSortIsDone() {
        // Note: Ce test ne semble plus être logique, vu que sortList ne va pas changer la liste passé en paramètre.
        fail();
        /*this.requestSorter.sortList(this.mockListRequest);
        verify(this.mockListRequest).sort(any());*/
    }

    @Test
    public void whenStrategySort_SortIsCalledOnList_ContainingMoreThanOneRequestThenListIsSorted() {
        ArrayList<Request> sortedArray = this.requestSorter.sortList(this.listRequest);

        assertEquals(this.requestWithHighPriority, sortedArray.get(REQUEST_INDEX_FIRST));
        assertEquals(this.requestWithMediumPriority, sortedArray.get(REQUEST_INDEX_SECOND));
        assertEquals(this.requestWithLowPriority, sortedArray.get(REQUEST_INDEX_THIRD));
    }

    @Test
    public void whenStrategySort_SortIsCalledOnListContainingElementOfSamePriority_ThenListIsSortedWithFirstInFistOutOrderForSamePriorityRequest() {
        this.listRequest.add(this.requestWithMediumPriority_2);

        ArrayList<Request> sortedArray = this.requestSorter.sortList(this.listRequest);

        assertEquals(this.requestWithHighPriority, sortedArray.get(REQUEST_INDEX_FIRST));
        assertEquals(this.requestWithMediumPriority, sortedArray.get(REQUEST_INDEX_SECOND));
        assertEquals(this.requestWithMediumPriority_2, sortedArray.get(REQUEST_INDEX_THIRD));
        assertEquals(this.requestWithLowPriority, sortedArray.get(REQUEST_INDEX_FOURTH));
    }

}