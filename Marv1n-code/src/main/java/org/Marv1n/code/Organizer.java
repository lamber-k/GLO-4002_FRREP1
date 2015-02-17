package org.Marv1n.code;

<<<<<<< HEAD
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.StrategyAssignation.IStrategyAssignation;
import org.Marv1n.code.StrategySortRequest.IStrategySortRequest;
=======
import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Repository.ReservableRepository;
import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.StrategyAssignation.StrategyAssignation;
import org.Marv1n.code.StrategySortRequest.StrategySortRequest;
>>>>>>> Reservation System. Accepted ?

import java.util.ArrayList;
import java.util.List;

public class Organizer implements Runnable {

    private List<Request> pendingRequest;
<<<<<<< HEAD
    private List<IReservable> IReservables;
=======
    private IReservableRepository reservables;
>>>>>>> Reservation System. Accepted ?
    private TaskScheduler taskScheduler;
    private Integer maximumPendingRequests;
    private IStrategyAssignation assigner;
    private IStrategySortRequest requestSorter;

<<<<<<< HEAD
    public void initialize(TaskScheduler scheduler, Integer maximumPendingRequests, IStrategyAssignation IStrategyAssignation, IStrategySortRequest IStrategySortRequest) {
        this.pendingRequest = new ArrayList<>();
        this.IReservables = new ArrayList<>();
=======
    public Organizer(IReservableRepository reservables)
    {
        this.reservables = reservables;
    }

    public void initialize(TaskScheduler scheduler, Integer maximumPendingRequests, StrategyAssignation strategyAssignation, StrategySortRequest strategySortRequest) {
        this.pendingRequest = new ArrayList<>();
>>>>>>> Reservation System. Accepted ?
        this.taskScheduler = scheduler;
        this.maximumPendingRequests = maximumPendingRequests;
        this.assigner = IStrategyAssignation;
        this.requestSorter = IStrategySortRequest;
    }

    public Boolean hasReservable() {
<<<<<<< HEAD
        return !this.IReservables.isEmpty();
    }

    public void addReservable(IReservable IReservable) {
        this.IReservables.add(IReservable);
=======
        return !this.reservables.findAll().isEmpty();
    }

    public void addReservable(Reservable reservable) {
        this.reservables.create(reservable);
>>>>>>> Reservation System. Accepted ?
    }

    public void addRequest(Request request) {
        this.pendingRequest.add(request);
        if (this.pendingRequest.size() >= this.maximumPendingRequests) {
            this.treatPendingRequestsNow();
        }
    }

    public boolean hasPendingRequest() {
        return !this.pendingRequest.isEmpty();
    }

    public void treatPendingRequestsNow() {
        this.taskScheduler.runNow(this);
    }

    public void treatPendingRequest() {
        this.requestSorter.sortList(this.pendingRequest);
        this.assigner.assignReservables(this.pendingRequest, this.IReservables);
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

