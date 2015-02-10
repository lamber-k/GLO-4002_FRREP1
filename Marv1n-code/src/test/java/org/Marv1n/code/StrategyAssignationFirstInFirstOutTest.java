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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StrategyAssignationFirstInFirstOutTest {

    private static final int ONE_TIME = 1;
    private List<Request> pendingRequest;
    private List<Room> rooms;
    private StrategyAssignation assigner;
    @Mock
    private Room mocRoom;
    @Mock
    private Request mocRequest1;
    @Mock
    private Request mocRequest2;

    @Before
    public void init() {
        this.pendingRequest = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.assigner = new StrategyAssignationFirstInFirstOut();
        this.pendingRequest.add(mocRequest1);
        this.rooms.add(this.mocRoom);
    }

    @Test
    public void WhenEnoughRoomAreAvailableAndAssignationIsStarted_AllRequestShouldBeAssigned() {
        this.assigner.assignRooms(this.pendingRequest, this.rooms);

        assertTrue(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenNoEnoughRoomAreAvailableAndAssignationIsStarted_SomeRequestWontBeAssigned() {
        when(this.mocRoom.isBooked()).thenReturn(true);

        this.assigner.assignRooms(this.pendingRequest, this.rooms);

        assertFalse(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenAssignationIsRunCallToRoom_BookedAreDoneToCheckAvailability() {
        this.assigner.assignRooms(this.pendingRequest, this.rooms);

        verify(this.mocRoom, times(ONE_TIME)).isBooked();
    }

    @Test
    public void WhenAssignationIsRun_CallingRoomBookToBook_ShouldBeCalledOnlyOnce() {
        when(this.mocRoom.isBooked()).thenReturn(false).thenReturn(true);
        this.pendingRequest.add(this.mocRequest2);

        this.assigner.assignRooms(this.pendingRequest, this.rooms);

        verify(this.mocRoom, times(ONE_TIME)).book(any());
    }

}