package org.Marv1n.code.Facade;

import org.Marv1n.code.*;
import org.Marv1n.code.Notification.NotificationFactory;
import org.Marv1n.code.Repository.Person.PersonRepository;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;

import java.util.Optional;
import java.util.UUID;

public class Marv1nFacade {

    private RequestRepository requestRepository;
    private PersonRepository personRepository;
    private PendingRequests pendingRequests;
    private RequestStatusUpdater requestStatusUpdater;

    public Marv1nFacade(RequestRepository requestRepository,
                        PersonRepository personRepository,
                        PendingRequests pendingRequests, ReservationRepository reservationRepository, NotificationFactory notificationFactory) {
        this.requestRepository = requestRepository;
        this.personRepository = personRepository;
        this.pendingRequests = pendingRequests;
        this.requestStatusUpdater = new RequestStatusUpdater(requestRepository, reservationRepository, notificationFactory);
    }

    public void createRequest(int numberOfSeatsNeeded, int priority, String email) {
        if (EmailAddressValidator.validate(email)) {
            Person person;
            Optional<Person> result = personRepository.findByEmail(email);
            if (result.isPresent()) {
                person = result.get();
            } else {
                person = new Person(email);
                personRepository.create(person);
            }
            Request newRequest = new Request(numberOfSeatsNeeded, priority, person.getID());
            pendingRequests.addRequest(newRequest);
        }
    }

    public void cancelRequest(UUID requestID) {
        Optional result = requestRepository.findByUUID(requestID);
        if (result.isPresent()) {
            Request request = (Request) result.get();
            requestStatusUpdater.updateRequest(request, RequestStatus.CANCELED);

        }
    }
}
