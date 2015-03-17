package org.Marv1n.code.Facade;

import org.Marv1n.code.Notification.NotificationFactory;
import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Person.PersonRepository;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Marv1nFacadeTest {

    private static final int NUMBER_OF_SEATS = 5;
    private static final int PRIORITY = 1;
    private static final String EMAIL = "exemple@exemple.com";
    private static final String INVALID_EMAIL = "invalidEmail";
    private Marv1nFacade marv1NFacade;
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

    public void initializeWithReservationRepository() {
        marv1NFacade = new Marv1nFacade(requestRepositoryMock, personRepositoryMock, pendingRequestsMock, reservationRepository, notificationFactory);
    }

    @Test
    public void givenMarv1nInterface_WhenCreateNewRequest_ThenPendingRequestShouldBeCalledWithAddRequest() {
        initializeWithReservationRepository();
        Person person = mock(Person.class);
        when(personRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.of(person));

        marv1NFacade.createRequest(NUMBER_OF_SEATS, PRIORITY, EMAIL);

        verify(pendingRequestsMock).addRequest(any(Request.class));
    }

    @Test
    public void givenMarv1nInterfaceWithEmptyPersonRepository_WhenAddNewRequest_ThenPersonShouldBeCreated() {
        initializeWithReservationRepository();
        when(personRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.empty());

        marv1NFacade.createRequest(NUMBER_OF_SEATS, PRIORITY, EMAIL);

        verify(personRepositoryMock).create(any(Person.class));
    }

    @Test
    public void givenMarv1nInterface_WhenCreateNewRequestWithInvalidEmailAddressFormat_ThenPersonShouldBeCreated() {
        initializeWithReservationRepository();
        when(personRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.empty());

        marv1NFacade.createRequest(NUMBER_OF_SEATS, PRIORITY, INVALID_EMAIL);

        verify(requestRepositoryMock, never()).create(any(Request.class));
    }
}