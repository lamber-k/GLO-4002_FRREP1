package org.Marv1n.code;

import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.StrategyAssignation.IStrategyAssignation;
import org.Marv1n.code.StrategySortRequest.IStrategySortRequest;

import java.util.ArrayList;
import java.util.List;

public class Organizer implements Runnable {

    private List<Request> pendingRequest;
    private List<IReservable> IReservables;
    private TaskScheduler taskScheduler;
    private Integer maximumPendingRequests;
    private IStrategyAssignation assigner;
    private IStrategySortRequest requestSorter;

    public void initialize(TaskScheduler scheduler, Integer maximumPendingRequests, IStrategyAssignation IStrategyAssignation, IStrategySortRequest IStrategySortRequest) {
        this.pendingRequest = new ArrayList<>();
        this.IReservables = new ArrayList<>();
        this.taskScheduler = scheduler;
        this.maximumPendingRequests = maximumPendingRequests;
        this.assigner = IStrategyAssignation;
        this.requestSorter = IStrategySortRequest;
    }

    public Boolean hasReservable() {
        return !this.IReservables.isEmpty();
    }

    public void addReservable(IReservable IReservable) {
        this.IReservables.add(IReservable);
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

