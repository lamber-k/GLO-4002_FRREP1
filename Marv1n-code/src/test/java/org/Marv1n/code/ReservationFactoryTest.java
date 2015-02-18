package org.Marv1n.code;

import org.Marv1n.code.Reservation.Reservation;
import org.Marv1n.code.Reservation.ReservationFactory;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void reservingOnEmptyEvaluationResultReturnsEmptyOptional() throws Exception {
        when(this.mockEvaluationResult.matchFound()).thenReturn(false);

        Optional<Reservation> reservation = this.reservationFactory.reserve(this.mockRequest, this.mockEvaluationResult);

        assertFalse(reservation.isPresent());
    }


} 
