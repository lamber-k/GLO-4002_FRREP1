package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservable.Room;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReservableEvaluationResultTest {

    private static final int A_NUMBER_OF_SEAT = 4;

    private IReservable aIReservable;

    @Before
    public void initializeNewReservable() {
        aIReservable = new Room(A_NUMBER_OF_SEAT);
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
