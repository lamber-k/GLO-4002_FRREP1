package core;

public class PendingRequestFullSchedulerNotifyObserver implements MaximumPendingRequestReachedObserver {

    private final Scheduler scheduler;

    public PendingRequestFullSchedulerNotifyObserver(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void onMaximumPendingRequestReached() {
        scheduler.runNow();
    }
}
