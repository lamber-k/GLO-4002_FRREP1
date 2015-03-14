package org.Marv1n.code;

import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Repository.Reservable.IReservableRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Reservation.IReservationFactory;
import org.Marv1n.code.Reservation.Reservation;
import org.Marv1n.code.StrategyEvaluation.IStrategyEvaluation;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;
import org.Marv1n.code.StrategySortRequest.IStrategySortRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestTreatmentTest {

    private ArrayList<Request> arrayWithOneRequest;
    private List<Request> pendingRequests;
    private RequestTreatment requestTreatment;
    @Mock
    private IStrategyEvaluation assignerStrategyMock;
    @Mock
    private IReservationRepository reservationsRepositoryMock;
    @Mock
    private IReservationFactory reservationFactoryMock;
    @Mock
    private IReservableRepository reservablesRepositoryMock;
    @Mock
    private IStrategySortRequest requestSortedStrategyMock;
    @Mock
    private Request requestMock;
    @Mock
    private ReservableEvaluationResult evaluationResultMock;
    @Mock
    private IRequestRepository requestRepositoryMock;

    @Before
    public void initializeRequestTreatment() {
        arrayWithOneRequest = new ArrayList<>();
        arrayWithOneRequest.add(requestMock);
        pendingRequests = new ArrayList<>();
        when(requestRepositoryMock.findAllPendingRequest()).thenReturn(pendingRequests);
        requestTreatment = new RequestTreatment(assignerStrategyMock, requestSortedStrategyMock, reservablesRepositoryMock, reservationFactoryMock, reservationsRepositoryMock, requestRepositoryMock);
    }

    @Test
    public void givenPendingRequest_WhenRun_ThenShouldSortIt() {
        requestTreatment.run();

        verify(requestSortedStrategyMock).sortList(pendingRequests);
    }

    private void havingOnePendingRequest() {
        when(assignerStrategyMock.evaluateOneRequest(reservablesRepositoryMock, reservationsRepositoryMock, requestMock)).thenReturn(evaluationResultMock);
        when(requestSortedStrategyMock.sortList(pendingRequests)).thenReturn(arrayWithOneRequest);
        pendingRequests.add(requestMock);
    }

    @Test
    public void givenOnePendingRequest_WhenRunTHenShouldEvaluateIt() {
        havingOnePendingRequest();
        Optional<Reservation> emptyOptional = Optional.empty();
        when(reservationFactoryMock.reserve(requestMock, evaluationResultMock)).thenReturn(emptyOptional);

        requestTreatment.run();

        verify(assignerStrategyMock).evaluateOneRequest(reservablesRepositoryMock, reservationsRepositoryMock, requestMock);
    }

    @Test
    public void givenOnePendingRequest_WhenReserveSuccess_ThenShouldConfirmReservation() {
        Reservation aReservation = mock(Reservation.class);
        havingOnePendingRequest();
        Optional<Reservation> emptyOptional = Optional.of(aReservation);
        when(reservationFactoryMock.reserve(requestMock, evaluationResultMock)).thenReturn(emptyOptional);

        requestTreatment.run();

        verify(reservationsRepositoryMock).create(aReservation);
    }
}
