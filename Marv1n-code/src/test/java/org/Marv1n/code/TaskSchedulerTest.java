package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import java.rmi.UnexpectedException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Rafaël on 27/01/2015.
 */
public class TaskSchedulerTest {

    private static final Runnable A_RUNNABLE = new Runnable() {
        @Override
        public void run() {
        }
    };

    private static final Integer TIMER_ZERO = 0;
    private static final Integer A_TIMER = 5;
    private static final Integer ONE_THREAD = 1;
    private TaskScheduler taskScheduler;

    @Before
    public void CreateTaskScheduler() {
        this.taskScheduler = new TaskScheduler(ONE_THREAD);
    }

    @Test
    public void TaskScheduler_WhenCreate_ShouldNotRunning() {
        assertFalse(taskScheduler.IsSchedulerRunning());
    }

    @Test
    public void TaskScheduler_WhenStarted_ShouldRunning() {
        this.taskScheduler.startScheduler(A_TIMER, A_RUNNABLE);
        assertTrue(taskScheduler.IsSchedulerRunning());
    }

    @Test
    public void TaskSchedulerRunning_WhenCanceled_ShouldNotRunning() {
        this.taskScheduler.startScheduler(A_TIMER, A_RUNNABLE);
        this.taskScheduler.cancelScheduler();
        assertFalse(this.taskScheduler.IsSchedulerRunning());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TaskScheduler_WhenStartWithTimerZero_ShouldThrowIllegalArgumentException() {
        Runnable RunnableMock = mock(Runnable.class);
        this.taskScheduler.startScheduler(TIMER_ZERO, RunnableMock);
    }

    @Test
    public void TaskScheduler_WhenRunOnce_RunMethodOfRunnableShouldBeCalled() throws ExecutionException, InterruptedException {
        Runnable RunnableMock = mock(Runnable.class);
        this.taskScheduler.runOnce(RunnableMock);
        verify(RunnableMock).run();
    }
}
