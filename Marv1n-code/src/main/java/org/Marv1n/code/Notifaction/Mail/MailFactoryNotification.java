package org.Marv1n.code.Notifaction.Mail;

import org.Marv1n.code.Notifaction.FactoryNotification;
import org.Marv1n.code.Notifaction.INotification;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservable.ReservableRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Kevin on 08/03/2015.
 */
public class MailFactoryNotification extends FactoryNotification {

    private final IMailServiceAdapter mailService;

    public MailFactoryNotification(IMailServiceAdapter mailService) {
        this.mailService = mailService;
    }

    @Override
    public INotification createNotification(UUID requestUUID, RequestRepository requestRepository, ReservableRepository reservableRepository, StateNotification stateNotification) {
        String from = "";
        List<String> to = new ArrayList<>();
        String object = "none";
        String message = super.buildNotification(stateNotification, "");
        return new MailNotification(mailService, new Mail(from, to, object, message));
    }
}
