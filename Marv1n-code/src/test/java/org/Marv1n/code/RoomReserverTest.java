package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Rafaël on 21/01/2015.
 */
public class RoomReserverTest {

    private RoomReserver roomReserver;
    private Integer DEFAULT_TIMER = 180;
    private Integer A_TIMER = 120;

    @Before
    public void createARoomReserverAndInitialize()
    {
        this.roomReserver = new RoomReserver();
        this.roomReserver.Initialize();
    }

    @Test
    public void NewRoomReserverDoNotHaveRooms() {
        assertFalse(this.roomReserver.haveRooms());
    }

    @Test
    public void NewRoomReserverWhenAddRoomHaveRoom() {
        this.roomReserver.AddRoom(new Room());
        assertTrue(this.roomReserver.haveRooms());
    }

    @Test
    public void NewRoomReserverDoNotHavePendingReservation() {
        assertFalse(this.roomReserver.HavePendingReservation());
    }

    @Test
    public void NewRoomReserverWhithoutRoomWhenReserveRoomReturnFalse() {
        assertFalse(this.roomReserver.ReserveRoom(new Reservation()));
    }

    @Test
    public void RoomResserverWhithOneRoomWhenReserveRoomHavePendingReservation() {
        this.roomReserver.AddRoom(new Room());
        this.roomReserver.ReserveRoom(new Reservation());
        assertTrue(this.roomReserver.HavePendingReservation());
    }

    @Test
    public void NewRoomReserverHaveDefaultTimer() {
        assertEquals(DEFAULT_TIMER, this.roomReserver.GetReservationIntervalTimer());
    }

    @Test
    public void NewRoomReserverWhenChangeReservationIntervalTimer() {
        this.roomReserver.SetReservationIntervalTime(A_TIMER);
        assertEquals(A_TIMER, this.roomReserver.GetReservationIntervalTimer());
    }

    @Test
    public void RoomReserverWithOneRoomAndOneReservationWhenRoomAssignToReservationHaveNoPendingReservation() {
        this.roomReserver.AddRoom(new Room());
        this.roomReserver.ReserveRoom(new Reservation());
        this.roomReserver.SetReservationIntervalTime(2);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertFalse(this.roomReserver.HavePendingReservation());
    }
}
