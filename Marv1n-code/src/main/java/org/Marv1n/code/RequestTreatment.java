package org.Marv1n.code;

import org.Marv1n.code.EvaluationStrategy.EvaluationStrategy;
import org.Marv1n.code.EvaluationStrategy.ReservableEvaluationResult;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Reservation.IReservationFactory;
import org.Marv1n.code.Reservation.Reservation;
import org.Marv1n.code.SortingRequestStrategy.SortingRequestStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class RequestTreatment extends RunnableRequestTreatment {

    private EvaluationStrategy assigner;
    private SortingRequestStrategy requestSorter;
    private ReservationRepository reservations;
    private IReservationFactory reservationFactory;
    private ReservableRepository reservables;
    private RequestRepository requests;
    private RequestStatusUpdater requestStatusUpdater;

    RequestTreatment(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, ReservableRepository reservableRepository, IReservationFactory reservationFactory, ReservationRepository reservationRepository, RequestRepository requestRepository, RequestStatusUpdater requestStatusUpdater) {
        this.reservables = reservableRepository;
        this.assigner = strategyAssignation;
        this.requestSorter = strategySortRequest;
        this.reservationFactory = reservationFactory;
        this.reservations = reservationRepository;
        this.requests = requestRepository;
        this.requestStatusUpdater = requestStatusUpdater;
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
                requestStatusUpdater.updateRequest(pendingRequest, RequestStatus.ACCEPTED);
            }
            else {
                requestIterator.remove();
            }
        }
        pendingRequests.removeAll(sortedRequests);
    }
}
