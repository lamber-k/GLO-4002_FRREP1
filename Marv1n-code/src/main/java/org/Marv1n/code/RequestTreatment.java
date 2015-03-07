package org.Marv1n.code;

import org.Marv1n.code.Repository.Reservable.IReservableRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Reservation.IReservationFactory;
import org.Marv1n.code.Reservation.Reservation;
import org.Marv1n.code.StrategyEvaluation.IStrategyEvaluation;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;
import org.Marv1n.code.StrategySortRequest.IStrategySortRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
        ArrayList<Request> sortedRequests = this.requestSorter.sortList(this.pendingRequests);
        Iterator<Request> requestIterator = sortedRequests.iterator();

        while (requestIterator.hasNext()) {
            Request pendingRequest = requestIterator.next();

            ReservableEvaluationResult evaluationResult = this.assigner.evaluateOneRequest(this.reservables, this.reservations, pendingRequest);

            Optional<Reservation> reservation = this.reservationFactory.reserve(pendingRequest, evaluationResult);
            if (reservation.isPresent()) {
                this.reservations.create(reservation.get());
            } else {
                requestIterator.remove();
            }
        }
        this.pendingRequests.removeAll(sortedRequests);
    }

    @Override
    public void run() {
        treatPendingRequest();
    }
}
