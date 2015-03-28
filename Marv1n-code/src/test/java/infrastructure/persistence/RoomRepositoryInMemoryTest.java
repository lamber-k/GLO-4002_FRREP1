package infrastructure.persistence;

        import core.room.Room;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.Mock;
        import org.mockito.runners.MockitoJUnitRunner;

        import java.util.List;

        import static org.junit.Assert.assertFalse;

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
        assertFalse(results.isEmpty());
    }
}