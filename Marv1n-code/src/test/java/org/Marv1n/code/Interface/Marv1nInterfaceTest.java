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
    private Marv1nInterface marv1nInterface;
    @Mock
    private StrategyRequestCancellationFactory requestCancellationStrategyFactoryMock;
    @Mock
    private PendingRequests pendingRequestsMock;
    @Mock
    private IRequestRepository requestRepositoryMock;
    @Mock
    private IReservationRepository reservationRepositoryMock;
    @Mock
    private IPersonRepository personRepositoryMock;

    public void initWithReservationRepository() {
        marv1nInterface = new Marv1nInterface(requestRepositoryMock, reservationRepositoryMock, personRepositoryMock, pendingRequestsMock);
    }

    public void initWithStrategyRequestCancellationFactory() {
        marv1nInterface = new Marv1nInterface(requestRepositoryMock, personRepositoryMock, requestCancellationStrategyFactoryMock, pendingRequestsMock);
    }

    @Test
    public void givenMarv1nInterface_WhenCreateNewRequest_ThenRequestRepositoryShouldBeCalledWithCreate() {
        initWithReservationRepository();
        Person person = mock(Person.class);
        when(personRepositoryMock.findByEmail(AN_EMAIL)).thenReturn(Optional.of(person));

        marv1nInterface.createRequest(A_NUMBER_OF_SEATS, A_PRIORITY, AN_EMAIL);

        verify(requestRepositoryMock).create(any(Request.class));
    }

    @Test
    public void givenMarv1nInterface_WhenCreateNewRequest_ThenPendingRequestShouldBeCalledWithAddRequest() {
        initWithReservationRepository();
        Person person = mock(Person.class);
        when(personRepositoryMock.findByEmail(AN_EMAIL)).thenReturn(Optional.of(person));

        marv1nInterface.createRequest(A_NUMBER_OF_SEATS, A_PRIORITY, AN_EMAIL);

        verify(pendingRequestsMock).addRequest(any(Request.class));
    }

    @Test
    public void givenMarv1nInterfaceWithEmptyPersonRepository_WhenAddNewRequest_ThenPersonShouldBeCreated() {
        initWithReservationRepository();
        when(personRepositoryMock.findByEmail(AN_EMAIL)).thenReturn(Optional.empty());

        marv1nInterface.createRequest(A_NUMBER_OF_SEATS, A_PRIORITY, AN_EMAIL);

        verify(personRepositoryMock).create(any(Person.class));
    }

    @Test
    public void givenMarv1nInterface_WhenCreateNewRequestWithInvalidEmailAddressFormat_ThenPersonShouldBeCreated() {
        initWithReservationRepository();
        when(personRepositoryMock.findByEmail(AN_EMAIL)).thenReturn(Optional.empty());

        marv1nInterface.createRequest(A_NUMBER_OF_SEATS, A_PRIORITY, AN_INVALID_EMAIL);

        verify(requestRepositoryMock, never()).create(any(Request.class));
    }

    @Test
    public void givenMarv1nInterface_WhenCancelRequestWithExistingRequest_ThenRequestIsCancelledByCallingTheCancelStrategy() {
        initWithStrategyRequestCancellationFactory();
        UUID id = UUID.randomUUID();
        Request requestMock = mock(Request.class);
        when(requestRepositoryMock.findByUUID(id)).thenReturn(Optional.of(requestMock));
        IStrategyRequestCancellation strategyRequestCancellationMock = mock(IStrategyRequestCancellation.class);
        when(requestCancellationStrategyFactoryMock.createStrategyCancellation(any())).thenReturn(strategyRequestCancellationMock);

        marv1nInterface.cancelRequest(id);

        verify(strategyRequestCancellationMock).cancelRequest(requestMock);
    }

    @Test
    public void givenMarv1nInterface_WhenCancelRequestWithNonExistingRequest_ThenNothingIsDone() {
        initWithStrategyRequestCancellationFactory();
        UUID id = UUID.randomUUID();
        when(requestRepositoryMock.findByUUID(id)).thenReturn(Optional.empty());
        IStrategyRequestCancellation strategyRequestCancellationMock = mock(IStrategyRequestCancellation.class);

        marv1nInterface.cancelRequest(id);

        verify(strategyRequestCancellationMock, never()).cancelRequest(any(Request.class));
    }
}