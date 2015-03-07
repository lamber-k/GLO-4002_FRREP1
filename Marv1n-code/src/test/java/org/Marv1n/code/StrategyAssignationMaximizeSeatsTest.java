package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Repository.IReservationRepository;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StrategyAssignationMaximizeSeatsTest {

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
    public void init() {
        reservableList = new ArrayList<>();
        assigner = new StrategyEvaluationMaximizeSeats();

        reservableList.add(mockReservable);
        when(reservableRepository.findAll()).thenReturn(reservableList);
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsNotBestThanFirst_ReturnTheFirst() {
        when(mockReservable.hasGreaterCapacityThan(anotherMockReservable)).thenReturn(false);
        reservableList.add(anotherMockReservable);

        ReservableEvaluationResult result = assigner.evaluateOneRequest(reservableRepository, reservationRepository, mockRequest);

        assertEquals(result.getBestReservableMatch(), mockReservable);
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsBestThanFirst_ReturnTheSecond() {
        when(mockReservable.hasGreaterCapacityThan(anotherMockReservable)).thenReturn(true);
        reservableList.add(anotherMockReservable);

        ReservableEvaluationResult result = assigner.evaluateOneRequest(reservableRepository, reservationRepository, mockRequest);

        assertEquals(result.getBestReservableMatch(), anotherMockReservable);
    }
}
