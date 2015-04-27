package ca.ulaval.glo4002.core.request.sorting;

import ca.ulaval.glo4002.core.request.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SequentialSortingRequestStrategyTest {

    private static final int HIGH_PRIORITY = 1;
    private static final int MEDIUM_PRIORITY = 3;
    private SequentialSortingRequestStrategy requestSorter;
    private List<Request> requestList;
    @Mock
    private Request requestWithHighPriorityMock;
    @Mock
    private Request requestWithMediumPriorityMock;

    @Before
    public void initializeSequentialSortingRequestStrategy() {
        when(requestWithHighPriorityMock.getPriority()).thenReturn(HIGH_PRIORITY);
        when(requestWithMediumPriorityMock.getPriority()).thenReturn(MEDIUM_PRIORITY);
        requestSorter = new SequentialSortingRequestStrategy();
        requestList = new ArrayList<>();
    }

    @Test
    public void givenEmptyListOfRequest_WhenNoSortingOnList_ThenReturnEmptyList() {
        requestSorter.sortList(requestList);
        assertTrue(requestList.isEmpty());
    }

    @Test
    public void giveListOfOneRequest_WhenNoSortingOnList_ThenReturnInSameOrderAsOriginalList() {
        requestList.add(requestWithMediumPriorityMock);

        requestSorter.sortList(requestList);

        assertEquals(requestWithMediumPriorityMock, requestList.get(0));
    }

    @Test
    public void giveListOfTwoRequest_WhenNoSortingOnList_ThenReturnInSameOrderAsOriginalList() {
        requestList.add(requestWithMediumPriorityMock);
        requestList.add(requestWithHighPriorityMock);

        requestSorter.sortList(requestList);


        assertEquals(requestWithMediumPriorityMock, requestList.get(0));
        assertEquals(requestWithHighPriorityMock, requestList.get(1));
    }
}