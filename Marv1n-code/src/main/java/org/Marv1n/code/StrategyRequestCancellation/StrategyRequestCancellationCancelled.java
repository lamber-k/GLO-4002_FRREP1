package org.Marv1n.code.StrategyRequestCancellation;

import org.Marv1n.code.Request;

public class StrategyRequestCancellationCancelled implements IStrategyRequestCancellation {

    @Override
    public void cancelRequest(Request request) {
        //Nothing to do, request already cancelled
    }
}
