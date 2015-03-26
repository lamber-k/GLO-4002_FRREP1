package org.Marv1n.core.request.evaluation;

import org.Marv1n.core.room.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomEvaluationResultTest {

    private ReservableEvaluationResult reservableEvaluationResult;
    @Mock
    private Room roomMock;

    @Test
    public void givenReservableEvaluationResult_WhenAssignationResult_ThenShouldNotFoundMatch() {
        reservableEvaluationResult = new ReservableEvaluationResult();
        assertFalse(reservableEvaluationResult.matchFound());
    }

    private void initializeReservableEvaluationResultWithReservable() {
        reservableEvaluationResult = new ReservableEvaluationResult(roomMock);
    }

    @Test
    public void givenReservableEvaluationResult_WhenAssignationResultWithReservable_ThenShouldFoundMatch() {
        initializeReservableEvaluationResultWithReservable();
        assertTrue(reservableEvaluationResult.matchFound());
    }

    @Test
    public void givenReservableEvaluationResult_WhenAssignationResultWithReservable_ThenCanReturnMatchingReservable() {
        initializeReservableEvaluationResultWithReservable();
        assertEquals(roomMock, reservableEvaluationResult.getBestReservableMatch());
    }
}
