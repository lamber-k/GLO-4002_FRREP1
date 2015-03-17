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
public class TaskSchedulerTest {

    private static final int TIMER_ZERO = 0;
    private static final int A_TIMER = 5;
    private static final TimeUnit TIME_UNIT_SECOND = TimeUnit.SECONDS;
    private static final ScheduledExecutorService A_SCHEDULER_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final int DEFAULT_TIMER = 42;
    private TaskScheduler taskScheduler;
    @Mock
    private Runnable runnableMock;

    @Before
    public void initializeTaskScheduler() {
        taskScheduler = new TaskScheduler(A_SCHEDULER_EXECUTOR_SERVICE, DEFAULT_TIMER, TIME_UNIT_SECOND, runnableMock);
    }

    @Test
    public void givenTaskScheduler_WhenCreate_ThenShouldNotRunning() {
        assertFalse(taskScheduler.isSchedulerRunning());
    }

    @Test
    public void givenTaskScheduler_WhenStarted_ThenShouldRunning() {
        taskScheduler.startScheduler();
        assertTrue(taskScheduler.isSchedulerRunning());
    }

    @Test
    public void givenTaskSchedulerRunning_WhenCanceled_ThenShouldNotRunning() {
        taskScheduler.startScheduler();
        taskScheduler.cancelScheduler();
        assertFalse(taskScheduler.isSchedulerRunning());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenTaskScheduler_WhenStartWithTimerZero_ThenShouldThrowIllegalArgumentException() {
        taskScheduler.setIntervalTimer(TIMER_ZERO);
        taskScheduler.startScheduler();
    }

    @Test
    public void givenTaskSchedulerWithDefaultTimer_WhenGetDefaultTimer_ThenReturnDefaultTimer() {
        assertEquals(DEFAULT_TIMER, taskScheduler.getIntervalTimer());
    }

    @Test
    public void givenTaskSchedulerWithDefaultTimer_WhenSetDefaultTimer_ThenReturnTheNewDefaultTimer() {
        taskScheduler.setIntervalTimer(A_TIMER);
        assertEquals(A_TIMER, taskScheduler.getIntervalTimer());
    }

    @Test
    public void givenTaskScheduler_WhenStartScheduler_ThenMethodScheduleAtFixedRateShouldBeCalled() {
        ScheduledExecutorService aScheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        TaskScheduler scheduler = new TaskScheduler(aScheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND, runnableMock);

        scheduler.startScheduler();

        verify(aScheduledExecutorServiceMock).scheduleAtFixedRate(runnableMock, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
    }

    @Test
    public void givenTaskSchedulerRunning_WhenCancelScheduler_ThenMethodCancelShouldBeCalled() {
        ScheduledExecutorService aScheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        ScheduledFuture aScheduledFutureMock = mock(ScheduledFuture.class);
        doReturn(aScheduledFutureMock).when(aScheduledExecutorServiceMock).scheduleAtFixedRate(runnableMock, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
        TaskScheduler scheduler = new TaskScheduler(aScheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND, runnableMock);
        scheduler.startScheduler();

        scheduler.cancelScheduler();

        verify(aScheduledFutureMock).cancel(anyBoolean());
    }

    @Test
    public void givenTaskScheduler_WhenRestartSchedule_ThenRestartSchedulerAtBeginning() {
        ScheduledExecutorService aScheduledExecutorServiceMock = mock(ScheduledExecutorService.class);
        TaskScheduler scheduler = new TaskScheduler(aScheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND, runnableMock);

        scheduler.restartSchedule();

        verify(aScheduledExecutorServiceMock, times(1)).scheduleAtFixedRate(runnableMock, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
    }
}
