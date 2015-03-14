package org.Marv1n.code;

public class PendingRequestFullSchedulerNotify implements IObserverMaximumPendingRequestReached {

    private final Scheduler scheduler;

    public PendingRequestFullSchedulerNotify(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void onMaximumPendingRequestReached() {
        scheduler.restartSchedule();
    }
}
