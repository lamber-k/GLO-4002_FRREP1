package Persistence;

import Persistence.RepositoryInMemory;
import org.Marv1n.code.Room.Room;
import org.Marv1n.code.Room.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RoomRepositoryInMemory extends RepositoryInMemory<Room> implements RoomRepository {

    @Override
    public List<Room> findAll() {
        return query().collect(Collectors.toList());
    }
}
