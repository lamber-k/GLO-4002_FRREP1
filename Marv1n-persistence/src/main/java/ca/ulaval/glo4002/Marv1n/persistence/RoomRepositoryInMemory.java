package ca.ulaval.glo4002.Marv1n.persistence;

import core.request.Request;
import core.room.Room;
import core.room.RoomNotFoundException;
import core.room.RoomRepository;

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
        Optional<Room> roomFound = query().filter(r -> r.getRequest() == request).findFirst();

        if (!roomFound.isPresent())
            throw new RoomNotFoundException();
        return roomFound.get();
    }
}
