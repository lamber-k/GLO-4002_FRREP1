package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrganizerTest {

    private static final int DEFAULT_MAXIMUM_PENDING_REQUESTS = 2;
    private static final int A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final int MAXIMUM_ONE_PENDING_REQUEST = 1;
    private Organizer organizer;

    @Mock
    private TaskScheduler taskScheduler;

    @Mock
    private Request aRequest;

    @Mock
    private RequestTreatment requestTreatmentMock;

    @Before
    public void initializeNewOrganizer() {
        organizer = new Organizer(taskScheduler, DEFAULT_MAXIMUM_PENDING_REQUESTS, requestTreatmentMock);
    }

    @Test
    public void newOrganizerHasNoPendingRequest() {
        assertFalse(this.organizer.hasPendingRequest());
    }

    @Test
    public void whenAddingRequestOrganizerReportsHavingPendingRequest() {
        organizer.addRequest(aRequest);
        assertTrue(organizer.hasPendingRequest());
    }

    @Test(expected = SchedulerAlreadyRunningException.class)
    public void whenStartOrganizerTwiceShouldThrowAlreadyRunning() throws SchedulerAlreadyRunningException {
        when(taskScheduler.isSchedulerRunning()).thenReturn(true);
        organizer.start();
    }

    @Test()
    public void whenStopOrganizerShouldStopScheduler() throws SchedulerAlreadyRunningException {
        when(taskScheduler.isSchedulerRunning()).thenReturn(true);
        organizer.stop();
        verify(taskScheduler).cancelScheduler();
    }

    @Test
    public void whenStartOrganizerShouldStartTaskScheduler() throws SchedulerAlreadyRunningException {
        organizer.start();
        verify(taskScheduler).startScheduler(requestTreatmentMock);
    }

    @Test
    public void organizerWhenTreatPendingRequestNowThenCallRequestTreatment() {
        this.organizer.treatPendingRequestsNow();
        verify(this.taskScheduler).runNow(requestTreatmentMock);
    }

    @Test
    public void newOrganizerHasMaximumPendingRequests() {
        assertEquals(DEFAULT_MAXIMUM_PENDING_REQUESTS, this.organizer.getMaximumPendingRequests());
    }

    @Test
    public void newOrganizerReflectsValueChange() {
        this.organizer.setMaximumPendingRequests(A_MAXIMUM_PENDING_REQUESTS);
        assertEquals(A_MAXIMUM_PENDING_REQUESTS, this.organizer.getMaximumPendingRequests());
    }

    @Test
    public void organizerWhenPendingRequestsReachMaximumPendingRequestsShouldRunAssignation() {
        this.organizer.setMaximumPendingRequests(MAXIMUM_ONE_PENDING_REQUEST);
        this.organizer.addRequest(this.aRequest);

        verify(this.taskScheduler).runNow(any());
    }
}
