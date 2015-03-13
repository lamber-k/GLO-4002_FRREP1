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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StrategyRequestCancellationAcceptedTest {

    private StrategyRequestCancellationAccepted strategyRequestCancellationAccepted;
    @Mock
    private Request request;
    @Mock
    private IRequestRepository requestRepository;
    @Mock
    private IReservationRepository reservationRepository;
    @Mock
    private Reservation reservation;


    @Before
    public void init() {
        strategyRequestCancellationAccepted = new StrategyRequestCancellationAccepted(requestRepository, reservationRepository);
        when(reservationRepository.findReservationByRequest(request)).thenReturn(reservation);
    }

    @Test
    public void givenStrategyRequestCancellationAccepted_whenCallCancelRequest_thenRequestStatusShouldBeCanceled() {
        strategyRequestCancellationAccepted.cancelRequest(request);

        verify(request, times(1)).setRequestStatus(RequestStatus.CANCELED);
    }

    @Test
    public void givenStrategyRequestCancellationAccepted_whenCallCancelRequest_thenRequestShouldBeUpdateOnRequestRepository() {
        strategyRequestCancellationAccepted.cancelRequest(request);

        verify(requestRepository, times(1)).remove(request);
        verify(requestRepository, times(1)).create(request);
    }

    @Test
    public void givenStrategyRequestCancellationAccepted_whenCallCancelRequest_thenAssociatedReservationShouldBeRemoved() {
        strategyRequestCancellationAccepted.cancelRequest(request);

        verify(reservationRepository, times(1)).remove(reservation);
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenStrategyRequestCancellationAccepted_whenCallCancelRequestAndNoReservationAssociate_thenThrowNotFoundException() {
        when(reservationRepository.findReservationByRequest(request)).thenThrow(ReservationNotFoundException.class);
        strategyRequestCancellationAccepted.cancelRequest(request);
    }

}