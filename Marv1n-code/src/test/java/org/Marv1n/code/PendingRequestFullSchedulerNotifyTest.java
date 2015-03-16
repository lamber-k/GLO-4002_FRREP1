package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PendingRequestFullSchedulerNotifyTest {

    private PendingRequestFullSchedulerNotifyObserver pendingRequestNotifier;
    @Mock
    private Scheduler SchedulerMock;

    @Before
    public void initializePendingRequestFullSchedulerNotify() {
        SchedulerMock = mock(Scheduler.class);
        pendingRequestNotifier = new PendingRequestFullSchedulerNotifyObserver(SchedulerMock);
    }

    @Test
    public void givenNotifier_WhenLimitReached_ThenShouldRestartScheduler() {
        pendingRequestNotifier.onMaximumPendingRequestReached();
        verify(SchedulerMock).restartSchedule();
    }
}
