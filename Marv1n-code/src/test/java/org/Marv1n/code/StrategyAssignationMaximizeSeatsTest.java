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

    @Before
    public void init() {
        this.reservableList = new ArrayList<>();
        this.assigner = new StrategyEvaluationMaximizeSeats();

        this.reservableList.add(this.mockReservable);
        when(reservableRepository.findAll()).thenReturn(this.reservableList);
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsNotBestThanFirst_ReturnTheFirst() {
        when(this.mockReservable.hasGreaterCapacityThan(this.anotherMockReservable)).thenReturn(false);
        this.reservableList.add(this.anotherMockReservable);

        this.assigner.evaluateOneRequest(this.reservableRepository, this.mockRequest);

        verify(this.mockReservable).isBooked();
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsBestThanFirst_ReturnTheSecond() {
        when(this.mockReservable.hasGreaterCapacityThan(this.anotherMockReservable)).thenReturn(true);
        this.reservableList.add(this.anotherMockReservable);

        this.assigner.evaluateOneRequest(this.reservableRepository, this.mockRequest);

        verify(this.anotherMockReservable).isBooked();
    }
}
