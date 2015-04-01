package core.notification.mail;

import core.notification.InvalidNotificationException;
import core.notification.NotificationFactory;
import core.notification.NotificationInfo;
import core.person.Person;
import core.person.PersonRepository;

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
    public MailNotification createNotification(NotificationInfo info) throws InvalidNotificationException {
        List<MailAddress> mailTo = new LinkedList<>();
        mailTo.addAll(personRepository.findAdmins().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        mailTo.addAll(info.getDestination().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        Mail mail = buildMail(info, mailTo);
        return new MailNotification(mailSender, mail);
    }

    private Mail buildMail(NotificationInfo info, List<MailAddress> mailTo) {
        MailBuilder mailBuilder = new MailBuilder();
        String message = buildMessage(info);
        try {
            return mailBuilder.setTo(mailTo)
                    .setCategory(info.getCategory())
                    .setMessage(message)
                    .setStatus(info.getStatus())
                    .setIdentifier(info.getIdentifier())
                    .buildMail();
        } catch (MailBuilderException exception) {
            throw new InvalidNotificationException(exception);
        }
    }

    private String buildMessage(NotificationInfo info) {

        return "Bonjour,\n" + "Ceci est un message automatique.\n" + "Ce mail vous a été envoyé pour la raison suivante:\n" + info.getDetail() + "\n\nCordialement,\n";
    }
}
