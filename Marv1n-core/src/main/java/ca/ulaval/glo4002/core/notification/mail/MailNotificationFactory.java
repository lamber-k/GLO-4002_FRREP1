package ca.ulaval.glo4002.core.notification.mail;

import ca.ulaval.glo4002.core.notification.InvalidNotificationException;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.notification.NotificationInfo;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.person.PersonRepository;

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
    public MailNotification createNotification(NotificationInfo info) {
        List<String> mailTo = new LinkedList<>();
        mailTo.addAll(personRepository.findAdmins().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        mailTo.addAll(info.getDestination().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        Mail mail = buildMail(info, mailTo);
        return new MailNotification(mailSender, mail);
    }

    private Mail buildMail(NotificationInfo info, List<String> mailTo) {
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
