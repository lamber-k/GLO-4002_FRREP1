package org.Marv1n.core.room;

import org.Marv1n.core.persistence.Repository;
import org.Marv1n.core.request.Request;

import java.util.List;

public interface RoomRepository extends Repository<Room> {

    List<Room> findAll();

    Room findRoomByAssociatedRequest(Request request) throws RoomNotFoundException;
}
