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

    @Mock
    private IStrategyEvaluation assigner;
    @Mock
    private IReservationRepository reservations;
    @Mock
    private IReservationFactory reservationFactory;
    @Mock
    private IReservableRepository reservables;
    @Mock
    private IStrategySortRequest requestSorter;
    @Mock
    private Request aRequest;
    @Mock
    private ReservableEvaluationResult evaluationResult;
    @Mock
    private IRequestRepository requestRepository;

    private ArrayList<Request> arrayWithOneRequest;
    private List<Request> pendingRequests;
    private RequestTreatment requestTreatment;

    @Before
    public void initializeRequestTreatment() {
        arrayWithOneRequest = new ArrayList<>();
        pendingRequests = new ArrayList<>();

        arrayWithOneRequest.add(aRequest);
        when(requestRepository.findAllPendingRequest()).thenReturn(pendingRequests);

        requestTreatment = new RequestTreatment(assigner, requestSorter, reservables, reservationFactory, reservations, requestRepository);
    }

    @Test
    public void givenPendingRequest_whenRun_ShouldSortIt() {
        requestTreatment.run();

        verify(requestSorter).sortList(pendingRequests);
    }

    private void havingOnePendingRequest() {
        when(assigner.evaluateOneRequest(reservables, reservations, aRequest)).thenReturn(evaluationResult);
        when(requestSorter.sortList(pendingRequests)).thenReturn(arrayWithOneRequest);
        pendingRequests.add(aRequest);
    }


    @Test
    public void givenOnePendingRequest_whenRun_ShouldEvaluateIt() {
        havingOnePendingRequest();
        Optional<Reservation> emptyOptional = Optional.empty();
        when(reservationFactory.reserve(aRequest, evaluationResult)).thenReturn(emptyOptional);

        requestTreatment.run();

        verify(assigner).evaluateOneRequest(reservables, reservations, aRequest);
    }

    @Test
    public void givenOnePendingRequest_whenReserveSuccess_ShouldConfirmReservation() {
        Reservation aReservation = mock(Reservation.class);
        havingOnePendingRequest();
        Optional<Reservation> emptyOptional = Optional.of(aReservation);
        when(reservationFactory.reserve(aRequest, evaluationResult)).thenReturn(emptyOptional);

        requestTreatment.run();

        verify(reservations).create(aReservation);
    }

}
