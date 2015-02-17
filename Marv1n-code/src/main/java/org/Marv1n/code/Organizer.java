package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.StrategyAssignation.IStrategyAssignation;
import org.Marv1n.code.StrategySortRequest.IStrategySortRequest;

import java.util.ArrayList;
import java.util.List;

public class Organizer implements Runnable {

    private List<Request> pendingRequest;
    private IReservableRepository reservables;
    private TaskScheduler taskScheduler;
    private Integer maximumPendingRequests;
    private IStrategyAssignation assigner;
    private IStrategySortRequest requestSorter;

    public Organizer(IReservableRepository reservables)
    {
        this.reservables = reservables;
    }

    public void initialize(TaskScheduler scheduler, Integer maximumPendingRequests, IStrategyAssignation strategyAssignation, IStrategySortRequest strategySortRequest) {
        this.pendingRequest = new ArrayList<>();
        this.taskScheduler = scheduler;
        this.maximumPendingRequests = maximumPendingRequests;
        this.assigner = strategyAssignation;
        this.requestSorter = strategySortRequest;
    }

    public Boolean hasReservable() {
        return !this.reservables.findAll().isEmpty();
    }

    public void addReservable(IReservable reservable) {
        this.reservables.create(reservable);
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
        this.assigner.assignReservables(this.pendingRequest, this.reservables);
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

