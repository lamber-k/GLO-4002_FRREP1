package ca.ulaval.glo4002.core.notification.mail;

import java.util.List;

public class MailBuilder {

    private static final String MAIL_OBJECT_FORMAT = "[%s][#%s] status %s";
    private static final String MAIL_MESSAGE_DEFAULT_FORMAT = "Bonjour,\n"
            + "Le status de votre %s ayant pour identifiant %s a changé de status.\n"
            + "Elle est désormais dans le status %s.\n\n"
            + "Cordialement,";

    private String status = null;
    private String identifier = null;
    private String mailFrom;
    private List<String> mailTo;
    private String message = null;
    private String category = null;

    public MailBuilder setFrom(String mailFrom) {
        this.mailFrom = mailFrom;
        return this;
    }

    public MailBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public MailBuilder setTo(List<String> mailTo) {
        this.mailTo = mailTo;
        return this;
    }

    public MailBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public MailBuilder setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public MailBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public Mail buildMail() throws MailBuilderException {
        String from = mailFrom;
        List<String> to = mailTo;
        String object = buildMailObject();
        if (message == null) {
            message = String.format(MAIL_MESSAGE_DEFAULT_FORMAT, category, identifier, status);
        }
        return new Mail(from, to, object, message);
    }

    private String buildMailObject() throws MailBuilderException {
        if (category == null) {
            throw new MailBuilderException("Category not set");
        }
        if (identifier == null) {
            throw new MailBuilderException("ID not set");
        }
        if (status == null) {
            throw new MailBuilderException("Status not set");
        }
        return String.format(MAIL_OBJECT_FORMAT, category, identifier, status);
    }
}
