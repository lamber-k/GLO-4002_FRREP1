package org.Marv1n.code;

import java.util.UUID;

public class Request {

    private UUID requestID;
    private Integer numberOfSeatsNeeded ;
    private Integer priority;

    public Request(Integer numberOfSeatsNeeded, Integer priority) {
        this.priority = priority;
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
    }

    public Integer getNumberOfSeatsNeeded() {
        return this.numberOfSeatsNeeded;
    }

    public UUID getRequestID() {
        return (this.requestID);
    }

    public Integer getPriority() {
        return this.priority;
    }



}
