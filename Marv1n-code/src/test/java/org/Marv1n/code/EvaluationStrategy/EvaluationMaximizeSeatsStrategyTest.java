package org.Marv1n.code.EvaluationStrategy;

import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepositoryInMemory;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.Reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EvaluationMaximizeSeatsStrategyTest {

    private List<Reservable> reservableList;
    private EvaluationStrategy assignerStrategy;
    @Mock
    private Reservable reservableMock;
    @Mock
    private Reservable anotherReservableMock;
    @Mock
    private Request requestMock;
    @Mock
    private Request anotherRequestMock;
    @Mock
    private ReservableRepository reservableRepositoryMock;
    @Mock
    private ReservationRepository reservationRepositoryMock;

    @Before
    public void initializeEvaluationMaximizeSeatsStrategy() throws ReservationNotFoundException {
        reservableList = new ArrayList<>();
        assignerStrategy = new MaximizeSeatsEvaluationStrategy();
        reservableList.add(reservableMock);
        loadDefaultBehaviours();
    }

    private void loadDefaultBehaviours() throws ReservationNotFoundException {
        when(reservableRepositoryMock.findAll()).thenReturn(reservableList);
        when(reservableMock.hasEnoughCapacity(anyInt())).thenReturn(true);
        when(anotherReservableMock.hasEnoughCapacity(anyInt())).thenReturn(true);
        when(reservationRepositoryMock.findReservationByReservable(any(Reservable.class))).thenThrow(ReservationNotFoundException.class);
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsNotBestThanFirst_ThenReturnTheFirst() throws ReservationNotFoundException {
        when(reservableMock.hasGreaterOrEqualCapacityThan(anotherReservableMock)).thenReturn(false);
        reservableList.add(anotherReservableMock);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationRepositoryMock, requestMock);

        assertEquals(result.getBestReservableMatch(), reservableMock);
    }

    @Test
    public void givenAssignationIsRun_WhenTheSecondBestRoomIsBestThanFirst_ThenReturnTheSecond() throws ReservationNotFoundException {
        when(reservableMock.hasGreaterOrEqualCapacityThan(anotherReservableMock)).thenReturn(true);
        reservableList.add(anotherReservableMock);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationRepositoryMock, requestMock);

        assertEquals(result.getBestReservableMatch(), anotherReservableMock);
    }

    @Test
    public void givenAssignationIsRun_WhenOneReservableCanNotBeFound_ThenReturnEmptyEvaluationResult() throws ReservationNotFoundException {
        ReservationRepositoryInMemory reservationsRepository = mock(ReservationRepositoryInMemory.class);
        when(reservationsRepository.findReservationByReservable(any(Reservable.class))).thenReturn(mock(Reservation.class));

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationsRepository, requestMock);

        assertFalse(result.matchFound());
    }
}
