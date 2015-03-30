package core.notification.mail;

import core.notification.InvalidRequestException;
import core.notification.NotificationAbstractFactory;
import core.person.Person;
import core.person.PersonNotFoundException;
import core.request.Request;
import core.request.RequestStatus;
import core.room.Room;
import core.room.RoomNotFoundException;
import core.room.RoomRepository;
import core.person.PersonRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MailNotificationFactory extends NotificationAbstractFactory {

    private final MailSender mailSender;
    private final RoomRepository roomRepository;
    private PersonRepository personRepository;

    public MailNotificationFactory(MailSender mailSender, RoomRepository roomRepository, PersonRepository personRepository) {
        this.mailSender = mailSender;
        this.personRepository = personRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public MailNotification createNotification(Request request) throws InvalidRequestException {
        Person responsible = findResponsible(request, personRepository);
        Room room = null;
        if (request.getRequestStatus() == RequestStatus.ACCEPTED) {
            try {
                room = roomRepository.findRoomByAssociatedRequest(request);
            } catch (RoomNotFoundException e) {
                throw new InvalidRequestException("Requête non associé à une salle");
            }
        }
        List<String> mailTo = new LinkedList<>();
        mailTo.add(responsible.getMailAddress());
        mailTo.addAll(personRepository.findAdmins().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        Mail mail = buildMail(request, room, mailTo);
        return new MailNotification(mailSender, mail);
    }

    private Person findResponsible(Request request, PersonRepository personRepository) throws InvalidRequestException {
        try {
            return personRepository.findByUUID(request.getResponsibleUUID());
        } catch (PersonNotFoundException e) {
            throw new InvalidRequestException("Aucun responsable assigné");
        }
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
