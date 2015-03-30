package core;

@FunctionalInterface
public interface MaximumPendingRequestReachedObserver {

    void onMaximumPendingRequestReached();
}
