package org.Marv1n.core.room;

import org.Marv1n.core.persistence.Repository;

import java.util.List;

public interface RoomRepository extends Repository<Room> {

    List<Room> findAll();
}
