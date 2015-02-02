package org.Marv1n.code;

import org.Marv1n.code.exception.NoRoomAvailableException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrganizerTest {

    private static final Integer RUN_INTERVAL = 120;

    private Organizer organizer;

    @Before
    public void initializeNewOrganizer() {
        this.organizer = new Organizer();
        this.organizer.initialize();
    }

    @Test
    public void newOrganizerHasNoRoom() {
        assertFalse(this.organizer.hasRoom());
    }

    @Test
    public void newOrganizerHasARoomWhenAdded() {
        this.organizer.addRoom(new Room());
        assertTrue(this.organizer.hasRoom());
    }

    @Test
    public void newOrganizerHasNoPendingRequest() {
        assertFalse(this.organizer.hasPendingRequest());
    }

    @Test(expected = NoRoomAvailableException.class)
    public void organizerThrowExceptionWhenThereIsNoRoom() {
        this.organizer.addRequest(new Request());
    }

    @Test
    public void whenAddingRequestOrganizerReportsHavingPendingRequest() {
        this.organizer.addRoom(new Room());
        this.organizer.addRequest(new Request());
        assertTrue(this.organizer.hasPendingRequest());
    }

    @Test
    public void newOrganizerHasDefaultTimer() {
        assertTrue(this.organizer.getReservationIntervalTimer() > 0);
    }

    @Test
    public void newOrganizerReflectsTimerChange() {
        this.organizer.setOrganizerRunInterval(RUN_INTERVAL);
        assertEquals(RUN_INTERVAL, this.organizer.getReservationIntervalTimer());
    }

    @Test
    public void organizerAfterTreatingPendingRequestsHasNoMoreRequestPending() throws Exception {
        this.organizer.addRoom(new Room());
        this.organizer.addRequest(new Request());

        this.organizer.treatPendingRequest();

        assertFalse(this.organizer.hasPendingRequest());
    }
}
