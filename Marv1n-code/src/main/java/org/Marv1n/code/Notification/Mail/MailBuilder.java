package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.RequestStatus;

import java.util.List;

public class MailBuilder {

    public static final String MAIL_OBJECT_STATUS_ACCEPTED = "acceptée";
    public static final String MAIL_OBJECT_STATUS_REFUSED = "refusée";
    public static final String MAIL_OBJECT_STATUS_CANCELED = "annulée";
    private static final String MAIL_OBJECT_FORMAT = "[Reservation][requête n°%d] status %s";
    private RequestStatus requestStatus = null;
    private Integer requestID = null;
    private String mailFrom;
    private List<String> mailTo;
    private String message;

    public MailBuilder setFrom(String mailFrom) {
        this.mailFrom = mailFrom;
        return this;
    }

    public MailBuilder setTo(List<String> mailTo) {
        this.mailTo = mailTo;
        return this;
    }

    public MailBuilder setStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    public MailBuilder setRequestID(int requestID) {
        this.requestID = requestID;
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
        String message = this.message;
        return new Mail(from, to, object, message);
    }

    private String buildMailObject() throws MailBuilderException {
        String mailObject;
        if (requestID == null) {
            throw new MailBuilderException("Request ID not set");
        }
        if (requestStatus == null) {
            throw new MailBuilderException("Request Status not set");
        }
        switch (requestStatus) {
            case ACCEPTED:
                mailObject = String.format(MAIL_OBJECT_FORMAT, requestID, MAIL_OBJECT_STATUS_ACCEPTED);
                break;
            case REFUSED:
                mailObject = String.format(MAIL_OBJECT_FORMAT, requestID, MAIL_OBJECT_STATUS_REFUSED);
                break;
            case CANCELED:
                mailObject = String.format(MAIL_OBJECT_FORMAT, requestID, MAIL_OBJECT_STATUS_CANCELED);
                break;
            default:
                throw new MailBuilderException("Invalid Request Status");
        }
        return (mailObject);
    }
}