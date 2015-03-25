package org.Marv1n.core.Facade;

import org.Marv1n.core.EmailAddressValidator;
import org.Marv1n.core.PendingRequests;
import org.Marv1n.core.person.Person;
import org.Marv1n.core.persistence.PersonRepository;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.persistence.RequestRepository;
import org.Marv1n.core.request.RequestStatus;
import org.Marv1n.core.RequestStatusUpdater;

import java.util.Optional;
import java.util.UUID;

public class Marv1nFacade {

    private RequestRepository requestRepository;
    private PersonRepository personRepository;
    private PendingRequests pendingRequests;
    private RequestStatusUpdater requestStatusUpdater;

    public Marv1nFacade(RequestRepository requestRepository,
                        PersonRepository personRepository,
                        PendingRequests pendingRequests,
                        RequestStatusUpdater requestStatusUpdater) {
        this.requestRepository = requestRepository;
        this.personRepository = personRepository;
        this.pendingRequests = pendingRequests;
        this.requestStatusUpdater = requestStatusUpdater;
    }

    public void createRequest(int numberOfSeatsNeeded, int priority, String email) {
        if (EmailAddressValidator.validate(email)) {
            Person person;
            Optional<Person> result = personRepository.findByEmail(email);
            if (result.isPresent()) {
                person = result.get();
            } else {
                person = new Person(email);
                personRepository.persist(person);
            }
            Request newRequest = new Request(numberOfSeatsNeeded, priority, person.getID());
            pendingRequests.addRequest(newRequest);
        }
    }

    public void cancelRequest(UUID requestID) {
        Optional<Request> result = requestRepository.findByUUID(requestID);
        if (result.isPresent()) {
            Request request = result.get();
            requestStatusUpdater.updateRequest(request, RequestStatus.CANCELED);
        }
    }
}
