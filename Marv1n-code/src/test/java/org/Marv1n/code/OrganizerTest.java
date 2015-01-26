package org.Marv1n.code;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class OrganizerTest {

    private static final Integer TWO_SECOND = 2;
    private Organizer organizer;
    private Integer A_TIMER = 120;

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
        this.organizer.setReservationInterval(A_TIMER);
        assertEquals(A_TIMER, this.organizer.getReservationIntervalTimer());
    }

    @Test
    public void newOrganizerHasSchedulerNotRunning() {
        assertFalse(this.organizer.isSchedulerRunning());
    }

    @Test
    public void newOrganizerWhenSchedulerStartedIsRunning() {
        this.organizer.setReservationInterval(TWO_SECOND);
        this.organizer.startScheduler();
        assertTrue(this.organizer.isSchedulerRunning());
    }


    @Test
    public void organizerAfterTreatingPendingRequestsHasNoMoreRequestPending() throws Exception {
        this.organizer.addRoom(new Room());
        this.organizer.addRequest(new Request());

        this.organizer.treatPendingRequestsNow();

        assertFalse(this.organizer.hasPendingRequest());
    }

    @Test
    public void organizerAfterAutomaticallyTreatingPendingRequestsHasNoMoreRequestPending() throws Exception {
        this.organizer.addRoom(new Room());
        this.organizer.addRequest(new Request());

        this.organizer.setReservationInterval(TWO_SECOND);
        this.organizer.startScheduler();
        Thread.sleep(3000);

        assertFalse(this.organizer.hasPendingRequest());
    }

    @After
    public void terminateScheduler() throws Exception {
        this.organizer.cancelScheduler();
    }
}
