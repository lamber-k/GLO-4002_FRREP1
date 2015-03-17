package org.Marv1n.code.Repository.Reservable;

import org.Marv1n.code.Reservable.Reservable;
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
    private Reservable reservableMock;

    @Before
    public void initializeReservableRepositoryInMemory() throws Exception {
        reservableRepository = new ReservableRepositoryInMemory();
    }

    @Test
    public void givenNotEmptyRepository_WhenFindAll_ThenReturnAllReservable() throws Exception {
        reservableRepository.create(reservableMock);
        List<Reservable> results = reservableRepository.findAll();
        assertFalse(results.isEmpty());
    }
}
