package ca.ulaval.glo4002.core.request.evaluation;

import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomInsufficientSeatsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

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
    public void initializeEvaluationMaximizeSeatsStrategy() {
        reservableList = new ArrayList<>();
        assignerStrategy = new MaximizeSeatsEvaluationStrategy();
        reservableList.add(reservableMock);
        loadDefaultBehaviours();
    }

    private void loadDefaultBehaviours() {
        when(reservableRepositoryMock.findAll()).thenReturn(reservableList);
        when(reservableMock.hasEnoughCapacity(anyInt())).thenReturn(true);
        when(anotherReservableMock.hasEnoughCapacity(anyInt())).thenReturn(true);
        when(reservableMock.isReserved()).thenReturn(false);
        when(anotherReservableMock.isReserved()).thenReturn(false);
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsNotBestThanFirst_ThenReturnTheFirst() throws RoomInsufficientSeatsException, EvaluationNoRoomFoundException {
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(REQUESTED_CAPACITY);
        when(anotherReservableMock.getBestFit(reservableMock, REQUESTED_CAPACITY)).thenReturn(reservableMock);
        reservableList.add(anotherReservableMock);

        Room result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, requestMock);

        assertEquals(reservableMock, result);
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsBestThanFirst_ThenReturnTheSecond() throws RoomInsufficientSeatsException, EvaluationNoRoomFoundException {
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(REQUESTED_CAPACITY);
        when(anotherReservableMock.getBestFit(reservableMock, REQUESTED_CAPACITY)).thenReturn(anotherReservableMock);
        reservableList.add(anotherReservableMock);

        Room result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, requestMock);

        assertEquals(anotherReservableMock, result);
    }

    @Test(expected = EvaluationNoRoomFoundException.class)
    public void givenAssignationIsRun_WhenNoRoomAvailable_ThenShouldThrowNoRoomFound() throws EvaluationNoRoomFoundException {
        when(reservableMock.isReserved()).thenReturn(true);
        assignerStrategy.evaluateOneRequest(reservableRepositoryMock, requestMock);
    }

    @Test(expected = EvaluationNoRoomFoundException.class)
    public void givenAssignationIsRun_WhenNoRoomHasEnoughSeats_ThenShouldThrowNoRoomFound() throws EvaluationNoRoomFoundException {
        when(reservableMock.hasEnoughCapacity(REQUESTED_CAPACITY)).thenReturn(false);
        when(anotherReservableMock.hasEnoughCapacity(REQUESTED_CAPACITY)).thenReturn(false);
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(REQUESTED_CAPACITY);

        assignerStrategy.evaluateOneRequest(reservableRepositoryMock, requestMock);
    }

}