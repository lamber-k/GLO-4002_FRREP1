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
public class StrategyAssignationFirstInFirstOutTest {

    private static List<Request> pendingRequest;
    private static List<Room> rooms;
    private static StrategyAssignation assignator;

    @Mock
    private static Room mocRoom;
    @Mock
    private static Request mocRequest1;
    @Mock
    private static Request mocRequest2;

    @Before
    public void init() {
        this.pendingRequest = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.assignator = new StrategyAssignationFirstInFirstOut();
        this.pendingRequest.add(this.mocRequest1);
        this.rooms.add(this.mocRoom);
    }

    @Test
    public void WhenEnoughRoomAreAvalibleAndAssignationIsStartedAllRequestShouldBeAssigned() {
        this.assignator.assingRooms(this.pendingRequest, this.rooms);

        assertTrue(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenNoEnoughRoomAreAvalibleAndAssignationIsStartedSomeRequestWontBeAssigned() {
        when(mocRoom.isBooked()).thenReturn(true);

        this.assignator.assingRooms(this.pendingRequest, this.rooms);

        assertFalse(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenAssignationIsRunCallToRoomIsBookedAreDoneToCheckAvalibility() {
        this.assignator.assingRooms(this.pendingRequest, this.rooms);

        verify(mocRoom, times(1)).isBooked();
    }

    @Test
    public void WhenAssignationIsRunCallToRoomBookToBookTheRoomAndBookIsCalledOnlyOnce() {
        when(mocRoom.isBooked()).thenReturn(false).thenReturn(true);
        this.pendingRequest.add(mocRequest2);

        this.assignator.assingRooms(this.pendingRequest, this.rooms);

        verify(mocRoom, times(1)).book(any());
    }

}