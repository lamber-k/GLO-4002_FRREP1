package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Repository.IReservationRepository;
import org.Marv1n.code.Reservation.IReservationFactory;
import org.Marv1n.code.StrategyEvaluation.IStrategyEvaluation;
import org.Marv1n.code.StrategySortRequest.IStrategySortRequest;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 23/02/2015.
 */
public class RequestTreatmentTest {

    @Mock
    private Request aRequest;

    @Mock
    private IStrategyEvaluation assigner;

    @Mock
    private IStrategySortRequest requestSorter;

    @Mock
    private IReservationRepository reservations;

    @Mock
    private IReservationFactory reservationFactory;

    @Mock
    private IReservableRepository reservables;


    private ArrayList<Request> arrayWithOneRequest;
    private List<Request> pendingRequests;
    private RequestTreatment requestTreatment;

    @Before
    public void initializeRequestTreatment() {
        requestTreatment = new RequestTreatment(assigner, requestSorter, reservables, reservationFactory, reservations, pendingRequests);
        arrayWithOneRequest = new ArrayList<>();
        pendingRequests = new ArrayList<>();
        arrayWithOneRequest.add(aRequest);
    }

    @Test
    public void givenPendingRequest_whenRun_ShouldSortIt() {
        requestTreatment.run();
        verify(requestSorter).sortList(pendingRequests);
    }

    @Test
    public void givenOnePendingRequest_whenRun_ShouldEvaluateIt() {
        when(requestSorter.sortList(pendingRequests)).thenReturn(arrayWithOneRequest);
        requestTreatment.run();
        verify(assigner).evaluateOneRequest(reservables, aRequest);
    }

}
