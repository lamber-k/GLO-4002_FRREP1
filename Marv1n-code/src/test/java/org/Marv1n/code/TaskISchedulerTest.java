package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskISchedulerTest {

    private TaskIScheduler taskScheduler;
    private static final int TIMER_ZERO = 0;
    private static final int A_TIMER = 5;
    private static final TimeUnit TIME_UNIT_SECOND = TimeUnit.SECONDS;
    private static final ScheduledExecutorService A_SCHEDULER_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final int DEFAULT_TIMER = 42;
    @Mock
    private Runnable runnable;

    @Before
    public void createTaskScheduler() {
        taskScheduler = new TaskIScheduler(A_SCHEDULER_EXECUTOR_SERVICE, DEFAULT_TIMER, TIME_UNIT_SECOND, runnable);
    }

    @Test
    public void taskScheduler_WhenCreate_ThenShouldNotRunning() {
        assertFalse(taskScheduler.isSchedulerRunning());
    }

    @Test
    public void taskScheduler_WhenStarted_ThenShouldRunning() {
        taskScheduler.startScheduler();
        assertTrue(taskScheduler.isSchedulerRunning());
    }

    @Test
    public void taskSchedulerRunning_WhenCanceled_ThenShouldNotRunning() {
        taskScheduler.startScheduler();
        taskScheduler.cancelScheduler();
        assertFalse(taskScheduler.isSchedulerRunning());
    }

    @Test(expected = IllegalArgumentException.class)
    public void taskScheduler_WhenStartWithTimerZero_ThenShouldThrowIllegalArgumentException() {
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
    public void newTaskScheduler_WhenStartScheduler_ThenMethodScheduleAtFixedRateShouldBeCalled() {
        ScheduledExecutorService aScheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        TaskIScheduler scheduler = new TaskIScheduler(aScheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND, runnable);

        scheduler.startScheduler();

        verify(aScheduledExecutorServiceMock).scheduleAtFixedRate(runnable, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
    }

    @Test
    public void aTaskSchedulerRunning_WhenCancelScheduler_ThenMethodCancelShouldBeCalled() {
        ScheduledExecutorService aScheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        ScheduledFuture aScheduledFutureMock = mock(ScheduledFuture.class);
        doReturn(aScheduledFutureMock).when(aScheduledExecutorServiceMock).scheduleAtFixedRate(runnable, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
        TaskIScheduler scheduler = new TaskIScheduler(aScheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND, runnable);
        scheduler.startScheduler();

        scheduler.cancelScheduler();

        verify(aScheduledFutureMock).cancel(anyBoolean());
    }


    @Test
    public void aTaskScheduler_WhenRestartSchedule_ThenRestartSchedulerAtBeginning() {
        ScheduledExecutorService aScheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        TaskIScheduler scheduler = new TaskIScheduler(aScheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND, runnable);

        scheduler.restartSchedule();

        verify(aScheduledExecutorServiceMock, times(1)).scheduleAtFixedRate(runnable, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
    }
}
