package infrastructure.persistence;

import org.Marv1n.core.room.Room;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class RoomRepositoryInMemoryTest {

    private RoomRepositoryInMemory reservableRepository;
    @Mock
    private Room roomMock;

    @Before
    public void initializeReservableRepositoryInMemory() throws Exception {
        reservableRepository = new RoomRepositoryInMemory();
    }

    @Test
    public void givenNotEmptyRepository_WhenFindAll_ThenReturnAllReservable() throws Exception {
        reservableRepository.create(roomMock);
        List<Room> results = reservableRepository.findAll();
        assertFalse(results.isEmpty());
    }
}
