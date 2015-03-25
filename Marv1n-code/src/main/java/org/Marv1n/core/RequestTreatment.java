package org.Marv1n.core;

import org.Marv1n.core.EvaluationStrategy.EvaluationStrategy;
import org.Marv1n.core.EvaluationStrategy.ReservableEvaluationResult;
import org.Marv1n.core.Request.Request;
import org.Marv1n.core.Request.RequestRepository;
import org.Marv1n.core.Request.RequestStatus;
import org.Marv1n.core.Reservation.IReservationFactory;
import org.Marv1n.core.Reservation.Reservation;
import org.Marv1n.core.Reservation.ReservationRepository;
import org.Marv1n.core.Room.RoomRepository;
import org.Marv1n.core.SortingRequestStrategy.SortingRequestStrategy;

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
            ReservableEvaluationResult evaluationResult = assigner.evaluateOneRequest(reservables, reservations, pendingRequest);
            Optional<Reservation> reservation = reservationFactory.reserve(pendingRequest, evaluationResult);
            if (reservation.isPresent()) {
                reservations.create(reservation.get());
                requestStatusUpdater.updateRequest(pendingRequest, RequestStatus.ACCEPTED);
            } else {
                requestIterator.remove();
            }
        }
        pendingRequests.removeAll(sortedRequests);
    }
}
