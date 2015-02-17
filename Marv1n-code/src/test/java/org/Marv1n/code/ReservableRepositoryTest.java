package org.Marv1n.code;

import org.Marv1n.code.Repository.ReservableRepository;
import org.Marv1n.code.Reservable.Reservable;
import org.junit.Before;

import java.util.UUID;

import static org.mockito.Mockito.mock;

public class ReservableRepositoryTest {
    private ReservableRepository reservableRepository;
    private Reservable mockReservable;
    private UUID mockReservableID;

    @Before
    public void setUp() throws Exception {

        this.reservableRepository = new ReservableRepository();
        this.mockReservable = mock(Reservable.class);
        this.mockReservableID = UUID.randomUUID();
    }

}
