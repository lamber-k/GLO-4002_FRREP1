package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Kevin on 13/03/2015.
 */
public class PendingRequestFullSchedulerNotifyTest {
    private Scheduler mockScheduler;
    private PendingRequestFullSchedulerNotify pendingRequestNotifier;

    @Before
    public void initializePendingRequestFullSchedulerNotify() {
        mockScheduler = mock(Scheduler.class);
        pendingRequestNotifier = new PendingRequestFullSchedulerNotify(mockScheduler);
    }

    @Test
    public void givenNotifier_whenLimitReached_thenShouldRestartScheduler() {
        pendingRequestNotifier.onMaximumPendingRequestReached();
        verify(mockScheduler).restartSchedule();
    }
}
