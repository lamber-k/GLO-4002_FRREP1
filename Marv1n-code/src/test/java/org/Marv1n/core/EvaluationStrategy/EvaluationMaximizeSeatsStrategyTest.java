package org.Marv1n.core.EvaluationStrategy;

import org.Marv1n.core.Room.RoomRepository;
import org.Marv1n.core.Reservation.ReservationNotFoundException;
import org.Marv1n.core.Reservation.ReservationRepository;
import Persistence.ReservationRepositoryInMemory;
import org.Marv1n.core.Request.Request;
import org.Marv1n.core.Room.Room;
import org.Marv1n.core.Reservation.Reservation;
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
public class EvaluationMaximizeSeatsStrategyTest {

    private List<Room> roomList;
    private EvaluationStrategy assignerStrategy;
    @Mock
    private Room roomMock;
    @Mock
    private Room anotherRoomMock;
    @Mock
    private Request requestMock;
    @Mock
    private Request anotherRequestMock;
    @Mock
    private RoomRepository roomRepositoryMock;
    @Mock
    private ReservationRepository reservationRepositoryMock;

    @Before
    public void initializeEvaluationMaximizeSeatsStrategy() throws ReservationNotFoundException {
        roomList = new ArrayList<>();
        assignerStrategy = new MaximizeSeatsEvaluationStrategy();
        roomList.add(roomMock);
        loadDefaultBehaviours();
    }

    private void loadDefaultBehaviours() throws ReservationNotFoundException {
        when(roomRepositoryMock.findAll()).thenReturn(roomList);
        when(roomMock.hasEnoughCapacity(anyInt())).thenReturn(true);
        when(anotherRoomMock.hasEnoughCapacity(anyInt())).thenReturn(true);
        when(reservationRepositoryMock.findReservationByReservable(any(Room.class))).thenThrow(ReservationNotFoundException.class);
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsNotBestThanFirst_ThenReturnTheFirst() throws ReservationNotFoundException {
        when(roomMock.hasGreaterOrEqualCapacityThan(anotherRoomMock)).thenReturn(false);
        roomList.add(anotherRoomMock);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(roomRepositoryMock, reservationRepositoryMock, requestMock);

        assertEquals(result.getBestReservableMatch(), roomMock);
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsBestThanFirst_ThenReturnTheSecond() throws ReservationNotFoundException {
        when(roomMock.hasGreaterOrEqualCapacityThan(anotherRoomMock)).thenReturn(true);
        roomList.add(anotherRoomMock);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(roomRepositoryMock, reservationRepositoryMock, requestMock);

        assertEquals(result.getBestReservableMatch(), anotherRoomMock);
    }

    @Test
    public void givenAssignationIsRun_WhenOneReservableCanNotBeFound_ThenReturnEmptyEvaluationResult() throws ReservationNotFoundException {
        ReservationRepositoryInMemory reservationsRepository = mock(ReservationRepositoryInMemory.class);
        when(reservationsRepository.findReservationByReservable(any(Room.class))).thenReturn(mock(Reservation.class));

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(roomRepositoryMock, reservationsRepository, requestMock);

        assertFalse(result.matchFound());
    }
}
