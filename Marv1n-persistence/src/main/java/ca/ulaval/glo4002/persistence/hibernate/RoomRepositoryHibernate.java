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
        Query query = entityManager.createQuery("select r from Room r where r.associatedRequest = :requestSearched");
        query.setParameter("requestSearched", request);
        try {
            return (Room) query.getSingleResult();
        }
        catch (EntityNotFoundException | NoResultException exception) {
            throw new RoomNotFoundException();
        }
    }
}
