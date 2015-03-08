package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrganizerTest {

    private Organizer organizer;
    @Mock
    private TaskScheduler taskScheduler;
    @Mock
    private Request aRequest;
    @Mock
    private RequestTreatment requestTreatmentMock;

    @Before
    public void initializeNewOrganizer() {
        organizer = new Organizer(taskScheduler, requestTreatmentMock);
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
        organizer.treatPendingRequestsNow();
        verify(taskScheduler).runNow(requestTreatmentMock);
    }
}
