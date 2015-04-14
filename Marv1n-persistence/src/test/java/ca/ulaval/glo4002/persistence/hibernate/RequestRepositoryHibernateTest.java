package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

@RunWith(MockitoJUnitRunner.class)
public class RequestRepositoryHibernateTest {

    private static final UUID REQUEST_ID = UUID.randomUUID();
    private static final int REQUEST_PRIORITY = 4;
    private static final Person RESPONSIBLE_PERSON = new Person("person@mail.com");
    private static final int NUMBER_OF_SEATS = 42;
    private EntityManagerFactory entityManagerFactory;
    private RequestRepositoryHibernate requestRepository;
    private Request request;

    @Before
    public void initializeRequestRepositoryHibernate() throws Exception {
        entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        EntityManagerProvider.setEntityManager(entityManagerFactory.createEntityManager());
        requestRepository = new RequestRepositoryHibernate();
        request = new Request(NUMBER_OF_SEATS, REQUEST_PRIORITY, RESPONSIBLE_PERSON);
        requestRepository.persist(request);
    }

    @Test(expected = RequestNotFoundException.class)
    public void givenEmptyRequestRepositoryHibernate_WhenReservableNotFound_ThenThrowNotFoundRequestException() throws RequestNotFoundException {
        requestRepository.findByUUID(REQUEST_ID);
    }

    @Test
    public void givenEmptyRequestRepositoryHibernate_WhenCreateReservable_ThenSameObjectCanBeFound() throws Exception {
        Request result = requestRepository.findByUUID(request.getRequestID());
        Assert.assertEquals(request, result);
    }

    @Test(expected = RequestNotFoundException.class)
    public void givenNotEmptyRequestRepositoryHibernate_WhenRemoveReservable_ThenFindDoNotFoundRequest() throws InvalidFormatException, RequestNotFoundException {
        Request toRemoveRequest = new Request(NUMBER_OF_SEATS, REQUEST_PRIORITY, RESPONSIBLE_PERSON);
        requestRepository.persist(toRemoveRequest);

        requestRepository.remove(toRemoveRequest);

        requestRepository.findByUUID(toRemoveRequest.getRequestID());
    }

    @Test
    public void givenRequestRepositoryContainingOnePendingRequest_WhenGetAllPendingRequest_ThenReturnThisRequest() throws InvalidFormatException {
        List<Request> pendingRequestList = requestRepository.findAllPendingRequest();

        Assert.assertEquals(Arrays.asList(request), pendingRequestList);
    }

    @Test
    public void givenRequestRepositoryHibernateContainingMultiplePendingRequest_WhenGetAllPendingRequest_ThenReturnArrayWithThePendingRequests() throws InvalidFormatException {
        Request anotherRequest = new Request(NUMBER_OF_SEATS, REQUEST_PRIORITY, RESPONSIBLE_PERSON);
        requestRepository.persist(anotherRequest);

        List<Request> pendingRequestList = requestRepository.findAllPendingRequest();

        Assert.assertThat(pendingRequestList, containsInAnyOrder(request, anotherRequest));
    }

    @After
    public void clearRequestTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Request r").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}