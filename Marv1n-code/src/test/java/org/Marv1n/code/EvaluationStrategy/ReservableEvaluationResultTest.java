package org.Marv1n.code.EvaluationStrategy;

import org.Marv1n.code.Reservable.IReservable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservableEvaluationResultTest {

    ReservableEvaluationResult reservableEvaluationResult;
    @Mock
    private IReservable reservable;

    @Test
    public void givenReservableEvaluationResult_WhenAssignationResult_ThenShouldNotFoundMatch() {
        reservableEvaluationResult = new ReservableEvaluationResult();
        assertFalse(reservableEvaluationResult.matchFound());
    }

    private void initializeReservableEvaluationResultWithReservable() {
        reservableEvaluationResult = new ReservableEvaluationResult(reservable);
    }

    @Test
    public void givenReservableEvaluationResult_WhenAssignationResultWithReservable_ThenShouldFoundMatch() {
        initializeReservableEvaluationResultWithReservable();
        assertTrue(reservableEvaluationResult.matchFound());
    }

    @Test
    public void givenReservableEvaluationResult_WhenAssignationResultWithReservable_ThenCanReturnMatchingReservable() {
        initializeReservableEvaluationResultWithReservable();
        assertEquals(reservable, reservableEvaluationResult.getBestReservableMatch());
    }
}
