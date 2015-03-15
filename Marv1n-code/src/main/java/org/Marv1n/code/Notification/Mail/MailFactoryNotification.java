package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notification.FactoryNotification;
import org.Marv1n.code.Notification.INotification;
import org.Marv1n.code.Notification.InvalidRequestException;
import org.Marv1n.code.Notification.Mail.MailService.IMailService;
import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Person.PersonRepositoryInMemory;
import org.Marv1n.code.Repository.Reservable.ReservableRepositoryInMemory;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Repository.Reservation.ReservationRepositoryInMemory;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.Reservation;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MailFactoryNotification extends FactoryNotification {

    private final IMailService mailService;

    public MailFactoryNotification(IMailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public INotification createNotification(Request request, ReservationRepositoryInMemory reservationRepository, ReservableRepositoryInMemory reservableRepository, PersonRepositoryInMemory personRepository) throws InvalidRequestException {
        Person responsible = findResponsible(request, personRepository);
        IReservable reservable = findReservable(request, reservationRepository);
        List<String> mailTo = new LinkedList<>();
        mailTo.add(responsible.getMailAddress());
        mailTo.addAll(personRepository.findAdmins().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        Mail mail = buildMail(request, reservable, mailTo);
        return new MailNotification(mailService, mail);
    }

    private IReservable findReservable(Request request, ReservationRepositoryInMemory reservationRepository) throws InvalidRequestException {
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

    private Person findResponsible(Request request, PersonRepositoryInMemory personRepository) throws InvalidRequestException {
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
