package org.Marv1n.core;

import org.Marv1n.core.notification.Notification;
import org.Marv1n.core.notification.NotificationFactory;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.persistence.RequestRepository;
import org.Marv1n.core.request.RequestStatus;
import org.Marv1n.core.reservation.Reservation;
import org.Marv1n.core.persistence.ReservationRepository;

public class RequestStatusUpdater {

    private final RequestRepository pendingRequests;
    private final ReservationRepository reservationRepository;
    private final NotificationFactory notificationFactory;

    public RequestStatusUpdater(RequestRepository pendingRequests, ReservationRepository reservationRepository, NotificationFactory notificationFactory) {
        this.pendingRequests = pendingRequests;
        this.reservationRepository = reservationRepository;
        this.notificationFactory = notificationFactory;
    }

    public void updateRequest(Request request, RequestStatus newStatus) {
        if (newStatus != RequestStatus.PENDING) {
            notifyRequestStatusUpdate(request);
        }
        if (newStatus == RequestStatus.CANCELED && request.getRequestStatus() == RequestStatus.ACCEPTED) {
            cancelReservation(request);
        }
        pendingRequests.remove(request);
        request.setRequestStatus(newStatus);
        pendingRequests.persist(request);
    }

    private void cancelReservation(Request request) {
        Reservation reservation = reservationRepository.findReservationByRequest(request);
        reservationRepository.remove(reservation);
    }

    private void notifyRequestStatusUpdate(Request request) {
        Notification notification = notificationFactory.createNotification(request);
        notification.announce();
    }
}
