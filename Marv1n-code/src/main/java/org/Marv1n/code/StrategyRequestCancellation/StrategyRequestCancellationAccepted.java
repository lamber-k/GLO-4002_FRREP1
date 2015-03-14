package org.Marv1n.code.StrategyRequestCancellation;

import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
import org.Marv1n.code.Reservation.Reservation;

public class StrategyRequestCancellationAccepted implements IStrategyRequestCancellation {

    private IRequestRepository requestRepository;
    private IReservationRepository reservationRepository;

    public StrategyRequestCancellationAccepted(IRequestRepository requestRepository, IReservationRepository reservationRepository) {
        this.requestRepository = requestRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void cancelRequest(Request request) {
        Reservation reservation;
        try {
            reservation = reservationRepository.findReservationByRequest(request);
            reservationRepository.remove(reservation);
            requestRepository.remove(request);
            request.setRequestStatus(RequestStatus.CANCELED);
            requestRepository.create(request);
        } catch (Exception e) {
            throw e;
            //TODO Handle exception??
        }
    }
}
