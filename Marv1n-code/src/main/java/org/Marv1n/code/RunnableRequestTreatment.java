package org.Marv1n.code;

/**
 * Created by Kevin on 11/03/2015.
 */
public abstract class RunnableRequestTreatment implements Runnable {

    protected abstract void treatPendingRequest();

    @Override
    public void run() {
        treatPendingRequest();
    }
}
