package org.Marv1n.core;

@FunctionalInterface
public interface MaximumPendingRequestReachedObserver {

    void onMaximumPendingRequestReached();
}
