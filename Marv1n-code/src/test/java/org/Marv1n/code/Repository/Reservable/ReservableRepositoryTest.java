package org.Marv1n.code.Repository.Reservable;

import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Reservable.IReservable;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.mock;

public class ReservableRepositoryTest {
    private ReservableRepository reservableRepository;
    private IReservable mockIReservable;

    @Before
    public void setUp() throws Exception {

        this.reservableRepository = new ReservableRepository();
        this.mockIReservable = mock(IReservable.class);
    }

    @Test
    public void respositoryContainsReservableWhenFindAllThenReturnAllReservable() throws Exception {
        this.reservableRepository.create(mockIReservable);

        List<IReservable> results = this.reservableRepository.findAll();

        assertFalse(results.isEmpty());
    }
}
