package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RoomRepositoryHibernateTest {

    private static final int A_NUMBER_OF_SEATS = 42;
    private static final String A_NAME = "roomName";
    private RoomRepositoryHibernate roomRepositoryHibernate;
    private EntityManagerFactory entityManagerFactory;
    private Room room;
    @Mock
    private Request requestMock;

    @Before
    public void initializeEntityManager() {
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

    @Test
    public void givenAnExistingRoom_WhenPersistSameRoom_ThenShouldUpdateIt() throws Exception {
        roomRepositoryHibernate.persist(room);
        roomRepositoryHibernate.persist(room);

        List<Room> results = roomRepositoryHibernate.findAll();

        Assert.assertEquals(room, results.get(0));
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