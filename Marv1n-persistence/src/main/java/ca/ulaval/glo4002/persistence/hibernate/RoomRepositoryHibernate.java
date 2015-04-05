package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomNotFoundException;
import ca.ulaval.glo4002.core.room.RoomRepository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class RoomRepositoryHibernate extends RepositoryHibernate<Room> implements RoomRepository {
    public RoomRepositoryHibernate() {
        super(new EntityManagerProvider().getEntityManager());
    }

    @Override
    public List<Room> findAll() {
        return entityManager.createQuery("from Room").getResultList();
    }

    @Override
    public Room findRoomByAssociatedRequest(Request request) throws RoomNotFoundException {
        Query q = entityManager.createQuery("select r from Room r where r.associatedRequest = :requestSearched");
        q.setParameter("requestSearched", request);
        try {
            return (Room) q.getSingleResult();
        }
        catch (EntityNotFoundException | NoResultException e) {
            throw new RoomNotFoundException();
        }
    }
}
