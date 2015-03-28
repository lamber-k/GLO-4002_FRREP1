package core;

import core.persistence.RoomRepository;
import core.request.Request;
import core.reservation.IReservationFactory;
import core.request.evaluation.EvaluationStrategy;
import core.request.evaluation.ReservableEvaluationResult;
import core.persistence.RequestRepository;
import core.reservation.Reservation;
import core.persistence.ReservationRepository;
import core.request.sorting.SortingRequestStrategy;
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

    private List<Request> arrayWithOneRequest;
    private List<Request> pendingRequests;
    private RequestTreatment requestTreatment;
    @Mock
    private EvaluationStrategy assignerStrategyMock;
    @Mock
    private ReservationRepository reservationsRepositoryMock;
    @Mock
    private IReservationFactory reservationFactoryMock;
    @Mock
    private RoomRepository reservablesRepositoryMock;
    @Mock
    private SortingRequestStrategy requestSortedStrategyMock;
    @Mock
    private Request requestMock;
    @Mock
    private ReservableEvaluationResult evaluationResultMock;
    @Mock
    private RequestRepository requestRepositoryMock;
    @Mock
    private RequestStatusUpdater requestStatusUpdaterMock;

    @Before
    public void initializeRequestTreatment() {
        arrayWithOneRequest = new ArrayList<>();
        arrayWithOneRequest.add(requestMock);
        pendingRequests = new ArrayList<>();
        when(requestRepositoryMock.findAllPendingRequest()).thenReturn(pendingRequests);
        requestTreatment = new RequestTreatment(assignerStrategyMock, requestSortedStrategyMock, reservablesRepositoryMock, reservationFactoryMock, reservationsRepositoryMock, requestRepositoryMock, requestStatusUpdaterMock);
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
    public void givenOnePendingRequest_WhenRun_ThenShouldEvaluateIt() {
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

        verify(reservationsRepositoryMock).persist(aReservation);
    }
}
