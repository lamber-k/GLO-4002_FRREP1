package org.Marv1n.code.EvaluationStrategy;

import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.Reservable;
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
public class EvaluationFirstInFirstOutStrategyTest {

    private List<Reservable> reservableList;
    private EvaluationStrategy assignerStrategy;
    @Mock
    private Reservable reservableMock;
    @Mock
    private Reservable anotherReservableMock;
    @Mock
    private Request requestMock;
    @Mock
    private ReservableRepository reservableRepositoryMock;
    @Mock
    private ReservationRepository reservationsRepositoryMock;

    @Before
    public void initializeEvaluationFirstInFirstOutStrategy() {
        reservableList = new ArrayList<>();
        assignerStrategy = new FirstInFirstOutEvaluationStrategy();
        when(reservableRepositoryMock.findAll()).thenReturn(reservableList);
    }

    @Test
    public void givenAssignationIsRun_WhenNoReservableAvailable_ThenReturnEmptyEvaluationResult() {
        ReservableEvaluationResult reservableEvaluationResult = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationsRepositoryMock, requestMock);
        assertFalse(reservableEvaluationResult.matchFound());
    }

    @Test
    public void givenAssignationIsRun_WhenOnlyOneReservableAvailable_ThenReturnNonEmptyEvaluationResultContainingTheReservable() throws Exception {
        reservableList.add(reservableMock);
        when(reservationsRepositoryMock.findReservationByReservable(reservableMock)).thenThrow(ReservationNotFoundException.class);

        ReservableEvaluationResult reservableEvaluationResult = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationsRepositoryMock, requestMock);

        assertEquals(reservableMock, reservableEvaluationResult.getBestReservableMatch());
    }

    @Test
    public void givenAssignationIsRun_WhenMultipleReservableAvailable_ThenReturnNonEmptyEvaluationResultContainingTheFirstReservable() throws Exception {
        reservableList.add(anotherReservableMock);
        reservableList.add(reservableMock);
        when(reservationsRepositoryMock.findReservationByReservable(reservableMock)).thenThrow(ReservationNotFoundException.class).thenReturn(mock(Reservation.class)).thenThrow(ReservationNotFoundException.class);
        when(reservationsRepositoryMock.findReservationByReservable(anotherReservableMock)).thenThrow(ReservationNotFoundException.class).thenReturn(mock(Reservation.class)).thenThrow(ReservationNotFoundException.class);

        ReservableEvaluationResult reservableEvaluationResult = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationsRepositoryMock, requestMock);

        assertEquals(anotherReservableMock, reservableEvaluationResult.getBestReservableMatch());
    }

    @Test
    public void givenAssignationIsRun_WhenOneReservableCanNotBeFound_ThenReturnEmptyEvaluationResult() throws Exception {
        reservableList.add(reservableMock);

        ReservableEvaluationResult reservableEvaluationResult = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationsRepositoryMock, requestMock);

        assertFalse(reservableEvaluationResult.matchFound());
    }
}