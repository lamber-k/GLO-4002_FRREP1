package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReservableAssignationResultTest {

    private static final Integer A_NUMBER_OF_SEAT = 4;

    private Reservable aReservable;

    @Before
    public void initializeNewReservable() {
        this.aReservable = new Room(A_NUMBER_OF_SEAT);
        fail("new Room(...), si nouveau implements Reservable ?");
    }

    @Test
    public void newReservable_WhenAssignationResult_ShouldNotFoundMatch() {
        ReservableAssignationResult ReservableAssignationResult = new ReservableAssignationResult();
        assertFalse(ReservableAssignationResult.matchFound());
    }

    @Test
    public void newReservable_WhenAssignationResultWithReservable_ShouldFoundMatch() {
        ReservableAssignationResult ReservableAssignationResult = new ReservableAssignationResult(this.aReservable);
        assertTrue(ReservableAssignationResult.matchFound());
    }

    @Test
    public void newReservable_WhenAssignationResultWithReservable_CanReturnMatchingReservable() {
        ReservableAssignationResult ReservableAssignationResult = new ReservableAssignationResult(this.aReservable);
        assertEquals(this.aReservable, ReservableAssignationResult.getBestReservableMatch());
    }
}
