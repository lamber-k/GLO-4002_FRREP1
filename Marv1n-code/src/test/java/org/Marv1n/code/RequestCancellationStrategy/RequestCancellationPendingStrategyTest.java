package org.Marv1n.code.RequestCancellationStrategy;

import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RequestCancellationPendingStrategyTest {

    private RequestCancellationPendingStrategy requestCancellationPendingStrategy;
    @Mock
    RequestRepository requestRepositoryMock;
    @Mock
    PendingRequests pendingRequestsMock;
    @Mock
    Request requestMock;

    @Before
    public void initializeRequestCancellationPendingStrategy() {
        requestCancellationPendingStrategy = new RequestCancellationPendingStrategy(requestRepositoryMock, pendingRequestsMock);
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