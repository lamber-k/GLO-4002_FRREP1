package org.Marv1n.code;

import org.Marv1n.code.Repository.ReservableRepository;
import org.Marv1n.code.Reservable.Reservable;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.mock;

public class ReservableRepositoryTest {
    private ReservableRepository reservableRepository;
    private Reservable mockReservable;

    @Before
    public void setUp() throws Exception {

        this.reservableRepository = new ReservableRepository();
        this.mockReservable = mock(Reservable.class);
    }

    @Test
    public void respositoryContainsReservableWhenFindAllThenReturnAllReservable() throws Exception {
        this.reservableRepository.create(mockReservable);

        List<Reservable> results = this.reservableRepository.findAll();

        assertFalse(results.isEmpty());
    }
}
