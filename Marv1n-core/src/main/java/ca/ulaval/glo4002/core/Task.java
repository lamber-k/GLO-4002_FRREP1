package ca.ulaval.glo4002.core;

public abstract class Task extends Thread {

    protected abstract void runTask() throws InterruptedException;

    @Override
    public void run() {
        try {
            runTask();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
