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

    private static final Integer TIMER_ZERO = 0;
    private static final Integer A_TIMER = 5;
    private static final TimeUnit TIME_UNIT_SECOND = TimeUnit.SECONDS;
    private static final ScheduledExecutorService A_SCHEDULER_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final Integer DEFAULT_TIMER = 42;
    @Mock
    private Runnable A_RUNNABLE;
    private TaskScheduler taskScheduler;

    @Before
    public void createTaskScheduler() {
        this.taskScheduler = new TaskScheduler(A_SCHEDULER_EXECUTOR_SERVICE, DEFAULT_TIMER, TIME_UNIT_SECOND);
    }

    @Test
    public void taskScheduler_WhenCreate_ShouldNotRunning() {
        assertFalse(taskScheduler.isSchedulerRunning());
    }

    @Test
    public void taskScheduler_WhenStarted_ShouldRunning() {
        this.taskScheduler.startScheduler(A_RUNNABLE);
        assertTrue(taskScheduler.isSchedulerRunning());
    }

    @Test
    public void taskSchedulerRunning_WhenCanceled_ShouldNotRunning() {
        this.taskScheduler.startScheduler(A_RUNNABLE);
        this.taskScheduler.cancelScheduler();
        assertFalse(this.taskScheduler.isSchedulerRunning());
    }

    @Test(expected = IllegalArgumentException.class)
    public void taskScheduler_WhenStartWithTimerZero_ShouldThrowIllegalArgumentException() {
        this.taskScheduler.setIntervalTimer(TIMER_ZERO);
        this.taskScheduler.startScheduler(A_RUNNABLE);
    }

    @Test
    public void taskScheduler_WhenRunOnce_RunMethodOfRunnableShouldBeCalled() throws ExecutionException, InterruptedException {
        this.taskScheduler.runNow(A_RUNNABLE);
        verify(A_RUNNABLE).run();
    }

    @Test
    public void newTaskSchedulerHasDefaultTimer() {
        assertEquals(DEFAULT_TIMER, this.taskScheduler.getIntervalTimer());
    }

    @Test
    public void newTaskSchedulerReflectsTimerChange() {
        this.taskScheduler.setIntervalTimer(A_TIMER);
        assertEquals(A_TIMER, this.taskScheduler.getIntervalTimer());
    }

    @Test
    public void newTaskScheduler_WhenStartScheduler_MethodScheduleAtFixedRateShouldBeCalled() {
        ScheduledExecutorService aScheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        TaskScheduler scheduler = new TaskScheduler(aScheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND);

        scheduler.startScheduler(A_RUNNABLE);

        verify(aScheduledExecutorServiceMock).scheduleAtFixedRate(A_RUNNABLE, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
    }

    @Test
    public void aTaskSchedulerRunning_WhenCancelScheduler_MethodCancelShouldBeCalled() {
        ScheduledExecutorService aScheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        ScheduledFuture aScheduledFutureMock = mock(ScheduledFuture.class);
        doReturn(aScheduledFutureMock).when(aScheduledExecutorServiceMock).scheduleAtFixedRate(A_RUNNABLE, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
        TaskScheduler scheduler = new TaskScheduler(aScheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND);
        scheduler.startScheduler(A_RUNNABLE);

        scheduler.cancelScheduler();

        verify(aScheduledFutureMock).cancel(anyBoolean());
    }
}
