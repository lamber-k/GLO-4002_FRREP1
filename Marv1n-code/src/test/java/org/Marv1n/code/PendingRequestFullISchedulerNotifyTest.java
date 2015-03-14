package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PendingRequestFullISchedulerNotifyTest {

    private PendingRequestFullSchedulerNotify pendingRequestNotifier;
    @Mock
    private IScheduler ISchedulerMock;

    @Before
    public void initializePendingRequestFullSchedulerNotify() {
        ISchedulerMock = mock(IScheduler.class);
        pendingRequestNotifier = new PendingRequestFullSchedulerNotify(ISchedulerMock);
    }

    @Test
    public void givenNotifier_WhenLimitReached_ThenShouldRestartScheduler() {
        pendingRequestNotifier.onMaximumPendingRequestReached();
        verify(ISchedulerMock).restartSchedule();
    }
}
