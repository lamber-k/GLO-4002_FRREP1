package ca.ulaval.glo4002.marv1n.uat.fakes;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomNotFoundException;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.persistence.inMemory.RepositoryInMemory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomRepositoryInMemoryFake extends RepositoryInMemory<Room> implements RoomRepository {

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