package org.Marv1n.code.Notifaction;

import org.Marv1n.code.Repository.Person.PersonRepository;
import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;

public interface IFactoryNotification {

    public INotification createNotification(Request request, ReservationRepository reservationRepository, ReservableRepository reservableRepository, PersonRepository personRepository) throws InvalidRequestException;
}
