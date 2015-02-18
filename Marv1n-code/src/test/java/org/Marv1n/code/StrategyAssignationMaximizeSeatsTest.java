package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.StrategyEvaluation.IStrategyEvaluation;
import org.Marv1n.code.StrategyEvaluation.StrategyEvaluationMaximizeSeats;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StrategyAssignationMaximizeSeatsTest {

    private final static Integer ONE_TIME = 1;

    private List<IReservable> reservableList;
    private IStrategyEvaluation assigner;
    @Mock
    private IReservable mockIReservable1;
    @Mock
    private IReservable mockIReservable2;
    @Mock
    private Request mockRequest1;
    @Mock
    private Request mockRequest2;
    @Mock
    private IReservableRepository reservableRepository;

    @Before
    public void init() {
        this.reservableList = new ArrayList<>();
        this.assigner = new StrategyEvaluationMaximizeSeats();

        this.reservableList.add(this.mockIReservable1);
        when(reservableRepository.findAll()).thenReturn(this.reservableList);
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsNotBestThanFirst_ReturnTheFirst() {
        when(this.mockIReservable1.hasGreaterCapacityThan(this.mockIReservable2)).thenReturn(false);
        this.reservableList.add(this.mockIReservable2);

        this.assigner.evaluateOneRequest(this.reservableRepository, this.mockRequest1);

        verify(this.mockIReservable1, times(ONE_TIME)).isBooked();
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsBestThanFirst_ReturnTheSecond() {
        when(this.mockIReservable1.hasGreaterCapacityThan(this.mockIReservable2)).thenReturn(true);
        this.reservableList.add(this.mockIReservable2);

        this.assigner.evaluateOneRequest(this.reservableRepository, this.mockRequest1);

        verify(this.mockIReservable2, times(ONE_TIME)).isBooked();
    }
}
