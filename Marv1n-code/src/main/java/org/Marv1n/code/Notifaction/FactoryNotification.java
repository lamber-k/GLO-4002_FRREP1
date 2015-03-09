package org.Marv1n.code.Notifaction;

import org.Marv1n.code.Request;

public abstract class FactoryNotification implements IFactoryNotification {

    private static final String ASSIGNATION_REFUSED_MESSAGE = "Votre demande de salle à été refusée.\n";
    private static final String ASSIGNATION_CANCEL_MESSAGE = "Votre demande d'annulation à été prise en compte.\n";
    private static final String ASSIGNATION_SUCCESS_FORMAT = "Votre requête a été traité avec succès.\n"
            + "Vous avez été assigné à la salle %s.\n";

    protected String buildNotification(Request request, String room) {
        String  notificationDetail;

        switch (request.getRequestStatus()) {
            case ACCEPTED:
                notificationDetail = String.format(ASSIGNATION_SUCCESS_FORMAT, room);
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
