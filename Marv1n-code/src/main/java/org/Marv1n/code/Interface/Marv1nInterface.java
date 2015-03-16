package org.Marv1n.code.Interface;

import org.Marv1n.code.EmailAddressValidator;
import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Person.PersonRepository;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestCancellationStrategy.RequestCancellationStrategy;
import org.Marv1n.code.RequestCancellationStrategy.RequestCancellationFactoryStrategy;

import java.util.Optional;
import java.util.UUID;

public class Marv1nInterface {

    private RequestCancellationFactoryStrategy requestCancellationFactoryStrategy;
    private RequestRepository requestRepository;
    private PersonRepository personRepository;
    private PendingRequests pendingRequests;

    public Marv1nInterface(RequestRepository requestRepository,
                           ReservationRepository reservationRepository,
                           PersonRepository personRepository,
                           PendingRequests pendingRequests) {
        this.requestCancellationFactoryStrategy = new RequestCancellationFactoryStrategy(requestRepository, reservationRepository, pendingRequests);
        this.requestRepository = requestRepository;
        this.personRepository = personRepository;
        this.pendingRequests = pendingRequests;
    }

    public Marv1nInterface(RequestRepository requestRepository,
                           PersonRepository personRepository,
                           RequestCancellationFactoryStrategy requestCancellationFactoryStrategy,
                           PendingRequests pendingRequests) {
        this.requestCancellationFactoryStrategy = requestCancellationFactoryStrategy;
        this.requestRepository = requestRepository;
        this.personRepository = personRepository;
        this.pendingRequests = pendingRequests;
    }

    public void createRequest(int numberOfSeatsNeeded, int priority, String email) {
        if (EmailAddressValidator.validate(email)) {
            Person person;
            Optional<Person> result = personRepository.findByEmail(email);
            if (result.isPresent()) {
                person = result.get();
            }
            else {
                person = new Person(email);
                personRepository.create(person);
            }
            Request newRequest = new Request(numberOfSeatsNeeded, priority, person.getID());
            requestRepository.create(newRequest);
            pendingRequests.addRequest(newRequest);
        }
    }

    public void cancelRequest(UUID requestID) {
        Optional result = requestRepository.findByUUID(requestID);
        if (result.isPresent()) {
            Request request = (Request) result.get();
            RequestCancellationStrategy strategyRequestCancellation = requestCancellationFactoryStrategy.createStrategyCancellation(request.getRequestStatus());
            strategyRequestCancellation.cancelRequest(request);
        }
    }
}
