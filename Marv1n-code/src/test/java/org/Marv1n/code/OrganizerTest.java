package org.Marv1n.code;

import org.Marv1n.code.exception.NoRoomAvailableException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrganizerTest {

    private static final Integer DEFAULT_MAXIMUM_PENDING_REQUESTS = 2;
    private static final Integer A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final Integer MAXIMUM_ONE_PENDING_REQUEST = 1;
    private int oneTime = 1;

    private Organizer organizer;

    @Mock
    private TaskScheduler taskScheduler;

    @Mock
    private Room mockRoom;

    @Mock
    private Request aRequest;

    @Mock
    private StrategyAssignation mocStrategyAssignation;

    @Before
    public void initializeNewOrganizer() {
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
    public void organizerAfterTreatingPendingRequestsAssignationHasRun() throws Exception {
        this.organizer.addRoom(this.mockRoom);
        this.organizer.addRequest(aRequest);

        this.organizer.treatPendingRequest();

        verify(mocStrategyAssignation, times(this.oneTime)).assignRooms(any(), any());
    }

    @Test
    public void organizerWhenTreatPendingRequestNowShouldCallTaskSchedulerRunNow() {
        this.organizer.treatPendingRequestsNow();

        verify(taskScheduler).runNow(any(Runnable.class));
    }

    @Test
    public void newOrganizerHasMaximumPendingRequests() {
        assertEquals(DEFAULT_MAXIMUM_PENDING_REQUESTS, this.organizer.getMaximumPendingRequests());
    }

    @Test
    public void newOrganizerReflectsTimerChange() {
        this.organizer.setMaximumPendingRequests(A_MAXIMUM_PENDING_REQUESTS);
        assertEquals(A_MAXIMUM_PENDING_REQUESTS, this.organizer.getMaximumPendingRequests());
    }

    @Test
    public void organizerWhenPendingRequestsReachMaximumPendingRequestsShouldRunAssignation() {
        this.organizer.setMaximumPendingRequests(MAXIMUM_ONE_PENDING_REQUEST);
        this.organizer.addRoom(this.mockRoom);
        this.organizer.addRequest(aRequest);

        verify(taskScheduler, times(this.oneTime)).runNow(any());
    }

    @Test
    public void organizerCallsTreatPendingRequestWhenRun() {
        Organizer organizerSpy = spy(this.organizer);
        organizerSpy.run();
        verify(organizerSpy).treatPendingRequest();
    }

    @After
    public void tearDown() {
        this.taskScheduler.cancelScheduler();
    }
}
