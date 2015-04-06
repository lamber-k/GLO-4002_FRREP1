package ca.ulaval.glo4002.persistence;

import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.persistence.inMemory.RoomRepositoryInMemory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RoomRepositoryInMemoryTest {

    private RoomRepositoryInMemory roomRepositoryInMemory;
    @Mock
    private Room roomMock;

    @Before
    public void initializeReservableRepositoryInMemory() throws Exception {
        roomRepositoryInMemory = new RoomRepositoryInMemory();
    }

    @Test
    public void givenNotEmptyRepository_WhenFindAll_ThenReturnAllReservable() throws Exception {
        roomRepositoryInMemory.persist(roomMock);
        List<Room> results = roomRepositoryInMemory.findAll();
        Assert.assertFalse(results.isEmpty());
    }
}