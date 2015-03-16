package org.Marv1n.code.RequestCancellationStrategy;

import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
import org.Marv1n.code.Reservation.Reservation;

public class RequestCancellationAcceptedStrategy implements RequestCancellationStrategy {

    private RequestRepository requestRepository;
    private ReservationRepository reservationRepository;

    public RequestCancellationAcceptedStrategy(RequestRepository requestRepository, ReservationRepository reservationRepository) {
        this.requestRepository = requestRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void cancelRequest(Request request) {
        Reservation reservation = reservationRepository.findReservationByRequest(request);
        reservationRepository.remove(reservation);
        requestRepository.remove(request);
        request.setRequestStatus(RequestStatus.CANCELED);
        requestRepository.create(request);
    }
}
