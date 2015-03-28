package core.request.evaluation;

import core.persistence.ReservationRepository;
import core.persistence.RoomRepository;
import core.request.Request;
import core.reservation.Reservation;
import core.reservation.ReservationNotFoundException;
import core.room.Room;
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
public class MaximizeSeatsEvaluationStrategyTest {

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
    @Mock
    private ReservationRepository reservationRepositoryMock;

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
        when(reservationRepositoryMock.findReservationByReservable(any(Room.class))).thenThrow(ReservationNotFoundException.class);
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsNotBestThanFirst_ThenReturnTheFirst() throws ReservationNotFoundException {
        when(reservableMock.hasGreaterOrEqualCapacityThan(anotherReservableMock)).thenReturn(false);
        reservableList.add(anotherReservableMock);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationRepositoryMock, requestMock);

        assertEquals(result.getBestReservableMatch(), reservableMock);
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsBestThanFirst_ThenReturnTheSecond() throws ReservationNotFoundException {
        when(reservableMock.hasGreaterOrEqualCapacityThan(anotherReservableMock)).thenReturn(true);
        reservableList.add(anotherReservableMock);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationRepositoryMock, requestMock);

        assertEquals(result.getBestReservableMatch(), anotherReservableMock);
    }

    @Test
    public void givenAssignationIsRun_WhenOneReservableCanNotBeFound_ThenReturnEmptyEvaluationResult() throws ReservationNotFoundException {
        ReservationRepository reservationsRepository = mock(ReservationRepository.class);
        when(reservationsRepository.findReservationByReservable(any(Room.class))).thenReturn(mock(Reservation.class));

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationsRepository, requestMock);

        assertFalse(result.matchFound());
    }
}