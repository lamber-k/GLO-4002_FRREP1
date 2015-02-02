package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TaskSchedulerTest {

    private static final Runnable A_RUNNABLE = new Runnable() {
        @Override
        public void run() {
        }
    };

    private static final Integer TIMER_ZERO = 0;
    private static final Integer A_TIMER = 5;
    private static final Integer ONE_THREAD = 1;
    private static final TimeUnit TIME_UNIT_SECOND = TimeUnit.SECONDS;
    private TaskScheduler taskScheduler;

    @Before
    public void createTaskScheduler() {
        this.taskScheduler = new TaskScheduler(ONE_THREAD, TIME_UNIT_SECOND);
    }

    @Test
    public void taskScheduler_WhenCreate_ShouldNotRunning() {
        assertFalse(taskScheduler.isSchedulerRunning());
    }

    @Test
    public void taskScheduler_WhenStarted_ShouldRunning() {
        this.taskScheduler.startScheduler(A_TIMER, A_RUNNABLE);
        assertTrue(taskScheduler.isSchedulerRunning());
    }

    @Test
    public void taskSchedulerRunning_WhenCanceled_ShouldNotRunning() {
        this.taskScheduler.startScheduler(A_TIMER, A_RUNNABLE);
        this.taskScheduler.cancelScheduler();
        assertFalse(this.taskScheduler.isSchedulerRunning());
    }

    @Test(expected = IllegalArgumentException.class)
    public void taskScheduler_WhenStartWithTimerZero_ShouldThrowIllegalArgumentException() {
        Runnable RunnableMock = mock(Runnable.class);
        this.taskScheduler.startScheduler(TIMER_ZERO, RunnableMock);
    }

    @Test
    public void taskScheduler_WhenRunOnce_RunMethodOfRunnableShouldBeCalled() throws ExecutionException, InterruptedException {
        Runnable RunnableMock = mock(Runnable.class);
        this.taskScheduler.runNow(A_TIMER, RunnableMock);
        verify(RunnableMock).run();
    }
}
