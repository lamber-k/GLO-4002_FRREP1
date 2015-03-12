package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Reservable.IReservable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ReservableEvaluationResultTest {

    private IReservable aIReservable;

    @Before
    public void initializeNewReservable() {
        aIReservable = mock(IReservable.class);
    }


    @Test
    public void newReservable_WhenAssignationResult_ShouldNotFoundMatch() {
        ReservableEvaluationResult ReservableEvaluationResult = new ReservableEvaluationResult();
        assertFalse(ReservableEvaluationResult.matchFound());
    }

    @Test
    public void newReservable_WhenAssignationResultWithReservable_ShouldFoundMatch() {
        ReservableEvaluationResult ReservableEvaluationResult = new ReservableEvaluationResult(aIReservable);
        assertTrue(ReservableEvaluationResult.matchFound());
    }

    @Test
    public void newReservable_WhenAssignationResultWithReservable_CanReturnMatchingReservable() {
        ReservableEvaluationResult ReservableEvaluationResult = new ReservableEvaluationResult(aIReservable);
        assertEquals(aIReservable, ReservableEvaluationResult.getBestReservableMatch());
    }
}
