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
    @Mock
    private Request mockRequest;
    @Mock
    private ReservableEvaluationResult mockEvaluationResult;
    @Mock
    private IReservable mockReservable;

    private ReservationFactory reservationFactory;

    @Before
    public void initializeNewReservationFactory() throws Exception {
        reservationFactory = new ReservationFactory();
    }

    @Test
    public void EmptyEvaluation_Reservation_ReturnsEmptyOptional() throws Exception {
        when(mockEvaluationResult.matchFound()).thenReturn(false);

        Optional<Reservation> reservation = reservationFactory.reserve(mockRequest, mockEvaluationResult);

        assertFalse(reservation.isPresent());
    }

    @Test
    public void FullEvaluation_Reservation_ReturnsFullOptional() {
        when(mockEvaluationResult.matchFound()).thenReturn(true);
        when(mockEvaluationResult.getBestReservableMatch()).thenReturn(mockReservable);
        when(mockRequest.getNumberOfSeatsNeeded()).thenReturn(REQUEST_NUMBER_OF_SEATS);
        when(mockReservable.hasEnoughCapacity(REQUEST_NUMBER_OF_SEATS)).thenReturn(true);

        Optional<Reservation> reservation = reservationFactory.reserve(mockRequest, mockEvaluationResult);

        assertEquals(mockReservable, reservation.get().getReserved());
        assertEquals(mockRequest, reservation.get().getRequest());
    }

    @Test
    public void InsufficientCapacityEvaluation_Reservation_ReturnsEmptyOptional() {
        when(mockEvaluationResult.matchFound()).thenReturn(true);
        when(mockEvaluationResult.getBestReservableMatch()).thenReturn(mockReservable);
        when(mockRequest.getNumberOfSeatsNeeded()).thenReturn(REQUEST_NUMBER_OF_SEATS);
        when(mockReservable.hasEnoughCapacity(REQUEST_NUMBER_OF_SEATS)).thenReturn(false);

        Optional<Reservation> reservation = reservationFactory.reserve(mockRequest, mockEvaluationResult);

        assertFalse(reservation.isPresent());
    }
}
