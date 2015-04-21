package ca.ulaval.glo4002.core.notification.mail;

import ca.ulaval.glo4002.core.request.RequestStatus;

import java.util.List;
import java.util.UUID;

public class MailBuilder {

    private static final String MAIL_OBJECT_FORMAT = "[Reservation][#%s] status %s";
    private static final String MAIL_MESSAGE_HEADER = "Bonjour,\n"
            + "Le status de votre demande ayant pour identifiant %s a changé de status.\n"
            + "Elle est désormais dans le status %s.\n";
    private static final String MAIL_MESSAGE_REASON = "Pour la raison suivante :\n%s\n";
    private static final String MAIL_MESSAGE_FOOTER = "\nCordialement,";

    private RequestStatus status;
    private UUID identifier;
    private String mailFrom;
    private List<String> mailTo;
    private String reason;
    private String message;

    public MailBuilder setFrom(String mailFrom) {
        this.mailFrom = mailFrom;
        return this;
    }

    public MailBuilder setTo(List<String> mailTo) {
        this.mailTo = mailTo;
        return this;
    }

    public MailBuilder setStatus(RequestStatus status) {
        this.status = status;
        return this;
    }

    public MailBuilder setIdentifier(UUID identifier) {
        this.identifier = identifier;
        return this;
    }

    public Mail buildMail() throws MailBuilderException {
        String from = mailFrom;
        List<String> to = mailTo;
        String object = buildMailObject();
        message = String.format(MAIL_MESSAGE_HEADER, identifier, status);
        if (reason != null) {
            //TODO ALL Test me properly
            message += String.format(MAIL_MESSAGE_REASON, reason);
        }
        message += MAIL_MESSAGE_FOOTER;
        return new Mail(from, to, object, message);
    }

    private String buildMailObject() throws MailBuilderException {
        if (identifier == null) {
            //TODO ALL Test me properly
            throw new MailBuilderException("ID not set");
        }
        if (status == null) {
            throw new MailBuilderException("Status not set");
        }
        return String.format(MAIL_OBJECT_FORMAT, identifier, status);
    }

    public MailBuilder setReason(String reason) {
        //TODO ALL Test me properly
        this.reason = reason;
        return this;
    }
}
