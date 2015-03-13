package org.Marv1n.code.Notifaction.Mail;

import org.Marv1n.code.Notifaction.FactoryNotification;
import org.Marv1n.code.Notifaction.INotification;
import org.Marv1n.code.Notifaction.InvalidRequestException;
import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Person.PersonRepository;
import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class MailFactoryNotification extends FactoryNotification {

    private final IMailServiceAdapter mailService;

    public MailFactoryNotification(IMailServiceAdapter mailService) {
        this.mailService = mailService;
    }

    @Override
    public INotification createNotification(Request request, ReservationRepository reservationRepository, ReservableRepository reservableRepository, PersonRepository personRepository) throws InvalidRequestException {
        Person responsible = findResponsible(request, personRepository);
        IReservable reservable = findReservable(request, reservationRepository);
        List<String> mailTo = new LinkedList<>();
        mailTo.add(responsible.getMailAddress());
        mailTo.addAll(personRepository.findAdmins().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        Mail mail = buildMail(request, reservable, mailTo);

        return new MailNotification(mailService, mail);
    }

    private IReservable findReservable(Request request, ReservationRepository reservationRepository) throws InvalidRequestException {
        Reservation reservation;
        try {
            reservation = reservationRepository.findReservationByRequest(request);
        } catch (ReservationNotFoundException e) {
            if (request.getRequestStatus() == RequestStatus.REFUSED) {
                return null;
            }
            throw new InvalidRequestException("Aucune salle n'a été assignée");
        }
        return reservation.getReserved();
    }

    private Person findResponsible(Request request, PersonRepository personRepository) throws InvalidRequestException {
        Optional<Person> optionalResponsible = personRepository.findByUUID(request.getResponsibleUUID());
        if (!optionalResponsible.isPresent()) {
            throw new InvalidRequestException("Aucun responsable assigné");
        }
        return optionalResponsible.get();
    }

    private Mail buildMail(Request request, IReservable reservable, List<String> mailTo) {
        MailBuilder mailBuilder = new MailBuilder();
        String message = super.buildNotification(request, reservable);

        try {
            return mailBuilder.setTo(mailTo)
                    .setMessage(message)
                    .setStatus(request.getRequestStatus())
                    .setRequestID(request.hashCode())
                    .buildMail();
        } catch (MailBuilderException e) {
            throw new InvalidRequestException(e.getMessage());
        }
    }
}
