package ca.ulaval.glo4002.applicationServices.services;

import ca.ulaval.glo4002.applicationServices.locator.LocatorService;
import ca.ulaval.glo4002.applicationServices.models.RequestInformationModel;
import ca.ulaval.glo4002.applicationServices.models.RequestModel;
import ca.ulaval.glo4002.applicationServices.models.RequestNotAcceptedInformationModel;
import ca.ulaval.glo4002.applicationServices.models.RequestsInformationModel;
import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.PendingRequests;
import ca.ulaval.glo4002.core.notification.mail.EmailValidator;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.RequestStatus;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomNotFoundException;
import ca.ulaval.glo4002.core.room.RoomRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class RequestServiceTest {

    //TODO Typo ?
    private static final String A_ROOM_NAME = "A Room Name";
    private static LocatorService locatorService = LocatorService.getInstance();
    private static RequestRepository requestRepository = mock(RequestRepository.class);
    private static RoomRepository roomRepository = mock(RoomRepository.class);
    private static PendingRequests pendingRequests = mock(PendingRequests.class);
    private static EmailValidator emailValidator = mock(EmailValidator.class);
    private static int NUMBER_PERSON = 2;
    private static String VALID_EMAIL = "aEmail@test.com";
    private static String INVALID_EMAIL = "INVALID";
    private static String NO_ASSIGNED_ROOM_MESSAGE = "Aucune salle n'est assignée.";
    private static int PRIORITY = 1;
    private static UUID AN_ID = UUID.randomUUID();
    private static UUID AN_OTHER_ID = UUID.randomUUID();
    private long A_DATE = 1;
    private Request request = mock(Request.class);
    private Request secondRequest = mock(Request.class);
    private Person person = mock(Person.class);
    private Room room = mock(Room.class);
    private List<Request> requestList;
    private RequestService requestService;
    private List<String> emailParticipants;
    private long A_LATER_DATE = 10;

    @BeforeClass
    public static void initializeLocatorServices() {
        locatorService.register(RequestRepository.class, requestRepository);
        locatorService.register(RoomRepository.class, roomRepository);
        locatorService.register(PendingRequests.class, pendingRequests);
        locatorService.register(EmailValidator.class, emailValidator);
        when(emailValidator.validateMailAddress(VALID_EMAIL)).thenReturn(true);
        when(emailValidator.validateMailAddress(INVALID_EMAIL)).thenReturn(false);
    }

    @AfterClass
    public static void finishTests() {
        locatorService.unregisterAll();
    }

    @Before
    public void initializeRequestService() {
        requestService = new RequestService();
        requestList = new ArrayList<>();
    }

    @Test
    public void givenAValidRequestModel_WhenAddRequest_ThenShouldCallPendingRequest() throws InvalidFormatException {
        emailParticipants = new ArrayList<>();
        emailParticipants.add(VALID_EMAIL);
        RequestModel requestModel = new RequestModel(NUMBER_PERSON, VALID_EMAIL, PRIORITY, emailParticipants);

        Request resultRequest = requestService.addRequest(requestModel);

        verify(pendingRequests).addRequest(resultRequest);
    }

    @Test
    public void givenAValidRequestModel_WhenAddRequest_ThenShouldReturnTheCreatedRequestCorrespondingToRequestModel() throws InvalidFormatException {
        emailParticipants = new ArrayList<>();
        emailParticipants.add(VALID_EMAIL);
        RequestModel requestModel = new RequestModel(NUMBER_PERSON, VALID_EMAIL, PRIORITY, emailParticipants);

        Request resultRequest = requestService.addRequest(requestModel);

        assertTrue(matchValidRequestModel(resultRequest, requestModel));
    }

    @Test(expected = InvalidFormatException.class)
    public void givenAnInvalidOrganizerEmailAddressInRequestModel_WhenAddRequest_ThenShouldThrowInvalidFormatException() throws InvalidFormatException {
        emailParticipants = new ArrayList<>();
        RequestModel requestModel = new RequestModel(NUMBER_PERSON, INVALID_EMAIL, PRIORITY, emailParticipants);

        requestService.addRequest(requestModel);
    }

    @Test(expected = InvalidFormatException.class)
    public void givenAnIInvalidParticipantEmailAddressInRequestModel_WhenAddRequest_ThenShouldThrowInvalidFormatException() throws InvalidFormatException {
        emailParticipants = new ArrayList<>();
        emailParticipants.add(INVALID_EMAIL);
        RequestModel requestModel = new RequestModel(NUMBER_PERSON, VALID_EMAIL, PRIORITY, emailParticipants);

        requestService.addRequest(requestModel);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenRequestService_WhenCallGetRequestByEmailAndNoRequestAssociatedFound_ThenShouldThrowObjectNotFoundException() throws ObjectNotFoundException, RequestNotFoundException {
        doThrow(RequestNotFoundException.class).when(requestRepository).findByResponsibleMail(any(String.class));
        when(pendingRequests.getCurrentPendingRequest()).thenReturn(requestList);

        requestService.getRequestByEmail(VALID_EMAIL);
    }

    @Test
    public void givenRequestService_WhenCallGetRequestByEmailAndRequestFoundInPendingRequest_ThenRequestsInformationModelShouldContainRequest() throws RequestNotFoundException, ObjectNotFoundException {
        initBehaviorOfUsedObjectInGetter();
        doThrow(RequestNotFoundException.class).when(requestRepository).findByResponsibleMail(any(String.class));
        requestList.add(request);
        when(pendingRequests.getCurrentPendingRequest()).thenReturn(requestList);
        when(request.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        RequestsInformationModel requestsInformationModel = requestService.getRequestByEmail(VALID_EMAIL);

        assertTrue(requestsInformationModel.getAcceptees().isEmpty());
        assertEquals(1, requestsInformationModel.getAutres().size());
        assertTrue(isRequestMatchingRequestNotAcceptedInformationModel(request, requestsInformationModel.getAutres().get(0)));
    }

    @Test
    public void givenRequestService_WhenCallGetRequestByEmailAndMultipleRequestFoundInPendingRequest_ThenRequestsInformationModelShouldContainRequestsInOrderOfArrival() throws RequestNotFoundException, ObjectNotFoundException {
        initBehaviorOfUsedObjectInGetter();
        doThrow(RequestNotFoundException.class).when(requestRepository).findByResponsibleMail(any(String.class));
        requestList.add(request);
        requestList.add(secondRequest);
        when(pendingRequests.getCurrentPendingRequest()).thenReturn(requestList);
        when(request.getRequestStatus()).thenReturn(RequestStatus.PENDING);
        when(secondRequest.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        RequestsInformationModel requestsInformationModel = requestService.getRequestByEmail(VALID_EMAIL);

        assertTrue(requestsInformationModel.getAcceptees().isEmpty());
        assertEquals(2, requestsInformationModel.getAutres().size());
        assertTrue(isRequestMatchingRequestNotAcceptedInformationModel(request, requestsInformationModel.getAutres().get(0)));
        assertTrue(isRequestMatchingRequestNotAcceptedInformationModel(secondRequest, requestsInformationModel.getAutres().get(1)));
    }

    @Test
    public void givenRequestService_WhenCallGetRequestByEmailAndRequestFoundInRequestRepository_ThenRequestsInformationModelShouldContainRequest() throws RequestNotFoundException, ObjectNotFoundException {
        initBehaviorOfUsedObjectInGetter();
        requestList.add(request);
        when(requestRepository.findByResponsibleMail(VALID_EMAIL)).thenReturn(requestList);
        when(pendingRequests.getCurrentPendingRequest()).thenReturn(new ArrayList<>());
        when(request.getRequestStatus()).thenReturn(RequestStatus.REFUSED);

        RequestsInformationModel requestsInformationModel = requestService.getRequestByEmail(VALID_EMAIL);

        assertTrue(requestsInformationModel.getAcceptees().isEmpty());
        assertEquals(1, requestsInformationModel.getAutres().size());
        assertTrue(isRequestMatchingRequestNotAcceptedInformationModel(request, requestsInformationModel.getAutres().get(0)));
    }

    @Test
    public void givenRequestService_WhenCallGetRequestByEmailAndMultipleRequestFoundInRequestRepository_ThenRequestsInformationModelShouldContainRequestsInOrderOfArrival() throws RequestNotFoundException, ObjectNotFoundException {
        initBehaviorOfUsedObjectInGetter();
        requestList.add(request);
        requestList.add(secondRequest);
        when(requestRepository.findByResponsibleMail(VALID_EMAIL)).thenReturn(requestList);
        when(pendingRequests.getCurrentPendingRequest()).thenReturn(new ArrayList<>());
        when(request.getRequestStatus()).thenReturn(RequestStatus.REFUSED);
        when(secondRequest.getRequestStatus()).thenReturn(RequestStatus.REFUSED);

        RequestsInformationModel requestsInformationModel = requestService.getRequestByEmail(VALID_EMAIL);

        assertTrue(requestsInformationModel.getAcceptees().isEmpty());
        assertEquals(2, requestsInformationModel.getAutres().size());
        assertTrue(isRequestMatchingRequestNotAcceptedInformationModel(request, requestsInformationModel.getAutres().get(0)));
        assertTrue(isRequestMatchingRequestNotAcceptedInformationModel(secondRequest, requestsInformationModel.getAutres().get(1)));
    }

    @Test
    public void givenRequestService_WhenCallGetRequestByEmailAndRequestFoundInRequestRepositoryAndPendingRequest_ThenRequestInformationModelShouldContainRequestInOrderOfArrival() throws RequestNotFoundException, ObjectNotFoundException {
        initBehaviorOfUsedObjectInGetter();
        requestList.add(request);
        doReturn(requestList).when(requestRepository).findByResponsibleMail(VALID_EMAIL);
        List<Request> pendingList = new ArrayList<>();
        pendingList.add(secondRequest);
        when(pendingRequests.getCurrentPendingRequest()).thenReturn(pendingList);
        when(request.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(secondRequest.getRequestStatus()).thenReturn(RequestStatus.PENDING);
        RequestsInformationModel requestsInformationModel = requestService.getRequestByEmail(VALID_EMAIL);

        assertEquals(1, requestsInformationModel.getAutres().size());
        assertEquals(1, requestsInformationModel.getAcceptees().size());
        assertTrue(isRequestMatchingRequestInformationModel(request, requestsInformationModel.getAcceptees().get(0)));
        assertTrue(isRequestMatchingRequestNotAcceptedInformationModel(secondRequest, requestsInformationModel.getAutres().get(0)));
    }

    @Test
    public void givenRequestService_WhenCallGetRequestByEmailAndMultipleAcceptedRequestFoundInRequestRepository_ThenRequestInformationModelShouldContainRequestInOrderOfArrival() throws RequestNotFoundException, ObjectNotFoundException {
        initBehaviorOfUsedObjectInGetter();
        requestList.add(request);
        requestList.add(secondRequest);
        doReturn(requestList).when(requestRepository).findByResponsibleMail(VALID_EMAIL);
        when(pendingRequests.getCurrentPendingRequest()).thenReturn(new ArrayList<>());
        when(request.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(secondRequest.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        RequestsInformationModel requestsInformationModel = requestService.getRequestByEmail(VALID_EMAIL);

        assertTrue(requestsInformationModel.getAutres().isEmpty());
        assertEquals(2, requestsInformationModel.getAcceptees().size());
        assertTrue(isRequestMatchingRequestInformationModel(request, requestsInformationModel.getAcceptees().get(0)));
        assertTrue(isRequestMatchingRequestInformationModel(secondRequest, requestsInformationModel.getAcceptees().get(1)));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenRequestService_WhenCallGetRequestByEmailAndUUIDAndNoRequestCorrespondingIsFound_ThenShouldThrowObjectNotFoundException() throws RequestNotFoundException, ObjectNotFoundException {
        doThrow(RequestNotFoundException.class).when(requestRepository).findByUUID(AN_ID);
        requestService.getRequestByEmailAndId(VALID_EMAIL, AN_ID);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenRequestService_WhenCallGetRequestByEmailAndUUIDAndNoRequestCorrespondingIDIsFoundInPendingRequestButNotMatchingEmail_ThenShouldThrowObjectNotFoundException() throws RequestNotFoundException, ObjectNotFoundException {
        requestList.add(request);
        when(request.getRequestID()).thenReturn(AN_ID);
        when(request.getResponsible()).thenReturn(person);
        when(pendingRequests.getCurrentPendingRequest()).thenReturn(requestList);
        doThrow(RequestNotFoundException.class).when(requestRepository).findByUUID(AN_ID);

        requestService.getRequestByEmailAndId(VALID_EMAIL, AN_ID);
    }


    @Test
    public void givenRequestService_WhenCallGetRequestByEmailAndUUIDAndMatchFoundInPendingRequest_ThenShouldReturnMatchingRequestInformationModel() throws RequestNotFoundException, ObjectNotFoundException {
        requestList.add(request);
        initBehaviorOfUsedObjectInGetter();
        when(pendingRequests.getCurrentPendingRequest()).thenReturn(requestList);
        doThrow(RequestNotFoundException.class).when(requestRepository).findByUUID(AN_ID);

        RequestInformationModel requestInformationModel = requestService.getRequestByEmailAndId(VALID_EMAIL, AN_ID);

        assertTrue(isRequestMatchingRequestInformationModel(request, requestInformationModel));
    }

    @Test
    public void givenRequestService_WWhenCallGetRequestByEmailAndUUIDAndMatchFoundInRepositoryButNotMatchingEmail_ThenShouldReturnMatchingRequestInformationModel() throws RequestNotFoundException, ObjectNotFoundException, RoomNotFoundException {
        initBehaviorOfUsedObjectInGetter();
        when(requestRepository.findByUUID(any(UUID.class))).thenReturn(request);
        when(roomRepository.findRoomByAssociatedRequest(request)).thenReturn(room);

        RequestInformationModel requestInformationModel = requestService.getRequestByEmailAndId(VALID_EMAIL, AN_ID);

        assertTrue(isRequestMatchingRequestInformationModel(request, requestInformationModel));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenRequestService_WhenCallGetRequestByEmailAndUUIDAndNoRequestCorrespondingIDIsFoundInRepositoryButNotMatchingEmail_ThenShouldThrowObjectNotFoundException() throws RequestNotFoundException, ObjectNotFoundException, RoomNotFoundException {
        when(request.getRequestID()).thenReturn(AN_ID);
        when(request.getResponsible()).thenReturn(person);
        when(requestRepository.findByUUID(any(UUID.class))).thenReturn(request);
        when(roomRepository.findRoomByAssociatedRequest(request)).thenReturn(room);

        requestService.getRequestByEmailAndId(VALID_EMAIL, AN_ID);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenRequestService_WhenCallGetRequestByEmailAndUUIDAndNoRequestCorrespondingIDIsFoundInRepositoryButNotMatchingReservedRoomFound_ThenShouldThrowObjectNotFoundException() throws RequestNotFoundException, ObjectNotFoundException, RoomNotFoundException {
        when(request.getRequestID()).thenReturn(AN_ID);
        when(request.getResponsible()).thenReturn(person);
        when(requestRepository.findByUUID(any(UUID.class))).thenReturn(request);
        doThrow(RoomNotFoundException.class).when(roomRepository).findRoomByAssociatedRequest(request);

        requestService.getRequestByEmailAndId(VALID_EMAIL, AN_ID);
    }

    private boolean matchValidRequestModel(Request request, RequestModel model) {
        if (model.getNombrePersonne() == request.getNumberOfSeatsNeeded() &&
                model.getPriorite() == request.getPriority() &&
                model.getCourrielOrganisateur().equals(request.getResponsible().getMailAddress()) &&
                model.getParticipantsCourriels().size() == request.getParticipants().size() &&
                validateAllParticipantPresenceInRequest(request, model.getParticipantsCourriels())) {
            return true;
        }
        return false;
    }

    private boolean validateAllParticipantPresenceInRequest(Request request, List<String> emailParticipants) {
        int amount = emailParticipants.size();
        for (String email : emailParticipants) {
            for (Person person : request.getParticipants()) {
                if (person.getMailAddress().equals(email)) {
                    amount--;
                    break;
                }
            }
        }
        if (amount == 0) {
            return true;
        }
        return false;
    }

    private void initBehaviorOfUsedObjectInGetter() {
        when(request.getRequestID()).thenReturn(AN_ID);
        when(request.getResponsible()).thenReturn(person);
        when(request.getReservedRoom()).thenReturn(room);
        when(request.getCreationDate()).thenReturn(A_DATE);
        when(secondRequest.getRequestID()).thenReturn(AN_OTHER_ID);
        when(secondRequest.getResponsible()).thenReturn(person);
        when(secondRequest.getReservedRoom()).thenReturn(room);
        when(person.getMailAddress()).thenReturn(VALID_EMAIL);
        when(secondRequest.getCreationDate()).thenReturn(A_LATER_DATE);
        when(room.getName()).thenReturn(A_ROOM_NAME);
    }

    private boolean isRequestMatchingRequestInformationModel(Request request, RequestInformationModel requestInformationModel) {
        if (request.getResponsible().getMailAddress().equals(requestInformationModel.getCourrielOrginsateur()) &&
                request.getNumberOfSeatsNeeded() == requestInformationModel.getNombrePersonne() &&
                request.getRequestStatus() == requestInformationModel.getStatutDemande()) {
            if ((request.getRequestStatus() != RequestStatus.ACCEPTED &&
                    requestInformationModel.getSalleAssigne().equals(NO_ASSIGNED_ROOM_MESSAGE)) ||
                    (request.getRequestStatus() == RequestStatus.ACCEPTED &&
                            requestInformationModel.getSalleAssigne().equals(request.getReservedRoom().getName()))) {
                return true;
            }
        }
        return false;
    }

    private boolean isRequestMatchingRequestNotAcceptedInformationModel(Request request, RequestNotAcceptedInformationModel requestInformationModel) {
        if (request.getResponsible().getMailAddress().equals(requestInformationModel.getCourrielOrginsateur()) &&
                request.getNumberOfSeatsNeeded() == requestInformationModel.getNombrePersonne() &&
                request.getRequestStatus() == requestInformationModel.getStatutDemande()) {
            return true;
        }
        return false;
    }
}