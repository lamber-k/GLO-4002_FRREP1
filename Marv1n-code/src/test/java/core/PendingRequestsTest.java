package core;

import core.request.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PendingRequestsTest {

    private static final int DEFAULT_MAXIMUM_PENDING_REQUESTS = 2;
    private static final int A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final int A_MAXIMUM_ONE_PENDING_REQUEST = 1;
    private PendingRequests pendingRequests;
    @Mock
    private Request requestMock;
    @Mock
    private TaskSchedulerFactory shedulerFactoryMock;
    @Mock
    private Scheduler schedulerMock;

    @Before
    public void initializePendingRequests() {
        when(shedulerFactoryMock.getTaskSheduler(any(LinkedList.class))).thenReturn(schedulerMock);
        pendingRequests = new PendingRequests(DEFAULT_MAXIMUM_PENDING_REQUESTS, shedulerFactoryMock);
    }

    @Test
    public void givenEmptyPendingRequest_WhenAddOneRequest_ThenItIsAddedToThePendingList() {
        pendingRequests.addRequest(requestMock);

        fail();
    }

    @Test
    public void givenPendingRequest_WhenSetMaximumPendingAtConstructor_ThenReflectValue() {
        assertEquals(DEFAULT_MAXIMUM_PENDING_REQUESTS, pendingRequests.getMaximumPendingRequests());
    }

    @Test
    public void givenAPendingRequest_WhenSetMaximumPendingRequest_ThenReflectsValueChange() {
        pendingRequests.setMaximumPendingRequests(A_MAXIMUM_PENDING_REQUESTS);
        assertEquals(A_MAXIMUM_PENDING_REQUESTS, pendingRequests.getMaximumPendingRequests());
    }

    @Test
    public void givenPendingRequestWithObserver_WhenPendingRequestFull_ThenShouldNotifyRegisteredObserver() {
        pendingRequests.setMaximumPendingRequests(A_MAXIMUM_ONE_PENDING_REQUEST);
        pendingRequests.addRequest(requestMock);

        verify(schedulerMock).runNow();
    }

    @Test
    public void givenPendingRequestWithObserver_WhenPendingRequestIsNotFull_ThenDoesNotNotifyRegisteredObserver() {
        pendingRequests.setMaximumPendingRequests(DEFAULT_MAXIMUM_PENDING_REQUESTS);

        pendingRequests.addRequest(requestMock);

        verify(schedulerMock, never()).runNow();
    }
}
