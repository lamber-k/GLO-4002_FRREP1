package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Repository.IReservationRepository;
import org.Marv1n.code.Reservation.IReservationFactory;
import org.Marv1n.code.Reservation.Reservation;
import org.Marv1n.code.StrategyEvaluation.IStrategyEvaluation;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;
import org.Marv1n.code.StrategySortRequest.IStrategySortRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class RequestTreatmentTest {


    private IStrategyEvaluation assigner;
    private IReservationRepository reservations;
    private IReservationFactory reservationFactory;
    private IReservableRepository reservables;
    private IStrategySortRequest requestSorter;
    private Request aRequest;


    private ArrayList<Request> arrayWithOneRequest;
    private List<Request> pendingRequests;
    private RequestTreatment requestTreatment;

    @Before
    public void initializeRequestTreatment() {
        arrayWithOneRequest = new ArrayList<>();
        pendingRequests = new ArrayList<>();

        assigner = mock(IStrategyEvaluation.class);
        reservations = mock(IReservationRepository.class);
        reservationFactory = mock(IReservationFactory.class);
        reservables = mock(IReservableRepository.class);
        requestSorter = mock(IStrategySortRequest.class);
        aRequest = mock(Request.class);

        arrayWithOneRequest.add(aRequest);

        requestTreatment = new RequestTreatment(assigner, requestSorter, reservables, reservationFactory, reservations, pendingRequests);
    }

    @Test
    public void givenPendingRequest_whenRun_ShouldSortIt() {
        requestTreatment.run();

        verify(requestSorter).sortList(pendingRequests);
    }

    @Test
    public void givenOnePendingRequest_whenRun_ShouldEvaluateIt() {
        // Bloc is mandatory for the test to work. We clearly need refactoring.
        ReservableEvaluationResult evaluationResult = mock(ReservableEvaluationResult.class);
        Optional<Reservation> emptyOptional = Optional.empty();
        when(assigner.evaluateOneRequest(this.reservables, this.aRequest)).thenReturn(evaluationResult);
        when(requestSorter.sortList(pendingRequests)).thenReturn(arrayWithOneRequest);
        when(reservationFactory.reserve(this.aRequest, evaluationResult)).thenReturn(emptyOptional);

        requestTreatment.run();

        verify(assigner).evaluateOneRequest(reservables, aRequest);
    }

}
