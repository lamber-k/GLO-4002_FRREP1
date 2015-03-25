package org.Marv1n.core.request.evaluation;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.reservation.Reservation;
import org.Marv1n.core.reservation.ReservationNotFoundException;
import org.Marv1n.core.persistence.ReservationRepository;
import org.Marv1n.core.room.Room;
import org.Marv1n.core.persistence.RoomRepository;
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

    private List<Room> roomList;
    private EvaluationStrategy assignerStrategy;
    @Mock
    private Room roomMock;
    @Mock
    private Room anotherRoomMock;
    @Mock
    private Request requestMock;
    @Mock
    private RoomRepository roomRepositoryMock;
    @Mock
    private ReservationRepository reservationsRepositoryMock;

    @Before
    public void initializeEvaluationFirstInFirstOutStrategy() {
        roomList = new ArrayList<>();
        assignerStrategy = new FirstInFirstOutEvaluationStrategy();
        when(roomRepositoryMock.findAll()).thenReturn(roomList);
    }

    @Test
    public void givenAssignationIsRun_WhenNoReservableAvailable_ThenReturnEmptyEvaluationResult() {
        ReservableEvaluationResult reservableEvaluationResult = assignerStrategy.evaluateOneRequest(roomRepositoryMock, reservationsRepositoryMock, requestMock);
        assertFalse(reservableEvaluationResult.matchFound());
    }

    @Test
    public void givenAssignationIsRun_WhenOnlyOneReservableAvailable_ThenReturnNonEmptyEvaluationResultContainingTheReservable() throws Exception {
        roomList.add(roomMock);
        when(reservationsRepositoryMock.findReservationByReservable(roomMock)).thenThrow(ReservationNotFoundException.class);

        ReservableEvaluationResult reservableEvaluationResult = assignerStrategy.evaluateOneRequest(roomRepositoryMock, reservationsRepositoryMock, requestMock);

        assertEquals(roomMock, reservableEvaluationResult.getBestReservableMatch());
    }

    @Test
    public void givenAssignationIsRun_WhenMultipleReservableAvailable_ThenReturnNonEmptyEvaluationResultContainingTheFirstReservable() throws Exception {
        roomList.add(anotherRoomMock);
        roomList.add(roomMock);
        when(reservationsRepositoryMock.findReservationByReservable(roomMock)).thenThrow(ReservationNotFoundException.class).thenReturn(mock(Reservation.class)).thenThrow(ReservationNotFoundException.class);
        when(reservationsRepositoryMock.findReservationByReservable(anotherRoomMock)).thenThrow(ReservationNotFoundException.class).thenReturn(mock(Reservation.class)).thenThrow(ReservationNotFoundException.class);

        ReservableEvaluationResult reservableEvaluationResult = assignerStrategy.evaluateOneRequest(roomRepositoryMock, reservationsRepositoryMock, requestMock);

        assertEquals(anotherRoomMock, reservableEvaluationResult.getBestReservableMatch());
    }

    @Test
    public void givenAssignationIsRun_WhenOneReservableCanNotBeFound_ThenReturnEmptyEvaluationResult() throws Exception {
        roomList.add(roomMock);

        ReservableEvaluationResult reservableEvaluationResult = assignerStrategy.evaluateOneRequest(roomRepositoryMock, reservationsRepositoryMock, requestMock);

        assertFalse(reservableEvaluationResult.matchFound());
    }
}