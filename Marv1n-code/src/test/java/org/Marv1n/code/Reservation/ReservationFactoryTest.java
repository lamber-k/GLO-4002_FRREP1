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
        this.reservationFactory = new ReservationFactory();
    }

    @Test
    public void EmptyEvaluation_Reservation_ReturnsEmptyOptional() throws Exception {
        when(this.mockEvaluationResult.matchFound()).thenReturn(false);

        Optional<Reservation> reservation = this.reservationFactory.reserve(this.mockRequest, this.mockEvaluationResult);

        assertFalse(reservation.isPresent());
    }

    @Test
    public void FullEvaluation_Reservation_ReturnsFullOptional() {
        when(this.mockEvaluationResult.matchFound()).thenReturn(true);
        when(this.mockEvaluationResult.getBestReservableMatch()).thenReturn(this.mockReservable);
        when(this.mockRequest.getNumberOfSeatsNeeded()).thenReturn(REQUEST_NUMBER_OF_SEATS);
        when(this.mockReservable.hasEnoughCapacity(REQUEST_NUMBER_OF_SEATS)).thenReturn(true);

        Optional<Reservation> reservation = this.reservationFactory.reserve(this.mockRequest, this.mockEvaluationResult);

        assertEquals(this.mockReservable, reservation.get().getReserved());
        assertEquals(this.mockRequest, reservation.get().getRequest());
    }

    @Test
    public void InsufficientCapacityEvaluation_Reservation_ReturnsEmptyOptional() {
        when(this.mockEvaluationResult.matchFound()).thenReturn(true);
        when(this.mockEvaluationResult.getBestReservableMatch()).thenReturn(this.mockReservable);
        when(this.mockRequest.getNumberOfSeatsNeeded()).thenReturn(REQUEST_NUMBER_OF_SEATS);
        when(this.mockReservable.hasEnoughCapacity(REQUEST_NUMBER_OF_SEATS)).thenReturn(false);

        Optional<Reservation> reservation = this.reservationFactory.reserve(this.mockRequest, this.mockEvaluationResult);

        assertFalse(reservation.isPresent());
    }
}
