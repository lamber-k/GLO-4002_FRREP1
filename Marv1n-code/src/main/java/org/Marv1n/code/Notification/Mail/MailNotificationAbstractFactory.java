package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notification.InvalidRequestException;
import org.Marv1n.code.Notification.Mail.MailService.MailService;
import org.Marv1n.code.Notification.NotificationAbstractFactory;
import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Person.PersonRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.Reservation.Reservation;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MailNotificationAbstractFactory extends NotificationAbstractFactory {

    private final MailService mailService;
    private ReservationRepository reservationRepository;
    private PersonRepository personRepository;

    public MailNotificationAbstractFactory(MailService mailService, ReservationRepository reservationRepository, PersonRepository personRepository) {
        this.mailService = mailService;
        this.reservationRepository = reservationRepository;
        this.personRepository = personRepository;
    }

    @Override
    public MailNotification createNotification(Request request) throws InvalidRequestException {
        Person responsible = findResponsible(request, personRepository);
        Reservable reservable = findReservable(request, reservationRepository);
        List<String> mailTo = new LinkedList<>();
        mailTo.add(responsible.getMailAddress());
        mailTo.addAll(personRepository.findAdmins().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        Mail mail = buildMail(request, reservable, mailTo);
        return new MailNotification(mailService, mail);
    }

    private Reservable findReservable(Request request, ReservationRepository reservationRepository) throws InvalidRequestException {
        Reservation reservation;
        try {
            reservation = reservationRepository.findReservationByRequest(request);
        } catch (ReservationNotFoundException exception) {
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

    private Mail buildMail(Request request, Reservable reservable, List<String> mailTo) {
        MailBuilder mailBuilder = new MailBuilder();
        try {
            String message = buildNotification(request, reservable);
            return mailBuilder.setTo(mailTo)
                    .setMessage(message)
                    .setStatus(request.getRequestStatus())
                    .setRequestID(request.hashCode())
                    .buildMail();
        } catch (MailBuilderException exception) {
            throw new InvalidRequestException(exception.getMessage());
        }
    }
}
