package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Repository.Reservable.IReservableRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
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
public class StrategyEvaluationMaximizeSeatsTest {

    private List<IReservable> reservableList;
    private IStrategyEvaluation assignerStrategy;
    @Mock
    private IReservable reservableMock;
    @Mock
    private IReservable anotherReservableMock;
    @Mock
    private Request requestMock;
    @Mock
    private Request anotherRequestMock;
    @Mock
    private IReservableRepository reservableRepositoryMock;
    @Mock
    private IReservationRepository reservationRepositoryMock;

    @Before
    public void initializeStrategy() throws ReservationNotFoundException {
        reservableList = new ArrayList<>();
        assignerStrategy = new StrategyEvaluationMaximizeSeats();
        reservableList.add(reservableMock);
        loadDefaultBehaviours();
    }

    private void loadDefaultBehaviours() throws ReservationNotFoundException {
        when(reservableRepositoryMock.findAll()).thenReturn(reservableList);
        when(reservableMock.hasEnoughCapacity(anyInt())).thenReturn(true);
        when(anotherReservableMock.hasEnoughCapacity(anyInt())).thenReturn(true);
        when(reservationRepositoryMock.findReservationByReservable(any(IReservable.class))).thenThrow(ReservationNotFoundException.class);
    }

    @Test
    public void assignationIsRun_WhenTheSecondBestRoomIsNotBestThanFirst_ThenReturnTheFirst() throws ReservationNotFoundException {
        when(reservableMock.hasGreaterCapacityThan(anotherReservableMock)).thenReturn(false);
        reservableList.add(anotherReservableMock);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationRepositoryMock, requestMock);

        assertEquals(result.getBestReservableMatch(), reservableMock);
    }

    @Test
    public void assignationIsRun_WhenTheSecondBestRoomIsBestThanFirst_ThenReturnTheSecond() throws ReservationNotFoundException {
        when(reservableMock.hasGreaterCapacityThan(anotherReservableMock)).thenReturn(true);
        reservableList.add(anotherReservableMock);

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationRepositoryMock, requestMock);

        assertEquals(result.getBestReservableMatch(), anotherReservableMock);
    }

    @Test
    public void whenOneReservableCanTBeFoundReturnsEmptyEvaluationResult() throws ReservationNotFoundException {
        ReservationRepository reservationsRepository = mock(ReservationRepository.class);
        when(reservationsRepository.findReservationByReservable(any(IReservable.class))).thenReturn(mock(Reservation.class));

        ReservableEvaluationResult result = assignerStrategy.evaluateOneRequest(reservableRepositoryMock, reservationsRepository, requestMock);

        assertFalse(result.matchFound());
    }
}
