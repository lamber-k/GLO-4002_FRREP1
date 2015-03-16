package org.Marv1n.code.RequestCancellationStrategy;

import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.RequestStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class RequestCancellationFactoryStrategyTest {

    private RequestCancellationStrategy resultStrategy;
    private RequestCancellationFactoryStrategy requestCancellationFactoryStrategy;
    @Mock
    private RequestRepository requestRepositoryMock;
    @Mock
    private ReservationRepository reservationRepositoryMock;
    @Mock
    private PendingRequests pendingRequestsMock;

    @Before
    public void initializeRequestCancellationFactoryStrategy() {
        requestCancellationFactoryStrategy = new RequestCancellationFactoryStrategy(requestRepositoryMock, reservationRepositoryMock, pendingRequestsMock);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_WhenCreateCancellationFactoryWithRequestStatusCancelled_ThenReturnStrategyRequestCancellationCancelled() {
        resultStrategy = requestCancellationFactoryStrategy.createStrategyCancellation(RequestStatus.CANCELED);

        assertTrue(resultStrategy instanceof RequestCancellationCancelledStrategy);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_WhenCreateCancellationFactoryWithRequestStatusRefused_ThenReturnStrategyRequestCancellationRefused() {
        resultStrategy = requestCancellationFactoryStrategy.createStrategyCancellation(RequestStatus.REFUSED);

        assertTrue(resultStrategy instanceof RequestCancellationRefusedStrategy);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_WhenCreateCancellationFactoryWithRequestStatusPending_ThenReturnStrategyRequestCancellationPending() {
        resultStrategy = requestCancellationFactoryStrategy.createStrategyCancellation(RequestStatus.PENDING);

        assertTrue(resultStrategy instanceof RequestCancellationPendingStrategy);
    }

    @Test
    public void givenStrategyRequestCancellationFactory_WhenCreateCancellationFactoryWithRequestStatusAccepted_ThenReturnStrategyRequestCancellationAccepted() {
        resultStrategy = requestCancellationFactoryStrategy.createStrategyCancellation(RequestStatus.ACCEPTED);

        assertTrue(resultStrategy instanceof RequestCancellationAcceptedStrategy);
    }
}