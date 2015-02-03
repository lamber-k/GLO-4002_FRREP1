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

@RunWith(MockitoJUnitRunner.class)
public class StrategyAssignationFirstInFirstOutTest {

    private static List<Request> pendingRequest;
    private static List<Room> rooms;
    private static StrategyAssignation assignator;

    @Mock
    private static Room mocRoom1;
    @Mock
    private static Request mocRequest1;

    @Before
    public void init() {
        this.pendingRequest = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.assignator = new StrategyAssignationFirstInFirstOut();
        this.pendingRequest.add(mocRequest1);

    }

    @Test
    public void WhenEnoughRoomAreAvalibleAndAssignationIsStartedAllRequestShouldBeAssigned() {
        this.rooms.add(mocRoom1);

        this.assignator.assingRooms(this.pendingRequest, this.rooms);

        assertTrue(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenNoEnoughRoomAreAvalibleAndAssignationIsStartedSomeRequestWontBeAssigned() {
        this.assignator.assingRooms(this.pendingRequest, this.rooms);

        assertFalse(this.pendingRequest.isEmpty());
    }

}