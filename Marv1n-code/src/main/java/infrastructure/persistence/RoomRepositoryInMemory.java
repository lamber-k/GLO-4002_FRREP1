package infrastructure.persistence;

import org.Marv1n.core.room.Room;
import org.Marv1n.core.persistence.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RoomRepositoryInMemory extends RepositoryInMemory<Room> implements RoomRepository {

    @Override
    public List<Room> findAll() {
        return query().collect(Collectors.toList());
    }
}
