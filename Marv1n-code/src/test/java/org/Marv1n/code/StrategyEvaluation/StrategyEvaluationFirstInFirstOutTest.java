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
    private IStrategyEvaluation evaluator;
    @Mock
    private IReservable mockReservable;
    @Mock
    private IReservable anotherMockReservable;
    @Mock
    private Request mockRequest;
    @Mock
    private IReservableRepository reservableRepository;
    @Mock
    private IReservationRepository reservationsRepository;

    @Before
    public void init() {
        evaluator = new StrategyEvaluationFirstInFirstOut();

        reservableList = new ArrayList<>();

        when(reservableRepository.findAll()).thenReturn(reservableList);
    }

    @Test
    public void whenNoReservableAvailableReturnsEmptyEvaluationResult() {
        ReservableEvaluationResult reservableEvaluationResult = evaluator.evaluateOneRequest(reservableRepository, reservationsRepository, mockRequest);

        assertFalse(reservableEvaluationResult.matchFound());
    }

    @Test
    public void whenOnlyOneReservableAvailableReturnsNonEmptyEvaluationResultContainingTheReservable() throws Exception, ReservationNotFoundException {
        reservableList.add(mockReservable);
        when(reservationsRepository.findReservationByReservable(mockReservable)).thenThrow(ReservationNotFoundException.class);

        ReservableEvaluationResult reservableEvaluationResult = evaluator.evaluateOneRequest(reservableRepository, reservationsRepository, mockRequest);

        assertEquals(mockReservable, reservableEvaluationResult.getBestReservableMatch());
    }

    @Test
    public void whenMultipleReservableAvailableReturnsNonEmptyEvaluationResultContainingTheFirstReservable() throws Exception, ReservationNotFoundException {
        reservableList.add(anotherMockReservable);
        reservableList.add(mockReservable);
        when(reservationsRepository.findReservationByReservable(mockReservable)).thenThrow(ReservationNotFoundException.class).thenReturn(mock(Reservation.class));
        when(reservationsRepository.findReservationByReservable(anotherMockReservable)).thenThrow(ReservationNotFoundException.class);

        ReservableEvaluationResult reservableEvaluationResult = evaluator.evaluateOneRequest(reservableRepository, reservationsRepository, mockRequest);

        assertEquals(anotherMockReservable, reservableEvaluationResult.getBestReservableMatch());
    }

    @Test
    public void whenOneReservableCanTBeFoundReturnsEmptyEvaluationResult() throws Exception {
        reservableList.add(mockReservable);

        ReservableEvaluationResult reservableEvaluationResult = evaluator.evaluateOneRequest(reservableRepository, reservationsRepository, mockRequest);

        assertFalse(reservableEvaluationResult.matchFound());
    }
}