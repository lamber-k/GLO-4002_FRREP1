package org.Marv1n.code;

import org.Marv1n.code.exception.NoRoomAvailableException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OrganizerTest {

    private static final Integer RUN_INTERVAL = 120;
    private static final Integer DEFAULT_MAXIMUM_PENDING_REQUESTS = 2;
    private static final Integer A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final Integer Maximum_ONE_PENDING_REQUEST = 1;

    private Organizer organizer;
    private TaskScheduler taskScheduler;

    @Before
    public void initializeNewOrganizer() {
        this.taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), RUN_INTERVAL, TimeUnit.MINUTES);
        this.organizer = new Organizer();
        this.organizer.initialize(taskScheduler, DEFAULT_MAXIMUM_PENDING_REQUESTS);
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
        this.organizer.initialize(schedulerMock, DEFAULT_MAXIMUM_PENDING_REQUESTS);

        this.organizer.treatPendingRequestsNow();

        verify(schedulerMock).runNow(any(Runnable.class));
    }

    @Test
    public void newOrganiserHasMaximumPendingRequests() {
        assertEquals(DEFAULT_MAXIMUM_PENDING_REQUESTS, this.organizer.getMaximumPendingRequests());
    }

    @Test
    public void newOrganiserReflectsTimerChange() {
        this.organizer.setMaximumPendingRequests(A_MAXIMUM_PENDING_REQUESTS);
        assertEquals(A_MAXIMUM_PENDING_REQUESTS, this.organizer.getMaximumPendingRequests());
    }

    @Test
    public void organiserWhenPendingRequestsReachMaximumPendingRequestsShouldCallRunNowOf() {
        this.organizer.setMaximumPendingRequests(Maximum_ONE_PENDING_REQUEST);
        this.organizer.addRoom(new Room());
        this.organizer.addRequest(new Request());

        assertFalse(this.organizer.hasPendingRequest());
    }

    @After
    public void tearDown() {
        this.taskScheduler.cancelScheduler();
    }
}
