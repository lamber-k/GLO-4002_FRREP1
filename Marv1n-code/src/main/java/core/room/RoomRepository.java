package core.room;

import core.persistence.Repository;
import core.request.Request;

import java.util.List;

public interface RoomRepository extends Repository<Room> {

    List<Room> findAll();

    Room findRoomByAssociatedRequest(Request request) throws RoomNotFoundException;
}
