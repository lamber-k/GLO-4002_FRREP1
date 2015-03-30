package org.Marv1n.core.request.evaluation;

import infrastructure.persistence.ReservationRepositoryInMemory;
import org.Marv1n.core.persistence.ReservationRepository;
import org.Marv1n.core.persistence.RoomRepository;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.reservation.Reservation;
import org.Marv1n.core.reservation.ReservationNotFoundException;
import org.Marv1n.core.room.Room;
import org.Marv1n.core.room.RoomInsufficientSeats;
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

    private static final int REQUESTED_CAPACITY = 10;
    private List<Room> reservableList;
    private EvaluationStrategy assignerStrategy;
    @Mock
    private Room reservableMock;
    @Mock
    private Room anotherReservableMock;
    @Mock
    private Request requestMock;
    @Mock
    private Request anotherRequestMock;
    @Mock
    private RoomRepository reservableRepositoryMock;

    @Before
    public void initializeEvaluationMaximizeSeatsStrategy() throws ReservationNotFoundException {
        reservableList = new ArrayList<>();
        assignerStrategy = new MaximizeSeatsEvaluationStrategy();
        reservableList.add(reservableMock);
        loadDefaultBehaviours();
    }

    private void loadDefaultBehaviours() throws ReservationNotFoundException {
        when(reservableRepositoryMock.findAll()).thenReturn(reservableList);
        when(reservableMock.hasEnoughCapacity(anyInt())).thenReturn(true);
        when(anotherReservableMock.hasEnoughCapacity(anyInt())).thenReturn(true);
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsNotBestThanFirst_ThenReturnTheFirst() throws ReservationNotFoundException, RoomInsufficientSeats {
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(REQUESTED_CAPACITY);
        when(reservableMock.isReserved()).thenReturn(false);
        when(anotherReservableMock.isReserved()).thenReturn(false);
        when(anotherReservableMock.getBestFit(reservableMock, REQUESTED_CAPACITY)).thenReturn(reservableMock);
        reservableList.add(anotherReservableMock);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, requestMock);

        assertEquals(reservableMock, result.getBestReservableMatch());
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsBestThanFirst_ThenReturnTheSecond() throws ReservationNotFoundException, RoomInsufficientSeats {
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(REQUESTED_CAPACITY);
        when(reservableMock.isReserved()).thenReturn(false);
        when(anotherReservableMock.isReserved()).thenReturn(false);
        when(anotherReservableMock.getBestFit(reservableMock, REQUESTED_CAPACITY)).thenReturn(anotherReservableMock);
        reservableList.add(anotherReservableMock);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, requestMock);

        assertEquals(anotherReservableMock, result.getBestReservableMatch());
    }

    @Test
    public void givenAssignationIsRun_WhenNoRoomAvailable_ThenReturnEmptyEvaluationResult() throws ReservationNotFoundException {
        when(reservableMock.isReserved()).thenReturn(true);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, requestMock);

        assertFalse(result.matchFound());
    }

    @Test
    public void givenAssignationIsRun_WhenNoRoomHasEnoughSeats_ThenReturnEmptyEvaluationResult() throws ReservationNotFoundException {
        when(reservableMock.isReserved()).thenReturn(false);
        when(anotherReservableMock.isReserved()).thenReturn(false);
        when(reservableMock.hasEnoughCapacity(REQUESTED_CAPACITY)).thenReturn(false);
        when(anotherReservableMock.hasEnoughCapacity(REQUESTED_CAPACITY)).thenReturn(false);
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(REQUESTED_CAPACITY);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, requestMock);

        assertFalse(result.matchFound());
    }

}