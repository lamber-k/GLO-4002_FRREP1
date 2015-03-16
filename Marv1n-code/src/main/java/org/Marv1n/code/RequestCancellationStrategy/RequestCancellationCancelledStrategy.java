package org.Marv1n.code.RequestCancellationStrategy;

import org.Marv1n.code.Request;

public class RequestCancellationCancelledStrategy implements RequestCancellationStrategy {

    @Override
    public void cancelRequest(Request request) {
        //Nothing to do, request already cancelled
    }
}
