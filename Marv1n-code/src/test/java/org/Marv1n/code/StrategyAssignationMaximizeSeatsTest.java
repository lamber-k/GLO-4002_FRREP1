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
<<<<<<< HEAD
    private List<IReservable> IReservableList;
    private IStrategyAssignation assigner;
    @Mock
    private IReservable mockIReservable1;
    @Mock
    private IReservable mockIReservable2;
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
        this.IReservableList = new ArrayList<>();
        this.assigner = new StrategyAssignationMaximizeSeats();
        this.pendingRequest.add(this.mockRequest1);

        this.IReservableList.add(this.mockIReservable1);
=======
        this.assigner = new StrategyAssignationMaximizeSeats();
        this.pendingRequest.add(this.mockRequest1);
        this.reservableList = new ArrayList<Reservable>();
        this.reservableList.add(mockReservable);
        when(this.reservables.findAll()).thenReturn(this.reservableList);
>>>>>>> Reservation System. Accepted ?
    }

    @Test
    public void WhenEnoughReservableAreAvailableAndAssignationIsStartedAllRequestShouldBeAssigned() {
        when(this.mockIReservable1.isBooked()).thenReturn(false);
        this.assigner.assignReservables(this.pendingRequest, this.IReservableList);
        assertTrue(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenNoEnoughReservableAreAvailableAndAssignationIsStartedSomeRequestWontBeAssigned() {
        when(this.mockIReservable1.isBooked()).thenReturn(true);
        this.assigner.assignReservables(this.pendingRequest, this.IReservableList);
        assertFalse(this.pendingRequest.isEmpty());
    }

    @Test
    public void WhenAssignationIsRun_CallToReservableIsBookedAreDoneToCheckAvailability() {
        this.assigner.assignReservables(this.pendingRequest, this.IReservableList);
        verify(this.mockIReservable1, times(ONE_TIME)).isBooked();
    }

    @Test
<<<<<<< HEAD
    public void WhenAssignationIsRun_CallToReservableBookToBookTheReservableAndBookIsCalledOnlyOnce() {
        when(this.mockIReservable1.isBooked()).thenReturn(false).thenReturn(true);
=======
    public void WhenAssignationIsRun_CallToReservableBookToBookTheReservableAndBookIsCalledOnlyOnce() throws ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        when(this.mockReservable.isBooked()).thenReturn(false).thenReturn(true);
>>>>>>> Reservation System. Accepted ?
        this.pendingRequest.add(this.mockRequest2);

        this.assigner.assignReservables(this.pendingRequest, this.IReservableList);

        verify(this.mockIReservable1, times(ONE_TIME)).book(any());
    }

    @Test
    public void WhenAssignationIsRunCallToReservableGetNumberSeatsAndCallToRequest_GetSeatsNeededAreDone() {
        when(this.mockIReservable1.isBooked()).thenReturn(false);

        this.assigner.assignReservables(this.pendingRequest, this.IReservableList);

<<<<<<< HEAD
        verify(this.mockRequest1, times(ONE_TIME)).getNumberOdSeatsNeeded();
        verify(this.mockIReservable1, times(ONE_TIME)).getNumberSeats();
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsNotBestThanFirst_ReturnTheFirst() {
        when(this.mockIReservable1.hasGreaterCapacityThan(this.mockIReservable2)).thenReturn(false);
        this.IReservableList.add(this.mockIReservable2);

        this.assigner.assignReservables(this.pendingRequest, this.IReservableList);

        verify(this.mockIReservable1, times(ONE_TIME)).isBooked();
    }

    @Test
    public void assignationIsRun_TheSecondBestRoomIsBestThanFirst_ReturnTheSecond() {
        when(this.mockIReservable1.hasGreaterCapacityThan(this.mockIReservable2)).thenReturn(true);
        this.IReservableList.add(this.mockIReservable2);

        this.assigner.assignReservables(this.pendingRequest, this.IReservableList);

        verify(this.mockIReservable2, times(ONE_TIME)).isBooked();
=======
        verify(this.mockRequest1, times(ONE_TIME)).getNumberOfSeatsNeeded();
        verify(this.mockReservable, times(ONE_TIME)).hasEnoughCapacity(any());
>>>>>>> Reservation System. Accepted ?
    }
}
