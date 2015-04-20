package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.Notification;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
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
    private Scheduler schedulerMock;
    @Mock
    private RequestRepository requestRepositoryMock;
    @Mock
    private NotificationFactory notificationFactoryMock;
    @Mock
    private Notification notificationMock;

    @Before
    public void initializePendingRequests() {
        pendingRequests = new PendingRequests(DEFAULT_MAXIMUM_PENDING_REQUESTS);
        pendingRequests.setScheduler(schedulerMock);
        when(notificationFactoryMock.createNotification(requestMock)).thenReturn(notificationMock);
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
    public void givenPendingRequest_WhenCancellingTheExistingPendingRequest_ThenPendingListShouldBeEmpty() throws ObjectNotFoundException {
        givenRequest();

        pendingRequests.cancelPendingRequest(AN_UUID, requestRepositoryMock, notificationFactoryMock);

        assertTrue(isEmptyPendingRequest(pendingRequests));
    }

    @Test
    public void givenPendingRequest_WhenCancellingTheExistingPendingRequest_ThenRequestCancelledShouldBePersist() throws ObjectNotFoundException {
        givenRequest();

        pendingRequests.cancelPendingRequest(AN_UUID, requestRepositoryMock, notificationFactoryMock);

        verify(requestMock).cancel();
        verify(requestRepositoryMock).persist(requestMock);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenPendingRequest_WhenCancellingANonExistingPendingRequest_ThenThrowObjectNotFoundException() throws ObjectNotFoundException {
        pendingRequests.cancelPendingRequest(AN_UUID, requestRepositoryMock, notificationFactoryMock);
    }

    @Test
    public void givenPendingRequest_WhenCancelling_ThenShouldAnnounce() throws ObjectNotFoundException {
        givenRequest();

        pendingRequests.cancelPendingRequest(AN_UUID, requestRepositoryMock, notificationFactoryMock);

        verify(notificationMock).announce();
    }

    @Test
    public void givenRequest_WhenRetrieveCurrentPendingRequest_TheShouldReturnTheRequest() {
        pendingRequests.addRequest(requestMock);

        List<Request> retreive = pendingRequests.retrieveCurrentPendingRequest();

        assertEquals(1, retreive.size());
        assertEquals(requestMock, retreive.get(0));
    }

    @Test
    public void givenRequest_WhenRetrieveTwice_ShouldReturnEmptyList() {
        pendingRequests.addRequest(requestMock);

        List<Request> retrieve = pendingRequests.retrieveCurrentPendingRequest();
        retrieve = pendingRequests.retrieveCurrentPendingRequest();

        assertEquals(0, retrieve.size());
    }

    @Test
    public void givenTwoRequest_WhenGetCurrentPendingRequest_ShouldReturnAllRequest() {
        Request secondRequest = mock(Request.class);
        pendingRequests.addRequest(requestMock);
        pendingRequests.addRequest(secondRequest);

        List<Request> requests = pendingRequests.getCurrentPendingRequest();

        assertEquals(requestMock, requests.get(0));
        assertEquals(secondRequest, requests.get(1));
    }

    @Test
    public void givenRequests_WhenModifyListReturnByGet_ShouldNotModifyInternalList() {
        Request secondRequest = mock(Request.class);
        pendingRequests.addRequest(requestMock);
        List<Request> requests = pendingRequests.getCurrentPendingRequest();
        requests.add(secondRequest);

        requests = pendingRequests.getCurrentPendingRequest();

        assertEquals(1, requests.size());
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
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
