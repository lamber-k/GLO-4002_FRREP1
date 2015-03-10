package org.Marv1n.code.StrategyRequestCancellation;

import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.RequestStatus;

public class StrategyRequestCancellationFactory {

    private IRequestRepository requestRepository;
    private IReservationRepository reservationRepository;

    public StrategyRequestCancellationFactory(IRequestRepository requestRepository, IReservationRepository reservationRepository) {
        this.requestRepository = requestRepository;
        this.reservationRepository = reservationRepository;
    }

    public IStrategyRequestCancellation createStrategyCancellation(RequestStatus requestStatus) {
        if (requestStatus.equals(RequestStatus.CANCELED))
            return new StrategyRequestCancellationCancelled();
        if (requestStatus.equals(RequestStatus.REFUSED))
            return new StrategyRequestCancellationRefused();
        if (requestStatus.equals(RequestStatus.PENDING))
            return new StrategyRequestCancellationPending(requestRepository);
        else
            return new StrategyRequestCancellationAccepted(requestRepository, reservationRepository);
    }

}
