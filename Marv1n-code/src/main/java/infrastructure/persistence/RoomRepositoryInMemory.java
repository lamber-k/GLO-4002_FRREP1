package infrastructure.persistence;

import core.room.Room;
import core.persistence.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RoomRepositoryInMemory extends RepositoryInMemory<Room> implements RoomRepository {

    @Override
    public List<Room> findAll() {
        return query().collect(Collectors.toList());
    }
}
