package org.Marv1n.code;

import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.StrategyAssignation.IStrategyAssignation;
import org.Marv1n.code.StrategyAssignation.StrategyAssignationFirstInFirstOut;
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

    private static final Integer ONE_TIME = 1;

    private List<Request> pendingRequest;
    private List<IReservable> IReservables;
    private IStrategyAssignation assigner;
    @Mock
    private IReservable mockIReservable;
    @Mock
    private Request mockRequest1;
    @Mock
    private Request mockRequest2;

    @Before
    public void init() {
        this.pendingRequest = new ArrayList<>();
        this.IReservables = new ArrayList<>();
        this.assigner = new StrategyAssignationFirstInFirstOut();
        this.pendingRequest.add(this.mockRequest1);
        this.IReservables.add(this.mockIReservable);
    }

    @Test
    public void WhenEnoughReservableAreAvailableAndAssignationIsStarted_AllRequestShouldBeAssigned() {
        this.assigner.assignReservables(this.pendingRequest, this.IReservables);
        assertTrue(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenNoEnoughReservableAreAvailableAndAssignationIsStarted_SomeRequestWontBeAssigned() {
        when(this.mockIReservable.isBooked()).thenReturn(true);
        this.assigner.assignReservables(this.pendingRequest, this.IReservables);
        assertFalse(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenAssignationIsRunCallToReservable_BookedAreDoneToCheckAvailability() {
        this.assigner.assignReservables(this.pendingRequest, this.IReservables);
        verify(this.mockIReservable, times(ONE_TIME)).isBooked();
    }

    @Test
    public void WhenAssignationIsRun_CallingReservableBookToBook_ShouldBeCalledOnlyOnce() {
        when(this.mockIReservable.isBooked()).thenReturn(false).thenReturn(true);
        this.pendingRequest.add(this.mockRequest2);

        this.assigner.assignReservables(this.pendingRequest, this.IReservables);

        verify(this.mockIReservable, times(ONE_TIME)).book(any());
    }
}