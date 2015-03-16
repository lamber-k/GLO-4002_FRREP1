package org.Marv1n.code.Notification;

import org.Marv1n.code.Repository.Person.PersonRepositoryInMemory;
import org.Marv1n.code.Repository.Reservable.ReservableRepositoryInMemory;
import org.Marv1n.code.Repository.Reservation.ReservationRepositoryInMemory;
import org.Marv1n.code.Request;

public interface NotificationFactory {

    public Notification createNotification(Request request, ReservationRepositoryInMemory reservationRepository, ReservableRepositoryInMemory reservableRepository, PersonRepositoryInMemory personRepository) throws InvalidRequestException;
}
