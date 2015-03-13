package org.Marv1n.code.StrategyRequestCancellation;

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

    private StrategyRequestCancellationFactory strategyRequestCancellationFactory;
    @Mock
    private IRequestRepository requestRepositoryMock;
    @Mock
    private IReservationRepository reservationRepositoryMock;
    private IStrategyRequestCancellation resultStrategy;

    @Before
    public void init() {
        strategyRequestCancellationFactory = new StrategyRequestCancellationFactory(requestRepositoryMock, reservationRepositoryMock);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_whenCreateCancellationFactoryWithRequestStatusCancelled_thenReturnStrategyRequestCancellationCancelled() {
        resultStrategy = strategyRequestCancellationFactory.createStrategyCancellation(RequestStatus.CANCELED);

        assertTrue(resultStrategy instanceof StrategyRequestCancellationCancelled);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_whenCreateCancellationFactoryWithRequestStatusRefused_thenReturnStrategyRequestCancellationRefused() {
        resultStrategy = strategyRequestCancellationFactory.createStrategyCancellation(RequestStatus.REFUSED);

        assertTrue(resultStrategy instanceof StrategyRequestCancellationRefused);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_whenCreateCancellationFactoryWithRequestStatusPending_thenReturnStrategyRequestCancellationPending() {
        resultStrategy = strategyRequestCancellationFactory.createStrategyCancellation(RequestStatus.PENDING);

        assertTrue(resultStrategy instanceof StrategyRequestCancellationPending);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_whenCreateCancellationFactoryWithRequestStatusAccepted_thenReturnStrategyRequestCancellationAccepted() {
        resultStrategy = strategyRequestCancellationFactory.createStrategyCancellation(RequestStatus.ACCEPTED);

        assertTrue(resultStrategy instanceof StrategyRequestCancellationAccepted);
    }


}