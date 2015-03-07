package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Repository.Reservable.IReservableRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        this.evaluator = new StrategyEvaluationFirstInFirstOut();

        this.reservableList = new ArrayList<>();

        when(reservableRepository.findAll()).thenReturn(this.reservableList);
    }

    @Test
    public void whenNoReservableAvailableReturnsEmptyEvaluationResult() {
        ReservableEvaluationResult reservableEvaluationResult = this.evaluator.evaluateOneRequest(this.reservableRepository, this.reservationsRepository, this.mockRequest);

        assertFalse(reservableEvaluationResult.matchFound());
    }

    @Test
    public void whenOnlyOneReservableAvailableReturnsNonEmptyEvaluationResultContainingTheReservable() throws Exception {
        this.reservableList.add(this.mockReservable);

        ReservableEvaluationResult reservableEvaluationResult = this.evaluator.evaluateOneRequest(this.reservableRepository, this.reservationsRepository, this.mockRequest);

        assertEquals(this.mockReservable, reservableEvaluationResult.getBestReservableMatch());
    }

    @Test
    public void whenMultipleReservableAvailableReturnsNonEmptyEvaluationResultContainingTheFirstReservable() throws Exception {
        this.reservableList.add(this.anotherMockReservable);
        this.reservableList.add(this.mockReservable);

        ReservableEvaluationResult reservableEvaluationResult = this.evaluator.evaluateOneRequest(this.reservableRepository, this.reservationsRepository, this.mockRequest);

        assertEquals(this.anotherMockReservable, reservableEvaluationResult.getBestReservableMatch());
    }
}