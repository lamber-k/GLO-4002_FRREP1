package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.StrategyAssignation.StrategyAssignation;
import org.Marv1n.code.StrategyAssignation.StrategyAssignationMaximizeSeats;
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
public class StrategyAssignationMaximizeSeatsTest {

    private final static Integer ONE_TIME = 1;

    private List<Request> pendingRequest;
    private List<Reservable> reservableList;
    private StrategyAssignation assigner;
    @Mock
    private IReservableRepository reservables;
    @Mock
    private Reservable mockReservable;
    @Mock
    private Request mockRequest1;
    @Mock
    private Request mockRequest2;

    @Before
    public void init() {
        this.pendingRequest = new ArrayList<>();
        this.assigner = new StrategyAssignationMaximizeSeats();
        this.pendingRequest.add(this.mockRequest1);
        this.reservableList = new ArrayList<Reservable>();
        this.reservableList.add(mockReservable);
        when(this.reservables.findAll()).thenReturn(this.reservableList);
    }

    @Test
    public void WhenEnoughReservableAreAvailableAndAssignationIsStartedAllRequestShouldBeAssigned() {
        when(this.mockReservable.isBooked()).thenReturn(false);
        this.assigner.assignReservables(this.pendingRequest, this.reservables);
        assertTrue(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenNoEnoughReservableAreAvailableAndAssignationIsStartedSomeRequestWontBeAssigned() {
        when(this.mockReservable.isBooked()).thenReturn(true);
        this.assigner.assignReservables(this.pendingRequest, this.reservables);
        assertFalse(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenAssignationIsRun_CallToReservableIsBookedAreDoneToCheckAvailability() {
        this.assigner.assignReservables(this.pendingRequest, this.reservables);
        verify(this.mockReservable, times(ONE_TIME)).isBooked();
    }

    @Test
    public void WhenAssignationIsRun_CallToReservableBookToBookTheReservableAndBookIsCalledOnlyOnce() throws ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        when(this.mockReservable.isBooked()).thenReturn(false).thenReturn(true);
        this.pendingRequest.add(this.mockRequest2);

        this.assigner.assignReservables(this.pendingRequest, this.reservables);

        verify(this.mockReservable, times(ONE_TIME)).book(any());
    }

    @Test
    public void WhenAssignationIsRunCallToReservableGetNumberSeatsAndCallToRequest_GetSeatsNeededAreDone() {
        when(this.mockReservable.isBooked()).thenReturn(false);
        this.assigner.assignReservables(this.pendingRequest, this.reservables);

        verify(this.mockRequest1, times(ONE_TIME)).getNumberOfSeatsNeeded();
        verify(this.mockReservable, times(ONE_TIME)).hasEnoughCapacity(any());
    }
}
