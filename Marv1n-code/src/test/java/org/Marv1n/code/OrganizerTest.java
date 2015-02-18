package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Repository.IReservationRepository;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.IReservationFactory;
import org.Marv1n.code.StrategyEvaluation.IStrategyEvaluation;
import org.Marv1n.code.StrategySortRequest.IStrategySortRequest;
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
    private Organizer organizer;

    @Mock
    private IReservableRepository reservableRepository;

    @Mock
    private IReservationRepository reservationRepository;

    @Mock
    private IReservationFactory reservationFactory;

    @Mock
    private TaskScheduler taskScheduler;

    @Mock
    private IReservable mockIReservable;

    @Mock
    private Request aRequest;

    @Mock
    private IStrategyEvaluation mockIStrategyEvaluation;

    @Mock
    private IStrategySortRequest mockIStrategySortRequest;

    @Before
    public void initializeNewOrganizer() {
        this.organizer = new Organizer();
        this.organizer.initialize(this.taskScheduler, DEFAULT_MAXIMUM_PENDING_REQUESTS, this.mockIStrategyEvaluation, this.mockIStrategySortRequest, this.reservableRepository, this.reservationFactory, this.reservationRepository);
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
        this.organizer.addReservable(this.mockIReservable);
        this.organizer.addRequest(this.aRequest);
        assertTrue(this.organizer.hasPendingRequest());
    }

    @Test
    public void organizerWhenTreatPendingRequestThenCallStrategySortRequest() {
        this.organizer.treatPendingRequest();
        verify(this.mockIStrategySortRequest).sortList(any());
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
        this.organizer.setMaximumPendingRequests(1);
        this.organizer.addReservable(this.mockIReservable);
        this.organizer.addRequest(this.aRequest);

        verify(this.taskScheduler).runNow(any());
    }

    @Test
    public void organizerCallsTreatPendingRequestWhenRun() {
        Organizer organizerSpy = spy(this.organizer);
        organizerSpy.run();
        verify(organizerSpy).treatPendingRequest();
    }
}
