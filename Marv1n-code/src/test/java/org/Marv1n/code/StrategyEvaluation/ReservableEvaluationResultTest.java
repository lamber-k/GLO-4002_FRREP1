package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Reservable.IReservable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ReservableEvaluationResultTest {

    private IReservable reservable;

    @Before
    public void initializeNewReservable() {
        reservable = mock(IReservable.class);
    }


    @Test
    public void newReservable_WhenAssignationResult_ThenShouldNotFoundMatch() {
        ReservableEvaluationResult ReservableEvaluationResult = new ReservableEvaluationResult();
        assertFalse(ReservableEvaluationResult.matchFound());
    }

    @Test
    public void newReservable_WhenAssignationResultWithReservable_ThenShouldFoundMatch() {
        ReservableEvaluationResult ReservableEvaluationResult = new ReservableEvaluationResult(reservable);
        assertTrue(ReservableEvaluationResult.matchFound());
    }

    @Test
    public void newReservable_WhenAssignationResultWithReservable_ThenCanReturnMatchingReservable() {
        ReservableEvaluationResult ReservableEvaluationResult = new ReservableEvaluationResult(reservable);
        assertEquals(reservable, ReservableEvaluationResult.getBestReservableMatch());
    }
}
