package core.reservation;

import core.request.Request;
import core.room.Room;
import core.request.evaluation.ReservableEvaluationResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationFactoryTest {

    private static final int A_NUMBER_OF_SEATS_REQUEST = 20;
    private ReservationFactory reservationFactory;
    @Mock
    private Request requestMock;
    @Mock
    private ReservableEvaluationResult evaluationResultMock;
    @Mock
    private Room roomMock;

    @Before
    public void initializeReservationFactory() throws Exception {
        reservationFactory = new ReservationFactory();
    }

    @Test
    public void givenEmptyEvaluation_WhenReservation_ThenReturnEmptyOptional() throws Exception {
        when(evaluationResultMock.matchFound()).thenReturn(false);
        Optional<Reservation> reservation = reservationFactory.reserve(requestMock, evaluationResultMock);
        assertFalse(reservation.isPresent());
    }

    @Test
    public void givenNotEmptyEvaluation_WhenReservation_ThenReturnNotEmptyOptional() {
        when(evaluationResultMock.matchFound()).thenReturn(true);
        when(evaluationResultMock.getBestReservableMatch()).thenReturn(roomMock);
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(A_NUMBER_OF_SEATS_REQUEST);
        when(roomMock.hasEnoughCapacity(A_NUMBER_OF_SEATS_REQUEST)).thenReturn(true);

        Optional<Reservation> reservation = reservationFactory.reserve(requestMock, evaluationResultMock);

        Assert.assertEquals(roomMock, reservation.get().getReserved());
        Assert.assertEquals(requestMock, reservation.get().getRequest());
    }

    @Test
    public void givenInsufficientCapacityEvaluation_WhenReservation_ThenReturnEmptyOptional() {
        when(evaluationResultMock.matchFound()).thenReturn(true);
        when(evaluationResultMock.getBestReservableMatch()).thenReturn(roomMock);
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(A_NUMBER_OF_SEATS_REQUEST);
        when(roomMock.hasEnoughCapacity(A_NUMBER_OF_SEATS_REQUEST)).thenReturn(false);

        Optional<Reservation> reservation = reservationFactory.reserve(requestMock, evaluationResultMock);

        assertFalse(reservation.isPresent());
    }
}
