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
    @OneToOne(cascade = {CascadeType.ALL})
    private final Person responsible;
    private final long creationDate;
    @Enumerated(EnumType.ORDINAL)
    private RequestStatus status;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Person> participants;
    @OneToOne
    private Room reservedRoom;
    private String reason;

    public Request(int numberOfSeatsNeeded, int priority, Person person, List<Person> participant) {
        //TODO ALL Test me properly
        this.priority = priority;
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
        this.status = RequestStatus.PENDING;
        this.responsible = person;
        this.participants = participant;
        this.creationDate = System.currentTimeMillis();
    }

    public Request(int numberOfSeatsNeeded, int priority, Person person) {
        //TODO ALL Test me properly
        this.priority = priority;
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
        this.status = RequestStatus.PENDING;
        this.responsible = person;
        this.creationDate = System.currentTimeMillis();
    }

    public Request() {
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = 0;
        this.priority = 0;
        this.responsible = null;
        this.participants = new ArrayList<>();
        this.reservedRoom = null;
        this.creationDate = System.currentTimeMillis();
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
        //TODO ALL Test me properly
        return rhs != null && rhs instanceof Request && hashCode() == rhs.hashCode();
    }

    @Override
    public int hashCode() {
        return requestID.hashCode();
    }

    public UUID getRequestID() {
        return requestID;
    }

    public long getCreationDate() {return creationDate; }

    public Person getResponsible() {
        return responsible;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    public Room getReservedRoom() {return reservedRoom; }

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
            reservedRoom = null;  //TODO ALL Test me properly
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