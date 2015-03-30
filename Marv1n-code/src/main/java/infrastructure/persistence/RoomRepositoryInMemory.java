package infrastructure.persistence;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.room.Room;
import org.Marv1n.core.room.RoomRepository;
import org.Marv1n.core.room.RoomNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomRepositoryInMemory extends RepositoryInMemory<Room> implements RoomRepository {

    @Override
    public List<Room> findAll() {
        return query().collect(Collectors.toList());
    }

    @Override
    public Room findRoomByAssociatedRequest(Request request) throws RoomNotFoundException {
        Optional<Room> roomFound = query().filter((Room r) -> r.getRequest() == request).findFirst();

        if (!roomFound.isPresent())
            throw new RoomNotFoundException();
        return (roomFound.get());
    }
}
