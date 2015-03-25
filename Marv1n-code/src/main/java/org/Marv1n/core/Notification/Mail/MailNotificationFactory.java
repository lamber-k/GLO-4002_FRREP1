package org.Marv1n.core.notification.mail;

import org.Marv1n.core.notification.InvalidRequestException;
import org.Marv1n.core.notification.NotificationAbstractFactory;
import org.Marv1n.core.person.Person;
import org.Marv1n.core.person.PersonRepository;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.request.RequestStatus;
import org.Marv1n.core.reservation.Reservation;
import org.Marv1n.core.reservation.ReservationNotFoundException;
import org.Marv1n.core.reservation.ReservationRepository;
import org.Marv1n.core.room.Room;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MailNotificationFactory extends NotificationAbstractFactory {

    private final MailSender mailSender;
    private ReservationRepository reservationRepository;
    private PersonRepository personRepository;

    public MailNotificationFactory(MailSender mailSender, ReservationRepository reservationRepository, PersonRepository personRepository) {
        this.mailSender = mailSender;
        this.reservationRepository = reservationRepository;
        this.personRepository = personRepository;
    }

    @Override
    public MailNotification createNotification(Request request) throws InvalidRequestException {
        Person responsible = findResponsible(request, personRepository);
        Room room = findReservable(request, reservationRepository);
        List<String> mailTo = new LinkedList<>();
        mailTo.add(responsible.getMailAddress());
        mailTo.addAll(personRepository.findAdmins().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        Mail mail = buildMail(request, room, mailTo);
        return new MailNotification(mailSender, mail);
    }

    private Room findReservable(Request request, ReservationRepository reservationRepository) throws InvalidRequestException {
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

    private Mail buildMail(Request request, Room room, List<String> mailTo) {
        MailBuilder mailBuilder = new MailBuilder();
        try {
            String message = buildNotification(request, room);
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
