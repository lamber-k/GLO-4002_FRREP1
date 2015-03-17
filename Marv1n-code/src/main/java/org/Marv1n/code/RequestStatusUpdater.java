package org.Marv1n.code;

import org.Marv1n.code.Notification.Notification;
import org.Marv1n.code.Notification.NotificationFactory;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Reservation.Reservation;

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
        pendingRequests.create(request);
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
