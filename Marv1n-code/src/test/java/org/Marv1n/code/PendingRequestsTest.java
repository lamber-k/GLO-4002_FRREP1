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
    private PendingRequests pendingRequests2;
    @Mock
    private Request request;

    @Before
    public void initializeNewPendingRequests() {
        pendingRequests2 = new PendingRequests(DEFAULT_MAXIMUM_PENDING_REQUESTS);
    }

    @Test
    public void givenEmptyPendingRequest_whenAddOneRequest_thenNotEmpty() {
        pendingRequests2.addRequest(request);
        assertTrue(pendingRequests2.hasPendingRequest());
    }

    @Test
    public void givenPendingRequest_whenSetMaximumPendingAtConstructor_thenReflectValue() {
        assertEquals(DEFAULT_MAXIMUM_PENDING_REQUESTS, pendingRequests2.getMaximumPendingRequests());
    }

    @Test
    public void givenAPendingRequest_whenSetMaximumPendingRequest_thenReflectsValueChange() {
        pendingRequests2.setMaximumPendingRequests(A_MAXIMUM_PENDING_REQUESTS);
        assertEquals(A_MAXIMUM_PENDING_REQUESTS, pendingRequests2.getMaximumPendingRequests());
    }

    @Test
    public void givenPendingRequestWithObserver_whenPendingRequestFull_thenShouldNotifyRegisteredObserver() {
        IObserverMaximumPendingRequestReached observer = mock(IObserverMaximumPendingRequestReached.class);
        pendingRequests2.addObserverMaximumPendingRequestsReached(observer);
        pendingRequests2.setMaximumPendingRequests(MAXIMUM_ONE_PENDING_REQUEST);

        pendingRequests2.addRequest(request);

        verify(observer).onMaximumPendingRequestReached();
    }

    @Test
    public void givenPendingRequestsWithOneRequest_whenCancelRequest_thenPendingRequestsShouldHaveNoRequest() {
        pendingRequests2.addRequest(request);

        pendingRequests2.cancelRequest(request);

        assertFalse(pendingRequests2.hasPendingRequest());
    }

    @Test
    public void givenPendingRequestsWithOneRequest_whenCancelRequestAnOtherRequest_thenPendingRequestsShouldHaveNoRequest() {
        Request anOtherRequest = mock(Request.class);
        pendingRequests2.addRequest(anOtherRequest);

        pendingRequests2.cancelRequest(request);

        assertTrue(pendingRequests2.hasPendingRequest());
    }

}
