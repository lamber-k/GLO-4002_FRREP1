package org.Marv1n.core.Room;

import org.Marv1n.core.Persistence.Repository;

import java.util.List;

public interface RoomRepository extends Repository<Room> {

    List<Room> findAll();
}
