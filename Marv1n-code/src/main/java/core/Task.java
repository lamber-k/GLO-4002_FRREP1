package core;

public abstract class Task implements Runnable {


    protected abstract void runTask();

    @Override
    public void run() {
        runTask();
    }
}
