package core;

import core.notification.Notification;
import core.notification.NotificationFactory;
import core.persistence.RequestRepository;
import core.persistence.ReservationRepository;
import core.request.Request;
import core.request.RequestStatus;
import core.reservation.Reservation;

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
