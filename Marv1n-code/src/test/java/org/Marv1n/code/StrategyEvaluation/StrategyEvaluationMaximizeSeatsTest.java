package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Repository.Reservable.IReservableRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.StrategyEvaluation.IStrategyEvaluation;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;
import org.Marv1n.code.StrategyEvaluation.StrategyEvaluationMaximizeSeats;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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
    public void initializeStrategy() {
        this.reservableList = new ArrayList<>();
        this.assigner = new StrategyEvaluationMaximizeSeats();

        this.reservableList.add(this.mockReservable);
        loadDefaultBehaviours();
    }

    private void loadDefaultBehaviours() {
        when(this.reservableRepository.findAll()).thenReturn(this.reservableList);
        when(this.mockReservable.hasEnoughCapacity(any())).thenReturn(true);
        when(this.anotherMockReservable.hasEnoughCapacity(any())).thenReturn(true);
        try {
            doThrow(ReservationNotFoundException.class).when(this.reservationRepository).findReservationByReservable(any());
        } catch (ReservationNotFoundException e) {

        }
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsNotBestThanFirst_ReturnTheFirst() throws ReservationNotFoundException {
        when(this.mockReservable.hasGreaterCapacityThan(this.anotherMockReservable)).thenReturn(false);
        this.reservableList.add(this.anotherMockReservable);

        ReservableEvaluationResult result = this.assigner.evaluateOneRequest(this.reservableRepository, this.reservationRepository, this.mockRequest);

        assertEquals(result.getBestReservableMatch(), this.mockReservable);
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsBestThanFirst_ReturnTheSecond() throws ReservationNotFoundException {
        when(mockReservable.hasGreaterCapacityThan(this.anotherMockReservable)).thenReturn(true);
        this.reservableList.add(this.anotherMockReservable);

        ReservableEvaluationResult result = assigner.evaluateOneRequest(this.reservableRepository, this.reservationRepository, this.mockRequest);

        assertEquals(result.getBestReservableMatch(), this.anotherMockReservable);
    }
}
