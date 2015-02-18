package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Repository.IReservationRepository;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.IReservationFactory;
import org.Marv1n.code.Reservation.Reservation;
import org.Marv1n.code.StrategyEvaluation.IStrategyEvaluation;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;
import org.Marv1n.code.StrategySortRequest.IStrategySortRequest;

import java.util.*;

public class Organizer implements Runnable {

    private List<Request> pendingRequests;
    private IReservableRepository reservables;
    private TaskScheduler taskScheduler;
    private Integer maximumPendingRequests;
    private IStrategyEvaluation assigner;
    private IStrategySortRequest requestSorter;
    private IReservationRepository reservations;
    private IReservationFactory reservationFactory;

    public void initialize(TaskScheduler scheduler, Integer maximumPendingRequests, IStrategyEvaluation strategyAssignation, IStrategySortRequest strategySortRequest, IReservableRepository reservableRepository, IReservationFactory reservationFactory, IReservationRepository reservationRepository) {
        this.pendingRequests = Collections.synchronizedList(new ArrayList<>());
        this.taskScheduler = scheduler;
        this.maximumPendingRequests = maximumPendingRequests;
        this.assigner = strategyAssignation;
        this.requestSorter = strategySortRequest;
        this.reservables = reservableRepository;
        this.reservationFactory = reservationFactory;
        this.reservations = reservationRepository;
    }

    public boolean hasReservable() {
        return !this.reservables.findAll().isEmpty();
    }

    public void addReservable(IReservable reservable) {
        this.reservables.create(reservable);
    }

    public void addRequest(Request request) {
        this.pendingRequests.add(request);
        if (this.pendingRequests.size() >= this.maximumPendingRequests) {
            this.treatPendingRequestsNow();
        }
    }

    public boolean hasPendingRequest() {
        return !this.pendingRequests.isEmpty();
    }

    public void treatPendingRequestsNow() {
        this.taskScheduler.runNow(this);
    }

    public void treatPendingRequest() {
        ArrayList<Request> sortedRequests = this.requestSorter.sortList(this.pendingRequests);
        Iterator<Request> it = sortedRequests.iterator();

        while (it.hasNext()) {
            Request pendingRequest = it.next();

            ReservableEvaluationResult evaluationResult = this.assigner.evaluateOneRequest(this.reservables, pendingRequest);

            Optional<Reservation> reservation = reservationFactory.reserve(pendingRequest, evaluationResult);
            if (reservation.isPresent()) {
                this.reservations.create(reservation.get());
            }
            else {
                it.remove();
            }
        }
        this.pendingRequests.removeAll(sortedRequests);
    }

    @Override
    public void run() {
        this.treatPendingRequest();
    }

    public Integer getMaximumPendingRequests() {
        return this.maximumPendingRequests;
    }

    public void setMaximumPendingRequests(Integer maximumPendingRequests) {
        this.maximumPendingRequests = maximumPendingRequests;
    }
}

