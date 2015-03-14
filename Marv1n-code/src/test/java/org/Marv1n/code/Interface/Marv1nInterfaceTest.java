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

    private static final int NUMBER_OF_SEATS = 5;
    private static final int PRIORITY = 1;
    private static final String EMAIL = "exemple@exemple.com";
    private static final String INVALID_EMAIL = "invalidEmail";
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

    public void initializeWithReservationRepository() {
        marv1nInterface = new Marv1nInterface(requestRepositoryMock, reservationRepositoryMock, personRepositoryMock, pendingRequestsMock);
    }

    public void initializeWithStrategyRequestCancellationFactory() {
        marv1nInterface = new Marv1nInterface(requestRepositoryMock, personRepositoryMock, requestCancellationStrategyFactoryMock, pendingRequestsMock);
    }

    @Test
    public void givenMarv1nInterface_WhenCreateNewRequest_ThenRequestRepositoryShouldBeCalledWithCreate() {
        initializeWithReservationRepository();
        Person person = mock(Person.class);
        when(personRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.of(person));

        marv1nInterface.createRequest(NUMBER_OF_SEATS, PRIORITY, EMAIL);

        verify(requestRepositoryMock).create(any(Request.class));
    }

    @Test
    public void givenMarv1nInterface_WhenCreateNewRequest_ThenPendingRequestShouldBeCalledWithAddRequest() {
        initializeWithReservationRepository();
        Person person = mock(Person.class);
        when(personRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.of(person));

        marv1nInterface.createRequest(NUMBER_OF_SEATS, PRIORITY, EMAIL);

        verify(pendingRequestsMock).addRequest(any(Request.class));
    }

    @Test
    public void givenMarv1nInterfaceWithEmptyPersonRepository_WhenAddNewRequest_ThenPersonShouldBeCreated() {
        initializeWithReservationRepository();
        when(personRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.empty());

        marv1nInterface.createRequest(NUMBER_OF_SEATS, PRIORITY, EMAIL);

        verify(personRepositoryMock).create(any(Person.class));
    }

    @Test
    public void givenMarv1nInterface_WhenCreateNewRequestWithInvalidEmailAddressFormat_ThenPersonShouldBeCreated() {
        initializeWithReservationRepository();
        when(personRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.empty());

        marv1nInterface.createRequest(NUMBER_OF_SEATS, PRIORITY, INVALID_EMAIL);

        verify(requestRepositoryMock, never()).create(any(Request.class));
    }

    @Test
    public void givenMarv1nInterface_WhenCancelRequestWithExistingRequest_ThenRequestIsCancelledByCallingTheCancelStrategy() {
        initializeWithStrategyRequestCancellationFactory();
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
        initializeWithStrategyRequestCancellationFactory();
        UUID id = UUID.randomUUID();
        when(requestRepositoryMock.findByUUID(id)).thenReturn(Optional.empty());
        IStrategyRequestCancellation strategyRequestCancellationMock = mock(IStrategyRequestCancellation.class);

        marv1nInterface.cancelRequest(id);

        verify(strategyRequestCancellationMock, never()).cancelRequest(any(Request.class));
    }
}