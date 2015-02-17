package org.Marv1n.code;

<<<<<<< HEAD
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.StrategyAssignation.IStrategyAssignation;
=======
import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.StrategyAssignation.StrategyAssignation;
>>>>>>> Reservation System. Accepted ?
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
<<<<<<< HEAD
    private List<IReservable> IReservables;
    private IStrategyAssignation assigner;
    @Mock
    private IReservable mockIReservable;
=======
    private List<Reservable> reservableList;
    private StrategyAssignation assigner;
    @Mock
    private IReservableRepository reservables;
    @Mock
    private Reservable mockReservable;
>>>>>>> Reservation System. Accepted ?
    @Mock
    private Request mockRequest1;
    @Mock
    private Request mockRequest2;

    @Before
    public void init() {
        this.pendingRequest = new ArrayList<>();
<<<<<<< HEAD
        this.IReservables = new ArrayList<>();
        this.assigner = new StrategyAssignationFirstInFirstOut();
        this.pendingRequest.add(this.mockRequest1);
        this.IReservables.add(this.mockIReservable);
=======
        this.assigner = new StrategyAssignationFirstInFirstOut();
        this.pendingRequest.add(this.mockRequest1);
        this.reservableList = new ArrayList<Reservable>();
        this.reservableList.add(mockReservable);
        when(this.reservables.findAll()).thenReturn(this.reservableList);
>>>>>>> Reservation System. Accepted ?
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
<<<<<<< HEAD
    public void WhenAssignationIsRun_CallingReservableBookToBook_ShouldBeCalledOnlyOnce() {
        when(this.mockIReservable.isBooked()).thenReturn(false).thenReturn(true);
=======
    public void WhenAssignationIsRun_CallingReservableBookToBook_ShouldBeCalledOnlyOnce() throws ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        when(this.mockReservable.isBooked()).thenReturn(false).thenReturn(true);
>>>>>>> Reservation System. Accepted ?
        this.pendingRequest.add(this.mockRequest2);

        this.assigner.assignReservables(this.pendingRequest, this.IReservables);

        verify(this.mockIReservable, times(ONE_TIME)).book(any());
    }
}