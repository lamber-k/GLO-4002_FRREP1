package ca.ulaval.glo4002.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    private static final int DEFAULT_TIMER = 42;
    private static final int NOW = 0;
    private TaskScheduler taskScheduler;
    @Mock
    private TaskFactory taskFactoryMock;
    @Mock
    private Task taskMock;
    @Mock
    private ScheduledExecutorService scheduledExecutorServiceMock;

    @Before
    public void initializeTaskScheduler() {
        when(taskFactoryMock.createTask()).thenReturn(taskMock);
        taskScheduler = new TaskScheduler(scheduledExecutorServiceMock, DEFAULT_TIMER, TIME_UNIT_SECOND, taskFactoryMock);
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
        ScheduledFuture<?> aScheduledFutureMock = mock(ScheduledFuture.class);
        doReturn(aScheduledFutureMock).when(scheduledExecutorServiceMock).scheduleAtFixedRate(taskScheduler, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);

        taskScheduler.startScheduler();
        taskScheduler.cancelScheduler();

        assertFalse(taskScheduler.isSchedulerRunning());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenTaskScheduler_WhenStartWithTimerZero_ThenShouldThrowIllegalArgumentException() {
        doThrow(IllegalArgumentException.class).when(scheduledExecutorServiceMock).scheduleAtFixedRate(taskScheduler, TIMER_ZERO, TIMER_ZERO, TIME_UNIT_SECOND);
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
        taskScheduler.startScheduler();

        verify(scheduledExecutorServiceMock).scheduleAtFixedRate(taskScheduler, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
    }

    @Test
    public void givenTaskSchedulerRunning_WhenCancelScheduler_ThenMethodCancelShouldBeCalled() {
        ScheduledFuture<?> aScheduledFutureMock = mock(ScheduledFuture.class);
        doReturn(aScheduledFutureMock).when(scheduledExecutorServiceMock).scheduleAtFixedRate(taskScheduler, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
        taskScheduler.startScheduler();

        taskScheduler.cancelScheduler();

        verify(aScheduledFutureMock).cancel(anyBoolean());
    }

    @Test
    public void givenTaskScheduler_WhenRestartSchedule_ThenRestartSchedulerAtBeginning() {
        taskScheduler.restartSchedule();

        verify(scheduledExecutorServiceMock, times(1)).scheduleAtFixedRate(taskScheduler, DEFAULT_TIMER, DEFAULT_TIMER, TIME_UNIT_SECOND);
    }

    @Test
    public void givenTaskScheduler_WhenRunNow_ThenRestartSchedulerNow() {
        taskScheduler.runNow();

        verify(scheduledExecutorServiceMock, times(1)).scheduleAtFixedRate(taskScheduler, NOW, DEFAULT_TIMER, TIME_UNIT_SECOND);
    }


}
