package org.Marv1n.code.Notifaction;


public abstract class FactoryNotification implements IFactoryNotification {

    private static final String ASSIGNATION_REFUSED_MESSAGE = "Votre demande de salle à été refusée.\n";
    private static final String ASSIGNATION_CANCEL_MESSAGE = "Votre demande d'annulation à été prise en compte.\n";
    private static final String ASSIGNATION_SUCCESS_FORMAT = "Votre requête a été traité avec succès.\n"
            + "Vous avez été assigné à la salle %s.\n";

    protected String buildNotification(StateNotification notification, String room) {
        String  notificationDetail;

        switch (notification) {
            case ASSIGNATION_SUCCESS:
                notificationDetail = String.format(ASSIGNATION_SUCCESS_FORMAT, room);
                break;
            case ASSIGNATION_REFUSED:
                notificationDetail = ASSIGNATION_REFUSED_MESSAGE;
                break;
            case ASSIGNATION_CANCEL:
                notificationDetail = ASSIGNATION_CANCEL_MESSAGE;
                break;
            default:
                notificationDetail = "";
        }
        return notificationDetail;
    }
}
