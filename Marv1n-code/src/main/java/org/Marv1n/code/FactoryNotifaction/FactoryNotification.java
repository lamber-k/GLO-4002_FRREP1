package org.Marv1n.code.FactoryNotifaction;

import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservable.ReservableRepository;

import javax.management.Notification;
import java.util.UUID;

public interface FactoryNotification {

    public Notification createNotification(UUID requestUUID, RequestRepository requestRepository, ReservableRepository reservableRepository, StateNotification stateNotification);

    public enum StateNotification {ASSIGNATION_SUCCESS, ASSIGNATION_REFUSED, ASSIGNATION_CANCEL}
}
