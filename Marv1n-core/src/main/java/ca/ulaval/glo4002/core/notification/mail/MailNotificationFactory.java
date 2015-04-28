package ca.ulaval.glo4002.core.notification.mail;

import ca.ulaval.glo4002.core.notification.InvalidNotificationException;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MailNotificationFactory implements NotificationFactory {

    private final MailSender mailSender;
    private final EmailValidator emailValidator;
    private List<String> forwardedEmailAddress = new LinkedList<>();

    public MailNotificationFactory(MailSender mailSender, EmailValidator emailValidator) {
        this.mailSender = mailSender;
        this.emailValidator = emailValidator;
    }

    @Override
    public MailNotification createNotification(Request request) {
        List<String> mailTo = new LinkedList<>();

        mailTo.addAll(request.getParticipants().stream().map(Person::getMailAddress).collect(Collectors.toList()));
        mailTo.addAll(forwardedEmailAddress);
        Mail mail = buildMail(request, mailTo);
        return new MailNotification(mailSender, mail);
    }

    private Mail buildMail(Request info, List<String> mailTo) {
        MailBuilder mailBuilder = new MailBuilder();
        try {
            return mailBuilder.setFrom(info.getResponsible().getMailAddress())
                    .setTo(mailTo)
                    .setReason(info.getReason())
                    .setStatus(info.getRequestStatus())
                    .setIdentifier(info.getRequestID())
                    .buildMail();
        } catch (Exception exception) {
            throw new InvalidNotificationException(exception);
        }
    }

    public void addForwardEmail(String emailAddress) throws InvalidMailAddressException {
        if (!emailValidator.validateMailAddress(emailAddress)) {
            throw new InvalidMailAddressException();
        }
        forwardedEmailAddress.add(emailAddress);
    }
}
