package org.Marv1n.code.Notifaction;

import org.Marv1n.code.Repository.Person.PersonRepository;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservable.ReservableRepository;

import java.util.UUID;

public interface IFactoryNotification {

    public INotification createNotification(UUID requestUUID, RequestRepository requestRepository, ReservableRepository reservableRepository, StateNotification stateNotification, PersonRepository personRepository);

    public enum StateNotification {ASSIGNATION_SUCCESS, ASSIGNATION_REFUSED, ASSIGNATION_CANCEL}
}
