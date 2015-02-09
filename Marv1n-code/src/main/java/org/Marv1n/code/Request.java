package org.Marv1n.code;

public class Request {
    private int numberOfSeatsNeeded = 0;
    private int priority = 0;

    public Request(int numberOfSeatsNeeded) {
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
    }

    public int getNumberOdSeatsNeeded() {
        return this.numberOfSeatsNeeded;
    }

    public void setNumberOfSeatsNeeded(int NumberOfSeatsNeeded){
        this.numberOfSeatsNeeded = NumberOfSeatsNeeded;
    }

    public int getPriority(){
        return this.priority;
    }

    public void setPriority(int Priority){
        this.priority = Priority;
    }
}
