package org.Marv1n.code.StrategyRequestCancellation;

import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.RequestStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class StrategyRequestCancellationFactoryTest {

    private IStrategyRequestCancellation resultStrategy;
    private StrategyRequestCancellationFactory requestCancellationFactoryStrategy;
    @Mock
    private IRequestRepository requestRepositoryMock;
    @Mock
    private IReservationRepository reservationRepositoryMock;
    @Mock
    private PendingRequests pendingRequests;

    @Before
    public void init() {
        requestCancellationFactoryStrategy = new StrategyRequestCancellationFactory(requestRepositoryMock, reservationRepositoryMock, pendingRequests);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_WhenCreateCancellationFactoryWithRequestStatusCancelled_ThenReturnStrategyRequestCancellationCancelled() {
        resultStrategy = requestCancellationFactoryStrategy.createStrategyCancellation(RequestStatus.CANCELED);

        assertTrue(resultStrategy instanceof StrategyRequestCancellationCancelled);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_WhenCreateCancellationFactoryWithRequestStatusRefused_ThenReturnStrategyRequestCancellationRefused() {
        resultStrategy = requestCancellationFactoryStrategy.createStrategyCancellation(RequestStatus.REFUSED);

        assertTrue(resultStrategy instanceof StrategyRequestCancellationRefused);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_WhenCreateCancellationFactoryWithRequestStatusPending_ThenReturnStrategyRequestCancellationPending() {
        resultStrategy = requestCancellationFactoryStrategy.createStrategyCancellation(RequestStatus.PENDING);

        assertTrue(resultStrategy instanceof StrategyRequestCancellationPending);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_WhenCreateCancellationFactoryWithRequestStatusAccepted_ThenReturnStrategyRequestCancellationAccepted() {
        resultStrategy = requestCancellationFactoryStrategy.createStrategyCancellation(RequestStatus.ACCEPTED);

        assertTrue(resultStrategy instanceof StrategyRequestCancellationAccepted);
    }
}