package ca.ulaval.glo4002.core.request.evaluation;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

    @Before
    public void initializeEvaluationFirstInFirstOutStrategy() {
        roomList = new ArrayList<>();
        assignerStrategy = new FirstInFirstOutEvaluationStrategy();
        when(roomRepositoryMock.findAll()).thenReturn(roomList);
    }

    @Test(expected = EvaluationNoRoomFoundException.class)
    public void givenAssignationIsRun_WhenNoReservableAvailable_ThenShouldThrowNoRoomFound() throws EvaluationNoRoomFoundException {
        assignerStrategy.evaluateOneRequest(roomRepositoryMock, requestMock);
    }

    @Test
    public void givenAssignationIsRun_WhenOnlyOneRoomAvailable_ThenReturnThisRoom() throws EvaluationNoRoomFoundException {
        roomList.add(roomMock);

        Room roomFound = assignerStrategy.evaluateOneRequest(roomRepositoryMock, requestMock);

        assertEquals(roomMock, roomFound);
    }

    @Test
    public void givenAssignationIsRun_WhenMultipleRoomsAvailable_ThenReturnTheFirstRoomAvailable() throws EvaluationNoRoomFoundException {
        roomList.add(anotherRoomMock);
        roomList.add(roomMock);

        Room roomFound = assignerStrategy.evaluateOneRequest(roomRepositoryMock, requestMock);

        assertEquals(anotherRoomMock, roomFound);
    }

    @Test
    public void givenAssignationIsRun_WhenSecondRoomAvailable_ThenReturnTheSecondRoom() throws EvaluationNoRoomFoundException {
        roomList.add(anotherRoomMock);
        roomList.add(roomMock);
        when(anotherRoomMock.isReserved()).thenReturn(true);

        Room roomFound = assignerStrategy.evaluateOneRequest(roomRepositoryMock, requestMock);

        assertEquals(roomMock, roomFound);
    }
}