package ca.ulaval.glo4002.core.room;

import ca.ulaval.glo4002.core.persistence.Repository;
import ca.ulaval.glo4002.core.request.Request;

import java.util.List;

public interface RoomRepository extends Repository<Room> {

    List<Room> findAll();

    Room findRoomByAssociatedRequest(Request request) throws RoomNotFoundException;
}
