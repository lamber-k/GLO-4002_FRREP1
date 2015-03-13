package org.Marv1n.code;

import org.Marv1n.code.Repository.Request.IRequestRepository;
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

public class RequestTreatment extends RunnableRequestTreatment {

    private IStrategyEvaluation assigner;
    private IStrategySortRequest requestSorter;
    private IReservationRepository reservations;
    private IReservationFactory reservationFactory;
    private IReservableRepository reservables;
    private IRequestRepository requests;

    RequestTreatment(IStrategyEvaluation strategyAssignation, IStrategySortRequest strategySortRequest, IReservableRepository reservableRepository, IReservationFactory reservationFactory, IReservationRepository reservationRepository, IRequestRepository requestRepository) {
        this.reservables = reservableRepository;
        this.assigner = strategyAssignation;
        this.requestSorter = strategySortRequest;
        this.reservationFactory = reservationFactory;
        this.reservations = reservationRepository;
        this.requests = requestRepository;
    }

    @Override
    protected void treatPendingRequest() {
        List<Request> pendingRequests = requests.findAllPendingRequest();
        ArrayList<Request> sortedRequests = requestSorter.sortList(pendingRequests);
        Iterator<Request> requestIterator = sortedRequests.iterator();

        while (requestIterator.hasNext()) {
            Request pendingRequest = requestIterator.next();

            ReservableEvaluationResult evaluationResult = assigner.evaluateOneRequest(reservables, reservations, pendingRequest);

            Optional<Reservation> reservation = reservationFactory.reserve(pendingRequest, evaluationResult);
            if (reservation.isPresent()) {
                reservations.create(reservation.get());
                updateAcceptedRequest(pendingRequest);
            } else {
                requestIterator.remove();
            }
        }
        pendingRequests.removeAll(sortedRequests);
    }

    private void updateAcceptedRequest(Request request) {
        //TODO would be better to use an update in repository
        requests.remove(request);
        request.setRequestStatus(RequestStatus.ACCEPTED);
        requests.create(request);
    }


}
