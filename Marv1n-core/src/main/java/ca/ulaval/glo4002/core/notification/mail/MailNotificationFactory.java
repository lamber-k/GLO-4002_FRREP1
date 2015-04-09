package ca.ulaval.glo4002.core.notification.mail;

import ca.ulaval.glo4002.core.notification.InvalidNotificationException;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.person.PersonRepository;
import ca.ulaval.glo4002.core.request.Request;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MailNotificationFactory implements NotificationFactory {

    private final MailSender mailSender;
    private PersonRepository personRepository;

    public MailNotificationFactory(MailSender mailSender, PersonRepository personRepository) {
        this.mailSender = mailSender;
        this.personRepository = personRepository;
    }

    @Override
    public MailNotification createNotification(Request request) {
        List<String> mailTo = new LinkedList<>();
        mailTo.addAll(personRepository.findAdmins().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        mailTo.addAll(request.getParticipants().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        Mail mail = buildMail(request, mailTo);
        return new MailNotification(mailSender, mail);
    }

    private Mail buildMail(Request info, List<String> mailTo) {
        MailBuilder mailBuilder = new MailBuilder();
        try {
            return mailBuilder.setTo(mailTo)
                    .setReason(info.getReason())
                    .setStatus(info.getRequestStatus())
                    .setIdentifier(info.getRequestID())
                    .buildMail();
        } catch (MailBuilderException exception) {
            throw new InvalidNotificationException(exception);
        }
    }
}
