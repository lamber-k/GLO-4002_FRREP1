package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Repository.IReservationRepository;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.IReservationFactory;
import org.Marv1n.code.Reservation.Reservation;
import org.Marv1n.code.StrategyEvaluation.IStrategyEvaluation;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;
import org.Marv1n.code.StrategySortRequest.IStrategySortRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kevin on 23/02/2015.
 */
public class RequestTreatment implements Runnable {

    private IStrategyEvaluation assigner;
    private IStrategySortRequest requestSorter;
    private IReservationRepository reservations;
    private IReservationFactory reservationFactory;
    private IReservableRepository reservables;
    private List<Request> pendingRequests;

    RequestTreatment(IStrategyEvaluation strategyAssignation, IStrategySortRequest strategySortRequest, IReservableRepository reservableRepository, IReservationFactory reservationFactory, IReservationRepository reservationRepository, List<Request> pendingRequests) {
        this.reservables = reservableRepository;
        this.assigner = strategyAssignation;
        this.requestSorter = strategySortRequest;
        this.reservationFactory = reservationFactory;
        this.reservations = reservationRepository;
        this.pendingRequests = pendingRequests;
    }

    private void treatPendingRequest() {
        ArrayList<Request> sortedRequests = requestSorter.sortList(pendingRequests);
        Iterator<Request> requestIterator = sortedRequests.iterator();

        while (requestIterator.hasNext()) {
            Request pendingRequest = requestIterator.next();

            ReservableEvaluationResult evaluationResult = assigner.evaluateOneRequest(reservables, pendingRequest);

            Optional<Reservation> reservation = reservationFactory.reserve(pendingRequest, evaluationResult);
            if (reservation.isPresent()) {
                reservations.create(reservation.get());
            }
            else {
                requestIterator.remove();
            }
        }
        pendingRequests.removeAll(sortedRequests);
    }

    @Override
    public void run() {
        treatPendingRequest();
    }
}
