package org.Marv1n.code.RequestCancellationStrategy;

import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.RequestStatus;

public class RequestCancellationFactoryStrategy {

    private RequestRepository requestRepository;
    private ReservationRepository reservationRepository;
    private PendingRequests pendingRequests;

    public RequestCancellationFactoryStrategy(RequestRepository requestRepository, ReservationRepository reservationRepository, PendingRequests pendingRequests) {
        this.requestRepository = requestRepository;
        this.reservationRepository = reservationRepository;
        this.pendingRequests = pendingRequests;
    }

    public RequestCancellationStrategy createStrategyCancellation(RequestStatus requestStatus) {
        switch (requestStatus) {
            case CANCELED:
                return new RequestCancellationCancelledStrategy();
            case REFUSED:
                return new RequestCancellationRefusedStrategy();
            case PENDING:
                return new RequestCancellationPendingStrategy(requestRepository, pendingRequests);
            default:
                return new RequestCancellationAcceptedStrategy(requestRepository, reservationRepository);
        }
    }
}
