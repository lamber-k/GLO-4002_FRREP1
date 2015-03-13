package org.Marv1n.code;

import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.StrategyRequestCancellation.IStrategyRequestCancellation;
import org.Marv1n.code.StrategyRequestCancellation.StrategyRequestCancellationFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PendingRequestsTest {

    private static final int DEFAULT_MAXIMUM_PENDING_REQUESTS = 2;
    private static final int A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final int MAXIMUM_ONE_PENDING_REQUEST = 1;
    private static final UUID A_REQUEST_UUID = UUID.randomUUID();
    private PendingRequests pendingRequests2;
    @Mock
    private Request request;
    @Mock
    private StrategyRequestCancellationFactory strategyRequestCancellationFactoryMock;

    @Before
    public void initializeNewPendingRequests() {
        pendingRequests2 = new PendingRequests(DEFAULT_MAXIMUM_PENDING_REQUESTS, strategyRequestCancellationFactoryMock);
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
    public void givenPendingRequestsWithRequest_whenCancelRequest_thenRequestShouldBeCancelled() {
        IStrategyRequestCancellation mockCancellationStrategy = mock(IStrategyRequestCancellation.class);
        pendingRequests2.addRequest(request);
        when(request.getRequestID()).thenReturn(A_REQUEST_UUID);
        when(request.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(strategyRequestCancellationFactoryMock.createStrategyCancellation(RequestStatus.ACCEPTED)).thenReturn(mockCancellationStrategy);

        pendingRequests2.cancelRequest(A_REQUEST_UUID);

        verify(mockCancellationStrategy).cancelRequest(request);
    }

    @Test
    public void givenPendingRequest_whenCancelNonExistingRequest_shouldDoNothing() {
        pendingRequests2.cancelRequest(UUID.randomUUID());

        verify(strategyRequestCancellationFactoryMock, never()).createStrategyCancellation(any());
    }

}
