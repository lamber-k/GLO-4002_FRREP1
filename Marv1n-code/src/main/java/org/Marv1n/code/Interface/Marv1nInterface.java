package org.Marv1n.code.Interface;

import org.Marv1n.code.EmailAddressValidator;
import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Person.IPersonRepository;
import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.StrategyRequestCancellation.IStrategyRequestCancellation;
import org.Marv1n.code.StrategyRequestCancellation.StrategyRequestCancellationFactory;

import java.util.Optional;
import java.util.UUID;

public class Marv1nInterface {

    private StrategyRequestCancellationFactory strategyRequestCancellationFactory;
    private IRequestRepository requestRepository;
    private IPersonRepository personRepository;
    private PendingRequests pendingRequests;

    public Marv1nInterface(IRequestRepository requestRepository,
                           IReservationRepository reservationRepository,
                           IPersonRepository personRepository,
                           PendingRequests pendingRequests) {
        strategyRequestCancellationFactory = new StrategyRequestCancellationFactory(requestRepository, reservationRepository, pendingRequests);
        this.requestRepository = requestRepository;
        this.personRepository = personRepository;
        this.pendingRequests = pendingRequests;
    }

    public Marv1nInterface(IRequestRepository requestRepository,
                           IPersonRepository personRepository,
                           StrategyRequestCancellationFactory strategyRequestCancellationFactory,
                           PendingRequests pendingRequests) {
        this.strategyRequestCancellationFactory = strategyRequestCancellationFactory;
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
            } else {
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
            IStrategyRequestCancellation strategyRequestCancellation = strategyRequestCancellationFactory.createStrategyCancellation(request.getRequestStatus());
            strategyRequestCancellation.cancelRequest(request);
        }
    }
}
