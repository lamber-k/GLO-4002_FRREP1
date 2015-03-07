package org.Marv1n.code;

import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.Reservation;
import org.Marv1n.code.Reservation.ReservationFactory;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.*;

public class ReservationFactoryTest {

    private static final int REQUEST_NUMBER_OF_SEATS = 20;
    @Mock
    private Request mockRequest;

    private ReservableEvaluationResult mockEvaluationResult;

    private ReservationFactory reservationFactory;

    @Before
    public void initializeNewReservationFactory() throws Exception {
        mockRequest = mock(Request.class);
        mockEvaluationResult = mock(ReservableEvaluationResult.class);
        reservationFactory = new ReservationFactory();
    }

    @Test
    public void EmptyEvaluation_Reservation_ReturnsEmptyOptional() throws Exception {
        when(mockEvaluationResult.matchFound()).thenReturn(false);

        Optional<Reservation> reservation = reservationFactory.reserve(mockRequest, mockEvaluationResult);

        assertFalse(reservation.isPresent());
    }

    @Test
    public void FullEvaluation_Reservation_ReturnsFullOptional() throws Exception, ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        IReservable mockReservable = mock(IReservable.class);
        when(this.mockEvaluationResult.matchFound()).thenReturn(true);
        when(this.mockEvaluationResult.getBestReservableMatch()).thenReturn(mockReservable);
        when(mockRequest.getNumberOfSeatsNeeded()).thenReturn(REQUEST_NUMBER_OF_SEATS);
        when(mockReservable.hasEnoughCapacity(REQUEST_NUMBER_OF_SEATS)).thenReturn(true);

        Optional<Reservation> reservation = reservationFactory.reserve(mockRequest, this.mockEvaluationResult);

        assertEquals(mockReservable, reservation.get().getReserved());
        assertEquals(mockRequest, reservation.get().getRequest());
    }

    @Test
    public void InsufficientCapacityEvaluation_Reservation_ReturnsEmptyOptional() throws Exception, ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        IReservable mockReservable = mock(IReservable.class);
        when(mockEvaluationResult.matchFound()).thenReturn(true);
        when(mockEvaluationResult.getBestReservableMatch()).thenReturn(mockReservable);
        when(mockRequest.getNumberOfSeatsNeeded()).thenReturn(REQUEST_NUMBER_OF_SEATS);
        when(mockReservable.hasEnoughCapacity(REQUEST_NUMBER_OF_SEATS)).thenReturn(false);
        //doThrow(ExceptionReservableInsufficientCapacity.class).when(mockReservable).book(this.mockRequest);

        Optional<Reservation> reservation = reservationFactory.reserve(mockRequest, mockEvaluationResult);

        assertFalse(reservation.isPresent());
    }
}
