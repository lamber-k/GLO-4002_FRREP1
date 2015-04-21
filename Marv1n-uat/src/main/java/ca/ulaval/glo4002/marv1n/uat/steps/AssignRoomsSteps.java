package ca.ulaval.glo4002.marv1n.uat.steps;

import ca.ulaval.glo4002.core.PendingRequests;
import ca.ulaval.glo4002.core.RequestCancellation;
import ca.ulaval.glo4002.core.RequestTreatmentTaskFactory;
import ca.ulaval.glo4002.core.TaskScheduler;
import ca.ulaval.glo4002.core.notification.Notification;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.evaluation.FirstInFirstOutEvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestByPriorityStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StatefulStep;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StepState;
import ca.ulaval.glo4002.persistence.inmemory.RequestRepositoryInMemory;
import ca.ulaval.glo4002.persistence.inmemory.RoomRepositoryInMemory;
import org.jbehave.core.annotations.Given;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssignRoomsSteps extends StatefulStep<AssignRoomsSteps.AssignRoomsStepsState> {

    @Override
    protected AssignRoomsStepsState getInitialState() {
        return new AssignRoomsStepsState();
    }

    @Given("an existing pending reservation")
    public void givenAnExistingPendingReservation() {
        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
        addAPendingRequest();
        System.out.println("Assign Rooms Steps");
    }

    private void addAPendingRequest() {
        state().request = new Request(5, 5, new Person());
        state().pendingRequests = new PendingRequests(2);
        state().pendingRequests.addRequest(state().request);
    }

    @Given("some room")
    public void givenSomeRoom() {
        state().room = new Room(5, "Une salle");
        state().roomRepositoryInMemory.persist(state().room);
        state().anotherRoom = new Room(10, "Une autre salle");
        state().roomRepositoryInMemory.persist(state().anotherRoom);
    }

    public class AssignRoomsStepsState extends StepState {
        public Room room;
        public Room anotherRoom;
        public Request request;
        public RequestTreatmentTaskFactory requestTreatmentTaskFactory;
        public EvaluationStrategy evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
        public SortingRequestStrategy sortingRequestStrategy = new SortingRequestByPriorityStrategy();
        public RoomRepositoryInMemory roomRepositoryInMemory = new RoomRepositoryInMemory();
        public NotificationFactory notificationFactory = mock(NotificationFactory.class);
        public RequestRepositoryInMemory requestRepositoryInMemory = new RequestRepositoryInMemory();
        public RequestCancellation requestCancellation;
        public TaskScheduler taskScheduler;
        public PendingRequests pendingRequests;
    }
}
