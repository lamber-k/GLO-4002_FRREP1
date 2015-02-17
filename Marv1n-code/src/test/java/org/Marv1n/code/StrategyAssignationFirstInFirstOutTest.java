package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
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

    private List<IReservable> reservableList;
    private IStrategyAssignation assigner;
    @Mock
    private IReservableRepository reservables;
    @Mock
    private IReservable mockReservable;
    @Mock
    private Request mockRequest1;
    @Mock
    private Request mockRequest2;

    @Before
    public void init() {
        this.pendingRequest = new ArrayList<>();
        this.assigner = new StrategyAssignationFirstInFirstOut();
        this.pendingRequest.add(this.mockRequest1);
        this.reservableList = new ArrayList<>();
        this.reservableList.add(mockReservable);
        when(this.reservables.findAll()).thenReturn(this.reservableList);
    }

    @Test
    public void WhenEnoughReservableAreAvailableAndAssignationIsStarted_AllRequestShouldBeAssigned() {
        this.assigner.assignReservables(this.pendingRequest, this.reservables);
        assertTrue(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenNoEnoughReservableAreAvailableAndAssignationIsStarted_SomeRequestWontBeAssigned() {
        when(this.mockReservable.isBooked()).thenReturn(true);
        this.assigner.assignReservables(this.pendingRequest, this.reservables);
        assertFalse(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenAssignationIsRunCallToReservable_BookedAreDoneToCheckAvailability() {
        this.assigner.assignReservables(this.pendingRequest, this.reservables);
        verify(this.mockReservable, times(ONE_TIME)).isBooked();
    }

    @Test
    public void WhenAssignationIsRun_CallingReservableBookToBook_ShouldBeCalledOnlyOnce() throws ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        when(this.mockReservable.isBooked()).thenReturn(false).thenReturn(true);
        this.pendingRequest.add(this.mockRequest2);

        this.assigner.assignReservables(this.pendingRequest, this.reservables);

        verify(this.mockReservable, times(ONE_TIME)).book(any());
    }
}