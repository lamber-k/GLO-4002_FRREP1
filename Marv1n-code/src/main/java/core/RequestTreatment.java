package core;

import core.persistence.RequestRepository;
import core.persistence.ReservationRepository;
import core.persistence.RoomRepository;
import core.request.Request;
import core.request.RequestStatus;
import core.request.evaluation.EvaluationStrategy;
import core.request.evaluation.ReservableEvaluationResult;
import core.request.sorting.SortingRequestStrategy;
import core.reservation.IReservationFactory;
import core.reservation.Reservation;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class RequestTreatment extends RunnableRequestTreatment {

    private EvaluationStrategy evaluationStrategy;
    private SortingRequestStrategy sortingRequestStrategy;
    private ReservationRepository reservationRepository;
    private IReservationFactory reservationFactory;
    private RoomRepository roomRepository;
    private RequestRepository requestRepository;
    private RequestStatusUpdater requestStatusUpdater;

    RequestTreatment(EvaluationStrategy evaluationStrategy, SortingRequestStrategy sortingRequestStrategy, RoomRepository roomRepository, IReservationFactory reservationFactory, ReservationRepository reservationRepository, RequestRepository requestRepository, RequestStatusUpdater requestStatusUpdater) {
        this.roomRepository = roomRepository;
        this.evaluationStrategy = evaluationStrategy;
        this.sortingRequestStrategy = sortingRequestStrategy;
        this.reservationFactory = reservationFactory;
        this.reservationRepository = reservationRepository;
        this.requestRepository = requestRepository;
        this.requestStatusUpdater = requestStatusUpdater;
    }

    @Override
    protected void treatPendingRequest() {
        List<Request> pendingRequests = requestRepository.findAllPendingRequest();
        List<Request> sortedRequests = sortingRequestStrategy.sortList(pendingRequests);
        Iterator<Request> requestIterator = sortedRequests.iterator();
        while (requestIterator.hasNext()) {
            Request pendingRequest = requestIterator.next();
            ReservableEvaluationResult evaluationResult = evaluationStrategy.evaluateOneRequest(roomRepository, reservationRepository, pendingRequest);
            Optional<Reservation> reservation = reservationFactory.reserve(pendingRequest, evaluationResult);
            if (reservation.isPresent()) {
                reservationRepository.persist(reservation.get());
                requestStatusUpdater.updateRequest(pendingRequest, RequestStatus.ACCEPTED);
            } else {
                requestIterator.remove();
            }
        }
        pendingRequests.removeAll(sortedRequests);
    }
}
