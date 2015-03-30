package org.Marv1n.core;

import org.Marv1n.core.request.evaluation.EvaluationStrategy;
import org.Marv1n.core.request.evaluation.ReservableEvaluationResult;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.persistence.RequestRepository;
import org.Marv1n.core.request.RequestStatus;
import org.Marv1n.core.reservation.IReservationFactory;
import org.Marv1n.core.reservation.Reservation;
import org.Marv1n.core.persistence.ReservationRepository;
import org.Marv1n.core.persistence.RoomRepository;
import org.Marv1n.core.request.sorting.SortingRequestStrategy;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class RequestTreatment extends RunnableRequestTreatment {

    private EvaluationStrategy assigner;
    private SortingRequestStrategy requestSorter;
    private ReservationRepository reservations;
    private IReservationFactory reservationFactory;
    private RoomRepository reservables;
    private RequestRepository requests;
    private RequestStatusUpdater requestStatusUpdater;

    RequestTreatment(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository, IReservationFactory reservationFactory, ReservationRepository reservationRepository, RequestRepository requestRepository, RequestStatusUpdater requestStatusUpdater) {
        this.reservables = roomRepository;
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
        List<Request> sortedRequests = requestSorter.sortList(pendingRequests);
        Iterator<Request> requestIterator = sortedRequests.iterator();
        while (requestIterator.hasNext()) {
            Request pendingRequest = requestIterator.next();
            ReservableEvaluationResult evaluationResult = assigner.evaluateOneRequest(reservables, pendingRequest);
            Optional<Reservation> reservation = reservationFactory.reserve(pendingRequest, evaluationResult);
            if (reservation.isPresent()) {
                reservations.persist(reservation.get());
                requestStatusUpdater.updateRequest(pendingRequest, RequestStatus.ACCEPTED);
            } else {
                requestIterator.remove();
            }
        }
        pendingRequests.removeAll(sortedRequests);
    }
}
