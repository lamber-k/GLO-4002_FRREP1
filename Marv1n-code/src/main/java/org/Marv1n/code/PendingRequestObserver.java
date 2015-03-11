package org.Marv1n.code;

import java.util.concurrent.Callable;

/**
 * Created by Kevin on 11/03/2015.
 */
public interface PendingRequestObserver {
    void alert();
    void register(Callable<Void> registeredFunction);
}
