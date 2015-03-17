package org.Marv1n.code.EvaluationStrategy;

import org.Marv1n.code.Reservable.Reservable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservableEvaluationResultTest {

    private ReservableEvaluationResult reservableEvaluationResult;
    @Mock
    private Reservable reservableMock;

    @Test
    public void givenReservableEvaluationResult_WhenAssignationResult_ThenShouldNotFoundMatch() {
        reservableEvaluationResult = new ReservableEvaluationResult();
        assertFalse(reservableEvaluationResult.matchFound());
    }

    private void initializeReservableEvaluationResultWithReservable() {
        reservableEvaluationResult = new ReservableEvaluationResult(reservableMock);
    }

    @Test
    public void givenReservableEvaluationResult_WhenAssignationResultWithReservable_ThenShouldFoundMatch() {
        initializeReservableEvaluationResultWithReservable();
        assertTrue(reservableEvaluationResult.matchFound());
    }

    @Test
    public void givenReservableEvaluationResult_WhenAssignationResultWithReservable_ThenCanReturnMatchingReservable() {
        initializeReservableEvaluationResultWithReservable();
        assertEquals(reservableMock, reservableEvaluationResult.getBestReservableMatch());
    }
}
