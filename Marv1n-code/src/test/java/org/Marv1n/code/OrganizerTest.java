package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.StrategyAssignation.StrategyAssignation;
import org.Marv1n.code.StrategySortRequest.StrategySortRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrganizerTest {

    private static final Integer DEFAULT_MAXIMUM_PENDING_REQUESTS = 2;
    private static final Integer A_MAXIMUM_PENDING_REQUESTS = 5;
    private static final Integer MAXIMUM_ONE_PENDING_REQUEST = 1;
    private static final Integer ONE_TIME = 1;

    private Organizer organizer;

    @Mock
    private IReservableRepository reservableRepository;

    @Mock
    private TaskScheduler taskScheduler;

    @Mock
    private Reservable mockReservable;

    @Mock
    private Request aRequest;

    @Mock
    private StrategyAssignation mockStrategyAssignation;

    @Mock
    private StrategySortRequest mockStrategySortRequest;

    @Before
    public void initializeNewOrganizer() {
        this.organizer = new Organizer(this.reservableRepository);
        this.organizer.initialize(this.taskScheduler, DEFAULT_MAXIMUM_PENDING_REQUESTS, this.mockStrategyAssignation, this.mockStrategySortRequest);
    }

    @Test
    public void newOrganizerHasNoReservable() {
        assertFalse(this.organizer.hasReservable());
    }

    @Test
    public void newOrganizerHasNoPendingRequest() {
        assertFalse(this.organizer.hasPendingRequest());
    }

    @Test
    public void whenAddingRequestOrganizerReportsHavingPendingRequest() {
        this.organizer.addReservable(this.mockReservable);
        this.organizer.addRequest(this.aRequest);
        assertTrue(this.organizer.hasPendingRequest());
    }

    @Test
    public void organizerAfterTreatingPendingRequestsAssignationHasRun() throws Exception {
        this.organizer.addReservable(this.mockReservable);
        this.organizer.addRequest(this.aRequest);

        this.organizer.treatPendingRequest();

        verify(this.mockStrategyAssignation, times(ONE_TIME)).assignReservables(any(), any());
    }

    @Test
    public void organizerWhenTreatPendingRequestThenCallStrategySortRequest(){
        this.organizer.treatPendingRequest();
        verify(this.mockStrategySortRequest, times(ONE_TIME)).sortList(any());
    }

    @Test
    public void organizerWhenTreatPendingRequestNowShouldCallTaskSchedulerRunNow() {
        this.organizer.treatPendingRequestsNow();

        verify(this.taskScheduler).runNow(any(Runnable.class));
    }

    @Test
    public void newOrganizerHasMaximumPendingRequests() {
        assertEquals(DEFAULT_MAXIMUM_PENDING_REQUESTS, this.organizer.getMaximumPendingRequests());
    }

    @Test
    public void newOrganizerReflectsTimerChange() {
        this.organizer.setMaximumPendingRequests(A_MAXIMUM_PENDING_REQUESTS);
        assertEquals(A_MAXIMUM_PENDING_REQUESTS, this.organizer.getMaximumPendingRequests());
    }

    @Test
    public void organizerWhenPendingRequestsReachMaximumPendingRequestsShouldRunAssignation() {
        this.organizer.setMaximumPendingRequests(MAXIMUM_ONE_PENDING_REQUEST);
        this.organizer.addReservable(this.mockReservable);
        this.organizer.addRequest(this.aRequest);

        verify(this.taskScheduler, times(ONE_TIME)).runNow(any());
    }

    @Test
    public void organizerCallsTreatPendingRequestWhenRun() {
        Organizer organizerSpy = spy(this.organizer);
        organizerSpy.run();
        verify(organizerSpy).treatPendingRequest();
    }

    @After
    public void tearDown() {
        this.taskScheduler.cancelScheduler();
    }
}
