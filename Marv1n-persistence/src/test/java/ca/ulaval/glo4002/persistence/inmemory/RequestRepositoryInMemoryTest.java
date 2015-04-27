package ca.ulaval.glo4002.persistence.inmemory;

import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestRepositoryInMemoryTest {

    private RequestRepositoryInMemory requestRepository;
    private UUID requestID;
    private String RESPONSIBLE_EMAIL_ADDRESS = "aEmail@test.com";
    private String AN_EMAIL_ADDRESS = "anOtherEmail@test.com";
    @Mock
    private Request requestMock;
    @Mock
    private Person responsible;
    @Mock
    private Person anOtherPerson;

    @Before
    public void initializeRequestRepositoryInMemory() {
        requestRepository = new RequestRepositoryInMemory();
        requestID = UUID.randomUUID();
        when(requestMock.getRequestID()).thenReturn(requestID);
        initRequestResponsible();
        when(requestMock.getResponsible()).thenReturn(anOtherPerson);
    }

    @Test(expected = RequestNotFoundException.class)
    public void givenEmptyRequestRepositoryInMemory_WhenReservableNotFound_ThenThrowNotFoundRequestException() throws RequestNotFoundException {
        requestRepository.findByUUID(requestID);
    }

    @Test
    public void givenEmptyRequestRepositoryInMemory_WhenCreateReservable_ThenSameObjectCanBeFound() throws RequestNotFoundException {
        requestRepository.persist(requestMock);

        Request result = requestRepository.findByUUID(requestID);
        assertEquals(requestMock, result);
    }

    @Test(expected = RequestNotFoundException.class)
    public void givenNotEmptyRequestRepositoryInMemory_WhenRemoveReservable_ThenNotFound() throws RequestNotFoundException, ObjectNotFoundException {
        requestRepository.persist(requestMock);

        requestRepository.remove(requestMock);

        requestRepository.findByUUID(requestID);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenEmptyRequestRepositoryInMemory_WhenMissingReservableRemoved_ThenRepositoryThrowRequestNotFoundException() throws ObjectNotFoundException {
        requestRepository.remove(requestMock);
    }

    @Test(expected = RequestNotFoundException.class)
    public void givenRequestRepositoryNotContainingRequestAssociatedToTheResponsibleEmailResearched_WhenFindByResponsibleMail_ThenThrowRequestNotFoundException() throws RequestNotFoundException {
        requestRepository.persist(requestMock);
        requestRepository.findByResponsibleMail(RESPONSIBLE_EMAIL_ADDRESS);
    }

    @Test
    public void givenRequestRepositoryContainingRequestAssociatedToTheResponsibleEmailResearched_WhenFindByResponsibleMail_ThenReturnListOfReservationAssociated() throws RequestNotFoundException {
        Request anAssociatedRequest = mock(Request.class);
        Request anOtherAssociatedRequest = mock(Request.class);
        when(anAssociatedRequest.getResponsible()).thenReturn(responsible);
        when(anOtherAssociatedRequest.getResponsible()).thenReturn(responsible);
        requestRepository.persist(requestMock);
        requestRepository.persist(anAssociatedRequest);
        requestRepository.persist(anOtherAssociatedRequest);


        List<Request> resultList = requestRepository.findByResponsibleMail(RESPONSIBLE_EMAIL_ADDRESS);

        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(anAssociatedRequest));
        assertTrue(resultList.contains(anOtherAssociatedRequest));
    }

    private void initRequestResponsible() {
        when(responsible.getMailAddress()).thenReturn(RESPONSIBLE_EMAIL_ADDRESS);
        when(anOtherPerson.getMailAddress()).thenReturn(AN_EMAIL_ADDRESS);
    }
}
