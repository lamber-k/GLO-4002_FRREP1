package ca.ulaval.glo4002.marv1n.uat.steps;

import ca.ulaval.glo4002.core.PendingRequests;
import ca.ulaval.glo4002.core.RequestTreatmentTaskFactory;
import ca.ulaval.glo4002.core.TaskScheduler;
import ca.ulaval.glo4002.core.notification.Notification;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.RequestStatus;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.evaluation.FirstInFirstOutEvaluationStrategy;
import ca.ulaval.glo4002.core.request.evaluation.MaximizeSeatsEvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SequentialSortingRequestStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestByPriorityStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StatefulStep;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StepState;
import ca.ulaval.glo4002.persistence.inmemory.RequestRepositoryInMemory;
import ca.ulaval.glo4002.persistence.inmemory.RoomRepositoryInMemory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.mockito.InOrder;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class AssignRoomsSteps extends StatefulStep<AssignRoomsSteps.AssignRoomsStepsState> {

    private static final String FIRST_ROOM = "Une salle";
    private static final String SECOND_ROOM = "Une autre salle";
    private static final String THIRD_ROOM = "Une 3e salle";
    private static final int REQUEST_NUMBER_OF_SEATS_NEEDED = 5;
    private static final int REQUEST_PRIORITY = 5;
    private static final int MAXIMUM_PENDING_REQUESTS = 5;
    private static final int INTERVAL_TIMER = 10;
    private static final Person REQUEST_RESPONSIBLE = new Person();
    private static final int FIRST_ROOM_SEATS_NUMBER = 10;
    private static final int SECOND_ROOM_SEATS_NUMBER = 12;
    private static final int REQUEST_PRIORITY_1 = 1;
    private static final int REQUEST_PRIORITY_2 = 2;
    private static final int REQUEST_PRIORITY_3 = 3;

    @Override
    protected AssignRoomsStepsState getInitialState() {
        return new AssignRoomsStepsState();
    }

    private Room addRoomInRepository(int capacity, String roomName) {
        Room room = new Room(capacity, roomName);
        state().roomRepositoryInMemory.persist(room);
        return (room);
    }

    private Request addRequest(int numberOfSeatsNeeded, int requestPriority, Person requestResponsible) {
        Request request = mock(Request.class);
        when(request.getNumberOfSeatsNeeded()).thenReturn(numberOfSeatsNeeded);
        when(request.getPriority()).thenReturn(requestPriority);
        when(request.getResponsible()).thenReturn(requestResponsible);
        state().pendingRequests.addRequest(request);
        return (request);
    }

    @Given("a new pending reservation")
    public void givenANewPendingReservation() {
        state().secondRoom = addRoomInRepository(SECOND_ROOM_SEATS_NUMBER, SECOND_ROOM);
        state().firstRequest = addRequest(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY, REQUEST_RESPONSIBLE);
    }

    @Given("pending reservation assigned to the first available room")
    public void GivenPendingReservationAssignedToTheFirstAvailableRoom() {
        state().evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
    }

    @Given("a request treatment with a scheduler")
    public void givenARequestTreatmentWithAScheduler() {
        state().firstRoom = addRoomInRepository(FIRST_ROOM_SEATS_NUMBER, FIRST_ROOM);
        state().firstRequest = mock(Request.class);
        state().pendingRequests.addRequest(state().firstRequest);
        state().evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
        state().sortingRequestStrategy = new SequentialSortingRequestStrategy();
        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemory, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemory);
        state().taskScheduler = spy(new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), INTERVAL_TIMER, TimeUnit.MINUTES, state().requestTreatmentTaskFactory));
    }

    @Given("multiple pending reservation")
    public void givenMultiplePendingReservation() {
        state().firstRequest = addRequest(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY, REQUEST_RESPONSIBLE);
        state().secondRequest = addRequest(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY, REQUEST_RESPONSIBLE);
        state().thirdRequest = addRequest(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY, REQUEST_RESPONSIBLE);
        state().firstRoom = addRoomInRepository(FIRST_ROOM_SEATS_NUMBER, FIRST_ROOM);
        state().secondRoom = addRoomInRepository(SECOND_ROOM_SEATS_NUMBER, SECOND_ROOM);
        state().thirdRoom = addRoomInRepository(FIRST_ROOM_SEATS_NUMBER, THIRD_ROOM);
        state().inOrder = inOrder(state().firstRequest, state().secondRequest, state().thirdRequest);
    }

    @Given("pending reservation treated sequentially")
    public void givenPendingReservationTreatedSequentially() {
        state().sortingRequestStrategy = new SequentialSortingRequestStrategy();
    }

    @Given("pending reservation with different capacity needed")
    public void givenPendingReservationWithDifferentCapacityNeeded() {
        // PENDING
    }

    @Given("a maximize strategy")
    public void givenAMaximizeStrategy() {
        state().evaluationStrategy = new MaximizeSeatsEvaluationStrategy();
    }

    @Given("multiple rooms with same capacity")
    public void givenMultipleRoomsWithSameCapacity() {
        // PENDING
    }

    @Given("multiple pending reservation with same priority")
    public void givenMultiplePendingReservationWithSamePriority() {
        state().firstRequest = addRequest(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY, REQUEST_RESPONSIBLE);
        state().secondRequest = addRequest(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY, REQUEST_RESPONSIBLE);
        state().thirdRequest = addRequest(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY, REQUEST_RESPONSIBLE);
        state().firstRoom = addRoomInRepository(FIRST_ROOM_SEATS_NUMBER, FIRST_ROOM);
        state().secondRoom = addRoomInRepository(SECOND_ROOM_SEATS_NUMBER, SECOND_ROOM);
        state().thirdRoom = addRoomInRepository(FIRST_ROOM_SEATS_NUMBER, THIRD_ROOM);
        state().inOrder = inOrder(state().firstRequest, state().secondRequest, state().thirdRequest);
    }

    @Given("pending reservation treated by priority")
    public void givenPendingReservationTreatedByPriority() {
        state().sortingRequestStrategy = new SortingRequestByPriorityStrategy();
    }

    @Given("multiple pending reservation with different priority")
    public void givenMultiplePendingReservationWithDifferentPriority() {
        state().firstRequest = addRequest(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY_1, REQUEST_RESPONSIBLE);
        state().secondRequest = addRequest(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY_3, REQUEST_RESPONSIBLE);
        state().thirdRequest = addRequest(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY_2, REQUEST_RESPONSIBLE);
        state().firstRoom = addRoomInRepository(FIRST_ROOM_SEATS_NUMBER, FIRST_ROOM);
        state().secondRoom = addRoomInRepository(SECOND_ROOM_SEATS_NUMBER, SECOND_ROOM);
        state().thirdRoom = addRoomInRepository(FIRST_ROOM_SEATS_NUMBER, THIRD_ROOM);
        state().inOrder = inOrder(state().firstRequest, state().thirdRequest, state().secondRequest);
    }

    @When("I treat pending reservation")
    public void whenITreatPendingRequestsToTheFirstAvailableRoom() {
        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemory, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemory);
        state().taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), INTERVAL_TIMER, TimeUnit.SECONDS, state().requestTreatmentTaskFactory);
        state().taskScheduler.run();
    }

    @When("I start the scheduler to call the request treatment every $period minutes")
    public void whenIStartTheSchedulerToCallTheRequestTreatmentEvery1Minutes(int period) {
        state().taskScheduler.setIntervalTimer(period);
    }

    @When("the limit of pending reservation is reached")
    public void whenTheLimitOfPendingReservationIsReached() {
        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemory, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemory);
        state().taskScheduler = spy(new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), INTERVAL_TIMER, TimeUnit.SECONDS, state().requestTreatmentTaskFactory));
        state().pendingRequests.setScheduler(state().taskScheduler);
        state().pendingRequests.setMaximumPendingRequests(1);
    }

    @Then("the reservation should be assigned to the first available room")
    public void thenTheRequestShouldBeAssignedToTheFirstAvailableRoom() {
        assertEquals(state().firstRoom, state().firstRequest.getReservedRoom());
    }

    @Then("pending reservations are being treated periodically")
    public void thenPendingReservationsAreBeingTreatedPeriodically() {
      //  verify(state().taskScheduler, timeout(60 * 1000).atLeastOnce()).run();
    }

    @Then("pending reservation are being treated in order")
    public void thenPendingReservationsAreBeingTreatedInOrder() {
        state().inOrder.verify(state().firstRequest).reserve(any(Room.class));
        state().inOrder.verify(state().secondRequest).reserve(any(Room.class));
        state().inOrder.verify(state().thirdRequest).reserve(any(Room.class));
    }

    @Then("the pending reservation are being immediately treated")
    public void thenThePendingReservationAreBeingImmediatelyTreated() {
        assertNotEquals(RequestStatus.PENDING, state().firstRequest.getRequestStatus());
        assertNotEquals(RequestStatus.PENDING, state().secondRequest.getRequestStatus());
        assertNotEquals(RequestStatus.PENDING, state().thirdRequest.getRequestStatus());
    }

    @Then("the scheduler restart the timer")
    public void thenTheSchedulerRestartTheTimer() {
        verify(state().taskScheduler).runNow();
    }

    @Then("pending reservation are being treated in order of priority")
    public void thenPendingReservationAreBeingTreatedInOrderOfPriority() {
        state().inOrder.verify(state().firstRequest).reserve(any(Room.class));
        state().inOrder.verify(state().thirdRequest).reserve(any(Room.class));
        state().inOrder.verify(state().secondRequest).reserve(any(Room.class));
    }

    @Then("reservations should have been assigned in order to maximize capacity")
    public void thenReservationShouldHaveBeenAssignedInOrderToMaximizeCapacity() {
        // PENDING
    }

    @Then("reservation should have been assigned to one of the room")
    public void thenReservationShouldHaveBeenAssignedToOneOfTheRoom() {
        // PENDING
    }

    public class AssignRoomsStepsState extends StepState {

        public Room firstRoom;
        public Room secondRoom;
        public Room thirdRoom;
        public Request firstRequest;
        public Request secondRequest;
        public Request thirdRequest;
        public PendingRequests pendingRequests;
        public RequestTreatmentTaskFactory requestTreatmentTaskFactory;
        public TaskScheduler taskScheduler;
        public NotificationFactory notificationFactory;
        public RoomRepository roomRepositoryInMemory;
        public RequestRepository requestRepositoryInMemory;
        public SortingRequestStrategy sortingRequestStrategy;
        public EvaluationStrategy evaluationStrategy;
        public InOrder inOrder;

        public AssignRoomsStepsState() {
            this.pendingRequests = new PendingRequests(MAXIMUM_PENDING_REQUESTS);
            this.evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
            this.sortingRequestStrategy = new SequentialSortingRequestStrategy();
            this.notificationFactory = mock(NotificationFactory.class);
            when(this.notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
            this.roomRepositoryInMemory = new RoomRepositoryInMemory();
            this.requestRepositoryInMemory = new RequestRepositoryInMemory();
            this.sortingRequestStrategy = new SequentialSortingRequestStrategy();
            this.evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
        }
    }
}