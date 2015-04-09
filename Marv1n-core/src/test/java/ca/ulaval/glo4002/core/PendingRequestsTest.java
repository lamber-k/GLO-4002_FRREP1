package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.request.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PendingRequestsTest {

    private static final int DEFAULT_MAXIMUM_PENDING_REQUESTS = 10;
    private static final int A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final int A_MAXIMUM_ONE_PENDING_REQUEST = 1;
    private static final int A_MAXIMUM_TWO_PENDING_REQUEST = 2;
    private PendingRequests pendingRequests;
    @Mock
    private Request requestMock;
    @Mock
    private TaskSchedulerFactory taskSchedulerFactoryMock;
    @Mock
    private Scheduler schedulerMock;

    @Before
    public void initializePendingRequests() {
        when(taskSchedulerFactoryMock.getTaskScheduler(any(List.class))).thenReturn(schedulerMock);
        pendingRequests = new PendingRequests(DEFAULT_MAXIMUM_PENDING_REQUESTS, taskSchedulerFactoryMock);
    }

    @Test
    public void givenPendingRequest_WhenSetMaximumPendingAtConstructor_ThenReflectValue() {
        assertEquals(DEFAULT_MAXIMUM_PENDING_REQUESTS, pendingRequests.getMaximumPendingRequests());
    }

    @Test
    public void givenAPendingRequest_WhenSetMaximumPendingRequest_ThenReflectsValueChange() {
        pendingRequests.setMaximumPendingRequests(A_MAXIMUM_PENDING_REQUESTS);
        assertEquals(A_MAXIMUM_PENDING_REQUESTS, pendingRequests.getMaximumPendingRequests());
    }

    @Test
    public void givenPendingRequest_WhenPendingRequestFullAfterAddingARequest_ThenShouldCallSchedulerToRunNow() {
        pendingRequests.setMaximumPendingRequests(A_MAXIMUM_ONE_PENDING_REQUEST);
        pendingRequests.addRequest(requestMock);

        verify(schedulerMock).runNow();
    }

    @Test
    public void givenPendingRequest_WhenPendingRequestIsNotFullAfterAddingARequest_ThenDoesNotCallSchedulerToRunNow() {
        pendingRequests.setMaximumPendingRequests(DEFAULT_MAXIMUM_PENDING_REQUESTS);

        pendingRequests.addRequest(requestMock);

        verify(schedulerMock, never()).runNow();
    }

    @Test
    public void givenPendingRequest_WhenAddingRequest_ThenAmountOfRequestInPendingShouldIncreaseAndCallSchedulerRunNowAtMaximumPendingRequestHitting() {
        pendingRequests.setMaximumPendingRequests(A_MAXIMUM_TWO_PENDING_REQUEST);

        pendingRequests.addRequest(requestMock);

        verify(schedulerMock, never()).runNow();
        pendingRequests.addRequest(requestMock);
        verify(schedulerMock).runNow();

        //TODO FIXME any better idea to test adding request??
    }

    @Test
    public void givenPendingRequest_WhenCancellingTheExistingPendingRequest_ThenPendingListShouldBeEmpty() {
        pendingRequests.addRequest(requestMock);

        pendingRequests.cancelPendingRequest(requestMock);

        assertTrue(isEmptyPendingRequest(pendingRequests));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenPendingRequest_WhenCancellingANonExistingPendingRequest_ThenThrowObjectNotFoundException() {
        pendingRequests.cancelPendingRequest(requestMock);
    }

    private boolean isEmptyPendingRequest(PendingRequests pendingRequests) {

        try {
            for (int i = 1; i < pendingRequests.getMaximumPendingRequests(); i++) {
                pendingRequests.addRequest(requestMock);
            }
            verify(schedulerMock, never()).runNow();

            pendingRequests.addRequest(requestMock);
            verify(schedulerMock).runNow();
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
