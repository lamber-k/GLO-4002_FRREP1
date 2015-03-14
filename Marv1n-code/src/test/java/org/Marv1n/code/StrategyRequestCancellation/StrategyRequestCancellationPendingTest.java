package org.Marv1n.code.StrategyRequestCancellation;

import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StrategyRequestCancellationPendingTest {

    @Mock
    IRequestRepository requestRepositoryMock;
    @Mock
    PendingRequests pendingRequestsMock;
    @Mock
    Request requestMock;
    private StrategyRequestCancellationPending requestCancellationPendingStrategy;

    @Before
    public void init() {
        requestCancellationPendingStrategy = new StrategyRequestCancellationPending(requestRepositoryMock, pendingRequestsMock);
    }

    @Test
    public void givenStrategyRequestCancellationPending_WhenCancelRequestCalled_ThenUpdateRequestStatus() {
        requestCancellationPendingStrategy.cancelRequest(requestMock);

        verify(requestMock).setRequestStatus(RequestStatus.CANCELED);
    }

    @Test
    public void givenStrategyRequestCancellationPending_WhenCancelRequestCalled_ThenRequestIsUpdatedInRequestRepository() {
        requestCancellationPendingStrategy.cancelRequest(requestMock);

        verify(requestRepositoryMock).remove(requestMock);
        verify(requestRepositoryMock).create(requestMock);
    }

    @Test
    public void givenStrategyRequestCancellationPending_WhenCancelRequestCalled_ThenRequestIsRemovedFromPendingRequest() {
        requestCancellationPendingStrategy.cancelRequest(requestMock);

        verify(pendingRequestsMock).cancelRequest(requestMock);
    }
}