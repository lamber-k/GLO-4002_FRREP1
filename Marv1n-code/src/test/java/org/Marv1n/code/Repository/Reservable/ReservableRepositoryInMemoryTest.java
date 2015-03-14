package org.Marv1n.code.Repository.Reservable;

import org.Marv1n.code.Reservable.IReservable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class ReservableRepositoryInMemoryTest {

    private ReservableRepositoryInMemory reservableRepository;
    @Mock
    private IReservable reservableMock;

    @Before
    public void setUp() throws Exception {
        reservableRepository = new ReservableRepositoryInMemory();
    }

    @Test
    public void repositoryContainsReservable_WhenFindAll_ThenReturnAllReservable() throws Exception {
        reservableRepository.create(reservableMock);

        List<IReservable> results = reservableRepository.findAll();

        assertFalse(results.isEmpty());
    }
}
