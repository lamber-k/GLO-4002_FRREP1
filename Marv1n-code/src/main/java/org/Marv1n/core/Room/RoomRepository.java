package org.Marv1n.core.Room;

import org.Marv1n.core.Repository.Repository;

import java.util.List;

public interface RoomRepository extends Repository<Room> {

    List<Room> findAll();
}
