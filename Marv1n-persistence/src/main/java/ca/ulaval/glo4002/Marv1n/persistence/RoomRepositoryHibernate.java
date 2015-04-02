package ca.ulaval.glo4002.Marv1n.persistence;

import ca.ulaval.glo4002.Marv1n.persistence.hibernate.EntityManagerProvider;
import core.request.Request;
import core.room.Room;
import core.room.RoomNotFoundException;
import core.room.RoomRepository;

import java.util.List;

public class RoomRepositoryHibernate extends RepositoryHibernate<Room> implements RoomRepository {
    public RoomRepositoryHibernate() {
        super(new EntityManagerProvider().getEntityManager());
    }

    @Override
    public List<Room> findAll() {
        return null;
    }

    @Override
    public Room findRoomByAssociatedRequest(Request request) throws RoomNotFoundException {
        return null;
    }
}
