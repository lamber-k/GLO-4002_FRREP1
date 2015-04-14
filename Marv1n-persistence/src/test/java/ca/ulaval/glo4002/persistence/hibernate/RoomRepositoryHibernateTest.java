package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.room.Room;
import org.hibernate.cfg.Configuration;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class RoomRepositoryHibernateTest {

    private static final int A_NUMBER_OF_SEATS = 42;
    private static final String A_NAME = "roomName";
    private RoomRepositoryHibernate roomRepositoryHibernate;
    private EntityManagerFactory entityManagerFactory;
    private Room room;

    @Before
    public void loadEntityManager() {
        entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        EntityManagerProvider.setEntityManager(entityManagerFactory.createEntityManager());
        roomRepositoryHibernate = new RoomRepositoryHibernate();
        room = new Room(A_NUMBER_OF_SEATS, A_NAME);
    }

    @Test
    public void givenNotEmptyRepository_WhenFindAll_ThenReturnAllReservable() throws Exception {
        roomRepositoryHibernate.persist(room);
        List<Room> results = roomRepositoryHibernate.findAll();
        Assert.assertFalse(results.isEmpty());
    }

    @After
    public void clearRoomTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Room r").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}