package org.Marv1n.code;

import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.StrategyAssignation.StrategyAssignation;
import org.Marv1n.code.StrategySortRequest.StrategySortRequest;

import java.util.ArrayList;
import java.util.List;

public class Organizer implements Runnable {

    private List<Request> pendingRequest;
    private List<Reservable> reservables;
    private TaskScheduler taskScheduler;
    private Integer maximumPendingRequests;
    private StrategyAssignation assigner;
    private StrategySortRequest requestSorter;

    public void initialize(TaskScheduler scheduler, Integer maximumPendingRequests, StrategyAssignation strategyAssignation, StrategySortRequest strategySortRequest) {
        this.pendingRequest = new ArrayList<>();
        this.reservables = new ArrayList<>();
        this.taskScheduler = scheduler;
        this.maximumPendingRequests = maximumPendingRequests;
        this.assigner = strategyAssignation;
        this.requestSorter = strategySortRequest;
    }

    public Boolean hasReservable() {
        return !this.reservables.isEmpty();
    }

    public void addReservable(Reservable reservable) {
        this.reservables.add(reservable);
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

