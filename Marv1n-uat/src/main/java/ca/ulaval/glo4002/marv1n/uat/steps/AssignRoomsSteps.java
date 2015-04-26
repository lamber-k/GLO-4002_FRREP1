package ca.ulaval.glo4002.marv1n.uat.steps;

import ca.ulaval.glo4002.core.PendingRequests;
import ca.ulaval.glo4002.core.RequestTreatmentTaskFactory;
import ca.ulaval.glo4002.core.TaskScheduler;
import ca.ulaval.glo4002.core.notification.Notification;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.evaluation.MaximizeSeatsEvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestByPriorityStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StatefulStep;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StepState;
import ca.ulaval.glo4002.persistence.inmemory.RequestRepositoryInMemory;
import ca.ulaval.glo4002.persistence.inmemory.RoomRepositoryInMemory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssignRoomsSteps extends StatefulStep<AssignRoomsSteps.AssignRoomsStepsState> {

    @Override
    protected AssignRoomsStepsState getInitialState() {
        return new AssignRoomsStepsState();
    }

    @Given("a new reservation")
    public void givenANewReservation() {
        addAPendingRequest();
    }

    private void addAPendingRequest() {
        state().request = new Request(5, 5, new Person());
        state().pendingRequests = new PendingRequests(2);
        state().pendingRequests.addRequest(state().request);
    }

    @Given("a maximize seats evaluation strategy")
    public void givenAMaximizeSeatsEvaluationStrategy() {
        state().evaluationStrategy = new MaximizeSeatsEvaluationStrategy();
        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemory, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemory);
    }

    @Given("a first assigned room with lower capacity")
    public void givenAFirstAssignedRoomWithLowerCapacity() throws RoomAlreadyReservedException {
        state().firstRoom = new Room(5, "Premiere salle");
        state().firstRoom.book(mock(Request.class));
        state().roomRepositoryInMemory.persist(state().firstRoom);
    }

    @Given("a second unassigned room with medium capacity")
    public void givenASecondUnassignedRoomWithMediumCapacity() {
        state().secondRoom = new Room(7, "Seconde salle");
        state().roomRepositoryInMemory.persist(state().secondRoom);
    }

    @Given("a third unassigned room with higher capacity")
    public void givenAThirdUnassignedRoomWithHigherCapacity() {
        state().thirdRoom = new Room(10, "Troisieme salle");
        state().roomRepositoryInMemory.persist(state().thirdRoom);
    }

    @Given("a first unassigned room with medium capacity")
    public void givenAFirstUnassignedRoomWithMediumCapacity() {
        state().firstRoom = new Room(7, "premiere salle");
        state().roomRepositoryInMemory.persist(state().firstRoom);
    }

    @When("I treat with the maximize seats evaluation strategy")
    public void whenITreatWithTheMaximizeSeatsEvaluationStrategy() {
        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
        state().taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), 1, TimeUnit.SECONDS, state().requestTreatmentTaskFactory);
        state().taskScheduler.run();
    }

    @Then("the unassigned room with minimum seats, but enough, should have been assigned")
    public void thenTheUnassignedRoomWithMinimumSeatsButEnoughShouldHaveBeenAssigned() {
        assertTrue(state().secondRoom.isReserved());
    }

    @Then("an unassigned room with minimum seats, but enough, should have been assigned")
    public void thenAnUnassignedRoomWithMinimumSeatsButEnoughShouldHaveBeenAssigned() {
        assertTrue(state().firstRoom.isReserved() ^ state().secondRoom.isReserved());
    }

    public class AssignRoomsStepsState extends StepState {
        public Room firstRoom;
        public Room secondRoom;
        public Room thirdRoom;
        public Request request;
        public RequestTreatmentTaskFactory requestTreatmentTaskFactory;
        public EvaluationStrategy evaluationStrategy;
        public SortingRequestStrategy sortingRequestStrategy = new SortingRequestByPriorityStrategy();
        public RoomRepositoryInMemory roomRepositoryInMemory = new RoomRepositoryInMemory();
        public NotificationFactory notificationFactory = mock(NotificationFactory.class);
        public RequestRepositoryInMemory requestRepositoryInMemory = new RequestRepositoryInMemory();
        public TaskScheduler taskScheduler;
        public PendingRequests pendingRequests;
    }
}
