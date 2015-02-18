package org.Marv1n.code;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.StrategyEvaluation.IStrategyEvaluation;
import org.Marv1n.code.StrategyEvaluation.StrategyEvaluationFirstInFirstOut;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StrategyAssignationFirstInFirstOutTest {

    private static final Integer ONE_TIME = 1;

    private List<IReservable> reservableList;
    private IStrategyEvaluation assigner;
    @Mock
    private IReservable mockIReservable;
    @Mock
    private Request mockRequest1;
    @Mock
    private Request mockRequest2;
    @Mock
    private IReservableRepository reservableRepository;

    @Before
    public void init() {
        this.assigner = new StrategyEvaluationFirstInFirstOut();

        List<IReservable> reservableList = new ArrayList<>();
        this.reservableList.add(this.mockIReservable);

        when(reservableRepository.findAll()).thenReturn(this.reservableList);
    }

    @Test
    public void redoTests() {
        fail();
    }
}