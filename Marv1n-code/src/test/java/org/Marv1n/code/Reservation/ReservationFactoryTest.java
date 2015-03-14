package org.Marv1n.code.Reservation;

import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationFactoryTest {

    private static final int REQUEST_NUMBER_OF_SEATS = 20;
    private ReservationFactory reservationFactory;
    @Mock
    private Request requestMock;
    @Mock
    private ReservableEvaluationResult evaluationResultMock;
    @Mock
    private IReservable reservableMock;

    @Before
    public void initializeNewReservationFactory() throws Exception {
        reservationFactory = new ReservationFactory();
    }

    @Test
    public void EmptyEvaluation_WhenReservation_ThenReturnsEmptyOptional() throws Exception {
        when(evaluationResultMock.matchFound()).thenReturn(false);

        Optional<Reservation> reservation = reservationFactory.reserve(requestMock, evaluationResultMock);

        assertFalse(reservation.isPresent());
    }

    @Test
    public void FullEvaluation_WhenReservation_ThenReturnsFullOptional() {
        when(evaluationResultMock.matchFound()).thenReturn(true);
        when(evaluationResultMock.getBestReservableMatch()).thenReturn(reservableMock);
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(REQUEST_NUMBER_OF_SEATS);
        when(reservableMock.hasEnoughCapacity(REQUEST_NUMBER_OF_SEATS)).thenReturn(true);

        Optional<Reservation> reservation = reservationFactory.reserve(requestMock, evaluationResultMock);

        assertEquals(reservableMock, reservation.get().getReserved());
        assertEquals(requestMock, reservation.get().getRequest());
    }

    @Test
    public void InsufficientCapacityEvaluation_WhenReservation_ThenReturnsEmptyOptional() {
        when(evaluationResultMock.matchFound()).thenReturn(true);
        when(evaluationResultMock.getBestReservableMatch()).thenReturn(reservableMock);
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(REQUEST_NUMBER_OF_SEATS);
        when(reservableMock.hasEnoughCapacity(REQUEST_NUMBER_OF_SEATS)).thenReturn(false);

        Optional<Reservation> reservation = reservationFactory.reserve(requestMock, evaluationResultMock);

        assertFalse(reservation.isPresent());
    }
}
