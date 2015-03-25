package org.Marv1n.core.notification;

import org.Marv1n.core.notification.mail.MailBuilderException;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.room.Room;

public abstract class NotificationAbstractFactory implements NotificationFactory {

    private static final String ASSIGNATION_REFUSED_MESSAGE = "Votre demande de salle à été refusée.%n";
    private static final String ASSIGNATION_CANCEL_MESSAGE = "Votre demande d'annulation à été prise en compte.%n";
    private static final String ASSIGNATION_SUCCESS_FORMAT = "Votre requête a été traité avec succès.%n"
            + " Vous avez été assigné à la salle %s.%n";

    protected String buildNotification(Request request, Room room) throws MailBuilderException {
        if (request.getRequestStatus() == null) {
            throw new MailBuilderException("Request Status not set");
        }
        String notificationDetail;
        switch (request.getRequestStatus()) {
            case ACCEPTED:
                notificationDetail = String.format(ASSIGNATION_SUCCESS_FORMAT, room.getName());
                break;
            case REFUSED:
                notificationDetail = ASSIGNATION_REFUSED_MESSAGE;
                break;
            case CANCELED:
                notificationDetail = ASSIGNATION_CANCEL_MESSAGE;
                break;
            default:
                notificationDetail = "";
        }
        return notificationDetail;
    }
}
