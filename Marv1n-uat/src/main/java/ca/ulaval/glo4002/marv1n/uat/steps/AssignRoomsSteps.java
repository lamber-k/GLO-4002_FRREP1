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
        state().pendingRequests = new PendingRequests(MAXIMUM_PENDING_REQUESTS);
        state().secondRoom = addRoomInRepository(SECOND_ROOM_SEATS_NUMBER, SECOND_ROOM);
        state().firstRequest = addRequest(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY, REQUEST_RESPONSIBLE);
        /*state().secondRoom = new Room(SECOND_ROOM_SEATS_NUMBER, SECOND_ROOM);
        state().roomRepositoryInMemory.persist(state().secondRoom);
        state().firstRequest = new Request(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY, REQUEST_RESPONSIBLE);
        state().pendingRequests.addRequest(state().firstRequest);*/
    }

    @Given("pending reservation assigned to the first available room")
    public void GivenPendingReservationAssignedToTheFirstAvailableRoom() {
        state().sortingRequestStrategy = new SequentialSortingRequestStrategy();
        state().evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
    }

    @Given("a request treatment with a scheduler")
    public void givenARequestTreatmentWithAScheduler() {
        state().pendingRequests = new PendingRequests(MAXIMUM_PENDING_REQUESTS);
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
        state().pendingRequests = new PendingRequests(MAXIMUM_PENDING_REQUESTS);
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
        state().evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
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
        // PENDING
    }

    @Given("pending reservation treated by priority")
    public void givenPendingReservationTreatedByPriority() {
        state().sortingRequestStrategy = new SortingRequestByPriorityStrategy();
    }

    @Given("multiple pending reservation with different priority")
    public void givenMultiplePendingReservationWithDifferentPriority() {
        // PENDING
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
        // PENDING
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
            this.notificationFactory = mock(NotificationFactory.class);
            when(notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
            this.roomRepositoryInMemory = new RoomRepositoryInMemory();
            this.requestRepositoryInMemory = new RequestRepositoryInMemory();
            this.sortingRequestStrategy = new SequentialSortingRequestStrategy();
            this.evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
        }
    }
}

//public class AssignRoomsSteps extends StatefulStep<AssignRoomsSteps.AssignRoomsStepsState> {
//
//    private static final int TWO_SECOND_IN_MILLIS = 2 * 1000;
//
//    @Override
//    protected AssignRoomsStepsState getInitialState() {
//        return new AssignRoomsStepsState();
//    }
//
//    @Given("a pendingRequest system")
//    public void givenAPendingRequestSystem(){
//        state().pendingRequests = new PendingRequests(5);
//    }
//
//    @Given("a new reservation")
//    public void givenANewReservation() {
//        addAPendingRequest();
//    }
//
//    private void addAPendingRequest() {
//        state().firstRequest = spy(new Request(5, 5, new Person()));
//        state().pendingRequests = new PendingRequests(2);
//        state().pendingRequests.addRequest(state().firstRequest);
//    }
//
//    @Given("a maximize seats evaluation strategy")
//    public void givenAMaximizeSeatsEvaluationStrategy() {
//        state().evaluationStrategy = new MaximizeSeatsEvaluationStrategy();
//        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemory, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemory);
//    }
//
//    @Given("a first assigned room with lower capacity")
//    public void givenAFirstAssignedRoomWithLowerCapacity() throws RoomAlreadyReservedException {
//        state().firstRoom = new Room(5, "Premiere salle");
//        state().firstRoom.book(mock(Request.class));
//        state().roomRepositoryInMemory.persist(state().firstRoom);
//    }
//
//    @Given("a second unassigned room with medium capacity")
//    public void givenASecondUnassignedRoomWithMediumCapacity() {
//        state().secondRoom = new Room(7, "Seconde salle");
//        state().roomRepositoryInMemory.persist(state().secondRoom);
//    }
//
//    @Given("a third unassigned room with higher capacity")
//    public void givenAThirdUnassignedRoomWithHigherCapacity() {
//        state().thirdRoom = new Room(10, "Troisieme salle");
//        state().roomRepositoryInMemory.persist(state().thirdRoom);
//    }
//
//    @Given("a first unassigned room with medium capacity")
//    public void givenAFirstUnassignedRoomWithMediumCapacity() {
//        state().firstRoom = new Room(7, "premiere salle");
//        state().roomRepositoryInMemory.persist(state().firstRoom);
//    }
//
//    @When("I treat with the maximize seats evaluation strategy")
//    public void whenITreatWithTheMaximizeSeatsEvaluationStrategy() {
//        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
//        state().taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), 1, TimeUnit.SECONDS, state().requestTreatmentTaskFactory);
//        state().taskScheduler.run();
//    }
//
//    @Then("the unassigned room with minimum seats, but enough, should have been assigned")
//    public void thenTheUnassignedRoomWithMinimumSeatsButEnoughShouldHaveBeenAssigned() {
//        assertTrue(state().secondRoom.isReserved());
//    }
//
//    @Then("an unassigned room with minimum seats, but enough, should have been assigned")
//    public void thenAnUnassignedRoomWithMinimumSeatsButEnoughShouldHaveBeenAssigned() {
//        assertTrue(state().firstRoom.isReserved() ^ state().secondRoom.isReserved());
//    }
//
//    @Given("multiple pending reservation with different priority")
//    public void givenMultiplePendingReservationWithDifferentPriority() {
//        state().firstRequest = spy(new Request(5, 1, new Person()));
//        state().secondRequest = spy(new Request(5, 3, new Person()));
//        state().thirdRequest = spy(new Request(5, 5, new Person()));
//        state().pendingRequests.addRequest(state().firstRequest);
//        state().pendingRequests.addRequest(state().secondRequest);
//        state().pendingRequests.addRequest(state().thirdRequest);
//    }
//
//    @Given("multiple available room fitting firstRequest")
//    public void givenMultipleAvailableRoomFittingRequests(){
//        state().firstRoom = spy(new Room(5, "a name"));
//        state().secondRoom = spy(new Room(5, "a second name"));
//        state().thirdRoom = spy(new Room(5, "a third name"));
//        state().roomRepositoryInMemory.persist(state().firstRoom);
//        state().roomRepositoryInMemory.persist(state().secondRoom);
//        state().roomRepositoryInMemory.persist(state().thirdRoom);
//    }
//
//
//    @Then("pending reservation are being treated in order of priority")
//    public void thenPendingReservationAreBeingTreatedInOrderOfPriority() {
//        InOrder inOrder = inOrder(state().firstRequest,state().secondRequest,state().thirdRequest);
//
//        inOrder.verify(state().firstRequest).reserve(any(Room.class));
//        inOrder.verify(state().secondRequest).reserve(any(Room.class));
//        inOrder.verify(state().thirdRequest).reserve(any(Room.class));
//    }
//
//    @Given("evaluation strategy assign to first available room")
//    public void givenPendingReservationAssignedToTheFirstAvailableRoom() {
//        state().evaluationStrategy = spy(new FirstInFirstOutEvaluationStrategy());
//    }
//
//    @When("I treat pending reservation")
//    public void whenITreatPendingReservation() {
//        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
//        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemory, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemory);
//        state().taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), 1, TimeUnit.SECONDS, state().requestTreatmentTaskFactory);
//        state().taskScheduler.run();
//    }
//
//    @Then("the reservation should be assigned to the first available room")
//    public void thenTheReservationShouldBeAssignedToTheFirstAvailableRoom() {
//        verify(state().firstRequest).reserve(state().firstRoom);
//    }
//
//    @Given("a firstRequest treatment with a scheduler")
//    public void givenARequestTreatmentWithAScheduler() {
//        state().taskScheduler = spy(new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), 1000, TimeUnit.SECONDS, state().requestTreatmentTaskFactory));
//    }
//
//    @When("I start the scheduler to call the firstRequest treatment periodically")
//    public void whenIStartTheSchedulerToCallTheRequestTreatmentPeriodically() {
//        state().taskScheduler.setIntervalTimer(2);
//        state().taskScheduler.startScheduler();
//    }
//
//    @Then("pending reservations are being treated periodically")
//    public void thenPendingReservationsAreBeingTreatedPeriodically() {
//        verify(state().taskScheduler,timeout(10*1000).atLeast(3)).run();
//    }
//
//    @Given("a new reservation with medium priority")
//    public void givenANewReservationWithMediumPriority() {
//        state().firstRequest = spy(new Request(3, 3, new Person()));
//        state().pendingRequests.addRequest(state().firstRequest);
//    }
//
//    @Given("a second reservation with medium priority")
//    public void givenSecondReservationWithMediumPriority() {
//        state().secondRequest = spy(new Request(3, 3, new Person()));
//        state().pendingRequests.addRequest(state().secondRequest);
//    }
//
//    @Given("a room fitting medium priority firstRequest")
//    public void givenARoomFittingMediumPriorityRequest() {
//        state().firstRoom = spy(new Room(3, "a name"));
//        state().roomRepositoryInMemory.persist(state().firstRoom);
//    }
//
//    @Given("a second room fitting medium priority firstRequest")
//    public void givenASecondRoomFittingMediumPriorityRequest() {
//        state().secondRoom = spy(new Room(3, "a second name"));
//        state().roomRepositoryInMemory.persist(state().secondRoom);
//    }
//
//    @Given("a room")
//    public void givenARoom() {
//        state().firstRoom = spy(new Room(5, "a name"));
//        state().roomRepositoryInMemory.persist(state().firstRoom);
//    }
//
//    @Given("a second room")
//    public void givenASecondRoom() {
//        state().secondRoom = spy(new Room(5, "a second name"));
//        state().roomRepositoryInMemory.persist(state().secondRoom);
//    }
//
//    @Given("an evaluation strategy")
//    public void givenAnEvaluationStrategy() {
//        state().evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
//    }
//
//    @Given("a sorting firstRequest strategy")
//    public void givenASortingRequestStrategy() {
//        givenASortingRequestByArrivalOrderStrategy();
//    }
//
//    @Given("a sorting firstRequest by priority strategy")
//    public void givenASortingRequestByPriorityStrategy() {
//        state().sortingRequestStrategy = spy(new SortingRequestByPriorityStrategy());
//    }
//
//    @Given("a sorting firstRequest by arrival order strategy")
//    public void givenASortingRequestByArrivalOrderStrategy() {
//        state().sortingRequestStrategy = spy(new SequentialSortingRequestStrategy());
//    }
//
//    @Then("same priority demands are treat in order of arrival")
//    public void thenSamePriorityDemandsAreTreatInOrderOfArrival() {
//        InOrder inOrder = inOrder(state().firstRequest, state().secondRequest);
//
//        inOrder.verify(state().firstRequest).reserve(any(Room.class));
//        inOrder.verify(state().secondRequest).reserve(any(Room.class));
//    }
//
//    @Then("the pending reservation are being immediately treated")
//    public void thenThePendingReservationAreBeingImmediatelyTreated() {
//        verify(state().taskScheduler, timeout(TWO_SECOND_IN_MILLIS).atLeastOnce()).runNow();
//        verify(state().taskScheduler, timeout(TWO_SECOND_IN_MILLIS).atLeastOnce()).run();
//    }
