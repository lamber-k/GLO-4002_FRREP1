package ca.ulaval.glo4002.marv1n.uat.steps;

import ca.ulaval.glo4002.core.person.Person;
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
        state().request = new Request(5, 5, new Person(), null);
        state().request.reserve(new Room(5, "Une salle"));
    }

    @Given("An existing pending reservation")
    public void givenAnExistingPendingReservation() {
        state().request = new Request(5, 5, new Person(), null);
    }

    @Given("An existing reservation")
    public void givenAnExistingReservation() {

    }

    @When("I cancel a reserved reservation")
    public void whenICancelAReservedReservation() {
        state().request.cancel();
    }

    @When("I cancel a pending reservation")
    public void whenICancelAPendingReservation() {
        state().request.cancel();
    }

    @Then("The reserved reservation is cancel")
    public void thenTheReservedReservationIsCancel() {
        assertEquals(RequestStatus.CANCELED, state().request.getRequestStatus());
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
