package org.Marv1n.code.StrategyRequestCancellation;

import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
import org.Marv1n.code.Reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StrategyRequestCancellationAcceptedTest {

    private StrategyRequestCancellationAccepted strategyRequestCancellationAccepted;
    @Mock
    private Request requestMock;
    @Mock
    private IRequestRepository requestRepositoryMock;
    @Mock
    private IReservationRepository reservationRepositoryMock;
    @Mock
    private Reservation reservationMock;

    @Before
    public void init() {
        strategyRequestCancellationAccepted = new StrategyRequestCancellationAccepted(requestRepositoryMock, reservationRepositoryMock);
        when(reservationRepositoryMock.findReservationByRequest(requestMock)).thenReturn(reservationMock);
    }

    @Test
    public void givenStrategyRequestCancellationAccepted_WhenCallCancelRequest_ThenRequestStatusShouldBeCanceled() {
        strategyRequestCancellationAccepted.cancelRequest(requestMock);

        verify(requestMock).setRequestStatus(RequestStatus.CANCELED);
    }

    @Test
    public void givenStrategyRequestCancellationAccepted_WhenCallCancelRequest_ThenRequestShouldBeUpdateOnRequestRepository() {
        strategyRequestCancellationAccepted.cancelRequest(requestMock);

        verify(requestRepositoryMock).remove(requestMock);
        verify(requestRepositoryMock).create(requestMock);
    }

    @Test
    public void givenStrategyRequestCancellationAccepted_WhenCallCancelRequest_ThenAssociatedReservationShouldBeRemoved() {
        strategyRequestCancellationAccepted.cancelRequest(requestMock);

        verify(reservationRepositoryMock).remove(reservationMock);
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenStrategyRequestCancellationAccepted_WhenCallCancelRequestAndNoReservationAssociate_ThenThrowNotFoundException() {
        when(reservationRepositoryMock.findReservationByRequest(requestMock)).thenThrow(ReservationNotFoundException.class);
        strategyRequestCancellationAccepted.cancelRequest(requestMock);
    }
}