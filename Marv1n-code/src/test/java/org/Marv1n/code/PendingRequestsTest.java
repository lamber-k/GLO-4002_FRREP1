package org.Marv1n.code;

import org.Marv1n.code.Repository.Request.RequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PendingRequestsTest {

    private static final int DEFAULT_MAXIMUM_PENDING_REQUESTS = 2;
    private static final int A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final int MAXIMUM_ONE_PENDING_REQUEST = 1;
    private PendingRequests pendingRequests;
    @Mock
    private Request requestMock;
    @Mock
    private RequestRepository requestRepositoryMock;

    @Before
    public void initializePendingRequests() {
        pendingRequests = new PendingRequests(DEFAULT_MAXIMUM_PENDING_REQUESTS, requestRepositoryMock);
    }

    @Test
    public void givenEmptyPendingRequest_WhenAddOneRequest_ThenItIsAddedToTheRepository() {
        pendingRequests.addRequest(requestMock);
        verify(requestRepositoryMock).create(requestMock);
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
        MaximumPendingRequestReachedObserver observer = mock(MaximumPendingRequestReachedObserver.class);
        pendingRequests.addObserverMaximumPendingRequestsReached(observer);
        pendingRequests.setMaximumPendingRequests(MAXIMUM_ONE_PENDING_REQUEST);
        List requestlist = new ArrayList<>();
        requestlist.add(requestMock);
        when(requestRepositoryMock.findAllPendingRequest()).thenReturn(requestlist);

        pendingRequests.addRequest(requestMock);

        verify(observer).onMaximumPendingRequestReached();
    }

    @Test
    public void givenPendingRequestWithObserver_WhenPendingRequestIsNotFull_ThenDosenNotNotifyRegisteredObserver() {
        MaximumPendingRequestReachedObserver observer = mock(MaximumPendingRequestReachedObserver.class);
        pendingRequests.addObserverMaximumPendingRequestsReached(observer);
        List requestlist = new ArrayList<>();
        requestlist.add(requestMock);
        when(requestRepositoryMock.findAllPendingRequest()).thenReturn(requestlist);

        pendingRequests.addRequest(requestMock);

        verify(observer,never()).onMaximumPendingRequestReached();
    }


}
