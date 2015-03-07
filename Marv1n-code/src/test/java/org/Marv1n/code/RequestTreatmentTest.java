package org.Marv1n.code;

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

    private ArrayList<Request> arrayWithOneRequest;
    private List<Request> pendingRequests;
    private RequestTreatment requestTreatment;

    @Before
    public void initializeRequestTreatment() {
        this.arrayWithOneRequest = new ArrayList<>();
        this.pendingRequests = new ArrayList<>();

        this.arrayWithOneRequest.add(this.aRequest);

        this.requestTreatment = new RequestTreatment(this.assigner, this.requestSorter, this.reservables, this.reservationFactory, this.reservations, this.pendingRequests);
    }

    @Test
    public void givenPendingRequest_whenRun_ShouldSortIt() {
        this.requestTreatment.run();

        verify(this.requestSorter).sortList(this.pendingRequests);
    }

    private void havingOnePendingRequest() {
        when(this.assigner.evaluateOneRequest(this.reservables, this.reservations, this.aRequest)).thenReturn(this.evaluationResult);
        when(this.requestSorter.sortList(this.pendingRequests)).thenReturn(this.arrayWithOneRequest);
    }


    @Test
    public void givenOnePendingRequest_whenRun_ShouldEvaluateIt() {
        havingOnePendingRequest();
        Optional<Reservation> emptyOptional = Optional.empty();
        when(this.reservationFactory.reserve(this.aRequest, this.evaluationResult)).thenReturn(emptyOptional);

        this.requestTreatment.run();

        verify(this.assigner).evaluateOneRequest(this.reservables, this.reservations, this.aRequest);
    }

    @Test
    public void givenOnePendingRequest_whenReserveSuccess_ShouldConfirmReservation() {
        Reservation aReservation = mock(Reservation.class);
        havingOnePendingRequest();
        Optional<Reservation> emptyOptional = Optional.of(aReservation);
        when(this.reservationFactory.reserve(this.aRequest, this.evaluationResult)).thenReturn(emptyOptional);

        this.requestTreatment.run();

        verify(this.reservations).create(aReservation);
    }

}
