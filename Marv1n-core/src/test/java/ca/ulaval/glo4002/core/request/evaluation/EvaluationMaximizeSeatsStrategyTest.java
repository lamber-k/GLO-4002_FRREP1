package ca.ulaval.glo4002.core.request.evaluation;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomInsufficientSeatsException;
import ca.ulaval.glo4002.core.room.RoomRepository;
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
    private Room roomMock;
    @Mock
    private Room anotherRoomMock;
    @Mock
    private Request requestMock;
    @Mock
    private Request anotherRequestMock;
    @Mock
    private RoomRepository roomRepositoryMock;

    @Before
    public void initializeEvaluationMaximizeSeatsStrategy() {
        reservableList = new ArrayList<>();
        assignerStrategy = new MaximizeSeatsEvaluationStrategy();
        reservableList.add(roomMock);
        loadDefaultBehaviours();
    }

    private void loadDefaultBehaviours() {
        when(roomRepositoryMock.findAll()).thenReturn(reservableList);
        when(roomMock.hasEnoughCapacity(anyInt())).thenReturn(true);
        when(anotherRoomMock.hasEnoughCapacity(anyInt())).thenReturn(true);
        when(roomMock.isReserved()).thenReturn(false);
        when(anotherRoomMock.isReserved()).thenReturn(false);
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsNotBestThanFirst_ThenReturnTheFirst() throws RoomInsufficientSeatsException, EvaluationNoRoomFoundException {
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(REQUESTED_CAPACITY);
        when(anotherRoomMock.getBestFit(roomMock, REQUESTED_CAPACITY)).thenReturn(roomMock);
        reservableList.add(anotherRoomMock);

        Room result = assignerStrategy.evaluateOneRequest(roomRepositoryMock, requestMock);

        assertEquals(roomMock, result);
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsBestThanFirst_ThenReturnTheSecond() throws RoomInsufficientSeatsException, EvaluationNoRoomFoundException {
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(REQUESTED_CAPACITY);
        when(anotherRoomMock.getBestFit(roomMock, REQUESTED_CAPACITY)).thenReturn(anotherRoomMock);
        reservableList.add(anotherRoomMock);

        Room result = assignerStrategy.evaluateOneRequest(roomRepositoryMock, requestMock);

        assertEquals(anotherRoomMock, result);
    }

    @Test(expected = EvaluationNoRoomFoundException.class)
    public void givenAssignationIsRun_WhenNoRoomAvailable_ThenShouldThrowNoRoomFound() throws EvaluationNoRoomFoundException {
        when(roomMock.isReserved()).thenReturn(true);
        assignerStrategy.evaluateOneRequest(roomRepositoryMock, requestMock);
    }

    @Test(expected = EvaluationNoRoomFoundException.class)
    public void givenAssignationIsRun_WhenNoRoomHasEnoughSeats_ThenShouldThrowNoRoomFound() throws EvaluationNoRoomFoundException {
        when(roomMock.hasEnoughCapacity(REQUESTED_CAPACITY)).thenReturn(false);
        when(anotherRoomMock.hasEnoughCapacity(REQUESTED_CAPACITY)).thenReturn(false);
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(REQUESTED_CAPACITY);

        assignerStrategy.evaluateOneRequest(roomRepositoryMock, requestMock);
    }

}