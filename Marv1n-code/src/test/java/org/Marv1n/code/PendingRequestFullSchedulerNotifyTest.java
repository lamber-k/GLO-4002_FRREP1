package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PendingRequestFullSchedulerNotifyTest {

    private PendingRequestFullSchedulerNotify pendingRequestNotifier;
    @Mock
    private Scheduler schedulerMock;

    @Before
    public void initializePendingRequestFullSchedulerNotify() {
        schedulerMock = mock(Scheduler.class);
        pendingRequestNotifier = new PendingRequestFullSchedulerNotify(schedulerMock);
    }

    @Test
    public void givenNotifier_WhenLimitReached_ThenShouldRestartScheduler() {
        pendingRequestNotifier.onMaximumPendingRequestReached();
        verify(schedulerMock).restartSchedule();
    }
}
