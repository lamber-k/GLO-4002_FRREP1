package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PendingRequestsTest {

    private static final int DEFAULT_MAXIMUM_PENDING_REQUESTS = 10;
    private static final int A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final int A_MAXIMUM_ONE_PENDING_REQUEST = 1;
    private static final int A_MAXIMUM_TWO_PENDING_REQUEST = 2;
    private static final UUID AN_UUID = UUID.randomUUID();
    private PendingRequests pendingRequests;
    @Mock
    private Request requestMock;
    @Mock
    private TaskSchedulerFactory taskSchedulerFactoryMock;
    @Mock
    private Scheduler schedulerMock;
    @Mock
    private RequestRepository requestRepositoryMock;

    @Before
    public void initializePendingRequests() {
        when(taskSchedulerFactoryMock.getTaskSheduler(any(LinkedList.class))).thenReturn(schedulerMock);
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
    public void givenPendingRequest_WhenPendingRequestFullAfterAddingARequest_ThenShouldCallShedulerToRunNow() {
        pendingRequests.setMaximumPendingRequests(A_MAXIMUM_ONE_PENDING_REQUEST);
        pendingRequests.addRequest(requestMock);

        verify(schedulerMock).runNow();
    }

    @Test
    public void givenPendingRequest_WhenPendingRequestIsNotFullAfterAddingARequest_ThenDoesNotCallShedulerToRunNow() {
        pendingRequests.setMaximumPendingRequests(DEFAULT_MAXIMUM_PENDING_REQUESTS);

        pendingRequests.addRequest(requestMock);

        verify(schedulerMock, never()).runNow();
    }

    @Test
    public void givenPendingRequest_WhenAddingRequest_ThenAmountOfRequestInPendingShouldIncreaseAndCallShedulerRunNowAtMaximumPendingRequestHitting() {
        pendingRequests.setMaximumPendingRequests(A_MAXIMUM_TWO_PENDING_REQUEST);

        pendingRequests.addRequest(requestMock);

        verify(schedulerMock, never()).runNow();
        pendingRequests.addRequest(requestMock);
        verify(schedulerMock).runNow();

        //TODO FIXME any better idea to test adding request??
    }

    @Test
    public void givenPendingRequest_WhenCancellingTheExistingPendingRequest_ThenPendingListShouldBeEmpty() throws InvalidFormatException {
        givenRequest();

        pendingRequests.cancelPendingRequest(AN_UUID, requestRepositoryMock);

        assertTrue(isEmptyPendingRequest(pendingRequests));
    }

    @Test
    public void givenPendingRequest_WhenCancellingTheExistingPendingRequest_ThenRequestCanceledShouldBePersist() throws InvalidFormatException {
        givenRequest();

        pendingRequests.cancelPendingRequest(AN_UUID, requestRepositoryMock);

        verify(requestRepositoryMock).persist(requestMock);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenPendingRequest_WhenCancellingANonExistingPendingRequest_ThenThrowObjectNotFoundException() throws InvalidFormatException {
        pendingRequests.cancelPendingRequest(AN_UUID, requestRepositoryMock);
    }

    private void givenRequest() {
        when(requestMock.getRequestID()).thenReturn(AN_UUID);
        pendingRequests.addRequest(requestMock);
    }

    private boolean isEmptyPendingRequest(PendingRequests pendingRequests) {

        try {
            for (int i = 1; i < pendingRequests.getMaximumPendingRequests(); i++) {
                pendingRequests.addRequest(requestMock);
            }
            verify(schedulerMock, never()).runNow();

            pendingRequests.addRequest(requestMock);
            verify(schedulerMock).runNow();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
