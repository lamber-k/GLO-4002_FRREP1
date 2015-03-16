package org.Marv1n.code.StrategyRequestCancellation;

import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.RequestStatus;

public class StrategyRequestCancellationFactory {

    private IRequestRepository requestRepository;
    private IReservationRepository reservationRepository;
    private PendingRequests pendingRequests;

    public StrategyRequestCancellationFactory(IRequestRepository requestRepository, IReservationRepository reservationRepository, PendingRequests pendingRequests) {
        this.requestRepository = requestRepository;
        this.reservationRepository = reservationRepository;
        this.pendingRequests = pendingRequests;
    }

    public IStrategyRequestCancellation createStrategyCancellation(RequestStatus requestStatus) {
        switch (requestStatus) {
            case CANCELED:
                return new StrategyRequestCancellationCancelled();
            case REFUSED:
                return new StrategyRequestCancellationRefused();
            case PENDING:
                return new StrategyRequestCancellationPending(requestRepository, pendingRequests);
            default:
                return new StrategyRequestCancellationAccepted(requestRepository, reservationRepository);

        }
    }
}
