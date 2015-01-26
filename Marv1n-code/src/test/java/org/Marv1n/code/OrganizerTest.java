package org.Marv1n.code;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rafaël on 21/01/2015.
 */
public class OrganizerTest {

    private static final Integer TWO_SECOND = 2;
    private Organizer organizer;
    private Integer DEFAULT_TIMER = 180;
    private Integer A_TIMER = 120;

    @Before
    public void initializeNewOrganizer() {
        this.organizer = new Organizer();
        this.organizer.Initialize();
    }

    @Test
    public void newOrganizerHasNoRoom() {
        assertFalse(this.organizer.HasRoom());
    }

    @Test
    public void newOrganizerHasARoomWhenAdded() {
        this.organizer.AddRoom(new Room());
        assertTrue(this.organizer.HasRoom());
    }

    @Test
    public void newOrganizerHasNoPendingRequest() {
        assertFalse(this.organizer.HasPendingRequest());
    }

    @Test(expected = NoRoomAvailableException.class)
    public void OrganizerThrowExceptionWhenThereIsNoRoom() {
        this.organizer.AddRequest(new Request());
    }

    @Test
    public void WhenAddingRequestOrganizerReportsHavingPendingRequest() {
        this.organizer.AddRoom(new Room());
        this.organizer.AddRequest(new Request());
        assertTrue(this.organizer.HasPendingRequest());
    }

    @Test
    public void NewOrganizerHasDefaultTimer() {
        assertTrue(this.organizer.GetReservationIntervalTimer() > 0);
    }

    @Test
    public void NewOrganizerReflectsTimerChange() {
        this.organizer.SetReservationInterval(A_TIMER);
        assertEquals(A_TIMER, this.organizer.GetReservationIntervalTimer());
    }

    @Test
    public void NewOrganizerHasSchedulerNotRunning() {
        assertFalse(this.organizer.IsSchedulerRunning());
    }

    @Test
    public void NewOrganizerWhenSchedulerStartedIsRunning() {
        this.organizer.SetReservationInterval(TWO_SECOND);
        this.organizer.StartScheduler();
        assertTrue(this.organizer.IsSchedulerRunning());
    }


    @Test
    public void OrganizerAfterTreatingPendingRequestsHasNoMoreRequestPending() throws Exception {
        this.organizer.AddRoom(new Room());
        this.organizer.AddRequest(new Request());

        this.organizer.TreatPendingRequestsNow();

        assertFalse(this.organizer.HasPendingRequest());
    }

    @Test
    public void OrganizerAfterAutomaticallyTreatingPendingRequestsHasNoMoreRequestPending() throws Exception {
        this.organizer.AddRoom(new Room());
        this.organizer.AddRequest(new Request());

        this.organizer.SetReservationInterval(TWO_SECOND);
        this.organizer.StartScheduler();
        Thread.sleep(3000);

        assertFalse(this.organizer.HasPendingRequest());
    }


    @After
    public void tearDown() throws Exception {
        this.organizer.CancelScheduler();
    }
}
