package ca.ulaval.glo4002.marv1n.uat.steps;

import ca.ulaval.glo4002.core.*;
import ca.ulaval.glo4002.core.notification.Notification;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestStatus;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.evaluation.FirstInFirstOutEvaluationStrategy;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestModificationSteps extends StatefulStep<RequestModificationSteps.RequestModificationStepsState> {

    @Override
    protected RequestModificationStepsState getInitialState() {
        return new RequestModificationStepsState();
    }

    @Given("an existing assigned reservation")
    public void givenAnExistingAssignedReservation() throws RoomAlreadyReservedException {
        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
        addARoomToRepository();
        addAPendingRequest();
        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemory, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemory);
        state().taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), 1, TimeUnit.SECONDS, state().requestTreatmentTaskFactory);
        state().taskScheduler.run();
    }

    private void addARoomToRepository() {
        state().room = new Room(5, "Une salle");
        state().roomRepositoryInMemory.persist(state().room);
    }

    private void addAPendingRequest() {
        state().request = new Request(5, 5, new Person());
        state().pendingRequests = new PendingRequests(2);
        state().pendingRequests.addRequest(state().request);
    }

    @Given("an existing pending reservation")
    public void givenAnExistingPendingReservation() {
        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
        addAPendingRequest();
    }

    @When("I cancel this reservation")
    public void whenICancelThisReservation() throws ObjectNotFoundException {
        PendingRequests pendingRequests = state().pendingRequests;
        RequestRepositoryInMemory requestRepositoryInMemory = state().requestRepositoryInMemory;
        NotificationFactory notificationFactory = state().notificationFactory;
        Request request = state().request;
        state().requestCancellation = new RequestCancellation(pendingRequests, requestRepositoryInMemory, notificationFactory);
        state().requestCancellation.cancelRequestByUUID(request.getRequestID());
    }

    @Then("the assigned reservation should have been cancelled")
    public void thenTheAssignedReservationShouldHaveBeenCancelled() throws RequestNotFoundException {
        assertEquals(RequestStatus.CANCELED, state().requestRepositoryInMemory.findByUUID(state().request.getRequestID()).getRequestStatus());
    }

    @Then("the pending reservation should have been cancelled")
    public void thenThePendingReservationShouldHaveBeenCancelled() throws RequestNotFoundException {
        assertEquals(RequestStatus.CANCELED, state().request.getRequestStatus());
    }

    @Then("the status of the assigned reservation should have changed")
    public void thenTheStatusOfTheAssignedReservationShouldHaveChanged() throws RequestNotFoundException {
        assertEquals(RequestStatus.CANCELED, state().requestRepositoryInMemory.findByUUID(state().request.getRequestID()).getRequestStatus());
    }

    @Then("the room should have been unassigned")
    public void thenTheRoomShouldHaveBeenUnassigned() {
        assertEquals(null, state().roomRepositoryInMemory.findAll().get(0).getRequest());
    }

    @Then("the reservation should have been archived")
    public void thenTheAssignedReservationShouldHaveBeenArchived() throws RequestNotFoundException {
        assertEquals(state().request.getRequestID(), state().requestRepositoryInMemory.findByUUID(state().request.getRequestID()).getRequestID());
    }

    public class RequestModificationStepsState extends StepState {
        public Room room;
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
