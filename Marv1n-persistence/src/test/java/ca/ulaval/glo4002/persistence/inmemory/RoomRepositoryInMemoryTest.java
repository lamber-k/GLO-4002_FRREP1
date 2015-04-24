package ca.ulaval.glo4002.persistence.inmemory;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomRepositoryInMemoryTest {

    private RoomRepositoryInMemory roomRepositoryInMemory;
    @Mock
    private Room roomMock;
    @Mock
    private Room secondRoomMock;
    @Mock
    private Request mockRequest;

    @Before
    public void initializeReservableRepositoryInMemory() throws Exception {
        roomRepositoryInMemory = new RoomRepositoryInMemory();
    }

    @Test
    public void givenEmptyRepository_WhenFindAll_ThenReturnEmptyList() throws Exception {
        roomRepositoryInMemory.persist(roomMock);
        List<Room> results = roomRepositoryInMemory.findAll();
        assertEquals(1,results.size());
    }

    @Test
    public void givenRepositoryWithOneRoom_WhenFindAll_ThenReturnAListWithTheRoom() {
        roomRepositoryInMemory.persist(roomMock);

        List<Room> results = roomRepositoryInMemory.findAll();

        assertEquals(1,results.size());
        assertTrue(results.contains(roomMock));
    }

    @Test
    public void givenRepositoryWithTwoRoom_WhenFindAll_ThenReturnAListWithAllRoom() {
        roomRepositoryInMemory.persist(roomMock);
        roomRepositoryInMemory.persist(secondRoomMock);

        List<Room> results = roomRepositoryInMemory.findAll();

        assertEquals(2, results.size());
        assertTrue(results.contains(roomMock));
        assertTrue(results.contains(secondRoomMock));
    }

    @Test (expected = RoomNotFoundException.class)
    public void givenRepositoryNotContainingRoomAssociateWithSearchedRequestAssociation_WhenFindRoomByAssociatedRequest_ThenThrowRoomNotFoundException() throws RoomNotFoundException {
        roomRepositoryInMemory.persist(roomMock);
        roomRepositoryInMemory.findRoomByAssociatedRequest(mockRequest);
    }

    @Test
    public void givenRepositoryContainingRoomAssociateWithSearchedRequestAssociation_WhenFindRoomByAssociatedRequest_ThenReturnAssociatedRoom() throws RoomNotFoundException {
        roomRepositoryInMemory.persist(roomMock);
        roomRepositoryInMemory.persist(secondRoomMock);
        when(secondRoomMock.getRequest()).thenReturn(mockRequest);

        Room foundRoom = roomRepositoryInMemory.findRoomByAssociatedRequest(mockRequest);

        assertEquals(secondRoomMock,foundRoom);
    }

}