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
import java.util.List;
import java.util.Optional;

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
        this.pendingRequests = new ArrayList<>();
        this.taskScheduler = scheduler;
        this.maximumPendingRequests = maximumPendingRequests;
        this.assigner = strategyAssignation;
        this.requestSorter = strategySortRequest;
        this.reservables = reservableRepository;
        this.reservationFactory = reservationFactory;
        this.reservations = reservationRepository;
    }

    public Boolean hasReservable() {
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
        this.requestSorter.sortList(this.pendingRequests);
        for (Request pendingRequest : this.pendingRequests) {
            ReservableEvaluationResult evaluationResult = this.assigner.evaluateOneRequest(this.reservables, pendingRequest);

            Optional<Reservation> reservation = reservationFactory.Reserve(pendingRequest, evaluationResult);
            if (reservation.isPresent())
                this.reservations.create(reservation.get());
        }
        this.pendingRequests.clear();
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

