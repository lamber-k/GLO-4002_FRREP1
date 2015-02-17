package org.Marv1n.code;

import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservable.Room;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReservableAssignationResultTest {

    private static final Integer A_NUMBER_OF_SEAT = 4;

    private IReservable aIReservable;

    @Before
    public void initializeNewReservable() {
        this.aIReservable = new Room(A_NUMBER_OF_SEAT);
    }

    @Test
    public void newReservable_WhenAssignationResult_ShouldNotFoundMatch() {
        ReservableAssignationResult ReservableAssignationResult = new ReservableAssignationResult();
        assertFalse(ReservableAssignationResult.matchFound());
    }

    @Test
    public void newReservable_WhenAssignationResultWithReservable_ShouldFoundMatch() {
        ReservableAssignationResult ReservableAssignationResult = new ReservableAssignationResult(this.aIReservable);
        assertTrue(ReservableAssignationResult.matchFound());
    }

    @Test
    public void newReservable_WhenAssignationResultWithReservable_CanReturnMatchingReservable() {
        ReservableAssignationResult ReservableAssignationResult = new ReservableAssignationResult(this.aIReservable);
        assertEquals(this.aIReservable, ReservableAssignationResult.getBestReservableMatch());
    }
}
