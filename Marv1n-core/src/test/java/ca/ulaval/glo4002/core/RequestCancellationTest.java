package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.Notification;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestCancellationTest {
    private static final UUID AN_UUID = UUID.randomUUID();
    @Mock
    PendingRequests pendingRequestsMock;
    @Mock
    RequestRepository requestRepositoryMock;
    private RequestCancellation requestCancellation;
    @Mock
    private Request requestMock;
    @Mock
    private NotificationFactory notificationFactoryMock;
    @Mock
    private Notification notificationMock;

    @Before
    public void initializeRequestCancellation() {
        this.requestCancellation = new RequestCancellation(pendingRequestsMock, requestRepositoryMock, notificationFactoryMock);
        when(notificationFactoryMock.createNotification(requestMock)).thenReturn(notificationMock);
    }

    @Test
    public void givenRequestCancellation_WhenCancellingRequest_ShouldCallPendingRequest() throws InvalidFormatException {
        requestCancellation.cancelRequestByUUID(AN_UUID);

        verify(pendingRequestsMock).cancelPendingRequest(AN_UUID, requestRepositoryMock, notificationFactoryMock);
    }

    @Test
    public void givenRequestCancellation_WhenCancellingRequestTreated_ShouldCancelRequestAndPersistIt() throws InvalidFormatException, RequestNotFoundException {
        Mockito.doThrow(ObjectNotFoundException.class).when(pendingRequestsMock).cancelPendingRequest(AN_UUID, requestRepositoryMock, notificationFactoryMock);
        when(requestRepositoryMock.findByUUID(AN_UUID)).thenReturn(requestMock);

        requestCancellation.cancelRequestByUUID(AN_UUID);

        verify(requestMock).cancel();
        verify(requestRepositoryMock).persist(requestMock);
    }

    @Test (expected = ObjectNotFoundException.class)
    public void givenRequestCancellation_WhenCancellingInvalidRequest_ShouldThrowObjectNotFoundException() throws InvalidFormatException, RequestNotFoundException {
        Mockito.doThrow(ObjectNotFoundException.class).when(pendingRequestsMock).cancelPendingRequest(AN_UUID, requestRepositoryMock, notificationFactoryMock);
        Mockito.doThrow(ObjectNotFoundException.class).when(requestRepositoryMock).findByUUID(AN_UUID);

        this.requestCancellation.cancelRequestByUUID(AN_UUID);
    }

    @Test
    public void givenRequestCancellation_WhenCancelling_ThenShouldAnnounce() throws InvalidFormatException, RequestNotFoundException {
        Mockito.doThrow(ObjectNotFoundException.class).when(pendingRequestsMock).cancelPendingRequest(AN_UUID, requestRepositoryMock, notificationFactoryMock);
        when(requestRepositoryMock.findByUUID(AN_UUID)).thenReturn(requestMock);

        requestCancellation.cancelRequestByUUID(AN_UUID);

        verify(notificationMock).announce();
    }
}
