package core.persistence;

import core.room.Room;

import java.util.List;

public interface RoomRepository extends Repository<Room> {

    List<Room> findAll();
}
