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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MailFactoryNotification extends FactoryNotification {

    private static final String MAIL_OBJECT_FORMAT = "[Reservation][Demande n°%d] requête %s";
    private static final String MAIL_OBJECT_STATUS_ACCEPTED = "acceptée";
    private static final String MAIL_OBJECT_STATUS_REFUSED = "refusée";
    private static final String MAIL_OBJECT_STATUS_CANCELED = "annulée";
    private final IMailServiceAdapter mailService;

    public MailFactoryNotification(IMailServiceAdapter mailService) {
        this.mailService = mailService;
    }

    @Override
    public INotification createNotification(Request request, ReservationRepository reservationRepository, ReservableRepository reservableRepository, PersonRepository personRepository) throws InvalidRequestException {
        Person responsible = findResponsible(request, personRepository);
        IReservable reservable = findReservable(request, reservationRepository, reservableRepository);
        List<String> mailTo = Arrays.asList(responsible.getMailAddress()); // Ajouter Admin.
        Mail mail = buildMail(request, reservable, mailTo);

        return new MailNotification(mailService, mail);
    }

    private IReservable findReservable(Request request, ReservationRepository reservationRepository, ReservableRepository reservableRepository) throws InvalidRequestException {
        Reservation reservation;
        try {
             reservation = reservationRepository.findReservationByRequest(request);
        } catch (ReservationNotFoundException e) {
            if (request.getRequestStatus() == RequestStatus.REFUSED) {
                return null;
            }
            throw new InvalidRequestException(); // "Aucune salle n'a été assignée"
        }
        return reservation.getReserved();
    }

    private Person findResponsible(Request request, PersonRepository personRepository) throws InvalidRequestException {
        Optional<Person> optionalResponsible = personRepository.findByUUID(request.getResponsibleUUID());
        if (!optionalResponsible.isPresent()) {
            throw new InvalidRequestException(); // "Aucune responsable assigné"
        }
        return optionalResponsible.get();
    }

    private String buildMailObject(Request request) {
        String mailObject;

        switch (request.getRequestStatus()) {
            case ACCEPTED:
                mailObject = String.format(MAIL_OBJECT_FORMAT, request.hashCode(), MAIL_OBJECT_STATUS_ACCEPTED);
                break;
            case REFUSED:
                mailObject = String.format(MAIL_OBJECT_FORMAT, request.hashCode(), MAIL_OBJECT_STATUS_REFUSED);
                break;
            case CANCELED:
                mailObject = String.format(MAIL_OBJECT_FORMAT, request.hashCode(), MAIL_OBJECT_STATUS_CANCELED);
                break;
            default:
                mailObject = "";
        }
        return (mailObject);
    }

    private Mail buildMail(Request request, IReservable reservable, List<String> mailTo) {
        String mailObject = buildMailObject(request);
        String message = super.buildNotification(request, reservable.toString());
        Mail mail = new Mail(null, mailTo, mailObject, message);

        return (mail);
    }
}
