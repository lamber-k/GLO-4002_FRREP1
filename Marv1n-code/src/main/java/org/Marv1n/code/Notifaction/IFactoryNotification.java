package org.Marv1n.code.Notifaction;

import org.Marv1n.code.Repository.Person.PersonRepositoryInMemory;
import org.Marv1n.code.Repository.Reservable.ReservableRepositoryInMemory;
import org.Marv1n.code.Repository.Reservation.ReservationRepositoryInMemory;
import org.Marv1n.code.Request;

public interface IFactoryNotification {

    public INotification createNotification(Request request, ReservationRepositoryInMemory reservationRepository, ReservableRepositoryInMemory reservableRepository, PersonRepositoryInMemory personRepository) throws InvalidRequestException;
}
