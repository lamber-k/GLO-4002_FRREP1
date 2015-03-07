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
        return numberOfSeatsNeeded;
    }

    public Integer getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        } else if (rhs instanceof Request) {
            return hashCode() == rhs.hashCode();
        }
        return (false);
    }

    @Override
    public int hashCode() {
        return (requestID.hashCode());
    }


    public UUID getRequestID() {
        return requestID;
    }
}
