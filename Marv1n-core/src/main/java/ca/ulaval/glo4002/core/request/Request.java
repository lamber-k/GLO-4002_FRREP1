package ca.ulaval.glo4002.core.request;

import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Request {

    @Id
    private final UUID requestID;
    private final int numberOfSeatsNeeded;
    private final int priority;
    // TODO Kevin faire que ca fonction u r welcome
    @Transient
    private final Person responsible;
    @Enumerated(EnumType.ORDINAL)
    private RequestStatus status;
    // TODO Kevin faire que ca fonction u r welcome
    @Transient
    private List<Person> participants;
    @Transient
    private Room reservedRoom;
    private String reason;

    public Request(int numberOfSeatsNeeded, int priority, Person person, List<Person> participant) {
        this.priority = priority;
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
        this.status = RequestStatus.PENDING;
        this.responsible = person;
        this.participants = participant;
        this.reservedRoom = null;
    }

    public Request(int numberOfSeatsNeeded, int priority, Person person) {
        this.priority = priority;
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
        this.status = RequestStatus.PENDING;
        this.responsible = person;
        this.participants = null;
        this.reservedRoom = null;
    }

    public Request() {
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = 0;
        this.priority = 0;
        this.responsible = null;
        this.participants = new ArrayList<>();
        this.reservedRoom = null;
    }

    public RequestStatus getRequestStatus() {
        return status;
    }

    public int getNumberOfSeatsNeeded() {
        return numberOfSeatsNeeded;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object rhs) {
        return rhs != null && rhs instanceof Request && hashCode() == rhs.hashCode();
    }

    @Override
    public int hashCode() {
        return requestID.hashCode();
    }

    public UUID getRequestID() {
        return requestID;
    }

    public Person getResponsible() {
        return responsible;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    private void accept() {
        status = RequestStatus.ACCEPTED;
    }

    public void refuse(String reason) {
        status = RequestStatus.REFUSED;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void cancel() {
        if (reservedRoom != null) {
            reservedRoom.unbook();
            reservedRoom = null;
        }
        status = RequestStatus.CANCELED;
    }

    public void reserve(Room room) {
        try {
            room.book(this);
            reservedRoom = room;
            accept();
        } catch (RoomAlreadyReservedException exception) {
            refuse(exception.getMessage());
        }
    }
}