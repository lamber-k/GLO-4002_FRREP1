package org.Marv1n.code;

public class Request {

    private Integer numberOfSeatsNeeded = 0;
    private Integer priority = 0;

    public Request(Integer numberOfSeatsNeeded) {
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
    }

    public Integer getNumberOdSeatsNeeded() {
        return this.numberOfSeatsNeeded;
    }

    public void setNumberOfSeatsNeeded(Integer numberOfSeatsNeeded) {
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
