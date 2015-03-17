package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PendingRequestFullSchedulerNotifyTest {

    private PendingRequestFullSchedulerNotifyObserver pendingRequestNotifier;
    @Mock
    private Scheduler schedulerMock;

    @Before
    public void initializePendingRequestFullSchedulerNotify() {
        pendingRequestNotifier = new PendingRequestFullSchedulerNotifyObserver(schedulerMock);
    }

    @Test
    public void givenNotifier_WhenLimitReached_ThenShouldRestartScheduler() {
        pendingRequestNotifier.onMaximumPendingRequestReached();
        verify(schedulerMock).restartSchedule();
    }
}
