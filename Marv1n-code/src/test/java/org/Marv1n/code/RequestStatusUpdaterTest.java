package org.Marv1n.code;

import org.Marv1n.code.Notification.Notification;
import org.Marv1n.code.Notification.NotificationFactory;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestStatusUpdaterTest {

    private static final int A_NUMBER_OF_SEATS = 2;
    private static final int A_PRIORITY = 4;
    private static final java.util.UUID A_UUID = UUID.randomUUID();
    private static final RequestStatus A_STATUS = RequestStatus.REFUSED;
    private static final RequestStatus ANOTHER_STATUS = RequestStatus.PENDING;
    private RequestStatusUpdater requestStatusUpdater;
    @Mock
    private RequestRepository pendingRequestsMock;
    @Mock
    private ReservationRepository reservationRepositoryMock;
    @Mock
    private NotificationFactory notificationFactoryMock;

    @Before
    public void initializeNewRequestStatusUpdater() throws Exception {
        requestStatusUpdater = new RequestStatusUpdater(pendingRequestsMock, reservationRepositoryMock, notificationFactoryMock);
    }

    @Test
    public void givenRequest_WhenRequestingAnUpdate_ThenRepositoryShouldBeChanged() throws Exception {
        Request request = new Request(A_NUMBER_OF_SEATS, A_PRIORITY, A_UUID, A_STATUS);

        requestStatusUpdater.updateRequest(request, ANOTHER_STATUS);

        verify(pendingRequestsMock).remove(request);
        class DoesRequestHaveTheProperStatus extends ArgumentMatcher {
            public boolean matches(Object request) {
                return ((Request) request).getRequestStatus() == ANOTHER_STATUS;
            }
        }
        verify(pendingRequestsMock).create(Matchers.<Request>argThat(new DoesRequestHaveTheProperStatus()));
    }

    @Test
    public void givenRequest_WhenRequestingAnUpdate_ThenNotificationShouldBeSent() throws Exception {
        Notification notificationMock = mock(Notification.class);
        Request request = new Request(A_NUMBER_OF_SEATS, A_PRIORITY, A_UUID, ANOTHER_STATUS);
        when(notificationFactoryMock.createNotification(request)).thenReturn(notificationMock);

        requestStatusUpdater.updateRequest(request, A_STATUS);

        verify(notificationMock).announce();
    }

    @Test
    public void givenAcceptedRequest_WhenCancellingRequest_ThenRepositoryShouldBeChanged() throws Exception {
        Notification notificationMock = mock(Notification.class);
        Request request = new Request(A_NUMBER_OF_SEATS, A_PRIORITY, A_UUID, RequestStatus.ACCEPTED);
        when(notificationFactoryMock.createNotification(request)).thenReturn(notificationMock);
        Reservation reservationMock = mock(Reservation.class);
        when(reservationRepositoryMock.findReservationByRequest(request)).thenReturn(reservationMock);

        requestStatusUpdater.updateRequest(request, RequestStatus.CANCELED);

        verify(reservationRepositoryMock).remove(reservationMock);
    }
}
