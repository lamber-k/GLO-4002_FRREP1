package org.Marv1n.code;

import java.util.UUID;

public class Request {

    private UUID requestID;
    private Integer numberOfSeatsNeeded = 0;
    private Integer priority = 0;

    public Request(Integer numberOfSeatsNeeded) {
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
    }

    public Integer getNumberOdSeatsNeeded() {
        return this.numberOfSeatsNeeded;
    }

    public UUID getRequestID() {
        return (this.requestID);
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
