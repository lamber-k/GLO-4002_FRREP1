package ca.ulaval.glo4002.rest;

import ca.ulaval.glo4002.core.ObjectNotFoundException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class ObjectNotFoundHandler implements ExceptionMapper<ObjectNotFoundException> {

    @Context
    private HttpHeaders httpHeaders;

    public Response toResponse(ObjectNotFoundException exception) {
        return Response.status(404)
                .entity(exception.getMessage())
                .type(getAcceptType())
                .build();
    }

    private String getAcceptType() {
        List<MediaType> accepts = httpHeaders.getAcceptableMediaTypes();
        if (accepts.isEmpty()) {
            return accepts.get(0).getType();
        }
        return MediaType.MEDIA_TYPE_WILDCARD;
    }
}
