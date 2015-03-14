package org.Marv1n.code;

public class PendingRequestFullSchedulerNotify implements IObserverMaximumPendingRequestReached {

    private final IScheduler IScheduler;

    public PendingRequestFullSchedulerNotify(IScheduler IScheduler) {
        this.IScheduler = IScheduler;
    }

    @Override
    public void onMaximumPendingRequestReached() {
        IScheduler.restartSchedule();
    }
}
