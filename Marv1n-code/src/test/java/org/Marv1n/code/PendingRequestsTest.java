package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PendingRequestsTest {

    private static final int DEFAULT_MAXIMUM_PENDING_REQUESTS = 2;
    private static final int A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final int MAXIMUM_ONE_PENDING_REQUEST = 1;
    private PendingRequests pendingRequests;
    @Mock
    private Organizer organizerMock;

    @Mock
    private Request request;

    @Before
    public void initializeNewPendingRequests() {
        organizerMock = mock(Organizer.class);
        pendingRequests = new PendingRequests(organizerMock, DEFAULT_MAXIMUM_PENDING_REQUESTS);
    }

    @Test
    public void newPendingRequestsHasNoPendingRequest() {
        assertFalse(pendingRequests.hasPendingRequest());
    }

    @Test
    public void whenAddingRequestPendingRequestsReportsHavingPendingRequest() {
        pendingRequests.addRequest(request);
        assertTrue(pendingRequests.hasPendingRequest());
    }

    @Test
    public void newPendingRequestsHasMaximumPendingRequests() {
        assertEquals(DEFAULT_MAXIMUM_PENDING_REQUESTS, pendingRequests.getMaximumPendingRequests());
    }

    @Test
    public void newPendingRequestsReflectsValueChange() {
        pendingRequests.setMaximumPendingRequests(A_MAXIMUM_PENDING_REQUESTS);
        assertEquals(A_MAXIMUM_PENDING_REQUESTS, pendingRequests.getMaximumPendingRequests());
    }

    @Test
    public void pendingRequestsWhenPendingRequestsReachMaximumPendingRequestsShouldRunAssignation() {
        pendingRequests.setMaximumPendingRequests(MAXIMUM_ONE_PENDING_REQUEST);
        pendingRequests.addRequest(request);

        verify(organizerMock).treatPendingRequestsNow();
    }
}
