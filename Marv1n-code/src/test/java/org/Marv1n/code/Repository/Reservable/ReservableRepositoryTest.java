package org.Marv1n.code.Repository.Reservable;

import org.Marv1n.code.Reservable.IReservable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static junit.framework.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class ReservableRepositoryTest {
    private ReservableRepository reservableRepository;
    @Mock
    private IReservable mockIReservable;

    @Before
    public void setUp() throws Exception {
        this.reservableRepository = new ReservableRepository();
    }

    @Test
    public void respositoryContainsReservableWhenFindAllThenReturnAllReservable() throws Exception {
        this.reservableRepository.create(mockIReservable);

        List<IReservable> results = this.reservableRepository.findAll();

        assertFalse(results.isEmpty());
    }
}