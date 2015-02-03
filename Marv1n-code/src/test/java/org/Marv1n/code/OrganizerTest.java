package org.Marv1n.code;

import org.Marv1n.code.exception.NoRoomAvailableException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class OrganizerTest {

    private static final Integer RUN_INTERVAL = 120;
    private static final Integer DEFAULT_MAXIMUM_PENDING_REQUESTS = 2;
    private static final Integer A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final Integer MAXIMUM_ONE_PENDING_REQUEST = 1;

    private Organizer organizer;
    private TaskScheduler taskScheduler;
    private Room mockRoom;
    private Request aRequest;
    private StrategyAssignation mocStrategyAssignation;

    @Before
    public void initializeNewOrganizer() {
        mocStrategyAssignation = mock(StrategyAssignation.class);
        this.mockRoom = mock(Room.class);
        this.aRequest = new Request();
        this.taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), RUN_INTERVAL, TimeUnit.MINUTES);
        this.organizer = new Organizer();
        this.organizer.initialize(taskScheduler, DEFAULT_MAXIMUM_PENDING_REQUESTS, mocStrategyAssignation);
    }

    @Test
    public void newOrganizerHasNoRoom() {
        assertFalse(this.organizer.hasRoom());
    }

    @Test
    public void newOrganizerHasARoomWhenAdded() {
        this.organizer.addRoom(this.mockRoom);
        assertTrue(this.organizer.hasRoom());
    }

    @Test
    public void newOrganizerHasNoPendingRequest() {
        assertFalse(this.organizer.hasPendingRequest());
    }

    @Test(expected = NoRoomAvailableException.class)
    public void organizerThrowExceptionWhenThereIsNoRoom() {
        this.organizer.addRequest(aRequest);
    }

    @Test
    public void whenAddingRequestOrganizerReportsHavingPendingRequest() {
        this.organizer.addRoom(this.mockRoom);
        this.organizer.addRequest(aRequest);
        assertTrue(this.organizer.hasPendingRequest());
    }

    @Test
    public void organizerAfterTreatingPendingRequestsAssignationAsRun() throws Exception {
        this.organizer.addRoom(this.mockRoom);
        this.organizer.addRequest(aRequest);

        this.organizer.treatPendingRequest();

        verify(mocStrategyAssignation, times(1)).assingRooms(any(), any());
    }

    @Test
    public void organizerWhenTreatPendingRequestNowShouldCallTaskSchedulerRunNow() {
        StrategyAssignation mocStrategyAssignation = mock(StrategyAssignation.class);
        TaskScheduler schedulerMock = mock(TaskScheduler.class);
        this.organizer.initialize(schedulerMock, DEFAULT_MAXIMUM_PENDING_REQUESTS, mocStrategyAssignation);

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
    public void organiserWhenPendingRequestsReachMaximumPendingRequestsShouldRuAssignation() {
        this.organizer.setMaximumPendingRequests(MAXIMUM_ONE_PENDING_REQUEST);
        this.organizer.addRoom(this.mockRoom);
        this.organizer.addRequest(aRequest);

        verify(mocStrategyAssignation, times(1)).assingRooms(any(), any());
    }

    @After
    public void tearDown() {
        this.taskScheduler.cancelScheduler();
    }
}
