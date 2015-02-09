package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StrategyAssignationMaximizeSeatsTest {

    private static List<Request> pendingRequest;
    private static List<Room> rooms;
    private static StrategyAssignation assigner;
    @Mock
    private static Room mocRoom;
    @Mock
    private static Request mocRequest1;
    @Mock
    private static Request mocRequest2;
    private int oneTime = 1;

    @Before
    public void init() {
        pendingRequest = new ArrayList<>();
        rooms = new ArrayList<>();
        assigner = new StrategyAssignationMaximizeSeats();
        pendingRequest.add(mocRequest1);
        rooms.add(mocRoom);
    }

    @Test
    public void WhenEnoughRoomAreAvailableAndAssignationIsStartedAllRequestShouldBeAssigned() {
        when(mocRoom.isBooked()).thenReturn(false);

        assigner.assignRooms(pendingRequest, rooms);

        assertTrue(pendingRequest.isEmpty());
    }

    @Test
    public void WhenNoEnoughRoomAreAvailableAndAssignationIsStartedSomeRequestWontBeAssigned() {
        when(mocRoom.isBooked()).thenReturn(true);

        assigner.assignRooms(pendingRequest, rooms);

        assertFalse(pendingRequest.isEmpty());
    }

    @Test
    public void WhenAssignationIsRunCallToRoomIsBookedAreDoneToCheckAvailability() {
        assigner.assignRooms(pendingRequest, rooms);

        verify(mocRoom, times(this.oneTime)).isBooked();
    }

    @Test
    public void WhenAssignationIsRunCallToRoomBookToBookTheRoomAndBookIsCalledOnlyOnce() {
        when(mocRoom.isBooked()).thenReturn(false).thenReturn(true);
        pendingRequest.add(mocRequest2);

        assigner.assignRooms(pendingRequest, rooms);

        verify(mocRoom, times(this.oneTime)).book(any());
    }

    @Test
    public void WhenAssignationIsRunCallToRoomGetNumberSeatsAndCallToRequestGetSeatsNeededAreDone() {
        when(mocRoom.isBooked()).thenReturn(false);
        assigner.assignRooms(pendingRequest, rooms);

        verify(mocRequest1, times(this.oneTime)).getNumberOdSeatsNeeded();
        verify(mocRoom, times(this.oneTime)).getNumberSeats();
    }
}
