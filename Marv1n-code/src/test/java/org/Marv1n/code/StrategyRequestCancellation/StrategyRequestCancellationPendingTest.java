package org.Marv1n.code.StrategyRequestCancellation;

import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StrategyRequestCancellationPendingTest {

    @Mock
    IRequestRepository requestRepository;
    @Mock
    Request request;
    private StrategyRequestCancellationPending strategyRequestCancellationPending;

    @Before
    public void init() {
        strategyRequestCancellationPending = new StrategyRequestCancellationPending(requestRepository);
    }


    @Test
    public void givenStrategyRequestCancellationPending_whenCancelRequestCalled_thenUpdateRequestStatus() {
        strategyRequestCancellationPending.cancelRequest(request);

        verify(request, times(1)).setRequestStatus(RequestStatus.CANCELED);
    }

    @Test
    public void givenStrategyRequestCancellationPending_whenCancelRequestCalled_thenRequestIsUpdatedInRequestRepository() {
        strategyRequestCancellationPending.cancelRequest(request);

        verify(requestRepository, times(1)).remove(request);
        verify(requestRepository, times(1)).create(request);
    }

}