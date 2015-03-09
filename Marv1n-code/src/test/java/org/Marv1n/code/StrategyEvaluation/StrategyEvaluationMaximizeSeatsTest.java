package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Repository.Reservable.IReservableRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StrategyEvaluationMaximizeSeatsTest {

    private List<IReservable> reservableList;
    private IStrategyEvaluation assigner;
    @Mock
    private IReservable mockReservable;
    @Mock
    private IReservable anotherMockReservable;
    @Mock
    private Request mockRequest;
    @Mock
    private Request anotherMockRequest;
    @Mock
    private IReservableRepository reservableRepository;
    @Mock
    private IReservationRepository reservationRepository;

    @Before
    public void initializeStrategy() throws ReservationNotFoundException {
        reservableList = new ArrayList<>();
        assigner = new StrategyEvaluationMaximizeSeats();

        reservableList.add(mockReservable);
        loadDefaultBehaviours();
    }

    private void loadDefaultBehaviours() throws ReservationNotFoundException {
        when(reservableRepository.findAll()).thenReturn(reservableList);
        when(mockReservable.hasEnoughCapacity(any())).thenReturn(true);
        when(anotherMockReservable.hasEnoughCapacity(any())).thenReturn(true);
        when(reservationRepository.findReservationByReservable(any(IReservable.class))).thenThrow(ReservationNotFoundException.class);
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsNotBestThanFirst_ReturnTheFirst() throws ReservationNotFoundException {
        when(mockReservable.hasGreaterCapacityThan(anotherMockReservable)).thenReturn(false);
        reservableList.add(anotherMockReservable);

        ReservableEvaluationResult result = assigner.evaluateOneRequest(reservableRepository, reservationRepository, mockRequest);

        assertEquals(result.getBestReservableMatch(), mockReservable);
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsBestThanFirst_ReturnTheSecond() throws ReservationNotFoundException {
        when(mockReservable.hasGreaterCapacityThan(anotherMockReservable)).thenReturn(true);
        reservableList.add(anotherMockReservable);

        ReservableEvaluationResult result = assigner.evaluateOneRequest(reservableRepository, reservationRepository, mockRequest);

        assertEquals(result.getBestReservableMatch(), anotherMockReservable);
    }

    @Test
    public void whenOneReservableCanTBeFoundReturnsEmptyEvaluationResult() throws ReservationNotFoundException {
        ReservationRepository reservationsRepository = mock(ReservationRepository.class);
        when(reservationsRepository.findReservationByReservable(any(IReservable.class))).thenReturn(mock(Reservation.class));

        ReservableEvaluationResult result = assigner.evaluateOneRequest(reservableRepository, reservationsRepository, mockRequest);

        assertFalse(result.matchFound());
    }
}
