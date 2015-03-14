package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Repository.Reservable.IReservableRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StrategyEvaluationFirstInFirstOutTest {

    private List<IReservable> reservableList;
    private IStrategyEvaluation evaluatorStrategy;
    @Mock
    private IReservable reservableMock;
    @Mock
    private IReservable anotherReservableMock;
    @Mock
    private Request requestMock;
    @Mock
    private IReservableRepository reservableRepositoryMock;
    @Mock
    private IReservationRepository reservationsRepositoryMock;

    @Before
    public void init() {
        evaluatorStrategy = new StrategyEvaluationFirstInFirstOut();
        reservableList = new ArrayList<>();
        when(reservableRepositoryMock.findAll()).thenReturn(reservableList);
    }

    @Test
    public void whenNoReservableAvailableReturnsEmptyEvaluationResult() {
        ReservableEvaluationResult reservableEvaluationResult = evaluatorStrategy.evaluateOneRequest(reservableRepositoryMock, reservationsRepositoryMock, requestMock);
        assertFalse(reservableEvaluationResult.matchFound());
    }

    @Test
    public void whenOnlyOneReservableAvailableReturnsNonEmptyEvaluationResultContainingTheReservable() throws Exception {
        reservableList.add(reservableMock);
        when(reservationsRepositoryMock.findReservationByReservable(reservableMock)).thenThrow(ReservationNotFoundException.class);

        ReservableEvaluationResult reservableEvaluationResult = evaluatorStrategy.evaluateOneRequest(reservableRepositoryMock, reservationsRepositoryMock, requestMock);

        assertEquals(reservableMock, reservableEvaluationResult.getBestReservableMatch());
    }

    @Test
    public void whenMultipleReservableAvailableReturnsNonEmptyEvaluationResultContainingTheFirstReservable() throws Exception {
        reservableList.add(anotherReservableMock);
        reservableList.add(reservableMock);
        when(reservationsRepositoryMock.findReservationByReservable(reservableMock)).thenThrow(ReservationNotFoundException.class).thenReturn(mock(Reservation.class)).thenThrow(ReservationNotFoundException.class);
        when(reservationsRepositoryMock.findReservationByReservable(anotherReservableMock)).thenThrow(ReservationNotFoundException.class).thenReturn(mock(Reservation.class)).thenThrow(ReservationNotFoundException.class);

        ReservableEvaluationResult reservableEvaluationResult = evaluatorStrategy.evaluateOneRequest(reservableRepositoryMock, reservationsRepositoryMock, requestMock);

        assertEquals(anotherReservableMock, reservableEvaluationResult.getBestReservableMatch());
    }

    @Test
    public void whenOneReservableCanTBeFoundReturnsEmptyEvaluationResult() throws Exception {
        reservableList.add(reservableMock);

        ReservableEvaluationResult reservableEvaluationResult = evaluatorStrategy.evaluateOneRequest(reservableRepositoryMock, reservationsRepositoryMock, requestMock);

        assertFalse(reservableEvaluationResult.matchFound());
    }
}