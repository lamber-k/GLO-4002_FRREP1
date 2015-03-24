package org.Marv1n.code;

public class PendingRequestFullSchedulerNotifyObserver implements MaximumPendingRequestReachedObserver {

    private final Scheduler Scheduler;

    public PendingRequestFullSchedulerNotifyObserver(Scheduler Scheduler) {
        this.Scheduler = Scheduler;
    }

    @Override
    public void onMaximumPendingRequestReached() {
        Scheduler.runNow();
    }
}
