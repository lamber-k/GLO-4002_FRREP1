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

    @Mock
    private Request mockRequest;

    private ReservableEvaluationResult mockEvaluationResult;

    private ReservationFactory reservationFactory;

    @Before
    public void initializeNewReservationFactory() throws Exception {
        this.mockEvaluationResult = mock(ReservableEvaluationResult.class);
        this.reservationFactory = new ReservationFactory();
    }

    @Test
    public void EmptyEvaluation_Reservation_ReturnsEmptyOptional() throws Exception {
        when(this.mockEvaluationResult.matchFound()).thenReturn(false);

        Optional<Reservation> reservation = this.reservationFactory.reserve(this.mockRequest, this.mockEvaluationResult);

        assertFalse(reservation.isPresent());
    }

    @Test
    public void FullEvaluation_Reservation_ReturnsFullOptional() throws Exception, ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        IReservable mockReservable = mock(IReservable.class);
        when(this.mockEvaluationResult.matchFound()).thenReturn(true);
        when(this.mockEvaluationResult.getBestReservableMatch()).thenReturn(mockReservable);

        Optional<Reservation> reservation = this.reservationFactory.reserve(this.mockRequest, this.mockEvaluationResult);

        assertEquals(mockReservable, reservation.get().getReserved());
        assertEquals(this.mockRequest, reservation.get().getRequest());
        verify(mockReservable).book(this.mockRequest);
    }

    @Test
    public void AlreadyBookedEvaluation_Reservation_ReturnsEmptyOptional() throws Exception, ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        IReservable mockReservable = mock(IReservable.class);
        when(this.mockEvaluationResult.matchFound()).thenReturn(true);
        when(this.mockEvaluationResult.getBestReservableMatch()).thenReturn(mockReservable);
        doThrow(ExceptionReservableAlreadyBooked.class).when(mockReservable).book(this.mockRequest);

        Optional<Reservation> reservation = this.reservationFactory.reserve(this.mockRequest, this.mockEvaluationResult);

        assertFalse(reservation.isPresent());
    }

    @Test
    public void InsufficientCapacityEvaluation_Reservation_ReturnsEmptyOptional() throws Exception, ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        IReservable mockReservable = mock(IReservable.class);
        when(this.mockEvaluationResult.matchFound()).thenReturn(true);
        when(this.mockEvaluationResult.getBestReservableMatch()).thenReturn(mockReservable);
        doThrow(ExceptionReservableInsufficientCapacity.class).when(mockReservable).book(this.mockRequest);

        Optional<Reservation> reservation = this.reservationFactory.reserve(this.mockRequest, this.mockEvaluationResult);

        assertFalse(reservation.isPresent());
    }
}
