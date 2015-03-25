package org.Marv1n.core.persistence;

import org.Marv1n.core.persistence.Repository;
import org.Marv1n.core.room.Room;

import java.util.List;

public interface RoomRepository extends Repository<Room> {

    List<Room> findAll();
}
