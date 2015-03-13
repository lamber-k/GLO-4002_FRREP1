package org.Marv1n.code;

/**
 * Created by Kevin on 13/03/2015.
 */
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
