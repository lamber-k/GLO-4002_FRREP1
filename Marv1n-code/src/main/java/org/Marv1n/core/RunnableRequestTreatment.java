package org.Marv1n.core;

public abstract class RunnableRequestTreatment implements Runnable {

    protected abstract void treatPendingRequest();

    @Override
    public void run() {
        treatPendingRequest();
    }
}
