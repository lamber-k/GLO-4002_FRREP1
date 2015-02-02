package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TaskSchedulerTest {

    private static final Runnable A_RUNNABLE = () -> {
    };


    private static final Integer TIMER_ZERO = 0;
    private static final Integer A_TIMER = 5;
    private static final TimeUnit TIME_UNIT_SECOND = TimeUnit.SECONDS;
    private static final ScheduledExecutorService A_SCHEDULER_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final Integer DEFAULT_TIMER = 42;
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
        Runnable RunnableMock = mock(Runnable.class);
        this.taskScheduler.runNow(RunnableMock);
        verify(RunnableMock).run();
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
}
