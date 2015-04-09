package ca.ulaval.glo4002.marv1n.uat.steps;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestStatus;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StatefulStep;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StepState;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class RequestModificationSteps extends StatefulStep<RequestModificationSteps.RequestStepsState> {

    protected RequestStepsState getInitialState() {
        return new RequestStepsState();
    }

    @Given("An existing reserved reservation")
    public void givenAnExistingReservedReservation() throws RoomAlreadyReservedException {
        state().room = new Room(5, "Une salle");
        state().room.reserve(new Request(5, 5, UUID.randomUUID()));
    }

    @Given("An existing pending reservation")
    public void givenAnExistingPendingReservation() {
        state().request = new Request(5, 5, UUID.randomUUID());
    }

    @Given("An existing reservation")
    public void givenAnExistingReservation() {

    }

    @When("I cancel a reserved reservation")
    public void whenICancelAReservedReservation() {
        state().room.cancelReservation();
    }

    @When("I cancel a pending reservation")
    public void whenICancelAPendingReservation() {
        state().request.cancel();
    }

    @Then("The reserved reservation is cancel")
    public void thenTheReservedReservationIsCancel() {
        assertEquals(RequestStatus.CANCELED, state().room.getRequest().getRequestStatus());
    }

    @Then("The pending reservation is cancel")
    public void thenThePendingReservationIsCancel() {
        assertEquals(RequestStatus.CANCELED, state().request.getRequestStatus());
    }

    public class RequestStepsState extends StepState {
        public Room room;
        public Request request;
    }
}
