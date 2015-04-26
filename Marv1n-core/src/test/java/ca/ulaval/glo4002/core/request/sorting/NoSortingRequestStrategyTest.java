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

@RunWith(MockitoJUnitRunner.class)
public class NoSortingRequestStrategyTest {

    private NoSortingRequestStrategy requestSorter;
    private List<Request> requestList;
    @Mock
    private Request firstRequestMock;
    @Mock
    private Request secondRequestMock;

    @Before
    public void initializeNoSortingRequestStrategy() {
        requestSorter = new NoSortingRequestStrategy();
        requestList = new ArrayList<>();
    }

    @Test
    public void givenEmptyListOfRequest_WhenNoSortingOnList_ThenReturnEmptyList() {
        requestSorter.sortList(requestList);
        assertTrue(requestList.isEmpty());
    }

    @Test
    public void giveListOfOneRequest_WhenNoSortingOnList_ThenReturnInSameOrderAsOriginalList() {
        requestList.add(firstRequestMock);

        requestSorter.sortList(requestList);

        assertEquals(firstRequestMock,requestList.get(0));
    }

    @Test
    public void giveListOfTwoRequest_WhenNoSortingOnList_ThenReturnInSameOrderAsOriginalList() {
        requestList.add(firstRequestMock);
        requestList.add(secondRequestMock);

        requestSorter.sortList(requestList);

        assertEquals(firstRequestMock,requestList.get(0));
        assertEquals(secondRequestMock,requestList.get(1));
    }
}