package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PendingRequestsTest {

    private static final int DEFAULT_MAXIMUM_PENDING_REQUESTS = 2;
    private static final int A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final int MAXIMUM_ONE_PENDING_REQUEST = 1;
    private static final UUID A_REQUEST_UUID = UUID.randomUUID();
    private PendingRequests pendingRequests;
    @Mock
    private Request requestMock;

    @Before
    public void initializeNewPendingRequests() {
        pendingRequests = new PendingRequests(DEFAULT_MAXIMUM_PENDING_REQUESTS);
    }

    @Test
    public void givenEmptyPendingRequest_WhenAddOneRequest_ThenNotEmpty() {
        pendingRequests.addRequest(requestMock);
        assertTrue(pendingRequests.hasPendingRequest());
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
        IObserverMaximumPendingRequestReached observer = mock(IObserverMaximumPendingRequestReached.class);
        pendingRequests.addObserverMaximumPendingRequestsReached(observer);
        pendingRequests.setMaximumPendingRequests(MAXIMUM_ONE_PENDING_REQUEST);

        pendingRequests.addRequest(requestMock);

        verify(observer).onMaximumPendingRequestReached();
    }

    @Test
    public void givenPendingRequestsWithOneRequest_WhenCancelRequest_ThenPendingRequestsShouldHaveNoRequest() {
        pendingRequests.addRequest(requestMock);

        pendingRequests.cancelRequest(requestMock);

        assertFalse(pendingRequests.hasPendingRequest());
    }

    @Test
    public void givenPendingRequestsWithOneRequest_WhenCancelRequestAnOtherRequest_ThenPendingRequestsShouldHaveNoRequest() {
        Request anOtherRequest = mock(Request.class);
        pendingRequests.addRequest(anOtherRequest);

        pendingRequests.cancelRequest(requestMock);

        assertTrue(pendingRequests.hasPendingRequest());
    }
}
