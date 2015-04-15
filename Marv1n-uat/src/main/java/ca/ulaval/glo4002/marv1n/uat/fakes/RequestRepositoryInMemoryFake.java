package ca.ulaval.glo4002.marv1n.uat.fakes;

import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.persistence.Repository;
import ca.ulaval.glo4002.core.request.Request;

import java.util.LinkedList;
import java.util.List;

public class RequestRepositoryInMemoryFake implements Repository<Request> {

    private List<Request> requestList = new LinkedList<>();

    @Override
    public void persist(Request request) {
        requestList.add(request);
    }

    @Override
    public void remove(Request request) throws ObjectNotFoundException {
        if (!requestList.remove(request)) {
            throw new ObjectNotFoundException();
        }
    }
}