package org.Marv1n.code;

import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PendingRequestsTest {

    private static final int DEFAULT_MAXIMUM_PENDING_REQUESTS = 2;
    private static final int A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final int MAXIMUM_ONE_PENDING_REQUEST = 1;
    private PendingRequests pendingRequests2;
    @Mock
    private Organizer organizerMock;
    @Mock
    private IRequestRepository requestRepositoryMock;
    @Mock
    private Request request;

    @Before
    public void initializeNewPendingRequests() {
        pendingRequests2 = new PendingRequests(organizerMock, DEFAULT_MAXIMUM_PENDING_REQUESTS, requestRepositoryMock);
    }

    @Test
    public void whenAddingRequestPendingRequestsReportsHavingPendingRequest() {
        List<Request> reqList = new ArrayList<>();
        reqList.add(request);
        when(requestRepositoryMock.findAllPendingRequest()).thenReturn(reqList);

        pendingRequests2.addRequest(request);

        verify(requestRepositoryMock,times(1)).create(request);
        assertTrue(pendingRequests2.hasPendingRequest());
    }

    @Test
    public void newPendingRequestsHasMaximumPendingRequests() {
        assertEquals(DEFAULT_MAXIMUM_PENDING_REQUESTS, pendingRequests2.getMaximumPendingRequests());
    }

    @Test
    public void newPendingRequestsReflectsValueChange() {
        pendingRequests2.setMaximumPendingRequests(A_MAXIMUM_PENDING_REQUESTS);
        assertEquals(A_MAXIMUM_PENDING_REQUESTS, pendingRequests2.getMaximumPendingRequests());
    }

    @Test
    public void pendingRequestsWhenPendingRequestsReachMaximumPendingRequestsShouldRunAssignation() {
        pendingRequests2.setMaximumPendingRequests(MAXIMUM_ONE_PENDING_REQUEST);
        List<Request> reqList = new ArrayList<>();
        reqList.add(request);
        when(requestRepositoryMock.findAllPendingRequest()).thenReturn(reqList);

        pendingRequests2.addRequest(request);

        verify(organizerMock, times(1)).treatPendingRequestsNow();
    }

    @Test
    public void pendingRequestsWhenCancelInvalidRequestPendingRequestsShouldDoNothing() {
        when(requestRepositoryMock.findByUUID(any(UUID.class))).thenReturn(Optional.empty());
        pendingRequests2.cancelRequest(UUID.randomUUID());

        verify(requestRepositoryMock, never()).remove(any(Request.class));
    }

    @Test
    public void pendingRequestsWhenCancelValidRequestWithNonCancellableStatusPendingRequestsShouldCheckCancellableStatusAndDoNothing() {
        when(requestRepositoryMock.findByUUID(any(UUID.class))).thenReturn(Optional.of(request));
        when(request.getRequestStatus()).thenReturn(RequestStatus.REFUSED);
        pendingRequests2.cancelRequest(UUID.randomUUID());

        verify(request, times(2)).getRequestStatus();
        verify(requestRepositoryMock, never()).remove(any(Request.class));
    }

    @Test
    public void pendingRequestsWhenCancelValidRequestWithCancellableStatusPendingRequestsShouldCancelRequest() {
        when(requestRepositoryMock.findByUUID(any(UUID.class))).thenReturn(Optional.of(request));
        when(request.getRequestStatus()).thenReturn(RequestStatus.PENDING);
        pendingRequests2.cancelRequest(UUID.randomUUID());

        verify(requestRepositoryMock, times(1)).remove(request);
        verify(requestRepositoryMock, times(1)).create(request);
    }

}
