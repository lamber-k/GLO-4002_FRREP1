package org.Marv1n.code.Interface;

import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Person.IPersonRepository;
import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.StrategyRequestCancellation.IStrategyRequestCancellation;
import org.Marv1n.code.StrategyRequestCancellation.StrategyRequestCancellationFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Marv1nInterfaceTest {

    private static int A_NUMBER_OF_SEATS = 5;
    private static int A_PRIORITY = 1;
    private static String AN_EMAIL = "exemple@exemple.com";
    private static String AN_INVALID_EMAIL = "invalidEmail";
    private Marv1nInterface marv1NInterface;
    @Mock
    private StrategyRequestCancellationFactory strategyRequestCancellationFactoryMock;
    @Mock
    private PendingRequests pendingRequestsMock;
    @Mock
    private IRequestRepository requestRepositoryMock;
    @Mock
    private IReservationRepository reservationRepositoryMock;
    @Mock
    private IPersonRepository personRepositoryMock;

    public void initWithReservationRepository() {
        marv1NInterface = new Marv1nInterface(requestRepositoryMock, reservationRepositoryMock, personRepositoryMock, pendingRequestsMock);
    }

    public void initWithStrategyRequestCancellationFactory() {
        marv1NInterface = new Marv1nInterface(requestRepositoryMock, personRepositoryMock, strategyRequestCancellationFactoryMock, pendingRequestsMock);
    }

    @Test
    public void givenMarv1nInterface_whenAddNewRequest_thenRequestRepositoryShouldBeCalledWithCreate() {
        initWithReservationRepository();
        Person aPerson = mock(Person.class);
        when(personRepositoryMock.findByEmail(AN_EMAIL)).thenReturn(Optional.of(aPerson));
        when(aPerson.getID()).thenReturn(UUID.randomUUID());
        marv1NInterface.createRequest(A_NUMBER_OF_SEATS, A_PRIORITY, AN_EMAIL);

        verify(requestRepositoryMock, times(1)).create(any(Request.class));
    }

    @Test
    public void givenMarv1nInterface_whenAddNewRequest_thenPendingRequestShouldBeCalledWithAddRequest() {
        initWithReservationRepository();
        Person aPerson = mock(Person.class);
        when(personRepositoryMock.findByEmail(AN_EMAIL)).thenReturn(Optional.of(aPerson));
        when(aPerson.getID()).thenReturn(UUID.randomUUID());
        marv1NInterface.createRequest(A_NUMBER_OF_SEATS, A_PRIORITY, AN_EMAIL);

        verify(pendingRequestsMock, times(1)).addRequest(any(Request.class));
    }

    @Test
    public void givenMarv1nInterfaceWithEmptyPersonRepository_whenAddNewRequest_thenPersonShouldBeCreated() {
        initWithReservationRepository();
        when(personRepositoryMock.findByEmail(AN_EMAIL)).thenReturn(Optional.empty());
        marv1NInterface.createRequest(A_NUMBER_OF_SEATS, A_PRIORITY, AN_EMAIL);

        verify(personRepositoryMock, times(1)).create(any(Person.class));
    }

    @Test
    public void givenMarv1nInterface_whenAddNewRequestWithInvalidEmailAddressFormat_thenPersonShouldBeCreated() {
        initWithReservationRepository();
        when(personRepositoryMock.findByEmail(AN_EMAIL)).thenReturn(Optional.empty());
        marv1NInterface.createRequest(A_NUMBER_OF_SEATS, A_PRIORITY, AN_INVALID_EMAIL);

        verify(requestRepositoryMock, never()).create(any(Request.class));
    }

    @Test
    public void givenMarv1nInterface_whenCancelRequestWithExistingRequest_thenRequestIsCancelledByCallingTheCancelScraggy() {
        initWithStrategyRequestCancellationFactory();
        UUID id = UUID.randomUUID();
        Request requestMock = mock(Request.class);
        when(requestRepositoryMock.findByUUID(id)).thenReturn(Optional.of(requestMock));
        IStrategyRequestCancellation strategyRequestCancellationMock = mock(IStrategyRequestCancellation.class);
        when(strategyRequestCancellationFactoryMock.createStrategyCancellation(any())).thenReturn(strategyRequestCancellationMock);

        marv1NInterface.cancelRequest(id);

        verify(strategyRequestCancellationMock, times(1)).cancelRequest(requestMock);
    }

    @Test
    public void givenMarv1nInterface_whenCancelRequestWithNonExistingRequest_thenNothingIsDone() {
        initWithStrategyRequestCancellationFactory();
        UUID id = UUID.randomUUID();
        when(requestRepositoryMock.findByUUID(id)).thenReturn(Optional.empty());
        IStrategyRequestCancellation strategyRequestCancellationMock = mock(IStrategyRequestCancellation.class);
        when(strategyRequestCancellationFactoryMock.createStrategyCancellation(any())).thenReturn(strategyRequestCancellationMock);

        marv1NInterface.cancelRequest(id);

        verify(strategyRequestCancellationMock, never()).cancelRequest(any(Request.class));
    }


}