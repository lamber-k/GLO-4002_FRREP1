package ca.ulaval.glo4002.persistence;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomNotFoundException;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.persistence.hibernate.EntityManagerProvider;

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
