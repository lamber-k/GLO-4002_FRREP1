package org.Marv1n.code;

import org.Marv1n.code.exception.NoRoomAvailableException;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OrganizerTest {

    private static final Integer RUN_INTERVAL = 120;

    private Organizer organizer;

    @Before
    public void initializeNewOrganizer() {
        TaskScheduler taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), RUN_INTERVAL, TimeUnit.MINUTES);
        this.organizer = new Organizer();
        this.organizer.initialize(taskScheduler);
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
    public void organizerAfterTreatingPendingRequestsHasNoMoreRequestPending() throws Exception {
        this.organizer.addRoom(new Room());
        this.organizer.addRequest(new Request());

        this.organizer.treatPendingRequest();

        assertFalse(this.organizer.hasPendingRequest());
    }

    @Test
    public void organizerWhenTreatPendingRequestNowShouldCallTaskSchedulerRunNow() {
        TaskScheduler schedulerMock = mock(TaskScheduler.class);
        this.organizer.initialize(schedulerMock);

        this.organizer.treatPendingRequestsNow();

        verify(schedulerMock).runNow(any(Runnable.class));
    }
}
