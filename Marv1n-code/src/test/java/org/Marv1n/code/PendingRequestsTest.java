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
    private PendingRequests pendingRequests2;
    @Mock
    private IRequestRepository requestRepositoryMock;
    @Mock
    private Request request;
    @Mock
    private StrategyRequestCancellationFactory strategyRequestCancellationFactoryMock;

    @Before
    public void initializeNewPendingRequests() {
        pendingRequests2 = new PendingRequests(DEFAULT_MAXIMUM_PENDING_REQUESTS, requestRepositoryMock, strategyRequestCancellationFactoryMock);
    }

    @Test
    public void whenAddingRequestPendingRequestsReportsHavingPendingRequest() {
        List<Request> reqList = new ArrayList<>();
        reqList.add(request);
        when(requestRepositoryMock.findAllPendingRequest()).thenReturn(reqList);

        pendingRequests2.addRequest(request);

        verify(requestRepositoryMock, times(1)).create(request);
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
    public void pendingRequestsWithAnObserverWhenPendingRequestsReachMaximumPendingRequestsShouldNotifyRegistredObserver() {
        IObserverMaximumPendingRequestReached observer = mock(IObserverMaximumPendingRequestReached.class);
        pendingRequests2.addObserverMaximumPendingRequestsReached(observer);
        pendingRequests2.setMaximumPendingRequests(MAXIMUM_ONE_PENDING_REQUEST);
        List<Request> reqList = new ArrayList<>();
        reqList.add(request);
        when(requestRepositoryMock.findAllPendingRequest()).thenReturn(reqList);

        pendingRequests2.addRequest(request);

        verify(observer, times(1)).onMaximumPendingRequestReached();
    }

    @Test
    public void pendingRequestsWhenCancelRequestIsCalledPendingRequestsShouldCheckIfRequestExist() {
        when(requestRepositoryMock.findByUUID(any(UUID.class))).thenReturn(Optional.empty());
        pendingRequests2.cancelRequest(UUID.randomUUID());

        verify(requestRepositoryMock, times(1)).findByUUID(any(UUID.class));
    }

    @Test
    public void pendingRequestsWhenCancelRequestWithValidRequestPendingRequestsShouldCallAskForAStrategyRequestCancellation() {
        when(requestRepositoryMock.findByUUID(any(UUID.class))).thenReturn(Optional.of(request));
        when(request.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(strategyRequestCancellationFactoryMock.createStrategyCancellation(RequestStatus.ACCEPTED)).thenReturn(mock(IStrategyRequestCancellation.class));
        pendingRequests2.cancelRequest(UUID.randomUUID());

        verify(strategyRequestCancellationFactoryMock, times(1)).createStrategyCancellation(RequestStatus.ACCEPTED);
    }

    @Test
    public void pendingRequestsWhenCancelRequestWithValidRequestPendingRequestsShouldCreatedStrategyRequestCancellation() {
        when(requestRepositoryMock.findByUUID(any(UUID.class))).thenReturn(Optional.of(request));
        when(request.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        IStrategyRequestCancellation generatedStrategyRequestCancellation = mock(IStrategyRequestCancellation.class);
        when(strategyRequestCancellationFactoryMock.createStrategyCancellation(RequestStatus.ACCEPTED)).thenReturn(generatedStrategyRequestCancellation);
        pendingRequests2.cancelRequest(UUID.randomUUID());

        verify(generatedStrategyRequestCancellation, times(1)).cancelRequest(request);
    }
}
