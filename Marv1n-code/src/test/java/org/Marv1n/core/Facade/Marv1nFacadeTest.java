package org.Marv1n.core.Facade;

import org.Marv1n.core.notification.NotificationFactory;
import org.Marv1n.core.PendingRequests;
import org.Marv1n.core.person.Person;
import org.Marv1n.core.person.PersonRepository;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.request.RequestRepository;
import org.Marv1n.core.request.RequestStatus;
import org.Marv1n.core.RequestStatusUpdater;
import org.Marv1n.core.reservation.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Marv1nFacadeTest {

    private static final int NUMBER_OF_SEATS = 5;
    private static final int PRIORITY = 1;
    private static final String EMAIL = "exemple@exemple.com";
    private static final String INVALID_EMAIL = "invalidEmail";
    private static final UUID A_REQUEST_ID = UUID.randomUUID();
    private Marv1nFacade marv1nFacade;
    @Mock
    private Request requestMock;
    @Mock
    private PendingRequests pendingRequestsMock;
    @Mock
    private RequestRepository requestRepositoryMock;
    @Mock
    private ReservationRepository reservationRepositoryMock;
    @Mock
    private PersonRepository personRepositoryMock;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private NotificationFactory notificationFactory;
    @Mock
    private RequestStatusUpdater requestStatusUpdaterMock;

    @Before
    public void initializeMarv1nFacade() {
        marv1nFacade = new Marv1nFacade(requestRepositoryMock, personRepositoryMock, pendingRequestsMock, requestStatusUpdaterMock);
    }

    @Test
    public void givenMarv1nFacade_WhenCreateNewRequest_ThenPendingRequestShouldBeCalledWithAddRequest() {
        Person person = mock(Person.class);
        when(personRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.of(person));

        marv1nFacade.createRequest(NUMBER_OF_SEATS, PRIORITY, EMAIL);

        verify(pendingRequestsMock).addRequest(any(Request.class));
    }

    @Test
    public void givenMarv1nFacadeWithEmptyPersonRepository_WhenAddNewRequest_ThenPersonShouldBeCreated() {
        when(personRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.empty());
        marv1nFacade.createRequest(NUMBER_OF_SEATS, PRIORITY, EMAIL);
        verify(personRepositoryMock).create(any(Person.class));
    }

    @Test
    public void givenMarv1nFacade_WhenCreateNewRequestWithInvalidEmailAddressFormat_ThenPersonShouldNotBeCreated() {
        when(personRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.empty());
        marv1nFacade.createRequest(NUMBER_OF_SEATS, PRIORITY, INVALID_EMAIL);
        verify(requestRepositoryMock, never()).create(any(Request.class));
    }

    @Test
    public void givenMarv1nFacade_WhenCancelExistingRequest_ThenUpdateRequestToCancelled() {
        when(requestRepositoryMock.findByUUID(A_REQUEST_ID)).thenReturn(Optional.of(requestMock));
        marv1nFacade.cancelRequest(A_REQUEST_ID);
        verify(requestStatusUpdaterMock).updateRequest(requestMock, RequestStatus.CANCELED);
    }

    @Test
    public void givenMarv1nFacade_WhenCancelNonExistingRequest_ThenShouldDoNothing() {
        when(requestRepositoryMock.findByUUID(A_REQUEST_ID)).thenReturn(Optional.empty());
        marv1nFacade.cancelRequest(A_REQUEST_ID);
        verify(requestStatusUpdaterMock, never()).updateRequest(requestMock, RequestStatus.CANCELED);
    }
}