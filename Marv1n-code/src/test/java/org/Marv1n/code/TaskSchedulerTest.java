package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskSchedulerTest {

    private static final int TIMER_ZERO = 0;
    private static final int A_TIMER = 5;
    private static final TimeUnit TIME_UNIT_SECOND = TimeUnit.SECONDS;
    private static final ScheduledExecutorService A_SCHEDULER_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final int DEFAULT_TIMER = 42;

    @Mock
    private Runnable aRunnable;
    private TaskScheduler taskScheduler;

    @Before
    public void createTaskScheduler() {
        taskScheduler = new TaskScheduler(A_SCHEDULER_EXECUTOR_SERVICE, DEFAULT_TIMER, TIME_UNIT_SECOND, aRunnable);
    }

    @Test
    public void taskScheduler_WhenCreate_ShouldNotRunning() {
        assertFalse(taskScheduler.isSchedulerRunning());
    }

    @Test
    public void taskScheduler_WhenStarted_ShouldRunning() {
        taskScheduler.startScheduler();
        assertTrue(taskScheduler.isSchedulerRunning());
    }

    @Test
    public void taskSchedulerRunning_WhenCanceled_ShouldNotRunning() {
        taskScheduler.startScheduler();
        taskScheduler.cancelScheduler();
        assertFalse(taskScheduler.isSchedulerRunning());
    }

    @Test(expected = IllegalArgumentException.class)
    public void taskScheduler_WhenStartWithTimerZero_ShouldThrowIllegalArgumentException() {
        taskScheduler.setIntervalTimer(TIMER_ZERO);
        taskScheduler.startScheduler();
    }

    @Test
    public void newTaskScheduler_HasDefaultTimer() {
        assertEquals(DEFAULT_TIMER, taskScheduler.getIntervalTimer());
    }

    @Test
    public void newTaskScheduler_ReflectsTimerChange() {
        taskScheduler.setIntervalTimer(A_TIMER);
        assertEquals(A_TIMER, taskScheduler.getIntervalTimer());
    }

    @Test
    public void newTaskScheduler_WhenStartScheduler_MethodScheduleAtFixedRateShouldBeCalled() {
        ScheduledExecutorService aScheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        TaskScheduler scheduler = new TaskScheduler(aScheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND, aRunnable);

        scheduler.startScheduler();

        verify(aScheduledExecutorServiceMock).scheduleAtFixedRate(aRunnable, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
    }

    @Test
    public void aTaskSchedulerRunning_WhenCancelScheduler_MethodCancelShouldBeCalled() {
        ScheduledExecutorService aScheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        ScheduledFuture aScheduledFutureMock = mock(ScheduledFuture.class);
        doReturn(aScheduledFutureMock).when(aScheduledExecutorServiceMock).scheduleAtFixedRate(aRunnable, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
        TaskScheduler scheduler = new TaskScheduler(aScheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND, aRunnable);
        scheduler.startScheduler();

        scheduler.cancelScheduler();

        verify(aScheduledFutureMock).cancel(anyBoolean());
    }


    @Test
    public void aTaskScheduler_whenInformedOfMaximumPendingRequestReached_thenRestartSchedulerAnRun(){
        ScheduledExecutorService aScheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        TaskScheduler scheduler = new TaskScheduler(aScheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND, aRunnable);

        scheduler.onMaximumPendingRequestReached();

        verify(aScheduledExecutorServiceMock, times(1)).scheduleAtFixedRate(aRunnable,DEFAULT_TIMER,DEFAULT_TIMER,TIME_UNIT_SECOND);

    }
}
